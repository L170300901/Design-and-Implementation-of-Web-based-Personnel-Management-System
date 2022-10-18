package kr.co.yooooon.common.servlet.context;
import java.io.*;
import java.util.*;

import javax.servlet.*;

import kr.co.yooooon.common.servlet.mvc.*;
public class ApplicationContext {
	private HashMap<String,Object> beans;
	
	public ApplicationContext(ServletContext application, ServletConfig config){
		beans=new HashMap<String,Object>();
		String filename=application.getRealPath(config.getInitParameter("configFile"));
		//System.out.println("-------filename-------"+filename);
		Properties properties=new Properties();
		
		try {
			properties.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Object key: properties.keySet()){
			String classname=properties.getProperty((String)key);
			Object controller=null;
			try {
				System.out.println("@@@@@@@"+classname);
				controller = Class.forName(classname).newInstance();
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			beans.put((String)key,controller);
		}
	}
	
	public Controller getBean(String beanName){
		return (Controller)beans.get(beanName);
	}
}
