<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#break_tabs {
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
	var conversionDate = 0;
	var breakList = [];
	var requestDate = getDay();
	$(document).ready(function() {
		$("#break_tabs").tabs();
		showBreakListGrid();
		$("#selectBreak").click(function() { //휴가신청 - 구분
			getCode("T02", "selectBreak", "selectBreakCode");
			$("#break_endD").val("");
			$("#break_startD").val("");
			$("#break_day").val("");
		})
		
		$("#selectBreakType").click(function() { //휴가조회 - 구분
			getCode("T02", "selectBreakType", "selectBreakTypeCode");
		})
		
		//휴가조회탭 날짜 선택 이벤트
		$("#search_startD").click(getDatePicker($("#search_startD")));
		$("#search_endD").click(getDatePicker($("#search_endD")));
		
		$("#search_breakList_Btn").click(findbreakList); // 조회버튼
		
		$("#delete_break_Btn").click(function(){ // 휴가 삭제버튼
			if(gridOptions.api.getSelectedRows() == ""){
				alert("Please select an item to be deleted.");
				return;
			}
			var flag = confirm("Are you sure you want to delete the vacation request you chose?");
			if(flag)
				removeBreakList();
		});	
		
		/* 근태외신청 시작일 */
		$("#break_startD").click(getDatePicker($("#break_startD")));		
		$("#break_startD").change(function(){			
			//반차라면 시작일과 종료일이 같게
			var banchaCode=["ASC006","ASC007"];
			var selectBreakCode=$("#selectBreakCode").val().trim();
			if(banchaCode.includes(selectBreakCode)){			  	
			    $("#break_endD").val($(this).val()); 
			    toDay = $("#break_startD").val();
			};
			//종료일은 시작일보다 이전일 수 없다
			$("#break_endD").datepicker("option","minDate",$(this).val());
			if($("#break_endD").val()!="")
				calculateNumberOfDays();
		}); 
		
		/* 신청 종료일 */
		$("#break_endD").click(getDatePicker($("#break_endD")));
		$("#break_endD").change(function(){ 
			//시작일은 종료일보다 이후일 수 없다
			$("#break_startD").datepicker("option","maxDate",$(this).val());
			if($("#break_startD").val()!="")
				calculateNumberOfDays();
		}); 
		
		$("#btn_regist").click(registbreak); //신청탭 신청버튼
		
		/* 사용자 기본 정보 넣기 */
		$("#break_emp").val("${sessionScope.name}");
		$("#break_dept").val("${sessionScope.dept}");
		$("#break_position").val("${sessionScope.position}");
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
	
	
	/* 휴가목록 조회버튼  */
	function findbreakList(){
		var startVar = $("#search_startD").val();
		var endVar = $("#search_endD").val();
		var code = $("#selectBreakTypeCode").val();
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
					var str = "An internal server error has occurred\n";
					str += "Please contact your manager\n";
					str += "Error position : " + $(this).attr("id");
					str += "Error message : " + data.errorMsg;
					alert(str);
					return;
				}

				breakList = data.restAttdList;

				/* 시간형태변경포문부분 */
				for(var index in breakList){
					breakList[index].startTime = getRealTime(breakList[index].startTime);
					breakList[index].endTime = getRealTime(breakList[index].endTime);
				}  

				showBreakListGrid();
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
    function removeBreakList(){
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
					alert("It's not deleted normally");
				} else {
					alert("It's been deleted");
				}
				location.reload();
			}
		});
    }
	
	
	/* 휴가신청 그리드 띄우는 함수 */
function showBreakListGrid(){
	var columnDefs = [
	      {headerName: "Employee code", field: "empCode",hide:true },
	      {headerName: "Serial number", field: "restAttdCode",hide:true },
	      {headerName: "Holiday division code", field: "restTypeCode",hide:true},
	      {headerName: "Vacation", field: "restTypeName",checkboxSelection: true},
	      {headerName: "Application date", field: "requestDate"},
	      {headerName: "Start Date", field: "startDate"},
	      {headerName: "End Date", field: "endDate"},
	      {headerName: "Days", field: "numberOfDays"},
	      {headerName: "Reason", field: "cause"},
	      {headerName: "Approval status", field: "applovalStatus"},
	      {headerName: "Reason for Return", field: "rejectCause"}
	];    
	gridOptions = {
			columnDefs: columnDefs,
			rowData: breakList,
			defaultColDef: { editable: false, width: 100 },
			rowSelection: 'multiple', /* 'single' or 'multiple',*/
			enableColResize: true,
			enableSorting: true,
			enableFilter: true,
			enableRangeSelection: true,
			suppressRowClickSelection: false,
			animateRows: true,
			suppressHorizontalScroll: true,
			localeText: {noRowsToShow: 'There is no inquiry result'},
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
	$('#breakList_grid').children().remove();	 
	var eGridDiv = document.querySelector('#breakList_grid');
	new agGrid.Grid(eGridDiv, gridOptions);	
 }
	
	
	
	/* 일수 계산 함수  */
	function calculateNumberOfDays(){
	    var startStr = $("#break_startD").val();
		var endStr = $("#break_endD").val();
		
		var banchaCode=["ASC006","ASC007"];
		var anotherCode=["ASC001","ASC005"];
		var selectBreakCode=($("#selectBreakCode").val().trim());	
		
		if(banchaCode.includes(selectBreakCode)){
		    $("#break_day").val(0.5); //반차라면 무조건 0.5일
		}else if(anotherCode.includes(selectBreakCode)){//경조사 및 연차라면 주말, 공휴일을 제외한 계산일수를 반환
			
		
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
						var str = "An internal server error has occurred\n";
						str += "Please contact your manager\n";
						str += "Error position : " + $(this).attr("id");
						str += "Error message : " + data.errorMsg;
						alert(str);
						return;
					}
					$("#break_day").val(data.weekdayCount);
				}
			});
			
		}else{ // 그 외에는 주말 및 공휴일을 포함한 일자를 가져온다
			var startMs = (new Date(startStr)).getTime();
			var endMs = (new Date(endStr)).getTime();
			var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
			$("#break_day").val(numberOfDays);
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
	
	/* 신청 버튼  */
	function registbreak(){
		if($("#selectBreak").val()==""){
			alert("Please choose the vacation category");
			return;
		}else if($("#break_startD").val()=="" || $("#break_endD").val()==""){
			alert("Please choose the date");
			return;
		}
		var breakList = {
				"empCode" : empCode,
				"restTypeCode" : $("#selectBreakCode").val(),
				"restTypeName" : $("#selectBreak").val(),
				"requestDate" : requestDate,
				"startDate" : $("#break_startD").val(),
				"endDate" : $("#break_endD").val(),
				"numberOfDays" : $("#break_day").val(),
				"cause" : $("#break_cause").val(),
				"applovalStatus" : 'Waiting for approval',
				"time" : conversionDate
			};
		
		
		var sendData = JSON.stringify(breakList);
		
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
<h4>Vacation/time off request form</h4>
<hr/>
	<center>
	<div id="break_tabs" style="width: 1500px; height: 600px">
		<ul>
			<li><a href="#breakAttd_tab">Application</a></li>
			<li><a href="#breakSerach_tab">Inquiry</a></li>
		</ul>
		<!-- 휴가신청 탭 -->
		<div id="breakAttd_tab">
			<font>Vacation categories </font>
			<input id="selectBreak" class="ui-button ui-widget ui-corner-all" readonly> 
			<input type="hidden" id="selectBreakCode" name="breakCode">
			<hr>
			<table>

				<tr>
					<td><font>Applicant </font></td>
					<td><input id="break_emp"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>Department </font></td>
					<td><input id="break_dept"
						class="ui-button ui-widget ui-corner-all" readonly></td>

					<td><font>position</font></td>
					<td><input id="break_position"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<form>
					<tr>
						<td><font>The start date</font></td>
						<td><input id="break_startD" class="ui-button ui-widget ui-corner-all" readonly></td>

						<td><font>End date</font></td>
						<td><input id="break_endD" class="ui-button ui-widget ui-corner-all" readonly></td>
					</tr>
					<tr>
						<td><h3></h3></td>
					</tr>
					<tr>
						<td><font>The number of days</font></td>
						<td><input id="break_day" class="ui-button ui-widget ui-corner-all" readonly></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><h3></h3></td>
					</tr>
					<tr>
						<td><font>reason</font></td>
						<td colspan="3">
							<input id="break_cause" autocomplete="off" style="width: 490px" class="ui-button ui-widget ui-corner-all">
						</td>
					</tr>
			</table>
			<hr>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="btn_regist" value="Application"> 
			<input type="reset" class="ui-button ui-widget ui-corner-all" value="Cancellation">
			</form>
		</div>

		<!-- 휴가조회 탭 -->
		<div id="breakSerach_tab">
			<table>
				<tr>
					<td colspan="2"><center>
							<h2>Choose the range of views</h2>
						</center></td>
				</tr>
				<tr>
					<td><h3 align="center">Code name</h3></td>
					<td><input id="selectBreakType" class="ui-button ui-widget ui-corner-all" readonly> 
					<input type="hidden" id="selectBreakTypeCode"></td>
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
							<button class="ui-button ui-widget ui-corner-all" id="search_breakList_Btn">Application</button>
							<button class="ui-button ui-widget ui-corner-all" id="delete_break_Btn">Elimination</button>
						</center>
					</td>
				</tr>
			</table>
			<hr>
			<div id="breakList_grid" style="height: 230px; width: 1450px"
				class="ag-theme-balham"></div>
			<div id="restAttdList_pager"></div>
		</div>
	</div>
	</center>
</body>
</html>