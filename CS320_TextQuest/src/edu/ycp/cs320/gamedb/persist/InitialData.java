package edu.ycp.cs320.gamedb.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.model.Game;

public class InitialData {
	public static List<Player> getPlayers() throws IOException {
		List<Player> playerList = new ArrayList<Player>();
		ReadCSV readPlayers = new ReadCSV("authors.csv"); //FIX CSV LATER
		try {
			// auto-generated primary key for authors table
			Integer playerId = 1;
			while (true) {
				List<String> tuple = readPlayers.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Player player = new Player();
				player.setPlayerId(playerId++);				
				player.setUsername(i.next());
				player.setPassword(i.next());
				playerList.add(player);
			}
			return playerList;
		} finally {
			readPlayers.close();
		}
	}
	
	public static List<Game> getGame() throws IOException {
		List<Game> gameList = new ArrayList<Game>();
		ReadCSV readGame = new ReadCSV("books.csv"); 	//FIX CSV LATER
		try {
			// auto-generated primary key for books table
			Integer gameId = 1;
			while (true) {
				List<String> tuple = readGame.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Game game = new Game();
				game.setGameId(gameId++);
				game.setPlayerId(Integer.parseInt(i.next()));
				game.setMove(i.next());
				game.setScore(Integer.parseInt(i.next()));
				game.setHealth(Integer.parseInt(i.next()));
				gameList.add(game);
			}
			return gameList;
		} finally {
			readGame.close();
		}
	}
}
