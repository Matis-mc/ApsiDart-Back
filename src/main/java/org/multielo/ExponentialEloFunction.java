package org.multielo;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExponentialEloFunction {
    
    @ConfigProperty(name = "multielo.exponential.base")
    private double expBase;

    @ConfigProperty(name = "multielo.exponential.prediction.control")
    private double ctrlPred;

    @ConfigProperty(name = "multielo.exponential.elo.volatility")
    private double eloVolatility;

    private double BASE_10 = 10;

    // --------------- Calculate new elo ------------------ \\
    public Double calculateNewElo(Double actualScore, Double expectedScore, Double nbPlayer, Double currentElo){

        return currentElo + eloVolatility * ( nbPlayer - 1) * (actualScore - expectedScore);

    }
    
    // --------------- Calculate actual score ------------------ \\
    public Double calculateScore(Double nbPlayer, Integer position){

        Double a = Math.pow(expBase, (nbPlayer - position)) - 1;
        Double b = 0d;
        for (int i = 1; i <= nbPlayer; i ++){
            b += Math.pow(expBase, (nbPlayer - i)) - 1;
        }

        return a / b;

    }

    // --------------- Calculate expected score ------------------ \\
    public Double predicteScore(Double nbPlayer, Double elo, Double[] eloPlayers){

        Double a = 0d;
        for (int i = 0; i < nbPlayer; i ++){
            if(elo != eloPlayers[i]){
                a += 1 / (1 + Math.pow(BASE_10, (eloPlayers[i] - elo) / ctrlPred));
            }
        }
        Double b = nbPlayer * ( nbPlayer - 1) / 2;

        return a / b;

    }

}
