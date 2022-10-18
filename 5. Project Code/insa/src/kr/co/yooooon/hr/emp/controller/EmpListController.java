package kr.co.yooooon.hr.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;
import kr.co.yooooon.common.servlet.mvc.MultiActionController;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacade;
import kr.co.yooooon.hr.emp.sf.EmpServiceFacadeImpl;
import kr.co.yooooon.hr.emp.to.EmpTO;
import net.sf.json.JSONObject;

public class EmpListController extends MultiActionController {
	private static EmpServiceFacade empServiceFacade = EmpServiceFacadeImpl.getInstance();
	PrintWriter out = null;

	public ModelAndView emplist(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("@@@@@@@@@@@@@@3번통과 여기는  emplistconteroller@@@@@@@@@@@@@@@@");
		response.setContentType("application/json; charset=UTF-8");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String viewName = null;
		try {
			
			String value = "전체부서";
			System.out.println(value);
			if (request.getParameter("value") != null)			// value가 null이 아니면
				value = (String) request.getParameter("value"); //dept: "전체부서"를 넣어라		
			System.out.println("@@@@@@@@@@@@@@구하고자 하는 value 값 바꾸기 완료 @@@@@@@@@@@@@@@@");
			System.out.println(value);
			
			//empListController의 emplist의 ArrayList<EmpTO> empServiceFacade.findEmpList(value)
			//의 list변수에 담아서 map에 넣고 json으로 변환후 out객체를 사용하여 view에 뿌려줌 
			ArrayList<EmpTO> list = (ArrayList<EmpTO>) empServiceFacade.findEmpList(value);						
			map.put("list", list);

			JSONObject jsonobject = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			out.println(jsonobject);
			
			System.out.println("jsonobject결과물: "+jsonobject); //콘솔에 jsonobject 찍힘

		} catch (Exception e) {
			viewName = "error";
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			JSONObject jsonobject = JSONObject.fromObject(map);
			try {
				PrintWriter out = response.getWriter();
				out.println(jsonobject);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		ModelAndView modelAndView = null;  
		return modelAndView;
	}

	
	
	/*public ModelAndView workInfoList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		String viewName = null;
		try {
			String code = request.getParameter("code");

			EmpServiceFacadeImpl sf = EmpServiceFacadeImpl.getInstance();

			ListForm listForm = new ListForm();

			
			ArrayList<EmpTO> list = sf.workInfoList(code);
			map.put("list", list);

			JSONObject jsonobject = JSONObject.fromObject(map);
			PrintWriter out = response.getWriter();
			out.println(jsonobject);
			System.out.println(jsonobject);
		} catch (Exception e) {
			viewName = "error";
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			JSONObject jsonobject = JSONObject.fromObject(map);
			try {
				PrintWriter out = response.getWriter();
				out.println(jsonobject);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		ModelAndView modelAndView = null;
		return null;
	} */
	
}