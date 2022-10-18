package kr.co.yooooon.hr.emp.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp.applicationService.*;
import kr.co.yooooon.hr.emp.to.*;

public class EmpServiceFacadeImpl implements EmpServiceFacade {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();
	private static EmpServiceFacadeImpl instance = new EmpServiceFacadeImpl();

	private EmpServiceFacadeImpl() {
	}

	public static EmpServiceFacadeImpl getInstance() {
		System.out.println("여기는 getInstance");
		return instance;
	}

	@Override
	public LoginCheckTO getEmp(String name) {
		if (logger.isDebugEnabled()) {
			logger.debug(" getEmp 시작 ");
		}
		LoginCheckTO loginCheckTO = null;
		dataSourceTransactionManager.beginTransaction();
		try {
			loginCheckTO = empApplicationService.selectEmp(name);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" getEmp 종료 ");
		}
		return loginCheckTO;
	}

	@Override
	public String findLastEmpCode() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findLastEmpCode 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			String empCode = empApplicationService.findLastEmpCode();
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findLastEmpCode 종료 ");
			}
			return empCode;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public EmpTO findAllEmpInfo(String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findAllEmpInfo 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			
			EmpTO empTO = empApplicationService.findAllEmployeeInfo(empCode);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findAllEmpInfo 종료 ");
			}
			return empTO;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public List<EmpTO> findEmpList(String dept) {
		if (logger.isDebugEnabled()) {
			System.out.println("@@@@@@@@@@@@@@4번 통과 여기는 empservicefacadeimpl@@@@@@@@@@@@@@@@");
			logger.debug(" findEmpList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			//empServiceFacadeImpl의 findEmployeeList()의 emplist에 담음
			ArrayList<EmpTO> empList = empApplicationService.findEmployeeListByDept(dept);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findEmployeeList 종료 ");
			}
			return empList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public void registEmployee(EmpTO empto) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registEmployee 시작 "+empto.getEmpCode());
		}
		
		dataSourceTransactionManager.beginTransaction();
		try {
			empApplicationService.registEmployee(empto);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" registEmployee 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public void modifyEmployee(EmpTO emp) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyEmployee 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			empApplicationService.modifyEmployee(emp);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyEmployee 종료 ");
		}		
	}

	@Override
	public void deleteEmpList(ArrayList<EmpTO> empList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteEmpList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			empApplicationService.deleteEmpList(empList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteEmpList 종료 ");
		}		
	}

	@Override
	public ArrayList<DeptTO> findDeptList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDeptList 시작 ");
		}
		ArrayList<DeptTO> deptList = empApplicationService.findDeptList();
		dataSourceTransactionManager.commitTransaction();
		if (logger.isDebugEnabled()) {
			logger.debug(" findDeptList 종료 ");
		}
		return deptList;
	}
	/*@Override
	public List<EmpTO> getMemberList(){
		if (logger.isDebugEnabled()) {
			logger.debug(" findDeptList 시작 ");
		}
		List<EmpTO> deptList = empApplicationService.findDeptList();
		dataSourceTransactionManager.commitTransaction();
		if (logger.isDebugEnabled()) {
			logger.debug(" findDeptList 종료 ");
		}
		return deptList;
	}*/
}
