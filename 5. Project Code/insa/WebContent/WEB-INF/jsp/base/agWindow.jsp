<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CODE</title>

<style>
/* 레이아웃 */
.container {
  overflow:hidden;
  height:auto;
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 45px 45px 55px 360px;
  gap: 0px 0px;
  grid-auto-flow: row;
  grid-template-areas:
    "title"
    "search"
    "buttons"
    "grid";
}
.title { grid-area: title; padding: 10px; }
.search { grid-area: search; padding: 10px;   }
.buttons { grid-area: buttons; padding: 10px;  }
.grid { grid-area: grid; padding: 10px;  }


/* 텍스트박스 */
.css-input {
	 size : 200;
     padding: 2px;
     font-size: 14px;
     border-width: 1px;
     border-color: #25b299;
     background-color: #FFFFFF;
     color: #000000;
     border-style: solid;
     border-radius: 0px;
}
 .css-input:focus {
     outline:none;
}

/* 버튼 */
.myButton {
	box-shadow:inset 0px -3px 7px 0px #29bbff;
	background:linear-gradient(to bottom, #2dabf9 5%, #0688fa 100%);
	background-color:#2dabf9;
	border-radius:3px;
	border:1px solid #0b0e07;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:15px;
	padding:6px 17px;
	text-decoration:none;
	text-shadow:0px 1px 0px #263666;
}
.myButton:hover {
	background:linear-gradient(to bottom, #0688fa 5%, #2dabf9 100%);
	background-color:#0688fa;
}
.myButton:active {
	position:relative;
	top:1px;
}


</style>

<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>

var empInfoList;
var empInfo;
var EmpListGridOptions={};

$(document).ready(function(){
	findEmpInfo();
	$("#Search").click(findEmpInfo); /* 조회버튼 */
	$("#Check").click(checkInfo); /* 확인버튼 */
	$("#Cancel").click(closeWindow); /* 취소버튼 */
});

/* 취소버튼 누르면 새창이 닫힘 */
function closeWindow(){
	window.close();
}


function findEmpInfo(){ //실행하자마자 사원들의 데이터를 가져옴 
	$.ajax({
		url:'${pageContext.request.contextPath}/salary/fullTimePayday.do',
		data:{
			"method" : "paydayList",
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
			empInfoList = data.list;
			console.log(empInfoList);
			showEmpListGrid();
		}
	});
}

function showEmpListGrid() { //가져온 사원들의 데이터를 그리드에 출력함
	var columnDefs = [ { headerName : "순번", field : "ord" , checkboxSelection: true}, 
					   { headerName : "지급일자", field : "payment_date" }, 
					   { headerName : "동시발행", field : "smltn_issue" }, 
					   { headerName : "급여구분", field : "salary_type" }, 
					   { headerName : "비고", field : "remarks" }, 
	];
	EmpListGridOptions = {
		columnDefs : columnDefs,
		rowData : empInfoList,
		defaultColDef : { editable : false, width : 10 },
		rowSelection : 'single', 
		enableColResize : true,
		enableSorting : true,
		enableFilter : true,
		enableRangeSelection : true,
		suppressRowClickSelection : false,
		animateRows : true,
		suppressHorizontalScroll : true,
		localeText : { noRowsToShow : "There's no result." },
		
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
		onGridReady : function(event) {
			event.api.sizeColumnsToFit();
		},
		
		onGridSizeChanged : function(event) {
			event.api.sizeColumnsToFit();
		}
	};
	var eGridDiv = document.querySelector('#empSelect_grid');
	new agGrid.Grid(eGridDiv, EmpListGridOptions);
}

function checkInfo(){
	empInfo = EmpListGridOptions.api.getSelectedRows();
	console.log(empInfo);
	var ord = empInfo[0].ord;
	
	var payment_date = empInfo[0].payment_date;
	var salary_type = empInfo[0].salary_type;
	var smltn_issue = empInfo[0].smltn_issue;

	payment_date = payment_date.replaceAll("-", "/");
	opener.document.getElementById("payment_ord").value = ord;
 	opener.document.getElementById("payment_value").value = payment_date + " " + salary_type + " " + smltn_issue;
 	window.close();
}



</script>
</head>
<body>
<div class="container">
  <div class="title" style="border: 1px solid black; position: relative;">
   급/상여지급일자
  </div>
  <div class="search" style="border: 1px solid black; position: relative;">
  검색
  <input type="text" class="css-input" />
  </div>
  <div class="buttons" style="border: 1px solid black; position: relative;">
	  <div style = "position: absolute; right:0px;">
	  	<a href="#" class="myButton" id="Search">조회</a>
		<a href="#" class="myButton" id="Check">확인</a>
		<a href="#" class="myButton" id="Cancel">취소</a>
	  </div>
  </div>
  <div class="grid" style="border: 1px solid black; position: relative;">
  <div id="empSelect_grid" class="ag-theme-balham" style="width:100%; height:100%;"></div>
  </div>
</div>
</body>
</html>