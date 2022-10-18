<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CODE</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.min.css">
<script
	src="${pageContext.request.contextPath}/script/jquery/jquery-3.3.1.min.js"></script>
<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
<script>

	var gridOptions;
	var rowData;
	
	$(document).ready(function() {
		if ("${param.code}" == "searchCompany") { 
			showCodeGrid1();
			searchCompanyCode();
		}
		else {
			//console.log(opener.document.getElementById("companyCode").value);
			
			var companyCode=(opener.document.getElementById("companyCode").value);
			showCodeGrid2();
			searchEmpCode(companyCode);
		} 
		
	});
	
	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	}
	
	//1. 	회사코드 조회
	function searchCompanyCode() {
		$.ajax({
			url : '${pageContext.request.contextPath}/searchCompanyCode.do',
			data : {
				"method" : "searchCompanyCode"

			},
			dataType : "json",
			success : function(data) {
				//console.log(data.companyCodeList);
				if (data.errorCode < 0) {
					var str = " An internal server error has occurred.\n";
					str += "Please contact the administrator.\n";
					str += "Location : an error : " + $(this).attr("id");
					str += "Error messages : " + data.errorMsg;
					alert(str);
					return;
				}
				gridOptions.api.setRowData(data.companyCodeList);
			}
		});
	}
	//2.	사원코드 조회
	function searchEmpCode(code) {
		//console.log(code);
		
		 $.ajax({
			url : '${pageContext.request.contextPath}/searchEmpCode.do',
			data : {
				"method" : "searchEmpCode",
				"companyCode" : code

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
				gridOptions.api.setRowData(data.empCodeList);
			}
		}); 
	}
	
	
	function showCodeGrid1() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Company Code",
			field : "companyCode",
		}, {
			headerName : "Company Name",
			field : "companyName",
		}
		];
		gridOptions = {
			columnDefs : columnDefs,

			//row는 하나만 선택 가능
			rowSelection : 'single',

			// 정의하지 않은 컬럼은 자동으로 설정
			defaultColDef : {
				editable : false
			},
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 10,

			// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
			onGridReady : function(event) {
				event.api.sizeColumnsToFit();
			},
			// GRID READY 이벤트, 사이즈 자동조정 
			onGridReady : function(event) {
				event.api.sizeColumnsToFit();
			},

			// 창 크기 변경 되었을 때 이벤트 
			onGridSizeChanged : function(event) {
				event.api.sizeColumnsToFit();
			},

			//rowData : codeList,
			defaultColDef : {
				editable : false,
				width : 100
			},

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
					//'text-align' : 'center'
				};
			},
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			},
			onCellClicked : function(node) {
				var companyCode = node.data.companyCode;
				var companyName=node.data.companyName;
				opener.document.getElementById("companyCode").value = companyCode;
				opener.document.getElementById("companyName").value = companyName;
				window.close();
			},
		};
		codeFind_grid = document.querySelector('#codeFind_grid');
		new agGrid.Grid(codeFind_grid, gridOptions);
	}
	function showCodeGrid2() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Employee Code",
			field : "empCode",
		}, {
			headerName : "Employee Name",
			field : "empName",
		}
		];
		gridOptions = {
			columnDefs : columnDefs,

			//row는 하나만 선택 가능
			rowSelection : 'single',

			// 정의하지 않은 컬럼은 자동으로 설정
			defaultColDef : {
				editable : false
			},
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 10,

			// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
			onGridReady : function(event) {
				event.api.sizeColumnsToFit();
			},
			// GRID READY 이벤트, 사이즈 자동조정 
			onGridReady : function(event) {
				event.api.sizeColumnsToFit();
			},

			// 창 크기 변경 되었을 때 이벤트 
			onGridSizeChanged : function(event) {
				event.api.sizeColumnsToFit();
			},

			//rowData : codeList,
			defaultColDef : {
				editable : false,
				width : 100
			},

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
					//'text-align' : 'center'
				};
			},
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			},
			onCellClicked : function(node) {
				var empCode = node.data.empCode;
				var empName = node.data.empName;
				opener.document.getElementById("empCode").value = empCode;
				opener.document.getElementById("empName").value = empName;
				window.close();
			},
		};
		codeFind_grid = document.querySelector('#codeFind_grid');
		new agGrid.Grid(codeFind_grid, gridOptions);
	}

			
</script>
</head>
<body>
	<div>
		<table>
			<tr>
				<td>
					Search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
				</td>
			</tr>
			<tr>
				<td>
					<div id="codeFind_grid" style="height: 300px; width: 300px"
						class="ag-theme-balham"></div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>