package kr.co.yooooon.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.salary.dao.BaseSalaryDAO;
import kr.co.yooooon.hr.salary.dao.BaseSalaryDAOImpl;
import kr.co.yooooon.hr.salary.to.BaseSalaryTO;

public class BaseSalaryDAOImpl implements BaseSalaryDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static BaseSalaryDAO instance;
	private BaseSalaryDAOImpl(){}
	public static BaseSalaryDAO getInstance(){
		if(instance==null) instance=new BaseSalaryDAOImpl();
		return instance;
	}
	
	@Override
	public ArrayList<BaseSalaryTO> selectBaseSalaryList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectBaseSalaryList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from position order by position_code");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseSalaryTO> baseSalaryList=new ArrayList<BaseSalaryTO>();
			BaseSalaryTO baseSalary = null;
			while (rs.next()) {
				baseSalary = new BaseSalaryTO();
				baseSalary.setPositionCode(rs.getString("position_code"));
				baseSalary.setPosition(rs.getString("position"));
				baseSalary.setBaseSalary(rs.getString("base_salary"));
				baseSalary.setHobongRatio(rs.getString("hobong_ratio"));
				baseSalaryList.add(baseSalary);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" selectBaseSalaryList 종료 ");
			}
			return baseSalaryList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateBaseSalary(BaseSalaryTO baseSalary) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateBaseSalary 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE POSITION SET ");
			query.append("BASE_SALARY = ?, HOBONG_RATIO = ? ");
			query.append("where POSITION_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseSalary.getBaseSalary());
			pstmt.setString(2, baseSalary.getHobongRatio());
			pstmt.setString(3, baseSalary.getPositionCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateBaseSalary 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public void updatePosition(BaseSalaryTO position) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updatePosition시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(" UPDATE  POSITION SET ");
			query.append(" POSITION = ?, BASE_SALARY = ?, HOBONG_RATIO = ? ");
			query.append(" WHERE POSITION_CODE = ? ");

			pstmt = con.prepareStatement(query.toString());
			
			
			
			pstmt.setString(1, position.getPosition());
			pstmt.setString(2, position.getBaseSalary());
			pstmt.setString(3, position.getHobongRatio());
			pstmt.setString(4, position.getPositionCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updatePosition 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}			
	}
	
	@Override
	public void insertPosition(BaseSalaryTO position) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertPosition 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append( " INSERT INTO POSITION VALUES ( ?, ?, ?, ? ) " );

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, position.getPositionCode());
			pstmt.setString(2, position.getPosition());
			pstmt.setString(3, position.getBaseSalary());
			pstmt.setString(4, position.getHobongRatio());
			
			pstmt.executeUpdate();
		
			if (logger.isDebugEnabled()) {
				logger.debug(" insertPosition 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}			
	}
	
	@Override
	public void deletePosition(BaseSalaryTO position) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deletePosition시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(" DELETE  POSITION ");
			query.append(" WHERE POSITION_CODE = ? AND POSITION = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, position.getPositionCode());
			pstmt.setString(2, position.getPosition());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deletePosition 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}			
	}
	
}
