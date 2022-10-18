package kr.co.yooooon.hr.emp.dao;

import java.util.ArrayList;

import kr.co.yooooon.hr.emp.to.WorkInfoTO;


public interface WorkInfoDAO {
	public ArrayList<WorkInfoTO> selectWorkList(String empCode);

	public void insertWorkInfo(WorkInfoTO workInfo);;
	public void updateWorkInfo(WorkInfoTO workInfo);
	public void deleteWorkInfo(WorkInfoTO workInfo);
}
