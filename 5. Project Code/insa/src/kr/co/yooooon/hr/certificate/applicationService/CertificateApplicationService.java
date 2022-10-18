package kr.co.yooooon.hr.certificate.applicationService;

import java.util.*;

import kr.co.yooooon.hr.certificate.to.*;

public interface CertificateApplicationService {
	public void registRequest(CertificateTO certificate);
	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate,String endDate);
	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList);
	public ArrayList<CertificateTO> findCertificateListByDept();
	public void modifyCertificateList(ArrayList<CertificateTO> certificateList);
}
