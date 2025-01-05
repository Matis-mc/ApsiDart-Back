package org.multiElo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.multielo.ExponentialEloFunction;

import io.quarkus.test.component.QuarkusComponentTest;
import io.quarkus.test.component.TestConfigProperty;
import jakarta.inject.Inject;

@QuarkusComponentTest
@TestConfigProperty(key = "multielo.exponential.base", value = "2")
@TestConfigProperty(key = "multielo.exponential.prediction.control", value = "400")
@TestConfigProperty(key = "multielo.exponential.elo.volatility", value = "32")

public class ExpEloFunctionTest {

    @Inject
    ExponentialEloFunction tested;

    @Test
    void scoreProcessor_3players(){

        double scoreFirst = tested.calculateScore(3, 1);
        double scoreSecond = tested.calculateScore(3, 2);
        double scoreThird = tested.calculateScore(3, 3);
        assertEquals(0.75, scoreFirst);
        assertEquals(0.25, scoreSecond);
        assertEquals(0, scoreThird);
        assertEquals(1, scoreFirst + scoreSecond + scoreThird);
    }

    @Test
    void scorePredictor_3players(){

        double[] eloPlayers = {1200, 1000, 800};

        double predictionFirst = tested.predicteScore(3, 1200, eloPlayers);
        double predictionSecond = tested.predicteScore(3, 1000, eloPlayers);
        double predictionThird = tested.predicteScore(3, 800, eloPlayers);
        assertEquals(true, predictionFirst > predictionSecond);
        assertEquals(true, predictionSecond > predictionThird);
        assertEquals(1, predictionFirst + predictionSecond + predictionThird);
    }

    @Test
    void caclulateNewElo_bestFinishFirst(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreFirst = tested.calculateScore(3, 1);
        double predictionBest = tested.predicteScore(3, 1200, eloPlayers);
        double newElo = tested.calculateNewElo(scoreFirst, predictionBest, 3d, 1200d);

        assertEquals(true, newElo > 1200);

    }

    @Test
    void caclulateNewElo_worstFinishFirst(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreFirst = tested.calculateScore(3, 1);
        double predictionWorst = tested.predicteScore(3, 800, eloPlayers);
        double newElo = tested.calculateNewElo(scoreFirst, predictionWorst, 3d, 800d);

       assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_worstFinishMiddle(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreMiddle = tested.calculateScore(3, 2);
        double predictionWorst = tested.predicteScore(3, 800, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_firstFinishMiddle(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreMiddle = tested.calculateScore(3, 2);
        double predictionBest = tested.predicteScore(3, 1200, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }

    @Test
    void caclulateNewElo_WorstFinishMiddle(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreMiddle = tested.calculateScore(3, 2);
        double predictionWorst = tested.predicteScore(3, 800, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }
    
    @Test
    void caclulateNewElo_firstFinishLast(){

        double[] eloPlayers = {1200, 1000, 800};
        double scoreLast = tested.calculateScore(3, 3);
        double predictionBest = tested.predicteScore(3, 1200, eloPlayers);
        double newElo = tested.calculateNewElo(scoreLast, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }
    
}
