package kr.co.yooooon.system.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.system.to.*;

public class DeptDAOImpl implements DeptDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static DeptDAO instance;

	private DeptDAOImpl() {
	}

	public static DeptDAO getInstance() {
		if (instance == null) {
			
			instance = new DeptDAOImpl();
			
		}
		return instance;
	}
	@Override
	public ArrayList<Dept_newTO> deptList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" deptList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT e1.*,e2.BRANCH_NAME FROM DEPt_new e1,COMPANY_branch e2 WHERE e1.BRANCH_CODE=e2.BRANCH_CODE");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<Dept_newTO> deptList = new ArrayList<Dept_newTO>();
			Dept_newTO code = null;
			while (rs.next()) {
				code = new Dept_newTO();
				code.setDeptCode(rs.getString("DEPT_CODE"));
				code.setDeptName(rs.getString("DEPT_NAME"));
				code.setBranchCode(rs.getString("BRANCH_CODE"));
				code.setBranchName(rs.getString("BRANCH_name"));
				code.setBumunCode(rs.getString("BUMUN_CODE"));
				code.setBumunName(rs.getString("BUMUN_NAME"));
				code.setDeptPeriodA(rs.getString("DEPT_PERIODA"));
				code.setDeptPeriodB(rs.getString("DEPT_PERIODB"));
				deptList.add(code);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" deptList 종료 ");
			}
			return deptList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}
