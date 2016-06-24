package com.scme.order.adpater;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.scme.order.model.Tfoods;
import com.scme.order.ui.R;
import com.scme.order.util.Cart;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class MySimpleAdapter extends SimpleAdapter{

    int tabId=Cart.tabid;
    private List<? extends Map<String, ?>> foods;
    public MySimpleAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        this.foods=data;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub  
        final int mPosition = position;
        convertView = super.getView(position, convertView, parent);
        //获取菜的Map对象
        HashMap<String,String> foodMap=(HashMap<String, String>) foods.get(position);
        Button buttonAdd = (Button) convertView
                .findViewById(R.id.btnAddFoods);// id为你自定义布局中按钮的id
        buttonAdd .setTag(foodMap);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub  
                HashMap<String,String> btnFoodMap=(HashMap<String, String>) v.getTag();
                System.out.println(btnFoodMap.get("foodsName"));

                mHandler.obtainMessage(R.id.btnAddFoods
                        , mPosition, 0)
                        .sendToTarget();
            }
        });
//        Button buttonDelete = (Button) convertView  
//                .findViewById(R.id.xxx);  
//        buttonDelete.setOnClickListener(new View.OnClickListener() {  

//            @Override  
//            public void onClick(View v) {  
//                // TODO Auto-generated method stub  
//                mHandler.obtainMessage(BUTTON_XXX, mPosition, 0)  
//                        .sendToTarget();  
//            }  
//        });  
        return convertView;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub  
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.btnAddFoods:
                    //处理Button点击事件
                    break;
//            case BUTTON_XXX:  
//                mList.remove(msg.arg1);  
//                notifyDataSetChanged();  
//               mShowInfo.setText("你删除了第" + (msg.arg1 + 1) + "行");  
//                break;  
            }
        }
    };

}