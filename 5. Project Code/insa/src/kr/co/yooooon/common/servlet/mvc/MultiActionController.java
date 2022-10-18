package kr.co.yooooon.common.servlet.mvc;

import javax.servlet.http.*;

import kr.co.yooooon.common.servlet.*;

public class MultiActionController extends AbstractController {

	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView modelAndView = null;
		String methodName = request.getParameter("method"); // "emplist" , "findAllEmployeeInfo"
		//제너릭 형 배열
		Class<?>[] parameters = new Class<?>[] { HttpServletRequest.class, HttpServletResponse.class };
		
		//런타임시 최초로 실행된 컨트롤러를 받아온다
		//dispatcherServlet에서 doPost로 handleRequest를 호출하는데 컨트롤러에는 해당 request가 없기 때문에 부모의 부모인(ActionMultyController)러 가서 차례대로 실행되다가
		//handelRequestInternal가 실행되는데 이게 멀티액션컨트롤러에 있다
		//그래서 해당 클래스의 제너릭 클래스를 가져오는 역활을 한다
		Class<?> cl = this.getClass();
		//System.out.println("---------MultiActionController테스트 시작11111111111:--------------");
		//System.out.println("methodName="+methodName);
		//System.out.println("---------MultiActionController테스트 종료222222222222:--------------");
		try {
			//System.out.println("@@@@@@@@@@@@@@2번통과@@@@@@@@@@@@@@@@");
			if (logger.isDebugEnabled()) {
				//logger.debug(methodName + " 시작 ");
			}
			//System.out.println("@@@@@@@@@@@@@@111111111111111111111111111111111@@@@@@@@@@@@@@@@");
		
			modelAndView = (ModelAndView) cl.getMethod(methodName, parameters).invoke(cl.newInstance(), request, response);
			//System.out.println("@@@@@@@@@@@@@@222222222222222222222222222222@@@@@@@@@@@@@@@@");
			if (logger.isDebugEnabled()) {
				//logger.debug(methodName + " 종료 ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}

}