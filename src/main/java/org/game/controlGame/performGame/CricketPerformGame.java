package org.game.controlGame.performGame;

import org.game.dto.GamePerformDto;

public interface CricketPerformGame {

    String getType();

    void persistPerformGame(GamePerformDto dto);

}
