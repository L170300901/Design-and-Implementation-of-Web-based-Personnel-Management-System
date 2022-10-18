package kr.co.yooooon.system.dao;

import java.util.*;

import kr.co.yooooon.system.to.*;

public interface CompanyDAO {
	public ArrayList<CompanyTO> companyList();
	public ArrayList<CompanyBasicRegistTO> companyBasicRegist(String code);
}
