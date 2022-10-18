package kr.co.yooooon.common.servlet.mapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.co.yooooon.common.servlet.context.ApplicationContext;
import kr.co.yooooon.common.servlet.mvc.Controller;

public class SimpleUrlHandlerMapping {
	public static SimpleUrlHandlerMapping getInstance(ServletContext application) {
		if (instance == null)
			instance = new SimpleUrlHandlerMapping(application);
		return instance;
	}

	private static SimpleUrlHandlerMapping instance;

	private HashMap<String, String> beanNames;

	private SimpleUrlHandlerMapping(ServletContext application) {
		beanNames = new HashMap<String, String>();
		String filename = application.getRealPath(application.getInitParameter("urlmappingFile"));
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object key : properties.keySet()) {
			String value = properties.getProperty((String) key);
			beanNames.put((String) key, value);
		}
	}

	public Controller getController(ApplicationContext applicationContext, HttpServletRequest request) {
		String uri = request.getRequestURI(); 				// uri= /insa/base/positionList.html
		System.out.println("요청uri:"+uri);
		String contextPath = request.getContextPath(); 		// contextPath= /insa
		String key = uri.substring(contextPath.length());	// key= /base/positionList.html
		String beanName = beanNames.get(key); 				// beanName="urlFilenameViewController"
		System.out.println("beanName:" + beanName);
		if (beanName == null)
			beanName = "unknownURLController";
		return applicationContext.getBean(beanName);
	}
}
