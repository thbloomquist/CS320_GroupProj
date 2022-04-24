package edu.ycp.cs320.groupProj.controller;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;
import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.persist.DatabaseProvider;
import edu.ycp.cs320.gamedb.persist.DerbyDatabase;
import edu.ycp.cs320.gamedb.persist.IDatabase;

public class DBController {
	
	public Boolean InsertNewPlayer(String username, String password){
	
	// Create the default IDatabase instance
	DatabaseProvider.setInstance(new DerbyDatabase());

	// get the DB instance and execute transaction
	IDatabase db = DatabaseProvider.getInstance();
	Boolean player =  db.InsertNewPlayer(username, password);
	
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
}