package com.scme.order.model;


/**
 * �û��־û���
 *
 */
public class Userinfo  implements java.io.Serializable{

	private int UserID	;
	 private String	BadgeNumber	;
	 private String	SSN	;
	 private String	name;
	 private String	Gender;
	 private String	Title;
	 private String Pager;	
	private String Birthday;
	private String HiredDay;
	 private String	Street;
	 private String	City;
	 private String	State;	
	 private String	ZIP	;
	 private String	OPhone;	
	 private String	FPhone	;
	 private int	VERIFICATIONMETHOD;
	 private int	DEFAULTDEPTID	;
	 private int	SECURITYFLAGS	;
	 private int	ATT	;
	 private int	INLATE;	
	 private int	OUTEARLY;	
	 private int	OVERTIME;	
	 private int	SEP	;
	 private int	HOLIDAY;	
	 private String	MINZU	;
	 private String	PASSWORD;	
	 private int	LUNCHDURATION;	
	 private String	MVERIFYPASS	;
//	private Image PHOTO;
//	private Image Notes	;
	private int	Privilege;	
	private int	InheritDeptSch;	
	private int	InheritDeptSchClass	;
	private int	AutoSchPlan	;
	private int	MinAutoSchInterval;	
	private int	RegisterOT	;
	private int	InheritDeptRule;	
	private int	EMPRIVILEGE	;
	 private String	CardNo	;
	 private Departments departments;
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getBadgeNumber() {
		return BadgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		BadgeNumber = badgeNumber;
	}
	public String getSSN() {
		return SSN;
	}
	public void setSSN(String sSN) {
		SSN = sSN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getPager() {
		return Pager;
	}
	public void setPager(String pager) {
		Pager = pager;
	}

	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZIP() {
		return ZIP;
	}
	public void setZIP(String zIP) {
		ZIP = zIP;
	}
	public String getOPhone() {
		return OPhone;
	}
	public void setOPhone(String oPhone) {
		OPhone = oPhone;
	}
	public String getFPhone() {
		return FPhone;
	}
	public void setFPhone(String fPhone) {
		FPhone = fPhone;
	}
	public int getVERIFICATIONMETHOD() {
		return VERIFICATIONMETHOD;
	}
	public void setVERIFICATIONMETHOD(int vERIFICATIONMETHOD) {
		VERIFICATIONMETHOD = vERIFICATIONMETHOD;
	}
	public int getDEFAULTDEPTID() {
		return DEFAULTDEPTID;
	}
	public void setDEFAULTDEPTID(int dEFAULTDEPTID) {
		DEFAULTDEPTID = dEFAULTDEPTID;
	}
	public int getSECURITYFLAGS() {
		return SECURITYFLAGS;
	}
	public void setSECURITYFLAGS(int sECURITYFLAGS) {
		SECURITYFLAGS = sECURITYFLAGS;
	}
	public int getATT() {
		return ATT;
	}
	public void setATT(int aTT) {
		ATT = aTT;
	}
	public int getINLATE() {
		return INLATE;
	}
	public void setINLATE(int iNLATE) {
		INLATE = iNLATE;
	}
	public int getOUTEARLY() {
		return OUTEARLY;
	}
	public void setOUTEARLY(int oUTEARLY) {
		OUTEARLY = oUTEARLY;
	}
	public int getOVERTIME() {
		return OVERTIME;
	}
	public void setOVERTIME(int oVERTIME) {
		OVERTIME = oVERTIME;
	}
	public int getSEP() {
		return SEP;
	}
	public void setSEP(int sEP) {
		SEP = sEP;
	}
	public int getHOLIDAY() {
		return HOLIDAY;
	}
	public void setHOLIDAY(int hOLIDAY) {
		HOLIDAY = hOLIDAY;
	}
	public String getMINZU() {
		return MINZU;
	}
	public void setMINZU(String mINZU) {
		MINZU = mINZU;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public int getLUNCHDURATION() {
		return LUNCHDURATION;
	}
	public void setLUNCHDURATION(int lUNCHDURATION) {
		LUNCHDURATION = lUNCHDURATION;
	}
	public String getMVERIFYPASS() {
		return MVERIFYPASS;
	}
	public void setMVERIFYPASS(String mVERIFYPASS) {
		MVERIFYPASS = mVERIFYPASS;
	}
	
	public int getPrivilege() {
		return Privilege;
	}
	public void setPrivilege(int privilege) {
		Privilege = privilege;
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
	public int getAutoSchPlan() {
		return AutoSchPlan;
	}
	public void setAutoSchPlan(int autoSchPlan) {
		AutoSchPlan = autoSchPlan;
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
	public int getInheritDeptRule() {
		return InheritDeptRule;
	}
	public void setInheritDeptRule(int inheritDeptRule) {
		InheritDeptRule = inheritDeptRule;
	}
	public int getEMPRIVILEGE() {
		return EMPRIVILEGE;
	}
	public void setEMPRIVILEGE(int eMPRIVILEGE) {
		EMPRIVILEGE = eMPRIVILEGE;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public Departments getDepartments() {
		return departments;
	}
	public void setDepartments(Departments departments) {
		this.departments = departments;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		Birthday = birthday;
	}

	public String getHiredDay() {
		return HiredDay;
	}

	public void setHiredDay(String hiredDay) {
		HiredDay = hiredDay;
	}
}