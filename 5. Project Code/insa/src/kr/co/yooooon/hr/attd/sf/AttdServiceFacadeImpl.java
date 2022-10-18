package kr.co.yooooon.hr.attd.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.to.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.attd.applicationService.*;
import kr.co.yooooon.hr.attd.to.*;

public class AttdServiceFacadeImpl implements AttdServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	private AttdApplicationService attdApplicationService=AttdApplicationServiceImpl.getInstance();
	/*private AnnualLeaveApplicationService annualLeaveApplicationService=AnnualLeaveApplicationServiceImpl.getInstance();*/
	
	private static AttdServiceFacade instance;
	private AttdServiceFacadeImpl(){}
	public static AttdServiceFacade getInstance(){
		if(instance==null) instance=new AttdServiceFacadeImpl();
		return instance;
	}
	
	@Override
	public ArrayList<DayAttdTO> findDayAttdList(String empCode, String applyDay) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<DayAttdTO> dayAttdList=attdApplicationService.findDayAttdList(empCode, applyDay);
			if (logger.isDebugEnabled()) {
				logger.debug(" findDayAttdList 종료 ");
			}
			return dayAttdList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public ResultTO registDayAttd(DayAttdTO dayAttd) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registDayAttd 시작 ");
		}
		/*
		 DataSourceTransactionManager------->
		 
		 위에서 빈 객체로 생성한 transactionManager를 사용해 실제 트랜잭션에서 예외가 발생하면 롤백이 되는지 알아보자.
		 */
		dataSourceTransactionManager.beginTransaction();
		
		try {
			ResultTO resultTO=attdApplicationService.registDayAttd(dayAttd);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" registDayAttd 종료 ");
			}
			return resultTO;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
	}

	@Override
	public void removeDayAttdList(ArrayList<DayAttdTO> dayAttdList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" removeDayAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.removeDayAttdList(dayAttdList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" removeDayAttdList 종료 ");
		}
	}
	
	@Override
	public ArrayList<RestAttdTO> findRestAttdList(String empCode, String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdList(empCode, startDate, endDate, code);
			if (logger.isDebugEnabled()) {
				logger.debug(" findRestAttdList 종료 ");
			}
			return restAttdList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByDept() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByDept 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdListByDept();
			if (logger.isDebugEnabled()) {
				logger.debug(" findRestAttdListByDept 종료 ");
			}
			return restAttdList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public ArrayList<RestAttdTO> findRestAttdListByToday(String empCode, String toDay) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByToday 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<RestAttdTO> restAttdList = attdApplicationService.findRestAttdListByToday(empCode,toDay);
			if (logger.isDebugEnabled()) {
				logger.debug(" findRestAttdListByToday 종료 ");
			}
			return restAttdList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public void registRestAttd(RestAttdTO restAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" registRestAttd 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.registRestAttd(restAttd);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" registRestAttd 종료 ");
		}		
	}

	@Override
	public void modifyRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyRestAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.modifyRestAttdList(restAttdList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyRestAttdList 종료 ");
		}
				
	}
	
	@Override
	public void removeRestAttdList(ArrayList<RestAttdTO> restAttdList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" removeRestAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.removeRestAttdList(restAttdList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" removeRestAttdList 종료 ");
		}		
	}
	
	@Override
	public ArrayList<DayAttdMgtTO> findDayAttdMgtList(String applyDay, String dept) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findDayAttdMgtList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<DayAttdMgtTO> dayAttdMgtList = attdApplicationService.findDayAttdMgtList(applyDay, dept);
			if (logger.isDebugEnabled()) {
				logger.debug(" findDayAttdMgtList 종료 ");
			}
			return dayAttdMgtList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public void modifyDayAttdMgtList(ArrayList<DayAttdMgtTO> dayAttdMgtList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyDayAttdMgtList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.modifyDayAttdMgtList(dayAttdMgtList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
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

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<MonthAttdMgtTO> monthAttdMgtList = attdApplicationService.findMonthAttdMgtList(applyYearMonth);
			if (logger.isDebugEnabled()) {
				logger.debug(" findMonthAttdMgtList 종료 ");
			}
			return monthAttdMgtList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void modifyMonthAttdMgtList(ArrayList<MonthAttdMgtTO> monthAttdMgtList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthAttdMgtList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.modifyMonthAttdMgtList(monthAttdMgtList);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthAttdMgtList 종료 ");
		}		
	}
	@Override
	public void insertDayAttd(DayAttdTO dayAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertDayAttdList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			attdApplicationService.insertDayAttd(dayAttd);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" insertDayAttdList 종료 ");
		}
		
	}

}
