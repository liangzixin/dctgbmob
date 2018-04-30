package com.scme.order.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;	//导入java.text.SimpleDateFormat类
/**
 * Teats entity. @author MyEclipse Persistence Tools
 */

public class Teats implements java.io.Serializable {

	// Fields
	private Integer eatid;
	private Integer workerid;
	private java.lang.Integer eatnumber;

	public String getEatdate() {
		return eatdate;
	}

	public void setEatdate(String eatdate) {
		this.eatdate = eatdate;
	}

	private String eatdate;
	private java.lang.String  eatdate1;
	private Integer unitprice ;
	private java.lang.Integer departmentid;
	private java.lang.String operator;
	private java.lang.String unclear;
	private java.lang.Integer eatid1=2;
	private Branch branch;
	private Tusers user;

	public int getCountfs() {
		return countfs;
	}

	public void setCountfs(int countfs) {
		this.countfs = countfs;
	}

	private int countfs;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private int count;

	public int getCountmoney() {
		return countmoney;
	}

	public void setCountmoney(int countmoney) {
		this.countmoney = countmoney;
	}

	private int countmoney;
//	private Set user = new HashSet(0);

//	DateProcessing dateProcessing=new DateProcessing();
	/** default constructor */
//	public Teats() {
//	}
//
//	/** full constructor */
//	public Teats(Integer workerid, Integer eatnumber,
//				 Integer unitprice,Integer departmentid,String operator,String unclear) {
//		this.workerid = workerid;
//		this.eatnumber=eatnumber;
//       //this.eatdate1=eatdate1;
//		this.departmentid=departmentid;
//		this.operator=operator;
//		this.unclear=unclear;
//		this.unitprice=unitprice;
//	}

	public Integer getEatid() {
		return eatid;
	}

	public void setEatid(Integer eatid) {
		this.eatid = eatid;
	}
	public Integer getWorkerid() {
		return workerid;
	}
	public void setWorkerid(Integer workerid) {
		this.workerid = workerid;

	}
	public java.lang.Integer getEatnumber() {
		return eatnumber;
	}
	public void setEatnumber(java.lang.Integer eatnumber) {
		this.eatnumber = eatnumber;
	}

	public java.lang.Integer getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(java.lang.Integer unitprice) {
		this.unitprice = unitprice;
	}
	public java.lang.Integer getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(java.lang.Integer departmentid) {
		this.departmentid = departmentid;

	}
	public java.lang.String getOperator() {
		return operator;
	}
	public void setOperator(java.lang.String operator) {
		this.operator = operator;

	}
	public java.lang.String getUnclear() {
		return unclear;
	}
	public void setUnclear(java.lang.String unclear) {
		this.unclear = unclear;
	}

	public void setEatid1(java.lang.Integer eatid1) {
		this.eatid1 = eatid1;
	}
	public java.lang.Integer getEatid1() {
		return eatid1;
	}

public Tusers getUser() {
	return user;
}

	public void setUser(Tusers user) {
		this.user = user;
	}

	public String getEatdate1() {
		return eatdate1;
	}

	public void setEatdate1(String eatdate1) {
		this.eatdate1 = eatdate1;
	}
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

}