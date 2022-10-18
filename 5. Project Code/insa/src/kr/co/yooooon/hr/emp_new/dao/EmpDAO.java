package kr.co.yooooon.hr.emp_new.dao;

import java.util.*;

import kr.co.yooooon.hr.emp_new.to.*;

public interface EmpDAO {
	public ArrayList<Emp_newTO> selectEmpList();
	public ArrayList<EmpInforTO> empPersonal(String emp);
	public ArrayList<EmpResidentTO> findEmpResident(String empCode);
}
