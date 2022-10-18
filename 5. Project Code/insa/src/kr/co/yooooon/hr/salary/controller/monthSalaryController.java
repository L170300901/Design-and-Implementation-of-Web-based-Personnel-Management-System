package kr.co.yooooon.hr.salary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.salary.sf.SalaryServiceFacade;
import kr.co.yooooon.hr.salary.sf.SalaryServiceFacadeImpl;
import kr.co.yooooon.hr.salary.to.MonthSalaryTO;
import net.sf.json.JSONObject;

public class monthSalaryController extends MultiActionController{
	private static SalaryServiceFacade salaryServiceFacade = SalaryServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView findMonthSalary(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String applyYearMonth = request.getParameter("applyYearMonth");
		String empCode = request.getParameter("empCode");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();

			MonthSalaryTO monthSalary = salaryServiceFacade.findMonthSalary(applyYearMonth,empCode);
			model.put("monthSalary", monthSalary);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
			String viewname = "redirect:welcome.html";
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			modelAndView = new ModelAndView(viewname, model);
		} catch (DataAccessException dae){
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
	
	public ModelAndView findYearSalary(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String applyYear = request.getParameter("applyYear");
		String empCode = request.getParameter("empCode");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();

			ArrayList<MonthSalaryTO> yearSalary = salaryServiceFacade.findYearSalary(applyYear, empCode);
			model.put("yearSalary", yearSalary);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
			String viewname = "redirect:welcome.html";
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			modelAndView = new ModelAndView(viewname, model);
		} catch (DataAccessException dae){
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

	public ModelAndView modifyMonthSalary(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();
			/* 간편하고 성능좋은 gson으로 변경 */			
			Gson gson = new Gson();
			MonthSalaryTO monthSalary = gson.fromJson(sendData, MonthSalaryTO.class);
			salaryServiceFacade.modifyMonthSalary(monthSalary);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
			String viewname = "redirect:welcome.html";
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			modelAndView = new ModelAndView(viewname, model);
		} catch (DataAccessException dae){
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