package kr.co.yooooon.hr.salary.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.to.*;
import kr.co.yooooon.hr.salary.dao.*;
import kr.co.yooooon.hr.salary.to.*;

public class SalaryApplicationServiceImpl implements SalaryApplicationService{
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private BaseSalaryDAO baseSalaryDAO = BaseSalaryDAOImpl.getInstance();
	private BaseDeductionDAO baseDeductionDAO = BaseDeductionDAOImpl.getInstance();
	private BaseExtSalDAO baseExtSalDAO = BaseExtSalDAOImpl.getInstance();
	private MonthSalaryDAO monthSalaryDAO = MonthSalaryDAOImpl.getInstance();
	private MonthDeductionDAO monthDeductionDAO = MonthDeductionDAOImpl.getInstance();
	private MonthExtSalDAO monthExtSalDAO = MonthExtSalDAOImpl.getInstance();
	private SocialInsureDAO SocialInsureDAO = SocialInsureDAOImpl.getInstance();
	
	private PymntDditmDAO pymntDditmDAO = PymntDditmDAOImpl.getInstance();
	
	private FullTimeSalaryDAO fullTimeSalaryDAO = FullTimeSalaryDAOImpl.getInstance();
	
	
	private static SalaryApplicationService instance;
	private SalaryApplicationServiceImpl(){}
	public static SalaryApplicationService getInstance(){
		if(instance==null) instance=new SalaryApplicationServiceImpl();
		return instance;
	}

	@Override
	public ArrayList<BaseSalaryTO> findBaseSalaryList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseSalaryList 시작 ");
		}

		ArrayList<BaseSalaryTO> baseSalaryList = baseSalaryDAO.selectBaseSalaryList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseSalaryList 종료 ");
		}

		return baseSalaryList;
	}
	
	@Override
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseSalaryList 시작 ");
		}

		for(BaseSalaryTO baseSalary : baseSalaryList){
			if(baseSalary.getStatus().equals("update"))
				baseSalaryDAO.updateBaseSalary(baseSalary);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseSalaryList 종료 ");
		}		
	}
	
	@Override
	public ArrayList<BaseDeductionTO> findBaseDeductionList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseDeductionList 시작 ");
		}

		ArrayList<BaseDeductionTO> baseDeductionList = baseDeductionDAO.selectBaseDeductionList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseDeductionList 종료 ");
		}
		return baseDeductionList;
	}
	
	@Override
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" batchBaseDeductionProcess 시작 ");
		}
		for(BaseDeductionTO baseDeduction : baseDeductionList){
			switch(baseDeduction.getStatus()){
				case "update" :
					baseDeductionDAO.updateBaseDeduction(baseDeduction);
					break;
				
				case "insert" :
					baseDeductionDAO.insertBaseDeduction(baseDeduction);
					break;
				
				case "delete" :
					baseDeductionDAO.deleteBaseDeduction(baseDeduction);
					break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" batchBaseDeductionProcess 종료 ");
		}		
	}
	@Override
	public ArrayList<BaseExtSalTO> findBaseExtSalList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseExtSalList 시작 ");
		}

		ArrayList<BaseExtSalTO> baseExtSalList = baseExtSalDAO.selectBaseExtSalList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseExtSalList 종료 ");
		}
		return baseExtSalList;
	}
	
	@Override
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseSalaryList 시작 ");
		}

		for(BaseExtSalTO baseExtSal : baseExtSalList){
			if(baseExtSal.getStatus().equals("update"))
				baseExtSalDAO.updateBaseExtSal(baseExtSal);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBaseSalaryList 종료 ");
		}		
		
	}

	@Override
	public MonthSalaryTO findMonthSalary(String applyYearMonth, String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findMonthSalary 시작 ");
		}

		HashMap<String, Object> resultMap = monthSalaryDAO.batchMonthSalaryProcess(applyYearMonth, empCode);
		ResultTO resultTO = (ResultTO) resultMap.get("resultTO");
		if(Integer.parseInt(resultTO.getErrorCode()) < 0){
			throw new DataAccessException(resultTO.getErrorMsg());
		}
		MonthSalaryTO monthSalary = (MonthSalaryTO) resultMap.get("monthSalary");
		monthSalary.setMonthDeductionList(monthDeductionDAO.selectMonthDeductionList(applyYearMonth, empCode));
		monthSalary.setMonthExtSalList(monthExtSalDAO.selectMonthExtSalList(applyYearMonth, empCode));

		if (logger.isDebugEnabled()) {
			logger.debug(" findMonthSalary 종료 ");
		}
		return monthSalary;
	}

	@Override
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findYearSalary 시작 ");
		}

		ArrayList<MonthSalaryTO> yearSalary = monthSalaryDAO.selectYearSalary(applyYear, empCode);

		if (logger.isDebugEnabled()) {
			logger.debug(" findYearSalary 종료 ");
		}

		return yearSalary;
	}
	
	@Override
	public void modifyMonthSalary(MonthSalaryTO monthSalary) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		monthSalaryDAO.updateMonthSalary(monthSalary);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
	}
	@Override
	public ArrayList<SocialInsureTO> findBaseInsureList(String yearBox) {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseInsureList 시작 ");
		}

		ArrayList<SocialInsureTO> BaseInsureList = SocialInsureDAO.selectBaseInsureList(yearBox);

		if (logger.isDebugEnabled()) {
			logger.debug(" findBaseInsureList 종료 ");
		}
		return BaseInsureList;
	}
	
	@Override
	public void updateInsureData(ArrayList<SocialInsureTO> baseInsureList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" updateInsureData 시작 ");
		}

		for(SocialInsureTO baseInsure : baseInsureList){
				SocialInsureDAO.updateInsureData(baseInsure);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" updateInsureData 종료 ");
		}		
	}
	
	@Override
	public void insertInsureData(ArrayList<SocialInsureTO> baseInsureList) {
		if (logger.isDebugEnabled()) {
			logger.debug(" insertInsureData 시작 ");
		}

		for(SocialInsureTO baseInsure : baseInsureList){
				SocialInsureDAO.insertInsureData(baseInsure);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(" insertInsureData 종료 ");
		}		
	}
	@Override
	public ArrayList<basePymntItmNameCodeTO> findPymntDditmList(pymntDditmTO pymntData) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		ArrayList<basePymntItmNameCodeTO> pymntDditmList = pymntDditmDAO.findPymntDditmList(pymntData);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
		return pymntDditmList;
	}
	
	@Override
	public ArrayList<paymentItmOptionRsTO> setPaymentItmNodeOption(paymentItmOptionTO paymntOtionTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		ArrayList<paymentItmOptionRsTO> paymntOtionRs = pymntDditmDAO.setPaymentItmNodeOption(paymntOtionTO);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
		return paymntOtionRs;
	}
	
	@Override
	public ArrayList<jobCodeRsTO> setJobCode(jobCodeTO priorityCode){
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		ArrayList<jobCodeRsTO> jobCodeRs = pymntDditmDAO.setJobCode(priorityCode);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
		return jobCodeRs;
	}
	
	@Override
	public ArrayList<dissectionRsTO> setDissection(dissectionTO dissectionData){
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		ArrayList<dissectionRsTO> jobCodeRs = pymntDditmDAO.setDissection(dissectionData);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
		return jobCodeRs;
	}
	
	@Override
	public ArrayList<amnclFrmlRsTO> setAmnclFrml(amnclFrmlTO AFTTO){
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 시작 ");
		}

		ArrayList<amnclFrmlRsTO> AFRTOList = pymntDditmDAO.setAmnclFrml(AFTTO);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyMonthSalary 종료 ");
		}		
		return AFRTOList;
	}

	@Override
	public ArrayList<FullTimeSalTO> selectFullTimeSalary(String applyYear, String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 시작 ");
		}
		
		ArrayList<FullTimeSalTO> FullTimeSalaryList = fullTimeSalaryDAO.selectFullTimeSalary(applyYear, empCode);

		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 종료 ");
		}

		return FullTimeSalaryList;
	}
	
	@Override
	public ArrayList<FullTimeSalTO> findAllMoney(String empCode) {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 시작 ");
		}
		
		ArrayList<FullTimeSalTO> FullTimeSalaryList = fullTimeSalaryDAO.findAllMoney(empCode);

		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 종료 ");
		}

		return FullTimeSalaryList;
	}
	
	@Override
	public void modifyFullTimeSalary(List<FullTimeSalTO> fullTimeSalary) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyFullTimeSalary 시작 ");
		}

		fullTimeSalaryDAO.updateFullTimeSalary(fullTimeSalary);

		if (logger.isDebugEnabled()) {
			logger.debug(" modifyFullTimeSalary 종료 ");
		}		
	}
	
	@Override
	public ArrayList<PayDayTO> selectPayDayList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 시작 ");
		}
		
		ArrayList<PayDayTO> selectPayDayList = fullTimeSalaryDAO.selectPayDayList();

		if (logger.isDebugEnabled()) {
			logger.debug(" selectFullTimeSalary 종료 ");
		}

		return selectPayDayList;
	}
}
