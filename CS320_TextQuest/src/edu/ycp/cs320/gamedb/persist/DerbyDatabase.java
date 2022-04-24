package edu.ycp.cs320.gamedb.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.gamedb.model.Game;
import edu.ycp.cs320.gamedb.model.Pair;
import edu.ycp.cs320.sqldemo.DBUtil;

public class DerbyDatabase implements IDatabase { //FIX
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadPlayer(Player player, ResultSet resultSet, int index) throws SQLException {
		player.setPlayerId(resultSet.getInt(index++));
		player.setUsername(resultSet.getString(index++));
		player.setPassword(resultSet.getString(index++));
	}
	
	private void loadGame(Game game, ResultSet resultSet, int index) throws SQLException {
		game.setGameId(resultSet.getInt(index++));
		game.setPlayerId(resultSet.getInt(index++));
		game.setMove(resultSet.getString(index++));
		game.setScore(resultSet.getInt(index++));
		game.setHealth(resultSet.getInt(index++));		
	}
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table players (" +
						"	playerId integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	username varchar(40)," +
						"	password varchar(40)" +
						")"
					);	
					stmt1.executeUpdate();
					// DO I NEED A PRIMARY KEY?
					stmt2 = conn.prepareStatement(
							"create table game (" +
							"	gameId integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	playerId integer constraint playerId references players, " +
							"	move varchar(70)," +
							"	score integer," +
							"   health integer " +
							")"
					);
					stmt2.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Player> playerList;
				List<Game> gameList;
				
				try {
					playerList = InitialData.getPlayers(); 	
					gameList = InitialData.getGame();		
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertPlayer = null;
				PreparedStatement insertGame   = null;

				try {
					// populate players table (do players first, since playerId is foreign key in game table)
					insertPlayer = conn.prepareStatement("insert into players (username, password) values (?, ?)");
					for (Player player : playerList) {
//						insertPlayer.setInt(1, player.getPlayerId());	// auto-generated primary key, don't insert this
						insertPlayer.setString(1, player.getUsername());
						insertPlayer.setString(2, player.getPassword());
						insertPlayer.addBatch();
					}
					insertPlayer.executeBatch();
					
					// populate game table (do this after players table,
					// since playerId must exist in players table before inserting game)
					insertGame = conn.prepareStatement("insert into game (playerId, move, score, health) values (?, ?, ?, ?)");
					for (Game game : gameList) {
//						insertGame.setInt(1, game.getGameId());		// auto-generated primary key, don't insert this
						insertGame.setInt(1, game.getPlayerId());
						insertGame.setString(2, game.getMove());
						insertGame.setInt(3, game.getScore());
						insertGame.setInt(4,  game.getHealth());
						insertGame.addBatch();
					}
					insertGame.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertGame);
					DBUtil.closeQuietly(insertPlayer);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}



	@Override
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(String username, String password, String move,
			String score, int health) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean InsertNewPlayer(String username, String password) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertPlayer = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertPlayer = conn.prepareStatement("insert into players (username, password) values (?, ?)");
				
				insertPlayer.setString(1, username);
				insertPlayer.setString(2, password);
				
				resultSet = insertPlayer.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + username + "> could not be inserted into the Player table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(insertPlayer);
			}
			}
	});
	}
	
	public Boolean InsertNewGame(int playerId) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertGame = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertGame = conn.prepareStatement("insert into game (playerId, move, score, health) values (?, ' ', 0, 100)");
				
				insertGame.setInt(1, playerId);

				resultSet = insertGame.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the Games table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(insertGame);
			}
			}
	});
	}

	@Override
	public Player getPlayerByUsernameAndPassword(String username, String password) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
			PreparedStatement getPlayer = null;
			ResultSet resultSet = null;
			
			try {
				// retreive all attributes from both Books and Authors tables
				getPlayer = conn.prepareStatement("select * from players where players.username=? AND players.password=?");
				
				getPlayer.setString(1, username);
				getPlayer.setString(2,  password);

				resultSet = getPlayer.executeQuery();
				
				// for testing that a result was returned
				Boolean found = false;
				
				while (resultSet.next()) {
					found = true;
					
					// create new Student object
					// retrieve attributes from resultSet starting with index 1
					Player player = new Player();
					loadPlayer(player, resultSet, 1);
					return player;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + username + "> could not be found in the Player table");
				}
				
				return null;
			} finally {
				DBUtil.closeQuietly(getPlayer);
				DBUtil.closeQuietly(resultSet);
			}
			}
	});
	}
}
