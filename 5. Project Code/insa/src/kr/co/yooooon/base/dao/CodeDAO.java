package kr.co.yooooon.base.dao;

import java.util.*;

import kr.co.yooooon.base.to.*;

public interface CodeDAO {
	   public ArrayList<CodeTO> selectCode();
	   public void updateCodeList(DetailCodeTO Code);
	   public void insertCodeList(DetailCodeTO Code);
	   public void deleteCodeList(DetailCodeTO Code);
	   public void saveCodeList(List<DetailCodeTO> saveCodeList);
		
}
