
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/css/custom.css">
<script src="${pageContext.request.contextPath}/css2/js/bootstrap.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>게시판 </title>
<style>
a {
  color:black;
}
</style>
<script>
 
$(document).ready(function() {
	
	$.ajax({
	       url : "${pageContext.request.contextPath}/board/boardList.do",
	       dataType : "json",
	       data : {
	          method : "findBoardList"
	       },
	       success : function(data) {    	   
	    	  var results=data.boardList;
		      var str='<TR>';
		      //colesole.log(typeof(results[i].boardlevel));
		      $.each(results , function(i){
		    	   
		    		   //var level=results[i].boardlevel;
		    		   str += '<TD>'+ results[i].boardID+'</TD><TD style="text-align:left"><a href="boardShow.html?boardID='+results[i].boardID+'&boardGroup='+results[i].boardGroup+'&boardLevel='+results[i].boardlevel+'&boardSequence='+results[i].boardsequence+'">';
		    		   if(results[i].boardlevel>0){
		    			  
		    			   for(var j=0;j<(results[i].boardlevel); j++ ){
		    				   str +='<span class="glyphicon glyphicon-arrow-right" aria-hidden="true">&nbsp </span>';
		    			   }
		    		   }
		    		   str += results[i].boardTitle + '</a></TD><TD>' + results[i].userID + '</TD><TD>' + results[i].boardDate + '</TD><TD>' + results[i].boardHit + '</TD>';
		               str += '</TR>';
		               console.log(str);
		    		   //str +='<span class="glyphicon glyphicon-arrow-right" aria-hidden="true">';
		    		   //str +='</span>';
		    		   
		    	   
		    	 
		                 
		    	   
	              
	          });
	          $("#boardList").prepend(str); 
				}
		    });
});

	
</script>
</head>
<body>
	<div class= "container">
		<table class="table table-bordered table-hover" style="text-align:center; border:1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #47C83E; color:#fafafa;" colspan="5"><h4 >자유 게시판</h4></th>
				</tr>
				<tr>
					<td style="background-color:#fafafa; color: #000000; width: 100px;"><h5>번호</h5></td>
					<td style="background-color:#fafafa; color: #000000;"><h5>제목</h5></td>
					<td style="background-color:#fafafa; color: #000000; width: 100px"><h5>작성자</h5></td>
					<td style="background-color:#fafafa; color: #000000;width: 200px "><h5>작성 날짜</h5></td>
					<td style="background-color:#fafafa; color: #000000; width: 100px;"><h5>조회수</h5></td>
				</tr>
			</thead>
			<tbody id="boardList">
				
				<tr>
					<td colspan="5">
						<a href="${pageContext.request.contextPath}/board/boardWrite.html" style="background-color:#47C83E;" class="btn btn-primary pull-right" type="submit">글 쓰기</a>
						
					</td>
				</tr>
			</tbody>
			
			
		</table>
	</div>
</body>
</html>