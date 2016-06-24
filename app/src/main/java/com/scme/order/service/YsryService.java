package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Ysry;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class YsryService extends BaseService{

    private String json="";
    private List<Ysry> ysrysList=null;
    private  Ysry ysry=null;
    private List ysrysTotalList=null;
     boolean str=false;
    private int count=0;
    /**
     * 根据职工的Id查询职工的信息
     * @param ysryid
     * @return Ttables
     */
    public Ysry queryYsryId(int ysryid) throws Exception
    {

        String path = HttpUtil.BASE_URL+"ysry!queryUseYsryId.action?a1="+ysryid+"";
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
                ysry=getGson().fromJson(json, new TypeToken<Ysry>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ysry;
    }
    /**
     * 添加订单
     * @param workerid 餐桌Id
     * @param  ysrynumber 订单总价
     * */
    public void addYsry(int workerid,int ysrynumber) throws Exception
    {
        String path=HttpUtil.BASE_URL+"ysrys!insterEat.action?workerid="+workerid+"&ysrynumber="+ysrynumber+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        System.out.println("----------"+ conn.getResponseCode());
    }
    public boolean DeleteYsry(int workerid) throws Exception
    {
       boolean str=false;
        String path=HttpUtil.BASE_URL+"ysrys!DeleteYsry.action?ysryid="+workerid+"";
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
    public int queryYsryByNewYsryId(int tabId) throws Exception
    {
        String json="";
        int ysrysId=0;
        String path=HttpUtil.BASE_URL+"ysrys!queryYsryByNewYsryId.action?tabId="+tabId+"";
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
                ysrysId=jsonObject.getInt("YsryId");
            }
        }
        return ysrysId;
    }

    /**
     * 根据餐桌Id结账(最新账单)
     * 改变餐桌状态
     * 改变订单状态
     * @param TabId 餐桌Id
     * @throws IOException
     */
    public void settlCountYsryByTabId(int TabId) throws IOException
    {
        String path=HttpUtil.BASE_URL+"ysrys!settlCountYsryByTabId.action?tabId="+TabId+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        System.out.println("结账完成----------"+ conn.getResponseCode());
    }

    /**
     * 查询所有订单
     * @return List<Ysry> foods 订单对象集合
     */
    public List<Ysry> QueryAllYsrys(int intFrist,int recPerPage) throws IOException
    {
        String path = HttpUtil.BASE_URL+"ysry!queryAllYsrys.action?intFirst="+intFrist+"&recPerPage="+recPerPage+"";
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
                ysrysList =getGson().fromJson(json, new TypeToken<List<Ysry>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ysrysList;
    }


    /**
     * 查询今天订单
     * @return List<Ysry> foods 订单对象集合
     */
    public int QueryAllYsrysCount() throws IOException
    {
        int count=0;
        String path = HttpUtil.BASE_URL+"ysry!queryAllYsrysCount.action";

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
                if(json!="0")
                {
                    JSONObject jsonObject=new JSONObject(json);
                    count=jsonObject.getInt("count");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }


    /**
     * 交费
     * @param workerid 职工Id
     * @param  ymonth 年月
     * */
    public void updateYsryYmonth(int workerid,String ymonth) throws Exception
    {
        String ym=ymonth.substring(0,4)+ymonth.substring(5,7);
        String path=HttpUtil.BASE_URL+"ysrys!updateYsryYmonth.action?workerid="+workerid+"&ymonth="+ym+"";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        System.out.println("----------"+ conn.getResponseCode());
    }
    /**
     * 其它条件的记录数
     * @param
     * @param
     * */


    public int queryYsryOtherCount(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"ysry!queryYsryOtherCount.action";

       json=loginPostData(path, map);

            if(json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                count=jsonObject.getInt("count");

        }

        return count;
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
    public List<Ysry> queryYsryOther(Map map)
    {
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path=HttpUtil.BASE_URL+"ysry!queryYsryOther.action";

        json=loginPostData(path, map);
//        System.out.println(json);
        ysrysList=getGson().fromJson(json, new TypeToken<List<Ysry>>() {}.getType());
        return  ysrysList;
    }
    /**
     * 添加请假　
     * @param
     * @return Ttables
     */
    public boolean AddYsry(Map map) throws Exception
    {
        boolean  str2=false;

        String path=HttpUtil.BASE_URL+"ysrys!AddYsry.action";
        String result =loginPostData(path, map);
        try {
            if( result!="0")
            {
                JSONObject jsonObject=new JSONObject(result);
                str2=jsonObject.getBoolean("str");
            }

        }   catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str2;
    }
    /**
     * 根据职工的姓名查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Ysry> queryYsryName(Map map)
    {

        String path=HttpUtil.BASE_URL+"ysry!queryYsryName.action";

        json=loginPostData(path, map);
//        System.out.println(json);
        ysrysList=getGson().fromJson(json, new TypeToken<List<Ysry>>() {}.getType());
        return ysrysList;
    }
    /**
     * 认证
     * @param
     * @param
     * */


    public boolean updateYsryId(Map map) throws Exception
    {
        String path=HttpUtil.BASE_URL+"ysry!updateYsryId.action";

       json=loginPostData(path, map);
        try {
            if( json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                str=jsonObject.getBoolean("str");
            }

        }   catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;

    }
}
