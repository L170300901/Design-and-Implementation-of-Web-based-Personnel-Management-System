package kr.co.yooooon.hr.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacade;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacadeImpl;
import kr.co.yooooon.hr.emp.to.EmpTO;
import net.sf.json.JSONObject;


public class EmpRegistController extends MultiActionController{
	private static EmpServiceFacade empServiceFacade = EmpServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView registEmployee(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out=response.getWriter();
			
			Gson gson = new Gson();
			EmpTO emp = gson.fromJson(sendData, new TypeToken<EmpTO>(){}.getType());		
			empServiceFacade.registEmployee(emp);		

			model.put("errorMsg","request.getParameter(\"emp_name\")+\" 사원이 등록되었습니다.\"");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);			
			
		} catch (DataAccessException dae){
			logger.fatal(dae.getMessage());
			
			model.put("errorMsg", "사원 등록에 실패했습니다 : "+dae.getMessage());
			model.put("errorCode", -1);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);			
			
		} catch(Exception e) {
			logger.fatal(e.getMessage());	
			
			model.put("errorMsg", e.getMessage());
			model.put("errorCode", -1);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);	
		}finally {
			out.close();
		}

		return modelAndView;
	}
	
	public ModelAndView findLastEmpCode(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		try {
			out=response.getWriter();
			String empCode = empServiceFacade.findLastEmpCode();
			model.put("lastEmpCode", empCode);
			model.put("errorMsg","success");
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
