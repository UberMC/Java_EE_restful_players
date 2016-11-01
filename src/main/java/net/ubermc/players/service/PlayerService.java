package net.ubermc.players.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.ubermc.players.database.DatabaseClass;
import net.ubermc.players.exception.DataNotFoundException;
import net.ubermc.players.model.Player;

public class PlayerService {

	private Map<String, Player> players = DatabaseClass.getPlayers();
	private Map<String, Player> playernametoobject = DatabaseClass.getPlayerNametoObject();
	
	public PlayerService() {

	}
	
	public List<Player> getAllPlayers() {
		return new ArrayList<Player>(players.values()); 
	}
	
	public Player getPlayer(String playername) {
		Player player = playernametoobject.get(playername);
		System.out.println(player.getName());
		if (player == null) {
			throw new DataNotFoundException("Message with id " + playername + " not found");
		}
		return player;
	}
	

	
	
}
