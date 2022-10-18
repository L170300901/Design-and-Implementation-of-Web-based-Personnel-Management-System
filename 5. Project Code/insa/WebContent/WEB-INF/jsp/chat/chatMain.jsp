<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.code}">
	<c:set var="name" value="${sessionScope.name}" />
</c:if>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/css/bootstrap.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css2/css/custom.css">
<script src="${pageContext.request.contextPath}/css2/js/bootstrap.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Insert title here</title>
<script>
	$(document).ready(function() {
		$("#submitBtn").click(submitFunction);
	});
		/*var findChat = {
				
				"fromId" : "${name}",					
				"toId" : "${param.toID}",
				"chatID": "10"
				//"chatContent" : $("#chatContent").val(),
		};*/
		//var sendData = JSON.stringify(findChat);
		//채팅 화면을 불럿을때 나오는 ajax
		var lastID=0;
		function chatListFunction(type){
			console.log("1111111"+lastID);
			$.ajax({
			       url : "${pageContext.request.contextPath}/chat/findChatList.do",
			       dataType : "json",
			       data : {
			          "method" : "findChatList", 
			          "fromIdD" : "${name}",
			          "toID" : "${param.toID}",
			          "number": type
			       },
			       success : function(data) {
			    	   //alert("성공");
			    	   var results=data.getChatListByRecent;
			    	   console.log(lastID);
			    	   //alert(results[3].fromId);
			    	   //alert(results[0].chatId);
					      var str='<TR>';
					      $.each(results , function(i){
						  		addChat(results[i].fromId,results[i].chatContent,results[i].chatTime)
						  		//console.log(results[i].chatId);
						  		 lastID=Number(results[i].chatId);	
				          });
					      //lastID=Number(parsed.last);	
					     // alert("성공2222");
					}
			});
			
		}
		

	
	function addChat(chatName,chatContent,chatTime){
		/*
		$('#chatList').append(
				'<div class="row">'+
				'<div class="col-lg-12">'+
				'<div class="media">'+
				'<a class="pull-left" href="#">'+
				'<image class="media-object img-circle" style="width:30px;height:30px;" src="${pageContext.request.contextPath}/image/'+chatName+'.jpg" alt="" >'+
				'</a>'+
				'<div class="media-body">'+
				'<h4 style="text-align: left" class="media-heading">'+
				chatName+
				'<span class="small pull-right">'+
				chatTime+
				'</span>'+
				'</h4>'+
				'<p style="text-align: left">'+
				chatContent+
				'</p>'+
				'</div>'+
				'</div>'+
				'</div>'+
				'</div>'+
				'<hr>');
		
		*/
		var str='';
		if(chatName=='${name}'){
			str +='<div class="row"><div class="col-lg-12"><div class="media"><a class="pull-left" href="#"><image class="media-object img-circle" style="width:50px;height:50px;" src="${pageContext.request.contextPath}/image/'+chatName+'.jpg" alt="" >';
			str+='</a><div class="media-body"><h4 style="text-align: left" class="media-heading">';
			console.log(chatName);
			if(chatName=='${name}'){
				str+='나';
			}
			else{
				str+=chatName;
				
			}
			str+='<span class="small pull-right" style="text-align: center"><p>';
			str+=chatTime;
			str+='</p></span></h4><p style="text-align: left">'+chatContent+'</p></div></div></div></div><hr>'
		}
		else{
			
			str +='<div class="row"><div class="col-lg-12"><div class="media"><a class="pull-left" href="#"><image class="media-object img-circle" style="width:50px;height:50px;" src="${pageContext.request.contextPath}/image/'+chatName+'.jpg" alt="" >';
			str+='</a><div class="media-body"><h4 style="text-align: left" class="media-heading">';
			console.log(chatName);
			if(chatName=='${name}'){
				str+='나';
			}
			else{
				str+=chatName;
				
			}
			str+='<span class="small pull-right" style="text-align: center"><p>';
			str+=chatTime;
			str+='</p></span></h4><p style="text-align: left">'+chatContent+'</p></div></div></div></div><hr>'
		}
		
		
		$('#chatList').append(str);
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	function getInfiniteChat(){
		
		setInterval(function(){
			console.log("222222222"+lastID);
			chatListFunction(lastID);
			
		},1000);
	}
		
		function autoClosingAlert(selector,delay){
			var alert= alert=$(selector).alert();
			alert.show();
			window.setTimeout(function(){alert.hide()},delay);	
		}
		//제출을 누를시 db에 데이터 내용 저장 
		function submitFunction(){
			var chatSubmit = {
					
					"fromId" : "${name}",					
					"toId" : "${param.toID}",
					"chatContent" : $("#chatContent").val()
			};
			//alert("222222222");
			var sendData = JSON.stringify(chatSubmit);
			//alert("출발");
			$.ajax({
				url : "${pageContext.request.contextPath}/chat/chatSubmit.do",
				data : {
					"method" : "chatSubmit",
					"sendData" : sendData
				},
				dataType : "json",
				success : function(ressult) {
					//alert("성공")
					autoClosingAlert('#successMessage',1000);
					//alert("성공12221")
				}
				
			}); 
			$("#chatContent").val('');
		}
</script>

</head>
<body>
<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-x2-12">
            <div class="portlet portlet-default">
                <div class="portlet-heading" style="text-align: center; background-color:#47C83E;">
                    <div class="portlet-title">
                        <h4><i class="fa fa-circle text-green"></i> ${param.toID}</h4>
                    </div>
                    <div class="clearfix">
                    </div>
                </div> 
                <div id="chat" class="panel-collapse collapse in">
                    <div id ="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width: auto; height: 500px;">
                     </div>
                    <div class="portlet-footer">
                         <div class="row" style="height:90px;">
                         	<div class="form-group col-xs-10">
                        		<textarea style="height:80px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요" maxlength="100" ></textarea>
                        	</div>
                         	<div class="form-group col-xs-2">
                         		<button type="button" class="btn btn-default pull-right" id="submitBtn">전송</button>
                         	</div>
                         </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="alert alert-success" id="successMessage" style="display:none;">
	<strong>메세지 전송에 성공했습니다</strong>
</div>
<div class="alert alert-daㅜger" id="dangerMessage" style="display:none;">
	<strong>이름과 내용을 모두 입력해주세요</strong>
</div> 
<div class="alert alert-warning" id="warningMessage" style="display:none;">
	<strong>데이터베이스 오류가 발생햇습니다</strong>
</div>  
<script>
	//$('#messegeModal').modal("show");
	$(document).ready(function() {
		chatListFunction(1);
		getInfiniteChat();
	});
</script>

</body>
</html>