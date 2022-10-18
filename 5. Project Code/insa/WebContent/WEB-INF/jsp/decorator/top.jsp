
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="dept" value="" />
<c:if test="${ not empty sessionScope.code}"> <!-- sessionScope.code가 있으면 -->
	<c:set var="dept" value="${sessionScope.dept}" />
</c:if>

<c:set var="position" value="" />
<c:if test="${ not empty sessionScope.code}">
	<c:set var="position" value="${sessionScope.position}" />
</c:if>

<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.code}">
	<c:set var="name" value="${sessionScope.name}" />
</c:if>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.dTagStyle1 {
font-family: "Poppins", sans-serif;
text-align : right;
font-size:20px;
}
.dTagStyle2 {
font-family: "Poppins", sans-serif;
float:right;
font-size:15px;
}
</style>

</head>

<body>
	<a href="http://localhost:8282/insa/home.html">
		<img src="${pageContext.request.contextPath}/image/logo.jpg">
	</a>
	
	<div class="dTagStyle1">
		<font color="#FFBB00">${name}</font>
		<%-- <font color="black">${position}님</font> --%>
	</div>
	<c:if test="${ not empty sessionScope.code}">
		<%--값이 null이 아닐경우 --%>
		<div class="dTagStyle2">
			<font color="black">&nbsp Welcome</font>
		</div>
	</c:if>
	<c:if test="${empty sessionScope.code}">
	<div class="dTagStyle2" style="float:right;">
			<font color="black">Please log in</font>
		</div>
		<%-- <a href='${pageContext.request.contextPath}/loginForm.html' id="loginLink" style="float:right;" target="_blank" class="w3-hover-opacity">
		로그인 
		</a> --%>
		
	</c:if>



</body>
</html>