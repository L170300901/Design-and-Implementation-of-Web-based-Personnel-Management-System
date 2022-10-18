package kr.co.yooooon.base.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;

import kr.co.yooooon.base.sf.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;

public class CodeListController extends MultiActionController {
	private static BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
	PrintWriter out = null;

	public void detailCodelist(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String code = request.getParameter("code");
		//System.out.println("@@1.code======"+code);
		try {
			out = response.getWriter();
			ArrayList<DetailCodeTO> detailCodeList = baseServiceFacade.findDetailCodeList(code);
			model.put("detailCodeList", detailCodeList);
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
	
	public void detailCodelistRest(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String code1 = request.getParameter("code1");
		String code2 = request.getParameter("code2");
		String code3 = request.getParameter("code3");
		System.out.println("@@"+code1+code2+code3);
		try {
			out = response.getWriter();
			ArrayList<DetailCodeTO> detailCodeList = baseServiceFacade.findDetailCodeListRest(code1, code2, code3);
			model.put("detailCodeList", detailCodeList);
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

	public void codelist(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		HashMap<String, Object> model = new HashMap<String, Object>();

		try {
			out = response.getWriter();
			ArrayList<CodeTO> codeList = baseServiceFacade.findCodeList();
			model.put("codeList", codeList);
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
	public void outPutList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String outPut = request.getParameter("outPut");
		//System.out.println("@@  outPut======"+outPut);
		try {
			out = response.getWriter();
			ArrayList<CodeTO> findoutPutList = baseServiceFacade.findoutPutList(outPut);
			model.put("findoutPutList", findoutPutList);
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
	public ModelAndView saveCodeList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<>();
		String sendData = request.getParameter("sendData");
		System.out.println("sendData==="+sendData);

		try {
			out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("1");
			ArrayList<DetailCodeTO> saveCodeList = mapper.readValue(sendData, new TypeReference<ArrayList<DetailCodeTO>>() {
			});
			System.out.println("22");
			
			
			
			//System.out.println("1");
			//System.out.println("saveCodeList==="+saveCodeList);
			//System.out.println("2");
			baseServiceFacade.saveCodeList(saveCodeList);
			map.put("errorMsg", "success");
			map.put("errorCode", 0);
			
			JSONObject jsonObject = JSONObject.fromObject(map);
			out.println(jsonObject);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			map.put("errorMsg", e.getMessage());
			JSONObject jsonobject = JSONObject.fromObject(map);
			
			try {
				out = response.getWriter();
				out.println(jsonobject);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			out.close();
		}
		return null;
	}
	public void basicDetailCodelist(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String code = request.getParameter("code");

		try {
			out = response.getWriter();
			ArrayList<DetailCodeTO> basicDetailCodelist = baseServiceFacade.basicDetailCodelist(code);
			model.put("basicDetailCodelist", basicDetailCodelist);
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
