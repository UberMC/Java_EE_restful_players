package net.ubermc.players.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.ubermc.players.model.Player;

public class DatabaseClass {
	static boolean cycle = true;
	static long iT;
	static long eT;
	private static DatabaseManager dbman = DatabaseManager.getInstance();

	private static Map<String, Player> players = new HashMap<>();
	private static Map<String, Player> playernametoobject = new HashMap<>();

	private static DatabasePrepared dbprepared = new DatabasePrepared();
	
	public static Map<String, Player> getPlayers() {
		return players;
	}
	public static DatabaseManager getDatabaseManager(){
		return dbman;
	}
	
	public static Map<String, Player> getPlayerNametoObject(){
		return playernametoobject;
	}
	static{
		CheckOnlineStatus();
	}
	
	
    static void timings(String type) {

        if (cycle) {
            iT = System.currentTimeMillis();
        } else {
            eT = System.currentTimeMillis();
            System.out.println(type + " Timing:" + Long.toString(eT - iT) + " (ms)");
        }
        cycle = !cycle;
    }
    
    static void CheckOnlineStatus() {
    	System.out.println("check online status");
    	String name;
    	String motto;
    	String gender;
    	long points;
    	String last;
    	int online;
    	String server_id;
    	String uuid;
    	int elohg;
    	int eloluckywars;
    	int elopvpop;
    	
    	String friend1;
    	String friend2;
    	
    	HashSet<String> queryplayersuuid = new HashSet<String>();
    
        dbman.connect();
        try {
        	timings("db select");
            ResultSet rs;
            
            rs = dbprepared.get_RecentPlayersOnline().executeQuery();
            if ((rs != null)) {
                while (rs.next()) {
                //	System.out.println("get sql row");
                	
                    uuid = name = rs.getString(2);
                	name = rs.getString(3);
                    motto = rs.getString(9);
                    gender = rs.getString(7);
                    points = rs.getLong(10);
                    last = rs.getString(4);
                    online = rs.getInt(6);
                    server_id = rs.getString(5);
                    
                	Player player = new Player(name, motto, gender, points, last, online, server_id, uuid);
                	players.put(uuid, player);
                	playernametoobject.put(name, player);
                	queryplayersuuid.add(uuid);
                }
            }

            rs = dbprepared.get_Friends(queryplayersuuid).executeQuery();
            if ((rs != null)) {
                while (rs.next()) {
                	friend1 = rs.getString(2);
                    friend2 = rs.getString(3);
  
                    if (players.containsKey(friend1)){
                    	if (!players.get(friend1).friends_uuid.contains(friend2)){
                    	players.get(friend1).friends_uuid.add(friend2);
                    	}
                    }
                    if (players.containsKey(friend2)){
                    	if (!players.get(friend2).friends_uuid.contains(friend1)){
                    	players.get(friend2).friends_uuid.add(friend1);
                    	}
                    }
                }
            }
       
        timings("db select");
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
