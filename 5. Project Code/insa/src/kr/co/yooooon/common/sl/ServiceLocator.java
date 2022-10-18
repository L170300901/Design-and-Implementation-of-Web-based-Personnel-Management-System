package kr.co.yooooon.common.sl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*JNDI(Java Naming and Directory Interface)란 외부에서 객체를 가져올 때 쓰는 기술.
 * Tomcat과 같은 WAS에서 설정을 통해 데이터소스(커넥션 풀 - 미리 db에 연결해놓은 커넥션을 pool에 저장해두고 가져다씀)
 * 을 만들어놓으면, 이걸 갖다써야함. 
 * WAS 내부에 접근할 때 디렉토리를 기반으로 접근하는 JNDI를 활용*/



public class ServiceLocator {
	private Map<String,DataSource> cache;
	private Context envCtx; //=new InitialContext()
	private static ServiceLocator instance;

	static {
		try {
			instance = new ServiceLocator();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}

	private ServiceLocator() {
		try {
			envCtx = new InitialContext(); //JNDI 서비스에서 객체를 찾아오는 녀석.
			cache = Collections.synchronizedMap(new HashMap<String,DataSource>());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}

	public static ServiceLocator getInstance() {
		return instance;
	}

	public DataSource getDataSource(String jndiName) throws ServiceLocatorException {
		DataSource dataSource;
		try {
			if (cache.containsKey(jndiName)) {
				dataSource=cache.get(jndiName);
			} else {
				dataSource = (DataSource)envCtx.lookup("java:comp/env/"+jndiName); //찾는다
				cache.put(jndiName, dataSource);
			}
		} catch (NamingException e) {
			throw new ServiceLocatorException(e.getMessage());
		}
		return dataSource; 
	}
}




