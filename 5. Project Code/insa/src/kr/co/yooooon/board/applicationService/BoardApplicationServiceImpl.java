package kr.co.yooooon.board.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.board.dao.*;
import kr.co.yooooon.board.to.*;
import kr.co.yooooon.hr.emp.applicationService.*;
import kr.co.yooooon.hr.emp.sf.*;


public class BoardApplicationServiceImpl implements BoardApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();

	EmpServiceFacade empServiceFasade = EmpServiceFacadeImpl.getInstance();
	private BoardDAO boardDAO = BoardDAOImpl.getInstance();
	
	private static BoardApplicationService instance;
	private BoardApplicationServiceImpl() {
	}
	public static BoardApplicationService getInstance() {
		if (instance == null)
			instance = new BoardApplicationServiceImpl();
		return instance;
	}

	
	
	@Override
	public ArrayList<BoardTO> findBordList() {
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 시작 ");
		}

		ArrayList<BoardTO> boardList = boardDAO.selectBoardList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 종료 ");
		}
		return boardList;
	}
	@Override
	public void findBoardWrite(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardWrite(board) 시작 ");
		}
		boardDAO.insertBoardWrite(board);
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardWrite(board) 종료 ");
		}		
	}
	@Override
	public ArrayList<BoardTO> boardShow(String boardID) {
		if (logger.isDebugEnabled()) {
			logger.debug(" boardShow 시작 ");
		}

		ArrayList<BoardTO> boardShow = boardDAO.boardShow(boardID);

		if (logger.isDebugEnabled()) {
			logger.debug(" boardShow 종료 ");
		}
		return boardShow;
	}
	@Override
	public void boardHit(String boardID) {
		if (logger.isDebugEnabled()) {
			logger.debug(" boardHit 시작 ");
		}

		boardDAO.boardHit(boardID);

		if (logger.isDebugEnabled()) {
			logger.debug(" boardHit 종료 ");
		}
	}
	@Override
	public void removeBoard(String boardID) {
		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 시작 ");
		}

		boardDAO.removeBoard(boardID);

		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 종료 ");
		}
	}
	@Override
	public void modifyBoard(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBoard 시작 ");
		}

		boardDAO.modifyBoard(board);
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBoard 종료 ");
		}		
	}
	@Override
	public void boardReply(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardReply 시작 ");
		}
		boardDAO.boardReply(board);
		if (logger.isDebugEnabled()) {
			logger.debug(" boardReply 종료 ");
		}		
	}
	
}
