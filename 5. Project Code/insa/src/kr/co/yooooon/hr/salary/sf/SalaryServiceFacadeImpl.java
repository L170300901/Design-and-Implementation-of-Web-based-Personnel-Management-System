package kr.co.yooooon.hr.salary.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.salary.applicationService.*;
import kr.co.yooooon.hr.salary.to.*;                                            

public class SalaryServiceFacadeImpl implements SalaryServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	private SalaryApplicationService salaryApplicationService = SalaryApplicationServiceImpl.getInstance();

	private static SalaryServiceFacade instance;
	private SalaryServiceFacadeImpl(){}
	public static SalaryServiceFacade getInstance(){
		if(instance==null) instance=new SalaryServiceFacadeImpl();
		return instance;
	}
	
	@Override
	public ArrayList<BaseSalaryTO> findBaseSalaryList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseSalaryList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BaseSalaryTO> baseSalaryList=salaryApplicationService.findBaseSalaryList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseSalaryList 종료 ");
			}
			return baseSalaryList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseSalaryList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.modifyBaseSalaryList(baseSalaryList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" modifyBaseSalaryList 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public ArrayList<BaseDeductionTO> findBaseDeductionList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseDeductionList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BaseDeductionTO> baseDeductionList=salaryApplicationService.findBaseDeductionList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseDeductionList 종료 ");
			}
			return baseDeductionList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" batchBaseDeductionProcess 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.batchBaseDeductionProcess(baseDeductionList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" batchBaseDeductionProcess 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public ArrayList<BaseExtSalTO> findBaseExtSalList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseExtSalList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BaseExtSalTO> baseExtSalList=salaryApplicationService.findBaseExtSalList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseExtSalList 종료 ");
			}
			return baseExtSalList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseExtSalList 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.modifyBaseExtSalList(baseExtSalList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" modifyBaseExtSalList 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
		
	}

	@Override
	public MonthSalaryTO findMonthSalary(String ApplyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findMonthSalary 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			MonthSalaryTO monthSalary=salaryApplicationService.findMonthSalary(ApplyYearMonth, empCode);
			if (logger.isDebugEnabled()) {
				logger.debug(" findMonthSalary 종료 ");
			}
			return monthSalary;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}

	@Override
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findYearSalary 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<MonthSalaryTO> monthSalary=salaryApplicationService.findYearSalary(applyYear, empCode);
			if (logger.isDebugEnabled()) {
				logger.debug(" findYearSalary 종료 ");
			}
			return monthSalary;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void modifyMonthSalary(MonthSalaryTO monthSalary) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.modifyMonthSalary(monthSalary);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" modifyMonthSalary 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public ArrayList<SocialInsureTO> findBaseInsureList(String yearBox) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseDeductionList 시작 ");
		}
		
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<SocialInsureTO> baseInsureList=salaryApplicationService.findBaseInsureList(yearBox);
			
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseDeductionList 종료 ");
			}
			
			return baseInsureList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void updateInsureData(ArrayList<SocialInsureTO> baseInsureList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" updateInsureData 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.updateInsureData(baseInsureList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" updateInsureData 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public void insertInsureData(ArrayList<SocialInsureTO> baseInsureList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertInsureData 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.insertInsureData(baseInsureList);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" insertInsureData 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	@Override
	public ArrayList<basePymntItmNameCodeTO> findPymntDditmList(pymntDditmTO pymntData) {
	// TODO Auto-generated method stub
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseExtSalList 시작 ");
			}
			dataSourceTransactionManager.beginTransaction();
			try {
				ArrayList<basePymntItmNameCodeTO> pymntDditmList=salaryApplicationService.findPymntDditmList(pymntData);
				if (logger.isDebugEnabled()) {
					logger.debug(" findBaseExtSalList 종료 ");
				}
				return pymntDditmList;
			} catch (DataAccessException e){
				dataSourceTransactionManager.rollbackTransaction();
				logger.fatal(e.getMessage());
				throw e;
			} finally {
				dataSourceTransactionManager.closeConnection();
			}
		}
	
	@Override
	public ArrayList<paymentItmOptionRsTO> setPaymentItmNodeOption(paymentItmOptionTO paymntOtionTO) {
	// TODO Auto-generated method stub
			if (logger.isDebugEnabled()) {
				logger.debug(" findBaseExtSalList 시작 ");
			}
			dataSourceTransactionManager.beginTransaction();
			try {
				ArrayList<paymentItmOptionRsTO> paymentItmOptionRs=salaryApplicationService.setPaymentItmNodeOption(paymntOtionTO);
				if (logger.isDebugEnabled()) {
					logger.debug(" findBaseExtSalList 종료 ");
				}
				return paymentItmOptionRs;
			} catch (DataAccessException e){
				dataSourceTransactionManager.rollbackTransaction();
				logger.fatal(e.getMessage());
				throw e;
			} finally {
				dataSourceTransactionManager.closeConnection();
			}
		}
	
	@Override
	public ArrayList<jobCodeRsTO> setJobCode(jobCodeTO priorityCode) {
		// TODO Auto-generated method stub
				if (logger.isDebugEnabled()) {
					logger.debug(" findBaseExtSalList 시작 ");
				}
				dataSourceTransactionManager.beginTransaction();
				try {
					ArrayList<jobCodeRsTO> jobCodeRs=salaryApplicationService.setJobCode(priorityCode);
					if (logger.isDebugEnabled()) {
						logger.debug(" findBaseExtSalList 종료 ");
					}
					return jobCodeRs;
				} catch (DataAccessException e){
					dataSourceTransactionManager.rollbackTransaction();
					logger.fatal(e.getMessage());
					throw e;
				} finally {
					dataSourceTransactionManager.closeConnection();
				}
			}
	
	@Override
	public ArrayList<dissectionRsTO> setDissection(dissectionTO dissectionData) {
		// TODO Auto-generated method stub
				if (logger.isDebugEnabled()) {
					logger.debug(" findBaseExtSalList 시작 ");
				}
				dataSourceTransactionManager.beginTransaction();
				try {
					ArrayList<dissectionRsTO> jobCodeRs=salaryApplicationService.setDissection(dissectionData);
					if (logger.isDebugEnabled()) {
						logger.debug(" findBaseExtSalList 종료 ");
					}
					return jobCodeRs;
				} catch (DataAccessException e){
					dataSourceTransactionManager.rollbackTransaction();
					logger.fatal(e.getMessage());
					throw e;
				} finally {
					dataSourceTransactionManager.closeConnection();
				}
			}
	
	@Override
	public ArrayList<amnclFrmlRsTO> setAmnclFrml(amnclFrmlTO AFTTO) {
		// TODO Auto-generated method stub
				if (logger.isDebugEnabled()) {
					logger.debug(" findBaseExtSalList 시작 ");
				}
				dataSourceTransactionManager.beginTransaction();
				try {
					ArrayList<amnclFrmlRsTO> AFRTOList=salaryApplicationService.setAmnclFrml(AFTTO);
					if (logger.isDebugEnabled()) {
						logger.debug(" findBaseExtSalList 종료 ");
					}
					return AFRTOList;
				} catch (DataAccessException e){
					dataSourceTransactionManager.rollbackTransaction();
					logger.fatal(e.getMessage());
					throw e;
				} finally {
					dataSourceTransactionManager.closeConnection();
				}
			}
	@Override
	public ArrayList<FullTimeSalTO> findselectSalary(String applyYearMonth, String empCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) { logger.debug(" findselectSalary 시작 "); }
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<FullTimeSalTO> FullTimeSalaryList=salaryApplicationService.selectFullTimeSalary(applyYearMonth, empCode);
			if (logger.isDebugEnabled()) { logger.debug(" findselectSalary 종료 "); }
			return FullTimeSalaryList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	public ArrayList<FullTimeSalTO> findAllMoney(String empCode){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) { logger.debug(" findAllMoney 시작 "); }
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<FullTimeSalTO> findAllMoneyList=salaryApplicationService.findAllMoney(empCode);
			if (logger.isDebugEnabled()) { logger.debug(" findAllMoney 종료 "); }
			return findAllMoneyList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void modifyFullTimeSalary(List<FullTimeSalTO> fullTimeSalary) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}
		dataSourceTransactionManager.beginTransaction();
		try {
			salaryApplicationService.modifyFullTimeSalary(fullTimeSalary);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" modifyMonthSalary 종료 ");
			}
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	
	@Override
	public ArrayList<PayDayTO> findPayDayList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) { logger.debug(" findselectSalary 시작 "); }
		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<PayDayTO> PayDayList=salaryApplicationService.selectPayDayList();
			if (logger.isDebugEnabled()) { logger.debug(" findselectSalary 종료 "); }
			return PayDayList;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
}
