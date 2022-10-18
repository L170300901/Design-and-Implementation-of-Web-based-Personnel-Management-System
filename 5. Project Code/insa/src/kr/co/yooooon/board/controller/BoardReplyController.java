package kr.co.yooooon.board.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.google.gson.*;

import kr.co.yooooon.board.sf.*;
import kr.co.yooooon.board.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;

public class BoardReplyController extends MultiActionController {
	private static BoardServiceFacade boardServiceFacade = BoardServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView boardReply(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("@@@@@@@@@@boardReply@@@@@@@@@@@@@@@@");
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String sendData = request.getParameter("sendData");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();

			Gson gson = new Gson();
			BoardTO boardto = gson.fromJson(sendData, BoardTO.class);
			boardServiceFacade.boardReply(boardto);
			model.put("errorMsg", "success");
			model.put("errorCode", 0);

			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			
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
