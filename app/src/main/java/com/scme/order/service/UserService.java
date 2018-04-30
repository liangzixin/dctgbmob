package com.scme.order.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.CItem;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Tfoods;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService extends BaseService {
    private Tusers user = null;
    private String json = "";
    private List<Tusers> users = null;
    private List list1 = null;
    private List<CItem> list2 = null;
    private boolean str=false;
    private int count=0;

    /**
     * 用户登陆
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return user Tusers对象
     */
    public Tusers login(String userName, String userPwd) throws Exception {
        String json = "";
        Tusers user = null;
        String path = HttpUtil.BASE_URL + "user!login.action?userName=" + userName + "&userPwd=" + userPwd + "";
        URL url = new URL(path);
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
            if (json != "0") {
                user = getGson().fromJson(json, new TypeToken<Tusers>() {
                }.getType());
            }
        }
        return user;
    }
    public boolean DeleteUserId(int  id) throws Exception
    {
        String path=HttpUtil.BASE_URL+"user!DeleteUserId.action?Id="+id+"";
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
     *
     * @return List<Tfoods> foods
     */
    public List<Tusers> QueryAllUsers( int intFirst,int recPerPage) throws Exception
    { String path = HttpUtil.BASE_URL + "user!queryAllUsers.action?intFirst="+intFirst+"&recPerPage="+recPerPage+"";
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
                users = getGson().fromJson(json, new TypeToken<List<Tusers>>() { }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }
    /**
     * 查询记录数
     * @param
     * @return
     */
    public int queryUserCount()
    {
        String json="";
        int ordersId=0;
        String path = HttpUtil.BASE_URL+"user!queryUserCount.action";
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
     * 查询所有菜单
     *
     * @return List<Tfoods> foods
     */
    public List<Tusers> queryTodayBirthday() throws Exception {
        String path = HttpUtil.BASE_URL + "user!queryTodayBirthday.action";
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
                users = getGson().fromJson(json, new TypeToken<List<Tusers>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 查询未来7天生日记录
     *
     * @return List<Tfoods> foods
     */
    public List<Tusers> queryToday7Birthday() throws Exception {
        String path = HttpUtil.BASE_URL + "user!queryToday7Birthday.action";
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
                users = getGson().fromJson(json, new TypeToken<List<Tusers>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 根据职工的Id查询职工的信息
     *
     * @param workerid
     * @return Ttables
     */
    public Tusers queryUseUserId(int workerid) throws Exception {
        String json = "";
        Tusers user = null;
        String path = HttpUtil.BASE_URL + "user!queryUseUserId.action?id=" + workerid ;
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
                user = getGson().fromJson(json, new TypeToken<Tusers>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }
    /**
     * 修改请假　
     * @param
     * @return Ttables
     */
    public boolean UpdateUser(Map map) throws Exception
    {

        String path=HttpUtil.BASE_URL+"user!updateuser.action";
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
     *
     * @param
     * @param
     */


    public Tusers updateUserPwd(int id, String userPwd) throws Exception {

        String path = HttpUtil.BASE_URL + "user!updateUserPwd.action?id=" + id + "&userPwd=" + userPwd + "";
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
                user = getGson().fromJson(json, new TypeToken<Tusers>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 用户登陆
     * 查询出科室名称
     */

    public List QueryUserBranchId(int branchid) throws Exception {
        String path = HttpUtil.BASE_URL + "user!queryUserBranchId.action?branchid=" + branchid + "";
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
                list1 = getGson().fromJson(json, new TypeToken<List>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list1;
    }
    public List<CItem> QueryUserIdName(int branchid) throws Exception {
        String path = HttpUtil.BASE_URL + "user!queryUserIdName.action?branchid=" + branchid + "";
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
                list2= getGson().fromJson(json, new TypeToken<List<CItem>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list2;
    }
    public List<Tusers> QueryUserBranchIdOther(int branchid) throws Exception {
        String path = HttpUtil.BASE_URL + "user!queryUserBranchIdOther.action?branchid=" + branchid + "";
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
                users = getGson().fromJson(json, new TypeToken<List<Tusers>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }
    /**
     * 根据其它条件查询职工的信息
     *
     * @param
     * @return Ttables
     */
    public List<Tusers> queryUserOther(Map map) {
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path = HttpUtil.BASE_URL + "user!queryUserOther.action";

        json = loginPostData(path, map);
//        System.out.println(json);
        users = getGson().fromJson(json, new TypeToken<List<Tusers>>() {
        }.getType());
        return users;
    }
    /**
     * 发短信
     *
     * @param
     * @return Ttables
     */
    public int queryUserSend(Map map) {

        String path = HttpUtil.BASE_URL + "user!queryUserSend.action";

        json = loginPostData(path, map);
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
    public int  queryUserOtherCount(Map map) {
        int count=0;
//      map.put("intFirst",intFirst);
//        map.put("recPerPage",recPerPage);
        String path = HttpUtil.BASE_URL + "user!queryUserOtherCount.action";

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
     * 根据职工的姓名查询职工的信息
     * @param
     * @return Ttables
     */
    public List<Tusers> queryTuserName(String name)
    {

        String path=HttpUtil.BASE_URL+"user!queryTuserName.action?name="+name+"";
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
               users = getGson().fromJson(json, new TypeToken<List<Tusers>>() {
                }.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }
}