package com.reqman.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "index", eager = true)
@SessionScoped
public class Index implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	//validate login
	public String welcomepage() {
		
		return "index";
	}

}
