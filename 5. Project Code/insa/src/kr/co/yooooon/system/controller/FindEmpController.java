package kr.co.yooooon.system.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.emp_new.to.*;
import kr.co.yooooon.system.sf.*;
import net.sf.json.*;


public class FindEmpController extends MultiActionController {	
	private static SystemServiceFacade systemServiceFacade = SystemServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView empList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		HashMap<String, Object> model = new HashMap<String, Object>();

		try {
			out = response.getWriter();
			ArrayList<Emp_newTO> empList = systemServiceFacade.empList();
			model.put("empList", empList);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject + "!");

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
			String viewname = "redirect:welcome.html";
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			modelAndView = new ModelAndView(viewname, model);
		} catch (DataAccessException dae) {
			logger.fatal(dae.getMessage());
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", dae.getMessage());
			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
		} finally {
			out.close();
		}
		return modelAndView;
	}
}
