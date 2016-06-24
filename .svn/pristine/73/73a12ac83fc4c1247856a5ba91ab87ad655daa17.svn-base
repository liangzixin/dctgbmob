package com.scme.order.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.scme.order.model.Torders;
import com.scme.order.service.OrdersService;
import com.scme.order.service.TablesService;
import com.scme.order.util.Pictures;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 查看订单
 * */
public class OrdersListActivity extends Activity {

	private ListView lvOrders;
	private ProgressDialog progressDialog;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> ordersMapList=new ArrayList<HashMap<String,Object>>();
	private List<Torders> ordersList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders_list);
		lvOrders=(ListView) findViewById(R.id.lvOrders);
		
		progressDialog = new ProgressDialog(this); 
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {	    	  
		    	  OrdersService ordersService=new OrdersService();
		    	  TablesService tablesService=new TablesService();	  
		    	  //ordersList=ordersService.QueryAllOrders();
				 ordersList=ordersService.QueryTodayOrders();
		    	    for(int i=0;i<ordersList.size();i++)
			  	     {
			  	        	//在线程中完成数据请求
			  		       HashMap<String, Object>map=new HashMap<String, Object>();
			  		       String tabName=tablesService.queryTablesNameByTabId(ordersList.get(i).getTablesId());
			  		       map.put("ordersId",ordersList.get(i).getOid());
			  	           map.put("isCheckOut", IsCheckOut(ordersList.get(i).getIsCheckOut()));
			  		       map.put("price", ordersList.get(i).getOprice());
			  		       map.put("date", ordersList.get(i).getOdate());
			  		       map.put("tabName",tabName );
			  		       ordersMapList.add(map);  
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

	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); 
            simpleAdapter=new SimpleAdapter(OrdersListActivity.this, ordersMapList, R.layout.orders_item, 
    				new String[]{"ordersId","isCheckOut","price","date","tabName"}, 
    				new int[]{R.id.tvOrdersItemOid,R.id.tvOrdersItemIsCheckOut,R.id.tvOrdersItemPrice,
    				R.id.tvOrdersItemDate,R.id.tvOrdersItemTabName});
    		lvOrders.setAdapter(simpleAdapter);
            super.handleMessage(msg); 
        } 
    };
    
    public String IsCheckOut(int status)
    {
    	String str="";
    	if(status==0)
    		str="否";
    	else
    		str="是";
    	return str;
    }

}
