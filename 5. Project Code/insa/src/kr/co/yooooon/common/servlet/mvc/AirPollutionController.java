package kr.co.yooooon.common.servlet.mvc;

import java.io.PrintWriter;
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.yooooon.common.servlet.ModelAndView;
import net.sf.json.JSONObject;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;

public class AirPollutionController extends MultiActionController {
	private ModelAndView modelAndView = null;
	PrintWriter out = null;
	BufferedReader br = null;

	public ModelAndView showDust(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");

		try{     
			out = response.getWriter();          
					  String serviceKey =
					  "3mdiAYH1D2bnbdkEio3XJCqCD15oudYH18XvcLgs8DVRLcbgn4WpNnFalMpgvW8V0bC79bNhELI8m2n7FvaAMQ%3D%3D";

					  String urlstr =
					  "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?"
					  + "serviceKey="+serviceKey + "&numOfRows=18" + "&pageNo=1" +
					  "&sidoName=%EA%B2%BD%EB%82%A8" + "&searchCondition=DAILY" +
					  "&_returnType=json";
					  URL url = new URL(urlstr);
					  HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
					  urlconnection.setRequestMethod("GET"); 
					  br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8")); 
					  String result = ""; 
					  String line; 
					  while((line = br.readLine()) != null) 
					  		{ result = result + line + "\n"; } 
					  //System.out.println(result);

					  JSONObject jsonObject = JSONObject.fromObject(result);
					  out.println(jsonObject);
		}catch(Exception e){
			logger.fatal(e.getMessage());
		}finally {
			out.close();
		}
		return modelAndView;

	}

}