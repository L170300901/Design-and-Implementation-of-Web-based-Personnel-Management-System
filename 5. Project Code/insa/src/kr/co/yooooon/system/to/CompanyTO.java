package kr.co.yooooon.system.to;

import kr.co.yooooon.base.to.*;

public class CompanyTO extends BaseTO{
	public String companyCode, companyName,companySort;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanySort() {
		return companySort;
	}

	public void setCompanySort(String companySort) {
		this.companySort = companySort;
	}
	
}
