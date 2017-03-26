package com.scme.order.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.litao.android.lib.entity.PhotoEntry;
import com.scme.order.adpater.ChooseAdapter;
import com.scme.order.common.T;
import com.scme.order.holder.PhotoHolder;
import com.scme.order.interfaces.ItemClickListener;
import com.scme.order.model.Photo;
import com.scme.order.model.Photoimage;
import com.scme.order.model.Tusers;
import com.scme.order.model.Checkinout;
import com.scme.order.service.BaseService;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;
import com.twiceyuan.commonadapter.library.adapter.MultiTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class CheckinoutDetailActivity extends BaseActivity {
    Context context =CheckinoutDetailActivity.this;
    private ProgressDialog progressDialog;
    private Checkinout checkinout;
    private Tusers tusers;
    private MyAppVariable myAppVariable;
private HttpHandler<String> handler;
    private HttpUtils httpUtils= new HttpUtils();
    private    String url=null;
    private 	RequestParams params;
    private String bmmc,name,checktime;
    public static final int RESULT_CODE = 0;
    private String userid="";

    @InjectView(R.id.bmmc) TextView mbmmc;
    @InjectView(R.id.name) MaterialEditText mname;
    @InjectView(R.id.checktime) MaterialEditText mchecktime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinoutdetail);



        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();


 Intent intent = getIntent();
       bmmc= intent.getStringExtra("bmmc");
       name= intent.getStringExtra("name");
      checktime = intent.getStringExtra("checktime");
        mbmmc.setText(bmmc);
        mname.setText(name);
        mchecktime.setText(checktime);
        userid=intent.getStringExtra("userid");
    }
    private void mThreadmy() {

        handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                if (responseInfo.result != null) {
                    Toast.makeText(CheckinoutDetailActivity.this, "删除成功！！！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                progressDialog.dismiss();
                Toast.makeText(CheckinoutDetailActivity.this, "删除失败！！！", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

   /*
     * 显示视图
     * @param checkinout 职工的图片
     * @param checkinout 职工的对象
     * */
    public void showView(Checkinout checkinout)
    {

        mbmmc.setText(bmmc);
        mname.setText(name);
        mchecktime.setText(checktime);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
//        Toast.makeText(TxxxDetailActivity.this, tusers.getPurview()+"与"+checkinout.getRz13jk(), Toast.LENGTH_SHORT).show();

        if(tusers.getPurview().equals("系统")) {

                menu.getItem(2).setEnabled(true);
                menu.getItem(2).setVisible(true);

        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String url = HttpUtil.BASE_URL+"checkinout!deleteCheckinout.action";//获得详细页面的url      //分享用
        if (id == R.id.Menu_Delete) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("删除提交中  请稍后...");
            progressDialog.show();

                httpUtils = new HttpUtils();

                RequestParams params = new RequestParams();

            params.addQueryStringParameter("userid",userid);
            params.addQueryStringParameter("checkinouttime",checktime);




            // params.addQueryStringParameter("product.gsdz","东川");
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {

                    if (responseInfo.result != null) {
                        progressDialog.dismiss();
                        Toast.makeText(CheckinoutDetailActivity.this, "删除成功！！！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();

                        setResult(RESULT_CODE, intent);// 设置resultCode，onActivityResult()中能获取到
                        finish();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckinoutDetailActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
                }
            });

        }



        return super.onOptionsItemSelected(item);
    }




}
