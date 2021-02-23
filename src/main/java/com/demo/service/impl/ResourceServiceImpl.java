package com.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.entity.Resource;
import com.demo.dao.ResourceMapper;
import com.demo.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

		@Override
		public List<ResourceVo> listResourceByRoleId(Long roleId){
			QueryWrapper<Resource> query = Wrappers.query();
			query.eq("rr.role_id",roleId).isNull("re.parent_id");
			List<ResourceVo> resourceVos = baseMapper.listResource(query);
			resourceVos.forEach(x->{
				QueryWrapper<Resource> sonQuery = Wrappers.query();
				sonQuery.eq("re.parent_id",x.getResourceId()).eq("rr.role_id",roleId);
				List<ResourceVo> son = baseMapper.listResource(sonQuery);
				x.setSubs(son);
			});
			return resourceVos;
		}
}
