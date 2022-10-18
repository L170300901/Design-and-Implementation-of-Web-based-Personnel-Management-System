package kr.co.yooooon.system.applicationService;

import java.util.*;

import kr.co.yooooon.hr.emp_new.to.*;
import kr.co.yooooon.system.to.*;

public interface SystemApplicationService {
	public ArrayList<CompanyTO> companyList();
	public ArrayList<CompanyBasicRegistTO> companyBasicRegist(String code);
	public ArrayList<BranchTO> branchList();
	public ArrayList<BranchBasicRegistTO> branchBasicRegist(String code);
	public ArrayList<Dept_newTO> deptList();
	public ArrayList<Emp_newTO> empList();
}
