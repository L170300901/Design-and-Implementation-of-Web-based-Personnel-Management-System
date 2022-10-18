package kr.co.yooooon.hr.salary.controller;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.salary.sf.*;
import kr.co.yooooon.hr.salary.to.*;
import net.sf.json.*;
public class pymntDditmController extends MultiActionController {
		private static SalaryServiceFacade salaryServiceFacade = SalaryServiceFacadeImpl.getInstance();
		private ModelAndView modelAndView = null;
		PrintWriter out = null;
		
		public ModelAndView findPymntDditm(HttpServletRequest request, HttpServletResponse response){
			HashMap<String, Object> model = new HashMap<String, Object>();
			response.setContentType("application/json; charset=UTF-8");
			

			try {
				out=response.getWriter();
				pymntDditmTO pymntData = new pymntDditmTO();
				pymntData.setYear(request.getParameter("year"));
				pymntData.setPymddClsfc(request.getParameter("pymddClsfc"));
				pymntData.setSlryClsfc(request.getParameter("slryClsfc"));
				ArrayList<basePymntItmNameCodeTO> pymntDditmList = salaryServiceFacade.findPymntDditmList(pymntData);
				
				
				model.put("pymntDditmList", pymntDditmList);
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
		
	public ModelAndView setPaymentItmNodeOption(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		

		try {
			out=response.getWriter();
			paymentItmOptionTO paymntOtionTO = new paymentItmOptionTO();
			paymntOtionTO.setYear(request.getParameter("year"));
			paymntOtionTO.setSlryClsfc(request.getParameter("slryClsfc"));
			paymntOtionTO.setCode(request.getParameter("code"));
			paymntOtionTO.setPymntDdctn(request.getParameter("pymntDdctn"));
			System.out.println(paymntOtionTO.getCode());
			ArrayList<paymentItmOptionRsTO> paymentItmOptionRs = salaryServiceFacade.setPaymentItmNodeOption(paymntOtionTO);
			
			
			model.put("paymentItmOptionRs", paymentItmOptionRs);
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
	
	public ModelAndView setJobCode(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		

		try {
			out=response.getWriter();
			jobCodeTO priorityCode = new jobCodeTO();
			priorityCode.setPriorityCode(request.getParameter("priorityCode"));
			priorityCode.setSlryClsfc(request.getParameter("slryClsfc"));
			
			ArrayList<jobCodeRsTO> jobCodeRs = salaryServiceFacade.setJobCode(priorityCode);
			
			
			model.put("jobCodeRs", jobCodeRs);
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
			 
	public ModelAndView setDissection(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		

		try {
			out=response.getWriter();
			dissectionTO dissectionData = new dissectionTO();
			dissectionData.setNum(request.getParameter("num"));
			if(dissectionData.getNum().equals("1")) {
				dissectionData.setJobCode(request.getParameter("jobCode"));
				dissectionData.setDisecPrioCode(request.getParameter("disecPrioCode"));
			}else {
				dissectionData.setDisecPrioCode(request.getParameter("disecPrioCode"));
			}
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+dissectionData.getJobCode()+dissectionData.getDisecPrioCode());
			ArrayList<dissectionRsTO> dissectionRs = salaryServiceFacade.setDissection(dissectionData);
			
			
			model.put("dissectionRs", dissectionRs);
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
	
	public ModelAndView setAmnclFrml(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		response.setContentType("application/json; charset=UTF-8");
		

		try {
			out=response.getWriter();
			amnclFrmlTO AFTTO = new amnclFrmlTO();
			String num=request.getParameter("num");
			AFTTO.setNum(num);
			if(num.equals("1")) {
				AFTTO.setSlryClsfc(request.getParameter("slryClsfc"));
				AFTTO.setPymntDdctn(request.getParameter("pymntDdctn"));
				AFTTO.setCode(request.getParameter("code"));
				AFTTO.setJobCode(request.getParameter("jobCode"));
				AFTTO.setYear(request.getParameter("year"));
				System.out.println("1번출력@@@@@@@@@@@@@@@@@@@@@@@@@");
			}else {
				if(num.equals("2")) {
					AFTTO.setSlryClsfc(request.getParameter("slryClsfc"));
					AFTTO.setPymntDdctn(request.getParameter("pymntDdctn"));
					AFTTO.setCode(request.getParameter("code"));
					AFTTO.setYear(request.getParameter("year"));
					AFTTO.setDisecPrioCode(request.getParameter("disecPrioCode"));
					AFTTO.setAmnclFrmlCode(request.getParameter("amnclFrmlCode"));
					System.out.println("2번출력@@@@@@@@@@@@@@@@@@@@@@@@@");
				}else {
					AFTTO.setSlryClsfc(request.getParameter("slryClsfc"));
					AFTTO.setPymntDdctn(request.getParameter("pymntDdctn"));
					AFTTO.setCode(request.getParameter("code"));
					AFTTO.setJobCode(request.getParameter("jobCode"));
					AFTTO.setYear(request.getParameter("year"));
					AFTTO.setDisecPrioCode(request.getParameter("disecPrioCode"));
					AFTTO.setAmnclFrmlCode(request.getParameter("amnclFrmlCode"));
					System.out.println("3번출력@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
			}
			ArrayList<amnclFrmlRsTO> AFRTOList = salaryServiceFacade.setAmnclFrml(AFTTO);
			
			
			model.put("amnclFrmlRs", AFRTOList);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println("결과물 ======"+jsonObject);

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