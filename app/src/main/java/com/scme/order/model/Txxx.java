package com.scme.order.model;


import java.util.List;
import java.util.Set;

public class Txxx implements java.io.Serializable {
	private int id;
	private int bmbh;
	private String grbh;
	private String name;
	private String sfzh;
	private String hkdz;
	private String czdz;
	private String yzbm;
	private String lxdh1;
	private String lxdh2;
	private String lxdh3;
	private String swsj;
	private String dfsj;
	private String rz12;

	private String rzzb;
	private String rzdd;


	private String remark;
//	private Date  rzrj;
	private Branch branch;
	private String sendok;
	private int count;
	private String photo="";
	private String sfzzm="";
	private String sfzfm="";

	private String sfzfyj="";

	private Set rzxx;

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setBmbh(int bmbh) {
		this.bmbh = bmbh;
	}
	public int getBmbh() {
		return bmbh;
	}
	public void setGrbh(String grbh) {
		this.grbh = grbh;
	}
	public String getGrbh() {
		return grbh;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setHkdz(String hkdz) {
		this.hkdz = hkdz;
	}
	public String getHkdz() {
		return hkdz;
	}
	public void setCzdz(String czdz) {
		this.czdz = czdz;
	}
	public String getCzdz() {
		return czdz;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getYzbm() {
		return yzbm;
	}
	public void setLxdh1(String lxdh1) {
		this.lxdh1 = lxdh1;
	}
	public String getLxdh1() {
		return lxdh1;
	}
	public void setLxdh2(String lxdh2) {
		this.lxdh2 = lxdh2;
	}
	public String getLxdh2() {
		return lxdh2;
	}
	public void setLxdh3(String lxdh3) {
		this.lxdh3 = lxdh3;
	}
	public String getLxdh3() {
		return lxdh3;
	}
	public void setSwsj(String swsj) {
		this.swsj = swsj;
	}
	public String getSwsj() {
		return swsj;
	}
	public void setDfsj(String dfsj) {
		this.dfsj = dfsj;
	}
	public String getDfsj() {
		return dfsj;
	}
	public void setRz12(String rz12) {
		this.rz12 = rz12;
	}
	public String getRz12() {
		return rz12;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Branch getBranch() {
		return branch;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSfzzm() {
		return sfzzm;
	}

	public void setSfzzm(String sfzzm) {
		this.sfzzm = sfzzm;
	}

	public String getSfzfm() {
		return sfzfm;
	}

	public void setSfzfm(String sfzfm) {
		this.sfzfm = sfzfm;
	}


	public String getSfzfyj() {
		return sfzfyj;
	}

	public void setSfzfyj(String sfzfyj) {
		this.sfzfyj = sfzfyj;
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


	public Set getRzxx() {
		return rzxx;
	}

	public void setRzxx(Set rzxx) {
		this.rzxx = rzxx;
	}
}
