<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>


<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.id}">
	<c:set var="name" value="${sessionScope.id}" />
</c:if>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/css/custom.css">
<script src="${pageContext.request.contextPath}/css/js/bootstrap.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script>
function button1_click() {
	alert("버튼1을 누르셨습니다.");
	uri:"${pageContext.request.contextPath}/main.jsp";
	}
$(document).ready(function() {
	
	$('#submit_Btn').click(function() {
		findFunction();
	})
	
	
});
	function findFunction(){
		//alert("출발");
		var userID=$('#findID').val();
		//alert(userID);
		$.ajax({
			
		       url : "${pageContext.request.contextPath}/chat/userCheck.do",
		       dataType : "json",
		       data : {
		          "method" : "userCheck",
		          "userID": userID
		       },
		       dataType:"json",
		       success : function(result) {
		    	  console.log(result);
	  	    	   if(result.result==0){
	  	    		 //console.log("성공");
	  	    		   //$('#checkMessage').html('사원찾기에 성공했습니다');
	  	    		   //$('#checkType').attr('class','modal-content panel-success');
	  	    		   	alert("사원찾기에 성공했습니다")
	  	    		 	getFriend(userID);
	  	    	   }
	  	    	   else{
	  	    		 alert("사원찾기에 실패했습니다")
	  	    		 //alert("실패");
	  	    		 //$("#checkMessage").html("사원찾기에 실패했습니다");
	  	    		 //$("#checkType").attr('class','modal-content panel-warning');
	  	    		failFrieend();
	  	    	   }
	  	    	 $("#checkModal").modal("show");
		       }
		    	 
		    });
		
	}
	function getFriend(findID){
		//alert("getFriend");
		$("#friendResult").prepend(
				'<tr>'+
				'<th style="text-align: center; background-color:#47C83E; color:white;"  colspan="2"><h4>검색 결과</h4></th>'+
				'</tr>'+
				'</thead>'+
				'<tbody>'+
				'<tr>'+
				'<td style="text-align: center;"><br/><h4>'+findID+ '</h3><a href="chatMain.html?toID='+findID+'" class="btn btn-primary pull-right" style="background-color:#47C83E;">'+'메세지 보내기</a></td>'+
				'</h3></td>'+
				'</tr>'
				
				);
	}
	function failFrieend() {
		//alert("failFrieend")
		$("#friendResult").html('');
				
	}
		
</script>

<title>Insert title here</title>

</head>
<body>
	<div class="container">
		<table class="table table-bordered table-hover">

			<thead>
				<tr>
					<th style="text-align: center; background-color:#47C83E; color:white;"  colspan="2">
						<h4>검색으로 사원 찾기</h4>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr style="text-align: center;">
					<td style="width:130px;"><h6>사원 이름</h6></td>
					<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 사원 이름을 입력하세요"></td>
				</tr>
				<tr style="text-align: right;">	
						<td colspan="2">
						<input type="button" value="찾기 " id="submit_Btn" style="background-color:#47C83E;" class="btn btn-primary pull-right">
						</td>
						
				</tr>
				
			</tbody>
		</table>
	</div>
	<div class="container">
		<table class="table-bordered table-hover" style="width: 100%;">
			<tbody id="friendResult">
					
					<!-- 
					<td>
						<button type="button" onclick="location.href= 'chatMain.html?toID='+$('#findID').val();" style="background-color:#47C83E;" class="btn btn-primary pull-right" >메세지 보내기</button>
					</td>
					 -->
			</tbody>
		</table>
	</div>
<!-- 
<script>
		$('#messageModal').modal("show");
	</script>
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-allignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="checkType" class="modal-content panel-info">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-disniss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>							
						</button>
						<h4 class="modal-title">
							확인메시지
						</h4>
						
					</div>
					<div id="checkMessage" class="modal-body">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인 </button>
					</div>
				</div>
			</div>
		</div>
	</div>
	 -->
</body>
</html>