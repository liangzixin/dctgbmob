package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.scme.order.model.Diningcard;
import com.scme.order.model.DiningcardJson;
import com.scme.order.model.Tusers;
import com.scme.order.service.DiningcardService;
import com.scme.order.service.UserService;
import com.scme.order.view.XListView;
import com.scme.order.view.XListView.IXListViewListener;
import com.scme.order.util.MyAppVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;

//import com.scme.order.service.EatsDetailsService;

/**
 * 查看订单
 */
public class EatTopupActivity extends BaseActivity implements IXListViewListener,AdapterView.OnItemClickListener,OnItemSelectedListener{

	private XListView lvEats;
	private TextView textView;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> eatsMapList = new ArrayList<HashMap<String, Object>>();
	private List eatsTopupList;
	private DiningcardJson diningcardJson;
	private int workerid = 0;
	private int eattolnumfs = 0;
	private int eattolnumje = 0;
	private EditText etEatNum;//人数
	private ListView eatsListView;
	private TextView tvEatTolNums;
	private TextView tvEatTolprices;
	private TextView tvEatTopuptotal;
	private EatsListSimpleAdapter eatsListSimpleAdapter;
	private ActionBar actionBar;
	private int intFirst = 0;
	private int recPerPage = 20;
	private Tusers user, user0;
	private MyAppVariable myAppVariable;
	private int userid;
	private int branchid;
	private String name="";
	private String purview="0";
	private Handler testHandler;
	private int pages;
	private int start = 0;
	private static int refreshCnt = 0;
	Map<String,String> map ;
	Map<String,String> map0 ;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar = getActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eattopup_list);
		lvEats = (XListView) findViewById(R.id.lvEats);

		tvEatTolNums = (TextView) findViewById(R.id.tvEatTolNum);
		tvEatTolprices = (TextView) findViewById(R.id.tvEatToltalprice);
		tvEatTopuptotal = (TextView) findViewById(R.id.tvEattopuptotal);
		myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
		user0 = myAppVariable.getTusers();
		userid = user0.getId();
		branchid=user0.getBranchid();
		if(user0.getPurview().equals("系统")||user0.getPurview().equals("食堂")){purview="1";}
//
//		workerid=MainMenuActivity.userId1;

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		if(Thread.State.NEW == t.getState()) {
			try {
				Intent intent = getIntent();
				if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
					String query = intent.getStringExtra(SearchManager.QUERY);
					purview="2";
				//	doSearching(query);
					name=query;
				//	geneTusersItems();
					myAppVariable.setOtherquery(true);
				} else {
					myAppVariable.setOtherquery(false);
					//geneTusersItems();

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			geneTusersItems();
			t.start();

		}


	}

	private Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
			Log.d("TAG", "mThread run");
			Looper.prepare();

			testHandler = new Handler() {
				public void handleMessage(Message msg) {
					Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
//					System.out.println("我的线程："+msg.what);

					switch (msg.what) {
						//handle message here
						case 1:
							pages = (eattolnumfs  + recPerPage - 1) / recPerPage;

						//	tolpage.setText("记录数："+eattolnumfs );
					//		nowpage.setText("页码："+(intFirst+1)+"/"+pages);

							if(eattolnumfs>recPerPage*(intFirst+1)) {
								lvEats.setPullLoadEnable(true);
							}else{
								lvEats.setPullLoadEnable(false);
							}
							myHandler.sendMessage(myHandler.obtainMessage());
						//	myAdapter = new UserListActivity.MyAdapter(userList, 1);

						//	lvEats.setAdapter(myAdapter);

							lvEats.setXListViewListener(EatTopupActivity.this);
							lvEats.setOnItemClickListener(EatTopupActivity.this);


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
	});

	private void doSearching(String query) throws Exception {

		;
		//map.put("name", query);
		//map.put("intFirst",intFirst+"");
		//map.put("recPerPagee",recPerPage+"");
		DiningcardService diningcardService = new DiningcardService();
//		    	  TablesService tablesService=new TablesService();
		//	 eatsList=diningcardService.QueryAllEats();
		diningcardJson = diningcardService.QueryDiningcard(1,purview,branchid,name, intFirst, recPerPage);
		eatsTopupList = diningcardJson.getDiningcard();
		eattolnumfs = diningcardJson.getCount();
		eattolnumje = diningcardJson.getSum();
		for (int i = 0; i < eatsTopupList.size(); i++) {
			Diningcard diningcard = (Diningcard) eatsTopupList.get(i);
			//在线程中完成数据请求
			HashMap<String, Object> map=map = new HashMap<String, Object>();

			map.put("eatTotalid", eatsTopupList.size() - i);

			map.put("ym", diningcard.getTopuptime().substring(0, 10));
////
			map.put("fs", diningcard.getUser().getName());
			map.put("je", diningcard.getTopupamount());
			map.put("operator", diningcard.getOperator());
			eatsMapList.add(map);
		}

//		if(eatsTopupList!=null) {
//
//			myAppVariable.setListuser(eatsTopupList);
//
//		} else{
//			new AlertDialog.Builder(this).setTitle("没有查到请假记录！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent();
//
//					intent.setClass(UserListActivity.this, UserListActivity.class);
//					startActivity(intent);
//
//				}
//			}).show();
//		}

	}
	private void geneTusersItems() {
		map0 = new HashMap<String, String>();
       map0.put("name", name);
		//map=myAppVariable.getMap();
		map0.put("intFirst",intFirst+"");
		map0.put("recPerPage",recPerPage+"");
		map0.put("userid",user0.getId()+"");
		map0.put("branchid",user0.getBranchid()+"");
		map0.put("purview",user0.getPurview());

		try {
			DiningcardService diningcardService = new DiningcardService();
			diningcardJson = diningcardService.queryDiningcardMap(map0);
			eatsTopupList = diningcardJson.getDiningcard();
			eattolnumfs = diningcardJson.getCount();
			eattolnumje = diningcardJson.getSum();
			for (int i = 0; i < eatsTopupList.size(); i++) {
				Diningcard diningcard = (Diningcard) eatsTopupList.get(i);
				//在线程中完成数据请求
				HashMap<String, Object> map=map = new HashMap<String, Object>();

				map.put("eatTotalid",(intFirst*recPerPage)+i+1+ "");

				map.put("ym", diningcard.getTopuptime().substring(0, 10));
////
				map.put("fs", diningcard.getUser().getName());
				map.put("je", diningcard.getTopupamount());
				map.put("operator", diningcard.getOperator());
				eatsMapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
创建菜单项
 */
	@SuppressLint("RestrictedApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
		menu.getItem(2).setVisible(false);
		if(user0.getPurview().equals("系统")) {
			menu.getItem(1).setEnabled(true);
			menu.getItem(1).setTitle(R.string.add);
			menu.getItem(1).setVisible(false);
			menu.getItem(5).setVisible(true);
		}
		menu.getItem(4).setVisible(true);
		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) EatTopupActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(EatTopupActivity.this.getComponentName()));
		searchItem.setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {
					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
//                        Toast.makeText(TusersDetailActivity.this, "扩张了", Toast.LENGTH_SHORT).show();
						System.out.println("扩张了");
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
//                        Toast.makeText(TusersDetailActivity.this, "收缩了", Toast.LENGTH_SHORT).show();
						System.out.println("收缩了");
						return true;
					}
				});

		return super.onCreateOptionsMenu(menu);
//        return  true;
	}
	/**
	 * 数据加载完之后消除Loding对话框
	 */
	private Handler myHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			simpleAdapter = new SimpleAdapter(EatTopupActivity.this, eatsMapList, R.layout.eattopup_item,
					new String[]{"eatTotalid", "ym", "fs", "je","operator"},
					new int[]{R.id.tvEatsItemEatid, R.id.tvEatsItemName,
							R.id.tvEatsItemNumber, R.id.tvEatsItemPrice, R.id.tvEatsOperator});
			lvEats.setAdapter(simpleAdapter);
			super.handleMessage(msg);
		tvEatTolNums.setText("总次数：" + eattolnumfs + " 次");
		tvEatTolprices.setText("总金额: " + eattolnumje  + " 元");
		tvEatTopuptotal.setText("页码："+(intFirst+1)+"/"+pages);
	}
	};

	public String IsCheckOut(int status) {
		String str = "";
		if (status == 0)
			str = "否";
		else
			str = "是";
		return str;
	}
	/**
	 * 对象转换成字符串
	 *
	 * @param object
	 * @return
	 */
	public String toString(Object object) {
		if (object == null)
			return "";
		else
			return object.toString();
	}
	/**
	 * 对象转换成整形
	 *
	 * @param object
	 * @return
	 */
	public Integer toInteger(Object object) {
		String str = toString(object);
		if ("".equals(str))
			return 0;
		else
			return Integer.parseInt(str);
	}

	@Override
	public void onRefresh() {
		testHandler.sendEmptyMessage(2);

		start = ++refreshCnt;
		if (intFirst >= 1&&pages!=1) {
			intFirst--;
			if (myAppVariable.getOtherquery()){
				geneTusersOther();
			} else {
				geneTusersItems();
			}
			myHandler.sendMessage(myHandler.obtainMessage());
//			nowpage.setText("页码：" + (intFirst + 1) + "/" + pages);
//			myAdapter = new UserListActivity.MyAdapter(userList, 1);
//			lvEats.setAdapter(myAdapter);
			lvEats.setPullLoadEnable(true);
			onLoad();
		}else{
			lvEats.setPullLoadEnable(false);
		}

	}

	@Override
	public void onLoadMore() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		testHandler.sendEmptyMessage(2);

		if ((intFirst+1) < pages&&pages!=1) {
			intFirst++;
			if (myAppVariable.getOtherquery()){
				geneTusersOther();
			} else {
				geneTusersItems();
			}
			myHandler.sendMessage(myHandler.obtainMessage());
			lvEats.setPullLoadEnable(true);
			onLoad();
		} else {
			intFirst = pages;
			lvEats.setPullLoadEnable(false);
		}

	}
	private void onLoad() {

		lvEats.stopRefresh();
		lvEats.stopLoadMore();
		lvEats.setRefreshTime("刚刚");

	}
	/*
*根据查询条件转到另外页面
 */
	private void geneTusersOther() {
		map = new HashMap<String, String>();
//		map.put("name1", query);
		map=myAppVariable.getMap();
		map.put("intFirst",intFirst+"");
		map.put("recPerPage",recPerPage+"");
		try {
//			UserService userService = new UserService();
//			userList = userService.queryUserOther(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 吃饭列表适配器
	 */
	public class EatsListSimpleAdapter extends SimpleAdapter {

		private List<? extends Map<String, ?>> eatsFood;

		public EatsListSimpleAdapter(Context context,
									 List<? extends Map<String, ?>> data, int resource, String[] from,
									 int[] to) {
			super(context, data, resource, from, to);
			this.eatsFood = data;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			final int mPosition = position;
			convertView = super.getView(position, convertView, parent);
			//获取菜的Map对象
			HashMap<String, String> eatsFoodMap = (HashMap<String, String>) eatsFood.get(position);
//			Button buttonDel = (Button) convertView.findViewById(R.id.btnDeleteEatsFood);// id为你自定义布局中按钮的id
//			buttonDel .setTag(eatsFoodMap);
//			buttonDel.setOnClickListener(new View.OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					// TODO Auto-generated method stub
//					HashMap<String,String> ordersFoodMap=(HashMap<String, String>) v.getTag();
//					food.remove(eatsFoodMap);//根据对象标实删除List集合中的一条菜的订单
//					foodsNums--;
//					mHandler.obtainMessage(R.id.btnAddFoods, mPosition, 0) .sendToTarget();
//				}
//			});
			return convertView;
		}

	}



	/**
	 * 下拉菜单事件处理
	 */
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
		//	GetFoodListTask getFoodListTask=new GetFoodListTask();
		//	getFoodListTask.execute(arg2);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		HashMap<String, String> map = (HashMap<String, String>) eatsListView.getItemAtPosition(arg2);
//		Object OBtabId = map.get("foodsId");//取得点击列表项的Id
//		int foodsId = (Integer) OBtabId;
//		System.out.println("============foodsId" + foodsId);
//		Intent intent = new Intent();
//		Bundle bundle = new Bundle();
//		bundle.putInt("foodsId", foodsId);
//		intent.putExtras(bundle);
//		intent.setClass(this, FoodsDetailedActivity.class);
//		startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
