package kr.co.yooooon.hr.emp_new.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.hr.emp_new.dao.*;
import kr.co.yooooon.hr.emp_new.sf.*;
import kr.co.yooooon.hr.emp_new.to.*;

public class EmpCodeApplicationServiceImpl implements EmpCodeApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private EmpCodeDAO empCodeDAO = EmpCodeDAOImpl.getInstance();
	
	EmpCodeServiceFacade empCodeServiceFacade = EmpCodeServiceFacadeImpl.getInstance();

	
	private static EmpCodeApplicationServiceImpl instance;
	private EmpCodeApplicationServiceImpl() {
	}
	public static EmpCodeApplicationService getInstance() {
		if (instance == null)
			instance = new EmpCodeApplicationServiceImpl();
		return instance;
	}
	
	public ArrayList<BASIC_DETAIL_CODETO> lastSchoolCodeList(String code){
		ArrayList<BASIC_DETAIL_CODETO> lastSchoolCodeList = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug(" lastSchoolCodeList 시작 ");
		}
		
		lastSchoolCodeList = empCodeDAO.lastSchoolCodeList(code);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" lastSchoolCodeList 종료 ");
		}
		return lastSchoolCodeList;
	
	}
	
	
}