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
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-fresh.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-material.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>급여기준관리</title>
<style type="text/css">
#baseSalaryList_tabs {
	margin: auto;
	width: 550px;
}

.rag-amber {
	<!--background-color: lightsalmon;-->
}
</style>

<script>

	// 전역변수 선언
	var updatedSalaryBean = []; 
	var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)
	var gridOptions;
	
	$(document).ready(function(){
		$("#baseSalaryList_tabs").tabs();		
	    getBaseSalaryListFunc();
		$("#submit_Btn").click(modifyBaseSalaryList);
	});

	function getBaseSalaryListFunc(){
			$.ajax({
				url:"${pageContext.request.contextPath}/salary/baseSalaryManage.do",
				data:{
					"method" : "findBaseSalaryList"
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

					initField(); // 전역변수 초기화
					
					updatedSalaryBean = $.extend(true, [], data.baseSalaryList); // 변경된 내용이 들어갈 공간에 딥카피
					console.log(updatedSalaryBean);
					
					showBaseSalaryListGrid();
				}
			});

		}

	/* 전역변수 초기화 함수 */
	function initField() {
		updatedDeptBean = [];
		lastId = 0;
	}
	
	/*수정 버튼 눌렀을때 */
	function modifyBaseSalaryList(){
		var sendData = JSON.stringify(updatedSalaryBean);
		$('#baseSalaryList_grid').children().remove();
		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseSalaryManage.do",
			data : {
				"method" : "modifyBaseSalaryList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("저장에 실패했습니다");
				} else {
					alert("저장되었습니다");
				}
				var eGridDiv = document.querySelector('#baseSalaryList_grid');
				new agGrid.Grid(eGridDiv, gridOptions);	
				console.log(sendData);
				location.reload();
			}
		});
	}
 
	 /* 급여기준목록 그리드 띄우는 함수 aggrid*/
	 function showBaseSalaryListGrid(){
		 var result=[];   
		 var columnDefs = [
		      {headerName: "직급코드", field: "positionCode", hide: true  },
		      {headerName: "직급", field: "position", cellClass: 'rag-amber' },
		      {headerName: "기본급", field: "baseSalary" },
		      {headerName: "호봉인상율", field: "hobongRatio"},
		      {headerName: "상태", field: "status"}
		    	];    
		     gridOptions = {
		    		columnDefs: columnDefs,
		    		rowData: updatedSalaryBean,
		    		defaultColDef: { editable: true },
		    	 	onCellEditingStarted:function(e) {
		    	        console.log('cellEditingStarted');
		    	        },
	    		    onCellEditingStopped: function (event) {
	    		        console.log('cellEditingStopped');
	    		        console.log(event.data.status);
	    		        console.log(event.data);
						if (event.data.status == "normal"){event.data.status = "update"}
				  			console.log(event.data.status);	
	    		    }
		    	};
			var eGridDiv = document.querySelector('#baseSalaryList_grid');
			new agGrid.Grid(eGridDiv, gridOptions);	
			gridOptions.api.sizeColumnsToFit();
	}
	 
</script>


</head>
<body>
	<div id="baseSalaryList_tabs">
		<ul>
			<li><a href="#baseSalaryList_tab">급여기준관리</a></li>
		</ul>
		<div id="baseSalaryList_tab">
			<button class="ui-button ui-widget ui-corner-all" id="submit_Btn">변경확정</button>
			<br /> <br />
			<div id="baseSalaryList_grid" style="height: 600px; width: 500px"
				class="ag-theme-balham"></div>
			<div id="baseSalaryList_pager"></div>
		</div>
	</div>
</body>
</html>