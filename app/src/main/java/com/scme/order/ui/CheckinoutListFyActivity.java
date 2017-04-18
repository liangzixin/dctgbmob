package com.scme.order.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.model.Checkinout;
import com.scme.order.model.Tusers;
import com.scme.order.service.BaseService;
import com.scme.order.service.BranchService;
import com.scme.order.service.TxxxService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;
import com.scme.order.view.XListView;
import com.scme.order.view.XListView.IXListViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CheckinoutListFyActivity extends BaseActivity implements IXListViewListener,AdapterView.OnItemClickListener,OnItemSelectedListener {
	private XListView mListView;
//	private ArrayAdapter<String> mAdapter;
	//	private ArrayList<String> items = new ArrayList<String>();
	private List<HashMap<String, Object>> txxxMapList = new ArrayList<HashMap<String, Object>>();
	private Handler mHandler;
	private Handler testHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private List listbmmz;
	private List listbmmz0;
	private List listuser;
	private int branchid;
	private Tusers user;
	private static final String[] m = {"2017", "2016", "2015"};
	private MyAdapter myAdapter;
	private ArrayAdapter<String> adapter;
	private int intFirst = 0;
	private int recPerPage = 20;
	private int count=0;
	private Checkinout checkinout;
	private ProgressDialog progressDialog;
	private int pages;
	private EditText   textpage;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private Spinner spinner4;
	private Spinner spinner5;
	//private static final String[] m={"上大院管理服务站","下大院管理服务站","桂苑街管理服务站","落雪大院管理服务站","腊利大院管理服务站"};
	//private static final String[] m1={"姓名","个人编号","身份证号","经办人","认证地点","认证日期","居住地","联系电话1","联系电话2"};
	//private static final String[] m2={"name","grbh","sfzh","rz14zb","rz14dd","rzrj","czdz","lxdh1","lzdh2"};
	private static final String[] m3={"第一周","第二周","第三周","第四周","第五周","第六周","第七周","第八周","第九周","第十周","第十一周","第十二周","第十三周","第十四周","第十五周","第十六周","第十七周","第十八周","第十九周"};
//	private String bmmz0="";
//	private String name0="";
//	private String rzjk0="";
//	private int userid=0;
//	private Map param;
	Map<String, Object> map ;
	private  Boolean otherquery=false;
	private  boolean jump=false;

	private List checkinoutList;
	private MyAppVariable myAppVariable;
	private TextView mTextView;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;
	private CheckBox mCheckBox4;
	int checkedcount = 0; //计数器，用于统计选中的个数
	private HttpHandler<String> handler;
//	private HttpUtils httpUtils= new HttpUtils();
	private    String url=null;
private 	RequestParams params;
	private GetDate getDate=new GetDate();


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
		setContentView(R.layout.activity_checkinout_list);
		ButterKnife.inject(this);
		myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        otherquery=myAppVariable.getOtherquery();
		user = myAppVariable.getTusers();
		mListView = (XListView) findViewById(R.id.lvcheckinouts);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();

//		if(Thread.State.NEW == mThread.getState()) {
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			myAppVariable.setOtherquery(true);
//			if (Intent.ACTION_SEARCH.equals(intent.getAction())||) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			myAppVariable.setQuery(query);

			doSearching(query);
		}else if(otherquery){

			myAppVariable.setOtherquery(true);
			String query =myAppVariable.getQuery();

					doSearching(query);
		}else{
			myAppVariable.setOtherquery(false);
			geneCheckinoutItems();
		}

//		}

	}
	private void mThreadmy() {
//	 HttpHandler<String> handler;
	 HttpUtils httpUtils= new HttpUtils();
		// 不缓存，设置缓存0秒。
		httpUtils.configCurrentHttpCacheExpiry(0*1000);
		handler= httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {

					if (responseInfo.result != null) {
						progressDialog.dismiss();
						JSONObject myobject =null;
						String listArray=null;
						try {

							myobject = new JSONObject(responseInfo.result);
							count=myobject.getInt("count");
							listArray=myobject.getString("checkinoutlist");
							checkinoutList= BaseService.getGson().fromJson(listArray, new TypeToken<List<Checkinout>>() {}.getType());
						 System.out.println(checkinoutList.size());
						} catch (JSONException e) {
							e.printStackTrace();
						}


						pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

						tolpage.setText("记录数："+count);
						nowpage.setText("页码："+(intFirst+1)+"/"+pages);

						if(count>recPerPage) {
							mListView.setPullLoadEnable(true);
						}else{
							mListView.setPullLoadEnable(false);
						}

						myAdapter = new MyAdapter(checkinoutList, 1);

						mListView.setAdapter(myAdapter);

						mListView.setXListViewListener(CheckinoutListFyActivity.this);
						mListView.setOnItemClickListener(CheckinoutListFyActivity.this);

					}
				}

				@Override
				public void onFailure(HttpException e, String s) {
					progressDialog.dismiss();
					Toast.makeText(CheckinoutListFyActivity.this, "数据加载失败！！！", Toast.LENGTH_SHORT).show();
				}
			});

		}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
		if(user.getPurview().equals("系统")) {
			menu.getItem(1).setEnabled(true);
			menu.getItem(1).setVisible(true);
			menu.getItem(1).setTitle(R.string.add);
		}else {
			menu.getItem(1).setVisible(false);
		}
		menu.getItem(3).setVisible(false);
		menu.getItem(4).setVisible(true);
		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) CheckinoutListFyActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(CheckinoutListFyActivity.this.getComponentName()));

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
		if (id == R.id.action_txxxdetail_mainrz) {
			Intent intent = new Intent();
			intent.setClass(this, CheckinoutAddActivity.class);
			startActivityForResult(intent, 1);
		}else if(id == R.id.search_other) {
			LayoutInflater factory = LayoutInflater.from(CheckinoutListFyActivity.this);
			final View loginForm = factory.inflate(R.layout.loginsearchcheckinout, null);

			spinner1 = (Spinner) loginForm.findViewById(R.id.spinner1);
			spinner1.setDropDownWidth(-2);
//			//		//将可选内容与ArrayAdapter连接起来
			try {
				BranchService branchService = new BranchService();
//
				listbmmz0 = branchService.QueryBranch();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listbmmz= new ArrayList<String>() ;
					if(listbmmz0.size()>0) {
			for(int i=0;i<listbmmz0.size();i++){
				int ll=listbmmz0.get(i).toString().length();
				String ss=listbmmz0.get(i).toString();
				if(ll>5) {
				listbmmz.add(ss.substring(0,5));
					}else{
					listbmmz.add(ss);
					}
			}
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
			spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
					adapter = new ArrayAdapter<String>(CheckinoutListFyActivity.this, android.R.layout.simple_spinner_item, listuser);
//
					// 设置二级下拉列表的选项内容适配器

					spinner3.setAdapter(adapter);

				//	setSpinnerItemSelectedByValue(spinner3, user.getName());
				//	provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

			});


			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
			spinner4 = (Spinner) loginForm.findViewById(R.id.spinner4);
			spinner4.setAdapter(adapter);
			spinner4.setDropDownWidth(-2);

			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m3);
			spinner5 = (Spinner) loginForm.findViewById(R.id.spinner5);
			spinner5.setAdapter(adapter);
			spinner5.setDropDownWidth(-2);
			//加载控件
//			 mTextView = (TextView) loginForm.findViewById(R.id.textview);
			mCheckBox1 = (CheckBox) loginForm.findViewById(R.id.checkBox1);
			mCheckBox2 = (CheckBox) loginForm.findViewById(R.id.checkBox2);
			mCheckBox3 = (CheckBox) loginForm.findViewById(R.id.checkBox3);
			mCheckBox4 = (CheckBox) loginForm.findViewById(R.id.checkBox4);
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
			//选项3事件监听
			mCheckBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
					//		map = new HashMap<String, String>();
                     doSearchingOther();


//
//							try {
//								intFirst = 0;
//								map.put("intFirst", intFirst + "");
//								QingjiaService qingjiaService = new QingjiaService();
//								Map map1 = qingjiaService.queryOtherCount(map);
//								count = (int) map1.get("count");
//								qingjiacountday = (double) map1.get("countday");
////								qingjiacountday=qingjiacount.getCountday();
//								qingjiaList = qingjiaService.queryQingjiaOther(map);
//							} catch (Exception e) {
//								// TODO: handle exception
//
//							}
//
//							myAppVariable.setOtherquery(true);
//							myAppVariable.setMap(map);
//							pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数
//
//							tolpage.setText("记录数：" + count);
//							nowpage.setText("页码：" + (intFirst + 1) + "/" + pages);
//							myAdapter = new MyAdapter(qingjiaList, 1);
//							mListView.setAdapter(myAdapter);
//							mListView.setPullLoadEnable(true);
//							mListView.setOnItemClickListener(QingjiaListActivity.this);
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



		return super.onOptionsItemSelected(item);
	}
	/*
	*转到另外页面
	 */
	private void geneCheckinoutItems() {
		url= HttpUtil.BASE_URL+"checkinout!queryCheckinoutAll.action";
	    params= new RequestParams();
		params.addQueryStringParameter("purview",user.getPurview());
		params.addQueryStringParameter("name","");
		params.addQueryStringParameter("queryname","0");
	//	System.out.println(myAppVariable.getTusers().getPurview());
		params.addQueryStringParameter("deptid",user.getDeptid()+"");
		params.addQueryStringParameter("intFirst",intFirst+"");
		params.addQueryStringParameter("recPerPage",recPerPage+"");
		mThreadmy();


	}
	/*
*根据查询条件转到另外页面
 */
	private void geneCheckinoutOther() {
		map.put("inFirst",intFirst);
		map.put("pages",pages);
		try {
			TxxxService txxxService = new TxxxService();
			checkinoutList = txxxService.queryTxxxOther(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");

	}

	@Override
	public void onRefresh() {
		params= new RequestParams();
		if (intFirst >= 1) {
			intFirst--;
			params.addQueryStringParameter("intFirst",intFirst+"");
			params.addQueryStringParameter("recPerPage",recPerPage+"");
			mThreadmy();
			onLoad();
		} else {
//			intFirst = pages;
			mListView.setPullLoadEnable(false);
		}


	}

	@Override
	public void onLoadMore() {


		 params= new RequestParams();
		if (intFirst < pages) {
			intFirst++;
			params.addQueryStringParameter("intFirst",intFirst+"");
			params.addQueryStringParameter("recPerPage",recPerPage+"");
			mThreadmy();
		} else {
		intFirst = pages;
		mListView.setPullLoadEnable(false);
	}



	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		int ii= new Long(id).intValue();
//		myAppVariable.setCheckinout(new Checkinout());
		Checkinout checkinout=(Checkinout)checkinoutList.get(position-1);
//		myAppVariable.setTxxxid(ii);
//        Checkinout checkinout=;
//		String bmmc="aaa";
//		String name="aaa";
//		String checktime="aaa";
		Intent intent = new Intent();
		intent.putExtra("bmmc",checkinout.getUserinfo().getDepartments().getDeptname());//也可以绑定数组
		intent.putExtra("name",checkinout.getUserinfo().getName());
		intent.putExtra("checktime",checkinout.getCheckTime());
		intent.putExtra("userid",checkinout.getUSERID()+"");
		intent.setClass(this, CheckinoutDetailActivity.class);
		startActivityForResult(intent,1);
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

		public List<Checkinout> checkinouts;//数据源
		public int layoutId;//样式布局文件

		public MyAdapter(List<Checkinout> checkinouts, int layoutId) {//构造函数
			this.checkinouts = checkinouts;
			this.layoutId = layoutId;
		}

		public void setCheckinouts(List<Checkinout> checkinouts) {
			this.checkinouts = checkinouts;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return checkinouts.size();//返回个数
		}

		@Override
		public Object getItem(int position) {
			return checkinouts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return checkinouts.get(position).getUSERID();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HeadlineBodyCard.ViewHolder vh;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.checkinoutlist_item, null);
				Display display =getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				int height=display.getHeight();
				Checkinout checkinout = checkinouts.get(position);
				if (position % 2 == 0) {//奇偶行背景色
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.palegreen));
				}else {
					convertView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
				}
////			ImageView ivTab = (ImageView) convertView.findViewById(R.id.imgv_tables);
				TextView id = (TextView) convertView.findViewById(R.id.id);
				TextView name = (TextView) convertView.findViewById(R.id.name);
				TextView bmmz = (TextView) convertView.findViewById(R.id.bmmz);
				TextView checktime= (TextView) convertView.findViewById(R.id.checktime);
				TextView rz = (TextView) convertView.findViewById(R.id.rz);

				id.setGravity(Gravity.CENTER);
				name.setGravity(Gravity.CENTER);
				bmmz.setGravity(Gravity.CENTER);
				checktime.setGravity(Gravity.CENTER);
				rz.setGravity(Gravity.CENTER);

				id.setWidth((int)(width*0.12));
				name.setWidth((int)(width*0.15));
				bmmz.setWidth((int)(width*0.2));
				checktime.setWidth((int)(width*0.43));
				rz.setWidth((int)(width*0.1));
//				state.setWidth((int) (width * 0.1));

				id.setHeight((int)(height*0.04));
				name.setHeight((int)(height*0.04));
				bmmz.setHeight((int)(height*0.04));
				checktime.setHeight((int)(height*0.04));
				rz.setHeight((int)(height*0.04));
//				state.setHeight((int) (height * 0.04));



				id.setText((intFirst*recPerPage)+position+1 + "");
			//	bmmz.setText("aaaa");
			//	System.out.println(checkinout.getUserinfo().getDepartments().getDeptname());
				bmmz.setText(checkinout.getUserinfo().getDepartments().getSubname1(4));

				name.setText(checkinout.getUserinfo().getName());
				//checktime.setText("bbbb");
			//	System.out.println(checkinout.getCheckTime());
				checktime.setText(checkinout.getCheckTime());
				rz.setText("");

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

		intFirst=0;
		recPerPage=20;
	url=HttpUtil.BASE_URL+"checkinout!queryCheckinoutAll.action";
	params= new RequestParams();
		params.addQueryStringParameter("name",query);
		params.addQueryStringParameter("purview",user.getPurview());
		params.addQueryStringParameter("deptid",user.getDeptid()+"");
		params.addQueryStringParameter("queryname","1");
		params.addQueryStringParameter("intFirst",intFirst+"");
		params.addQueryStringParameter("recPerPage",recPerPage+"");
		otherquery=true;
		mThreadmy();
	}
	private void doSearchingOther() {

		intFirst=0;
		recPerPage=20;
		url=HttpUtil.BASE_URL+"checkinout!queryCheckinoutAll.action";
		params= new RequestParams();
		if (mCheckBox1.isChecked()) {
			params.addQueryStringParameter("branchid",spinner1.getSelectedItemPosition()+"");
		//	map.put("branchid", spinner1.getSelectedItemPosition()+"");
		} else {
			params.addQueryStringParameter("branchid","0");

		}

		if (mCheckBox2.isChecked()) {
			params.addQueryStringParameter("name",spinner3.getSelectedItem().toString());

		} else {
			params.addQueryStringParameter("name","");
		}

		if (mCheckBox3.isChecked()) {
			params.addQueryStringParameter("searchnd",spinner4.getSelectedItem().toString());

		} else {
			params.addQueryStringParameter("searchnd","0");
		}
		if (mCheckBox4.isChecked()) {
			params.addQueryStringParameter("weekl",spinner5.getSelectedItemPosition()+"");
		;
		} else {
			params.addQueryStringParameter("weekl","-1");
		}
		params.addQueryStringParameter("purview",user.getPurview());
		params.addQueryStringParameter("deptid",user.getDeptid()+"");
		params.addQueryStringParameter("queryname","1");
		params.addQueryStringParameter("intFirst",intFirst+"");
		params.addQueryStringParameter("recPerPage",recPerPage+"");
		otherquery=true;
		mThreadmy();
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
				myAdapter = new MyAdapter(checkinoutList, 1);
				mListView.setAdapter(myAdapter);
				mListView.setPullLoadEnable(true);

				mListView.setXListViewListener(CheckinoutListFyActivity.this);
				mListView.setOnItemClickListener(CheckinoutListFyActivity.this);

				onLoad();
			}
		}, 2000);
	}
	/**
	 * 提交转到页码按钮处理事件
	 * */
	public void txxx_pages_sumbit_Event(View view) throws Exception
	{


		LayoutInflater factory = LayoutInflater.from(CheckinoutListFyActivity.this);
		final View textEntryView = factory.inflate(R.layout.dialogpage, null);
		AlertDialog dlg = new AlertDialog.Builder(CheckinoutListFyActivity.this)

				.setTitle("页码跳转页面！！！")
				.setView(textEntryView)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						System.out.println("-------------->6");
						EditText dialognum= (EditText) textEntryView.findViewById(R.id.username_edit);
						String page0=dialognum.getText().toString();
						Toast.makeText(CheckinoutListFyActivity.this, "页码: " + page0, Toast.LENGTH_SHORT).show();
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
							if(otherquery){
								geneCheckinoutOther();
							}else {
								geneCheckinoutItems();
							}
							nowpage.setText("页码：" + (intFirst+1)+ "/" + pages);
							myAdapter = new MyAdapter(checkinoutList, 1);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// 当otherActivity中返回数据的时候，会响应此方法
// requestCode和resultCode必须与请求startActivityForResult()和返回setResult()的时候传入的值一致。
		if (requestCode == 1 &&(resultCode ==CheckinoutAddActivity.RESULT_CODE||resultCode ==CheckinoutDetailActivity.RESULT_CODE)) {

		//	geneCheckinoutItems();
		}
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