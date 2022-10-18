package kr.co.yooooon.hr.certificate.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.hr.certificate.dao.*;
import kr.co.yooooon.hr.certificate.to.*;

public class CertificateApplicatonServiceImpl implements CertificateApplicationService{
	protected final Log logger = LogFactory.getLog(this.getClass());
	private CertificateDAO certificateDAO=CertificateDAOImpl.getInstance();
	private static CertificateApplicationService instance;
	private CertificateApplicatonServiceImpl(){}
	public static CertificateApplicationService getInstance(){
		if(instance==null) instance=new CertificateApplicatonServiceImpl();
		return instance;
	}
	
	@Override
	public void registRequest(CertificateTO certificate) {		
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" certificate applicationService - registRequest 시작 ");
		}
		certificateDAO.insertCertificateRequest(certificate);
		if (logger.isDebugEnabled()) {
			logger.debug(" registDayAttd 종료 ");
		}
		 
	}
	@Override
	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate, String endDate) {
		// TODO Auto-generated method stub
				if (logger.isDebugEnabled()) {
					logger.debug(" findCertificateList 시작 ");
				}

				ArrayList<CertificateTO> certificateList=certificateDAO.selectCertificateList(empCode, startDate, endDate);

				if (logger.isDebugEnabled()) {
					logger.debug(" findCertificateList 종료 ");
				}
				return certificateList;
	}
	@Override
	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList) {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()) {
			logger.debug("as - removeCertificateRequest 시작");
		}
		
		for(CertificateTO certificate : certificateList) {
			certificateDAO.deleteCertificate(certificate);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("as - revmoveCertificateRuqest 종료");
		}
	}
	@Override
	public ArrayList<CertificateTO> findCertificateListByDept() {
		ArrayList<CertificateTO> certificateList = null;
		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByDept 시작 ");
		}
		certificateList = certificateDAO.selectCertificateListByAllDept();

		if (logger.isDebugEnabled()) {
			logger.debug(" findRestAttdListByDept 종료 ");
		}
		return certificateList;
	}
	@Override
	public void modifyCertificateList(ArrayList<CertificateTO> certificateList) {
		if(logger.isDebugEnabled()) {
			logger.debug("as - modifyCertificateList 시작");
		}
		
		for(CertificateTO certificate : certificateList) {
			System.out.println(certificate.getApprovalStatus());
			if(certificate.getStatus().equals("update")) {
				certificateDAO.updateCertificate(certificate);
			}
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("as - modifyCertificateList 종료");
		}
		
	}

}
