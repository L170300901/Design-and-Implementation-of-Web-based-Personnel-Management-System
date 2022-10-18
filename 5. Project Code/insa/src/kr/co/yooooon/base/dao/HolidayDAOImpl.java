package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class HolidayDAOImpl implements HolidayDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static HolidayDAO instance;

	private HolidayDAOImpl() {
	}

	public static HolidayDAO getInstance() {
		if (instance == null)
			instance = new HolidayDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<HolidayTO> selectHolidayList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectHolidayList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select to_char(apply_day, 'YYYY-MM-DD') apply_day, holiday_name, note from holiday order by apply_day");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<HolidayTO> holidayList = new ArrayList<HolidayTO>();
			HolidayTO holiday = null;
			while (rs.next()) {
				holiday = new HolidayTO();
				holiday.setApplyDay(rs.getString("APPLY_DAY"));
				holiday.setHolidayName(rs.getString("HOLIDAY_NAME"));
				holiday.setNote(rs.getString("NOTE"));
				holidayList.add(holiday);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectHolidayList 종료 ");
			}
			return holidayList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public String selectWeekDayCount(String startDate, String endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectWeekDayCount 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT WEEKDAY_COUNTING_FUNC(?,?) WEEKDAY_COUNT FROM DUAL ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			rs = pstmt.executeQuery();

			String weekdayCount = null;
			while (rs.next()) {
				weekdayCount = rs.getString("WEEKDAY_COUNT");
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectWeekDayCount 종료 "+"+++"+weekdayCount);
			}
			return weekdayCount;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateCodeList(HolidayTO holyday) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("UPDATE holiday SET ");
			query.append("HOLIDAY_NAME = ?, NOTE = ? ");
			query.append("WHERE APPLY_DAY = ? ");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, holyday.getHolidayName());
			pstmt.setString(2, holyday.getNote());
			pstmt.setString(3, holyday.getApplyDay());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" updateCodeList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
	
	@Override
	public void insertCodeList(HolidayTO holyday) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" insert into holiday values(TO_DATE(?,'YYYY-MM-DD') ,?,?)");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, holyday.getApplyDay());
			pstmt.setString(2, holyday.getHolidayName());
			pstmt.setString(3, holyday.getNote());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" insertCodeList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

	@Override
	public void deleteCodeList(HolidayTO holyday) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("delete holiday where APPLY_DAY = ?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, holyday.getApplyDay());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" deleteCodeList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

}
