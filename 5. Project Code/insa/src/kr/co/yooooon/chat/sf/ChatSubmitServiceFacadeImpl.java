package kr.co.yooooon.chat.sf;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.chat.applicationService.*;
import kr.co.yooooon.chat.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;
import kr.co.yooooon.hr.emp.applicationService.*;


public class ChatSubmitServiceFacadeImpl implements ChatSubmitServiceFacade{
	protected final Log logger = LogFactory.getLog(this.getClass());

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();
	ChatSubmitApplicationService chatSubmitApplicationService = ChatSubmitApplicationServiceImpl.getInstance();
	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();
	
	private static ChatSubmitServiceFacade instance;
	private ChatSubmitServiceFacadeImpl() {		
	}	
	public static ChatSubmitServiceFacade getInstance() {
		if (instance == null)
			instance = new ChatSubmitServiceFacadeImpl();
		return instance;
	}
	
	@Override
	public void chatSubmit(ChatTO chat) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit시작 ");
		}
		System.out.println("------------------------------");
		dataSourceTransactionManager.beginTransaction();
		try {
			System.out.println("11111111111111111111111111");
			chatSubmitApplicationService.chatSubmit(chat);
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
			logger.debug(" chatSubmit 종료 ");
		}		
	}
	public ArrayList<ChatTO> findChatList(){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<ChatTO> charList = chatSubmitApplicationService.findChatList();
			if (logger.isDebugEnabled()) {
				logger.debug(" findBoardList 종료 ");
			}
			return charList;
		} catch (DataAccessException e) {
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	@Override
	public int chatSubmit(String userID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			int result=chatSubmitApplicationService.chatSubmit(userID);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" chatSubmit 종료 ");
			}
			return result;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
		
	}
	@Override
	public ArrayList<ChatTO> getChatListByRecent(String fromID, String toID, String number) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<ChatTO> getChatListByRecent=chatSubmitApplicationService.getChatListByRecent(fromID, toID, number);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" chatSubmit 종료 ");
			}
			return getChatListByRecent;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}
		
		
	}
	@Override
	public void readChat(String fromID, String toID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" readChat 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			//boardApplicationService.findBoardWrite(board);
			chatSubmitApplicationService.readChat(fromID, toID);
			if (logger.isDebugEnabled()) {
				logger.debug(" readChat 종료 ");
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
	public int chatUnread(String userID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			//logger.debug(" chatSubmit 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			int result=chatSubmitApplicationService.chatUnread(userID);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				//logger.debug(" chatSubmit 종료 ");
			}
			return result;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
	@Override
	public ArrayList<ChatTO> getBox(String userID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" getBox 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			ArrayList<ChatTO> getBox=chatSubmitApplicationService.getBox(userID);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" getBox 종료 ");
			}
			return getBox;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}

	}
	
	@Override
	public int getUnreadChat(String fromID, String toID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" getUnreadChat 시작 ");
		}

		dataSourceTransactionManager.beginTransaction();
		try {
			int result=chatSubmitApplicationService.getUnreadChat(fromID,toID);
			dataSourceTransactionManager.commitTransaction();
			if (logger.isDebugEnabled()) {
				logger.debug(" getUnreadChat 종료 ");
			}
			return result;
		} catch (DataAccessException e){
			dataSourceTransactionManager.rollbackTransaction();
			logger.fatal(e.getMessage());
			throw e;
		} finally {
			dataSourceTransactionManager.closeConnection();
		}		
	}
}
