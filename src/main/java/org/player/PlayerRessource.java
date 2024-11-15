package org.player;

import java.util.List;
import java.util.Objects;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.player.entity.Player;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/players")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerRessource {

    private static final Logger LOG = Logger.getLogger(PlayerRessource.class);


    @Inject
    PlayerRepository pr;

    @POST
    @Transactional
    public Long createPlayer(Player player){
        checkPlayerProperties(player);
        LOG.debug("Creation d'un nouveau joueur : " + player.toString());
        pr.persist(player);
        return player.id;
    }

    @GET
    public List<Player> getPlayers(){
        return pr.listAll();
    }

    @GET
    @Path("/{id}")
    public Player getPlayerById(@PathParam long id){
        return pr.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletePlayer(@PathParam long id){
        pr.deleteById(id);
    }

    private void checkPlayerProperties(Player player){
        if(Objects.isNull(player.firstName)
        || Objects.isNull(player.lastName)){
            throw new BadRequestException("Le(s) champ(s) firstName ou lastName n'est pas renseigné.");
        }
    }

    
}
