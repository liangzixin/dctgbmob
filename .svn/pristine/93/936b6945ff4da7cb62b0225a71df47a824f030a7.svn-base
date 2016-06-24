package com.scme.order.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Ttables;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

public class FoodsService extends BaseService{

	private String json="";
	private List<Tfoods> foods=null;
	private Tfoods food=null;

	/**
	 * 查询所有菜单
	 * @return List<Tfoods> foods
	 */
	public List<Tfoods> QueryAllFoods()
	{
		String path = HttpUtil.BASE_URL+"foods!queryAllFoods.action";
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
				foods =getGson().fromJson(json, new TypeToken<List<Tfoods>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foods;
	}

	/**
	 * 根据菜的Id查询餐菜的信息
	 * @param foodsId
	 * @return Ttables
	 */
	public Tfoods queryFoodsFoodsId(int foodsId)
	{
		String path = HttpUtil.BASE_URL+"foods!queryFoodsByFoodsId.action?foodsId="+foodsId+"";
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
				food =getGson().fromJson(json, new TypeToken<Tfoods>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return food;
	}

	/**
	 * 根据菜的类型查询菜单
	 * @param typeId 菜的类型Id
	 * @return foods 菜的对象集合
	 * */
	public List<Tfoods> queryFoodsByTypeId(int typeId)
	{
		String path = HttpUtil.BASE_URL+"foods!queryFoodsByFoodsTypeId.action?typeId="+typeId+"";
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
				foods =getGson().fromJson(json, new TypeToken<List<Tfoods>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foods;
	}

}
