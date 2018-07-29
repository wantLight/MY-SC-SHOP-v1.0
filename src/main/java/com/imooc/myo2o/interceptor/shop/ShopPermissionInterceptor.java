package com.imooc.myo2o.interceptor.shop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imooc.myo2o.entity.Shop;

/**
 * 店家管理系统操作验证拦截器
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(
				"shopList");
		if (currentShop != null && shopList != null) {
			for (Shop shop : shopList) {
				if (shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		return false;
	}
}
