<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>호봉테이블등록</title>
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
	var payDateList = [];
	var v_startDate;
	var v_positionName;
	var v_positionCode;
		
	var gridOptions1;
	var gridOptions2;
	var gridOptions3;
	var rowData;
	var addrowData;
	var hobongPositionList=[];
	var finaHobongTableList=[];	
	$(document).ready(function() {
		//버튼 이벤트
		$('input:button').hover(function() {
			$(this).css("background-color", "#D8D8D8");
		}, function() {
			$(this).css("background-color", "");
		});
		//1. 	호봉테이블 등록 버튼 
		$("#add_btn").click(addPay);
		$("#impression_btn").click(impressionPay);
		$("#copy_btn").click(copyHobong);
		$("#update_btn").click(updateCodeList);
		
		//2. 	호봉이력 테이블 버튼 
		$("#date_add").click(addDateGridRow);
		$("#date_save").click(saveDateGridRow);
		$("#date_delete").click(deleteDateGridRow);
		
		//1. 대상직급 그리드
		findPayPositionGrid();
		//2. 호봉이력 그리드 
		findPayDateGrid();
		showPayDateList();
		//3. 호봉 테이블 그리드 
		findPayTableGrid();
	});
	//1.	일괄등록 버튼을 누를시 
	function addPay(){
		//alert("일괄등록 버튼을 누름");
		var x = (document.body.offsetWidth/2)-(200/2);
		var y= (window.screen.height/ 2)-(300/2);
		option = "width=1000px; height=400px; left="+x+"; top="+y+"; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open("${pageContext.request.contextPath}/base/payCheckWindow.html?startDate="+v_startDate+"&&positionCode="+v_positionCode+"&&positionName="+v_positionName, "newwins", option);
		
	}
	//2. 	일괄인상 버튼을 누를시 
	function impressionPay(){
		//alert("일괄인상 버튼을 누름");
	}
	//3.	코드복사 버튼을 누를시 
	function copyHobong(){
		//alert("호봉복사 버튼을 누름");
	}
	//4.	코드 설정 버튼을 누를시
	function updateCodeList(){
		//alert("코드설정 버튼을 누름");
	}
	//코드추가 버튼을 누를시-(1)-2
	function createNewRowData() {
	      var newData = {
	    		 startDate :"",
	    		 endDate : "",
	    		 status:"insert"
	      };
	      return newData;
	}
	//코드추가 버튼을 누를시-(1)-1
	function addDateGridRow(){
		var newItem = createNewRowData();
		gridOptions2.api.updateRowData({
	         add : [ newItem ]
	     });
	     getRowData();
	}
	//코드추가 버튼을 누를시-(2)
	function getRowData() {
	      addrowData = [];
	      gridOptions2.api.forEachNode(function(node) {
	         addrowData.push(node.data);
	      });
	      
	}
	//코드삭제 버튼을 누를시-(1)
	   function deleteDateGridRow() {
			//console.log("@@");
	      	var selectedData = gridOptions2.api.getSelectedRows();
	      	var selectedData0 = selectedData[0];
	      	if (selectedData0.status != "delete") {
	         	selectedData0.status = 'delete'
	      	}
	      	gridOptions2.api.updateRowData({
	         	update : selectedData
	      	});
	      	//console.log('delete Data:');
	      	//console.log(selectedData0);
	      	
	      	selectedData.forEach(function (selectedData, index) { 
	            gridOptions2.api.applyTransaction({ remove: [selectedData] });
	        });
	      	//addrowData.pop(selectedData);
	      	//getRowData();
	   }
		// 저장 버튼을 눌렀을 때 실행되는 함수 
		   function saveDateGridRow() {
			console.log(1);
			var sendData=[];
		      if (addrowData != null) {
		    	  console.log(2);
		    	  var sendData = JSON.stringify(addrowData[0]);
		         //console.log("111111"+sendData);
		         console.log("111111"+addrowData[0].startDate);
		      } else {
		    	  console.log(3);
		    	  var sendData = JSON.stringify(detailCodeList);
		          sendData=sendData.substring(1,sendData.length-1);
		          
		          //sendData=sendData.substring(sendData.length,1);
		          //sendData = detailCodeList+"";
		         // console.log("44444"+addrowData);
		          //console.log("212121"+detailCodeList);
		        console.log("sendData===="+sendData);
		      }
				
		      $.ajax({
		         url : "${pageContext.request.contextPath}/base/payCheck.do",
		         data : {
		            "method" : "createHobongList",
		            "sendData" : addrowData[0].startDate
		         },
		         dataType : "json",
		         success : function(sendData) {
		            //console.log(sendData);
		            if (sendData.errorCode < 0) {
		               alert("저장에 실패했습니다");
		            } else {		            		
		            	addrowData=[];		            	
		                alert("저장되었습니다");
		            }
		            location.reload();
		         }
		      });
		      
		   }
		
	function showPayDateList() {
		$.ajax({
			url : '${pageContext.request.contextPath}/base/payCheck.do',
			data : {
				"method" : "payCheckList"
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
				//payPosition = data.payPosition;
				//gridOptions1.api.setRowData(data.payPosition);
				//console.log(data.payDateList);
				payDateList = data.payDateList;
				gridOptions2.api.setRowData(data.payDateList);
				//payTableList = data.payTableList;
				//gridOptions3.api.setRowData(data.payTableList);
			}
		});
	}
	
	
	//1. 대상직급
	function findPayPositionGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Code",
			field : "positionCode",
			width : 100
		}, {
			headerName : "Position",
			field : "positionName"
		} ];
		gridOptions1 = {
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
					'text-align' : 'center'
				};
			},
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			},
			onCellClicked : function(node) {

				//companyList = [];
				/*for(value of codeList){
				    console.log(value);
				}
				 */
				//console.log(node.data);
				v_positionCode = node.data.positionCode;
				v_positionName = node.data.positionName;
				v_startDate = node.data.startDay;
				//console.log(positionCode);
				//console.log(startDate);
				
				$.ajax({
					
							url : '${pageContext.request.contextPath}/base/payCheck.do',
							data : {
								"method" : "findHobongTable",
								"positionCode" : v_positionCode,
								"positionName" : v_positionName,
								"startDate" : v_startDate
								
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
								$("#add_btn").css("visibility", "visible");					
								$("#impression_btn").css("visibility", "visible");
								$("#copy_btn").css("visibility", "visible");
								$("#update_btn").css("visibility", "visible");
								finaHobongTableList = [];
								//받아온 데이터를 배열에 저장
								finaHobongTableList = data.finaHobongTableList;
								gridOptions3.api.setRowData(data.finaHobongTableList);
								
							}
						});
			}
		};
		payPosition_grid = document.querySelector('#payPosition_grid');
		new agGrid.Grid(payPosition_grid, gridOptions1);
	}

	
	//3. 호봉 테이블 
	function findPayTableGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Hobong",
			field : "hobong",
			width : 100,
			
			
		}, {
			headerName : "Salary",
			field : "basicSalary",
			cellClass: 'cell-right', 
			valueFormatter: (params) => {return Number(params.value).toLocaleString()}
		},{
			headerName : "Sum",
			field : "sum",
			cellClass: 'cell-right', 
			valueFormatter: (params) => {return Number(params.value).toLocaleString()}
		},{
			headerName : "Status",
			field : "status"
		} ];
		gridOptions3 = {
				columnDefs : columnDefs,
				rowSelection : 'single', //row는 하나만 선택 가능
				 // 정의하지 않은 컬럼은 자동으로 설정
				pagination : true, // 페이저
				paginationPageSize : 20, // 페이저에 보여줄 row의 수
				onGridReady : function(event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
					event.api.sizeColumnsToFit();
				},
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
							background : '#FFE08C'
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
				onCellEditingStarted : function(event) {
					//detailCodeList.pop(event.data);
					
	                console.log('@@cellEditingStarted');
	         
	                //console.log(event.data);
	                if (event.data.status == "normal") {
	                    event.data.status = "update"
	                 }
	                //console.log("22"+event.data);
	                
	             },
	             onCellEditingStopped : function(event) {
	                //console.log('cellEditingStopped');
	               // console.log(event.data.status);
	                //console.log(event.data);
	                /*if (event.data.status == "normal") {
	                   event.data.status = "update"
	                }*/
	                //console.log("##"+event.data.status);
	                //detailCodeList.push(event.data);
	             }
		};
		payTable_grid = document.querySelector('#payTable_grid');
		new agGrid.Grid(payTable_grid, gridOptions3);
	}
	//2. 호봉 이력 
	function findPayDateGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Start Date",
			field : "startDate",
			width : 100
		}, {
			headerName : "End Date",
			field : "endDate"
		}, {
			headerName : "Status",
			field : "status"
		} ];
		gridOptions2 = {
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
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			},
			onCellClicked : function(node) {

				//companyList = [];
				
				//console.log(node.data);
				var startDate = node.data.startDate;
				//console.log(node.data);
				//console.log(companyCode);

				$.ajax({
							url : '${pageContext.request.contextPath}/base/payCheck.do',
							data : {
								"method" : "findHobongPositionList",
								"startDate" : startDate
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
								//console.log(data.HobongPositionList);
								//배열초기화
								hobongPositionList = [];
								//받아온 데이터를 배열에 저장
								hobongPositionList = data.HobongPositionList;		
								//console.log("hobongPositionList======"+hobongPositionList.startDate);
								gridOptions1.api.setRowData(data.HobongPositionList);							
							}
						});
			}
		};
		payDate_grid = document.querySelector('#payDate_grid');
		new agGrid.Grid(payDate_grid, gridOptions2);
	}
	
</script>
</head>

<body>

	<h4>Sign up for the salary table</h4>
	<div>
		<div style="text-align: right;">
			<input type="button" id="add_btn" style="visibility: hidden" value="Registration"class="btn btn-Light"> 
			<input type="button" id="impression_btn" style="visibility: hidden" value="Impression" class="btn btn-Light"> 
			<!-- <input type="button" id="copy_btn" style="visibility: hidden" value="Payroll radiation" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="update_btn" style="visibility: hidden" value="Code Settings" class="btn btn-Light shadow-sm btnsize"> -->
		</div>
	</div>
	<hr>
	<div class="col-12" style="float: left">
		<div id="payPosition_tab">			
			<div>
				<div>
					<div style="width: 400px">
						<span>▶  Target position</span>
					</div>
					<br/>
					<div id="payPosition_grid" style="height: 400px; width: 400px" class=" ag-theme-balham">
					</div>
					<br/>
					<div style="width: 400px">
						<span>▶ Salary table history</span>
					</div>
					<div style="text-align: right;">			
							<input type="button" id="date_add" value="Add" class="btn btn-Light">
							<input type="button" id="date_delete" value="Delete" class="btn btn-Light">
							<input type="button" id="date_save" value="Save" class="btn btn-Light">
					</div>
					
					<br/>
					<div>
						<div id="payDate_grid" style="height: 200px; width: 400px" class=" ag-theme-balham"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="float: right">
		<div>
			<div id="payTable_tab">
				<div style="width: 400px">
					<span>▶  Salary table</span>
				</div>
				<br/>
				<div>
					<div id="payTable_grid" style="height: 500px; width: 800px" class=" ag-theme-balham"></div>
				</div>
			</div>
		</div>				
	</div>		
</body>
</html>