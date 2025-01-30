package org.multiElo;

import org.multielo.ExponentialEloFunction;
import org.multielo.MultiEloService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;

@QuarkusComponentTest
public class MultiEloServiceTest {

    @InjectMock
    ExponentialEloFunction expEloFunction;

    @Inject
    MultiEloService tested;
/*
    @BeforeAll
    public static void setup() {
        ExponentialEloFunction spy = Mockito.spy(ExponentialEloFunction.class);
        QuarkusMock.installMockForInstance(spy, ExponentialEloFunction.class);  
    }
        

    @Test
    void calculateEloForAll_EqualsPlayer(){

        List<ClassementElement> classementElements = List.of(
            new ClassementElement(1l, 1200d, 1, 50d, null),
            new ClassementElement(2l, 1200d, 3, 200d, null),
            new ClassementElement(3l, 1200d, 2, 100d, null)
        );

        List<EloRating> newRatings = tested.processNewEloRating(classementElements, 3d);
        EloRating ratingPlayer1 = newRatings.stream().filter(p -> p.idPlayer() == 1l).findFirst().get();
        EloRating ratingPlayer2 = newRatings.stream().filter(p -> p.idPlayer() == 2l).findFirst().get();
        EloRating ratingPlayer3 = newRatings.stream().filter(p -> p.idPlayer() == 3l).findFirst().get();

        assertEquals(true, ratingPlayer1.eloScore() > ratingPlayer3.eloScore());
        assertEquals(true, ratingPlayer3.eloScore() > ratingPlayer2.eloScore());

        }
        */
    
}
