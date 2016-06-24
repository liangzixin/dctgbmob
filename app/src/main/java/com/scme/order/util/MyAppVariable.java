package com.scme.order.util;

import android.app.Application;

import com.scme.order.model.Chuchai;
import com.scme.order.model.Placard;
import com.scme.order.model.Qingjia;
import com.scme.order.model.Tusers;
import com.scme.order.model.Txxx;
import com.scme.order.model.Ysry;

import java.util.List;
import java.util.Map;

public class MyAppVariable extends Application {

    private Tusers tusers;
    private List<Ysry> ysryList;
    private List<Placard> listplacard;
    private List list;
    private List<Qingjia> qingjias;
    public List<Placard> getListplacard() {
        return listplacard;
    }

    public void setListplacard(List<Placard> listplacard) {
        this.listplacard = listplacard;
    }



    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }



    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String query="";

    public List<Ysry> getYsryList() {
        return ysryList;
    }

    public void setYsryList(List<Ysry> ysryList) {
        this.ysryList = ysryList;
    }

    public List<Tusers> getListuser() {
        return listuser;
    }

    public void setListuser(List<Tusers> listuser) {
        this.listuser = listuser;
    }

    private  List<Tusers> listuser;
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private Map<String, String> map ;

    public List<Chuchai> getChuchais() {
        return chuchais;
    }

    public void setChuchais(List<Chuchai> chuchais) {
        this.chuchais = chuchais;
    }

    private List<Chuchai> chuchais;


    public Boolean getOtherquery() {
        return otherquery;
    }

    public void setOtherquery(Boolean otherquery) {
        this.otherquery = otherquery;
    }

    private  Boolean otherquery=false;

    public List<Txxx> getTxxxs() {
        return txxxs;
    }

    public void setTxxxs(List<Txxx> txxxs) {
        this.txxxs = txxxs;
    }

    private List<Txxx> txxxs;

    public List<Qingjia> getQingjias() {
        return qingjias;
    }

    public void setQingjias(List<Qingjia> qingjias) {
        this.qingjias = qingjias;
    }



    public int getIntFrist() {
        return intFrist;
    }

    public void setIntFrist(int intFrist) {
        this.intFrist = intFrist;
    }

    private int intFrist;

    public int getRecPerPage() {
        return recPerPage;
    }

    public void setRecPerPage(int recPerPage) {
        this.recPerPage = recPerPage;
    }

    private int recPerPage;
    public int getTxxxid() {
        return txxxid;
    }

    public void setTxxxid(int txxxid) {
        this.txxxid = txxxid;
    }

    private  int txxxid;




    public Tusers getTusers() {
        return tusers;
    }

    public void setTusers(Tusers tusers) {
        this.tusers = tusers;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//        setLabel("Welcome!"); //初始化全局变量
    }
}
