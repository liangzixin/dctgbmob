package com.scme.order.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.scme.order.model.Tfoods;
import com.scme.order.model.Ttables;
import com.scme.order.service.OrdersService;
import com.scme.order.service.TablesService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 餐桌详细信息
 */
public class TableDetailedActivity extends Activity {

	private TextView tvDetailedTablesName;
	private TextView tvDetailedTablesStatus;
	private TextView tvDetailedTablesPersonNum;
	private TextView tvDetailedTablesDesc;
	private TextView tvTablesFoodsPriceNum;
	private TextView tvTablesFoodsNums;
	private ListView lvDetailedTables;
	private Button btnSettlCount;
	private String tablesStatus;
	private int tabId;
	private float priceNum=0;
	private Ttables tables;
	private List<Tfoods> foods;
	private ProgressDialog progressDialog;
	private List<HashMap<String, Object>> foodsList=new ArrayList<HashMap<String,Object>>();
	private  int tabStatus;//餐桌状态
	private int settlCount;//结账按钮显示状态
	private int foodsNums=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_detailed);
		tvDetailedTablesName=(TextView) findViewById(R.id.tvDetailedTablesName);
		tvDetailedTablesStatus=(TextView) findViewById(R.id.tvDetailedTablesStatus);
		tvDetailedTablesPersonNum=(TextView) findViewById(R.id.tvDetailedTablesPersonNum);
		tvDetailedTablesDesc=(TextView) findViewById(R.id.tvDetailedTablesDesc);
		tvTablesFoodsPriceNum=(TextView) findViewById(R.id.tvTablesFoodsPriceNum);
		tvTablesFoodsNums=(TextView) findViewById(R.id.tvTablesFoodsNums);
		lvDetailedTables=(ListView) findViewById(R.id.lvDetailedTables);
		btnSettlCount=(Button) findViewById(R.id.btnSettlConut);
		
		//获得绑定参数
		Bundle bundle=this.getIntent().getExtras();
	    //获得餐桌信息
	    tabId=bundle.getInt("tabId");
	    tabStatus=bundle.getInt("tabStatus");
	    settlCount=bundle.getInt("SeeltCount");
		System.out.println();
	    System.out.println("seelt==="+settlCount);
		final TablesService tbs=new TablesService();
		
		progressDialog = new ProgressDialog(this); 
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {
		    	  tables=tbs.queryTablesByTabId(tabId);//在线程中完成数据请求
		    	  foods=tbs.queryFoodsByTabId(tabId);	    	  
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
	 * 结账按钮处理事件
	 * @throws IOException 
	 * */
	public void btn_settl_conut_Event(View view) throws IOException
	{
		OrdersService os=new OrdersService();
		os.settlCountOrdersByTabId(tabId);
        new AlertDialog.Builder(this).setTitle("结账成功！").setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();     
				intent.setClass(TableDetailedActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}).show();
	}
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); 
            showView(tables, foods);
            SimpleAdapter simpleAdapter=new SimpleAdapter(TableDetailedActivity.this, foodsList, 
    				R.layout.detailed_tables_foods_item, new String[]{"foodsName","foodsPrice"},
    				new int[]{R.id.tvFoodsItemName,R.id.tvFoodsItemPrice});
    		lvDetailedTables.setAdapter(simpleAdapter);   		
            super.handleMessage(msg); 
        } 
    };
    
    /**
     * 显示视图
     * @param tables 餐桌对象
     * @param foods 菜单对象集合
     * */
    public void showView(Ttables tables, List<Tfoods> foods)
    {
    	if(tables.getTstatus()==0)
		{
			tablesStatus="空闲";
		}
		else {
			tablesStatus="服务";
		}
		tvDetailedTablesName.setText("餐桌名称："+tables.getTabName());
		tvDetailedTablesPersonNum.setText("餐桌人数："+tables.getTpersonNum());
		tvDetailedTablesStatus.setText("餐桌状态："+tablesStatus);
		tvDetailedTablesDesc.setText("餐桌备注："+tables.getTdesc());
		//List<Tfoods> foods=tbs.queryFoodsByTabId(tabId);
		//如果餐桌为服务状态就查询消费信息
		if(tabStatus==1)
		{
		     for(int i=0;i<foods.size();i++)
		     {
			       HashMap<String, Object>map=new HashMap<String, Object>();
		           map.put("foodsName", foods.get(i).getFname());
			       map.put("foodsPrice", foods.get(i).getFprice());
			       foodsList.add(map);
			       priceNum=(float) (priceNum+foods.get(i).getFprice());
			       foodsNums++;
		     }
		
		String strPticeNum="消费金额："+Float.toString(priceNum);
		tvTablesFoodsPriceNum.setText(strPticeNum);
		tvTablesFoodsNums.setText("菜品数量："+foodsNums);
		}
		if(settlCount==1)
		{
			btnSettlCount.setVisibility(0);
		}
    }

}
