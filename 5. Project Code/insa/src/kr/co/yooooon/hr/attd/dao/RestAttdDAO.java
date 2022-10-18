package kr.co.yooooon.hr.attd.dao;

import java.util.*;

import kr.co.yooooon.hr.attd.to.*;

public interface RestAttdDAO {
	public ArrayList<RestAttdTO> selectRestAttdListByToday(String empCode, String toDay);
	public ArrayList<RestAttdTO> selectRestAttdList(String empCode, String startDate, String endDate);
	public ArrayList<RestAttdTO> selectRestAttdListCode(String empCode, String startDate, String endDate, String code);
	public ArrayList<RestAttdTO> selectRestAttdListByDept(String deptName, String startDate, String endDate);
	public ArrayList<RestAttdTO> selectRestAttdListByAllDept();
	public void insertRestAttd(RestAttdTO restAttd);
	public void updateRestAttd(RestAttdTO restAttd);
	public void deleteRestAttd(RestAttdTO restAttd);
}
