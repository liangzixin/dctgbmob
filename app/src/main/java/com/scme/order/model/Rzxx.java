package com.scme.order.model;

/**
 * Created by Administrator on 2018-03-12.
 */
public class Rzxx  implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer txxxid;
    private Integer rznd;
    private String rzjk;
    private String rzsj;
    private String rzzb;
    private String rzdd;
    private String rzrj;

    private String tbrzjg="";
    private String tbrzzb="";
    private String tbrzfz="";
    private String rzlxdh="";
    private String tbsm="";
    private String drzxm="";
    private String  drzgx="";

    private String  sbjt="";
    private Txxx txxx;
    public Txxx getTxxx() {
        return txxx;
    }
    public void setTxxx(Txxx txxx) {
        this.txxx = txxx;
    }
    public Integer getTxxxid() {
        return txxxid;
    }
    public void setTxxxid(Integer txxxid) {
        this.txxxid = txxxid;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRznd() {
        return rznd;
    }
    public void setRznd(Integer rznd) {
        this.rznd = rznd;
    }
    public String getRzjk() {
        return rzjk;
    }
    public void setRzjk(String rzjk) {
        this.rzjk = rzjk;
    }
    public String getRzsj() {
        return rzsj;
    }
    public void setRzsj(String rzsj) {
        this.rzsj = rzsj;
    }
    public String getRzzb() {
        return rzzb;
    }
    public void setRzzb(String rzzb) {
        this.rzzb = rzzb;
    }
    public String getRzdd() {
        return rzdd;
    }
    public void setRzdd(String rzdd) {
        this.rzdd = rzdd;
    }

    public String getTbrzzb() {
        return tbrzzb;
    }
    public void setTbrzzb(String tbrzzb) {
        this.tbrzzb = tbrzzb;
    }
    public String getTbrzjg() {
        return tbrzjg;
    }
    public void setTbrzjg(String tbrzjg) {
        this.tbrzjg = tbrzjg;
    }
    public String getTbrzfz() {
        return tbrzfz;
    }
    public void setTbrzfz(String tbrzfz) {
        this.tbrzfz = tbrzfz;
    }
    public String getRzlxdh() {
        return rzlxdh;
    }
    public void setRzlxdh(String rzlxdh) {
        this.rzlxdh = rzlxdh;
    }
    public String getTbsm() {
        return tbsm;
    }
    public void setTbsm(String tbsm) {
        this.tbsm = tbsm;
    }
    public String getDrzxm() {
        return drzxm;
    }
    public void setDrzxm(String drzxm) {
        this.drzxm = drzxm;
    }
    public String getDrzgx() {
        return drzgx;
    }
    public void setDrzgx(String drzgx) {
        this.drzgx = drzgx;
    }

    public String getSbjt() {
        return sbjt;
    }
    public void setSbjt(String sbjt) {
        this.sbjt = sbjt;
    }
    public String getRzrj() {
        return rzrj;
    }
    public void setRzrj(String rzrj) {
        this.rzrj = rzrj;
    }



}