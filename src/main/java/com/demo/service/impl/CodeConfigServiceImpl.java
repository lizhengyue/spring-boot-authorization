package com.demo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.demo.entity.CodeConfig;
import com.demo.mapper.CodeConfigMapper;
import com.demo.service.ICodeConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 编码生成配置 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
@Service
public class CodeConfigServiceImpl extends ServiceImpl<CodeConfigMapper, CodeConfig> implements ICodeConfigService {
	@Override
	public CodeConfig getByBizType(String bizType){
		Map<String, Object> map = new HashMap<>(1);
		map.put("biz_type",bizType);
		List<CodeConfig> codeConfigs = listByMap(map);
		 //获取第一条数据和最后一条数据 可以用hutool工具 获取 如下    不用使用stream.first这种
		 //getFirst 获取第一条 需要判断数据是否为空
		 //CodeConfig first = CollectionUtil.getFirst(codeConfigs);

		return CollectionUtil.getLast(codeConfigs);
	}
}
