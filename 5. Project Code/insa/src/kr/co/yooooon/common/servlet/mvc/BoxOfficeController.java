package kr.co.yooooon.common.servlet.mvc;

import java.io.PrintWriter;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.yooooon.common.servlet.ModelAndView;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import net.sf.json.JSONObject;

public class BoxOfficeController extends MultiActionController {
	   private ModelAndView modelAndView = null;
	   PrintWriter out = null;
	   BufferedReader br = null;
	   
	   public ModelAndView showBoxOffice(HttpServletRequest request, HttpServletResponse response) {
	      response.setContentType("application/json; charset=UTF-8");
	      try {
	         out = response.getWriter();    
	         
	         Calendar c1 = new GregorianCalendar(); // 날짜가져오는 class 
	         c1.add(Calendar.DATE, -1); // 일별박스오피스 API : 오늘날짜로부터 -1 (영화데이터가오늘날짜는업로드가안됨)
	         SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷
	         
	         String yesterDay = date.format(c1.getTime()); // String으로 저장         
	         // c1.getTime()이  Tue Feb 04 15:16:59 KST 2020 찍히는데 지정해둔 타입으로 날짜형식을변경 
	         
	         String targetDt=yesterDay; //날짜기준
	         String itemPerPage="10"; //한페이지에보여질수
	         String multiMovieYn=""; //다양성 "Y",상업영화 "N" ,default 전체
	         String repNationCd=""; //한국 "k",외국영화 "F" ,default 전체
	         String wideAreaCd=""; //상영지역별조회가능, default 전체

	         String key="6b146419f65095b1292760b0974a7add";  // 공공api에서받은키번호 (신청해서받음)
	         KobisOpenAPIRestService service=new KobisOpenAPIRestService(key);
	         
	         String dailyResponse=service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationCd, wideAreaCd);          
	               
	         JSONObject jsonObject = JSONObject.fromObject(dailyResponse);
	         out.println(jsonObject);          

	      } catch (Exception e) {
	         logger.fatal(e.getMessage());
	      }  finally {
	         out.close();
	      }
	      return modelAndView;
	   }

	}