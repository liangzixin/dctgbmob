package com.scme.order.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.scme.order.ui.R;


/**
 * Created by Administrator on 2015/11/10.
 */
public class XutilsGetData {
    //数据存储的名字
    private  String CONFIG = "config";

    private HttpHandler<String> hand;
    private HttpUtils http;
    private  String data = null;
    private  CallBackHttp callbackhttp;
//    static CustomProgressDialog dialog = null;
    //网络请求string数据
    public  void xUtilsHttp(final Context context, final String url, CallBackHttp callback, final boolean isprogressdialog) {
        //设置精度条
//        final ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setMax(100);
        http = new HttpUtils();
        callbackhttp = callback;
        if (isprogressdialog){

            if (dialog==null){
                dialog=new CustomProgressDialog(context,"正在加载中.......", R.drawable.donghua_frame);
            }
            dialog.show();
        }
        //打开子线程请求网络
        final CustomProgressDialog finalDialog = dialog;
      //  http.configCurrentHttpCacheExpiry(1000 * 10);
        hand = http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            //开始请求调用的方法
            public void onStart() {
                super.onStart();
//                progressBar.setVisibility(View.VISIBLE);

            }

            //正在请求调用的方法
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
//                progressBar.setProgress((int)(((float)current/total)*100));
            }

            //请求成功回调的方法
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                progressBar.setVisibility(View.GONE);
                LogUtils.e("onSuccess", responseInfo + "");
                LogUtils.e("onSuccessurl", url + "");
                data = responseInfo.result;//获得网络请求的字符串
                LogUtils.e("onSuccess", data + "");
               callbackhttp.handleData(data);//接口回调的方法
               saveData(context, url, data);//保存数据
                if (finalDialog !=null){
                    finalDialog.dismiss();

                }
            }

            //请求失败回调的方法
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private  SharedPreferences sp;

    //保存数据
    public  void saveData(Context context, String key, String data) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, data).commit();
    }

    //读取本地缓存数据
    public  String getData(Context context,String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    private static BitmapUtils utils;
    private static CallBackImage callbackimage;
    //网络请求图片
    static CustomProgressDialog dialog = null;
    public static void xUtilsImageiv(ImageView iv, String imageurl, Context context,boolean isprogressdialog) {
//        BitmapDisplayConfig config=new BitmapDisplayConfig();
//        final Animation alpha= AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha);
//        config.setAnimation(alpha);
        //第一个参数为上下文 第二个参数为缓冲路径(如果不写也会缓存到默认路径)
//        callbackimage=callback;

        utils = new BitmapUtils(context, context.getFilesDir().getPath());//保存图片路径

        if (isprogressdialog){
            if (dialog==null){
                dialog=new CustomProgressDialog(context,"正在加载中.......", R.drawable.donghua_frame);
            }
            dialog.show();
        }
        //第一个为放入图片的控件  第二个为图片地址  config为显示方式   CallBack为回调的方法可以自定义显示
//        utils.display(iv, url, config);
        utils.display(iv, imageurl, new BitmapLoadCallBack<ImageView>() {
            //请求成功调用的方法
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
//                imageView.setAnimation(alpha);
                imageView.setImageBitmap(bitmap);
//                callbackimage.handleData(bitmap);
                if (dialog !=null){
                    dialog.dismiss();
                }
            }

            //请求失败调用的方法
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
    }

    //关闭Xutils流
    public  void XutilsClose() {
        if (hand != null) {
            hand.cancel();
        }
        if (utils != null) {
            utils.cancel();
        }
    }

    //数据接口回调
    public interface CallBackHttp {
        void handleData(String data);
    }

    //图片的接口回调
    public interface CallBackImage {
        void handleData(Bitmap bitmap);
    }

    private String data1="{\"T18908805728\":[{\"postid\":\"PHOT4GEC000500D8\",\"hasCover\":false,\"hasHead\":1,\"replyCount\":1059,\"hasImg\":1,\"digest\":\"\",\"hasIcon\":true,\"docid\":\"9IG74V5H00963VRO_C03TJQC0bjyexiangupdateDoc\",\"title\":\"枪手米兰元老过招 仿佛梦回十五年前\",\"order\":1,\"priority\":249,\"lmodify\":\"2016-09-04 12:22:38\",\"boardid\":\"sports2_bbs\",\"ads\":[{\"title\":\"吃嫩草!纳什再婚 妻子小17岁乃沙排队员\",\"tag\":\"photoset\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/7c8c7fd892e24371bada48c905bfe5d920160904074433.jpeg\",\"subtitle\":\"\",\"url\":\"00MK0005|147911\"}],\"photosetID\":\"00D80005|147916\",\"template\":\"normal1\",\"votecount\":908,\"skipID\":\"00D80005|147916\",\"alias\":\"Sports\",\"skipType\":\"photoset\",\"cid\":\"C1348649048655\",\"hasAD\":1,\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/6a7b676bef6b48c0ad16e98ff752403220160904082340.jpeg\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/5342881ca9f044e3bcaa71d0b7d297f920160904082359.jpeg\"}],\"source\":\"网易原创\",\"ename\":\"tiyu\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/9266740f3b794a0996db95e5e9a417ef20160904121923.jpeg\",\"tname\":\"体育\",\"ptime\":\"2016-09-04 08:23:02\"},{\"postid\":\"C03VL59100051C8M\",\"url_3w\":\"http://sports.163.com/16/0904/08/C03VL59100051C8M.html\",\"votecount\":1285,\"replyCount\":1441,\"skipID\":\"S1464920831306\",\"ltitle\":\"伊朗对餐饮住宿要求高 酒店为其急购30箱矿泉水\",\"digest\":\"昨天上午，伊朗队乘坐该国军方的运输机飞抵沈阳机场。由于走的是特殊通道，球迷和记者在机场没能见到伊朗队成员。在他们下榻的酒店，倒是有一些中国球迷等待签名合影。在这\",\"skipType\":\"special\",\"url\":\"http://3g.163.com/sports/16/0904/08/C03VL59100051C8M.html\",\"specialID\":\"S1464920831306\",\"docid\":\"C03VL59100051C8M\",\"title\":\"伊朗对餐饮住宿要求高 酒店为其急购30箱矿泉水\",\"source\":\"网易体育\",\"priority\":199,\"lmodify\":\"2016-09-04 11:41:40\",\"boardid\":\"sports_bbs\",\"subtitle\":\"\",\"imgsrc\":\"http://img2.cache.netease.com/news/2016/9/4/20160904085948e0663.jpg\",\"ptime\":\"2016-09-04 08:58:43\"},{\"url_3w\":\"http://sports.163.com/16/0904/07/C03PUMU800051C8V.html\",\"postid\":\"C03PUMU800051C8V\",\"votecount\":3297,\"replyCount\":3544,\"ltitle\":\"GIF-没你就像隔胸罩亲吻胸!梅西为迷妹胸部签名\",\"digest\":\"“莱奥，你不在的日子，就如同隔着胸罩去亲吻胸部一样！”这是阿根廷1-0乌拉圭的比赛场边的标语，这样的比喻足以感受到球迷的热情。一位女球迷在社交媒体晒出一张照片，\",\"url\":\"http://3g.163.com/sports/16/0904/07/C03PUMU800051C8V.html\",\"docid\":\"C03PUMU800051C8V\",\"title\":\"GIF-没你就像隔胸罩亲吻胸!梅西为迷妹胸部签名\",\"source\":\"网易体育\",\"priority\":198,\"lmodify\":\"2016-09-04 07:50:49\",\"imgsrc\":\"http://img6.cache.netease.com/news/2016/9/4/201609040720559935a.jpg\",\"subtitle\":\"\",\"boardid\":\"sports2_bbs\",\"ptime\":\"2016-09-04 07:19:04\"},{\"url_3w\":\"\",\"postid\":\"C02E39NT05299A54\",\"votecount\":22970,\"replyCount\":24169,\"ltitle\":\"郎平曝3年前执教女排条件:不让当局领导干涉球队\",\"digest\":\"当“铁榔头”郎平率领着这支不被世人看好的中国女排，勇夺里约奥运会的金牌后，“女排精神”再次席卷中国大江南北，而郎平多次重申不要光谈“精神”！近日媒体报道，郎平2\",\"url\":\"null\",\"docid\":\"C02E39NT05299A54\",\"title\":\"郎平曝3年前执教女排条件:不让当局领导干涉球队\",\"source\":\"三十年莱斯特城球迷\",\"priority\":195,\"lmodify\":\"2016-09-04 09:19:15\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/1e49c0c2c0ed40b090b97a09e2b3152720160904084807.jpeg\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 08:43:47\"},{\"url_3w\":\"\",\"postid\":\"C0416KQ005298MRS\",\"votecount\":844,\"replyCount\":1016,\"ltitle\":\"水有源树有根,中韩战张琳芃被打爆竟是这原因\",\"digest\":\"12强赛首场比赛，中国队客场挑战韩国队，韩国主教练施蒂利克输不起，韩国媒体形容，一旦太极虎输球，德国人必然下课。而中国队则输赢皆可，只要打出士气，球迷都会满意。\",\"url\":\"null\",\"docid\":\"C0416KQ005298MRS\",\"title\":\"水有源树有根,中韩战张琳芃被打爆竟是这原因\",\"source\":\"齐帅\",\"priority\":194,\"lmodify\":\"2016-09-04 10:23:00\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/2b638979cbed4416a410dee62f65951620160904095335.png\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 09:25:44\"},{\"postid\":\"PHOT4GDQ00056OTQ\",\"votecount\":6816,\"replyCount\":7114,\"skipID\":\"6OTQ0005|147898\",\"digest\":\"\",\"skipType\":\"photoset\",\"docid\":\"9IG74V5H00963VRO_C03P98J4bjyexiangupdateDoc\",\"title\":\"是宠儿也是弃儿 十强赛英雄祁宏沉沦史\",\"source\":\"网易原创\",\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/c7b047948024460db9849610cc4bd7d320160904070841.jpeg\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/6010275e4f68498584b8eecd0eceb5d020160904070857.jpeg\"}],\"priority\":150,\"lmodify\":\"2016-09-04 12:23:01\",\"boardid\":\"sports2_bbs\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/d71329952b204c77b43b85b95f1af51420160904070816.jpeg\",\"photosetID\":\"6OTQ0005|147898\",\"ptime\":\"2016-09-04 07:07:21\"},{\"url_3w\":\"\",\"postid\":\"C044M7RS0529ADMH\",\"votecount\":1388,\"replyCount\":1644,\"ltitle\":\"国足不该忘记的耻辱 竟被伊朗用如此方式进球\",\"digest\":\"中伊大战，关系到国足在12强赛的开局形势，也是中国队再一次寻找与亚洲强队差距的机会。然而，虽然历史交锋上处于明显下风，但是伊朗这个对手，中国队还是得全力争胜，即\",\"url\":\"null\",\"docid\":\"C044M7RS0529ADMH\",\"title\":\"国足不该忘记的耻辱 竟被伊朗用如此方式进球\",\"source\":\"黑色柳丁\",\"priority\":145,\"lmodify\":\"2016-09-04 10:44:17\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/a1017f811a9f43e2a4f3e55171148dfa20160904103645.jpeg\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 10:26:41\"},{\"url_3w\":\"http://sports.163.com/16/0904/09/C03VU8OH00051C8M.html\",\"postid\":\"C03VU8OH00051C8M\",\"votecount\":814,\"replyCount\":938,\"ltitle\":\"01年十强赛国足13球祁宏独造6球 能出线他记头功\",\"digest\":\"国足在12强赛首战2-3客场不敌韩国队，没有收获开门红。不过，在15年前的10强赛，国足的实力相比现在却有着天壤之别，那届10强赛，国足最终杀出重围，闯入了韩日\",\"url\":\"http://3g.163.com/sports/16/0904/09/C03VU8OH00051C8M.html\",\"docid\":\"C03VU8OH00051C8M\",\"title\":\"01年十强赛国足13球祁宏独造6球 能出线他记头功\",\"source\":\"网易体育\",\"priority\":145,\"lmodify\":\"2016-09-04 10:44:17\",\"imgsrc\":\"http://img1.cache.netease.com/news/2016/9/4/201609040904371d1ab.jpg\",\"subtitle\":\"\",\"boardid\":\"sports_bbs\",\"ptime\":\"2016-09-04 09:03:41\"},{\"url_3w\":\"http://sports.163.com/16/0904/11/C047MES000051C8M.html\",\"postid\":\"C047MES000051C8M\",\"votecount\":891,\"replyCount\":947,\"ltitle\":\"祁宏等4人当初为何会打假球?谁该为此恶行买单\",\"digest\":\"随着江津、李明以及祁宏的出狱，2003年末代甲A末轮陷入假球案的只有申思还在服刑。对于那场比赛，原上海中远的总经理王国林说起来依然愤怒不已。2003年甲A联赛末\",\"url\":\"http://3g.163.com/sports/16/0904/11/C047MES000051C8M.html\",\"docid\":\"C047MES000051C8M\",\"title\":\"祁宏等4人当初为何会打假球?谁该为此恶行买单\",\"source\":\"网易体育\",\"priority\":144,\"lmodify\":\"2016-09-04 11:24:21\",\"imgsrc\":\"http://img1.cache.netease.com/news/2016/9/4/20160904112036a9297.jpg\",\"subtitle\":\"\",\"boardid\":\"sports_bbs\",\"ptime\":\"2016-09-04 11:19:14\"},{\"url_3w\":\"http://sports.163.com/16/0904/10/C046FFL000051C89.html\",\"postid\":\"C046FFL000051C89\",\"votecount\":405,\"replyCount\":441,\"ltitle\":\"佩莱:想在中超进球不易 我爸说来中国能赚大钱\",\"digest\":\"虽然佩莱来到中超后进球效率不高，但他还是被招入了意大利国家队，并且在对法国队的热身赛上打入1球。在接受意大利媒体《共和报》采访时表示，自己的能力不会因踢中超而退\",\"url\":\"http://3g.163.com/sports/16/0904/10/C046FFL000051C89.html\",\"docid\":\"C046FFL000051C89\",\"title\":\"佩莱:想在中超进球不易 我爸说来中国能赚大钱\",\"source\":\"网易体育\",\"priority\":144,\"lmodify\":\"2016-09-04 11:24:07\",\"imgsrc\":\"http://img4.cache.netease.com/news/2016/9/4/2016090410583788485.jpg\",\"subtitle\":\"\",\"boardid\":\"sports_bbs\",\"ptime\":\"2016-09-04 10:57:57\"},{\"postid\":\"PHOT4GEG000505HE\",\"votecount\":67,\"replyCount\":75,\"skipID\":\"05HE0005|147920\",\"digest\":\"\",\"skipType\":\"photoset\",\"docid\":\"9IG74V5H00963VRO_C042R9EHbjyexiangupdateDoc\",\"title\":\"伊万拍摄杂志写真 文艺风尽显万种风情\",\"source\":\"网易原创\",\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/aafe5863edeb44bd88adae0d72aa3fec20160904095610.jpeg\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/c07a256cd80e4c0296d5651913ffbe5920160904095623.jpeg\"}],\"priority\":140,\"lmodify\":\"2016-09-04 10:07:12\",\"boardid\":\"sports_zh_bbs\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/e1998275bc1b4d99b4681262516197de20160904095553.jpeg\",\"photosetID\":\"05HE0005|147920\",\"ptime\":\"2016-09-04 09:54:29\"},{\"postid\":\"PHOT4GEI000500C9\",\"votecount\":675,\"replyCount\":714,\"skipID\":\"00C90005|147922\",\"digest\":\"\",\"skipType\":\"photoset\",\"docid\":\"9IG74V5H00963VRO_C049FGQQzhaoyuliangupdateDoc\",\"title\":\"22岁截肢小伙爱上足球 梦想成为C罗\",\"source\":\"网易原创\",\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/5f4ff2a69f9749c1ad7775cdab6d704e20160904115054.jpeg\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/0f0d64b2f94545b690fc95eb2a3eaaf520160904115102.jpeg\"}],\"priority\":121,\"lmodify\":\"2016-09-04 11:51:37\",\"boardid\":\"sports2_bbs\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/da047d77d98240caae69427d13439d4220160904115047.jpeg\",\"photosetID\":\"00C90005|147922\",\"ptime\":\"2016-09-04 11:50:24\"},{\"url_3w\":\"\",\"postid\":\"C031GQM40529A1E9\",\"votecount\":13,\"replyCount\":21,\"ltitle\":\"GIF-满满的都是回忆!米兰枪手元老赛上的6粒进球\",\"digest\":\"昨日，一场足球慈善赛在酋长球场展开角逐，对阵双方是阿森纳传奇队和AC米兰荣耀队。皮雷的长发没了，永贝里感觉没老，奥华马斯速度还在，佩蒂特小马尾还在，吉尔伯特身体\",\"url\":\"null\",\"docid\":\"C031GQM40529A1E9\",\"title\":\"GIF-满满的都是回忆!米兰枪手元老赛上的6粒进球\",\"source\":\"足球TOP10\",\"priority\":120,\"lmodify\":\"2016-09-04 11:24:32\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/5b638c42896c42789022b90cc4a77c9b20160904105745.jpeg\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 10:47:50\"},{\"postid\":\"C03UMAVP00051CCL\",\"url_3w\":\"http://sports.163.com/16/0904/08/C03UMAVP00051CCL.html\",\"votecount\":2417,\"replyCount\":2619,\"skipID\":\"S1467395362939\",\"ltitle\":\"英超踢假球?惊天内幕曝光 前主裁:有人逼我说谎\",\"digest\":\"英媒体《每日镜报》、《太阳报》今日都在体育版的头条刊登了这样一条新闻：英超涉嫌假球。一位前英超主裁曝料称自己经常会受到PGMOL（职业赛事官员有限公司）的施压，\",\"skipType\":\"special\",\"url\":\"http://3g.163.com/sports/16/0904/08/C03UMAVP00051CCL.html\",\"specialID\":\"S1467395362939\",\"docid\":\"C03UMAVP00051CCL\",\"title\":\"英超踢假球?惊天内幕曝光 前主裁:有人逼我说谎\",\"source\":\"网易体育\",\"priority\":120,\"lmodify\":\"2016-09-04 10:44:03\",\"boardid\":\"sports2_bbs\",\"subtitle\":\"\",\"imgsrc\":\"http://img3.cache.netease.com/news/2016/9/4/20160904084210a1c5d.jpg\",\"ptime\":\"2016-09-04 08:41:53\"},{\"url_3w\":\"http://sports.163.com/16/0904/08/C03TUOPF00051CCL.html\",\"postid\":\"C03TUOPF00051CCL\",\"votecount\":1473,\"replyCount\":1578,\"ltitle\":\"GIF-熟悉的味道!阿森纳元老队5人8脚传递进攻666\",\"digest\":\"酋长球场迎来了一场满是回忆的元老赛，阿森纳传奇队主场4-2战胜AC米兰荣耀队，卡努上演帽子戏法，皮雷打进一球，维埃里梅开二度。不过比赛刚开场第9分钟，阿森纳元老\",\"url\":\"http://3g.163.com/sports/16/0904/08/C03TUOPF00051CCL.html\",\"docid\":\"C03TUOPF00051CCL\",\"title\":\"GIF-熟悉的味道!阿森纳元老队5人8脚传递进攻666\",\"source\":\"网易体育\",\"priority\":120,\"lmodify\":\"2016-09-04 10:44:03\",\"imgsrc\":\"http://img1.cache.netease.com/news/2016/9/4/20160904082934d90f8.jpg\",\"subtitle\":\"\",\"boardid\":\"sports2_bbs\",\"ptime\":\"2016-09-04 08:29:00\"},{\"url_3w\":\"http://sports.163.com/16/0904/08/C03SI3QT00051C8V.html\",\"postid\":\"C03SI3QT00051C8V\",\"votecount\":3081,\"replyCount\":3300,\"ltitle\":\"皇马更衣室炸弹?曝队友不满贝尔索1700万欧高薪\",\"digest\":\"新赛季刚刚开始，西媒就又传出了皇马更衣室不稳的消息。据《每日体育报》报道称，由于贝尔索要1700万欧元的税后年薪，这让球队更衣室里的几名大佬级球员很不满意。贝尔\",\"url\":\"http://3g.163.com/sports/16/0904/08/C03SI3QT00051C8V.html\",\"docid\":\"C03SI3QT00051C8V\",\"title\":\"皇马更衣室炸弹?曝队友不满贝尔索1700万欧高薪\",\"source\":\"网易体育\",\"priority\":120,\"lmodify\":\"2016-09-04 10:44:04\",\"imgsrc\":\"http://img1.cache.netease.com/news/2016/9/4/20160904080457a90e0.png\",\"subtitle\":\"\",\"boardid\":\"sports2_bbs\",\"ptime\":\"2016-09-04 08:04:37\"},{\"url_3w\":\"http://sports.163.com/16/0904/04/C03F8JNU00051CD5.html\",\"postid\":\"C03F8JNU00051CD5\",\"votecount\":4263,\"replyCount\":4837,\"ltitle\":\"舍得么?罗马10号球衣换名 陪你25年的人真要走了\",\"digest\":\"\\\"NoTotti，NoParty\\\"对于喜欢托蒂、罗马以及早年蓝衣飘飘的意大利球迷来说，这样的一句话怕是再熟悉不过了。但天下没有不散的筵席，托蒂，罗马城的图腾，陪\",\"url\":\"http://3g.163.com/sports/16/0904/04/C03F8JNU00051CD5.html\",\"docid\":\"C03F8JNU00051CD5\",\"title\":\"舍得么?罗马10号球衣换名 陪你25年的人真要走了\",\"source\":\"网易体育\",\"priority\":120,\"lmodify\":\"2016-09-04 11:42:06\",\"imgsrc\":\"http://img1.cache.netease.com/news/2016/9/4/20160904041248d1128.png\",\"subtitle\":\"\",\"boardid\":\"sports2_bbs\",\"ptime\":\"2016-09-04 04:12:14\"},{\"postid\":\"C0496G3S0529A80Q\",\"url_3w\":\"\",\"votecount\":7,\"replyCount\":16,\"ltitle\":\"个个都是女神级!带你认识国足美艳太太团\",\"digest\":\"9月3日，国足前锋郜林在微博上晒照秀恩爱，庆祝和妻子王晨结婚三周年，并写道“更懂得爱”，王晨则回应“开心的笑到变形”。爱情的力量是无穷的，爱人在背后默默地支持，\",\"url\":\"null\",\"docid\":\"C0496G3S0529A80Q\",\"title\":\"个个都是女神级!带你认识国足美艳太太团\",\"source\":\"小鸟玩足球\",\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/4bccd69b7ac74748946cb3724a60de6c20160904121922.jpeg\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/422066af36fb48d3a03d593aa4113da520160904121933.jpeg\"}],\"priority\":119,\"lmodify\":\"2016-09-04 12:25:45\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/9d270054fe4e4ed396953692720136b820160904121911.jpeg\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 11:45:28\"},{\"postid\":\"C0419ANS0529A297\",\"url_3w\":\"\",\"votecount\":141,\"replyCount\":201,\"ltitle\":\"揭\\\"波斯铁骑\\\"神秘面纱 1人竟贵为亚洲梅西\",\"digest\":\"在2018年世界杯亚洲区预选赛第一轮的比赛中，中国队2:3输给了韩国队，虽然比赛失利，但是中国队在比赛最后20分钟的表现让我们更加期待这一场与伊朗队之间的对决了\",\"url\":\"null\",\"docid\":\"C0419ANS0529A297\",\"title\":\"揭\\\"波斯铁骑\\\"神秘面纱 1人竟贵为亚洲梅西\",\"source\":\"惊奇侃球\",\"imgextra\":[{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/725d08ae2f1c4481b77bf1540272663120160904093328.png\"},{\"imgsrc\":\"http://cms-bucket.nosdn.127.net/860376a646e949fbbcbe28ebc18285bb20160904093343.png\"}],\"priority\":119,\"lmodify\":\"2016-09-04 10:49:59\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/7f63467dde7e4fd2aad488b50e926b6a20160904093317.png\",\"subtitle\":\"\",\"boardid\":\"dy_wemedia_bbs\",\"ptime\":\"2016-09-04 09:27:12\"},{\"url_3w\":\"http://sports.163.com/16/0904/03/C03BFPVC00051C8M.html\",\"postid\":\"C03BFPVC00051C8M\",\"votecount\":83,\"replyCount\":106,\"ltitle\":\"谍影重重!为防战术外泄 国足训练场围红色帷幔\",\"digest\":\"中国男足坐镇沈阳主场迎战伊朗队，此战的重要性甚至比首战还高，如若再败，国足将很可能提前退出小组前两名的争夺。为此，中伊战前国足的训练严格保密，昨天训练时更是在训\",\"url\":\"http://3g.163.com/sports/16/0904/03/C03BFPVC00051C8M.html\",\"docid\":\"C03BFPVC00051C8M\",\"title\":\"谍影重重!为防战术外泄 国足训练场围红色帷幔\",\"source\":\"成都日报\",\"priority\":117,\"lmodify\":\"2016-09-04 10:12:50\",\"imgsrc\":\"http://img3.cache.netease.com/news/2016/9/4/20160904092829afca3.jpg\",\"subtitle\":\"\",\"boardid\":\"sports_bbs\",\"ptime\":\"2016-09-04 03:06:16\"}]}";
}
