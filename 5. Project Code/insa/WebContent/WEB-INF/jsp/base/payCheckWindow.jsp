<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CODE</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.min.css">
<script src="${pageContext.request.contextPath}/script/jquery/jquery-3.3.1.min.js"></script>
<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-alpine.css">
<script>

</script>
</head>
<body>
		<div style="height: 200px;">
		<br/>
        	<div id="myGrid" style="height: 200px;width:800px;" class="ag-theme-alpine"></div>
        <br/>
	        <div>
	        	<center>
	        		<input type="button" value="Application" onclick="saveFunc()">
	        		<input type="button" value="Cancellation" onclick="cancelFunc()">
	        	</center>       
	        </div>
		</div>
		
		        
        <script type="text/javascript" charset="utf-8">
	        var v_positionCode;
			var v_positionName;
			var v_startDate;
			v_positionCode="${param.positionCode}";
			v_positionName="${param.positionName}";
			v_startDate="${param.startDate}";
            // specify the columns
            var rowData = [];
            var memberData = { member: [] };
            var processData = { insert: [], delete: [], update: [] };
            let existSize = 0;
			
            const columnDefs = [
            	{ checkboxSelection: true, editable: false , width:30},
                { headerName: "Hobong", field: "hobong", width:150},
                { headerName: "Initial Value", field: "initialValue", width:200, cellClass: 'cell-right', valueFormatter: (params) => {return Number(params.value).toLocaleString()} },
                { headerName: "Increase Amount", field: "increaseAmount", width:200, cellClass: 'cell-right', valueFormatter: (params) => {return Number(params.value).toLocaleString()}},
                { headerName: "Status", field: "status", width:70}
              
            ];
            const gridOptions = {
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
		
            };
 
            // lookup the container we want the Grid to use
            const eGridDiv = document.querySelector('#myGrid');
            // create the grid passing in the div to use together with the columns &amp; data we want to use
            new agGrid.Grid(eGridDiv, gridOptions);
            memberData.member.push({ hobong: "Base pay", initialValue: "", increaseAmount: "",status: "insert" });
            gridOptions.api.setRowData(memberData.member);
            
            function saveFunc(){
 
            	//let selectedInfo = gridOptions.api.getSelectedNodes();
            	//sconsole.log(v_startDate);
            	//console.log(memberData.member[0].increaseAmount);
            	//console.log(memberData.member[0].initialValue);
            	$.ajax({
    		         url : "${pageContext.request.contextPath}/base/payCheck.do",
    		         data : {
    		            "method" : "payCheckResist",
    		            "increaseAmount" : memberData.member[0].increaseAmount,
    		            "initialValue":memberData.member[0].initialValue,
    		            "startDate":v_startDate,
    		            "positionCode": v_positionCode
    		 
    		         },
    		         dataType : "json",
    		         success : function(sendData) {
    		            if (sendData.errorCode < 0) {
    		               alert("Failed to save");
    		            } else {		            		            	
    		                alert("Saved");
    		                opener.location.reload();
    		                window.close();
    		            }
    		         }
    		     });
            	
            }

        </script> 
        
</body> 
</html>