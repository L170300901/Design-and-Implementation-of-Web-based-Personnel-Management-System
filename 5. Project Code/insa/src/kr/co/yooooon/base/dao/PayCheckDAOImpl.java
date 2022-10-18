package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class PayCheckDAOImpl implements PayCheckDAO{

	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static PayCheckDAO instance;

	private PayCheckDAOImpl() {
	}

	public static PayCheckDAO getInstance() {
		if (instance == null)
			instance = new PayCheckDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<PayDateTO> payDateList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" payDateList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select *from payroll_date");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<PayDateTO> payDateList = new ArrayList<PayDateTO>();
			PayDateTO payDate = null;
			while (rs.next()) {
				payDate = new PayDateTO();
				payDate.setStartDate(rs.getString("START_DATE"));
				payDate.setEndDate(rs.getString("END_DATE"));
				payDateList.add(payDate);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" payDateList 종료 ");
			}
			return payDateList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void createHobongList(String startDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" createHobongList 시작 ");
		}
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			System.out.println("@@@@@@@@@"+startDate);
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("{call P_HOBONG_TABLE(?)}");
			cstmt = con.prepareCall(query.toString());
			cstmt.setString(1, startDate);
			cstmt.executeUpdate();										
			if (logger.isDebugEnabled()) {
				logger.debug(" createHobongList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
	         dataSourceTransactionManager.close(cstmt);
	      }

	}
	@Override
	public ArrayList<HobongTO> HobongList(String startDate) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" HobongList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("select *from hobong where START_DATE=?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			rs = pstmt.executeQuery();		
			
			ArrayList<HobongTO> hobongList = new ArrayList<HobongTO>();
			HobongTO hobong = null;
			while (rs.next()) {
				hobong = new HobongTO();
				hobong.setStartDay(rs.getString("START_DATE"));
				hobong.setEndDay(rs.getString("END_DATE"));
				hobong.setPositionCode(rs.getString("POSITION_CODE"));
				hobong.setPositionName(rs.getString("POSITION_NAME"));
				//System.out.println("@@@@");
				hobong.setHobong(rs.getString("HOBONG"));
				hobong.setBasicSalary(rs.getString("BASIC_SALARY"));
				hobong.setSum(rs.getString("SUM"));
				//System.out.println("@@@@");
				hobongList.add(hobong);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" HobongList 종료 ");
			}
			return hobongList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}
	@Override
	public ArrayList<HobongTO> hobongPositionList(String startDate){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" hobongPositionList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT (POSITION_CODE), POSITION_NAME FROM HOBONG WHERE START_DATE=? \r\n" + 
					"ORDER BY POSITION_CODE");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			rs = pstmt.executeQuery();
			System.out.println(startDate);

			ArrayList<HobongTO> hobongPositionList = new ArrayList<HobongTO>();
			HobongTO hobongPosition = null;
			while (rs.next()) {
				hobongPosition = new HobongTO();
				hobongPosition.setStartDay(startDate);
				//hobongPosition.setEndDay(rs.getString("END_DATE"));
				
				hobongPosition.setPositionCode(rs.getString("POSITION_CODE"));
				hobongPosition.setPositionName(rs.getString("POSITION_NAME"));
				//hobongPosition.setHobong(rs.getString("HOBONG"));
				//hobongPosition.setBasicSalary(rs.getString("BASIC_SALARY"));
				//hobongPosition.setSum(rs.getString("SUM"));
				hobongPositionList.add(hobongPosition);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" hobongPositionList 종료 ");
			}
			return hobongPositionList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<HobongTO> findHobongTable(String positionCode, String positionName, String startDate){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findHobongTable 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT HOBONG, BASIC_SALARY, SUM FROM HOBONG WHERE START_DATE=? AND POSITION_CODE=? AND POSITION_NAME=? order by TO_NUMBER(basic_salary)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, startDate);
			pstmt.setString(2, positionCode);
			pstmt.setString(3, positionName);
			rs = pstmt.executeQuery();
			//System.out.println(startDate);

			ArrayList<HobongTO> findHobongTableList = new ArrayList<HobongTO>();
			HobongTO hobongPosition = null;
			while (rs.next()) {
				hobongPosition = new HobongTO();
				//hobongPosition.setStartDay(rs.getString("START_DATE"));
				//hobongPosition.setEndDay(rs.getString("END_DATE"));
				
				//hobongPosition.setPositionCode(rs.getString("POSITION_CODE"));
				//hobongPosition.setPositionName(rs.getString("POSITION_NAME"));
				hobongPosition.setHobong(rs.getString("HOBONG"));
				hobongPosition.setBasicSalary(rs.getString("BASIC_SALARY"));
				hobongPosition.setSum(rs.getString("SUM"));
				findHobongTableList.add(hobongPosition);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findHobongTable 종료 ");
			}
			return findHobongTableList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void payCheckResist(String increaseAmount,String initialValue,String startDate,String positionCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" payCheckResist 시작 ");
		}
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			System.out.println("@@@@@@@@@"+startDate);
			System.out.println("@@@@@@@@@"+increaseAmount);
			System.out.println("@@@@@@@@@"+initialValue);
			System.out.println("@@@@@@@@@"+positionCode);
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("{call P_INSERT_HOBONG(?,?,?,?)}");
			cstmt = con.prepareCall(query.toString());
			cstmt.setString(1, startDate);
			cstmt.setString(2, increaseAmount);
			cstmt.setString(3, initialValue);
			cstmt.setString(4, positionCode);
			cstmt.executeUpdate();										
			if (logger.isDebugEnabled()) {
				logger.debug(" payCheckResist 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
	         dataSourceTransactionManager.close(cstmt);
	    }

	}
	
}
