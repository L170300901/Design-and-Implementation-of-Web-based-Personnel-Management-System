package kr.co.yooooon.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.salary.dao.MonthExtSalDAO;
import kr.co.yooooon.hr.salary.dao.MonthExtSalDAOImpl;
import kr.co.yooooon.hr.salary.to.MonthExtSalTO;

public class MonthExtSalDAOImpl implements MonthExtSalDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static MonthExtSalDAO instance;
	private MonthExtSalDAOImpl(){}
	public static MonthExtSalDAO getInstance(){
		if(instance==null) instance=new MonthExtSalDAOImpl();
		return instance;
	}
	@Override
	public ArrayList<MonthExtSalTO> selectMonthExtSalList(String applyYearMonth, String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectMonthExtSalList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MonthExtSalTO> monthExtSalList=new ArrayList<MonthExtSalTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * ");
			query.append("from month_ext_sal ");
			query.append("where apply_year_month = ? and emp_code = ?  ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, applyYearMonth);
			pstmt.setString(2, empCode);
			rs = pstmt.executeQuery();

			MonthExtSalTO monthExtSal = null;
			while (rs.next()) {
				monthExtSal = new MonthExtSalTO();
				monthExtSal.setEmpCode(rs.getString("EMP_CODE"));
				monthExtSal.setApplyYearMonth(rs.getString("apply_year_month"));
				monthExtSal.setExtSalCode(rs.getString("ext_sal_code"));
				monthExtSal.setExtSalName(rs.getString("ext_sal_name"));
				monthExtSal.setPrice(rs.getString("price"));
				monthExtSalList.add(monthExtSal);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectMonthExtSalList 종료 ");
			}
			return monthExtSalList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}
