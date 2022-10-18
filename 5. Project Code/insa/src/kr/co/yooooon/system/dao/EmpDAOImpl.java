package kr.co.yooooon.system.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.to.*;


public class EmpDAOImpl implements EmpDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static EmpDAO instance;

	private EmpDAOImpl() {
	}

	public static EmpDAO getInstance() {
		if (instance == null) {
			
			instance = new EmpDAOImpl();
			
		}
		return instance;
	}
	@Override
	public ArrayList<Emp_newTO> empList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" empList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("  SELECT e1.*, E2.EMP_PW,e3.ENGLISH_NAME FROM EMP_NEW e1, EMP_PW e2, EMP_PERSONAL_INFORMATION e3 WHERE e1.emp_code=e2.EMP_CODE AND e3.emp_code=e1.EMP_CODE");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<Emp_newTO> empList = new ArrayList<Emp_newTO>();
			Emp_newTO code = null;
			while (rs.next()) {
				code = new Emp_newTO();
				code.setEmpCode(rs.getString("EMP_CODE"));
				code.setEmpName(rs.getString("EMP_NAME"));
				code.setEmpDept(rs.getString("EMP_DEPT"));
				code.setEmpStatus(rs.getString("EMP_STATUS"));
				code.setCompanyCode(rs.getString("COMPANY_CODE"));
				code.setDeptCode(rs.getString("DEPT_CODE"));
				code.setEmpJoin(rs.getString("EMP_JOIN"));
				System.out.println("@@@");
				code.setEmpOut(rs.getString("EMP_OUT"));
				System.out.println("@@@");
				code.setEmpPw(rs.getString("EMP_PW"));
				System.out.println("@@@");
				code.setEngLishName(rs.getString("ENGLISH_NAME"));
				System.out.println("@@@");
				
				empList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" empList 종료 ");
			}
			return empList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}
