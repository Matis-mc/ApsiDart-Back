package org.game.controlGame;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.dto.GamePerformRetourDto;

public interface GameControl {

    String getType();
    
    Long initGame(GameCreationDto paylaod);

    GamePerformRetourDto performOnGame(GamePerformDto payload);

    void terminateGame(GamePerformDto payload);

}
