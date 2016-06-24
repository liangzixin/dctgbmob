package com.scme.order.model;


import java.util.*;


public class Zbmz implements java.io.Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String zbmz;
	public  Zbmz(){};
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getZbmz() {
		return zbmz;
	}
	public void setZbmz(String zbmz) {
		this.zbmz = zbmz;
	}
	public String getSubname1(int len){
		if(this.zbmz.length()>12)
		{
			len=12;
		}else{
			len=this.zbmz.length();
		}
		return this.zbmz.substring(0,len);
	}
 

}
