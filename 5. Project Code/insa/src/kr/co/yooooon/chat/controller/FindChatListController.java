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

public class FindChatListController extends MultiActionController {
	private static ChatSubmitServiceFacade chatServiceFacade = ChatSubmitServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	
	public ModelAndView findChatList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("11111111111111111");
		// TODO Auto-generated method stub
		HashMap<String, Object> model = new HashMap<String, Object>();
		String fromID = request.getParameter("fromIdD");
		String toID = request.getParameter("toID");
		String number = request.getParameter("number");
		System.out.println("7777777777777777777777777777");
		System.out.println(fromID);
		System.out.println(toID);
		System.out.println(number);
		System.out.println("777777777777777777777777777");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
		
			ArrayList<ChatTO> getChatListByRecent =chatServiceFacade.getChatListByRecent(fromID,toID,number);
			chatServiceFacade.readChat(fromID, toID);
			model.put("getChatListByRecent",  getChatListByRecent);
			//model.put("boardList2", boardto);
			//model.put("errorMsg", "success");
			//model.put("errorCode", 0);

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