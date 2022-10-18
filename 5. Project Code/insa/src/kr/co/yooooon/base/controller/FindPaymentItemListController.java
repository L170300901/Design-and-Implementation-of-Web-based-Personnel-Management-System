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


public class FindPaymentItemListController extends MultiActionController {
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView findPaymentItemList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		String salaryClassification = request.getParameter("salaryClassification");
		String paymentClassification = request.getParameter("paymentClassification");
		String year = request.getParameter("year");
		try {
			//System.out.println("year======="+year);
			//System.out.println("salaryClassification======="+salaryClassification);
			//System.out.println("paymentClassification======="+paymentClassification);
			out = response.getWriter();
			ArrayList<PaymentItemTO> paymentItemList = baseServiceFacade.findPaymentItemList(salaryClassification,paymentClassification,year);
			model.put("paymentItemList", paymentItemList);
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
