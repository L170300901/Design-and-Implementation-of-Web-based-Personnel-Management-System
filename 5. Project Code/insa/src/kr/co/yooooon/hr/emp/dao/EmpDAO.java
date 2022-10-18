package kr.co.yooooon.hr.emp.dao;
import java.util.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.hr.emp.to.*;

public interface EmpDAO {
	public LoginCheckTO selectEmp(String empno);
	public String selectLastEmpCode();
	public ArrayList<EmpTO> selectEmpList();
	public ArrayList<EmpTO> selectEmpListD(String dept);
	public ArrayList<EmpTO> selectEmpListN(String name);
	public String getEmpCode(String name);
	public EmpTO selectEmployee(String empCode);
	

	public void registEmployee(EmpTO empto);
	public void updateEmployee(EmpTO emp);
	public void deleteEmployee(EmpTO emp);
	
}
