<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="dept" value="" />
<c:if test="${ not empty sessionScope.code}">
	<%--값이 null이 아닐경우 --%>
	<c:set var="dept" value="${sessionScope.dept}" />
</c:if>

<%-- <c:set var="position" value="" />
<c:if test="${ not empty sessionScope.code}">
	<c:set var="position" value="${sessionScope.position}" />
</c:if> --%>

<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.code}">
	<c:set var="name" value="${sessionScope.name}" />
</c:if>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>


.navbar {
	overflow: hidden;
	background-color: #47C83E;
}

.navbar a {
	float: left;
	font-size: 16px;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

.subnav {
	float: left;
	overflow: hidden;
	
}

.subnav .subnavbtn {
	font-size: 16px;
	border: none;
	outline: none;
	color: white;
	padding: 14px 16px;
	background-color: inherit;
	font-family: inherit;
	margin: 0;
}

.navbar a:hover, .subnav:hover .subnavbtn {
	background-color: #ABF200;
}

.subnav-content {
	display: none;
	position: absolute;
	/* left: 300; */
	background-color: #B7F0B1;
	/* width: 100%; */
	 width: 500px;
	z-index: 1;
}

.subnav-content a {
	
	float: left;
	color: black;
	text-decoration: none;
	
	width: 500px;
	 text-align: left;
}

.subnav-content a:hover {
	background-color: #eee;
	color: black;
}

.subnav:hover .subnav-content {
	display: block;
}
</style>
<script>
	$(document).ready(
			function() {

				var id = "${sessionScope.code}";
				//var position = "${sessionScope.position}";
				var dept = "${sessionScope.dept}";
				//console.log($("#dTag1 a:nth-child(1)").text());
				if (dept=="인사팀" && position=="팀장") {
					$("#dTag").css("visibility", "visible");
					$("#dTag1").css("visibility", "visible");
					$("#dTag2").css("visibility", "visible");
					$("#dTag3").css("visibility", "visible");
					$("#dTag4").css("visibility", "visible");
					$("#dTag5").css("visibility", "visible");
				}else if(dept == "인사팀" && position!="팀장"){
		               $("#dTag").css("visibility", "visible");
		               $("#dTag3").css("visibility", "visible");
		               $("#dTag1 a:nth-child(2)").remove(); //팀장외에는 사원상세조회 못함
		               $("#dTag1").css("visibility", "visible");
				}else if(dept != ""){
					   $("#dTag").css("visibility", "visible");
		               $("#dTag3").css("visibility", "visible");
					}
			});
</script>
</head>

<body>
	<div class="navbar">
		<div class="subnav">
			<button class="subnavbtn">
				Employee's menu <i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				
				<a href="${pageContext.request.contextPath}/attendance/dayAttendance.html" id="emp2">Day attendance record form</a> 
				<a href="${pageContext.request.contextPath}/attendance/restAttendance.html" id="emp3">Early leave/Go out request form</a> 
				<a href="${pageContext.request.contextPath}/attendance/break.html" id="emp4">Vacation/time off request form</a> 
				<a href="${pageContext.request.contextPath}/attendance/travel.html" id="emp5">Business trip request form/Continuing education request form</a> 
				<a href="${pageContext.request.contextPath}/attendance/overwork.html" id="emp6">Overtime request form</a> 
				<a href="${pageContext.request.contextPath}/certificate/employment.html" id="emp7">Certificate of employment form</a>
			</div>
		</div>
		
		<div class="subnav">
			<button class="subnavbtn">
				Basic environment setting <i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				<!-- <a href="#">소득/세액공제환경설정</a> -->
				<a href="${pageContext.request.contextPath}/base/payCheck.html">Register the Hobong table</a>
				<!-- <a href="#">급/상여지급일자등록</a> -->
				
				<!--<a href="${pageContext.request.contextPath}/salary/pymntDditm.html">Registration of payment deduction items</a>-->
				<a href="${pageContext.request.contextPath}/salary/socialInsure.html">Social insurance environment setting</a>
				<!-- <a href="#">인사/급여환경등록</a> -->
				<a href="${pageContext.request.contextPath}/base/codeList.html">Registration of the basic human resources code</a>
		
				<a href="${pageContext.request.contextPath}/base/holidayList.html">Holiday inquiry</a>
			</div>
		</div>
		
		<div class="subnav">
			<button class="subnavbtn">
				Personnel management<i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				<a href="${pageContext.request.contextPath}/new_emp/empFind_new.html" id="emp1">Employee information inquiry</a> 
				<a href="${pageContext.request.contextPath}/attendance/attendanceApploval.html">Approval management</a>
				<a href="${pageContext.request.contextPath}/certificate/certificateApproval.html">Management of employment certificate</a>
			</div>
		</div>
		
		
		
		<div class="subnav">
			<button class="subnavbtn">
				Attendance management <i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				<a
					href="${pageContext.request.contextPath}/attendance/dayAttendanceManage.html">Daily attendance management</a>
				<a
					href="${pageContext.request.contextPath}/attendance/monthAttendanceManage.html">Month attendance management</a>
			</div>
		</div>
		<div class="subnav">
			<button class="subnavbtn">
				Payroll management<i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				<a href="${pageContext.request.contextPath}/salary/fullTimeSalary.html">Calculation of salary for regular workers</a>
				<!--  
				<a
					href="${pageContext.request.contextPath}/salary/baseSalaryManage.html">급여기준관리</a>
				
				<a
					href="${pageContext.request.contextPath}/salary/baseDeductionManage.html">공제기준관리</a>
				-->
				<a
					href="${pageContext.request.contextPath}/salary/baseExtSalManage.html">Excess allowance management</a>
			</div>
		</div>
		<div class="subnav">
			<button class="subnavbtn">
				System settings <i class="fa fa-caret-down"></i>
			</button>
			<div class="subnav-content">
				<a href="${pageContext.request.contextPath}/system/companyRegister.html">Company registration</a>
				<a href="${pageContext.request.contextPath}/system/establishmentRegister.html">Business registration</a>
				<a href="${pageContext.request.contextPath}/system/deptRegister.html">Department registration</a>
				<a href="${pageContext.request.contextPath}/system/empRegister.html">Employee registration</a>
				<%-- <a href="${pageContext.request.contextPath}/system/systemConfigurate.html">(정비)시스템환경설정</a>
				<a href="${pageContext.request.contextPath}/system/userPermissionSet.html">(정비)사용자권한설정</a> --%>
			</div>
		</div>

	</div>
	<br />
	<br />
</body>
<hr>
</html>

