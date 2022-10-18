<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	var selectedPositionBean, updatedPositionBean = []; // 사원의 상세정보를 저장하는 객체들
	var emptyPositionBean = []; // 그리드에 새 행을 추가하기 위한 비어있는 객체들
	var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)
	var addrowData;

	$(document).ready(function() {
		$("#positionList_tabs").tabs();

		positionListAjax();

		// 그리드의 행 추가, 삭제 버튼들
		$("#add_position_Btn").click(addListGridRow);
		$("#del_position_Btn").click(delListGridRow);

		/* 상세정보 탭의 저장 버튼 */
		$("#modifyPosition_Btn").click(function() {
			if (updatedPositionBean == null) {
				alert("저장할 내용이 없습니다");
			} else {
				var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
				if (flag)
					modifyPosition();
			}
		});
	});

	function positionListAjax() {
		$.ajax({
			url : "${pageContext.request.contextPath}/base/positionList.do",
			data : {
				"method" : "findPositionList"
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

				// initField(); 
				// 전역변수 초기화

				setAllEmptyBean(data); // 비어있는 객체들 생성

				selectedPositionBean = $.extend(true, [], data.positionList); // 취소버튼을 위한 임시 저장공간에 딥카피
				updatedPositionBean = $.extend(true, [], data.positionList); // 변경된 내용이 들어갈 공간에 딥카피

				showPositionListGrid();
			}
		});

	}

	/* 전역변수 초기화 함수 */
	function initField() {
		selectedPositionBean, updatedPositionBean = [];
		emptyPositionBean = [];
		lastId = 0;
	}

	/* 직급목록 그리드 띄우는 함수 aggrid*/
	function showPositionListGrid() {
		var columnDefs = [ {
			headerName : "직급코드",
			field : "positionCode"
		}, {
			headerName : "직급명",
			field : "position"
		}, {
			headerName : "기본급",
			field : "baseSalary"
		}, {
			headerName : "호봉인상률",
			field : "hobongRatio"
		}, {
			headerName : "상태",
			field : "status"
		} ];
		gridOptions = {
			columnDefs : columnDefs,
			rowData : updatedPositionBean,
			defaultColDef : {
				editable : true,
				width : 100
			},
			rowSelection : 'multiple', /* 'single' or 'multiple',*/
			//rowMultiSelectWithClick : true,
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
				// process();
				console.log(event.data.status);
				console.log(event.data);
				if (event.data.status == "normal") {
					event.data.status = "update"
				}
				console.log(event.data.status);
			},
		};

		var eGridDiv = document.querySelector('#positionList_grid');
		new agGrid.Grid(eGridDiv, gridOptions);
	}

	function process() {
		result.forEach(function(element) {
			console.log(element);
		});
	}

	/* 새로운 정보들을 추가하기 위한 빈 객체 세팅 */
	function setAllEmptyBean(data) {
		emptyPositionBean = data.emptyPositionBean;
		emptyPositionBean.status = "insert";
	}

	// 그리드에 행 추가하는 함수
	function createNewRowData() {
		var newData = {
			positionCode : "POS",
			position : "PosName ",
			baseSalary : "",
			hobongRatio : "",
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
		console.log(selectedData);
		var selectedData0 = selectedData[0];
		console.log(selectedData0.status);
		if (selectedData0.status == "normal") {
			selectedData0.status = 'delete'
		}
		gridOptions.api.updateRowData({
			update : selectedData
		});
		console.log('delete Data:');
		getRowData();
	}

	/* 저장 버튼을 눌렀을 때 실행되는 함수 */
	function modifyPosition() {
		if (addrowData != null) {
			var sendData = JSON.stringify(addrowData);
			alert(sendData);
		} else {
			var sendData = JSON.stringify(updatedPositionBean);
			alert(sendData);
		}

		$('#positionList_grid').children().remove();
		$.ajax({
			url : "${pageContext.request.contextPath}/base/positionList.do",
			data : {
				"method" : "modifyPosition",
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
				var eGridDiv = document.querySelector('#positionList_grid');
				new agGrid.Grid(eGridDiv, gridOptions);
				console.log(sendData);
				location.reload();
			}
		});
	}
</script>


<div id="positionList_tabs" style="display: inline-block;">
	<ul>
		<li><a href="#positionList_tab">직급관리목록</a></li>
	</ul>
	<div id="positionList_tab">
		<input type="button" id="add_position_Btn"
			class="ui-button ui-widget ui-corner-all" value="추가"> <input
			type="button" id="del_position_Btn"
			class="ui-button ui-widget ui-corner-all" value="삭제"> <input
			type="button" id="modifyPosition_Btn"
			class="ui-button ui-widget ui-corner-all" value="저장"><br />
		<br />
		<div id="positionList_grid" style="height: 600px; width: 500px"
			class="ag-theme-balham"></div>
		<div id="positionList_pager"></div>
	</div>
</div>