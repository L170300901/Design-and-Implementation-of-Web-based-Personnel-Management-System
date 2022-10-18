package kr.co.yooooon.hr.emp_new.applicationService;


import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.hr.emp_new.dao.*;
import kr.co.yooooon.hr.emp_new.sf.*;
import kr.co.yooooon.hr.emp_new.to.*;


public class EmpApplicationServiceImpl implements EmpApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private EmpDAO empDAO = EmpDAOImpl.getInstance();
		
	EmpServiceFacade empServiceFacade = EmpServiceFacadeImpl.getInstance();

	
	private static EmpApplicationServiceImpl instance;
	private EmpApplicationServiceImpl() {
	}
	public static EmpApplicationService getInstance() {
		if (instance == null)
			instance = new EmpApplicationServiceImpl();
		return instance;
	}
	
	public ArrayList<Emp_newTO> findEmpList() {
		ArrayList<Emp_newTO> empList = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 시작 ");
		}
		
			empList = empDAO.selectEmpList();
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 종료 ");
		}
		return empList;
	}

	public ArrayList<EmpInforTO> empPersonal(String empCode){
		ArrayList<EmpInforTO> empInfo = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 시작 ");
		}
		
		empInfo = empDAO.empPersonal(empCode);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpList 종료 ");
		}
		return empInfo;
	
	}
	public ArrayList<EmpResidentTO> findEmpResident(String empCode){
		ArrayList<EmpResidentTO> empResident = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpResident 시작 ");
		}
		
		empResident = empDAO.findEmpResident(empCode);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpResident 종료 ");
		}
		return empResident;
	
	}
	
}