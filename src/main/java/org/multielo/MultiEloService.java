package org.multielo;


import java.util.List;

import org.game.model.ClassementElement;

import jakarta.inject.Inject;

public class MultiEloService {
    
    @Inject
    private ExponentialEloFunction eloFunction;

    
    public List<ClassementElement> processNewEloRating(List<ClassementElement> gameResult, double nbPlayer){
        return null;

    } 

    public double calculateNewEloRating(ClassementElement ce,  double nbPlayer, double[] allEloPlayers){
        double actualScore = eloFunction.calculateScore(nbPlayer, ce.positionClassement());
        double expectedScore = eloFunction.predicteScore(nbPlayer, ce.elo(), allEloPlayers);
        return eloFunction.calculateNewElo(actualScore, expectedScore, nbPlayer, ce.elo());
    }
}
