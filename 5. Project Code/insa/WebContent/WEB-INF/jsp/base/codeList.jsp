<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인사기초코드등록</title>

<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">

<script>
	var codeList = [];
	var detailCodeList = [];
	var gridOptions; 
	var gridOptions2;
	var rowData;
	var codeList_grid;
	var addrowData;
	
	$(document).ready(function() {
		 //버튼 이벤트
		 $('input:button').hover(function() {
			 $(this).css("background-color","#D8D8D8");
			}, function(){
			$(this).css("background-color","");
		 });
		
		$("#find").click(findgrid);
		$("#findCode").click(findCode);
		$("#add").click(addGridRow);
		$("#delete").click(removeGirdRow);
		$("#save").click(saveGridRow);
		
		showCodeGrid();
		showDetailCodeListGrid();		
	});
	
	
	//검색기능
	function onQuickFilterChanged() {
	     gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	}
	function createNewRowData() {
	      var newData = {
	    		 detailCodeNumber : "",
	    		 codeNumber : codeList[0].codeNumber,
	    		 detailCodeName : "",
	    		 detailCodeNameusing:"",
	    		 status:"insert"
	      };
	      return newData;
	   }
	//코드추가 버튼을 누를시-(1)
	function addGridRow(){
		//sconsole.log(api.getModel().gridOptionsWrapper.gridOptions2.rowData);
			//console.log("@@@@@"+codeList[0]);
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
	    	 //console.log("node"+node.data);
	         addrowData.push(node.data);
	      });
	      
	   }
	//코드삭제 버튼을 누를시-(1)
   function removeGirdRow() {
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
	   function saveGridRow() {
		//console.log(1);
		var sendData=[];
	      if (addrowData != null) {
	    	  console.log(2);
	    	  var sendData = JSON.stringify(addrowData);
	         //console.log("111111"+sendData);
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
	         url : "${pageContext.request.contextPath}/base/codeList.do",
	         data : {
	            "method" : "saveCodeList",
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
	      
	   }
	
	//1. 왼쪽 그리드 출력
	function findgrid() {
		$.ajax({
			url : '${pageContext.request.contextPath}/base/codeList.do',
			data : {
				"method" : "outPutList",
				"outPut" : $("#outPut").val()
			
			},
			dataType : "json",
			success : function(data) {
				//console.log(data);
				//console.log(data.detailCodeList);
				if (data.errorCode < 0) {
					var str = " An internal server error has occurred.\n";
					str += "Please contact the administrator.\n";
					str += "Location : an error : " + $(this).attr("id");
					str += "Error messages : " + data.errorMsg;
					alert(str);
					return;
				}
				
				//codeList = data.detailCodeList;
				//showCodeGrid();
				
				
				gridOptions.api.setRowData(data.findoutPutList);
			}
		});
	}
	
	// 2. 왼쪽 코드목록 그리드 띄우는 함수 
	function showCodeGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Code",
			field : "codeNumber",
			width : 50
		}, {
			headerName : "Name",
			field : "codeName"
		}, {
			headerName : "Modification",
			field : "modifiable",
			width : 50
		}, {
			headerName : "Status",
			field : "status",
			width : 50
		} ];
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
				
				codeList = [];
				detailCodeList=[];
				codeList.push(node.data);
				/*for(value of codeList){
					console.log(value);
				}
				*/
				//console.log(node.data);
				
				var codeNumber = node.data.codeNumber;
				//console.log(codeNumber);

				$.ajax({
							url : "${pageContext.request.contextPath}/base/codeList.do",
							data : {
								"method" : "detailCodelist",
								"code" : codeNumber
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
								//console.log(data.detailCodeList);
								detailCodeList.push(data.detailCodeList);
								
								//showDetailCodeListGrid();
								gridOptions2.api.setRowData(data.detailCodeList);
							}
						});
			}

		};
		codeList_grid = document.querySelector('#codeList_grid');
		new agGrid.Grid(codeList_grid, gridOptions);
		//gridOptions.api.setRowData([]);
	}
	
	
	//상세 코드 그리드 띄우기
	
	function showDetailCodeListGrid() {

		var columnDefs = [ {
			headerName : "Detail code",
			field : "detailCodeNumber",
			width : 50
		}, {
			headerName : "Code",
			field : "codeNumber",
			width : 50
		}, {
			headerName : "Detail code name",
			field : "detailCodeName"
		}, {
			headerName : "Modification",
			field : "detailCodeNameusing",
			width : 50
		}, {
			headerName : "Status",
			field : "status",
			width : 50
		} ];
		gridOptions2 = {
			columnDefs : columnDefs,
			rowSelection : 'single', //row는 하나만 선택 가능
			 // 정의하지 않은 컬럼은 자동으로 설정
			pagination : true, // 페이저
			paginationPageSize : 20, // 페이저에 보여줄 row의 수
			onGridReady : function(event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
				event.api.sizeColumnsToFit();
			},
			rowData : detailCodeList,
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
			onCellEditingStarted : function(event) {
				//detailCodeList.pop(event.data);
				
                //console.log('@@cellEditingStarted');
         
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
             },
			getRowHeight : function(param) {
				if (param.node.rowPinned) {
					return 30;
				}
				return 24;
			}
			
		}
		//$('#detailCodeList_grid').children().remove();
		detailCodeList_grid = document.querySelector('#detailCodeList_grid');
		new agGrid.Grid(detailCodeList_grid, gridOptions2);

	}
</script>
</head>

<body>
	<h4>Personnel basic code registration</h4>
	<hr>

	<div class="col-12">
		<div class="card">
			<div class="card-body">
				<div id="findtab1">
					Output classification &nbsp 
					<select id="outPut">
						<option value="0">0. Entire</option>
						<option value="1">1. Personnel(H,R)</option>
						<option value="2">2. Absenteeism and tardiness(T)</option>
						<option value="3">3. Salary(P)</option>
						<option value="4">4. Social insurance(I)</option>
						<option value="5">5. Social group(G)</option>
						<option value="6">6. Business/Other Income(B)</option>
						<option value="7">7. Other(E)</option>
						<option value="8">8. System Setup(S)</option>
					</select> &nbsp <input type="button" value="Inquiry" id="find"> 
					<input type="button" value="Code table" id="findCode">

					<div style="text-align: right;">
						Search &nbsp&nbsp 
						<input name="searchKeyword" type="text"oninput="onQuickFilterChanged()" id="quickFilter"placeholder="Search">
					</div>
					<div id="codeList_tab">
						<br />
						<div>
							<div style="text-align: right;">
								<input type="button" id="add" value="Add"class="btn btn-Light shadow-sm btnsize"> 
								<input type="button" id="delete"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
								<input type="button" id="showPDF" value="PDF" class="btn btn-Light shadow-sm btnsize"> 
								<input type="button" id="save" value="Save" class="btn btn-Light shadow-sm btnsize">
							</div>
						</div>
						<table style="height: 400px; width: auto">
							<tr>
								<td>
									<div id="codeList_grid"style="height: 600px; width: 740px; float: left;"class="ag-theme-balham"></div>

								</td>
								<td>&nbsp&nbsp&nbsp&nbsp</td>
								<td>
									<div id="detailCodeList_grid"style="height: 600px; width: 740px; display: inline-block; float: right;"class="ag-theme-balham"></div>
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