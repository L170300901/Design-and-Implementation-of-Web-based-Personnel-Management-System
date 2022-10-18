package kr.co.yooooon.base.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.base.sf.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.emp.sf.*;
import net.sf.json.*;

public class EmpLoginController extends AbstractController {

	BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	EmpServiceFacade empServiceFacade = EmpServiceFacadeImpl.getInstance();

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		String viewName = "loginform";
		
		try {
			String empName = request.getParameter("empName");
			String empPw = request.getParameter("empPw");
			
			if (baseServiceFacade.login(empName, empPw)) {

				LoginCheckTO loginCheckTO = empServiceFacade.getEmp(empName);

				request.getSession().setAttribute("name", loginCheckTO.getEmpName());
				request.getSession().setAttribute("dept", loginCheckTO.getEmpDept());
				request.getSession().setAttribute("companyCode", loginCheckTO.getCompanyCode());
				request.getSession().setAttribute("code", loginCheckTO.getEmpCode());
				request.getSession().setAttribute("position", loginCheckTO.getPosition());


				map.put("me", "enter"); 
				JSONObject jsonobject =JSONObject.fromObject(map); 
				PrintWriter out = response.getWriter();
				out.println(jsonobject);				 
			}

		} catch (Exception e) {
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			JSONObject jsonobject = JSONObject.fromObject(map);
			try {
				PrintWriter out = response.getWriter();
				out.println(jsonobject);
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
		return null; 
	}
}
