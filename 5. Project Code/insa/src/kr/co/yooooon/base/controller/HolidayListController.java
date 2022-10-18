package kr.co.yooooon.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yooooon.base.sf.BaseServiceFacade;
import kr.co.yooooon.base.sf.BaseServiceFacadeImpl;
import kr.co.yooooon.base.to.HolidayTO;
import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import net.sf.json.JSONObject;

public class HolidayListController extends MultiActionController {
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView findHolidayList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();

			ArrayList<HolidayTO> holidayList = baseServiceFacade.findHolidayList();
			HolidayTO holito = new HolidayTO();
			model.put("holidayList", holidayList);
			model.put("emptyHoilday", holito);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject);

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

	public ModelAndView findWeekDayCount(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			out = response.getWriter();

			String weekdayCount = baseServiceFacade.findWeekDayCount(startDate, endDate);
			model.put("weekdayCount", weekdayCount);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject);

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

	public ModelAndView regitCodeList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<>();
		String sendData = request.getParameter("sendData");
		System.out.println(sendData);

		try {
			out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<HolidayTO> holydayList = mapper.readValue(sendData, new TypeReference<ArrayList<HolidayTO>>() {
			});
			
			baseServiceFacade.registCodeList(holydayList);
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

}
