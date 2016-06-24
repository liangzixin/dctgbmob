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
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class YsryAddActivity extends BaseActivity implements OnItemSelectedListener{
    private ProgressDialog progressDialog;
    private Ysry ysry;
    private List<Ysry> ysrys;
    private int ysryid;
    private Spinner spinner;
    private Spinner spinner1;
//    private Spinner spinner;
//    private Spinner spinner1;

    private Tusers tusers;
    private boolean str=false;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
//    private  YsryService ysryService ;
//    private MenuItem searchItem;
    private static final String[] m={"请选择部门","上大院管理服务站","下大院管理服务站","桂苑街管理服务站","落雪大院管理服务站","腊利大院管理服务站"};
    private static final String[] m6={"请选择性别","男","女"};
    private static final String[] m7={"请选择供养关系","夫妻","儿子","女儿"};
    private static final String[] m8={"请选择类别","工亡","非因工","离休遗属","精减退职"};
    private static final String[] m9={"请选择户口性质","城镇","农村"};
    private static final String[] m24={"请选择伤残级别","壹级","贰级","叁级","四级"};

  // @InjectView(R.id.main_edt_error) MaterialEditText medtError;
//  private android.support.v7.app.ActionBar mActionBar;
    @InjectView(R.id.img_1) ImageView img1;
    @InjectView(R.id.img_2) ImageView img2;
    @InjectView(R.id.img_3) ImageView img3;
  @InjectView(R.id.ysry_a3) MaterialEditText a3;
    @InjectView(R.id.ysry_a2) MaterialSpinner a2;
//
    @InjectView(R.id.ysry_a4) MaterialEditText a4;
    @InjectView(R.id.ysry_a5) MaterialEditText a5;
    @InjectView(R.id.ysry_a6) MaterialSpinner a6;
    @InjectView(R.id.ysry_a7) MaterialSpinner a7;
    @InjectView(R.id.ysry_a8) MaterialSpinner a8;
    @InjectView(R.id.ysry_a9) MaterialSpinner a9;
    @InjectView(R.id.ysry_a10) MaterialEditText a10;
    @InjectView(R.id.ysry_a11) MaterialEditText a11;
    @InjectView(R.id.ysry_a12) MaterialEditText a12;
    @InjectView(R.id.ysry_a13) MaterialEditText a13;
    @InjectView(R.id.ysry_a14) MaterialEditText a14;
    @InjectView(R.id.ysry_a15) MaterialEditText a15;
    @InjectView(R.id.ysry_a160) MaterialEditText a160;
    @InjectView(R.id.ysry_a161) MaterialEditText a161;
    @InjectView(R.id.ysry_a162) MaterialEditText a162;
    @InjectView(R.id.ysry_a18) MaterialEditText a18;
    @InjectView(R.id.ysry_a19) MaterialEditText a19;
    @InjectView(R.id.ysry_a20) MaterialEditText a20;
    @InjectView(R.id.ysry_a21) MaterialEditText a21;
    @InjectView(R.id.ysry_a22) MaterialEditText a22;
    @InjectView(R.id.ysry_a23) MaterialEditText a23;
    @InjectView(R.id.ysry_a24) MaterialSpinner a24;
    @InjectView(R.id.ysry_a30) MaterialEditText a30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ysryadd);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();
        ysryid=myAppVariable.getTxxxid();
//        spinner = (Spinner) findViewById(R.id.rzjk);
//        spinner1 = (Spinner) findViewById(R.id.rzsj);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);
        a2.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m6);
        a6.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m7);
        a7.setAdapter(adapter);

         adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m8);
        a8.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m9);
        a9.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m24);
        a24.setAdapter(adapter);

        if (isThemeLight()) {
            img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
            img2.setImageResource(R.drawable.ic_phone_grey600_24dp);
            img3.setImageResource(R.drawable.ic_phone_grey600_24dp);

        } else {
            img1.setImageResource(R.drawable.ic_phone_white_24dp);
            img2.setImageResource(R.drawable.ic_phone_white_24dp);
            img3.setImageResource(R.drawable.ic_phone_white_24dp);
        }
//
//        if (savedInstanceState == null) {
//         //   medtError.setError("Username or Password is incorrect.");
//        }
    }


//    /**
//     * 数据加载完之后消除Loding对话框
//     * */
//    private Handler myHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            progressDialog.dismiss(); //消除Loding对话框
////            showView(ysry);
////            rz.se;
//            super.handleMessage(msg);
//        }
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
        menu.getItem(2).setVisible(false);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setTitle("保存");

        if(tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统")){

            menu.getItem(1).setEnabled(true);

        }else{
            menu.getItem(1).setEnabled(false);

        }
        SupportMenuItem searchItem = (SupportMenuItem) menu
                .findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);

        SearchManager searchManager = (SearchManager)YsryAddActivity.this
                .getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(YsryAddActivity.this.getComponentName()));

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

//            if (a2.getSelectedItemPosition() == 0) {
//                Toast.makeText(this, "请选择部门！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a6.getSelectedItemPosition() == 0) {
//                Toast.makeText(this, "请选择性别！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a7.getSelectedItemPosition() == 0) {
//                Toast.makeText(this, "请选择供养关系！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a8.getSelectedItemPosition() == 0) {
//                Toast.makeText(this, "请选择类别！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a9.getSelectedItemPosition() == 0) {
//                Toast.makeText(this, "请选择户口性质！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            if (a12.length() != 18) {
//                Toast.makeText(this, "身份证号码错误！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a160.length() != 11 && !a160.getText().equals("")) {
//                Toast.makeText(this, "电话号码1错误！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (a3.length()== 0) {
//                Toast.makeText(this, "请输入个人编号！！！" , Toast.LENGTH_SHORT).show();
//                return false;
//            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("保存提交中  请稍后...");
            progressDialog.show();

                    try {

                        String rzjk = "";
                        String rzzb = "";
                        String rzdd = "";

//                        rzjk = spinner.getSelectedItem().toString();

                        rzzb = tusers.getUserName();
                        rzdd = tusers.getBranch().getName();
                        Map<String, String> map = new HashMap<String, String>();
//                        if(a2.getSelectedItemPosition()<5) {
//                            map.put("a2", "0" + a2.getSelectedItemPosition());
//                        }else{
//                            map.put("a2", a2.getSelectedItemPosition()+"");
//                        }
//                        map.put("a3", a3.getText().toString());
//                        map.put("a4",a4.getText().toString());
//                        map.put("a5", a5.getText().toString());
//                        map.put("a6",a6.getSelectedItem().toString());
//                        map.put("a7",a7.getSelectedItem().toString());
//                        map.put("a8",a8.getSelectedItem().toString());
//                        map.put("a9",a9.getSelectedItem().toString());
//                        map.put("a10", a10.getText().toString());
//                        map.put("a11",a11.getText().toString());
//                        map.put("a12", a12.getText().toString());
//                        map.put("a13", a13.getText().toString());
//                        map.put("a14",a14.getText().toString());
//                        map.put("a15", a15.getText().toString());
//                        map.put("a160", a160.getText().toString());
//                        map.put("a161",a161.getText().toString());
//                        map.put("a162", a162.getText().toString());
//                        map.put("a18", a18.getText().toString());
//                        map.put("a19",a19.getText().toString());
//                        map.put("a20", a20.getText().toString());
//                        map.put("a21",a21.getText().toString());
//                        map.put("a22", a22.getText().toString());
//                        map.put("a23", a23.getText().toString());
//                        map.put("a24",a24.getText().toString());
//                        map.put("a30", a30.getText().toString());
//

                       YsryService ysryService = new YsryService();

                    str=ysryService.updateYsryId(map);
//
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

//                Toast.makeText(this,"认证成功", Toast.LENGTH_SHORT).show();
//                return true;
            if(str) {
                new AlertDialog.Builder(this).setTitle("认证提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();

                        intent.setClass(YsryAddActivity.this, YsryAddActivity.class);
                        startActivity(intent);
                    }
                }).show();
            }else{
                new AlertDialog.Builder(this).setTitle("认证提交成功！").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                      return;
                    }
                }).show();

            }

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
//          showView(ysry);
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
                  intent.setClass(YsryAddActivity.this, YsryAddActivity.class);
                  startActivity(intent);
              }
          }).show();
      }
    }

}
