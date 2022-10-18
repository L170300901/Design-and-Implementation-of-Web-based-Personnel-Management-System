package kr.co.yooooon.system.to;

import kr.co.yooooon.base.to.*;

public class Dept_newTO extends BaseTO{
	public String deptCode, deptName, branchCode, branchName,bumunCode,bumunName,deptPeriodA,deptPeriodB;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBumunCode() {
		return bumunCode;
	}

	public void setBumunCode(String bumunCode) {
		this.bumunCode = bumunCode;
	}

	public String getBumunName() {
		return bumunName;
	}

	public void setBumunName(String bumunName) {
		this.bumunName = bumunName;
	}

	public String getDeptPeriodA() {
		return deptPeriodA;
	}

	public void setDeptPeriodA(String deptPeriodA) {
		this.deptPeriodA = deptPeriodA;
	}

	public String getDeptPeriodB() {
		return deptPeriodB;
	}

	public void setDeptPeriodB(String deptPeriodB) {
		this.deptPeriodB = deptPeriodB;
	}

}
