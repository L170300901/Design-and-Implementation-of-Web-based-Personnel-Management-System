package kr.co.yooooon.board.dao;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;

import kr.co.yooooon.board.to.*;
import kr.co.yooooon.common.exception.*;
import kr.co.yooooon.common.transaction.*;

public class BoardDAOImpl implements BoardDAO {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	private static BoardDAO instance;

	private BoardDAOImpl() {
	}

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAOImpl();
		return instance;
	}

	@Override
	public ArrayList<BoardTO> selectBoardList() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" selectBoardList 시작 ");
		}
		ArrayList<BoardTO> boardList = new ArrayList<BoardTO>();
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
				BoardTO board = new BoardTO();
				board = new BoardTO();
				board. setUserID(rs.getString("UserID"));
				
				board.setUserID(rs.getString("UserID"));
				board.setBoardID(rs.getInt("BoardID"));
				board.setBoardTitle(rs.getString("BoardTitle"));
				board.setBoardContent(rs.getString("BoardContent"));
				board.setBoardDate(rs.getString("BoardDate"));
				board.setBoardHit(rs.getInt("BoardHit"));
				board.setBoardFile(rs.getString("BoardFile"));
				board.setBoardRealFile(rs.getString("BoardRealFile"));
				board.setBoardGroup(rs.getInt("BoardGroup"));
				board.setBoardsequence(rs.getInt("Boardsequence"));
				board.setBoardlevel(rs.getInt("Boardlevel"));
				
				boardList.add(board);
				
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" selectBoardList 종료 ");
			}
			return boardList;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void insertBoardWrite(BoardTO board) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" insertBoardWrite 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO BOARD VALUES (?,(NVL((SELECT MAX(boardID)+1 from BOARD),1)),?,?,(SYSDATE),0,0,0,NVL((SELECT MAX(boardGroup)+1 from BOARD),0),0,0)");
//NVL((SELECT MAX(boardID)+1 from BOARD),1)
//IFNULL((SELECT MAX(boardGroup)+1 from BOARD),0)
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, board.getUserID());
			//pstmt.setInt(2, board.getBoardID());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			//pstmt.setString(5, board.getBoardDate());
			//pstmt.setInt(4, board.getBoardHit());
			//pstmt.setString(4, board.getBoardFile());
			//pstmt.setString(5, board.getBoardRealFile());
			//pstmt.setInt(9, board.getBoardGroup());
			//pstmt.setInt(10, board.getBoardsequence());
			//pstmt.setInt(11, board.getBoardlevel());
			
			
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
	public ArrayList<BoardTO> boardShow(String boardID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardShow 시작 ");
		}
		ArrayList<BoardTO> boardShow = new ArrayList<BoardTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			//query.append("update board set boardhit= boardhit+1 where boardID=?");
			//pstmt.setString(1, boardID);
			query.append("select *from board where boardId=?");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			BoardTO board = new BoardTO();
			if (rs.next()) {
				System.out.println("check11111111111111");
				board.setUserID(rs.getString("UserID"));
				//board.setUserID(rs.getString("UserID"));
				board.setBoardID(rs.getInt("BoardID"));
				board.setBoardTitle(rs.getString("BoardTitle"));
				board.setBoardContent(rs.getString("BoardContent"));
				board.setBoardDate(rs.getString("BoardDate"));
				board.setBoardHit(rs.getInt("BoardHit"));
				board.setBoardFile(rs.getString("BoardFile"));
				board.setBoardRealFile(rs.getString("BoardRealFile"));
				board.setBoardGroup(rs.getInt("BoardGroup"));
				board.setBoardsequence(rs.getInt("Boardsequence"));
				board.setBoardlevel(rs.getInt("Boardlevel"));	
				boardShow.add(board);
				
			}
			
			
			if (logger.isDebugEnabled()) {
				logger.debug(" selectBoardList 종료 ");
			}
			return boardShow;
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void boardHit(String boardID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardHit 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		
		//String SQL="update board set boardhit= boardhit+1 where boardID=?";
		//int number=Integer.parseInt(boardID);
		System.out.println(boardID);
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();		
			query.append("update board set boardhit= boardhit+1 where boardID=? ");
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, boardID);
			pstmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug(" boardHit 종료 ");
			}
			
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
	public void removeBoard(String boardID){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" removeBoard 시작 ");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM board WHERE boardID = ?");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, boardID);
			pstmt.executeUpdate();
			
			if (logger.isDebugEnabled()) {
				logger.debug(" removeBoard 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
	}
	@Override
	public void modifyBoard(BoardTO board) {
		if (logger.isDebugEnabled()) {
			logger.debug(" modifyBoard 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			
			StringBuffer query = new StringBuffer();
			query.append("UPDATE BOARD SET boardTitle = ?, boardContent = ? WHERE boardID = ? ");
		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardID());
	
			
			
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" modifyBoard 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}	
		
	}
	@Override
	public void boardReply(BoardTO board ){
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug(" boardReply 시작 ");
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO BOARD VALUES (?,(NVL((SELECT MAX(boardID)+1 from BOARD),1)),?,?,(SYSDATE),0,0,0,?,?,?) ");

			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, board.getUserID());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			
			//파일 업로드는 일단 보류 좀 할께요 채크좀 해야됨 
			//pstmt.setString(4, board.getBoardFile());
			//pstmt.setString(5, board.getBoardRealFile());
			
			pstmt.setInt(4, board.getBoardGroup());
			pstmt.setInt(5, board.getBoardsequence()+1);
			pstmt.setInt(6, board.getBoardlevel()+1);
			
			
			pstmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(" boardReply 종료 ");
			}
		} catch (Exception sqle) {
			logger.fatal(sqle.getMessage());
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}		
	}
	
}