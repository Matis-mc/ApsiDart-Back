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
    public double calculateNewElo(double actualScore, double expectedScore, double nbPlayer, double currentElo){

        return currentElo + eloVolatility * ( nbPlayer - 1) * (actualScore - expectedScore);

    }
    
    // --------------- Calculate actual score ------------------ \\
    public double calculateScore(double nbPlayer, double position){

        double a = Math.pow(expBase, (nbPlayer - position)) - 1;
        double b = 0;
        for (int i = 1; i <= nbPlayer; i ++){
            b += Math.pow(expBase, (nbPlayer - i)) - 1;
        }

        return a / b;

    }

    // --------------- Calculate expected score ------------------ \\
    public double predicteScore(double nbPlayer, double elo, double[] eloPlayers){

        double a = 0;
        for (int i = 0; i < nbPlayer; i ++){
            if(elo != eloPlayers[i]){
                a += 1 / (1 + Math.pow(BASE_10, (eloPlayers[i] - elo) / ctrlPred));
            }
        }
        double b = nbPlayer * ( nbPlayer - 1) / 2;

        return a / b;

    }

}
