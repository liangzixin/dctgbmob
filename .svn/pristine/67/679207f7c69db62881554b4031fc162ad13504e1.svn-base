package com.scme.order.ui;

import java.util.GregorianCalendar;
import java.util.List;


import com.scme.order.model.Ttables;
import com.scme.order.service.TablesService;
import com.scme.order.ui.IntoTablesActivity.TablesAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 结账
 * 查询所有服务中的餐桌
 * */
public class SettlCountActivity extends Activity implements OnItemClickListener {

	private GridView gridView;
	private ProgressDialog progressDialog;
	private List<Ttables> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settl_count);
		gridView=(GridView) findViewById(R.id.settl_count_tables_gridview);
		final TablesService tbs=new TablesService();
		
		  progressDialog = new ProgressDialog(this); 
	      progressDialog.setMessage("数据加载中  请稍后..."); 
	      progressDialog.show();
	      Thread t=new Thread(new Runnable() { 
		      @Override 
		      public void run() {         
		      try {
		    	 //获取餐桌列表数据	
		  		list=tbs.QueryStatuTables();//在线程中完成数据请求
		       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		      myHandler.sendMessage(myHandler.obtainMessage()); 
		      } 
		 });
		t.start();	
		gridView.setOnItemClickListener(this);
	}

	/**
	 * 网格视图适配器
	 */
	class TablesAdapter extends BaseAdapter
	{

		public List<Ttables> tables;//数据源
		public int layoutId;//样式布局文件
		public TablesAdapter(List<Ttables> tables, int layoutId){//构造函数
			this.tables = tables;
			this.layoutId = layoutId;
		}
		public void setTables(List<Ttables> tables){
			this.tables = tables;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tables.size();//返回个数
		}

		@Override
		public Object getItem(int position) {
			return tables.get(position);
		}

		@Override
		public long getItemId(int position) {
			return tables.get(position).getTabId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null){
				convertView = getLayoutInflater().inflate(layoutId, null);
			}
			Ttables table = tables.get(position);
			ImageView ivTab = (ImageView) convertView.findViewById(R.id.imgv_tables);
			TextView tvTabName = (TextView) convertView.findViewById(R.id.tv_tablesName);
			TextView tvTabId=(TextView)convertView.findViewById(R.id.tv_tablesId);
			if(table.getTstatus()==1){
				ivTab.setImageResource(R.drawable.tables_fuwu);
			} 
			if(table.getTstatus()==0){
				ivTab.setImageResource(R.drawable.tables_kongxian);
			} 
			tvTabName.setText(table.getTabName());
			tvTabId.setText(table.getTabId()+"");
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Ttables tables=(Ttables) gridView.getItemAtPosition(arg2);
		int tabId=tables.getTabId();
		int tabStatus=tables.getTstatus();
		System.out.println("============tabId"+tabId);
		Intent intent=new Intent();
    	Bundle bundle=new Bundle();
    	bundle.putInt("tabId", tabId);
    	bundle.putInt("tabStatus", tabStatus);
    	bundle.putInt("SeeltCount", 1);
		intent.putExtras(bundle);
		intent.setClass(this, TableDetailedActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); 
            TablesAdapter tablesAdapter=new TablesAdapter(list, R.layout.tables_item);
    		gridView.setAdapter(tablesAdapter);
            super.handleMessage(msg); 
        } 
    };


}
