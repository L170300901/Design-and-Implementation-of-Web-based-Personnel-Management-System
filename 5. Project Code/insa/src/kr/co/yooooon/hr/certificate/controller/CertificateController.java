package kr.co.yooooon.hr.certificate.controller;

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
import kr.co.yooooon.hr.certificate.sf.CertificateServiceFacade;
import kr.co.yooooon.hr.certificate.sf.CertificateServiceFacadeImpl;
import kr.co.yooooon.hr.certificate.to.CertificateTO;
import net.sf.json.JSONObject;

public class CertificateController extends MultiActionController{
	private static CertificateServiceFacade certificateServiceFacade = CertificateServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView registRequest(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();

			Gson gson = new Gson();
			CertificateTO certificate = gson.fromJson(sendData, CertificateTO.class);
			certificateServiceFacade.registRequest(certificate);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());			
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", ioe.getMessage());
			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			
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
	
	public ModelAndView findCertificateList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String empCode = request.getParameter("empCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");		
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			ArrayList<CertificateTO> certificateList = certificateServiceFacade.findCertificateList(empCode, startDate, endDate);
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
	
	public ModelAndView removeCertificateRequest(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model=new HashMap<>();
		String sendData=request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out=response.getWriter();
			Gson gson=new Gson();
			ArrayList<CertificateTO> certificateList=gson.fromJson(sendData, new TypeToken<ArrayList<CertificateTO>>() {
			}.getType());
			certificateServiceFacade.removeCertificateRequest(certificateList);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);
			
			JSONObject jsonObject=JSONObject.fromObject(model);
			out.println(jsonObject);
		}catch(IOException ioe) {
			logger.fatal(ioe.getMessage());
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", ioe.getMessage());
			
			JSONObject jsonObject=JSONObject.fromObject(model);
			out.println(jsonObject);
		}catch(DataAccessException dae) {
			logger.fatal(dae.getMessage());
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", dae.getMessage());
			
			JSONObject jsonObject=JSONObject.fromObject(model);
			out.println(jsonObject);
		}finally{
			out.close();
		}
		
		return modelAndView;
		
	}
}
