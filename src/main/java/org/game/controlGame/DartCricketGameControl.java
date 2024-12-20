package org.game.controlGame;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.Constant;
import org.game.controlGame.performGame.CricketPerformGame;
import org.game.dto.GameCreationDto;
import org.game.dto.GamePerformDto;
import org.game.dto.GamePerformRetourDto;
import org.game.entity.DGame;
import org.game.entity.DPerform;
import org.player.entity.Player;

import io.quarkus.arc.All;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DartCricketGameControl implements GameControl {

    public final String TYPE = "DACKT";

    @Inject
    @All
    List<CricketPerformGame> performGames;

    private Map<String, CricketPerformGame> controlMap = new HashMap<String, CricketPerformGame>();

    @PostConstruct
    void initGameMap(){
        performGames.forEach(g -> {
            controlMap.put(g.getType(), g);
        });
    }

    @Override
    @Transactional
    public Long initGame(GameCreationDto paylaod) {
        DGame dGame = createGame(paylaod);
        addParticipant(dGame, paylaod.participants());
        return dGame.id;
    }

    @Override
    public GamePerformRetourDto performOnGame(GamePerformDto payload) {
       return controlMap.get(getModeJeu(payload)).persistPerformGame(payload);
    }

    @Override
    public void terminateGame(GamePerformDto payload) {
        controlMap.get("TOUR").persistEndGame(payload);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    private DGame createGame(GameCreationDto payload){
        DGame dGame = new DGame();
        dGame.date = LocalDate.now();
        dGame.statut = Constant.Game.STATUT_IN_PROGRESS;
        dGame.type = payload.typeJeu().code().name();
        dGame.persistAndFlush();
        return dGame;
    }

    private void addParticipant(DGame dGame, List<Player> participants){
        participants.forEach(p -> DPerform.createDPerform(dGame, p, participants.indexOf(p) + 1));
    }

    private String getModeJeu(GamePerformDto dto){
        return dto.modeJeu().properties().get("mode").toString();
    }
    
}
