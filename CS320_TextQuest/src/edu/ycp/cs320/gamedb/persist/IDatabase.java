package edu.ycp.cs320.gamedb.persist;

import java.util.List;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;

public interface IDatabase {
	public List<Pair<Player, Game>> findAuthorAndBookByTitle(String title);
	public List<Pair<Player, Game>> findAuthorAndBookByAuthorLastName(String lastName);
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(String username, String password, String move,
			String score, int health);
}
