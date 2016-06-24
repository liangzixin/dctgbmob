package com.scme.order.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.scme.order.model.Tfoods;
import com.scme.order.service.FoodsService;
import com.scme.order.util.CustomViewBinder;
import com.scme.order.util.Pictures;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

/**
 * 查看全部菜单
 * 可根据菜品类型选择
 * */
public class FoodsListActivity extends Activity implements OnItemSelectedListener, OnItemClickListener {

	private String[] selectFoodsTyep={"全部","甜品类","鱼类","猪肉类","牛肉类","海鲜类","素食类","鸡肉类","主食类","其他肉类","汤类"};
	private Spinner spSelectFoodType;
	private ListView foodsListView;
	private ArrayAdapter<String>arrayAdapter;
	private ProgressDialog progressDialog;
	private List<HashMap<String, Object>> foodsList=new ArrayList<HashMap<String,Object>>();
	private SimpleAdapter foodsSimpleAdapter;
	private List<Tfoods>foods;
	private FoodsService foodsService=new FoodsService();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foods_list);
		spSelectFoodType=(Spinner) findViewById(R.id.spFoodsTypeSelect);
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectFoodsTyep);
		//设置下拉列表的风格
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSelectFoodType.setAdapter(arrayAdapter);
		spSelectFoodType.setOnItemSelectedListener(this);
		foodsListView=(ListView) findViewById(R.id.lvSeletFoodsType);
		
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		System.out.println(arg2);
		final int typeId=arg2;
		
		progressDialog = new ProgressDialog(this); 
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {	    	  
		    	    foodsList.clear();//清空一遍List
		    	    if(arg2==0)//类型Id为0查询全部
			    	  {
		    	    	  foods=foodsService.QueryAllFoods();
			    	  }
		    	    else {
		    	    	foods=foodsService.queryFoodsByTypeId(typeId);	//类型Id不为0分类型查询	
					}	    	    
		    	   	    	   
		    	    for(int i=0;i<foods.size();i++)
			  	     {
			  	        	//在线程中完成数据请求
			  		       HashMap<String, Object>map=new HashMap<String, Object>();
			  		       Pictures pic=new Pictures();
			  			   Bitmap bmp=pic.getMenuPic(foods.get(i).getFpicName());	
			  		       map.put("foodsImage",bmp);
			  	           map.put("foodsName", foods.get(i).getFname());
			  		       map.put("foodsPrice", foods.get(i).getFprice());
			  		       map.put("foodsId", foods.get(i).getFid());
			  		       foodsList.add(map);    
			  	     }
		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		      myHandler.sendMessage(myHandler.obtainMessage()); 
		      } 
		 });
	      t.start();
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 数据加载完之后消除Loding对话框
	 * 并显示视图
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); 
            System.out.println("11111111111111OVER!!!!");
            //progressDialog2.dismiss(); 
            foodsSimpleAdapter=new SimpleAdapter(FoodsListActivity.this, foodsList, R.layout.foods_selet_type_item, 
					new String[]{"foodsImage","foodsName","foodsPrice"},
					new int[]{R.id.ivFoodsSelectTypeItem,R.id.tvFoodsSelectTypeItemName,R.id.tvFoodsSelectTypeItemPrice});
            foodsListView.setAdapter(foodsSimpleAdapter);//线程执行完后显示视图
            foodsListView.setOnItemClickListener(FoodsListActivity.this);
			foodsSimpleAdapter.setViewBinder(new CustomViewBinder());
            super.handleMessage(msg); 
        } 
    };
    
    /**
     * 菜单项点击事件
     * */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		HashMap<String,String> map=(HashMap<String,String>)foodsListView.getItemAtPosition(arg2);   
		Object OBtabId=map.get("foodsId");//取得点击列表项的Id
		int foodsId=(Integer) OBtabId;
		System.out.println("============foodsId"+foodsId);
		Intent intent=new Intent();
    	Bundle bundle=new Bundle();
    	bundle.putInt("foodsId", foodsId);
		intent.putExtras(bundle);
		intent.setClass(this, FoodsDetailedActivity.class);
		startActivity(intent);
	}
	
}
