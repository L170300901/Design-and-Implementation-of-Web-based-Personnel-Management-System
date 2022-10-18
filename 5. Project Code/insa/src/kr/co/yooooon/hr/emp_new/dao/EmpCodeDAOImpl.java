package kr.co.yooooon.hr.emp_new.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp_new.to.*;

public class  EmpCodeDAOImpl implements EmpCodeDAO {
	// 로그, 트렌젝션
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	// 싱글톤 패턴 만들기
	private static EmpCodeDAOImpl instance = new EmpCodeDAOImpl();

	private EmpCodeDAOImpl() {
	}

	public static EmpCodeDAO getInstance() {
		if (instance == null)
			instance = new EmpCodeDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<BASIC_DETAIL_CODETO> lastSchoolCodeList(String code){
		if (logger.isDebugEnabled()) {
			logger.debug(" lastSchoolCodeList 시작");
		}
		ArrayList<BASIC_DETAIL_CODETO> list = new ArrayList<BASIC_DETAIL_CODETO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//System.out.println("@@@@@@@@test 완료 "+code);
			StringBuffer query = new StringBuffer();
			BASIC_DETAIL_CODETO codeList = null;

			query.append("SELECT *FROM BASIC_DETAIL_CODE WHERE CODE_NUMBER=?");

			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				//System.out.println("@@");
				codeList = new BASIC_DETAIL_CODETO();
				codeList.setDetailCodeNumber(rs.getString("DETAIL_CODE_NUMBER"));
				codeList.setCodeNumber(rs.getString("CODE_NUMBER"));
				codeList.setDetailCodeName(rs.getString("DETAIL_CODE_NAME"));
				codeList.setDetailCodeNameusing(rs.getString("DETAIL_CODE_NAMEUSING"));
				list.add(codeList);
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" lastSchoolCodeList 종료 ");
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