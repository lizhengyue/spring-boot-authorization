package com.demo.service;

import com.demo.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.vo.ResourceVo;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
public interface IResourceService extends IService<Resource> {

	List<ResourceVo> listResourceByRoleId(Long roleId);
}
