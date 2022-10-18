package kr.co.yooooon.board.to;

public class BoardTO {
		String userID;
		int boardID;
		String boardTitle;
		String boardDate;
		int boardHit;
		String boardFile;
		String boardContent;		
		String boardRealFile;
		int boardGroup;
		int boardsequence;
		int boardlevel;
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public int getBoardID() {
			return boardID;
		}
		public void setBoardID(int boardID) {
			this.boardID = boardID;
		}
		public String getBoardTitle() {
			return boardTitle;
		}
		public void setBoardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
		}
		public String getBoardDate() {
			return boardDate;
		}
		public void setBoardDate(String boardDate) {
			this.boardDate = boardDate;
		}
		public int getBoardHit() {
			return boardHit;
		}
		public void setBoardHit(int boardHit) {
			this.boardHit = boardHit;
		}
		public String getBoardFile() {
			return boardFile;
		}
		public void setBoardFile(String boardFile) {
			this.boardFile = boardFile;
		}
		public String getBoardContent() {
			return boardContent;
		}
		public void setBoardContent(String boardContent) {
			this.boardContent = boardContent;
		}
		public String getBoardRealFile() {
			return boardRealFile;
		}
		public void setBoardRealFile(String boardRealFile) {
			this.boardRealFile = boardRealFile;
		}
		public int getBoardGroup() {
			return boardGroup;
		}
		public void setBoardGroup(int boardGroup) {
			this.boardGroup = boardGroup;
		}
		public int getBoardsequence() {
			return boardsequence;
		}
		public void setBoardsequence(int boardsequence) {
			this.boardsequence = boardsequence;
		}
		public int getBoardlevel() {
			return boardlevel;
		}
		public void setBoardlevel(int boardlevel) {
			this.boardlevel = boardlevel;
		}
}