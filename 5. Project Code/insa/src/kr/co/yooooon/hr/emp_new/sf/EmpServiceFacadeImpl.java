package kr.co.yooooon.hr.emp_new.sf;


import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.applicationService.*;
import kr.co.yooooon.hr.emp_new.to.*;

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
	public List<Emp_newTO> findEmpList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			
			ArrayList<Emp_newTO> empList = empApplicationService.findEmpList();
			
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
	public List<EmpInforTO> empPersonal(String empCode){
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			
			ArrayList<EmpInforTO> empInfo = empApplicationService.empPersonal(empCode);
			
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findEmployeeList 종료 ");
			}
			return empInfo;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
	}
	
	@Override
	public List<EmpResidentTO> findEmpResident(String empCode){
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpResident 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			
			ArrayList<EmpResidentTO> empResident = empApplicationService.findEmpResident(empCode);
			
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" findEmpResident 종료 ");
			}
			return empResident;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
	}
	
}

