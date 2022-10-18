<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회사등록</title>
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
	var companyList = [];
	var companyBasicList = [];
	//var empResidentList=[];
	var gridOptions;
	var rowData;
	//var empList_grid;
	var addrowData;
	//var selectedEmpBean={};
	
	$(document).ready(function() {
		
		//버튼 이벤트
		$('input:button').hover(function() {
			$(this).css("background-color", "#D8D8D8");
		}, function() {
			$(this).css("background-color", "");
		});

		/* DatePicker  */
		$('#from').val(today.substring(0, 8) + '01'); // 오늘이 포함된 해당 달의 첫번째 날, 1월달이면 1월 1일로 세팅. 	2020-xx 총 7자리
		$('#to').val(today.substring(0, 10)); // 오늘 날짜의 년-월-일.
		
		/* // 돋보기 버튼을 클릭시 클릭
		$('#lastSchoolC').click(function() { 
			getCode("lastSchool");
		}) */
		
		showCopanyList();
		findgrid();
		$('#b1').trigger("click");
	});
	
	var selectedRow;
	/* 날짜 */
	var date = new Date();
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1 > 9 ? date.getMonth() : '0'
			+ (date.getMonth() + 1)).toString(); // getMonth()는 0~9까지
	// 10월 이상이면 true
	var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate(); // getDate()는 1~31 까지 
	var today = year + "-" + month + "-" + day;

	//검색기능
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
	

	//출력구분하기 
	function findgrid() {
		$.ajax({
			url : '${pageContext.request.contextPath}/system/findCompany.do',
			data : {
				"method" : "companyList"
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
				companyList = data.companyList; 
				gridOptions.api.setRowData(data.companyList);
			}
		});
	}
	
	//	1. 왼쪽 코드목록 그리드 띄우는 함수 
	function showCopanyList(){
		rowData = [];
		var columnDefs = [ {
			headerName : "Code",
			field : "companyCode",
			width : 100
		}, {
			headerName : "Company came",
			field : "companyName"
		}, {
			headerName : "Sortation",
			field : "companySort"
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
		companyFind_grid = document.querySelector('#companyFind_grid');
		new agGrid.Grid(companyFind_grid, gridOptions);
	}
	/* 현재 표시된 모든 정보를 비우는 함수 */
	function clearEmpInfo() {
		//console.log("오른쪽 text 값 초기화");
		// 찾았던 사진을 기본 사진으로 되돌린다
		/* $("#profileImg")
				.attr("src",
						"${pageContext.request.contextPath }/profile/profile.png"); */
		$("input:text").each(function() {
			$(this).val(""); //모든 input text타입의 value값을 비운다
		});
	}
	//오른쪽에 회사 기본등록사항 출력하는 함수
	function showCompanyBasicList(){
		$("#businessLicense").val(companyBasicList[0].businessLicense);
		$("#corporateRegistrate").val(companyBasicList[0].corporateRegistrate);
		$("#representativeName").val(companyBasicList[0].representativeName);
		$("#residentNumber").val(companyBasicList[0].residentNumber);
		$("#postNumber").val(companyBasicList[0].postNumber);
		$("#companyAddress").val(companyBasicList[0].companyAddress);
		$("#companyPost").val(companyBasicList[0].companyPost);
		$("#companyTel").val(companyBasicList[0].companyTel);
		$("#companyFax").val(companyBasicList[0].companyFax);
		$("#businessCondition").val(companyBasicList[0].businessCondition);
		$("#companyEvent").val(companyBasicList[0].companyEvent);
		
		//0.	jQuery - prop()으로 select 상태 값 변경
		var empForeignNumber;
		if (companyBasicList[0].empForeign == "Local") {
			empForeignNumber = 0; //내국인
		} else {
			empForeignNumber = 1; //외국인
		}
		$("#empForeign").val(empForeignNumber).prop("selected", true);
		
		//설립연월일, 개업연월일 
		var year = companyBasicList[0].openingDate.substr(0, 4);
		var month = companyBasicList[0].openingDate.substr(5, 2);
		var day = companyBasicList[0].openingDate.substr(8, 2);
		$("#openingDate").val(year + "-" + month + "-" + day);
		$("#establateDate").val(year + "-" + month + "-" + day);
		
		//회계년도 계산법
		var now = new Date();
		var nowYear = now.getFullYear();
		
		var newYear= (nowYear-year+1);
		$("#gisu").val(nowYear-year+1);
		$("#startDate").val(nowYear + "-01-01");
		$("#finishDate").val(nowYear + "-12-31");
	}
</script>
</head>

<body>

	<h4>Company registration</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="companyAdd" value="Add"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="companyDelete"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="companySave" value="Save" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	<hr>
	<div class="col-12" style="float: left">
		<div class="card">
			<div class="card-body">
				<div>				
					Company search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br/><br/>
					<div id="codeList_tab">
						<div>
							<div id="companyFind_grid" style="height: 500px; width: 500px" class="ag-theme-balham"></div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 2.	오른쪽  -->
	<div>
		<div style="float: right">
			<div style="width: 1000px; height: 1000px;">
				<div class="tab">
		        	<button class="tablinks" id="b1" onclick="openCity(event, '0')">Basic registration details</button>
		        	<button class="tablinks" onclick="openCity(event, '1')">Additional registration details</button>		        	
		    	</div>
		    	<!-- 기본등록사항-->
		    	<div id="0" class="tabcontent">
		    	
		   			<!-- 1번째줄-->
		   			<br/>
		            <div style="width: 900px; height: 200px;">
		            	<div>
		            		<span style="margin-left:30px">
								<font>Fiscal year</font>
							</span>
							<span style="margin-left:70px">
								<font>The</font>
							</span>
							<span>
								<input type="text" style="width: 30px; height: 20px;" id="gisu">
							</span>
							<span>
								<font>th</font>
							</span>
							<span style="margin-left:48px">									 	
								<input id="startDate" type="date" class="date" required style="width: 200px height:20px">																												 										 
							</span>
							<span style="margin-left:10px"> ~ 
							</span>
							<span style="margin-left:10px">									 	
								<input id="finishDate" type="date" class="date" required style="width: 200px height:20px">																												 										 
							</span>
		            	</div>
		            	<br/>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Business registration number</font>
							</span>
							<span style="margin-left:72px">
								<input type="text" style="width: 250px; height: 20px;" id="businessLicense">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Corporate registration number </font>
							</span>
							<span style="margin-left:67px">
								<input type="text" style="width: 250px; height: 20px;" id="corporateRegistrate">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Name of the representative</font>
							</span>
							<span style="margin-left:87.5px">
								<input type="text" style="width: 250px; height: 20px;" id="representativeName">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Foreigner status</font>
							</span>
		            		<span style="margin-left:166px">	
								<select id="empForeign" style="width: 140px; height: 20px;">
									<option value="0">Local</option>
									<option value="1">Foreigner</option>								
								</select>
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Resident registration number</font>
							</span>
							<span style="margin-left:76px">
								<input type="text" style="width: 250px; height: 20px;" id="residentNumber">
							</span>		
		            	</div>  
		            </div>
		            <!-- 2번째줄-->
		            <div style="width: 900px; height: 200px;">
		            	<div>
		            		<span style="margin-left:30px">
								<font>Postal code of the head office</font>
							</span>
							<span style="margin-left:71px">
								<input type="text" style="width: 95px; height: 20px;" id="postNumber">
							</span>
							<span>
								<button>
									<i class="w3-small-margin-left fa fa-search"></i>
								</button>
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>The address of the main store</font>
							</span>
							<span style="margin-left:69px">
								<input type="text" style="width: 500px; height: 20px;" id="companyAddress">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>The main branch</font>
							</span>
							<span style="margin-left:161px">
								<input type="text" style="width: 500px; height: 20px;" id="companyPost">
							</span>		
		            	</div>	
		            	<div>
		            		<span style="margin-left:30px">
								<font>Phone number of the head office</font>
							</span>
							<span style="margin-left:50px">
								<input type="text" style="width: 250px; height: 20px;" id="companyTel">
							</span>		
		            	</div>	  
		            	<div>
		            		<span style="margin-left:30px">
								<font>The main store's FAX</font>
							</span>
							<span style="margin-left:129px">
								<input type="text" style="width: 250px; height: 20px;" id="companyFax">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Up-tae</font>
							</span>
							<span style="margin-left:233px">
								<input type="text" style="width: 250px; height: 20px;" id="businessCondition">
							</span>		
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Event</font>
							</span>
							<span style="margin-left:241px">
								<input type="text" style="width: 250px; height: 20px;" id="companyEvent">
							</span>		
		            	</div>          	  							 
		            </div>
		            <!-- 3번째줄-->
		            <div style="width: 900px; height: 200px;">
		            	<div>
		            		<span style="margin-left:30px">
								<font>Date of establishment</font>
							</span>
							<span style="margin-left:127px">									 	
								<input id="establateDate" type="date" class="date" required">																												 										 
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Opening date</font>
							</span>
							<span style="margin-left:185px">									 	
								<input id="openingDate" type="date" class="date" required">																												 										 
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Date of closure</font>
							</span>
							<span style="margin-left:174px">									 	
								<input id="1" type="date" class="date" required">																												 										 
							</span>
		            	</div>
		            	<div>
		            		<span style="margin-left:30px">
								<font>Whether to use</font>
							</span>
		            		<span style="margin-left:174px">	
								<select id="1" style="width: 140px; height: 20px;">
									<option value="0">Use</option>
									<option value="1">Not use</option>								
								</select>
							</span>
		            	</div>
		            </div>
		        </div>
		        <!-- 추가등록사항 -->
		        
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