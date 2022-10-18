package kr.co.yooooon.base.dao;

import java.util.*;

import kr.co.yooooon.base.to.*;

public interface PayCheckDAO {
	public ArrayList<PayDateTO> payDateList();
	public void createHobongList(String startDate);
	public ArrayList<HobongTO> HobongList(String startDate);
	public ArrayList<HobongTO> hobongPositionList(String startDate);
	public ArrayList<HobongTO> findHobongTable(String positionCode, String positionName, String startDate);
	public void payCheckResist(String increaseAmount,String initialValue,String startDate,String positionCode);
}
