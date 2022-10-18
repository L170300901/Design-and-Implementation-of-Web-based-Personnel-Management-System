package kr.co.yooooon.base.applicationService;

import java.util.*;

import kr.co.yooooon.base.exception.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.hr.emp.to.*;
import kr.co.yooooon.hr.salary.to.*;


public interface BaseApplicationService {
   public boolean loginEmployee(String name, String empCode) throws IdNotFoundException, PwMissMatchException; 

   public ArrayList<DetailCodeTO> findDetailCodeList(String codetype);
   public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3);
   public ArrayList<CodeTO> findoutPutList(String outPut);
   public ArrayList<HolidayTO> findHolidayList();
   public String findWeekDayCount(String startDate, String endDate);

   public void registEmpImg(String empCode, String imgExtend);

   public void batchDeptProcess(ArrayList<DeptTO> deptto);
   public void modifyPosition(ArrayList<BaseSalaryTO> positionList);
   
   public void registEmpCode(EmpTO emp);
   public void deleteEmpCode(EmpTO emp);

   public ArrayList<CodeTO> findCodeList();

   public void registCodeList(List<HolidayTO> holyday);

   public ReportTO viewReport(String empCode);
   public ReportSalaryTO viewSalaryReport(String empCode, String applyMonth);

   public void saveCodeList(List<DetailCodeTO> saveCodeList);
   public ArrayList<CompanyTO> searchCompanyCode();
   public ArrayList<LoginEmpTo> searchEmpCode(String companyCode);
   
   public ArrayList<PayDateTO> payDateList();
   public void createHobongList(String startDate);
   public ArrayList<HobongTO> hobongPositionList(String startDate);
   public ArrayList<HobongTO> findHobongTable(String positionCode, String positionName, String startDate);
   public void payCheckResist(String increaseAmount,String initialValue,String startDate,String positionCode);
   public ArrayList<PaymentItemTO> findPaymentItemList(String salaryClassification, String paymentClassification, String year);
 
   public ArrayList<DetailCodeTO> basicDetailCodelist(String codetype);
}