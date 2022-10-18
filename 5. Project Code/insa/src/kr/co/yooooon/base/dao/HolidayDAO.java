package kr.co.yooooon.base.dao;

import java.util.ArrayList;

import kr.co.yooooon.base.to.HolidayTO;

public interface HolidayDAO {
	public ArrayList<HolidayTO> selectHolidayList();
	String selectWeekDayCount(String startDate, String endDate);
	public void updateCodeList(HolidayTO holyday);
	public void insertCodeList(HolidayTO holyday);
	public void deleteCodeList(HolidayTO holyday);
}
