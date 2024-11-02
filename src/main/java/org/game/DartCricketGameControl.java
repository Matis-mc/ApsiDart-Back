package org.game;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DartCricketGameControl implements GameControl {

    public final String TYPE = "DACKT";

    @Override
    public Long initGame(GameCreationDto paylaod) {
        
        return 1L;

    }

    @Override
    public void performOnGame(GamePerformDto payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
