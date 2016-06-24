package com.scme.order.ui;

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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.model.Txxx;
import com.scme.order.service.EatsService;
import com.scme.order.service.OrdersService;
import com.scme.order.service.TxxxService;
import com.scme.order.util.MyAppVariable;
import com.scme.order.view.XListView;
import com.scme.order.view.XListView.IXListViewListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TxxxListFyActivity extends BaseActivity implements IXListViewListener,AdapterView.OnItemClickListener,OnItemSelectedListener {
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	//	private ArrayList<String> items = new ArrayList<String>();
	private List<HashMap<String, Object>> txxxMapList = new ArrayList<HashMap<String, Object>>();
	private Handler mHandler;
	private Handler testHandler;
	private int start = 0;
	private static int refreshCnt = 0;

	private MyAdapter myAdapter;
	private ArrayAdapter<String> adapter;
	private int intFrist = 0;
	private int recPerPage = 20;
	private int count=0;
	private Txxx txxx;
	private ProgressDialog progressDialog;
	private int pages;
	private EditText   textpage;
	private Spinner spinner1;
	private Spinner spinner2;
	private static final String[] m={"上大院管理服务站","下大院管理服务站","桂苑街管理服务站","落雪大院管理服务站","腊利大院管理服务站"};
	private static final String[] m1={"姓名","个人编号","身份证号","经办人","认证地点","认证日期","居住地","联系电话1","联系电话2"};
	private static final String[] m2={"name","grbh","sfzh","rz13zb","rz13dd","rzrj","czdz","lxdh1","lzdh2"};
	private String bmmz0="";
	private String name0="";
	private String rzjk0="";
	private Map param;
	Map<String, Object> map ;
	private  Boolean otherquery=false;
	private  boolean jump=false;

	private List txxxList;
	private MyAppVariable myAppVariable;
	private TextView mTextView;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;
	int checkedcount = 0; //计数器，用于统计选中的个数

	RadioGroup mRadioGroup; //RadioGroup对象，用于显示答案
	 RadioButton mRadioButton_1; //RadioButton对象，用于显示选项1
	RadioButton mRadioButton_2; //RadioButton对象，用于显示选项2
	 RadioButton mRadioButton_3; //RadioButton对象，用于显示选项3


	@InjectView(R.id.TolPage) TextView tolpage;
	@InjectView(R.id.NowPage) TextView nowpage;

//	@InjectView(R.id.textpage) TextView textpage;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_txxx_list);
		ButterKnife.inject(this);
		myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
		mListView = (XListView) findViewById(R.id.lvtxxxs);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		if(Thread.State.NEW == mThread.getState()) {
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			doSearching(query);



		}else{
			geneTxxxItems();

			count = geneTxxxItemsCount();
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
							pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

							tolpage.setText("记录数："+count);
							nowpage.setText("页码："+(intFrist+1)+"/"+pages);

							if(count>recPerPage) {
								mListView.setPullLoadEnable(true);
							}else{
								mListView.setPullLoadEnable(false);
							}

							myAdapter = new MyAdapter(txxxList, 1);

							mListView.setAdapter(myAdapter);

							mListView.setXListViewListener(TxxxListFyActivity.this);
							mListView.setOnItemClickListener(TxxxListFyActivity.this);
						case 2:

							//send message here

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

		menu.getItem(1).setVisible(false);
		menu.getItem(3).setVisible(false);
		menu.getItem(4).setVisible(true);
		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) TxxxListFyActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(TxxxListFyActivity.this.getComponentName()));

		searchItem
				.setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
//                        Toast.makeText(TxxxDetailActivity.this, "扩张了", Toast.LENGTH_SHORT).show();
//                        System.out.println("扩张了");
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
//                        Toast.makeText(TxxxDetailActivity.this, "收缩了", Toast.LENGTH_SHORT).show();
//                        System.out.println("收缩了");
						return true;
					}
				});

		return super.onCreateOptionsMenu(menu);
//        return  true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.search_other) {


			LayoutInflater factory = LayoutInflater.from(TxxxListFyActivity.this);
			final View loginForm = factory.inflate(R.layout.loginsearchother, null);
//			TableLayout loginForm = (TableLayout)getLayoutInflater(getActivity())
//					.inflate( R.layout.loginsearchother, null);
			spinner1 = (Spinner) loginForm.findViewById(R.id.spinner1);
             spinner1.setDropDownWidth(-2);
//			//		//将可选内容与ArrayAdapter连接起来
			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m);
////        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m1);
//		//设置下拉列表的风格
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
//		//将adapter 添加到spinner中
		spinner1.setAdapter(adapter);
			spinner2 = (Spinner) loginForm.findViewById(R.id.spinner2);
			spinner2.setDropDownWidth(-2);

			adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m1);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter);
			//加载控件
			 mTextView = (TextView) loginForm.findViewById(R.id.textview);
			 mCheckBox1 = (CheckBox) loginForm.findViewById(R.id.checkBox1);
			 mCheckBox2 = (CheckBox) loginForm.findViewById(R.id.checkBox2);
			 mCheckBox3 = (CheckBox) loginForm.findViewById(R.id.checkBox3);

			 mRadioGroup = (RadioGroup) loginForm.findViewById(R.id.radioGroup);
			 mRadioButton_1 = (RadioButton) loginForm.findViewById(R.id.radioButton1);
			mRadioButton_2 = (RadioButton) loginForm.findViewById(R.id.radioButton2);
			mRadioButton_3 = (RadioButton) loginForm.findViewById(R.id.radioButton3);

			//选项1事件监听
			mCheckBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
//					if (mCheckBox3.isChecked()) {
//						 checkedcount++;
//						DisplayToast("你选择了认证：" + mCheckBox3.getText());
//						 } else {
//						 checkedcount--;
//						 }
					 }
				 });

			//设置事件监听
			 mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				 public void onCheckedChanged(RadioGroup group, int checkedId) {

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
							map= new HashMap<String, Object>();

							if (mCheckBox1.isChecked()) {

								map.put("branchname", spinner1.getSelectedItem().toString());
							}else{

								map.put("branchname", "");
							}

							if (mCheckBox2.isChecked()) {
                                  map.put("name0",m2[spinner2.getSelectedItemPosition()].toString());
								  map.put("name1",mTextView.getText().toString());



							}else{
								map.put("name0", "");
								map.put("name", "");
							}

							if (mCheckBox3.isChecked()) {
								int spin1 = mRadioGroup.getCheckedRadioButtonId();
//								RadioButton radioButton = （RadioButton）findViewById（mradioGroup.getCheckedRadioButtonId()）;
//
//								System.out.println("-------------->"+text);
								switch (spin1) {
									case R.id.radioButton1:
										map.put("rzjk0", "已认证");
										break;
									case R.id.radioButton2:
										map.put("rzjk0", "未认证");
										break;
									case R.id.radioButton3:
										map.put("swsj", "死亡");
										break;
								}
							}else{
								map.put("rzjk0", "");
								map.put("swsj", "");
							}


							try {

								TxxxService txxxService = new TxxxService();
								count= txxxService.queryTxxxOtherCount(map);
								txxxList = txxxService.queryTxxxOther(map);
							} catch (Exception e) {
								// TODO: handle exception

							}

							otherquery=true;
							pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

							tolpage.setText("记录数："+count);
								nowpage.setText("页码：" + (intFrist+1)+ "/" + pages);
								myAdapter = new MyAdapter(txxxList, 1);
								mListView.setAdapter(myAdapter);
								mListView.setPullLoadEnable(true);
								onLoad();

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


		}



		return super.onOptionsItemSelected(item);
	}
	/*
	*转到另外页面
	 */
	private void geneTxxxItems() {
		try {
			 TxxxService txxxService = new TxxxService();
			txxxList = txxxService.QueryAllTxxx(intFrist, recPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
*根据查询条件转到另外页面
 */
	private void geneTxxxOther() {
		map.put("inFirst",intFrist);
		map.put("pages",pages);
		try {
			TxxxService txxxService = new TxxxService();
			txxxList = txxxService.queryTxxxOther(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int  geneTxxxItemsCount() {

		try {
			TxxxService txxxService= new TxxxService();
			count= txxxService.queryTxxxCount();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
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
				if (intFrist >= 1) {
					intFrist--;
					geneTxxxItems();
					nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
					myAdapter = new MyAdapter(txxxList, 1);
					mListView.setAdapter(myAdapter);
					mListView.setPullLoadEnable(true);
					onLoad();
				}else{
					mListView.setPullLoadEnable(false);
				}

	}

	@Override
	public void onLoadMore() {
		testHandler.sendEmptyMessage(2);
				if (intFrist < pages) {
					intFrist++;
                   if(otherquery){
					   geneTxxxOther();
				   }else {
					   geneTxxxItems();
				   }
					nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
					myAdapter = new MyAdapter(txxxList, 1);
					mListView.setAdapter(myAdapter);
					mListView.setPullLoadEnable(true);
					onLoad();
				} else {
					intFrist = pages;
					mListView.setPullLoadEnable(false);
				}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		int ii= new Long(id).intValue();
		myAppVariable.setTxxxid(ii);

		Intent intent = new Intent();

		intent.setClass(this, TxxxDetailActivity.class);

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

		public List<Txxx> txxxs;//数据源
		public int layoutId;//样式布局文件

		public MyAdapter(List<Txxx> txxxs, int layoutId) {//构造函数
			this.txxxs = txxxs;
			this.layoutId = layoutId;
		}

		public void setTxxxs(List<Txxx> txxxs) {
			this.txxxs = txxxs;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return txxxs.size();//返回个数
		}

		@Override
		public Object getItem(int position) {
			return txxxs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return txxxs.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HeadlineBodyCard.ViewHolder vh;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.txxxlist_item, null);
				Display display =getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				int height=display.getHeight();
				Txxx txxx = txxxs.get(position);
				if (position % 2 == 0) {//奇偶行背景色
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.palegreen));
				}else {
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
				}
////			ImageView ivTab = (ImageView) convertView.findViewById(R.id.imgv_tables);
				TextView id = (TextView) convertView.findViewById(R.id.id);
				TextView name = (TextView) convertView.findViewById(R.id.name);
				TextView bmmz = (TextView) convertView.findViewById(R.id.bmmz);
				TextView sfzh = (TextView) convertView.findViewById(R.id.sfzh);
				TextView rz = (TextView) convertView.findViewById(R.id.rz);

				id.setGravity(Gravity.CENTER);
				name.setGravity(Gravity.CENTER);
				bmmz.setGravity(Gravity.CENTER);
				sfzh.setGravity(Gravity.CENTER);
				rz.setGravity(Gravity.CENTER);

				id.setWidth((int)(width*0.12));
				name.setWidth((int)(width*0.15));
				bmmz.setWidth((int)(width*0.2));
				sfzh.setWidth((int)(width*0.43));
				rz.setWidth((int)(width*0.1));
//				state.setWidth((int) (width * 0.1));

				id.setHeight((int)(height*0.04));
				name.setHeight((int)(height*0.04));
				bmmz.setHeight((int)(height*0.04));
				sfzh.setHeight((int)(height*0.04));
				rz.setHeight((int)(height*0.04));
//				state.setHeight((int) (height * 0.04));



				id.setText((intFrist*recPerPage)+position+1 + "");
				bmmz.setText(txxx.getSubname1(4));
//				grbh.setText(txxx.getGrbh());
				name.setText(txxx.getName());
				sfzh.setText(txxx.getSfzh());
				rz.setText(IsCheckOut(txxx.getRz13jk()));

			} else {

			}

			return convertView;
		}
	}

	public String IsCheckOut(String status) {
		String str = "";
		if (status.equals("")) {
			str = "否";
		}else {
			str = "是";
		}
		return str;
	}

	private void doSearching(String query) {
		TxxxService txxxService = new TxxxService();
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", query);
		map.put("intFirst",intFrist+"");
		map.put("recPerPage",recPerPage+"");

		try {
			count=txxxService.queryTxxxOtherCount(map);
			txxxList = txxxService.queryTxxxName(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(txxxList!=null) {

			myAppVariable.setTxxxs(txxxList);


		} else{
			new AlertDialog.Builder(this).setTitle("查无此人！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();

					intent.setClass(TxxxListFyActivity.this, TxxxListFyActivity.class);
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


				nowpage.setText("页码："+(intFrist+1)+"/"+pages);
				myAdapter = new MyAdapter(txxxList, 1);
				mListView.setAdapter(myAdapter);
				mListView.setPullLoadEnable(true);

				mListView.setXListViewListener(TxxxListFyActivity.this);
				mListView.setOnItemClickListener(TxxxListFyActivity.this);

				onLoad();
			}
		}, 2000);
	}
	/**
	 * 提交转到页码按钮处理事件
	 * */
	public void txxx_pages_sumbit_Event(View view) throws Exception
	{

//		System.out.println("workerid"+workerid);
//		progressDialog2 = new ProgressDialog(this);
//		progressDialog2.setMessage("订单提交中  请稍后...");
//		progressDialog2.show();
//		Thread t2=new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					EatsService os=new EatsService();
//					//EatsDetailsService ods=new EatsDetailsService();
//					String strEatNum=etEatNum.getText().toString();
//					Integer eatNum=Integer.parseInt(strEatNum);
//					os.addEats(workerid, eatNum);//添加订单
//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				myHandler2.sendMessage(myHandler2.obtainMessage());
//				//		myHandler.sendMessage(myHandler.obtainMessage());
//			}
//		});
//		t2.start();
		//t.join();

//		new AlertDialog.Builder(this).setTitle("转到页码！").setView(textpage).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				mHandler.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						if (intFrist < pages) {
//
//							intFrist=this.getEditText();
//							geneTxxxItems();
//							nowpage.setText("页码：" + intFrist+ "/" + pages);
//							myAdapter = new MyAdapter(txxxList, 1);
//							mListView.setAdapter(myAdapter);
//							mListView.setPullLoadEnable(true);
//							onLoad();
//						} else {
//							intFrist = pages;
//							mListView.setPullLoadEnable(false);
//						}
//					}
//				}, 2000);
//			}
//		}).show();
		LayoutInflater factory = LayoutInflater.from(TxxxListFyActivity.this);
		final View textEntryView = factory.inflate(R.layout.dialogpage, null);
		AlertDialog dlg = new AlertDialog.Builder(TxxxListFyActivity.this)

				.setTitle("页码跳转页面！！！")
				.setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						System.out.println("-------------->6");
						EditText dialognum= (EditText) textEntryView.findViewById(R.id.username_edit);
						String page0=dialognum.getText().toString();
						Toast.makeText(TxxxListFyActivity.this, "页码: " + page0, Toast.LENGTH_SHORT).show();
							try {
							//if ((heighText.getText().toString())!=null)
							intFrist=Integer.parseInt(page0)-1;
//								intFrist=10;
						} catch (Exception e) {
							// TODO: handle exception

						}
                       jump=true;
//						String inputPwd = secondPwd.getText().toString();
//						setTitle(inputPwd);
//						System.out.println("-------------->1");
//						TextView tv=(TextView)findViewById(R.id.password_view);
//						tv.setText(inputPwd);
						if (intFrist < pages) {
							if(otherquery){
								geneTxxxOther();
							}else {
								geneTxxxItems();
							}
							nowpage.setText("页码：" + (intFrist+1)+ "/" + pages);
							myAdapter = new MyAdapter(txxxList, 1);
							mListView.setAdapter(myAdapter);
							mListView.setPullLoadEnable(true);
							onLoad();
						} else {
							intFrist = pages;
							mListView.setPullLoadEnable(false);
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
}