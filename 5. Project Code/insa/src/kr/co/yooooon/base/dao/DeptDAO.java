package kr.co.yooooon.base.dao;

import java.util.ArrayList;

import kr.co.yooooon.base.to.DeptTO;

public interface DeptDAO {
	public ArrayList<DeptTO> selectDeptList();
	
	public void updateDept(DeptTO dept);
	public void registDept(DeptTO dept);
	public void deleteDept(DeptTO dept);
}
