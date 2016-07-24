package com.scme.order.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scme.order.model.Branch;
import com.scme.order.model.Chuchai;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
import com.scme.order.service.ChuchaiService;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.MyAppVariable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class ChuchaiDetailActivity extends BaseActivity implements View.OnTouchListener {
    private ProgressDialog progressDialog;
    private Chuchai chuchai;
    private Tusers user;
    private List listbmmz;
    private List listuser;
    private int chuchaiid;
    private  int branchid;
    private  String mytitel="";
    private  String mytitel1="";
   // private Spinner spinner;
   private  int str=0;
    private boolean str1=false;
   private   Map<String, String> map;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    private GetDate getDate = new GetDate();
    private Handler testHandler;
    private int state0=0;
    private static final String[] m={"请选择出差地点","昆明","汤丹","殡仪馆","其它"};
    private static final String[] m1={"请选择司机","翁家彬","吕春红","王安华","李守应"};
    private Spinner spinbranch = null;  //省级（省、直辖市）
    private Spinner spinname = null;     //地级市
    ArrayAdapter<String>  branchAdapter = null;  //省级适配器
    ArrayAdapter<String>  nameAdapter = null;  //省级适配器

    @InjectView(R.id.Chuchai_Time1) MaterialEditText etStartTime;
    @InjectView(R.id.Chuchai_Time2) MaterialEditText etEndTime;
    @InjectView(R.id.Chuchai_Longtime) MaterialEditText chuchailongtime;
    @InjectView(R.id.chuchai_leader0) MaterialEditText chuchaileader0;
    @InjectView(R.id.chuchai_leader) MaterialEditText chuchaileader;
    @InjectView(R.id.chuchai_leader1) MaterialEditText chuchaileader1;
    @InjectView(R.id.chuchai_leader2) MaterialEditText chuchaileader2;
    @InjectView(R.id.Chuchai_Content) MaterialEditText chuchaicontent;
    @InjectView(R.id.chuchai_state) MaterialEditText chuchaistate;
    @InjectView(R.id.chuchai_bmmz) MaterialEditText chuchaibmmz;
    @InjectView(R.id.chuchai_name) MaterialEditText chuchainame;
    @InjectView(R.id.spin_type1) MaterialSpinner chuchaid;
    @InjectView(R.id.spin_driver) MaterialSpinner chuchaidriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuchai_detailrepare);

        ButterKnife.inject(this);
//        menureplay.setEnabled(true);

        //获得绑定参数

        myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
        chuchaiid = myAppVariable.getTxxxid();
        user = myAppVariable.getTusers();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();
       /*
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取餐桌列表数据
                    ChuchaiService chuchaiService = new ChuchaiService();
                    BranchService branchService=new BranchService();
                    chuchai = chuchaiService.queryUseChuchaiId(chuchaiid);
                    listbmmz=branchService.QueryBranch();
//                    System.out.println("姓名:" + chuchai.getName());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                myHandler.sendMessage(myHandler.obtainMessage());
            }
        });
        t.start();
     */
        if(Thread.State.NEW == mThread.getState()) {
            try {
                //获取餐桌列表数据
                ChuchaiService chuchaiService = new ChuchaiService();
                chuchai = chuchaiService.queryUseChuchaiId(chuchaiid);
                BranchService branchService=new BranchService();

                listbmmz=branchService.QueryBranch();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mThread.start();
        }

        ArrayAdapter  adapte0= new ArrayAdapter<String>(ChuchaiDetailActivity.this,
                android.R.layout.simple_spinner_item,m);
        chuchaid.setAdapter(adapte0);
        ArrayAdapter  adapte1= new ArrayAdapter<String>(ChuchaiDetailActivity.this,
                android.R.layout.simple_spinner_item,m1);
        chuchaidriver.setAdapter(adapte1);
        if(user.getBranch().getId()==1&&(user.getJob()==4||user.getJob()==5)) {
            chuchaidriver.setEnabled(true);
        }else{
            chuchaidriver.setEnabled(false);
        }

        etStartTime.setOnTouchListener(ChuchaiDetailActivity.this);
        etEndTime.setOnTouchListener(ChuchaiDetailActivity.this);
    }

    private Thread mThread = new Thread() {
        public void run() {
            Log.d("TAG", "mThread run");
            Looper.prepare();
            testHandler = new Handler() {

                public void handleMessage(Message msg) {
                    Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
                    System.out.println("我的线程："+msg.what);
                    progressDialog.dismiss();
                    switch (msg.what) {
                        //handle message here
                        case 1:

                            showView(chuchai);

                            break;
                        case 2:
                            break;

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
        menu.getItem(3).setVisible(false);
        boolean branchleader1=false;

        boolean branchleader2=false;
       if(user.getPurview().equals("系统")) {
           menu.getItem(1).setEnabled(true);
           menu.getItem(1).setVisible(true);
           menu.getItem(1).setTitle(R.string.repare);
           branchleader1=true;
       }
        if(((user.getName().equals(chuchai.getName1()))&&chuchai.getState()==0)||user.getPurview().equals("系统")) {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).setVisible(true);
        }

        if(chuchai.getState()==0&&chuchai.getLeader2().equals("")&&!chuchai.getLeader1().equals("")){
            branchleader2=true;
        }
      if(chuchai.getChuchaid().equals("殡仪馆")&&!chuchai.getLeader().equals("")){
          menu.getItem(3).setTitle("派车签字");
          menu.getItem(3).setVisible(true);
          state0=4;
      }else {
          if ((user.getId() == 5 || user.getId() == 7) && chuchai.getLeader0().equals("") && !chuchai.getLeader().equals("") && chuchai.getState() == 0 && !chuchai.getLeader1().equals("") && !chuchai.getLeader2().equals("")) {
              menu.getItem(3).setTitle("派车签字");
              menu.getItem(3).setVisible(true);
              state0 = 4;
          } else if (chuchai.getLeader1().equals("") && chuchai.getBranch().getLeader() == user.getId() && chuchai.getLeader0().equals("") && !chuchai.getLeader().equals("")) {
              menu.getItem(3).setTitle("分管签字");
              menu.getItem(3).setVisible(true);
              state0 = 2;
          } else if (branchleader2 && user.getJob() == 1 && !chuchai.getLeader().equals("") && chuchai.getLeader0().equals("")) {
              menu.getItem(3).setTitle("主任签字");
              menu.getItem(3).setVisible(true);
              state0 = 3;
          }
      }
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {
            if (etStartTime.getText().toString().equals("")) {
                Toast.makeText(this, "请选择出差开始时间！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (chuchaicontent.getText().toString().equals("")) {
                Toast.makeText(this, "请填写出差事由！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if ( chuchaid.getSelectedItem().toString().equals("请选择出差地点")) {
                Toast.makeText(this, "请选择出差地点！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!getDate.isDateBefore(etStartTime.getText().toString(), etEndTime.getText().toString())) {
                Toast.makeText(this, "错误！结束时间在开始时间之前", Toast.LENGTH_SHORT).show();
                return false;
            }
            if((user.getId()==5||user.getId()==7)&&chuchaistate.getText().toString().equals("是")) {
                if (chuchaidriver.getSelectedItem().toString().equals("请选择司机")) {
                    Toast.makeText(this, "请选择司机！！！", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            progressDialog = new ProgressDialog(ChuchaiDetailActivity.this);
            progressDialog.setMessage("修改记录提交中  请稍后...");
            progressDialog.show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        map = new HashMap<String, String>();
                        map.put("id", myAppVariable.getTxxxid() + "");
                        map.put("time1", etStartTime.getText().toString());
                        map.put("time2", etEndTime.getText().toString());
                        map.put("chuarea", chuchaicontent.getText().toString());
                        map.put("djr", user.getName());
                        map.put("chuchaid",chuchaid.getSelectedItem().toString());
                        if(chuchaidriver.getSelectedItemPosition()==0) {
                            map.put("driver","");
                        } else{
                            map.put("driver", chuchaidriver.getSelectedItem().toString());
                        }
                  //      map.put("name1",spinname.getSelectedItem().toString());
                  //      map.put("departmentid",(spinbranch.getSelectedItemPosition()+1)+"");

                        map.put("leader0",chuchaileader0.getText().toString());
                        map.put("leader",chuchaileader.getText().toString());
                        map.put("leader1",chuchaileader1.getText().toString());
                        map.put("leader2",chuchaileader2.getText().toString());
                        ChuchaiService chuchaiService = new ChuchaiService();

                        str1 = chuchaiService.RepareChuchai(map);
                 if(str1) {
                        mytitel = "出差修改成功";
                    }else{    mytitel = "出差记录失败";  }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    myHandlermy.sendMessage(myHandlermy.obtainMessage());
                }

            });
            t.start();


        } else if (id == R.id.Menu_Delete) {

            new AlertDialog.Builder(this).setTitle("删除记录")
                    .setMessage("真要删除记录啊???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    progressDialog = new ProgressDialog(ChuchaiDetailActivity.this);
                    progressDialog.setMessage("删除记录提交中  请稍后...");
                    progressDialog.show();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                    try {
                        chuchaiid=myAppVariable.getTxxxid();
                        ChuchaiService chuchaiService = new ChuchaiService();

                        str1=chuchaiService.DeleteChuchaiId(chuchaiid);
                        if(str1) {
                            mytitel = "删除记录成功";
                        }else{    mytitel = "删除记录失败";  }
//
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                            myHandlermy.sendMessage(myHandlermy.obtainMessage());
                        }

                    });
                    t.start();
                }
            }).show();

        } else if (id == R.id.Menu_Leader) {
            if(user.getId()==5||user.getId()==7) {
                if (chuchaidriver.getSelectedItem().toString().equals("请选择司机")) {
                    Toast.makeText(this, "请选择司机！！！", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("签字提交中  请稍后...");
            progressDialog.show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        chuchaiid=myAppVariable.getTxxxid();
                        ChuchaiService chuchaiService = new ChuchaiService();


                        System.out.println("司机:"+chuchaidriver.getSelectedItem().toString());
                        map = new HashMap<String, String>();
                        map.put("id", myAppVariable.getTxxxid() + "");
                        map.put("userid",user.getId()+"");
                        map.put("job",user.getJob()+"");
                        map.put("name",user.getName());
                        map.put("state0",state0+"");
                        map.put("chuarea", chuchaicontent.getText().toString());
                        if(user.getId()==5||user.getId()==7) map.put("driver", chuchaidriver.getSelectedItem().toString());

                       str1=chuchaiService.UpdateChuchai(map);
                        if(str1) {
                            mytitel = "签字提交成功";
                        }else{    mytitel = "签字提交失败";  }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    myHandlermy.sendMessage(myHandlermy.obtainMessage());
                }

            });
            t.start();

        }

            return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.date_time_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.Chuchai_Time1) {
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


                        if(etStartTime.getText().toString()!=null&&etEndTime.getText().toString()!=null)
                        {
                            chuchailongtime.setText(getDate.dateDiffChuchai(etStartTime.getText().toString(),etEndTime.getText().toString())+" 天");
                        }
                        etEndTime.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.Chuchai_Time2) {
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


                        if(etStartTime.getText().toString()!=null&&etEndTime.getText().toString()!=null)
                        {
                            chuchailongtime.setText(getDate.dateDiffChuchai(etStartTime.getText().toString(),etEndTime.getText().toString())+" 天");
                        }
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
    public void showView(Chuchai chuchai)
    {

       etStartTime.setText(chuchai.getTime1());
        etEndTime.setText(chuchai.getTime2());

        chuchailongtime.setText(chuchai.getCountd()+" 天");
        chuchaistate.setText(IsSate(chuchai.getState()));
        chuchaileader0.setText(chuchai.getLeader0());
        chuchaileader.setText(chuchai.getLeader());
        chuchaileader1.setText(chuchai.getLeader1());
        chuchaileader2.setText(chuchai.getLeader2());
        chuchaicontent.setText(chuchai.getChuarea());
        chuchaibmmz.setText(chuchai.getBranch().getName());
        chuchainame.setText(chuchai.getName1());
     setSpinnerItemSelectedByValue(chuchaid, chuchai.getChuchaid());
     setSpinnerItemSelectedByValue(chuchaidriver, chuchai.getDriver());
    //  setSpinner();
    }

    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {


            ArrayAdapter  adapte0= new ArrayAdapter<String>(ChuchaiDetailActivity.this,
                    android.R.layout.simple_spinner_item,m);
            chuchaid.setAdapter(adapte0);
            ArrayAdapter  adapte1= new ArrayAdapter<String>(ChuchaiDetailActivity.this,
                    android.R.layout.simple_spinner_item,m1);
            chuchaidriver.setAdapter(adapte1);
            if(user.getBranch().getId()==1&&(user.getJob()==4||user.getJob()==5)) {
                chuchaidriver.setEnabled(true);
            }else{
                chuchaidriver.setEnabled(false);
            }
            showView(chuchai);
            etStartTime.setOnTouchListener(ChuchaiDetailActivity.this);
            etEndTime.setOnTouchListener(ChuchaiDetailActivity.this);
            progressDialog.dismiss(); //消除Loding对话框


            super.handleMessage(msg);
        }
    };
      private Handler myHandlermy= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框

            if(str1) {

                new AlertDialog.Builder(ChuchaiDetailActivity.this).setTitle(mytitel).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        startActivity(new Intent(ChuchaiDetailActivity.this, ChuchaiListActivity.class));

                    }
                }).show();
            }else{
                Toast.makeText(ChuchaiDetailActivity.this,mytitel, Toast.LENGTH_SHORT).show();
            }
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
    /*
     * 设置下拉框
     */
    private void setSpinner() {
      //  spinbranch= ( MaterialSpinner) findViewById(R.id.spin_branch);
      //  spinname = ( MaterialSpinner) findViewById(R.id.spin_name);
//        countySpinner = (Spinner) findViewById(R.id.spin_county);

        //绑定适配器和值
      branchAdapter = new ArrayAdapter<String>(ChuchaiDetailActivity.this,
                android.R.layout.simple_spinner_item,listbmmz);
//
       spinbranch.setAdapter(branchAdapter);
     setSpinnerItemSelectedByValue(spinbranch,chuchai.getBranch().getName());
        try {
            UserService userService=new UserService();
//
            listuser=userService.QueryUserBranchId(chuchai.getDepartmentid());
//
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       nameAdapter = new ArrayAdapter<String>(ChuchaiDetailActivity.this,
              android.R.layout.simple_spinner_item,listuser);
        spinname.setAdapter(nameAdapter);

      setSpinnerItemSelectedByValue(spinname,chuchai.getName1());  //默认选中第0个




        //省级下拉框监听
        spinbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position为当前省级选中的值的序号
                branchid=position+1;
                try {
                    UserService userService=new UserService();
//
                    listuser=userService.QueryUserBranchId(branchid);
//
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //将地级适配器的值改变为city[position]中的值
                nameAdapter = new ArrayAdapter<String>(
                        ChuchaiDetailActivity.this, android.R.layout.simple_spinner_item,  listuser);
//
                // 设置二级下拉列表的选项内容适配器

                spinname.setAdapter(nameAdapter);

          //      setSpinnerItemSelectedByValue(spinname,chuchai.getName1());
//                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

    }
    /**
     * 根据值, 设置spinner默认选中:
     * @param spinner
     * @param value
     */
    public  void setSpinnerItemSelectedByValue(Spinner spinner,String value){
        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= apsAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(apsAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
//                System.out.println("默认:"+i);
                break;
            }
        }
    }
}
