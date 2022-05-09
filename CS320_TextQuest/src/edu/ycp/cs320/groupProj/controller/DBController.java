package edu.ycp.cs320.groupProj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;
import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.persist.DatabaseProvider;
import edu.ycp.cs320.gamedb.persist.DerbyDatabase;
import edu.ycp.cs320.gamedb.persist.IDatabase;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;

public class DBController {
	
	public Boolean InsertNewPlayer(String username, String password){
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Boolean player =  db.InsertNewPlayer(username, password);
		
		return player;
	}
	
	public Boolean InsertNewMove(int playerId, int gameId, String move){
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Boolean player =  db.InsertNewMove(playerId, gameId, move);
		
		return player;
	}
	
	public Boolean InsertNewGame(int playerId){
		
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());
	
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Boolean player =  db.InsertNewGame(playerId);
		
		return player;
	}

	public Player getPlayerByUsernameAndPassword(String username, String password) {
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Player player =  db.getPlayerByUsernameAndPassword(username, password);
		
		return player;
	}

	public Game LoadGame(int playerId, int i) {
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Game game =  db.LoadGame(playerId, i);
		
		return game;	
	}
	
	public PlayerModel LoadPlayerModel(int playerId) {
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		PlayerModel playerModel =  db.LoadPlayerModel(playerId);

		return playerModel;	
	}
	
	public Boolean InsertNewPlayerModel(int playerId, int health, int x, int y, int score, int matches) {
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Boolean playerModel =  db.InsertNewPlayerModel(playerId, health, x, y, score, matches);
		if(playerModel == false) {
			return null;
		}
		return playerModel;	
	}
	
	public Boolean UpdatePlayerInven(int playerId, String inven) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		Boolean playerInven = db.UpdatePlayerInven(playerId, inven);
		return playerInven;
	}

	public Boolean UpdateRoomInven(int playerId, int xLoc, int yLoc, String inven) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		Boolean roomInven = db.UpdateRoomInven(playerId, xLoc, yLoc, inven);
		return roomInven;
	}

	public Boolean InsertRoomInven(int playerId, int xLoc, int yLoc, String thing) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		Boolean roomInven = db.InsertRoomInven(playerId, xLoc, yLoc, thing);
		return roomInven;
		
	}

	public Boolean UpdatePlayerModel(int playerId, int health, int xLoc, int yLoc, int score, int matches) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		Boolean playerModel = db.UpdatePlayerModel(playerId, health, xLoc, yLoc, score, matches);
		return playerModel;
		
	}

	public ArrayList<ObjectModel> getPlayerInventory(int playerId) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		ArrayList<ObjectModel> playerInventory = db.getPlayerInventory(playerId);
		return playerInventory;
	}

	public ArrayList<ObjectModel> getRoomInventory(int playerId, int x, int y) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		ArrayList<ObjectModel> roomInventory = db.getRoomInventory(playerId, x, y);
		
		if(roomInventory.size() > 0) {
			return roomInventory;
		}else
			return null;
	}
	
	public ArrayList<PlayerModel> getLeaderboard(){
		DatabaseProvider.setInstance(new DerbyDatabase());
		
		IDatabase db = DatabaseProvider.getInstance();
		ArrayList<PlayerModel> players = db.getAllPlayersFromPlayerModel();
		
		if(players.size() > 0) {
			return players;
		}else
			return null;
	}
}