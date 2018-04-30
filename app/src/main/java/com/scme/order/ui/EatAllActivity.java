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
import android.os.Message;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.model.CItem;
import com.scme.order.model.Teats;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
import com.scme.order.service.EatsService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.Pingyin;
import com.scme.order.view.XListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import com.scme.order.service.EatsDetailsService;

/**
 * 查看订单
 * */
public class EatAllActivity extends BaseActivity implements XListView.IXListViewListener,OnItemClickListener, OnItemSelectedListener {

	private XListView lvEats;
	private TextView textView;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> eatsMapList = new ArrayList<HashMap<String, Object>>();
	private List<Teats> eatsList;
	private List<CItem> listuser;
	private int workerid = 0;
	private int eattolnums = 0;
//	private EditText etEatNum;//人数
	private ListView eatsListView;
	private TextView tvEatTolNums;
	private TextView tvEatTolprices;
	private EatsListSimpleAdapter eatsListSimpleAdapter;
	private ActionBar actionBar;
	private MyAppVariable myAppVariable;
	private MyAdapter myAdapter;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<CItem> adapter0;
	private Tusers user;
	private int count;
	private Map param;
	Map<String, Object> map;
	private Handler mHandler;
	private Spinner spinner1;
	private Spinner spinner2;
	private TextView mTextView;
	private Spinner spinner3;
	private Spinner spinner4;
	private List listbmmz;
	private int countfs;
	private Double countmoeny;
	private int intFrist = 0;
	private int recPerPage = 20;
	private int pages = 0;
	private TextView qingjiafoodid;
	private int start = 0;
	private static int refreshCnt = 0;
	private GetDate getDate = new GetDate();
	private boolean jump = false;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;
	private Boolean otherquery = false;
	private Map map1 = new HashMap<String, Object>();
	private static final String[] m = {"请选年度", "2014", "2015", "2016", "2017"};
	private int branchid;
	@InjectView(R.id.TolPage) TextView tolpage;
	@InjectView(R.id.NowPage) TextView nowpage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar=getActionBar();
//		actionBar=getSupportActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_eat_all);
		ButterKnife.inject(this);
		lvEats = (XListView) findViewById(R.id.lvEats);
		textView = (TextView) findViewById(R.id.tvFoodsOrderPrice);

	//	etEatNum = (EditText) findViewById(R.id.etEatNum);
		tvEatTolNums = (TextView) findViewById(R.id.tvEatTolNum);
//		tvEatTolprices=( TextView)findViewById(R.id.tvEatTolprice);

		myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
		user = myAppVariable.getTusers();
		workerid = myAppVariable.getTusers().getId();

		try {
			EatsService eatsService = new EatsService();
			map1 = eatsService.QueryAllEatsCount();
			eatsList = eatsService.QueryAllEats(intFrist, recPerPage);
			System.out.println("eatList.size()" + eatsList.size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = (int) map1.get("count");

		pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

		tolpage.setText("记录数：" + count);
		nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);

		if (count > recPerPage * (intFrist + 1)) {
			lvEats.setPullLoadEnable(true);
		} else {
			lvEats.setPullLoadEnable(false);
		}
		myAdapter = new MyAdapter(eatsList, 1);
//		View view = (LinearLayout) getLayoutInflater().inflate(R.layout.qingjia_fooder, null);
//		qingjiafoodid = (TextView) view.findViewById(R.id.qingjia_foodid);
//		qingjiafoodid.setText("份   数:  "+eatcount.getCountfs()+" 　份"+"  金　　额:  "+eatcount.getCountmoney()+" 　元");
//		lvEats.addFooterView(view);
		lvEats.setAdapter(myAdapter);

		lvEats.setXListViewListener(this);
		lvEats.setOnItemClickListener(this);
		myAdapter = new MyAdapter(eatsList, 1);
		mHandler = new Handler();

	}

	/*
    创建菜单项
     */
	@SuppressLint("RestrictedApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
		if (user.getPurview().equals("系统") || user.getPurview().equals("服务")) {
			menu.getItem(2).setVisible(false);
			menu.getItem(1).setVisible(true);
			menu.getItem(1).setEnabled(true);
			menu.getItem(2).setEnabled(false);
			menu.getItem(1).setTitle(R.string.add);
		}

		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) EatAllActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(EatAllActivity.this.getComponentName()));

		searchItem
				.setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
//                        Toast.makeText(ChuchaiDetailActivity.this, "扩张了", Toast.LENGTH_SHORT).show();
						System.out.println("扩张了");
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
//                        Toast.makeText(ChuchaiDetailActivity.this, "收缩了", Toast.LENGTH_SHORT).show();
						System.out.println("收缩了");
						return true;
					}
				});

		return super.onCreateOptionsMenu(menu);
//        return  true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_txxxdetail_mainrz) {
			Intent intent = new Intent();
			intent.setClass(this, EatsAddActivity.class);
			startActivity(intent);
		} else if (id == R.id.search_other) {

			LayoutInflater factory = LayoutInflater.from(EatAllActivity.this);
			final View loginForm = factory.inflate(R.layout.loginsearchchuchai, null);

			spinner1 = (Spinner) loginForm.findViewById(R.id.spinner1);
			spinner1.setDropDownWidth(-2);
//			//		//将可选内容与ArrayAdapter连接起来
			try {
				BranchService branchService = new BranchService();
//
				listbmmz = branchService.QueryBranch();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listbmmz);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listbmmz);
//		//设置下拉列表的风格
//			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
//		//将adapter 添加到spinner中
			spinner1.setAdapter(adapter);
			spinner2 = (Spinner) loginForm.findViewById(R.id.spinner2);
			spinner2.setDropDownWidth(-2);
//
//			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m1);
//			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter);
			spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

				// 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
					//position为当前省级选中的值的序号
					branchid = position + 1;
					try {
						UserService userService = new UserService();

					//	listuser = userService.QueryUserBranchId(branchid);
						listuser = userService.QueryUserIdName(branchid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//将地级适配器的值改变为city[position]中的值
					spinner3 = (Spinner) loginForm.findViewById(R.id.spinner3);
					adapter0 = new ArrayAdapter<CItem>(EatAllActivity.this, android.R.layout.simple_spinner_item, listuser);
//
					// 设置二级下拉列表的选项内容适配器

					spinner3.setAdapter(adapter0);

					setSpinnerItemSelectedByValue(spinner3, user.getName());
//					provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

			});


			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
			spinner4 = (Spinner) loginForm.findViewById(R.id.spinner4);
			spinner4.setAdapter(adapter);
			spinner4.setDropDownWidth(-2);
			//加载控件
//			 mTextView = (TextView) loginForm.findViewById(R.id.textview);
			mCheckBox1 = (CheckBox) loginForm.findViewById(R.id.checkBox1);
			mCheckBox2 = (CheckBox) loginForm.findViewById(R.id.checkBox2);
			mCheckBox3 = (CheckBox) loginForm.findViewById(R.id.checkBox3);

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

			//选项3事件监听
			mCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
							map = new HashMap<String, Object>();

							if (mCheckBox1.isChecked()) {

								map.put("branchname", spinner1.getSelectedItem().toString());
							} else {

								map.put("branchname", "未选");
							}

							if (mCheckBox2.isChecked()) {
								map.put("branchname", spinner2.getSelectedItem().toString());
								map.put("name", spinner3.getSelectedItem().toString());


							} else {
								if (!mCheckBox1.isChecked()) map.put("branchname", "未选");
								map.put("name", "未选");
							}

							if (mCheckBox3.isChecked()) {
								map.put("eatnd", spinner4.getSelectedItem().toString());
							} else {
								map.put("eatnd", "未选");

							}


							try {
								intFrist = 0;
								EatsService eatsService = new EatsService();
								map1 = eatsService.queryEatsOtherCount(map);
								count = (int) map1.get("count");
								countmoeny = (Double) map1.get("countmoney");
								eatsList = eatsService.queryTeatsOther(map);
							} catch (Exception e) {
								// TODO: handle exception

							}


							myAppVariable.setOtherquery(true);
							pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

							tolpage.setText("记录数：" + count);
							nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
							myAdapter = new MyAdapter(eatsList, 1);
							lvEats.setAdapter(myAdapter);
							lvEats.setPullLoadEnable(true);
							lvEats.setOnItemClickListener(EatAllActivity.this);
							onLoad();
//							} else {
//								intFrist = pages;
//								mListView.setPullLoadEnable(false);
//							}
//输入的内容会在页面上显示来因为是做来测试，所以功能不是很全，只写了username没有学password
//						}, 2000);

							// 此处可执行登录处理
						}
					})
							// 为对话框设置一个“取消”按钮
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
											int which) {
							// 取消登录，不做任何事情
						}
					})
							// 创建并显示对话框
					.create()
					.show();


		}


		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				if (intFrist >= 1 && pages != 1) {
					intFrist--;
					try {
						EatsService eatsService = new EatsService();
						eatsList = eatsService.QueryAllEats(intFrist, recPerPage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
					myAdapter = new MyAdapter(eatsList, 1);
					lvEats.setAdapter(myAdapter);
					lvEats.setPullLoadEnable(true);
					onLoad();
				} else {
					lvEats.setPullLoadEnable(false);
				}
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if ((intFrist + 1) < pages && pages != 1) {
					intFrist++;
					try {
						EatsService eatsService = new EatsService();
						eatsList = eatsService.QueryAllEats(intFrist, recPerPage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
					myAdapter = new MyAdapter(eatsList, 1);
					lvEats.setAdapter(myAdapter);
					lvEats.setPullLoadEnable(true);
					onLoad();
				} else {
					intFrist = pages;
					lvEats.setPullLoadEnable(false);
				}
			}
		}, 2000);
	}

	private void onLoad() {

		lvEats.stopRefresh();
		lvEats.stopLoadMore();
		lvEats.setRefreshTime("刚刚");

	}

	/**
	 * 翻页适配器
	 */
	class MyAdapter extends BaseAdapter {

		public List<Teats> eats;//数据源
		public int layoutId;//样式布局文件

		public MyAdapter(List<Teats> eats, int layoutId) {//构造函数
			this.eats = eats;
			this.layoutId = layoutId;
		}

		public void setChuchais(List<Teats> eats) {
			this.eats = eats;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return eats.size();//返回个数
		}

		@Override
		public Object getItem(int position) {
			return eats.get(position);
		}

		@Override
		public long getItemId(int position) {
			return eats.get(position).getEatid();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HeadlineBodyCard.ViewHolder vh;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.eats_item, null);
				Display display = getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				int height = display.getHeight();

				Teats eat = eats.get(position);
				if (position % 2 == 0) {//奇偶行背景色
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.palegreen));
				} else {
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
				}

				TextView id = (TextView) convertView.findViewById(R.id.tvEatsItemEatid);
				TextView name = (TextView) convertView.findViewById(R.id.tvEatsItemName);
				TextView eatdate = (TextView) convertView.findViewById(R.id.tvEatsItemDate);
				TextView eatnumber = (TextView) convertView.findViewById(R.id.tvEatsItemNumber);
				TextView eatprice = (TextView) convertView.findViewById(R.id.tvEatsItemPrice);


				id.setGravity(Gravity.CENTER);
				name.setGravity(Gravity.CENTER);
				eatdate.setGravity(Gravity.CENTER);
				eatnumber.setGravity(Gravity.CENTER);
				eatprice.setGravity(Gravity.CENTER);


				id.setWidth((int) (width * 0.1));
				name.setWidth((int) (width * 0.15));
				eatdate.setWidth((int) (width * 0.4));
				eatnumber.setWidth((int) (width * 0.08));
				eatprice.setWidth((int) (width * 0.08));


				id.setHeight((int) (height * 0.04));
				name.setHeight((int) (height * 0.04));
				eatdate.setHeight((int) (height * 0.04));
				eatnumber.setHeight((int) (height * 0.04));
				eatprice.setHeight((int) (height * 0.04));


				id.setText((intFrist * recPerPage) + position + 1 + "");

				name.setText(eat.getUser().getUserName());
				eatnumber.setText(eat.getEatnumber() + "");
				eatprice.setText(eat.getEatnumber() * eat.getUnitprice() + "");
				eatdate.setText(getDate.formatTime1(getDate.StrToDate(eat.getEatdate())));

			}

			return convertView;
		}
	}


	public String IsCheckOut(int status) {
		String str = "";
		if (status == 0)
			str = "否";
		else
			str = "是";
		return str;
	}

	/**
	 * 提交吃饭打卡按钮处理事件
	 */
	public void eats_insert_sumbit_Event(View view) throws Exception {

		System.out.println("workerid" + workerid);
		progressDialog2 = new ProgressDialog(this);
		progressDialog2.setMessage("订单提交中  请稍后...");
		progressDialog2.show();
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EatsService os = new EatsService();
					//EatsDetailsService ods=new EatsDetailsService();
			//		String strEatNum = etEatNum.getText().toString();
				//	Integer eatNum = Integer.parseInt(strEatNum);
			//		os.addEats(workerid, eatNum);//添加订单

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler2.sendMessage(myHandler2.obtainMessage());
				//		myHandler.sendMessage(myHandler.obtainMessage());
			}
		});
		t2.start();
		//t.join();

		new AlertDialog.Builder(this).setTitle("订单提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				//	intent.setClass(SelectEatsFoodsListActivity.this, MainActivity.class);
				//	intent.setClass(EatListActivity.this, MainActivity.class);
				intent.setClass(EatAllActivity.this, EatAllActivity.class);
				startActivity(intent);
			}
		}).show();

	}

	private Handler myHandler2 = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog2.dismiss();
			super.handleMessage(msg);
		}
	};

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
	 * 计算吃饭总份数
	 *
	 * @paramprice
	 */
	public int getEatsTolPrice() {
		int price = 0;
//		for(int i=0;i<food.size();i++)
//		{
//			price=price+(Double) food.get(i).get("foodsPrice");
//		}
		return price;
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
		int ii = new Long(arg3).intValue();
		myAppVariable.setTxxxid(ii);
//		System.out.println("就餐序号eatsId"+ii);
		Intent intent = new Intent();
		intent.setClass(this, EatDeleteDetailActivity.class);
		startActivity(intent);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 提交转到页码按钮处理事件
	 */
	public void eats_pages_sumbit_Event(View view) throws Exception {

		LayoutInflater factory = LayoutInflater.from(EatAllActivity.this);
		final View textEntryView = factory.inflate(R.layout.dialogpage, null);
		AlertDialog dlg = new AlertDialog.Builder(EatAllActivity.this)

				.setTitle("页码跳转页面！！！")
				.setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						System.out.println("-------------->6");
						EditText dialognum = (EditText) textEntryView.findViewById(R.id.username_edit);
						String page0 = dialognum.getText().toString();
						Toast.makeText(EatAllActivity.this, "页码: " + page0, Toast.LENGTH_SHORT).show();
						try {
							//if ((heighText.getText().toString())!=null)
							intFrist = Integer.parseInt(page0) - 1;
//								intFrist=10;
						} catch (Exception e) {
							// TODO: handle exception

						}
						jump = true;
//						String inputPwd = secondPwd.getText().toString();
//						setTitle(inputPwd);
//						System.out.println("-------------->1");
//						TextView tv=(TextView)findViewById(R.id.password_view);
//						tv.setText(inputPwd);
						EatsService eatsService = new EatsService();

						if (intFrist < pages) {
							if (myAppVariable.getOtherquery()) {
								try {
									eatsList = eatsService.QueryAllEats(intFrist, recPerPage);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								try {
									eatsList = eatsService.QueryAllEats(intFrist, recPerPage);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
							myAdapter = new MyAdapter(eatsList, 1);
							lvEats.setAdapter(myAdapter);
							lvEats.setPullLoadEnable(true);
							onLoad();
						} else {
							intFrist = pages;
							lvEats.setPullLoadEnable(false);
						}
//输入的内容会在页面上显示来因为是做来测试，所以功能不是很全，只写了username没有学password
//						}, 2000);
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						System.out.println("-------------->2");

					}
				})
				.create();

		dlg.show();

	}

	//显示提示信息
	public void DisplayToast(String string) {
		Toast mToast = Toast.makeText(this, string, Toast.LENGTH_LONG);
		mToast.setGravity(Gravity.TOP, 0, 450);
		mToast.show();
	}

	/**
	 * 根据值, 设置spinner默认选中:
	 *
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
		SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
		int k = apsAdapter.getCount();
		for (int i = 0; i < k; i++) {
			if (value.equals(apsAdapter.getItem(i).toString())) {
				spinner.setSelection(i, true);// 默认选中项
//                System.out.println("默认:"+i);
				break;
			}
		}
	}

	public Object ChangeName(String name) {

		Pingyin pingyin = new Pingyin();

		Object namepinyin =null;
		if (name != null) {
			namepinyin = pingyin.HanyuToPinyin(name);

		}
		return namepinyin;
	}


	public Object Changefs(int fs) {
	Object chanfs=null;
	switch (fs)  {
	case 	1:
		   chanfs="one";
		break;
	case 	2:
		   chanfs="two";
		  	break;
	}
		return chanfs;
	}

}