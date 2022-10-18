package kr.co.yooooon.system.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.system.to.*;

public class BranchDAOImpl implements BranchDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static BranchDAO instance;

	private BranchDAOImpl() {
	}

	public static BranchDAO getInstance() {
		if (instance == null) {
			
			instance = new BranchDAOImpl();
			
		}
		return instance;
	}
	@Override
	public ArrayList<BranchTO> branchList() {
		if (logger.isDebugEnabled()) {
			logger.debug("branchList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM company_branch");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BranchTO> branchList = new ArrayList<BranchTO>();
			BranchTO code = null;
			while (rs.next()) {
				code = new BranchTO();
				code.setCompanyCode(rs.getString("COMPANY_CODE"));
				code.setBranchName(rs.getString("BRANCH_NAME"));
				code.setBranchCode(rs.getString("BRANCH_CODE"));
				branchList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" branchList 종료 ");
			}
			return branchList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<BranchBasicRegistTO> branchBasicRegist(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" branchBasicRegist 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			System.out.println(code);
			StringBuffer query = new StringBuffer();
			query.append("SELECT *FROM BRANCH_BASIC WHERE branch_code=?");

			pstmt = con.prepareStatement(query.toString());
			
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			ArrayList<BranchBasicRegistTO> branchBasic = new ArrayList<BranchBasicRegistTO>();
			BranchBasicRegistTO code1  = new BranchBasicRegistTO();
			while (rs.next()) {
				code1.setBranchLicense(rs.getString("BRANCH_LICENSE"));
				code1.setTaxCode(rs.getString("TAX_CODE"));
				code1.setRepregentName(rs.getString("REPREGENT_NAME"));
				code1.setBranchCode(rs.getString("BRANCH_CODE"));	
				code1.setBranchAddress(rs.getString("BRANCH_ADDRESS"));
				code1.setBranchPost(rs.getString("BRANCH_POST"));
				code1.setPostNumber(rs.getString("POST_NUMBER"));
				code1.setTaxNumber(rs.getString("TAX_NUMBER"));
				code1.setBranchTel(rs.getString("BRANCH_TEL"));
				code1.setBranchFax(rs.getString("BRANCH_FAX"));
						
				code1.setBusinessCondition(rs.getString("BUSINESS_CONDITION"));
				code1.setBranchEvent(rs.getString("BRANCH_EVENT"));
				
				code1.setCompetentTax(rs.getString("COMPETENT_TAX"));
				code1.setOpenDate(rs.getString("OPEN_DATE"));
				code1.setCloseDate(rs.getString("CLOSE_DATE"));
				code1.setIsHead(rs.getString("IS_HEAD"));
				branchBasic.add(code1);
				
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" branchBasicRegist 종료 ");
			}
			return branchBasic;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}