package kr.co.yooooon.board.dao;

import java.util.*;

import kr.co.yooooon.board.to.*;

public interface BoardDAO {
	public ArrayList<BoardTO> selectBoardList();
	public void insertBoardWrite(BoardTO board);
	public ArrayList<BoardTO> boardShow(String boardID);
	public void boardHit(String boardID);
	public void removeBoard(String boardID);
	public void modifyBoard(BoardTO board);
	public void boardReply(BoardTO board);
}

