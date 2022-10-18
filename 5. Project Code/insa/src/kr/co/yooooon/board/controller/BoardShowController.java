package kr.co.yooooon.board.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.board.sf.*;
import kr.co.yooooon.board.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.json.*;


public class BoardShowController extends MultiActionController {
	private static BoardServiceFacade boardServiceFacade = BoardServiceFacadeImpl.getInstance();
	private ModelAndView modelAndView = null;
	PrintWriter out = null;

	public ModelAndView boardShow(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("@@@@@@@@@@boardShow@@@@@@@@@@@@@@@@");
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		String boardID = request.getParameter("boardID");
		System.out.println("-------------------------------");
		System.out.println(boardID);
		System.out.println("-------------------------------");
		response.setContentType("application/json; charset=UTF-8");
		try {
			out = response.getWriter();
			boardServiceFacade.boardHit(boardID);
			ArrayList<BoardTO> BoardShow = boardServiceFacade.boardShow(boardID);
			BoardTO boardto = new BoardTO();
			model.put("boardShow", BoardShow);
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