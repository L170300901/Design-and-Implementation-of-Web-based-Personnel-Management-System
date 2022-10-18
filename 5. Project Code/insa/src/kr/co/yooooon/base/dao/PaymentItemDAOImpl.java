package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class PaymentItemDAOImpl implements PaymentItemDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static PaymentItemDAO instance;

	private PaymentItemDAOImpl() {
	}

	public static PaymentItemDAO getInstance() {
		if (instance == null)
			instance = new PaymentItemDAOImpl();
		return instance;
	}
	@Override
	public ArrayList<PaymentItemTO> findPaymentItemList(String salaryClassification, String paymentClassification, String year){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findPaymentItemList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			System.out.println(salaryClassification+paymentClassification+year);
			StringBuffer query = new StringBuffer();
			query.append("SELECT SALARY_CLASSIFICATION,PAYMENT_CLASSIFICATION,YEAR,CODE1,DETAIL_CODE_NAME\r\n" + 
					"FROM (\r\n" + 
					"SELECT *FROM PAYMENTITEM WHERE SALARY_CLASSIFICATION=?AND PAYMENT_CLASSIFICATION=? AND YEAR=?\r\n" + 
					")t1, (SELECT *FROM BASIC_DETAIL_CODE)t2\r\n" + 
					"WHERE t2.DETAIL_CODE_NUMBER=t1.CODE1");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, salaryClassification);
			pstmt.setString(2, paymentClassification);
			pstmt.setString(3, year);
			rs = pstmt.executeQuery();

			ArrayList<PaymentItemTO> findPaymentItemList = new ArrayList<PaymentItemTO>();
			PaymentItemTO PaymentItem = null;
			while (rs.next()) {
				PaymentItem = new PaymentItemTO();
				PaymentItem.setSalaryClassification(rs.getString("SALARY_CLASSIFICATION"));
				PaymentItem.setPaymentClassification(rs.getString("PAYMENT_CLASSIFICATION"));
				PaymentItem.setYear(rs.getString("YEAR"));
				PaymentItem.setCode1(rs.getString("CODE1"));
				PaymentItem.setCode1Name(rs.getString("DETAIL_CODE_NAME"));
				findPaymentItemList.add(PaymentItem);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findPaymentItemList 종료 ");
			}
			return findPaymentItemList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
				
		
	}
}
