<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공제기준관리</title>
<style type="text/css">
section {
    padding: 30px 0;
}

section .section-title {
    text-align: center;
    color: #007b5e;
    text-transform: uppercase;
}
#tabs{
	

    background-color: #BDBDBD;
}

#flo{
	/* display: inline-block; */
	float:left;
	
	height:100px;
	/* text-align:center; */
}
</style>
<script>
$(document).ready(function(){
	
	$("#search").click(searchFunction);
	$("#SLRY_CLSFC").change(function() {
		$("#PYMNT_DDCTN").focus();
	});
	$("#PYMNT_DDCTN").change(function() {
		$("#year").focus();
	});
	$("#year").on('keyup', function() {
			 if (window.event.keyCode == 13)  
			alert(213);
		if($("#year").val().length>3){
			slryClsfc=$("#SLRY_CLSFC").val()
			pymntDdctn=$("#PYMNT_DDCTN").val()
			year=$("#year").val()
			showPaymentItemName();
			showJobCodeGrid();
			showPriorityGrid();
		}
	});
	pymntDditmList=[];
	jobCodeList=[];
	dissectionList=[];
	PYMNTITMOPTList=[];
	$("#1").val("");
	$("#2").val("");
	$("#3").val("");
	$("#4").val("");
	$("#5").val("");
	$("#6").val("");
	$("#7").val("");
	$("#8").val("");
	$("#9").val("");
	$("#10").val("");
	$("#11").val("");
	$("#12").val("");
	showPriorityGrid()
	showJobCodeGrid();
	showPaymentItmNameListGrid();
	showPriorityGrid();
	showDissectionGrid();
});

function searchFunction(){
	slryClsfc=$("#SLRY_CLSFC").val()
	pymntDdctn=$("#PYMNT_DDCTN").val()
	year=$("#year").val()
	showPaymentItemName();
	showJobCodeGrid();
	showPriorityGrid();
	
}





//지급항목명
var slryClsfc;
var pymntDdctn;
var year;
var pymntDditmList;
//지급공제항목설정
var PYMNTITMOPTList;
var code;
//직종코드
var jobCodeList;
var priorityCode;
var jobCode;
//분류구분코드
var disecPrioCode;
var dissectionList;
//금액계산
var amnclFrmlCode;
var priorityCode2;


/* ag그리드에 쓰일 ajax *////////////////////////////////////////////////////////
function showPaymentItemName(){
	$.ajax({
		url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
		data:{
			"method" : "findPymntDditm",
			"slryClsfc" : slryClsfc,
			"pymddClsfc" : pymntDdctn,
			"year" : year
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
			console.log(data)
			pymntDditmList = data.pymntDditmList;
			
			//직종
			jobCodeList=[];
			//분류구분
			dissectionList=[];
			//순위
			PYMNTITMOPTList=[];
			
			$("#prioNum1").val("")
			$("#prioName1").val("")
			$("#prioNum2").val("")
			$("#prioName2").val("")
			showDissectionGrid();
			showJobCodeGrid();
			
			showPaymentItmNameListGrid();
			showPriorityGrid()
		}
	});
};
function showPaymentItmNameListGrid(){
	NaLiGriOpt=this.gridOptions;
	
	 var columnDefs = [
	      {headerName: "코드", field: "code", width: 50},
	      {headerName: "지급항목명", field: "pymntItmName"}
	 ];  
	 var addr=this.gridOptions
	gridOptions = {
		columnDefs: columnDefs,
		rowData: pymntDditmList,
		defaultColDef: { editable: false },
		rowSelection: 'single', 
		onGridSizeChanged: function (event) { event.api.sizeColumnsToFit(); },//스크롤 없앰
		onRowClicked : function(event) { 
			jobCode="";
			
			setPaymentItmNodeOption(event.node.data);
		}
	}
	

	$('#pymntDditmList_grid').children().remove(); /* 자식노드를 지워서 중복생성 방지 */
	
	var eGridDiv = document.querySelector('#pymntDditmList_grid');
	new agGrid.Grid(eGridDiv, gridOptions);
	gridOptions.api.sizeColumnsToFit();
}

//지급공제항목설정///////////////////////////////////////////////////////////////////////////////
function setPaymentItmNodeOption(sendData){
//초기화
	//직종코드
	jobCodeList=[];
	//분류구분코드
	dissectionList=[];
	$("#prioNum1").val("")
	$("#prioName1").val("")
	$("#prioNum2").val("")
	$("#prioName2").val("")
	priorityCode="";
	showDissectionGrid();
	showJobCodeGrid();
	
	showPaymentItmNameListGrid();
	showPriorityGrid();
	
	code=sendData.code
	
/* 	if(code=="P00" || code=="V00" || code=="ZY1"){
		$("#AMNCL_FRML_div").innerHtml="<Textarea id=AMNCL_FRML_text style='border: 1px solid gold; width: 88%; height: 100px; float:left'></Textarea>";
	}else{
		$("#AMNCL_FRML_div").innerHtml="<input type=text id=AMNCL_FRML_text style='width:88%'>";
	} */
	
	$.ajax({
		url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
		data:{
			"method" : "setPaymentItmNodeOption",
			"code" : code,
			"slryClsfc" : slryClsfc,
			"pymntDdctn" : pymntDdctn,
			"year" : year
		},
		dataType:"json",
		success : function(data){
			console.log(data);
			console.log(data.paymentItmOptionRs[0].monthlyWage);
			PYMNTITMOPTList = data.paymentItmOptionRs[0];
			var codeCheck=PYMNTITMOPTList.code;
			$("#1").val(PYMNTITMOPTList.taxationClassification);
			$("#2").val(PYMNTITMOPTList.taxFreeType);
			$("#3").val(PYMNTITMOPTList.reductionStatus);
			$("#4").val(PYMNTITMOPTList.apprenticeApplication);
			$("#5").val(PYMNTITMOPTList.monthlyWage);
			$("#6").val(PYMNTITMOPTList.cutSelection);
			$("#7").val(PYMNTITMOPTList.classificationStatus);
			$("#8").val(PYMNTITMOPTList.calculationClassification);
			$("#9").val(PYMNTITMOPTList.applyLeaveAbsence);
			$("#10").val(PYMNTITMOPTList.applicationDropParty);
			$("#11").val(PYMNTITMOPTList.employmentInsurance);
			$("#12").val(PYMNTITMOPTList.exclsClcd);
			PYMNTITMOPTList = data.paymentItmOptionRs;
			$("#AMNCL_FRML_text").val("")
			if(code=="P40") setAmnclFrmlText("P40");
					
					
					
					
					
				if(PYMNTITMOPTList[0].clsfcCdcl == "직종별"){
						$("#prioNum1").val(PYMNTITMOPTList[0].priority)
						$("#prioName1").val(PYMNTITMOPTList[0].clsfcCdcl)
						//책깔피
						showPriorityGrid();
						priorityCode=PYMNTITMOPTList[0].priorityCode;
						showJobCode();
				}else{
				$("#prioNum2").val(PYMNTITMOPTList[0].priority)
				$("#prioName2").val(PYMNTITMOPTList[0].clsfcCdcl)
				showPriorityGrid();
				setDissection2();
				//책깔피
				showJobCode();
				}
			
		}
	});
}
//우선순위 그리드
function showPriorityGrid(){
	var columnDefs = [
		{headerName: "순위", field: "priority", width: 50},
	    {headerName: "코드", field: "priorityCode", width: 50},
		{headerName: "분류코드구분명", field: "clsfcCdcl"}
	];  
	var gridOpt;
	gridOptions = {
		columnDefs: columnDefs,
		rowData: PYMNTITMOPTList,
		defaultColDef: { editable: false },
		rowSelection: 'single', 
		onGridSizeChanged: function (event) { event.api.sizeColumnsToFit(); },//스크롤 없앰
		onRowClicked : function(event) {
		/* alert(JSON.stringify(event.node.data))*/
		/*  if() */
		/* showPaymentItmNodeOption(event.node.data) */}
	}

	$('#PRIORITY_grid').children().remove(); /* 자식노드를 지워서 중복생성 방지 */
	var eGridDiv = document.querySelector('#PRIORITY_grid');
	new agGrid.Grid(eGridDiv, gridOptions);
	gridOptions.api.sizeColumnsToFit();
}
//직종코드////////////////////////////////////////////////////////////////////////////
function showJobCode(){
	$.ajax({
		url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
		data:{
			"method" : "setJobCode",
			"priorityCode" : priorityCode,
			"slryClsfc" : slryClsfc
		},
		dataType:"json",
		success : function(data){
			console.log(data);
			jobCodeList=data.jobCodeRs;
			
			showJobCodeGrid();
		}
	});
}

function showJobCodeGrid(){
	 var columnDefs = [
	     {headerName: "코드", field: "jobCode", width: 100},
	     {headerName: "분류명", field: "categoryName"}
	 ];  
	gridOptions = {
		columnDefs: columnDefs,
		rowData: jobCodeList,
		defaultColDef: { editable: true },
		rowSelection: 'single', 
		onGridSizeChanged: function (event) { event.api.sizeColumnsToFit(); },//스크롤 없앰
		onRowClicked : function(event) { setDissection1(event.node.data) }
	}

	$('#JOBCODE_grid').children().remove(); /* 자식노드를 지워서 중복생성 방지 */
	var eGridDiv = document.querySelector('#JOBCODE_grid');
	new agGrid.Grid(eGridDiv, gridOptions);
	gridOptions.api.sizeColumnsToFit();
}
/////////////////////////////////////////////////////////////////////////////

function setDissection1(sendData){
	jobCode = sendData.jobCode;
	$("#prioNum2").val(PYMNTITMOPTList[1].priority)
	$("#prioName2").val(PYMNTITMOPTList[1].clsfcCdcl)
	if(PYMNTITMOPTList[0].priorityCode==005){
		disecPrioCode=PYMNTITMOPTList[1].priorityCode
		}else{
		disecPrioCode=PYMNTITMOPTList[0].priorityCode
	}
	$.ajax({
		url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
		data:{
			"method" : "setDissection",
			"jobCode" : jobCode,
			"disecPrioCode" : disecPrioCode,
			"num" : "1"
		},
		dataType:"json",
		success : function(data){
			console.log(data);
			dissectionList=data.dissectionRs;
			showDissectionGrid();
		}
	});
}

													function setDissection2(){
														
														disecPrioCode=PYMNTITMOPTList[0].priorityCode
														
														$.ajax({
															url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
															data:{
																"method" : "setDissection",
																"disecPrioCode" : disecPrioCode,
																"num" : "2"
															},
															dataType:"json",
															success : function(data){
																console.log(data);
																dissectionList=data.dissectionRs;
																showDissectionGrid();
															}
														});
													}

													function showDissectionGrid(){
														 var columnDefs = [
														     {headerName: "코드", field: "categoryCode", width: 50},
														     {headerName: "분류명", field: "categoryName", width: 50},
														     {headerName: "계산구분", field: "CLCLT_CLSFC"}
														 ];  
														gridOptions = {
															columnDefs: columnDefs,
															rowData: dissectionList,
															defaultColDef: { editable: true },
															rowSelection: 'single', 
															onGridSizeChanged: function (event) { event.api.sizeColumnsToFit(); },//스크롤 없앰
															onRowClicked : function(event) {
																				setAmnclFrmlText( event.node.data );
																			}
														}
													
														$('#DISSECTION_grid').children().remove(); /* 자식노드를 지워서 중복생성 방지 */
														var eGridDiv = document.querySelector('#DISSECTION_grid');
														new agGrid.Grid(eGridDiv, gridOptions);
														gridOptions.api.sizeColumnsToFit();
													}
													
							function setAmnclFrmlText(sendData){
								if(sendData!="P40")
								amnclFrmlCode=sendData.categoryCode;
								
								if(jobCode=="JJC001" || jobCode=="JJC002"){
								
									
								$.ajax({
									
									url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
									data:{
										"method" : "setAmnclFrml",
										"slryClsfc" : slryClsfc,
										"pymntDdctn" : pymntDdctn,
										"code" : code,
										"jobCode" : jobCode,
										"year" : year,
										"disecPrioCode" : disecPrioCode,
										"amnclFrmlCode" : amnclFrmlCode,
										"num" : "3"
									},
									dataType:"json",
									success : function(data){
										
										console.log(data);
										$("#AMNCL_FRML_text").val(data.amnclFrmlRs[0].data);
									}
								});
								}else{
									if(code=="P40"){
										
										$.ajax({
											
											url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
											data:{
												"method" : "setAmnclFrml",
												"slryClsfc" : slryClsfc,
												"pymntDdctn" : pymntDdctn,
												"code" : code,
												"year" : year,
												"num" : "1"
											},
											dataType:"json",
											success : function(data){
												
												console.log(data);
												$("#AMNCL_FRML_text").val(data.amnclFrmlRs[0].data)
											}
										});
									}else{
										
								$.ajax({
									
									url:"${pageContext.request.contextPath}/salary/pymntDditm.do",
									data:{
										"method" : "setAmnclFrml",
										"slryClsfc" : slryClsfc,
										"pymntDdctn" : pymntDdctn,
										"code" : code,
										"year" : year,
										"disecPrioCode" : disecPrioCode,
										"amnclFrmlCode" : amnclFrmlCode,
										"num" : "2"
									},
									dataType:"json",
									success : function(data){
										
										console.log(data);
										$("#AMNCL_FRML_text").val(data.amnclFrmlRs[0].data)
									}
								});
								}}
							}
</script>
</head>
<body>
	<h4>지급공제항목등록</h4>
	<br/>
	<hr>
	<section id="tabs" style="width:1500px; height:900px;" class="wow fadeInDown">
		<div>
		<span style="margin-left:50px">급여구분</span>
		<span>
			<select id="SLRY_CLSFC" style="width: 200px; height: 20px;">
				<option>급여</option>
				<option>상여</option>								
			</select>								
		</span>
		<span style="margin-left:50px">지급/공제구분</span>
		<span>
			<select id="PYMNT_DDCTN" style="width: 200px; height: 20px;">
				<option>지급</option>
				<option>공제</option>								
			</select>								
		</span>
		<span style="margin-left:50px">귀속연도</span>
		<span>
			<input type="text" style="width: 150px; height: 20px;" id="year">
		</span>
		<span  style="margin-left:30px">
			<input type="button" id="search" style="width: 100px; height: 30px;" value="조회"class="btn btn-Light shadow-sm btnsize"> 
		</span>	
		</div>
		<br/>
		<br/>
		<!-- <div style=" float: left; width: 16.6%; height: 52px; padding: 10px;">
		<h6>급여구분</h6>
		</div>
		<div style=" float: left; width: 16.6%; height: 52px">
		<select class="btn btn-light btn-sm" id= "SLRY_CLSFC" style="font-Size: 15px;">
			<option>급여</option>
			<option>상여</option>
		</select>
		</div>
		
		<div style=" float: left; width: 16.6%; height: 52px; padding: 10px;">
		<h6>지급/공제구분</h6>
		</div>
		<div style=" float: left; width: 16.6%; height: 52px">
		<select class="btn btn-light btn-sm" id="PYMNT_DDCTN" style="font-Size: 15px;">
			<option>지급</option>
			<option>공제</option>
		</select>
		</div>
		
		<div style=" float: left; width: 16.6%; height: 52px; padding: 10px;">
		<h6>귀속연도</h6>
		</div>
		<div style=" float: left; width: 16.6%; height: 52px">
		<input type="text" class="btn btn-light btn-sm" id="year" maxlength="4" style=" width: 100px; font-Size: 15px;">
		</div>
		<span  style="margin-left:30px">
			<input type="button" id="search" value="조회"class="btn btn-Light shadow-sm btnsize"> 
		</span> -->
		
		<!-- 왼쪽 창 -->
		<div style=" width: 30%; height: 750px; float: left; ">
			<div style=" width: 100%; height: 285px;">
						<div id="pymntDditmList_grid" style="height: 285px; width: 100%; overflow: hidden;" class="ag-theme-balham"></div>
			</div>
		</div>
		<!-- 오른쪽 창 -->
		<div style=" width: 70%; height: 750px; float: left">
			<!-- 큰 내용들 -->
			<div style=" width: 100%; height: 380px;">
				<div style=" width: 100%; height: 75%;">
					<!-- 내용 div -->
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						과세구분
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=1>
							<option>과세
							<option>비과세
							<option>소득제외
						</select>
					</div>

					<!-- 공백 -->
					<div style=" width: 60%; height:33%; float: right;"></div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						비과세유형
						</div>
						<input type=text style=" width: 50px; text-align:left; float: left;" id=2>
						<input type=button style=" width: 25px; float: left;">
						<input type=text style=" width: 155px; text-align:left; float: left;">
					</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						감면여부
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=3>
							<option>부
							<option>여
						</select>
					</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						수습적용
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=4>
							<option>환경등록적용
							<option>정상적용
							<option>일할적용
							<option>미지급
						</select>
					</div>
					
												<div style=" width: 60%; height:11%; float: left;">
													<div style=" width: 30%; text-align:right; float: left;">
													입퇴사자적용
													</div>
													<select style=" width: 230px; text-align:left; float: left;" id=10>
														<option>환경등록적용
														<option>정상적용
														<option>일할적용
														<option>미지급
														<option>미지급(입사자만)
														<option>미지급(퇴사자만)
													</select>
												</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						월정급여
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=5>
							<option>포함
							<option>제외
						</select>
					</div>
					
												<div style=" width: 60%; height:11%; float: left;">
													<div style=" width: 30%; text-align:right; float: left;">
													고용보험(임금)
													</div>
													<select style=" width: 230px; text-align:left; float: left;" id=11>
														<option>포함
														<option>미포함
													</select>
												</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						절사선택
						</div>
						<input type=text style=" width: 50px; text-align:left; float: left;" id=6>
						<input type=button style=" width: 25px; float: left;">
						<input type=text style=" width: 155px; text-align:left; float: left;">
					</div>
					
					<!-- 공백 -->
					<div style=" width: 60%; height:22%; float: right;"></div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						분류여부
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=7>
							<option>무분류
							<option>분류
							<option>제외조건
						</select>
					</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						계산구분
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=8>
							<option>금액
							<option>계산
						</select>
					</div>
					
												<div style=" width: 60%; height:11%; float: left;">
													<div style=" width: 30%; text-align:right; float: left;">
													제외분류코드
													</div>
													<input type=text style=" width: 50px; text-align:left; float: left;" id=12>
													<input type=button style=" width: 25px; float: left;">
													<input type=text style=" width: 155px; text-align:left; float: left;">
												</div>
					
					<div style=" width: 40%; height:11%; float: left;">
						<div style=" width: 30%; text-align:right; float: left;">
						휴직자적용
						</div>
						<select style=" width: 70%; text-align:left; float: left;" id=9>
							<option>정상적용
							<option>휴직계산식적용
						</select>
					</div>
					<input type=button value="휴직계산식적용" style="width: 250px; float:left;">
					
					<!-- -------------------------------------------------------------------------------- -->
				</div>
			<div style=" width: 100%; height: 100px;">
				<!-- 우선순위그리드 -->
				<div style=" width: 100%; height: 100px;">
					<div id="PRIORITY_grid" style="height: 100%; width: 50%; float: left" class="ag-theme-balham"></div>
				</div>
			</div>
			<!-- 작은 그리드 2개 -->
				<div style=" width: 50%; height: 200px; float:left">
					<div style=" width: 100%; height: 15%;">
						순위<input type=text id=prioNum1 style="width: 30px">
						<input type=text id=prioName1 style="width: 100px">
					</div>
					<div id="JOBCODE_grid" style="height: 85%; width: 100%; float: left" class="ag-theme-balham"></div>
				</div>
																	<div style=" width: 50%; height: 200px; float:left">
																		<div style=" width: 100%; height: 15%;">
																			순위<input type=text id=prioNum2 style="width: 30px">
																			<input type=text id=prioName2 style="width: 100px">
																		</div>
																		<div id="DISSECTION_grid" style="height: 85%; width: 100%;" class="ag-theme-balham"></div>
																	</div>
			</div>
			<div style=" width: 100%; height: 100px; float:left">
				<div style=" width: 12%; height: 100px; float:left">
					<input type=button value="금액/계산식" id=AMNCL_FRML_button>
				</div>
				<div id=AMNCL_FRML_div><!-- 선택한것에 따라 바뀌어야 할 부분 -->
					<input type=text id=AMNCL_FRML_text style="width:88%">
				</div><!-- 책깔피 -->
			</div>
		</div>
</section>
</body>
</html>