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
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;
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
	
	private void loadPlayerModel(PlayerModel playerModel, ResultSet resultSet, int index) throws SQLException {
		playerModel.setHP(resultSet.getInt(index++));
		playerModel.setSideLoc(resultSet.getInt(index++));
		playerModel.setUpLoc(resultSet.getInt(index++));		
		playerModel.setScore(resultSet.getInt(index++));
	}
	
	private void loadPlayerInventory(ArrayList<ObjectModel> items2, ResultSet resultSet, int index) throws SQLException {
		System.out.println("Loading  Inventory");
		String itemNames = resultSet.getString(index++);
		String[] items = null;
		// splits the items in inventory into multiple strings because they're all seperate
		for (int i = 0; i < 2; i++) {
			items = itemNames.split(" ");
		}
		
		for(String item : items) {
			System.out.println(item);
			NameTag name = null;
			if(item.toLowerCase().equals("crate")) {
				name = new NameTag(item, "an old wooden crate");
			} else if(item.toLowerCase().equals("key")) {
				name = new NameTag(item, "It's a shiny golden key, damn it's sparkly.");
			} else if(item.toLowerCase().equals("torch")) {
				name = new NameTag(item, "It's a piece of wood with oil-covered cloth wrapped on the tip.");
			} else if(item.toLowerCase().equals("banana")) {
					name = new NameTag(item, "a yellow banana you found in a box. You don't know where it's been but beggers can't be choosers.");
			}else if(item.toLowerCase().equals("sapphire")) {
				name = new NameTag(item, "as blue as the ocean over by the beach that I work at over the summer.");
			}else if(item.toLowerCase().equals("ruby")) {
				name = new NameTag(item, "as red as a box of candy on valentines day.");
			}else if(item.toLowerCase().equals("emerald")) {
				name = new NameTag(item, "as green as a well maintained suburban lawn.");
			}else if(item.toLowerCase().equals("diamond")) {
				name = new NameTag(item, "as yellow as one of those cool ass smiley face cookies they got down by this local bakery near where I work over the summer.");
			}else if(item.toLowerCase().equals("onyx")) {
				name = new NameTag(item, "as dark as the room you found it in.");
			}else {
				name = new NameTag("REMOVE", "REMOVE");
			}
			items2.add(new ObjectModel(name, false));
		}
	}

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				
				try {
					//PLAYER TABLE
					stmt1 = conn.prepareStatement(
						"create table players (" +
						"	playerId integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	username varchar(40)," +
						"	password varchar(40)" +
						")"
					);	
					stmt1.executeUpdate();
					//GAME TABLE
					stmt2 = conn.prepareStatement(
							"create table game (" +
							"	gameId integer, " +
							"	playerId integer constraint playerId references players, " +
							"	move varchar(70)," +
							"	score integer," +
							"   health integer " +
							")"
					);
					stmt2.executeUpdate();
					//MOVES TABLE
					stmt3 = conn.prepareStatement(
							"create table moves (" +
									"	playerId integer constraint playerId1 references players,  " +
									"	gameId integer, " +									
									"	move varchar(20) " +
									")"
									);
					stmt3.executeUpdate();
					//PLAYERMODEL TABLE	
					stmt4 = conn.prepareStatement(
							"create table playermodel (" +
									"	playerId integer constraint playerId2 references players,  " +
									"	health integer, " +									
									"	xloc integer,"
									+ "yloc integer,"
									+ "score integer,"
									+ "matches integer"+
									")"
									);
					stmt4.executeUpdate();
					//PLAYERINVEN TABLE
					stmt5 = conn.prepareStatement(
							"create table playerinven (" +
									"	playerId integer constraint playerId3 references players,  " +
									"	inven varchar(100) " +
									")"
									);
					stmt5.executeUpdate();
					//ROOMINVEN TABLE
					stmt6 = conn.prepareStatement(
							"create table roominven (" +
									"	playerId integer constraint playerId4 references players,  " +
									"   xloc integer," + 
									"   yloc integer," +
									"	inven varchar(70) " +
									")"
									);
					stmt6.executeUpdate(); 
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
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
					insertGame = conn.prepareStatement("insert into game (gameId, playerId, move, score, health) values (?, ?, ?, ?, ?)");
					for (Game game : gameList) {
						insertGame.setInt(1, game.getGameId());		// auto-generated primary key, don't insert this
						insertGame.setInt(2, game.getPlayerId());
						insertGame.setString(3, game.getMove());
						insertGame.setInt(4, game.getScore());
						insertGame.setInt(5,  game.getHealth());
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
	public Boolean InsertNewPlayer(final String username, final String password) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertPlayer = null;
			PreparedStatement getPlayer = null;
			PreparedStatement insertPlayerInven = null;
			
			int resultSet = -1;
			ResultSet resultSet1 = null;
			
			try {

				insertPlayer = conn.prepareStatement("insert into players (username, password) values (?, ?)");
				insertPlayer.setString(1, username);
				insertPlayer.setString(2, password);
				
				resultSet = insertPlayer.executeUpdate();
				
				getPlayer = conn.prepareStatement("select playerid from players where username = ?");
				getPlayer.setString(1,  username);
				resultSet1 = getPlayer.executeQuery();
				
				if (resultSet1.next())
				{
					insertPlayerInven = conn.prepareStatement("insert into playerinven (playerId, inven) values (?, 'torch')");
					insertPlayerInven.setInt(1, resultSet1.getInt(1));
					resultSet = insertPlayerInven.executeUpdate();
				}
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
				DBUtil.closeQuietly(insertPlayerInven);
			}
			}
		});
	}
	
	@Override
	public Boolean InsertNewMove(final int playerId, final int gameId, final String move) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertMove = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertMove = conn.prepareStatement("insert into moves (playerId, gameId, move) values (?, ?, ?)");
				
				insertMove.setInt(1, playerId);
				insertMove.setInt(2, gameId);
				insertMove.setString(3, move); //cant store array, fix later.
				
				resultSet = insertMove.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + move + "> could not be inserted into the Move table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(insertMove);
			}
			}
		});
	}
	
	public Boolean InsertNewGame(final int playerId) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertGame = null;
			
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertGame = conn.prepareStatement("insert into game (gameId, playerId, move, score, health) values (1, ?, ' ', 0, 100)");
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
	public Player getPlayerByUsernameAndPassword(final String username, final String password) {
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
	
	public Boolean UpdateCurrentGame(final int playerId, String move, int score, int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertGame = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertGame = conn.prepareStatement("update game (playerId, move, score, health) values (?, ?. ?, ?)");
				
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
	public Game LoadGame(final int playerId, final int gameId) {
		return executeTransaction(new Transaction<Game>() {
			@Override
			public Game execute(Connection conn) throws SQLException {
			PreparedStatement getGame = null;
			ResultSet resultSet = null;
			
			try {
				// retreive all attributes from both Books and Authors tables
				getGame = conn.prepareStatement("select * from game where game.playerId=? AND game.gameId=?");
				
				getGame.setInt(1, playerId);
				getGame.setInt(2,  gameId);

				resultSet = getGame.executeQuery();
				
				// for testing that a result was returned
				Boolean found = false;
				
				while (resultSet.next()) {
					found = true;
					
					// create new Student object
					// retrieve attributes from resultSet starting with index 1
					Game game = new Game();
					loadGame(game, resultSet, 1);
					return game;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + gameId + "> could not be found in the Game table");
				}
				
				return null;
			} finally {
				DBUtil.closeQuietly(getGame);
				DBUtil.closeQuietly(resultSet);
			}
			}
		});
	}

	@Override
	public PlayerModel LoadPlayerModel(final int playerId) {
		return executeTransaction(new Transaction<PlayerModel>() {
			@Override
			public PlayerModel execute(Connection conn) throws SQLException {
			PreparedStatement getGame = null;
			ResultSet resultSet = null;
			
			try {
				// retreive all attributes from both Books and Authors tables
				getGame = conn.prepareStatement("select * from playermodel where playermodel.playerId=?");
				
				getGame.setInt(1, playerId);

				resultSet = getGame.executeQuery();
				
				// for testing that a result was returned
				Boolean found = false;
				
				while (resultSet.next()) {
					found = true;
					
					// create new Student object
					// retrieve attributes from resultSet starting with index 1
					PlayerModel playerModel = new PlayerModel();
					loadPlayerModel(playerModel, resultSet, 2);
					return playerModel;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + playerId + "> could not be found in the playermodel table");
				}
				
				return null;
			} finally {
				DBUtil.closeQuietly(getGame);
				DBUtil.closeQuietly(resultSet);
			}
			}
		});
	}

	@Override
	public Boolean InsertNewPlayerModel(final int playerId, final int health, final int x, final int y, final int score, final int matches) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertPlayerModel = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				insertPlayerModel = conn.prepareStatement("insert into playermodel (playerId, health, xloc, yloc, score, matches) values (?,?,?,?,?,?)");
				
				insertPlayerModel.setInt(1, playerId);
				insertPlayerModel.setInt(2, health);
				insertPlayerModel.setInt(3, x);
				insertPlayerModel.setInt(4, y);
				insertPlayerModel.setInt(5, score);
				insertPlayerModel.setInt(6, matches);

				resultSet = insertPlayerModel.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the playermodel table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(insertPlayerModel);
				}
			}
		});
	}

	public Boolean UpdatePlayerModel(final int playerId, final int health, final int x, final int y, final int score, final int matches) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement updatePlayerModel = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				updatePlayerModel = conn.prepareStatement("update playermodel set health = ?, xloc = ?, yloc = ?, score = ?, matches = ? where playerId = ?");
				
				updatePlayerModel.setInt(6, playerId);
				updatePlayerModel.setInt(1, health);
				updatePlayerModel.setInt(2, x);
				updatePlayerModel.setInt(3, y);
				updatePlayerModel.setInt(4, score);
				updatePlayerModel.setInt(5, matches);

				resultSet = updatePlayerModel.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the playermodel table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(updatePlayerModel);
				}
			}
		});
	}
	
	@Override
	public Boolean UpdatePlayerInven(final int playerId, final String inven) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement updatePlayerInven = null;
			int resultSet = -1;
			
			try {
				updatePlayerInven = conn.prepareStatement("update playerinven set inven = ? where playerId = ?");
				
				updatePlayerInven.setString(1, inven);
				updatePlayerInven.setInt(2, playerId);

				resultSet = updatePlayerInven.executeUpdate();
				
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the playerinven table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(updatePlayerInven);
				}
			}
		});
	}

	@Override
	public Boolean InsertRoomInven(final int playerId, final int xLoc, final int yLoc, final String inven) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement insertRoomInven = null;
			int resultSet = -1;
			
			try {
				
				insertRoomInven = conn.prepareStatement("insert into roominven (playerId, xloc, yloc, inven) values (?,?,?,?)");
				
				insertRoomInven.setInt(1, playerId);
				insertRoomInven.setInt(2, xLoc);
				insertRoomInven.setInt(3, yLoc);
				insertRoomInven.setString(4, inven);

				resultSet = insertRoomInven.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the playerinven table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(insertRoomInven);
				}
			}
		});
	}
	
	public Boolean UpdateRoomInven(final int playerId, final int xLoc, final int yLoc, final String inven) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
			PreparedStatement updateRoomInven = null;
			int resultSet = -1;
			
			try {
				// retreive all attributes from both Books and Authors tables
				updateRoomInven = conn.prepareStatement("update roominven set inven = ? where playerId = ? AND xloc = ? AND yloc = ?");
				
				updateRoomInven.setInt(2, playerId);
				updateRoomInven.setInt(3, xLoc);
				updateRoomInven.setInt(4, yLoc);
				updateRoomInven.setString(1, inven);
				
				resultSet = updateRoomInven.executeUpdate();
				
				// for testing that a result was returned
				Boolean found = false;
				
				if (resultSet != -1) {
					found = true;
					return found;
				}
				
				// check if the title was found
				if (!found) {
					System.out.println("<" + playerId + "> could not be inserted into the playerinven table");
				}
				
				return found;
			} finally {
				DBUtil.closeQuietly(updateRoomInven);
				}
			}
		});
	}
	
	@Override
	public ArrayList<ObjectModel> getPlayerInventory(final int playerId) {
		return executeTransaction(new Transaction<ArrayList<ObjectModel>>() {
			@Override
			public ArrayList<ObjectModel> execute(Connection conn) throws SQLException {
			PreparedStatement getPlayerInven = null;
			ResultSet resultSet = null;
			
			try {
				getPlayerInven = conn.prepareStatement("select * from playerinven where playerId = ?");
				
				getPlayerInven.setInt(1, playerId);

				resultSet = getPlayerInven.executeQuery();
				ArrayList<ObjectModel> items = new ArrayList<ObjectModel>();
				
				Boolean found = false;
				
				if (resultSet.next()) {
					loadPlayerInventory(items, resultSet, 2);
					found = true;
				}
				
				if (!found) {
					System.out.println("<" + playerId + "> could not be found in the playerinven table");
				}
				
				return items;
			} finally {
				DBUtil.closeQuietly(getPlayerInven);
				}
			}
		});
	}
	
	@Override
	public ArrayList<ObjectModel> getRoomInventory(final int playerId, final int xLoc, final int yLoc) {
		return executeTransaction(new Transaction<ArrayList<ObjectModel>>() {
			@Override
			public ArrayList<ObjectModel> execute(Connection conn) throws SQLException {
			PreparedStatement getPlayerInven = null;
			ResultSet resultSet = null;
			
			try {
				getPlayerInven = conn.prepareStatement("select * from roominven where playerId = ? and xloc = ? and yloc = ?");
				
				getPlayerInven.setInt(1, playerId);
				getPlayerInven.setInt(2, xLoc);
				getPlayerInven.setInt(3, yLoc);

				resultSet = getPlayerInven.executeQuery();
				ArrayList<ObjectModel> items = new ArrayList<ObjectModel>();
				
				Boolean found = false;
				
				if (resultSet.next()) {
					loadPlayerInventory(items, resultSet, 4);
					found = true;
				}
				
				if (!found) {
					System.out.println("<" + playerId + "> could not be found in the roominven table");
				}
				
				return items;
			} finally {
				DBUtil.closeQuietly(getPlayerInven);
				}
			}
		});
	}
	
}
