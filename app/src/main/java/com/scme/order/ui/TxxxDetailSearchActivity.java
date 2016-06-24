package com.scme.order.ui;

import android.annotation.SuppressLint;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.service.TxxxService;
import com.scme.order.util.MyAppVariable;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class TxxxDetailSearchActivity extends BaseActivity implements OnItemSelectedListener{
    private ProgressDialog progressDialog;
    private Txxx txxx;
    private int txxxid;
    private Spinner spinner;
    private Spinner spinner1;
    private Tusers tusers;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
//    private  TxxxService txxxService ;
//    private MenuItem searchItem;
    private static final String[] m={"请选择认证方式","填表认证","本人认证","代认证"};
    private static final String[] m1={"请选择认证时间","201602","201603","201604","201605"};
  // @InjectView(R.id.main_edt_error) MaterialEditText medtError;
//  private android.support.v7.app.ActionBar mActionBar;
    @InjectView(R.id.img_1) ImageView img1;
    @InjectView(R.id.img_2) ImageView img2;
    @InjectView(R.id.img_3) ImageView img3;
    @InjectView(R.id.bmmc) TextView bmmc;
    @InjectView(R.id.grbh) MaterialEditText grbh;
    @InjectView(R.id.name) MaterialEditText name;
    @InjectView(R.id.sfzh) MaterialEditText sfzh;
    @InjectView(R.id.hkdz) MaterialEditText hkdz;
    @InjectView(R.id.czdz) MaterialEditText czdz;
    @InjectView(R.id.lxdh1) MaterialEditText lxdh1;
    @InjectView(R.id.lxdh2) MaterialEditText lxdh2;
    @InjectView(R.id.lxdh3) MaterialEditText lxdh3;
//    @InjectView(R.id.rzjk) MaterialEditText rz13jk;
    @InjectView(R.id.rzsj) MaterialEditText rz13sj;
    @InjectView(R.id.rzzb) MaterialEditText rz13zb;
    @InjectView(R.id.rzdd) MaterialEditText rz13dd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txxxdetail);

        ButterKnife.inject(this);
//        mActionBar = getSupportActionBar();
//
//        mActionBar.setHomeButtonEnabled(true);
//        mActionBar.setDisplayShowHomeEnabled(true);
//
//        mActionBar.setDisplayHomeAsUpEnabled(true);
        //获得绑定参数
		Bundle bundle = this.getIntent().getExtras();
	//	Tusers users0=(Tusers)bundle.getSerializable("user0");
	  txxxid=bundle.getInt("txxxid");
        spinner = (Spinner) findViewById(R.id.rzjk);
//        spinner1 = (Spinner) findViewById(R.id.rzsj);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
//        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m1);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
//        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);
//        spinner1.setAdapter(adapter1);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(this);
        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();
//        txxxid=myAppVariable.getTxxxid();
//        txxxid=14;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearching(query);

            showView(txxx);
            progressDialog.dismiss(); //消除Loding对话框
        }else{
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取餐桌列表数据
                        TxxxService txxxService = new TxxxService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
                            txxx = txxxService.queryTxxxId(txxxid);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    myHandler.sendMessage(myHandler.obtainMessage());
                }
            });

            t.start();

        }
        if (isThemeLight()) {
            img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
            img2.setImageResource(R.drawable.ic_phone_grey600_24dp);
            img3.setImageResource(R.drawable.ic_phone_grey600_24dp);

        } else {
            img1.setImageResource(R.drawable.ic_phone_white_24dp);
            img2.setImageResource(R.drawable.ic_phone_white_24dp);
            img3.setImageResource(R.drawable.ic_phone_white_24dp);
        }

        if (savedInstanceState == null) {
         //   medtError.setError("Username or Password is incorrect.");
        }
    }
    /**
     * 显示视图
     * @param txxx 职工的图片
     * @param txxx 职工的对象
     * */
    public void showView(Txxx txxx)
    {
        bmmc.setText(txxx.getBranch().getName());
        grbh.setText(txxx.getGrbh());
        name.setText(txxx.getName());
        sfzh.setText(txxx.getSfzh());
        hkdz.setText(txxx.getHkdz());
        czdz.setText(txxx.getCzdz());
        lxdh1.setText(txxx.getLxdh1());
        lxdh2.setText(txxx.getLxdh2());
        lxdh3.setText(txxx.getLxdh3());
//        rz13jk.setText(txxx.getRz13jk());
       rz13sj.setText(txxx.getRz13sj());
        rz13zb.setText(txxx.getRz13zb());
       rz13dd.setText(txxx.getRz13dd());
        if(txxx.getRz13jk().equals("")){
            spinner.setSelection(0);
        }else if(txxx.getRz13jk().equals("填表认证")){
            spinner.setSelection(1);
        }else if(txxx.getRz13jk().equals("本人认证")){
            spinner.setSelection(2);
        }else if(txxx.getRz13jk().equals("代认证")){
            spinner.setSelection(3);
        }

    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框
            showView(txxx);
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);

        if((tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统"))&&txxx.getRz13jk().equals("")){
            menu.getItem(1).setEnabled(true);
//            Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
        }else{
            menu.getItem(1).setEnabled(false);
        }
        SupportMenuItem searchItem = (SupportMenuItem) menu
                .findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);

        SearchManager searchManager = (SearchManager)TxxxDetailSearchActivity.this
                .getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(TxxxDetailSearchActivity.this.getComponentName()));

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {

            if (spinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "请选择认证方式！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (sfzh.length() != 18) {
                Toast.makeText(this, "身份证号码错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lxdh1.length() != 11 && !lxdh1.getText().equals("")) {
                Toast.makeText(this, "电话号码1错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lxdh2.length() != 11 && lxdh2.length() != 0) {
                Toast.makeText(this, "电话号码2错误！！！" + lxdh2.length(), Toast.LENGTH_SHORT).show();
                return false;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("认证提交中  请稍后...");
            progressDialog.show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                            String name="";
                        String rzjk = "";
                        String rzzb = "";
                        String rzdd = "";
//
                        rzjk = spinner.getSelectedItem().toString();
//                            rzsj=spinner1.getSelectedItem().toString();
                        rzzb = tusers.getUserName();
                        rzdd = tusers.getBranch().getName();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", name.getText().toString());
                        map.put("sfzh", sfzh.getText().toString());
                        map.put("hkdz", hkdz.getText().toString());
                        map.put("czdz", czdz.getText().toString());
                        map.put("lxdh1", lxdh1.getText().toString());
                        map.put("lxdh2", lxdh2.getText().toString());
                        map.put("lxdh3", lxdh3.getText().toString());
                        map.put("rzjk", rzjk);
//
                        map.put("rzzb", rzzb);
                        map.put("rzdd", rzdd);

                       TxxxService txxxService = new TxxxService();

                        txxxService.updateTxxxId(map);
//
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    myHandler.sendMessage(myHandler.obtainMessage());
                }
            });
            t.start();
//                Toast.makeText(this,"认证成功", Toast.LENGTH_SHORT).show();
//                return true;
            new AlertDialog.Builder(this).setTitle("认证提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    //	intent.setClass(SelectEatsFoodsListActivity.this, MainActivity.class);
                    //	intent.setClass(EatListActivity.this, MainActivity.class);
                    intent.setClass(TxxxDetailSearchActivity.this, TxxxDetailSearchActivity.class);
                    startActivity(intent);
                }
            }).show();
            return true;
        }
//        else if (id == R.id.action_about) {
//            startActivity(new Intent(this, AboutActivity.class));
//            return true;
//        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
//        bmmc.setText("你的血型是："+m[arg2]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void doSearching(String query) {
        TxxxService txxxService = new TxxxService();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", query);
//        txxx = txxxService.queryTxxxName(map);
//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
    }

}
