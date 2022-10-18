package kr.co.yooooon.hr.attd.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.attd.to.*;

public class RestAttdDAOImpl implements RestAttdDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static RestAttdDAO instance;
	private RestAttdDAOImpl(){}
	public static RestAttdDAO getInstance(){
		if(instance==null) instance=new RestAttdDAOImpl();
		return instance;
	}
	
	@Override
	public ArrayList<RestAttdTO> selectRestAttdListByToday(String empCode, String toDay) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectRestAttdListByToday 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME,R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? AND R.START_DATE = to_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.END_DATE = to_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.APPLOVAL_STATUS = 'Approval' ");
			query.append("AND R.REST_TYPE_CODE <> 'ASC008' ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, toDay);
			pstmt.setString(3, toDay);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				
				restAttdList.add(restAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectRestAttdListByToday 종료 ");
			}
			return restAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public ArrayList<RestAttdTO> selectRestAttdList(String empCode, String startDate, String endDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectRestAttdList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP E WHERE R.EMP_CODE = ? AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.EMP_CODE = E.EMP_CODE ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectRestAttdList 종료 ");
			}
			return restAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public void insertRestAttd(RestAttdTO restAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertRestAttd 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO REST_ATTD VALUES (?,REST_ATTD_CODE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getEmpCode());
			pstmt.setString(2, restAttd.getRestTypeCode());
			pstmt.setString(3, restAttd.getRestTypeName());
			pstmt.setString(4, restAttd.getRequestDate());
			pstmt.setString(5, restAttd.getStartDate());
			pstmt.setString(6, restAttd.getEndDate());
			pstmt.setString(7, restAttd.getCause());
			pstmt.setString(8, restAttd.getApplovalStatus());
			pstmt.setString(9, restAttd.getRejectCause());
			pstmt.setString(10, restAttd.getCost());
			pstmt.setString(11, restAttd.getStartTime());
			pstmt.setString(12, restAttd.getEndTime());
			pstmt.setString(13, restAttd.getNumberOfDays());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" insertRestAttd 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public ArrayList<RestAttdTO> selectRestAttdListCode(String empCode, String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectRestAttdList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME ");
			query.append("FROM REST_ATTD R, EMP_new E WHERE R.EMP_CODE = ? AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') ");
			query.append("AND R.REST_TYPE_CODE = ? AND R.EMP_CODE = E.EMP_CODE ");

			System.out.println(empCode);
			System.out.println(startDate);
			System.out.println(endDate);
			System.out.println(code);
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			pstmt.setString(4, code);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectRestAttdList 종료 ");
			}
			return restAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}

	}

	@Override
	public ArrayList<RestAttdTO> selectRestAttdListByDept(String deptName, String startDate, String endDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectRestAttdListByDept 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, ");
			query.append("R.REST_TYPE_CODE, R.REST_TYPE_NAME, ");
			query.append("TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, ");
			query.append("TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, ");
			query.append("TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, ");
			query.append("R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, ");
			query.append("R.REJECT_CAUSE, R.START_TIME, R.END_TIME");
			query.append(" FROM REST_ATTD R, EMP E");
			query.append(" WHERE E.DEPT_CODE = ?");
			query.append(" AND R.REQUEST_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')");
			query.append(" AND R.EMP_CODE = E.EMP_CODE ");
			

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, deptName);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectRestAttdListByDept 종료 ");
			}
			return restAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public ArrayList<RestAttdTO> selectRestAttdListByAllDept() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectRestAttdListByAllDept 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<RestAttdTO> restAttdList=new ArrayList<RestAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT R.EMP_CODE, E.EMP_NAME, R.REST_ATTD_CODE, \r\n" + 
					"R.REST_TYPE_CODE, R.REST_TYPE_NAME, \r\n" + 
					"TO_CHAR(R.REQUEST_DATE, 'YYYY-MM-DD') REQUEST_DATE, \r\n" + 
					"TO_CHAR(R.START_DATE, 'YYYY-MM-DD') START_DATE, \r\n" + 
					"TO_CHAR(R.END_DATE, 'YYYY-MM-DD') END_DATE, \r\n" + 
					"R.NUMBER_OF_DAYS, R.COST, R.CAUSE, R.APPLOVAL_STATUS, \r\n" + 
					"R.REJECT_CAUSE, R.START_TIME, R.END_TIME \r\n" + 
					"FROM REST_ATTD R, EMP_new E\r\n" + 
					"WHERE R.EMP_CODE = E.EMP_CODE ");
			

			pstmt = con.prepareStatement(query.toString());
			//pstmt.setString(1, applyDay);
			rs = pstmt.executeQuery();
			RestAttdTO restAttd = null;
			while (rs.next()) {
				restAttd = new RestAttdTO();
				restAttd.setEmpCode(rs.getString("EMP_CODE"));
				restAttd.setEmpName(rs.getString("EMP_NAME"));
				restAttd.setRestAttdCode(rs.getString("REST_ATTD_CODE"));
				restAttd.setRestTypeCode(rs.getString("REST_TYPE_CODE"));
				restAttd.setRestTypeName(rs.getString("REST_TYPE_NAME"));
				restAttd.setRequestDate(rs.getString("REQUEST_DATE"));
				restAttd.setStartDate(rs.getString("START_DATE"));
				restAttd.setEndDate(rs.getString("END_DATE"));
				restAttd.setNumberOfDays(rs.getString("NUMBER_OF_DAYS"));
				restAttd.setCost(rs.getString("COST"));
				restAttd.setCause(rs.getString("CAUSE"));
				restAttd.setApplovalStatus(rs.getString("APPLOVAL_STATUS"));
				restAttd.setRejectCause(rs.getString("REJECT_CAUSE"));
				restAttd.setStartTime(rs.getString("START_TIME"));
				restAttd.setEndTime(rs.getString("END_TIME"));
				restAttdList.add(restAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectRestAttdListByAllDept 종료 ");
			}
			return restAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateRestAttd(RestAttdTO restAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateRestAttd 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE REST_ATTD SET ");
			query.append("CAUSE = ?, APPLOVAL_STATUS = ?, REJECT_CAUSE = ? ");
			query.append("WHERE EMP_CODE = ? AND REST_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getCause());
			pstmt.setString(2, restAttd.getApplovalStatus());
			pstmt.setString(3, restAttd.getRejectCause());
			pstmt.setString(4, restAttd.getEmpCode());
			pstmt.setString(5, restAttd.getRestAttdCode());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" updateRestAttd 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public void deleteRestAttd(RestAttdTO restAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteRestAttd 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println(restAttd.getEmpCode());
			System.out.println(restAttd.getRestAttdCode());
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM REST_ATTD WHERE EMP_CODE = ? AND REST_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, restAttd.getEmpCode());
			pstmt.setString(2, restAttd.getRestAttdCode());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" deleteRestAttd 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
				
	}
	
	
}
