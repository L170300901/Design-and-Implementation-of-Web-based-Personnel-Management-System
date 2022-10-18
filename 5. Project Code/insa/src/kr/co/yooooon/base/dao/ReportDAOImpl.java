package kr.co.yooooon.base.dao;

import java.sql.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class ReportDAOImpl implements ReportDAO{
   

   protected final Log logger = LogFactory.getLog(this.getClass());
   private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
   
   private static ReportDAOImpl instance;
   public static ReportDAOImpl getInstance() {
      if (instance == null)
         instance = new ReportDAOImpl();
      return instance;
   }
   
   @Override
   public  ReportTO selectReport(String empCode) {
         if (logger.isDebugEnabled()) {
            logger.debug("  다오임플 시작 ");
         }
         ReportTO to= new ReportTO(); 
        
         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs=null;
         
         try {
            StringBuffer insertQuery = new StringBuffer();
            insertQuery.append(" select e1.EMP_NAME as empName\r\n" + 
            		"        ,e1.emp_dept as deptName \r\n" + 
            		"        ,e3.emp_address as address\r\n" + 
            		"        ,e3.detail_address as detailAddress\r\n" + 
            		"        , TO_CHAR(e1.emp_join,'MM')||'.'||TO_CHAR(e1.emp_join, 'DD')||'.'||TO_CHAR(e1.emp_join,'YYYY') as hiredate \r\n" + 
            		"        , e4.EMPLOYMENT_TYPE as employmentType \r\n" + 
            		"        , e4.POSITION as position \r\n" + 
            		"        , e4.emp_occupation as occupation\r\n" + 
            		" from emp_new e1 ,emp_personal_information e2, emp_residence_information e3, emp_office_information e4\r\n" + 
            		" where e1.emp_code=e2.emp_code\r\n" + 
            		" and  e1.emp_code=e3.emp_code\r\n" + 
            		" and  e1.emp_code=e4.emp_code\r\n" + 
            		" and  e1.emp_code=?"
            		);
		
                               
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(insertQuery.toString());
            pstmt.setString(1, empCode);
            rs=pstmt.executeQuery();
        	
            while(rs.next()) {
            	to.setEmpName(rs.getString("empName"));
            	to.setHiredate(rs.getString("hiredate"));
            	to.setOccupation(rs.getString("occupation"));
            	to.setEmploymentType(rs.getString("employmentType"));
            	to.setPosition(rs.getString("position"));
            	to.setAddress(rs.getString("address"));
            	to.setDetailAddress(rs.getString("detailAddress"));
            	to.setDeptName(rs.getString("deptName"));
            }
            if (logger.isDebugEnabled()) {
                logger.debug(" 다오임플  종료 ");
             }
            return to;
         } catch(Exception sqle) {
            throw new DataAccessException(sqle.getMessage());         
         } finally {
            dataSourceTransactionManager.close(pstmt, rs);
         }
      }
   
   
   
   @Override
   public  ReportSalaryTO selecSalarytReport(String empCode, String applyMonth) {
         if (logger.isDebugEnabled()) {
            logger.debug(" 다오임플 시작 ");
         }
         ReportSalaryTO to= new ReportSalaryTO(); 
        
         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs=null;
         try {
        	System.out.println(empCode+"/3/"+applyMonth);
            StringBuffer insertQuery = new StringBuffer();
            insertQuery.append(" SELECT e.EMP_NAME as empName ");
            insertQuery.append(", p.POSITION as position ");
            insertQuery.append(", d.DEPT_name as deptName ");
            insertQuery.append(", TO_CHAR(w.HIREDATE, 'YYYY\"년\"MM\"월\"DD\"일\"' ) as hiredate ");
            insertQuery.append(", REPLACE(ms.APPLY_YEAR_MONTH, '-', '년')||'월' as applyYearMonth ");
            insertQuery.append(", (TO_CHAR(ms.TOTAL_EXT_SAL, '9,999,999,999')) ||'원' as totalExtSal ");
            insertQuery.append(", (TO_CHAR(ms.TOTAL_DEDUCTION, '9,999,999,999')) ||'원' as totalDeduction ");
            insertQuery.append(", (TO_CHAR(ms.TOTAL_PAYMENT, '9,999,999,999'))||'원' as totalPayment ");
            insertQuery.append(", (TO_CHAR(ms.REAL_SALARY, '9,999,999,999')) ||'원' as realSalary ");
            insertQuery.append(", (TO_CHAR(ms.SALARY, '9,999,999,999'))||'원' as salary ");
            insertQuery.append(", (TO_CHAR(ms.COST, '9,999,999,999'))||'원' as cost ");
            insertQuery.append(", (TO_CHAR(MAX(decode(md.DEDUCTION_NAME,'건강보험',price,price,null)), '9,999,999,999'))||'원'  as healthIns ");
            insertQuery.append(", (TO_CHAR(MAX(decode(md.DEDUCTION_NAME,'고용보험',price,price,null)), '9,999,999,999'))||'원' as goyongIns ");
            insertQuery.append(", (TO_CHAR(MAX(decode(md.DEDUCTION_NAME,'장기요양보험',price,price,null)), '9,999,999,999'))||'원' as janggiIns ");
            insertQuery.append(", (TO_CHAR(MAX(decode(md.DEDUCTION_NAME,'국민연금',price,price,null)), '9,999,999,999'))||'원' as gukmin ");
            insertQuery.append(" FROM EMP e , MONTH_SALARY ms  ,  WORK_INFO w, MONTH_DEDUCTION md , POSITION p,DEPT d ");
            insertQuery.append(" WHERE e.EMP_CODE = ? ");
            insertQuery.append("AND e.EMP_CODE = w.EMP_CODE ");
            insertQuery.append("AND e.dept_code=d.dept_code ");
            insertQuery.append("AND md.APPLY_YEAR_MONTH = ms.APPLY_YEAR_MONTH ");
            insertQuery.append("AND ms.APPLY_YEAR_MONTH = ? ");
            insertQuery.append("AND P.POSITION_CODE = e.POSITION_CODE ");
            insertQuery.append("GROUP BY ");
            insertQuery.append("e.EMP_NAME, p.POSITION, d.DEPT_name, w.HIREDATE, p.BASE_SALARY ");
            insertQuery.append(", ms.APPLY_YEAR_MONTH, ms.SALARY, ms.TOTAL_EXT_SAL, ms.TOTAL_DEDUCTION, ms.TOTAL_PAYMENT, ms.REAL_SALARY,MS.COST ");
            
            con = dataSourceTransactionManager.getConnection();
            pstmt = con.prepareStatement(insertQuery.toString());
            pstmt.setString(1, empCode);
            pstmt.setString(2, applyMonth);
            rs=pstmt.executeQuery();
        	
            while(rs.next()) {
            	to.setEmpName(rs.getString("empName"));
            	to.setPosition(rs.getString("position"));
            	to.setDeptName(rs.getString("deptName"));
            	to.setHiredate(rs.getString("hiredate"));
            	to.setApplyYearMonth(rs.getString("applyYearMonth"));
            	to.setTotalExtSal(rs.getString("totalExtSal"));
            	to.setTotalDeduction(rs.getString("totalDeduction"));
            	to.setTotalPayment(rs.getString("totalPayment"));
            	to.setRealSalary(rs.getString("realSalary"));
            	to.setSalary(rs.getString("salary"));
            	to.setCost(rs.getString("cost"));
            	to.setHealthIns(rs.getString("healthIns"));
            	to.setGoyongIns(rs.getString("goyongIns"));
            	to.setJanggiIns(rs.getString("janggiIns"));
            	to.setGukmin(rs.getString("gukmin"));	
            	System.out.println(rs.getString("gukmin"));
            }

            if (logger.isDebugEnabled()) {
                logger.debug(" 다오임플  종료 ");
             }
            return to;
         } catch(Exception sqle) {
            throw new DataAccessException(sqle.getMessage());         
         } finally {
            dataSourceTransactionManager.close(pstmt, rs);
         }
        
       
      }
}