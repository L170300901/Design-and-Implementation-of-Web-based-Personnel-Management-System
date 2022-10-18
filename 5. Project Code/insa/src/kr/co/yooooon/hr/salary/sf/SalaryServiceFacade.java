package kr.co.yooooon.hr.salary.sf;

import java.util.*;

import kr.co.yooooon.hr.salary.to.*;

public interface SalaryServiceFacade {
	
	public ArrayList<BaseSalaryTO> findBaseSalaryList();
	public void modifyBaseSalaryList(ArrayList<BaseSalaryTO> baseSalaryList);

	public ArrayList<BaseDeductionTO> findBaseDeductionList();
	public void batchBaseDeductionProcess(ArrayList<BaseDeductionTO> baseDeductionList);

	public ArrayList<BaseExtSalTO> findBaseExtSalList();
	public void modifyBaseExtSalList(ArrayList<BaseExtSalTO> baseExtSalList);

	public MonthSalaryTO findMonthSalary(String ApplyYearMonth, String empCode);
	public ArrayList<MonthSalaryTO> findYearSalary(String applyYear, String empCode);
	public void modifyMonthSalary(MonthSalaryTO monthSalary);
	
	public ArrayList<SocialInsureTO> findBaseInsureList(String yearBox);
	public void updateInsureData(ArrayList<SocialInsureTO> baseInsureList);
	public void insertInsureData(ArrayList<SocialInsureTO> baseInsureList);
	
	public ArrayList<basePymntItmNameCodeTO> findPymntDditmList(pymntDditmTO pymntData);
	public ArrayList<paymentItmOptionRsTO> setPaymentItmNodeOption(paymentItmOptionTO paymntOtionTO);
	public ArrayList<jobCodeRsTO> setJobCode(jobCodeTO priorityCode);
	public ArrayList<dissectionRsTO> setDissection(dissectionTO dissectionData);
	public ArrayList<amnclFrmlRsTO> setAmnclFrml(amnclFrmlTO AFTTO);
	
	public ArrayList<FullTimeSalTO> findselectSalary(String ApplyYearMonth, String empCode);
	public ArrayList<FullTimeSalTO> findAllMoney(String empCode);
	public void modifyFullTimeSalary(List<FullTimeSalTO> fullTimeSalary);
	public ArrayList<PayDayTO> findPayDayList();

}
