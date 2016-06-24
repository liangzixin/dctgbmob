package com.scme.order.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Tfoods entity. @author MyEclipse Persistence Tools
 */

public class Tfoods implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Tftypes tftypes;
	private String fname;
	private String fpicName;
	private Double fprice;
	private String fdesc;
	private Integer typeId;

	private Set tordersDetailses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tfoods() {
	}

	/** minimal constructor */
	public Tfoods(Tftypes tftypes) {
		this.tftypes = tftypes;
	}

	/** full constructor */
	public Tfoods(Tftypes tftypes, String fname, String fpicName,
			Double fprice, String fdesc,Integer typeId, Set tordersDetailses) {
		this.tftypes = tftypes;
		this.fname = fname;
		this.fpicName = fpicName;
		this.fprice = fprice;
		this.fdesc = fdesc;
		this.typeId=typeId;
		this.tordersDetailses = tordersDetailses;
	}

	// Property accessors

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Tftypes getTftypes() {
		return this.tftypes;
	}

	public void setTftypes(Tftypes tftypes) {
		this.tftypes = tftypes;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFpicName() {
		return this.fpicName;
	}

	public void setFpicName(String fpicName) {
		this.fpicName = fpicName;
	}

	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	public String getFdesc() {
		return this.fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Set getTordersDetailses() {
		return this.tordersDetailses;
	}

	public void setTordersDetailses(Set tordersDetailses) {
		this.tordersDetailses = tordersDetailses;
	}

}