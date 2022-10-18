package kr.co.yooooon.hr.emp.dao;

import java.util.ArrayList;

import kr.co.yooooon.hr.emp.to.LicenseInfoTO;

public interface LicenseInfoDAO {
	public ArrayList<LicenseInfoTO> selectLicenseList(String code);

	public void insertLicenseInfo(LicenseInfoTO licenscInfo);
	public void updateLicenseInfo(LicenseInfoTO licenscInfo);
	public void deleteLicenseInfo(LicenseInfoTO licenscInfo);
}
