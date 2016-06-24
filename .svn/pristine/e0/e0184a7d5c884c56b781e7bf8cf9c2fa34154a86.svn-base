package com.scme.order.service;

import java.net.HttpURLConnection;
import java.net.URL;

import com.scme.order.util.HttpUtil;

public class OrdersDetailsService extends BaseService{

	/**
	 * 添加订单明细
	 * @param foodsId 菜的Id
	 * @param OrdersId 订单Id
	 * */
	public void addOredersDetails(int  foodsId,int OrdersId) throws Exception
	{
		String path=HttpUtil.BASE_URL+"ordersDetails!intersOdersDetails.action?ordersId="+OrdersId+"&foodsId="+foodsId+"";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		System.out.println("----------"+ conn.getResponseCode());
	}
}
