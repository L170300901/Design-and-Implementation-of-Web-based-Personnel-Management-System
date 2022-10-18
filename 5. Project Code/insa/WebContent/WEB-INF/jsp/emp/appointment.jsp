<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인사발령등록</title>
<style>
</style>
<script>
var appointment = [];
var monthSalary = {};
var yearSalary = {};
var appointmentTargetGridData = [];
var empCode = "${sessionScope.code}";

   $(document).ready(function(){
	// 인사발령정보 날짜 검색 버튼
      $("#appointmentSearch_Btn").click(); 
	
	  $("#search_startD").click(getDatePicker($("#search_startD")));
	  $("#search_endD").click(getDatePicker($("#search_endD")));

	      
	  $("#break_startD").click(getDatePicker($("#break_startD")));
	  $("#break_endD").click(getDatePicker($("#break_endD")));

      // input:text에 버튼 형식의 css 씌움
      $("input:text").button().css({
         width:"150px",
         height:"10px"
      });

      // 인사발령정보 탭
      $("#personalAppointInfo_tab").tabs().css({
         width:"1300px",
         height:"400px",
         padding:"10px",
         margin:"10px",
      });    

      // 발령대상자 탭
      $("#appointmentTargetInfo_tab").tabs().css({
         width:"1300px",
         height:"400px",
         margin:"10px 10px 10px 0px",
      });     
      
      // 버튼을 눌렀을 때 행 추가
       $("#add_appointment_btn").click(addListGridRow);
       $("#add_target_btn").click(addListGridRow);
       
      // 버튼을 눌렀을 때 행 삭제
       $("#del_appointment_btn").click(delListGridRow);
       $("#del_target_btn").click(delListGridRow);
       
      // 저장 버튼
     // $("#save_Btn").click();
   	appointInfoGrid();
   	appointmentTargetGrid();
   });

   function appointmentList(){
      var startVar = $("#search_startD").val();
      var endVar = $("#search_endD").val();
      var code = $("#selectTravelTypeCode").val();
      $.ajax({
         url:"${pageContext.request.contextPath}/emp/appointment.do",
         data:{
            "method" : "findAppointmentList",
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

            appointment = data.appointmentList;
            
            
            appointment.push({
               "No":appointment.No, 
                "apponitmentNum":appointment.apponitmentNum,
                "title":appointment.title,
                "apponitmentType":appointment.apponitmentType,
                "apponitmentDate":appointment.apponitmentDate,
                "apponitmentManager":appointment.apponitmentManager,
                "remark":appointment.remark,
                "approvalStatus":appointment.approvalStatus
            });


            /* 시간형태변경포문부분 */
            for(var index in travelList){
               travelList[index].startTime = getRealTime(travelList[index].startTime);
               travelList[index].endTime = getRealTime(travelList[index].endTime);
            }  

            appointmentTargetGrid(); 
            appointInfoGrid();
         }
      });
   }

    
   /* 날짜 조회창 함수 */
   function getDatePicker($Obj, maxDate) {
         $Obj.datepicker({
            defaultDate : "d",
            changeMonth : true,
            changeYear : true,
            dateFormat : "yy-mm-dd",
            dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
            monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
            yearRange: "1980:2020",
            showOptions: "up",
            maxDate : (maxDate==null? "+100Y" : maxDate)
         });
   }
   
   
   ////////////////////////////////////////////////////그리드이벤트////////////////////////////////////////////////////////     
   
   /* 그리드에 행 추가하는 함수 */
   function addListGridRow(){
         var key = $(this).attr("id").split("_")[1];
         var emptyBean = {
          /*   "family" : emptyFamilyInfoListBean,
            "career" : emptyCareerInfoListBean,
            "work" : emptyWorkInfoListBean,
            "education" : emptyEducationInfoListBean,
            "license" : emptyLicenseInfoListBean */
         }
         /* 
          * appRowData가 아닌 addRow로 cell을 추가하면 굉장히 편하지만 이 방법을 사용하는 이유는
          * appRow로 cell을 추가하면 editable에 따라 textbox가 자동으로 생성된 상태로 cell이 추가됨
          * 이걸 일일이 처리하는것보다 빈 객체를 가져와 처리하는게 편하기 때문에 addRowData를 사용
          */
         var nextSeq = Number($("#" + key + "InfoGrid").getGridParam("records")) + 1;
         $("#" + key + "InfoGrid").addRowData(nextSeq, emptyBean);
         // $(#workInfoListGrid).addRowData(1,emptyWorkInfoListBean);
         
   }
   
   /* 그리드에 행 삭제 */
   function delListGridRow(){
         var key = $(this).attr("id").split("_")[1];
         var selectedRowId = $("#" + key + "InfoGrid").getGridParam("selrow");
         if(selectedRowId != null){
            /* var data = $("#" + key + "InfoListGrid").getRowData(selectedRowId);
            if(data.status == "select" || data.status == "update")
               $("#" + key + "InfoListGrid").setCell(selectedRowId, "status", "delete");
            else if(data.status == "delete")
               $("#" + key + "InfoListGrid").setCell(selectedRowId, "status", "update");
            else */
               $("#" + key + "InfoGrid").delRowData(selectedRowId);
         } else {
            alert("삭제할 행을 선택해 주세요!");
         }
   }
   
   
    /* 인사발령정보 그리드 */
    
/*     $.ajax({
			url : "${pageContext.request.contextPath}/emp/appointment.do",
			dataType : "json",
			data : {
				method : "findHolidayList"
			},
			success : function(data) {
				holidayList = data.holidayList;
				emptyInfo = data.emptyHoilday;
				$("#holidayListGrid").jqGrid({
					data : holidayList,
					datatype : "local",
					colNames : [ "일자", "휴일명", "비고", "상태" ],
					colModel : [
           $.jgrid.gridUnload("#appointmentInfoGrid");
              $("#appointmentInfoGrid").jqGrid({
                 data : appointmentTargetGridData,
                 datatype : "local",
                 colNames : ['NO','발령호수','제목','발령구분','발령일자','발령담당자','비고','승인상태'],
                 colModel : [ {
                   name : 'No',
                
                }, {
                   name : 'apponitmentNum',
                
                }, {
                   name : 'title'
                }, {
                   name : 'apponitmentType'
                }, {
                   name : 'apponitmentDate'
                }, {
                   name : 'apponitmentManager'
                }, {
                   name : 'remark'
                }, {
                   name : 'approvalStatus',
                   width : 130
                }, ],
                width : 800,
                height : 200,
                rowNum : 1
              });
       }
       
   }); */
				
				
   function appointInfoGrid(){
       $.jgrid.gridUnload("#appointmentInfoGrid");
          $("#appointmentInfoGrid").jqGrid({
             data : appointmentTargetGridData,
             datatype : "local",
             colNames : ['NO','발령호수','제목','발령구분','발령일자','발령담당자','비고','승인상태'],
             colModel : [ {
               name : 'No',
               /* hidden : true */
            }, {
               name : 'apponitmentNum',
               /* hidden : true */
            }, {
               name : 'title'
            }, {
               name : 'apponitmentType'
            }, {
               name : 'apponitmentDate'
            }, {
               name : 'apponitmentManager'
            }, {
               name : 'remark'
            }, {
               name : 'approvalStatus',
               width : 130
            }, ],
            width : 800,
            height : 200,
            rowNum : 1
          });
   }
    
   /* 발령대상자 그리드 */
   function appointmentTargetGrid(){
       $.jgrid.gridUnload("#targetInfoGrid");
       $("#targetInfoGrid").jqGrid({
          data : monthSalary.monthDeductionList,
          datatype : "local",
          colNames : ['NO','발령호수','발령구분','사원번호','사원명','직급','부서','승인상태','비고'],
         colModel : [ {
            name : 'No',
            
         },{
            name : 'apponitmentNum',
            
         }, {
            name : 'apponitmentType'
         }, {
            name : 'empCode',
            
         }, {
            name : 'empName'
         }, {
            name : 'position'
         } ,
          {
            name : 'dept'
         } ,
          {
            name : 'approvalStatus'
         } ,
          {
            name : 'remark'
         } ],
         width : 800,
         height : 200,
         viewrecords : true,
         rowNum : 5
       });
   }
   
    

</script>
</head>
<body>
	<div id="personalAppointInfo_tab">
		<ul>
			<li id="monthSalary_col"><a href="#appointmentInfo_tab">인사발령정보</a>
			</li>
		</ul>
		<div id="appointmentInfo_tab">
			<input id="break_startD" class="ui-button ui-widget ui-corner-all"
				readonly>~<input id="break_endD"
				class="ui-button ui-widget ui-corner-all" readonly> <input
				type="button" class="ui-button ui-widget ui-corner-all"
				id="appointmentSearch_Btn" value="검색" /><br />
			<br /> <input type="button"
				class="ui-button ui-widget ui-corner-all" id="add_appointment_btn"
				value="발령추가" /> <input type="button"
				class="ui-button ui-widget ui-corner-all" id="del_appointment_btn"
				value="발령삭제" /> <input type="button"
				class="ui-button ui-widget ui-corner-all" id="save_Btn" value="저장" /><br />
			<br />
			<table id="appointmentInfoGrid"></table>
		</div>
	</div>

	<div id="appointmentTargetInfo_tab">
		<ul>
			<li><a href="#appointmentTarget_tab">발령대상자</a></li>
		</ul>
		<div id="appointmentTarget_tab">
			<input type="button" class="ui-button ui-widget ui-corner-all"
				id="add_target_btn" value="사원추가" /> <input type="button"
				class="ui-button ui-widget ui-corner-all" id="del_target_btn"
				value="사원삭제" /><br />
			<br />
			<table id="targetInfoGrid"></table>
		</div>
	</div>
</body>
</html>