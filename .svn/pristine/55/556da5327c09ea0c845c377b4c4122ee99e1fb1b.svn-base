package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Chuchai;

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

public class ChuchaiService extends BaseService{
    private Chuchai chuchai=null;
    private String json="";
    private List<Chuchai> chuchais=null;
    private List list;
    private Map map1;
    private int count=0;
    private double countday=0.0;
    private boolean str=false;


    /**
     * 根据ID删除记录
     * @param id 菜的Id
     * @param id 订单Id
     * */
    public boolean DeleteChuchaiId(int  id) throws Exception
    {
        String path=HttpUtil.BASE_URL+"chuchai!DeleteChuchaiId.action?Id="+id+"";
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
    public List<Chuchai> QueryAllChuchais( int intFirst,int recPerPage) throws Exception
    {
        String path = HttpUtil.BASE_URL+"chuchai!queryAllChuchais.action?intFirst="+intFirst+"&recPerPage="+recPerPage+"";
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
               chuchais =getGson().fromJson(json, new TypeToken<List<Chuchai>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return chuchais;
    }
    /**
     * 查询记录数
     * @param
     * @return
     */
    public int queryChuchaiCount()
    {
        String json="";
        int ordersId=0;
        String path = HttpUtil.BASE_URL+"chuchai!queryChuchaiCount.action";
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
          map1=new HashMap<String,Object>();
        String path=HttpUtil.BASE_URL+"chuchai!queryChuchaiOtherCount.action";

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
     * 根据职工的Id查询职工的信息
     * @param workerid
     * @return Ttables
     */
    public Chuchai queryUseChuchaiId(int workerid) throws Exception
    {
        String json="";
        Chuchai chuchai=null;
        String path = HttpUtil.BASE_URL+"chuchai!queryUseChuchaiId.action?id="+workerid+"";
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
                chuchai=getGson().fromJson(json, new TypeToken<Chuchai>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return chuchai;
    }
    /**
     * 根据职工的姓名查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Chuchai> queryChuchaiName(String name,int intFirst,int recPerPage)
    {

        String path=HttpUtil.BASE_URL+"chuchai!queryChuchaiName.action?name="+name+"&intFirst="+intFirst+"&recPerPage="+recPerPage;
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
                chuchais =getGson().fromJson(json, new TypeToken<List<Chuchai>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return chuchais;
    }
    public int queryChuchaiNameCount(Map map)
    {
        int count=0;
        String path=HttpUtil.BASE_URL+"chuchai!queryChuchaiNameCount.action";
        json=loginPostData(path, map);

        try {
            if(json!="0")
            {
                JSONObject jsonObject=new JSONObject(json);
                count=jsonObject.getInt("count");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }
//    /**
//     * 添加请假　
//     * @param
//     * @return Ttables
//     */
//    public String AddChuchai(Map map) throws Exception
//    {
//      boolean  str2=false;
//        String str1="0";
//        String path=HttpUtil.BASE_URL+"chuchai!AddChuchai.action";
//        String result =loginPostData(path, map);
//        try {
//            if( result!="0")
//            {
//                JSONObject jsonObject=new JSONObject(result);
//                str2=jsonObject.getBoolean("str");
//            }
//            if(str2) str1="1";
//        }   catch (Exception e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
//
//        return str1;
//    }
    /**
     * 批复请假　
     * @param
     * @return Ttables
     */
    public boolean UpdateChuchai(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"chuchai!UpdateChuchai.action";
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
    public  boolean RepareChuchai(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"chuchai!RepareChuchai.action";
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
    public int UpdateStateChuchai(Map map) throws Exception
    {

        boolean  str2=false;
        int  str1=0;
        String path=HttpUtil.BASE_URL+"chuchai!UpdateStateChuchai.action";
        String result =loginPostData(path, map);
        try {
            if( result!="0")
            {
                JSONObject jsonObject=new JSONObject(result);
                str2=jsonObject.getBoolean("str");
            }
            if(str2) str1=1;
        }   catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str1;

    }
//    /**
//     * 根据ID修改密码
//     * @param
//     * @param
//     * */
//
//
//
//        public Chuchai updateUserPwd(int  id,String userPwd) throws Exception
//        {
//
//            String path = HttpUtil.BASE_URL+"chuchai!updateUserPwd.action?id="+id+"&userPwd="+userPwd+"";
//            URL url;
//            try {
//                url = new URL(path);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(5000);
//                conn.setRequestMethod("POST");
//                if (200 == conn.getResponseCode())
//                {
//                    //获取输入流
//                    InputStream is= conn.getInputStream();
//                    ToolsHandler toolsHandler=new ToolsHandler();
//                    byte[] data=toolsHandler.InputStreamToByte(is);
//                    json=new String(data);
//                    System.out.println(json);
//                    chuchai=getGson().fromJson(json, new TypeToken<Chuchai>() {}.getType());
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return chuchai;
//        }
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
    public List<Chuchai> queryChuchaiOther(Map map)
    {
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path=HttpUtil.BASE_URL+"chuchai!queryChuchaiOther.action";

        json=loginPostData(path, map);
//        System.out.println(json);
       chuchais=getGson().fromJson(json, new TypeToken<List<Chuchai>>() {}.getType());
        return chuchais;
    }
    /**
     * 添加请假　
     * @param
     * @return Ttables
     */
    public boolean AddChuchai(Map map) throws Exception
    {

        String path = HttpUtil.BASE_URL+"chuchai!AddChuchai.action";
        json=loginPostData(path, map);
        JSONObject jsonObject=new JSONObject(json);
        str=jsonObject.getBoolean("str");
        return str;
    }

}
