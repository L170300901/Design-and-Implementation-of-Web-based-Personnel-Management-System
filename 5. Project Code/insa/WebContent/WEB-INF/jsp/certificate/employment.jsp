<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<script>
var empCode = "${sessionScope.code}";
var usage = "";
var requestDay = "";
var useDay = "";
var eMail= "";
var certificateRequestList=[];

$(document).ready(function(){
   
   $("#deptName").val("${sessionScope.dept}")
   
   $("#usage").click(function(){
      getCode('T04',"usage","usageCode");
   });      
   
   showCertificateListGrid();
   
   $("#employment").tabs();  
   
   var today=$.datepicker.formatDate($.datepicker.ATOM,new Date());      
   $("#requestDate").val(today);   
   
   
/* 신청탭 */
   
   	/* 신청, 사용 날짜 */
    getDatePicker($("#requestDate"));      
   $("#requestDate").change(function(){
         $("#endDate").datepicker("option","minDate",$(this).val());                      
      }); 
   
      getDatePicker($("#endDate"));
      $("#endDate").change(function(){
         $("#requestDate").datepicker("option","maxDate",$(this).val());                  
      }); 
      
      /* 재직증명서 신청 버튼  */
      $("#issuance_employmen_Btn").click(requireCertificate);
      
      /* 재직증명서 삭제 버튼 */
      $("#delete_employmen_Btn").click(function(){
         var flag = confirm("재직증명서 신청을 취소하시겠습니까?");
         if(flag)
            removeCertificateList(); //
      });

      
/* 조회탭 */
      /* 조회 날짜 */   
      getDatePicker($("#search_startDate"));
      $("#search_startDate").change(function(){ 
         $("#search_endDate").datepicker("option","minDate",$(this).val());
      }); 
      
      getDatePicker($("#search_endDate"));
      $("#search_endDate").change(function(){ 
         $("#search_startDate").datepicker("option","maxDate",$(this).val());
      });                 
     
      /* 재직증명서 조회 버튼 */
      $("#search_employmentList_Btn").click(findCertificateList);  
      
      /* 재직증명서 발급 버튼 */
      $("#print_certificate").click(printWorkInfoReport);
      
      /*e-mail전송*/
      $("#send_Btn").click(sendEmail); 
   
});

function requireCertificate(){   
	if($("#usageCode").val().trim()==""){
		alert("Please input the purpose");
		return ;
	}
	if($("#etc").val().trim()==""){
		alert("Please write a comment");
		return ;
	}
   var check=false;
   var requestDate=$("#requestDate").val();
   var useDate=$("#endDate").val();
   var usageCode=$("#usageCode").val();
   var etc=$("#etc").val();   
   
   var certificateList={
         "empCode":empCode,         
         "requestDate": requestDate,
         "useDate": useDate,
         "usageCode": usageCode,
         "etc":etc,
         "approvalStatus":"Waiting for approval"         
   }
   
   var sendData=JSON.stringify(certificateList);   
    
    $.ajax({
           url : "${pageContext.request.contextPath}/certificate/certificate.do",
           data:{
              "method":"registRequest",
              "sendData":sendData
           },
           dataType:'json',
           success:function(data){
              if(data.errorCode<0){
                 var error=/unique constraint/;
                 if(error.test(data.errorMsg)){
                    alert("There's a certificate of employment that I applied for on that date.");
                 }else{
                    var str = "There was an internal server error\n";
                  str += "Please contact your manager\n";
                  str += "Error position : " + $(this).attr("id");
                  str += "Error message : " + data.errorMsg;
                  alert(str);   
                 }                 
              }else{
                 alert("applied for a certificate of employment.");
              }
              location.reload();
           }
        });    
}

function findCertificateList(){ // 재직증명서신청조회버튼
   var startVar = $("#search_startDate").val(); //시작날짜
   var endVar = $("#search_endDate").val(); //종료날짜
   
   //alert(startVar);
   
    $.ajax({
      url:"${pageContext.request.contextPath}/certificate/certificate.do",
      data:{
         "method" : "findCertificateList",
         "empCode" : empCode,  //session으로 사원코드가져옴 
         "startDate" : startVar, // 시작날짜
         "endDate" : endVar,   //종료날짜      
      },
      dataType:"json",
      success : function(data){
         if(data.errorCode < 0){
            var str = "There was an internal server error.\n";
            str += "Please contact your manager.\n";
            str += "Error position : " + $(this).attr("id");
            str += "Error message : " + data.errorMsg;
            alert(str);
            return;
         }
         certificateRequestList = data.certificateList;   
         showCertificateListGrid();
         
      }
   }); 
}

/* 삭제버튼 눌렀을 때 실행되는 함수 */
function removeCertificateList(){
   var selectedRowData=gridOptions.api.getSelectedRows();
   
/*    var selectedRowIds=$("#certificateList_grid").getGridParam("selarrrow");
   if(selectedRowIds.lengt==0){
      alert("취소할 재직증명서 신청을 선택해 주세요");
      return;
   }
   
   var selectedData=[];
   
   $.each(selectedRowIds,function(index,id){
      var data=$("#certificateList_grid").getRowData(id);
      if(data.approvalStatus!="승인"){
         selectedData.push(data);
      }
   });
   
   if(selectedData.length==0){
      alert("승인이 완료되어 신청을 취소할 수 없습니다");
      return;
   } */
   
   var sendData=JSON.stringify(selectedRowData);   
   $.ajax({
      url : "${pageContext.request.contextPath}/certificate/certificate.do",
      data : {
         "method" : "removeCertificateRequest",
         "sendData" : sendData
      },
      dataType : "json",
      success : function(data) {
         if(data.errorCode < 0){
            var str = "There was an internal server error.\n";
            str += "Please contact your manager.\n";
            str += "Error position : " + $(this).attr("id");
            str += "Error message : " + data.errorMsg;
            alert(str);
         } else {
            alert("It's been deleted");
             findCertificateList();
         }
         
      }
   });
}

function showCertificateListGrid(){
      var columnDefs = [
         {headerName: "empCode", field: "empCode",hide:true }, //hide:true = 숨김 
         {headerName: "Purpose", field: "usageName",checkboxSelection: true}, //체크박스옵션
         {headerName: "Application Date", width: 50, field: "requestDate"},
         {headerName: "Used Date", width: 50,field: "useDate"},
         {headerName: "Approval status", field: "approvalStatus"},
         {headerName: "Reason for Refusal", field: "rejectCause"},
         {headerName: "Note", field: "etc"}
   ];    
   gridOptions = {
         columnDefs: columnDefs,
         rowData: certificateRequestList,
         defaultColDef: { editable: false, width: 100 }, //수정불가
         rowSelection: 'single', /* 'single' or 'multiple',*/
         enableColResize: true, //칼럼크기
         enableSorting: true, //정렬
         enableFilter: true, //필터
         enableRangeSelection: true, //정렬고정
         suppressRowClickSelection: false, //선택허용
         animateRows: true,
         suppressHorizontalScroll: true, //가로스크롤허용 
         localeText: {noRowsToShow: 'There is no inquiry result.'}, //데이터가없을경우보여질메세지
         getRowStyle: function (param) {
              if (param.node.rowPinned) {
                  return {'font-weight': 'bold', background: '#dddddd'};
              }
              return {'text-align': 'center'};
          },
          getRowHeight: function(param) {
              if (param.node.rowPinned) {
                  return 30;
              }
              return 24;
          },
          // GRID READY 이벤트, 사이즈 자동조정 
          onGridReady: function (event) {
              event.api.sizeColumnsToFit();
          },
          // 창 크기 변경 되었을 때 이벤트 
          onGridSizeChanged: function(event) {
              event.api.sizeColumnsToFit();
          },
          onCellEditingStarted: function (event) {
              console.log('cellEditingStarted');
          }, 
   };   
   $('#certificateList_grid').children().remove();    
   var eGridDiv = document.querySelector('#certificateList_grid');
   new agGrid.Grid(eGridDiv, gridOptions);   
 }


/* DatePicker 함수 */
function getDatePicker($CellId) {
         $CellId.datepicker({
            changeMonth : true,
            changeYear : true,
            dateFormat : "yy-mm-dd",
            showAnim: "slide",
            dayNamesMin : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
            monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
            yearRange: "2021:2023",
               });
  }
   
/* 코드선택 창 띄우기 */
function getCode(code, inputText, inputCode) {
	option = "width=300; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
	window.open(
			"${pageContext.request.contextPath}/base/detailCodeWindow.html?code="
					+ code + "&inputText=" + inputText + "&inputCode="
					+ inputCode, "newwins", option);
}
/* 승인된 재직증명서 발급 */
 function printWorkInfoReport(){
      var selectedRowData=gridOptions.api.getSelectedRows();
      var data=selectedRowData[0];
      console.log(data.empCode);
      
      if(data.approvalStatus=="Approval"){
         console.log(data.empCode);
         
         empCode=data.empCode;
         usage = data.usageName;
         requestDay = data.requestDate;
         useDay = data.useDate      
         //신청할때마다 변화는 정보들을 담아서 윈도우화면에 띄운다.
         window.open(
                "${pageContext.request.contextPath}/base/empReport.do?method=requestEmployment&empCode="
                      +empCode+"&usage="+usage+"&requestDay="+requestDay+
                      "&useDay="+useDay,"재직증명서","width=1280, height=1024");
      }else{
         alert("Only authorized employment certificates can be issued");
      }   
         
}



   /*메일전송*/
  function sendEmail(){
     var selectedRowData=gridOptions.api.getSelectedRows();
     var data=selectedRowData[0];
     eMail=$("#email_id").val().trim();

     if(data==null){ alert("Please select a certificate of employment");
    }else if(eMail=="") {alert("Please enter your email");
    }else{
    
     
      console.log(data);
      empCode=data.empCode;
      usage = data.usageName;
      requestDay = data.requestDate;
      useDay = data.useDate
      
   
   alert(usage+requestDay+useDay+eMail+"\n"+"completion of transmission");
    
    $.ajax({
        url : "${pageContext.request.contextPath }/base/sendEmail.do",
        data:{
           "method":"sendEmail",
           "empCode":empCode,
           "usage":usage,
           "requestDay":requestDay,
           "useDay":useDay,
           "eMail":eMail
        },
        dataType:'json',
        success:function(data){
           alert("Email sent");
        }
     });

   }


   }
/* 재직증명서 ireport */



</script>
<h4>Certificate of employment form</h4>
<hr/>
<center>
<div id="employment" style="width: 1500px; position: relative; display: inline-block;">
	<ul>
		<li><a href="#employmentRequest_tab">Application for employment certificate</a></li>
		<li><a href="#employmentList_tab">Inquiry/issuance/cancellation of employment certificate</a></li>
	</ul>
	<div id="employmentRequest_tab">
		<table>
			<tr>
				<td>Name of the employee</td>
				<td><input type="text"
					class="ui-button ui-widget ui-corner-all"
					value="${sessionScope.name}" readonly> <input type="hidden"
					id="employmentEmpCode" value="${ssesionScope.code}"></td>
				<td>Department</td>
				<td><input type="text" id="deptName"
					class="ui-button ui-widget ui-corner-all" value="${emp.deptName}"
					readonly></td>
			</tr>

			<tr>
				<td>Application date</td>
				<td><input type="text" class="ui-button ui-widget ui-corner-all" autocomplete="off" id="requestDate" readonly></td>
				<td>Date of use</td>
				<td><input class="ui-button ui-widget ui-corner-all" autocomplete="off" type="text" id="endDate"></td>

			</tr>

			<tr>
				<td>purpose</td>
				<td><input class="ui-button ui-widget ui-corner-all" autocomplete="off" id="usage" type="text"> <input type="hidden" id="usageCode"></td>
				<td>Remarks</td>
				<td><input class="ui-button ui-widget ui-corner-all"autocomplete="off"  type="text" id="etc"></td>
			</tr>

		</table>
		<hr>
		<div>
			<br> <input type="button" class="ui-button ui-widget ui-corner-all" id="issuance_employmen_Btn" value="Application">
		</div>


	</div>
	<div id="employmentList_tab">
		<span>Application date</span>
		<input type=text autocomplete="off" class="ui-button ui-widget ui-corner-all" id="search_startDate" readonly> 
		From
		<input type=text autocomplete="off" class="ui-button ui-widget ui-corner-all" id="search_endDate" readonly> 
		Until
		<br />
		<br /> 
		<input type="button" class="ui-button ui-widget ui-corner-all" id="search_employmentList_Btn" value="inquiry"> 
		<input type="button" class="ui-button ui-widget ui-corner-all" id="delete_employmen_Btn" value="Cancel the application"> 
		<input type="button" class="ui-button ui-widget ui-corner-all" id="print_certificate" value="Issuance of employment certificate"> 
		<input type=text class="ui-button ui-widget ui-corner-all" id="email_id" placeholder="Please enter your email"> 
		<input type="button" class="ui-button ui-widget ui-corner-all" id="send_Btn" value="Email sent">
		
		<br />
		<br /> <br />
		
		<div id="certificateList_grid" style="height: 230px; width: 1450px" class="ag-theme-balham"></div>
	</div>
	</center>
</div>