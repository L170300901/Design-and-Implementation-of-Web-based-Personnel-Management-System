package kr.co.yooooon.hr.salary.dao;

import java.util.ArrayList;

import kr.co.yooooon.hr.salary.to.BaseDeductionTO;

public interface BaseDeductionDAO {
	public ArrayList<BaseDeductionTO> selectBaseDeductionList();
	public void updateBaseDeduction(BaseDeductionTO baseDeduction);
	public void insertBaseDeduction(BaseDeductionTO baseDeduction);
	public void deleteBaseDeduction(BaseDeductionTO baseDeduction);
}
