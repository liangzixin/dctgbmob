package com.scme.order.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.DiningcardJson;
import com.scme.order.model.EatsJson;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Teats;
import com.scme.order.model.EatTotal;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Teats;
import com.scme.order.model.Tusers;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

public class EatsService extends BaseService{

    private String json="";
    private List<Teats> eatsList=null;
    private  Teats eat;
    private List list;
    private List eatsTotalList=null;
    private  int count;
    private int countfs;
    private Double countmoney;
    private Map  map1=new HashMap<String,String>();
   private boolean  str=false;
   private EatsJson eatsJson=null;
    /**
     * 根据职工的Id查询职工的信息
     * @param eatid
     * @return Ttables
     */
    public Teats queryEatsId(int eatid) throws Exception
    {

        String path = HttpUtil.BASE_URL+"eats!queryUseEatsId.action?eatid="+eatid+"";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
                eat=getGson().fromJson(json, new TypeToken<Teats>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eat;
    }
    /**
     * 添加订单
     * @param workerid 餐桌Id
     * @param  eatnumber 订单总价
     * */
    public void addEats(int workerid,int eatnumber) throws Exception
    {
        String path=HttpUtil.BASE_URL+"eats!insterEat.action?workerid="+workerid+"&eatnumber="+eatnumber+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        System.out.println("----------"+ conn.getResponseCode());
    }
    public boolean DeleteEats(int workerid) throws Exception
    {
       boolean str=false;
        String path=HttpUtil.BASE_URL+"eats!DeleteEats.action?eatid="+workerid+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        if (200 == conn.getResponseCode())
        {
            //获取输入流
            InputStream is = conn.getInputStream();
            ToolsHandler toolsHandler=new ToolsHandler();
            byte[] data=toolsHandler.InputStreamToByte(is);
            json=new String(data);
            System.out.println(json);
            if(json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                str=jsonObject.getBoolean("str");
            }
        }
        return str;
    }
    /**
     * 查询最新订单Id
     * @param
     * @return 订单Id
     */
    public int queryEatsByNewEatsId(int tabId) throws Exception
    {
        String json="";
        int eatsId=0;
        String path=HttpUtil.BASE_URL+"eats!queryEatsByNewEatsId.action?tabId="+tabId+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        if (200 == conn.getResponseCode())
        {
            //获取输入流
            InputStream is = conn.getInputStream();
            ToolsHandler toolsHandler=new ToolsHandler();
            byte[] data=toolsHandler.InputStreamToByte(is);
            json=new String(data);
            System.out.println(json);
            if(json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                eatsId=jsonObject.getInt("EatsId");
            }
        }
        return eatsId;
    }
    /**
     * 查询最新订单Id
     * @param
     * @return 订单Id
     */
    public List queryEatsName(String name) throws Exception
    {
        String json="";
        int eatsId=0;
        String path=HttpUtil.BASE_URL+"eats!queryEatsTotalPersionName.action?name="+name+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        if (200 == conn.getResponseCode())
        {
            //获取输入流
            InputStream is = conn.getInputStream();
            ToolsHandler toolsHandler=new ToolsHandler();
            byte[] data=toolsHandler.InputStreamToByte(is);
            json=new String(data);
            System.out.println(json);
           list =getGson().fromJson(json, new TypeToken<List>() {}.getType());
        }
        return  list;
    }
    /**
     * 根据餐桌Id结账(最新账单)
     * 改变餐桌状态
     * 改变订单状态
     * @param TabId 餐桌Id
     * @throws IOException
     */
    public void settlCountEatsByTabId(int TabId) throws IOException
    {
        String path=HttpUtil.BASE_URL+"eats!settlCountEatsByTabId.action?tabId="+TabId+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        System.out.println("结账完成----------"+ conn.getResponseCode());
    }

    /**
     * 查询所有订单
     * @return List<Teats> foods 订单对象集合
     */
    public List<Teats> QueryAllEats(int intFrist,int recPerPage) throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryAllEats.action?intFirst="+intFrist+"&recPerPage="+recPerPage+"";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
                eatsList =getGson().fromJson(json, new TypeToken<List<Teats>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eatsList;
    }
    /**
     * 查询今天订单
     * @return List<Teats> foods 订单对象集合
     */
    public EatsJson QueryTodayEats(int intFrist, int recPerPage) throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryTodayEats.action?intFirst="+intFrist+"&recPerPage="+recPerPage+"";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                eatsJson=getGson().fromJson(json,EatsJson.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return eatsJson;
    }
    /**
     * 查询今天订单
     * @return List<Teats> foods 订单对象集合
     */
    public Map QueryTodayEatsCount(int workerid) throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryTodayEatsCount.action?workerid="+workerid+"";

       URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
                if(  json!="0")
                {
                    JSONObject jsonObject=new JSONObject( json);
                    count=jsonObject.getInt("count");
                    countfs=jsonObject.getInt("countfs");
                    countmoney=jsonObject.getDouble("countmoney");
                }
                map1.put("count", count);
                map1.put("countfs", countfs);
                map1.put("countmoney",countmoney);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map1;
    }
    /**
     * 查询今天订单
     * @return List<Teats> foods 订单对象集合
     */
    public Map QueryAllEatsCount() throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryAllEatsCount.action";

        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                if(  json!="0")
                {
                    JSONObject jsonObject=new JSONObject( json);
                    count=jsonObject.getInt("count");
                    countfs=jsonObject.getInt("countfs");
                    countmoney=jsonObject.getDouble("countmoney");
                }
                map1.put("count", count);
                map1.put("countfs", countfs);
                map1.put("countmoney",countmoney);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map1;
    }
    /**
     * 查询年月合计
     * @return List<Teats> foods 订单对象集合
     */
    public List  QueryEatsTotal() throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryEatsTotal.action";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
         //      eatsTotalList =getGson().fromJson(json, new TypeToken<List<EatTotal>>(){}.getType());
                eatsTotalList =getGson().fromJson(json, new TypeToken<List>(){}.getType());
//                eatsTotalList =json;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  eatsTotalList ;
    }
    /**
     * 查询年月合计
     * @return List<Teats> foods 订单对象集合
     */
    public List  QueryEatsTotalPersion(int workerid) throws IOException
    {
        String path = HttpUtil.BASE_URL+"eats!queryEatsTotalPersion.action?workerid="+workerid+"";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
                //      eatsTotalList =getGson().fromJson(json, new TypeToken<List<EatTotal>>(){}.getType());
                eatsTotalList =getGson().fromJson(json, new TypeToken<List>(){}.getType());
//                eatsTotalList =json;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  eatsTotalList ;
    }
    /**
     * 交费
     * @param workerid 职工Id
     * @param  ym 年月
     * */
    public int updateEatsYmonth(int workerid,String ym) throws Exception
    {

        String path=HttpUtil.BASE_URL+"eats!updateEatsYmonth.action?workerid="+workerid+"&ymonth="+ym+"";

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        if (200 == conn.getResponseCode())
        {
            //获取输入流
            InputStream is = conn.getInputStream();
            ToolsHandler toolsHandler=new ToolsHandler();
            byte[] data=toolsHandler.InputStreamToByte(is);
            json=new String(data);
            System.out.println(json);
            if(json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                count=jsonObject.getInt("count");
            }
        }
        return  count;
    }
    /**
     * 其它条件的记录数
     * @param
     * @param
     * */


    public Map queryEatsOtherCount(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"eats!queryEatsOtherCount.action";

        json=loginPostData(path, map);
        if(  json!="0")
        {
            JSONObject jsonObject=new JSONObject( json);
            count=jsonObject.getInt("count");
            countfs=jsonObject.getInt("countfs");
            countmoney=jsonObject.getDouble("countmoney");
        }
        map1.put("count", count);
        map1.put("countfs", countfs);
        map1.put("countmoney",countmoney);
        return map1;
    }
    public  String loginPostData(String path, Map<String, String> map) {
        String json="";
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
//            connection.setReadTimeout(50000);
            connection.setRequestMethod("POST");
            StringBuffer buffer = new StringBuffer();
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                            .append("&");
                }
                buffer.deleteCharAt(buffer.length() - 1);
            }
            byte[] data = buffer.toString().getBytes();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(data.length));
            outputStream = connection.getOutputStream();
            outputStream.write(data);
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data1=toolsHandler.InputStreamToByte(inputStream);
                json=new String(data1);
//                return getContext(inputStream, "utf-8");
//                           return  true;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return json;
    }
    /**
     * 根据其它条件查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Teats> queryTeatsOther(Map map)
    {

        String path=HttpUtil.BASE_URL+"eats!queryTeatsOther.action";

        json=loginPostData(path, map);

        eatsList=getGson().fromJson(json, new TypeToken<List<Teats>>() {}.getType());
        return  eatsList;
    }
    /**
     * 添加请假　
     * @param
     * @return Ttables
     */
    public boolean addEats(Map map) throws Exception
    {


        String path=HttpUtil.BASE_URL+"eats!AddTeats.action";
        String result =loginPostData(path, map);
        try {
            if( result!="0")
            {
                JSONObject jsonObject=new JSONObject(result);
                str=jsonObject.getBoolean("str");
            }

        }   catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }
}
