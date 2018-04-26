package com.scme.order.model;

/**
 * Created by Administrator on 2018-04-26.
 */

public class Diningcard implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userid;
    private Integer topupamount;
    private String operator;

    public String getTopuptime() {
        return topuptime;
    }

    public void setTopuptime(String topuptime) {
        this.topuptime = topuptime;
    }

    private String topuptime;

    public Tusers getUser() {
        return user;
    }

    public void setUser(Tusers user) {
        this.user = user;
    }

    private Tusers user;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
    public Integer getTopupamount() {
        return topupamount;
    }
    public void setTopupamount(Integer topupamount) {
        this.topupamount = topupamount;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }



}