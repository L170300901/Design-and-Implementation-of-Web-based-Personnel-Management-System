<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	$(document).ready(function(){
		chatBox();
		//getInfiniteBox();
		
	});
	function chatBox(){
		$.ajax({
		       url : "${pageContext.request.contextPath}/chat/chatBox.do",
		       dataType : "json",
		       data : {
		          "method" : "getBox", 
		          "userID" : "${sessionScope.name}"
		        
		       },
		       success : function(data) {
		    	   var results=data.getBox;
		    	   $.each(results , function(i){
			    	 	if(results[i].fromId!='${sessionScope.name}'){
			    	 		console.log("1111111111111111");
			    	 		//console.log(results[i].toId);
			    	 		console.log(results[i].toId);
			    	 		results[i].toId=results[i].fromId;
			    	 		console.log(results[i].toId);
			    	 		
			    	 	}else{
			    	 		console.log("22222222222222");
			    	 		console.log(results[i].fromId);
			    	 		results[i].fromId=results[i].toId;
			    	 		console.log(results[i].fromId);
			    	 		
			    	 	}
			    	 	addBox(results[i].toId,results[i].fromId,results[i].chatContent,results[i].chatTime);
		    	   });
		    	 	
		    	  	
				}
		});
	}
	function addBox(lastID,toID,chatContent,chatTime){
		
		
		$('#boxTable').append('<tr onclick="location.href=\'chatMain.html?toID='+toID+'\'">'+
				'<td><image class="media-object img-circle" style="width:50px;height:50px;" src="${pageContext.request.contextPath}/image/'+toID+'.jpg" alt="" ></td>'+
				'<td style="width:150px;"><h5>'+lastID+'</h5></td>'+
				'<td>'+
				'<h5>'+chatContent+'</h5>'+
				'<div class="pull-right">'+chatTime+'<div>'+
				'</td>'+
				'</tr>');
	}
	function getInfiniteBox(){
		setInterval(function(){	
			chatBox();
		},1000)
		
	}
</script>
</head>
<body>
	<div class="container">
		<table class="table table-bordered table-hover"style="min-width: 100%;">
			<thead>
				<tr>
					<th style="text-align: center; background-color:#47C83E; color:white;"  colspan="2">
						<h4>주고받은 메세지 목록</h4>
					</th>
				</tr>
			</thead>
			<div style="overflow-y: auto; width:100%; max-height:450px;">
				<table class="table table-bordered table-hover" style="min-width: 100%; text-align:center; border:1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable">
					</tbody>
				</table>
			</div>
		</table>
	</div>


</body>
</html>