package kr.co.yooooon.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.yooooon.common.exception.DataAccessException;
import kr.co.yooooon.common.transaction.DataSourceTransactionManager;
import kr.co.yooooon.hr.salary.dao.MonthDeductionDAO;
import kr.co.yooooon.hr.salary.dao.MonthDeductionDAOImpl;
import kr.co.yooooon.hr.salary.to.MonthDeductionTO;

public class MonthDeductionDAOImpl implements MonthDeductionDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static MonthDeductionDAO instance;
	private MonthDeductionDAOImpl(){}
	public static MonthDeductionDAO getInstance(){
		if(instance==null) instance=new MonthDeductionDAOImpl();
		return instance;
	}
	@Override
	public ArrayList<MonthDeductionTO> selectMonthDeductionList(String applyYearMonth, String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectMonthDeductionList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MonthDeductionTO> monthDeductionList=new ArrayList<MonthDeductionTO>();
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * ");
			query.append("from month_deduction ");
			query.append("where apply_year_month = ? and emp_code = ?  ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, applyYearMonth);
			pstmt.setString(2, empCode);
			rs = pstmt.executeQuery();

			MonthDeductionTO monthDeduction = null;
			while (rs.next()) {
				monthDeduction = new MonthDeductionTO();
				monthDeduction.setEmpCode(rs.getString("EMP_CODE"));
				monthDeduction.setApplyYearMonth(rs.getString("apply_year_month"));
				monthDeduction.setDeductionCode(rs.getString("deduction_code"));
				monthDeduction.setDeductionName(rs.getString("deduction_name"));
				monthDeduction.setPrice(rs.getString("price"));
				monthDeductionList.add(monthDeduction);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectMonthDeductionList 종료 ");
			}
			return monthDeductionList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
}
