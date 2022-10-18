package kr.co.yooooon.hr.salary.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.salary.to.*;

public class SocialInsureDAOImpl implements SocialInsureDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static SocialInsureDAO instance;
	private SocialInsureDAOImpl(){}
	public static SocialInsureDAO getInstance(){
		if(instance==null) instance=new SocialInsureDAOImpl();
		return instance;
	}
	
	@Override
	public ArrayList<SocialInsureTO> selectBaseInsureList(String yearBox) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectBaseInsureList 시작 ");
		}

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<SocialInsureTO> BaseInsureList=new ArrayList<SocialInsureTO>();
		try {
			con = dataSourceTransactionManager.getConnection();
			
	        String sql="{call P_SELECT_INSURE(?,?)}";
	        cstmt=con.prepareCall(sql);
			cstmt.setString(1, yearBox);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			
			SocialInsureTO baseInsure = null;
			rs=(ResultSet)cstmt.getObject(2);
			if (rs.next()) {
				baseInsure = new SocialInsureTO();
				baseInsure.setAttributionYear(rs.getString("ATTRIBUTION_YEAR"));
				baseInsure.setHealthinsureRates(rs.getString("HEALTH_INSURE_RATES"));
				baseInsure.setLongtermcareRate(rs.getString("LONG_TERM_CARE_RATE"));
				baseInsure.setNationpenisionRates(rs.getString("NATION_PENISION_RATES"));
				baseInsure.setTeachpenisionRates(rs.getString("TEACH_PENISION_RATES")); 
				baseInsure.setEmpinsureRates(rs.getString("EMP_INSURE_RATES")); 
				baseInsure.setWrkinsureRates(rs.getString("WRK_INSURE_RATES"));    
				baseInsure.setJobstabilRates(rs.getString("JOBSTABIL_RATES"));  
				baseInsure.setVocacompetencyRates(rs.getString("VOCA_COMPETENCY_RATES"));  
				baseInsure.setIndustinsureRates(rs.getString("INDUST_INSURE_RATES"));  
				baseInsure.setIndustinsurecharRates(rs.getString("INDUST_INSURE_CHAR_RATES")); 
			}
			BaseInsureList.add(baseInsure);
			if (logger.isDebugEnabled()) {
				logger.debug(" selectBaseInsureList 종료 ");
			}
			return BaseInsureList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt, rs);
		}
	}
	
	@Override
	public void updateInsureData(SocialInsureTO baseInsure) {
		if (logger.isDebugEnabled()) {
			logger.debug(" updateInsureData 시작 ");
		}

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("{call P_INSURE_UPDATE(?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt = con.prepareCall(query.toString());
			cstmt.setString(1, baseInsure.getAttributionYear());
			cstmt.setString(2, baseInsure.getHealthinsureRates());
			cstmt.setString(3, baseInsure.getLongtermcareRate());
			cstmt.setString(4, baseInsure.getNationpenisionRates());
			cstmt.setString(5, baseInsure.getTeachpenisionRates());
			cstmt.setString(6, baseInsure.getEmpinsureRates());
			cstmt.setString(7, baseInsure.getWrkinsureRates());
			cstmt.setString(8, baseInsure.getJobstabilRates());
			cstmt.setString(9, baseInsure.getVocacompetencyRates());
			cstmt.setString(10, baseInsure.getIndustinsureRates());
			cstmt.setString(11, baseInsure.getIndustinsurecharRates());
			cstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateInsureData 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt);
		}	
	}
	
	@Override
	public void insertInsureData(SocialInsureTO baseInsure) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertInsureData 시작 ");
		}

		Connection con = null;
		CallableStatement cstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("{call P_INSURE_INSERT(?,?,?,?,?,?,?,?,?,?,?)}");

			cstmt = con.prepareCall(query.toString());
			cstmt.setString(1, baseInsure.getAttributionYear());
			cstmt.setString(2, baseInsure.getHealthinsureRates());
			cstmt.setString(3, baseInsure.getLongtermcareRate());
			cstmt.setString(4, baseInsure.getNationpenisionRates());
			cstmt.setString(5, baseInsure.getTeachpenisionRates());
			cstmt.setString(6, baseInsure.getEmpinsureRates());
			cstmt.setString(7, baseInsure.getWrkinsureRates());
			cstmt.setString(8, baseInsure.getJobstabilRates());
			cstmt.setString(9, baseInsure.getVocacompetencyRates());
			cstmt.setString(10, baseInsure.getIndustinsureRates());
			cstmt.setString(11, baseInsure.getIndustinsurecharRates());
			cstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" insertInsureData 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(cstmt);
		}
	}
}