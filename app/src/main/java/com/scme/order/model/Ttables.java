package com.scme.order.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Ttables entity. @author MyEclipse Persistence Tools
 */

public class Ttables implements java.io.Serializable {

	// Fields

	private Integer tabId;
	private String tabName;
	private Integer tstatus;
	private Integer tpersonNum;
	private String tdesc;
	private Set torderses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Ttables() {
	}

	/** full constructor */
	public Ttables(String tabName, Integer tstatus, Integer tpersonNum,
			String tdesc, Set torderses) {
		this.tabName = tabName;
		this.tstatus = tstatus;
		this.tpersonNum = tpersonNum;
		this.tdesc = tdesc;
		this.torderses = torderses;
	}

	// Property accessors

	public Integer getTabId() {
		return this.tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getTstatus() {
		return this.tstatus;
	}

	public void setTstatus(Integer tstatus) {
		this.tstatus = tstatus;
	}

	public Integer getTpersonNum() {
		return this.tpersonNum;
	}

	public void setTpersonNum(Integer tpersonNum) {
		this.tpersonNum = tpersonNum;
	}

	public String getTdesc() {
		return this.tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	public Set getTorderses() {
		return this.torderses;
	}

	public void setTorderses(Set torderses) {
		this.torderses = torderses;
	}

}