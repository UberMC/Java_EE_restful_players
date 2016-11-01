package net.ubermc.players.database;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;

public class DatabasePrepared {

	private DatabaseManager dbman;
	private PreparedStatement p_getrecentplayersonline;
	private PreparedStatement p_getallplayers;
	
	//PreparedStatement s2 = dbman.createStatement("SELECT * FROM friend__players_data LEFT JOIN friend__friends ON friend__players_data.player_uuid=friend__friends.player_x_uuid OR friend__players_data.player_uuid=friend__friends.player_y_uuid LIMIT 500");
    //Multiple where statements: 600ms
    //using in statement: 280ms : 2000 lines (3x work)
    //using join 400ms (But did double work on friend_friend since checked friendx and friendy 831 lines
    
	public DatabasePrepared(){
		dbman = DatabaseClass.getDatabaseManager();
		dbman.connect();
		p_getrecentplayersonline = dbman.createStatement("SELECT * FROM friend__players_data where status = '1' LIMIT 500");
		p_getallplayers = dbman.createStatement("SELECT * FROM friend__players_data LIMIT 500");
	}
	
	public PreparedStatement get_Friends(HashSet<String> playeruuids){
		String parambuilder = "";
		for (String str : playeruuids){
			parambuilder = parambuilder + "?,";
		}
		parambuilder = parambuilder.substring(0, parambuilder.length()-1);
		PreparedStatement p_getfriends = dbman.createStatement("SELECT * FROM friend__friends WHERE player_x_uuid in (" + parambuilder+ ") OR player_y_uuid in (" + parambuilder+ ") and confirmed ='1'");
		try {
			
		//Arrays Not supported in MySQL :(
		//Array array = dbman.conn.createArrayOf("VARCHAR", playeruuids.toArray(new String[playeruuids.size()]));

		int counter = 0;
		for (String str : playeruuids){
			counter = counter + 1;
			p_getfriends.setString(counter, str);
		}
		for (String str : playeruuids){
			counter = counter + 1;
			p_getfriends.setString(counter, str);
		}
		

		System.out.println(p_getfriends.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p_getfriends;
	}
	
	public PreparedStatement get_RecentPlayersOnline() {
		return p_getrecentplayersonline;
	}

	public void get_RecentPlayersOnline(
			PreparedStatement p_getrecentplayersonline) {
		this.p_getrecentplayersonline = p_getrecentplayersonline;
	}

	public PreparedStatement get_AllPlayers() {
		return p_getallplayers;
	}

	
}
