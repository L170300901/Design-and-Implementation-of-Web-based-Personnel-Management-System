package kr.co.yooooon.common.servlet.mvc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;

public class myProfileController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();				//uri=/insa/emp/myProfile.html
		String contextPath = request.getContextPath(); 			// contextPath= /insa
		int beginIndex = contextPath.length();// 5
		System.out.println("------------------");
		System.out.println("uri="+uri);
		System.out.println("contextPath="+contextPath);
		int endIndex = uri.lastIndexOf("."); 					// 23
		String viewname = uri.substring(beginIndex, endIndex); // viewname=/emp/myProfile
		System.out.println("viewname="+viewname);
		HashMap<String, Object> modelObject = null;
		ModelAndView modelAndView = new ModelAndView(viewname, modelObject);
		return modelAndView;
	}
}
