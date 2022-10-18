<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<c:set var="dept" value="" />
<c:if test="${ not empty sessionScope.id}">
	<%--값이 null이 아닐경우 --%>
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

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/custom.css">
<script src="${pageContext.request.contextPath}/css/js/bootstrap.js"></script>

   

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Insert title here</title>
<style>
 .cancelbtn {
 background-color: #F15F5F;
       
    }
   .signupbtn{
    background-color:#47C83E;
   
   }

    /* Float cancel and signup buttons and add an equal width */
    .cancelbtn,
    .signupbtn {
        float: left;
        width: 50%;
    }
</style>
<script>


	$(document).ready(function() {
		$("#btn_regist").click(registBoard);
	})
		
	
	
		function registBoard(){
			if($("#boardTitle").val().trim()==""){
				alert("글 게목을 입력하세요.");
				return ;
			}
			if($("#boardContent").val().trim()==""){
				alert("글 내용을 입력하세요.");
				return ;
			}
			//alert("111111111");
			var findBoardWrite = {
					
					"userID" : $("#userID").val(),
					//"boardID": null,
					"boardTitle" : $("#boardTitle").val(),
					"boardContent" : $("#boardContent").val(),
					//"boardDate" : null,
					//"boardHit": null,
					//"boardFile" : null,
					//"boardRealFile" : null,
					//"boardGroup" : null,
					//"boardsequence" : null,
					//"boardlevel" : null
			};
			//alert("222222222");
			var sendData = JSON.stringify(findBoardWrite);
			//alert("출발");
			$.ajax({
				url : "${pageContext.request.contextPath}/board/boardWrite.do",
				data : {
					"method" : "findBoardWrite",
					"sendData" : sendData
				},
				dataType : "json",
				success : function(data) {
					//alert("성공")
					if(data.errorCode < 0){
						alert("게시물이 등록에 실패 하였습니다");
					} else {
						alert("게시물이 등록되었습니다");
						location.href= "boardList.html";
					}
				}
			}); 
		}
</script>
</head>
<body>
	<div class= "container">
		<table class="table table-bordered table-hover" style="text-align:center; border:1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #47C83E; color:#fafafa;" colspan="3"><h4 >게시물 작성 하기</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:150px"><h5>아이디</h5></td>
					<td><h5>${name}</h5>
					<input type="hidden" id="userID" value="${name}"></td>					
				</tr>
				<tr>
					<td style="width:150px"><h5>글 제목</h5></td>
					<td><input class="form-control" type="text" maxlength="50" id="boardTitle" placeholder="글 제목을 입력하세요."></td>					
				</tr>
				<tr>
					<td style="width:110px"><h5>글 내용</h5></td>
					<td><textarea class="form-control" rows="10" id="boardContent" maxlength="2048" placeholder="글 내용을 입력하세요."></textarea></td>					
				</tr>
								
				<tr>
					<td style="text-align:left;" colspan="3">
					<!-- 
					<h5 style="color: red;"></h5>
					<input type="button" class="btn btn-primary pull-right" id="btn_regist" value="등록하기">
					
					<input type="reset" class="btn btn-primary pull-right" value="취소">
					 -->
					
					<div class="clearfix">
                    <button type="button" onclick="location.href='http://localhost:8282/insa/board/boardList.html' "class="cancelbtn">취소하기</button>
                    <button type="button" id="btn_regist" class="signupbtn">등록하기</button>
                </div>
					</td>
				</tr>
				
				
			</tbody>
			
			
		</table>
	</div>

</body>
</html>