package kr.co.yooooon.hr.salary.to;

import kr.co.yooooon.base.to.BaseTO;

public class BaseSalaryTO extends BaseTO {
	private String positionCode, position, baseSalary,	hobongRatio;
	
	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(String baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getHobongRatio() {
		return hobongRatio;
	}

	public void setHobongRatio(String hobongRatio) {
		this.hobongRatio = hobongRatio;
	}

	

	
}
