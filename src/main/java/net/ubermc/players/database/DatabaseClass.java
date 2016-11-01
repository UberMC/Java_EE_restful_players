package net.ubermc.players.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.ubermc.players.model.Player;

public class DatabaseClass {
	static boolean cycle = true;
	static long iT;
	static long eT;
	private static DatabaseManager dbman = DatabaseManager.getInstance();

	private static Map<String, Player> players = new HashMap<>();
	private static Map<String, Player> playernametoobject = new HashMap<>();

	
	public static Map<String, Player> getPlayers() {
		return players;
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
    
        dbman.connect();
        try {
        	timings("db select");
            if (dbman == null) {
                System.out.println("dbman null");
            }  
            
            PreparedStatement s2 = dbman.createStatement("SELECT * FROM friend__players_data where status = 1 LIMIT 500");
            //PreparedStatement s2 = dbman.createStatement("SELECT * FROM friend__players_data LEFT JOIN friend__friends ON friend__players_data.player_uuid=friend__friends.player_x_uuid OR friend__players_data.player_uuid=friend__friends.player_y_uuid LIMIT 500");
            ResultSet rs;
            
            rs = s2.executeQuery();
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
                    /*
                    friend1 = rs.getString(12);
                    friend2 = rs.getString(13);
                    String otherfrienduuid;
                    if (uuid.equals(friend1)){
                    	otherfrienduuid=friend2;
                    }else{
                    	otherfrienduuid=friend1;
                    }
                    */
                    	Player player = new Player(name, motto, gender, points, last, online, server_id, uuid);
                    	players.put(uuid, player);
                    	playernametoobject.put(name, player);
                    	
                   
                }
            }
            
            //LIMIT 100
            //Multiple where statements: 600ms
            //using in statement: 280ms : 2000 lines (3x work)
            //using join 400ms (But did double work on friend_friend since checked friendx and friendy 831 lines
            
            
            String whereplayer = "WHERE player_x_uuid in ('";
            for (String playername: players.keySet()){
            	whereplayer  =  whereplayer + players.get(playername).getUuid() +  "','";
            }
            whereplayer = whereplayer.substring(0, whereplayer.length()-2);
            
            whereplayer = whereplayer + ")";
       
            	PreparedStatement s3 = dbman.createStatement("SELECT * FROM friend__friends " + whereplayer);
                ResultSet rs3;
                rs3 = s3.executeQuery();
                if ((rs3 != null)) {
                    while (rs3.next()) {
                    	friend1 = rs3.getString(2);
                        friend2 = rs3.getString(3);
                       
                        
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
            
                String whereplayery = "WHERE player_y_uuid in ('";
                for (String playername: players.keySet()){
                	whereplayery  =  whereplayery + players.get(playername).getUuid() +  "','";
                }
                whereplayery = whereplayery.substring(0, whereplayery.length()-2);
                
                whereplayery = whereplayery + ") and confirmed ='1'";
           
                	PreparedStatement s4 = dbman.createStatement("SELECT * FROM friend__friends " + whereplayery);
                    ResultSet rs4;
                    rs4 = s4.executeQuery();
                    if ((rs4 != null)) {
                        while (rs4.next()) {
                        	friend1 = rs4.getString(2);
                            friend2 = rs4.getString(3); 
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
