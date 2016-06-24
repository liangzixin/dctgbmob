package com.scme.order.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MaterialEditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scme.order.model.Branch;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Tusers;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.TxxxService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class QingjiaDetailActivity extends BaseActivity implements View.OnTouchListener {
    private ProgressDialog progressDialog;
    private Qingjia qingjia;
    private Tusers user;
    private Handler testHandler;
    private int qingjiaid;
    private Spinner spinner;
   private  boolean str=false;
   private   Map<String, String> map;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    private GetDate getDate = new GetDate();
    private static final String[] m={"请选择请假事由","事  假","病  假","公休假","探亲假","婚丧假","生育假","哺乳假","其  它"};


    @InjectView(R.id.Qingjia_Name) MaterialEditText qingjianame;
    @InjectView(R.id.Qingjia_Branch) MaterialEditText qingjiabranch;
    @InjectView(R.id.Qingjia_Time1) MaterialEditText etStartTime;
    @InjectView(R.id.Qingjia_Time2) MaterialEditText etEndTime;
    @InjectView(R.id.Qingjia_Longtime) MaterialEditText qingjialongtime;
    @InjectView(R.id.qingjia_leader) MaterialEditText qingjialender;
    @InjectView(R.id.qingjia_leader1) MaterialEditText qingjialender1;
    @InjectView(R.id.qingjia_leader2) MaterialEditText qingjialender2;
    @InjectView(R.id.Qingjia_Content) MaterialEditText qingjiacontent;
    @InjectView(R.id.qingjia_state) MaterialEditText qingjiastate;
//    @InjectView(R.id.Menu_Replay) MenuItem menureplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qingjia_detailrepare);

        ButterKnife.inject(this);
//        menureplay.setEnabled(true);

        //获得绑定参数

        myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
        qingjiaid = myAppVariable.getTxxxid();
        user = myAppVariable.getTusers();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();

        if(Thread.State.NEW == mThread.getState()) {
                try {
                    //获取餐桌列表数据
                    QingjiaService qingjiaService = new QingjiaService();

                    qingjia = qingjiaService.queryUseQingjiaId(qingjiaid);
//                    System.out.println("姓名:" + qingjia.getName());
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
                            showView(qingjia);
                            etStartTime.setOnTouchListener(QingjiaDetailActivity.this);
                            etEndTime.setOnTouchListener(QingjiaDetailActivity.this);
                            break;
                        case 2:
                            break;

                    }
                    progressDialog.dismiss();
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
       if((user.getName().equals(qingjia.getName1())&&qingjia.getState()==0)||user.getPurview().equals("系统")) {
           menu.getItem(1).setEnabled(true);
           menu.getItem(1).setTitle(R.string.repare);
           menu.getItem(1).setVisible(true);
       }
        if((user.getName().equals(qingjia.getName1())&&qingjia.getState()==0)||user.getPurview().equals("系统")) {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).setVisible(true);
        }
        boolean branchleader=false;
        boolean branchleader1=false;
        boolean branchleader10=false;
        boolean branchleader2=false;
       double days=getDate.dateDiff(qingjia.getTime1(),qingjia.getTime2());


        if(user.getName().equals("梁晓明")&&(qingjia.getBmbh()==1||qingjia.getBmbh()==5)||qingjia.getBmbh()==8){
            branchleader1=true;
        }else if(user.getName().equals("魏  蓉")&&qingjia.getBmbh()==6){
            branchleader1=true;
        }else if(user.getName().equals("秦莉")&&(qingjia.getBmbh()==10)){
            branchleader1=true;
        }if(user.getName().equals("郑云琨")&&(qingjia.getBmbh()==2||qingjia.getBmbh()==4||qingjia.getBmbh()==7||qingjia.getBmbh()==11)){
            branchleader1=true;
        }if(user.getName().equals("蔡晓明")&&(qingjia.getBmbh()==3||qingjia.getBmbh()==9)){
            branchleader1=true;
        }
        if(days>0&&qingjia.getState()==0&&(user.getBranchid()==qingjia.getBmbh())&&qingjia.getLeader().equals("")&&(user.getJob()>3&&user.getJob()<9)){
            branchleader=true;
        }
        if(days>2&&qingjia.getState()==0&&branchleader1&&qingjia.getLeader1().equals("")&&!qingjia.getLeader().equals("")){
            branchleader10=true;
        }
        if((days>=3)&&qingjia.getState()==0&&qingjia.getLeader2().equals("")&&!qingjia.getLeader().equals("")&&!qingjia.getLeader1().equals("")){
            branchleader2=true;
        }

        if(branchleader&&((user.getJob()>4||user.getId()==7))){
            menu.getItem(3).setTitle("部门签字");
            menu.getItem(3).setVisible(true);
        }else if (branchleader10){
            menu.getItem(3).setTitle("分管签字");
            menu.getItem(3).setVisible(true);
        }else if(branchleader2&&user.getJob()==1){
            menu.getItem(3).setTitle("主任签字");
            menu.getItem(3).setVisible(true);
        }
//        if (user.getJob() < 6){
//            System.out.println("职务:"+user.getJob());44
//            menu.getItem(3).setEnabled(true);
//    }else{
//            System.out.println("职务:"+user.getJob());
//            menu.getItem(3).setEnabled(false);
//    }
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {
            if (etStartTime.getText().equals("")) {
                Toast.makeText(this, "请选择请假开始时间！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (etEndTime.getText().equals("")) {
                Toast.makeText(this, "请选择请假结束时间！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!getDate.isDateBefore(etStartTime.getText().toString(), etEndTime.getText().toString())) {
                Toast.makeText(this, "错误！结束时间在开始时间之前", Toast.LENGTH_SHORT).show();
                return false;
            }

            new AlertDialog.Builder(this).setTitle("修改请假记录")
                    .setMessage("真要修改职工:"+qingjia.getName1()+"的当前请假记录吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dorepare(qingjiaid);
                }
            }).show();


        } else if (id == R.id.Menu_Delete) {

            new AlertDialog.Builder(this).setTitle("删除请假记录")
                    .setMessage("真要删除职工:"+qingjia.getName1()+"的当前请假记录吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dodelete(qingjiaid);
                }
            }).show();
        } else if (id == R.id.Menu_Leader) {
            new AlertDialog.Builder(this).setTitle("签请假记录")
                    .setMessage("真要签职工:"+qingjia.getName1()+"的当前请假记录吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dowrite(qingjiaid);
                }
            }).show();


        }

            return super.onOptionsItemSelected(item);

    }

    /**
     * 删除
     *
     * */
    public void dodelete(int qingjiaid) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("删除提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        try {
            qingjiaid=myAppVariable.getTxxxid();
            QingjiaService qingjiaService = new QingjiaService();

            str=qingjiaService.DeleteQingjiaId(qingjiaid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(str) {
            new AlertDialog.Builder(this).setTitle("删除提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(QingjiaDetailActivity.this,QingjiaListActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "删除提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 修改
     *
     * */
    public void dorepare(int qingjiaid) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("修改提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        try {
            map = new HashMap<String, String>();
            map.put("id", myAppVariable.getTxxxid() + "");
            map.put("time1", etStartTime.getText().toString());
            map.put("time2", etEndTime.getText().toString());
            map.put("content", qingjiacontent.getText().toString());
            map.put("djr", user.getName());
            QingjiaService qingjiaService = new QingjiaService();

            str = qingjiaService.UpdateQingjia(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(str) {
            new AlertDialog.Builder(this).setTitle("修改提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(QingjiaDetailActivity.this,QingjiaDetailActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "修改提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 领导签字
     *
     * */
    public void dowrite(int qingjiaid) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("签字提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
            try {
                qingjiaid=myAppVariable.getTxxxid();
                QingjiaService qingjiaService = new QingjiaService();

                map = new HashMap<String, String>();
                map.put("id", myAppVariable.getTxxxid() + "");
                map.put("userid",user.getId()+"");
                map.put("job",user.getJob()+"");
                map.put("name",user.getName());
                str=qingjiaService.UpdateStateQingjia(map);
//
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        if(str) {
            new AlertDialog.Builder(this).setTitle("签字提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(QingjiaDetailActivity.this,QingjiaDetailActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "签字提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.date_time_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.Qingjia_Time1) {
                final int inType = etStartTime.getInputType();
                etStartTime.setInputType(InputType.TYPE_NULL);
                etStartTime.onTouchEvent(event);
                etStartTime.setInputType(inType);
                etStartTime.setSelection(etStartTime.getText().length());

                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append(" ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute()).append(":00");

                        etStartTime.setText(sb);


                        double lg2=0;

                        if(etStartTime.getText().toString()!=null&&etEndTime.getText().toString()!=null)
                        {
                            lg2=getDate.dateDiff(qingjia.getTime1(), qingjia.getTime2());
                        }

                        qingjialongtime.setText(lg2+" 天");
                        etEndTime.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.Qingjia_Time2) {
                int inType = etEndTime.getInputType();
                etEndTime.setInputType(InputType.TYPE_NULL);
                etEndTime.onTouchEvent(event);
                etEndTime.setInputType(inType);
                etEndTime.setSelection(etEndTime.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append(" ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute()).append(":00");
                        etEndTime.setText(sb);

                        double lg2=0;

                        if(etStartTime.getText().toString()!=null&&etEndTime.getText().toString()!=null)
                        {
                            lg2=getDate.dateDiff(qingjia.getTime1(), qingjia.getTime2());
                        }

                        qingjialongtime.setText(lg2+" 天");
                        dialog.cancel();
                    }
                });
            }

            Dialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }
    /**
     * 显示视图
 *
     * */
    public void showView(Qingjia qingjia)
    {

//
        qingjianame.setText(qingjia.getName1());
        qingjiabranch.setText(qingjia.getBranch().getName());


       etStartTime.setText(qingjia.getTime1());
        etEndTime.setText(qingjia.getTime2());

        qingjiastate.setText(IsSate(qingjia.getState()));
        qingjialender.setText(qingjia.getLeader());
        qingjialender1.setText(qingjia.getLeader1());
        qingjialender2.setText(qingjia.getLeader2());
        qingjiacontent.setText(qingjia.getContent());

        double lg2=0;

        if(qingjia.getTime1()!=null&&qingjia.getTime2()!=null)
        {
            lg2=getDate.dateDiff(qingjia.getTime1(), qingjia.getTime2());
        }
        qingjialongtime.setText(lg2+" 天");


    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框
//
            showView(qingjia);
            etStartTime.setOnTouchListener(QingjiaDetailActivity.this);
            etEndTime.setOnTouchListener(QingjiaDetailActivity.this);
            super.handleMessage(msg);
        }
    };
public String IsSate(int state) {
        String str = "";
        if (state==0) {
            str = "否";
        } else {
            str = "是";
        }
        return str;
        }


}
