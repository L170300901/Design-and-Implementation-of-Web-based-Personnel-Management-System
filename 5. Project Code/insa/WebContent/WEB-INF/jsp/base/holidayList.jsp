<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>휴일목록</title>
<script>
   // 휴일목록을 담을 배열 
   var holidayList = [];
   var emptyInfo = {};
   var lastId = 0;
   var addrowData;
   $(document).ready(function() {

      // 휴일목록 탭 등록   
      //$("#holidayListTabs").tabs()
      $("#add_Btn").click(addGridRow);
      $("#remove_Btn").click(removeGirdRow);
      $("#save_Btn").click(saveGridRow);

      $.ajax({
         url : "${pageContext.request.contextPath}/base/holidayList.do",
         dataType : "json",
         data : {
            method : "findHolidayList"
         },
         success : function(data) {
        	 //console.log(data.holidayList);
        	// console.log(data.emptyHoilday);
            holidayList = data.holidayList;
            emptyInfo = data.emptyHoilday;
            var columnDefs = [ {
               headerName : "Apply Day",
               field : "applyDay",
               width : 40
            }, {
               headerName : "Holiday name",
               field : "holidayName"
            }, {
               headerName : "Note",
               field : "note",
               width : 40
           
            }, {
               headerName : "State",
               field : "State",
               width : 40
            } ];
            gridOptions = {
               columnDefs : columnDefs,
               rowData : holidayList,
               defaultColDef : {
                  editable : true,
                  width : 100
               },
	            // 페이저
	   			pagination : true,
	
	   			// 페이저에 보여줄 row의 수
	   			paginationPageSize : 20,

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
                  console.log(event.data.status);
                  console.log(event.data);
                  if (event.data.status == "normal") {
                     event.data.status = "update"
                  }
                  console.log(event.data.status);
               },
            };

            var eGridDiv = document.querySelector('#holidayListGrid');
            new agGrid.Grid(eGridDiv, gridOptions);
         }
      });
   });
	
	 //검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
 
   // 그리드에 행 추가하는 함수
   function createNewRowData() {
      var newData = {
         applyDay : "",
         holidayName : "",
         note : "",
         status : "insert"
      };
      return newData;
   }

   function addGridRow() {
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
   function removeGirdRow() {
      var selectedData = gridOptions.api.getSelectedRows();
      var selectedData0 = selectedData[0];
      if (selectedData0.status == "normal") {
         selectedData0.status = 'delete'
      }
      gridOptions.api.updateRowData({
         update : selectedData
      });
      console.log('delete Data:');
      console.log(selectedData0.status);
      console.log(selectedData);
      getRowData();
   }

   /* 저장 버튼을 눌렀을 때 실행되는 함수 */
   function saveGridRow() {
      if (addrowData != null) {
         var sendData = JSON.stringify(addrowData);
         alert(sendData);
      } else {
         var sendData = JSON.stringify(holidayList);
         alert(sendData);
      }

      $('#holidayListGrid').children().remove();
      $.ajax({
         url : "${pageContext.request.contextPath}/base/holidayList.do",
         data : {
            "method" : "regitCodeList",
            "sendData" : sendData
         },
         dataType : "json",
         success : function(sendData) {
            console.log(sendData);
            if (sendData.errorCode < 0) {
               alert("Failed to save");
            } else {
               alert("Saved it");
            }
            var eGridDiv = document.querySelector('#holidayListGrid');
            new agGrid.Grid(eGridDiv, gridOptions);
            console.log(sendData);
            location.reload();
         }
      });
   }
</script>
</head>
<body>
	<h4>Holiday inquiry</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="add_Btn" value="Add"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="remove_Btn"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="save_Btn" value="Save" class="btn btn-Light shadow-sm btnsize">
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
							<div id="holidayListGrid" style="height: 600px; width: 1400px" class="ag-theme-balham"></div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>