package com.scme.order.model;



public class Qingjia {
   private int id;
   private String name1;
	private int bmbh;
	private String content;
	private String time1;

	private String time2;

	private int state;

	private int type1;
	private Branch branch;
	private String djr;
	private String leader;
	private String leader1;
	private String leader2;
	private double countday=0.0;
	private int count;

	public double getCountd() {
		return countd;
	}

	public void setCountd(double countd) {
		this.countd = countd;
	}

	private double countd;


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



	public double getCountday() {
		return countday;
	}

	public void setCountday(double countday) {
		this.countday = countday;
	}



	public int getType1() {
		return type1;
	}

	public void setType1(int type1) {
		this.type1 = type1;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public String getLeader2() {
		return leader2;
	}

	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}


	public String getLeader1() {
		return leader1;
	}

	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}


	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}



	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}


	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public int getBmbh() {
	return bmbh;
}
public void setBmbh( int bmbh) {
	this.bmbh = bmbh;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName1() {
	return name1;
}
public void setName1(String name1) {
	this.name1 = name1;
}


public void setBranch(Branch branch) {
	this.branch = branch;
}
public Branch getBranch() {
	return branch;
}
public void setDjr(String djr) {
	this.djr = djr;
}
public String getDjr() {
	return djr;
}

	public String getSubname1(int len){
		if(this.getBranch().getName().length()>=4)
		{
			len=4;
		}else{
			len=this.getBranch().getName().length();
		}
		return this.getBranch().getName().substring(0,len);
	}
}
