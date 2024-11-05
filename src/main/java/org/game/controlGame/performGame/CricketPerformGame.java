package org.game.controlGame.performGame;

import org.game.dto.GamePerformDto;

public interface CricketPerformGame {

    String getMode();

    void persistPerformGame(GamePerformDto dto);

}
