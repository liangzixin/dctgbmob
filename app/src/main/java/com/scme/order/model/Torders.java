package com.scme.order.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Torders entity. @author MyEclipse Persistence Tools
 */

public class Torders implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Ttables ttables;
	private Integer isCheckOut;
	private Double oprice;
	private String odate;
	private Integer tablesId;


	private Set tordersDetailses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Torders() {
	}

	/** full constructor */
	public Torders(Ttables ttables, Integer isCheckOut, Double oprice,
				   String odate,Integer tablesId, Set tordersDetailses) {
		this.ttables = ttables;
		this.isCheckOut = isCheckOut;
		this.oprice = oprice;
		this.odate = odate;
		this.tablesId=tablesId;
		this.tordersDetailses = tordersDetailses;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Ttables getTtables() {
		return this.ttables;
	}

	public void setTtables(Ttables ttables) {
		this.ttables = ttables;
	}

	public Integer getIsCheckOut() {
		return this.isCheckOut;
	}

	public void setIsCheckOut(Integer isCheckOut) {
		this.isCheckOut = isCheckOut;
	}

	public Double getOprice() {
		return this.oprice;
	}

	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}

	public String getOdate() {
		return this.odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
	}

	public Integer getTablesId() {
		return tablesId;
	}

	public void setTablesId(Integer tablesId) {
		this.tablesId = tablesId;
	}

	public Set getTordersDetailses() {
		return this.tordersDetailses;
	}

	public void setTordersDetailses(Set tordersDetailses) {
		this.tordersDetailses = tordersDetailses;
	}

}