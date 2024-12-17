package org.game.controlGame.performGame;

import org.game.dto.GamePerformDto;
import org.game.dto.GamePerformRetourDto;

public interface CricketPerformGame {

    String getType();

    GamePerformRetourDto persistPerformGame(GamePerformDto dto);

    void persistEndGame(GamePerformDto dto);

}
