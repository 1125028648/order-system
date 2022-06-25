package com.lx.ordersystem.Discount.service;

import com.lx.ordersystem.Discount.entity.Discount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2019-03-10
 */
public interface IDiscountService extends IService<Discount> {

	//查找最新的折扣价
	public Float selectOne();

	//修改折扣
	public boolean insertOne(Float discount);
}
