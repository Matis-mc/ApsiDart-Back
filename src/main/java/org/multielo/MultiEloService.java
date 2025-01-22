package org.multielo;


import java.util.List;

import org.game.dto.dart.DartPerformDto;
import org.multielo.model.EloRating;

import com.arjuna.ats.arjuna.common.recoveryPropertyManager;

import jakarta.inject.Inject;

public class MultiEloService {
    
    @Inject
    private ExponentialEloFunction eloFunction; 

    /**
     * On calcule pour chaque joueur d'une partie son nouveau score elo.
     * @param gameResults
     * @param nbPlayer
     * @return une liste de nouveaux score Elo. /!\ Ils ne sont pas persistés.
     */
    public List<DartPerformDto> processNewEloRating(List<DartPerformDto> gameResults, Double nbPlayer){
        Double[] allEloPlayers = gameResults.stream()
                                        .map(p -> p.getElo())
                                        .toArray(Double[]::new);
        gameResults.stream()
            .forEach(p -> p.setElo(calculateNewEloRating(p, nbPlayer, allEloPlayers)));

        return gameResults;

    } 

    /**
     * On calcule le nouveaux score Elo d'un joueur à partir de son classement, du nombre de joueur présent et de leurs scores elo.
     */
    public Double calculateNewEloRating(DartPerformDto performDto,  Double nbPlayer, Double[] allEloPlayers){
        Double actualScore = eloFunction.calculateScore(nbPlayer, Integer.parseInt(performDto.getPositionClassement()));
        Double expectedScore = eloFunction.predicteScore(nbPlayer, performDto.getElo(), allEloPlayers);
        return eloFunction.calculateNewElo(actualScore, expectedScore, nbPlayer, performDto.getElo());
    }
}
