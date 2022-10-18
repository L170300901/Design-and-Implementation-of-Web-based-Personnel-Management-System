package kr.co.yooooon.hr.emp_new.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.applicationService.*;
import kr.co.yooooon.hr.emp_new.to.*;


public class EmpCodeServiceFacadeImpl implements EmpCodeServiceFacade {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	EmpCodeApplicationService empCodeApplicationService = EmpCodeApplicationServiceImpl.getInstance();
	private static EmpCodeServiceFacadeImpl instance = new  EmpCodeServiceFacadeImpl();

	private EmpCodeServiceFacadeImpl() {
	}

	public static  EmpCodeServiceFacadeImpl getInstance() {
		return instance;
	}

	public List<BASIC_DETAIL_CODETO> lastSchoolCodeList(String code){
		if (logger.isDebugEnabled()) {
			logger.debug(" lastSchoolCodeList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			
			ArrayList<BASIC_DETAIL_CODETO> lastSchoolCodeList = empCodeApplicationService.lastSchoolCodeList(code);
			//ArrayList<BASIC_DETAIL_CODETO> empInfo=null;
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" lastSchoolCodeList 종료 ");
			}
			return lastSchoolCodeList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
	}
	
	
}

