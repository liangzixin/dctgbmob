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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scme.order.model.Branch;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.service.BranchService;
import com.scme.order.service.ChuchaiService;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.TxxxService;
import com.scme.order.service.UserService;
import com.scme.order.util.MyAppVariable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class UserDetailActivity extends BaseActivity  implements View.OnTouchListener {
    private ProgressDialog progressDialog;
    private Tusers user,user0;
    private Handler testHandler;
    private int userid;
    private Spinner spinner;
    private List listbmmz;
    private  boolean str = false;
    private Map map;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    private static final String[] m={"请选择职务","主任","副书记","副主任","科长","副科长","站书记","站长","副站长","职工"};
    private static final String[] m1={"请选择性别","男","女"};
    private static final String[] m2={"是否单身","否","是"};
    private static final String[] m3={"请选择权限","系统","社保","服务","只读","司机"};

   @InjectView(R.id.User_Name) MaterialEditText username;
    @InjectView(R.id.User_Birthday) MaterialEditText userbirthday;
    @InjectView(R.id.User_Email) MaterialEditText useremail;
    @InjectView(R.id.User_Address) MaterialEditText useradress;
    @InjectView(R.id.User_Tel) MaterialEditText usertel;
    @InjectView(R.id.User_Preview) MaterialSpinner userpreview;
    @InjectView(R.id.User_branch) MaterialSpinner userbranch;
    @InjectView(R.id.User_Post) MaterialSpinner userpost;
    @InjectView(R.id.User_Sex) MaterialSpinner usersex;
    @InjectView(R.id.User_Single)  MaterialSpinner usersingle;
    @InjectView(R.id.User_Sfzh) MaterialEditText usersfzh;
    @InjectView(R.id.User_id) MaterialEditText usersid;
    @InjectView(R.id.User_pwd) MaterialEditText userspwd;
    @InjectView(R.id.img_1) ImageView img1;
    @InjectView(R.id.normal1) LinearLayout llayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detailed);

        ButterKnife.inject(this);



        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
       user0=myAppVariable.getTusers();
        userid=myAppVariable.getTxxxid();
        if(user0.getJob()>8) usersfzh.setVisibility(View.INVISIBLE);
        if(user0.getPurview().equals("系统"))  llayout.setVisibility(View.VISIBLE);
       progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();
        if(Thread.State.NEW == mThread.getState()) {
            try {
                //获取餐桌列表数据
                BranchService branchService = new BranchService();
//
                listbmmz = branchService.QueryBranch();
                UserService userService = new UserService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
                user = userService.queryUseUserId(userid);
//                    System.out.println("姓名:" + user.getName());
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

                            showView(user);
                            setSpinner();
                            progressDialog.dismiss();
                            //send message here
                        case 2:
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
        if((user.getName().equals(user0.getName()))) {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).setTitle(R.string.repare);
            menu.getItem(1).setVisible(true);

        }
        if(user0.getPurview().equals("系统")) {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).setTitle(R.string.repare);
            menu.getItem(1).setVisible(true);
//            menu.getItem(2).setEnabled(true);
//            menu.getItem(2).setTitle(R.string.add);
//            menu.getItem(2).setVisible(true);
            menu.getItem(3).setEnabled(true);
            menu.getItem(3).setTitle(R.string.menu_delete);
            menu.getItem(3).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {
            if (userpost.getSelectedItemPosition()==0) {
                Toast.makeText(this, "请选择职务！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (usersex.getSelectedItemPosition()==0) {
                Toast.makeText(this, "请选择性别！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
//            if (userpreview.getSelectedItem().toString().equals("系统")) {
//                Toast.makeText(this, "权限选择错误！！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }



                        map = new HashMap<String, String>();
                        map.put("id", userid+ "");
                        map.put("name", username.getText().toString());
                        map.put("branchid",(userbranch.getSelectedItemPosition()+1)+"");
                        map.put("jop",(userpost.getSelectedItemPosition())+"");
                        map.put("sex", usersex.getSelectedItem().toString());
                        map.put("tel",usertel.getText().toString());
                        map.put("address",useradress.getText().toString());
                        map.put("workersfz",usersfzh.getText().toString());
                        map.put("single",(usersingle.getSelectedItemPosition()-1)+"");
                        map.put("preview", userpreview.getSelectedItem().toString());
                        map.put("newer","false");
                        map.put("userbirday",userbirthday.getText().toString());

                     dosave(map);


        }else if(item.toString().equals("删　除")) {

            new AlertDialog.Builder(this).setTitle("删除职工记录")
                    .setMessage("真要删除职工:"+user.getName()+"吗???").setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dodelete(userid);
                }
            }).show();



        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示视图
 *
     * */
    public void showView(Tusers user)
    {

        username.setText(user.getName());
        userbirthday.setText(user.getBirday());
        useremail.setText(user.getEmail());
        useradress.setText(user.getAddress());
        usertel.setText(user.getTel());
        usersfzh.setText(user.getWorkersfz());
        usersid.setText(user.getId()+"");
        userspwd.setText(user.getPwd());
        userbirthday.setOnTouchListener(this);


    }
    /**
     * 保存
     *
     * */
    public void dosave(Map map) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("保存提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        UserService userService = new UserService();


        try {
            str = userService.UpdateUser(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(str) {
            new AlertDialog.Builder(this).setTitle("修改提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(UserDetailActivity.this, UserListActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "修改提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 保存
     *
     * */
    public void dodelete(int userid) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("删除提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        UserService userService = new UserService();


        try {
            str = userService.DeleteUserId(userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(str) {
            new AlertDialog.Builder(this).setTitle("删除提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(UserDetailActivity.this, UserListActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "删除提交错误！！！", Toast.LENGTH_SHORT).show();
        }

    }
public String IsSingle(String status) {
        String str = "";
        if (status.equals("0"))
        str = "否";
        else
        str = "是";
        return str;
        }

    public String IsJob(int job) {
        String str = "";
       switch (job) {
           case 1:
               str = "主任";
               break;
           case 2:
               str = "副书记";
               break;
           case 3:
               str = "副主任";
               break;
           case 4:
               str = "科长";
               break;
           case 5:
               str = "副科长";
               break;
           case 6:
               str = "站书记";
               break;
           case 7:
               str = "站长";
               break;
           case 8:
               str = "副站长";
               break;
           case 9:
               str = "职工";
               break;
       }
        return str;
    }
    /*
 * 设置下拉框
 */
    private void setSpinner() {
        ArrayAdapter  adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listbmmz);
        userbranch.setAdapter(adapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
        setSpinnerItemSelectedByValue(userbranch, user.getBranch().getName());
        ArrayAdapter  jopadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m);
        userpost.setAdapter(jopadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
        setSpinnerItemSelectedByValue(userpost, IsJob(user.getJob()));
        ArrayAdapter  sexadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m1);
        usersex.setAdapter(sexadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
        setSpinnerItemSelectedByValue(usersex, user.getSex());
        ArrayAdapter  singleadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m2);
        usersingle.setAdapter(singleadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
        setSpinnerItemSelectedByValue(usersingle, IsSingle(user.getSingle()));
        ArrayAdapter  previewadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m3);
        userpreview.setAdapter(previewadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
        setSpinnerItemSelectedByValue(userpreview, user.getPurview());
    }
    /**
     * 根据值, 设置spinner默认选中:
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
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

            if (v.getId() == R.id.User_Birthday) {
                final int inType = userbirthday.getInputType();
                userbirthday.setInputType(InputType.TYPE_NULL);
                userbirthday.onTouchEvent(event);
                userbirthday.setInputType(inType);
                userbirthday.setSelection(userbirthday.getText().length());

                builder.setTitle("选取出生");
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

                        userbirthday.setText(sb);

                        dialog.cancel();
                    }
                });
            }
            Dialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }
}
