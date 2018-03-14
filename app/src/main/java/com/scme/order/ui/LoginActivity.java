package com.scme.order.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.scme.order.model.Tusers;
import com.scme.order.service.BaseService;
import com.scme.order.service.UserService;
import com.scme.order.tq.view.OwlView;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;

//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;

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
	private HttpUtils httpUtils;
	private String url=null;
	private HttpHandler<String> handler;
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

	}
	private OnClickListener btnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnLogin : { // 点击登录按钮
					userName=etUserName.getText().toString();
				    userPwd=etPwd.getText().toString();
					login(userName,userPwd);
//					try {
//					 //转中文乱码
//					 struserName = URLEncoder.encode(userName, "utf-8");
//					 final UserService userService=new UserService();
//
//					  progressDialog = new ProgressDialog(LoginActivity.this);
//				      progressDialog.setMessage("登录中  请稍后...");
//				      progressDialog.show();
//						Thread th= new Thread(new Runnable() {
//				      @Override
//				      public void run() {
////				      try {
//				    	  user=userService.login(struserName, userPwd);//在线程中完成数据请求
////				      } catch (InterruptedException e) {
////				      e.printStackTrace();
////				       } catch (Exception e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//				      myHandler.sendMessage(myHandler.obtainMessage());
//				      }
//				      }).start();
////						System.out.println("用户名0：");
////				System.out.println("用户名："+user.getUserName());
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//					btn_app_sy();
					break;
			}

				case R.id.btnExit : // 点击注册按钮
					System.exit(0);
					break;
			}
		}
	};
	public void btn_app_sy() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("数据加载中  请稍后...");
		progressDialog.show();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final UserService userService=new UserService();
					user=userService.login(struserName, userPwd);//在线程中完成数据请求
//					Thread.sleep(500);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHandler.sendMessage(myHandler.obtainMessage());
				//		myHandler.sendMessage(myHandler.obtainMessage());
			}
		});
		t.start();



	}
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
				 myAppVariable.setTusers(new Tusers());
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

	private void login(String zhanghao,String password) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在登录  请稍后...");
		progressDialog.show();
//        ThreadPoolUtils.execute(new HttpPostThread(this, zhanghao, password, hand));
		httpUtils = new HttpUtils();
		url= HttpUtil.BASE_URL + "user!login.action?userName=" + userName + "&userPwd=" + userPwd + "";

		handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progressDialog.dismiss();
				if (responseInfo.result !="0") {

					SharedPreferences.Editor editor=sharedPreferences.edit();
					editor.putString("userName", etUserName.getText().toString());
					editor.putString("password", etPwd.getText().toString());
					//使用commit方法提交修个的数据
					if(cbSaveUser.isChecked())
					{
						//提交数据
						editor.commit();
					}
					String result = responseInfo.result;

						user = BaseService.getGson().fromJson(responseInfo.result, new TypeToken<Tusers>() {
						}.getType());

					titleUserName=user.getUserName();
					titleUserId=user.getId();
					myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable

					myAppVariable.setTusers(user);
					Intent intentmy=new Intent(LoginActivity.this, MainMenuActivity.class);
					startActivity(intentmy);
					finish();
//					overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
				}
			}

			@Override
			public void onFailure(HttpException e, String s) {
				Toast.makeText(LoginActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
			}
		});
	}

}
