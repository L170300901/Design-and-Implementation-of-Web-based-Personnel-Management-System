package kr.co.yooooon.board.applicationService;

import java.util.*;

import kr.co.yooooon.board.to.*;

public interface BoardApplicationService {
	
	public ArrayList<BoardTO> findBordList();
	public void findBoardWrite(BoardTO board);
	public ArrayList<BoardTO> boardShow(String boardID);
	public void boardHit(String boardID);
	public void removeBoard(String boardID);
	public void modifyBoard(BoardTO board);
	public void boardReply(BoardTO board);
}

