<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인사정보등록</title>
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
	var empList = [];
	var empPersonalList = [];
	var empResidentList=[];
	var gridOptions;
	var rowData;
	var empList_grid;
	var addrowData;
	var selectedEmpBean={};
	
	$(document).ready(function() {
		
		//버튼 이벤트
		//$('#b1').trigger("click");
		$('input:button').hover(function() {
			$(this).css("background-color", "#D8D8D8");
		}, function() {
			$(this).css("background-color", "");
		});

		/* DatePicker  */
		$('#from').val(today.substring(0, 8) + '01'); // 오늘이 포함된 해당 달의 첫번째 날, 1월달이면 1월 1일로 세팅. 	2020-xx 총 7자리
		$('#to').val(today.substring(0, 10)); // 오늘 날짜의 년-월-일.
		
		
		
		
		//최종학력 돋보기 버튼을 클릭시 클릭
		$('#lastSchoolC').click(function() { 
			getCode("lastSchool");
		})
		
		showEmpGrid();
		findgrid();
		$('#b1').trigger("click");
	});
	
	//select box 선택 시 input 값 비활성화

	$(document).on("change", "select[name=empForeign]", function(){
		var value = $(this).find("option:selected").val();
		if (value == 0) {
			$("#ffresidentNumber").val('');
			$("#ffresidentNumber").attr("disabled",true);
			$("#residentNumber").attr("disabled",false);
		}
		if (value  == 1) {
			$("#residentNumber").val('');
			$("#residentNumber").attr("disabled",true);
			$("#ffresidentNumber").attr("disabled",false);
		}
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
	/* //sortByAllDesc()
	function sortByAllDesc() {
		var filterComponent = gridOptions.api.getFilterInstance('empStatus');
		filterComponent.setModel({
			type : 'empStatus',

		});
		gridOptions.api.onFilterChanged();
	}

	function sortByYDesc() {
		var filterComponent = gridOptions.api.getFilterInstance('empStatus');
		filterComponent.setModel({
			type : 'empStatus',
			filter : "Y"
		});
		gridOptions.api.onFilterChanged();
	}
	function sortByNDesc() {

		var filterComponent = gridOptions.api.getFilterInstance('empStatus');
		filterComponent.setModel({
			type : 'empStatus',
			filter : "N"
		});
		gridOptions.api.onFilterChanged();
	} */

	//출력구분하기 
	function findgrid() {
		$.ajax({
			url : '${pageContext.request.contextPath}/new_emp/list_new.do',
			data : {
				"method" : "emplist"

			},
			dataType : "json",
			success : function(data) {
				//console.log("1");
				//console.log(data.list);
				if (data.errorCode < 0) {
					var str = " An internal server error has occurred.\n";
					str += "Please contact the administrator.\n";
					str += "Location : an error : " + $(this).attr("id");
					str += "Error messages : " + data.errorMsg;
					alert(str);
					return;
				}
				//만들어놓은 빈 배열(empList)에 받아온 data.list담음
				empList = data.list; 
				clearEmpInfo();
				gridOptions.api.setRowData(data.list);
			}
		});
	}
	
	// 코드선택창 띄우기
	
	function getCode(inputCode) {
		var x = (document.body.offsetWidth/2)-(200/2);
		var y= (window.screen.height/ 2)-(300/2);
		option = "width=450; height=500px; left="+x+"; top="+y+"; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open("${pageContext.request.contextPath}/new_emp/codeWindow.html?code="+ inputCode, "newwins", option);
	}

	//	1. 왼쪽 코드목록 그리드 띄우는 함수 
	function showEmpGrid() {
		rowData = [];
		var columnDefs = [ {
			headerName : "Code",
			field : "empCode",
			width : 100
		}, {
			headerName : "Name",
			field : "empName"
		}, {
			headerName : "Department",
			field : "empDept"
		}, {
			headerName : "Status",
			field : "empStatus",
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

				empPersonalList = [];
				/*for(value of codeList){
					console.log(value);
				}
				 */
				//console.log(node.data);
				var empCode = node.data.empCode;
				//console.log(node.data);
				console.log(empCode);

				$.ajax({
							url : "${pageContext.request.contextPath}/new_emp/empPersonal.do",
							data : {
								"method" : "empPersonal",
								"code" : empCode
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
								empPersonalList = data.list;
								empResidentList=data.list2;
								//console.log(data.list);
								///console.log(1);
								//console.log(data.list[0].empName);

								//initField();// 전역변수 초기화
								clearEmpInfo(); // 상세정보 칸 초기화

								//setAllEmptyBean(data);

								//selectedEmpBean = $.extend(true, {}, data.list); // 취소버튼을 위한 임시 저장공간에 딥카피
								//updatedEmpBean = $.extend(true, {}, data.list); // 변경된 내용이 들어갈 공간에 딥카피
								// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임

								/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름 */
								showDetailInfo();
								showResidentInfo();
							}
						});

			}
		};
		empFind_grid = document.querySelector('#empFind_grid');
		new agGrid.Grid(empFind_grid, gridOptions);
	}

	/* 전역변수 초기화 함수 */
	function initField() {
		selectedEmpBean, updatedEmpBean = {};

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
	//	2. 오른쪽 개인정보 출력하는 함수 
	function showDetailInfo() {

		$("#empName").val(empPersonalList[0].empName);
		$("#englishName").val(empPersonalList[0].englishName);

		
		
		//0.	jQuery - prop()으로 select 상태 값 변경
		var empForeignNumber;
		if (empPersonalList[0].empForeign == "Local") {
			empForeignNumber = 0; //내국인
		} else {
			empForeignNumber = 1; //외국인
		}
		 if (empForeignNumber == 0) {
			$("#ffresidentNumber").val('');
			$("#ffresidentNumber").attr("disabled",true);
		}
		if (empForeignNumber == 1) {
			$("#residentNumber").val('');
			$("#residentNumber").attr("disabled",true);
		} 

		$("#empForeign").val(empForeignNumber).prop("selected", true);
		
		

		$("#residentNumber").val(empPersonalList[0].residentNumber);
		$("#ffresidentNumber").val(empPersonalList[0].ffresidentNumber);

		//1.	jQuery - prop()으로 select 상태 값 변경
		var empGenderNumber;
		if (empPersonalList[0].empGender == "Man") {
			empGenderNumber = 0;
		} else {
			empGenderNumber = 1;
		}
		$("#empGender").val(empGenderNumber).prop("selected", true);

		//2.	생년월일 
		var year = empPersonalList[0].empBirthdate.substr(0, 4);
		var month = empPersonalList[0].empBirthdate.substr(5, 2);
		var day = empPersonalList[0].empBirthdate.substr(8, 2);
		$("#empBirthdate").val(year + "-" + month + "-" + day);
		//console.log(empPersonalList[0].empBirthdate);
		//$("#empBirthdate").val(empPersonalList[0].empBirthdate);

		$("#mobileNumber").val(empPersonalList[0].mobileNumber);
		$("#empHp").val(empPersonalList[0].empHp);

		//3.	최종학력 
		$("#lastSchoolB").val(empPersonalList[0].lastSchool);
		if (empPersonalList[0].lastSchool == "Graduate school") {
			$("#lastSchoolA").val(100);
		} else if (empPersonalList[0].lastSchool == "University") {
			$("#lastSchoolA").val(200);
		} else if (empPersonalList[0].lastSchool == "Junior college") {
			$("#lastSchoolA").val(300);
		} else if (empPersonalList[0].lastSchool == "High school") {
			$("#lastSchoolA").val(400);
		}

	}
	//	3. 오른쪽 거주정보 출력하는 함수 
	function showResidentInfo() {
		//console.log("123123showResidentInfo");
		
		$("#empAddress").val(empResidentList[0].empAddress);
		$("#detailAddress").val(empResidentList[0].detailAddress);
		$("#englishAddress").val(empResidentList[0].englishAddress);
		$("#empEmail").val(empResidentList[0].empEmail);
		$("#empCardno").val(empResidentList[0].empCardno);
		
		
		//0.	jQuery - prop()으로 select 상태 값 변경
		var houseOwner ;
		if (empResidentList[0].houseOwner  == "yes") {
			houseOwner  = 0; //부
		} else {
			houseOwner  = 1; //여
		}
		
		$("#houseOwner").val(houseOwner).prop("selected", true);
		
		
		//1.	jQuery - prop()으로 select 상태 값 변경
		var disabledPerson;
		if (empResidentList[0].disabledPerson  == "no") {
			disabledPerson  = 0; //비해당
		} else {
			disabledPerson  = 1; //해당
		}
		$("#disabledPerson").val(disabledPerson).prop("selected", true);
		
		
		//2.	jQuery - prop()으로 select 상태 값 변경
		var nationalityA ;
		if (empResidentList[0].nationalityA  == "KOR") {
			nationalityA  = 0; //대한민국
		}else if (empResidentList[0].nationalityA  == "US") {
			nationalityA  = 1; //미국
		}else if (empResidentList[0].nationalityA  == "JAP") {
			nationalityA  = 2; //일본
		}else {
			nationalityA  = 3; //중국
		}
		$("#nationalityA").val(nationalityA).prop("selected", true);
		
		//3.	jQuery - prop()으로 select 상태 값 변경
		/* var houseOwner ;
		if (empResidentList[0].houseOwner  == "no") {
			houseOwner  = 0; //부
		} else {
			houseOwner  = 1; //여
		}
		$("#houseOwner").val(houseOwner).prop("selected", true); */
		

		//4.	jQuery - prop()으로 select 상태 값 변경
		var religiousStatus;
		if (empResidentList[0].religiousStatus == "yes") {
			religiousStatus = 0;//해당 
		} else {
			religiousStatus = 1; //비해당 
		}
		$("#religiousStatus").val(religiousStatus).prop("selected", true);
		

		
		
		
		
		//1.	국적(신고용)
		$("#nationalityCodeA").val(empResidentList[0].nationalityCodeA);
		$("#nationalityCodeB").val(empResidentList[0].nationalityCodeB);
		
		//5.	jQuery - prop()으로 select 상태 값 변경
		var residentIdentification;
		if (empResidentList[0].residentIdentification == "Resident") {
			residentIdentification = 0;//해당 
		} else {
			residentIdentification = 1; //비해당 
		}
		$("#residentIdentification").val(residentIdentification).prop("selected", true);
		
		
		//2.	거주자코드
		$("#nationalityB1").val(empResidentList[0].nationalityB1);
		$("#nationalityB2").val(empResidentList[0].nationalityB2);

	}
</script>
</head>

<body>

	<h4>Personnel information registration</h4> 
	<div>
		<div style="text-align: right;">
			<input type="button" id="empAdd" value="Add"class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="empDelete"  value="Delete" class="btn btn-Light shadow-sm btnsize"> 
			<input type="button" id="empSave" value="Save" class="btn btn-Light shadow-sm btnsize">
		</div>
	</div>
	
	<hr>
	
	<div class="col-12" style="float: left">
		<div class="card">
			<div class="card-body">
				<div id="findtab1">
					<!-- Inquiry standard &nbsp <input type='radio' name="1" onclick="sortByYDesc()" />in-service
					&nbsp <input type='radio' onclick="sortByNDesc()" name="1" />retirement
					&nbsp <input type='radio' onclick="sortByAllDesc()" name="1" />whole -->
					<br /> Search &nbsp 
					<input name="searchKeyword" type="text" oninput="onQuickFilterChanged()" id="quickFilter" placeholder="quick filter..."> 
					<br /> <br />
					<div id="codeList_tab">
						<table>
							<tr>
								<td>
									<div id="empFind_grid" style="height: 500px; width: 400px"
										class="ag-theme-balham"></div>
								</td>
								<td></td>
							</tr>
						</table>
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
		        	<button class="tablinks" id="b1" onclick="openCity(event, '0')">Personal information</button>
		        	<button class="tablinks" onclick="openCity(event, '1')">Information on employment</button>
		        	<button class="tablinks" onclick="openCity(event, '2')">Salary information</button>
		    	</div>
		
		   		<div id="0" class="tabcontent">
		   			<!-- 1번째줄-->
		            <div style="width: 900px; height: 350px;">
		                <!-- 1번째줄 1번째칸 -->
		                <div style=" border: 1px solid #FAECC5; writing-mode: tb-rl; float: left; width: 3%; height: 350px;">
		                    <center> ▶  Personal information</center>
		                </div>
		                <!-- 1번째줄 2번째칸 -->
		                <div style=" float: left; width: 25%; height: 350px;">
		                    <div>
			                	<center>
			                        <img id="profileImg" src="${pageContext.request.contextPath}/profile/profile.png" width="180px" height="200px">
			                        <br>
			                        <form id="emp_img_form" action="${pageContext.request.contextPath }/base/empImg.do" enctype="multipart/form-data" method="post"> 
			                        <input type="hidden" name="empCode" id="emp_img_empCode"> 
			                        <input type="file" name="empImgFile" style="display: none;" id="emp_img_file" onChange="readURL(this)" accept="image/*">
			
			                        <br />
			                        <button type="button" style="width: 180px;" class="btn info" id="findPhoto">Photo registration</button>
			                        </form>
			                        <br>
			                    </center>
		                    </div>		                 		
		                </div>
		                <!-- 1번째줄 3번째칸 -->
		                <div style="border: 1px solid #FAECC5; float: left; width: 70%; height: 350px;">
		                    <center>
		                        <table>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Name</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="empName"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp English Name</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="englishName"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Foreigner classification</font></td>
		                                <td><select name="empForeign" id="empForeign"
		                                    style="width: 300px; height: 20px;">
		                                        <option value="0">Local</option>
		                                        <option value="1">Foreigner</option>
		                                </select></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Resident registration number</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="residentNumber"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Foreigner registration number</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="ffresidentNumber"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbspGender</font></td>
		                                <td><select id="empGender"
		                                    style="width: 300px; height: 20px;">
		                                        <option value="0">Man</option>
		                                        <option value="1">Woman</option>
		                                </select></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Date of birth</font></td>
		                                <td>
		                                	<input id="empBirthdate" type="date" class="date" required style="width: 130px height:20px"> 
		                                    <select id="empSex" style="width: 155px; height: 20px;">
		                                        <option value="test1">solar calendar</option>
		                                        <option value="test2">Lunar calendar</option>
		                                	</select>
		                                </td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Phone number</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="mobileNumber"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp HP</font></td>
		                                <td><input type="text" style="width: 300px; height: 20px;"
		                                    id="empHp"></td>
		                            </tr>
		                            <tr>
		                                <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp Final academic background</font></td>
		                                <td>
		                                	<input type="text" style="width: 95px; height: 20px;"id="lastSchoolA">
		                                    <button id="lastSchoolC">
		                                    	<i class="w3-small-margin-left fa fa-search"></i>
		                                    </button> 
		                                    <input type="text" style="width: 165px; height: 20px;"id="lastSchoolB">
		                                </td>
		                            </tr>
		                        </table>
		                    </center>
		                </div>
	            	</div>
	
		            <!-- 2번째줄-->
		            <hr>
		            <div style="width: 900px; height: 350px;">
		                <!-- 2번째줄 1번째칸 -->
		                <div
		                    style=" border: 1px solid #FAECC5; writing-mode: tb-rl; float: left; width: 3%; height: 350px;">
		                    <center> ▶  Residential information</center>
		                </div>
		
		                <!-- 2번째줄 2번째칸 -->
		                
		                <div style=" float: left; width: 855px; height: 350px;">		                	
			                <div>
			            		<span style="margin-left:20px">
									<font>Address</font>
								</span>
								<span style="margin-left:221.3px">
									<input type="text" style="width: 500px; height: 20px;" id="empAddress">
								</span>		
			            	</div>
			            	<div>
			            		<span style="margin-left:20px">
									<font>Detail address</font>
								</span>
								<span style="margin-left:177.73px">
									<input type="text" style="width: 500px; height: 20px;" id="detailAddress">
								</span>		
			            	</div>
			            	 <!-- <tr>
		                            <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp영문주소</font></td>
		                            <td><input type="text" id="englishAddress" style="width: 600px; height: 20px;"
		                                id="empName"></td>
		                     </tr> -->
		                     <div>
			            		<span style="margin-left:20px">
									<font>E-MAIL</font>
								</span>
								<span style="margin-left:227.24px">
									<input type="text" style="width: 500px; height: 20px;" id="empEmail">
								</span>		
			            	</div>
			            	<!-- <tr>
		                            <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp출입카드NO.</font></td>
		                            <td><input type="text" id="empCardno" style="width: 600px; height: 20px;"
		                                id="empName"></td>
		                        </tr> -->
			            	<div>
			            		<span style="margin-left:20px">
									<font>Household owner status</font>
								</span>
			            		<span style="margin-left:108.33px">	
									<select id="houseOwner" style="width: 100px; height: 20px;">
										<option value="0">yes</option>
										<option value="1">no</option>								
									</select>
								</span>
								<span >
									<font>Disabled person classification</font>
								</span>
			            		<span style="margin-left:79.22px">	
									<select id="disabledPerson" style="width: 100px; height: 20px;">
		                            	<option value="0">no</option>
		                                <option value="1">yes</option>		                             
		                             </select>
								</span>
		            		</div>
		            		<div>
			            		<span style="margin-left:20px">
									<font>Nationality (for management)</font>
								</span>
			            		<span style="margin-left:73.68px">	
									<select id="nationalityA" style="width: 100px; height: 20px;">
		                            	<option value="0">KOR</option>
		                                <option value="1">US</option>
		                                <option value="2">JAP</option>
		                                <option value="3">CN</option>
		                             </select>
								</span>
								<span >
									<font>Religious status</font>
								</span>
			            		<span style="margin-left:177.95px">	
									<select id="religiousStatus" style="width: 100px; height: 20px;">
		                                  <option value="0">no</option>
		                                  <option value="1">yes</option>		                                   
		                            </select>
								</span>
		            		</div>
		                    <div>
			            		<span style="margin-left:20px">
									<font>Nationality (for declaration)</font>
								</span>
								<span style="margin-left:38.8px">
									<input type="text" style="width: 50px; height: 20px;" id="nationalityCodeA">
								</span>	
								<span>
									<button>
			                        	<i class="w3-small-margin-left fa fa-search"></i>
			                        </button>
								</span>
								<span>
									<input type="text" style="width: 460px; height: 20px;" id="nationalityCodeB">
								</span>		
			            	</div>
		                    <div>
			            		<span style="margin-left:20px">
									<font>Resident classification</font>
								</span>
			            		<span style="margin-left:121.7px">	
									<select id="residentIdentification"style="width: 500px; height: 20px;">                                      
		                            	<option value="0">Resident</option>
		                            	<option value="1">Nonresident</option>                                       
		                            </select>
								</span>			
		            		</div>  
	
			            	<div>
			            		<span style="margin-left:20px">
									<font>Residents'code</font>
								</span>
								<span style="margin-left:121.09px">
									<input type="text" style="width: 50px; height: 20px;" id="nationalityB1">
								</span>	
								<span>
									<button>
			                        	<i class="w3-small-margin-left fa fa-search"></i>
			                        </button>
								</span>
								<span>
									<input type="text" style="width: 460px; height: 20px;" id="nationalityB2">
								</span>		
			            	</div>          
		                </div>
		            </div>
			        
			        <!-- 3번째 줄 저장/취소버튼 -->
			        <div style="width: 885px; height: 200px;">			                
			                <center>
			                    <input type="button" id="info" style="width: 180px;" value="Save"> 
			                </center>		
			        </div>	    	        	   
				</div>
				
				<!--  -->
				<!-- 재직정보 -->
				<div id="1" class="tabcontent">
					   	 		
					<div style="width: 900px; height: 300px;">
						<!-- 1번째줄 1번째칸-->
						<div style=" border: 1px solid #FAECC5; writing-mode: tb-rl; float: left; width: 3%; height: 300px;">
							 <center> ▶  입사정보</center>
						 </div>
						 
						 <!-- 1번째줄 2번째칸 -->
						 <div style="float: left; width: 97%; height: 300px; display:flex; flex-direction:row;">
						 	
							 <div style="width: 50%; ">						 	
									<span style="margin-left:30px">
										<font>입사일</font>
									</span>
									<span style="margin-left:95px">									 	
										 <input id="1" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>	
									<br/>
									
									<span style="margin-left:30px">
										<font>퇴직일</font>
									</span>
									<span style="margin-left:95px">									 	
										 <input id="2" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>	
									<br/>
									
									<span style="margin-left:35px">
										<font>중도퇴사일</font>
									</span>
									<span style="margin-left:60px">									 	
										 <input id="3" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>
									<br/>
									<span style="margin-left:30px">
										<font>수습기간</font>
									</span>
									<span style="margin-left:80px">	
										<select id="4" style="width: 140px; height: 20px;">
											<option value="0">부</option>
											<option value="1">여</option>					
									 	</select>
								 	</span>	
									
									<br/>
									<span style="margin-left:30px">
										<font>근속기간포함</font>
									</span>
									<span style="margin-left:50px">	
										<select id="5" style="width: 140px; height: 20px;">
											<option value="0">안함</option>
											<option value="1">함</option>
											
									 	</select>
								 	</span>	
									<br/>
									<span style="margin-left:35px">
										<font>휴직기간 시작일</font>
									</span>
									<span style="margin-left:22px">									 	
										 <input id="6" type="date" class="date" required style="width: 100px height:20px">																															 										 
									</span>
									<br/>
									<span style="margin-left:35px">
										<font>휴직기간 종료일</font>
									</span>
									<span style="margin-left:22px">									 	
										 <input id="7" type="date" class="date" required style="width: 100px height:20px">																															 										 
									</span>
																									 								 							 
							 </div>							 
							 <div style="width: 50%; ">						 	
									<span style="margin-left:30px">
										<font>그룹입사일</font>
									</span>
									<span style="margin-left:130px">									 	
										 <input id="1" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>
									<br/>
									<span style="margin-left:30px">
										<font>재직구분</font>
									</span>
									<span style="margin-left:145px">	
										<select id="2" style="width: 140px; height: 20px;">
											<option value="0">재직</option>
											<option value="1">파견</option>
											<option value="2">휴직</option>
											<option value="3">대기</option>
											<option value="4">퇴직</option>
									 	</select>
								 	</span>	
								 	<br/>
								 	<span style="margin-left:30px">
										<font>퇴직연급가입일(DB형)</font>
									</span>
									<span style="margin-left:50px">									 	
										 <input id="3" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>
									<br/>
									<span style="margin-left:30px">
										<font>수습만료일</font>
									</span>
									<span style="margin-left:130px">									 	
										 <input id="4" type="date" class="date" required style="width: 200px height:20px">																												 										 
									</span>
									<br/>																	 								 							 
							 </div>
						 </div>
						 
					</div>
					
					<!-- 2번째줄-->
					<hr>
					<div style="width: 900px; height: 350px;">
						<!-- 2번째줄 1번째칸 -->
						<div style=" border: 1px solid #FAECC5; writing-mode: tb-rl; float: left; width: 3%; height: 350px;">
							<center> ▶  근무정보</center>
						</div>
					 	<!-- 2번째줄 2번째칸 -->
						 <div style=" float: left; width: 855px; height: 350px;">
							 <table>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp부서</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp고용형태</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp직종</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp급여형태</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp프로젝트</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp근무조</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp직급</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp직책</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp직무</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp분류코드</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
								 <tr>
										 <td><font>&nbsp&nbsp&nbsp&nbsp&nbsp퇴직사유</font></td>
										 <td><input type="text" style="width: 95px; height: 20px;"
											 id="nationalityCodeA">
											 <button>
												 <i class="w3-small-margin-left fa fa-search"></i>
											 </button> <input type="text" style="width: 470px; height: 20px;"
											 id="nationalityCodeB"></td>			
								 </tr>
															  
							</table>
						</div>
					</div>
					<div style="width: 885px; height: 200px;">
						 <!-- 저장/취소버튼 -->
						 <center>
							 <input type="button" id="info" style="width: 180px;" value="저장하기"> 
						 </center>					
					</div>					
				</div>	
				
				
				
		    	<div id="2" class="tabcontent">
		        	<div style="width: 900px; height: 300px;">
		        	
		            
					</div>
					<div style="width: 885px; height: 200px;">
							 <!-- 저장/취소버튼 -->
							 <center>
								 <input type="button" id="info" style="width: 180px;" value="저장하기"> 
							 </center>
		
					</div>
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