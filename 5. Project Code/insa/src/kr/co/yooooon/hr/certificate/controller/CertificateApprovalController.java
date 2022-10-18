package kr.co.yooooon.hr.certificate.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.certificate.sf.*;
import kr.co.yooooon.hr.certificate.to.*;
import net.sf.json.*;

public class CertificateApprovalController extends MultiActionController{
	private static CertificateServiceFacade certificateServiceFacade = CertificateServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView findCertificateListByDept(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		//String deptName = request.getParameter("deptName");
		//String startDate = request.getParameter("startDate");
		//String endDate = request.getParameter("endDate");		
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			ArrayList<CertificateTO> certificateList = certificateServiceFacade.findCertificateListByDept();
			model.put("certificateList", certificateList);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println("확인" + jsonObject);
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
	
	public ModelAndView modifyCertificateList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		
		try {
			out = response.getWriter();
			Gson gson = new Gson();
			ArrayList<CertificateTO> certificateList = gson.fromJson(sendData, new TypeToken<ArrayList<CertificateTO>>(){}.getType());
			certificateServiceFacade.modifyCertificateList(certificateList);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());			
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			
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
