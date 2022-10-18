package kr.co.yooooon.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.emp.to.LicenseInfoTO;

public class LicenseInfoDAOImpl implements LicenseInfoDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	private static LicenseInfoDAO instance;
	
	public static LicenseInfoDAO getInstance(){
		if(instance==null) instance=new LicenseInfoDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<LicenseInfoTO> selectLicenseList(String code) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectLicenseList 시작 ");
		}
		

		
		ArrayList<LicenseInfoTO> licenselist=new ArrayList<LicenseInfoTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			LicenseInfoTO licenseInfo = null;
	        query.append("SELECT LICENSE_CODE,LICENSE_NAME,LICENSE_LEVEL,LICENSE_CENTER,ISSUE_NUMBER,EMP_CODE, "); 
	        query.append("TO_CHAR(get_date, 'YYYY/MM/DD') GET_DATE, "); 
	        query.append("TO_CHAR(expire_date, 'YYYY/MM/DD') EXPIRE_DATE "); 
	        query.append("FROM LICENSE_INFO WHERE EMP_CODE = ? "); 
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				licenseInfo = new LicenseInfoTO();
				licenseInfo.setEmpCode(rs.getString("EMP_CODE"));
				licenseInfo.setLicenseCode(rs.getString("LICENSE_CODE"));
				licenseInfo.setLicenseName(rs.getString("LICENSE_NAME"));
				licenseInfo.setGetDate(rs.getString("GET_DATE"));
				licenseInfo.setExpireDate(rs.getString("EXPIRE_DATE"));
				licenseInfo.setLicenseLevel(rs.getString("LICENSE_LEVEL"));
				licenseInfo.setLicenseCenter(rs.getString("LICENSE_CENTER"));
				licenseInfo.setIssueNumber(rs.getString("ISSUE_NUMBER"));
				licenselist.add(licenseInfo);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectLicenseList 종료 ");
			}
			
			return licenselist;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public void insertLicenseInfo(LicenseInfoTO licenscInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertLicenseInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO LICENSE_INFO VALUES (?, LICENSE_CODE_SEQ.NEXTVAL, ?, TO_DATE(?, 'YYYY/MM/DD'), TO_DATE(?, 'YYYY/MM/DD'), ?, ?, ?)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, licenscInfo.getEmpCode());
			pstmt.setString(2, licenscInfo.getLicenseName());
			pstmt.setString(3, licenscInfo.getGetDate());
			pstmt.setString(4, licenscInfo.getExpireDate());
			pstmt.setString(5, licenscInfo.getLicenseLevel());
			pstmt.setString(6, licenscInfo.getLicenseCenter());
			pstmt.setString(7, licenscInfo.getIssueNumber());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" insertLicenseInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}

	@Override
	public void updateLicenseInfo(LicenseInfoTO licenscInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateLicenseInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			
			query.append("UPDATE LICENSE_INFO SET ");
			query.append("LICENSE_NAME= ? , GET_DATE = TO_DATE(?, 'YYYY/MM/DD'), EXPIRE_DATE = TO_DATE(?, 'YYYY/MM/DD'), ");
			query.append("LICENSE_LEVEL = ?, LICENSE_CENTER = ?, ISSUE_NUMBER = ?");
			query.append("WHERE EMP_CODE = ? and LICENSE_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, licenscInfo.getLicenseName());
			pstmt.setString(2, licenscInfo.getGetDate());
			pstmt.setString(3, licenscInfo.getExpireDate());
			pstmt.setString(4, licenscInfo.getLicenseLevel());
			pstmt.setString(5, licenscInfo.getLicenseCenter());
			pstmt.setString(6, licenscInfo.getIssueNumber());
			pstmt.setString(7, licenscInfo.getEmpCode());
			pstmt.setString(8, licenscInfo.getLicenseCode());
			
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateLicenseInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}

	@Override
	public void deleteLicenseInfo(LicenseInfoTO licenscInfo) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteLicenseInfo 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM LICENSE_INFO WHERE EMP_CODE = ? and LICENSE_CODE = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, licenscInfo.getEmpCode());
			pstmt.setString(2, licenscInfo.getLicenseCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteLicenseInfo 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}


}
