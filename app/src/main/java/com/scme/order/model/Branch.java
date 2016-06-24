package com.scme.order.model;



import java.util.*;


public class Branch  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private String tel;
    private String memo;
    private int leader;
    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }


 
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getMemo() {
        return memo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getSubname1(int len){
        if(this.name.length()>6)
        {
            len=6;
        }else{
            len=this.name.length();
        }
        return this.name.substring(0,len);
    }


}
