package kr.co.yooooon.common.servlet.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;

public class InternalResourceViewResolver {
	private String prefix;
	private String postfix;
	private static InternalResourceViewResolver instance;
	
	private InternalResourceViewResolver(ServletContext application){
		String filename=application.getRealPath(application.getInitParameter("pathFile"));
		Properties properties=new Properties();
				try {
					properties.load(new FileInputStream(filename));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		
		for(Object o: properties.keySet()){
			String key=(String)o;
			if(key.equals("prefix"))
					prefix=(String)properties.get(key);
			else
					postfix=(String)properties.get(key);
		}		
	}
	public static InternalResourceViewResolver getInstance(ServletContext application){
		if(instance==null) instance= new  InternalResourceViewResolver(application);
		return instance;
	}
	
	public void resolveView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String viewname=modelAndView.getViewname();
		if(viewname.indexOf("redirect:")==-1){
			HashMap<String,Object> modelObject=modelAndView.getModelObject();
			if(modelObject!=null){
				for(String key : modelObject.keySet()){
					request.setAttribute(key, modelObject.get(key));
				}
			}
			String path=prefix+viewname+postfix;
			System.out.println(path);
			request.getRequestDispatcher(path).forward(request, response);
		}else{
			String path=viewname.substring(viewname.indexOf(":")+1);
			response.sendRedirect(request.getContextPath()+"/"+path);
		}
	}
}
