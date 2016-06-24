package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.scme.order.model.Dydh;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Tusers;
import com.scme.order.service.FoodsService;
import com.scme.order.service.UserService;
import com.scme.order.service.DydhService;
import com.scme.order.util.CustomViewBinder;
import com.scme.order.util.Pictures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 查看全部菜单
 * 可根据菜品类型选择
 * */
public class MainLzxListActivity extends Activity implements OnItemSelectedListener, OnItemClickListener {


	private String[] selectMainLzxType={"首页","吃饭打卡"};
	private Spinner spSelectMainLzxType;
	private ListView mainLzxListView;
	private ListView mainLzxListView7;
	private ListView mainLzxListViewDydh;
	private TextView mainTextView;
	private TextView textView;
	static String userNamel="";
	static int userId1=0;
	private ArrayAdapter<String>arrayAdapter;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog1;
	private ProgressDialog progressDialog2;
	private ProgressDialog progressDialog3;
	private List<HashMap<String, Object>> mainLzxList=new ArrayList<HashMap<String,Object>>();
	private List<HashMap<String, Object>> mainLzxList7=new ArrayList<HashMap<String,Object>>();
	private List<HashMap<String, Object>> mainLzxListDydh=new ArrayList<HashMap<String,Object>>();
	private SimpleAdapter dydhSimpleAdapter;
	private SimpleAdapter usersSimpleAdapter;
	private SimpleAdapter usersSimpleAdapter7;
	private List<Tfoods>foods;
	private List<Tusers>users;
	private List<Tusers>users7;
	private List<Dydh>dydhs;
	private FoodsService foodsService=new FoodsService();
	private UserService userService=new UserService();
	private DydhService dydhService=new DydhService();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainlzx_list);
		textView=(TextView) findViewById(R.id.tvMainUserName);
		textView.setText("昆明市东川区企业退休人员管理办公室移动OA  "+LoginActivity.titleUserName);
		userNamel=LoginActivity.titleUserName;
		userId1=LoginActivity.titleUserId;
		spSelectMainLzxType=(Spinner) findViewById(R.id.spFoodsTypeSelect);
		arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectMainLzxType);
		//设置下拉列表的风格
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSelectMainLzxType.setAdapter(arrayAdapter);
	spSelectMainLzxType.setOnItemSelectedListener(this);
		mainLzxListView=(ListView) findViewById(R.id.lvSeletFoodsType);
		mainLzxListView7=(ListView) findViewById(R.id.lv7DayBirthday);
		mainLzxListViewDydh=(ListView) findViewById(R.id.lvDydhBirthday);
//        mainTextView=(TextView)findViewById(R.id.textView);

	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		System.out.println(arg2);
		final int typeId=arg2;
		if (arg2 == 0)//类型Id为0查询全部
		{
		progressDialog = new ProgressDialog(this);
	    progressDialog.setMessage("数据加载中  请稍后...");
	    progressDialog.show();
		Thread t=new Thread(new Runnable() {
		      @Override
		      public void run() {
		      try {
				  mainLzxList.clear();//清空一遍List

					  //  foods=foodsService.QueryAllFoods();
					  users = userService.queryTodayBirthday();



                if(users.size()>0){
                 mainTextView.setText("今日过生日职工");
				  for (int i = 0; i < users.size(); i++) {
					  //在线程中完成数据请求
					  HashMap<String, Object> map = new HashMap<String, Object>();
					  Pictures pic = new Pictures();
					  Bitmap bmp = pic.getMenuPic(users.get(i).getId() + ".jpg");
					  map.put("foodsImage", bmp);
					  map.put("username", users.get(i).getUserName());
					  map.put("branchid", users.get(i).getBranch().getSubname1(2));
					  map.put("tel", users.get(i).getBirday());

					  mainLzxList.add(map);
				  }
			  }
		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      myHandler.sendMessage(myHandler.obtainMessage());
		      }
		 });
	      t.start();

		//未来一星期过生日人员
		progressDialog1 = new ProgressDialog(this);
		progressDialog1.setMessage("数据加载中1  请稍后...");
		progressDialog1.show();
		Thread t1=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mainLzxList7.clear();//清空一遍List

						//  foods=foodsService.QueryAllFoods();
						users7=userService.queryToday7Birthday();



					for(int i=0;i<users7.size();i++)
					{
						//在线程中完成数据请求
						HashMap<String, Object>map7=new HashMap<String, Object>();
						Pictures pic=new Pictures();
						Bitmap bmp=pic.getMenuPic(users7.get(i).getId()+".jpg");
						map7.put("foodsImage7",bmp);
						map7.put("username7",users7.get(i).getUserName());
						map7.put("branchid7",users7.get(i).getBranch().getSubname1(2));
						map7.put("tel7", users7.get(i).getBirday());
						mainLzxList7.add(map7);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler1.sendMessage(myHandler1.obtainMessage());
			}
		});
		t1.start();
		//入党周年党员

		progressDialog2 = new ProgressDialog(this);
		progressDialog2.setMessage("数据加载中2  请稍后...");
		progressDialog2.show();
		Thread t2=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mainLzxListDydh.clear();//清空一遍List

						//  foods=foodsService.QueryAllFoods();
						dydhs=dydhService.queryTodayBirthday();



					for(int i=0;i<dydhs.size();i++)
					{
						//在线程中完成数据请求
						HashMap<String, Object>map=new HashMap<String, Object>();
                        map.put("idd",i+1);
						map.put("dydhname",dydhs.get(i).getName());
						map.put("dybmmz",dydhs.get(i).getZbmz().getSubname1(12));
						map.put("lxdh", dydhs.get(i).getLxdh());
						mainLzxListDydh.add(map);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler2.sendMessage(myHandler2.obtainMessage());
			}
		});
		t2.start();
		}else if(arg2==1){
			Intent intent=new Intent();
			Bundle bundle=new Bundle();
		//	bundle.putInt("foodsId", foodsId);
			intent.putExtras(bundle);
			//	intent.setClass(this, FoodsDetailedActivity.class);
			intent.setClass(this, EatListActivity.class);
			startActivity(intent);
		}
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
		//	progressDialog1.dismiss();
            System.out.println("今日过生日人员!!!!");
       //    progressDialog.dismiss();
	//		usersSimpleAdapter=new SimpleAdapter(MainLzxListActivity.this,null,layout.mainlzx_item,new String[]{"aaaaaa"},new int[]{R.id.tvMainLzx});
			//mainTextView.setAdapter(usersSimpleAdapter);

           usersSimpleAdapter=new SimpleAdapter(MainLzxListActivity.this, mainLzxList, R.layout.users_birthday_list,
					new String[]{"foodsImage","branchid","username","tel"},
					new int[]{R.id.ivFoodsItem,R.id.tvUsersBranchid,R.id.tvUsersName,R.id.tvUsersTel});
			mainLzxListView.setAdapter(usersSimpleAdapter);//线程执行完后显示视图
         mainLzxListView.setOnItemClickListener(MainLzxListActivity.this);
			usersSimpleAdapter.setViewBinder(new CustomViewBinder());
			super.handleMessage(msg);
        } 
    };
	private Handler myHandler1 = new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			//   progressDialog.dismiss();
			progressDialog1.dismiss();
			System.out.println("未来７天日过生日人员!!!!!!!!");
			//progressDialog2.dismiss();
			usersSimpleAdapter7=new SimpleAdapter(MainLzxListActivity.this, mainLzxList7, R.layout.users_birthday_list7,
					new String[]{"foodsImage7","branchid7","username7","tel7"},
					new int[]{R.id.ivFoodsItem7,R.id.tvUsersBranchid7,R.id.tvUsersName7,R.id.tvUsersTel7});
			mainLzxListView7.setAdapter(usersSimpleAdapter7);//线程执行完后显示视图
			mainLzxListView7.setOnItemClickListener(MainLzxListActivity.this);
			usersSimpleAdapter7.setViewBinder(new CustomViewBinder());
			super.handleMessage(msg);
		}
	};
	/**
	 * 数据加载完之后消除Loding对话框
	 * 并显示视图
	 * */
	private Handler myHandler2 = new Handler(){
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog2.dismiss();

			System.out.println("今日是放学入党周年纪念党员!!!!");
			//	progressDialog2.dismiss();
			usersSimpleAdapter=new SimpleAdapter(MainLzxListActivity.this, mainLzxListDydh, R.layout.dydh_birthday_list,
					new String[]{"idd","dybmmz","dydhname","lxdh"},
					new int[]{R.id.tvDydhIdd,R.id.tvDydhBranchid,R.id.tvDydhName,R.id.tvDydhTel});
			mainLzxListViewDydh.setAdapter(usersSimpleAdapter);//线程执行完后显示视图
			mainLzxListViewDydh.setOnItemClickListener(MainLzxListActivity.this);
			usersSimpleAdapter.setViewBinder(new CustomViewBinder());
			super.handleMessage(msg);
		}
	};

	/**
     * 菜单项点击事件
     * */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		HashMap<String,String> map=(HashMap<String,String>)mainLzxListView.getItemAtPosition(arg2);
		Object OBtabId=map.get("foodsId");//取得点击列表项的Id
		int foodsId=(Integer) OBtabId;
		System.out.println("============foodsId"+foodsId);
		Intent intent=new Intent();
    	Bundle bundle=new Bundle();
    	bundle.putInt("foodsId", foodsId);
		intent.putExtras(bundle);
	//	intent.setClass(this, FoodsDetailedActivity.class);
		intent.setClass(this, EatListActivity.class);
		startActivity(intent);
	}
	
}
