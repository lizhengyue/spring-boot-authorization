package com.demo.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 这个类的主要目的是为了让数据在插入或者更新时有些字段统一在这里处理
 * 常见的有创建人，创建时间，更新时间，更新人 租户id 这些统一字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println(LocalDateTime.now());
        //这个是判断是否你的实体类中又这个属性 如果有就处理,这样可以节省程序开销
        boolean createTime = metaObject.hasSetter("createTime");
        if (createTime) {
            //setFieldValByName("createTime", LocalDateTime.now(),metaObject);
            setFieldValByName("createTime", new Date(), metaObject);
        }
        // nested exception is org.apache.ibatis.reflection.ReflectionException: Could not set property 'createTime' of 'class com.demo.entity.User' with value
        // '2021-01-31T14:07:00.117' Cause: java.lang.IllegalArgumentException: argument type mismatch
        // LocalDateTime.now() 如果用这个的话 那么实体类中时间地类型也应该是LocalDateTime 不能是Date类型
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);//如果有值就不需要更新
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
