package kr.co.yooooon.chat.applicationService;

import java.util.*;

import kr.co.yooooon.chat.to.*;

public interface ChatSubmitApplicationService {
	
	public void chatSubmit(ChatTO chat);
	public ArrayList<ChatTO> findChatList();
	public int chatSubmit(String userID);
	//public ArrayList<ChatTO> getChatListByID(String fromID, String toID, String chatID);
	public ArrayList<ChatTO> getChatListByRecent(String fromID, String toID, String number);
	public void readChat(String fromID, String toID);
	public int chatUnread(String userID);
	public ArrayList<ChatTO> getBox(String userID);
	public int getUnreadChat(String fromID, String toID);
}
