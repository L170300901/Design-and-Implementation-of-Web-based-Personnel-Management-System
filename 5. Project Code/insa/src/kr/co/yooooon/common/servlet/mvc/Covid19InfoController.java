package kr.co.yooooon.common.servlet.mvc;

import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.XML;

import kr.co.yooooon.common.servlet.ModelAndView;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;

public class Covid19InfoController extends MultiActionController {
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	BufferedReader br = null;

	public ModelAndView showInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");

		/*
		  Calendar cal = Calendar.getInstance();
		  String year = Integer.toString(cal.get(Calendar.YEAR)); //연월일을 더할때 덧셈이 되지 않도록 문자화 해준다
		  String month = Integer.toString(cal.get(Calendar.MONTH)+1); //월은 0 ~ 11로 표시되므로 +1해준다
		  if(month.length()==1) {month="0"+month;} //1~9월까지는 한자리수만 표기되므로 앞에 0을 붙여준다
		  int date = cal.get(Calendar.DATE);
		  String yesterday = year+month+Integer.toString(date-1);
		  String today = year+month+Integer.toString(date);
		 */
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd"); 
		// Date를 String으로 변경하거나 String을 Date로 변경할 때 사용하는 class
		//SimpleDateFormat formatDate2 = new SimpleDateFormat("E"); // 요일
		Date currentTime = new Date();
		Date dayBefore = new Date();
		//Date now = new Date();
		String today = formatDate.format(currentTime); // 지금날짜
		dayBefore.setTime(currentTime.getTime() - ((long) 1000 * 60 * 60 * 24)*5); 
			// 토,일요일은 업뎃이 안되므로 일요일엔 시작일을 이틀전으로
		/*
		 * if(formatDate2.format(now).equals("일")) { // 토,일요일은 업뎃이 안되므로 일요일엔 시작일을 이틀전으로
		 * dayBefore.setTime(currentTime.getTime() - ((long) 1000 * 60 * 60 * 24)*2);
		 * }else if(formatDate2.format(now).equals("월")) { // 토,일요일은 업뎃이 안되므로 월요일엔 시작일을
		 * 사흘전으로 dayBefore.setTime(currentTime.getTime() - ((long) 1000 * 60 * 60 *
		 * 24)*3); }else if(formatDate2.format(now).equals("화")) { // 토,일요일은 업뎃이 안되므로
		 * 월요일엔 시작일을 사흘전으로 dayBefore.setTime(currentTime.getTime() - ((long) 1000 * 60 *
		 * 60 * 24)*4); }else { dayBefore.setTime(currentTime.getTime() - ((long) 1000 *
		 * 60 * 60 * 24)); }
		 */
		String yesterday = formatDate.format(dayBefore); 

		System.out.println(yesterday+"//"+today);
		try{     
			out = response.getWriter();  
			String serviceKey = "eFyevRHJydGro5IQB8Um61UPTTEzdMQ3L6a4CHr2GSjZpyvsRTqYyMap7EBv6YRCoYHXt3NHeiqIP3kQYrnX0A%3D%3D";
			//String serviceKey = "hV9Zxz2P0r%2FdE8yur%2Fyvpwq1dhUuRvjikkTAcwMYLSHPjjz%2F5P4GvGsxonVO5on5tz3cxg3pf72BPnLsO1RG3g%3D%3D";

			
			StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?"); /*URL*/
			urlBuilder.append("ServiceKey=" + serviceKey); /*Service Key*/
	        urlBuilder.append("&pageNo=1"); /*페이지번호*/
	        urlBuilder.append("&numOfRows=10"); /*한 페이지 결과 수*/
	        urlBuilder.append("&startCreateDt=" + yesterday); /*검색할 생성일 범위의 시작*/
	        urlBuilder.append("&endCreateDt=" + today); /*검색할 생성일 범위의 종료*/
			
			//StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?"); /*URL*/
			//urlBuilder.append("ServiceKey=" + serviceKey); /*Service Key*/
	        //urlBuilder.append("&pageNo=1"); /*페이지번호*/
	        //urlBuilder.append("&numOfRows=10"); /*한 페이지 결과 수*/
	        //urlBuilder.append("&startCreateDt=" + yesterday); /*검색할 생성일 범위의 시작*/
	        //urlBuilder.append("&endCreateDt=" + today); /*검색할 생성일 범위의 종료*/ 
	        
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        org.json.JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
	        String jsonPrettyPrintString = xmlJSONObj.toString(4);
	        System.out.println(jsonPrettyPrintString);
	        out.println(jsonPrettyPrintString);
			
			//String url= "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson";
			//HttpURLConnection conn = (HttpURLConnection) new URL( url
			//		+ "?ServiceKey=" + serviceKey
			//		+ "&pageNo=1&numOfRows=10"
			//		+ "&startCreateDt=" + yesterday + "&endCreateDt=" + today).openConnection();
	        //conn.connect();
	        //BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	        //BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
	        //StringBuffer st = new StringBuffer();
	        //String line;
	        //while ((line = reader.readLine()) != null) {
	        //    st.append(line);
	        //}
	 
	        //org.json.JSONObject xmlJSONObj = XML.toJSONObject(st.toString());
	        //String jsonPrettyPrintString = xmlJSONObj.toString(4);
	        //System.out.println(jsonPrettyPrintString);
	        //out.println(jsonPrettyPrintString);
		}catch(Exception e){
			logger.fatal(e.getMessage());
		}finally {
			out.close();
		}
		return modelAndView;

	}

}