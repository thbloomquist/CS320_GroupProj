package edu.ycp.cs320.groupProj.controller;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;
import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.persist.DatabaseProvider;
import edu.ycp.cs320.gamedb.persist.DerbyDatabase;
import edu.ycp.cs320.gamedb.persist.IDatabase;
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
	
	public Boolean InsertNewPlayerModel(int playerId, int health, int x, int y, int score) {
		// Create the default IDatabase instance
		DatabaseProvider.setInstance(new DerbyDatabase());

		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		Boolean playerModel =  db.InsertNewPlayerModel(playerId, health, x, y, score);
		
		return playerModel;	
	}

	//Don't I want a single query that updates game with map, score, and health instead of individual ones?
}