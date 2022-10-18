package kr.co.yooooon.base.dao;

import java.util.*;

import kr.co.yooooon.base.to.*;

public interface LoginCodeDAO {
	public ArrayList<CompanyTO> searchCompanyCode();
	public ArrayList<LoginEmpTo> searchEmpCode(String companyCode);

}
