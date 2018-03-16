package com.scme.order.ui;

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
import com.scme.order.model.Ysry;
import com.scme.order.service.YsryService;
import com.scme.order.util.MyAppVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class YsryDetailActivity extends BaseActivity implements OnItemSelectedListener{
    private ProgressDialog progressDialog;
    private Ysry ysry;
    private List<Ysry> ysrys;
    private int ysryid;
    private Spinner spinner;
    private Spinner spinner1;
    private Tusers tusers;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
//    private  YsryService ysryService ;
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
//    @InjectView(R.id.action_ysrydetail_mainrz) TextView  rz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ysrydetail);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();
        ysryid=myAppVariable.getTxxxid();
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
//        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
//        tusers=myAppVariable.getTusers();
//        ysryid=myAppVariable.getYsryid();
//        ysryid=14;
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("数据加载中  请稍后...");
//        progressDialog.show();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearching(query);

        }else {


            ysry =myAppVariable.getYsry();
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
        showView(ysry);
    }
    /**
     * 显示视图
     * @param ysry 职工的图片
     * @param ysry 职工的对象
     * */
    public void showView(Ysry ysry)
    {
//       bmmc.setText(ysry.getId()+"号");

        bmmc.setText(ysry.getBranch().getName());

        name.setText(ysry.getA5());
        sfzh.setText(ysry.getA12());
        hkdz.setText(ysry.getA13());
        czdz.setText(ysry.getA14());
        lxdh1.setText(ysry.getA160());
        lxdh2.setText(ysry.getA161());
        lxdh3.setText(ysry.getA162());
       grbh.setText(ysry.getA3());
       rz13sj.setText(ysry.getA26());
        rz13zb.setText(ysry.getA27());
       rz13dd.setText(ysry.getA28());
        if(ysry.getA25().equals("")){
            spinner.setSelection(0);
        }else if(ysry.getA25().equals("填表认证")){
            spinner.setSelection(1);
        }else if(ysry.getA25().equals("本人认证")){
            spinner.setSelection(2);
        }else if(ysry.getA25().equals("代认证")){
            spinner.setSelection(3);
        }

    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框
            showView(ysry);
//            rz.se;
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
        menu.getItem(2).setVisible(false);
//        Toast.makeText(YsryDetailActivity.this, tusers.getPurview()+"与"+ysry.getRz13jk(), Toast.LENGTH_SHORT).show();

        if((tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统"))&&ysry.getA25().equals("")){
//            if(tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统")){
            menu.getItem(1).setEnabled(true);
//            Toast.makeText(this,"0", Toast.LENGTH_SHORT).show();
        }else{
            menu.getItem(1).setEnabled(false);
//            Toast.makeText(this,"1", Toast.LENGTH_SHORT).show();
        }
        SupportMenuItem searchItem = (SupportMenuItem) menu
                .findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);

        SearchManager searchManager = (SearchManager)YsryDetailActivity.this
                .getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(YsryDetailActivity.this.getComponentName()));

        searchItem
                .setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
//                        Toast.makeText(YsryDetailActivity.this, "扩张了", Toast.LENGTH_SHORT).show();
//                        System.out.println("扩张了");
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
//                        Toast.makeText(YsryDetailActivity.this, "收缩了", Toast.LENGTH_SHORT).show();
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

                       YsryService ysryService = new YsryService();

                        ysryService.updateYsryId(map);
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
                    intent.setClass(YsryDetailActivity.this, YsryDetailActivity.class);
                    startActivity(intent);
                }
            }).show();

        }



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

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", query);
        try {
            //获取餐桌列表数据
            YsryService ysryService = new YsryService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
            ysrys = ysryService.queryYsryName(map);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      if(ysrys!=null) {
//          myAppVariable.setYsrys(ysrys);
          ysry=ysrys.get(0);
//          Intent intent = new Intent();
//          Bundle bundle=new Bundle();
//          bundle.putInt("tabId", searchManager);
//          intent.putExtras(bundle);
          //	intent.setClass(SelectEatsFoodsListActivity.this, MainActivity.class);
          //	intent.setClass(EatListActivity.this, MainActivity.class);
//          intent.setClass(YsryDetailActivity.this, YsryDetailActivity.class);
//          startActivity(intent);
          showView(ysry);
          progressDialog.dismiss(); //消除Loding对话框
//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
      } else{
          new AlertDialog.Builder(this).setTitle("查无此人！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int which) {
                  // TODO Auto-generated method stub
                  Intent intent = new Intent();
                  //	intent.setClass(SelectEatsFoodsListActivity.this, MainActivity.class);
                  //	intent.setClass(EatListActivity.this, MainActivity.class);
                  intent.setClass(YsryDetailActivity.this, YsryDetailActivity.class);
                  startActivity(intent);
              }
          }).show();
      }
    }

}
