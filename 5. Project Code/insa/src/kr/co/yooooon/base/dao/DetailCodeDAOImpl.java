package kr.co.yooooon.base.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class DetailCodeDAOImpl implements DetailCodeDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static DetailCodeDAO instance;
	private DetailCodeDAOImpl() {}
	public static DetailCodeDAO getInstance() {
		if (instance == null)
			instance = new DetailCodeDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<DetailCodeTO> selectDetailCodeList(String codetype) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectDetailCodeList 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from BASIC_detail_code where code_number = ?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, codetype);
			rs = pstmt.executeQuery();

			ArrayList<DetailCodeTO> detailCodeList=new ArrayList<DetailCodeTO>();
			DetailCodeTO detailCode=null;
			while(rs.next()){
				detailCode=new DetailCodeTO();
				detailCode.setCodeNumber(rs.getString("code_number"));
				detailCode.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCode.setDetailCodeName(rs.getString("detail_code_name"));
				detailCode.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));
				detailCodeList.add(detailCode);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectDetailCodeList 종료 ");
			}
			return detailCodeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<CodeTO> findoutPutList(String outPut){
		
		if (logger.isDebugEnabled()) {
			logger.debug(" findoutPutList 시작 ");
		}
		String s=" ";
		if(outPut.equals("0")) {
			s="select * from BASIC_code";
		}
		if(outPut.equals("1")) {
			s="select * from BASIC_code where (CODE_NUMBER like 'H%' or CODE_NUMBER  LIKE 'R%') ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("2")) {
			s="select * from BASIC_code where CODE_NUMBER like 'T%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("3")) {
			s="select * from BASIC_code where CODE_NUMBER like 'P%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("4")) {
			s="select * from BASIC_code where CODE_NUMBER like 'I%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("5")) {

			s="select * from BASIC_code where CODE_NUMBER like 'G%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("6")) {
			//outPut="H%";
			s="select * from BASIC_code where CODE_NUMBER like 'B%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("7")) {
			//outPut="H%";
			s="select * from BASIC_code where CODE_NUMBER like 'E%' ORDER BY CODE_NUMBER";
			
		}
		if(outPut.equals("8")) {
			//outPut="H%";
			s="select * from BASIC_code where CODE_NUMBER like 'S%' ORDER BY CODE_NUMBER";
			
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(s);
			pstmt = con.prepareStatement(query.toString());
			//pstmt.setString(1, outPut);
			rs = pstmt.executeQuery();

			ArrayList<CodeTO> findoutPutList=new ArrayList<CodeTO>();
			CodeTO code= null;
			while(rs.next()){
				code=new CodeTO();
				code.setCodeName(rs.getString("CODE_NAME"));
				code.setCodeNumber(rs.getString("CODE_NUMBER"));
				code.setModifiable(rs.getString("MODIFIABLE"));
				findoutPutList.add(code);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug(" findoutPutList 종료 ");
			}
			return findoutPutList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	
	@Override
	public void updateDetailCode(DetailCodeTO detailCodeto) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteDetailCode 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("UPDATE DETAIL_CODE SET ");
			query.append("CODE_NUMBER = ?, DETAIL_CODE_NAME = ?, DETAIL_CODE_NAMEUSING = ? ");
			query.append("WHERE DETAIL_CODE_NUMBER = ? ");
		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, detailCodeto.getCodeNumber());
			pstmt.setString(2, detailCodeto.getDetailCodeName());
			pstmt.setString(3, detailCodeto.getDetailCodeNameusing());
			pstmt.setString(4, detailCodeto.getDetailCodeNumber());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteDetailCode 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	
	@Override
	public void registDetailCode(DetailCodeTO detailCodeto) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" registDetailCode 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into detail_code values(?,?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			pstmt.setString(1, detailCodeto.getDetailCodeNumber());
			pstmt.setString(2, detailCodeto.getCodeNumber());
			pstmt.setString(3, detailCodeto.getDetailCodeName());
			pstmt.setString(4, detailCodeto.getDetailCodeNameusing());
			
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" registDetailCode 종료 ");
			}		
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public void deleteDetailCode(DetailCodeTO detailCodeto) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteDetailCode 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM DETAIL_CODE WHERE DETAIL_CODE_NUMBER = ? AND DETAIL_CODE_NAME = ? ");
		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, detailCodeto.getDetailCodeNumber());
			pstmt.setString(2, detailCodeto.getDetailCodeName());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" deleteDetailCode 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	
	@Override
	public ArrayList<DetailCodeTO> selectDetailCodeListRest(String code1, String code2, String code3) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectDetailCodeListRest 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from detail_code where DETAIL_CODE_NUMBER = ? OR DETAIL_CODE_NUMBER = ? OR DETAIL_CODE_NUMBER = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code1);
			pstmt.setString(2, code2);
			pstmt.setString(3, code3);			
			rs = pstmt.executeQuery();

			ArrayList<DetailCodeTO> detailCodeList=new ArrayList<DetailCodeTO>();
			DetailCodeTO detailCode=null;
			while(rs.next()){
				detailCode=new DetailCodeTO();
				detailCode.setCodeNumber(rs.getString("code_number"));
				detailCode.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCode.setDetailCodeName(rs.getString("detail_code_name"));
				detailCode.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));				
				detailCodeList.add(detailCode);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectDetailCodeListRest 종료 ");
			}
			return detailCodeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<DetailCodeTO> basicDetailCodelist(String code)  {
		if (logger.isDebugEnabled()) {
			logger.debug(" basicDetailCodelist 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from BASIC_detail_code where code_number = ?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			ArrayList<DetailCodeTO> detailCodeList=new ArrayList<DetailCodeTO>();
			DetailCodeTO detailCode=null;
			while(rs.next()){
				detailCode=new DetailCodeTO();
				//detailCode.setCodeNumber(rs.getString("code_number"));
				detailCode.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCode.setDetailCodeName(rs.getString("detail_code_name"));
				//detailCode.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));
				detailCodeList.add(detailCode);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" basicDetailCodelist 종료 ");
			}
			return detailCodeList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	
}
