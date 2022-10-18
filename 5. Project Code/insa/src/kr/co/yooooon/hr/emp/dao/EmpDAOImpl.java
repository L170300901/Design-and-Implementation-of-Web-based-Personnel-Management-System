package kr.co.yooooon.hr.emp.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.base.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp.to.*;


public class EmpDAOImpl implements EmpDAO{
	//로그, 트렌젝션
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	//싱글톤 패턴 만들기
	private static EmpDAOImpl instance=new EmpDAOImpl();	
	
	private EmpDAOImpl(){}
	public static EmpDAO getInstance(){
		if(instance==null) instance=new EmpDAOImpl();
		return instance;
	}
	
	//오버라이드 메서드
	public LoginCheckTO selectEmp(String name){
		if (logger.isDebugEnabled()) {
			logger.debug(" selectEmp 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("SELECT e1.*,e2.*,e3.position FROM EMP_PW e1, EMP_NEW e2,  EMP_OFFICE_INFORMATION e3 WHERE e1.EMP_CODE=e2.EMP_CODE AND emp_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			LoginCheckTO emp=null;
			if(rs.next()){
				emp=new LoginCheckTO();
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setEmpName(rs.getString("EMP_NAME"));
				emp.setEmpDept(rs.getString("EMP_DEPT"));
				emp.setCompanyCode(rs.getString("COMPANY_CODE"));
				emp.setEmpPw(rs.getString("EMP_PW"));
				emp.setPosition(rs.getString("position"));
				
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmp 종료 ");
			}
			return emp;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public String selectLastEmpCode() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectLastEmpCode 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("select emp_code from emp order by emp_code desc");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rs.next();

			if (logger.isDebugEnabled()) {
				logger.debug(" selectLastEmpCode 종료 ");
			}
			return rs.getString("emp_code");
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public void registEmployee(EmpTO empto) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" registEmployee 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into emp values(?,?,TO_DATE(?,'YYYY/MM/DD'),?,?,?,?,?,?,?,?,?,?)");
			
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			pstmt.setString(1, empto.getEmpCode());
			pstmt.setString(2, empto.getEmpName());
			pstmt.setString(3, empto.getBirthdate());
			pstmt.setString(4, empto.getGender() );
			pstmt.setString(5, empto.getMobileNumber());
			pstmt.setString(6, empto.getAddress());
			pstmt.setString(7, empto.getDetailAddress());
			pstmt.setString(8, empto.getPostNumber());
			pstmt.setString(9, empto.getEmail());
			pstmt.setString(10, empto.getLastSchool());
			pstmt.setString(11, empto.getImgExtend());
			pstmt.setString(12, empto.getDeptName());
			pstmt.setString(13, empto.getPosition());
			
			pstmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug(" registEmployee 종료 ");
			}		
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
	@Override
	public ArrayList<EmpTO> selectEmpList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectEmpList 시작");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("SELECT E.EMP_CODE, E.EMP_NAME, TO_CHAR(E.BIRTHDATE,'YYYY/MM/DD') BIRTHDATE");
			insertQuery.append(", E.GENDER, E.MOBILE_NUMBER, E.ADDRESS");
			insertQuery.append(", E.DETAIL_ADDRESS, E.POST_NUMBER, E.EMAIL");
			insertQuery.append(", E.LAST_SCHOOL, E.IMG_EXTEND, PD.DEPT_NAME, PD.POSITION ");
			insertQuery.append("FROM EMP E, (SELECT * FROM POSITION P, DEPT D) PD ");
			insertQuery.append("WHERE 1=1 ");
			insertQuery.append("AND E.POSITION_CODE = PD.POSITION_CODE(+) ");
			insertQuery.append("AND E.DEPT_CODE = PD.DEPT_CODE(+) ");
			insertQuery.append("ORDER BY E.EMP_CODE");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			rs=pstmt.executeQuery();
			ArrayList<EmpTO> list=new ArrayList<EmpTO>(); 
			while(rs.next()){
				EmpTO emp=new EmpTO();
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
				list.add(emp);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmpList 종료 ");
			}
			return list;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public ArrayList<EmpTO> selectEmpListD(String dept) {
		System.out.println(dept+"DAO");
		if (logger.isDebugEnabled()) {
			System.out.println("@@@@@@@@@@@@@@6번통과 여기는 db query@@@@@@@@@@@@@@@@");
			logger.debug(" selectEmpListD 시작 ");
		}
		ArrayList<EmpTO> list=new ArrayList<EmpTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT E.EMP_CODE, E.EMP_NAME, TO_CHAR(E.BIRTHDATE,'YYYY/MM/DD') BIRTHDATE");
			query.append(", E.GENDER, E.MOBILE_NUMBER, E.ADDRESS");
			query.append(", E.DETAIL_ADDRESS, E.POST_NUMBER, E.EMAIL");
			query.append(", E.LAST_SCHOOL, E.IMG_EXTEND, PD.DEPT_NAME, PD.POSITION ");
			query.append("FROM EMP E, (SELECT * FROM POSITION P, DEPT D) PD ");
			query.append("WHERE 1=1 ");
			query.append("AND E.POSITION_CODE = PD.POSITION_CODE(+) ");
			query.append("AND E.DEPT_CODE = PD.DEPT_CODE(+) ");
			query.append("AND PD.DEPT_NAME = ? ");
			query.append("ORDER BY E.EMP_CODE");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dept);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				EmpTO emp=new EmpTO();
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
				list.add(emp);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmpListD 종료 ");
			}
			return list;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public ArrayList<EmpTO> selectEmpListN(String name) {
		System.out.println(name+"DAO");
		
		if (logger.isDebugEnabled()) {
			logger.debug(" selectEmp 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ArrayList<EmpTO> list=new ArrayList<EmpTO>();
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("SELECT E.EMP_CODE, E.EMP_NAME, TO_CHAR(E.BIRTHDATE,'YYYY/MM/DD') BIRTHDATE");
			insertQuery.append(", E.GENDER, E.MOBILE_NUMBER, E.ADDRESS");
			insertQuery.append(", E.DETAIL_ADDRESS, E.POST_NUMBER, E.EMAIL");
			insertQuery.append(", E.LAST_SCHOOL, E.IMG_EXTEND, PD.DEPT_NAME, PD.POSITION ");
			insertQuery.append("FROM EMP E, (SELECT * FROM POSITION P, DEPT D) PD ");
			insertQuery.append("WHERE 1=1 ");
			insertQuery.append("AND E.POSITION_CODE = PD.POSITION_CODE(+) ");
			insertQuery.append("AND E.DEPT_CODE = PD.DEPT_CODE(+) ");
			insertQuery.append("AND E.EMP_NAME = ? ");
			insertQuery.append("ORDER BY E.EMP_CODE");
			/*insertQuery.append("select emp_code,emp_name,TO_CHAR(birthdate) birthdate,gender,mobile_number, ");
			insertQuery.append("address,detail_address,post_number,email,last_school,img_extend, ");
			insertQuery.append("position,dept_name ");
			insertQuery.append("from emp where emp_name=?");*/
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			while(rs.next()){
				
					EmpTO emp=new EmpTO();
					emp.setEmpCode(rs.getString("emp_code"));
					emp.setEmpName(rs.getString("emp_name"));
					emp.setBirthdate(rs.getString("birthdate"));
					emp.setGender(rs.getString("gender"));
					emp.setMobileNumber(rs.getString("mobile_number"));
					emp.setAddress(rs.getString("address"));
					emp.setDetailAddress(rs.getString("detail_address"));
					emp.setPostNumber(rs.getString("post_number"));
					emp.setEmail(rs.getString("email"));
					emp.setLastSchool(rs.getString("last_school"));
					emp.setImgExtend(rs.getString("img_extend"));
					emp.setPosition(rs.getString("position"));
					emp.setDeptName(rs.getString("dept_name"));
					list.add(emp);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmp 종료 ");
			}
			return list;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public String getEmpCode(String name) {
		// TODO Auto-generated method stub
		String empCode=null;
		if (logger.isDebugEnabled()) {
			logger.debug(" getEmpCode 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ArrayList<EmpTO> list=new ArrayList<EmpTO>();
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select emp_code from emp where emp_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			while(rs.next()){
					empCode = rs.getString("emp_code");
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" getEmpCode 종료 ");
			}
			return empCode;
		} catch(Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public EmpTO selectEmployee(String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectEmployee 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT E.EMP_CODE, E.EMP_NAME, TO_CHAR(E.BIRTHDATE,'YYYY/MM/DD') BIRTHDATE");
			query.append(", E.GENDER, E.MOBILE_NUMBER, E.ADDRESS");
			query.append(", E.DETAIL_ADDRESS, E.POST_NUMBER, E.EMAIL");
			query.append(", E.LAST_SCHOOL, E.IMG_EXTEND, PD.DEPT_NAME, PD.POSITION ");
			query.append("FROM EMP E, (SELECT * FROM POSITION P, DEPT D) PD ");
			query.append("WHERE 1=1 ");
			query.append("AND E.POSITION_CODE = PD.POSITION_CODE(+) ");
			query.append("AND E.DEPT_CODE = PD.DEPT_CODE(+) ");
			query.append("AND E.EMP_CODE = ? ");
			query.append("ORDER BY E.EMP_CODE");
						
			/* 위랑 같은 결과, 다른 조인방식 사용
			 * query.
			 * append("select emp_code,emp_name,TO_CHAR(birthdate,'YYYY/MM/DD') birthdate");
			 * query.
			 * append(", gender, mobile_number,address,detail_address,post_number,email,last_school,img_extend"
			 * ); query.append(",d.dept_name,p.POSITION");
			 * query.append("from emp, DEPT d, POSITION p");
			 * query.append("where emp.dept_code=d.dept_code(+)");
			 * query.append("and emp.POSITION_CODE=p.POSITION_CODE(+)");
			 * query.append("AND emp.emp_code=?"); query.append("ORDER BY E.EMP_CODE");
			 */
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			rs = pstmt.executeQuery();
			EmpTO emp = new EmpTO();
			if (rs.next()) {
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
			}

			if (logger.isDebugEnabled()) {
				logger.debug(" selectEmployee 종료 ");
			}
			return emp;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	
	@Override
	public void updateEmployee(EmpTO emp) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateEmployee 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("update emp set ");
	        query.append("EMP_NAME = ?, BIRTHDATE = to_date(?,'YYYY/MM/DD'),GENDER= ?, MOBILE_NUMBER=?,  ");
	        query.append("ADDRESS = ?, DETAIL_ADDRESS = ?, POST_NUMBER = ?, EMAIL= ?, LAST_SCHOOL=?, IMG_EXTEND=?, ");
	        query.append(" position_code = (select position_code from position where position.position=?), ");
	        query.append(" dept_code = (select dept_code from dept where dept.dept_name = ?) where emp_code = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getBirthdate());
			pstmt.setString(3, emp.getGender());
			pstmt.setString(4, emp.getMobileNumber());
			pstmt.setString(5, emp.getAddress());
			pstmt.setString(6, emp.getDetailAddress());
			pstmt.setString(7, emp.getPostNumber());
			pstmt.setString(8, emp.getEmail());
			pstmt.setString(9, emp.getLastSchool());
			pstmt.setString(10, emp.getImgExtend());
			pstmt.setString(11, emp.getPosition());
			pstmt.setString(12, emp.getDeptName());
			pstmt.setString(13, emp.getEmpCode());
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" updateEmployee 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	@Override
	public void deleteEmployee(EmpTO emp) {
	      if (logger.isDebugEnabled()) {
	         logger.debug(" deleteEmployee 시작 ");
	      }

	      Connection con = null;
	      CallableStatement cstmt = null;      

	      try {
	         con = dataSourceTransactionManager.getConnection();
	         StringBuffer query = new StringBuffer();
	         query.append("{call P_DELETE_EMP(?,?,?)}");
	         cstmt = con.prepareCall(query.toString());
	         cstmt.setString(1, emp.getEmpCode());
	         cstmt.registerOutParameter(2,java.sql.Types.VARCHAR);
	         cstmt.registerOutParameter(3,java.sql.Types.VARCHAR);	         
	         cstmt.execute();
	         
	         System.out.println("★★★★★★★★프로시저에러★★★★★★★★★★"+cstmt.getString(2));
	         System.out.println("★★★★★★★★프로시저에러★★★★★★★★★★"+cstmt.getString(3));
	       
			 
	         if (logger.isDebugEnabled()) {
	            logger.debug(" deleteEmployee 종료 ");
	         }
	       
	      } catch (Exception sqle) {
	         logger.fatal(sqle.getMessage());
	         throw new DataAccessException(sqle.getMessage());
	      } finally {
	         dataSourceTransactionManager.close(cstmt);
	      }
	      
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2
	
	
	
	
}