package kr.co.yooooon.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.emp.to.CareerInfoTO;

public class CareerInfoDAOImpl implements CareerInfoDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static CareerInfoDAO instance;
	
	public static CareerInfoDAO getInstance(){
		if(instance==null) instance=new CareerInfoDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<CareerInfoTO> selectCareerList(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectCareerList 시작 ");
		}
		

		ArrayList<CareerInfoTO> careerlist=new ArrayList<CareerInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			CareerInfoTO careerInfo = null;
			query.append("select emp_code,career_code,company_name,occupation,assignment_task, ");
			query.append("TO_CHAR(ex_retirement_date,'YYYY/MM/DD') ex_retirement_date,TO_CHAR(ex_hiredate,'YYYY/MM/DD') ex_hiredate ");
			query.append("from career_info ");
			query.append("where emp_code = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				careerInfo = new CareerInfoTO();
				careerInfo.setEmpCode(rs.getString("emp_code"));
				careerInfo.setCareerCode(rs.getString("career_code"));
				careerInfo.setCompanyName(rs.getString("company_name"));
				careerInfo.setOccupation(rs.getString("occupation"));
				careerInfo.setAssignmentTask(rs.getString("assignment_task"));
				careerInfo.setExHiredate(rs.getString("ex_hiredate"));
				careerInfo.setExRetirementDate(rs.getString("ex_retirement_date"));
				careerlist.add(careerInfo);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectCareerList 종료 ");
			}
			
			return careerlist;
			
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertCareerInfo(CareerInfoTO careerInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertCareerInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO CAREER_INFO VALUES (?, CAREER_CODE_SEQ.NEXTVAL, ?, ?, ?, to_date(?,'YYYY/MM/DD'), to_date(?,'YYYY/MM/DD'))");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getEmpCode());
			pstmt.setString(2, careerInfo.getCompanyName());
			pstmt.setString(3, careerInfo.getOccupation());
			pstmt.setString(4, careerInfo.getAssignmentTask());
			pstmt.setString(5, careerInfo.getExHiredate());
			pstmt.setString(6, careerInfo.getExRetirementDate());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" insertCareerInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void updateCareerInfo(CareerInfoTO careerInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateCareerInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			
			query.append("UPDATE CAREER_INFO SET ");
			query.append("COMPANY_NAME = ?, OCCUPATION = ?, ASSIGNMENT_TASK = ?, ");
			query.append("EX_HIREDATE = TO_DATE(?,'YYYY/MM/DD'), EX_RETIREMENT_DATE = TO_DATE(?,'YYYY/MM/DD') ");
			query.append("WHERE EMP_CODE = ? and CAREER_CODE = ? ");
			
			
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getCompanyName());
			pstmt.setString(2, careerInfo.getOccupation());
			pstmt.setString(3, careerInfo.getAssignmentTask());
			pstmt.setString(4, careerInfo.getExHiredate());
			pstmt.setString(5, careerInfo.getExRetirementDate());
			pstmt.setString(6, careerInfo.getEmpCode());
			pstmt.setString(7, careerInfo.getCareerCode());
			
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateCareerInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void deleteCareerInfo(CareerInfoTO careerInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteCareerInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM CAREER_INFO WHERE EMP_CODE = ? AND CAREER_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, careerInfo.getEmpCode());
			pstmt.setString(2, careerInfo.getCareerCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteCareerInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}

	
}
