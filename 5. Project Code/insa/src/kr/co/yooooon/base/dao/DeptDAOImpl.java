package kr.co.yooooon.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.base.to.DeptTO;
import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;

public class DeptDAOImpl implements DeptDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static DeptDAO instance=new DeptDAOImpl();
	
	private DeptDAOImpl(){}
	public static DeptDAO getInstance(){
		return instance;
	}
	
	@Override
	public ArrayList<DeptTO> selectDeptList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectDeptList 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select * from dept order by dept_code");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			rs=pstmt.executeQuery();
			ArrayList<DeptTO> list=new ArrayList<DeptTO>(); 
			while(rs.next()){
				DeptTO dept=new DeptTO();
				dept.setDeptCode(rs.getString("dept_code"));
				dept.setDeptName(rs.getString("dept_name"));
				dept.setDeptTel(rs.getString("dept_tel"));
				list.add(dept);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectDeptList 종료 ");
			}
			return list;
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	
	@Override
	public void updateDept(DeptTO dept) {
		if (logger.isDebugEnabled()) {
			logger.debug(" registDept 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append("UPDATE dept SET ");
			updateQuery.append("DEPT_NAME = ?, DEPT_TEL = ? ");
			updateQuery.append("WHERE DEPT_CODE = ? ");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(1, dept.getDeptName());
			pstmt.setString(2, dept.getDeptTel());
			pstmt.setString(3, dept.getDeptCode());
			pstmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug(" registDept 종료 ");
			}
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	@Override
	public void registDept(DeptTO dept) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" registDept 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into dept values(?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, dept.getDeptCode());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setString(3, dept.getDeptTel());
			pstmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug(" registDept 종료 ");
			}
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
			
	}
	
	@Override
	public void deleteDept(DeptTO dept) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteDept 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("delete dept where dept_code = ?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, dept.getDeptCode());
			pstmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug(" deleteDept 종료 ");
			}
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

}
