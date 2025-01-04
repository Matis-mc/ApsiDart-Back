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
public class ExpEloFunctionTest {

    @Inject
    ExponentialEloFunction tested;

    @Test
    void scoreProcessor_3players(){

        double scoreFirst = tested.scoreProcessor(3, 1);
        double scoreSecond = tested.scoreProcessor(3, 2);
        double scoreThird = tested.scoreProcessor(3, 3);
        assertEquals(0.75, scoreFirst);
        assertEquals(0.25, scoreSecond);
        assertEquals(0, scoreThird);
        assertEquals(1, scoreFirst + scoreSecond + scoreThird);
    }

    @Test
    void scorePredictor_3players(){

        double[] eloPlayers = {1200, 1000, 800};

        double predictionFirst = tested.scorePredictor(3, 1200, eloPlayers);
        double predictionSecond = tested.scorePredictor(3, 1000, eloPlayers);
        double predictionThird = tested.scorePredictor(3, 800, eloPlayers);
        assertEquals(true, predictionFirst > predictionSecond);
        assertEquals(true, predictionSecond > predictionThird);
        assertEquals(1, predictionFirst + predictionSecond + predictionThird);
    }
    
}
