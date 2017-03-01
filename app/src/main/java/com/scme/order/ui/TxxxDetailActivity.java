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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.litao.android.lib.entity.PhotoEntry;
import com.scme.order.adpater.ChooseAdapter;
import com.scme.order.common.T;
import com.scme.order.holder.PhotoHolder;
import com.scme.order.interfaces.ItemClickListener;
import com.scme.order.model.Photo;
import com.scme.order.model.Photoimage;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.service.TxxxService;
import com.scme.order.util.MyAppVariable;
import com.twiceyuan.commonadapter.library.adapter.MultiTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class TxxxDetailActivity extends BaseActivity implements OnItemSelectedListener{
    Context context =TxxxDetailActivity.this;
    private ProgressDialog progressDialog;
    private Txxx txxx;
    private List<Txxx> txxxs;
    private int txxxid;
    private Spinner spinner;
    private Spinner spinner1;
    private Tusers tusers;
    private Handler testHandler;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private MyAppVariable myAppVariable;
    MultiTypeAdapter adapterlzx;
    private List<Photoimage> potolist;
    private ChooseAdapter mPhotoAdapter;
    private int type = 0;
//    private RecyclerView recyclerView;

//    private MyAdapter myadapter;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager StaggeredGridLayoutManager;

    private static final String[] m={"请选择认证方式","填表认证","本人认证","代认证","入户认证","视频认证"};
    private static final String[] m1={"请选择认证时间","201703","201704","201705","201706","201707"};

    @InjectView(R.id.img_1) ImageView img1;
    @InjectView(R.id.img_2) ImageView img2;
    @InjectView(R.id.img_3) ImageView img3;
    @InjectView(R.id.bmmc) TextView bmmc;
    @InjectView(R.id.grbh) MaterialEditText grbh;
    @InjectView(R.id.name) MaterialEditText name;
    @InjectView(R.id.sfzh) MaterialEditText sfzh;
    @InjectView(R.id.hkdz) MaterialEditText hkdz;
    @InjectView(R.id.czdz) MaterialEditText czdz;
    @InjectView(R.id.lxdh1) MaterialEditText lxdh1;
    @InjectView(R.id.lxdh2) MaterialEditText lxdh2;
    @InjectView(R.id.lxdh3) MaterialEditText lxdh3;
//    @InjectView(R.id.rzjk) MaterialEditText rz13jk;
    @InjectView(R.id.rzsj) MaterialEditText rz13sj;
    @InjectView(R.id.rzzb) MaterialEditText rz13zb;
    @InjectView(R.id.rzdd) MaterialEditText rz13dd;
   @InjectView(R.id.recyclerView) RecyclerView   recyclerView;
    @InjectView(R.id.recyclerViewlzx) RecyclerView   recyclerViewlzx;
    @InjectView(R.id.photo_image) ImageView   photoImageView;
    @InjectView(R.id.tv) TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txxxdetail);

        EventBus.getDefault().register(this);

        ButterKnife.inject(this);

        //获得绑定参数

        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();
        txxxid=myAppVariable.getTxxxid();
        spinner = (Spinner) findViewById(R.id.rzjk);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

        spinner.setAdapter(adapter);

        assert recyclerViewlzx != null;
        recyclerViewlzx.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterlzx = new MultiTypeAdapter(this);
        adapterlzx.registerViewType(Photo.class, PhotoHolder.class);
        recyclerViewlzx.setAdapter(adapterlzx);
        spinner.setOnItemSelectedListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
        progressDialog.show();
        if(Thread.State.NEW == mThread.getState()) {

           Intent intent = getIntent();
             if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearching(query);

               }else {
                 try{
                 //获取餐桌列表数据
                 TxxxService txxxService = new TxxxService();

                 txxx = txxxService.queryTxxxId(txxxid);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
             }
                mThread.start();

            }
        showView(txxx);
        setGridLayoutRecyclerView();
//        setLinstener();
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    private void setGridLayoutRecyclerView() {

        gridLayoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        // 设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);


//        mPhotoAdapter = new ChooseAdapter(this);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
//        mRecyclerView.setAdapter(mPhotoAdapter);
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, 2, true));
        // 创建数据集
        List<PhotoEntry> listData = new ArrayList<PhotoEntry>();
        for (int i = 0; i < 6; ++i) {
            PhotoEntry uBean = new PhotoEntry();

            switch (i){
                case 0:  uBean.setPath("退休人员头像"); break;
                case 1:  uBean.setPath("身份证正面"); break;
                case 2:  uBean.setPath("身份证反面"); break;
                case 3:  uBean.setPath("填表扫描图"); break;
                case 4:  uBean.setPath("复印件描图"); break;
                case 5:  uBean.setPath("视频认证截图"); break;
                default :  break;
            }

            listData.add(uBean);
        }
        // 使用RecyclerView提供的默认的动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 为Item添加分割线
        recyclerView.addItemDecoration(new ItemDecorationDivider(context,
                R.drawable.item_divider, LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new ItemDecorationDivider(context,
                R.drawable.item_divider, LinearLayoutManager.HORIZONTAL));
        // recyclerView.addItemDecoration(new
        // DividerItemDecoration(context,oritation));

        // 创建Adapter，并指定数据集
        mPhotoAdapter = new ChooseAdapter(context, listData);
        // 为Item具体实例点击3种事件
        mPhotoAdapter.setItemClickListener(new ItemClickListener() {

            @Override
            public void onItemSubViewClick(View view, int postion) {
                T.showShort(context, "亲，你点击了Image" + postion);
//                mPhotoAdapter = new ChooseAdapter(context);
                startActivity(new Intent(TxxxDetailActivity.this, PhotosActivity.class));

                EventBus.getDefault().postSticky(new EventEntry(mPhotoAdapter.getData(),EventEntry.SELECTED_PHOTOS_ID));
//                EventBus.getDefault().postSticky(new EventEntry(myadapter.g,EventEntry.SELECTED_PHOTOS_ID));
//                Intent in = new Intent();
//                in.putExtra( "text", tv.getText() );
//                in.setClassName( getApplicationContext(), "PhotosActivity.class" );
//                startActivityForResult( in, 0 );
            }

            @Override
            public void onItemLongClick(View view, int postion) {
                T.showShort(context, "亲，你长按了Item" + postion);

            }

            @Override
            public void onItemClick(View view, int postion) {
                T.showShort(context, "亲，你点击了Item" + postion);

            }
        });
        // 设置Adapter
        recyclerView.setAdapter(mPhotoAdapter);
    }
    protected void setLinstener() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                String s = "";
                if (type == 0) {
                    s = "可见Item数量：" + layoutManager.getChildCount() + "\n"
                            + "可见Item第一个Position："
                            + layoutManager.findFirstVisibleItemPosition()
                            + "\n" + "可见Item最后一个Position："
                            + layoutManager.findLastVisibleItemPosition();

                } else if (type == 1) {
                    s = "可见Item数量：" + gridLayoutManager.getChildCount() + "\n"
                            + "可见Item第一个Position："
                            + gridLayoutManager.findFirstVisibleItemPosition()
                            + "\n" + "可见Item最后一个Position："
                            + gridLayoutManager.findLastVisibleItemPosition();
                } else {
                    s = "可见Item数量："
                            + StaggeredGridLayoutManager.getChildCount();

                }
                tv.setText(s);
            }
        });

    }
    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                stateName = "Idle";
                break;

            case SCROLL_STATE_DRAGGING:
                stateName = "Dragging";
                break;

            case SCROLL_STATE_SETTLING:
                stateName = "Flinging";
                break;
        }

//        tv_state.setText("滑动状态：" + stateName);
    }

   private Thread mThread = new Thread() {
      public void run() {
        Log.d("TAG", "mThread run");
        Looper.prepare();

        testHandler = new Handler() {
    public void handleMessage(Message msg) {
        Log.d("TAG", "worker thread:" + Thread.currentThread().getName());
//					System.out.println("我的线程："+msg.what);

        switch (msg.what) {
        //handle message here
        case 1:

            if (isThemeLight()) {
                img1.setImageResource(R.drawable.ic_phone_grey600_24dp);
                img2.setImageResource(R.drawable.ic_phone_grey600_24dp);
                img3.setImageResource(R.drawable.ic_phone_grey600_24dp);

            } else {
                img1.setImageResource(R.drawable.ic_phone_white_24dp);
                img2.setImageResource(R.drawable.ic_phone_white_24dp);
                img3.setImageResource(R.drawable.ic_phone_white_24dp);
            }
//         showView(txxx);

        progressDialog.dismiss();
        //send message here

        }

        }
        };

        testHandler.sendEmptyMessage(1);
        Looper.loop();

        }

        };
    /**
     * 显示视图
     * @param txxx 职工的图片
     * @param txxx 职工的对象
     * */
    public void showView(Txxx txxx)
    {
//       bmmc.setText(txxx.getId()+"号");

        bmmc.setText(txxx.getBranch().getName());
        grbh.setText(txxx.getGrbh());
        name.setText(txxx.getName());
        sfzh.setText(txxx.getSfzh());
        hkdz.setText(txxx.getHkdz());
        czdz.setText(txxx.getCzdz());
        lxdh1.setText(txxx.getLxdh1());
        lxdh2.setText(txxx.getLxdh2());
        lxdh3.setText(txxx.getLxdh3());
//        rz13jk.setText(txxx.getRz13jk());
       rz13sj.setText(txxx.getRz14sj());
        rz13zb.setText(txxx.getRz14zb());
       rz13dd.setText(txxx.getRz14dd());
        if(txxx.getRz14jk().equals("")){
            spinner.setSelection(0);
        }else if(txxx.getRz14jk().equals("填表认证")){
            spinner.setSelection(1);
        }else if(txxx.getRz14jk().equals("本人认证")){
            spinner.setSelection(2);
        }else if(txxx.getRz14jk().equals("代认证")){
            spinner.setSelection(3);
        }
        Glide.with(TxxxDetailActivity.this)
                .load(new File("/storage/emulated/0/ImageSelector/CropImage/ImageSelector_20170226_214833.JPEG"))
                .into(photoImageView);

        potolist= new ArrayList<>();
        int i=0;
        if(!txxx.getPhoto().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getPhoto());
            photoimage.setSubject("退休人员头像");
            potolist.add(photoimage);
        }
        if(!txxx.getSfzzm().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getSfzzm());
            photoimage.setSubject("身份证正面");
            potolist.add(photoimage);
        }
        if(!txxx.getSfzfm().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getSfzfm());
            photoimage.setSubject("身份证反面");
            potolist.add(photoimage);
        }
        if(!txxx.getTbsm().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getTbsm());
            photoimage.setSubject("填表认证扫描图");
            potolist.add(photoimage);
        }
        if(!txxx.getSfzfyj().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getSfzfyj());
            photoimage.setSubject("身份证复印件扫描图");
            potolist.add(photoimage);
        }
        if(!txxx.getSbjt().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getSbjt());
            photoimage.setSubject("视频认证截图");
            potolist.add(photoimage);
        }

        for (int j = 0; j <potolist.size(); j++) {

            adapterlzx.add(mockPhoto(j));
        }

    }
    public Photo mockPhoto(int seed) {
        Photo photo = new Photo();
        photo.path=potolist.get(seed).getPath();
       photo.photoId =seed;
        photo.description =potolist.get(seed).getSubject();
        return photo;
    }
    /**
     * 数据加载完之后消除Loding对话框
     * */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss(); //消除Loding对话框
            showView(txxx);
//            rz.se;
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
        menu.getItem(2).setVisible(false);
//        Toast.makeText(TxxxDetailActivity.this, tusers.getPurview()+"与"+txxx.getRz13jk(), Toast.LENGTH_SHORT).show();

        if(tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统")){
            if(txxx.getRz14jk().equals("")) {
                menu.getItem(1).setEnabled(true);
            }else{  menu.getItem(1).setVisible(true);}
//            Toast.makeText(this,"0", Toast.LENGTH_SHORT).show();
        }else{
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).setVisible(false);
        }
        SupportMenuItem searchItem = (SupportMenuItem) menu
                .findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(searchItem);

        SearchManager searchManager = (SearchManager)TxxxDetailActivity.this
                .getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(TxxxDetailActivity.this.getComponentName()));

        searchItem
                .setSupportOnActionExpandListener(new MenuItemCompat.OnActionExpandListener() {

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
//                        Toast.makeText(TxxxDetailActivity.this, "扩张了", Toast.LENGTH_SHORT).show();
//                        System.out.println("扩张了");
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
//                        Toast.makeText(TxxxDetailActivity.this, "收缩了", Toast.LENGTH_SHORT).show();
//                        System.out.println("收缩了");
                        return true;
                    }
                });

        return super.onCreateOptionsMenu(menu);
//        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_txxxdetail_mainrz) {

            if (spinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "请选择认证方式！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (sfzh.length() != 18) {
                Toast.makeText(this, "身份证号码错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lxdh1.length() != 11 && !lxdh1.getText().equals("")) {
                Toast.makeText(this, "电话号码1错误！！！", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lxdh2.length() != 11 && lxdh2.length() != 0) {
                Toast.makeText(this, "电话号码2错误！！！" + lxdh2.length(), Toast.LENGTH_SHORT).show();
                return false;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("认证提交中  请稍后...");
            progressDialog.show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                            String name="";
                        String rzjk = "";
                        String rzzb = "";
                        String rzdd = "";
//
                        rzjk = spinner.getSelectedItem().toString();
//                            rzsj=spinner1.getSelectedItem().toString();
                        rzzb = tusers.getUserName();
                        rzdd = tusers.getBranch().getName();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", name.getText().toString());
                        map.put("sfzh", sfzh.getText().toString());
                        map.put("hkdz", hkdz.getText().toString());
                        map.put("czdz", czdz.getText().toString());
                        map.put("lxdh1", lxdh1.getText().toString());
                        map.put("lxdh2", lxdh2.getText().toString());
                        map.put("lxdh3", lxdh3.getText().toString());
                        map.put("rzjk", rzjk);
//
                        map.put("rzzb", rzzb);
                        map.put("rzdd", rzdd);

                       TxxxService txxxService = new TxxxService();

                        txxxService.updateTxxxId(map);
//
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    myHandler.sendMessage(myHandler.obtainMessage());
                }
            });
            t.start();
//                Toast.makeText(this,"认证成功", Toast.LENGTH_SHORT).show();
//                return true;
            new AlertDialog.Builder(this).setTitle("认证提交成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    //	intent.setClass(SelectEatsFoodsListActivity.this, MainActivity.class);
                    //	intent.setClass(EatListActivity.this, MainActivity.class);
                    intent.setClass(TxxxDetailActivity.this, TxxxDetailActivity.class);
                    startActivity(intent);
                }
            }).show();

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
//        bmmc.setText("你的血型是："+m[arg2]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void doSearching(String query) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", query);
        try {
            //获取餐桌列表数据
            TxxxService txxxService = new TxxxService();

//                        Toast.makeText(this,"aa", Toast.LENGTH_SHORT).show();
            txxxs = txxxService.queryTxxxName(map);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      if(txxxs!=null) {
//          myAppVariable.setTxxxs(txxxs);
          txxx=txxxs.get(0);

          showView(txxx);

//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
      } else{
          new AlertDialog.Builder(this).setTitle("查无此人！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int which) {
                  // TODO Auto-generated method stub
                  Intent intent = new Intent();

                  intent.setClass(TxxxDetailActivity.this, TxxxDetailActivity.class);
                  startActivity(intent);
              }
          }).show();
      }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photosMessageEvent(EventEntry entries){
        if (entries.id == EventEntry.RECEIVED_PHOTOS_ID) {
            mPhotoAdapter.reloadList(entries.photos);
//            mSelectedPhotos=entries.photos;
            T.showShort(context, "亲，返回来了" );
        }
    }
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( resultCode ) {
            case RESULT_OK :
//                System.out.println(data.getExtras().getString( "result" ));
//                tv1.setText( data.getExtras().getString( "result" ));
                T.showShort(context, "亲，返回来了" );
                break;
            default :
                break;
        }

    }


}
