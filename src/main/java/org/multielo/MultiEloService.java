package org.multielo;


import java.util.List;

import org.game.model.ClassementElement;
import org.multielo.model.EloRating;

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
    public List<EloRating> processNewEloRating(List<ClassementElement> gameResults, Double nbPlayer){
        Double[] allEloPlayers = gameResults.stream()
                                        .map(c -> c.elo())
                                        .toArray(Double[]::new);
        return gameResults.stream()
            .map(ce -> new EloRating(ce.idPlayer(), calculateNewEloRating(ce, nbPlayer, allEloPlayers)))
            .toList();
    } 

    /**
     * On calcule le nouveaux score Elo d'un joueur à partir de son classement, du nombre de joueur présent et de leurs scores elo.
     */
    public Double calculateNewEloRating(ClassementElement ce,  Double nbPlayer, Double[] allEloPlayers){
        Double actualScore = eloFunction.calculateScore(nbPlayer, ce.positionClassement());
        Double expectedScore = eloFunction.predicteScore(nbPlayer, ce.elo(), allEloPlayers);
        return eloFunction.calculateNewElo(actualScore, expectedScore, nbPlayer, ce.elo());
    }
}
