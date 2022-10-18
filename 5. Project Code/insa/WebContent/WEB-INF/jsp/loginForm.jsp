<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body, html {
  height: 100%;
  font-family: Arial, Helvetica, sans-serif;
}

* {
  box-sizing: border-box;
}

.bg-img {
  /* The image used */
  background-image: url("${pageContext.request.contextPath}/image/myschool.jpg");
  
  min-height: 380px;
  width: auto; 
  height: 800px;

  /* Center and scale the image nicely */
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}

/* Add styles to the form container */
.container {
  position: absolute;
  right: 0;
  margin: 20px;
  max-width: 400px;
  padding: 16px;
  background-color: white;
}

/* Full-width input fields */
input[type=text],input[type=password]{
  margin: 5px 0 22px 0;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

/* 버튼 스타일 준거 */
.btn {
  background-color: #4CAF50;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}
/* 버튼 HOVER */
.btn:hover {
  opacity: 1;
}
</style>
<script>
$(document).ready(function(){
	
	$("#empPw").keydown(
			function(e){
				
				if(e.keyCode == 13)  
					login(); 
			}
	);
	
	
	//readonly효과주기
	$("#companyCode").attr("readonly",true);
	$("#companyName").attr("readonly",true);
	$("#empCode").attr("readonly",true);
	$("#empName").attr("readonly",true);
	
	
	//회사코드보기 돋보기 버튼을 클릭시 클릭
	$("#btnA").click(function() { 
		getCode("searchCompany");
	})
	//사원코드보기 돋보기 버튼을 클릭시 클릭
	$("#btnB").click(function() { 
		if($("#companyCode").val()==""){
			alert("Enter the company code");
			return;
		}
		getCode("searchEmpCode");
	})
	//로그인버튼을 클릭시
	$("#btnC").click(login);
	
	
});
	function getCode(inputCode){
		var x = (document.body.offsetWidth/2)-(200/2);
		var y= (window.screen.height/ 2)-(300/2);
		option = "width=450; height=500px; left="+x+"; top="+y+"; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open("${pageContext.request.contextPath}/loginCodeWindow.html?code="+ inputCode, "newwins", option);
	}
	//로그인 눌렀을때 실행 되는 함수 
	function login(){
		if($("#companyCode").val()==""){
			alert("Enter the company code");
			$("#companyCode").focus();
			return;
		}
		else if($("#companyName").val()==""){
			alert("Enter the company name");
			$("#companyName").focus();
			return;
		}
		else if($("#empCode").val()==""){
			alert("Enter the employee code");
			$("#empCode").focus();
			return;
		}
		else if($("#empName").val()==""){
			alert("Enter the employee's name");
			$("#empName").focus();
			return;
		}
		else if($("#empPw").val()==""){
			alert("Please enter the employee password");
			$("#empPw").focus();
			return;
		}
		$.ajax({
			url : "${pageContext.request.contextPath}/login.do",
			data:{
				"empName":$("#empName").val(),
				"empPw":$("#empPw").val() 
			},
			dataType : "json",
			success : function(data){
				if(data.me=="enter"){
					location.href="${pageContext.request.contextPath}/home.html";
				}else{					
					alert("The password you entered is invalid.");
				}
			}
		});	  
	}
</script>

<title>main</title>
</head>
<body>
	<div class="bg-img">
		<div class="container">
		
		    <h1>Login</h1>
		    <!--  회사코드 -->
		    <label for="companyCode"><b>Company code &nbsp</b></label>
		    <input type="text" style="width: 200px; height: 40px;" id="companyCode">
		    <button id="btnA">
            	<i class="w3-small-margin-left fa fa-search"></i>
            </button>
            <br/>
            <!--  회사명 -->
            <label for="companyName"><b>Company name &nbsp</b></label>
            <input type="text" style="width: 230px; height: 40px;" id="companyName">
            <br/>
            <!--사원코드-->
            <label for="empCode"><b>Employee code &nbsp</b></label>
		    <input type="text"  style="width: 200px; height: 40px;" id="empCode">
		    <button id="btnB">
            	<i class="w3-small-margin-left fa fa-search"></i>
            </button>
            <br/>
            <!-- 사원명 -->
            <label for="empName"><b>Employee name &nbsp</b></label>
            <input type="text"  style="width: 230px; height: 40px;" id="empName">
            <br/>
            <!-- 사원암호 -->
            <label for="empPw"><b>Employee password &nbsp</b></label>
            <input type="password" placeholder="  Enter Password" style="width: 190px; height: 40px;" id="empPw">
            <br/>
            <!-- 로그인버튼 -->
		    <input class="btn" type="button" value="Login" id="btnC">
		</div>
	</div>
</body>
</html>