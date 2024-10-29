package org.game;

import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;

public abstract class GameControl {
    
    public abstract Long initGame(GameCreationDto paylaod);

    public abstract void performOnGame(GamePerformDto payload);

    public abstract void terminateGame();

}
