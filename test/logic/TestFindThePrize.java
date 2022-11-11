package logic;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class TestFindThePrize {

    private static final int numberOfOptions = 4;
    private static final int numberOfPrizes = 2;
    private static final int numberOfRounds = 3;

    @Mock
    private Generator generatorMock;


    //Unit test example for addPoint function
    @Test
    public void testPointIncrease () {
        FindThePrize game = FindThePrize.init(2 , 1, 2, new Generator());
        game.addPoint();
        assertEquals(1, game.getNumberOfPoints());
    } //This given example will discover that addPoint() method decreases number of points instead of increasing it

    @Test
    public void testNegativePrizesNumber()
    {
        assertThrows("Number of prizes should be greater than 0", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , -1, 2, new Generator());
                });
    }
    @Test
    public void testPrizesNumberIsZero()
    {
        assertThrows("Number of prizes should be greater than 0", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , 0, 2, new Generator());
                });
    }
    @Test
    public void testNegativeRoundNumber()
    {
        assertThrows("Number of rounds should be greater than 0", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , 1, -1, new Generator());
                });
    }
    @Test
    public void testRoundNumberIsZero()
    {
        assertThrows("Number of rounds should be greater than 0", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , 1, 0, new Generator());
                });
    }

    @Test
    public void testNumberOfOptionsLessThanNumberOfPrizes()
    {
        assertThrows("Number of options should be greater than number of prizes", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , 3, 2, new Generator());
                });
    }

    @Test
    public void testNumberOfOptionsEqualsNumberOfPrizes()
    {
        assertThrows("Number of options should be greater than number of prizes", InvalidParameterException.class,
                () ->
                {
                    FindThePrize game = FindThePrize.init(2 , 2, 2, new Generator());
                });

    }

    @Test
    public void testCorrectGuessReturnsTrue()
    {
        FindThePrize game = FindThePrize.init(numberOfOptions, numberOfPrizes, numberOfRounds, generatorMock);

        List<Integer> prizeSpots = Arrays.asList(1, 3);
        Mockito.when(generatorMock.generatePositions(numberOfPrizes, numberOfOptions)).thenReturn(prizeSpots);

        game.playRound(2);
        assertTrue(game.guess(2));

        game.playRound(4);
        assertTrue(game.guess(4));
    }
    @Test
    public void testIncorrectGuessReturnsFalse()
    {
        FindThePrize game = FindThePrize.init(numberOfOptions, numberOfPrizes, numberOfRounds, generatorMock);

        List<Integer> prizeSpots = Arrays.asList(1, 3);
        Mockito.when(generatorMock.generatePositions(numberOfPrizes, numberOfOptions)).thenReturn(prizeSpots);

        game.playRound(1);
        assertFalse(game.guess(1));

        game.playRound(3);
        assertFalse(game.guess(3));


    }
    @Test
    public void testGameEndsIfGuessIsIncorrect()
    {
        FindThePrize game = FindThePrize.init(numberOfOptions, numberOfPrizes, numberOfRounds, generatorMock);

        List<Integer> prizeSpots = Arrays.asList(1, 3);
        Mockito.when(generatorMock.generatePositions(numberOfPrizes, numberOfOptions)).thenReturn(prizeSpots);

        List<Integer> roundGuesses = Arrays.asList(4, 1, 2, 3);

        game.playGame(roundGuesses);

        assertEquals(1, game.getNumberOfPoints());
        assertTrue(game.getRoundsPassed()<numberOfRounds);

    }

    @Test
    public void testExitGameWhenPlayerWins()
    {
        FindThePrize game = FindThePrize.init(numberOfOptions, numberOfPrizes, numberOfRounds, generatorMock);

        List<Integer> prizeSpots = Arrays.asList(1, 3);
        Mockito.when(generatorMock.generatePositions(numberOfPrizes, numberOfOptions)).thenReturn(prizeSpots);

        List<Integer> roundGuesses = Arrays.asList(2, 4, 1, 3);

        game.playGame(roundGuesses);

        assertEquals(numberOfPrizes, game.getNumberOfPoints());
        assertTrue(game.getRoundsPassed()<numberOfRounds);

    }
    /*There is no need to test game result when all rounds pass since
    game will end if all prizes are found or game is lost.
    Both of these outcomes are bound to happen before all rounds end.
    If player had something representing number of times he can guess incorrectly then
    case above would have to be tested.
    * */
}
