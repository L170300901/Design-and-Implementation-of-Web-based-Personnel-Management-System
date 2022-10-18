package kr.co.yooooon.common.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.co.yooooon.common.servlet.context.*;
import kr.co.yooooon.common.servlet.mapper.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.common.servlet.view.*;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet{
	private ApplicationContext applicationContext;
	private SimpleUrlHandlerMapping simpleUrlHandlerMapping;
	private InternalResourceViewResolver internalResourceViewResolver;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		//application과 config의 주소값을 넘김
		applicationContext=new ApplicationContext(this.getServletContext(),this.getServletConfig());
		simpleUrlHandlerMapping=SimpleUrlHandlerMapping.getInstance(this.getServletContext());
		internalResourceViewResolver = InternalResourceViewResolver.getInstance(this.getServletContext());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setCharacterEncoding("utf-8"); request.setCharacterEncoding("utf-8");
		//보내는것에 대한 인코딩,응답하는것에 대한 인코딩 

		Controller controller=simpleUrlHandlerMapping.getController(applicationContext, request);  //해당 컨트롤러
		ModelAndView modelAndView=controller.handleRequest(request, response);
		System.out.println("MAV:"+modelAndView);
		
		if(modelAndView!=null) {
			System.out.println("MAV's viewname:"+modelAndView.getViewname());
			internalResourceViewResolver.resolveView(modelAndView, request, response);
		}
	}
}
