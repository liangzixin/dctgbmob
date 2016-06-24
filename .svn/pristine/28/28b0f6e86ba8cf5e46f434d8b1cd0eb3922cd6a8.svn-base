package com.scme.order.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import com.scme.order.service.TablesService;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Paint.Join;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 查询所有餐桌
 */
public class TablesListActivity extends Activity implements OnItemClickListener {

	ListView listView;
	private List<HashMap<String, Object>> tableList=new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables_list);
		TablesService queryTables=new TablesService();
		String json="";	
		try {
			//解析为JSON数组
	        JSONArray jsonArray=new JSONArray(json);
	        System.out.println("================="+jsonArray.length());

	        for(int i=0;i<jsonArray.length();i++)
	        {
	        	JSONObject jsonObject=jsonArray.getJSONObject(i);
	        	System.out.println("桌子Id:"+jsonObject.getInt("tabId"));
	        	System.out.println("桌子名称："+jsonObject.getString("tabName"));
	        	System.out.println("餐桌状态："+jsonObject.getString("tstatus"));
	        	System.out.println("餐桌人数："+jsonObject.getString("tpersonNum"));
	        	System.out.println("备        注："+jsonObject.getString("tdesc"));
	        	HashMap<String, Object> map=new HashMap();
	        	map.put("tabId", jsonObject.getInt("tabId"));
	        	map.put("tabName", jsonObject.getString("tabName"));
	        	map.put("tpersonNum", "人数："+jsonObject.getString("tpersonNum"));
	        	if(jsonObject.getString("tstatus").equals("0"))
	        	{
	        	    map.put("tststus", "状态：空闲");
	        	}
	        	else {
					map.put("tststus", "状态：有客");
				}
	        	map.put("tdesc", "备注："+jsonObject.getString("tdesc"));
	        	tableList.add(map);
	        }
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}
        
		listView=(ListView) findViewById(R.id.listView);
		SimpleAdapter simpleAdapter=new SimpleAdapter(this, tableList, R.layout.list_item, 
				new String[]{"tabId","tabName","tpersonNum","tststus","tdesc"},new int[]{R.id.tvTableId,
				R.id.tvTableName,R.id.tvPersonNum,R.id.tvTableStatus,R.id.tvTableDesc});
		listView.setAdapter(simpleAdapter);
		//设置ListView列表的点击事件
		listView.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//获得选中项的HashMap对象   
		HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(arg2);   
		Object OBtabId=map.get("tabId");//取得点击列表项的Id
		int tabId=(Integer) OBtabId;
		Toast.makeText(this, "  当前餐桌Id："+tabId, Toast.LENGTH_SHORT).show();
	}


}
