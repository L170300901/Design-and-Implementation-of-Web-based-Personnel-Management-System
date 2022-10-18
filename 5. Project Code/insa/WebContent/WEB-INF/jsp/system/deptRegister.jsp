k<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사업자 등록</title>
<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
<style>
/* Style the tab */
.tab {
	overflow: hidden;
	border: 1px solid #ccc;
	background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
	background-color: inherit;
	float: left;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
	background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 6px 12px;
	border: 1px solid #ccc;
	border-top: none;
}

select {
	text-align-last: center;
	text-align: center;
	-ms-text-align-last: center;
	-moz-text-align-last: center;
}

input[type=text], select {
	display: inline-block;
	border: 1px solid #ccc;
	color: #4C4C4C;
}

.date {
	width: 140px;
	height: 30px;
	font-size: 0.9em;
}

.btnsize {
	width: 90px;
	height: 30px;
	font-size: 0.9em;
	font-align: center;
	color: black;
}

.btnsize2 {
	width: 60px;
	height: 30px;
	font-size: 0.9em;
	color: black;
}

.btn {
	border: none;
	font-size: 16px;
	cursor: pointer;
	background-color:#FFBB00;
}

.info {
	background-color: #FAED7D;
}

.info:hover {
	background: #FAF4C0;
}
</style>
<script>
	var deptList = [];
	var gridOptions;
	var rowData;
	var addrowData;
	
	$(document).ready(function() {
		
		//버튼 이벤트
		$('input:button').hover(function() {
			$(this).css("background-color", "#D8D8D8");
		}, function() {
			$(this).css("background-color", "");
		});
		$("#deptAdd").click(addGridRow);
		$("#deptDelete").click(removeGirdRow);
		$("#deptSave").click(saveGridRow);
		
		showDeptList();
		findgrid();
	});
	////부서추가 버튼을 누를시-(1)-1
	function createNewRowData() {
	      var newData = {
	    		 deptCode : "",
	    		 deptName : "",
	    		 branchCode : "",
	    		 branchName:"",
	    		 bumunCode: "",
	    		 bumunName: "",
	    		 deptPeriodA: "",
	    		 deptPeriodB: "",
	    		 status:"insert"
	      };
	      return newData;
	}
	
	//부서추가 버튼을 누를시-(1)
	function addGridRow(){
		//console.log("@@ 1. 추가버튼을 눌렀습니다");
		var newItem = createNewRowData();
		gridOptions.api.updateRowData({
	         add : [ newItem ]
	     });
	     getRowData();
	}
	//부서추가 버튼을 누를시-(2)
	function getRowData() {
	      addrowData = [];
	      gridOptions.api.forEachNode(function(node) {
	    	 //console.log("node"+node.data);
	         addrowData.push(node.data);
	      });
	      
	   }
	//부서삭제 버튼을 누를시-(1)
	   function removeGirdRow() {
		   //console.log("@@ 2. 삭제버튼을 눌렀습니다");
	      	var selectedData = gridOptions.api.getSelectedRows();
	      	var selectedData0 = selectedData[0];
	      	if (selectedData0.status != "delete") {
	         	selectedData0.status = 'delete'
	      	}
	      	gridOptions.api.updateRowData({
	         	update : selectedData
	      	});
	      	//console.log('delete Data:');
	      	//console.log(selectedData0);
	      	
	      	selectedData.forEach(function (selectedData, index) { 
	            gridOptions.api.applyTransaction({ remove: [selectedData] });
	        });
	      	//addrowData.pop(selectedData);
	      	//getRowData();
	   }
	 //저장 버튼을 눌렀을 때 실행되는 함수 
	   function saveGridRow() {
		//alert("아직 구현을 안했음 ");
		//console.log("@@ 3. 저장버튼을 눌렀습니다");
		var sendData=[];
		//1번 경우 추가를 한것이 있을경우 
	      if (addrowData != null) {
	    	  var sendData = JSON.stringify(addrowData);
	      } else { //2번 경우 추가를 한것이 없을경우 but 수정이 있을경우 
	    	  console.log(3);
	    	  var sendData = JSON.stringify(deptList);
	          sendData=sendData.substring(1,sendData.length-1);
	          
	          //sendData=sendData.substring(sendData.length,1);
	          //sendData = detailCodeList+"";
	         // console.log("44444"+addrowData);
	          //console.log("212121"+detailCodeList);
	        console.log("sendData===="+sendData);
	      }
		/*
	      $.ajax({
	         url : "${pageContext.request.contextPath}/base/codeList.do",
	         data : {
	            "method" : "saveDeptList",
	            "sendData" : sendData
	         },
	         dataType : "json",
	         success : function(sendData) {
	            //console.log(sendData);
	            if (sendData.errorCode < 0) {
	               alert("저장에 실패했습니다");
	            } else {
	            	addrowData=[];
	            	detailCodeList=[];
	               alert("저장되었습니다");
	            }
	            //var eGridDiv = document.querySelector('#holidayListGrid');
	            //new agGrid.Grid(eGridDiv, gridOptions);
	            //console.log(sendData);
	            //location.reload();
	         }
	      });
	      */
	   }
	
	
	//출력구분하기 
	function findgrid() {
		$.ajax({
			url : '${pageContext.request.contextPath}/system/findDept.do',
			data : {
				"method" : "deptList"
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
				deptList = data.deptList; 
				gridOptions.api.setRowData(data.deptList);
			}
		});
	}
	
	//	1. 왼쪽 코드목록 그리드 띄우는 함수 
	function showDeptList(){
		rowData = [];
		var columnDefs = [ {
			headerName : "Code",
			field : "deptCode",
			width : 50
		}, {
			headerName : "Name",
			field : "deptName",
			width: 50
		}, {
			headerName : "Business code",
			field : "branchCode",
			width: 70
		}
		, {
			headerName : "Business name",
			field : "branchName"
		}, {
			headerName : "Division Code",
			field : "bumunCode",
			width: 50
		}
		, {
			headerName : "Division name",
			field : "bumunName",
			
			
		}
		, {
			headerName : "Usage period Start",
			field : "deptPeriodA",
				width: 50
		}
		, {
			headerName : "Usage period End",
			field : "deptPeriodB",
				width: 50
		}, {
			headerName : "Status",
			field : "status",
			width: 30
		}
		];
		gridOptions = {
			columnDefs : columnDefs,

			//row는 하나만 선택 가능
			rowSelection : 'single',

			// 정의하지 않은 컬럼은 자동으로 설정
			defaultColDef : {
				editable : true
			},
			// 페이저
			pagination : true,

			// 페이저에 보여줄 row의 수
			paginationPageSize : 20,

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
			
			//클릭시 수정가능하게 하는 
			defaultColDef : {
				editable : true,
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
					'text-align' : 'center'
				};
			},
			onCellEditingStarted : function(event) {
                if (event.data.status == "normal") {
                    event.data.status = "update"
                 }              
            },
            onCellEditingStopped : function(event) {
            },
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			}
		};
		deptFind_grid = document.querySelector('#deptFind_grid');
		new agGrid.Grid(deptFind_grid, gridOptions);
	}
</script>
</head>

<body>

	<h4>Department registration</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="deptAdd" value="Add"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="deptDelete"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="deptSave" value="Save" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	<hr>
	<div class="col-12" style="float: left">
		<div class="card">
			<div class="card-body">
				<div>				
					Department search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br/><br/>
					<div id="codeList_tab">
						<div>
							<div id="deptFind_grid" style="height: 500px; width: 1500px" class="ag-theme-balham"></div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>