package kr.co.yooooon.hr.emp.sf;

import java.util.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.hr.emp.to.*;

public interface EmpServiceFacade {
	public LoginCheckTO getEmp(String name); //selectEmp
	public String findLastEmpCode();
	public EmpTO findAllEmpInfo(String empCode);	
	public List<EmpTO> findEmpList(String dept); //findEmployeeListByDept
	public void registEmployee(EmpTO empto);
	public void modifyEmployee(EmpTO emp);
	public void deleteEmpList(ArrayList<EmpTO> empList);
	public ArrayList<DeptTO> findDeptList();
	
	//public List<EmpTO> getMemberList();
}