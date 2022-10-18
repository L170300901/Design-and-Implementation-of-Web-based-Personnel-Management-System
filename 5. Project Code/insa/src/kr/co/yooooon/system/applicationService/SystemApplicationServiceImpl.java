package kr.co.yooooon.system.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.hr.emp_new.to.*;
import kr.co.yooooon.system.dao.*;
import kr.co.yooooon.system.to.*;


public class SystemApplicationServiceImpl implements SystemApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	//SystemApplicationService systemApplicationService = SystemApplicationServiceImpl.getInstance();

	private CompanyDAO companyDAO = CompanyDAOImpl.getInstance();
	private BranchDAO branchDAO = BranchDAOImpl.getInstance();
	private DeptDAO deptDAO = DeptDAOImpl.getInstance();
	private EmpDAO empDAO = EmpDAOImpl.getInstance();
	
	private static SystemApplicationService instance;
	private SystemApplicationServiceImpl() {
	}
	
	public static SystemApplicationService getInstance() {
		if (instance == null)
			instance = new SystemApplicationServiceImpl();
		return instance;
	}

	
	@Override
	public ArrayList<CompanyTO> companyList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" companyList 시작 ");
		}

		ArrayList<CompanyTO> companyList = companyDAO.companyList();

		if (logger.isDebugEnabled()) {
			logger.debug(" companyList 종료 ");
		}
		return companyList;
	}
	@Override
	public ArrayList<CompanyBasicRegistTO> companyBasicRegist(String code){
		if (logger.isDebugEnabled()) {
			logger.debug(" companyBasicRegist 시작 ");
		}

		ArrayList<CompanyBasicRegistTO> companyBasic = companyDAO.companyBasicRegist(code);

		if (logger.isDebugEnabled()) {
			logger.debug(" companyBasicRegist 종료 ");
		}
		return companyBasic;
	}
	@Override
	public ArrayList<BranchTO> branchList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" branchList 시작 ");
		}

		ArrayList<BranchTO> branchList = branchDAO.branchList();

		if (logger.isDebugEnabled()) {
			logger.debug(" branchList 종료 ");
		}
		return branchList;
	}
	@Override
	public ArrayList<BranchBasicRegistTO> branchBasicRegist(String code){
		if (logger.isDebugEnabled()) {
			logger.debug(" branchBasicRegist 시작 ");
		}

		ArrayList<BranchBasicRegistTO> branchBasic = branchDAO.branchBasicRegist(code);

		if (logger.isDebugEnabled()) {
			logger.debug(" branchBasicRegist 종료 ");
		}
		return branchBasic;
	}
	@Override
	public ArrayList<Dept_newTO> deptList(){
		if (logger.isDebugEnabled()) {
			logger.debug(" deptList 시작 ");
		}

		ArrayList<Dept_newTO> deptList = deptDAO.deptList();

		if (logger.isDebugEnabled()) {
			logger.debug(" deptList 종료 ");
		}
		return deptList;
	}
	@Override
	public ArrayList<Emp_newTO> empList(){
		if (logger.isDebugEnabled()) {
			logger.debug(" empList 시작 ");
		}

		ArrayList<Emp_newTO> empList = empDAO.empList();

		if (logger.isDebugEnabled()) {
			logger.debug(" empList 종료 ");
		}
		return empList;
	}
	
}
