package com.scme.order.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;


import com.scme.order.model.Tusers;
import com.scme.order.service.UserService;
import com.scme.order.tq.view.OwlView;
import com.scme.order.util.MyAppVariable;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnCheckedChangeListener {

	private EditText etUserName;
	private EditText etPwd;
	private CheckBox cbSaveUser;
	private CheckBox cbAutoLogin;
	private SharedPreferences sharedPreferences;

	private String userName;
	private String userPwd;
	private String struserName;
	private String json;
	private Tusers user;
	private String userNameStr; 
	private String passwordStr;
	private ProgressDialog progressDialog;
	static String titleUserName="";
	static int titleUserId=0;
	private ActionBar actionBar;
	private MyAppVariable myAppVariable;
	private TextView tvBtnReg,loginButton;
	private OwlView mOwlView;
//	private Button loginButton1;

//	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		actionBar=getActionBar();
		actionBar.hide();
		if (Build.VERSION.SDK_INT >= 11) {
		      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads     ().detectDiskWrites().detectNetwork().penaltyLog().build());
		   StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		  }

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		etUserName=(EditText) findViewById(R.id.etUserName);
		etPwd=(EditText) findViewById(R.id.etPwd);
		cbAutoLogin=(CheckBox) findViewById(R.id.cbAutoLogin);
		cbSaveUser=(CheckBox) findViewById(R.id.cbSaveUser);
//		loginButton=(Button) findViewById(R.id.btnLogin);
//       loginButton1=new Button();
		//获得一个SharedPreferences对象
		sharedPreferences=getSharedPreferences("SETTING_INFOS",0);
		//取出保存信息
		userNameStr=sharedPreferences.getString("userName", "");
		passwordStr=sharedPreferences.getString("password", "");
		//把信息放进输入框
		etUserName.setText(userNameStr);
		etPwd.setText(passwordStr);
		cbSaveUser.setOnCheckedChangeListener(this);
		loginButton= (TextView) findViewById(R.id.btnLogin);
		loginButton.setOnClickListener(btnClickListener);

		tvBtnReg = (TextView) findViewById(R.id.btnExit);
		tvBtnReg.setOnClickListener(btnClickListener);
		TextView textView = (TextView) findViewById(R.id.tv_headerTitle);
		textView.setText("区退管办移动OA系统");
		mOwlView= (OwlView) findViewById(R.id.owl_view);
		etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					mOwlView.open();

				}else{
					mOwlView.close();
				}
			}
		});

//		loginButton1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v)  {
//				userName=etUserName.getText().toString();
//				userPwd=etPwd.getText().toString();
//				try {
//					 //转中文乱码
//					 struserName = URLEncoder.encode(userName, "utf-8");
//					 final UserService userService=new UserService();
//
//					  progressDialog = new ProgressDialog(LoginActivity.this);
//				      progressDialog.setMessage("登录中  请稍后...");
//				      progressDialog.show();
//				      new Thread(new Runnable() {
//				      @Override
//				      public void run() {
//				      try {
//				    	  user=userService.login(struserName, userPwd);//在线程中完成数据请求
//				      } catch (InterruptedException e) {
//				      e.printStackTrace();
//				       } catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				      myHandler.sendMessage(myHandler.obtainMessage());
//				      }
//				      }).start();
////					System.out.println("用户名0：");
////					System.out.println("用户名："+user.getUserName());
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
	}
	private OnClickListener btnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnLogin : { // 点击登录按钮
					userName=etUserName.getText().toString();
				userPwd=etPwd.getText().toString();
					try {
					 //转中文乱码
					 struserName = URLEncoder.encode(userName, "utf-8");
					 final UserService userService=new UserService();

					  progressDialog = new ProgressDialog(LoginActivity.this);
				      progressDialog.setMessage("登录中  请稍后...");
				      progressDialog.show();
				      new Thread(new Runnable() {
				      @Override
				      public void run() {
				      try {
				    	  user=userService.login(struserName, userPwd);//在线程中完成数据请求
				      } catch (InterruptedException e) {
				      e.printStackTrace();
				       } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      myHandler.sendMessage(myHandler.obtainMessage());
				      }
				      }).start();
//						System.out.println("用户名0：");
//				System.out.println("用户名："+user.getUserName());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
					break;
			}

				case R.id.btnExit : // 点击注册按钮
					System.exit(0);
					break;
			}
		}
	};
	/**
	 * 保存密码
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){			
		    etUserName.setText(userNameStr);
		    etPwd.setText(passwordStr);
		}
		if(isChecked==false){
		    etUserName.setText(null);
	     	etPwd.setText(null);
		}
	}
	
	
	/**
	 * 数据加载完之后消除Loding对话框
	 * */
	private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
		@Override 
        public void handleMessage(Message msg) { 
            progressDialog.dismiss(); 
            if(user!=null)
			 {
				//使SharedPreferences处于编辑状态
				SharedPreferences.Editor editor=sharedPreferences.edit();
				editor.putString("userName", etUserName.getText().toString());
				editor.putString("password", etPwd.getText().toString());
				//使用commit方法提交修个的数据
				if(cbSaveUser.isChecked())
				{
					//提交数据
					editor.commit();
				}
				
//		        	Intent intent=new Intent();
//		        	Bundle bundle=new Bundle();
//					bundle.putString("userName", userName);
				//	titleUserName=userName;//保存用户名
				 titleUserName=user.getUserName();
				 titleUserId=user.getId();
				  myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
//				 myAppVariable.getTusers().setPurview(user.getPurview());
//				 myAppVariable.setPurview(user.getPurview());
				 myAppVariable.setTusers(user);
//				 Tusers tusers0=new Tusers();
//				 tusers0=user;
//				 tusers0.setUserName(user.getUserName().toString());
//				 tusers0.setPurview(user.getPurview());
//				 tusers0.setId(user.getId());
				 Intent intentmy=new Intent(LoginActivity.this, MainMenuActivity.class);
//
//				 intent.setClass(LoginActivity.this, MainMenuActivity.class);
//				 Intent intentmy=new Intent(LoginActivity.this, ItemActivity.class);
//				 Bundle bundlemy=new Bundle();
//
//				 bundlemy.putSerializable("user0",tusers0);
//				 intentmy.putExtras(bundlemy);
				 startActivity(intentmy);
				 LoginActivity.this.finish();
//					intent.putExtras(bundle);
//				 	intent.setClass(LoginActivity.this, MainActivity.class);
//					 intent.setClass(LoginActivity.this, MainMenuActivity.class);
//					startActivity(intent);
//				 intent.putExtra("contentId", mContentIds[position]);
//				 intent.putExtra("title",mTitle[position]);
//				 startActivity(intent);
			 }
			 else
			 {
					Toast.makeText(LoginActivity.this, R.string.test_no, Toast.LENGTH_SHORT).show();
			 }
			user=null;
            super.handleMessage(msg);
        } 
    };

}
