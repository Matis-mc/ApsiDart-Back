package org.dart.model.game;

import java.util.List;

import org.dart.model.DartPlayer;
import org.dart.model.Throw;

public abstract sealed class Game permits CricketGame{

    public String id;
    public List<DartPlayer> players;
    public String gameName;
    public String variant;

    public void playerThrow(Throw throwPlayed, DartPlayer player){
        // do stuff here
    }
    
}
