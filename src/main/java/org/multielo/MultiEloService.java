package org.multielo;

import java.util.List;

import org.game.dto.dart.DartPerformDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
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
        double[] allEloPlayers = gameResults.stream()
                                        .map(p -> p.getElo())
                                        .mapToDouble(Double::doubleValue)
                                        .toArray();
        gameResults.stream()
            .forEach(p -> p.setElo(calculateNewEloRating(p, nbPlayer, allEloPlayers)));
        return gameResults;

    } 

    /**
     * On calcule le nouveaux score Elo d'un joueur à partir de son classement, du nombre de joueur présent et de leurs scores elo.
     */
    public double calculateNewEloRating(DartPerformDto performDto,  double nbPlayer, double[] allEloPlayers){
        double actualScore = eloFunction.calculateScore(nbPlayer, Integer.parseInt(performDto.getPositionDepart()));
        double expectedScore = eloFunction.predicteScore(nbPlayer, performDto.getElo(), allEloPlayers);
        return eloFunction.calculateNewElo(actualScore, expectedScore, nbPlayer, performDto.getElo());
    }
}
