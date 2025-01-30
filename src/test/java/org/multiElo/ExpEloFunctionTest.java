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

    double[] eloPlayers = {1200d, 1000d, 800d};

    @Test
    void scoreProcessor_3players(){

        double scoreFirst = tested.calculateScore(3d, 1);
        double scoreSecond = tested.calculateScore(3d, 2);
        double scoreThird = tested.calculateScore(3d, 3);
        assertEquals(0.75, scoreFirst);
        assertEquals(0.25, scoreSecond);
        assertEquals(0, scoreThird);
        assertEquals(1, scoreFirst + scoreSecond + scoreThird);
    }

    @Test
    void scorePredictor_3players(){

        double predictionFirst = tested.predicteScore(3d, 1200d, eloPlayers);
        double predictionSecond = tested.predicteScore(3d, 1000d, eloPlayers);
        double predictionThird = tested.predicteScore(3d, 800d, eloPlayers);
        assertEquals(true, predictionFirst > predictionSecond);
        assertEquals(true, predictionSecond > predictionThird);
        assertEquals(1, predictionFirst + predictionSecond + predictionThird);
    }

    @Test
    void caclulateNewElo_bestFinishFirst(){

        double scoreFirst = tested.calculateScore(3d, 1);
        double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreFirst, predictionBest, 3d, 1200d);

        assertEquals(true, newElo > 1200);

    }

    @Test
    void caclulateNewElo_worstFinishFirst(){

        double scoreFirst = tested.calculateScore(3d, 1);
        double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreFirst, predictionWorst, 3d, 800d);

       assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_worstFinishMiddle(){

        double scoreMiddle = tested.calculateScore(3d, 2);
        double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_firstFinishMiddle(){

        double scoreMiddle = tested.calculateScore(3d, 2);
        double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }

    @Test
    void caclulateNewElo_WorstFinishMiddle(){

        double scoreMiddle = tested.calculateScore(3d, 2);
        double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }
    
    @Test
    void caclulateNewElo_firstFinishLast(){

        double scoreLast = tested.calculateScore(3d, 3);
        double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        double newElo = tested.calculateNewElo(scoreLast, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }
    
}
