package kr.co.yooooon.hr.emp_new.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.hr.emp_new.sf.*;
import kr.co.yooooon.hr.emp_new.to.*;
import net.sf.json.*;


public class EmpCodeListController extends MultiActionController {
	private static EmpCodeServiceFacade empCodeServiceFacade = EmpCodeServiceFacadeImpl.getInstance();
	PrintWriter out = null;

	
	public void lastSchoolCodeList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String code = request.getParameter("code");
		//System.out.println("5월 26일 ===="+code);
		try {
			out = response.getWriter();
			ArrayList<BASIC_DETAIL_CODETO> lastSchoolCodeList = (ArrayList<BASIC_DETAIL_CODETO>) empCodeServiceFacade.lastSchoolCodeList(code);
			model.put("lastSchoolCodeList", lastSchoolCodeList);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println("!" + jsonObject);

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
}
