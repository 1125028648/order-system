package com.lx.ordersystem.Discount.controller;


import com.lx.ordersystem.Discount.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2019-03-10
 */
@RestController
@RequestMapping("/discount")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class DiscountController {

	@Autowired
	private IDiscountService discountService;

	@PostMapping(value = "/selectOne")
	public Float selectOne(){
		return discountService.selectOne();
	}

	@GetMapping(value = "/insertOne")
	public boolean insertOne(Float discount){
		return discountService.insertOne(discount);
	}
}
