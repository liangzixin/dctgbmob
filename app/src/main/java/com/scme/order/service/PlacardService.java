package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Placard;
import com.scme.order.model.Placard;
import com.scme.order.model.Tusers;
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
import java.util.List;
import java.util.Map;

/**
 * Created by lzxyy on 2016/5/8.
 */
public class PlacardService extends BaseService {
    private String json = "";
    private int count = 0;
    private List<Placard> placards;
    private Placard placard;
    boolean str=false;
    /**
     * 查询所有菜单
     *
     * @return List<Tfoods> foods
     */
    public List<Placard> QueryAllPlacard(int intFirst, int recPerPage) throws Exception {
        String path = HttpUtil.BASE_URL + "placard!queryAllPlacard.action?intFirst=" + intFirst + "&recPerPage=" + recPerPage + "";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode()) {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler = new ToolsHandler();
                byte[] data = toolsHandler.InputStreamToByte(is);
                json = new String(data);
                System.out.println(json);
                placards = getGson().fromJson(json, new TypeToken<List<Placard>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return placards;
    }

    /**
     * 查询记录数
     *
     * @param
     * @return
     */
    public int queryPlacardCount() {

        String path = HttpUtil.BASE_URL + "placard!queryPlacardCount.action";
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode()) {
                //获取输入流
//
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler = new ToolsHandler();
                byte[] data = toolsHandler.InputStreamToByte(is);
                json = new String(data);
                System.out.println(json);
                if (json != "0") {
                    JSONObject jsonObject = new JSONObject(json);
                    count = jsonObject.getInt("count");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 根据其它条件查询职工的信息
     *
     * @param
     * @return Ttables
     */
    public List<Placard> queryPlacardOther(Map map) {
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path = HttpUtil.BASE_URL + "placard!queryPlacardOther.action";

        json = loginPostData(path, map);
//        System.out.println(json);
        placards = getGson().fromJson(json, new TypeToken<List<Placard>>() {
        }.getType());
        return placards;
    }
    public int  queryPlacardOtherCount(Map map) {

        String path = HttpUtil.BASE_URL + "placard!queryPlacardOtherCount.action";

        json = loginPostData(path, map);
//        System.out.println(json);
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
    public String loginPostData(String path, Map<String, String> map) {
        String json = "";
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
                ToolsHandler toolsHandler = new ToolsHandler();
                byte[] data1 = toolsHandler.InputStreamToByte(inputStream);
                json = new String(data1);
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
     * 根据职工的Id查询职工的信息
     *
     * @param workerid
     * @return Ttables
     */
    public Placard queryUsePlacardId(int workerid) throws Exception {

        String path = HttpUtil.BASE_URL + "placard!queryUsePlacardId.action?id=" + workerid ;
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            if (200 == conn.getResponseCode()) {
                //获取输入流
                InputStream is = conn.getInputStream();
                ToolsHandler toolsHandler = new ToolsHandler();
                byte[] data = toolsHandler.InputStreamToByte(is);
                json = new String(data);
                System.out.println(json);
               placard= getGson().fromJson(json, new TypeToken<Placard>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return placard;
    }
    /**
     * 根据ID删除记录
     * @param id 菜的Id
     * @param id 订单Id
     * */
    public boolean DeletePlacardId(int  id) throws Exception
    {
        String path=HttpUtil.BASE_URL+"placard!DeletePlacardId.action?Id="+id+"";
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
                    str=jsonObject.getBoolean("str");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
}