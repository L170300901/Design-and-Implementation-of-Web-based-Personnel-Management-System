<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#overwork_tabs {
	display: inline-block;
} /* 
td{
	border:1px solid navy;
} */
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
	var endTimeHour = 0;
	var overworkList = [];
	var requestDate = getDay();
	$(document).ready(function() {
		$("#overwork_tabs").tabs();
		showOverworkListGrid();
		$("#selectOverwork").click(function() { //신청탭 초과근무 구분버튼
			getCode("T05", "selectOverwork", "selectOverworkCode");
		})
		
		$("#selectOverworkType").click(function() { //조회탭 구분 버튼
			getCode("T05", "selectOverworkType", "selectOverworkTypeCode");
		})
		
		$("#overwork_startT").timepicker({ //신청탭 시작시간..초과근무는 19시 고정이다
			step: 5,            
			timeFormat: "H:i",    
			minTime: "00:00",
			maxTime: "24:00"	
		});
		
		$("#overwork_endT").timepicker({ //신청탭 종료시간
			step: 5,            
			timeFormat: "H:i",    
			minTime: "00:00",
			maxTime: "24:00"	
		});
		
		
		
		$("#search_startD").click(getDatePicker($("#search_startD")));
		$("#search_endD").click(getDatePicker($("#search_endD")));
		
		$("#search_overworkList_Btn").click(findoverworkList); // 조회버튼
		
		
		$("#delete_overwork_Btn").click(function(){ // 초과근무 삭제버튼
			var flag = confirm("선택한 초과근무신청을 정말 삭제하시겠습니까?");
			if(flag)
				removeOverworkList();
		});
		
		
		$("#overwork_startD").click(getDatePicker($("#overwork_startD")));
		
		$("#overwork_startD").change(function(){ // 근태외신청 시작일 
			if($("#selectOverworkCode").val().trim()=="ASC006"||$("#selectOverworkCode").val().trim()=="ASC007"){
			  	// 반차라면
			    $("#overwork_endD").val($(this).val()); // 시작일과 종료일을 같게한다
			    toDay = $("#overwork_startD").val();
			};
			$("#overwork_endD").datepicker("option","minDate",$(this).val());
			if($("#overwork_endD").val()!="")
				calculateNumberOfDays();
		}); 
		
		
		$("#overwork_endD").click(getDatePicker($("#overwork_endD")));
		$("#overwork_endD").change(function(){ // 근태외신청 종료일
			$("#overwork_startD").datepicker("option","maxDate",$(this).val());
			if($("#overwork_startD").val()!="")
				calculateNumberOfDays();
		}); 
		
		$("#btn_regist").click(registoverwork);
		
		/* 사용자 기본 정보 넣기 */
		$("#overwork_emp").val("${sessionScope.name}");
		$("#overwork_dept").val("${sessionScope.dept}");
		$("#overwork_position").val("${sessionScope.position}");
	})
	
	/* 오늘 날자를 RRRR-MM-DD 형식으로 리턴하는 함수 */
	function getDay(){
	    var today = new Date();
	    var rrrr = today.getFullYear();
	    var mm = today.getMonth()+1;
	    var dd = today.getDate();
	    var searchDay = rrrr+"-"+mm+"-"+dd;
		return searchDay;
	}
	
	
	/* 초과근무목록 조회버튼  */
	function findoverworkList(){
		var startVar = $("#search_startD").val();
		var endVar = $("#search_endD").val();
		var code = $("#selectOverworkTypeCode").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/restAttendance.do",
			data:{
				"method" : "findRestAttdList",
				"empCode" : empCode,
				"startDate" : startVar,
				"endDate" : endVar,
				"code":code
			},
			dataType:"json",
			success : function(data){
				if(data.errorCode < 0){
					var str = "An internal server error has occurred.\n";
					str += "Please contact your manager.\n";
					str += "Error position : " + $(this).attr("id");
					str += "Error message : " + data.errorMsg;
					alert(str);
					return;
				}

				overworkList = data.restAttdList;

				/* 시간형태변경포문부분 */
				for(var index in overworkList){
					overworkList[index].startTime = getRealTime(overworkList[index].startTime);
					overworkList[index].endTime = getRealTime(overworkList[index].endTime);
				} 

				showOverworkListGrid();
			}
		});
	}
	
	
	
	 
	
	
	/* 숫자로 되있는 시간을 시간형태로  */
	function getRealTime(time){
		var hour = Math.floor(time/100);
		var min = time-(Math.floor(time/100)*100);
		if(min.toString().length==1) min="0"+min; //분이 1자리라면 앞에0을 붙임
		if(min==0) min="00";
		return hour + ":" + min;
	}
	
	/* 삭제버튼 눌렀을 때 실행되는 함수 */
    function removeOverworkList(){
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
					alert("not deleted normally");
				} else {
					alert("deleted");
				}
				location.reload();
			}
		});
    }
	
	
	/* 초과근무신청 그리드 띄우는 함수 */
    function showOverworkListGrid(){
    	var columnDefs = [
	  	      {headerName: "empCode", field: "empCode",hide:true },
	  	      {headerName: "일련번호", field: "restAttdCode",hide:true },
	  	      {headerName: "Code", field: "restTypeCode",hide:true},
	  	      {headerName: "CodeName", field: "restTypeName",checkboxSelection: true},
	  	      {headerName: "Application Date", field: "requestDate"},
	  	      {headerName: "Start Date", field: "startDate"},
	  	      {headerName: "End Date", field: "endDate"},
	  	      {headerName: "Day", field: "numberOfDays"},
	  	      {headerName: "Start Time", field: "startTime"},
	  	      {headerName: "End Time", field: "endTime"},
	  	      {headerName: "Reason", field: "cause"},
	  	      {headerName: "Approval status", field: "applovalStatus"},
	  	      {headerName: "Reason for Refusal", field: "rejectCause"}
	  	];    
	  	gridOptions = {
	  			columnDefs: columnDefs,
	  			rowData: overworkList,
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
	  	$('#overworkList_grid').children().remove();	 
	  	var eGridDiv = document.querySelector('#overworkList_grid');
	  	new agGrid.Grid(eGridDiv, gridOptions);	
	   }
	
	
	
	/* 일수 계산 함수  */
	function calculateNumberOfDays(){
	    startStr = $("#overwork_startD").val();
		endStr = $("#overwork_endD").val();
		if($("#selectOverworkCode").val().trim()=="ASC006"||$("#selectOverworkCode").val().trim()=="ASC007"){
		    $("#overwork_day").val(0.5); //반차라면 무조건 0.5일
		}else if($("#selectOverworkCode").val().trim()=="ASC001"||$("#selectOverworkCode").val().trim()=="ASC004"||$("#selectOverworkCode").val().trim()=="ASC005"){
			
			$.ajax({ //경조사 및 연차라면 주말, 공휴일을 제외한 계산일수를 반환하는 함수 사용
				url:"${pageContext.request.contextPath}/base/holidayList.do",
				data:{
					"method" : "findWeekDayCount",
					"startDate" : startStr,
					"endDate" : endStr
				},
				dataType:"json",
				success : function(data){
					if(data.errorCode < 0){
						var str = "An internal server error has occurred.\n";
						str += "Please contact your manager.\n";
						str += "Error position : " + $(this).attr("id");
						str += "Error message : " + data.errorMsg;
						alert(str);
						return;
					}
					$("#overwork_day").val(data.weekdayCount);
				}
			});
			
		}else{ // 그 외에는 주말 및 공휴일을 포함한 일자를 가져온다
			var startMs = (new Date(startStr)).getTime();
			var endMs = (new Date(endStr)).getTime();
			var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
			$("#overwork_day").val(numberOfDays);
		}
	}
	

	/* 코드선택 창 띄우기 */
	function getCode(code, inputText, inputCode) {
		option = "width=300; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open(
				"${pageContext.request.contextPath}/base/detailCodeWindow.html?code="
						+ code + "&inputText=" + inputText + "&inputCode="
						+ inputCode, "newwins", option);
	}
	
	/* 달력띄우기 */
	function getDatePicker($Obj) {
		$Obj.datepicker({
			defaultDate : "d",
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy/mm/dd",
			dayNamesMin : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
			monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12" ],
			yearRange : "2021:2022"
		});
	}
	
	
	/* timePicker시간을 변경하는 함수 */
	function convertTimePicker(){
		startTime = $("#overwork_startT").val().replace(":","");
		endTime = $("#overwork_endT").val().replace(":","");
		endTimeHour = $("#overwork_endT").val().substring(0,2);
		console.log(endTimeHour);
		
 		if($("#overwork_endT").val().substring(0,2) < 09){
			endTime = Number(endTime) + 2400;
		}
	}
	
	
	
	/* 신청 버튼  */
	function registoverwork(){
		if($("#selectOverwork").val()==""){
			alert("Please choose a code name");
			return;
		}else if($("#overwork_startD").val()=="" || $("#overwork_endD").val()==""){
			alert("Please choose the date");
			return;
		}else if($("#overwork_endT").val()=="" || $("overwork_startT").val()==""){
			alert("Please choose the time");
			return;
		}
		convertTimePicker();
		var overworkList = {
				"empCode" : empCode,
				"restTypeCode" : $("#selectOverworkCode").val(),
				"restTypeName" : $("#selectOverwork").val(),
				"requestDate" : requestDate,
				"startDate" : $("#overwork_startD").val(),
				"endDate" : $("#overwork_endD").val(),
				"numberOfDays" : $("#overwork_day").val(),
				"cause" : $("#overwork_cause").val(),
				"applovalStatus" : 'Waiting for approval',
				"startTime" : startTime,
				"endTime" : endTime
			};
		
		
		var sendData = JSON.stringify(overworkList);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "registRestAttd",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("application failed");
				} else {
					alert("applied");
				}
				location.reload();
			}
		});
	}
	
</script>
</head>
<body>
<h4>Overtime request form</h4>
<hr/>
	<center>
	<div id="overwork_tabs" style="width: 1500px; height: 600px">
		<ul>
			<li><a href="#overworkAttd_tab">Application</a></li>
			<li><a href="#overworkSerach_tab">Inquiry</a></li>
		</ul>
		<div id="overworkAttd_tab">
			<font>Code name </font><input id="selectOverwork"
				class="ui-button ui-widget ui-corner-all" value="Overtime" readonly>
			<input type="hidden" id="selectOverworkCode" name="overworkCode"
				value="T05A">
			<hr>
			<table>
				<tr>
					<td><font>Applicant </font></td>
					<td><input id="overwork_emp" class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>Department </font></td>
					<td><input id="overwork_dept"
						class="ui-button ui-widget ui-corner-all" readonly></td>

					<td><font>position</font></td>
					<td><input id="overwork_position"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>

				<tr>
					<form>
						<td><font>The start date</font></td>
						<td><input id="overwork_startD"
							class="ui-button ui-widget ui-corner-all" readonly></td>

						<td><font>End date</font></td>
						<td><input id="overwork_endD"
							class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>The start time</font></td>
					<td><input id="overwork_startT"
						class="ui-button ui-widget ui-corner-all" name="timePicker1"
						value="19:00" readonly></td>

					<td><font>Time to end</font></td>
					<td><input id="overwork_endT"
						class="ui-button ui-widget ui-corner-all" name="timePicker2"
						placeholder="Time selection"></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>The number of days</font></td>
					<td><input id="overwork_day" class="ui-button ui-widget ui-corner-all" readonly></td>
					<td><font>Certificate</font></td>
					<td><input id="overwork_certi" class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td>
						<font>reason</font>
					</td>
					<td colspan="3">
						<input id="overwork_cause" autocomplete="off" style="width: 490px" class="ui-button ui-widget ui-corner-all">
					</td>
				</tr>
			</table>
			<hr>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="btn_regist" value="Application"> 
			<input type="reset" class="ui-button ui-widget ui-corner-all" value="Cancellation">
			</form>
		</div>
		<div id="overworkSerach_tab">
			<table>
				<tr>
					<td colspan="2"><center>
							<h2>Choose the range of views</h2>
						</center></td>
				</tr>
				<tr>
					<td>
						<h3 align="center">Choice</h3>
					</td>
					<td>
						<input id="selectOverworkType" class="ui-button ui-widget ui-corner-all" readonly> 
						<input type="hidden" id="selectOverworkTypeCode">
					</td>
				</tr>
				<tr>
					<td><input type=text class="ui-button ui-widget ui-corner-all" id="search_startD" readonly>~</td>
					<td><input type=text class="ui-button ui-widget ui-corner-all" id="search_endD" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td colspan="2">
						<center>
							<button class="ui-button ui-widget ui-corner-all" id="search_overworkList_Btn">Application</button>
							<button class="ui-button ui-widget ui-corner-all" id="delete_overwork_Btn">Elimination</button>
						</center>
					</td>
				</tr>
			</table>
			<hr>
			<div id="overworkList_grid" style="height: 230px; width: 1450px" class="ag-theme-balham"></div>
			<div id="overworkList_pager"></div>
		</div>
	</div>
	</center>
</body>
</html>