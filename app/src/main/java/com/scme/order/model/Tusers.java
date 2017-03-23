package com.scme.order.model;

//import java.util.Date;

public class Tusers implements java.io.Serializable {

	private String userName;

	private int id;

	private String pwd;
	private String name;
	private String purview;
	private int branchid;
	private int job;
	private String sex;
	private String email;
	private String  tel;
	private String address;
	private int bestMan;
	private String workersfz;

	private String diskutil;

	private String single;

	public String getBirday() {
		return birday;
	}

	//	private Date birthday;
	private String birday;
	private String picName;

	private Branch branch;
	private int deptid;
	private int userinfoid;
//	GetDate getDate=new GetDate();
	// Constructors

	/** default constructor */
	public Tusers() {
	}

	/** full constructor */
	public Tusers(String userName, String userPwd) {
		this.userName = userName;
		this.pwd = userPwd;



	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurview() {
		return purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	public int getBranchid() {
		return branchid;
	}

	public void setBranchid(int branchid) {
		this.branchid = branchid;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getBestMan() {
		return bestMan;
	}

	public void setBestMan(int bestMan) {
		this.bestMan = bestMan;
	}

	public String getWorkersfz() {
		return workersfz;
	}

	public void setWorkersfz(String workersfz) {
		this.workersfz = workersfz;
	}

	public String getDiskutil() {
		return diskutil;
	}

	public void setDiskutil(String diskutil) {
		this.diskutil = diskutil;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

//	public Date getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(Date birthday) {
//		this.birthday = birthday;
//	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}



	public String getDtoC2( ){

		return this.setBirday("bbccdd");
	}

	public String getSubname1(int len){
		if(this.name.length()>22)
		{
			len=22;
		}else{
			len=this.name.length();
		}
		return this.name.substring(0,len);
	}

	public String getSubname(){
		int len=0;
		if(this.branch.getName().length()>8)
		{
			len=8;
		}else{
			len=this.branch.getName().length();
		}
		return this.branch.getName().substring(0,len);
	}

	public String setBirday(String birday) {
		this.birday = birday;
		return birday;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}


	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getUserinfoid() {
		return userinfoid;
	}

	public void setUserinfoid(int userinfoid) {
		this.userinfoid = userinfoid;
	}
}