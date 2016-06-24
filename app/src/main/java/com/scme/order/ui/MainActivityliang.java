package com.scme.order.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
//import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivityliang extends Activity
{
	private TextView txt;
	private ActionBar actionBar;
	//private View titleView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//	supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		setContentView(R.layout.main);
		actionBar=getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);

//		ActionBar.LayoutParams lp =new ActionBar.LayoutParams(
//				ActionBar.LayoutParams.MATCH_PARENT,
//				ActionBar.LayoutParams.MATCH_PARENT,
//				Gravity.CENTER);
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View titleView = inflater.inflate(R.layout.main,null);
//		actionBar.setCustomView(titleView, lp);
		txt = (TextView) findViewById(R.id.txt);
		// 为文本框注册上下文菜单
		registerForContextMenu(txt);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

//		View viewTitleBar = getLayoutInflater().inflate(R.layout.main1, null);
//		getActionBar().setCustomView(viewTitleBar, lp);
		MenuInflater inflator = new MenuInflater(this);
		//装填R.menu.my_menu对应的菜单，并添加到menu中
		inflator.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	// 创建上下文菜单时触发该方法
	@Override
	public void onCreateContextMenu(ContextMenu menu, View source,
									ContextMenu.ContextMenuInfo menuInfo)
	{
		MenuInflater inflator = new MenuInflater(this);
		//装填R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.context , menu);
		menu.setHeaderIcon(R.drawable.tools);
		menu.setHeaderTitle("请选择背景色");
	}
	// 上下文菜单中菜单项被单击时触发该方法
	@Override
	public boolean onContextItemSelected(MenuItem mi)
	{
		// 勾选该菜单项
		mi.setChecked(true);  // ①
		switch (mi.getItemId())
		{
			case R.id.red:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.RED);
				break;
			case R.id.green:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.GREEN);
				break;
			case R.id.blue:
				mi.setChecked(true);
				txt.setBackgroundColor(Color.BLUE);
				break;
		}
		return true;
	}
	@Override
	// 菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi)
	{
		if(mi.isCheckable())
		{
			// 勾选该菜单项
			mi.setChecked(true);  // ②
		}
		//判断单击的是哪个菜单项，并有针对性地作出响应
		/*
		switch (mi.getItemId())
		{
			case R.id.zhk_qj:
				txt.setTextSize(10 * 2);
				break;
			case R.id.zhk_yz:
				txt.setTextSize(12 * 2);
				break;
			case R.id.zhk_bx:
				txt.setTextSize(14 * 2);
				break;
			case R.id.sbk_tx:
				txt.setTextSize(16 * 2);
				break;
			case R.id.sbk_ys:
				txt.setTextSize(18 * 2);
				break;
			case R.id.fwk_dk:
				txt.setTextColor(Color.RED);
				mi.setChecked(true);
				break;
			case R.id.fwk_cx:
				txt.setTextColor(Color.GREEN);
				mi.setChecked(true);
				break;
			case R.id.fwk_hj:
				txt.setTextColor(Color.BLUE);
				mi.setChecked(true);
				break;
			case R.id.app_sy:
				Toast toast = Toast.makeText(MainActivityliang.this
						, "您单击了首页菜单项" , Toast.LENGTH_SHORT);
				toast.show();
				break;
		}
		*/
		return true;
	}
}

