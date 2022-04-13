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
	
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(final int playerId, final String username, final String password, final String move, final int score, final int health) {
		return executeTransaction(new Transaction<List<Pair<Player,Game>>>() {
			@Override
			public List<Pair<Player, Game>> execute(Connection conn) throws SQLException {
				PreparedStatement getPlayerStmt = null;
				PreparedStatement getPlayerStmt2 = null;
				PreparedStatement insertPlayerStmt = null;
				PreparedStatement insertGameStmt = null;
				PreparedStatement insertGameStmt2 = null;
				PreparedStatement stmt = null;
				ResultSet playerResultSet = null;
				ResultSet playerResultSet2 = null;
				ResultSet resultSet = null;
				
				try { //NO IDEA IF THIS IS RIGHT
					getPlayerStmt = conn.prepareStatement(
							"select playerId"
							+ "  from players "
							+ "  where username = ?"
							+ " and password = ?"
					);
					getPlayerStmt.setString(1, username);
					getPlayerStmt.setString(2, password);
					
					playerResultSet = getPlayerStmt.executeQuery();

					
					// for testing that a result was returned
					Boolean found = false;
					
					while (playerResultSet.next()) {
						found = true;
						Object obj = playerResultSet.getObject(1);
						int id = Integer.parseInt(obj.toString());
						
						insertGameStmt = conn.prepareStatement(
								"insert into game (playerId, move, score, health)"
								
								+"values("+id+", ?, ?, ?)"
						);
						insertGameStmt.setString(1, move);
						insertGameStmt.setInt(2,  score);
						insertGameStmt.setInt(3, health);
						
						insertGameStmt.executeUpdate();
					}
					
					
					// check if the title was found
					if (!found) {
						System.out.println("Adding player here");
						insertPlayerStmt = conn.prepareStatement(
								"insert into players (username, password)"
								
								+"values(?, ?)"
						);
						insertPlayerStmt.setString(1, username);
						insertPlayerStmt.setString(2,  password);
						System.out.println(username + " " +password);
						
						insertPlayerStmt.executeUpdate();						

						getPlayerStmt2 = conn.prepareStatement(
								"select playerId"
								+ "  from players "
								+ "  where username = ?"
								+ " and password = ?"
						);
						// substitute the title entered by the user for the placeholder in the query
						getPlayerStmt2.setString(1, username);
						getPlayerStmt2.setString(2,  password);
						
						int id = 0;
						// execute the query
						playerResultSet2 = getPlayerStmt2.executeQuery();
						if(playerResultSet2.next()) {
							Object obj = playerResultSet2.getObject(1);
							id = Integer.parseInt(obj.toString());
						}
						System.out.println(id);
						
						DBUtil.closeQuietly(playerResultSet2);
						
						insertGameStmt2 = conn.prepareStatement(
								"insert into game (playerId, move, score, health)"
								
								+"values("+id+", ?, ?, ?)"
						);
						insertGameStmt2.setString(1, move);
						insertGameStmt2.setInt(2,  score);
						insertGameStmt2.setInt(3, health);
						System.out.println("Move: " + move + " Score: " + score + " Health: " + health);
						insertGameStmt2.executeUpdate();
					}
					
					
					stmt = conn.prepareStatement(
							"select players.*, game.* "
									+ "  from players, game "
									+ "  where players.playerId = game.playerId "
									+ " 		and player.username = ?"
					);
					stmt.setString(1, username);
					
					List<Pair<Player, Game>> result = new ArrayList<Pair<Player,Game>>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found2 = false;
					
					while (resultSet.next()) {
						found2 = true;
						
						// create new Player object
						// retrieve attributes from resultSet starting with index 1
						Player player = new Player();
						loadPlayer(player, resultSet, 1);
						
						// create new Game object
						// retrieve attributes from resultSet starting at index 4
						Game game = new Game();
						loadGame(game, resultSet, 4);
						
						result.add(new Pair<Player, Game>(player, game));
					}
					
					// check if the title was found
					if (!found2) {
						System.out.println("<" + username + "> was not found in the players table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(getPlayerStmt);
					DBUtil.closeQuietly(getPlayerStmt2);
					DBUtil.closeQuietly(insertPlayerStmt);
					DBUtil.closeQuietly(insertGameStmt);
					DBUtil.closeQuietly(insertGameStmt2);
					DBUtil.closeQuietly(stmt);

					DBUtil.closeQuietly(playerResultSet);
					DBUtil.closeQuietly(playerResultSet2);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}
	
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
							"	playerId integer primary key " +
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
	public List<Pair<Player, Game>> findAuthorAndBookByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<Player, Game>> findAuthorAndBookByAuthorLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<Player, Game>> InsertNewGameInfoWithPlayer(String username, String password, String move,
			String score, int health) {
		// TODO Auto-generated method stub
		return null;
	}
}
