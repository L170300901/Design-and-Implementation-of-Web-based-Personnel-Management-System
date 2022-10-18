package kr.co.yooooon.base.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.dao.*;
import kr.co.yooooon.base.exception.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.hr.emp.applicationService.*;
import kr.co.yooooon.hr.emp.sf.*;
import kr.co.yooooon.hr.emp.to.*;
import kr.co.yooooon.hr.salary.dao.*;
import kr.co.yooooon.hr.salary.to.*;

public class BaseApplicationServiceImpl implements BaseApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();
	private EmpServiceFacade empServiceFasade = EmpServiceFacadeImpl.getInstance();
	private DetailCodeDAO detailCodeDAO = DetailCodeDAOImpl.getInstance();
	private HolidayDAO holidayDAO = HolidayDAOImpl.getInstance();
	private DeptDAO deptDAO = DeptDAOImpl.getInstance();
	private BaseSalaryDAO baseSalaryDAO = BaseSalaryDAOImpl.getInstance();
	private CodeDAO codeDAO = CodeDAOImpl.getInstance();
	private ReportDAO reportDAO= ReportDAOImpl.getInstance();
	private LoginCodeDAO loginCodeDAO= LoginCodeDAOImpl.getInstance();
	private PayCheckDAO payCheckDAO= PayCheckDAOImpl.getInstance();
	private PaymentItemDAO paymentItemDAO=PaymentItemDAOImpl.getInstance();
	
	private static BaseApplicationService instance;
	
	private BaseApplicationServiceImpl() {
	}
	public static BaseApplicationService getInstance() {
		if (instance == null)
			instance = new BaseApplicationServiceImpl();
		return instance;
	}

	
	public boolean loginEmployee(String empName, String empPw) throws IdNotFoundException, PwMissMatchException {
		if (logger.isDebugEnabled()) {
			logger.debug(" loginEmployee 시작 ");
		}

		LoginCheckTO emp = empServiceFasade.getEmp(empName); // empName으로 사원의 정보를 찾는다
		System.out.println();
		if (emp == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(" loginEmployee 종료 (MissName)");
			}
			throw new DataAccessException("사원명이 존재하지 않습니다");
		}else {
			if (emp.getEmpPw().equals(empPw)) {
				if (logger.isDebugEnabled()) {
					logger.debug(" loginEmployee 종료 ");
				}
				return true;
			}else {
				if (logger.isDebugEnabled()) {
					logger.debug(" loginEmployee 종료 (MissEmpCode)");
				}
				throw new DataAccessException("사원번호를 확인해주세요");
			 }
		}
	}

	@Override
	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 시작 ");
		}
		ArrayList<DetailCodeTO> detailCodeList = null;
		detailCodeList = detailCodeDAO.selectDetailCodeList(codetype);

		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 종료 ");
		}
		return detailCodeList;
	}
	@Override
	public ArrayList<CodeTO> findoutPutList(String outPut){
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 시작 ");
		}
		ArrayList<CodeTO> findoutPutList = null;
		findoutPutList = detailCodeDAO.findoutPutList(outPut);

		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 종료 ");
		}
		return findoutPutList;
	}
	@Override
	public void registEmpCode(EmpTO emp) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpCode 시작 ");
		}
		DetailCodeTO detailCodeto = new DetailCodeTO();
		detailCodeto.setDetailCodeNumber(emp.getEmpCode());
		detailCodeto.setDetailCodeName(emp.getEmpName());
		detailCodeto.setCodeNumber("CO-17");
		detailCodeto.setDetailCodeNameusing("N");
		detailCodeDAO.registDetailCode(detailCodeto);

		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpCode 종료 ");
		}
	}

	@Override
	public void deleteEmpCode(EmpTO emp) {
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteEmpCode 시작 ");
		}
		DetailCodeTO detailCodeto = new DetailCodeTO();
		detailCodeto.setDetailCodeNumber(emp.getEmpCode());
		detailCodeto.setDetailCodeName(emp.getEmpName());
		detailCodeDAO.deleteDetailCode(detailCodeto);

		if (logger.isDebugEnabled()) {
			logger.debug(" deleteEmpCode 종료 ");
		}

	}

	@Override
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeListRest 시작 ");
		}
		ArrayList<DetailCodeTO> detailCodeList = null;
		detailCodeList = detailCodeDAO.selectDetailCodeListRest(code1, code2, code3);

		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeListRest 종료 ");
		}
		return detailCodeList;
	}

	@Override
	public ArrayList<HolidayTO> findHolidayList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findHolidayList 시작 ");
		}

		ArrayList<HolidayTO> holidayList = holidayDAO.selectHolidayList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findHolidayList 종료 ");
		}
		return holidayList;
	}

	@Override
	public void batchDeptProcess(ArrayList<DeptTO> deptto) {
		if (logger.isDebugEnabled()) {

			logger.debug(" batchDeptProcess 시작 ");

		}
		DetailCodeTO detailCodeto = new DetailCodeTO();

		for (DeptTO dept : deptto) {
			switch (dept.getStatus()) {

			case "update":
				deptDAO.updateDept(dept);
				detailCodeto.setDetailCodeNumber(dept.getDeptCode());
				detailCodeto.setDetailCodeName(dept.getDeptName());
				detailCodeto.setCodeNumber("CO-07");
				detailCodeto.setDetailCodeNameusing("Y");
				detailCodeDAO.updateDetailCode(detailCodeto);
				break;

			case "insert":
				deptDAO.registDept(dept);
				detailCodeto.setDetailCodeNumber(dept.getDeptCode());
				detailCodeto.setDetailCodeName(dept.getDeptName());
				detailCodeto.setCodeNumber("CO-07");
				detailCodeto.setDetailCodeNameusing("Y");
				detailCodeDAO.registDetailCode(detailCodeto);
				break;

			case "delete":
				deptDAO.deleteDept(dept);
				detailCodeto.setDetailCodeNumber(dept.getDeptCode());
				detailCodeto.setDetailCodeName(dept.getDeptName());
				detailCodeDAO.deleteDetailCode(detailCodeto);
				break;

			case "normal":
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" batchDeptProcess 종료 ");
		}

	}

	@Override
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyPosition 시작 ");
		}

		if (positionList != null && positionList.size() > 0) {
			for (BaseSalaryTO position : positionList) {
				DetailCodeTO detailCodeto = new DetailCodeTO();
				switch (position.getStatus()) {

				case "update":
					baseSalaryDAO.updatePosition(position);
					detailCodeto.setDetailCodeNumber(position.getPositionCode());
					detailCodeto.setDetailCodeName(position.getPosition());
					detailCodeto.setCodeNumber("CO-04");
					detailCodeto.setDetailCodeNameusing("Y");
					detailCodeDAO.updateDetailCode(detailCodeto);
					break;

				case "insert":
					baseSalaryDAO.insertPosition(position);
					detailCodeto.setDetailCodeNumber(position.getPositionCode());
					detailCodeto.setDetailCodeName(position.getPosition());
					detailCodeto.setCodeNumber("CO-04");
					detailCodeto.setDetailCodeNameusing("Y");
					detailCodeDAO.registDetailCode(detailCodeto);
					break;

				case "delete":
					baseSalaryDAO.deletePosition(position);
					detailCodeto.setDetailCodeNumber(position.getPositionCode());
					detailCodeto.setDetailCodeName(position.getPosition());
					detailCodeDAO.deleteDetailCode(detailCodeto);
					break;
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyPosition 종료 ");
		}
	}

	@Override
	public String findWeekDayCount(String startDate, String endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findHolidayList 시작 ");
		}

		String weekdayCount = holidayDAO.selectWeekDayCount(startDate, endDate);

		if (logger.isDebugEnabled()) {
			logger.debug(" findHolidayList 종료 ");
		}
		return weekdayCount;
	}

	@Override
	public void registEmpImg(String empCode, String imgExtend) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpImg 시작 ");
		}

		EmpTO emp = empApplicationService.findAllEmployeeInfo(empCode);
		if (emp == null) {
			emp = new EmpTO();
			emp.setEmpCode(empCode);
			emp.setStatus("insert");
		} else {
			emp.setStatus("update");
		}
		emp.setImgExtend(imgExtend);
		empApplicationService.modifyEmployee(emp);

		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpImg 종료 ");
		}
	}
	
	@Override
	public ArrayList<CodeTO> findCodeList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findCodeList 시작 ");
		}

		ArrayList<CodeTO> codeList = codeDAO.selectCode();

		if (logger.isDebugEnabled()) {
			logger.debug(" findCodeList 종료 ");
		}
		return codeList;
	}
	
	@Override
	public void saveCodeList(List<DetailCodeTO> saveCodeList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" saveCodeList 시작 ");
		}
		codeDAO.saveCodeList(saveCodeList);
		/*
		for (DetailCodeTO DetailCode : saveCodeList) {
			//System.out.println("@@@@"+DetailCode.getStatus());
			switch (DetailCode.getStatus()) {
			
			
			case "update":
				codeDAO.updateCodeList(DetailCode);
				break;
			
			case "insert":
				codeDAO.insertCodeList(DetailCode);
				break;

			case "delete":
				codeDAO.deleteCodeList(DetailCode);
				break;
			
			}
		}*/
		if (logger.isDebugEnabled()) {
			logger.debug(" saveCodeList 종료 ");
		}
	}
	
	@Override
	public void registCodeList(List<HolidayTO> holyday) {
		if (logger.isDebugEnabled()) {
			logger.debug(" 어플리 ");
		}
		for (HolidayTO holiday : holyday) {
			switch (holiday.getStatus()) {

			case "update":
				holidayDAO.updateCodeList(holiday);
				break;
			
			case "insert":
				holidayDAO.insertCodeList(holiday);
				break;

			case "delete":
				holidayDAO.deleteCodeList(holiday);
				break;

			}
		}
	}
	@Override
	public ReportTO viewReport(String empCode) {
		
		 if (logger.isDebugEnabled()) {
			logger.debug(" viewReport 시작   확인");
			}
		 ReportTO to=null;

		try {
			 to=reportDAO.selectReport(empCode);
		 }catch(DataAccessException e){
			 logger.fatal(e.getMessage());
			 throw e;
		 }

		 if (logger.isDebugEnabled()) {
			logger.debug(" viewReport 종료   확인");
			}
		return to;
	}
	
	
	@Override
	public ReportSalaryTO viewSalaryReport(String empCode, String applyMonth) {
		
		 if (logger.isDebugEnabled()) {
			logger.debug(" viewSalaryReport 시작   확인");
			}
		 ReportSalaryTO to=null;

		try {
			 to=reportDAO.selecSalarytReport(empCode,applyMonth);
		 }catch(DataAccessException e){
			 logger.fatal(e.getMessage());
			 throw e;
		 }

		 if (logger.isDebugEnabled()) {
			logger.debug(" viewSalaryReport 종료   확인");
			}
		return to;
	}
	
	@Override
	public ArrayList<CompanyTO> searchCompanyCode() {
		if (logger.isDebugEnabled()) {
			logger.debug(" searchCompanyCode 시작 ");
		}

		ArrayList<CompanyTO> companyList = loginCodeDAO.searchCompanyCode();

		if (logger.isDebugEnabled()) {
			logger.debug(" searchCompanyCode 종료 ");
		}
		return companyList;
	}
	@Override
	public ArrayList<LoginEmpTo> searchEmpCode(String companyCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" dsearchEmpCode 시작 ");
		}

		ArrayList<LoginEmpTo> empList = loginCodeDAO.searchEmpCode(companyCode);

		if (logger.isDebugEnabled()) {
			logger.debug(" searchEmpCode 종료 ");
		}
		return empList;
	}
	
	@Override
	public ArrayList<PayDateTO> payDateList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" payDateList 시작 ");
		}

		ArrayList<PayDateTO> payDateList = payCheckDAO.payDateList();

		if (logger.isDebugEnabled()) {
			logger.debug(" payDateList 종료 ");
		}
		return payDateList;
	}
	@Override
	public void createHobongList(String startDate){
		if (logger.isDebugEnabled()) {
			logger.debug(" createHobongList 시작 ");
		}
		payCheckDAO.createHobongList(startDate);
		/*
		System.out.println("@@@@@@1. 호봉이력 날짜를 넣고 카테시안 조인으로 테이블 복제 완료");
		ArrayList<HobongTO> createHobongList = payCheckDAO.HobongList(startDate);
		System.out.println("@@@@@@2. 결과값 arrayList에 담기 성공");
		*/
		if (logger.isDebugEnabled()) {
			logger.debug(" createHobongList 종료 ");
		}
	}
	@Override
	public ArrayList<HobongTO> hobongPositionList(String startDate){
		if (logger.isDebugEnabled()) {
			logger.debug(" hobongPositionList 시작 ");
		}
		ArrayList<HobongTO> hobongPositionList = payCheckDAO.hobongPositionList(startDate);
		if (logger.isDebugEnabled()) {
			logger.debug(" hobongPositionList 종료 ");
		}
		return hobongPositionList;
	}
	@Override
	public ArrayList<HobongTO> findHobongTable(String positionCode, String positionName, String startDate){
		if (logger.isDebugEnabled()) {
			logger.debug(" findHobongTable 시작 ");
		}
		ArrayList<HobongTO> hobongPositionList = payCheckDAO.findHobongTable(positionCode,positionName,startDate);
		if (logger.isDebugEnabled()) {
			logger.debug(" findHobongTable 종료 ");
		}
		return hobongPositionList;
	}
	@Override
	public void payCheckResist(String increaseAmount,String initialValue,String startDate,String positionCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" payCheckResist 시작 ");
		}
		payCheckDAO.payCheckResist(increaseAmount, initialValue, startDate, positionCode);
		/*
		System.out.println("@@@@@@1. 호봉이력 날짜를 넣고 카테시안 조인으로 테이블 복제 완료");
		ArrayList<HobongTO> createHobongList = payCheckDAO.HobongList(startDate);
		System.out.println("@@@@@@2. 결과값 arrayList에 담기 성공");
		*/
		if (logger.isDebugEnabled()) {
			logger.debug(" payCheckResist 종료 ");
		}
	
		
	}
	@Override
	public ArrayList<PaymentItemTO> findPaymentItemList(String salaryClassification, String paymentClassification, String year){
		if (logger.isDebugEnabled()) {
			logger.debug(" findPaymentItemList 시작 ");
		}
		ArrayList<PaymentItemTO> findPaymentItemList = paymentItemDAO.findPaymentItemList(salaryClassification,paymentClassification,year);
		if (logger.isDebugEnabled()) {
			logger.debug(" findPaymentItemList 종료 ");
		}
		return findPaymentItemList;
		
	}
	@Override
	public ArrayList<DetailCodeTO> basicDetailCodelist(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" basicDetailCodelist 시작 ");
		}
		ArrayList<DetailCodeTO> basicDetailCodelist = null;
		basicDetailCodelist = detailCodeDAO.basicDetailCodelist(code);

		if (logger.isDebugEnabled()) {
			logger.debug(" basicDetailCodelist 종료 ");
		}
		return basicDetailCodelist;
	}
}