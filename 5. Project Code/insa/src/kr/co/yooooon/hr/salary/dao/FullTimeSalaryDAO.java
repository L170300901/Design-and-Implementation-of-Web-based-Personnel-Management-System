package kr.co.yooooon.hr.salary.dao;

import java.util.*;

import kr.co.yooooon.hr.salary.to.*;

public interface FullTimeSalaryDAO {
	public ArrayList<FullTimeSalTO> selectFullTimeSalary(String applyYear, String empCode); 
	public ArrayList<FullTimeSalTO> findAllMoney(String empCode); 
	public void updateFullTimeSalary(List<FullTimeSalTO> fullTimeSalary); 
	public ArrayList<PayDayTO> selectPayDayList(); 
}
