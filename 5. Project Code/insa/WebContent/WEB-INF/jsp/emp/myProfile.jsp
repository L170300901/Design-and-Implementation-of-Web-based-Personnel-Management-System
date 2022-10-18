<%@page import="kr.co.yooooon.tae.member.MemberBean"%>
<%@page import="kr.co.yooooon.tae.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.id}">
	<c:set var="name" value="${sessionScope.id}" />
</c:if>
<head>
<style>
    body {
        font-family: Arial, Helvetica, sans-serif;
    }

    * {
        box-sizing: border-box;
    }

    /* Full-width input fields */
    input[type=text],
    input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22xp 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }

    /* Add a background color when the inputs get focus */
    input[type=text]:focus,
    input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }

    /* Set a style for all buttons */
    button {
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        width: 100%;
        opacity: 0.9;
    }

    button:hover {
        opacity: 1;
    }

    /* Extra styles for the cancel button */
    .cancelbtn {
        padding: 14px 20px;
        background-color: #f44336;
    }

    /* Float cancel and signup buttons and add an equal width */
    .cancelbtn,
    .signupbtn {
        float: left;
        width: 50%;
    }

    


    /* Style the horizontal ruler */
    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }



    /* Clear floats */
    .clearfix::after {
        content: "";
        clear: both;
        display: table;
    }

    /* Change styles for cancel button and signup button on extra small screens */
    @media screen and (max-width: 300px) {

        .cancelbtn,
        .signupbtn {
            width: 100%;
        }
    }
</style>
</head>
<body>
     <c:set var="name" value="${sessionScope.id}" />
	<%  
	   
	 	String test = (String)pageContext.getAttribute("name") ;
        MemberDAO dao = MemberDAO.getInstance();
        MemberBean memberBean = dao.getUserInfo(test);
    %>

    <table>
            <div style=" width: 60%;">
                <h1>내 정보</h1>
                <p>${name}님의 연락처 정보입니다.</p>
                <hr>
                <label for="empCode"><b>사원번호</b></label>
                <input type="text" value="<%=memberBean.getEmpCode() %>" name="empCode">
				<label for="empCode"><b>이름</b></label>
                <input type="text" value="${name}" name="empCode">
                <label for="empCode"><b>생년월일</b></label>
                <input type="text" value="<%=memberBean.getBirthdate() %>" name="empCode">
                <label for="empCode"><b>성별</b></label>
                <input type="text" value="<%=memberBean.getGender() %>" name="empCode">
                <label for="empCode"><b>전화번호</b></label>
                <input type="text" value="<%=memberBean.getMobileNumber() %>" name="empCode">
                <label for="empCode"><b>주소</b></label>
                <input type="text" value="<%=memberBean.getAddress() %>" name="empCode">
                <label for="empCode"><b>세부주소</b></label>
                <input type="text" value="<%=memberBean.getDetailAddress() %>" name="empCode">
                <label for="name"><b>이메일</b></label>
                <input type="text" value="<%=memberBean.getEmail() %>" name="name">
                <label for="name"><b>최종학력</b></label>
                <input type="text" value="<%=memberBean.getLastSchool() %>" name="name">
                
                <p>소중한 개인정보 이용내역이 궁금하다면 <a href="#" style="color:dodgerblue">개인정보 이용내역 보기</a>.</p>

                <div class="clearfix">
                    <button type="button" onclick="location.href='http://localhost:8282/insa/home.html' "
                        class="cancelbtn">홈으로</button>
                    <button type="button" onclick="location.href='http://localhost:8282/emp/upDate.html' "
                    class="signupbtn">수정하기</button>
                </div>
            </div>
    </table>
        
   
    
	
  
   
</body>
</html>

