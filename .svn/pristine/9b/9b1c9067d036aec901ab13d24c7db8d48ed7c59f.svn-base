package com.scme.order.ui;

import java.net.URI;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.scme.order.adpater.MySimpleAdapter;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Ttables;
import com.scme.order.service.FoodsService;
import com.scme.order.service.OrdersDetailsService;
import com.scme.order.service.OrdersService;
import com.scme.order.service.TablesService;
import com.scme.order.util.Cart;
import com.scme.order.util.CustomViewBinder;
import com.scme.order.util.Pictures;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * 查询所有菜单
 * 点餐
 */
public class SelectOrdersFoodsListActivity extends Activity implements OnItemClickListener, OnItemSelectedListener {

	private ListView foodsListView;
	private ListView foodsOrdersListView;
	private TextView tvFoodsOrdersPrice;
	private TextView tvFoodsOrdersNums;
	private EditText etFoodsOrdersPresonNum;//人数
	private ProgressBar progressBar; 
	private List<HashMap<String, Object>> foodsList=new ArrayList<HashMap<String,Object>>();
	private List<HashMap<String, Object>> food=new ArrayList<HashMap<String,Object>>();
	private List<Tfoods>foodsOrders=new ArrayList<Tfoods>();
	private OrdersListSimpleAdapter ordersListSimpleAdapter;
	private int tabId;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private Spinner spIntoOrdersFoodsTypeSelect;
	private ArrayAdapter<String>arrayAdapter;
	private List<Tfoods>foods;
	private FoodsService foodsService=new FoodsService();
	private FoodsListSimpleAdapter foodsListSimpleAdapter;
	private String[] selectFoodsTyep={"全部","甜品类","鱼类","猪肉类","牛肉类","海鲜类","素食类","鸡肉类","主食类","其他肉类","汤类"};
	private int foodsNums=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_orders_foods_list);
		foodsListView=(ListView) findViewById(R.id.lvFoodsList);
		foodsOrdersListView=(ListView)findViewById(R.id.lvFoodsOrdersList);
		tvFoodsOrdersPrice=(TextView) findViewById(R.id.tvFoodsOrderPrice);
		etFoodsOrdersPresonNum=(EditText) findViewById(R.id.etFoodsOrdersPresonNum);
		spIntoOrdersFoodsTypeSelect=(Spinner) findViewById(R.id.spIntoOrdersFoodsTypeSelect);
		tvFoodsOrdersNums=(TextView) findViewById(R.id.tvFoodsOrderNums);
		progressBar=(ProgressBar) findViewById(R.id.pbFoodsList);
		
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectFoodsTyep);
		//设置下拉列表的风格
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spIntoOrdersFoodsTypeSelect.setAdapter(arrayAdapter);
		spIntoOrdersFoodsTypeSelect.setOnItemSelectedListener(this);
		
		//获得绑定参数
		Bundle bundle=this.getIntent().getExtras();
		//获得餐桌Id
		tabId=bundle.getInt("tabId");

	    foodsListSimpleAdapter=new FoodsListSimpleAdapter(this, foodsList, R.layout.foods_item,
				new String[]{"foodsImage","foodsName","foodsPrice"}, 
				new int[]{R.id.ivFoodsItem,R.id.tvFoodsItemName,R.id.tvFoodsItemPrice});
		foodsListView.setAdapter(foodsListSimpleAdapter);
		//设置ListView列表的点击事件
		foodsListView.setOnItemClickListener(this);
		foodsListSimpleAdapter.setViewBinder(new CustomViewBinder());
			
		ordersListSimpleAdapter=new OrdersListSimpleAdapter(this, food, R.layout.foods_orders_item, 
				new String[]{"foodsName","foodsPrice"}, new int[]{R.id.tvFoodsOrderItemName,
				R.id.tvFoodsOrderItemPrice});
		
	}
	
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
	
	/**
	 * 菜单列表适配器
	 */
	public class FoodsListSimpleAdapter extends SimpleAdapter{

		//int tabId=Cart.tabid;
		private List<? extends Map<String, ?>> foods;
		public FoodsListSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
			this.foods=data;
			// TODO Auto-generated constructor stub
		}

	    @Override  
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {  
	        // TODO Auto-generated method stub  
	        final int mPosition = position;  
	        convertView = super.getView(position, convertView, parent);  
	        //获取菜的Map对象
	        HashMap<String,String> foodMap=(HashMap<String, String>) foods.get(position);
	        Button buttonAdd = (Button) convertView.findViewById(R.id.btnAddFoods);// id为你自定义布局中按钮的id  
	        buttonAdd .setTag(foodMap);
	        buttonAdd.setOnClickListener(new View.OnClickListener() {  
	        	
	            @Override  
	            public void onClick(View v) {  
	                // TODO Auto-generated method stub  
	            	HashMap<String,Object> btnFoodMap=(HashMap<String, Object>) v.getTag();
	                System.out.println(btnFoodMap.get("foodsName"));
	                System.out.println("tabId====="+tabId);
	                food.add(btnFoodMap);	   
	                foodsNums++;
	                mHandler.obtainMessage(R.id.btnAddFoods, mPosition, 0) .sendToTarget();  
	            }  
	        });  
	        return convertView;  
	    }      
	}
	
	
	/**
	 * 定单列表适配器
	 */
	public class OrdersListSimpleAdapter extends SimpleAdapter{

		private List<? extends Map<String, ?>> ordersFood;
		public OrdersListSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
			this.ordersFood=data;
			// TODO Auto-generated constructor stub
		}

	    @Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	        final int mPosition = position;  
	        convertView = super.getView(position, convertView, parent);  
	        //获取菜的Map对象
	        HashMap<String,String> ordersFoodMap=(HashMap<String, String>) ordersFood.get(position);
	        Button buttonDel = (Button) convertView.findViewById(R.id.btnDeleteOrdersFood);// id为你自定义布局中按钮的id  
	        buttonDel .setTag(ordersFoodMap);
	        buttonDel.setOnClickListener(new View.OnClickListener()
	        {  
	        	
	            @Override  
	            public void onClick(View v) 
	            {  
	                // TODO Auto-generated method stub 
	            	HashMap<String,String> ordersFoodMap=(HashMap<String, String>) v.getTag();
	                food.remove(ordersFoodMap);//根据对象标实删除List集合中的一条菜的订单
	                foodsNums--;
	                mHandler.obtainMessage(R.id.btnAddFoods, mPosition, 0) .sendToTarget();  
	            }  
	        });  
	        return convertView;  
	    }      
	}
	
	
	
	private Handler mHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case R.id.btnAddFoods:  
                //处理Button点击事件
            	foodsOrdersListView.setAdapter(ordersListSimpleAdapter);//更新订单列表
            	tvFoodsOrdersPrice.setText("总价："+getOredersPrice());
            	tvFoodsOrdersNums.setText("数量："+foodsNums);
            	System.out.println("List长度"+food.size());
                break;  
            }  
        }  
    };
	
	/**
	 * 提交按钮处理事件
	 * */
	public void foods_orders_sumbit_Event(View view) throws Exception
	{
		System.out.println("TabId"+tabId);	
		  progressDialog2 = new ProgressDialog(this); 
	      progressDialog2.setMessage("订单提交中  请稍后..."); 
	      progressDialog2.show();
	      Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {
		    	OrdersService os=new OrdersService();
		  		OrdersDetailsService ods=new OrdersDetailsService();
		    	os.addOreders(tabId, getOredersPrice());//添加订单
		  		int ordersId=os.queryOrdersByNewOredesId(tabId);
		  		System.out.println("=========ordersId"+ordersId);
		  		for(int i=0;i<food.size();i++)
		  		{
		  			int foodsId=(Integer) food.get(i).get("foodsId");
		  			ods.addOredersDetails(foodsId,ordersId);//添加订单明细 
		  		}
		  		TablesService ts=new TablesService();
		  		String strPresonNum=etFoodsOrdersPresonNum.getText().toString();
		  		Integer prosonNum=Integer.parseInt(strPresonNum);
		  		ts.insertTables(tabId, prosonNum);
		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		      myHandler2.sendMessage(myHandler2.obtainMessage()); 
		      } 
		 });
		t.start();
		//t.join();
		
		new AlertDialog.Builder(this).setTitle("订单提交成功！").setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();     
				intent.setClass(SelectOrdersFoodsListActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}).show();
	}
	
	/**
	 * 计算订单总价
	 * @paramprice订单总价
	 * */
	public Double getOredersPrice()
	{
		Double price=0.00;
		for(int i=0;i<food.size();i++)
		{
			price=price+(Double) food.get(i).get("foodsPrice");
		}
		return price;
	}
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
//	private Handler myHandler = new Handler(){
//        @SuppressLint("HandlerLeak")
//		@Override 
//        public void handleMessage(Message msg) { 
//            progressDialog.dismiss(); 
//            foodsListView.setAdapter(foodsListSimpleAdapter);
//            super.handleMessage(msg); 
//        } 
//    };
    
    /**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler2 = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog2.dismiss(); 
            super.handleMessage(msg); 
        } 
    };

    /**
     * 下拉菜单事件处理
     * */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
//		progressDialog = new ProgressDialog(this); 
//	    progressDialog.setMessage("数据加载中  请稍后..."); 
//	    progressDialog.show();
//	    final int typeId=arg2;
//		Thread t=new Thread(new Runnable() { 
//		      @Override 
//		      public void run() {         
//		      try {	    	  
//		    	    foodsList.clear();//清空一遍List
//		    	    if(arg2==0)//类型Id为0查询全部
//			    	  {
//		    	    	  foods=foodsService.QueryAllFoods();
//			    	  }
//		    	    else {
//		    	    	foods=foodsService.queryFoodsByTypeId(typeId);	//类型Id不为0分类型查询	
//					}	    	    
//		    	   	    	   
//		    	    for(int i=0;i<foods.size();i++)
//			  	     {
//			  	        	//在线程中完成数据请求
//			  		       HashMap<String, Object>map=new HashMap<String, Object>();
//			  		       Pictures pic=new Pictures();
//			  			   Bitmap bmp=pic.getMenuPic(foods.get(i).getFpicName());	
//			  		       map.put("foodsImage",bmp);
//			  	           map.put("foodsName", foods.get(i).getFname());
//			  		       map.put("foodsPrice", foods.get(i).getFprice());
//			  		       map.put("foodsId", foods.get(i).getFid());
//			  		       foodsList.add(map);    
//			  	     }
//		       } catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//		      myHandler.sendMessage(myHandler.obtainMessage()); 
//		      } 
//		 });
//	      t.start();
		GetFoodListTask getFoodListTask=new GetFoodListTask();
		getFoodListTask.execute(arg2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 加载菜单
	 */
	public class GetFoodListTask extends AsyncTask< Integer , Integer,  List<HashMap<String, Object>> >
	{
		@Override
		protected List<HashMap<String, Object>> doInBackground(Integer... params) {
			foodsListSimpleAdapter.notifyDataSetChanged();
			foodsList.clear();//清空一遍List
			publishProgress(0);// 将会调用onProgressUpdate(Integer... progress)方法
			int typeId=params[0];
    	    if(typeId==0)//类型Id为0查询全部
	    	  {
    	    	  foods=foodsService.QueryAllFoods();
	    	  }
    	    else {
    	    	foods=foodsService.queryFoodsByTypeId(typeId);	//类型Id不为0分类型查询	
			}	    	    
    	   	    	 
    	    int rogressNum=0;
    	    int foodsSize=foods.size();
    	    int foodsAdd=100/foodsSize;
    	    if(foodsAdd==0)
    	    	foodsAdd=1;
    	    System.out.println("FoodsSize=="+foodsSize);
    	    System.out.println("FoodsAdd"+foodsAdd);
    	    for(int i=0;i<foods.size();i++)
	  	     {
    	    	   rogressNum=foodsAdd*(i+1);
	  	        	//在线程中完成数据请求
	  		       HashMap<String, Object>map=new HashMap<String, Object>();
	  		       Pictures pic=new Pictures();
	  			   Bitmap bmp=pic.getMenuPic(foods.get(i).getFpicName());	
	  		       map.put("foodsImage",bmp);
	  	           map.put("foodsName", foods.get(i).getFname());
	  		       map.put("foodsPrice", foods.get(i).getFprice());
	  		       map.put("foodsId", foods.get(i).getFid());
	  		       foodsList.add(map);
	  		       System.out.println("进度===="+rogressNum);
	  		       System.out.println("===="+i);
	  		       publishProgress(rogressNum);
	  	     }
    	    publishProgress(100);
			return foodsList;
		}

		@Override
		// 后台任务执行完之后被调用，在ui线程执行
		protected void onPostExecute(List<HashMap<String, Object>> result) {
			// TODO Auto-generated method stub				

			foodsListSimpleAdapter=new FoodsListSimpleAdapter(SelectOrdersFoodsListActivity.this, result, R.layout.foods_item,
					new String[]{"foodsImage","foodsName","foodsPrice"}, 
					new int[]{R.id.ivFoodsItem,R.id.tvFoodsItemName,R.id.tvFoodsItemPrice});
			foodsListSimpleAdapter.setViewBinder(new CustomViewBinder());
            foodsListView.setAdapter(foodsListSimpleAdapter);
            progressBar.setVisibility(View.GONE);//隐藏进度条
		}

		@Override
		// 在doInBackground(Params...)之前被调用，在ui线程执行
		protected void onPreExecute() {
			// TODO Auto-generated method stub		
			progressBar.setVisibility(View.VISIBLE);//显示进度条
			progressBar.setProgress(0);// 进度条复位
		}

		@Override
		// 在调用publishProgress之后被调用，在ui线程执行
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			progressBar.setProgress(values[0]);// 更新进度条的进度
		}
	}

	

	
	
}
