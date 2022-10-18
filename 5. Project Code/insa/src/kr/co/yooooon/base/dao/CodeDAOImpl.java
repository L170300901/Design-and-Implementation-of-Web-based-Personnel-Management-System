package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class CodeDAOImpl implements CodeDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static CodeDAO instance;

	private CodeDAOImpl() {
	}

	public static CodeDAO getInstance() {
		if (instance == null)
			instance = new CodeDAOImpl();
		return instance;
	}

	public ArrayList<CodeTO> selectCode() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectCode 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM BASIC_CODE ORDER BY CODE_NUMBER");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<CodeTO> codeList = new ArrayList<CodeTO>();
			CodeTO code = null;
			while (rs.next()) {
				code = new CodeTO();
				code.setCodeName(rs.getString("CODE_NAME"));
				code.setCodeNumber(rs.getString("CODE_NUMBER"));
				code.setModifiable(rs.getString("MODIFIABLE"));
				codeList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" selectCode 종료 ");
			}
			return codeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateCodeList(DetailCodeTO Code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE DETAIL_CODE SET ");
			query.append("DETAIL_CODE_NUMBER = ?, CODE_NUMBER = ? ");
			query.append("DETAIL_CODE_NAME = ?,DETAIL_CODE_NAMEUSING=? ");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, Code.getDetailCodeNumber());
			pstmt.setString(2, Code.getCodeNumber());
			pstmt.setString(3, Code.getDetailCodeName());
			pstmt.setString(4, Code.getDetailCodeNameusing());
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
	public void insertCodeList(DetailCodeTO Code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" insert into basic_DETAIL_CODE values(?,?,?,?)");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, Code.getDetailCodeNumber());
			pstmt.setString(2, Code.getCodeNumber());
			pstmt.setString(3, Code.getDetailCodeName());
			pstmt.setString(4, Code.getDetailCodeNameusing());
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
	public void deleteCodeList(DetailCodeTO Code) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("여기는 delete 입니다" + Code.getDetailCodeNumber());
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(
					"delete basic_DETAIL_CODE where DETAIL_CODE_NUMBER = ? and code_Number=? and detail_Code_Name=? and detail_Code_Nameusing=?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, Code.getDetailCodeNumber());
			pstmt.setString(2, Code.getCodeNumber());
			pstmt.setString(3, Code.getDetailCodeName());
			pstmt.setString(4, Code.getDetailCodeNameusing());
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

	@Override
	public void saveCodeList(List<DetailCodeTO> saveCodeList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" saveCodeList 시작 ");
		}

		Connection con = null;
		CallableStatement cstmt = null;

		try {			
			for (DetailCodeTO Code : saveCodeList) {
				con = dataSourceTransactionManager.getConnection();
				StringBuffer query = new StringBuffer();
				query.append("{call P_SAVE_CODE(?,?,?,?,?)}");
				cstmt = con.prepareCall(query.toString());
				cstmt.setString(1, Code.getDetailCodeNumber());
				cstmt.setString(2, Code.getCodeNumber());
				cstmt.setString(3, Code.getDetailCodeName());
				cstmt.setString(4, Code.getDetailCodeNameusing());
				cstmt.setString(5, Code.getStatus().toUpperCase());

				cstmt.executeUpdate();	

			}

			if (logger.isDebugEnabled()) {
				logger.debug(" saveCodeList 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
	         dataSourceTransactionManager.close(cstmt);
	      }

	}
}
