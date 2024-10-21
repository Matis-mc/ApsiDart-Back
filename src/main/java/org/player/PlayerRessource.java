package org.player;

import java.util.List;

import org.dart.dto.PlayerDto;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/players")
public class PlayerRessource {

    private static final Logger LOG = Logger.getLogger(PlayerRessource.class);


    @Inject
    PlayerRepository pr;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createPlayer(PlayerDto player){
        LOG.debug("Creation d'un nouveau joueur : " + player.toString());
        return pr.createPlayer(player);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlayerDto> getPlayers(){
        return pr.getAllPlayers();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlayerDto getPlayerById(String id){
        return null;
    }

    @DELETE
    @Path("{id}")
    public void deletePlayer(String id){

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updatePlayer(PlayerDto player){
    }

    
}
