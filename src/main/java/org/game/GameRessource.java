package org.game;

import java.util.List;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.model.Game;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/game")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GameRessource {

    @POST
    public Long createGame(GameCreationDto payload){
        return null;
    }

    @POST
    @Path("/perform")
    public void performOnGame(GamePerformDto payload){

    }

    @GET
    public List<Game> getAllGames(){
        return List.of();        
    }

    @GET()
    @Path("/criteria")
    public List<Game> getGamesOnCriteria(String typeJeu, String variante, String player, String date){
        return List.of();
    }
    
}
