package kr.co.yooooon.chat.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.chat.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class ChatDAOImpl implements ChatDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static ChatDAO instance;

	private ChatDAOImpl() {
	}

	public static ChatDAO getInstance() {
		if (instance == null)
			instance = new ChatDAOImpl();
		return instance;
	}
	@Override
	public void chatSubmit(ChatTO chat) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertBoardWrite 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO chat VALUES (seq_Chat_CHATID.nextval,?,?,?,(SYSDATE),0)");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, chat.getFromId());
			pstmt.setString(2, chat.getToId());
			pstmt.setString(3, chat.getChatContent());
			
			
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" insertBoardWrite 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	@Override
	public ArrayList<ChatTO> findChatList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findChatList 시작 ");
		}
		ArrayList<ChatTO> chatList = new ArrayList<ChatTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append("select * from board order by boardGroup DESC, boardSequence ASC ");
			
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				//System.out.println("check11111111111111");
				ChatTO chat = new ChatTO();
				chat = new ChatTO();
				//chat. setUserID(rs.getString("UserID"));
				
				
				
				chatList.add(chat);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findChatList 종료 ");
			}
			return chatList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public int chatSubmit(String userID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" chatSubmit 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			System.out.println("@@@@@@@@@@@@@="+userID);
			StringBuffer query = new StringBuffer();
			query.append("select * from emp_new where EMP_NAME= ?");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (logger.isDebugEnabled()) {
				logger.debug(" chatSubmit 종료 ");
			}
			if (rs.next()) {
				return 0;
			}
			else {
				return 1;
			}
	
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
	@Override
	public ArrayList<ChatTO> getChatListByID(String fromID, String toID, String chatID) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findChatList 시작 ");
		}
		ArrayList<ChatTO> chatList = new ArrayList<ChatTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append("select * from chat where ((fromID=? and toID=?) or (fromID=? and toID=?)) and chatID>? order by chatTime");
			
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();	
			while (rs.next()) {
				ChatTO chat = new ChatTO();
				
				chat.setChatId(rs.getInt("ChatId"));
				chat.setFromId(rs.getString("fromId"));
				chat.setToId(rs.getString("toId"));
				chat.setChatContent(rs.getString("ChatContent"));
				chat.setToId(rs.getString("toId"));
				int chatTime=Integer.parseInt(rs.getString("ChatTime").substring(11,13));
				String timeType="오전";
				if (chatTime>12) {
					timeType="오후";
					chatTime-=12;
				}
				chat.setChatTime(rs.getString("ChatTime").substring(0,11)+" "+timeType+" "+chatTime+":"+rs.getString("chatTime").substring(14,16)+"");
				
				chatList.add(chat);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findChatList 종료 ");
			}
			return chatList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public ArrayList<ChatTO> getChatListByRecent(String fromID, String toID, String number) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findChatList 시작 ");
		}
		ArrayList<ChatTO> chatList = new ArrayList<ChatTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append("select * from chat where ((fromID=? and toID=?) or (fromID=? and toID=?)) and chatID>? order by chatTime");
			
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(number));
			
			//pstmt.setString(6, fromID);
			//pstmt.setString(7, toID);
			//pstmt.setString(8, toID);
			//pstmt.setString(9, fromID);
			rs = pstmt.executeQuery();	
			while (rs.next()) {
				ChatTO chat = new ChatTO();
				
				chat.setChatId(rs.getInt("ChatId"));
				chat.setFromId(rs.getString("fromId"));
				chat.setToId(rs.getString("toId"));
				chat.setChatContent(rs.getString("ChatContent"));
				chat.setToId(rs.getString("toId"));
				int chatTime=Integer.parseInt(rs.getString("ChatTime").substring(11,13));
				String timeType="오전";
				if (chatTime>12) {
					timeType="오후";
					chatTime-=12;
				}
				chat.setChatTime(rs.getString("ChatTime").substring(0,11)+" "+timeType+" "+chatTime+":"+rs.getString("chatTime").substring(14,16)+"");
				
				chatList.add(chat);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findChatList 종료 ");
			}
			return chatList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void readChat(String fromID, String toID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" readChat 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			//System.out.println("@@@@@@@@@@@@@="+userID);
			StringBuffer query = new StringBuffer();
			query.append("update chat set chatRead=1 where (fromID=? and toID=?)");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, toID);
			pstmt.setString(2, fromID);
			pstmt.executeUpdate();
			//rs = pstmt.executeQuery();
			if (logger.isDebugEnabled()) {
				logger.debug(" readChat 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
	@Override
	public int chatUnread(String userID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			//logger.debug(" chatSubmit 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			//System.out.println("@@@@@@@@@@@@@="+userID);
			StringBuffer query = new StringBuffer();
			query.append("select count(chatID) from chat where toID=? and chatRead=0");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, userID);
			//pstmt.setString(2, fromID);
			
			rs = pstmt.executeQuery();
			if (logger.isDebugEnabled()) {
				//logger.debug(" chatSubmit 종료 ");
			}
			if (rs.next()) {
				System.out.println("8월 67일 --------"+rs.getInt("COUNT(chatID)"));
				return rs.getInt("COUNT(chatID)");
			}
			else {
				return 0;
			}

		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
	@Override
	public ArrayList<ChatTO> getBox(String userID ) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" findChatList 시작 ");
		}
		ArrayList<ChatTO> chatList = new ArrayList<ChatTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append("select * from chat where chatID IN(SELECT MAX(CHATID) FROM CHAT WHERE TOID=? OR FROMID=? GROUP BY FROMID, TOID)");
			
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			
			rs = pstmt.executeQuery();	
			while (rs.next()) {
				ChatTO chat = new ChatTO();
				
				chat.setChatId(rs.getInt("ChatId"));
				chat.setFromId(rs.getString("fromId"));
				chat.setToId(rs.getString("toId"));
				chat.setChatContent(rs.getString("ChatContent"));
				chat.setToId(rs.getString("toId"));
				int chatTime=Integer.parseInt(rs.getString("ChatTime").substring(11,13));
				String timeType="오전";
				if (chatTime>12) {
					timeType="오후";
					chatTime-=12;
				}
				chat.setChatTime(rs.getString("ChatTime").substring(0,11)+" "+timeType+" "+chatTime+":"+rs.getString("chatTime").substring(14,16)+"");
				
				chatList.add(chat);
				
			}
			for(int i=0;i<chatList.size();i++) {
				ChatTO x = chatList.get(i);
				for(int j=0;j<chatList.size();j++) {
					ChatTO y = chatList.get(j);
					if(x.getFromId().equals(y.getToId()) &&x.getToId().equals(y.getFromId())) {
						if(x.getChatId()<y.getChatId()) {
							chatList.remove(x);
							i--;
							break;
						}else {
							chatList.remove(y);
							j--;
							
						}
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" findChatList 종료 ");
			}
			return chatList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public int getUnreadChat(String fromID, String toID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			//logger.debug(" chatSubmit 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			//System.out.println("@@@@@@@@@@@@@="+userID);
			StringBuffer query = new StringBuffer();
			query.append("select count(chatID) from chat where fromID=? and toID=? and chatRead=0");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			//pstmt.setString(2, fromID);
			
			rs = pstmt.executeQuery();
			if (logger.isDebugEnabled()) {
				//logger.debug(" chatSubmit 종료 ");
			}
			if (rs.next()) {
				System.out.println("8월 67일 --------"+rs.getInt("COUNT(chatID)"));
				return rs.getInt("COUNT(chatID)");
			}
			else {
				return 0;
			}

		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
}
