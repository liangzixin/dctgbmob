package com.scme.order.ui;

import android.app.AlertDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.scme.order.model.Branch;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.service.BranchService;
import com.scme.order.service.QingjiaService;
import com.scme.order.service.TxxxService;
import com.scme.order.service.UserService;
import com.scme.order.util.MyAppVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class UserAddlActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Tusers user,user0;
    private Handler testHandler;
    private int userid;
    private Spinner spinner;
    private List listbmmz;
    private     boolean str=false;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    private static final String[] m={"请选择职务","主任","副书记","副主任","科长","副科长","站书记","站长","副站长","职工"};
    private static final String[] m1={"请选择性别","男","女"};
    private static final String[] m2={"是否单身","否","是"};
    private static final String[] m3={"请选择权限","系统","社保","服务","只读","司机"};

    @InjectView(R.id.User_Name) MaterialEditText username;

    @InjectView(R.id.User_Email) MaterialEditText useremail;
    @InjectView(R.id.User_Address) MaterialEditText useradress;
    @InjectView(R.id.User_Tel) MaterialEditText usertel;
    @InjectView(R.id.User_Preview) MaterialSpinner userpreview;
    @InjectView(R.id.User_branch) MaterialSpinner userbranch;
    @InjectView(R.id.User_Post) MaterialSpinner userpost;
    @InjectView(R.id.User_Sex) MaterialSpinner usersex;
    @InjectView(R.id.User_Single)  MaterialSpinner usersingle;
    @InjectView(R.id.User_Sfzh) MaterialEditText usersfzh;
    @InjectView(R.id.img_1) ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        user0=myAppVariable.getTusers();
        userid=myAppVariable.getTxxxid();
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
                user=new Tusers();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
//                user = userService.queryUseUserId(userid);
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
//
//                            showView(user);
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
        if(user0.getPurview().equals("系统")) {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).setTitle(R.string.save);
            menu.getItem(1).setVisible(true);

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
            if (userpreview.getSelectedItemPosition()==0) {
                Toast.makeText(this, "请选择权限！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (usersingle.getSelectedItemPosition()==0) {
                Toast.makeText(this, "是否单身选择错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(usertel.length()!=11){
                Toast.makeText(this, "手机号输入错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            Map<String,String> map = new HashMap<String,String >();
            map = new HashMap<String, String>();

            map.put("name", username.getText().toString());
            map.put("branchid",(userbranch.getSelectedItemPosition()+1)+"");
            map.put("jop",(userpost.getSelectedItemPosition())+"");
            map.put("sex", usersex.getSelectedItem().toString());
            map.put("tel",usertel.getText().toString());
            map.put("address",useradress.getText().toString());
            map.put("workersfz",usersfzh.getText().toString());
            map.put("single",(usersingle.getSelectedItemPosition()-1)+"");
            map.put("preview",userpreview.getSelectedItem().toString());
            map.put("newer","true");

           dosave(map);
        }

        return super.onOptionsItemSelected(item);
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
        UserService userService= new UserService();


        try {
           str = userService.UpdateUser(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
//

      if(str) {
          new AlertDialog.Builder(this).setTitle("添加职工提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int which) {
                  // TODO Auto-generated method stub

                  startActivity(new Intent(UserAddlActivity.this, UserListActivity.class));

              }
          }).show();
      }else{
          Toast.makeText(this, "添加职工错误！！！", Toast.LENGTH_SHORT).show();
      }

    }

    public String IsSingle(String status) {
        String str = "";
        if (status.equals("否"))
            str = "0";
        else
            str = "1";
        return str;
    }
//        public String IsSex(String status) {
//        String str = "";
//        if (status.equals("0"))
//            str = "男";
//        else
//            str = "女";
//        return str;
//    }
//    public String IsJob(int job) {
//        String str = "";
//        switch (job) {
//            case 1:
//                str = "书记";
//                break;
//            case 2:
//                str = "副书记";
//                break;
//            case 3:
//                str = "副主任";
//                break;
//            case 4:
//                str = "科长";
//                break;
//            case 5:
//                str = "副科长";
//                break;
//            case 6:
//                str = "站书记";
//                break;
//            case 7:
//                str = "站长";
//                break;
//            case 8:
//                str = "副站长";
//                break;
//            case 9:
//                str = "职工";
//                break;
//        }
//        return str;
//    }
    /*
 * 设置下拉框
 */
    private void setSpinner() {
        ArrayAdapter  adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listbmmz);
        userbranch.setAdapter(adapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
//        setSpinnerItemSelectedByValue(userbranch, user.getBranch().getName());
        ArrayAdapter  jopadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m);
        userpost.setAdapter(jopadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
//        setSpinnerItemSelectedByValue(userpost, IsJob(user.getJob()));
        ArrayAdapter  sexadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m1);
        usersex.setAdapter(sexadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
//        setSpinnerItemSelectedByValue(usersex, user.getSex());
        ArrayAdapter  singleadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m2);
        usersingle.setAdapter(singleadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
//        setSpinnerItemSelectedByValue(usersingle, IsSingle(user.getSingle()));
        ArrayAdapter  previewadapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,m3);
        userpreview.setAdapter(previewadapter);
//        img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
//        setSpinnerItemSelectedByValue(userpreview, user.getPurview());
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
