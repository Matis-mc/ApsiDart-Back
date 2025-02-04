package org.game;

import java.util.List;

import org.game.controlGame.GameFactory;
import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.dto.GamePerformRetourDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.game.model.Game;
import org.stat.entity.DGlobalPlayerStat;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/game")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class GameRessource {

    @Inject
    private GameFactory gf;

    @POST
    public Long createGame(GameCreationDto payload) throws UnsupportedDataTypeException{
        return gf.initGame(payload);
    }

    @POST
    @Path("/perform")
    public GamePerformRetourDto performOnGame(GamePerformDto payload){
        return gf.performGame(payload);
    }

    @POST
    @Path("/end")
    public void endGame(GamePerformDto payload){
        gf.endGame(payload);
    }

    @GET
    @Path("dart/{id}")
    @Transactional
    public Game getGameById(@PathParam("id") Long id){
        DGame dGame = DGame.findById(id);
        dGame.dPerform = DPerform.findByIdGame(id);
        return DartMapper.mapEntityToModel(dGame);
    }

    @GET()
    @Path("/dart")
    @Transactional    
    public List<Game> getDartGames(){
        List<DGame> dgames = DGame.findAll().list();
        return dgames
            .stream()
            .map(d -> {
                d.dPerform = DPerform.findByIdGame(d.id);
                return DartMapper.mapEntityToModel(d);
            })
            .toList();
    }

    
}
