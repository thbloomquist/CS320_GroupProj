package edu.ycp.cs320.gamedb.persist;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;

public interface IDatabase {
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(String username, String password, String move,
			String score, int health);
	public Boolean InsertNewPlayer(String username, String password);
	public Boolean InsertNewGame(int playerId);
	public Player getPlayerByUsernameAndPassword(String username, String password);
	public Boolean InsertNewMove(int playerId, int gameId, String move);
	public Game LoadGame(int playerId, int i);
	public PlayerModel LoadPlayerModel(int playerId);
	public Boolean InsertNewPlayerModel(int playerId, int health, int x, int y, int score, int matches);
	
	/**
	 * Updates the players inventory
	 * @param playerId		Players ID
	 * @param inven			New Inventory
	 * @return
	 */
	public Boolean UpdatePlayerInven(int playerId, String inven);
	public Boolean InsertRoomInven(int playerId, int xLoc, int yLoc, String inven);
	public Boolean UpdateRoomInven(int playerId, int xLoc, int yLoc, String inven); //idk what im doing here
	public Boolean UpdatePlayerModel(int playerId, int health, int xLoc, int yLoc, int score, int matches);
	/**
	 * Get the entire players inventory
	 * @param playerId		The players ID
	 * @return				Players ID
	 */
	public ArrayList<ObjectModel> getPlayerInventory(int playerId);
	/**
	 * Gets all items from a rooms inventory
	 * @param playerId	room for player ID
	 * @param xLoc		X Location of Room
	 * @param yLoc		Y Location of Room
	 * @return			Contents of rooms inventory
	 */
	public ArrayList<ObjectModel> getRoomInventory(int playerId, int xLoc, int yLoc);
	public ArrayList<PlayerModel> getAllPlayersFromPlayerModel();
}
