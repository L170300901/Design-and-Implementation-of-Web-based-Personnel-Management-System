package kr.co.yooooon.board.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.board.applicationService.*;
import kr.co.yooooon.board.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp.applicationService.*;

public class BoardServiceFacadeImpl implements BoardServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	BoardApplicationService boardApplicationService = BoardApplicationServiceImpl.getInstance();
	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();
	
	private static BoardServiceFacade instance;
	private BoardServiceFacadeImpl() {		
	}	
	public static BoardServiceFacade getInstance() {
		if (instance == null)
			instance = new BoardServiceFacadeImpl();
		return instance;
	}
	
	@Override
	public ArrayList<BoardTO> findBoardList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BoardTO> boardList = boardApplicationService.findBordList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findBoardList 종료 ");
			}
			return boardList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public void findBoardWrite(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardWrite 시작 ");
		}
		System.out.println("------------------------------");
		dataSourceTransactionManager.beginTransaction();
		System.out.println("0000000000000000000");
		try {
			System.out.println("11111111111111111111111111");
			boardApplicationService.findBoardWrite(board);
			System.out.println("2222222222222222222222");
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardWrite 종료 ");
		}		
	}
	@Override
	public ArrayList<BoardTO> boardShow(String boardID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardShow 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<BoardTO> boardShow = boardApplicationService.boardShow(boardID);
			if (logger.isDebugEnabled()) {
				logger.debug(" boardShow 종료 ");
			}
			return boardShow;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	
	@Override
	public void removeBoard(String boardID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			boardApplicationService.removeBoard(boardID);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
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

		dataSourceTransactionManager.beginTransaction();
		try {
			boardApplicationService.modifyBoard(board);
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBoard 종료 ");
		}		
	}
	@Override
	public void boardHit(String boardID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardHit 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			//boardApplicationService.findBoardWrite(board);
			boardApplicationService.boardHit(boardID);
			if (logger.isDebugEnabled()) {
				logger.debug(" boardHit 종료 ");
			}
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
	}
	@Override
	public void boardReply(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardReply 시작 ");
		}
		System.out.println("------------------------------");
		dataSourceTransactionManager.beginTransaction();
		System.out.println("0000000000000000000");
		try {
			System.out.println("11111111111111111111111111");
			boardApplicationService.boardReply(board);
			System.out.println("2222222222222222222222");
			dataSourceTransactionManager.commitTransaction();
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug(" boardReply 종료 ");
		}		
	}
	
}