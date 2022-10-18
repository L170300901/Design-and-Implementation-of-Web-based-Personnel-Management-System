package kr.co.yooooon.common.servlet;

import java.util.HashMap;

public class ModelAndView {
	private String viewname;
	private HashMap<String,Object> modelObject;
	
	public ModelAndView(String viewname, HashMap<String,Object> modelObject){
		this.viewname=viewname;
		this.modelObject=modelObject;
	}
	public String getViewname(){ return viewname; }
	public HashMap<String,Object> getModelObject(){
		return modelObject;
	}
}
