package com.scme.order.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MaterialEditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.litao.android.lib.entity.PhotoEntry;
import com.scme.order.adpater.ChooseAdapter;
import com.scme.order.adpater.MyPanelListAdapter;
import com.scme.order.adpater.MyRzxxListAdapter;
import com.scme.order.card.HeadlineBodyCard;
import com.scme.order.common.T;
import com.scme.order.holder.PhotoHolder;
import com.scme.order.interfaces.ItemClickListener;
import com.scme.order.model.Photo;
import com.scme.order.model.Photoimage;
import com.scme.order.model.Rzxx;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.service.BaseService;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.MyAppVariable;
import com.scme.order.view.XListView;
import com.twiceyuan.commonadapter.library.adapter.MultiTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sysu.zyb.panellistlibrary.PanelListLayout;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import com.scme.order.view.XListView;
import com.scme.order.view.XListView.IXListViewListener;
//
//import  android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class TxxxDetailActivity extends BaseActivity implements IXListViewListener, OnItemSelectedListener{
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
    private int photoposition=0;
    private List<PhotoEntry> mSelectedPhotos=new ArrayList<PhotoEntry>();
    private String filepath;
    private List<String> imgstmppath=new ArrayList<String>();
    private List<File> list=new ArrayList<>();
//    private HttpUtils httpUtils;
//    private HttpHandler<String> handler;
    private  Boolean otherquery=false;
//    private RecyclerView recyclerView;
private HttpHandler<String> handler;
    private HttpUtils httpUtils= new HttpUtils();
    private    String url=null;
    private 	RequestParams params;

//    private MyAdapter myadapter;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager StaggeredGridLayoutManager;

    private static final String[] m={"请选择认证方式","填表认证","本人认证","代认证","入户认证","视频认证"};
    private static final String[] m1={"请选择认证时间","201803","201804","201805","201806","201807"};

    private PanelListLayout pl_rootrzxx;
    private ListView lv_contentrzxx;

    private MyRzxxListAdapter adapterrzxx;

   private Set<Map<String,String>> listrzxx0 =new HashSet<>();
    private List<Map<String,String>> listrzxx = new ArrayList<>();

    private MyRzxxAdapter myRzxxAdapter;


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
   @InjectView(R.id.lvrzxxs)  XListView   mListRzxxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txxxdetail);

        EventBus.getDefault().register(this);

        ButterKnife.inject(this);

        //获得绑定参数
//        initView();
//        initContentDataList();
        myAppVariable=(MyAppVariable)getApplication(); //获得自定义的应用程序MyAppVariable
        tusers=myAppVariable.getTusers();
        txxxid=myAppVariable.getTxxxid();

        spinner = (Spinner) findViewById(R.id.rzjk);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

     //  mListRzxxView.setPullLoadEnable(false);


        spinner.setAdapter(adapter);

        assert recyclerViewlzx != null;
        recyclerViewlzx.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterlzx = new MultiTypeAdapter(this);
        adapterlzx.registerViewType(Photo.class, PhotoHolder.class);
     //   recyclerViewlzx.setAdapter(adapterlzx);
        spinner.setOnItemSelectedListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据加载中  请稍后...");
  //      progressDialog.show();
//        Intent intent = getIntent();

//            doSearching(txxxid);
       txxx=myAppVariable.getTxxx();
        listrzxx0=txxx.getRzxx();
        showView(txxx);
        myRzxxAdapter = new MyRzxxAdapter(listrzxx,1);
         mListRzxxView.setAdapter(myRzxxAdapter);
        mListRzxxView.setPullLoadEnable(true);
    //    setGridLayoutRecyclerView();

//        adapterrzxx = new MyRzxxListAdapter(TxxxDetailActivity.this, pl_rootrzxx, lv_contentrzxx, R.layout.item_contentrzxx,listrzxx);
//        adapterrzxx.setInitPosition(2);
//        adapterrzxx.setSwipeRefreshEnabled(true);
//        adapterrzxx.setRowDataList(getRowDataList());
//        adapterrzxx.setTitle("序号");
  //pl_rootrzxx.setAdapter(adapterrzxx);
       // progressDialog.dismiss();
    }
    private void initView() {

        pl_rootrzxx = (PanelListLayout) findViewById(R.id.id_pl_rootrzxx);
        lv_contentrzxx = (ListView) findViewById(R.id.id_lv_contentrzxx);
        //设置listView为多选模式，长按自动触发
     //   lv_contentrzxx.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());
    }  /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i <=5; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("1", "第" + i + "行第一个");
            data.put("2", "第" + i + "行第二个");
            data.put("3", "第" + i + "行第三个");
            data.put("4", "第" + i + "行第四个");
            data.put("5", "第" + i + "行第五个");

            listrzxx.add(data);
        }
    }

    private void mThreadmy() {

        handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                if (responseInfo.result != null) {
              //      progressDialog.dismiss();
                    JSONObject myobject =null;
                    String listArray=null;

                        txxx= BaseService.getGson().fromJson(responseInfo.result.toString(), new TypeToken<Txxx>() {}.getType());




                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
             //   progressDialog.dismiss();
                Toast.makeText(TxxxDetailActivity.this, "数据加载失败！！！", Toast.LENGTH_SHORT).show();
            }
        });

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



        // 创建数据集
        List<PhotoEntry> listData = new ArrayList<PhotoEntry>();
        for (int i = 0; i < 6; ++i) {
            PhotoEntry uBean = new PhotoEntry();
                       uBean.setImageId(i);


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
                photoposition=postion;
//                mPhotoAdapter = new ChooseAdapter(context);
                startActivity(new Intent(TxxxDetailActivity.this, PhotosActivity.class));

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

    //    progressDialog.dismiss();
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
//       rz13sj.setText(txxx.getRz14sj());
//        rz13zb.setText(txxx.getRz14zb());
//       rz13dd.setText(txxx.getRz14dd());
//        if(txxx.getRz14jk().equals("")){
//            spinner.setSelection(0);
//        }else if(txxx.getRz14jk().equals("填表认证")){
//            spinner.setSelection(1);
//        }else if(txxx.getRz14jk().equals("本人认证")){
//            spinner.setSelection(2);
//        }else if(txxx.getRz14jk().equals("代认证")){
//            spinner.setSelection(3);
//        }

        listrzxx = new ArrayList<Map<String, String>>(listrzxx0);
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
//        if(!txxx.getTbsm().equals("")){
//            Photoimage photoimage=new Photoimage();
//            i=i+1;
//            photoimage.setId(i);
//            photoimage.setPath(txxx.getTbsm());
//            photoimage.setSubject("填表认证扫描图");
//            potolist.add(photoimage);
//        }
        if(!txxx.getSfzfyj().equals("")){
            Photoimage photoimage=new Photoimage();
            i=i+1;
            photoimage.setId(i);
            photoimage.setPath(txxx.getSfzfyj());
            photoimage.setSubject("身份证复印件扫描图");
            potolist.add(photoimage);
        }
//        if(!txxx.getSbjt().equals("")){
//            Photoimage photoimage=new Photoimage();
//            i=i+1;
//            photoimage.setId(i);
//            photoimage.setPath(txxx.getSbjt());
//            photoimage.setSubject("视频认证截图");
//            potolist.add(photoimage);
//        }

        for (int j = 0; j <potolist.size(); j++) {

            adapterlzx.add(mockPhoto(j));
        }



    }
    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("头像");
        rowDataList.add("姓名");
        rowDataList.add("科室");
        rowDataList.add("生日(入党日)");
        rowDataList.add("电码号码");
        return rowDataList;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            // you can do sth here, for example: make a toast:
            Toast.makeText(TxxxDetailActivity.this, "custom SwipeRefresh listener", Toast.LENGTH_SHORT).show();
            // don`t forget to call this
            adapterrzxx.getSwipeRefreshLayout().setRefreshing(false);
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
         //   progressDialog.dismiss(); //消除Loding对话框
            showView(txxx);
//            rz.se;
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_txxxdetailmain, menu);
//        menu.getItem(0).setVisible(false);
//        menu.getItem(2).setVisible(false);
////        Toast.makeText(TxxxDetailActivity.this, tusers.getPurview()+"与"+txxx.getRz13jk(), Toast.LENGTH_SHORT).show();
//
//        if(tusers.getPurview().equals("社保")||tusers.getPurview().equals("系统")) {
//        //    if (txxx.getRz14jk().equals("")) {
//                menu.getItem(1).setEnabled(true);
//                menu.getItem(1).setVisible(true);
//        //    } else {
//          //      menu.getItem(1).setEnabled(false);
//          //      menu.getItem(1).setVisible(false);
//         //   }
//        }
//
//
//        return super.onCreateOptionsMenu(menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String url = HttpUtil.BASE_URL+"txxx!updateTxxxId.action";//获得详细页面的url      //分享用
        if (id == R.id.action_txxxdetail_mainrz) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("认证提交中  请稍后...");
            progressDialog.show();

                httpUtils = new HttpUtils();

                RequestParams params = new RequestParams();
//            }

            mSelectedPhotos=mPhotoAdapter.getData();
//            String tmepName1 = null;
                for (int i = 0; i < mSelectedPhotos.size(); i++) {
                    Log.i("F", filepath + "a0" + i + "jpg");

                    String tmepName = null;
                    if(mSelectedPhotos.get(i).getPath()!=null) {
                        try {
                            tmepName = PictureUtil.bitmapToPath(mSelectedPhotos.get(i).getPath(),i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        tmepName1 =tmepName;
                                imgstmppath.add(tmepName);
                    list.add(new File(tmepName));
                        params.addBodyParameter("upload[" + i + "]", new File(tmepName));

                    }

                }

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
            params.addQueryStringParameter("id",txxx.getId()+"");
            params.addQueryStringParameter("name",name.getText().toString());
            params.addQueryStringParameter("sfzh",sfzh.getText().toString());
            params.addQueryStringParameter("hkdz",hkdz.getText().toString());
            params.addQueryStringParameter("czdz",czdz.getText().toString());
            params.addQueryStringParameter("lxdh1",lxdh1.getText().toString());
            params.addQueryStringParameter("lxdh2",lxdh2.getText().toString());
            params.addQueryStringParameter("lxdh3",lxdh3.getText().toString());
            params.addQueryStringParameter("rzjk",spinner.getSelectedItem().toString());
            params.addQueryStringParameter("rzzb",tusers.getUserName());
            params.addQueryStringParameter("rzdd", tusers.getBranch().getName());



            // params.addQueryStringParameter("product.gsdz","东川");
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, params,new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {

                    if (responseInfo.result != null) {
                  //      progressDialog.dismiss();
                        Toast.makeText(TxxxDetailActivity.this, "认证成功！", Toast.LENGTH_SHORT).show();
                        //    SharedPreferencesUtil.saveData(ProductinfoAddActivity.this, url, responseInfo.result);
                        PictureUtil.deleteImgTmp(imgstmppath);
                        txxx= BaseService.getGson().fromJson(responseInfo.result.toString(), new TypeToken<Txxx>() {}.getType());
                        myAppVariable.setTxxx(new Txxx());
                        myAppVariable.setTxxx(txxx);
                        Intent intent = new Intent();
                        intent.setClass(TxxxDetailActivity.this,TxxxDetailActivity.class);

                        startActivity(intent);
//                        setResult(RESULT_CODE, intent);
                        finish();

                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                 //   progressDialog.dismiss();
                    Toast.makeText(TxxxDetailActivity.this, "认证失败！", Toast.LENGTH_SHORT).show();
                }
            });

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
    private void doSearching(int id) {


        url=HttpUtil.BASE_URL+"txxx!queryTxxxId.action";
        params = new RequestParams();
        params.addQueryStringParameter("id",id+"");

        otherquery=true;
        mThreadmy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photosMessageEvent(EventEntry entries) {
        if (entries.id == EventEntry.RECEIVED_PHOTOS_ID) {

               entries.photos.get(0).setImageId(photoposition);
            mPhotoAdapter.reloaddata(entries.photos.get(0));

        }
    }


    /**
     * 认证记录适配器
     */
    class MyRzxxAdapter extends BaseAdapter {

        public List<Map<String,String>> rzxxs;//数据源
        public int layoutId;//样式布局文件

        public MyRzxxAdapter(List<Map<String,String>> rzxxs,int layoutId) {//构造函数
            this.rzxxs =rzxxs;
             this.layoutId=layoutId;
                     }

        public void setRzxxs(List<Map<String,String>> rzxxs) {
            this.rzxxs = rzxxs;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return rzxxs.size();//返回个数
        }

        @Override
        public Object getItem(int position) {
            return rzxxs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            HeadlineBodyCard.ViewHolder vh;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.rzxxlist_item, null);
                Display display =getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height=display.getHeight();
                Map<String,String> data =rzxxs.get(position);
                if (position % 2 == 0) {//奇偶行背景色
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.palegreen));
                }else {
                    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
                }
////			ImageView ivTab = (ImageView) convertView.findViewById(R.id.imgv_tables);
                TextView rznd = (TextView) convertView.findViewById(R.id.rznd);
                TextView rzsj = (TextView) convertView.findViewById(R.id.rzsj);
                TextView rzjk = (TextView) convertView.findViewById(R.id.rzjk);
                TextView rzzb = (TextView) convertView.findViewById(R.id.rzzb);

                rznd.setGravity(Gravity.CENTER);
                rzsj.setGravity(Gravity.CENTER);
                rzjk.setGravity(Gravity.CENTER);
                rzzb.setGravity(Gravity.CENTER);


                rznd.setWidth((int)(width*0.12));
                rzsj.setWidth((int)(width*0.15));
                rzjk.setWidth((int)(width*0.2));
                rzzb.setWidth((int)(width*0.43));


               rznd.setHeight((int)(height*0.04));
                rzsj.setHeight((int)(height*0.04));
                rzjk.setHeight((int)(height*0.04));
                rzzb.setHeight((int)(height*0.04));

                rznd.setText((String)data.get("rznd")+"");
                rzsj.setText((String)data.get("rzsj"));
                rzjk.setText((String)data.get("rzjk"));
                rzzb.setText((String)data.get("rzzb"));


            } else {

            }

            return convertView;
        }
    }

}
