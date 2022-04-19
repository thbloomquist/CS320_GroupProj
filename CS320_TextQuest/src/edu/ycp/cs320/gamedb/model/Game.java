package edu.ycp.cs320.gamedb.model;

public class Game {
	private int    gameId;
	private int    playerId;
	private String move;
	private int	   score;
	private int    health;
	
	public Game() {
		
	}
	
	public void setGameId(int bookId) {
		this.gameId = gameId;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	public String getMove() {
		return move;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}
}
