package kr.co.yooooon.base.controller;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import kr.co.yooooon.base.sf.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import net.sf.jasperreports.engine.*;


public class EmpReportController extends MultiActionController {
   private ModelAndView modelAndView;
   
   private BaseServiceFacade baseServiceFacade = BaseServiceFacadeImpl.getInstance();
    
   public ModelAndView requestEmployment(HttpServletRequest request, HttpServletResponse response) {  //재직증명서 신청
     
      String empCode = request.getParameter("empCode"); //해쉬맵 생성, URL에서 보낸 파라미터값을 맵에 담는다
      String usage=request.getParameter("usage");
      String requestDay=request.getParameter("requestDay");
      String useDay=request.getParameter("useDay");
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("utf-8");
      try {
         ReportTO to = baseServiceFacade.viewReport(empCode);
         Map<String, Object> map = new HashMap<String, Object>();
         //JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\insa\\report\\employment.jrxml");
         // JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\insa\\report\\employment.jrxml");
         JasperReport jasperReport = JasperCompileManager.compileReport((request.getServletContext().getRealPath("/report/employment.jrxml")));
         
         //JasperCompileManager = 네트워크전송을위한보고서디자인개체를얻기위한클래스
         // 보고서 컴파일 기능을 제공
         
         JRDataSource datasource = new JREmptyDataSource();
         // 내부에 지정된 수의 가상 레코드로 데이터소스를 시뮬레이트하는 클래스 
           
             map.put( "empName", to.getEmpName());
             map.put( "hiredate", to.getHiredate());
             map.put( "occupation", to.getOccupation());
             map.put( "employmentType", to.getEmploymentType());
             map.put( "position", to.getPosition());
             map.put( "address", to.getAddress());
             map.put( "detailAddress", to.getDetailAddress());
             map.put( "deptName", to.getDeptName());
             map.put( "usage", usage);
             map.put( "date", requestDay);
             map.put( "end", useDay);

          OutputStream outputStream = null;    
      // 결과가 올바로 넘어 왔는지 출력으로 확인
      for (String key: map.keySet()) { 
    	  System.out.println(key);
    	  System.out.println(map.get(key)); 
      }
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, datasource); //맵에 담았던 값과 제스퍼 리포트를 인자값에 넣어서 JasperPrint를 실행한다.
		//JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Tomcat 9.0\\webapps\\minjoo\\report\\employment.pdf");
		//JasperFillManager= 보고서디자인에 데이터를 채우는 클래스 
	    //fillReport (jasperReport형식, Map형식, dataSource형식)	
		
		  outputStream = response.getOutputStream();	// 응답되어진것에대한적합한 OutputStream을 반환해줌
		  response.setContentType("application/pdf"); 	//PDF형식으로 변환!
		  //JasperExportManager.exportReportToPdfFile(jasperPrint, request.getServletContext().getRealPath("/report/test01.pdf"));
		  JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\dev\\insaWorkspace\\insa\\WebContent\\report\\test01.pdf"); 
		  JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		  //JasperExportManager = 생성된보고서를 pdf,html,xml 형식으로 내보내는 class
	      // 첫번째 매개변수로 생성 된 보고서를 pdf 형식으로 두번째 매개변수로 지정된 출력스트림에 사용함 
		  outputStream.flush();
		  
      } catch (Exception e) {

      } 
      return modelAndView; 
   }
   
   
   
   
   public ModelAndView requestMonthSalary(HttpServletRequest request, HttpServletResponse response) { //월급여신청
      HashMap<String, Object> parameters = new HashMap<>(); //해쉬맵 생성
      parameters.put("empCode", request.getParameter("empCode")); // URL에서 보낸 파라미터값을 맵에 담는다.
      parameters.put("applyMonth", request.getParameter("applyMonth"));
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("utf-8"); 
      
      String empCode = request.getParameter("empCode");
      String applyMonth = request.getParameter("applyMonth");
      
      try {
    	 ReportSalaryTO to = baseServiceFacade.viewSalaryReport(empCode,applyMonth);
    	 Map<String, Object> map = new HashMap<String, Object>();
         
    	 JasperReport jasperReport = JasperCompileManager.compileReport((request.getServletContext().getRealPath("/report/SalaryStatement.jrxml")));
    	 
    	 JRDataSource datasource = new JREmptyDataSource();
    	 
    	 map.put( "empName", to.getEmpName());
    	 map.put( "position", to.getPosition());
    	 map.put( "deptName", to.getDeptName());
         map.put( "hiredate", to.getHiredate());
         map.put( "applyYearMonth", to.getApplyYearMonth());
         map.put( "totalExtSal", to.getTotalExtSal());
         map.put( "totalDeduction", to.getTotalDeduction());
         map.put( "totalPayment", to.getTotalPayment());
         map.put( "realSalary", to.getRealSalary());
         map.put( "salary", to.getSalary());
         map.put( "cost", to.getCost());
         map.put( "healthIns", to.getHealthIns());
         map.put( "goyongIns", to.getGoyongIns());
         map.put( "janggiIns", to.getJanggiIns());
         map.put( "gukmin", to.getGukmin());

         OutputStream outputStream = null;
         // 결과가 올바로 넘어 왔는지 출력으로 확인
         for (String key: map.keySet()) { 
        	 System.out.println(key);
        	 System.out.println(map.get(key)); 
         }
   		 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, datasource); 	//맵에 담았던 값과 제스퍼 리포트를 인자값에 넣어서 JasperPrint를 실행한다.

   		  outputStream = response.getOutputStream();		// 응답되어진것에대한적합한 OutputStream을 반환해줌
   		  response.setContentType("application/pdf"); 		//PDF형식으로 변환!
   		  JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

   		  outputStream.flush();
         
      } catch (Exception e) {
         logger.fatal(e.getMessage());
      }
      return modelAndView;
   }
}

