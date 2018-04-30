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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MaterialEditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.model.Diningcard;
import com.scme.order.model.DiningcardJson;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
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
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;

	private List listbmmz;
	private List listuser;
	private ArrayAdapter<String> adapter;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;

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


		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		if(Thread.State.NEW == t.getState()) {
			try {
				Intent intent = getIntent();
				if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
					String query = intent.getStringExtra(SearchManager.QUERY);

					name=query;

					myAppVariable.setOtherquery(true);
				} else {
					myAppVariable.setOtherquery(false);

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



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
	if (id == R.id.search_other) {
			LayoutInflater factory = LayoutInflater.from(EatTopupActivity.this);
			final View loginForm = factory.inflate(R.layout.loginsearchuser, null);
			spinner1 = (Spinner) loginForm.findViewById(R.id.spinner1);
			spinner1.setDropDownWidth(-2);
//			//		//将可选内容与ArrayAdapter连接起来
			try {
				BranchService branchService=new BranchService();
//
				listbmmz=branchService.QueryBranch();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listbmmz);

//		//设置下拉列表的风格

//		//将adapter 添加到spinner中
			spinner1.setAdapter(adapter);
			spinner1.setSelection(user0.getBranch().getId()-1);

			spinner2 = (Spinner) loginForm.findViewById(R.id.spinner2);
			spinner2.setDropDownWidth(-2);

			spinner2.setAdapter(adapter);
		spinner2.setSelection(user0.getBranch().getId()-1);
			spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

				// 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
					//position为当前省级选中的值的序号
					branchid = position + 1;
					try {
						UserService userService = new UserService();

						listuser = userService.QueryUserBranchId(branchid);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//将地级适配器的值改变为city[position]中的值
					spinner3 = (Spinner) loginForm.findViewById(R.id.spinner3);
					adapter = new ArrayAdapter<String>(EatTopupActivity.this, android.R.layout.simple_spinner_item, listuser);
//
					// 设置二级下拉列表的选项内容适配器

					spinner3.setAdapter(adapter);

					setSpinnerItemSelectedByValue(spinner3, user0.getName());
//					provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

			});



//			 mTextView = (TextView) loginForm.findViewById(R.id.textview);
			mCheckBox1 = (CheckBox) loginForm.findViewById(R.id.checkBox1);
			mCheckBox2 = (CheckBox) loginForm.findViewById(R.id.checkBox2);
		    mCheckBox3 = (CheckBox) loginForm.findViewById(R.id.checkBox3);
		  mCheckBox3.setVisibility(View.GONE);
		  if(user0.getPurview().equals("系统")||user0.getPurview().equals("食堂")){
		     spinner1.setEnabled(true);
			  spinner2.setEnabled(true);
		  }else{
			  spinner1.setEnabled(false);
			  spinner2.setEnabled(false);
		  }


			//选项1事件监听
			mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
				}
			});

			//选项2事件监听
			mCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				}
			});


			new AlertDialog.Builder(this)
					// 设置对话框的图标
					.setIcon(R.drawable.icon_laucher)
					// 设置对话框的标题
					.setTitle("其它查询条件")
					// 设置对话框显示的View对象
					.setView(loginForm)
					// 为对话框设置一个“确定”按钮

					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
											int which) {
							map= new HashMap<String, String>();
							map.put("other","1");
							if (mCheckBox1.isChecked()) {
								map.put("mCheckBox1","1");
								map.put("branchname", spinner1.getSelectedItem().toString());
							}else{
								map.put("mCheckBox1","0");
								map.put("branchname", "");
							}

							if (mCheckBox2.isChecked()) {
								map.put("mCheckBox2","1");
								map.put("branchname",spinner2.getSelectedItem().toString());
								map.put("name",spinner3.getSelectedItem().toString());
							}else{
								map.put("name", "");
								map.put("mCheckBox2","0");
							}


							try {
								intFirst = 0;
								DiningcardService diningcardService = new DiningcardService();
								diningcardJson = diningcardService.queryDiningcardMap(map);
								eatsTopupList = diningcardJson.getDiningcard();
								eattolnumfs = diningcardJson.getCount();
								eattolnumje = diningcardJson.getSum();
								eatsMapList = new ArrayList<HashMap<String, Object>>();
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
							myAppVariable.setOtherquery(true);
							myAppVariable.setMap(map);

							lvEats.setPullLoadEnable(true);
							lvEats.setOnItemClickListener(EatTopupActivity.this);

							//	onLoad();
							testHandler.sendEmptyMessage(1);
							Looper.loop();
							//	t.start();
//							} else {
//								intFirst = pages;
//								mListView.setPullLoadEnable(false);
//							}
//输入的内容会在页面上显示来因为是做来测试，所以功能不是很全，只写了username没有学password
//						}, 2000);

							// 此处可执行登录处理
						}
					})
					// 为对话框设置一个“取消”按钮
					.setNegativeButton("取消", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog,
											int which)
						{
							// 取消登录，不做任何事情
						}
					})
					// 创建并显示对话框
					.create()
					.show();


		}



		return super.onOptionsItemSelected(item);
	}
	private void geneTusersItems() {
		map0 = new HashMap<String, String>();
       map0.put("name", name);
		map0.put("other","0");
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
		menu.getItem(3).setVisible(false);
		menu.getItem(4).setVisible(true);
		if(user0.getPurview().equals("系统")) {
			menu.getItem(1).setEnabled(true);
			menu.getItem(1).setTitle(R.string.add);
			menu.getItem(1).setVisible(false);
			menu.getItem(5).setVisible(false);
		}

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
	/**
	 * 根据值, 设置spinner默认选中:
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
		SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
		int k= apsAdapter.getCount();
		for(int i=0;i<k;i++){
			if(value.equals(apsAdapter.getItem(i).toString())){
				spinner.setSelection(i,true);// 默认选中项
//                System.out.println("默认:"+i);
				break;
			}
		}
	}
}
