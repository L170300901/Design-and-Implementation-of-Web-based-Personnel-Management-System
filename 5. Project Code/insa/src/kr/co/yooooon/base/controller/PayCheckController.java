package kr.co.yooooon.base.controller;


import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.base.sf.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;

public class PayCheckController extends MultiActionController {
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView payCheckList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		try {
			System.out.println("@@@@@");
			out = response.getWriter();
			ArrayList<PayDateTO> payDateList = baseServiceFacade.payDateList();
			model.put("payDateList", payDateList);
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
	public void createHobongList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String startDate = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			baseServiceFacade.createHobongList(startDate);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject + "!");

		} catch (DataAccessException dae) {
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", dae.getMessage());
			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println("de" + jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}
	public ModelAndView findHobongPositionList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String startDate = request.getParameter("startDate");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			ArrayList<HobongTO> hobongPositionList = baseServiceFacade.hobongPositionList(startDate);
			model.put("HobongPositionList", hobongPositionList);
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
	public ModelAndView findHobongTable(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String positionCode = request.getParameter("positionCode");
		String positionName = request.getParameter("positionName");
		String startDate = request.getParameter("startDate");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			ArrayList<HobongTO> finaHobongTable = baseServiceFacade.findHobongTable(positionCode,positionName,startDate);
			model.put("finaHobongTableList", finaHobongTable);
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
	public ModelAndView payCheckResist(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String increaseAmount = request.getParameter("increaseAmount");
		String initialValue = request.getParameter("initialValue");
		String startDate = request.getParameter("startDate");
		String positionCode = request.getParameter("positionCode");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			baseServiceFacade.payCheckResist(increaseAmount,initialValue,startDate,positionCode);
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
