package com.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.demo.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.vo.ResourceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
	/**
	 * 查询数据
	 * 此方法使用了mybatis-plus自定义条件构造器
	 * @param wrapper
	 * @return
	 */
	List<ResourceVo> listResource(@Param(Constants.WRAPPER) Wrapper<Resource> wrapper);
}
