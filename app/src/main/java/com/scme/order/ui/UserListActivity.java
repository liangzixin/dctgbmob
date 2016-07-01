package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MaterialEditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.model.Tusers;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
import com.scme.order.service.ChuchaiService;
import com.scme.order.service.UserService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;
import com.scme.order.view.XListView;
import com.scme.order.view.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserListActivity extends BaseActivity implements IXListViewListener,AdapterView.OnItemClickListener,OnItemSelectedListener {
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	//	private ArrayList<String> items = new ArrayList<String>();
	private List<HashMap<String, Object>> userMapList = new ArrayList<HashMap<String, Object>>();
	private Handler mHandler;
	private Handler testHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private Context context =UserListActivity.this;
	private MyAdapter myAdapter;
	private ArrayAdapter<String> adapter;
	private int intFirst = 0;
	private int recPerPage = 20;
	private int count=0;
//	private Tusers user;
	private Tusers usercount;
	private double usercountday;
	private ProgressDialog progressDialog;
	private int pages;
	private EditText   textpage;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private Spinner spinner4;
	private MaterialEditText sendcontent;
	private List listbmmz;
	private List listuser;
	private int branchid;
	private Tusers user;
	private static final String[] m={"请选条件","副主任以上","负责人","科室负责人","站负责人","退管办","全局"};

	private String bmmz0="";
	private String name0="";
	private String rzjk0="";
	private Map param;
	Map<String,String> map ;

	private  boolean jump=false;
    private GetDate getDate=new GetDate();
	private List userList;
	private MyAppVariable myAppVariable;
	private TextView mTextView;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;
	private TextView userfoodid;
	int checkedcount = 0; //计数器，用于统计选中的个数
	double lg5=0.0;

	RadioGroup mRadioGroup; //RadioGroup对象，用于显示答案
	 RadioButton mRadioButton_1; //RadioButton对象，用于显示选项1
	RadioButton mRadioButton_2; //RadioButton对象，用于显示选项2
	 RadioButton mRadioButton_3; //RadioButton对象，用于显示选项3


	@InjectView(R.id.TolPage) TextView tolpage;
	@InjectView(R.id.NowPage) TextView nowpage;

//	@InjectView(R.id.user_foodid) TextView userfoodid;

//	@InjectView(R.id.textpage) TextView textpage;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);
		ButterKnife.inject(this);
		myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
		user=myAppVariable.getTusers();
		mListView = (XListView) findViewById(R.id.lvusers);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		if(Thread.State.NEW == mThread.getState()) {
			try {
				Intent intent = getIntent();
				if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
					String query = intent.getStringExtra(SearchManager.QUERY);

					doSearching(query);
					myAppVariable.setOtherquery(true);
				} else {
					myAppVariable.setOtherquery(false);
					geneTusersItems();

					count = geneTusersItemsCount();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mThread.start();

		}
	}

	private Thread mThread = new Thread() {
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
						pages = (count + recPerPage - 1) / recPerPage;

						tolpage.setText("记录数："+count);
						nowpage.setText("页码："+(intFirst+1)+"/"+pages);

						if(count>recPerPage*(intFirst+1)) {
							mListView.setPullLoadEnable(true);
						}else{
							mListView.setPullLoadEnable(false);
						}

						myAdapter = new MyAdapter(userList, 1);

						mListView.setAdapter(myAdapter);

						mListView.setXListViewListener(UserListActivity.this);
						mListView.setOnItemClickListener(UserListActivity.this);


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
		menu.getItem(2).setVisible(false);
		if(user.getPurview().equals("系统")) {
			menu.getItem(1).setEnabled(true);
			menu.getItem(1).setTitle(R.string.add);
			menu.getItem(1).setVisible(true);
			menu.getItem(5).setVisible(true);
		}
		menu.getItem(4).setVisible(true);
		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) UserListActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(UserListActivity.this.getComponentName()));

		searchItem
				.setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {

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


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
        if(id==R.id.action_txxxdetail_mainrz){
			Intent intent = new Intent();
			intent.setClass(this,UserAddlActivity.class);
			startActivity(intent);
		}else if (id == R.id.search_other) {

			LayoutInflater factory = LayoutInflater.from(UserListActivity.this);
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
			spinner2 = (Spinner) loginForm.findViewById(R.id.spinner2);
			spinner2.setDropDownWidth(-2);

			spinner2.setAdapter(adapter);
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
					adapter = new ArrayAdapter<String>(UserListActivity.this, android.R.layout.simple_spinner_item, listuser);
//
					// 设置二级下拉列表的选项内容适配器

					spinner3.setAdapter(adapter);

					setSpinnerItemSelectedByValue(spinner3, user.getName());
//					provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

			});


			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
			spinner4 = (Spinner) loginForm.findViewById(R.id.spinner4);
			spinner4.setAdapter(adapter);
			spinner4.setDropDownWidth(-2);
			//加载控件
//			 mTextView = (TextView) loginForm.findViewById(R.id.textview);
			 mCheckBox1 = (CheckBox) loginForm.findViewById(R.id.checkBox1);
			 mCheckBox2 = (CheckBox) loginForm.findViewById(R.id.checkBox2);
			 mCheckBox3 = (CheckBox) loginForm.findViewById(R.id.checkBox3);

			//选项1事件监听
			mCheckBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
					}
				});

			 //选项2事件监听
			mCheckBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

					}
				});

			//选项3事件监听
			mCheckBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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

					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
											int which) {
							map= new HashMap<String, String>();

							if (mCheckBox1.isChecked()) {

								map.put("branchname", spinner1.getSelectedItem().toString());
							}else{

								map.put("branchname", "");
							}

							if (mCheckBox2.isChecked()) {
                                  map.put("branchname",spinner2.getSelectedItem().toString());
								  map.put("name",spinner3.getSelectedItem().toString());
							}else{
								map.put("name", "");

							}

							if (mCheckBox3.isChecked()&&spinner4.getSelectedItemPosition()!=0) {
								map.put("searchnd",spinner4.getSelectedItem().toString());
							}else{
								map.put("searchnd", "");

							}



							try {
								intFirst = 0;
								UserService userService = new UserService();
								count=userService.queryUserOtherCount(map);
								userList = userService.queryUserOther(map);
							} catch (Exception e) {
								// TODO: handle exception

							}
							myAppVariable.setOtherquery(true);
							myAppVariable.setMap(map);
							pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

							tolpage.setText("记录数："+count);
								nowpage.setText("页码：" + (intFirst+1)+ "/" + pages);
								myAdapter = new MyAdapter(userList, 1);
								mListView.setAdapter(myAdapter);
								mListView.setPullLoadEnable(true);
							    mListView.setOnItemClickListener(UserListActivity.this);
								onLoad();
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
					.setNegativeButton("取消", new OnClickListener()
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


		}else if (id == R.id.search_send) {
			if(myAppVariable.getMap()==null){
				return  false;
			}else{
				LayoutInflater factory = LayoutInflater.from(UserListActivity.this);
				final View sendForm = factory.inflate(R.layout.loginsearchsend, null);
			   sendcontent=(MaterialEditText)sendForm.findViewById(R.id.Send_content);
				new AlertDialog.Builder(this)
						// 设置对话框的图标
						.setIcon(R.drawable.icon_laucher)
								// 设置对话框的标题
						.setTitle("要给这"+count+"人发短信吗??")
								// 设置对话框显示的View对象
						.setView(sendForm)
								// 为对话框设置一个“确定”按钮

						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								map = new HashMap<String, String>();
                                map=myAppVariable.getMap();
								map.put("sendcontent",sendcontent.getText().toString());



								try {

									UserService userService = new UserService();
									count = userService.queryUserSend(map);

								} catch (Exception e) {
									// TODO: handle exception

								}
                           if(count>0){
							   Toast.makeText(UserListActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
						   }else {
							   Toast.makeText(UserListActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
						   }
								// 此处可执行登录处理
							}
						})
								// 为对话框设置一个“取消”按钮
						.setNegativeButton("取消", new OnClickListener() {
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

		}



				return super.onOptionsItemSelected(item);
	}
	/*
	*转到另外页面
	 */
	private void geneTusersItems() {
		try {
			 UserService userService = new UserService();
			userList = userService.QueryAllUsers(intFirst, recPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			UserService userService = new UserService();
			userList = userService.queryUserOther(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int  geneTusersItemsCount() {
		int count1=0;
		try {
			UserService userService = new UserService();
			count1= userService.queryUserCount();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  count1;
	}

	private void onLoad() {

		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");

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
					nowpage.setText("页码：" + (intFirst + 1) + "/" + pages);
					myAdapter = new MyAdapter(userList, 1);
					mListView.setAdapter(myAdapter);
					mListView.setPullLoadEnable(true);
					onLoad();
				}else{
					mListView.setPullLoadEnable(false);
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
					nowpage.setText("页码：" + (intFirst + 1) + "/" + pages);
					myAdapter = new MyAdapter(userList, 1);
					mListView.setAdapter(myAdapter);
					mListView.setPullLoadEnable(true);
					onLoad();
				} else {
					intFirst = pages;
					mListView.setPullLoadEnable(false);
				}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		ListView listView = (ListView) parent;
//		HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);

//		int workerid = Integer.parseInt(String.valueOf(map.get("workerid")));
		int workerid=(int)id;
         System.out.println("职工记录号:"+workerid);
	     myAppVariable.setTxxxid(workerid);
			Intent intent = new Intent();
			intent.setClass(this, UserDetailActivity.class);
			startActivity(intent);

	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}


	/**
	 * 翻页适配器
	 */
	class MyAdapter extends BaseAdapter {

		public List<Tusers> users;//数据源
		public int layoutId;//样式布局文件

		public MyAdapter(List<Tusers> users, int layoutId) {//构造函数
			this.users = users;
			this.layoutId = layoutId;
		}

		public void setTusers(List<Tusers> users) {
			this.users = users;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return users.size();//返回个数
		}

		@Override
		public Object getItem(int position) {
			return users.get(position);
		}

		@Override
		public long getItemId(int position) {
			return users.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HeadlineBodyCard.ViewHolder vh;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.userlist_item, null);
				Display display =getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				int height=display.getHeight();

				Tusers user = users.get(position);
				if (position % 2 == 0) {//奇偶行背景色
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.palegreen));
				}else {
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
				}

////			ImageView ivTab = (ImageView) convertView.findViewById(R.id.imgv_tables);
				TextView id = (TextView) convertView.findViewById(R.id.user_id);
				TextView name = (TextView) convertView.findViewById(R.id.user_name);
				TextView bmmz = (TextView) convertView.findViewById(R.id.user_bmmz);
				TextView lxdh= (TextView) convertView.findViewById(R.id.user_lxdh);



				id.setGravity(Gravity.CENTER);
				name.setGravity(Gravity.CENTER);
				bmmz.setGravity(Gravity.CENTER);
				lxdh.setGravity(Gravity.CENTER);


				id.setWidth((int)(width*0.1));
				name.setWidth((int)(width*0.2));
				bmmz.setWidth((int)(width*0.3));
				lxdh.setWidth((int)(width*0.4));


				id.setHeight((int)(height*0.04));
				name.setHeight((int)(height*0.04));
				bmmz.setHeight((int)(height*0.04));
				lxdh.setHeight((int)(height*0.04));


				id.setText((intFirst*recPerPage)+position+1 + "");
//				System.out.println("部门名称："+user.getBranch().getName()+"长度："+user.getBranch().getName().length());
				bmmz.setText(user.getSubname());
				name.setText(user.getName());
				lxdh.setText(user.getTel());
//				state.setText(IsState(user.getState()));
//				longtime.setText(getDate.dateDiff(user.getTime1(),user.getTime2())+"");
//				timel.setText(user.getTime1().substring(2,4)+"/"+user.getTime1().substring(5,7)+"/"+user.getTime1().substring(8,10)+"-"+user.getTime1().substring(2,4)+"/"+user.getTime2().substring(5,7)+"/"+user.getTime2().substring(8,10));


			} else {

			}
//			userfoodid.setText("合   计:   "+usercountday+" 　天");
			return convertView;
		}
	}

	public String IsState(int status) {
		String str = "";
		if (status==0) {
			str = "否";
		}else {
			str = "是";
		}
		return str;
	}

	private void doSearching(String query) throws Exception {
		UserService userService = new UserService();
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", query);
		map.put("intFirst",intFirst+"");
		map.put("recPerPagee",recPerPage+"");

		count=userService.queryUserOtherCount(map);

		userList = userService.queryUserOther(map);

		if(userList!=null) {

			myAppVariable.setListuser(userList);

		} else{
			new AlertDialog.Builder(this).setTitle("没有查到请假记录！").setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();

					intent.setClass(UserListActivity.this, UserListActivity.class);
					startActivity(intent);

				}
			}).show();
		}

	}
	/*
	若是查询
	 */

	public void onSearch() {

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;



				nowpage.setText("页码："+(intFirst+1)+"/"+pages);
				myAdapter = new MyAdapter(userList, 1);

				mListView.setAdapter(myAdapter);
				mListView.setPullLoadEnable(true);

				mListView.setXListViewListener(UserListActivity.this);
				mListView.setOnItemClickListener(UserListActivity.this);

				onLoad();
			}
		}, 2000);
	}
	/**
	 * 提交转到页码按钮处理事件
	 * */
	public void user_pages_sumbit_Event(View view) throws Exception
	{

		LayoutInflater factory = LayoutInflater.from(UserListActivity.this);
		final View textEntryView = factory.inflate(R.layout.dialogpage, null);
		AlertDialog dlg = new AlertDialog.Builder(UserListActivity.this)

				.setTitle("页码跳转页面！！！")
				.setView(textEntryView)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						System.out.println("-------------->6");
						EditText dialognum= (EditText) textEntryView.findViewById(R.id.username_edit);
						String page0=dialognum.getText().toString();
						Toast.makeText(UserListActivity.this, "页码: " + page0, Toast.LENGTH_SHORT).show();
							try {
							//if ((heighText.getText().toString())!=null)
							intFirst=Integer.parseInt(page0)-1;
//								intFirst=10;
						} catch (Exception e) {
							// TODO: handle exception

						}
                       jump=true;
//						String inputPwd = secondPwd.getText().toString();
//						setTitle(inputPwd);
//						System.out.println("-------------->1");
//						TextView tv=(TextView)findViewById(R.id.password_view);
//						tv.setText(inputPwd);
						if (intFirst < pages) {
							if(myAppVariable.getOtherquery()){
								geneTusersOther();
							}else {
								geneTusersItems();
							}
							nowpage.setText("页码：" + (intFirst+1)+ "/" + pages);
							myAdapter = new MyAdapter(userList, 1);
							mListView.setAdapter(myAdapter);
							mListView.setPullLoadEnable(true);
							onLoad();
						} else {
							intFirst = pages;
							mListView.setPullLoadEnable(false);
						}
//输入的内容会在页面上显示来因为是做来测试，所以功能不是很全，只写了username没有学password
//						}, 2000);
					}

				})
				.setNegativeButton("取消", new OnClickListener() {
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