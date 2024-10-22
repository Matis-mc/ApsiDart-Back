package org.player;


import java.util.List;

import org.dart.dto.PlayerDto;
import org.player.entity.Player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PlayerRepository {

    @Inject
    EntityManager em;

    @Transactional
    public Long createPlayer(PlayerDto p){
        Player pe = new Player();
        pe.firstName = p.prenom();
        pe.lastName = p.nom();
        pe.pseudo = p.pseudo();
        pe.persist();
        return pe.id;
        
    }

    public List<PlayerDto> getAllPlayers(){
        List<Player> players = Player.listAll();
        return players
            .stream()
            .map(p -> PlayerMapper.entityToDto(p))
            .toList();
    }

    public PlayerDto getPlayer(Long id){
        Player p =  Player.findById(id);
        return PlayerMapper.entityToDto(p);
    }

    @Transactional
    public boolean deletePlayer(Long id){
        Player p = Player.findById(id);
        if(p == null){
            throw new NotFoundException();
        }
        return Player.deleteById(id);
    }



}