package com.scme.order.service;

import com.google.gson.reflect.TypeToken;
import com.scme.order.model.Tusers;
import com.scme.order.util.HttpUtil;
import com.scme.order.util.ToolsHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BranchService extends BaseService{

    private List list1=null;
    private String json="";
    /**
     * 用户登陆
    *查询出科室名称
     * */

    public List  QueryBranch() throws Exception
    {
        String path = HttpUtil.BASE_URL+"branch!queryBranch.action";
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
               list1 =getGson().fromJson(json, new TypeToken<List>() {}.getType());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list1;
    }


}
