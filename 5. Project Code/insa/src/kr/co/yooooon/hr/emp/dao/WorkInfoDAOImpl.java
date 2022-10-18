package kr.co.yooooon.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.emp.to.WorkInfoTO;

public class WorkInfoDAOImpl implements WorkInfoDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static WorkInfoDAO instance;
	
	public static WorkInfoDAO getInstance(){
		if(instance==null) instance=new WorkInfoDAOImpl();
		return instance;
	}
	
	public ArrayList<WorkInfoTO> selectWorkList(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectWorkList 시작 ");
		}
		
		ArrayList<WorkInfoTO> worklist=new ArrayList<WorkInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			WorkInfoTO workInfo = null;
	        query.append(" SELECT EMP_CODE,TO_CHAR(HIREDATE, 'YYYY/MM/DD') HIREDATE, "); 
	        query.append(" TO_CHAR(RETIRE_DATE, 'YYYY/MM/DD') RETIRE_DATE, OCCUPATION, "); 
	        query.append(" EMPLOYMENT_TYPE, HOBONG, WORK_INFO_DAYS, POSITION, DEPT_NAME "); 
	        query.append(" FROM WORK_INFO WHERE EMP_CODE = ? "); 
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
		         workInfo = new WorkInfoTO();
		            workInfo.setEmpCode(rs.getString("EMP_CODE"));
		            workInfo.setHiredate(rs.getString("HIREDATE"));
		            workInfo.setRetireDate(rs.getString("RETIRE_DATE"));
		            workInfo.setOccupation(rs.getString("OCCUPATION"));
		            workInfo.setEmploymentType(rs.getString("EMPLOYMENT_TYPE"));
		            workInfo.setHobong(rs.getString("HOBONG"));
		            workInfo.setWorkInfoDays(rs.getString("WORK_INFO_DAYS"));
		            workInfo.setPosition(rs.getString("POSITION"));
		            workInfo.setDeptName(rs.getString("DEPT_NAME"));
		            worklist.add(workInfo);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectWorkList 종료 ");
			}
			return worklist;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertWorkInfo(WorkInfoTO workInfo) {
		if (logger.isDebugEnabled()) {
			logger.debug(" insertWorkInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*query.append("INSERT INTO CAREER_INFO VALUES (?, CAREER_CODE_SEQ.NEXTVAL, ?, ?, ?, ?, ?)");*/
			query.append("INSERT INTO WORK_INFO VALUES (?, TO_DATE(?, 'YYYY/MM/DD'), TO_DATE(?, 'YYYY/MM/DD'), ?, ?, ?, TO_NUMBER(?), ?, ?)");
		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, workInfo.getEmpCode());
			pstmt.setString(2, workInfo.getHiredate());
			pstmt.setString(3, workInfo.getRetireDate());
			pstmt.setString(4, workInfo.getOccupation());
			pstmt.setString(5, workInfo.getEmploymentType());
			pstmt.setString(6, workInfo.getHobong());
			pstmt.setString(7, workInfo.getWorkInfoDays());
			pstmt.setString(8, workInfo.getPosition());
			pstmt.setString(9, workInfo.getDeptName());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" insertWorkInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

	@Override
	public void updateWorkInfo(WorkInfoTO workInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateWorkInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			
			query.append("UPDATE WORK_INFO SET ");
			query.append("HIREDATE = TO_DATE(?, 'YYYY/MM/DD'), RETIRE_DATE= TO_DATE(?, 'YYYY/MM/DD') , OCCUPATION = ?, EMPLOYMENT_TYPE = ?, ");
			query.append("HOBONG = ?, POSITION = ?, DEPT_NAME = ? ");
			query.append("WHERE EMP_CODE = ? and WORK_INFO_DAYS = TO_NUMBER(?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, workInfo.getHiredate());
			pstmt.setString(2, workInfo.getRetireDate());
			pstmt.setString(3, workInfo.getOccupation());
			pstmt.setString(4, workInfo.getEmploymentType());
			pstmt.setString(5, workInfo.getHobong());
			pstmt.setString(6, workInfo.getPosition());
			pstmt.setString(7, workInfo.getDeptName());
			pstmt.setString(8, workInfo.getEmpCode());
			pstmt.setString(9, workInfo.getWorkInfoDays());
			
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateWorkInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

	@Override
	public void deleteWorkInfo(WorkInfoTO workInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteWorkInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM WORK_INFO WHERE EMP_CODE = ? AND WORK_INFO_DAYS = TO_NUMBER(?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, workInfo.getEmpCode());
			pstmt.setString(2, workInfo.getWorkInfoDays());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteWorkInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}

	
}
