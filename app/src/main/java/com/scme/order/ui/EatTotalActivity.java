package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.scme.order.model.EatTotal;
import com.scme.order.model.Teats;
import com.scme.order.service.EatsService;
import com.scme.order.service.TablesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import com.scme.order.service.EatsDetailsService;

/**
 * 查看订单
 */
public class EatTotalActivity extends BaseActivity implements OnItemClickListener, OnItemSelectedListener {

	private ListView lvEats;
	private TextView textView;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> eatsMapList = new ArrayList<HashMap<String, Object>>();
	private List eatsTotalList;
	private int workerid = 0;
	private int eattolnumfs =0;
	private int eattolnumje = 0;
	private EditText etEatNum;//人数
	private ListView eatsListView;
	private TextView tvEatTolNums;
	private TextView tvEatTolprices;
	private EatsListSimpleAdapter eatsListSimpleAdapter;
	private ActionBar actionBar;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar = getActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eattotal_list);
		lvEats = (ListView) findViewById(R.id.lvEats);
//		textView=(TextView) findViewById(id.tvFoodsOrderPrice);
//		textView.setText("姓名： "+MainMenuActivity.userNamel);
//		etEatNum=(EditText) findViewById(id.etEatNum);
		tvEatTolNums=(TextView)findViewById(R.id.tvEatTolNum);
		tvEatTolprices=( TextView)findViewById(R.id.tvEatToltalprice);
//
//		workerid=MainMenuActivity.userId1;

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EatsService eatsService = new EatsService();
//		    	  TablesService tablesService=new TablesService();
					//	 eatsList=eatsService.QueryAllEats();
					eatsTotalList = eatsService.QueryEatsTotal();
					for (int i = 0; i <eatsTotalList.size(); i++) {
						//在线程中完成数据请求
						HashMap<String, Object> map = new HashMap<String, Object>();
						//    String tabName=tablesService.queryTablesNameByTabId(eatsList.get(i).getTablesId());
						map.put("eatTotalid",eatsTotalList.size() - i);
						//   map.put("workerid",eatsList.get(i).getWorkerid());
						List list=(List)eatsTotalList.get(i);
						//System.out.println("份数" + object);
						map.put("ym", list.get(0));
////						//   map.put("isCheckOut", IsCheckOut(eatsList.get(i).getIsCheckOut()));
					map.put("fs",((Double) list.get(1)).intValue());
						map.put("je",((Double) list.get(2)).intValue());
//						System.out.println("份数" + ((Double) list.get(1)).intValue());
//						System.out.println("份数" + ((Double)list.get(2)).intValue());
						eattolnumfs= eattolnumfs+((Double) list.get(1)).intValue();
						eattolnumje= eattolnumje+((Double) list.get(2)).intValue();
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

	/**
	 * 数据加载完之后消除Loding对话框
	 */
	private Handler myHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			simpleAdapter = new SimpleAdapter(EatTotalActivity.this, eatsMapList, R.layout.eatstotal_item,
					new String[]{"eatTotalid", "ym", "fs", "je"},
					new int[]{R.id.tvEatsItemEatid, R.id.tvEatsItemName,
							R.id.tvEatsItemNumber, R.id.tvEatsItemPrice});
			lvEats.setAdapter(simpleAdapter);
			super.handleMessage(msg);
		tvEatTolNums.setText("总份数：" + eattolnumfs + " 份");
			tvEatTolprices.setText("总金额: " + eattolnumje  + " 元");
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
