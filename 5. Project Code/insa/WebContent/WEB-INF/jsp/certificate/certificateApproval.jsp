<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>재직증명서 관리</title>
<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
<style>

input[type=text], select {
	display: inline-block;
	border: 1px solid #ccc;
	color: #4C4C4C;
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
	var certificateRequestList = [];
	var lastId = 0;

	$(document).ready(
			function() {
				

				showCertificateListGrid();

				findCertificateRequestList();

				$("#apploval_certificate_Btn")
						.click(applovalCertificateRequest); // 승인버튼
				$("#cancel_certificate_Btn").click(cancelCertificateReqeust); // 승인취소버튼
				$("#reject_certificate_Btn").click(rejectCertificateRequest); // 반려버튼
				$("#update_certificate_Btn").click(modifyCertificateRequest); // 확정버튼
			});
	

	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	
	/*그리드 띄우는 함수 */
	function showCertificateListGrid() {
		var columnDefs = [ {
			headerName : "Department",
			field : "deptName",
			checkboxSelection : true,
			width: 70
		}, {
			headerName : "Emp Number",
			field : "empCode",
			width: 50
		}, {
			headerName : "Emp Name",
			field : "empName",
			width: 50
		}, {
			headerName : "Purpose",
			field : "usageName"
		}, {
			headerName : "Application Date",
			field : "requestDate",
			width: 50
		}, {
			headerName : "Used day",
			field : "useDate",
			width: 50
		}, {
			headerName : "Approval status",
			field : "approvalStatus",
			width: 70
		}, {
			headerName : "Reason for reject",
			field : "rejectCause",
			editable : true
		}, {
			headerName : "Note",
			field : "etc"
		}, {
			headerName : "Status",
			field : "status",
			hide : true
		} ];
		gridOptions = {
			columnDefs : columnDefs,
			rowData : certificateRequestList,
			defaultColDef : {
				editable : false,
				width : 100
			},
			rowSelection : 'single', /* 'single' or 'multiple',*/
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 10,
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
				console.log('cellEditingStarted');
			},
		};
		$('#certificateList_grid').children().remove();
		var eGridDiv = document.querySelector('#certificateList_grid');
		new agGrid.Grid(eGridDiv, gridOptions);
	}

	/* 재직증명서 조회버튼 함수 */
	function findCertificateRequestList() {

		$.ajax({
					url : "${pageContext.request.contextPath}/certificate/certificateApproval.do",
					data : {
						"method" : "findCertificateListByDept"						
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

						certificateRequestList = data.certificateList;
						showCertificateListGrid();
					}
				});
	}

	/*  확정버튼 눌렀을 때 실행되는 함수 */
	function modifyCertificateRequest() {
		var sendData = JSON.stringify(certificateRequestList);

		$
				.ajax({
					url : "${pageContext.request.contextPath}/certificate/certificateApproval.do",
					data : {
						"method" : "modifyCertificateList",
						"sendData" : sendData
					},
					dataType : "json",
					success : function(data) {
						if (data.errorCode < 0) {
							var str = " An internal server error has occurred.\n";
							str += "Please contact the administrator.\n";
							str += "Location : an error : " + $(this).attr("id");
							str += "Error messages : " + data.errorMsg;
							alert(str);
						} else {
							alert("It's confirmed");
						}
						findCertificateRequestList('모든부서');
					}
				});
	}

	/* 승인버튼 함수 */
	function applovalCertificateRequest() {
		var rowNode = gridOptions.api.getSelectedNodes();
		console.log(rowNode[0].data);
		if (rowNode == null) {
			alert("Please select the certificate of employment to be approved");
			return;
		}
		rowNode[0].setDataValue('approvalStatus', "Approval");
		rowNode[0].setDataValue('rejectCause', "");
		rowNode[0].setDataValue('status', "update");
		console.log(rowNode[0].data);
	}
	/* 승인취소버튼 함수 */
	function cancelCertificateReqeust() {
		var rowNode = gridOptions.api.getSelectedNodes();
		if (rowNode == null) {
			alert("Please select the certificate of employment that you want to cancel");
			return;
		}
		rowNode[0].setDataValue('approvalStatus', "Cancel the approval");
		rowNode[0].setDataValue('status', "update");
	}
	/* 근태외 반려버튼 함수 */
	function rejectCertificateRequest() {
		var rowNode = gridOptions.api.getSelectedNodes();
		if (rowNode == null) {
			alert("Please select the certificate of employment that you want to reject");
			return;
		}
		rowNode[0].setDataValue('approvalStatus', "Rejection");
		rowNode[0].setDataValue('status', "update");
	}

	/* 날짜 조회창 함수 */
	function getDatePicker($Obj, maxDate) {
		$Obj.datepicker({
			defaultDate : "d",
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
			monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12" ],
			yearRange : "1980:2020",
			showOptions : "up",
			maxDate : (maxDate == null ? "+100Y" : maxDate)
		});
	}

</script>
</head>
<body>

	<h4>Management of employment certificate</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="apploval_certificate_Btn" value="Approval"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="cancel_certificate_Btn"  value="Cancel the approval" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="reject_certificate_Btn" value="Rejection" class="btn btn-Light shadow-sm btnsize">
			<input type="button" id="update_certificate_Btn" value="Confirmation" class="btn btn-Light shadow-sm btnsize">
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
									<div id="certificateList_grid" style="height: 300px; width: 1500px" class="ag-theme-balham"></div>
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