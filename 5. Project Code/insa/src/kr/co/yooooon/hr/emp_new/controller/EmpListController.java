package kr.co.yooooon.hr.emp_new.controller;


import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.emp_new.sf.*;
import kr.co.yooooon.hr.emp_new.to.*;
import net.sf.json.*;

public class EmpListController extends MultiActionController {
	private static EmpServiceFacade empServiceFacade = EmpServiceFacadeImpl.getInstance();
	PrintWriter out = null;

	public ModelAndView emplist(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ArrayList<Emp_newTO> list = (ArrayList<Emp_newTO>) empServiceFacade.findEmpList();						
			map.put("list", list);

			JSONObject jsonobject = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			out.println(jsonobject);
			
			System.out.println("jsonobject결과물: "+jsonobject); 

		} catch (Exception e) {
			//viewName = "error";
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
		ModelAndView modelAndView = null;  
		return modelAndView;
	}
}
