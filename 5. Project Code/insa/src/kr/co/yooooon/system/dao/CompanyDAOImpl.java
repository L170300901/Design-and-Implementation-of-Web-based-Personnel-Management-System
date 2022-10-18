package kr.co.yooooon.system.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.system.to.*;

public class CompanyDAOImpl implements CompanyDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static CompanyDAO instance;

	private CompanyDAOImpl() {
	}

	public static CompanyDAO getInstance() {
		if (instance == null) {
			
			instance = new CompanyDAOImpl();
			
		}
		return instance;
	}
	@Override
	public ArrayList<CompanyTO> companyList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" companyList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM company");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<CompanyTO> companyList = new ArrayList<CompanyTO>();
			CompanyTO code = null;
			while (rs.next()) {
				code = new CompanyTO();
				code.setCompanyCode(rs.getString("COMPANY_CODE"));
				code.setCompanyName(rs.getString("COMPANY_NAME"));
				code.setCompanySort(rs.getString("COMPANY_SORT"));
				companyList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" companyList 종료 ");
			}
			return companyList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<CompanyBasicRegistTO> companyBasicRegist(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" companyBasicRegist 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("SELECT e1.*,e2.EMP_FOREIGN,e2.RESIDENT_NUMBER FROM COMPANY_BASIC e1,EMP_PERSONAL_INFORMATION e2 WHERE e1.REPRESENTATIVE_NAME=e2.EMP_NAME and COMPANY_CODE=?");

			pstmt = con.prepareStatement(query.toString());
			
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			ArrayList<CompanyBasicRegistTO> companyBasic = new ArrayList<CompanyBasicRegistTO>();
			CompanyBasicRegistTO code1  = new CompanyBasicRegistTO();
			System.out.println("####"+code);
			while (rs.next()) {
				System.out.println("@@@"+(rs.getString("COMPANY_CODE")));
				code1.setCompanyCode(rs.getString("COMPANY_CODE"));
				code1.setOpeningDate(rs.getString("Opening_Date"));
				code1.setBusinessLicense(rs.getString("Business_License"));	
				code1.setCorporateRegistrate(rs.getString("Corporate_Registrate"));
				code1.setRepresentativeName(rs.getString("Representative_Name"));
				
				code1.setEmpForeign(rs.getString("emp_Foreign"));
				code1.setResidentNumber(rs.getString("resident_Number"));
								
				code1.setCompanyAddress(rs.getString("Company_Address"));
				code1.setCompanyPost(rs.getString("Company_Post"));
				code1.setPostNumber(rs.getString("Post_Number"));
				code1.setBusinessCondition(rs.getString("Business_Condition"));
				code1.setCompanyEvent(rs.getString("Company_Event"));
				code1.setCompanyFax(rs.getString("Company_Fax"));
				code1.setCompanyTel(rs.getString("Company_Tel"));
				companyBasic.add(code1);
				
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" companyBasicRegist 종료 ");
			}
			return companyBasic;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}