package org.game.controlGame;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DartX01GameControl implements GameControl {

    public final String TYPE = "DAX01";

    @Override
    public Long initGame(GameCreationDto paylaod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initGame'");
    }

    @Override
    public void performOnGame(GamePerformDto payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performOnGame'");
    }

    @Override
    public void terminateGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'terminateGame'");
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
