package kr.co.yooooon.base.controller;

import java.sql.*;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.*;

import kr.co.yooooon.common.servlet.*;
import kr.co.yooooon.common.servlet.mvc.*;
import kr.co.yooooon.common.transaction.*;
import net.sf.jasperreports.engine.*;

public class SendEmailController extends MultiActionController{
    private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
    private Multipart multipart;
    
    public ModelAndView sendEmail(HttpServletRequest request, HttpServletResponse response) {
       HashMap<String, Object> parameters = new HashMap<>();
       parameters.put("empCode", request.getParameter("empCode"));
       parameters.put("usage", request.getParameter("usage"));
       parameters.put("date", request.getParameter("requestDay"));
       parameters.put("end", request.getParameter("useDay"));
       

       String eMail = request.getParameter("eMail");

       String host = "smtp.naver.com";
       final String user = ""; 
       final String password = "";
       int port = 465;
       
       String recipient = eMail; //받는 사람의 메일주소를 입력해주세요
       String subject = "메일테스트"; //메일 제목 입력해주세요.
       String body = user+"님으로 부터 메일을 받았습니다."; //메일 내용 입력해주세요.

       
       String toAddress = eMail;

       JasperReport jasperReport;
       JasperPrint jasperPrint;
       Connection con = dataSourceTransactionManager.getConnection();
       try {

    	  //  jasperReport = JasperCompileManager.compileReport("C:\\insa\\insa_first\\WebContent\\report\\employment.jrxml");
         jasperReport = JasperCompileManager.compileReport(request.getServletContext().getRealPath("/report/employment.jrxml"));
         // Connection con = dataSourceTransactionManager.getConnection();
           jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
        // JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\insa\\insa_first\\WebContent\\report\\employment.pdf");
         //JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\insa\\insa_first\\WebContent\\report\\test01.pdf");
          
         
        		  // Get the session object
          Properties props = new Properties();
          props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", port);
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.ssl.enable", "true");
          props.put("mail.smtp.ssl.trust", host);


          Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {      
                return new javax.mail.PasswordAuthentication(user, password);
             }
          });
           MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(""));  
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
           System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@toAddress:   "+toAddress);
             // Subject
             message.setSubject("제목: 메일 전송(아이리포트)");
             multipart = new MimeMultipart();
                   
             // Text
             MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setText("본문내용 : 메일 전송(아이리포트)");
                multipart.addBodyPart(mbp1);

             // send the message
             //if(fileName != null){
                // DataSource source = new FileDataSource("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\insa_first\\report\\test01.pdf");
                DataSource source = new FileDataSource("C:\\dev\\insaWorkspace\\insa\\WebContent\\report\\test01.pdf");
                // DataSource source = new FileDataSource(request.getServletContext().getRealPath("/report/test01.pdf/"));
                
               
                 String usage=request.getParameter("usage");
                 
                 BodyPart messageBodyPart = new MimeBodyPart();
                   messageBodyPart.setDataHandler(new DataHandler(source));
                   messageBodyPart.setFileName(MimeUtility.encodeText(usage, "UTF-8", "B")+".pdf"); //"usage"값 이름 pdf파일
                   multipart.addBodyPart(messageBodyPart);
               
             message.setContent(multipart);
                Transport.send(message);
             System.out.println("메일 발송 성공!");

       } catch (Exception e) {
          e.printStackTrace();
          System.out.println("메일에러" + e);
       }
       return null;
    }
}