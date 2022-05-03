package edu.ycp.cs320.gamedb.persist;

import java.util.List;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;

public interface IDatabase {
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(String username, String password, String move,
			String score, int health);
	public Boolean InsertNewPlayer(String username, String password);
	public Boolean InsertNewGame(int playerId);
	public Player getPlayerByUsernameAndPassword(String username, String password);
	Boolean InsertNewMove(int playerId, int gameId, String move);
	public Game LoadGame(int playerId, int i);
	public PlayerModel LoadPlayerModel(int playerId);
	public Boolean InsertNewPlayerModel(int playerId, int health, int x, int y, int score);
}