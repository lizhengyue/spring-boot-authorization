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

	String nextCodeByConfig(String type);

	@Transactional(rollbackFor = Exception.class)
	CodeInfo updateData4Thread(CodeInfo codeInfo);
}
