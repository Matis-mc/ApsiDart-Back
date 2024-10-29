package org.game;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DartCricketGameControl extends GameControl {

    @Override
    public Long initGame(GameCreationDto paylaod) {

    }

    @Override
    public void performOnGame(GamePerformDto payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void terminateGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
