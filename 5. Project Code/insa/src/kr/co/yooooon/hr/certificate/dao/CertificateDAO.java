package kr.co.yooooon.hr.certificate.dao;

import java.util.*;

import kr.co.yooooon.hr.certificate.to.*;

public interface CertificateDAO {
	
	public void insertCertificateRequest(CertificateTO certificate);
	public ArrayList<CertificateTO>selectCertificateList(String empCode, String startDate, String endDate);
	public void deleteCertificate(CertificateTO certificate);
	public ArrayList<CertificateTO> selectCertificateListByAllDept();
	public ArrayList<CertificateTO> selectCertificateListByDept(String deptName, String startDate, String endDate);
	public void updateCertificate(CertificateTO certificate);
	
}
