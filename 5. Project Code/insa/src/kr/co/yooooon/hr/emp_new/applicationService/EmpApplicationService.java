package kr.co.yooooon.hr.emp_new.applicationService;

import java.util.*;

import kr.co.yooooon.hr.emp_new.to.*;

public interface EmpApplicationService {
	public ArrayList<Emp_newTO> findEmpList();
	public ArrayList<EmpInforTO> empPersonal(String empCode);
	public ArrayList<EmpResidentTO> findEmpResident(String empCode);
}
