<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>근태외승인</title>
<style type="text/css">
input[type=text] {
	width: 150px;
	height: 30px;
}

.small_Btn {
	width: auto;
	height: auto;
	font-size: 15px;
}

input {
	z-index: 100;
}

table {
	z-index: 90;
}
</style>
<script>
var restAttdList = [];

	$(document).ready(function(){

		//$("input:text").button();
		//$(".small_Btn").button();

		//$(".comment_div").css({fontSize : "0.7em"});
		//$("#attdList_tabs").tabs().css({margin:"10px"});

		//getDatePicker($("#search_restAttd_startDate"));
		//getDatePicker($("#search_restAttd_endDate"));
		
		showRestAttdListGrid(); // 근태외신청목록 그리드

		findRestAttdList(); //신청일자가 오늘인 근태외신청을 바로 보여준다
		
		/* $("#search_restAttd_deptName").click(function(){
			getCode("CO-07","search_restAttd_deptName","search_restAttd_deptCode");
		}); // 부서선택
		
		$("#search_restAttd_startDate").change(function(){
			$("#search_restAttd_endDate").datepicker("option","minDate",$(this).val());
		}); // 시작일
		
		$("#search_restAttd_endDate").change(function(){
			$("#search_restAttd_startDate").datepicker("option","maxDate",$(this).val());
		}); // 종료일 */
		
		$("#apploval_restAttd_Btn").click(applovalRestAttd); // 승인버튼
		$("#cancel_restAttd_Btn").click(cancelRestAttd); // 승인취소버튼
		$("#reject_restAttd_Btn").click(rejectRestAttd); // 반려버튼
		$("#update_restAttd_Btn").click(modifyRestAttd); // 확정버튼
	});
	
	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	
    /* 근태외신청 그리드 띄우는 함수 */
    function showRestAttdListGrid(){
    		var columnDefs = [
    		      {headerName: "Emp Code", field: "empCode",hide:true },
    		      {headerName: "Emp Name", field: "empName",checkboxSelection: true},
    		      {headerName: "Serial Number", field: "restAttdCode",hide:true },
    		      {headerName: "Code", field: "restTypeCode",hide:true},
    		      {headerName: "Code Name", field: "restTypeName"},
    		      {headerName: "Application Date", field: "requestDate"},
    		      {headerName: "Start Date", field: "startDate"},
    		      {headerName: "End Date", field: "endDate"},
    		      {headerName: "Start Time", field: "startTime"},
    		      {headerName: "End Time", field: "endTime"},
    		      {headerName: "Day", field: "numberOfDays"},
    		      //{headerName: "경비", field: "cost"},
    		      {headerName: "Reason", field: "cause"},
    		      {headerName: "Approval status", field: "applovalStatus",editable:true},
    		      {headerName: "Reject Reason", field: "rejectCause",editable:true},
    		      {headerName: "Status", field: "status",hide:true}
    		];      
      	gridOptions = {
      			columnDefs: columnDefs,
      			rowData: restAttdList,
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
      			// 페이지
    			pagination : true,

    			// 페이지에 보여줄 row의 수
    			paginationPageSize : 20,
    			
      		    onCellEditingStarted: function (event) {
      		        console.log('cellEditingStarted');
      		    }, 
      	};   
      	$('#restAttdList_grid').children().remove();	 
      	var eGridDiv = document.querySelector('#restAttdList_grid');
      	new agGrid.Grid(eGridDiv, gridOptions);	
       }

	function findRestAttdList(){
	   
		
		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/attendanceApploval.do",
			data:{
				"method" : "findRestAttdListByDept"			
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

				restAttdList = data.restAttdList;
				
				/* for(var index in restAttdList){
					restAttdList[index].time = getRealTime(restAttdList[index].time);
				} */

				showRestAttdListGrid();
			}
		});
	}
	
	/* 근태외 확정버튼 눌렀을 때 실행되는 함수 */
	function modifyRestAttd(){
		var sendData = JSON.stringify(restAttdList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/attendanceApploval.do",
			data : {
				"method" : "modifyRestAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("It's failed to confirm");
				} else {
					alert("It's confirmed");
				}
				location.reload();
			}
		});
	}

	
	/* 근태외 승인버튼 함수 */
	function applovalRestAttd(){
		var rowNode = gridOptions.api.getSelectedNodes();
		console.log(rowNode[0]);
		if(rowNode[0] == null){
			alert("Please select the approval item you want to approve");
			return;
		}
		rowNode[0].setDataValue('rejectCause', "");
		rowNode[0].setDataValue('applovalStatus',"Approval");
		rowNode[0].setDataValue('status', "update");
		console.log(rowNode[0].data);
	}
	/* 근태외 승인취소버튼 함수 */
	function cancelRestAttd(){
		var rowNode = gridOptions.api.getSelectedNodes();
		if(rowNode[0] == null){
			alert("Please select the approval you want to cancel");
			return;
		}
		rowNode[0].setDataValue('applovalStatus', "Cancel the approval");
		rowNode[0].setDataValue('status', "update");
		console.log(rowNode[0].data);
	};	
	/* 근태외 반려버튼 함수 */
	function rejectRestAttd(){
		var rowNode = gridOptions.api.getSelectedNodes();
		if(rowNode[0] == null){
			alert("Please select the approval you want to reject");
			return;
		}
		rowNode[0].setDataValue('applovalStatus', "Rejection");
		rowNode[0].setDataValue('status', "update");
		console.log(rowNode[0].data);
	}

</script>
</head>
<body>
	<h4>Approval management</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="apploval_restAttd_Btn" value="Approval"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="cancel_restAttd_Btn"  value="Cancel the approval" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="reject_restAttd_Btn" value="Rejection" class="btn btn-Light shadow-sm btnsize">
			<input type="button" id="update_restAttd_Btn" value="Confirmation" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	
	<hr>
	<div class="col-12" style="float: left">
		<div class="card">
			<div class="card-body">
				<div id="findtab1">	
					Search &nbsp 
					<input name="searchKeyword" type="text"  oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br /> <br />
					<div id="codeList_tab">
						<table>
							<tr>
								<td>
									<div id="restAttdList_grid" style="height: 300px; width: 1500px" class="ag-theme-balham"></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>