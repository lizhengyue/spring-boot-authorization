package com.demo.service.impl;

import com.demo.entity.Role;
import com.demo.mapper.RoleMapper;
import com.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2021-02-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
