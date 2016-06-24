package com.scme.order.ui;


import com.scme.order.model.Tfoods;
import com.scme.order.service.FoodsService;
import com.scme.order.ui.AllTablesActivity.TablesAdapter;
import com.scme.order.util.Pictures;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 菜品详情
 */
public class FoodsDetailedActivity extends Activity {

	private TextView tvFoodsName;
	private TextView tvFoodType;
	private TextView tvFoodsPrice;
	private TextView tvFoodsDesc;
	private ImageView ivFoodsPic;
	private String foodsType="";
	private int foodsId;
	private ProgressDialog progressDialog;
	private Tfoods food;
	private Bitmap bmp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foods_detailed);
		tvFoodsName=(TextView) findViewById(R.id.tvFoodDetaName);
//		tvFoodType=(TextView) findViewById(R.id.tvFoodDetaType);
//		tvFoodsPrice=(TextView) findViewById(R.id.tvFoodDetaPrice);
//		tvFoodsDesc=(TextView) findViewById(R.id.tvFoodDetapDesc);
		ivFoodsPic=(ImageView) findViewById(R.id.ivFoodsDetaPic);
		
		//获得绑定参数
		Bundle bundle=this.getIntent().getExtras();
		//获得餐桌信息
		foodsId=bundle.getInt("foodsId");
		
		progressDialog = new ProgressDialog(this); 
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {
		    	//获取餐桌列表数据	
		    	FoodsService fs=new FoodsService();
		  		food=fs.queryFoodsFoodsId(foodsId);
		  		Pictures pic=new Pictures();
		  		bmp=pic.getMenuPic(food.getFpicName());
		  		
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
	 * 获取菜的类型
	 */
	public String getFoodType(int foodsId)
	{
		if(foodsId==1)
		{
			foodsType="甜食类";
		}
		if(foodsId==2)
		{
			foodsType="鱼类";
		}
		if(foodsId==3)
		{
			foodsType="猪肉类";
		}
		if(foodsId==4)
		{
			foodsType="牛肉类";
		}
		if(foodsId==5)
		{
			foodsType="海鲜类";
		}
		if(foodsId==6)
		{
			foodsType="素食类";
		}
		if(foodsId==7)
		{
			foodsType="鸡肉类";
		}
		if(foodsId==8)
		{
			foodsType="主食类";
		}
		if(foodsId==9)
		{
			foodsType="其他肉类";
		}
		if(foodsId==10)
		{
			foodsType="汤类";
		}
		return foodsType;
	}
	
	/**
	 * 显示视图
	 * @param bmp 菜的图片
	 * @param food 菜的对象
	 * */
	public void showView(Bitmap bmp,Tfoods food)
	{
		tvFoodsName.setText(food.getFname());
		tvFoodsPrice.setText("价格："+food.getFprice().toString());
		tvFoodType.setText("类型:"+getFoodType(food.getTypeId()));
		tvFoodsDesc.setText("          "+food.getFdesc());
		ivFoodsPic.setImageBitmap(bmp);
	}
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); //消除Loding对话框
            showView(bmp, food);
            super.handleMessage(msg); 
        } 
    };

}
