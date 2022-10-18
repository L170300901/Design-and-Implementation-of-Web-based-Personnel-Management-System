package kr.co.yooooon.hr.salary.dao;

import java.util.ArrayList;

import kr.co.yooooon.hr.salary.to.BaseExtSalTO;

public interface BaseExtSalDAO {
	public ArrayList<BaseExtSalTO> selectBaseExtSalList();
	public void updateBaseExtSal(BaseExtSalTO baseExtSal);
}
