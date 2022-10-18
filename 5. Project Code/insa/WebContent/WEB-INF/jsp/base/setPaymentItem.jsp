<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지급공제항목등록</title>
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
}

.info {
	background-color: #FAED7D;
}

.info:hover {
	background: #FAF4C0;
}
</style>
<script>
	var paymentItemList = [];
	var gridOptions;
	var rowData;

	$(document).ready(function() {
		
		//버튼 이벤트
		$('input:button').hover(function() {
			$(this).css("background-color", "#D8D8D8");
		}, function() {
			$(this).css("background-color", "");
		});		
		showPaymentItemList();
		$("#search").click(searchFunction);		
		//findgrid();
		$('#b1').trigger("click");
		  
		
	});
	function searchFunction(){
		var salaryClassification= $('#salaryClassification').val();
		var paymentClassification= $('#paymentClassification').val();
		var year= $('#year').val();
		console.log(salaryClassification);
		console.log(paymentClassification);
		console.log(year);
		 $.ajax({
			url : '${pageContext.request.contextPath}/base/findPaymentItemList.do',
			data : {
				method : "findPaymentItemList",
				salaryClassification:salaryClassification,
				paymentClassification:paymentClassification,					
				year : year
				
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
				paymentItemList = data.paymentItemList; 
				gridOptions.api.setRowData(data.paymentItemList);
			}
		});	 
	}
	
	
	
	var selectedRow;


	
	//	1. 왼쪽 코드목록 그리드 띄우는 함수 
	function showPaymentItemList(){
		rowData = [];
		var columnDefs = [{
			headerName : "코드",
			field : "code1",
			width : 10
		}, {
			headerName : "지급항목명",
			field : "code1Name"
		}
		];
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

				//companyList = [];
				/*for(value of codeList){
					console.log(value);
				}
				 */
				//console.log(node.data);
				var companyCode = node.data.companyCode;
				//console.log(node.data);
				//console.log(companyCode);

				$.ajax({
							url : '${pageContext.request.contextPath}/system/findCompany.do',
							data : {
								"method" : "companyBasicRegist",
								"code" : companyCode
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
								companyBasicList=[];
								//받아온 데이터를 배열에 저장
								companyBasicList = data.companyBasic;
								//empResidentList=data.list2;
								

								//initField();// 전역변수 초기화
								
								// 상세정보 칸 초기화
								clearEmpInfo(); 

								//setAllEmptyBean(data);

								//selectedEmpBean = $.extend(true, {}, data.list); // 취소버튼을 위한 임시 저장공간에 딥카피
								//updatedEmpBean = $.extend(true, {}, data.list); // 변경된 내용이 들어갈 공간에 딥카피
								// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임

								/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름 */
								showCompanyBasicList();
								//showResidentInfo();
							}
						});

			}
		};
		paymentItem_grid = document.querySelector('#paymentItem_grid');
		new agGrid.Grid(paymentItem_grid, gridOptions);
	}
</script>
</head>

<body>

	<h4>지급공제항목등록</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="" value="전년복사"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id=""  value="일괄등록" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="deadline" value="마감" class="btn btn-Light shadow-sm btnsize">
			<input type="button" id="cancelDeadline" value="마감취소" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	<hr>
	<div>
		<span style="margin-left:50px">급여구분</span>
		<span>
			<select id="salaryClassification" style="width: 200px; height: 20px;">
				<option value="0">급여</option>
				<option value="1">상여</option>								
			</select>								
		</span>
		<span style="margin-left:50px">지급/공제구분</span>
		<span>
			<select id="paymentClassification" style="width: 200px; height: 20px;">
				<option value="0">지급</option>
				<option value="1">공제</option>								
			</select>								
		</span>
		<span style="margin-left:50px">귀속연도</span>
		<span>
			<input type="text" style="width: 150px; height: 20px;" id="year">
		</span>
		<span  style="margin-left:30px">
			<input type="button" id="search" value="조회"class="btn btn-Light shadow-sm btnsize"> 
		</span>	
	</div>
	<br/>
	<br/>
	
	<div>
		<div>
			<span style="margin-left:215px">
				<input type="button" id="add" value="추가"class="btn btn-Light shadow-sm btnsize">
				<input type="button" id="delete" value="삭제"class="btn btn-Light shadow-sm btnsize"> 
			</span>	
		</div>
		<br/>				
		<div id="paymentItem_tab">
			<div style="float: left">
				<div id="paymentItem_grid" style="height: 500px; width: 400px" class="ag-theme-balham"></div>
			</div>						
		</div>
	</div>
	<!-- 2.	오른쪽  -->
	<div>
		<div style="float: right">
			<div style="width: 1000px; height: 1000px;">
				<div class="tab">
		        	<button class="tablinks" id="b1" onclick="openCity(event, '0')">지급/공제항목설정</button>
		        	<button class="tablinks" onclick="openCity(event, '1')">귀속년월</button>		        	
		    	</div>
		    	<!-- 기본등록사항-->
		    	<div id="0" class="tabcontent">		    	
		   			<!-- 1번째줄-->
		   			<br/>
		            <div style="width: 900px; height: 250px;">
		      			<div>
		            		<span style="margin-left:30px">
								<font>과세구분</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="1" style="width: 200px; height: 20px;">
									<option value="0">과세</option>
									<option value="1">비과세</option>	
									<option value="2">소득제외</option>								
								</select>
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>감면여부</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="2" style="width: 200px; height: 20px;">
									<option value="0">부</option>
									<option value="1">여</option>								
								</select>
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>수습적용</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="3" style="width: 200px; height: 20px;">
									<option value="0">환경등록적용</option>
									<option value="1">정상적용</option>
									<option value="2">일할적용</option>			
									<option value="3">미지급</option>								
								</select>
							</span>
							<span style="margin-left:100px">
								<font>입퇴사자적용</font>
							</span>
		            		<span style="margin-left:55px">	
								<select id="4" style="width: 200px; height: 20px;">
									<option value="0">환경등록적용</option>
									<option value="1">정상적용</option>
									<option value="2">일할적용</option>			
									<option value="3">미지급</option>
									<option value="4">미지급(입사자만)</option>	
									<option value="5">미지급(퇴직자만)</option>																	
								</select>
							</span>
		            	</div>		    
		            	<div>
		            		<span style="margin-left:30px">
								<font>월정급여</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="5" style="width: 200px; height: 20px;">
									<option value="0">포함</option>
									<option value="1">제외</option>														
								</select>
							</span>
							<span style="margin-left:100px">
								<font>고용보험(임금)</font>
							</span>
		            		<span style="margin-left:44px">	
								<select id="6" style="width: 200px; height: 20px;">
									<option value="0">포함</option>
									<option value="1">비포함</option>																	
								</select>
							</span>
		            	</div>
		            	<hr>
		            	<div>
		            		<span style="margin-left:30px">
								<font>분류여부</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="7" style="width: 200px; height: 20px;">
									<option value="0">무분류</option>
									<option value="1">분류</option>	
									<option value="2">제외조건</option>								
								</select>
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>계산구분</font>
							</span>
		            		<span style="margin-left:87px">	
								<select id="8" style="width: 200px; height: 20px;">
									<option value="0">금액</option>
									<option value="1">계산</option>								
								</select>
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>휴직자적용</font>
							</span>
		            		<span style="margin-left:70px">	
								<select id="1" style="width: 200px; height: 20px;">
									<option value="0">휴직자계산적용</option>
									<option value="1">정상적용</option>								
								</select>
							</span>
		            	</div>	  
		            </div>     
		        </div>
		         
		        <div id="1" class="tabcontent">
		        </div>  
		               
		     </div>
		 </div> 
	</div>
	<script>
        function openCity(evt, number) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(number).style.display = "block";
            evt.currentTarget.className += " active";
        }
    </script>
	
				
		
	
</body>
</html>