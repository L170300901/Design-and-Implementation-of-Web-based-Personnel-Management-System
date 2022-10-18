package kr.co.yooooon.common.servlet.mvc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;

public class UnknownURLController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String viewname="error";
		HashMap<String, Object> modelObject=new HashMap<String,Object>();
		modelObject.put("error", "찾을 수 없는 페이지입니다.");
		ModelAndView modelAndView=new ModelAndView(viewname,modelObject);
		return modelAndView;
	}

}
