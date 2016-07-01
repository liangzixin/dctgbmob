package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.model.Teats;
import com.scme.order.model.Teats;
//import com.scme.order.service.EatsDetailsService;
import com.scme.order.service.EatsService;
import com.scme.order.service.TablesService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;
import com.scme.order.util.Pingyin;
import com.scme.order.view.XListView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 查看订单
 * */
public class EatListActivity extends BaseActivity implements XListView.IXListViewListener,OnItemClickListener, OnItemSelectedListener,SoundPool.OnLoadCompleteListener {

	private XListView lvEats;
	private TextView textView;
	private ProgressDialog progressDialog;
	private ProgressDialog progressDialog2;
	private SimpleAdapter simpleAdapter;
	private List<HashMap<String, Object>> eatsMapList=new ArrayList<HashMap<String,Object>>();
	private Map map1=new HashMap<String,Object>();
	private Map map2=new HashMap<String,String>();
	private List<Teats> eatsList;
	private int workerid=0;
	private int eattolnums=0;
	private EditText etEatNum;//人数
	private ListView eatsListView;
	private TextView tvEatTolNums;
	private TextView tvEatTolprices;
	private EatsListSimpleAdapter eatsListSimpleAdapter;
	private android.app.ActionBar actionBar;
	private MyAppVariable myAppVariable;
	private MyAdapter myAdapter;
	private Teats eatcount;
	private int count;
	private Handler mHandler;
	private int countfs;
	private Double countmoeny;
	private int intFrist = 0;
	private int recPerPage = 20;
	private int pages=0;
	private TextView qingjiafoodid;
	private int start = 0;
	private static int refreshCnt = 0;
	private GetDate getDate=new GetDate();
	private boolean str=false;
	private SoundPool soundPool;
	private int volume;
	private AudioManager aMgr;
	private int sid_background;
	private int sid_chimp;
	private  int sid_bark;
	private Context context;
	private Integer eatNum=0;
	@InjectView(R.id.TolPage) TextView tolpage;
	@InjectView(R.id.NowPage) TextView nowpage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		actionBar=getActionBar();
//		actionBar=getSupportActionBar();
//		actionBar.hide();
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_eat_list);
		ButterKnife.inject(this);
		lvEats=(XListView) findViewById(R.id.lvEats);
		textView=(TextView) findViewById(R.id.tvFoodsOrderPrice);

		etEatNum=(EditText) findViewById(R.id.etEatNum);
		tvEatTolNums=(TextView)findViewById(R.id.tvEatTolNum);
//		tvEatTolprices=( TextView)findViewById(R.id.tvEatTolprice);

		myAppVariable=(MyAppVariable)getApplication();
		workerid=myAppVariable.getTusers().getId();
		textView.setText("姓名： "+myAppVariable.getTusers().getName());
//		progressDialog = new ProgressDialog(this);
//	    progressDialog.setMessage("数据加载中  请稍后...");
//	    progressDialog.show();
//		Thread t=new Thread(new Runnable() {
//		      @Override
//		      public void run() {
		    eatcount=new Teats();
		      try {

		    	  EatsService eatsService=new EatsService();

				  map1=eatsService.QueryTodayEatsCount(workerid);
				  countfs=(int)map1.get("countfs");
				  countmoeny=(Double)map1.get("countmoney");
				  eatsList=eatsService.QueryTodayEats(workerid, intFrist, recPerPage);
				  System.out.println("eatList.size()"+eatsList.size());

		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		count =(int)map1.get("count");

	pages = (count + recPerPage - 1) / recPerPage;       //计算出总的页数

	tolpage.setText("记录数："+count);
	nowpage.setText("页码："+(intFrist+1)+"/"+pages);

	if(count>recPerPage*(intFrist+1)) {
		lvEats.setPullLoadEnable(true);
	}else{
		lvEats.setPullLoadEnable(false);
	}
		myAdapter = new MyAdapter(eatsList, 1);
		View view = (LinearLayout) getLayoutInflater().inflate(R.layout.qingjia_fooder, null);
		qingjiafoodid = (TextView) view.findViewById(R.id.qingjia_foodid);
		qingjiafoodid.setText("份   数:  "+countfs+" 　份"+"  金　　额:  "+countmoeny+" 　元");
		lvEats.addFooterView(view);
		lvEats.setAdapter(myAdapter);

		lvEats.setXListViewListener(this);
		lvEats.setOnItemClickListener(this);
		myAdapter = new MyAdapter( eatsList, 1);
		mHandler = new Handler();

	}
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				if (intFrist >= 1&&pages!=1) {
					intFrist--;
					try {
						EatsService eatsService=new EatsService();
						eatsList=eatsService.QueryTodayEats(workerid,intFrist,recPerPage);
					} catch (IOException e) {
						e.printStackTrace();
					}
					nowpage.setText("页码：" + (intFrist + 1) + "/" + pages);
					myAdapter = new MyAdapter(eatsList, 1);
					lvEats.setAdapter(myAdapter);
					lvEats.setPullLoadEnable(true);
					onLoad();
				}else{
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
				if ((intFrist+1) < pages&&pages!=1) {
					intFrist++;
					try {
						EatsService eatsService=new EatsService();
						eatsList=eatsService.QueryTodayEats(workerid,intFrist,recPerPage);
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
				convertView = getLayoutInflater().inflate(R.layout.eats_dk_item, null);
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
				TextView eatbmmz= (TextView) convertView.findViewById(R.id.bmmz);



				id.setGravity(Gravity.CENTER);
				name.setGravity(Gravity.CENTER);
				eatdate.setGravity(Gravity.CENTER);
				eatnumber.setGravity(Gravity.CENTER);
				eatprice.setGravity(Gravity.CENTER);
				eatbmmz.setGravity(Gravity.CENTER);

				id.setWidth((int) (width * 0.1));
				name.setWidth((int) (width * 0.15));
				eatdate.setWidth((int) (width * 0.2));
				eatnumber.setWidth((int) (width * 0.1));
				eatprice.setWidth((int) (width * 0.1));
				eatbmmz.setWidth((int) (width * 0.35));

				id.setHeight((int) (height * 0.04));
				name.setHeight((int) (height * 0.04));
				eatdate.setHeight((int) (height * 0.04));
				eatnumber.setHeight((int) (height * 0.04));
				eatprice.setHeight((int) (height * 0.04));
				eatbmmz.setHeight((int) (height * 0.04));



				id.setText((intFrist * recPerPage) + position + 1 + "");
				eatbmmz.setText(eat.getBranch().getSubname1(8));
				name.setText(eat.getUser().getUserName());
               eatnumber.setText(eat.getEatnumber()+"");
				eatprice.setText(eat.getEatnumber()*eat.getUnitprice()+"");
              eatdate.setText(getDate.formatTime(getDate.StrToDate(eat.getEatdate())));

			}
			qingjiafoodid.setText("份   数:  "+countfs+" 　份"+"  金　　额:  "+countmoeny+" 　元");
			return convertView;
		}
	}

	/**
	 * 数据加载完之后消除Loding对话框
	 * */
//	private Handler myHandler = new Handler(){
//        @SuppressLint("HandlerLeak")
//		@Override
//        public void handleMessage(Message msg) {
//            progressDialog.dismiss();
//            simpleAdapter=new SimpleAdapter(EatListActivity.this, eatsMapList, R.layout.eats_item,
//    				new String[]{"eatsid","workerid","date","eatnumber","price","operator"},
//    				new int[]{R.id.tvEatsItemEatid, R.id.tvEatsItemName, R.id.tvEatsItemDate,
//    				R.id.tvEatsItemNumber, R.id.tvEatsItemPrice,R.id.tvEatsItemOperator});
//    		lvEats.setAdapter(simpleAdapter);
//            super.handleMessage(msg);
//			tvEatTolNums.setText("份数： " + eattolnums+"  份");
//			tvEatTolprices.setText("金额:  "+eattolnums*4+"  元");
//        }
//    };
    
    public String IsCheckOut(int status)
    {
    	String str="";
    	if(status==0)
    		str="否";
    	else
    		str="是";
    	return str;
    }
	/**
	 * 提交吃饭打卡按钮处理事件
	 * */
	public void eats_insert_sumbit_Event(View view) throws Exception
	{



				try {
					EatsService os=new EatsService();

					String strEatNum=etEatNum.getText().toString();
				    eatNum=Integer.parseInt(strEatNum);
                    map2.put("workerid",workerid+"");
					map2.put("eatnumber",strEatNum);
					map2.put("branchid",myAppVariable.getTusers().getBranch().getId()+"");
					map2.put("name",myAppVariable.getTusers().getName());
				 str=os.addEats(map2);//添加订单

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		if(str) {
			new AlertDialog.Builder(this).setTitle("打卡提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

            int r1=getResourdIdByResourdName(context, ChangeName(myAppVariable.getTusers().getName()));
			int r2=getResourdIdByResourdName(context,Changefs(eatNum));
					System.out.println("姓名拼音："+r1);
					System.out.println("份数拼音："+r2);
//					Object r1="R.raw."+ChangeName(myAppVariable.getTusers().getName());
					System.out.println("姓名拼音："+r1);
			if(r1!=0)		sid_background = soundPool.load(EatListActivity.this,r1, 1); //蟋蟀
					if(r1==0&&myAppVariable.getTusers().getName().equals("吕春红"))		sid_background = soundPool.load(EatListActivity.this,R.raw.luchunhong, 1); //蟋蟀
					if(r2!=0)		sid_chimp = soundPool.load(EatListActivity.this,r2, 1);//黑猩猩

					onLoadComplete(soundPool, sid_background,1);
					onLoadComplete(soundPool, sid_chimp,1);

					Intent intent = new Intent();

					intent.setClass(EatListActivity.this, EatListActivity.class);
					startActivity(intent);
				}
			}).show();
		}else{
			Toast.makeText(EatListActivity.this,"就餐打卡失败"+str, Toast.LENGTH_SHORT).show();

		}
	}

	/**
	 *吃饭列表适配器
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
			HashMap<String,String> eatsFoodMap=(HashMap<String, String>) eatsFood.get(position);

			return convertView;
		}

	}

	/**
	 * 计算吃饭总份数
	 * @paramprice
	 * */
	public  int getEatsTolPrice()
	{
	int price=0;
//		for(int i=0;i<food.size();i++)
//		{
//			price=price+(Double) food.get(i).get("foodsPrice");
//		}
		return price;
	}

	/**
	 * 下拉菜单事件处理
	 * */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2,
							   long arg3) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
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

	@Override
	protected    void onResume() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
//		soundPool.setOnLoadCompleteListener(this);
		aMgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);


		super.onResume();
	}
	@Override
	public void onLoadComplete(SoundPool sPool, int sid, int status) {
//
		Log.v("soundPool", "sid " + sid + " loaded with status " + status);
		final float currentVolume = ((float)aMgr.getStreamVolume(AudioManager.STREAM_MUSIC)) /
				((float)aMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//		if(status != 0)
//			return;
//		queueSound(sid,2000, currentVolume);
		if(sid == sid_background) {

			queueSound(sid,2000, currentVolume);
		} else if(sid == sid_chimp) {
			queueSound(sid,5000, currentVolume);
		}
	}
	private void queueSound(final int sid, final long delay, final float volume) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
//				if(soundPool == null) return;
				if (soundPool.play(sid, volume, volume, 1, 0, 1.0f) == 0) {
					Log.e("soundPool", "Failed to start sound (" + sid + ")");
				}
//				queueSound(sid, delay, volume);
			}
		}, delay);
	}

	public String  ChangeName(String name) {

		Pingyin pingyin = new Pingyin();

		String namepinyin ="";
		if (name != null) {
			namepinyin = pingyin.HanyuToPinyin(name);

		}
		return namepinyin;
	}


	public String Changefs(int fs) {
		String chanfs="";
		switch (fs)  {
			case 	1:
				chanfs="one";
				break;
			case 	2:
				chanfs="two";
				break;
			case 	3:
				chanfs="three";
				break;
			case 	4:
				chanfs="four";
				break;
			case 	5:
				chanfs="five";
				break;
		}
		return chanfs;
	}
	public static int getResourdIdByResourdName(Context context, String ResName){
		int resourceId = 0;
		try {
			Field field = R.raw.class.getField(ResName);
			field.setAccessible(true);

			try {
				resourceId = field.getInt(null);
			} catch (IllegalArgumentException e) {
//				log.showLogDebug("IllegalArgumentException:" + e.toString());
			} catch (IllegalAccessException e) {
//				log.showLogDebug("IllegalAccessException:" + e.toString());
			}
		} catch (NoSuchFieldException e) {
//			log.showLogDebug("NoSuchFieldException:" + e.toString());
		}
		return resourceId;
	}
}