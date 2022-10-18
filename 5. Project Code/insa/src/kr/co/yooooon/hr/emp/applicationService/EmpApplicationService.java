package kr.co.yooooon.hr.emp.applicationService;

import java.util.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.hr.emp.to.*;
import kr.co.yooooon.hr.salary.to.*;

public interface EmpApplicationService {
	public LoginCheckTO selectEmp(String name);
	public String findLastEmpCode();
	public EmpTO findAllEmployeeInfo(String empCode);
	public ArrayList<EmpTO> findEmployeeListByDept(String deptName);
	public void registEmployee(EmpTO emp);
	public void modifyEmployee(EmpTO emp);
	public void deleteEmpList(ArrayList<EmpTO> empList);
	public ArrayList<DeptTO> findDeptList();	
	
	public ArrayList<BaseSalaryTO> selectPositionList();
}
