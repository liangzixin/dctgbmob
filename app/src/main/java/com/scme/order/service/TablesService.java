package com.scme.order.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Ttables;
import com.scme.order.model.Tusers;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;


public class TablesService extends BaseService{

	private List<Ttables> tables;
	private Ttables tab;
	private String json="";
	private List<Tfoods> foods;
	/**
	 * 查询所有餐桌
	 * @return List<Ttables> tables
	 */
	public List<Ttables> QueryAllTables()
	{
		String path = HttpUtil.BASE_URL+"tables!queryAllTables.action";
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
				tables =getGson().fromJson(json, new TypeToken<List<Ttables>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;

	}

	/**
	 * 查询空闲餐桌
	 * @return List<Ttables> tables
	 */
	public List<Ttables> QueryNoStatuTables()
	{
		String path = HttpUtil.BASE_URL+"tables!queryNoStatusTables.action";
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
				tables =getGson().fromJson(json, new TypeToken<List<Ttables>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;
	}

	/**
	 * 查询服务中的餐桌
	 * @return List<Ttables> tables 餐桌列表
	 */
	public List<Ttables> QueryStatuTables()
	{
		String path = HttpUtil.BASE_URL+"tables!queryStatusTables.action";
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
				tables =getGson().fromJson(json, new TypeToken<List<Ttables>>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;
	}

	/**
	 * 根据餐桌Id查询餐桌信息
	 * @param tabId
	 * @param
	 */
	public Ttables queryTablesByTabId(int tabId)
	{
		String path = HttpUtil.BASE_URL+"tables!queryTablesByTabId.action?TabId="+tabId+"";
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
				tab =getGson().fromJson(json, new TypeToken<Ttables>() {}.getType());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tab;
	}

	/**
	 * 查询餐桌消费信息
	 * @param TabId 餐桌Id
	 * @return List<Tfoods> foods
	 */
	public List<Tfoods> queryFoodsByTabId(int TabId)
	{
		String path = HttpUtil.BASE_URL+"tables!queryFoodsByTabId.action?TabId="+TabId+"";
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(20000);
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
	 * 餐桌入座，状态改为服务
	 * @param TabId 餐桌Id
	 * @param PersonNum 顾客人数
	 * */
	public void insertTables(int TabId,int PersonNum) throws Exception
	{
		String path=HttpUtil.BASE_URL+"tables!insertTables.action?TabId="+TabId+"&PersonNum="+PersonNum+"";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(5000);
		conn.setRequestMethod("POST");
		System.out.println("----------"+ conn.getResponseCode());
	}

	/**
	 * 根据餐桌Id查询餐桌名称
	 * @param 餐桌Id
	 * @return 餐桌名称
	 */
	public String queryTablesNameByTabId(int tabId) throws Exception
	{
		String json="";
		String tabName="";
		String path=HttpUtil.BASE_URL+"tables!queryTablesNameByTabId.action?TabId="+tabId+"";
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
				tabName=jsonObject.getString("tabName");
			}
		}
		return tabName;
	}
}
