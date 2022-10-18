package kr.co.yooooon.system.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.to.*;
import kr.co.yooooon.system.applicationService.*;
import kr.co.yooooon.system.to.*;

public class SystemServiceFacadeImpl implements SystemServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	SystemApplicationService systemApplicationService = SystemApplicationServiceImpl.getInstance();
	
	private static SystemServiceFacade instance;
	private SystemServiceFacadeImpl() {		
	}	
	public static SystemServiceFacade getInstance() {
		if (instance == null) {
		
			instance = new SystemServiceFacadeImpl();
	
		}
		return instance;
	}
	
	@Override
	public ArrayList<CompanyTO> companyList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" companyList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<CompanyTO> companyList = systemApplicationService.companyList();
			if (logger.isDebugEnabled()) {
				logger.debug(" companyList 종료 ");
			}
			return companyList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public ArrayList<CompanyBasicRegistTO> companyBasicRegist(String code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" companyBasicRegist 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<CompanyBasicRegistTO> companyBasic = systemApplicationService.companyBasicRegist(code);
			if (logger.isDebugEnabled()) {
				logger.debug(" companyBasicRegist 종료 ");
			}
			return companyBasic;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public ArrayList<BranchTO> branchList(){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" branchList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BranchTO> branchList = systemApplicationService.branchList();
			if (logger.isDebugEnabled()) {
				logger.debug(" branchList 종료 ");
			}
			return branchList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public ArrayList<BranchBasicRegistTO> branchBasicRegist(String code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" branchBasicRegist 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BranchBasicRegistTO> branchBasic = systemApplicationService.branchBasicRegist(code);
			if (logger.isDebugEnabled()) {
				logger.debug(" branchBasicRegist 종료 ");
			}
			return branchBasic;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public ArrayList<Dept_newTO> deptList(){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deptList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<Dept_newTO> deptList = systemApplicationService.deptList();
			if (logger.isDebugEnabled()) {
				logger.debug(" deptList 종료 ");
			}
			return deptList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public ArrayList<Emp_newTO> empList(){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" empList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<Emp_newTO> empList = systemApplicationService.empList();
			if (logger.isDebugEnabled()) {
				logger.debug(" empList 종료 ");
			}
			return empList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

}