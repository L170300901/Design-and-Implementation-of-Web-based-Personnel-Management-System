<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
		$("#btn_regist").click(function() {
			//var flag = confirm("게시물을 수정 하시겠습니까?");
			//if (flag)
			registBoard();
		})
		//$("#btn_regist").click(registBoard);
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
		    	   $("#boardTitle").val(results[0].boardTitle);
		    	  // $("#userID").html(results[0].userID);
		    	  // $("#boardDate").html(results[0].boardDate);
		    	  // $("#boardHit").html(results[0].boardHit);
		    	   $("#boardID").val(results[0].boardID);
		    	   $("#boardContent").val(results[0].boardContent);
		    	   
		    	  /* $("#boardTitle").val(results[0].boardTitle);
		    	   $("#userID").val(results[0].userID);
		    	   $("#boardDate").val(results[0].boardDate);
		    	   $("#boardHit").val(results[0].boardHit);
		    	   $("#boardContent").val(results[0].boardContent);*/
		    	  	    	   
		       }
		    	 
		    });
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
			//alert($("#boardTitle").val());
			var modifyBoard = {
					
					//"userID" : $("#userID").val(),
					"boardID": $("#boardID").val(),
					"boardTitle" : $("#boardTitle").val(),
					"boardContent" : $("#boardContent").val()
					//"boardDate" : null,
					//"boardHit": null,
					//"boardFile" : null,
					//"boardRealFile" : null,
					//"boardGroup" : null,
					//"boardsequence" : null,
					//"boardlevel" : null
			};
			//alert("222222222");
			var sendData = JSON.stringify(modifyBoard);
			//alert($("#boardID").val());
			$.ajax({

				url : "${pageContext.request.contextPath}/board/boardUpdate.do",
				data : {
					"method" : "modifyBoard",
					"sendData" : sendData
				},
				dataType : "json",
				success : function(data) {
					//alert("성공");
					if(data.errorCode < 0){
						alert("게시물이 수정에 실패 하였습니다");
					} else {
						alert("게시물이 수정 되었습니다");
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
					<th style="background-color: #47C83E; color:#fafafa;" colspan="3"><h4 >게시물 수정 하기</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width:150px"><h5>아이디</h5></td>
					<td><h5>${name}</h5>
					<input type="hidden" id="userID" value="${name}">
					<input type="hidden" id="boardID">		
					</td>
						
				</tr>
				<tr>
					<td style="width:150px"><h5>글 제목</h5></td>
					<td><input class="form-control" type="text" maxlength="50" id="boardTitle"></td>					
				</tr>
				<tr>
					<td style="width:110px"><h5>글 내용</h5></td>
					<td><textarea class="form-control" rows="10" id="boardContent" maxlength="2048"></textarea></td>					
				</tr>
								
				<tr>
					<td style="text-align:left;" colspan="3">
					<!-- 
					<h5 style="color: red;"></h5>
					<input type="button" class="btn btn-primary pull-right" id="btn_regist" value="등록하기">
					
					<input type="reset" class="btn btn-primary pull-right" value="취소">
					 -->
					
					<div class="clearfix">
                    <button type="button" onclick="location.href= 'boardShow.html?boardID='+${param.boardID}; "class="cancelbtn">뒤로가기</button>
                    <button type="button" id="btn_regist" class="signupbtn">수정하기</button>
                </div>
					</td>
				</tr>
				
				
			</tbody>
			
			
		</table>
	</div>

</body>
</html>