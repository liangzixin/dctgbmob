package com.scme.order.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.scme.order.model.Dydh;
import com.scme.order.model.Tusers;
import com.scme.order.service.DydhService;
import com.scme.order.service.FoodsService;
import com.scme.order.service.ProgressListener;
import com.scme.order.service.UserService;
import com.scme.order.util.CustomViewBinder;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.Pictures;
import com.scme.order.view.PopMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
// * 主菜单
 */
public class MainMenuActivity extends Activity implements ProgressListener, AdapterView.OnItemClickListener {

	private TextView textView;


	static String userNamel = "";
	static int userId1 = 0;
	static String purview = "";
	private Handler testHandler;
	private ListView mainLzxListView;
	private ListView mainLzxListView7;
	private ListView mainLzxListViewDydh;
	private List<HashMap<String, Object>> mainLzxList = new ArrayList<HashMap<String, Object>>();
	private List<HashMap<String, Object>> mainLzxList7 = new ArrayList<HashMap<String, Object>>();
	private List<HashMap<String, Object>> mainLzxListDydh = new ArrayList<HashMap<String, Object>>();

	private ProgressDialog progressDialog;

	private List<Tusers> users;
	private List<Tusers> users7;
	private List<Dydh> dydhs;
	private FoodsService foodsService = new FoodsService();
	private UserService userService = new UserService();
	private DydhService dydhService = new DydhService();

	private SimpleAdapter usersSimpleAdapter;

	private MyAppVariable myAppVariable;

	private Context context = MainMenuActivity.this;
	private PopMenu popMenu;

	@InjectView(R.id.btn_title1)
	TextView button1;
	@InjectView(R.id.btn_title2)
	TextView button2;
	@InjectView(R.id.btn_title3)
	TextView button3;
	@InjectView(R.id.btn_title4)
	TextView button4;
	@InjectView(R.id.btn_title5)
	TextView button5;
	@InjectView(R.id.tvUserDay)
	TextView mainTextView;
	@InjectView(R.id.textView1)
	TextView mainTextView7;
	@InjectView(R.id.textView2)
	TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//
		setContentView(R.layout.activity_mainlzx_list);
		ButterKnife.inject(this);


		mainLzxListView = (ListView) findViewById(R.id.lvSeletFoodsType);
		mainLzxListView7 = (ListView) findViewById(R.id.lv7DayBirthday);
		mainLzxListViewDydh = (ListView) findViewById(R.id.lvDydhBirthday);
		myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
		Tusers users0 = myAppVariable.getTusers();
		purview = users0.getPurview();
		textView = (TextView) findViewById(R.id.tvMainUserName);

		textView.setText("昆明市东川区企业退休人员管理办公室移动OA  " + users0.getUserName());
//
//		if(Thread.State.NEW == mThread.getState()) {
//
//			try {
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			mThread.start();
//
//		}

		button1.setOnClickListener(onViewClick);
		button2.setOnClickListener(onViewClick);
		button3.setOnClickListener(onViewClick);
		button4.setOnClickListener(onViewClick);
		button5.setOnClickListener(onViewClick);
		btn_app_sy();
	}
//
//	private Thread mThread = new Thread() {
//		public void run() {
////			Log.d("TAG", "mThread run");
//			Looper.prepare();
//			testHandler = new Handler() {
//				public void handleMessage(Message msg) {
////					Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
////					System.out.println("我的线程："+msg.what);
//					switch (msg.what) {
//						//handle message here
//						case 1:
//							progressDialog.dismiss();
////							btn_app_sy();
////                            showView(user);
////							setSpinner();
//							break;
//							//send message here
//						case 2:
//							break;
//
//					}
//
//				}
//			};
//			testHandler.sendEmptyMessage(1);
//			Looper.loop();
//
//		}
//
//	};

	// 弹出菜单监听器
	AdapterView.OnItemClickListener popmenuItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			System.out.println("下拉菜单点击" + position + "下拉:");
			popMenu.dismiss();
		}
	};

	// 按钮监听器
	View.OnClickListener onViewClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.btn_title1) {
				System.out.println("下拉菜单点击首页下拉:");
//				popMenu.dismiss();
				btn_app_sy();

			} else if (v.getId() == R.id.btn_title_back) {
//
			} else if (v.getId() == R.id.btn_title2) { // 弹出式菜单
				System.out.println("综合科");
				String[] str2 = new String[]{getString(R.string.checkinout_list),getString(R.string.zhk_qj), getString(R.string.zhk_yz), getString(R.string.zhk_bx), getString(R.string.zhk_dx)};
				String str = "0";
				popMenu = new PopMenu(context, str);
				popMenu.addItems(str2);
				popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								myAppVariable.setOtherquery(false);
								startActivity(new Intent(MainMenuActivity.this, CheckinoutListFyActivity.class));
//								System.out.println("请假");
								break;
							case 1:
								startActivity(new Intent(MainMenuActivity.this, QingjiaListActivity.class));
//								System.out.println("请假");
								break;
							case 2:
								startActivity(new Intent(MainMenuActivity.this, ChuchaiListActivity.class));
								popMenu.dismiss();
								break;
							case 3:
								startActivity(new Intent(MainMenuActivity.this, UserListActivity.class));
								break;
							case 4:
								startActivity(new Intent(MainMenuActivity.this, PlacardListActivity.class));
								break;
						}

					}
				});
//				popMenu.showAsDropDown(v);
				popMenu.showPopUp(v);
			} else if (v.getId() == R.id.btn_title3) { // 弹出式菜单
//				System.out.println("下拉菜单点击");
				String[] str3 = new String[]{getString(R.string.sbk_tx), getString(R.string.sbk_ys)};
				String str = "1";
				popMenu = new PopMenu(context, str);
				popMenu.addItems(str3);
				popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
							case 0:
								myAppVariable.setOtherquery(false);
								startActivity(new Intent(MainMenuActivity.this, TxxxListFyActivity.class));

								break;
							case 1:
								startActivity(new Intent(MainMenuActivity.this, YsryListActivity.class));
								break;
						}
					}
				});
//				popMenu.showAsDropDown(v);
				popMenu.showPopUp(v);
			} else if (v.getId() == R.id.btn_title4) { // 弹出式菜单
//				System.out.println("下拉菜单点击");
				String[] str4 = new String[]{getString(R.string.fwk_dk), getString(R.string.fwk_cx), getString(R.string.fwk_jl), getString(R.string.fwk_hj)};
				String str = "1";
				popMenu = new PopMenu(context, str);
				popMenu.addItems(str4);
				popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {

							case 0:

								startActivity(new Intent(MainMenuActivity.this, EatListActivity.class));

								break;
							case 1:
								myAppVariable.setTxxxid(0);
								startActivity(new Intent(MainMenuActivity.this, EatTotalPersionActivity.class));

								break;
							case 2:
								startActivity(new Intent(MainMenuActivity.this, EatAllActivity.class));

								break;
							case 3:
								startActivity(new Intent(MainMenuActivity.this, EatTotalActivity.class));

								break;

						}
					}
				});
				//				popMenu.showAsDropDown(v);
				popMenu.showPopUp(v);
			} else if (v.getId() == R.id.btn_title5) { // 弹出式菜单
//				System.out.println("下拉菜单点击");
				String[] str5 = new String[]{getString(R.string.xgmm), getString(R.string.pbsm), getString(R.string.jszc), "退出系统"};
				String str = "1";
				popMenu = new PopMenu(context, str);
				popMenu.addItems(str5);
				popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {

							case 0:
								startActivity(new Intent(MainMenuActivity.this, Activityxgmm.class));

								break;
							case 1:
								startActivity(new Intent(MainMenuActivity.this, AboutActivity.class));

								break;
							case 2:
								startActivity(new Intent(MainMenuActivity.this, Activityfwzc.class));

								break;
							case 3:
								System.exit(0);

								break;
						}

					}
				});
				//				popMenu.showAsDropDown(v);
				popMenu.showPopUp(v);
			}

		}
	};

	@Override
	public void onProg(int what, int finish) {

	}

	public void btn_app_sy() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mainLzxList.clear();//清空一遍List
					mainLzxList7.clear();//清空一遍List
					mainLzxListDydh.clear();//清空一遍List
					//  foods=foodsService.QueryAllFoods();
					users = userService.queryTodayBirthday();
					users7 = userService.queryToday7Birthday();
					dydhs = dydhService.queryTodayBirthday();
//					Thread.sleep(500);
					if (users.size() > 0) {
//						mainTextView=(TextView)findViewById(R.id.tvUserDay);
////						mainTextView.setText("日");
//						mainTextView.setText("今日过生日职工");
						for (int i = 0; i < users.size(); i++) {
							//在线程中完成数据请求
							HashMap<String, Object> map = new HashMap<String, Object>();
//							System.out.println("是否为空:"+users.get(i).getPicName());
							Pictures pic = new Pictures();
							if (users.get(i).getPicName() == null || users.get(i).getPicName().length() <= 0) {
//								System.out.println("是为空:"+users.get(i).getPicName());
								Bitmap bmp = pic.getMenuPic("icon_laucher.gif");
								map.put("foodsImage", bmp);
							} else {

//								System.out.println("是不为空:"+users.get(i).getPicName());
								Bitmap bmp = pic.getMenuPic(users.get(i).getPicName());
								map.put("foodsImage", bmp);
							}

							map.put("username", users.get(i).getUserName());
							map.put("branchid", users.get(i).getBranch().getSubname1(2));
							map.put("tel", users.get(i).getBirday());
							map.put("workerid", users.get(i).getId());
							map.put("type", "1");
							mainLzxList.add(map);
						}
					}
					if (users7.size() > 0) {


						for (int i = 0; i < users7.size(); i++) {
							//在线程中完成数据请求
							HashMap<String, Object> map7 = new HashMap<String, Object>();
							Pictures pic = new Pictures();
							if (users7.get(i).getPicName() == null || users7.get(i).getPicName().length() <= 0) {
//								System.out.println("是为空:"+users.get(i).getPicName());
								Bitmap bmp = pic.getMenuPic("icon_laucher.gif");
								map7.put("foodsImage", bmp);
							} else {

//								System.out.println("是不为空:"+users.get(i).getPicName());
								Bitmap bmp = pic.getMenuPic(users7.get(i).getPicName());
								map7.put("foodsImage", bmp);
							}

							map7.put("username", users7.get(i).getUserName());
							map7.put("branchid", users7.get(i).getBranch().getSubname1(2));
							map7.put("tel", users7.get(i).getBirday());
							map7.put("workerid", users7.get(i).getId());
							map7.put("type", "1");
							mainLzxList.add(map7);
						}
					}
					if (dydhs.size() > 0) {
//						mainTextView = (TextView) findViewById(R.id.textView2);
//						mainTextView.setText("今日是入党周年的党员");

						for (int i = 0; i < dydhs.size(); i++) {
							//在线程中完成数据请求
							HashMap<String, Object> map = new HashMap<String, Object>();
							Pictures pic = new Pictures();
							Bitmap bmp = pic.getMenuPic("icon_laucher.gif");
							map.put("foodsImage", bmp);
							map.put("idd", i + 1);
							map.put("username", dydhs.get(i).getName());
							map.put("branchid", dydhs.get(i).getZbmz().getZbmz().substring(4, 10));
							map.put("tel", dydhs.get(i).getRdsj());
							map.put("workerid", dydhs.get(i).getId());
							map.put("type", "2");
							mainLzxList.add(map);
						}
					}


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler.sendMessage(myHandler.obtainMessage());
				//		myHandler.sendMessage(myHandler.obtainMessage());
			}
		});
		t.start();

//		usersSimpleAdapter = new SimpleAdapter(MainMenuActivity.this, mainLzxList, R.layout.users_birthday_list,
//				new String[]{"foodsImage", "branchid", "username", "tel", "workerid", "type"},
//				new int[]{R.id.ivFoodsItem, R.id.tvUsersBranchid, R.id.tvUsersName, R.id.tvUsersTel, R.id.tvWorkerId, R.id.tvType});
//		mainLzxListView.setAdapter(usersSimpleAdapter);//线程执行完后显示视图
//		mainLzxListView.setOnItemClickListener(MainMenuActivity.this);
//
//		usersSimpleAdapter.setViewBinder(new CustomViewBinder());


	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();

			usersSimpleAdapter = new SimpleAdapter(MainMenuActivity.this, mainLzxList, R.layout.users_birthday_list,
					new String[]{"foodsImage", "branchid", "username", "tel", "workerid", "type"},
					new int[]{R.id.ivFoodsItem, R.id.tvUsersBranchid, R.id.tvUsersName, R.id.tvUsersTel, R.id.tvWorkerId, R.id.tvType});
			mainLzxListView.setAdapter(usersSimpleAdapter);//线程执行完后显示视图
			mainLzxListView.setOnItemClickListener(MainMenuActivity.this);

			usersSimpleAdapter.setViewBinder(new CustomViewBinder());


			super.handleMessage(msg);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		ListView listView = (ListView) parent;
		HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);

		int workerid = Integer.parseInt(String.valueOf(map.get("workerid")));

		myAppVariable.setTxxxid(workerid);
//		if ( parent.getId()== R.id.lvSeletFoodsType|| parent.getId()==R.id.lv7DayBirthday) {
		if (map.get("type").equals("1")) {
			Intent intent = new Intent();
			intent.setClass(this, UserDetailActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent();
			intent.setClass(this, DydhDetailActivity.class);
			startActivity(intent);
		}
	}

}