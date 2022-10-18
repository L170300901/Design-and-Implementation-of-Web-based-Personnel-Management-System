package kr.co.yooooon.chat.applicationService;

import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.chat.dao.*;
import kr.co.yooooon.chat.to.*;
import kr.co.yooooon.hr.emp.applicationService.*;
import kr.co.yooooon.hr.emp.sf.*;

public class ChatSubmitApplicationServiceImpl implements ChatSubmitApplicationService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	EmpApplicationService empApplicationService = EmpApplicationServiceImpl.getInstance();

	EmpServiceFacade empServiceFasade = EmpServiceFacadeImpl.getInstance();
	private ChatDAO chatDAO = ChatDAOImpl.getInstance();
	
	private static ChatSubmitApplicationService instance;
	private ChatSubmitApplicationServiceImpl() {
	}
	public static ChatSubmitApplicationService getInstance() {
		if (instance == null)
			instance = new ChatSubmitApplicationServiceImpl();
		return instance;
	}

	
	
	@Override
	public void chatSubmit(ChatTO chat) {
		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 시작 ");
		}

		chatDAO.chatSubmit(chat);

		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 종료 ");
		}
	}
	@Override
	public ArrayList<ChatTO> findChatList(){
		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 시작 ");
		}

		ArrayList<ChatTO> chatList = chatDAO.findChatList();

		if (logger.isDebugEnabled()) {
			logger.debug(" findBoardList 종료 ");
		}
		return chatList;
	}
	@Override
	public int chatSubmit(String userID) {
		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit 시작 ");
		}

		int result= chatDAO.chatSubmit(userID);
		

		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit 종료 ");
		}
		return result;
	}
	@Override
	public ArrayList<ChatTO> getChatListByRecent(String fromID, String toID, String number) {
		if (logger.isDebugEnabled()) {
			logger.debug(" getChatListByRecent 시작 ");
		}

		ArrayList<ChatTO> getChatListByRecent = chatDAO.getChatListByRecent(fromID, toID,number);

		if (logger.isDebugEnabled()) {
			logger.debug(" getChatListByRecent 종료 ");
		}
		return getChatListByRecent;
	}
	@Override
	public void readChat(String fromID, String toID){
		if (logger.isDebugEnabled()) {
			logger.debug(" readChat 시작 ");
		}
		
		chatDAO.readChat(fromID, toID);

		if (logger.isDebugEnabled()) {
			logger.debug(" readChat 종료 ");
		}
	}
	@Override
	public int chatUnread(String userID) {
		if (logger.isDebugEnabled()) {
			//logger.debug(" chatUnread 시작 ");
		}

		int result= chatDAO.chatUnread(userID);
		

		if (logger.isDebugEnabled()) {
			//logger.debug(" chatUnread 종료 ");
		}
		return result;
	}
	@Override
	public ArrayList<ChatTO> getBox(String userID) {
		if (logger.isDebugEnabled()) {
			logger.debug(" getBox 시작 ");
		}

		ArrayList<ChatTO> getBox = chatDAO.getBox(userID);

		if (logger.isDebugEnabled()) {
			logger.debug(" getBox 종료 ");
		}
		return getBox;
	}
	@Override
	public int getUnreadChat(String fromID, String toID) {
		if (logger.isDebugEnabled()) {
			//logger.debug(" chatUnread 시작 ");
		}

		int result= chatDAO.getUnreadChat(fromID,toID);
		

		if (logger.isDebugEnabled()) {
			//logger.debug(" chatUnread 종료 ");
		}
		return result;
	}
}