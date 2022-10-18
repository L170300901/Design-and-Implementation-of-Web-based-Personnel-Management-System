package kr.co.yooooon.hr.salary.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.salary.to.*;
import oracle.jdbc.*;

public class FullTimeSalaryDAOImpl implements FullTimeSalaryDAO{
	   protected final Log logger = LogFactory.getLog(this.getClass());
	   private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	   private static FullTimeSalaryDAO instance;
	   private FullTimeSalaryDAOImpl(){}
	   public static FullTimeSalaryDAO getInstance(){
	      if(instance==null) instance=new FullTimeSalaryDAOImpl();
	      return instance;
	   }
	   
	   @Override
	   public ArrayList<FullTimeSalTO> findAllMoney(String empCode) {
	      // TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	         logger.debug(" selectYearSalary 시작 ");
	      }
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      ArrayList<FullTimeSalTO> fullTimeSalaryList=new ArrayList<FullTimeSalTO>();
	      try {
	         con = dataSourceTransactionManager.getConnection();

	         StringBuffer query = new StringBuffer();
	           
	         query.append("SELECT EMP_CODE, APPLY_YEAR_MONTH, BASIC_SALARY, POSITION_SALARY, FAMILY_SALARY, FOOD_SALARY, OVER_WORK_SALARY, NATIONAL_PENSION, HEALTH_INSURANCE, ");
	         query.append("LONG_TERM_INSURANCE, EMPLOYMENT_INSURANCE, RELIGION_DONATION, INCOME_TAX, RESIDENT_TAX, FINALIZE_STATUS, BASIC_SAL_BEFORE ");
	         query.append("FROM  FULLTIME_EMPLOYEE_SALARY WHERE APPLY_YEAR_MONTH = ? ");
	         
	         pstmt = con.prepareStatement(query.toString());
	         pstmt.setString(1, empCode);
	         rs = pstmt.executeQuery();
	         FullTimeSalTO fullTimeSalary = null;
	         while (rs.next()) {
	            fullTimeSalary = new FullTimeSalTO();
	            fullTimeSalary.setEmpCode(rs.getString("EMP_CODE"));
	            fullTimeSalary.setApplyYearMonth(rs.getString("APPLY_YEAR_MONTH"));
	            fullTimeSalary.setBasicSalary(rs.getString("BASIC_SALARY"));
	            fullTimeSalary.setPositionSalary(rs.getString("POSITION_SALARY"));
	            fullTimeSalary.setFamilySalary(rs.getString("FAMILY_SALARY"));
	            fullTimeSalary.setFoodSalary(rs.getString("FOOD_SALARY"));
	            fullTimeSalary.setOverWorkSalary(rs.getString("OVER_WORK_SALARY"));
	            fullTimeSalary.setNationalPension(rs.getString("NATIONAL_PENSION"));
	            fullTimeSalary.setHealthInsurance(rs.getString("HEALTH_INSURANCE"));
	            fullTimeSalary.setLongTermInsurance(rs.getString("LONG_TERM_INSURANCE"));
	            fullTimeSalary.setEmploymentInsurance(rs.getString("EMPLOYMENT_INSURANCE"));
	            fullTimeSalary.setReligionDonation(rs.getString("RELIGION_DONATION"));
	            fullTimeSalary.setIncomeTax(rs.getString("INCOME_TAX"));
	            fullTimeSalary.setResidentTax(rs.getString("RESIDENT_TAX"));
	            fullTimeSalary.setFinalizeStatus(rs.getString("FINALIZE_STATUS"));
	            fullTimeSalary.setBasicSalBefore(rs.getString("BASIC_SAL_BEFORE"));
	            fullTimeSalaryList.add(fullTimeSalary);
	         }
	         if (logger.isDebugEnabled()) {
	            logger.debug(" selectYearSalary 종료 ");
	         }
	         //System.out.println("왜안나옴?장난함?????");
	         //System.out.println(fullTimeSalaryList);
	         return fullTimeSalaryList;
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(pstmt, rs);
	      }
	   }
	   

	   @Override
	   public ArrayList<FullTimeSalTO> selectFullTimeSalary(String applyYear, String empCode) {
	      // TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	         logger.debug(" selectYearSalary 시작 ");
	      }

	      ArrayList<FullTimeSalTO> fullTimeSalaryList=new ArrayList<FullTimeSalTO>();
	      Connection con = null;
	      CallableStatement cstmt = null;
	      ResultSet rs = null;

	         try {         
	        	 	con = dataSourceTransactionManager.getConnection();
	        	 	StringBuffer query = new StringBuffer();
	                query.append("{call P_CREATE_MONTH_SALARY(?,?,?,?,?)}");
	                cstmt = con.prepareCall(query.toString());
	                cstmt.setString(1, applyYear);
	                cstmt.setString(2, empCode);
	                cstmt.registerOutParameter(3, OracleTypes.CURSOR);
	                cstmt.registerOutParameter(4, OracleTypes.VARCHAR);
	                cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
	                cstmt.executeQuery();
	                System.out.println(applyYear);
	                System.out.println(empCode);
	                System.out.println("44444444444444444444");
	                rs = (ResultSet) cstmt.getObject(3);
	                
	                String errorCode = cstmt.getString(4);
	                String errorMsg = cstmt.getString(5);
	                System.out.println(errorCode);
	                System.out.println(errorMsg);
	                
	                System.out.println("555555555555555555555555");
	                FullTimeSalTO fullTimeSalary = null;
	                System.out.println("rs에 얼마나들어감?");
	                System.out.println(rs);
	        
	         while (rs.next()) {
	        	System.out.println("666666666666666666666666666666");
	            fullTimeSalary = new FullTimeSalTO();
	            fullTimeSalary.setEmpCode(rs.getString("EMP_CODE"));
	            fullTimeSalary.setApplyYearMonth(rs.getString("APPLY_YEAR_MONTH"));
	            fullTimeSalary.setBasicSalary(rs.getString("BASIC_SALARY"));
	            fullTimeSalary.setPositionSalary(rs.getString("POSITION_SALARY"));
	            fullTimeSalary.setFamilySalary(rs.getString("FAMILY_SALARY"));
	            fullTimeSalary.setFoodSalary(rs.getString("FOOD_SALARY"));
	            fullTimeSalary.setOverWorkSalary(rs.getString("OVER_WORK_SALARY"));
	            fullTimeSalary.setNationalPension(rs.getString("NATIONAL_PENSION"));
	            fullTimeSalary.setHealthInsurance(rs.getString("HEALTH_INSURANCE"));
	            fullTimeSalary.setLongTermInsurance(rs.getString("LONG_TERM_INSURANCE"));
	            fullTimeSalary.setEmploymentInsurance(rs.getString("EMPLOYMENT_INSURANCE"));
	            fullTimeSalary.setReligionDonation(rs.getString("RELIGION_DONATION"));
	            fullTimeSalary.setIncomeTax(rs.getString("INCOME_TAX"));
	            fullTimeSalary.setResidentTax(rs.getString("RESIDENT_TAX"));
	            fullTimeSalary.setFinalizeStatus(rs.getString("FINALIZE_STATUS"));
	            fullTimeSalary.setBasicSalBefore(rs.getString("BASIC_SALARY"));
	            fullTimeSalaryList.add(fullTimeSalary);
	         }
	         if (logger.isDebugEnabled()) {
	            logger.debug(" selectYearSalary 종료 ");
	         }
	         //System.out.println("너는???");
	         //System.out.println(fullTimeSalaryList);
	         return fullTimeSalaryList;
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(cstmt, rs);
	      }
	   }
	  
	   
	   @Override
	   public void updateFullTimeSalary(List<FullTimeSalTO> fullTimeSalary) {
	      // TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	         logger.debug(" updateFullTimeSalary 시작 ");
	      }

	      Connection con = null;
	      PreparedStatement pstmt = null;
	      try {
	    	 for(FullTimeSalTO Code : fullTimeSalary) {
	         con = dataSourceTransactionManager.getConnection();
	         StringBuffer query = new StringBuffer();
	         
	         query.append("UPDATE FULLTIME_EMPLOYEE_SALARY SET FINALIZE_STATUS = ? WHERE EMP_CODE = ? AND APPLY_YEAR_MONTH = ?");
	         pstmt = con.prepareStatement(query.toString());
	         
	         //System.out.println("SQL1111111111111111111111111111111111111111111111111");
	         pstmt.setString(1, Code.getFinalizeStatus());
	        // System.out.println("SQL222222222222222222222222222222222222222222222222222");
	         pstmt.setString(2, Code.getEmpCode());
	         //System.out.println("SQL3333333333333333333333333333333333333333333333333333");
	         pstmt.setString(3, Code.getApplyYearMonth());
	         //System.out.println("SQL44444444444444444444444444444444444444444444444444444");
	         System.out.println(Code.getFinalizeStatus());
	         System.out.println(Code.getEmpCode());
	         System.out.println(Code.getApplyYearMonth());
	         //System.out.println("SQL555555555555555555555555555555555555555555555555555555");
	    	 }
	    	 pstmt.executeUpdate();
	    	 //System.out.println("SQL666666666666666666666666666666666666666666666666666666");
	    	 
	         if (logger.isDebugEnabled()) {
	            logger.debug(" updateFullTimeSalary 종료 ");
	         }
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(pstmt);
	      }
	   }
	   
	   @Override
		public ArrayList<PayDayTO> selectPayDayList(){
	      // TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	         logger.debug(" selectYearSalary 시작 ");
	      }

	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      ArrayList<PayDayTO> selectPayDayList=new ArrayList<PayDayTO>();
	      try {
	         con = dataSourceTransactionManager.getConnection();

	         StringBuffer query = new StringBuffer();
	           
	         query.append("select * from SALARY_BONUS_DATE");
	         
	         pstmt = con.prepareStatement(query.toString());
	         rs = pstmt.executeQuery();
	         PayDayTO payday = null;
	         while (rs.next()) {
	        	 payday = new PayDayTO();
	        	 payday.setOrd(rs.getString("ORD"));
	        	 payday.setPayment_date(rs.getString("PAYMENT_DATE"));
	        	 payday.setSmltn_issue(rs.getString("SMLTN_ISSUE"));
	        	 payday.setSalary_type(rs.getString("SALARY_TYPE"));
	        	 payday.setRemarks(rs.getString("REMARKS"));
	             selectPayDayList.add(payday);
	         }
	         if (logger.isDebugEnabled()) {
	            logger.debug(" selectYearSalary 종료 ");
	         }
	         //System.out.println("너는???");
	         //System.out.println(selectPayDayList);
	         return selectPayDayList;
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(pstmt, rs);
	      }
	   }
	   

}