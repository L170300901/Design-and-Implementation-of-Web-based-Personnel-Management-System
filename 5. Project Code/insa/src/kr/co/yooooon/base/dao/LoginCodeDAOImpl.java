package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class LoginCodeDAOImpl implements LoginCodeDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static LoginCodeDAO instance;

	private LoginCodeDAOImpl() {
	}

	public static LoginCodeDAO getInstance() {
		if (instance == null)
			instance = new LoginCodeDAOImpl();
		return instance;
	}

	public ArrayList<CompanyTO> searchCompanyCode() {
		if (logger.isDebugEnabled()) {
			logger.debug(" searchCompanyCode 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM COMPANY ORDER BY COMPANY_CODE");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<CompanyTO> codeList = new ArrayList<CompanyTO>();
			CompanyTO code = null;
			while (rs.next()) {
				code = new CompanyTO();
				code.setCompanyCode(rs.getString("COMPANY_CODE"));
				code.setCompanyName(rs.getString("COMPANY_NAME"));
				code.setCompanySort(rs.getString("COMPANY_SORT"));
				codeList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" searchCompanyCode 종료 ");
			}
			return codeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	public ArrayList<LoginEmpTo> searchEmpCode(String companyCode){
		if (logger.isDebugEnabled()) {
			logger.debug(" searchEmpCode 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT EMP_CODE, EMP_NAME FROM EMP_NEW WHERE EMP_STATUS='Y'AND COMPANY_CODE=?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, companyCode);
			rs = pstmt.executeQuery();
			
			ArrayList<LoginEmpTo> codeList = new ArrayList<LoginEmpTo>();
			LoginEmpTo code = null;
			
			
			while (rs.next()) {
				code = new LoginEmpTo();
				code.setEmpCode(rs.getString("EMP_CODE"));
				code.setEmpName(rs.getString("EMP_NAME"));
				codeList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" searchEmpCode 종료 ");
			}
			return codeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}
