package com.scme.order.model;


public class Departments  implements java.io.Serializable{
	

	
	private int Deptid;
	private String Deptname;
	private int SupDeptid;
	private int InheritParentSch;
	private int InheritDeptSch;
	private int InheritDeptSchClass;
	private int InLate;
	private int OutEarly;
	private int InheritDeptRule;
	private int MinAutoSchInterval;
	private int RegisterOT;
	private int DefaultSchId;
	private int ATT;
	private int Holiday;
	private int OverTime;
	public int getDeptid() {
		return Deptid;
	}
	public void setDeptid(int deptid) {
		Deptid = deptid;
	}
	public String getDeptname() {
		return Deptname;
	}
	public void setDeptname(String deptname) {
		Deptname = deptname;
	}
	public int getSupDeptid() {
		return SupDeptid;
	}
	public void setSupDeptid(int supDeptid) {
		SupDeptid = supDeptid;
	}
	public int getInheritParentSch() {
		return InheritParentSch;
	}
	public void setInheritParentSch(int inheritParentSch) {
		InheritParentSch = inheritParentSch;
	}
	public int getInheritDeptSch() {
		return InheritDeptSch;
	}
	public void setInheritDeptSch(int inheritDeptSch) {
		InheritDeptSch = inheritDeptSch;
	}
	public int getInheritDeptSchClass() {
		return InheritDeptSchClass;
	}
	public void setInheritDeptSchClass(int inheritDeptSchClass) {
		InheritDeptSchClass = inheritDeptSchClass;
	}
	public int getInLate() {
		return InLate;
	}
	public void setInLate(int inLate) {
		InLate = inLate;
	}
	public int getOutEarly() {
		return OutEarly;
	}
	public void setOutEarly(int outEarly) {
		OutEarly = outEarly;
	}
	public int getInheritDeptRule() {
		return InheritDeptRule;
	}
	public void setInheritDeptRule(int inheritDeptRule) {
		InheritDeptRule = inheritDeptRule;
	}
	public int getMinAutoSchInterval() {
		return MinAutoSchInterval;
	}
	public void setMinAutoSchInterval(int minAutoSchInterval) {
		MinAutoSchInterval = minAutoSchInterval;
	}
	public int getRegisterOT() {
		return RegisterOT;
	}
	public void setRegisterOT(int registerOT) {
		RegisterOT = registerOT;
	}
	public int getDefaultSchId() {
		return DefaultSchId;
	}
	public void setDefaultSchId(int defaultSchId) {
		DefaultSchId = defaultSchId;
	}
	public int getATT() {
		return ATT;
	}
	public void setATT(int aTT) {
		ATT = aTT;
	}
	public int getHoliday() {
		return Holiday;
	}
	public void setHoliday(int holiday) {
		Holiday = holiday;
	}
	public int getOverTime() {
		return OverTime;
	}
	public void setOverTime(int overTime) {
		OverTime = overTime;
	}

	public String getSubname1(int len){
		if(this.getDeptname().length()>=4)
		{
			len=4;
		}else{
			len=this.getDeptname().length();
		}
		return this.getDeptname().substring(0,len);
	}
}
