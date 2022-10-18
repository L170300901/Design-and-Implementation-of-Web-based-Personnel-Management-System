package kr.co.yooooon.hr.emp_new.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.to.*;

public class EmpDAOImpl implements EmpDAO {
	// 로그, 트렌젝션
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	// 싱글톤 패턴 만들기
	private static EmpDAOImpl instance = new EmpDAOImpl();

	private EmpDAOImpl() {
	}

	public static EmpDAO getInstance() {
		if (instance == null)
			instance = new EmpDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<Emp_newTO> selectEmpList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectEmpList 시작");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// System.out.println("23434554s");
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("SELECT *from EMP_NEW");

			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			rs = pstmt.executeQuery();
			ArrayList<Emp_newTO> list = new ArrayList<Emp_newTO>();
			while (rs.next()) {
				Emp_newTO emp = new Emp_newTO();
				emp.setEmpName(rs.getString("EMP_NAME"));
				emp.setEmpCode(rs.getString("EMP_CODE"));
				emp.setEmpDept(rs.getString("EMP_DEPT"));
				emp.setEmpStatus(rs.getString("EMP_STATUS"));
				list.add(emp);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmpList 종료 ");
			}
			return list;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<EmpInforTO> empPersonal(String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" empPersonal 시작");
		}
		ArrayList<EmpInforTO> list = new ArrayList<EmpInforTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//System.out.println("@@@@@@@@test 완료 "+empCode);
			StringBuffer query = new StringBuffer();
			EmpInforTO emp = null;

			query.append("SELECT *from EMP_PERSONAL_INFORMATION where EMP_CODE =?");

			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				emp = new EmpInforTO();
				emp.setEmpCode(rs.getString("EMP_CODE"));
				emp.setEmpName(rs.getString("EMP_NAME"));
				emp.setEnglishName(rs.getString("ENGLISH_NAME"));
				emp.setEmpForeign(rs.getString("EMP_FOREIGN"));
				emp.setResidentNumber(rs.getString("RESIDENT_NUMBER"));
				emp.setFfresidentNumber(rs.getString("FFRESIDENT_NUMBER"));
				emp.setEmpGender(rs.getString("EMP_GENDER"));
				emp.setEmpBirthdate(rs.getString("EMP_BIRTHDATE"));
				emp.setMobileNumber(rs.getString("MOBILE_NUMBER"));
				emp.setEmpHp(rs.getString("EMP_HP"));
				emp.setLastSchool(rs.getString("LAST_SCHOOL"));
				list.add(emp);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" empPersonal 종료 ");
			}
			return list;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public ArrayList<EmpResidentTO> findEmpResident(String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findEmpResident 시작");
		}
		ArrayList<EmpResidentTO> list = new ArrayList<EmpResidentTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//System.out.println("@@@@@@@@222번 test 완료 "+empCode);
			StringBuffer query = new StringBuffer();
			EmpResidentTO emp = null;

			query.append("SELECT *from EMP_residence_INFORMATION where EMP_CODE =?");

			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				emp = new EmpResidentTO();
				emp.setEmpCode(rs.getString("EMP_CODE"));
				emp.setEmpAddress(rs.getString("EMP_ADDRESS"));
				emp.setDetailAddress(rs.getString("DETAIL_ADDRESS"));
				emp.setEnglishAddress(rs.getString("ENGLISH_ADDRESS"));
				emp.setEmpEmail(rs.getString("EMP_EMAIL"));
				emp.setEmpCardno(rs.getString("EMP_CARDNO"));
				emp.setHouseOwner(rs.getString("HOUSE_OWNER"));
				emp.setDisabledPerson(rs.getString("DISABLED_PERSON"));
				emp.setNationalityA(rs.getString("NATIONALITY_A"));
				emp.setReligiousStatus(rs.getString("RELIGIOUS_STATUS"));
				emp.setNationalityB1(rs.getString("NATIONALITY_B1"));
				emp.setNationalityB2(rs.getString("NATIONALITY_B2"));
				emp.setResidentIdentification(rs.getString("RESIDENT_IDENTIFICATION"));
				emp.setNationalityCodeA(rs.getString("NATIONALITY_CODEA"));
				emp.setNationalityCodeB(rs.getString("NATIONALITY_CODEB"));
				list.add(emp);
				
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" findEmpResident 종료 ");
			}
			return list;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}
