package com.lx.ordersystem.Discount.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lx
 * @since 2019-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableField("createTime")
    private LocalDateTime createTime;

    private Float discount;


}
