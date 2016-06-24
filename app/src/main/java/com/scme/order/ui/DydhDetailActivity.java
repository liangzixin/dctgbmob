package com.scme.order.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.Spinner;

import com.scme.order.model.Dydh;

import com.scme.order.model.Tusers;
import com.scme.order.service.DydhService;

import com.scme.order.util.MyAppVariable;

import butterknife.ButterKnife;
import butterknife.InjectView;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class DydhDetailActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Dydh dydh;
    private Tusers user0;
    private int dydhid;


    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;


   @InjectView(R.id.Dydh_Name) MaterialEditText dydhname;
    @InjectView(R.id.Dydh_Branch) MaterialEditText dydhbranch;
      @InjectView(R.id.Dydh_Time) MaterialEditText dydhtime;
      @InjectView(R.id.Dydh_Mobile) MaterialEditText dydhmobile;
    @InjectView(R.id.Dydh_Sfzh) MaterialEditText dydhsfzh;

    @InjectView(R.id.img_1) ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dydh_detailed);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        dydhid=myAppVariable.getTxxxid();
        user0=myAppVariable.getTusers();
        if(user0.getJob()>8)  dydhsfzh.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取餐桌列表数据
                    DydhService dydhService = new DydhService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
                    dydh = dydhService.queryUseDydhId(dydhid);
                    System.out.println("姓名:" + dydh.getName());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                myHandler.sendMessage(myHandler.obtainMessage());
            }
            });
            t.start();

//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);

    }




    /**
     * 显示视图
 *
     * */
    public void showView(Dydh dydh)
    {

        dydhname.setText(dydh.getName());
        dydhbranch.setText(dydh.getZbmz().getZbmz());
        dydhtime.setText(dydh.getRdsj());
        dydhmobile.setText(dydh.getLxdh());
        dydhsfzh.setText(dydh.getXfh());

    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框

            showView(dydh);
            super.handleMessage(msg);
        }
    };


}
