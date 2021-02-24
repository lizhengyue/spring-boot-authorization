package com.demo.service;

import com.demo.entity.CodeConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 编码生成配置 服务类
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
public interface ICodeConfigService extends IService<CodeConfig> {

	/**
	 * 获取参数
	 * @param bizType
	 * @return
	 */
	CodeConfig getByBizType(String bizType);
}
