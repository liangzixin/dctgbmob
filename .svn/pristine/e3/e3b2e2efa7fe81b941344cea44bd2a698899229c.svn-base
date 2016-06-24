package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Dydh;
import com.scme.order.model.Dydh;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DydhService extends BaseService{
    private Dydh dydh=null;
    private String json="";
    private List<Dydh> dydhs=null;


    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Dydh> QueryAllDydh() throws Exception
    {
        String path = HttpUtil.BASE_URL+"dydh!queryAllUsers.action";
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
                dydhs =getGson().fromJson(json, new TypeToken<List<Dydh>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dydhs;
    }
    /**
     * 根据党员的Id查询职工的信息
     * @param id
     * @return Ttables
     */
    public Dydh queryUseDydhId(int id) throws Exception
    {
        String json="";
        Dydh dydh=null;
        String path = HttpUtil.BASE_URL+"dydh!queryUseDydhId.action?id="+id+"";
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
                dydh=getGson().fromJson(json, new TypeToken<Dydh>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dydh;
    }
    /**
     * 查询所有菜单
     * @return List<Tfoods> foods
     */
    public List<Dydh> queryTodayBirthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"dydh!queryTodayBirthday.action";
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
                dydhs =getGson().fromJson(json, new TypeToken<List<Dydh>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dydhs;
    }
    /**
     * 查询未来7天生日记录
     * @return List<Tfoods> foods
     */
    public List<Dydh> queryToday7Birthday() throws Exception
    {
        String path = HttpUtil.BASE_URL+"dydh!queryToday7Birthday.action";
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
                dydhs =getGson().fromJson(json, new TypeToken<List<Dydh>>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dydhs;
    }

}
