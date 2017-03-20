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

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.scme.order.model.Checkinout;
import com.scme.order.model.Departments;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Tusers;
import com.scme.order.service.BaseService;
import com.scme.order.service.BranchService;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.UserService;
import com.scme.order.util.GetDate;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class CheckinoutAddActivity extends BaseActivity implements View.OnTouchListener {
    private ProgressDialog progressDialog;
    private Checkinout checkinout;
    private Handler testHandler;
    private int qingjiaid;
    private Spinner spinner;
    private Tusers user;
    private List listbmmz;
    private List listuser;
    private int branchid;
    private boolean str=false;
    private HttpHandler<String> handler;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    private GetDate getDate=new GetDate();
    private static final String[] m={"请选择打卡类型","早上上班卡","早上下班卡","下午上班卡","下午下班卡"};
    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private 	RequestParams params;
    private String url;



    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;


    @InjectView(R.id.Checkinout_Time1) MaterialEditText  etStartTime;

  @InjectView(R.id.spin_type1)  MaterialSpinner checkinouttype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinout_add);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();

        getDepartments();




        //获得绑定参数
        ArrayAdapter  adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m);
       checkinouttype.setAdapter(adapter);
        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        user=myAppVariable.getTusers();
        setSpinner();
    }

    private void mThreadmy() {
//	 HttpHandler<String> handler;
        HttpUtils httpUtils= new HttpUtils();
        // 不缓存，设置缓存0秒。
        httpUtils.configCurrentHttpCacheExpiry(0*1000);
        handler= httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                if (responseInfo.result != null) {
                    progressDialog.dismiss();
//                    if(lzx) {
                        JSONObject myobject = null;
                        String listArray = null;
                        try {

                            myobject = new JSONObject(responseInfo.result);

                            listArray = myobject.getString("departmentslist");
                            listbmmz = BaseService.getGson().fromJson(listArray, new TypeToken<List>() {
                            }.getType());
                            System.out.println(listbmmz.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }






                }
//            }

            @Override
            public void onFailure(HttpException e, String s) {
                progressDialog.dismiss();
                Toast.makeText(CheckinoutAddActivity.this, "数据加载失败！！！", Toast.LENGTH_SHORT).show();
            }
        });

    }
/*
获取部门名称
 */
private void getDepartments() {
    url = HttpUtil.BASE_URL + "departments!queryDepartments.action";
    params = new RequestParams();

    mThreadmy();
}
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
//
//            System.out.println("开始----" + etStartTime.getText());
//            System.out.println("结束----" + etEndTime.getText());
            if ((etStartTime.getText().toString()).equals("请选择请假开始时间:")) {
                Toast.makeText(this, "请选择请假开始时间！！！", Toast.LENGTH_SHORT).show();
                return false;
            }


            if (checkinouttype.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "错误！请选择请假类型", Toast.LENGTH_SHORT).show();
                return false;
            }

              addqingjia();

        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * 添加请假
     *
     * */
    public void addqingjia() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("添加提交中  请稍后...");
        progressDialog.show();
        testHandler.sendEmptyMessage(2);
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name1", citySpinner.getSelectedItem().toString());
            map.put("time1", etStartTime.getText().toString());

//
            QingjiaService qingjiaService = new QingjiaService();

            str = qingjiaService.AddQingjia(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(str) {
            new AlertDialog.Builder(this).setTitle("请假添加成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    startActivity(new Intent(CheckinoutAddActivity.this,QingjiaListActivity.class));

                }
            }).show();
        }else{
            Toast.makeText(this, "请假添加失败！！！", Toast.LENGTH_SHORT).show();
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
    public String IsState(String state) {
        String str = "";
        if (state.equals("1"))
            str = "是";
        else
            str = "否";
        return str;
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



                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.Qingjia_Time2) {


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

                        double lg2=0;


//
                       dialog.cancel();
                    }
                });
            }

            Dialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }

    /*
   * 设置下拉框
   */
    private void setSpinner() {
        provinceSpinner = ( MaterialSpinner) findViewById(R.id.spin_province);
       citySpinner = ( MaterialSpinner) findViewById(R.id.spin_city);
//        countySpinner = (Spinner) findViewById(R.id.spin_county);

        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(CheckinoutAddActivity.this,
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
        cityAdapter = new ArrayAdapter<String>(CheckinoutAddActivity.this,
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
                    listuser=userService.QueryUserBranchId(branchid);
//
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        CheckinoutAddActivity.this, android.R.layout.simple_spinner_item,  listuser);
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
