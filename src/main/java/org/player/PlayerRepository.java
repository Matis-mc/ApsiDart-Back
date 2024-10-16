package org.player;


import org.dart.dto.PlayerDto;
import org.player.entity.Player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PlayerRepository {

    @Inject
    EntityManager em;

    @Transactional
    public String createPlayer(PlayerDto player){
        Player entity = new Player();
        entity.setFistName(player.prenom());
        entity.setLastName(player.nom());
        entity.setPseudo(player.pseudo());
        em.persist(player);
        return entity.getId();
    }

}