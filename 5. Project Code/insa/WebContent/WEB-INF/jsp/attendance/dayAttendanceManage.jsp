<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일근태관리</title>
<style type="text/css">
input[type=text] {
	width: 150px;
	height: 30px;
	z-index: 100;
}

table {
	z-index: 90;
}

</style>

<script>
	var dayAttdMgtList = [];

	$(document).ready(function() {
		
		getDatePicker($("#searchDay"));
		$("#search_dayAttdMgtList_Btn").click(findDayAttdMgtList); //조회하기
		$("#finalize_dayAttdMgt_Btn").click(finalizeDayAttdMgt); //마감하기
		$("#cancel_dayAttdMgt_Btn").click(cancelDayAttdMgt); // 전체마감취소
		showDayAttdMgtListGrid();
		findDayAttdMgtList("today");
	});
	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	
	/* 일근태관리 목록 조회버튼 함수 */
	function findDayAttdMgtList(check) {
		
		/*if($("#searchDay").val()==""){
			alert("Please choose the date of view.");
			return;
		
		}*/
		var searchDay = $("#searchDay").val();

		if (check == "today") { //해당 함수를 부를때에 today라는 글자가 변수로 넘어오면 오늘 날짜를 searchDay변수에 담아 ajax실행
			var today = new Date();
			var rrrr = today.getFullYear();
			var mm = today.getMonth() + 1;
			var dd = today.getDate();
			searchDay = rrrr + "-" + mm + "-" + dd;
		}
		else{
			if($("#searchDay").val()==""){
				
				alert("Please choose the date of view.");
				return;		
				
			}
		}
		//console.log(searchDay);
		$.ajax({
					url : "${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
					data : {
						"method" : "findDayAttdMgtList",
						"applyDay" : searchDay,
						"dept" : "${sessionScope.dept}"
					},
					dataType : "json",
					success : function(data) {
						if (data.errorCode < 0) {
							var str = " An internal server error has occurred.\n";
							str += "Please contact the administrator.\n";
							str += "Location : an error : " + $(this).attr("id");
							str += "Error messages : " + data.errorMsg;
							alert(str);
							return;
						}

						dayAttdMgtList = data.dayAttdMgtList;

						for ( var index in dayAttdMgtList) {
							dayAttdMgtList[index].attendTime = getRealTime(dayAttdMgtList[index].attendTime);
							dayAttdMgtList[index].quitTime = getRealTime(dayAttdMgtList[index].quitTime);
							dayAttdMgtList[index].lateHour = getRealKrTime(dayAttdMgtList[index].lateHour);
							dayAttdMgtList[index].leaveHour = getRealKrTime(dayAttdMgtList[index].leaveHour);
							dayAttdMgtList[index].workHour = getRealKrTime(dayAttdMgtList[index].workHour);
							dayAttdMgtList[index].overWorkHour = getRealKrTime(dayAttdMgtList[index].overWorkHour);
							dayAttdMgtList[index].nightWorkHour = getRealKrTime(dayAttdMgtList[index].nightWorkHour);
							dayAttdMgtList[index].publicLeaveHour = getRealKrTime(dayAttdMgtList[index].publicLeaveHour);
							dayAttdMgtList[index].privateLeaveHour = getRealKrTime(dayAttdMgtList[index].privateLeaveHour);
						}

						showDayAttdMgtListGrid();
					}
				});
	}
	/* 일근태관리 목록 그리드 띄우는 함수 */
	function showDayAttdMgtListGrid() {
		var columnDefs = [ 
			{headerName : "Emp Code",field : "empCode" }, 
			{headerName : "Emp Name",field : "empName"}, 
			{headerName : "Date",field : "applyDays"}, 
			{headerName : "Attd Code",field : "dayAttdCode"}, 
			{headerName : "Attd Name",field : "dayAttdName"}, 
			{headerName : "Clock-in",field : "attendTime"}, 
			{headerName : "Clock-out",field : "quitTime"}, 
			{headerName : "Late",field : "lateWhether" ,width: 50}, 
			{headerName : "Total Outing Time",field : "leaveHour"}, 
			{headerName : "Pubic Leave",field : "publicLeaveHour"}, 
			{headerName : "Priv Leave",field : "privateLeaveHour"}, 
			{headerName : "Work hours",field : "workHour"}, 
			{headerName : "Ext hours",field : "overWorkHour"}, 
			{headerName : "Night hours",field : "nightWorkHour"}, 
			{headerName : "Closing status",field : "finalizeStatus"}, 
			{headerName : "Status",field : "status",hide : true} 
			];
		gridOptions = {
			columnDefs : columnDefs,
			rowData : dayAttdMgtList,
			defaultColDef : {
				editable : false,
				width : 100
			},
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 10,
			
			rowSelection : 'single', /* 'single' or 'multiple',*/
			enableColResize : true,
			enableSorting : true,
			enableFilter : true,
			enableRangeSelection : true,
			suppressRowClickSelection : false,
			animateRows : true,
			suppressHorizontalScroll : true,
			localeText : {
				noRowsToShow : "There's no result."
			},
			getRowStyle : function(param) {
				if (param.node.rowPinned) {
					return {
						'font-weight' : 'bold',
						background : '#dddddd'
					};
				}
				return {
					'text-align' : 'center'
				};
			},
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			},
			// GRID READY 이벤트, 사이즈 자동조정 
			onGridReady : function(event) {
				event.api.sizeColumnsToFit();
			},
			// 창 크기 변경 되었을 때 이벤트 
			onGridSizeChanged : function(event) {
				event.api.sizeColumnsToFit();
			},
			onCellEditingStarted : function(event) {
				//console.log('cellEditingStarted');
			},
		};
		$('#dayAttdMgtList_grid').children().remove();
		var eGridDiv = document.querySelector('#dayAttdMgtList_grid');
		new agGrid.Grid(eGridDiv, gridOptions);
	}

	/* 마감처리 함수 */
	function finalizeDayAttdMgt() { //마감처리가 되지 않는다면 톰캣 Servers에서 server.xml에서 maxHttpHeaderSize="30000"를 추가해주면 된다
		var dayAttdMonthData = 0; // 일근태관리의 월 데이터 
		var monthAttdFinalizeStatus = 0; // 해당 일근태 관리의 월에 따른 월근태관리의 마감 여부 
		gridOptions.api.forEachNode(function(rowNode, index) {
					var dateData = rowNode.data.applyDays;
					dayAttdMonthData = dateData.substring(0, dateData
							.lastIndexOf("-")); //YYYY-MM
					//console.log(dayAttdMonthData);
					if (dateData.substring(5, 6) == 0) // 월에 0이 들어가 있다면   ex]2018-08  //YYYY-*M*M-DD
						dayAttdMonthData = replaceLast(dayAttdMonthData, 0, ""); // 0을 제거한다  ex]2018-8 //YYYY-M      
				})

		$.ajax({ // 월근태 목록을 가져온다
					url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
					data : {
						"method" : "findMonthAttdMgtList",
						"applyYearMonth" : dayAttdMonthData
					},
					dataType : "json",
					async : false, // 동기처리를 하지 않으면 전역 변수 monthAttdFinalizeStatus에 값이 할당되기 전에 취소 작업이 일어남
					success : function(data) {
						if (data.errorCode < 0) {
							var str = " An internal server error has occurred.\n";
							str += "Please contact the administrator.\n";
							str += "Location : an error : " + $(this).attr("id");
							str += "Error messages : " + data.errorMsg;
							alert(str);
							return;
						}

						$.each(data.monthAttdMgtList, function(i, elt) {
							monthAttdFinalizeStatus = elt.finalizeStatus;
							return false;
						})

					}
				});

		if (monthAttdFinalizeStatus == "N") { // 월근태 관리의 마감 상태가 N과 같다면 일근태 마감 작업을 한다
			for ( var index in dayAttdMgtList) {
				dayAttdMgtList[index].finalizeStatus = "Y";
				dayAttdMgtList[index].status = "update";
			}
			//console.log(dayAttdMgtList[0].applyDays);
			var sendData = JSON.stringify(dayAttdMgtList);
			//console.log("232323s");
			//console.log(dayAttdMgtList);
			$.ajax({ 
						url : "${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
						data : {
							"method" : "modifyDayAttdList",
							"sendData" : sendData
						},
						dataType : "json",
						success : function(data) {
							console.log("abc");
							if (data.errorCode < 0) {
								alert("Failed to close it");
							} else {
								alert("It's closed");
							}
							//location.reload(); 
							//새로고침.익스플로러에서 느림.
							//window.location.reload(); //크롬,익스 상관없음.
						}
					});
		} else if (monthAttdFinalizeStatus == "Y"){
			//console.log("월근태Y");
			alert("The month's attendance and health care has ended.\nIn case you want to close the data\nCancel the month's attendance management deadline and try it.")
		}
	}

	/* 마감취소 함수 */
	function cancelDayAttdMgt() {
		var dayAttdMonthData = 0; // 일근태관리의 월 데이터 
		var monthAttdFinalizeStatus = 0; // 해당 일근태 관리의 월에 따른 월근태관리의 마감 여부 
		gridOptions.api
				.forEachNode(function(rowNode, index) {
					var dateData = rowNode.data.applyDays;
					dayAttdMonthData = dateData.substring(0, dateData
							.lastIndexOf("-")); //YYYY-MM
					//console.log(dayAttdMonthData);
					if (dateData.substring(5, 6) == 0) // 월에 0이 들어가 있다면   ex]2018-08  //YYYY-*M*M-DD
						dayAttdMonthData = replaceLast(dayAttdMonthData, 0, ""); // 0을 제거한다  ex]2018-8 //YYYY-M      
				})

		$.ajax({ // 월근태 목록을 가져온다
					url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
					data : {
						"method" : "findMonthAttdMgtList",
						"applyYearMonth" : dayAttdMonthData
					},
					dataType : "json",
					async : false, // 동기처리를 하지 않으면 전역 변수 monthAttdFinalizeStatus에 값이 할당되기 전에 취소 작업이 일어남
					success : function(data) {
						if (data.errorCode < 0) {
							var str = " An internal server error has occurred.\n";
							str += "Please contact the administrator.\n";
							str += "Location : an error : " + $(this).attr("id");
							str += "Error messages : " + data.errorMsg;
							alert(str);
							return;
						}

						$.each(data.monthAttdMgtList, function(i, elt) {
							monthAttdFinalizeStatus = elt.finalizeStatus;
							return false;
						})

					}
				});

		if (monthAttdFinalizeStatus == "N") { //월근태 관리의 마감 상태가 N과 같다면 일근태 마감 취소 작업을 한다 
			for ( var index in dayAttdMgtList) {
				dayAttdMgtList[index].finalizeStatus = "N";
				dayAttdMgtList[index].status = "update";
			}

			var sendData = JSON.stringify(dayAttdMgtList);

			$.ajax({
						url : "${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
						data : {
							"method" : "modifyDayAttdList",
							"sendData" : sendData
						},
						dataType : "json",
						success : function(data) {
							if (data.errorCode < 0) {
								alert("Failed to cancel the deadline");
							} else {
								alert("The deadline has been canceled");
							}
							//location.reload();
						}
					});
		} else if (monthAttdFinalizeStatus == "Y") {
			alert("The month's attendance and health care has ended.\nIf you want to cancel the data deadline, \nCancel the month's attendance management deadline and try it.")
		}
	}

	/* 0000단위인 시간을 00:00(실제시간)으로 변환하는 함수 */
	function getRealTime(time) {
		var hour = Math.floor(time / 100);
		if (hour == 25)
			hour = 1; //데이터 베이스에 넘길때는 25시로 받고나서 grid에 표시하는건 1시로
		var min = time - (Math.floor(time / 100) * 100);
		if (min.toString().length == 1)
			min = "0" + min; //분이 1자리라면 앞에0을 붙임
		if (min == 0)
			min = "00";
		return hour + ":" + min;
	}
	
	/* 0000단위인 시간을 (00시간00분) 형식으로 변환하는 함수 */
	function getRealKrTime(time) {
		var hour = Math.floor(time / 100);
		if (hour == 25)
			hour = 1;
		var min = time - (Math.floor(time / 100) * 100);
		if (min == 0)
			min = "00";
		return hour + "Hours" + min + "Min";
	}

	/* 날짜 조회창 함수 */
	function getDatePicker($Obj, maxDate) {
		$Obj.datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			dayNamesMin : [ "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" ],
			monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12" ],
			yearRange : "2019:2023",
			maxDate : (maxDate == null ? "+100Y" : maxDate)
		});
	}

	/* replacefirst는 있는데 last가 없어서 구현 */
	function replaceLast(str, regex, replacement) {
		var regexIndexOf = str.lastIndexOf(regex);
		if (regexIndexOf == -1) {
			return str;
		} else {
			/* 넘어오는 regex가 number타입이기 때문에 length가 안먹힘 그래서 toString으로 문자열로 변경후 사용 */
			return str.substring(0, regexIndexOf) + replacement
					+ str.substring(regexIndexOf + regex.toString().length);
		}
	}
</script>
</head>
<body>
	<h4>Day attendance management</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="finalize_dayAttdMgt_Btn" value="Closing"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="cancel_dayAttdMgt_Btn"  value="Cancel the deadline" class="btn btn-Light shadow-sm btnsize"> 
		</div>
	</div>
	<hr>
	<div>
		<div>
			Date of inquiry &nbsp 
			<input type=text placeholder="Choose the date..." style="height: 30px; width: 200px" id="searchDay" class="small_Btn" readonly>
			<button class="small_Btn" id="search_dayAttdMgtList_Btn">Look up</button>
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
							<div id="dayAttdMgtList_grid" style="height: 300px; width: 2000px" class="ag-theme-balham"></div>
							<div id="dayAttdMgtList_pager"></div>
							
							-----------------------------------------------------<br>
							※ Reference <br>
							-----------------------------------------------------<br>
							1. Emp Code : Employee Code <br>
							2. Emp Name : Employee Name <br>
							3. Date : ApplicationDays <br>
							4. Attd Code : DayAttdCode <br>
							5. Attd Name : DayAttdName <br>
							6. Clock-in : Time to go to work <br>
							7. Clock-out : Time to leave work<br>
							8. Late : Whether or not to be late <br>
							9. Total Outing Time : Total Outing Time <br>
							10. Pubic Leave : Public Leave Hour <br>
							11. Pri Leave : Private Leave Hour <br>
							12. Wor hours : normal working hours <br>
							13. Ext hours : Extended working hours <br>
							14. Night hours : Late Night Working Hours <br>
							15. Closing status : Closing status <br>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>