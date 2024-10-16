package org.dart.model.game;

import java.util.List;

import org.dart.dto.PlayerDto;
import org.dart.model.Throw;

public abstract sealed class Game permits CricketGame{

    public String id;
    public List<PlayerDto> players;
    public String gameName;
    public String variant;

    public void playerThrow(Throw throwPlayed, PlayerDto player){
        // do stuff here
    }
    
}
