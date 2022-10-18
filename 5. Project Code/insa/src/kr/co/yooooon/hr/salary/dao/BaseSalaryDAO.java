package kr.co.yooooon.hr.salary.dao;

import java.util.ArrayList;

import kr.co.yooooon.hr.salary.to.BaseSalaryTO;

public interface BaseSalaryDAO {
	public ArrayList<BaseSalaryTO> selectBaseSalaryList();
	public void updateBaseSalary(BaseSalaryTO baseSalary);
	public void updatePosition(BaseSalaryTO position);
	public void insertPosition(BaseSalaryTO position);
	public void deletePosition(BaseSalaryTO position);
}
