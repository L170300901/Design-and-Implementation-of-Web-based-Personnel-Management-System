package kr.co.yooooon.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.salary.dao.BaseDeductionDAO;
import kr.co.yooooon.hr.salary.dao.BaseDeductionDAOImpl;
import kr.co.yooooon.hr.salary.to.BaseDeductionTO;

public class BaseDeductionDAOImpl implements BaseDeductionDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static BaseDeductionDAO instance;
	private BaseDeductionDAOImpl(){}
	public static BaseDeductionDAO getInstance(){
		if(instance==null) instance=new BaseDeductionDAOImpl();
		return instance;
	}
	@Override
	public ArrayList<BaseDeductionTO> selectBaseDeductionList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectBaseDeductionList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from base_deduction");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseDeductionTO> baseDeductionList=new ArrayList<BaseDeductionTO>();
			BaseDeductionTO baseDeduction = null;
			while (rs.next()) {
				baseDeduction = new BaseDeductionTO();
				baseDeduction.setDeductionCode(rs.getString("deduction_code"));
				baseDeduction.setDeductionName(rs.getString("deduction_name"));
				baseDeduction.setRatio(rs.getString("ratio"));
				baseDeductionList.add(baseDeduction);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" selectBaseDeductionList 종료 ");
			}
			return baseDeductionList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateBaseDeduction(BaseDeductionTO baseDeduction) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateBaseDeduction 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("update base_deduction set ");
			query.append("deduction_name = ?, ratio = ? ");
			query.append("where deduction_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionName());
			pstmt.setString(2, baseDeduction.getRatio());
			pstmt.setString(3, baseDeduction.getDeductionCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateBaseDeduction 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public void insertBaseDeduction(BaseDeductionTO baseDeduction) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertBaseDeduction 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("insert into base_deduction values (?, ?, ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionCode());
			pstmt.setString(2, baseDeduction.getDeductionName());
			pstmt.setString(3, baseDeduction.getRatio());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" insertBaseDeduction 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	
	@Override
	public void deleteBaseDeduction(BaseDeductionTO baseDeduction) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteBaseDeduction 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("delete from base_deduction where deduction_code = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseDeduction.getDeductionCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteBaseDeduction 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}


}
