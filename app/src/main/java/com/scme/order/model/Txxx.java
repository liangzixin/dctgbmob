package com.scme.order.model;


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
	private String rz13jk;
	private String rz13sj;
	private String rz13zb;
	private String rz13dd;
	private String rz14jk;
	private String rz14sj;
	private String rz14zb;
	private String rz14dd;
	private String rz15jk;
	private String rz15sj;
	private String rz15zb;
	private String rz15dd;

	private String remark;
//	private Date  rzrj;
	private Branch branch;
	private String sendok;
	private int count;
	private String photo="";
	private String sfzzm="";
	private String sfzfm="";
	private String sbjt="";
	private String tbsm="";
	private String sfzfyj="";

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
	public void setRz13jk(String rz13jk) {
		this.rz13jk = rz13jk;
	}
	public String getRz13jk() {
		return rz13jk;
	}
	public void setRz13sj(String rz13sj) {
		this.rz13sj = rz13sj;
	}
	public String getRz13sj() {
		return rz13sj;
	}
	public void setRz13zb(String rz13zb) {
		this.rz13zb = rz13zb;
	}
	public String getRz13zb() {
		return rz13zb;
	}
	public void setRz13dd(String rz13dd) {
		this.rz13dd = rz13dd;
	}
	public String getRz13dd() {
		return rz13dd;
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
//	public void setRzrj(Date rzrj) {
//		this.rzrj = rzrj;
//	}
//	public Date getRzrj() {
//		return rzrj;
//	}
	public void setRz14jk(String rz14jk) {
		this.rz14jk = rz14jk;
	}
	public String getRz14jk() {
		return rz14jk;
	}
	public void setRz14sj(String rz14sj) {
		this.rz14sj = rz14sj;
	}
	public String getRz14sj() {
		return rz14sj;
	}
	public void setRz14zb(String rz14zb) {
		this.rz14zb = rz14zb;
	}
	public String getRz14zb() {
		return rz14zb;
	}
	public void setRz14dd(String rz14dd) {
		this.rz14dd = rz14dd;
	}
	public String getRz14dd() {
		return rz14dd;
	}
	public void setSendok(String sendok) {
		this.sendok = sendok;
	}
	public String getSendok() {
		return sendok;
	}
	public String getRz15jk() {
		return rz15jk;
	}
	public void setRz15jk(String rz15jk) {
		this.rz15jk = rz15jk;
	}
	public String getRz15sj() {
		return rz15sj;
	}
	public void setRz15sj(String rz15sj) {
		this.rz15sj = rz15sj;
	}
	public String getRz15zb() {
		return rz15zb;
	}
	public void setRz15zb(String rz15zb) {
		this.rz15zb = rz15zb;
	}
	public String getRz15dd() {
		return rz15dd;
	}
	public void setRz15dd(String rz15dd) {
		this.rz15dd = rz15dd;
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

	public String getSbjt() {
		return sbjt;
	}

	public void setSbjt(String sbjt) {
		this.sbjt = sbjt;
	}

	public String getTbsm() {
		return tbsm;
	}

	public void setTbsm(String tbsm) {
		this.tbsm = tbsm;
	}

	public String getSfzfyj() {
		return sfzfyj;
	}

	public void setSfzfyj(String sfzfyj) {
		this.sfzfyj = sfzfyj;
	}
}
