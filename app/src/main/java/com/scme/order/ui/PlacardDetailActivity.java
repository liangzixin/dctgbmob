package com.scme.order.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.Toast;


import com.scme.order.model.Placard;
import com.scme.order.model.Tusers;

import com.scme.order.service.PlacardService;
import com.scme.order.service.QingjiaService;
import com.scme.order.util.MyAppVariable;


import butterknife.ButterKnife;
import butterknife.InjectView;


public class PlacardDetailActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Placard placard;
    private Tusers user0;
    private int placardid;
   private Handler testHandler;
   private boolean str=false;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;


   @InjectView(R.id.Placard_Name) MaterialEditText placardname;
    @InjectView(R.id.Placard_ddate) MaterialEditText placarddate;
    @InjectView(R.id.Placard_content) MaterialEditText content;
    @InjectView(R.id.Placard_subject) MaterialEditText subject;
    @InjectView(R.id.Placard_lxdh) MaterialEditText placardlxdh;

    @InjectView(R.id.img_1) ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placard_detailed);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
       user0=myAppVariable.getTusers();
        placardid=myAppVariable.getTxxxid();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();

        if(Thread.State.NEW == mThread.getState()) {
                try {
                    //获取餐桌列表数据

                    PlacardService placardService = new PlacardService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
                    placard = placardService.queryUsePlacardId(placardid);
                    System.out.println("姓名:" + placard.getPerson());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            mThread.start();
        }

    }
    private Thread mThread = new Thread() {
        public void run() {
            Log.d("TAG", "mThread run");
            Looper.prepare();
            testHandler = new Handler() {
                public void handleMessage(Message msg) {
                    Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
                    System.out.println("我的线程："+msg.what);
                    switch (msg.what) {
                        //handle message here
                        case 1:
                            showView(placard);
                            progressDialog.dismiss();
                    }

                }
            };
            testHandler.sendEmptyMessage(1);
            Looper.loop();

        }

    };
    /*
  创建菜单项
   */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(4).setVisible(false);
        if(user0.getPurview().equals("系统")) {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).setTitle(R.string.menu_delete);
            menu.getItem(2).setVisible(true);

        }

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.Menu_Delete) {
            new AlertDialog.Builder(this).setTitle("删除短信记录")
                    .setMessage("真要删除职工:"+placard.getPerson()+"的当前短信记录吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dodelete(placardid);
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 删除
     *
     * */
    public void dodelete(int placardid) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("删除提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        try {

            PlacardService placardService = new PlacardService();

            str=placardService.DeletePlacardId( placardid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(str) {
            new AlertDialog.Builder(this).setTitle("删除提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(PlacardDetailActivity.this,PlacardListActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "删除提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 显示视图
 *
     * */
    public void showView(Placard placard)
    {

        placardname.setText(placard.getPerson());
        placarddate.setText(placard.getDDate().toString());
        placardlxdh.setText(placard.getLxdh());
        content.setText("        "+placard.getContent());
        subject.setText(placard.getSubject());


    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

//            System.out.println("地址:" + placard.getAddress());
//            System.out.println("电子信息:" + placard.getEmail());
            showView(placard);

            progressDialog.dismiss(); //消除Loding对话框
            super.handleMessage(msg);
        }
    };

}
