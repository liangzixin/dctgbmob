package com.scme.order.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.scme.order.model.Tusers;
import com.scme.order.util.MyAppVariable;
import com.scme.order.view.PopMenu;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ItemActivity extends Activity
{
    private MyAppVariable myAppVariable;
    static String purview = "";
    private PopMenu popMenu;

    private Context context =ItemActivity.this;
    @InjectView(R.id.row_one_item_one) TextView textView;
//    @InjectView(R.id.btn_title1)
//    Button button1;
//    @InjectView(R.id.btn_title2)
//    Button button2;
//    @InjectView(R.id.btn_title3)
//    Button button3;
//    @InjectView(R.id.btn_title4)
//    Button button4;
//    @InjectView(R.id.btn_title5)
//    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view4);
        ButterKnife.inject(this);
//        setContentView(getIntent().getIntExtra("contentId", R.layout.activity_item));
//
//        setTitle(getIntent().getStringExtra("title"));

        myAppVariable = (MyAppVariable) getApplication(); //获得自定义的应用程序MyAppVariable
        Tusers users0 = myAppVariable.getTusers();
        purview = users0.getPurview();
        textView.setText("昆明市东川区企业退休人员管理办公室移动OA  " + users0.getUserName());
//
//        setTitle(getIntent().getStringExtra("title"));
//        button1.setOnClickListener(onViewClick);
//        button2.setOnClickListener(onViewClick);
//        button3.setOnClickListener(onViewClick);
//        button4.setOnClickListener(onViewClick);
//        button5.setOnClickListener(onViewClick);
    }
//    // 按钮监听器
//    View.OnClickListener onViewClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            if (v.getId() == R.id.btn_title1) {
//                System.out.println("下拉菜单点击首页下拉:");
////				popMenu.dismiss();
////                btn_app_sy();
//
//            } else  if (v.getId() == R.id.btn_title_back) {
////
//            } else if (v.getId() == R.id.btn_title2) { // 弹出式菜单
////				System.out.println("下拉菜单点击");
//                String[] str2 = new String[]{getString(R.string.zhk_qj), getString(R.string.zhk_yz), getString(R.string.zhk_bx)};
//                String str = "0";
//                popMenu = new PopMenu(context, str);
//                popMenu.addItems(str2);
//                popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        switch (0)
//                        {
//                            case R.id.zhk_qj:
//
//                                break;
//                            case R.id.zhk_yz:
//
//                                break;
//                            case R.id.zhk_bx:
//
//                                break;
//
//                        }
//
//                    }
//                });
//                popMenu.showAsDropDown(v);
//            } else if (v.getId() == R.id.btn_title3) { // 弹出式菜单
////				System.out.println("下拉菜单点击");
//                String[] str3 = new String[]{getString(R.string.sbk_tx), getString(R.string.sbk_ys)};
//                String str = "1";
//                popMenu = new PopMenu(context, str);
//                popMenu.addItems(str3);
//                popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        switch (position)
//                        {
//                            case 0:
//
//                                startActivity(new Intent(ItemActivity.this, TxxxListFyActivity.class));
//
//                                break;
//                            case 1:
//
//                                break;
//                        }
//                    }
//                });
//
//                popMenu.showAsDropDown(v);
//            } else if (v.getId() == R.id.btn_title4) { // 弹出式菜单
////				System.out.println("下拉菜单点击");
//                String[] str4 = new String[]{getString(R.string.fwk_dk), getString(R.string.fwk_cx), getString(R.string.fwk_hj)};
//                String str = "1";
//                popMenu = new PopMenu(context, str);
//                popMenu.addItems(str4);
//                popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        switch (position)
//                        {
//
//                            case 0:
//
//                                startActivity(new	Intent(ItemActivity.this, EatTotalPersionActivity.class));
//
//                                break;
//                            case 1:
//                                startActivity(new	Intent(ItemActivity.this,  EatTotalActivity.class));
//
//                                break;
//                            case 2:
//                                startActivity(new	Intent(ItemActivity.this,  EatTotalActivity.class));
//
//                                break;
//
//
//                        }
//                    }
//                });
//                popMenu.showAsDropDown(v);
//            } else if (v.getId() == R.id.btn_title5) { // 弹出式菜单
////				System.out.println("下拉菜单点击");
//                String[] str5 = new String[]{getString(R.string.xgmm), getString(R.string.pbsm), getString(R.string.jszc)};
//                String str = "1";
//                popMenu = new PopMenu(context, str);
//                popMenu.addItems(str5);
//                popMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        switch (position)
//                        {
//
//                            case 0:
//                                startActivity(new Intent(ItemActivity.this, Activityxgmm.class));
//
//                                break;
//                            case 1:
//                                startActivity(new Intent(ItemActivity.this, AboutActivity.class));
//
//                                break;
//                            case 2:
//                                startActivity(new Intent(ItemActivity.this, Activityfwzc.class));
//
//                                break;
//                            case 3:
//
//
//                                break;
//                        }
//
//                    }
//                });
//                popMenu.showAsDropDown(v);
//            }
//
//        }
//    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
