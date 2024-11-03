package org.game;

import java.util.List;

import org.game.controlGame.GameFactory;
import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.game.model.Game;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.inject.Inject;
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

    @Inject
    private GameFactory gf;

    @POST
    public Long createGame(GameCreationDto payload) throws UnsupportedDataTypeException{
        return gf.initGame(payload);
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
    @Path("/dart")
    public List<Game> getGamesOnCriteria(String typeJeu, String variante, String player, String date){
        return null;
    }

    // endpoint temporaire de debug
    @GET
    @Path("/temp/dgames")
    public List<DGame> getAllDartGames(){
        return DGame.listAll();
    }

    @GET
    @Path("/temp/dperform")
    public List<DPerform> getAllDartPerform(){
        return DPerform.listAll();
    }
    
}
