package com.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 编码生成信息
 * </p>
 *
 * @author lzy
 * @since 2021-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户ID
     */
    private Long tenantId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型，一般代表一种业务类型，或者说是一种生成类型
     */
    private String bizType;

    /**
     * 子编码，编码的子编码，如：20190608
     */
    private String subCode;

    /**
     * 序列的当前值
     */
    private Long currentValue;

    /**
     * 是否删除
     */
    private Integer deleted;


}
