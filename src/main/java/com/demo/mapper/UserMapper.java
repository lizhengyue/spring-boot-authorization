package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAll(@Param("a")String a);

    IPage<User> selectPageVo(Page<?> page);
}
