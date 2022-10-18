<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Tabs - Default functionality</title>

<style>
#divImg {
	display: inline-block;
} /* inline-blick 적용하면 div태그도 한줄 다 차지 안함
	   - width/height 적용 가능
	   - margin/padding-top/bottom 적용 가능
	   - line-height 적용 가능
    */
#divEmpinfo {
	display: inline-block;
}

#divEmpDinfo {
	display: inline-block;
}
</style>


<script>
var empList = [];
var empDList = []; //empDetailList
var selectedEmpBean, updatedEmpBean = {}; // 사원의 상세정보를 저장하는 객체들 
var workGrid, careerGrid, educationGrid, licenseGrid, familyGrid; //세부정보 그리드객체들
var deptGridf, nameGridf;

empName = "";
emptyWorkInfoBean = {}; // 그리드에 새 행을 추가하기 위한 비어있는 객체들
emptyCareerInfoBean = {};
emptyEducationInfoBean = {};
emptyLicenseInfoBean = {};
emptyFamilyInfoBean = {};
var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함) 

$(document).ready(function() {
	/* 부서명 검색/사원명 검색 탭 이동 */
	$("#tabs").tabs();
	$("#sel_dept").selectmenu(); //하위에 있는 option들을 선택할수 있다
	$("#tabs1").tabs();
	/* 부서명 검색, 이름 검색 이벤트 등록 */
	$("#btn_name_search").click(function() { //사원명 검색탭 검색버튼
		makeEmpList("name", $("#txt_name").val()); 
	});
	$("#btn_dept_search").click(function() { //부서명 검색탭 검색버튼
		makeEmpList("dept", $("#sel_dept").val());
	});
		/* 검색 함수 */
	function makeEmpList(grid, value) {
		$.ajax({
			url : "${pageContext.request.contextPath}/emp/list.do",
			data : {
				"method" : "emplist",
				"value" : value				//전체부서/회계팀/인사팀/전산팀 혹은 사원명
			},
			dataType : "json",
			success : function(data) {
						if (data.errorCode < 0) { //erroe code는 컨트롤러에서 날려보냄
							var str = " An internal server error has occurred.\n";
							str += "Please contact the administrator.\n";
							str += "Location : an error : " + $(this).attr("id");
							str += "Error messages : " + data.errorMsg;
							alert(str);
							return; //function 빠져나감
						}
						empList = data.list; //만들어놓은 빈 배열(empList)에 받아온 data.list담음
						clearEmpInfo();
						//console.log(empList);
						if (grid == "dept")
							showEmpListDeptGrid();
						else {
							showEmpListNameGrid();
						}
			},
			error : function(a, b, c) {
				alert(b);
			}
		});
	}

/* 부서별 사원 정보 그리드에 뿌리는 함수 */
function showEmpListDeptGrid() {
	var columnDefs = [ 
		{ headerName : "사원코드", field : "empCode", hide : true },
		{ headerName : "사원명", field : "empName", checkboxSelection : true },
		{ headerName : "부서", field : "deptName"}, 
		{ headerName : "직급", field : "position" }, 
		{ headerName : "성별", field : "gender", hide : true }, 
		{ headerName : "전화번호", field : "mobileNumber", hide : true }, 
		{ headerName : "이메일", field : "email" }, 
		{ headerName : "거주지", field : "address", hide : true }, 
		{ headerName : "최종학력", field : "lastSchool", hide : true }, 
		{ headerName : "사진", field : "imgExtend", hide : true }, 
		{ headerName : "생년월일", field : "birthdate", hide : true } 
	];
	 gridOptions = {
		columnDefs : columnDefs,
		rowData : empList,
		onRowClicked : showEmpDetailbyDept
	}
	$('#deptfindgrid').children().remove();
	var eGridDiv = document.querySelector('#deptfindgrid');
	deptGridf = gridOptions;
	new agGrid.Grid(eGridDiv, gridOptions);
	gridOptions.api.sizeColumnsToFit();
}

function showEmpDetailbyDept(event) {
	console.log(event);
	event.node.setSelected(true);
	var empCode = event.data.empCode;
	var profile = event.data.imgExtend;
	if(profile!=""){
		$("#profileImg").attr("src","${pageContext.request.contextPath}/profile/"+empCode+"."+profile);
	} else{
		$("#profileImg").attr("src","${pageContext.request.contextPath}/profile/profile.png");
	}
	$.ajax({
		url : "${pageContext.request.contextPath}/emp/empDetail.do",
		data : {
			"method" : "findAllEmployeeInfo",
			"empCode" : empCode
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
			console.log("data.empBean====="+data.empBean);
			empDList = data.empBean;
			initField(); // 전역변수 초기화
			clearEmpInfo(); // 상세정보 칸 초기화
			setAllEmptyBean(data); //빈 배열을 만들어서 세부정보 객체의 빈  틀을 넣는다

			/*
				$.extend(true, obj1, obj2); => obj1이 원본의 형태를 유지하면서  obj2가 obj1을 덮어씀
				예제 - https://loveroid.tistory.com/27
			*/
			selectedEmpBean = $.extend(true, {}, data.empBean); // 취소버튼을 위한 임시 저장공간에 딥카피
			updatedEmpBean = $.extend(true, {}, data.empBean); // 변경된 내용이 들어갈 공간에 딥카피
			// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임

			/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름 */
			showDetailInfo();
			showWorkInfoListGrid();
			showCareerInfoListGrid();
			showEducationInfoListGrid();
			showLicenseInfoListGrid();
			showFamilyInfoListGrid();
		}
	});
	}



/* 이름으로 검색결과 그리드에 뿌리는 함수 */
function showEmpListNameGrid() {
	var columnDefs = [ 
		{ headerName : "사원코드", field : "empCode", hide : true }, 
		{ headerName : "사원명", field : "empName" }, 
		{ headerName : "부서", field : "deptName" }, 
		{ headerName : "직급", field : "position" }, 
		{ headerName : "성별", field : "gender", hide : true }, 
		{ headerName : "전화번호", field : "mobileNumber", hide : true }, 
		{ headerName : "이메일", field : "email" }, 
		{ headerName : "거주지", field : "address", hide : true }, 
		{ headerName : "최종학력", field : "lastSchool", hide : true }, 
		{ headerName : "사진", field : "imgExtend", hide : true }, 
		{ headerName : "생년월일", field : "birthdate", hide : true } 
	];
	var gridOptions = {
	columnDefs : columnDefs,
	rowData : empList,
	onSelectionChanged : showEmpDetailbyName
	}
	$('#namefindgrid').children().remove();
	var eGridDiv = document.querySelector('#namefindgrid');
							new agGrid.Grid(eGridDiv, gridOptions);
							nameGridf = gridOptions;
							gridOptions.api.sizeColumnsToFit();
							gridOptions.api.selectAll();
						}

						$("#address").postcodifyPopUp(); //주소검색

						/* 사진찾기*/
						$("#findPhoto").button().click(function() {
							$("#emp_img_file").click(); //사진찾기 버튼을 누르면 숨겨진 file 태그를 클릭
						});
						// 사진 등록 form의 ajax 부분
						$("#emp_img_form").ajaxForm(
								{
									dataType : "json",
									success : function(responseText,
											statusText, xhr, $form) {
										alert(responseText.errorMsg);
										location.reload();
									}
								});
						
						function showEmpDetailbyName() {
							var empData = nameGridf.rowData[0];
							if(typeof empData == "undefined")
								return;
							var empCode = empData.empCode;
							var profile = empData.imgExtend;
							if(profile!=""){
								$("#profileImg").attr("src","${pageContext.request.contextPath}/profile/"+empCode+"."+profile);
							} else{
								$("#profileImg").attr("src","${pageContext.request.contextPath}/profile/profile.png");
							}
							$.ajax({
								url : "${pageContext.request.contextPath}/emp/empDetail.do",
								data : {
									"method" : "findAllEmployeeInfo",
									"empCode" : empCode
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
												console.log("data.empBean=="+data.empBean);
												empDList = data.empBean;
												
												initField(); // 전역변수 초기화
												clearEmpInfo(); // 상세정보, 재직정보 칸 초기화

												setAllEmptyBean(data);
												selectedEmpBean = $.extend(true, {}, data.empBean); // 취소버튼을 위한 임시 저장공간에 딥카피
												updatedEmpBean = $.extend(true, {}, data.empBean); // 변경된 내용이 들어갈 공간에 딥카피
												// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임

												/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름 */
												showDetailInfo();
												showWorkInfoListGrid();
												showCareerInfoListGrid();
												showEducationInfoListGrid();
												showLicenseInfoListGrid();
												showFamilyInfoListGrid();
												}
							});
						}
						/* 코드 선택창 띄우기 */
						function getCode(code, inputText, inputCode) {
							option = "width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
							window.open(
									"${pageContext.request.contextPath}/base/codeWindow.html?code="
											+ code + "&inputText=" + inputText
											+ "&inputCode=" + inputCode,
									"newwins", option);
						}

						/* 전역변수 초기화 함수 */
						function initField() {
							selectedEmpBean, updatedEmpBean = {};
							emptyFamilyInfoBean = {};
							emptyCareerInfoBean = {};
							emptyWorkInfoBean = {};
							emptyEducationInfoBean = {};
							emptyLicenseInfoBean = {};
							lastId = 0;
						}

						/* 현재 표시된 모든 정보를 비우는 함수 */
						function clearEmpInfo() {
							// 찾았던 사진을 기본 사진으로 되돌린다
							$("#profileImg")
									.attr("src",
											"${pageContext.request.contextPath }/profile/profile.png");
							$("input:text").each(function() {
									$(this).val(""); //모든 input text타입의 value값을 비운다
							});
						}

						/* 새로운 정보들을 추가하기 위한 빈 객체 세팅 */
						function setAllEmptyBean(data) {
							emptyFamilyInfoListBean = data.emptyFamilyInfoBean;
							emptyCareerInfoListBean = data.emptyCareerInfoBean;
							emptyWorkInfoListBean = data.emptyWorkInfoBean;
							emptyEducationInfoListBean = data.emptyEducationInfoBean;
							emptyLicenseInfoListBean = data.emptyLicenseInfoBean;

							emptyFamilyInfoListBean.status = "insert";
							emptyFamilyInfoListBean.empCode = data.empBean.empCode;
							emptyCareerInfoListBean.status = "insert";
							emptyCareerInfoListBean.empCode = data.empBean.empCode;
							emptyWorkInfoListBean.status = "insert";
							emptyWorkInfoListBean.empCode = data.empBean.empCode;
							emptyEducationInfoListBean.status = "insert";
							emptyEducationInfoListBean.empCode = data.empBean.empCode;
							emptyLicenseInfoListBean.status = "insert";
							emptyLicenseInfoListBean.empCode = data.empBean.empCode;
						}

						/* DatePicker 달력함수 
 						 * Date Picker 사용법 
 						 * 1. 칼럼 정의 시 editable : true, cellEditor : 'datePicker' 추가 
 						 * 2. 칼럼옵션에 컴포넌트 추가 
 						 * gridOpt.components = {
 						 *  datePicker: getDatePicker('yy-mm-dd') // date format 지정시 포맷 전달 
 						 *  datePicker: getDatePicker()  // 기본은 yymmdd 8자리로 됨 
 						 * };
 						 */
						function getDatePicker() {
							// function to act as a class
							function Datepicker() {
							}

							// gets called once before the renderer is used
							Datepicker.prototype.init = function(params) {
								// create the cell
								this.eInput = document.createElement('input');
								this.eInput.value = params.value;

								// https://jqueryui.com/datepicker/
								$(this.eInput).datepicker({
									dateFormat : 'yy/mm/dd'
								});
							};

							// gets called once when grid ready to insert the element
							Datepicker.prototype.getGui = function() {
								return this.eInput;
							};

							// focus and select can be done after the gui is attached
							Datepicker.prototype.afterGuiAttached = function() {
								this.eInput.focus();
								this.eInput.select();
							};

							// returns the new value after editing
							Datepicker.prototype.getValue = function() {
								return this.eInput.value;
							};

							// any cleanup we need to be done here
							Datepicker.prototype.destroy = function() {
								// but this example is simple, no cleanup, we could
								// even leave this method out as it's optional
							};

							// if true, then this editor will appear in a popup
							Datepicker.prototype.isPopup = function() {
								// and we could leave this method out also, false is the default
								return false;
							};

							return Datepicker;
						}

						function getDatePickerNumber($CellId) {
							// function to act as a class
							function Datepicker() {
							}

							// gets called once before the renderer is used
							Datepicker.prototype.init = function(params) {
								// create the cell
								this.eInput = document.createElement('input');
								this.eInput.value = params.value;

								// https://jqueryui.com/datepicker/
								$(this.eInput).datepicker({
									dateFormat : 'yy/mm/dd'
								});
							};

							// gets called once when grid ready to insert the element
							Datepicker.prototype.getGui = function() {
								return this.eInput;
							};

							// focus and select can be done after the gui is attached
							Datepicker.prototype.afterGuiAttached = function() {
								this.eInput.focus();
								this.eInput.select();
							};

							// returns the new value after editing
							Datepicker.prototype.getValue = function() {
								return this.eInput.value;
							};

							// any cleanup we need to be done here
							Datepicker.prototype.destroy = function() {
								// but this example is simple, no cleanup, we could
								// even leave this method out as it's optional
							};

							// if true, then this editor will appear in a popup
							Datepicker.prototype.isPopup = function() {
								// and we could leave this method out also, false is the default
								return false;
							};

							return Datepicker;
						}

						////////////////////////////////////////////////////그리드이벤트////////////////////////////////////////////////////////	  

						/* 그리드에 행 추가하는 함수 */
						function addListGridRow() {
							var key = $(this).attr("id").split("_")[1];
							var emptyBean = {
								"family" : emptyFamilyInfoListBean,
								"career" : emptyCareerInfoListBean,
								"work" : emptyWorkInfoListBean,
								"education" : emptyEducationInfoListBean,
								"license" : emptyLicenseInfoListBean
							}
                            var gridName=key+"Grid";
							var gridArr=[familyGrid, careerGrid, workGrid, educationGrid, licenseGrid];
							var data = [];
							var empty=emptyBean[key];
							var grid = null;
							gridArr.forEach(function(v,i){
								 if(gridName==gridArr[i].name){
									 grid = gridArr[i];
										grid.rowData.forEach(function(e,index){
											data[index]=e;
										});
									//gridArr[i].api.applyTransaction({ add: [emptyBean[key]] });
									//gridArr[i].api.updateRowData({add: [emptyBean[key]]});
									data[data.length] = emptyBean[key];
									grid.rowData = data;
									return;
								 }
							});
							grid.api.setRowData(grid.rowData);
						}

						/* 그리드에 행 삭제 */
						function delListGridRow() {
							var key = $(this).attr("id").split("_")[1];
							var gridName=key+"Grid";
							var array=[familyGrid, careerGrid, workGrid, educationGrid,licenseGrid];
							//var removedRows = [];
							array.forEach(function(v,i){
								if(gridName==array[i].name){
									var selectedRows=array[i].api.getSelectedRows();
									selectedRows.forEach( function(selectedRow, index) {
					            		//removedRows.push(selectedRow);
					            		array[i].api.updateRowData({remove: [selectedRow]});
									});
								}
					        });

						}

						/* 상세정보 탭의 저장 버튼 */
						$("#modifyEmp_Btn").click(function() {
							if (updatedEmpBean == null) {
								alert("저장할 내용이 없습니다");
							} else {
								var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
								if (flag)
									modifyEmp();
							}
						});

						/* 저장 */
						function modifyEmp() {
							saveEmpInfo();
							var sendData = JSON.stringify(updatedEmpBean);
							$.ajax({
										url : "${pageContext.request.contextPath}/emp/empDetail.do",
										data : {
											"method" : "modifyEmployee",
											"sendData" : sendData
										},
										dataType : "json",
										success : function(data) {
											if (data.errorCode < 0) {
												alert("저장에 실패했습니다");
											} else {
												/* 선택한 사진이 있다면 저장 버튼을 눌렀을때에 사진 저장도 같이 되게함 */
												if ($("#emp_img_file").val() != "") {
													$("#emp_img_form").submit();
												}
												alert("저장되었습니다");
											}

											location.reload();
										}
									});
						}

						/* 변경된 정보들을 저장하는 함수 */
						function saveEmpInfo() {

							updatedEmpBean.status = "update";
							updatedEmpBean.empCode = $("#empCode").val();
							updatedEmpBean.empName = $("#empName").val();
							updatedEmpBean.birthdate = $("#birthdate").val();
							updatedEmpBean.deptName = $("#deptName").val();
							updatedEmpBean.position = $("#position").val();
							updatedEmpBean.email = $("#email").val();
							updatedEmpBean.gender = $("#gender").val();
							updatedEmpBean.mobileNumber = $("#mobileNumber")
									.val();
							updatedEmpBean.lastSchool = $("#lastSchool").val();
							updatedEmpBean.address = $("#address").val();
							updatedEmpBean.detailAddress = $("#detailAddress")
									.val();
							updatedEmpBean.postNumber = $("#postNumber").val();
							/* 사진, 생년월일  추가해줘야함*/
						}

						/* 상세정보 탭의 취소 버튼 */
						$("#can_work_btn").click(function() {
							var flag = confirm("취소하시겠습니까?");
							if (flag)
								rollBackEmpInfo();
						});

						function rollBackEmpInfo() {
							clearEmpInfo(); // 모든 정보를 지운 후

							// 서버에서 불러온 미리 저장한 EmpBean의 정보를 수정하던 정보에 엎어씌움
							updatedEmpBean = $.extend(true, {}, selectedEmpBean);
							// 딥카피하는 이유는 주소타입의 변수가 제대로 카피되지 않기 때문임

							//상제정보와 사진을 다시 띄운다
							showDetailInfo();
							showEmpImg();
						}

						/* 사진 찾기 버튼 */
						$("#findImg_btn").click(function() {
							if (updatedEmpBean == null) {
								alert("저장할 내용이 없습니다");
							} else {
								var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
								if (flag)
									modifyEmp();
							}
						});

						/* 저장된 사진 불러오는 함수 */
						function showEmpImg() {
							var path = "${pageContext.request.contextPath }/profile/profile.png";

							if (selectedEmpBean.imgExtend != null) {
									path = "${pageContext.request.contextPath}/profile/";
									path += selectedEmpBean.empCode;
									path += "." + selectedEmpBean.imgExtend;
							}
							$("#emp_img").attr("src", path);
						}

						/* 사원 삭제 버튼 눌렀을 때 실행되는 함수 */
						function removeEmpListD() {
							
							var selectedRowIds = deptGridf.api.getSelectedRows();
							//console.log(selectedRowIds[0]);
							if (selectedRowIds.length == 0) {
								alert("삭제할 사원을 선택해 주세요");
								return;
							}

							var selectedRowData = [];
							$.each(selectedRowIds, function(index, id) {
								var data = selectedRowIds[0];
								selectedRowData.push(data);
							});

							var sendData = JSON.stringify(selectedRowData);

							$.ajax({
										url : "${pageContext.request.contextPath}/emp/empDetail.do",
										data : {
											"method" : "removeEmployeeList",
											"sendData" : sendData
										},
										dataType : "json",
										success : function(data) {
											if (data.errorCode < 0) {
												alert("정상적으로 삭제되지 않았습니다");
											} else {
												alert("삭제되었습니다");
											}

											location.reload();
										}
									});
						}

						function removeEmpListN() {
							var selectedRowIds = nameGridf.rowData[0];
							//console.log(nameGridf.rowData[0]);

							if (selectedRowIds == null) {
								alert("삭제할 사원을 선택해 주세요");
								return;
							}

							var selectedRowData = [];

							$.each(selectedRowIds, function(index, id) {
								var data = selectedRowIds;
								selectedRowData.push(data);
							});

							var sendData = JSON.stringify(selectedRowData);

							$
									.ajax({
										url : "${pageContext.request.contextPath}/emp/empDetail.do",
										data : {
											"method" : "removeEmployeeList",
											"sendData" : sendData
										},
										dataType : "json",
										success : function(data) {
											if (data.errorCode < 0) {
												alert("정상적으로 삭제되지 않았습니다");
											} else {
												alert("삭제되었습니다");
											}

											location.reload();
										}
									});
						}

						/*//////////////////////////////////////// 세부상세정보 그리드 띄우기 //////////////////////////////////////////////*/

						function showDetailInfo() {

							$("#empCode").val(selectedEmpBean.empCode);
							$("#empName").val(selectedEmpBean.empName);
							$("#deptName").val(selectedEmpBean.deptName);
							$("#position").val(selectedEmpBean.position);
							$("#birthdate").val(selectedEmpBean.birthdate);
							$("#gender").val(selectedEmpBean.gender);
							$("#mobileNumber")
									.val(selectedEmpBean.mobileNumber);
							$("#address").val(selectedEmpBean.address);
							$("#detailAddress").val(
									selectedEmpBean.detailAddress);
							$("#postNumber").val(selectedEmpBean.postNumber);
							$("#email").val(selectedEmpBean.email);
							$("#lastSchool").val(selectedEmpBean.lastSchool);
							if(selectedEmpBean.imgExtend!=""){
								$("#profileImg").attr("src"
										,"${pageContext.request.contextPath}/profile/"
										+selectedEmpBean.empCode+"."+selectedEmpBean.imgExtend);
							} else{
								$("#profileImg").attr("src"
										,"${pageContext.request.contextPath}/profile/profile.png");
							}
						}

						//재직정보 그리드 호출
						function showWorkInfoListGrid() {
							var columnDefs = [ {
								headerName : "사원코드",
								field : "empCode",
								editable : false
							}, {
								headerName : "적용일",
								field : "workInfoDays",
								editable : true,
								cellEditor : 'datePicker1'
							}, {
								headerName : "입사일",
								field : "hiredate",
								editable : true,
								cellEditor : 'datePicker'
							}, {
								headerName : "퇴사일",
								field : "retireDate",
								editable : true,
								cellEditor : 'datePicker'
							}, {
								headerName : "직종",
								field : "occupation",
								editable : true
							}, {
								headerName : "고용형태",
								field : "employmentType"
							}, {
								headerName : "호봉",
								field : "hobong"
							}, {
								headerName : "직급",
								field : "position"
							}, {
								headerName : "부서",
								field : "deptName"
							}, {
								headerName : "상태",
								field : "status"
							} ];
							var gridOptions = {
								columnDefs : columnDefs,
								rowData : updatedEmpBean.workInfoList,
								defaultColDef : {
									editable : true,
									width : 100,
									resizable : true,
									sortable : true, //정렬
									filter : true
								},
								components : {
									datePicker : getDatePicker(),
									datePicker1 : getDatePickerNumber()
								},
								onRowClicked :  function(rowId){
									console.log(rowId.rowIndex);
								},
								rowSelection : 'multiple', /* 'single' or 'multiple',*/
								enableRangeSelection :  false, //true일때 엑셀처럼 각 셀들을 범위로 선택할수있다
								suppressRowClickSelection : false, //false일경우 각각의 셀을 선택할수 없고 row전체가 선택된다
								animateRows : false, //정렬, 추가, 삭제등등 변화가 있을때 애니메이션효과
								suppressHorizontalScroll : true, //true일때 가로스크롤 안생김
								localeText : { //데이터가 없는 경우 보여 주는 사용자 메시지
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
								//cell 편집을 시작할 때(편집 허용인 칼럼을 더블 클릭할 때) 발생하는 이벤트.
								onCellEditingStarted : function(event) {
									console.log('cellEditingStarted');
								},
							};
							$('#workInfoListGrid').children().remove();
							var eGridDiv = document 
									.querySelector('#workInfoListGrid');
							workGrid= gridOptions;
							workGrid.name="workGrid";
							new agGrid.Grid(eGridDiv, gridOptions);
						}

						//가족정보 그리드 호출
						function showFamilyInfoListGrid() {
							var columnDefs = [ {
								headerName : "사원코드",
								field : "empCode",
								editable : false
							}, {
								headerName : "일련번호",
								field : "familyCode",
								editable : true
							}, {
								headerName : "성명",
								field : "familyName",
								editable : true
							}, {
								headerName : "관계",
								field : "relation",
								editable : true
							}, {
								headerName : "생년월일",
								field : "birthdate",
								editable : true,
								cellEditor : 'datePicker'
							}, {
								headerName : "동거여부",
								field : "liveTogether",
								editable : true
							}, {
								headerName : "상태",
								field : "status"
							} ];
							var gridOptions = {
								columnDefs : columnDefs,
								rowData : updatedEmpBean.familyInfoList,
								defaultColDef : {
									editable : true,
									width : 100,
									resizable : true,
									sortable : true, //정렬
									filter : true
								},
								components : {
									datePicker : getDatePicker(),
									datePicker1 : getDatePickerNumber()
								},
								rowSelection : 'multiple', /* 'single' or 'multiple',*/
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
							};
							$('#familyInfoListGrid').children().remove();
							var eGridDiv = document
									.querySelector('#familyInfoListGrid');
							familyGrid = gridOptions;
							familyGrid.name="familyGrid";
							new agGrid.Grid(eGridDiv, gridOptions);
						}

						//경력정보 그리드 호출
						function showCareerInfoListGrid() {
							var columnDefs = [ {
								headerName : "사원코드",
								field : "empCode",
								editable : false
							}, {
								headerName : "일련번호",
								field : "careerCode",
								editable : true
							}, {
								headerName : "회사명",
								field : "companyName",
								editable : true
							}, {
								headerName : "직종",
								field : "occupation",
								editable : true
							}, {
								headerName : "담당업무",
								field : "assignmentTask",
								editable : true
							}, {
								headerName : "입사일",
								field : "exHiredate",
								cellEditor : 'datePicker'
							}, {
								headerName : "퇴사일",
								field : "exRetirementDate",
								cellEditor : 'datePicker'
							}, {
								headerName : "상태",
								field : "status"
							} ];
							var gridOptions = {
								columnDefs : columnDefs,
								rowData : updatedEmpBean.careerInfoList,
								defaultColDef : {
									editable : true,
									width : 100,
									resizable : true,
									sortable : true, //정렬
									filter : true
								},
								components : {
									datePicker : getDatePicker(),
									datePicker1 : getDatePickerNumber()
								},
								rowSelection : 'multiple', /* 'single' or 'multiple',*/
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
							};
							$('#careerInfoListGrid').children().remove();
							var eGridDiv = document
									.querySelector('#careerInfoListGrid');
							careerGrid = gridOptions;
							careerGrid.name="careerGrid";
							new agGrid.Grid(eGridDiv, gridOptions);
						}

						//학력정보 그리드 호출
						function showEducationInfoListGrid() {
							var columnDefs = [ {
								headerName : "사원코드",
								field : "empCode",
								editable : false
							}, {
								headerName : "일련번호",
								field : "educationCode",
								editable : true
							}, {
								headerName : "학교명",
								field : "schoolName",//원래 shcoolName로 오타 나있었음
								editable : true
							}, {
								headerName : "전공",
								field : "major",
								editable : true
							}, {
								headerName : "입학일",
								field : "entranceDate",
								cellEditor : 'datePicker'
							}, {
								headerName : "졸업일",
								field : "graduateDate",
								cellEditor : 'datePicker'
							}, {
								headerName : "학점",
								field : "grade"
							}, {
								headerName : "상태",
								field : "status"
							} ];
							var gridOptions = {
								columnDefs : columnDefs,
								rowData : updatedEmpBean.educationInfoList,
								defaultColDef : {
									editable : true,
									width : 100,
									resizable : true,
									sortable : true, //정렬
									filter : true
								},
								components : {
									datePicker : getDatePicker(),
									datePicker1 : getDatePickerNumber()
								},
								rowSelection : 'multiple', /* 'single' or 'multiple',*/
								enableRangeSelection : true,
								suppressRowClickSelection : false,
								animateRows : true,
								suppressHorizontalScroll : true,
								localeText : {
									noRowsToShow : "There's no result."
								},
								getRowStyle : function(param) {
									//console.log(param.node.data);
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
							};
							$('#educationInfoListGrid').children().remove();
							var eGridDiv = document
									.querySelector('#educationInfoListGrid');
							educationGrid = gridOptions;
							educationGrid.name="educationGrid";
							new agGrid.Grid(eGridDiv, gridOptions);
						}

						//자격증정보 그리드 호출
						function showLicenseInfoListGrid() {
							var columnDefs = [ {
								headerName : "사원코드",
								field : "empCode",
								editable : false
							}, {
								headerName : "일련번호",
								field : "licenseCode",
								editable : true
							}, {
								headerName : "자격명",
								field : "licenseName",
								editable : true
							}, {
								headerName : "입학일",
								field : "getDate",
								cellEditor : 'datePicker',
								editable : true
								
							}, {
								headerName : "졸업일",
								field : "expireDate",
								cellEditor : 'datePicker',
								editable : true
							}, {
								headerName : "급수",
								field : "licenseLevel",
								editable : true
							}, {
								headerName : "발행기관",
								field : "licenseCenter",
								editable : true
							}, {
								headerName : "발급번호",
								field : "issueNumber",
								editable : true
							}, {
								headerName : "상태",
								field : "status"
							} ];
							var gridOptions = {
								columnDefs : columnDefs,
								rowData : updatedEmpBean.licenseInfoList,
								defaultColDef : {
									editable : true,
									width : 100,
									resizable : true,
									sortable : true, //정렬
									filter : true
								},
								components : {
									datePicker : getDatePicker(),
									datePicker1 : getDatePickerNumber()
								},
								rowSelection : 'multiple', /* 'single' or 'multiple',*/
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
							};
							$('#licenseInfoListGrid').children().remove();
							var eGridDiv = document
									.querySelector('#licenseInfoListGrid');
							licenseGrid = gridOptions;
							licenseGrid.name="licenseGrid";
							new agGrid.Grid(eGridDiv, gridOptions);
						}

						$("#add_work_btn").click(addListGridRow);
						$("#add_career_btn").click(addListGridRow);
						$("#add_education_btn").click(addListGridRow);
						$("#add_license_btn").click(addListGridRow);
						$("#add_family_btn").click(addListGridRow);

						$("#del_work_btn").click(delListGridRow);
						$("#del_career_btn").click(delListGridRow);
						$("#del_education_btn").click(delListGridRow);
						$("#del_license_btn").click(delListGridRow);
						$("#del_family_btn").click(delListGridRow);

						/* $("#deptName").click(function(){
						  getCode("CO-07","deptName");
						});
						$("#position").click(function(){
						  getCode("CO-04","position");
						}); */
						$("#gender").click(function() {
							getCode("CO-01", "gender");
						});
						$("#lastSchool").click(function() {
							getCode("CO-02", "lastSchool");
						});

						$("#birthdate").click(getDatePicker($("#birthdate")));

						$("#btn_dept_del").click(function() { // 부서명검색탭 삭제버튼
							var flag = confirm("선택한 사원을 정말 삭제하시겠습니까?");
							if (flag)
								removeEmpListD();
						});

						$("#btn_name_del").click(function() { // 이름삭제버튼
							var flag = confirm("선택한 사원을 정말 삭제하시겠습니까?");
							if (flag)
								removeEmpListN();
						});

						showEmpListDeptGrid();
						showEmpListNameGrid();
						showWorkInfoListGrid();
						showCareerInfoListGrid();
						showEducationInfoListGrid();
						showLicenseInfoListGrid();
						showFamilyInfoListGrid();

					});

	/*  사진찾기 버튼 눌렀을 때 실행되는 함수
		미리보기 기능으로 이미지를 띄워준다
	*/
	function readURL(input) {
		$("#emp_img_empCode").val(updatedEmpBean.empCode);
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				// 이미지 Tag의 SRC속성에 읽어들인 File내용을 지정 (아래 코드에서 읽어들인 dataURL형식)
				$("#profileImg").attr("src", e.target.result);
			}
			reader.readAsDataURL(input.files[0]); //File내용을 읽어 dataURL형식의 문자열로 저장
		}
	}
</script>


</head>
<body>
	<!-- 왼쪽창 -->
	<div id="tabs" style="width: 600px; height: 400px;">
		<ul>
			<li><a href="#tabs-11">부서명 검색</a></li>
			<li><a href="#tabs-1">사원명 검색</a></li>
		</ul>
		<!-- 부서명검색 탭 -->
		<div id="tabs-11">
			<select id="sel_dept">
				<option value="전체부서">전체부서</option>
				<option value="회계팀">회계팀</option>
				<option value="인사팀">인사팀</option>
				<option value="전산팀">전산팀</option>
				<option value="보안팀">보안팀</option>
			</select>
			<button id="btn_dept_search"
				class="ui-button ui-widget ui-corner-all">검색</button>
			<button id="btn_dept_del" class="ui-button ui-widget ui-corner-all">삭제</button>
			<br /> <br />
			<div id="deptfindgrid" style="height: 250px;" class="ag-theme-balham"></div>
			<!-- ag-theme-material -->

		</div>
		<!-- 사원명 검색탭 -->
		<div id="tabs-1">
			<input type="text" id="txt_name"
				class="ui-button ui-widget ui-corner-all">
			<button id="btn_name_search"
				class="ui-button ui-widget ui-corner-all">검색</button>
			<button id="btn_name_del" class="ui-button ui-widget ui-corner-all">삭제</button>
			<br /> <br />
			<div id="namefindgrid" style="height: 250px;" class="ag-theme-balham"></div>
		</div>

	</div>
	<br />
	<!-- 오른쪽 창 -->
	<div id="tabs1" style="width: 1050px; height: 1000px;">
		<ul>
			<li><a href="#tabs-0">기본 정보</a></li>
			<li><a href="#tabs-2">재직 정보</a></li>
			<li><a href="#tabs-3">경력 정보</a></li>
			<li><a href="#tabs-4">학력 정보</a></li>
			<li><a href="#tabs-5">자격증 정보</a></li>
			<li><a href="#tabs-6">가족 정보</a></li>
		</ul>
		<!-- 기본정보 -->
		<div id="tabs-0" align="left">
			<!-- 사진박스 -->
			<div id="divImg">
				<img id="profileImg"
					src="${pageContext.request.contextPath}/profile/profile.png"
					width="180px" height="200px"><br>
				<form id="emp_img_form"
					action="${pageContext.request.contextPath }/base/empImg.do"
					enctype="multipart/form-data" method="post">
					<input type="hidden" name="empCode" id="emp_img_empCode"> <input
						type="file" name="empImgFile" style="display: none;"
						id="emp_img_file" onChange="readURL(this)" accept="image/*">
					<!-- accept - 선택하는 파일의  종류를 제한한다-->
					<button type="button" style="width: 150px"
						class="ui-button ui-widget ui-corner-all" id="findPhoto">사진찾기</button>
					<br>
				</form>
				<br />
			</div>
			<!-- 상세정보박스1 -->
			<div id="divEmpInfo"
				style="display: inline-block; position: absolute; margin-left: 50px;">
				<br />
				<table>
					<tr>
						<td><font>사원코드</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="empCode" readonly></td>
					</tr>
					<tr>
						<td><font>이름</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="empName" readonly></td>
					</tr>
					<tr>
						<td><font>부서</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="deptName" readonly></td>
					</tr>
					<tr>
						<td><font>직급</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="position" readonly></td>
					</tr>
				</table>
				<!-- <font style="font-size: 10px">전화번호 </font><br/>-->
				<br />
				<!-- IMG_EXTEND -->
			</div>
			<hr>
			<!-- 선 -->
			<!-- 상제정보박스2 -->
			<div id="divEmpDinfo"
				style="display: block; position: relative; width: 700px; height: 300px; margin-left: 230px; margin-top: 20px">
				<table>
					<tr>
						<td><font>e-mail</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="email"></td>
					</tr>
					<tr>
						<td><font>휴대전화 </font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="mobileNumber"></td>
					</tr>
					<tr>
						<td><font>생년월일</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="birthdate" readonly></td>
					</tr>
					<tr>
						<td><font>성별</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="gender" readonly></td>
					</tr>
					<tr>
						<td><font>최종학력</font></td>
						<td><input class="ui-button ui-widget ui-corner-all"
							id="lastSchool" readonly></td>
					</tr>
					<tr>
						<td><font>주소</font></td>
						<td><input
							class="postcodify_address ui-button ui-widget ui-corner-all"
							id="address" readonly></td>
					</tr>
					<tr>
						<td><font>상세주소</font></td>
						<td><input
							class="postcodify_details ui-button ui-widget ui-corner-all"
							id="detailAddress"></td>
					</tr>
					<tr>
						<td><font>우편번호</font></td>
						<td><input
							class="postcodify_postcode5 ui-button ui-widget ui-corner-all"
							id="postNumber" readonly></td>
					</tr>
				</table>
				<input type="hidden" id="imgExtend">
			</div>
		</div>
		<!-- 재직정보 -->
		<div id="tabs-2">
			<input type="button" id="add_work_btn"
				class="ui-button ui-widget ui-corner-all" value="추가"> <input
				type="button" id="del_work_btn"
				class="ui-button ui-widget ui-corner-all" value="삭제"><br />
			<br />
			<div id="workInfoListGrid" style="height: 500px; width: 1020px"
				class="ag-theme-balham"></div>
		</div>
		<!-- 경력정보 -->
		<div id="tabs-3">
			<input type="button" id="add_career_btn"
				class="ui-button ui-widget ui-corner-all" value="추가"> <input
				type="button" id="del_career_btn"
				class="ui-button ui-widget ui-corner-all" value="삭제"><br />
			<br />
			<div id="careerInfoListGrid" style="height: 500px; width: 1020px"
				class="ag-theme-balham"></div>
		</div>
		<!-- 학력정보 -->
		<div id="tabs-4">
			<input type="button" id="add_education_btn"
				class="ui-button ui-widget ui-corner-all" value="추가"> <input
				type="button" id="del_education_btn"
				class="ui-button ui-widget ui-corner-all" value="삭제"><br />
			<br />
			<div id="educationInfoListGrid" style="height: 500px; width: 1020px"
				class="ag-theme-balham"></div>
		</div>
		<!-- 자격증정보 -->
		<div id="tabs-5">
			<input type="button" id="add_license_btn"
				class="ui-button ui-widget ui-corner-all" value="추가"> <input
				type="button" id="del_license_btn"
				class="ui-button ui-widget ui-corner-all" value="삭제"><br />
			<br />
			<div id="licenseInfoListGrid" style="height: 500px; width: 1020px"
				class="ag-theme-balham"></div>
		</div>
		<!-- 가족정보 -->
		<div id="tabs-6">
			<input type="button" id="add_family_btn"
				class="ui-button ui-widget ui-corner-all" value="추가"> <input
				type="button" id="del_family_btn"
				class="ui-button ui-widget ui-corner-all" value="삭제"><br />
			<br />
			<div id="familyInfoListGrid" style="height: 500px; width: 1020px"
				class="ag-theme-balham"></div>
		</div>
		<br />

		<!-- 저장/취소버튼 -->
		<input type="button" id="modifyEmp_Btn"
			class="ui-button ui-widget ui-corner-all" value="저장"> <input
			type="button" id="can_work_btn"
			class="ui-button ui-widget ui-corner-all" value="취소">
	</div>


</body>
</html>