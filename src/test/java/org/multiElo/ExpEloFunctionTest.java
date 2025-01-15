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

    Double[] eloPlayers = {1200d, 1000d, 800d};

    @Test
    void scoreProcessor_3players(){

        Double scoreFirst = tested.calculateScore(3d, 1);
        Double scoreSecond = tested.calculateScore(3d, 2);
        Double scoreThird = tested.calculateScore(3d, 3);
        assertEquals(0.75, scoreFirst);
        assertEquals(0.25, scoreSecond);
        assertEquals(0, scoreThird);
        assertEquals(1, scoreFirst + scoreSecond + scoreThird);
    }

    @Test
    void scorePredictor_3players(){

        Double predictionFirst = tested.predicteScore(3d, 1200d, eloPlayers);
        Double predictionSecond = tested.predicteScore(3d, 1000d, eloPlayers);
        Double predictionThird = tested.predicteScore(3d, 800d, eloPlayers);
        assertEquals(true, predictionFirst > predictionSecond);
        assertEquals(true, predictionSecond > predictionThird);
        assertEquals(1, predictionFirst + predictionSecond + predictionThird);
    }

    @Test
    void caclulateNewElo_bestFinishFirst(){

        Double scoreFirst = tested.calculateScore(3d, 1);
        Double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreFirst, predictionBest, 3d, 1200d);

        assertEquals(true, newElo > 1200);

    }

    @Test
    void caclulateNewElo_worstFinishFirst(){

        Double scoreFirst = tested.calculateScore(3d, 1);
        Double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreFirst, predictionWorst, 3d, 800d);

       assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_worstFinishMiddle(){

        Double scoreMiddle = tested.calculateScore(3d, 2);
        Double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }

    @Test
    void caclulateNewElo_firstFinishMiddle(){

        Double scoreMiddle = tested.calculateScore(3d, 2);
        Double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreMiddle, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }

    @Test
    void caclulateNewElo_WorstFinishMiddle(){

        Double scoreMiddle = tested.calculateScore(3d, 2);
        Double predictionWorst = tested.predicteScore(3d, 800d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreMiddle, predictionWorst, 3d, 800d);

        assertEquals(true, newElo > 800);

    }
    
    @Test
    void caclulateNewElo_firstFinishLast(){

        Double scoreLast = tested.calculateScore(3d, 3);
        Double predictionBest = tested.predicteScore(3d, 1200d, eloPlayers);
        Double newElo = tested.calculateNewElo(scoreLast, predictionBest, 3d, 1200d);

        assertEquals(true, newElo < 1200);

    }
    
}
