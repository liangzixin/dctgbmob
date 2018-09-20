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
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.scme.order.model.Diningcard;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Tusers;
import com.scme.order.service.BranchService;
import com.scme.order.service.DiningcardService;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.InputFilterMinMax;
import com.scme.order.util.MyAppVariable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class EatTopupAddActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Qingjia qingjia;
    private Handler testHandler;
    private int qingjiaid;
    private Spinner spinner;
    private Tusers user;
    private List listbmmz;
    private List<Tusers> listuser0;
    private List listuser;
    private int branchid;
    private boolean str=false;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;

    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
//    private MaterialSpinner citySpinner = null;
//    private Spinner countySpinner = null;    //县级（区、县、县级市


    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;
    @InjectView(R.id.eattopupnumber)   EditText eattopupnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eattopup_add);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();
        if(Thread.State.NEW == mThread.getState()) {
            try {
                    BranchService branchService=new BranchService();
//
                    listbmmz=branchService.QueryBranch();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        mThread.start();
        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        user=myAppVariable.getTusers();

        setSpinner();
    }

    private Thread mThread = new Thread() {
        public void run() {
//            Log.d("TAG", "mThread run");
            Looper.prepare();
            testHandler = new Handler() {
                public void handleMessage(Message msg) {
//                    Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
//                    System.out.println("我的线程："+msg.what);
                    switch (msg.what) {
                        //handle message here
                        case 1:

//                        etStartTime.setOnTouchListener(EatTopupAddActivity.this);
//                            etEndTime.setOnTouchListener(EatTopupAddActivity.this);
//
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
        menu.getItem(2).setVisible(false);
		menu.getItem(1).setEnabled(true);
		menu.getItem(1).setTitle(R.string.save);
        menu.getItem(1).setVisible(true);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {



    //       CharSequence  aa= eattopupnumber
//            if (eattopupnumber.getText().toString().equals("")) {
           //     Toast.makeText(this, "错误！请正确填写充值金额！！", Toast.LENGTH_SHORT).show();
//                return false;
//            }
              addeattopup();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 添加请假
     *
     * */
    public void addeattopup() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("充值提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        try {
            Map<String, String> map = new HashMap<String, String>();
         int   userid= listuser0.get(citySpinner.getSelectedItemPosition()).getId();
            map.put("diningcard.userid",userid+"");
            map.put("diningcard.topupamount", eattopupnumber.getText().toString());
            map.put("diningcard.operator",user.getName());
            DiningcardService  diningcardService = new DiningcardService();
            str = diningcardService.AddEattopup(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(str) {
            new AlertDialog.Builder(this).setTitle("充值成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(EatTopupAddActivity.this,EatTopupActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "充值失败！！！", Toast.LENGTH_SHORT).show();
        }

    }


//

    /*
   * 设置下拉框
   */
    private void setSpinner() {
        provinceSpinner = ( MaterialSpinner) findViewById(R.id.spin_province);
       citySpinner = ( MaterialSpinner) findViewById(R.id.spin_city);
//        countySpinner = (Spinner) findViewById(R.id.spin_county);
        eattopupnumber.setFilters(new InputFilter[]{ new InputFilterMinMax("-500", "500")});
        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(EatTopupAddActivity.this,
                android.R.layout.simple_spinner_item,listbmmz);
//
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setSelection(user.getBranchid()-1);  //设置默认选中项，此处为默认选中第4个值
        try {
            UserService userService=new UserService();
//
            listuser=userService.QueryUserBranchId(user.getBranchid());
//
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cityAdapter = new ArrayAdapter<String>(EatTopupAddActivity.this,
                android.R.layout.simple_spinner_item,listuser);
        citySpinner.setAdapter(cityAdapter);

        setSpinnerItemSelectedByValue(citySpinner,user.getName());  //默认选中第0个




        //省级下拉框监听
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //position为当前省级选中的值的序号
                branchid=position+1;

                try {
                    UserService userService=new UserService();
//
                    listuser0=userService.QueryUserBranchIdOther(branchid);
//
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(listuser0.size()>0) {
                    listuser=new ArrayList<String>();
                    for (int k = 0; k < listuser0.size(); k++) {
                        listuser.add(listuser0.get(k).getName());
                    }
                }
                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        EatTopupAddActivity.this, android.R.layout.simple_spinner_item,  listuser);
//
                // 设置二级下拉列表的选项内容适配器

                citySpinner.setAdapter(cityAdapter);

                setSpinnerItemSelectedByValue(citySpinner, user.getName());
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
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

}
