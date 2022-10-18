package kr.co.yooooon.common.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;

public interface Controller {
	public ModelAndView handleRequest(
			HttpServletRequest request,
			HttpServletResponse response);
}
