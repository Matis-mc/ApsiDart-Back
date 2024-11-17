package org.game;

import java.util.List;

import org.game.controlGame.GameFactory;
import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.game.model.Game;
import org.hibernate.Hibernate;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
    public void performOnGame(GamePerformDto payload){
        gf.performGame(payload);
    }

    @GET()
    @Path("/dart")
    @Transactional
    public List<Game> getDartGames(){
        List<DGame> dgames = DGame.listAll();
        dgames.forEach(d -> Hibernate.initialize(d.dPerform)
        );
        return dgames
            .stream()
            .map(d -> DartMapper.mapEntityToModel(d))
            .toList();
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
