package com.scme.order.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activityfwzc extends BaseActivity {

    private static final String TAG = Activityfwzc.class.getSimpleName();

    @InjectView(R.id.about_tel) TextView tvVersion;
    @InjectView(R.id.about_qq) TextView tvVersion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fwzc);
        ButterKnife.inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setElevation(0);
        }
        initVersion();
    }

    private void initVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            tvVersion.setText(pInfo.versionName);
             tvVersion.setText( "TEL: 18908805728");
            tvVersion1.setText( "QQ : 648194240");
            return;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Cannot get package info", e);
        }
    }

    @OnClick(R.id.about_btn_github)
    protected void githubClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dcgqxx.com/"));
        startActivity(intent);
    }


}