package kr.co.yooooon.tae.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;


import kr.co.yooooon.tae.dao.DataAccessException;
import kr.co.yooooon.tae.sl.ServiceLocator;


public class MemberDAO 
{
	private static MemberDAO instance;
	

	private MemberDAO(){}
	public static MemberDAO getInstance(){
		if(instance==null)
			instance=new MemberDAO();
		return instance;
	}
	
	public MemberBean getUserInfo(String name) 
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		System.out.println(name);
		try {
			System.out.println("00000000000000");
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM EMP WHERE emp_name=?");
			System.out.println("00000000000001");
			DataSource dataSource=ServiceLocator.getInstance().getDataSource("jdbc/insa");
			System.out.println("00000000000002");
			conn = dataSource.getConnection();
			System.out.println("00000000000003");
			pstmt = conn.prepareStatement(query.toString());
			System.out.println("00000000000004");
			pstmt.setString(1, name);
			System.out.println("11111111111111");
			rs = pstmt.executeQuery();
			System.out.println("2222222222222");
			MemberBean emp = null;
			System.out.println("33333333333333");
			if (rs.next()) {
				
				emp = new MemberBean();
				
				emp.setEmpCode(rs.getString("emp_code"));
			
				
				emp.setGender(rs.getString("gender"));
				
				emp.setMobileNumber(rs.getString("mobile_number"));
				
				emp.setEmpCode(rs.getString("emp_code"));
				
				emp.setAddress(rs.getString("address"));
				
				emp.setDetailAddress(rs.getString("detail_address"));
				
				//emp.setBirthdate(rs.getString("birthdate"));
				
				emp.setPostNumber(rs.getString("post_number"));
				
				emp.setImgExtend(rs.getString("img_extend"));
				
				emp.setLastSchool(rs.getString("last_school"));
				
				emp.setEmail(rs.getString("email"));
			
			}
		
			return emp;

		} catch(Exception sqle) {
			System.out.println("여기는 오류문 입니다 %%%%%%%%%%%%%%%%%%%%%%%%%");
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			try{
				if(rs!=null){rs.close(); rs=null; }
				if(pstmt!=null){pstmt.close(); pstmt=null; }
				if(conn!=null){conn.close(); conn=null; }
			}catch(Exception e){}		
		}
	}	
}