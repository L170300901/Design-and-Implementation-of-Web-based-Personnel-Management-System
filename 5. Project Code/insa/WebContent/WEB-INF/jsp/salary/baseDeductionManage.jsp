<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공제기준관리</title>
<style type="text/css">
#baseDeductionList_tabs {
	width: 550px;
	margin: auto;
}
</style>
<script>
	var baseDeductionList = [];
	var emptyBean= [];
	var lastId = 0;
	var gridOptions;
	var addrowData;

	$(document).ready(function(){
		/* css 전역 설정 */
		// 작은 버튼들의 css 별도 생성
		$(".small_Btn").css({
			width : "auto",
			height : "auto",
			fontSize : "15px",
			fontFamily : "MD개성체"
		}).button();

		// tab css
		$("#baseDeductionList_tabs").tabs();

		// event handling
		$("#add_baseDeduction_Btn").click(addListGridRow);
		$("#del_baseDeduction_Btn").click(delListGridRow);
		$("#submit_Btn").click(batchBaseDeductionProcess);

		// grid setting
		$.ajax({
			url:"${pageContext.request.contextPath}/salary/baseDeductionManage.do",
			data:{
				"method" : "findBaseDeductionList"
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

				baseDeductionList = data.baseDeductionList;
				emptyBean = data.emptyBean;
				showBaseDeductionListGrid();
			}
		});
	});

	//변경확정
	function batchBaseDeductionProcess(){
		if(addrowData != null){
			var sendData = JSON.stringify(addrowData);
			alert(sendData);
		}else{
			var sendData = JSON.stringify(baseDeductionList);
			alert(sendData);
		}
		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseDeductionManage.do",
			data : {
				"method" : "batchBaseDeductionProcess",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("저장에 실패했습니다");
				} else {
					alert("저장되었습니다");
				}
				var eGridDiv = document.querySelector('#baseDeductionList_grid');
				new agGrid.Grid(eGridDiv, gridOptions);	
				location.reload();
			}
		});
	}

	/* 공제기준목록 그리드 띄우는 함수 aggrid*/
	 function showBaseDeductionListGrid(){
		 var result=[];   
		 var columnDefs = [
		      {headerName: "공제항목코드", field: "deductionCode" },
		      {headerName: "공제항목명", field: "deductionName" },
		      {headerName: "공제율", field: "ratio" },
		      {headerName: "상태", field: "status" }
		 ];    
  		gridOptions = {
    		columnDefs: columnDefs,
    		rowData: baseDeductionList,
    		defaultColDef: { editable: true },
    		rowSelection: 'single', /* 'single' or 'multiple',*/
		    onCellEditingStopped: function (event) {
		        console.log('cellEditingStopped');
		        console.log(event.data.status);
		        console.log(event.data);
				if (event.data.status == "normal"){event.data.status = "update"}
		  			console.log(event.data.status);	
		    },  
    	};
			var eGridDiv = document.querySelector('#baseDeductionList_grid');
			new agGrid.Grid(eGridDiv, gridOptions);	
			gridOptions.api.sizeColumnsToFit();
	 }

	/* 그리드에 행 추가하는 함수 */
	function createNewRowData() {
		var newData = {
				deductionCode : "DED",
				deductionName : "deductionName ",
				ratio  : "",
			 	status   : "insert"
		};
	    return newData;
	}	
		
	function addListGridRow() {
	    var newItem = createNewRowData();
	    gridOptions.api.updateRowData({add: [newItem]});
	    getRowData();
	}
	
	function getRowData() {
		addrowData = [];
	    gridOptions.api.forEachNode( function(node) {
	        addrowData.push(node.data);
	    });
	    console.log('Row Data:');
	    console.log(addrowData);
	}

	/* 그리드에 행 삭제(멀티셀렉트 미적용 함수라서 1개씩만 삭제됨) */
	function delListGridRow() {
	    var selectedData = gridOptions.api.getSelectedRows();
	    console.log(selectedData);
		var selectedData0 = selectedData[0]; 
		console.log(selectedData0.status); 
	    if(selectedData0.status == "normal"){
	    	selectedData0.status = 'delete'
		}	
		console.log('delete Data:');
	    getRowData();
	}
	
</script>
</head>
<body>
	<div id="baseDeductionList_tabs">
		<ul>
			<li><a href="#baseDeductionList_tab">공제기준관리</a></li>
		</ul>
		<div id="baseDeductionList_tab">
			<button class="ui-button ui-widget ui-corner-all"
				id="add_baseDeduction_Btn">추가</button>
			<button class="ui-button ui-widget ui-corner-all"
				id="del_baseDeduction_Btn">삭제/삭제취소</button>
			<button class="ui-button ui-widget ui-corner-all" id="submit_Btn">변경확정</button>
			<br /> <br />
			<div id="baseDeductionList_grid" style="height: 600px; width: 500px"
				class="ag-theme-balham"></div>
			<div id="baseDeductionList_pager"></div>
		</div>
	</div>
</body>
</html>