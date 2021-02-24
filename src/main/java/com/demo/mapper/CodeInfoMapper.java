package com.demo.mapper;

import com.demo.entity.CodeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 编码生成信息 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
public interface CodeInfoMapper extends BaseMapper<CodeInfo> {


	int updateByIdForThread(CodeInfo entity);
}
