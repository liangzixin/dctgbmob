package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scme.order.model.Tusers;
import com.scme.order.service.EatsService;
import com.scme.order.service.UserService;
import com.scme.order.util.Pictures;
import com.scme.order.util.SerializableMap;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 吃饭年月详情
 */
public class XgmmActivity extends BaseActivity {

	private ProgressDialog progressDialog;
	private Bitmap bmp;
	private SerializableMap map;
	private ActionBar actionBar;
	private String purview;
	private int workerid;
	private String ymonth;
	private Tusers tusers;

	TextView show;
	@InjectView(R.id.tvEatTotalId)	TextView tvEatTotalId;
	@InjectView(R.id.tvEatTotalName)	TextView tvEatTotalName;
	@InjectView(R.id.tvEatTotalYmonth)	TextView tvEatTotalYmonth;
	@InjectView(R.id.tvEatTotalNumber)	TextView tvEatTotalNumber;
	@InjectView(R.id.tvEatTotalPrice)	TextView tvEatTotalPrice;
	@InjectView(R.id.tvEatTotalUnclear)	TextView tvEatTotalUnclear;
	@InjectView(R.id.ivUserPic)	ImageView ivEatTotalPic;
	@InjectView(R.id.button)	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar = getActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eattotal_detailed);
       ButterKnife.inject(this);

       button.setText("交费确定");
		//获得绑定参数
		Bundle bundle=this.getIntent().getExtras();
		//获得餐桌信息
	//	EatTotalId=bundle.getInt("eatTotalid");
	//	map=new SerializableMap();
		map=(SerializableMap) bundle.get("map");
		purview=map.getMap().get("purview").toString();
//	HashMap<String, String> map =(HashMap<String, String>) bundle.get("map");
//		System.out.println("purview:"+purview);
		Object OBtabId = map.getMap().get("workerid");//取得点击列表项的Id
		workerid= (Integer) OBtabId;
		ymonth= map.getMap().get("ym").toString();
	//	System.out.println("MAP1:"+eatTotalid1);
		progressDialog = new ProgressDialog(this);
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {
		    	//获取餐桌列表数据
				  UserService us=new UserService();
				  tusers=us.queryUseUserId(workerid);
				  Pictures pic=new Pictures();
				  bmp=pic.getMenuPic(tusers.getPicName());
				  System.out.println("权限:"+purview);
				  System.out.println("已交:"+map.getMap().get("unclear"));
                  if(tusers.getPurview().equals("系统")&&map.getMap().get("unclear").equals("否")){
					  button.setEnabled(true);
				  }else {
					  button.setEnabled(false);
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
	 * 显示视图
	 * @param bmp 职工的图片
	 * @param map 职工的对象
	 * */
	public void showView(Bitmap bmp,SerializableMap map)
	{
		tvEatTotalId.setText("序号："+ map.getMap().get("eatTotalid").toString());
		tvEatTotalName.setText("姓名："+map.getMap().get("name").toString());
		tvEatTotalYmonth.setText("年月："+map.getMap().get("ym").toString());
		tvEatTotalNumber.setText("份数："+map.getMap().get("fs").toString()+"  份");
		tvEatTotalPrice.setText("金额："+map.getMap().get("je").toString()+"  元");
	tvEatTotalUnclear.setText("是否已交："+map.getMap().get("unclear").toString());
//		tvEatTotalUnclear.setText("是否已交："+purview);
		ivEatTotalPic.setImageBitmap(bmp);
	}
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); //消除Loding对话框
            showView(bmp,map);
            super.handleMessage(msg); 
        } 
    };

	/**
	 * 交费按钮处理事件
	 * */
	public void eatTotalDetail__sumbit_Event(View view) throws Exception
	{
		System.out.println(tvEatTotalName.getText()+"/n"+tvEatTotalYmonth.getText());
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
				// 设置对话框标题
				.setTitle("交费对话框！！！")
				// 设置图标
				.setIcon(R.drawable.icon_laucher)
				.setMessage(tvEatTotalName.getText()+"\n"+tvEatTotalYmonth.getText());
		// 为AlertDialog.Builder添加“确定”按钮
		setPositiveButton(builder);
		// 为AlertDialog.Builder添加“取消”按钮
		setNegativeButton(builder)
				.create()
				.show();


	}
	private AlertDialog.Builder setPositiveButton(
			AlertDialog.Builder builder)
	{
		// 调用setPositiveButton方法添加“确定”按钮
		return builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
//		progressDialog= new ProgressDialog(this);
//		progressDialog.setMessage("订单提交中  请稍后...");
//		progressDialog.show();
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EatsService eatsService=new EatsService();
                     eatsService.updateEatsYmonth(workerid,ymonth);
//					OrdersService os=new OrdersService();
//					OrdersDetailsService ods=new OrdersDetailsService();
//					os.addOreders(tabId, getOredersPrice());//添加订单
//					int ordersId=os.queryOrdersByNewOredesId(tabId);
//					System.out.println("=========ordersId"+ordersId);
//					for(int i=0;i<food.size();i++)
//					{
//						int foodsId=(Integer) food.get(i).get("foodsId");
//						ods.addOredersDetails(foodsId,ordersId);//添加订单明细
//					}
//					TablesService ts=new TablesService();
//					String strPresonNum=etFoodsOrdersPresonNum.getText().toString();
//					Integer prosonNum=Integer.parseInt(strPresonNum);
//					ts.insertTables(tabId, prosonNum);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler2.sendMessage(myHandler2.obtainMessage());
			}
		});
		t.start();
		//	t.join();

		new AlertDialog.Builder(XgmmActivity.this).setTitle("交费提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
//				Bundle bundle = new Bundle();
//				//	bundle.putInt("eatTotalid", eatTotalid);
//				bundle.putSerializable("map",map);
//				intent.putExtras(bundle);
				intent.setClass(XgmmActivity.this,EatTotalPersionActivity.class);
				startActivity(intent);
			}
		}).show();

			}
		});
	}
	private AlertDialog.Builder setNegativeButton(
			AlertDialog.Builder builder)
	{
		// 调用setNegativeButton方法添加“取消”按钮
		return builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				show.setText("单击了【取消】按钮！");
			}
		});
	}

	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler2 = new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss(); //消除Loding对话框
		//	showView(bmp,map);
			super.handleMessage(msg);
		}
	};
}
