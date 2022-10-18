package kr.co.yooooon.hr.attd.dao;

import java.util.ArrayList;

import kr.co.yooooon.common.to.ResultTO;
import kr.co.yooooon.hr.attd.to.DayAttdTO;

public interface DayAttdDAO {
	public ArrayList<DayAttdTO> selectDayAttdList(String empCode, String applyDay);

	public ResultTO batchInsertDayAttd(DayAttdTO dayAttd);
	
	public void insertDayAttd(DayAttdTO dayAttd);
	public void deleteDayAttd(DayAttdTO dayAttd);
}
