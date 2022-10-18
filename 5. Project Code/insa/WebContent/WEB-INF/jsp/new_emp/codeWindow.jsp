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
		showCodeGrid();
		if ("${param.code}" == "lastSchool") { 
			showlastSchoolCode();
		} 
		
	});
	
	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	}
	
	function showlastSchoolCode() {
		$.ajax({
			url : '${pageContext.request.contextPath}/new_emp/codeList.do',
			data : {
				"method" : "lastSchoolCodeList",
				"code" : "HB"

			},
			dataType : "json",
			success : function(data) {
				//console.log("1");
				//console.log(data.list);
				if (data.errorCode < 0) {
					var str = " An internal server error has occurred.\n";
					str += "Please contact the administrator.\n";
					str += "Location : an error : " + $(this).attr("id");
					str += "Error messages : " + data.errorMsg;
					alert(str);
					return;
				}
				gridOptions.api.setRowData(data.lastSchoolCodeList);
			}
		});
	}
	
	
	function showCodeGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "코드",
			field : "detailCodeNumber",
		}, {
			headerName : "관리내역명",
			field : "detailCodeName",
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
				var detailCodeNumber = node.data.detailCodeNumber;
				var detailCodeName=node.data.detailCodeName;
				opener.document.getElementById("lastSchoolA").value = detailCodeNumber;
				opener.document.getElementById("lastSchoolB").value = detailCodeName;
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
					검색 &nbsp 
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