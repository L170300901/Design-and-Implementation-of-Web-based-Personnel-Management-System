package kr.co.yooooon.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.salary.dao.BaseExtSalDAO;
import kr.co.yooooon.hr.salary.dao.BaseExtSalDAOImpl;
import kr.co.yooooon.hr.salary.to.BaseExtSalTO;

public class BaseExtSalDAOImpl implements BaseExtSalDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static BaseExtSalDAO instance;
	private BaseExtSalDAOImpl(){}
	public static BaseExtSalDAO getInstance(){
		if(instance==null) instance=new BaseExtSalDAOImpl();
		return instance;
	}
	@Override
	public ArrayList<BaseExtSalTO> selectBaseExtSalList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectBaseExtSalList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from base_ext_sal ");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseExtSalTO> baseExtSalList=new ArrayList<BaseExtSalTO>();
			BaseExtSalTO baseExtSal = null;
			while (rs.next()) {
				baseExtSal = new BaseExtSalTO();
				baseExtSal.setExtSalCode(rs.getString("ext_sal_code"));
				baseExtSal.setExtSalName(rs.getString("ext_sal_name"));
				baseExtSal.setRatio(rs.getString("ratio"));
				baseExtSalList.add(baseExtSal);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" selectBaseExtSalList 종료 ");
			}
			return baseExtSalList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public void updateBaseExtSal(BaseExtSalTO baseExtSal) {
		if (logger.isDebugEnabled()) {
			logger.debug(" updateBaseExtSal 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("update base_ext_sal set ");
			query.append("ratio = ? ");
			query.append("where ext_sal_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseExtSal.getRatio());
			pstmt.setString(2, baseExtSal.getExtSalCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateBaseExtSal 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
}
