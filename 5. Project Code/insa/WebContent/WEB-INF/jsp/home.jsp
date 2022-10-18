<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:set var="dept" value="" />
<c:if test="${ not empty sessionScope.name}">
	<!-- sessionScope.name가 있으면 -->
	<c:set var="dept" value="${sessionScope.dept}" />
</c:if>
<%-- <c:set var="position" value="" />
<c:if test="${ not empty sessionScope.id}">
	<c:set var="position" value="${sessionScope.position}" />
</c:if>  --%>
<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.name}">
	<c:set var="name" value="${sessionScope.name}" />
</c:if>
<%-- <c:set var="jpg" value="jpg" /> --%>
<head>
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
}

.flip-card {
  background-color: transparent;
  width: 600px;
  height: 400px;
  perspective: 1000px;
}

.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.6s;
  transform-style: preserve-3d;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
}

.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}

.flip-card-front, .flip-card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
}

.flip-card-front {
  background-color: #bbb;
  color: black;
}

.flip-card-back {
  background-color: #4CAF50;
  color: white;
  transform: rotateY(180deg);
}
</style>
</head>

<body>

	<c:if test="${ code == null }">
		<script>
		location.href="${pageContext.request.contextPath}/loginForm.html";
		</script>		
  	</c:if>
  	<c:if test="${ code != null }">
  		<center>
  			<div>			 
				<div class="flip-card">
					<div class="flip-card-inner">
				    <div class="flip-card-front">	
				       <img src="${pageContext.request.contextPath}/profile2/${code}.jpg" alt="Aatar" style="width:600px;height:400px;">
				    </div>
				    <div class="flip-card-back">
				      <h1>Name: ${name}</h1> 
				      <p>Department: ${dept}</p> 
				      <p>Employee number: ${code}</p>
				    </div>
				  </div>
				</div>
			</div>
  		</center>
  				
	</c:if>
	
	<!-- 별 효과 -->
	<marquee scrollamount=40><font color=red>★</marquee><br>
	<marquee scrollamount=30><font color=blue>★</marquee><br>
	<marquee scrollamount=20><font color=orange>★</marquee><br>
	<marquee scrollamount=10><font color=green>★</marquee><br>
	<marquee scrollamount=20><font color=violet>★</marquee><br>
	<marquee scrollamount=30><font color=pink>★</marquee><br>
	<marquee scrollamount=40><font color=black>★</marquee><br>
	
</body>
</html>