package kr.co.yooooon.base.sf;

import java.util.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.hr.salary.to.*;

public interface BaseServiceFacade {
	public boolean login(String name, String empCode);
	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype);
	public ArrayList<CodeTO> findoutPutList(String outPut);
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1,String code2,String code3);
	public ArrayList<HolidayTO> findHolidayList();
	public String findWeekDayCount(String startDate, String endDate);
	public void registEmpImg(String empCode, String imgExtend);
	public void batchDeptProcess(ArrayList<DeptTO> deptto);
	public ArrayList<BaseSalaryTO> findPositionList();
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList);
	public ArrayList<CodeTO> findCodeList();
	void registCodeList(List<HolidayTO> holyday);
	public ReportTO viewReport(String empCode);
	public void saveCodeList(List<DetailCodeTO> saveCodeList);
	
	public ReportSalaryTO viewSalaryReport(String empCode, String applyMonth);
	
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
