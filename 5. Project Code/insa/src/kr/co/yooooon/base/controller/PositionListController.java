package kr.co.yooooon.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.yooooon.base.sf.BaseServiceFacade;
import kr.co.yooooon.base.sf.BaseServiceFacadeImpl;
import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.salary.to.BaseSalaryTO;
import net.sf.json.JSONObject;

public class PositionListController extends MultiActionController{
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	
	PrintWriter out = null;
	
	public ModelAndView findPositionList(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String,Object> model = new HashMap<>();
	
			try {
				out = response.getWriter();
				ArrayList<BaseSalaryTO> positionList = baseServiceFacade.findPositionList();
				BaseSalaryTO positionTO = new BaseSalaryTO();
				
				model.put("positionList", positionList);
				model.put("emptyPositionBean", positionTO);
				model.put("errorCode",0);
				model.put("errorMsg","success");
							
				JSONObject jsonObject = JSONObject.fromObject(model);
				out.println(jsonObject);
				System.out.println("포지션리스트"+jsonObject);
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
	
	public ModelAndView modifyPosition(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		
		response.setContentType("application/json; charset=UTF-8");
		try{
			out=response.getWriter();
			 //간편하고 성능좋은 gson으로 변경 
			Gson gson = new Gson();
			ArrayList<BaseSalaryTO> positionList = gson.fromJson(sendData, new TypeToken<ArrayList<BaseSalaryTO>>(){}.getType());
			
			//ObjectMapper mapper = new ObjectMapper();
			//ArrayList<BaseSalaryTO> positionList = mapper.readValue(sendData, new TypeReference<ArrayList<BaseSalaryTO>>() {
			//});
			baseServiceFacade.modifyPosition(positionList);
			model.put("errorMsg","success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);

		} catch (IOException ioe) {
			logger.fatal(ioe.getMessage());
			String viewname = "redirect:welcome.html";
			model.clear();
			model.put("errorMsg", ioe.getMessage());
			model.put("errorCode", -2);
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
