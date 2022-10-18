package kr.co.yooooon.hr.certificate.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.certificate.applicationService.*;
import kr.co.yooooon.hr.certificate.to.*;

public class CertificateServiceFacadeImpl implements CertificateServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	private CertificateApplicationService certificateApplicationService=CertificateApplicatonServiceImpl.getInstance();
	
	//싱글톤패턴
	private static CertificateServiceFacade instance;
	private CertificateServiceFacadeImpl(){}
	public static CertificateServiceFacade getInstance(){
		if(instance==null) instance=new CertificateServiceFacadeImpl();
		return instance;
	}
	
	//	
	public void registRequest(CertificateTO certificate) {
		// TODO Auto-generated method stub
				if (logger.isDebugEnabled()) {
					logger.debug(" ServiceFace에서 registRequest 시작 ");
				}
				dataSourceTransactionManager.beginTransaction();
				try {
					certificateApplicationService.registRequest(certificate);
					dataSourceTransactionManager.commitTransaction();
				} catch (DataAccessException e){
					dataSourceTransactionManager.rollbackTransaction();
					logger.fatal(e.getMessage());
					throw e;
				} finally {
					dataSourceTransactionManager.closeConnection();
				}
				if (logger.isDebugEnabled()) {
					logger.debug(" registDayAttd 종료 ");
				}		
		
	}
	@Override
	public ArrayList<CertificateTO> findCertificateList(String empCode, String startDate, String endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug(" servicefade -findCertificateList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<CertificateTO> certificateList=certificateApplicationService.findCertificateList(empCode, startDate, endDate);
			if (logger.isDebugEnabled()) {
				logger.debug(" findCertificateList 종료 ");
			}
			return certificateList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public void removeCertificateRequest(ArrayList<CertificateTO> certificateList) {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()) {
			logger.debug("sf - removeCertificateRequest 시작");
		}
		dataSourceTransactionManager.beginTransaction();
		
		try {
			certificateApplicationService.removeCertificateRequest(certificateList);
			dataSourceTransactionManager.commitTransaction();
		}catch(DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		}finally {
			dataSourceTransactionManager.closeConnection();
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("sf - removeCertificateRequest 종료");
		}
		
	}
	@Override
	public ArrayList<CertificateTO> findCertificateListByDept() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findCertificateListByDept 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<CertificateTO> certificateList=certificateApplicationService.findCertificateListByDept();
			if (logger.isDebugEnabled()) {
				logger.debug(" findCertificateListByDept 종료 ");
			}
			return certificateList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public void modifyCertificateList(ArrayList<CertificateTO> certificateList) {
		// TODO Auto-generated method stub
		if(logger.isDebugEnabled()) {
			logger.debug("sf - modifyCertificateList 시작");
		}
		dataSourceTransactionManager.beginTransaction();
		
		try {
			certificateApplicationService.modifyCertificateList(certificateList);
			dataSourceTransactionManager.commitTransaction();
		}catch(DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		}finally {
			dataSourceTransactionManager.closeConnection();
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("sf - modifyCertificateList 종료");
		}
		
		
	}

	
}
