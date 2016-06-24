package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Qingjia;

import com.scme.order.model.Txxx;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

import org.json.JSONException;
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

public class QingjiaService extends BaseService{
    private Qingjia qingjia=null;
    private String json="";
    private List<Qingjia> qingjias=null;
    private List list;
    private int count=0;
    private double countday=0.0;
    private Map  map1=new HashMap<String,Object>();
    private  boolean str=false;
    /**
     * 根据ID删除记录
     * @param id 菜的Id
     * @param id 订单Id
     * */
    public boolean DeleteQingjiaId(int  id) throws Exception
    {
        String path=HttpUtil.BASE_URL+"qingjia!DeleteQingjiaId.action?Id="+id+"";
        URL url;
        boolean str=false;
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
                if(json!="0")
                {
                    JSONObject jsonObject=new JSONObject(json);
                    str=jsonObject.getBoolean("str");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Qingjia> QueryAllQingjias( int intFirst,int recPerPage) throws Exception
    {
        String path = HttpUtil.BASE_URL+"qingjia!queryAllQingjias.action?intFirst="+intFirst+"&recPerPage="+recPerPage+"";
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
                qingjias =getGson().fromJson(json, new TypeToken<List<Qingjia>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qingjias;
    }
    /**
     * 查询记录数
     * @param
     * @return
     */
    public int queryQingjiaCount()
    {
        String json="";
        int ordersId=0;
        String path = HttpUtil.BASE_URL+"qingjia!queryQingjiaCount.action";
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
                if(json!="0")
                {
                    JSONObject jsonObject=new JSONObject(json);
                    ordersId=jsonObject.getInt("count");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ordersId;
    }
    /**
     * 其它条件的记录数
     * @param
     * @param
     * */


    public Map queryOtherCount(Map map) throws Exception
    {


        String path=HttpUtil.BASE_URL+"qingjia!queryOtherCount.action";

        String result =loginPostData(path, map);
        if( result!="0")
        {
            JSONObject jsonObject=new JSONObject( result);
            count=jsonObject.getInt("count");
            countday=jsonObject.getDouble("countday");

        }
        map1.put("count",count);
        map1.put("countday",countday);

        return map1;
    }
    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Qingjia> queryTodayBirthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"qingjia!queryTodayBirthday.action";
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
                qingjias =getGson().fromJson(json, new TypeToken<List<Qingjia>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qingjias;
    }
    /**
     * 查询未来7天生日记录
     * @return List<Tfoods> foods
     */
    public List<Qingjia> queryToday7Birthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"qingjia!queryToday7Birthday.action";
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
                qingjias =getGson().fromJson(json, new TypeToken<List<Qingjia>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qingjias;
    }
    /**
     * 根据职工的Id查询职工的信息
     * @param workerid
     * @return Ttables
     */
    public Qingjia queryUseQingjiaId(int workerid) throws Exception
    {
        String json="";
        Qingjia qingjia=null;
        String path = HttpUtil.BASE_URL+"qingjia!queryUseQingjiaId.action?id="+workerid+"";
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
                qingjia=getGson().fromJson(json, new TypeToken<Qingjia>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return qingjia;
    }
    /**
     * 根据职工的姓名查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Qingjia> queryQingjiaName(Map map)
    {

        String path=HttpUtil.BASE_URL+"qingjia!queryQingjiaName.action";

        json=loginPostData(path, map);
//        System.out.println(json);
        qingjias=getGson().fromJson(json, new TypeToken<List<Qingjia>>() {}.getType());
        return qingjias;
    }

    /**
     * 添加请假　
     * @param
     * @return Ttables
     */
    public boolean AddQingjia(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"qingjia!AddQingjia.action";
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
    /**
     * 修改请假　
     * @param
     * @return Ttables
     */
    public boolean UpdateQingjia(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"qingjia!UpdateQingjia.action";
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
    /**
     * 签字　
     * @param
     * @return Ttables
     */
    public boolean UpdateStateQingjia(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"qingjia!UpdateStateQingjia.action";
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
    /**
     * 根据ID修改密码
     * @param
     * @param
     * */



        public Qingjia updateUserPwd(int  id,String userPwd) throws Exception
        {

            String path = HttpUtil.BASE_URL+"qingjia!updateUserPwd.action?id="+id+"&userPwd="+userPwd+"";
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
                    System.out.println(json);
                    qingjia=getGson().fromJson(json, new TypeToken<Qingjia>() {}.getType());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return qingjia;
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
    public List<Qingjia> queryQingjiaOther(Map map)
    {
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path=HttpUtil.BASE_URL+"qingjia!queryQingjiaOther.action";

        json=loginPostData(path, map);
//        System.out.println(json);
       qingjias=getGson().fromJson(json, new TypeToken<List<Qingjia>>() {}.getType());
        return qingjias;
    }
}
