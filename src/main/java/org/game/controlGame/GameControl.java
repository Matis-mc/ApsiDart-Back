package org.game.controlGame;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

public interface GameControl {

    String getType();
    
    Long initGame(GameCreationDto paylaod);

    void performOnGame(GamePerformDto payload);

    void terminateGame();

}
