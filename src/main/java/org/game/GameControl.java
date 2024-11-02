package org.game;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

public interface GameControl {

    String getType();
    
    Long initGame(GameCreationDto paylaod);

    void performOnGame(GamePerformDto payload);

    void terminateGame();

}
