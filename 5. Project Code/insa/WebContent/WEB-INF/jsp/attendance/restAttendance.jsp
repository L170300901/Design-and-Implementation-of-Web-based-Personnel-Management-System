<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#restAttd_tabs {
		display: inline-block;
		/* inline-block 설정하면 <div>, <p>, <h1>도 다른 엘리먼트들과 나란히 배치가 가능해짐
		   width와 height 속성 지정 및 margin과 padding 속성의 상하 간격 지정이 가능해집니다. */
	}
	
	/* 
	td{
		border:1px solid navy;
	} 
	*/
	table {
		z-index: 100;
	}
	
	input {
		z-index: 500;
	}
</style>

<script>
	var empCode = "${sessionScope.code}";
	var startTime = 0;
	var endTime = 0;
	var restAttdList = [];
	var requestDate = getDay();
	
	$(document).ready(function() {
		$("#restAttd_tabs").tabs(); // jqueryUI로 id가 restAttd_tabs인 엘리먼트에 탭 스타일을 적용시킨다
		showRestAttdListGrid(); // 외출/조퇴신청 그리드 띄우는 함수 
		$("#selectRestAttd").click(function() { //신청탭에 근태외 구분 버튼 클릭
			getCodeRest("T01", "selectRestAttd", "selectRestAttdCode");
		})			  //  조퇴       ,   공외출   ,  사외출
		
		$("#selectRestAttdType").click(function() { // 조회탭에 구분 버튼 클릭
			getCodeRest("T01", "selectRestAttdType", "selectRestAttdTypeCode");
		})
		
		
		/* 시간 선택 함수 */
		$("#restAttd_startT").timepicker({ //신청탭 시작시간
			step: 5,            
			timeFormat: "H:i", //시간형식 : "시 : 분"
			minTime: "8:00am", //최소시간(시작시간)
			maxTime: "11:59pm" //최대시간(끝시간)
		});
		$("#restAttd_endT").timepicker({ //신청탭 종료시간
			step: 5,            
			timeFormat: "H:i",    
			minTime: "8:00am",
			maxTime: "11:59pm"	
		});
	  	
	  
		
		$("#search_startD").click(getDatePicker($("#search_startD"))); //조회탭 시작일 ..getDatePicker-달력띄우기 함수
		$("#search_endD").click(getDatePicker($("#search_endD"))); // 조회탭 종료일
		
		$("#search_restAttdList_Btn").click(findrestAttdList); // 조회탭 조회버튼 ..findrestAttdList-근태외목록 조회버튼
		
		$("#delete_restAttd_Btn").click(function(){ // 조회탭 삭제버튼
			var selectedRowData=gridOptions.api.getSelectedRows();
			var sendData = JSON.stringify(selectedRowData);
			
			if(selectedRowData == ""){
				alert("Please select an item to delete.");
			}else{
			var flag = confirm("Do you really want to delete the selected application?");
			if(flag)
				removeRestAttdList(); /* 삭제버튼 눌렀을 때 실행되는 함수 */
			}
		});
		
		
		
		$("#restAttd_startD").click(getDatePicker($("#restAttd_startD"))); //신청탭 시작일 버튼
			
		$("#restAttd_startD").change(function(){ // 신청탭 시작일 변경되면 작동
			if($("#selectRestAttdCode").val().trim()=="DAC004"||$("#selectRestAttdCode").val().trim()=="ADC003"||$("#selectRestAttdCode").val().trim()=="ADC005"){
		  	// 조퇴,공외출,사외출이라면
			    $("#restAttd_endD").val($(this).val()); // 시작일과 종료일을 같게한다
			    toDay = $("#restAttd_startD").val();
			};
			$("#restAttd_endD").datepicker("option","minDate",$(this).val()); //종료일의 날짜선택옵션의 시작일이 현재값과 같게 변경한다
			if($("#restAttd_endD").val()!="")
				calculateNumberOfDays(); /* 일수 계산 함수  */
		}); 
			
			
		$("#restAttd_endD").click(getDatePicker($("#restAttd_endD"))); // 신청탭 종료일 버튼
		$("#restAttd_endD").change(function(){ // 신청탭 종료일이 변경되면 호출됨
			$("#restAttd_startD").datepicker("option","maxDate",$(this).val()); //시작일의 날짜선택옵션의 종료일이 현재값과 같게 변경
			if($("#restAttd_startD").val()!="")
				calculateNumberOfDays(); /* 일수 계산 함수  */
		}); 
			
		$("#btn_regist").click(registrestAttd); // 신청탭 신청버튼
			
		/* 사용자 기본 정보 넣기 */
		$("#restAttd_emp").val("${sessionScope.name}");
		$("#restAttd_dept").val("${sessionScope.dept}");
		$("#restAttd_position").val("${sessionScope.position}");
	})
		
		
		
	/* timePicker시간을 변경하는 함수 */
	function convertTimePicker(){
		startTime = $("#restAttd_startT").val().replace(":",""); //신청탭 시작시간
		endTime = $("#restAttd_endT").val().replace(":",""); //신청탭 종료시간
		
		if(startTime.indexOf("00")==0){
			startTime = startTime.replace("00","24");
		}
		if(endTime.indexOf("00")==0){
			endTime = endTime.replace("00","24");
		}
	}
		
	/* 오늘 날자를 RRRR-MM-DD 형식으로 리턴하는 함수 */
	function getDay(){
	    var today = new Date();
	    var rrrr = today.getFullYear();
	    var mm = today.getMonth()+1;
	    var dd = today.getDate();
	    var searchDay = rrrr+"-"+mm+"-"+dd;
		return searchDay;
	}
	
	
	/* 근태외목록 조회버튼  */
	function findrestAttdList(){
		//alert("조회 되었습니다");
		var startVar = $("#search_startD").val();
		var endVar = $("#search_endD").val();
		var code = $("#selectRestAttdTypeCode").val();
		if($("#selectRestAttdType").val()==""){
			alert("Please choose a code name.");
			return;
		}else if($("#search_startD").val()=="" || $("#search_endD").val()==""){
			alert("Please choose the date.");
			return;
		}
		
		 $.ajax({
			url:"${pageContext.request.contextPath}/attendance/restAttendance.do",
			data:{
				"method" : "findRestAttdList",
				"empCode" : empCode,
				"startDate" : startVar,
				"endDate" : endVar,
				"code": code
			},
			dataType:"json",
			success : function(data){
				if(data.errorCode < 0){
					var str = "There was an internal server error\n";
					str += "Please contact your manager\n";
					str += "Error position : " + $(this).attr("id");
					str += "Error message : " + data.errorMsg;
					alert(str);
					return;
				}
	
				restAttdList = data.restAttdList;
	
					
				/* 시간형태변경포문부분 */
				for(var index in restAttdList){
					restAttdList[index].startTime = getRealTime(restAttdList[index].startTime);
					restAttdList[index].endTime = getRealTime(restAttdList[index].endTime);
				}  
					showRestAttdListGrid();
			}
		}); 
	}
	
	/* 숫자로 되있는 시간을 시간형태로  */
	function getRealTime(time){
		var hour = Math.floor(time/100);
		if(hour==25) hour=1; 
		var min = time-(Math.floor(time/100)*100);
		if(min.toString().length==1) min="0"+min; //분이 1자리라면 앞에0을 붙임
		if(min==0) min="00";
		return hour + ":" + min;
	}
	
		
		
		
	/* 삭제버튼 눌렀을 때 실행되는 함수 */
	function removeRestAttdList(){
		var selectedRowData=gridOptions.api.getSelectedRows();
	
	/* 		$.each(selectedRowIds, function(index, id){ //클릭한 회원들을 each로 풀어서 id를 얻음
				var data = $("#restAttdList_grid").getRowData(id); //얻은 아이디로 조회목록 해당아이디 직원의 모든데이터를 가져옴
				if(selectedRowData[0].applovalStatus!='승인')	//가져온 데이터의 승인여부가 '승인'이 아닐경우에 배열에 담음 
					selectedRowData.push(data);
			});
			
			if(selectedRowData.length == 0){ //배열에 담긴 데이터가 없을때에 발생하는 alert
				alert("승인되지 않은 삭제할 근태외정보가 없습니다\n삭제할 근태외정보를 선택해 주세요");
				return;
			}
	 */		
		var sendData = JSON.stringify(selectedRowData); //삭제할 목록들이 담긴 배열
	
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "removeRestAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("It's not deleted normally.");
				} else {
					alert("It's been deleted.");
				}
				location.reload();
			}
		});
	}
		
		
	/* 근태외신청 그리드 띄우는 함수 */
	function showRestAttdListGrid(){
		var columnDefs = [
		      {headerName: "Employee code", field: "empCode",hide:true },
		      {headerName: "Serial number", field: "restAttdCode",hide:true },
		      {headerName: "Classification of absenteeism and tardiness", field: "restTypeCode",hide:true},
		      {headerName: "Code name", field: "restTypeName",checkboxSelection: true},
		      {headerName: "Application date", field: "requestDate"},
		      {headerName: "The start date", field: "startDate"},
		      {headerName: "End date", field: "endDate"},
		      {headerName: "The number of days", field: "numberOfDays"},
		      {headerName: "The start time", field: "startTime"},
		      {headerName: "Time to end", field: "endTime"},
		      {headerName: "reason", field: "cause"},
		      {headerName: "Approval status", field: "applovalStatus"},
		      {headerName: "Reasons for rejection", field: "rejectCause"}
		];    
		gridOptions = {
				columnDefs: columnDefs,
				rowData: restAttdList,
				defaultColDef: { editable: false, width: 100 },
				rowSelection: 'multiple', /* 'single' or 'multiple',*/
				enableColResize: true,
				enableSorting: true,
				enableFilter: true,
				enableRangeSelection: true,
				suppressRowClickSelection: false,
				animateRows: true,
				suppressHorizontalScroll: true,
				localeText: {noRowsToShow: "There's no result"},
				getRowStyle: function (param) {
			        if (param.node.rowPinned) {
			            return {'font-weight': 'bold', background: '#dddddd'};
			        }
			        return {'text-align': 'center'};
			    },
			    getRowHeight: function(param) {
			        if (param.node.rowPinned) {
			            return 30;
			        }
			        return 24;
			    },
			    // GRID READY 이벤트, 사이즈 자동조정 
			    onGridReady: function (event) {
			        event.api.sizeColumnsToFit();
			    },
			    // 창 크기 변경 되었을 때 이벤트 
			    onGridSizeChanged: function(event) {
			        event.api.sizeColumnsToFit();
			    },
			    onCellEditingStarted: function (event) {
			        console.log('cellEditingStarted');
			    }, 
		};   
		$('#restAttdList_grid').children().remove();	 
		var eGridDiv = document.querySelector('#restAttdList_grid');
		new agGrid.Grid(eGridDiv, gridOptions);	
	}	 
		
		
		
	/* 일수 계산 함수  */
	function calculateNumberOfDays(){
	    var startStr = $("#restAttd_startD").val();
		var endStr = $("#restAttd_endD").val();
		if($("#selectRestAttdCode").val().trim()=="ASC006"||$("#selectRestAttdCode").val().trim()=="ASC007"){
		    $("#restAttd_day").val(0.5);
		}else if($("#selectRestAttdCode").val().trim()=="ASC001"||$("#selectRestAttdCode").val().trim()=="ASC004"||$("#selectRestAttdCode").val().trim()=="ASC005"){
			
			$.ajax({ 
				url:"${pageContext.request.contextPath}/base/holidayList.do",
				data:{
					"method" : "findWeekDayCount",
					"startDate" : startStr,
					"endDate" : endStr
				},
				dataType:"json",
				success : function(data){
					if(data.errorCode < 0){
						var str = "There was an internal server error.\n";
						str += "Please contact your manager\n";
						str += "Error position : " + $(this).attr("id");
						str += "Error message : " + data.errorMsg;
						alert(str);
						return;
					}
					$("#restAttd_day").val(data.weekdayCount);
				}
			});
			
		}else{ // 그 외에는 주말 및 공휴일을 포함한 일자를 가져온다
			var startMs = (new Date(startStr)).getTime();
			var endMs = (new Date(endStr)).getTime();
			var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
			$("#restAttd_day").val(numberOfDays);
		}
	}
	

	/* 코드선택 창 띄우기 */
	function getCodeRest(code, inputText, inputCode) {
		option = "width=300; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open(
				"${pageContext.request.contextPath}/base/detailCodeWindow.html?code="
						+ code + "&inputText=" + inputText + "&inputCode="
						+ inputCode, "newwins", option);
	}
	
	/* 달력띄우기 함수*/
	function getDatePicker($Obj) {
		$Obj.datepicker({
			defaultDate : "d",
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy/mm/dd",
			dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
			monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9","10", "11", "12" ],
			yearRange : "2021:2022"
		});
	}
		
	/* 신청 버튼  */
	function registrestAttd(){
		if($("#selectRestAttd").val().trim()==""){
			alert("Please enter the code name.");
			return ;
		}
		if($("#restAttd_startD").val().trim()==""){
			alert("Please enter the start date.");
			return ;
		}
		if($("#restAttd_endD").val().trim()==""){
			alert("Please enter the end date.");
			return ;
		}
		if($("#restAttd_startT").val().trim()==""){
			alert("Please enter the start time.");
			return ;
		}
		if($("#restAttd_endT").val().trim()==""){
			alert("Please enter the end time.");
			return ;
		}
		convertTimePicker();//timePicker에서 선택된 시간의 형식을 '12:34'에서 '1234'로 변환
		
		var restAttdList = {
				"empCode" : empCode,
				"restTypeCode" : $("#selectRestAttdCode").val(),
				"restTypeName" : $("#selectRestAttd").val(),
				"requestDate" : requestDate,
				"startDate" : $("#restAttd_startD").val(),
				"endDate" : $("#restAttd_endD").val(),
				"numberOfDays" : $("#restAttd_day").val(),
				"cause" : $("#restAttd_cause").val(),
				"applovalStatus" : 'Waiting for approval',
				"startTime" : startTime,
				"endTime" : endTime
		};
	
		var sendData = JSON.stringify(restAttdList);
		console.log(sendData);
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "registRestAttd",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("failed to apply");
				} else {
					alert("It's been applied.");
				}
				location.reload();
			}
		});	
	}
	
</script>
</head>

<body>
	<h4>Early leave/Go out request form</h4> 
	<hr/>
	<center>
	<div id="restAttd_tabs" style="width: 1500px; height: 600px; margin: auto;">
		<ul>
			<li><a href="#restAttdAttd_tab">Application</a></li>
			<li><a href="#restAttdSerach_tab">Inquiry</a></li>
		</ul>
		
		<div id="restAttdAttd_tab">
			<font>Choice</font> 
			<input id="selectRestAttd" class="ui-button ui-widget ui-corner-all" readonly> 
			<input type="hidden" id="selectRestAttdCode" name="restAttdCode">
			<hr>
			<table>
				<tr>
					<td><font>Applicant</font></td>
					<td><input id="restAttd_emp" class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td>
						<font>Department </font>
					</td>
					<td>
						<input id="restAttd_dept" class="ui-button ui-widget ui-corner-all" readonly>
					</td>
					<td>
						<font>Position</font>
					</td>
					<td>
						<input id="restAttd_position" class="ui-button ui-widget ui-corner-all" readonly>
					</td>
				</tr>
				<tr>
					<td>
						<h3></h3>
					</td>
				</tr>
				<tr>
					<td>
						<font>The start date</font>
					</td>
					<td>
						<input id="restAttd_startD" class="ui-button ui-widget ui-corner-all" readonly>
					</td>
					<td>
						<font>End date</font>
					</td>
					<td>
						<input id="restAttd_endD" class="ui-button ui-widget ui-corner-all" readonly>
					</td>
				</tr>
				<tr>
					<td>
						<h3></h3>
					</td>
				</tr>
				<tr>
					<td>
						<font>The start time</font>
					</td>
					<td>
						<input id="restAttd_startT" class="ui-button ui-widget ui-corner-all" name="timePicker1" placeholder="Time selection">
					</td>

					<td>
						<font>Time to end</font>
					</td>
					<td>
						<input id="restAttd_endT" class="ui-button ui-widget ui-corner-all" name="timePicker2" placeholder="Time selection">
					</td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>The number of days</font></td>
					<td><input id="restAttd_day"class="ui-button ui-widget ui-corner-all" readonly></td>
					<!-- <td><font>증명서</font></td>
					<td><input id="restAttd_certi"
						class="ui-button ui-widget ui-corner-all" readonly></td> // 아무 기능구현이 없어서 주석처리 -->
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>Reason</font></td>
					<td colspan="3">
						<input id="restAttd_cause" autocomplete="off" style="width: 490px" class="ui-button ui-widget ui-corner-all">
					</td>
				</tr>
			</table>
			
			<hr>
			
			<input type="button" class="ui-button ui-widget ui-corner-all" id="btn_regist" value="Application"> 
			<input type="reset" class="ui-button ui-widget ui-corner-all" value="Cancellation">
			
		</div>

		<div id="restAttdSerach_tab">
			<table>
				<tr>
					<td colspan="2">
					<center>
						<h2>Choose the range of views</h2>
					</center></td>
				</tr>	
				<tr>
					<td>
						<h3 align="center">Choice</h3>
					</td>
					<td>
						<input id="selectRestAttdType" class="ui-button ui-widget ui-corner-all" readonly> 
						<input type="hidden" id="selectRestAttdTypeCode">
					</td>
				</tr>
				<tr>
					<td>
						<input type=text class="ui-button ui-widget ui-corner-all"id="search_startD" readonly>
						~
					</td>
					<td>
						<input type=text class="ui-button ui-widget ui-corner-all"id="search_endD" readonly>
					</td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td colspan="2">
						<center>
							<button class="ui-button ui-widget ui-corner-all" id="search_restAttdList_Btn">Application</button>
							<button class="ui-button ui-widget ui-corner-all" id="delete_restAttd_Btn">Elimination</button>
						</center>
					</td>
				</tr>
			</table>
			<hr>
			<div id="restAttdList_grid" style="height: 230px; width: 1450px" class="ag-theme-balham"></div>
			<div id="restAttdList_pager"></div>
		</div>
	</div>
	</center>
</body>
</html>