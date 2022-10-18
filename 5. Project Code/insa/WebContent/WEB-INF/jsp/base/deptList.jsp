<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
</style>
<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">

<script>
	var deptList_grid = $("#deptList_grid");
	var selectedDeptBean, updatedDeptBean = []; // 사원의 상세정보를 저장하는 객체들
	var addrowData;
	var emptyBean = []; // 그리드에 새 행을 추가하기 위한 비어있는 객체들

	var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)
	var gridOptions;

	$(document).ready(function() {

		$("#deptList_tabs").tabs();
		deptListAjax();

		// 그리드의 행 추가, 삭제 버튼들
		$("#add_dept_Btn").click(addListGridRow);
		$("#del_dept_Btn").click(delListGridRow);

		/* 상세정보 탭의 저장 버튼 */
		$("#modifyDept_Btn").click(function() {
			if (updatedDeptBean == null) {
				alert("저장할 내용이 없습니다");
			} else {
				var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
				if (flag)
					modifyDept();
				showDeptListGrid();
			}
		});

	});

	function deptListAjax() {
		$.ajax({
			url : "${pageContext.request.contextPath}/base/deptList.do",
			data : {
				"method" : "findDeptList"
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
				initField(); // 전역변수 초기화
				setAllEmptyBean(data); // 비어있는 객체들 생성

				selectedDeptBean = $.extend(true, [], data.list); // 취소버튼을 위한 임시 저장공간에 딥카피
				updatedDeptBean = $.extend(true, [], data.list); // 변경된 내용이 들어갈 공간에 딥카피

				showDeptListGrid();
			}
		});

	}

	/* 전역변수 초기화 함수 */
	function initField() {
		selectedDeptBean, updatedDeptBean = [];
		emptyBean = [];
		lastId = 0;
	}

	/* 부서목록 그리드 띄우는 함수 aggrid*/
	function showDeptListGrid() {
		var columnDefs = [ {
			headerName : "부서코드",
			field : "deptCode"
		}, {
			headerName : "부서명",
			field : "deptName"
		}, {
			headerName : "부서전화번호",
			field : "deptTel"
		}, {
			headerName : "상태",
			field : "status"
		} ];
		gridOptions = {
			columnDefs : columnDefs,
			rowData : updatedDeptBean,
			defaultColDef : {
				editable : true,
				width : 100
			},
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
				console.log('cellEditingStarted');
			},
			onCellEditingStopped : function(event) {
				console.log('cellEditingStopped');
				process();
				console.log(event.data.status);
				console.log(event.data);
				if (event.data.status == "normal") {
					event.data.status = "update"
				}
				console.log(event.data.status);
			},
		};

		var eGridDiv = document.querySelector('#deptList_grid');
		new agGrid.Grid(eGridDiv, gridOptions);
	}
	function process() {
		result.forEach(function(element) {
			console.log(element);
		});
	}

	/* 새로운 정보들을 추가하기 위한 빈 객체 세팅 */
	function setAllEmptyBean(data) {
		emptyBean = data.emptyBean;
		emptyBean.status = "insert";
	}

	// 그리드에 행 추가하는 함수
	function createNewRowData() {
		var newData = {
			deptCode : "DEP00",
			deptName : "deptName",
			deptTel : "0000",
			status : "insert"
		};
		return newData;
	}

	function addListGridRow() {
		var newItem = createNewRowData();
		gridOptions.api.updateRowData({
			add : [ newItem ]
		});
		getRowData();
	}

	function getRowData() {
		addrowData = [];
		gridOptions.api.forEachNode(function(node) {
			addrowData.push(node.data);
		});
		console.log('Row Data:');
		console.log(addrowData);
	}

	/* 그리드에 행 삭제 (주석은 행 추가하는거 참조)*/
	function delListGridRow() {
		var selectedData = gridOptions.api.getSelectedRows();
		var selectedData0 = selectedData[0];
		if (selectedData0.status == "normal") {
			selectedData0.status = 'delete'
			gridOptions.api.updateRowData({
				update : selectedData
			});
		}
		console.log('delete Data:');
		console.log(selectedData0.status);
		console.log(selectedData);

		getRowData();
	}

	/* 저장 버튼을 눌렀을 때 실행되는 함수 */
	function modifyDept() {
		if (addrowData != null) {
			var sendData = JSON.stringify(addrowData);
			alert(sendData);
		} else {
			var sendData = JSON.stringify(updatedDeptBean);
			alert(sendData);
		}

		$('#deptList_grid').children().remove();
		$.ajax({
			url : "${pageContext.request.contextPath}/base/deptList.do",
			data : {
				"method" : "batchDeptProcess",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(sendData) {
				console.log(sendData);
				if (sendData.errorCode < 0) {
					alert("저장에 실패했습니다");
				} else {
					alert("저장되었습니다");
				}
				var eGridDiv = document.querySelector('#deptList_grid');
				new agGrid.Grid(eGridDiv, gridOptions);
				console.log(sendData);
				location.reload();
			}
		});
	}
</script>


<div id="deptList_tabs" style="display: inline-block;">
	<ul>
		<li><a href="#deptList_tab">부서관리목록</a></li>
	</ul>
	<div id="deptList_tab">
		<input type="button" id="add_dept_Btn"
			class="ui-button ui-widget ui-corner-all" value="추가"> <input
			type="button" id="del_dept_Btn"
			class="ui-button ui-widget ui-corner-all" value="삭제"> <input
			type="button" id="modifyDept_Btn"
			class="ui-button ui-widget ui-corner-all" value="저장"><br />
		<br />
		<div id="deptList_grid" style="height: 600px; width: 500px"
			class="ag-theme-balham"></div>
		<div id="deptList_pager"></div>
	</div>
</div>




