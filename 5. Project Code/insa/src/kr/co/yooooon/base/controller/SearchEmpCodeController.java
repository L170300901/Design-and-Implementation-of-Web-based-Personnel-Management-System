package kr.co.yooooon.base.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.base.sf.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;

public class SearchEmpCodeController extends MultiActionController {
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	PrintWriter out = null;

	public void searchEmpCode(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		HashMap<String, Object> model = new HashMap<String, Object>();
		String companyCode = request.getParameter("companyCode");
		try {
			System.out.println("----------------1번 ");
			out = response.getWriter();
			ArrayList<LoginEmpTo> empCodeList = baseServiceFacade.searchEmpCode(companyCode);
			model.put("empCodeList", empCodeList);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject + "!");

		} catch (DataAccessException dae) {
			model.clear();
			model.put("errorCode", -1);
			model.put("errorMsg", dae.getMessage());
			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}