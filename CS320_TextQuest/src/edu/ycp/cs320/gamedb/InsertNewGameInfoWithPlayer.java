package edu.ycp.cs320.gamedb;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;
import edu.ycp.cs320.gamedb.persist.DatabaseProvider;
import edu.ycp.cs320.gamedb.persist.IDatabase;

public class InsertNewGameInfoWithPlayer {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		//InitDatabase.init(keyboard);
		
		System.out.print("Enter player username: ");
		String username = keyboard.nextLine();
		System.out.print("Enter player password: ");
		String password = keyboard.nextLine();
		System.out.print("Enter move: ");
		String move = keyboard.nextLine();
		System.out.print("Enter score: ");
		String score = keyboard.nextLine();
		System.out.print("Enter year health: ");
		int health = keyboard.nextInt();
		int gameId; //I have no idea what I'm doing here or what this class is supposed to be doing someone send help 
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		List<Pair<Player, Game>> playerGameList =  db.InsertNewGameInfoWithPlayer(username, password, move, score, health);
		
		// check if anything was returned and output the list
		if (playerGameList.isEmpty()) {
			System.out.println("Nothings here buddy");

		}
		else {
			for (Pair<Player, Game> playerGame : playerGameList) {
				Player player = playerGame.getLeft();
				Game game = playerGame.getRight();
				System.out.println(player.getUsername() + "," + player.getPassword() + "," + game.getMove() + "," + game.getScore() + "," + game.getHealth());
			}
		}
	}
}
//NEED THIS 
