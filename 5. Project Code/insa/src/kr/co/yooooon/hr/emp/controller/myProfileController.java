package kr.co.yooooon.hr.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.List;

import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.Controller;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacade;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacadeImpl;
import kr.co.yooooon.hr.emp.to.EmpTO;
import net.sf.json.JSONObject;

public class myProfileController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response){
				// TODO Auto-generated method stub
				String uri=request.getRequestURI();
				String contextPath=request.getContextPath();
				String viewName=uri.split("[.]")[0].substring(contextPath.length());
				System.out.println("viewName"+viewName);
				
				
				HashMap<String,Object> map=new HashMap<String,Object>();
				ModelAndView mav=null;
				
	 	try {
	 			
	 			
		} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("errorCode", -1);
		 		map.put("errorMsg", e.getMessage());
		 		mav=new ModelAndView("error",map);
		}	
		return mav;
	}
}