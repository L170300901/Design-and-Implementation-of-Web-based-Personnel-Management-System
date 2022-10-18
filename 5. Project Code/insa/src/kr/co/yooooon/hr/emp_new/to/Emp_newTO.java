package kr.co.yooooon.hr.emp_new.to;

import kr.co.yooooon.base.to.*;

public class Emp_newTO extends BaseTO{
	private String empCode,empName,EmpDept,EmpStatus, companyCode;
	private String deptCode, empJoin, empOut;
	private String empPw;
	private String EngLishName;
	

	public String getEngLishName() {
		return EngLishName;
	}

	public void setEngLishName(String enlgishName) {
		EngLishName = enlgishName;
	}

	public String getEmpPw() {
		return empPw;
	}

	public void setEmpPw(String empPw) {
		this.empPw = empPw;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmpJoin() {
		return empJoin;
	}

	public void setEmpJoin(String empJoin) {
		this.empJoin = empJoin;
	}

	public String getEmpOut() {
		return empOut;
	}

	public void setEmpOut(String empOut) {
		this.empOut = empOut;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getEmpStatus() {
		return EmpStatus;
	}

	public void setEmpStatus(String empStatus) {
		EmpStatus = empStatus;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDept() {
		return EmpDept;
	}

	public void setEmpDept(String empDept) {
		EmpDept = empDept;
	}

	
}

