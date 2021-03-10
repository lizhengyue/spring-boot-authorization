package com.demo.service;

import com.demo.entity.CodeInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 编码生成信息 服务类
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
public interface ICodeInfoService extends IService<CodeInfo> {

	/**
	 * 生成编号 todo 后面这个方法需要近一步优化
	 *
	 * @param type test001
	 * @return
	 */
	String nextCodeByConfig(String type);

	/**
	 * 更新当前value值
	 * 为什么接口上放@Transactional注解目前我还没懂
	 * @param codeInfo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	CodeInfo updateData4Thread(CodeInfo codeInfo);
}
