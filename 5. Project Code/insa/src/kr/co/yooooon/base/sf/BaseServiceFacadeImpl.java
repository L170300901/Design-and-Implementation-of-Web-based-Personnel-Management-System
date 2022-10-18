package kr.co.yooooon.base.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.applicationService.*;
import kr.co.yooooon.base.exception.*;
import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp.applicationService.*;
import kr.co.yooooon.hr.salary.to.*;

public class BaseServiceFacadeImpl implements BaseServiceFacade {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	BaseApplicationService baseApplicationService = BaseApplicationServiceImpl.getInstance();
	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();
	
	private static BaseServiceFacade instance;
	private BaseServiceFacadeImpl() {		
	}	
	public static BaseServiceFacade getInstance() {
		if (instance == null)
			instance = new BaseServiceFacadeImpl();
		return instance;
	}

	
	@Override
	public boolean login(String empName, String empPw) {
		if (logger.isDebugEnabled()) {
			logger.debug(" login 시작 ");
		}
		
		dataSourceTransactionManager.beginTransaction();
		try {
			boolean check = baseApplicationService.loginEmployee(empName, empPw);
			if (logger.isDebugEnabled()) {
				logger.debug(" login 종료 ");
			}
			dataSourceTransactionManager.commitTransaction();
			return check;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} catch (IdNotFoundException e) {
			throw new DataAccessException("사원명을 확인해주세요");
		} catch (PwMissMatchException e) {
			throw new DataAccessException("사원번호를 확인해주세요");
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	
	@Override
	public ArrayList<DetailCodeTO> findDetailCodeList(String codetype) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 시작 ");
		}
		ArrayList<DetailCodeTO> detailCodeto = baseApplicationService.findDetailCodeList(codetype);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 종료 ");
		}
		return detailCodeto;
	}
	@Override
	public ArrayList<CodeTO> findoutPutList(String outPut){
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 시작 ");
		}
		ArrayList<CodeTO> findoutPutList = baseApplicationService.findoutPutList(outPut);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeList 종료 ");
		}
		return findoutPutList;
	}
	@Override
	public ArrayList<DetailCodeTO> findDetailCodeListRest(String code1, String code2, String code3) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeListRest 시작 ");
		}
		ArrayList<DetailCodeTO> detailCodeto = baseApplicationService.findDetailCodeListRest(code1, code2, code3);
		if (logger.isDebugEnabled()) {
			logger.debug(" findDetailCodeListRest 종료 ");
		}
		return detailCodeto;
	}

	
	@Override
	public ArrayList<HolidayTO> findHolidayList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findHolidayList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<HolidayTO> holidayList = baseApplicationService.findHolidayList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findHolidayList 종료 ");
			}
			return holidayList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	
	@Override
	public String findWeekDayCount(String startDate, String endDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findWeekdayList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {

			String weekdayCount = baseApplicationService.findWeekDayCount(startDate, endDate);
			if (logger.isDebugEnabled()) {
				logger.debug(" findWeekdayList 종료 ");
			}
			return weekdayCount;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	
	@Override
	public void registEmpImg(String empCode, String imgExtend) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpImg 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			baseApplicationService.registEmpImg(empCode, imgExtend);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" registEmpImg 종료 ");
		}
	}

	
	@Override
	public void batchDeptProcess(ArrayList<DeptTO> deptto) {
		if (logger.isDebugEnabled()) {
			logger.debug(" batchDeptProcess 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			baseApplicationService.batchDeptProcess(deptto);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" batchDeptProcess 종료 ");
		}

	}

	
	@Override
	public ArrayList<BaseSalaryTO> findPositionList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findPositionList 시작 ");
		}

		ArrayList<BaseSalaryTO> positionList = empApplicationService.selectPositionList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findPositionList 종료 ");
		}
		return positionList;
	}

	
	@Override
	public void modifyPosition(ArrayList<BaseSalaryTO> positionList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyPosition 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			baseApplicationService.modifyPosition(positionList);

			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyPosition 종료 ");
		}
	}

	
	@Override
	public ArrayList<CodeTO> findCodeList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findCodeList 시작 ");
		}
		ArrayList<CodeTO> codeto = baseApplicationService.findCodeList();
		if (logger.isDebugEnabled()) {
			logger.debug(" findCodeList 종료 ");
		}
		return codeto;
	}

	@Override
	public void saveCodeList(List<DetailCodeTO> saveCodeList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" saveCodeList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();

		try {
			baseApplicationService.saveCodeList(saveCodeList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" saveCodeList 종료 ");
			}
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public void registCodeList(List<HolidayTO> holyday) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findWeekdayList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();

		try {
			baseApplicationService.registCodeList(holyday);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findWeekdayList 종료 ");
			}
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public ReportTO viewReport(String empCode) {
		 if (logger.isDebugEnabled()) {
				logger.debug("viewReport 시작  확인");
			}
		 
		 ReportTO to=null;
		 dataSourceTransactionManager.beginTransaction();
			try {
				to=baseApplicationService.viewReport(empCode);
			//	dataSourceTransactionManager.commitTransaction();
			} catch (DataAccessException e) {
				dataSourceTransactionManager.rollbackTransaction();
				logger.fatal(e.getMessage());
				throw e;
			} finally {
				dataSourceTransactionManager.closeConnection();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("viewReport 종료 확인");
			}
			return to;
	}
	
	@Override
	public ReportSalaryTO viewSalaryReport(String empCode, String applyMonth) {
		 if (logger.isDebugEnabled()) {
				logger.debug("viewSalaryReport 시작  확인");
			}
		 ReportSalaryTO to=null;
		 dataSourceTransactionManager.beginTransaction();
			try {
				to=baseApplicationService.viewSalaryReport(empCode,applyMonth);
			//	dataSourceTransactionManager.commitTransaction();
			} catch (DataAccessException e) {
				dataSourceTransactionManager.rollbackTransaction();
				logger.fatal(e.getMessage());
				throw e;
			} finally {
				dataSourceTransactionManager.closeConnection();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("viewSalaryReport 종료 확인");
			}
			return to;
	}
	@Override
	public ArrayList<CompanyTO> searchCompanyCode() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" searchCompanyCode 시작 ");
		}
		ArrayList<CompanyTO> companyto = baseApplicationService.searchCompanyCode();
		if (logger.isDebugEnabled()) {
			logger.debug(" searchCompanyCode 종료 ");
		}
		return companyto;
	}
	@Override
	public ArrayList<LoginEmpTo> searchEmpCode(String companyCode) {
			// TODO Auto-generated method stub
			if (logger.isDebugEnabled()) {
				logger.debug(" searchEmpCode 시작 ");
			}
			ArrayList<LoginEmpTo> empto = baseApplicationService.searchEmpCode(companyCode);
			if (logger.isDebugEnabled()) {
				logger.debug(" searchEmpCode 종료 ");
			}
			return empto;
	}
	@Override
	public void createHobongList(String startDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" createHobongList 시작 ");
		}
		try {
			baseApplicationService.createHobongList(startDate);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" createHobongList 종료 ");
			}
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public ArrayList<PayDateTO> payDateList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" payDateList 시작 ");
		}
		ArrayList<PayDateTO> payDate = baseApplicationService.payDateList();
		if (logger.isDebugEnabled()) {
			logger.debug(" payDateList 종료 ");
		}
		return payDate;							
	}
	@Override
	public ArrayList<HobongTO> hobongPositionList(String startDate){
		if (logger.isDebugEnabled()) {
			logger.debug(" hobongPositionList 시작 ");
		}
		ArrayList<HobongTO> hobongPositionList = baseApplicationService.hobongPositionList(startDate);
		
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
		ArrayList<HobongTO> findHobongTable = baseApplicationService.findHobongTable(positionCode,positionName,startDate);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findHobongTable 종료 ");
		}
		return findHobongTable;
	}
	@Override
	public void payCheckResist(String increaseAmount,String initialValue,String startDate,String positionCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" payCheckResist 시작 ");
		}
		try {
			baseApplicationService.payCheckResist(increaseAmount,initialValue,startDate,positionCode);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" payCheckResist 종료 ");
			}
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
	}
	@Override
	public ArrayList<PaymentItemTO> findPaymentItemList(String salaryClassification, String paymentClassification, String year){
		if (logger.isDebugEnabled()) {
			logger.debug(" findPaymentItemList 시작 ");
		}
		ArrayList<PaymentItemTO> findPaymentItemList = baseApplicationService.findPaymentItemList(salaryClassification,paymentClassification,year);
		
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
		ArrayList<DetailCodeTO> basicDetailCodeto = baseApplicationService.basicDetailCodelist(code);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" basicDetailCodelist 종료 ");
		}
		return basicDetailCodeto;
	}
	
}
