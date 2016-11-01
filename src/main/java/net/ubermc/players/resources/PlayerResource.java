package net.ubermc.players.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.ubermc.players.model.Player;
import net.ubermc.players.service.PlayerService;

@Path("/players")
public class PlayerResource {

	PlayerService playerService = new PlayerService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getAllPlayers() {
	 return playerService.getAllPlayers();
	}
	
	@GET
	@Path("/{playername}")
	@Produces(MediaType.APPLICATION_JSON)
	public Player getPlayer(@PathParam("playername") String playername) {
		System.out.println("GET Request for: " + playername);
		
		Player player = playerService.getPlayer(playername);

		System.out.println("returning:" + player.getName());
		System.out.println("returning:" + player.getGender());
		
		return playerService.getPlayer(player.getName());
		
	}
	
}
