package kr.co.yooooon.hr.attd.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.to.*;
import kr.co.yooooon.hr.attd.dao.*;
import kr.co.yooooon.hr.attd.to.*;

public class AttdApplicationServiceImpl implements AttdApplicationService{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DayAttdDAO dayAttdDAO=DayAttdDAOImpl.getInstance();
	private RestAttdDAO restAttdDAO = RestAttdDAOImpl.getInstance();
	private DayAttdMgtDAO dayAttdMgtDAO = DayAttdMgtDAOImpl.getInstance();
	private MonthAttdMgtDAO monthAttdMgtDAO = MonthAttdMgtDAOImpl.getInstance();
	private static AttdApplicationService instance;
	private AttdApplicationServiceImpl(){}
	public static AttdApplicationService getInstance(){
		if(instance==null) instance=new AttdApplicationServiceImpl();
		return instance;
	}
	
	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdList 시작 ");
		}

		ArrayList<DayAttdTO> dayAttdList=dayAttdDAO.selectDayAttdList(empCode, applyDay);

		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdList 종료 ");
		}
		return dayAttdList;
	}

	@Override
	public ResultTO registDayAttd(DayAttdTO dayAttd) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registDayAttd 시작 ");
		}
		
		ResultTO resultTO=dayAttdDAO.batchInsertDayAttd(dayAttd);
		
		if (logger.isDebugEnabled()) {
			logger.debug(" registDayAttd 종료 ");
		}
		return resultTO;		
		
	}

	@Override
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" removeDayAttdList 시작 ");
		}
		for(DayAttdTO dayAttd : dayAttdList){
			dayAttdDAO.deleteDayAttd(dayAttd);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" removeDayAttdList 종료 ");
		}
		
	}
	
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByToday 시작 ");
		}
		ArrayList<RestAttdTO> restAttdList = restAttdDAO.selectRestAttdListByToday(empCode, toDay);
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByToday 종료 ");
		}
		return restAttdList;
	}

	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyDayAttdMgtList 시작 ");
		}

		for(DayAttdMgtTO dayAttdMgt : dayAttdMgtList){
			if(dayAttdMgt.getStatus().equals("update")){
				dayAttdMgtDAO.updateDayAttdMgtList(dayAttdMgt);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyDayAttdMgtList 종료 ");
		}		
	}

	@Override
	public ArrayList<MonthAttdMgtTO> findMonthAttdMgtList(String applyYearMonth) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findMonthAttdMgtList 시작 ");
		}

		HashMap<String, Object> resultMap = monthAttdMgtDAO.batchMonthAttdMgtProcess(applyYearMonth);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<MonthAttdMgtTO> monthAttdMgtList = (ArrayList<MonthAttdMgtTO>) resultMap.get("monthAttdMgtList");
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findMonthAttdMgtList 종료 ");
		}
		return monthAttdMgtList;
	}
	
	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept() {
		// TODO Auto-generated method stub
		ArrayList<RestAttdTO> restAttdList = null;
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByDept 시작 ");
		}
		
		
		restAttdList = restAttdDAO.selectRestAttdListByAllDept();
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByDept 종료 ");
		}
		return restAttdList;
	}
	
	
	@Override
	public void registRestAttd(RestAttdTO restAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" registRestAttd 시작 ");
		}
		restAttdDAO.insertRestAttd(restAttd);
		if (logger.isDebugEnabled()) {
			logger.debug(" registRestAttd 종료 ");
		}		
	}
	
	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdList 시작 ");
		}
		ArrayList<RestAttdTO> restAttdList=null;
		//System.out.println("초과근무때매확인중"+empCode);
		//System.out.println("초과근무때매확인중"+startDate);
		//System.out.println("초과근무때매확인중"+endDate);
		//System.out.println("초과근무때매확인중"+code);
		
		if(code == "")
			restAttdList = restAttdDAO.selectRestAttdList(empCode, startDate, endDate);
		else
			restAttdList = restAttdDAO.selectRestAttdListCode(empCode, startDate, endDate, code);
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdList 종료 ");
		}
		return restAttdList;
	}
	
	@Override
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" removeRestAttdList 시작 ");
		}
		for(RestAttdTO restAttd : restAttdList){
			restAttdDAO.deleteRestAttd(restAttd);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" removeRestAttdList 종료 ");
		}		
	}

	@Override
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyRestAttdList 시작 ");
		}
		for(RestAttdTO restAttd : restAttdList){
			if(restAttd.getStatus().equals("update")){
				restAttdDAO.updateRestAttd(restAttd);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyRestAttdList 종료 ");
		}		
	}
	
	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String dept) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdMgtList 시작 ");
		}

		HashMap<String, Object> resultMap = dayAttdMgtDAO.batchDayAttdMgtProcess(applyDay, dept);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		@SuppressWarnings("unchecked")
		ArrayList<DayAttdMgtTO> dayAttdMgtList = (ArrayList<DayAttdMgtTO>) resultMap.get("dayAttdMgtList");

		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdMgtList 종료 ");
		}
		return dayAttdMgtList;
	}
	
	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthAttdMgtList 시작 ");
		}

		for(MonthAttdMgtTO monthAttdMgt : monthAttdMgtList){
			if(monthAttdMgt.getStatus().equals("update")){
				monthAttdMgtDAO.updateMonthAttdMgtList(monthAttdMgt);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthAttdMgtList 종료 ");
		}		
	}
	@Override
	public void insertDayAttd(DayAttdTO dayAttd) { //test
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertDayAttd시작 ");
		}

		dayAttdDAO.insertDayAttd(dayAttd);
		

		if (logger.isDebugEnabled()) {
			logger.debug(" insertDayAttd 종료 ");
		}	
	}

}
