package kr.co.yooooon.hr.attd.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.to.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.attd.to.*;

public class DayAttdDAOImpl implements DayAttdDAO{

	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static DayAttdDAO instance;
	private DayAttdDAOImpl(){}
	public static DayAttdDAO getInstance(){
		if(instance==null) instance=new DayAttdDAOImpl();
		return instance;
	}
	
	@Override
	public ArrayList<DayAttdTO> selectDayAttdList(String empCode, String applyDay) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectDayAttdList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DayAttdTO> dayAttdList=new ArrayList<DayAttdTO>();
		try {
			con = dataSourceTransactionManager.getConnection();
			System.out.println("@@@@"+empCode+"@@"+applyDay);
			StringBuffer query = new StringBuffer();
			query.append("SELECT D.EMP_CODE, E.EMP_NAME, D.DAY_ATTD_CODE, ");
			query.append("TO_CHAR(D.APPLY_DAY, 'YYYY/MM/DD') APPLY_DAY, ");
			query.append("D.ATTD_TYPE_CODE, D.ATTD_TYPE_NAME, D.TIME ");
			query.append("FROM DAY_ATTD D, EMP_NEW E WHERE D.EMP_CODE = ? AND D.APPLY_DAY = ? ");
			query.append("AND D.EMP_CODE = E.EMP_CODE ");
			query.append("ORDER BY D.TIME ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			pstmt.setString(2, applyDay);
			rs = pstmt.executeQuery();

			DayAttdTO dayAttd = null;
			while (rs.next()) {
				System.out.println("!!!!!!");
				dayAttd = new DayAttdTO();
				dayAttd.setEmpCode(rs.getString("EMP_CODE"));
				dayAttd.setEmpName(rs.getString("EMP_NAME"));
				dayAttd.setDayAttdCode(rs.getString("DAY_ATTD_CODE"));
				dayAttd.setApplyDay(rs.getString("APPLY_DAY"));
				dayAttd.setAttdTypeCode(rs.getString("ATTD_TYPE_CODE"));
				dayAttd.setAttdTypeName(rs.getString("ATTD_TYPE_NAME"));
				dayAttd.setTime(rs.getString("TIME"));
				dayAttdList.add(dayAttd);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectDayAttdList 종료 ");
			}
			return dayAttdList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void insertDayAttd(DayAttdTO dayAttd) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertDayAttd 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO DAY_ATTD VALUES (?, DAY_ATTD_CODE_SEQ.NEXTVAL, ?, ?, ?, ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dayAttd.getEmpCode());
			pstmt.setString(2, dayAttd.getApplyDay());
			pstmt.setString(3, dayAttd.getAttdTypeCode());
			pstmt.setString(4, dayAttd.getAttdTypeName());
			pstmt.setString(5, dayAttd.getTime());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("insertDayAttd 종료");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	
	@Override
	public void deleteDayAttd(DayAttdTO dayAttd) {
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteDayAttd 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM DAY_ATTD WHERE EMP_CODE = ? AND DAY_ATTD_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dayAttd.getEmpCode());
			pstmt.setString(2, dayAttd.getDayAttdCode());
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" deleteDayAttd 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	@Override
	public ResultTO batchInsertDayAttd(DayAttdTO dayAttd) {
		 if (logger.isDebugEnabled()) {
	         logger.debug(" batchInsertDayAttd 시작 ");
	      }

	      Connection con = null;
	      CallableStatement cstmt = null;
	      ResultTO resultTO = null;

	      try {
	         con = dataSourceTransactionManager.getConnection();
	         StringBuffer query = new StringBuffer();
	         query.append("{call P_INSERT_DAY_ATTD(?,DAY_ATTD_CODE_SEQ.NEXTVAL,?,?,?,?,?,?)}");
	         cstmt = con.prepareCall(query.toString());
				System.out.println(dayAttd.getAttdTypeCode());
				System.out.println(dayAttd.getAttdTypeName());
	         cstmt.setString(1, dayAttd.getEmpCode());
	         cstmt.setString(2, dayAttd.getAttdTypeCode());
	         cstmt.setString(3, dayAttd.getAttdTypeName());
	         cstmt.setString(4, dayAttd.getApplyDay());
	         cstmt.setString(5, dayAttd.getTime());
	         cstmt.registerOutParameter(6,java.sql.Types.VARCHAR);
	         cstmt.registerOutParameter(7,java.sql.Types.VARCHAR);	         
	         cstmt.execute();	         
	         
	         resultTO = new ResultTO();
			 resultTO.setErrorCode(cstmt.getString(6));
			 resultTO.setErrorMsg(cstmt.getString(7));			 
	         
	         System.out.println("★★★★★★★★프로시저에러★★★★★★★★★★"+cstmt.getString(6));
	         System.out.println("★★★★★★★★프로시저에러★★★★★★★★★★"+cstmt.getString(7));
	         //에러가 없으면 6,7에 null 값이 출력이 된다 

			 if (logger.isDebugEnabled()) {
	            logger.debug(" batchInsertDayAttd 종료 ");
	         }
	         return resultTO;	       
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(cstmt);
	      }
	}
}
