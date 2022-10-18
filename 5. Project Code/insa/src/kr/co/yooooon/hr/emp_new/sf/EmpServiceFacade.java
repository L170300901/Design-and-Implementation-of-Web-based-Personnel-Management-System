package kr.co.yooooon.hr.emp_new.sf;


import java.util.*;

import kr.co.yooooon.hr.emp_new.to.*;

public interface EmpServiceFacade {
	
	public List<Emp_newTO> findEmpList(); 
	public List<EmpInforTO> empPersonal(String empCode);
	public List<EmpResidentTO> findEmpResident(String empCode);
	
}
