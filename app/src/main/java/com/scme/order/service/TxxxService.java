package com.scme.order.service;



import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Txxx;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxxxService extends BaseService {
    private Txxx txxx=null;
    private String json="";
    private List<Txxx> txxxs=null;
    private int count=0;

    /**
     * 用户登陆
     * @param userName 用户名
     * @param userPwd 密码
     * @return txxx Txxx对象
     * */
    public Txxx login(String userName,String userPwd) throws Exception
    {
        String json="";
        Txxx txxx=null;
        String path= HttpUtil.BASE_URL+"txxx!login.action?userName="+userName+"&userPwd="+userPwd+"";
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
                txxx =getGson().fromJson(json, new TypeToken<Txxx>() {}.getType());
            }
        }
        return txxx;
    }
    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Txxx> QueryAllTxxx(int intFirst,int recPerPage) throws Exception
    {
        String path = HttpUtil.BASE_URL+"txxx!queryTxxxAll.action?intFirst="+intFirst+"&recPerPage="+recPerPage+"";
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
                txxxs =getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return txxxs;
    }
    /**
     * 根据条件查询退休人员
     * @return List<Tfoods> foods
     */
    public List<Txxx> QueryAllTxxxOther(int intFirst,int recPerPage) throws Exception
    {
        String path = HttpUtil.BASE_URL+"txxx!queryTxxxAll.action?intFirst="+intFirst+"&recPerPage="+recPerPage+"";
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
                txxxs =getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return txxxs;
    }
    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Txxx> queryTodayBirthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"txxx!queryTodayBirthday.action";
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
                txxxs =getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return txxxs;
    }
    /**
     * 查询未来7天生日记录
     * @return List<Tfoods> foods
     */
    public List<Txxx> queryToday7Birthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"txxx!queryToday7Birthday.action";
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
                txxxs =getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return txxxs;
    }

    /**
     * 根据职工的Id查询职工的信息
     * @param id
     * @return Ttables
     */
    public Txxx queryTxxxId(int  id) throws Exception {


        String path = HttpUtil.BASE_URL+"txxx!queryTxxxId.action?id="+id+"";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
//
                InputStream is= conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);
                System.out.println(json);
                    txxx=getGson().fromJson(json, new TypeToken<Txxx>() {}.getType());

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return txxx;
    }
    /**
     * 根据职工的姓名查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Txxx> queryTxxxName(Map map)
    {

        String path=HttpUtil.BASE_URL+"txxx!queryTxxxName.action";

        json=loginPostData(path, map);
//        System.out.println(json);
        txxxs=getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
        return txxxs;
    }
    /**
     * 根据其它条件查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Txxx> queryTxxxOther(Map map)
    {

        String path=HttpUtil.BASE_URL+"txxx!queryTxxxOther.action";

        json=loginPostData(path, map);
//        System.out.println(json);
        txxxs=getGson().fromJson(json, new TypeToken<List<Txxx>>() {}.getType());
        return txxxs;
    }
    /**
     * 认证
     * @param
     * @param
     * */


    public void updateTxxxId(Map map) throws Exception
    {
        String path=HttpUtil.BASE_URL+"txxx!updateTxxxId.action";

        String result =loginPostData(path, map);


    }
    /**
     * 其它条件的记录数
     * @param
     * @param
     * */


    public int queryTxxxOtherCount(Map map) throws Exception
    {
        int count=0;
        String path=HttpUtil.BASE_URL+"txxx!queryTxxxOtherCount.action";

        String result =loginPostData(path, map);
        if( result!="0")
        {
            JSONObject jsonObject=new JSONObject( result);
            count=jsonObject.getInt("count");
        }

       System.out.println("返回记录户数数:"+result);
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
     * 查询记录数
     * @param
     * @return
     */
    public int  queryTxxxCount()
    {

        String path = HttpUtil.BASE_URL+"txxx!queryTxxxCount.action";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode())
            {
                //获取输入流
                InputStream is= conn.getInputStream();
                ToolsHandler toolsHandler=new ToolsHandler();
                byte[] data=toolsHandler.InputStreamToByte(is);
                json=new String(data);

                if(json!="0")
                {
                    JSONObject jsonObject=new JSONObject( json);
                    count=jsonObject.getInt("count");

                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   return count;
    }

}
