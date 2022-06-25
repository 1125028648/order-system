package com.lx.ordersystem.Discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.ordersystem.Discount.entity.Discount;
import com.lx.ordersystem.Discount.mapper.DiscountMapper;
import com.lx.ordersystem.Discount.service.IDiscountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2019-03-10
 */
@Service
public class DiscountServiceImpl extends ServiceImpl<DiscountMapper, Discount> implements IDiscountService {

	@Autowired
	private DiscountMapper discountMapper;

	@Override
	public Float selectOne() {
		QueryWrapper<Discount> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("create_time")
				.last("limit 1");
		Discount discount = discountMapper.selectOne(wrapper);
		return discount.getDiscount();
	}

	@Override
	public boolean insertOne(Float discount) {
		Discount oneDiscount = new Discount();
		oneDiscount.setCreateTime(LocalDateTime.now());
		oneDiscount.setDiscount(discount);
		discountMapper.insert(oneDiscount);
		return true;
	}
}
