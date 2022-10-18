<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- ag grid 사용 -->
<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
 <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<jsp:include page="header.jsp" />


<meta name="viewport" content="width=device-width, initial-scale=1">

<title>INSA COMPANY</title>
<decorator:head />
<!--decorator:head가 적용되는 대상 페이지에서 <head>의 내용을 가져다 붙여줍니다  -->
<c:if test="${requestScope.errorMsg != null}">
	<script>
		alert("${requestScope.errorMsg }");
	</script>
</c:if>
<style>
body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif}
body {font-size:16px;}
.w3-half img{margin-bottom:-6px;margin-top:16px;opacity:0.8;cursor:pointer}
.w3-half img:hover{opacity:1}
.dropdown-container {
  z-index:3;width:300px;font-weight:bold;
}
	body {
			font-family: Arial, Helvetica, sans-serif;
		}

		.notification {
			background-color: #555;
			color: yellow;
			text-decoration: none;
			padding: 10px 60px;
			position: relative;
			display: inline-block;
			border-radius: 2px;
		}

		.notification:hover {
			background: white;
		}

		.notification .badge {
			position: absolute;
			top: -10px;
			right: -10px;
			padding: 5px 10px;
			border-radius: 50%;
			background-color: red;
			color: black;
		}
		/* Style The Dropdown Button */
		.dropbtn {
			background-color: YELLOW;
			color: BLACK;
			padding: 10px 60px;
			font-size: 16px;
			border: none;
			cursor: pointer;
		}

		/* The container <div> - needed to position the dropdown content */
		.dropdown {
			position: relative;
			display: inline-block;
		}

		/* Dropdown Content (Hidden by Default) */
		.dropdown-content {
			display: none;
			position: absolute;
			background-color: #f9f9f9;
			min-width: 160px;
			box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
			z-index: 1;
		}

		/* Links inside the dropdown */
		.dropdown-content a {
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
		}

		/* Change color of dropdown links on hover */
		.dropdown-content a:hover {
			background-color: #f1f1f1
		}

		/* Show the dropdown menu on hover */
		.dropdown:hover .dropdown-content {
			display: block;
		}

		/* Change the background color of the dropdown button when the dropdown content is shown */
		.dropdown:hover .dropbtn {
			background-color: WHITE;
		}
</style>
<script>
//채팅기능
	/* $(document).ready(function(){
		getUnread();
		read();
		getInfiniteUnread();
		
	}); */
	function read() {

		var code = "${sessionScope.code}";
		var name = "${sessionScope.name}";
		var dept = "${sessionScope.dept}";
		var companyCode="${sessionScope.companyCode}"; 
		$('nav li').hover(function() {
			$('ul', this).stop().toggle();
		});
		// 로그인이 되지 않으면 기능 메뉴기능 사용불가 
		$("a").click(
				function() {
					if (code == "") {
						if ($(this).attr("id") != "loginTag"
								&& $(this).attr("id") != "joinTag"
								&& $(this).attr("id") != "homeTag"
								&& $(this).attr("id") != "link"
								&& $(this).attr("id") != "loginLink") {
							alert("Please log in.");
							location.href = "http://localhost:8282/insa/loginForm.html";
							return false;
						}
					}
		});
		/*
		if (dept == "인사팀") {
			$("#dTag").css("visibility", "visible");
			$("#dTag1").css("visibility", "visible");
			$("#dTag2").css("visibility", "visible");
			$("#dTag3").css("visibility", "visible");
			$("#dTag4").css("visibility", "visible");
			$("#dTag5").css("visibility", "visible");
			//$("#unread").css("visibility", "visible");
			
		}
		if(dept !="인사팀" && dept!=""){
               $("#dTag").css("visibility", "visible");
               $("#dTag3").css("visibility", "visible");
               //$("#unread").css("visibility", "visible");
        }
		*/
	}

	function getUnread(){
		$.ajax({
		       url : "${pageContext.request.contextPath}/chat/chatUnread.do",
		       dataType : "json",
		       data : {
		          "method" : "chatUnread", 
		          "userID" : "${sessionScope.name}"
		        
		       },
		       success : function(data) {
		    	   //var results=data.chatUnread;
		    	   //console.log("성공");
		    	   if(data.result>=1){
		    		   console.log(data.result);
		    		   showUnread(data.result);
		    		
		    		       
		    	       $("#unread").css("visibility", "visible");
		    	     	
		    		   
		    	   }
		    	   else{
		    		   //console.log("0개");
		    		   showUnread(0);
		    		   $("#unread").css("visibility", "hidden");
		    	   }
		    	  
				}
		});
	}
	//채팅기능 
	
	/* function getInfiniteUnread(){
		//console.log("${sessionScope.id}");
		
		setInterval(function(){
			getUnread();
			
		},1000);
		
	} */
	
	function showUnread(result){
		if(result==0){
			return;
		}
		//console.log("@@@"+result);
		$('#unread').html(result);
	}
	
</script>
</head>
<body>
<div>
	<!-- Sidebar/menu -->
	<nav class="w3-sidebar w3-yellow w3-collapse w3-top w3-large w3-padding" style="z-index:3;width:300px;font-weight:bold;" id="mySidebar"><br>
	  	<a href="javascript:void(0)" onclick="w3_close()" class="w3-button w3-hide-large w3-display-topleft" style="width:100%;font-size:22px">Close Menu</a>
	  	<div class="w3-container">
	    	
	    	<h3 class="w3-padding-64"><b>Seoul It<br>HR management system</b></h3>
	    	
	  	</div>
	  	<div class="w3-bar-block">
		    <a href="${pageContext.request.contextPath}/home.html" id="homeTag" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
		    	<i class="fa fa-home w3-xxlarge"></i>
		    	Home
		    </a>
		    <c:if test="${ code == null }">
				<a id="loginTag" href='${pageContext.request.contextPath}/loginForm.html' onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
	   				<i class="fa fa-connectdevelop w3-xxlarge"></i>
	  			 	Sign-In
	  			</a>
	  			<!-- <a id="joinTag" href="#" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
	    			<i class="fa fa-user-plus w3-xxlarge"></i>
	    			회원가입
	  			</a> -->
  			</c:if>
  			<c:if test="${ code != null }">
				<a href="#" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
					<i class="fa fa-user w3-xxlarge"></i>
					${sessionScope.name} 
				</a>
				<a id="logoutaTag" href='${pageContext.request.contextPath}/logout.do' onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
					<i class="fa fa-sign-out w3-xxlarge"></i>
					Sign-Out
				</a>
				<a id="systemTag" href='#' onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
					<i class="fa fa-cog w3-xxlarge"></i>
					System Settings
				</a>
				<a href="${pageContext.request.contextPath}/board/boardList.html" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
					<i class="fa fa-star"></i>
					Bulletin board
				</a> 
		    	<a href="${pageContext.request.contextPath}/chat/find.html" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
		    		<i class="fa fa-bell"></i>
		    		chatting
		    	</a> 
		    	<a href="${pageContext.request.contextPath}/chat/chatBox.html" onclick="w3_close()" class="notification" >
		    		<span>Message box</span>
		   			<div style="visibility: hidden"> <span id="unread" class="badge"></span></div>
		    	</a> 
		    	<a href="${pageContext.request.contextPath}/weather/weatherMain.html" onclick="w3_close()" class="w3-bar-item w3-button w3-hover-white">
		    		<i class="	fa fa-cloud"></i>
		    		Weather
		    	</a> 
			</c:if>

		   	
	  	</div>
	</nav>
</div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:340px;margin-right:40px">

 <!-- Header -->
	<div class="w3-container" style="margin-top:40px" id="showcase">
	    <jsp:include page="top.jsp" />   	
	    <hr style="width:50px;border:5px solid red" class="w3-round">
	</div>
		<jsp:include page="menu.jsp" /> 
	<div>		
		<decorator:body/>					
	</div>
	<div>
		<center>
			<%@ include file="bottom.jsp"%>
		</center>
	</div>	

			
</body>
</html>
