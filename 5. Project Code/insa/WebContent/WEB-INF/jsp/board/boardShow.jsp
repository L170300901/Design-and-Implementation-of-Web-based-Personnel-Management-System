<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
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
	
	$("#replyBtn").click(function() {
		replyBoard();
	})
	$("#updateBtn").click(function() {
		var flag = confirm("게시물을 수정 하시겠습니까?");
		if (flag)
		updateBoard();
	})
	$("#delteBtn").click(function() {
		var flag = confirm("게시물을 삭제 하시겠습니까?");
		if (flag)
		deleteBoard();
	})
	
	$.ajax({
		//오직 하나의 게시물만 확인  
	       url : "${pageContext.request.contextPath}/board/boardShow.do",
	       dataType : "json",
	       data : {
	          "method" : "boardShow",
	          "boardID": "${param.boardID}"
	       },
	       dataType:"json",
	       success : function(data) {
	    	   console.log(data);
	    	   var results=data.boardShow;
	    	   console.log(results);
	    	   console.log(results[0].boardTitle);
	    	   $("#boardTitle").html(results[0].boardTitle);
	    	   $("#userID").html(results[0].userID);
	    	   $("#boardDate").html(results[0].boardDate);
	    	   $("#boardHit").html(results[0].boardHit);
	    	   $("#boardContent").html(results[0].boardContent);
	    	   
	    	  /* $("#boardTitle").val(results[0].boardTitle);
	    	   $("#userID").val(results[0].userID);
	    	   $("#boardDate").val(results[0].boardDate);
	    	   $("#boardHit").val(results[0].boardHit);
	    	   $("#boardContent").val(results[0].boardContent);*/
	    	  	    	   
	       }
	    	 
	    });
});
function replyBoard(){
	location.href= "boardReply.html?boardID="+${param.boardID}+"&boardGroup="+${param.boardGroup}+"&boardLevel="+${param.boardLevel}+"&boardSequence="+${param.boardSequence};
	//location.href= "boardReply.html?boardID="+${param.boardID};
	//'&parentBoardGroup='+results[i].boardGroup+'&parentBoardLevel='+results[i].boardlevel+'&parentBoardSequence='+results[i].boardsequence+'">' + results[i].boardTitle + '</a>

}
function updateBoard(){
	location.href= "boardUpdate.html?boardID="+${param.boardID};
}
function deleteBoard(){
	$.ajax({
		url : "${pageContext.request.contextPath}/board/deleteBoard.do",
		data : {
				"method" : "removeBoardList",
				"boardID" : "${param.boardID}"
			},
			dataType : "json",
			success : function(data) {
					if(data.errorCode < 0){
						alert("정상적으로 삭제되지 않았습니다");
					} else {
 							alert("삭제되었습니다");
					}
					location.href= "boardList.html";
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
					<th style="background-color: #47C83E; color:#fafafa;" colspan="4">게시물 보기</th>
				</tr>
				<tr>
					<td style="background-color: #fafafa; color:#000000; width:100px">제목</td>
					<td id="boardTitle" colspan="3"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; color:#000000; width:100px">작성자</td>
					<td id="userID" colspan="3"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; color:#000000; width:100px">작성날짜</td>
					<td id="boardDate"></td>
					<td style="background-color: #fafafa; color:#000000; width:100px">조회수</td>
					<td id="boardHit" colspan="3"></td>
				</tr>
				<tr>
					<td style="vertical-align:middle; height:550px; background-color: #fafafa; color:#000000; width:100px">글내용</td>
					<td id="boardContent" colspan="3" style="text-align:left; height:550px;"></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" style="text-align: right;">	
						<input type="button" value="삭제" id="delteBtn" style="background-color:#47C83E;" class="btn btn-primary pull-right">
						<input type="button" value="답변" id="replyBtn" style="background-color:#47C83E;" class="btn btn-primary pull-right">
						<a href="boardList.html" id="listBtn" style="background-color:#47C83E;" class="btn btn-primary pull-right" type="submit">목록</a>
						<input type="button" value="수정" id="updateBtn" style="background-color:#47C83E;" class="btn btn-primary pull-right">	
					</td>
				</tr>			
			</tbody>
			
		</table>
	</div>
</body>
</html>
