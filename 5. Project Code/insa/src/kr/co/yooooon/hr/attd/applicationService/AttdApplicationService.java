package kr.co.yooooon.hr.attd.applicationService;

import java.util.*;

import kr.co.yooooon.common.to.*;
import kr.co.yooooon.hr.attd.to.*;

public interface AttdApplicationService {
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay);
	public ResultTO registDayAttd(DayAttdTO dayAttd);
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList);
	public void insertDayAttd(DayAttdTO dayAttd); //test

	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String dept);
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList);
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth);
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList);
	
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code);
	public ArrayList<RestAttdTO> findRestAttdListByDept();
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay);
	public void registRestAttd(RestAttdTO restAttd);
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList);
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList);
}
