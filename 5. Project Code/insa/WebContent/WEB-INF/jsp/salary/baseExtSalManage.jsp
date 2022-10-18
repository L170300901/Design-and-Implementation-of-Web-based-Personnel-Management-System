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
<title>초과수당관리</title>
<style type="text/css">
#baseExtSalList_tabs {
	margin: auto;
	width: 550px;
}
</style>
<script>
	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	// 전역변수 선언
	var baseExtSalList = [];
	var lastId = 0;
	var gridOptions;	
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
		$("#baseExtSalList_tabs").tabs();

		// event handling
		$("#submit_Btn").click(modifyBaseExtSalList);

		// grid setting
		$.ajax({
			url:"${pageContext.request.contextPath}/salary/baseExtSalManage.do",
			data:{
				"method" : "findBaseExtSalList"
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

				baseExtSalList = data.baseExtSalList;
				showBaseExtSalListGrid();
			}
		});
	});

	/* do submit */
	function modifyBaseExtSalList(){
		var sendData = JSON.stringify(baseExtSalList);
		$('#baseSalaryList_grid').children().remove();
		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseExtSalManage.do",
			data : {
				"method" : "modifyBaseExtSalList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("Failed to save");
				} else {
					alert("Saved it");
				}
				var eGridDiv = document.querySelector('#baseSalaryList_grid');
				new agGrid.Grid(eGridDiv, gridOptions);	
				console.log(sendData);
				location.reload();
			}
		});
	}

	/* 급여기준목록 그리드 띄우는 함수 aggrid*/
	 function showBaseExtSalListGrid(){
		 var result=[];   
		 var columnDefs = [
		      {headerName: "Code", field: "extSalCode" },
		      {headerName: "Name", field: "extSalName", cellClass: 'rag-amber' },
		      {headerName: "Ratio", field: "ratio" },
		      {headerName: "Status", field: "status"}
		    	];    
		gridOptions = {
			columnDefs: columnDefs,
			rowData: baseExtSalList,
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 20,
			defaultColDef: { editable: true },
			rowSelection: 'single', /* 'single' or 'multiple',*/
		    onCellEditingStopped: function (event) {
		        console.log('cellEditingStopped');
		        console.log(event.data.status);
		        console.log(event.data);
				if (event.data.status == "normal"){event.data.status = "update"}
					console.log(event.data.status);	
		    }
		    		
			    	};
			var eGridDiv = document.querySelector('#baseExtSalList_grid');
			new agGrid.Grid(eGridDiv, gridOptions);	
			gridOptions.api.sizeColumnsToFit();
	 		}

</script>
</head>
<body>
	<h4>Excess allowance management</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="add_Btn" value="Add"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="remove_Btn"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="submit_Btn" value="Save" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	<hr>
	<div class="col-12" style="float: center">
		<div class="card">
			<div class="card-body">
				<div>				
					Search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br/><br/>
					<div id="codeList_tab">
						<div>
							<div id="baseExtSalList_grid" style="height: 600px; width: 1000px" class="ag-theme-balham"></div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>