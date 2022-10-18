package kr.co.yooooon.hr.salary.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.salary.to.*;

public class PymntDditmDAOImpl implements PymntDditmDAO{
	protected final Log logger = LogFactory.getLog(this.getClass());
	   private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	   private static PymntDditmDAO instance;
	   private PymntDditmDAOImpl(){}
	   public static PymntDditmDAO getInstance(){
	      if(instance==null) instance=new PymntDditmDAOImpl();
	      return instance;
	   }
	
	@Override
	   public ArrayList<basePymntItmNameCodeTO> findPymntDditmList(pymntDditmTO pymntData) {
	      // TODO Auto-generated method stub
	      if (logger.isDebugEnabled()) {
	         logger.debug(" selectPymntDditmList 시작 ");
	      }

	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      basePymntItmNameCodeTO BPIC = null;
	      ArrayList<basePymntItmNameCodeTO> pymntDditmList=new ArrayList<basePymntItmNameCodeTO>();
	      try {
	         con = dataSourceTransactionManager.getConnection();

	         StringBuffer query = new StringBuffer();
	           
	         query.append("select code, PAYMENT_ITEM_NAME from pymnt_dditm ");
	         query.append("WHERE WAGE_CLASSIFICATION = ? and PYMDD_CLSFC = ? and YEAR = ? ");

	         String slryClsfc=pymntData.getSlryClsfc();
	         String pymddClsfc=pymntData.getPymddClsfc();
	         String year=pymntData.getYear();
	         
	         
	         pstmt = con.prepareStatement(query.toString());
	         pstmt.setString(1, slryClsfc);
	         pstmt.setString(2, pymddClsfc);
	         pstmt.setString(3, year);
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	        	BPIC= new basePymntItmNameCodeTO();
	            
	        	BPIC.setCode(rs.getString("CODE"));
	        	BPIC.setPymmtItmName(rs.getString("PAYMENT_ITEM_NAME"));
	            
	        	pymntDditmList.add(BPIC);
	         }
	         if (logger.isDebugEnabled()) {
	            logger.debug(" selectYearSalary 종료 ");
	         }
	         return pymntDditmList;
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(pstmt, rs);
	      }
	   }


	@Override
		public ArrayList<paymentItmOptionRsTO> setPaymentItmNodeOption(paymentItmOptionTO paymntOtionTO) {
			// TODO Auto-generated method stub
		    if (logger.isDebugEnabled()) {
		        logger.debug(" selectPaymentItmNodeOption 시작 ");
		    }

		    Connection con = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    paymentItmOptionRsTO PIOR = null;
		    Boolean sta=false;
		    ArrayList<paymentItmOptionRsTO> paymentItmOptionRs=new ArrayList<paymentItmOptionRsTO>();
		    try {
		    	con = dataSourceTransactionManager.getConnection();

		        StringBuffer query = new StringBuffer();
		        String code=paymntOtionTO.getCode();
		        
		        
				if(code.equals("P00") || code.equals("P10") || code.equals("P20") || code.equals("P30") || code.equals("V00") || code.equals("S30")) { 
		        query.append(" SELECT TAXATION_CLASSIFICATION, "
		        		+ " TAX_FREE_TYPE, "
		        		+ " REDUCTION_STATUS, "
		        		+ " APPRENTICE_APPLICATION, "
		        		+ " MONTHLY_WAGE, "
		        		+ " CUT_SELECTION, "
		        		+ " CLASSIFICATION_STATUS, "
		        		+ " CALCULATION_CLASSIFICATION, "
		        		+ " APPLY_LEAVE_ABSENCE, "
		        		+ " APPLICATION_DROP_PARTY, "
		        		+ " EMPLOYMENT_INSURANCE, "
		        		+ " EXCLS_CLCD, "
		        		+ " p.PRIORITY, "
		        		+ " p.PRIORITY_CODE, "
		        		+ " p.CLSFC_CDCL"
		        		+ " from PYMNT_DDITM, PRIORITY p ");
		        query.append(" WHERE pymnt_dditm.CODE = ? AND PYMNT_DDITM.CODE = p.CODE AND WAGE_CLASSIFICATION = ? AND PYMDD_CLSFC = ? AND YEAR = ? ");
		        
		        code=paymntOtionTO.getCode();
		        String slryClsfc=paymntOtionTO.getSlryClsfc();
		        String pymntDdctn=paymntOtionTO.getPymntDdctn();
		        String year=paymntOtionTO.getYear();
		        
		        pstmt = con.prepareStatement(query.toString());
		        pstmt.setString(1, code);
		        pstmt.setString(2, slryClsfc);
		        pstmt.setString(3, pymntDdctn);
		        pstmt.setString(4, year);
		        rs = pstmt.executeQuery();
			        while (rs.next()) {
			        	PIOR= new paymentItmOptionRsTO();
			            
			        	PIOR.setTaxationClassification(rs.getString("TAXATION_CLASSIFICATION"));
			        	PIOR.setTaxFreeType(rs.getString("TAX_FREE_TYPE"));
			        	PIOR.setReductionStatus(rs.getString("REDUCTION_STATUS"));
			        	PIOR.setApprenticeApplication(rs.getString("APPRENTICE_APPLICATION"));
			        	PIOR.setMonthlyWage(rs.getString("MONTHLY_WAGE"));
			        	PIOR.setCutSelection(rs.getString("CUT_SELECTION"));
			        	PIOR.setClassificationStatus(rs.getString("CLASSIFICATION_STATUS"));
			        	PIOR.setCalculationClassification(rs.getString("CALCULATION_CLASSIFICATION"));
			        	PIOR.setApplyLeaveAbsence(rs.getString("APPLY_LEAVE_ABSENCE"));
			        	PIOR.setApplicationDropParty(rs.getString("APPLICATION_DROP_PARTY"));
			        	PIOR.setEmploymentInsurance(rs.getString("EMPLOYMENT_INSURANCE"));
			        	PIOR.setExclsClcd(rs.getString("EXCLS_CLCD"));
			        	PIOR.setPriority(rs.getString("PRIORITY"));
			        	PIOR.setPriorityCode(rs.getString("PRIORITY_CODE"));
			        	PIOR.setClsfcCdcl(rs.getString("CLSFC_CDCL"));
			            
			        	paymentItmOptionRs.add(PIOR);
			        }
		        }else {
		        
		        	query.append(" SELECT TAXATION_CLASSIFICATION, "
		        			+ " TAX_FREE_TYPE, "
		        			+ " REDUCTION_STATUS, "
		        			+ " APPRENTICE_APPLICATION, "
		        			+ " MONTHLY_WAGE, "
		        			+ " CUT_SELECTION, "
		        			+ " CLASSIFICATION_STATUS, "
		        			+ " CALCULATION_CLASSIFICATION, "
		        			+ " APPLY_LEAVE_ABSENCE, "
		        			+ " APPLICATION_DROP_PARTY, "
		        			+ " EMPLOYMENT_INSURANCE, "
		        			+ " EXCLS_CLCD "
			        		+ " from pymnt_dditm");
			        query.append(" WHERE pymnt_dditm.CODE = ? AND WAGE_CLASSIFICATION = ? AND PYMDD_CLSFC = ? AND YEAR = ? ");
		         
			        code=paymntOtionTO.getCode();
			        String slryClsfc=paymntOtionTO.getSlryClsfc();
			        String pymntDdctn=paymntOtionTO.getPymntDdctn();
			        String year=paymntOtionTO.getYear();
			        
			        pstmt = con.prepareStatement(query.toString());
			        pstmt.setString(1, code);
			        pstmt.setString(2, slryClsfc);
			        pstmt.setString(3, pymntDdctn);
			        pstmt.setString(4, year);
			        
			        rs = pstmt.executeQuery();
			        while (rs.next()) {
			        	PIOR= new paymentItmOptionRsTO();
			            
			        	PIOR.setTaxationClassification(rs.getString("TAXATION_CLASSIFICATION"));
			        	PIOR.setTaxFreeType(rs.getString("TAX_FREE_TYPE"));
			        	PIOR.setReductionStatus(rs.getString("REDUCTION_STATUS"));
			        	PIOR.setApprenticeApplication(rs.getString("APPRENTICE_APPLICATION"));
			        	PIOR.setMonthlyWage(rs.getString("MONTHLY_WAGE"));
			        	PIOR.setCutSelection(rs.getString("CUT_SELECTION"));
			        	PIOR.setClassificationStatus(rs.getString("CLASSIFICATION_STATUS"));
			        	PIOR.setCalculationClassification(rs.getString("CALCULATION_CLASSIFICATION"));
			        	PIOR.setApplyLeaveAbsence(rs.getString("APPLY_LEAVE_ABSENCE"));
			        	PIOR.setApplicationDropParty(rs.getString("APPLICATION_DROP_PARTY"));
			        	PIOR.setEmploymentInsurance(rs.getString("EMPLOYMENT_INSURANCE"));
			        	PIOR.setExclsClcd(rs.getString("EXCLS_CLCD"));
			            
			        	paymentItmOptionRs.add(PIOR);
			        }

		        }
		        if (logger.isDebugEnabled()) {
		            logger.debug(" selectYearSalary 종료 ");
		        }
		        return paymentItmOptionRs;
		    } catch (Exception sqle) {
		    	logger.fatal(sqle.getMessage());
		        throw new DataAccessException(sqle.getMessage());
		    } finally {
		    	dataSourceTransactionManager.close(pstmt, rs);
		    }
		}
		
		@Override
		public ArrayList<jobCodeRsTO> setJobCode(jobCodeTO priorityCodeTO) {
		      // TODO Auto-generated method stub
		      if (logger.isDebugEnabled()) {
		         logger.debug(" selectPymntDditmList 시작 ");
		      }

		      Connection con = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      jobCodeRsTO JCRT = null;
		      ArrayList<jobCodeRsTO> jobCodeRs=new ArrayList<jobCodeRsTO>();
		      try {
		         con = dataSourceTransactionManager.getConnection();

		         StringBuffer query = new StringBuffer();
		           
		         query.append("SELECT JOB_CODE, CATEGORY_NAME FROM JOB_CODE ");
		         query.append("WHERE PRIORITY_CODE = ? AND PAYMENT_ITEM_NAME = ? ");
		         
		         String priorityCode=priorityCodeTO.getPriorityCode();
		         String slryClsfc=priorityCodeTO.getSlryClsfc();
		         pstmt = con.prepareStatement(query.toString());
		         pstmt.setString(1, priorityCode);
		         pstmt.setString(2, slryClsfc);
		         rs = pstmt.executeQuery();
		         while (rs.next()) {
		        	JCRT= new jobCodeRsTO();
		            
		        	JCRT.setJobCode(rs.getString("JOB_CODE"));
		        	JCRT.setCategoryName(rs.getString("CATEGORY_NAME"));

		        	jobCodeRs.add(JCRT);
		         }
		         if (logger.isDebugEnabled()) {
		            logger.debug(" selectYearSalary 종료 ");
		         }
		         return jobCodeRs;
		      } catch (Exception sqle) {
		         logger.fatal(sqle.getMessage());
		         throw new DataAccessException(sqle.getMessage());
		      } finally {
		         dataSourceTransactionManager.close(pstmt, rs);
		      }
		   }
		
		@Override
		public ArrayList<dissectionRsTO> setDissection(dissectionTO dissectionData) {
		      // TODO Auto-generated method stub
		      if (logger.isDebugEnabled()) {
		         logger.debug(" selectPymntDditmList 시작 ");
		      }

		      Connection con = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      dissectionRsTO DSECTRT = null;
		      ArrayList<dissectionRsTO> dissectionRs=new ArrayList<dissectionRsTO>();
		      try {
		         con = dataSourceTransactionManager.getConnection();

		         StringBuffer query = new StringBuffer();
		         String num=dissectionData.getNum();
		         if(num.equals("1")) {
		         query.append("SELECT CATEGORY_CODE, CATEGORY_NAME, CLCLT_CLSFC FROM PRSNL_AFBSC " );
		         query.append("WHERE JOB_CODE = ? AND PRIORITY_CODE = ? ");
		         
		         
		         String disecPrioCode=dissectionData.getDisecPrioCode();
		         String jobCode=dissectionData.getJobCode();
		         
		         pstmt = con.prepareStatement(query.toString());
		         pstmt.setString(1, jobCode);
		         pstmt.setString(2, disecPrioCode);
		         rs = pstmt.executeQuery();
		         while (rs.next()) {
		        	 DSECTRT= new dissectionRsTO();
		            
		        	 DSECTRT.setCategoryCode(rs.getString("CATEGORY_CODE"));
		        	 DSECTRT.setCategoryName(rs.getString("CATEGORY_NAME"));
		        	 DSECTRT.setCLCLT_CLSFC(rs.getString("CLCLT_CLSFC"));
		        	 dissectionRs.add(DSECTRT);
		         }
		         }else {
		        	 query.append("SELECT CATEGORY_CODE, CATEGORY_NAME, CLCLT_CLSFC FROM PRSNL_AFBSC " );
			         query.append("WHERE PRIORITY_CODE = ? ");
			         
			         
			         String disecPrioCode=dissectionData.getDisecPrioCode();
			         
			         pstmt = con.prepareStatement(query.toString());
			         pstmt.setString(1, disecPrioCode);
			         rs = pstmt.executeQuery();
			         while (rs.next()) {
			        	 DSECTRT= new dissectionRsTO();
			            
			        	 DSECTRT.setCategoryCode(rs.getString("CATEGORY_CODE"));
			        	 DSECTRT.setCategoryName(rs.getString("CATEGORY_NAME"));
			        	 DSECTRT.setCLCLT_CLSFC(rs.getString("CLCLT_CLSFC"));
			        	 dissectionRs.add(DSECTRT);
			         }
		         }
		         if (logger.isDebugEnabled()) {
		            logger.debug(" selectYearSalary 종료 ");
		         }
		         return dissectionRs;
		      } catch (Exception sqle) {
		         logger.fatal(sqle.getMessage());
		         throw new DataAccessException(sqle.getMessage());
		      } finally {
		         dataSourceTransactionManager.close(pstmt, rs);
		      }
		   }
		
		@Override
		public ArrayList<amnclFrmlRsTO> setAmnclFrml(amnclFrmlTO AFTTO) {
		      // TODO Auto-generated method stub
		      if (logger.isDebugEnabled()) {
		         logger.debug(" selectPymntDditmList 시작 ");
		      }

		      Connection con = null;
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      amnclFrmlRsTO AFRTO = null;
		      ArrayList<amnclFrmlRsTO> AFRTOList=new ArrayList<amnclFrmlRsTO>();
		      try {
		         con = dataSourceTransactionManager.getConnection();

		         StringBuffer query = new StringBuffer();
		         
		         
		         
		         
		         String num=AFTTO.getNum();
		         if(num.equals("1")) {
		        	 
		        	 
		        	 
		        
	        		 System.out.println("1"+AFTTO.getCode());
	        		
	        		 
	        		 System.out.println("3"+AFTTO.getSlryClsfc());
	        		 System.out.println("4"+AFTTO.getPymntDdctn());
	        		 System.out.println("5"+AFTTO.getYear());
	        		 
	        		 
	        		 
		        	 query.append("SELECT DATA FROM AMNCL_FRML " );
			         query.append("WHERE CODE = ? "
			         		+ "  AND WAGE_CLASSIFICATION = ? "
			         		+ "  AND PYMDD_CLSFC = ? "
			         		+ "  AND YEAR = ? ");
			         
			         String code=AFTTO.getCode();
			         String slryClsfc=AFTTO.getSlryClsfc();
			         String pymntDdctn=AFTTO.getPymntDdctn();
			         String year=AFTTO.getYear();
			         pstmt = con.prepareStatement(query.toString());
			         pstmt.setString(1, code);
			         pstmt.setString(2, slryClsfc);
			         pstmt.setString(3, pymntDdctn);
			         pstmt.setString(4, year);
			         
			         rs = pstmt.executeQuery();
			         while (rs.next()) {
			        	 AFRTO= new amnclFrmlRsTO();
			            
			        	 AFRTO.setData(rs.getString("DATA"));
			        	 AFRTOList.add(AFRTO);
			         }
			         System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@식댕ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
		         }else {
		        	 if(num.equals("2")) {
		        		 
		        		
		        		 
		        		 query.append("SELECT DATA FROM AMNCL_FRML " );
				         query.append("WHERE AMNCL_FRML = ? "
				         		+ "  AND CODE = ? "
				         		+ "  AND PRIORITY_CODE = ? "
				         		+ "  AND WAGE_CLASSIFICATION = ? "
				         		+ "  AND PYMDD_CLSFC = ? "
				         		+ "  AND YEAR = ? ");
				         
				         String amnclFrmlCode=AFTTO.getAmnclFrmlCode();
				         String code=AFTTO.getCode();
				         String disecPrioCode=AFTTO.getDisecPrioCode();
				         
				         String slryClsfc=AFTTO.getSlryClsfc();
				         String pymntDdctn=AFTTO.getPymntDdctn();
				         String year=AFTTO.getYear();
				         System.out.println(amnclFrmlCode);
				         System.out.println(code);
				         System.out.println(disecPrioCode);
				         System.out.println(slryClsfc);
				         System.out.println(pymntDdctn);
				         System.out.println(year);
				         
		        		 pstmt = con.prepareStatement(query.toString());
				         pstmt.setString(1, amnclFrmlCode);
				         pstmt.setString(2, code);
				         pstmt.setString(3, disecPrioCode);
				         pstmt.setString(4, slryClsfc);
				         pstmt.setString(5, pymntDdctn);
				         pstmt.setString(6, year);
				         rs = pstmt.executeQuery();
				         while (rs.next()) {
				        	 AFRTO= new amnclFrmlRsTO();
				            
				        	 AFRTO.setData(rs.getString("DATA"));
				        	 AFRTOList.add(AFRTO);
				         }
				         System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@가족수당ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
		        	 }else{
				         query.append("SELECT DATA FROM AMNCL_FRML " );
				         query.append("WHERE AMNCL_FRML = ? "
				         		+ "  AND CODE = ? "
				         		+ "  AND PRIORITY_CODE = ? "
				         		+ "  AND JOB_CODE = ? "
				         		+ "  AND WAGE_CLASSIFICATION = ? "
				         		+ "  AND PYMDD_CLSFC = ? "
				         		+ "  AND YEAR = ? ");
				         
				         String amnclFrmlCode=AFTTO.getAmnclFrmlCode();
				         String code=AFTTO.getCode();
				         String disecPrioCode=AFTTO.getDisecPrioCode();
				         String jobCode=AFTTO.getJobCode();
				         String slryClsfc=AFTTO.getSlryClsfc();
				         String pymntDdctn=AFTTO.getPymntDdctn();
				         String year=AFTTO.getYear();
				         System.out.println(amnclFrmlCode);
				         System.out.println(code);
				         System.out.println(disecPrioCode);
				         System.out.println(jobCode);
				         System.out.println(slryClsfc);
				         System.out.println(pymntDdctn);
				         System.out.println(year);
				         
		
				         pstmt = con.prepareStatement(query.toString());
				         pstmt.setString(1, amnclFrmlCode);
				         pstmt.setString(2, code);
				         pstmt.setString(3, disecPrioCode);
				         pstmt.setString(4, jobCode);
				         pstmt.setString(5, slryClsfc);
				         pstmt.setString(6, pymntDdctn);
				         pstmt.setString(7, year);
				         rs = pstmt.executeQuery();
				         while (rs.next()) {
				        	 AFRTO= new amnclFrmlRsTO();
				            
				        	 AFRTO.setData(rs.getString("DATA"));
				        	 AFRTOList.add(AFRTO);
				         }
				         System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@기본급ㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂㅂ");
		        	 }}

		         if (logger.isDebugEnabled()) {
		            logger.debug(" selectYearSalary 종료 ");
		         }
		         return AFRTOList;
		      } catch (Exception sqle) {
		         logger.fatal(sqle.getMessage());
		         throw new DataAccessException(sqle.getMessage());
		      } finally {
		         dataSourceTransactionManager.close(pstmt, rs);
		      }
		   }
}