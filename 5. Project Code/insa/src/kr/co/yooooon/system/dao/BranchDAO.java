package kr.co.yooooon.system.dao;

import java.util.*;

import kr.co.yooooon.system.to.*;

public interface BranchDAO {
	public ArrayList<BranchTO> branchList();
	public ArrayList<BranchBasicRegistTO> branchBasicRegist(String code);

}
