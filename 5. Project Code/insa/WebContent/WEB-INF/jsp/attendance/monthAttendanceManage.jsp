<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="width: 1579px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>월근태관리</title>

<style type="text/css">
input[type=text] {
	width: 150px;
	height: 30px;
	z-index: 100;
}
select {
	width: 200px;
	height: 30px;
	z-index: 100;
}

table {
	z-index: 90;
}

</style>
<script>
var monthAttdMgtList = [];

	$(document).ready(function(){

	    showMonthAttdMgtListGrid();
	    
		//$("input:text").button();
		//$(".small_Btn").button();

		//$(".comment_div").css({fontSize : "0.7em"});

		//$("#monthAttendance_tabs").tabs().css({margin:"10px"});

		$("#search_monthAttdMgtList_Btn").click(findMonthAttdMgtList);
		$("#finalize_monthAttdMgt_Btn").click(finalizeMonthAttdMgt);
		$("#cancel_monthAttdMgt_Btn").click(cancelMonthAttdMgt);
		
		//$("#searchYearMonth").selectmenu();
		
		var year = (new Date()).getFullYear();
		for(var i=1; i<=12; i++)
			$("#searchYearMonth").append($("<option />").val(year+"-"+i).text(year+"/"+i+"/"));
	});

	/* 월근태관리 목록 조회버튼 함수 */
	function findMonthAttdMgtList(){
		if($("#searchYearMonth").val()==""){
			alert("Please choose the date and month of application");
			return;
		}

		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data:{
				"method" : "findMonthAttdMgtList",
				"applyYearMonth" : $("#searchYearMonth").val()
			},
			dataType:"json",
			success : function(data){
				if(data.errorCode < 0){
					var str = " An internal server error has occurred.\n";
					str += "Please contact the administrator.\n";
					str += "Location : an error : " + $(this).attr("id");
					str += "Error messages : " + data.errorMsg;
					alert(str);

					return;
				}

				monthAttdMgtList = data.monthAttdMgtList;

 				for(var index in monthAttdMgtList){
 					monthAttdMgtList[index].workHour = getRealTime(monthAttdMgtList[index].workHour);
 					monthAttdMgtList[index].overWorkHour = getRealTime(monthAttdMgtList[index].overWorkHour);
 					monthAttdMgtList[index].nightWorkHour = getRealTime(monthAttdMgtList[index].nightWorkHour);
 					monthAttdMgtList[index].holidayWorkHour = getRealTime(monthAttdMgtList[index].holidayWorkHour);
 					monthAttdMgtList[index].holidayOverWorkHour = getRealTime(monthAttdMgtList[index].holidayOverWorkHour);
 					monthAttdMgtList[index].holidayNightWorkHour = getRealTime(monthAttdMgtList[index].holidayNightWorkHour);
				}
				
				showMonthAttdMgtListGrid();
			}
		});
	}

    /* 월근태관리 목록 그리드 띄우는 함수 */
    function showMonthAttdMgtListGrid(){
    	var columnDefs = [
		      {headerName: "Emp Code", field: "empCode" },
		      {headerName: "Emp Name", field: "empName",width:200},
		      {headerName: "Date", field: "applyYearMonth" },
		      {headerName: "1", field: "basicWorkDays"},
		      {headerName: "2", field: "weekdayWorkDays"},
		      {headerName: "3", field: "basicWorkHour"},
		      {headerName: "4", field: "workHour"},
		      {headerName: "5", field: "overWorkHour"},
		      {headerName: "6", field: "nightWorkHour"},
		      {headerName: "7", field: "holidayWorkDays"},
		      {headerName: "8", field: "holidayWorkHour"},
		      {headerName: "9", field: "lateDays"},
		      {headerName: "10", field: "absentDays"},
		      {headerName: "11", field: "halfHolidays"},
		      {headerName: "12", field: "holidays"},
		      {headerName: "Clo-status", field: "finalizeStatus"},
    {headerName: "Status", field: "status",hide:true}
		];      
	gridOptions = {
			columnDefs: columnDefs,
			rowData: monthAttdMgtList,
			defaultColDef: { editable: false, width: 100 },
			rowSelection: 'single', /* 'single' or 'multiple',*/
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
	$('#monthAttdMgtList_grid').children().remove();	 
	var eGridDiv = document.querySelector('#monthAttdMgtList_grid');
	new agGrid.Grid(eGridDiv, gridOptions);	
}

    /* 마감처리 함수 */
    function finalizeMonthAttdMgt(){
		for(var index in monthAttdMgtList){
			monthAttdMgtList[index].finalizeStatus = "Y";
			monthAttdMgtList[index].status = "update";
		}

		var sendData = JSON.stringify(monthAttdMgtList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data : {
				"method" : "modifyMonthAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("Failed to close it.");
				} else {
					alert("It's closed");
				}
				//location.reload();
			}
		});
	}

    /* 마감취소 함수 */
    function cancelMonthAttdMgt(){
		for(var index in monthAttdMgtList){
			monthAttdMgtList[index].finalizeStatus = "N";
			monthAttdMgtList[index].status = "update";
		}

		var sendData = JSON.stringify(monthAttdMgtList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data : {
				"method" : "modifyMonthAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("Failed to cancel the deadline.");
				} else {
					alert("The deadline has been canceled");
				}
				//location.reload();
			}
		});
	}

	/* 0000단위인 시간을 (00시간00분) 형식으로 변환하는 함수 */
	function getRealTime(time){
		var hour = Math.floor(time/100); 
		if(hour==25) hour=1; //데이터 베이스에 넘길때는 25시로 받고나서 grid에 표시하는건 1시로
		var min = time-(Math.floor(time/100)*100);
		if(min.toString().length==1) min="0"+min; //분이 1자리라면 앞에0을 붙임
		if(min==0) min="00";
		return hour + ":" + min;
	}
</script>
</head>
<body>
	<h4>Month attendance manage</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="finalize_monthAttdMgt_Btn" value="Closing"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="cancel_monthAttdMgt_Btn"  value="Cancel the deadline" class="btn btn-Light shadow-sm btnsize"> 
		</div>
	</div>
	<hr>
	<div>
		<div>
			Month of inquiry &nbsp 
			<select id="searchYearMonth"></select>
			&nbsp 
			<button class="small_Btn" id="search_monthAttdMgtList_Btn">Look up</button>
		</div>
	</div>
	<hr>
	<div class="col-12" style="float: left">
		<div class="card">
			<div class="card-body">
				<div>				
					Employee search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br/><br/>
					<div id="codeList_tab">
						<div>
							<div id="monthAttdMgtList_grid" style="height: 300px; width: 1500px" class="ag-theme-balham"></div>
							<div id="monthAttdMgtList_pager"></div>
							-----------------------------------------------------<br>
							※ Reference <br>
							-----------------------------------------------------<br>
							1. Emp Code : Employee Code <br>
							2. Emp Name : Employee Name <br>
							3. Date : Application Year Month <br>
							4. 1 : Standard working days <br>
							5. 2 : Working days on weekdays.<br>
							6. 3 : Standard working hours <br>
							7. 4 : Actual working hours <br>
							8. 5 : Extended working hours <br>
							9. 6 : Late Night Working Hours <br>
							10. 7 : Holiday Work Days <br>
							11. 8 : Holiday working hour <br>
							12. 9 : Number of days of latency <br>
							13. 10 : Day of Absence <br> 
							14. 11 : Day of Half day <br>
							15. 12 : Day of Vacation <br>
							16. Clo-status : Closing status <br>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 
	
	<div id="monthAttendance_tabs">
		<ul>
			<li><a href="#monthAttendance_tab">월근태관리</a></li>
		</ul>
		<div id="monthAttendance_tab">
			조회월 <select id="searchYearMonth"></select>
			<button class="ui-button ui-widget ui-corner-all"
				id="search_monthAttdMgtList_Btn">조회하기</button>
			<br /> <br />
			<button class="ui-button ui-widget ui-corner-all"
				id="finalize_monthAttdMgt_Btn">전체마감하기</button>
			<button class="ui-button ui-widget ui-corner-all"
				id="cancel_monthAttdMgt_Btn">전체마감취소</button>
			<br /> <br />
			<div id="monthAttdMgtList_grid" style="height: 300px; width: 1420px"
				class="ag-theme-balham"></div>
			<div id="monthAttdMgtList_pager"></div>
		</div>
	</div>
	 -->
</body>
</html>