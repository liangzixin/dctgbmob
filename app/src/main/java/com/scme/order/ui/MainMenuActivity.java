package com.scme.order.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.scme.order.adpater.MyPanelListAdapter;
import com.scme.order.model.Tusers;
import com.scme.order.service.BaseService;
import com.scme.order.service.ProgressListener;
import com.scme.order.util.CustomViewBinder;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.Pictures;
import com.scme.order.view.PopMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
// * 主菜单
 */
public class MainMenuActivity extends Activity implements ProgressListener, AdapterView.OnItemClickListener {

	private TextView textView;


	static String userNamel = "";
	static int userId1 = 0;
	//static String purview = "";
	private Handler testHandler;
//	private ListView mainLzxListView;

	private List<Map<String, Object>> mainLzxList = new ArrayList<Map<String, Object>>();


	private ProgressDialog progressDialog;

	private List<Tusers> users;


	private SimpleAdapter usersSimpleAdapter;

	private MyAppVariable myAppVariable;

	private Context context = MainMenuActivity.this;
	private PopMenu popMenu;
	private HttpHandler<String> handler;
	//	private HttpUtils httpUtils= new HttpUtils();
	private    String url=null;
	private 	RequestParams params;


	private PanelListLayout pl_root;
	private ListView lv_content;

	private MyPanelListAdapter adapter;

	//private List<Map<String, String>> contentList = new ArrayList<>();

	private List<String> columnList;

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
//	@InjectView(R.id.tvUserDay)
//	TextView mainTextView;
//	@InjectView(R.id.textView1)
//	TextView mainTextView7;
//	@InjectView(R.id.textView2)
//	TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//
		setContentView(R.layout.activity_mainlzx_list);
		ButterKnife.inject(this);


	//	mainLzxListView = (ListView) findViewById(R.id.lvSeletFoodsType);

		myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable

		textView = (TextView) findViewById(R.id.tvMainUserName);
       	String name="";
		if(myAppVariable.getTusers().getName()!=null) name=myAppVariable.getTusers().getName();
		textView.setText("昆明市东川区企业退休人员管理办公室移动OA  " +name);


		button1.setOnClickListener(onViewClick);
		button2.setOnClickListener(onViewClick);
		button3.setOnClickListener(onViewClick);
		button4.setOnClickListener(onViewClick);
		button5.setOnClickListener(onViewClick);
		initView();
		initContentDataList();


	}

	private void initView() {

		pl_root = (PanelListLayout) findViewById(R.id.id_pl_root);
		lv_content = (ListView) findViewById(R.id.id_lv_content);

		//设置listView为多选模式，长按自动触发
//		lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
	//	lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());

		//listView的点击监听
		lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				ListView listView = (ListView) parent;
//				HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				Map<String,Object> map =null;
				map=mainLzxList.get(position);
				int workerid = Integer.parseInt(String.valueOf(map.get("id")));

				myAppVariable.setTxxxid(workerid);

				if (map.get("type").equals("1")) {
					Intent intent = new Intent();
					intent.setClass(MainMenuActivity.this, UserDetailActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(MainMenuActivity.this, DydhDetailActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
		@Override
		public void onRefresh() {
			// you can do sth here, for example: make a toast:
			Toast.makeText(MainMenuActivity.this, "已经到顶了！", Toast.LENGTH_SHORT).show();
			// don`t forget to call this
			adapter.getSwipeRefreshLayout().setRefreshing(false);
		}
	}

	/** 生成一份横向表头的内容
	 *
	 * @return List<String>
	 */
	private List<String> getRowDataList(){
		List<String> rowDataList = new ArrayList<>();
		rowDataList.add("头像");
		rowDataList.add("姓名");
		rowDataList.add("科室");
		rowDataList.add("生日(入党日)");
		rowDataList.add("电码号码");
			return rowDataList;
	}

	/**
	 * 初始化content数据
	 */
	private void initContentDataList() {
		getBirthday();

	}

	private void initColumnDataList(){
		columnList = new ArrayList<>(mainLzxList.size());
		for (int i = 0;i<mainLzxList.size();i++){
			columnList.add(String.valueOf(i));
		}
	}

	/**
	 * 更新content数据
	 */
	private void changeContentDataList() {
		mainLzxList.clear();
//		for (int i = 1; i < 500; i++) {
//			Map<String, String> data = new HashMap<>();
//			data.put("1", "第" + i + "第一个");
//			data.put("2", "第" + i + "第二个");
//			data.put("3", "第" + i + "第三个");
//			data.put("4", "第" + i + "第四个");
//			data.put("5", "第" + i + "第五个");
//			data.put("6", "第" + i + "第六个");
//			data.put("7", "第" + i + "第七个");
//			mainLzxList.add(data);
//		}
	}
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


	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
		//	progressDialog.dismiss();

//			usersSimpleAdapter = new SimpleAdapter(MainMenuActivity.this, mainLzxList, R.layout.users_birthday_list,
//					new String[]{"foodsImage", "branchid", "username", "tel", "workerid", "type"},
//					new int[]{R.id.ivFoodsItem, R.id.tvUsersBranchid, R.id.tvUsersName, R.id.tvUsersTel, R.id.tvWorkerId, R.id.tvType});
//			mainLzxListView.setAdapter(usersSimpleAdapter);//线程执行完后显示视图
//			mainLzxListView.setOnItemClickListener(MainMenuActivity.this);
//
//			usersSimpleAdapter.setViewBinder(new CustomViewBinder());
			adapter = new MyPanelListAdapter(MainMenuActivity.this, pl_root, lv_content, R.layout.item_content, mainLzxList);
			adapter.setInitPosition(mainLzxList.size());
			adapter.setSwipeRefreshEnabled(true);
			adapter.setRowDataList(getRowDataList());
			adapter.setTitle("序号");
			adapter.setOnRefreshListener(new CustomRefreshListener());
			pl_root.setAdapter(adapter);

			super.handleMessage(msg);
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
//		ListView listView = (ListView) parent;
//		HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
//
//		int workerid = Integer.parseInt(String.valueOf(map.get("workerid")));
//
//		myAppVariable.setTxxxid(workerid);
//
//		if (map.get("type").equals("1")) {
//			Intent intent = new Intent();
//			intent.setClass(this, UserDetailActivity.class);
//			startActivity(intent);
//		} else {
//			Intent intent = new Intent();
//			intent.setClass(this, DydhDetailActivity.class);
//			startActivity(intent);
//		}
	}
	private void getBirthday() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
        url= HttpUtil.BASE_URL + "user!queryTodayAnd7.action";
		params= new RequestParams();
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
//						count=myobject.getInt("count");
						listArray=myobject.getString("userslist");
						users= BaseService.getGson().fromJson(listArray, new TypeToken<List<Tusers>>() {}.getType());
						System.out.println(users.size());
					} catch (JSONException e) {
						e.printStackTrace();
					}

					if (users.size() > 0) {

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
							map.put("branchid", users.get(i).getAddress());
							map.put("birday", users.get(i).getBirday());
							map.put("tel", users.get(i).getTel());
							map.put("id", users.get(i).getJob()+"");
							map.put("type",users.get(i).getSingle());
							mainLzxList.add(map);
						}
					}

				}
				myHandler.sendMessage(myHandler.obtainMessage());
			}

			@Override
			public void onFailure(HttpException e, String s) {
				progressDialog.dismiss();
				Toast.makeText(MainMenuActivity.this, "数据加载失败！！！", Toast.LENGTH_SHORT).show();
			}
		});

	}
}