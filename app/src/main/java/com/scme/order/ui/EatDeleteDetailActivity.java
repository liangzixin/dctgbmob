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
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.model.Teats;
import com.scme.order.model.Tusers;
import com.scme.order.service.EatsService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.Pictures;
import com.scme.order.util.SerializableMap;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 吃饭年月详情
 */
public class EatDeleteDetailActivity extends BaseActivity {

	private ProgressDialog progressDialog;
	private Bitmap bmp;
	private SerializableMap map;
	private ActionBar actionBar;
	private Handler testHandler;
	private String purview;
	private int workerid;
	private Teats eats;
	private String ymonth;
	private Tusers tusers;
	private MyAppVariable myAppVariable;
	private GetDate getDate=new GetDate();
	private int txxxid;
	private boolean str=false;
	TextView show;
//	@InjectView(R.id.tvEatTotalId)	TextView tvEatTotalId;
	@InjectView(R.id.tvEatTotalName)	TextView tvEatTotalName;
	@InjectView(R.id.tvEatTotalYmonth)	TextView tvEatTotalYmonth;
	@InjectView(R.id.tvEatTotalNumber)	TextView tvEatTotalNumber;
	@InjectView(R.id.tvEatTotalPrice)	TextView tvEatTotalPrice;
	@InjectView(R.id.tvEatTotalUnclear)	TextView tvEatTotalUnclear;
	@InjectView(R.id.tvDjr)	TextView tvdjr;
	@InjectView(R.id.ivUserPic)	ImageView ivEatTotalPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar = getActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eatdelete_detailed);
       ButterKnife.inject(this);


		//获得绑定参数
//		Bundle bundle=this.getIntent().getExtras();
		myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
		tusers=myAppVariable.getTusers();
		txxxid=myAppVariable.getTxxxid();
		purview=tusers.getPurview();

		progressDialog = new ProgressDialog(this);
	    progressDialog.setMessage("数据加载中  请稍后..."); 
	    progressDialog.show();
		if(Thread.State.NEW == mThread.getState()) {
		      try {
		    	//获取餐桌列表数据
				 EatsService eatsService=new EatsService();
				  System.out.println("eatid:"+txxxid);
			        eats=eatsService.queryEatsId(txxxid);

				  System.out.println("权限:"+purview);
//				  System.out.println("已交:"+map.getMap().get("unclear"));

		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mThread.start();
		}

	}
	private Thread mThread = new Thread() {
		public void run() {
//			Log.d("TAG", "mThread run");
			Looper.prepare();
			testHandler = new Handler() {
				public void handleMessage(Message msg) {
//					Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
//					System.out.println("我的线程："+msg.what);
					switch (msg.what) {
						//handle message here
						case 1:

							showView(bmp, eats);
//							setSpinner();
						break;
							//send message here
						case 2:

                      break;
					}
					progressDialog.dismiss();
				}
			};
			testHandler.sendEmptyMessage(1);
			Looper.loop();

		}

	};
	/*
 创建菜单项
  */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
		menu.getItem(0).setVisible(false);
		menu.getItem(2).setVisible(false);
		if((tusers.getPurview().equals("系统")||tusers.getPurview().equals("服务"))&&eats.getUnclear().equals("0")){
			menu.getItem(1).setEnabled(true);
			menu.getItem(1).setTitle(R.string.menu_delete);
			menu.getItem(1).setVisible(true);
		}else {
			menu.getItem(1).setEnabled(false);
		}


		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_txxxdetail_mainrz) {


			new AlertDialog.Builder(this).setTitle("删除就餐记录")
					.setMessage("真要删除职工:"+eats.getUser().getName()+"本条就餐记录吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dodelete(txxxid);
				}
			}).show();
			}

		return super.onOptionsItemSelected(item);

	}
	/**
	 * 显示视图
	 * @param bmp 职工的图片
	 * @param eats 职工的对象
	 * */
	public void showView(Bitmap bmp,Teats eats)
	{
//		tvEatTotalId.setText("序号："+eats.getEatid());
		tvEatTotalName.setText("姓名："+eats.getUser().getName());
		tvEatTotalYmonth.setText("就餐时间：" + getDate.getHour(eats.getEatdate()));
		tvEatTotalNumber.setText("份数："+eats.getEatnumber()+"  份");
		tvEatTotalPrice.setText("金额："+eats.getEatnumber()*eats.getUnitprice()+"  元");
	tvEatTotalUnclear.setText("是否已交："+eats.getUnclear());
		tvdjr.setText("登记人："+eats.getOperator());
//		tvEatTotalUncvlear.setText("是否已交："+purview);
		Pictures pic=new Pictures();
//	  bmp=pic.getMenuPic(eats.getUser().getPicName());
		ivEatTotalPic.setImageBitmap(bmp);
	}
	/**
	 * 删除记录
	 *
	 * */
	public void dodelete(int txxxidid) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("删除提交中  请稍后...");
		progressDialog.show();
		testHandler.sendEmptyMessage(2);

		try {
			EatsService eatService = new EatsService();

			str=eatService.DeleteEats(txxxid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(str) {
			new AlertDialog.Builder(this).setTitle("删除提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					startActivity(new Intent(EatDeleteDetailActivity.this, EatAllActivity.class));

				}
			}).show();
		}else{
			Toast.makeText(this, "删除提交错误！！！", Toast.LENGTH_SHORT).show();
		}

	}




}
