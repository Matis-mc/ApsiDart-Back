package org.stat;

import org.common.exceptions.FunctionalException;
import org.jboss.logging.Logger;
import org.stat.dto.DartGameStat;
import org.stat.entity.DGlobalPlayerStat;
import org.stat.service.DStatService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/stat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatRessource {

    private static final Logger LOG = Logger.getLogger(StatRessource.class);

    @Inject
    DStatService dStatService;

    @GET
    @Path("/dart/{id}")
    public DartGameStat getStatByIdGame(@PathParam(value = "id") String id) throws FunctionalException{
        return dStatService.calculateStatForCricketGame(id);
    }

    @GET
    @Path("/dart/player/{id}")
    public DGlobalPlayerStat getStatByIdPlayer(@PathParam(value = "id") String id) throws FunctionalException{
        return DGlobalPlayerStat.getLastStatByIdJoueur(id)
            .orElseThrow(() -> new FunctionalException("Aucune statistique n'est disponible pour le jouer d'id " + id));

    }
    
}
