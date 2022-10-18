package kr.co.yooooon.base.dao;

import java.util.*;

import kr.co.yooooon.base.to.*;

public interface DetailCodeDAO {
	public ArrayList<DetailCodeTO> selectDetailCodeList(String codetype);
	public ArrayList<DetailCodeTO> selectDetailCodeListRest(String code1,String code2, String code3);
	public ArrayList<CodeTO> findoutPutList(String outPut);
	public void updateDetailCode(DetailCodeTO detailCodeto);
	public void registDetailCode(DetailCodeTO detailCodeto);
	public void deleteDetailCode(DetailCodeTO detailCodeto);
	public ArrayList<DetailCodeTO> basicDetailCodelist(String code);
	
}