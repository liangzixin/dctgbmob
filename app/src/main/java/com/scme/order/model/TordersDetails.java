package com.scme.order.model;

/**
 * TordersDetails entity. @author MyEclipse Persistence Tools
 */

public class TordersDetails implements java.io.Serializable {

	// Fields

	private Integer odid;
	private Tfoods tfoods;
	private Torders torders;
	private Double amount;

	// Constructors

	/** default constructor */
	public TordersDetails() {
	}

	/** full constructor */
	public TordersDetails(Tfoods tfoods, Torders torders, Double amount) {
		this.tfoods = tfoods;
		this.torders = torders;
		this.amount = amount;
	}

	// Property accessors

	public Integer getOdid() {
		return this.odid;
	}

	public void setOdid(Integer odid) {
		this.odid = odid;
	}

	public Tfoods getTfoods() {
		return this.tfoods;
	}

	public void setTfoods(Tfoods tfoods) {
		this.tfoods = tfoods;
	}

	public Torders getTorders() {
		return this.torders;
	}

	public void setTorders(Torders torders) {
		this.torders = torders;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}