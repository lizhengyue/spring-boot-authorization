package com.demo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.CodeConfig;
import com.demo.entity.CodeInfo;
import com.demo.mapper.CodeInfoMapper;
import com.demo.service.ICodeConfigService;
import com.demo.service.ICodeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 编码生成信息 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
@Service
public class CodeInfoServiceImpl extends ServiceImpl<CodeInfoMapper, CodeInfo> implements ICodeInfoService {

	private Map<String, UpdateThread> updateThreadMap = new ConcurrentHashMap<>();

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private final static String STORAGE_KEY = "RedisPrefix";

	@Autowired
	protected ICodeConfigService codeConfigService;

	@Override
	public String nextCodeByConfig(String type) {
		CodeConfig codeConfig = codeConfigService.getByBizType(type);
		String analyzedExpression = codeConfig.analyzeExpression();
		long nextNumber = nextCustomCode(type, analyzedExpression);

		int serialLength = codeConfig.getSerialNumberEnd() - codeConfig.getSerialNumberStart();

		String serialNumberStr = StringUtils.right(nextNumber + "", serialLength);

		//填充0值
		serialNumberStr = StringUtils.leftPad(serialNumberStr, serialLength, "0");
		//字符串截取
		return StringUtils.substring(analyzedExpression, 0, codeConfig.getSerialNumberStart()) + serialNumberStr + StringUtils.substring(analyzedExpression, codeConfig.getSerialNumberEnd());
	}



	/**
	 * @param codeInfo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CodeInfo updateData4Thread(CodeInfo codeInfo) {
		int i = baseMapper.updateByIdForThread(codeInfo);
		return codeInfo;
	}



	private long nextCustomCode(String type, String key) {
		// 如果池中不存在需要获取的键值，则从数据库中获取
		// redis会保存两份缓存，1.键=type:key 值=CodeInfo 2.zset类型，键=type，值score=currentValue，值value=key
		// test001:ga20210304NNNN
		String mapKey = type + ":" + key;
		Double value = getCache(CodeInfo.class, type, key);
		//get方法返回的类型是object 所以需要转一下啊
		//RedisPrefix:test001:ga20210304NNNN
		String codeKey = STORAGE_KEY + ":" + mapKey;

		CodeInfo keyInfo = null;
		JSONObject o = (JSONObject)redisTemplate.opsForValue().get(codeKey);
		if(o != null){
			keyInfo = o.toJavaObject(CodeInfo.class);
		}

		if (value == null || keyInfo == null) {
			synchronized (this) {
				//如果缓存没有，就初始化一个对象
				keyInfo = this.initCodeInfo(type, key);
				value = new Double(keyInfo.getCurrentValue());
				//把当前的值放入缓存中
				addCache(CodeInfo.class, type, key, value);
				//把codeInfo对象放入缓存中
				redisTemplate.opsForValue().set(codeKey, keyInfo,3600L,TimeUnit.SECONDS);
			}
		}


		//自增+1
		final Double nextKey = incrementCache(CodeInfo.class, type, key);
		//设置当前值
		keyInfo.setCurrentValue(nextKey.longValue());
		trySave(getFullCacheKey(type, key), keyInfo);
		return nextKey.longValue();
	}

	//保存时使用
	private String getFullCacheKey(String type, String key) {
		return type +":"+ key;
	}

	/**
	 * 初始化键信息
	 *
	 * @param bizType 实体类型
	 * @param subCode 年份
	 * @return CodeInfo
	 */
	private CodeInfo initCodeInfo(String bizType, String subCode) {
		CodeInfo keyInfo = this.getCodeInfo(bizType, subCode);
		if (keyInfo == null) {
			keyInfo = new CodeInfo();
			keyInfo.setBizType(bizType);
			keyInfo.setSubCode(subCode);
			keyInfo.setCurrentValue(0L);
			save(keyInfo);
		}
		return keyInfo;
	}


	/**
	 * 获取配置的编码序列信息
	 */
	public CodeInfo getCodeInfo(String type, String key) {
		Map<String, Object> parameters = new HashMap<>(2);
		parameters.put("bizType", type);
		parameters.put("subCode", key);
		QueryWrapper<CodeInfo> eq = new QueryWrapper<CodeInfo>().eq("biz_type", type).eq("sub_code", key);
		return getOne(eq);
	}



	//保存分数
	public void addCache(Class cls, String key, String value, double score){
		String curKey = getValueCacheKey(key, cls.getSimpleName());
		redisTemplate.opsForZSet().add(curKey, value, score);
	}

	//获取分数
	public Double getCache(Class cls, String key, String value){
		String curKey = getValueCacheKey(key, cls.getSimpleName());
		return redisTemplate.opsForZSet().score(curKey, value);
	}

	//自增
	public Double incrementCache(Class cls, String key, String value){
		String curKey = getValueCacheKey(key, cls.getSimpleName());
		return redisTemplate.opsForZSet().incrementScore(curKey, value, 1);
	}

	//CodeInfoValue:test001
	private String getValueCacheKey(String key, String simpleName) {
		return simpleName + "Value"  + ":" + key;
	}

	/**
	 * 尝试保存，当一定时间内无请求进来时会执行保存操作
	 * @param mapKey
	 * @param keyInfo
	 */
	private synchronized void trySave(String mapKey, CodeInfo keyInfo){
		UpdateThread updateThread = updateThreadMap.get(mapKey);
		if(updateThread == null) {
			synchronized (this) {
				System.out.println("=======================lzy");
				updateThread = updateThreadMap.get(mapKey);
				if (updateThread == null) {
					updateThread = new UpdateThread(this, keyInfo);
					updateThreadMap.put(mapKey, updateThread);
					updateThread.start();
					return;
				}
			}
		}
		if(!updateThread.updateValue(keyInfo.getCurrentValue())){
			//do nothing
			updateThreadMap.remove(mapKey);
			trySave(mapKey, keyInfo);
		}
	}

	class UpdateThread extends Thread {

		private ICodeInfoService keyInfoService;
		private volatile boolean isUpdated = false;
		private volatile boolean isSaved = false;
		private volatile long value = 0;
		private CodeInfo keyInfo;
		public UpdateThread(ICodeInfoService keyInfoService, CodeInfo keyInfo){
			this.keyInfo = keyInfo;
			this.value = keyInfo.getCurrentValue();
			this.keyInfoService = keyInfoService;
		}

		protected boolean updateValue(long value){
			if(isSaved) {
				return false;
			}
			isUpdated = true;
			this.value = value;
			return true;
		}

		@Override
		public void run() {
			System.out.println("=======start=====");
			try {
				System.out.println("=======sleep=====start");
				sleep(500);
				System.out.println("=======sleep=====after");
				System.out.println("=======run====="+isUpdated);
				while(isUpdated) {
					isUpdated = false;
					System.out.println("=================sleep updateData keyInfo================");
					sleep(200);
				}
				isSaved = true;
				keyInfo.setCurrentValue(value);
				keyInfoService.updateData4Thread(keyInfo);
				System.out.println("=================updateData keyInfo================");
			} catch (InterruptedException e) {
				System.out.println("====------catch");
				Thread.interrupted();
			} catch (Exception e) {
				System.out.println("====------catch=============");
				log.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
		}
	}




}
