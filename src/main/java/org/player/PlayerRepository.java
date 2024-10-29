package org.player;


import org.player.entity.Player;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlayerRepository  implements PanacheRepository<Player>{

}