package kr.co.yooooon.hr.emp.to;

import kr.co.yooooon.base.to.BaseTO;

public class TempAppointmentTO extends BaseTO{

	String appointmentNo, 
	empCode,
	appointmentHistory,
	currentInformation,
	preAppointmentInformation;

	public String getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(String appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getAppointmentHistory() {
		return appointmentHistory;
	}

	public void setAppointmentHistory(String appointmentHistory) {
		this.appointmentHistory = appointmentHistory;
	}

	public String getCurrentInformation() {
		return currentInformation;
	}

	public void setCurrentInformation(String currentInformation) {
		this.currentInformation = currentInformation;
	}

	public String getPreAppointmentInformation() {
		return preAppointmentInformation;
	}

	public void setPreAppointmentInformation(String preAppointmentInformation) {
		this.preAppointmentInformation = preAppointmentInformation;
	}

}
