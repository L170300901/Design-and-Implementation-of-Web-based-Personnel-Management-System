package kr.co.yooooon.hr.salary.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.salary.sf.*;
import kr.co.yooooon.hr.salary.to.*;
import net.sf.json.*;

public class SocialInsureController extends MultiActionController {
	private static SalaryServiceFacade salaryServiceFacade = SalaryServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView findBaseInsureList(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		String yearBox = request.getParameter("yearBox");
			
		try {
			out=response.getWriter();

			ArrayList<SocialInsureTO> baseInsureList = salaryServiceFacade.findBaseInsureList(yearBox);
			SocialInsureTO emptyBean = new SocialInsureTO();
			
			model.put("baseInsureList", baseInsureList); 
			emptyBean.setStatus("insert");                     
			model.put("emptyBean", emptyBean);                 
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
	
	public ModelAndView updateInsureData(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();		
			Gson gson = new Gson();
			ArrayList<SocialInsureTO> baseInsureList = gson.fromJson(sendData, new TypeToken<ArrayList<SocialInsureTO>>(){}.getType());
			salaryServiceFacade.updateInsureData(baseInsureList);
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
	
	public ModelAndView insertInsureData(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");

		try {
			out=response.getWriter();		
			Gson gson = new Gson();
			ArrayList<SocialInsureTO> baseInsureList = gson.fromJson(sendData, new TypeToken<ArrayList<SocialInsureTO>>(){}.getType());
			salaryServiceFacade.insertInsureData(baseInsureList);
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