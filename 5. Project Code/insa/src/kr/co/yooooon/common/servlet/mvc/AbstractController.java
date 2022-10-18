package kr.co.yooooon.common.servlet.mvc;

import javax.servlet.http.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.servlet.*;

public abstract class AbstractController implements Controller {
	protected final Log logger = LogFactory.getLog(this.getClass());

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		String controllerFullName = this.getClass().getName(); 
		//System.out.println("3434");
		//kr.co.yooooon.hr.emp.controller.EmpListController
		String controllerShortName = controllerFullName.substring(controllerFullName.lastIndexOf(".")+1); 
		//EmpListController
		
		//System.out.println("---------abstract테스트 시작11111111111:--------------");
		//System.out.println("controllerFullName="+controllerFullName);
		//System.out.println("controllerShortName="+controllerShortName);
		//System.out.println("---------abstract테스트 종료222222222222:--------------");
		
		if (logger.isDebugEnabled()) {
			//logger.debug(controllerShortName + " 시작 ");
			//System.out.println("@@@@@@@@@@@@@@1번통과@@@@@@@@@@@@@@@@");
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store"); //캐시설정 안한는걸로

		ModelAndView modelAndView = this.handleRequestInternal(request, response);

		if (logger.isDebugEnabled()) {
			//logger.debug(controllerShortName + " 종료 ");
		}
		return modelAndView;
	}

	public abstract ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response);
}
