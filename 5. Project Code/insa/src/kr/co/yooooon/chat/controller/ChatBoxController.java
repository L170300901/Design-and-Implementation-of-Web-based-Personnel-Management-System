package kr.co.yooooon.chat.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.chat.sf.*;
import kr.co.yooooon.chat.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;

public class ChatBoxController extends MultiActionController {
	private static ChatSubmitServiceFacade chatServiceFacade = ChatSubmitServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView getBox(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String userID = request.getParameter("userID");
		
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			
			ArrayList<ChatTO> getBox=chatServiceFacade.getBox(userID);
			
			//String toID = request.getParameter("userID");
			//int result=chatServiceFacade.getUnreadChat(fromID,toID);
			model.put("getBox",  getBox);
			//model.put("result", result);
			
			JSONObject jsonObject = JSONObject.fromObject(model);
			out.println(jsonObject);
			System.out.println(jsonObject);
			
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


