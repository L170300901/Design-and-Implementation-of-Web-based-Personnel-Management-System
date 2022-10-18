package kr.co.yooooon.common.transaction;

import java.sql.*;

import javax.sql.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.sl.*;

public class DataSourceTransactionManager {
	private static DataSource dataSource;
	private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	// ThreadLocal=을 이용하면 쓰레드 영역에 변수를 설정할 수 있다. 
	// 특정 쓰레드가 실행하는 모든 코드(jvm이 알아서)에서 그 쓰레드에 설정된 변수 값을 사용할 수 있다.
	// 트렌젝션마다 쓰레드를 분배.  
	static {
		dataSource = ServiceLocator.getInstance().getDataSource("jdbc/insa"); //insa db에 연결!!
	}

	
	private static DataSourceTransactionManager instance;
	
	private DataSourceTransactionManager() {
	}
	
	public static DataSourceTransactionManager getInstance() {
		if (instance == null)
			instance = new DataSourceTransactionManager();
		return instance;
	}

	
	public void setDataSource(DataSource dataSource) {
		DataSourceTransactionManager.dataSource = dataSource;
	}


	public Connection getConnection() {
		Connection connection = (Connection) threadLocal.get(); //현재 쓰레드의 로컬 변수 값을 읽어온다 
		//System.out.println("getConnection");
		try {
			if (connection == null) {
				connection = dataSource.getConnection();
				threadLocal.set(connection);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
		return connection;
	}


	public void closeConnection() {
		Connection conn = (Connection) threadLocal.get();
		threadLocal.set(null);
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void beginTransaction() {
		//System.out.println("beginTransaction");
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void rollbackTransaction() {
		//System.out.println("rollbackTransaction");
		try {
			getConnection().rollback();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void commitTransaction() {
		//System.out.println("commitTransaction");
		try {
			getConnection().commit();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void close(PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void close(Connection conn) {
		closeConnection();
	}

	
	public void close(Connection conn, PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void close(Connection conn, Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			closeConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	
	public void beginReadOnlyTransaction() {
		try {
			getConnection().setReadOnly(true);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}
}
