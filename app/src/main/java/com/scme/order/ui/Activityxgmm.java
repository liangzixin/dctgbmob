package com.scme.order.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.MaterialEditText;
import android.widget.TextView;
import android.widget.Toast;


import com.scme.order.model.Tusers;
import com.scme.order.service.UserService;
import com.scme.order.util.MyAppVariable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activityxgmm extends BaseActivity {
    @InjectView(R.id.password2) MaterialEditText password2;
    @InjectView(R.id.password4) MaterialEditText password4;
    @InjectView(R.id.password6) MaterialEditText password6;
    private MyAppVariable myAppVariable;
    private Tusers tusers;
    private String pwd;
    private TextView mTextView;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xgmm);
        ButterKnife.inject(this);

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();

        pwd=tusers.getPwd();
//        password2.setText(pwd);
//        password4.addTextChangedListener(mTextWatcher);
    }
//    TextWatcher mTextWatcher = new TextWatcher() {
//        private CharSequence temp;
//        private int editStart ;
//        private int editEnd ;
//        @Override
//        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
//                                      int arg3) {
//            temp = s;
//            Toast.makeText(Activityxgmm.this, "你输入的字数太短，之前！", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int arg1, int arg2,
//                                  int arg3) {
//            password4.setText(s);
//            Toast.makeText(Activityxgmm.this,
//                    "你输入的字数太短，改变！", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            editStart = password4.getSelectionStart();
//            editEnd = password4.getSelectionEnd();
//            if (temp.length()< 6) {
//                Toast.makeText(Activityxgmm.this,
//                        "你输入的字数太短，要超5位！", Toast.LENGTH_SHORT)
//                        .show();
//                password4.setFocusable(true);
//                password4.setFocusableInTouchMode(true);
//                password4.requestFocus();
////                s.delete(editStart-1, editEnd);
////                int tempSelection = editStart;
////                password4.setText(s);
////                password4.setSelection(tempSelection);
//            }
//        }
//    };
    @OnClick(R.id.activytymmxg_btn_jt)
    protected void activitybtnjt() {

        if (!password2.getText().toString().equals(pwd)) {
            Toast.makeText(Activityxgmm.this,
                    "你输入的密码与原密码不一致！请重输！！！", Toast.LENGTH_SHORT)
                    .show();
            password2.setFocusable(true);
            password2.setFocusableInTouchMode(true);
            password2.requestFocus();
            return;
        }
        if (password4.length() < 6||password6.length()<6) {
            Toast.makeText(Activityxgmm.this,
                    "你输入的字数太短，要超5位！", Toast.LENGTH_SHORT)
                    .show();
            password4.setFocusable(true);
            password4.setFocusableInTouchMode(true);
            password4.requestFocus();
            return;
        }
        System.out.println("新密码1:"+password4.getText());
        System.out.println("新密码1:"+password6.getText());
        if (!(password4.getText().toString()).equals(password6.getText().toString())){
            Toast.makeText(Activityxgmm.this,
                    "你输入的新密码不一致！请重输！！！", Toast.LENGTH_SHORT)
                    .show();
            password4.setFocusable(true);
            password4.setFocusableInTouchMode(true);
            password4.requestFocus();
            return;
        }

        boolean xgmmtr=false;
        if(((password4.getText().toString()).equals(password6.getText().toString()))&&password4.length()>=6&&password2.getText().toString().equals(pwd))
        try {
        UserService userService=new UserService();
        tusers=userService.updateUserPwd(tusers.getId(),password6.getText().toString());
            xgmmtr=true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(xgmmtr){
          myAppVariable.setTusers(tusers);
            new AlertDialog.Builder(Activityxgmm.this).setTitle("密码修改成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent=new Intent();
                    intent.setClass(Activityxgmm.this,Activityxgmm.class);
                    startActivity(intent);
                }
            }).show();
            }else {
            new AlertDialog.Builder(Activityxgmm.this).setTitle("密码修改失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent=new Intent();
                    intent.setClass(Activityxgmm.this,Activityxgmm.class);
                    startActivity(intent);
                }
            }).show();
        }
//        System.out.println("按了确定按键：");

//        System.out.println("密码："+tusers.getPwd());
//        Intent intent = new Intent(this, LicensesActivity.class);
//        startActivity(intent);

    }
        @OnClick(R.id.activytymmxg_btn_can)
    protected void activitybtncan() {
            password2.setText("");
            password4.setText("");
            password6.setText("");
        Intent intent = new Intent(this,Activityxgmm.class);
        startActivity(intent);
    }
}
