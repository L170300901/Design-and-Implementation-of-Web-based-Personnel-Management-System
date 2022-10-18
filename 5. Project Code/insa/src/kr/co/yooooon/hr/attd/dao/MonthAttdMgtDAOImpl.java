package kr.co.yooooon.hr.attd.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.to.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.attd.to.*;

public class MonthAttdMgtDAOImpl implements MonthAttdMgtDAO{


	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static MonthAttdMgtDAO instance;
	private MonthAttdMgtDAOImpl(){}
	public static MonthAttdMgtDAO getInstance(){
		if(instance==null) instance=new MonthAttdMgtDAOImpl();
		return instance;
	}
	
	@Override
	public HashMap<String, Object> batchMonthAttdMgtProcess(String applyYearMonth) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" batchMonthAttdMgtProcess 시작 ");
		}

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ResultTO resultTO = null;
		ArrayList<MonthAttdMgtTO> monthAttdMgtList=new ArrayList<MonthAttdMgtTO>();
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("{call P_HR_ATTENDANCE.P_CREATE_MONTH_ATTD_MANAGE(?,?,?,?,?)}");
			cstmt = con.prepareCall(query.toString());
			System.out.println(applyYearMonth);
			cstmt.setString(1, applyYearMonth);
			cstmt.setString(2, "인사팀");
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.execute();

			resultTO = new ResultTO();
			resultTO.setErrorCode(cstmt.getString(4));
			resultTO.setErrorMsg(cstmt.getString(5));
			
			rs = (ResultSet)cstmt.getObject(3);
			MonthAttdMgtTO monthAttdMgt = null;
			while (rs.next()) {
				monthAttdMgt = new MonthAttdMgtTO();
				monthAttdMgt.setEmpCode(rs.getString("EMP_CODE"));
				monthAttdMgt.setEmpName(rs.getString("EMP_NAME"));
				monthAttdMgt.setApplyYearMonth(rs.getString("APPLY_YEAR_MONTH"));
				monthAttdMgt.setBasicWorkDays(rs.getString("BASIC_WORK_DAYS"));
				monthAttdMgt.setWeekdayWorkDays(rs.getString("WEEKDAY_WORK_DAYS"));
				monthAttdMgt.setBasicWorkHour(rs.getString("BASIC_WORK_HOUR"));
				monthAttdMgt.setWorkHour(rs.getString("WORK_HOUR"));
				monthAttdMgt.setOverWorkHour(rs.getString("OVER_WORK_HOUR"));
				monthAttdMgt.setNightWorkHour(rs.getString("NIGHT_WORK_HOUR"));
				monthAttdMgt.setHolidayWorkDays(rs.getString("HOLIDAY_WORK_DAYS"));
				monthAttdMgt.setHolidayWorkHour(rs.getString("HOLIDAY_WORK_HOUR"));
				monthAttdMgt.setLateDays(rs.getString("LATE_DAYS"));
				monthAttdMgt.setEarlyLeaveDays(rs.getString("EARLY_LEAVE_DAYS"));
				monthAttdMgt.setAbsentDays(rs.getString("ABSENT_DAYS"));
				monthAttdMgt.setHalfHolidays(rs.getString("HALF_HOLIDAYS"));
				monthAttdMgt.setHolidays(rs.getString("HOLIDAYS"));
				monthAttdMgt.setFinalizeStatus(rs.getString("FINALIZE_STATUS"));
				monthAttdMgtList.add(monthAttdMgt);
			}
			resultMap.put("monthAttdMgtList", monthAttdMgtList);
			resultMap.put("resultTO", resultTO);
			
			if (logger.isDebugEnabled()) {
				logger.debug(" batchMonthAttdMgtProcess 종료 ");
			}
			return resultMap;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt,rs);
		}
	}
	@Override
	public void updateMonthAttdMgtList(MonthAttdMgtTO monthAttdMgt) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateMonthAttdMgtList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE MONTH_ATTD_MANAGE SET ");
			query.append("FINALIZE_STATUS = ? ");
			query.append("WHERE EMP_CODE = ? AND APPLY_YEAR_MONTH = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, monthAttdMgt.getFinalizeStatus());
			pstmt.setString(2, monthAttdMgt.getEmpCode());
			pstmt.setString(3, monthAttdMgt.getApplyYearMonth());

			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" updateMonthAttdMgtList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}


}
