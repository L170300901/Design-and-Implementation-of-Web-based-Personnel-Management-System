package kr.co.yooooon.hr.salary.dao;

import java.util.*;

import kr.co.yooooon.hr.salary.to.SocialInsureTO;

public interface SocialInsureDAO {
	public ArrayList<SocialInsureTO> selectBaseInsureList(String yearBox);
	public void updateInsureData(SocialInsureTO baseInsure);
	public void insertInsureData(SocialInsureTO baseInsure);
}
