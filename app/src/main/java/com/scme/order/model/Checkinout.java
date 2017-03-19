package com.scme.order.model;

import java.util.Date;
import java.util.List;
import java.util.Set;



/**
 * ?????????
 *
 */
public class Checkinout  implements java.io.Serializable{
;
private int UserID;                
 private String checkTime;
   private String CheckType="I";      
   private int VerifyCode=1;           
   private String SensorID="1";          
   private String WorkCode="0";
   private String SN;          
   private int UserExtFmt;
   private String  tel;
  // private Userinfo userinfo;
   
public int getUserID() {
	return UserID;
}
public void setUserID(int userID) {
	UserID = userID;
}

public String getCheckType() {
	return CheckType;
}
public void setCheckType(String checkType) {
	CheckType = checkType;
}
public int getVerifyCode() {
	return VerifyCode;
}
public void setVerifyCode(int verifyCode) {
	VerifyCode = verifyCode;
}
public String getSensorID() {
	return SensorID;
}
public void setSensorID(String sensorID) {
	SensorID = sensorID;
}
public String getSN() {
	return SN;
}
public void setSN(String sN) {
	SN = sN;
}
public int getUserExtFmt() {
	return UserExtFmt;
}
public void setUserExtFmt(int userExtFmt) {
	UserExtFmt = userExtFmt;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}

public String getWorkCode() {
	return WorkCode;
}
public void setWorkCode(String workCode) {
	WorkCode = workCode;
}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

}