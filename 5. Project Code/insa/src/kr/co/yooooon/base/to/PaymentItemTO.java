package kr.co.yooooon.base.to;

public class PaymentItemTO extends BaseTO{
	public String salaryClassification,paymentClassification, year,code1;
	public String code1Name;

	public String getCode1Name() {
		return code1Name;
	}

	public void setCode1Name(String code1Name) {
		this.code1Name = code1Name;
	}

	public String getSalaryClassification() {
		return salaryClassification;
	}

	public void setSalaryClassification(String salaryClassification) {
		this.salaryClassification = salaryClassification;
	}

	public String getPaymentClassification() {
		return paymentClassification;
	}

	public void setPaymentClassification(String paymentClassification) {
		this.paymentClassification = paymentClassification;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}
}
