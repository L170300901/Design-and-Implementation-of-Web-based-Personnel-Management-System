package kr.co.yooooon.hr.attd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.attd.sf.AttdServiceFacade;
import kr.co.yooooon.hr.attd.sf.AttdServiceFacadeImpl;
import kr.co.yooooon.hr.attd.to.MonthAttdMgtTO;
import net.sf.json.JSONObject;

public class MonthAttdManageController extends MultiActionController{
	private static AttdServiceFacade attdServiceFacade = AttdServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView findMonthAttdMgtList(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String applyYearMonth = request.getParameter("applyYearMonth");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out=response.getWriter();

			ArrayList<MonthAttdMgtTO> monthAttdMgtList = attdServiceFacade.findMonthAttdMgtList(applyYearMonth);
			model.put("monthAttdMgtList", monthAttdMgtList);
			model.put("errorMsg","success");
			model.put("errorCode", 0);
			
			System.out.println(monthAttdMgtList);

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

	public ModelAndView modifyMonthAttdList(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out=response.getWriter();
			Gson gson = new Gson();
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = gson.fromJson(sendData, new TypeToken<ArrayList<MonthAttdMgtTO>>(){}.getType());
			attdServiceFacade.modifyMonthAttdMgtList(monthAttdMgtList);
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
