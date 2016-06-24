package com.scme.order.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Torders;
import com.scme.order.model.Tusers;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

public class OrdersService extends BaseService{

	private String json="";
	private List<Torders> ordersList=null;

	/**
	 * 添加订单
	 * @param tabId 餐桌Id
	 * @param price 订单总价
	 * */
	public void addOreders(int  tabId,Double price) throws Exception
	{
		String path=HttpUtil.BASE_URL+"orders!insterOrder.action?tabId="+tabId+"&price="+price+"";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		System.out.println("----------"+ conn.getResponseCode());
	}

	/**
	 * 查询最新订单Id
	 * @param 餐桌
	 * @return 订单Id
	 */
	public int queryOrdersByNewOredesId(int tabId) throws Exception
	{
		String json="";
		int ordersId=0;
		String path=HttpUtil.BASE_URL+"orders!queryOrdersByNewOredesId.action?tabId="+tabId+"";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		if (200 == conn.getResponseCode())
		{
			//获取输入流
			InputStream is = conn.getInputStream();
			ToolsHandler toolsHandler=new ToolsHandler();
			byte[] data=toolsHandler.InputStreamToByte(is);
			json=new String(data);
			System.out.println(json);
			if(json!="0")
			{
				JSONObject jsonObject=new JSONObject(json);
				ordersId=jsonObject.getInt("oredersId");
			}
		}
		return ordersId;
	}

	/**
	 * 根据餐桌Id结账(最新账单)
	 * 改变餐桌状态
	 * 改变订单状态
	 * @param TabId 餐桌Id
	 * @throws IOException
	 */
	public void settlCountOrdersByTabId(int TabId) throws IOException
	{
		String path=HttpUtil.BASE_URL+"orders!settlCountOrdersByTabId.action?tabId="+TabId+"";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		System.out.println("结账完成----------"+ conn.getResponseCode());
	}

	/**
	 * 查询所有订单
	 * @return List<Torders> foods 订单对象集合
	 */
	public List<Torders> QueryAllOrders()
	{
		String path = HttpUtil.BASE_URL+"orders!queryAllOrders.action";
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			if (200 == conn.getResponseCode())
			{
				//获取输入流
				InputStream is = conn.getInputStream();
				ToolsHandler toolsHandler=new ToolsHandler();
				byte[] data=toolsHandler.InputStreamToByte(is);
				json=new String(data);
				System.out.println(json);
				ordersList =getGson().fromJson(json, new TypeToken<List<Torders>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ordersList;
	}
	/**
	 * 查询今天订单
	 * @return List<Torders> foods 订单对象集合
	 */
	public List<Torders> QueryTodayOrders()
	{
		String path = HttpUtil.BASE_URL+"orders!queryTodayOrders.action";
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			if (200 == conn.getResponseCode())
			{
				//获取输入流
				InputStream is = conn.getInputStream();
				ToolsHandler toolsHandler=new ToolsHandler();
				byte[] data=toolsHandler.InputStreamToByte(is);
				json=new String(data);
				System.out.println(json);
				ordersList =getGson().fromJson(json, new TypeToken<List<Torders>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ordersList;
	}
}
