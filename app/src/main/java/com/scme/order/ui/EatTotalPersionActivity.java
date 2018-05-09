package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.scme.order.model.Teats;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
import com.scme.order.service.EatsService;

import com.scme.order.service.TxxxService;
import com.scme.order.service.UserService;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.SerializableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.scme.order.util.MyAppVariable;

//import com.scme.order.service.EatsDetailsService;

/**
 * 查看订单
 */
public class EatTotalPersionActivity  extends BaseActivity implements OnItemClickListener, OnItemSelectedListener {

	private ListView lvEats;
	private TextView textView;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> eatsMapList = new ArrayList<HashMap<String, Object>>();
	private List list;
	private int workerid = 0;
	private int eattolnumfs =0;
	private int eattolnumje = 0;
	private EditText etEatNum;//人数
	private ListView eatsListView;
	private TextView tvEatTolNums;
	private TextView tvEatTolprices;
	private EatsListSimpleAdapter eatsListSimpleAdapter;
	private ActionBar actionBar;
	private String name="";
	private String purview;
	private MyAppVariable myAppVariable;
	private List<Teats> eats;

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eattotalpersion_list);
		lvEats = (ListView) findViewById(R.id.lvEats);

		tvEatTolNums=(TextView)findViewById(R.id.tvEatTolNum);
		tvEatTolprices=( TextView)findViewById(R.id.tvEatToltalprice);
        myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
		Tusers users0 = myAppVariable.getTusers();

		name=users0.getUserName();
		if(myAppVariable.getTxxxid()==0) {
			workerid = users0.getId();
		}else{
			workerid=myAppVariable.getTxxxid();
		}
		purview=users0.getPurview();
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doSearching(query);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
			for (int i = 0; i <list.size(); i++) {
				//在线程中完成数据请求
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("eatTotalid",list.size() - i);
				List list0 = (List)list.get(i);
				//System.out.println("份数" + object);
				map.put("name",list0.get(0));
				map.put("ym", list0.get(2));
				map.put("fs", ((Double) list0.get(3)).intValue());
				map.put("je", ((Double) list0.get(4)).intValue());
				map.put("workerid", ((Double) list0.get(5)).intValue());
		//		map.put("unclear", IsCheckOut(list0.get(1).toString()));
				map.put("purview", purview);
				eattolnumfs = eattolnumfs + ((Double) list0.get(3)).intValue();
				eattolnumje = eattolnumje + ((Double) list0.get(4)).intValue();
				eatsMapList.add(map);
			}
					myAppVariable.setTxxxid((int)eatsMapList.get(0).get("workerid"));
					myHandler.sendMessage(myHandler.obtainMessage());
				}
			});
			t.start();
		}else {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {

							EatsService eatsService = new EatsService();
							list = eatsService.QueryEatsTotalPersion(workerid);
						System.out.println("份数" + workerid);
						for (int i = 0; i <list.size(); i++) {
							//在线程中完成数据请求
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("eatTotalid",list.size() - i);
							List list0 = (List)list.get(i);
							//System.out.println("份数" + object);
							map.put("name",list0.get(0));
							map.put("ym", list0.get(2));
							map.put("fs", ((Double) list0.get(3)).intValue());
							map.put("je", ((Double) list0.get(4)).intValue());
							map.put("workerid", ((Double) list0.get(5)).intValue());
						//	map.put("unclear", IsCheckOut(list0.get(1).toString()));
							map.put("purview", purview);
							eattolnumfs = eattolnumfs + ((Double) list0.get(3)).intValue();
							eattolnumje = eattolnumje + ((Double) list0.get(4)).intValue();
							eatsMapList.add(map);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					myHandler.sendMessage(myHandler.obtainMessage());
				}
			});
			t.start();
		}
	}
	/*
        创建菜单项
         */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
		menu.getItem(2).setVisible(false);
		menu.getItem(1).setEnabled(false);
		menu.getItem(0).setVisible(true);
		menu.getItem(1).setVisible(false);

		SupportMenuItem searchItem = (SupportMenuItem) menu
				.findItem(R.id.action_search);

		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		SearchManager searchManager = (SearchManager) EatTotalPersionActivity.this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(EatTotalPersionActivity.this.getComponentName()));

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




	/**
	 * 数据加载完之后消除Loding对话框
	 */
	private Handler myHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			simpleAdapter = new SimpleAdapter(EatTotalPersionActivity.this, eatsMapList, R.layout.eatstotalpersion_item,
					new String[]{"eatTotalid","name","ym", "fs", "je"},
					new int[]{R.id.tvEatsItemEatid, R.id.tvEatsItemName,R.id.tvEatsItemYmonth,
							R.id.tvEatsItemNumber, R.id.tvEatsItemPrice});
			lvEats.setAdapter(simpleAdapter);
			lvEats.setOnItemClickListener(EatTotalPersionActivity.this);
//
		tvEatTolNums.setText("总份数：" + eattolnumfs + " 份");
			tvEatTolprices.setText("总金额: " + eattolnumje  + " 元");
			super.handleMessage(msg);
	}
	};

	public String IsCheckOut(String status) {
		String str = "";
		if (status.equals("0"))
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

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		int ii= new Long(arg3).intValue();
//		myAppVariable.setTxxxid(workerid);
		Adapter adapter=arg0.getAdapter();
		Map<String,String> map=(Map<String, String>) adapter.getItem(arg2);
		String ym0=map.get("ym");
		System.out.println("ym="+ym0);
//		myAppVariable.setTxxxid(ii);
		myAppVariable.setMap(map);
		Intent intent = new Intent();
		intent.setClass(this, EatTotalDetailActivity.class);
		startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	private void doSearching(String query) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", query);
		try {
			//获取餐桌列表数据
			EatsService eatService = new EatsService();

			list = eatService.queryEatsName(query);
           myAppVariable.setList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(list!=null) {

			progressDialog.dismiss(); //消除Loding对话框
//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
		} else{
			new AlertDialog.Builder(this).setTitle("查无此人！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();

					intent.setClass(EatTotalPersionActivity.this, EatTotalPersionActivity.class);
					startActivity(intent);
				}
			}).show();
		}
	}
}
