<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#travel_tabs {
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
	var travelList = [];
	var requestDate = getDay();
	$(document).ready(function() {
		$("#travel_tabs").tabs();
		showTravelListGrid();
		$("#selectTravel").click(function() { //신청탭 출장/교육 구분 버튼
			getCode("T03", "selectTravel", "selectTravelCode");
			$("#travel_endD").val("");
			$("#travel_startD").val("");
			$("#travel_day").val("");
		})
		
		$("#selectTravelType").click(function() { //조회탭 구분 버튼
			getCode("T03", "selectTravelType", "selectTravelTypeCode");
		})
		
		
		/* 시간 선택 함수 */
		$("#travel_startT").timepicker(); //신청탭 시작시간
		$("#travel_endT").timepicker();	 //신청탭 종료시간
		
		$("#search_startD").click(getDatePicker($("#search_startD"))); //조회탭 시작일
		$("#search_endD").click(getDatePicker($("#search_endD"))); //조회탭 종료일
		
		$("#search_travelList_Btn").click(findtravelList); // 조회탭 조회하기버튼
		
		$("#delete_travel_Btn").click(function(){ // 조회탭 삭제버튼
			var flag = confirm("Are you sure you want to delete the selected business trip/training application?");
			if(flag)
				removeBreakList();
		});		
		
		$("#travel_startD").click(getDatePicker($("#travel_startD")));
		
		$("#travel_startD").change(function(){ // 출장/교육신청 시작일 
			$("#travel_endD").datepicker("option","minDate",$(this).val());
			if($("#travel_endD").val()!="")
				calculateNumberOfDays();
		}); 
		
		
		$("#travel_endD").click(getDatePicker($("#travel_endD")));
		$("#travel_endD").change(function(){ // 출장/교육신청 종료일
			$("#travel_startD").datepicker("option","maxDate",$(this).val());
			if($("#travel_startD").val()!="")
				calculateNumberOfDays();
		}); 
		
		$("#btn_regist").click(registtravel);
		
		/* 사용자 기본 정보 넣기 */
		$("#travel_emp").val("${sessionScope.name}");
		$("#travel_dept").val("${sessionScope.dept}");
		$("#travel_position").val("${sessionScope.position}");
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
	
	/* timePicker시간을 변경하는 함수 */
	function convertTimePicker(){
		startTime = $("#travel_startT").val().replace(":","");
		endTime = $("#travel_endT").val().replace(":","");
		startTime = startTime.substring(0,startTime.indexOf("m")-1)
		endTime = endTime.substring(0,endTime.indexOf("m")-1)
		
 		if(startTime.indexOf("00")==0){
 			startTime = $("#timePicker").val().replace(":","").replace("00","24");
		}
 		if(endTime.indexOf("00")==0){
			endTime = $("#timePicker").val().replace(":","").replace("00","24");
		}
	}
	
	
	/* 출장/교육 목록 조회버튼  */
	function findtravelList(){
		var startVar = $("#search_startD").val();
		var endVar = $("#search_endD").val();
		var code = $("#selectTravelTypeCode").val();
		if($("#selectTravelType").val()==""){
			alert("Please choose the code name");
			return;
		}else if($("#search_startD").val()=="" || $("#search_endD").val()==""){
			alert("Please choose the date");
			return;
		}
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

				travelList = data.restAttdList;

				/* 시간형태변경포문부분 */
				for(var index in travelList){
					travelList[index].startTime = getRealTime(travelList[index].startTime);
					travelList[index].endTime = getRealTime(travelList[index].endTime);
				}  

				showTravelListGrid();
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
			alert("There is no non-approved information to delete.\n Please select the information you want to delete.");
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
	    function showTravelListGrid(){
	    	var columnDefs = [
	  	      {headerName: "empCode", field: "empCode",hide:true },
	  	      {headerName: "Serial Number", field: "restAttdCode",hide:true },
	  	      {headerName: "Code", field: "restTypeCode",hide:true},
	  	      {headerName: "codeName", field: "restTypeName",checkboxSelection: true},
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
	  			rowData: travelList,
	  			defaultColDef: { editable: false, width: 100 },
	  			rowSelection: 'multiple', /* 'single' or 'multiple',*/
	  			enableColResize: true,
	  			enableSorting: true,
	  			enableFilter: true,
	  			enableRangeSelection: true,
	  			suppressRowClickSelection: false,
	  			animateRows: true,
	  			suppressHorizontalScroll: true,
	  			localeText: {noRowsToShow: "There's no result."},
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
	  	$('#travelList_grid').children().remove();	 
	  	var eGridDiv = document.querySelector('#travelList_grid');
	  	new agGrid.Grid(eGridDiv, gridOptions);	
	   }
	
	
	
	/* 일수 계산 함수  */
	function calculateNumberOfDays(){
	    var startStr = $("#travel_startD").val();
		var endStr = $("#travel_endD").val();
		 // 그 외에는 주말 및 공휴일을 포함한 일자를 가져온다
			var startMs = (new Date(startStr)).getTime();
			var endMs = (new Date(endStr)).getTime();
			var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
			$("#travel_day").val(numberOfDays);
		
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
	function registtravel(){
		if($("#selectTravel").val()==""){
			alert("Please choose between business trips and education.");
			return;
		}else if($("#travel_startD").val()=="" || $("#travel_endD").val()==""){
			alert("Please choose the date.");
			return;
		}else if($("#travel_startT").val()=="" || $("#travel_endT").val()==""){
			alert("Please choose the time.");
			return;
		}
		convertTimePicker();
		var travelList = {
				"empCode" : empCode,
				"restTypeCode" : $("#selectTravelCode").val(),
				"restTypeName" : $("#selectTravel").val(),
				"requestDate" : requestDate,
				"startDate" : $("#travel_startD").val(),
				"endDate" : $("#travel_endD").val(),
				"numberOfDays" : $("#travel_day").val(),
				"cause" : $("#travel_cause").val(),
				"applovalStatus" : "Waiting for approval",
				"startTime" : startTime,
				"endTime" : endTime
			};
		
		
		var sendData = JSON.stringify(travelList);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "registRestAttd",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("I failed to apply.");
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
	<h4>Business trip request form/Continuing education request form</h4>
	<hr>
	<center>
	<div id="travel_tabs" style="width: 1500px; height: 600px">
		<ul>
			<li><a href="#travelAttd_tab">Application</a></li>
			<li><a href="#travelSerach_tab">Inquiry</a></li>
		</ul>
		<div id="travelAttd_tab">
			<font>Code name </font><input id="selectTravel"
				class="ui-button ui-widget ui-corner-all" readonly> <input
				type="hidden" id="selectTravelCode" name="travelCode">
			<hr>
			<table>
				<tr>
					<td><font>Applicant </font></td>
					<td><input id="travel_emp"class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>Department</font></td>
					<td><input id="travel_dept"
						class="ui-button ui-widget ui-corner-all" readonly></td>

					<td><font>position</font></td>
					<td><input id="travel_position"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td id="target"><font>The start date</font></td>
					<td><input id="travel_startD"
						class="ui-button ui-widget ui-corner-all" readonly></td>

					<td><font>End date</font></td>
					<td><input id="travel_endD"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<form>
						<td><font>The start time</font></td>
						<td><input id="travel_startT"
							class="ui-button ui-widget ui-corner-all" name="timePicker1"
							placeholder="Time selection"></td>

						<td><font>Time to end</font></td>
						<td><input id="travel_endT"
							class="ui-button ui-widget ui-corner-all" name="timePicker2"
							placeholder="Time selection"></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>The number of days</font></td>
					<td><input id="travel_day"
						class="ui-button ui-widget ui-corner-all" readonly></td>
					<td><font>Certificate</font></td>
					<td><input id="travel_certi"
						class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr>
					<td><h3></h3></td>
				</tr>
				<tr>
					<td><font>reason</font></td>
					<td colspan="3">
						<input id="travel_cause" autocomplete="off" style="width: 490px" class="ui-button ui-widget ui-corner-all">
					</td>
				</tr>
			</table>
			<hr>
			<input type="button" class="ui-button ui-widget ui-corner-all"
				id="btn_regist" value="Application"> <input type="reset"
				class="ui-button ui-widget ui-corner-all" value="Cancellation">
			</form>
		</div>

		<div id="travelSerach_tab">
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
						<input id="selectTravelType" class="ui-button ui-widget ui-corner-all" readonly> 
						<input type="hidden" id="selectTravelTypeCode">
					</td>
				</tr>
				<tr>
					<td>
						<input type=text class="ui-button ui-widget ui-corner-all" id="search_startD" readonly>~
					</td>
					<td>
						<input type=text class="ui-button ui-widget ui-corner-all" id="search_endD" readonly>
					</td>
				</tr>
				<tr>
					<td>
						<h3></h3>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<center>
							<button class="ui-button ui-widget ui-corner-all" id="search_travelList_Btn">Application</button>
							<button class="ui-button ui-widget ui-corner-all" id="delete_travel_Btn">Elimination</button>
						</center>
					</td>
				</tr>
			</table>
			<hr>
			<div id="travelList_grid" style="height: 230px; width: 1450px" class="ag-theme-balham"></div>
			<div id="travelList_pager"></div>
		</div>
	</div>
	</center>
</body>
</html>