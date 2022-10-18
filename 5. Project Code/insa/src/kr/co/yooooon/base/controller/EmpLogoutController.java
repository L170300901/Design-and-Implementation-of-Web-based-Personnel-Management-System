package kr.co.yooooon.base.controller;

import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;

public class EmpLogoutController extends AbstractController {


	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		String viewName="redirect:loginForm.html";
		try{
			request.getSession().invalidate();
		}catch(Exception e){
			map.put("errorCode", -1);
			map.put("errorMsg", e.getMessage());
			viewName="error";
		}
		ModelAndView modelAndView=new ModelAndView(viewName,map);
		return modelAndView;
	}
}

