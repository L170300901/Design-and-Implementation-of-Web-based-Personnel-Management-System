package kr.co.yooooon.hr.salary.to;

import kr.co.yooooon.base.to.BaseTO;

public class BaseDeductionTO extends BaseTO{
	private String deductionCode,
	deductionName,
	ratio;

	public String getDeductionCode() {
		return deductionCode;
	}

	public void setDeductionCode(String deductionCode) {
		this.deductionCode = deductionCode;
	}

	public String getDeductionName() {
		return deductionName;
	}

	public void setDeductionName(String deductionName) {
		this.deductionName = deductionName;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

}
