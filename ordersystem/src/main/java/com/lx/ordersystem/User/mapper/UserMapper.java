package com.lx.ordersystem.User.mapper;

import com.lx.ordersystem.User.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2019-03-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
