package kr.co.yooooon.board.sf;

import java.util.*;

import kr.co.yooooon.board.to.*;

public interface BoardServiceFacade {
	
	public ArrayList<BoardTO> findBoardList();
	public void findBoardWrite(BoardTO board);
	public ArrayList<BoardTO> boardShow(String boardID);
	public void boardHit(String boardID);
	public void removeBoard(String boardID);
	public void modifyBoard(BoardTO board);
	public void boardReply(BoardTO board);
}
