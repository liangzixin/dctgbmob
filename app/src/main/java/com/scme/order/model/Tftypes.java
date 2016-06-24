package com.scme.order.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Tftypes entity. @author MyEclipse Persistence Tools
 */

public class Tftypes implements java.io.Serializable {

	// Fields

	private Integer tid;
	private String tname;
	private String tnote;
	private Set tfoodses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tftypes() {
	}

	/** full constructor */
	public Tftypes(String tname, String tnote, Set tfoodses) {
		this.tname = tname;
		this.tnote = tnote;
		this.tfoodses = tfoodses;
	}

	// Property accessors

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTnote() {
		return this.tnote;
	}

	public void setTnote(String tnote) {
		this.tnote = tnote;
	}

	public Set getTfoodses() {
		return this.tfoodses;
	}

	public void setTfoodses(Set tfoodses) {
		this.tfoodses = tfoodses;
	}

}