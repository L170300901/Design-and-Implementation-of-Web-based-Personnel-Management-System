package kr.co.yooooon.hr.salary.dao;

import java.util.*;

import kr.co.yooooon.hr.salary.to.amnclFrmlRsTO;
import kr.co.yooooon.hr.salary.to.amnclFrmlTO;
import kr.co.yooooon.hr.salary.to.basePymntItmNameCodeTO;
import kr.co.yooooon.hr.salary.to.dissectionRsTO;
import kr.co.yooooon.hr.salary.to.dissectionTO;
import kr.co.yooooon.hr.salary.to.jobCodeRsTO;
import kr.co.yooooon.hr.salary.to.jobCodeTO;
import kr.co.yooooon.hr.salary.to.paymentItmOptionRsTO;
import kr.co.yooooon.hr.salary.to.paymentItmOptionTO;
import kr.co.yooooon.hr.salary.to.pymntDditmTO;

public interface PymntDditmDAO {
	public ArrayList<basePymntItmNameCodeTO> findPymntDditmList(pymntDditmTO pymntData);
	public ArrayList<paymentItmOptionRsTO> setPaymentItmNodeOption(paymentItmOptionTO paymntOtionTO);
	public ArrayList<jobCodeRsTO> setJobCode(jobCodeTO priorityCode);
	public ArrayList<dissectionRsTO> setDissection(dissectionTO dissectionData);
	public ArrayList<amnclFrmlRsTO> setAmnclFrml(amnclFrmlTO AFTTO);
}