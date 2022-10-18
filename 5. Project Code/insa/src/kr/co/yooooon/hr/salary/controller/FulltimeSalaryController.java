package kr.co.yooooon.hr.salary.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.salary.sf.*;
import kr.co.yooooon.hr.salary.to.*;
import net.sf.json.*;

public class FulltimeSalaryController extends MultiActionController{
	private static SalaryServiceFacade salaryServiceFacade = SalaryServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView AllMoneyList(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String applyYearMonth = request.getParameter("apply_year_month");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();
			ArrayList<FullTimeSalTO> AllMoneyList = salaryServiceFacade.findAllMoney(applyYearMonth);
			model.put("AllMoneyList", AllMoneyList);
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
	

	public ModelAndView selectSalary(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String applyYearMonth = request.getParameter("apply_year_month");
		String empCode = request.getParameter("empCode");
		//System.out.println("cstmt가라고 해서 명령받아 컨트롤러에 온 아이들");
		System.out.println(applyYearMonth);
		System.out.println(empCode);
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();
			ArrayList<FullTimeSalTO> FullTimeSalaryList = salaryServiceFacade.findselectSalary(applyYearMonth,empCode);
			model.put("FullTimeSalaryList", FullTimeSalaryList);
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

	
	public ModelAndView modifyFullTimeSalary(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		System.out.println(sendData);

		      try {
		         out = response.getWriter();
		         ObjectMapper mapper = new ObjectMapper();
		         System.out.println("1");
		         ArrayList<FullTimeSalTO> fullTimeSalary = mapper.readValue(sendData, new TypeReference<ArrayList<FullTimeSalTO>>() {
		         });
		         System.out.println("22");
		         
		         System.out.println("1");
		         System.out.println("fullTimeSalary==="+fullTimeSalary);
		         System.out.println("2");
		         salaryServiceFacade.modifyFullTimeSalary(fullTimeSalary);
		         map.put("errorMsg", "success");
		         map.put("errorCode", 0);
		         
		         JSONObject jsonObject = JSONObject.fromObject(map);
		         out.println(jsonObject);
		      } catch (Exception e) {
		         logger.fatal(e.getMessage());
		         map.put("errorMsg", e.getMessage());
		         JSONObject jsonobject = JSONObject.fromObject(map);
		         
		         try {
		            out = response.getWriter();
		            out.println(jsonobject);
		         } catch (IOException e1) {
		            e1.printStackTrace();
		         }
		      } finally {
		         out.close();
		      }
		      return null;
		   }
	
	public ModelAndView paydayList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String viewName = null;
		
		try {
			String value = "전체부서"; //
			if (request.getParameter("value") != null) { // value가 null이 아니면 ( 뷰단에서 검색이 일어났을 때 ? )
				value = request.getParameter("value");
			}
			System.out.println("선택한 부서명 : "+value);
			ArrayList<PayDayTO> list = salaryServiceFacade.findPayDayList();
			map.put("list", list);
			JSONObject jsonobject = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			out.println(jsonobject);

		} catch (Exception e) {
			viewName = "error";
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