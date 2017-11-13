/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.haroldjcastillo.btc.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author harold.castillo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffOrderPayload {

	private long d;
	private String r;
	private long t;
	private String a;
	private String v;
	private String o;
	private String s;

	public long getD() {
		return d;
	}

	public void setD(long d) {
		this.d = d;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

}