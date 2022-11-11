package logic;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

public class FindThePrize {
    private int numberOfPrizes;
    private final int numberOfRounds;
    private int numberOfPoints;

    public int getRoundsPassed() {
        return roundsPassed;
    }
    private int roundsPassed;
    private final Generator generator;

    //gameSequence is list of possible choices in game for that round
    private final List<Boolean> gameSequence;

    private FindThePrize(int numberOfOptions, int numberOfPrizes, int numberOfRounds, Generator generator) {
        this.numberOfPrizes = numberOfPrizes;
        this.numberOfRounds = numberOfRounds;
        this.gameSequence= new ArrayList<>(Arrays.asList(new Boolean[numberOfOptions]))
                                .stream()
                                .map(i -> false).collect(Collectors.toList());
        this.generator = generator;
    }

    //Function for initializing new game.
    // Game is configurable, so it can contain multiple options for player to guess, but also multiple prizes
    public static FindThePrize init(int numberOfOptions, int numberOfPrizes, int numberOfRounds, Generator generator) {

        if (numberOfPrizes <= 0)
            throw new InvalidParameterException("Number of prizes should be greater than 0");
        if(numberOfRounds <= 0)
            throw new InvalidParameterException("Number of rounds should be greater than 0");
        if(numberOfPrizes>=numberOfOptions)
            throw new InvalidParameterException("Number of options should be greater than number of prizes");

        return new FindThePrize(numberOfOptions, numberOfPrizes, numberOfRounds, generator);
        //In order for generator mocks to work properly there is a need to add a Generator to the game via constructor
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }


    //Initializing new round and setting prizes on random positions

    /*In order to have full control over the game during testing
    I've decided to write a class Generator around generatePositions() method as a wrapper.
    This way, mocking prize positions becomes possible since class Random can't be mocked.
    As far as I can see, there is no need to have a game mock object.
     */

    public void newRound() {

        List<Integer> prizeSpots = generator.generatePositions(this.numberOfPrizes, this.gameSequence.size());
        for (Integer prizeSpot : prizeSpots) {
            this.gameSequence.set(prizeSpot, true);
        }
    }
    public boolean guess(int index) {
        return this.gameSequence.get(index-1);
    }

    public void addPoint() {
        this.numberOfPoints++;
    }

    //playing one round and adding points if needed
    public boolean playRound(int roundGuess) {
        this.newRound();
        boolean currentRoundGuess = this.guess(roundGuess);

        if(currentRoundGuess) {
            this.addPoint();
            this.numberOfPrizes--;
        }

        return currentRoundGuess;
    }

    //Game core loop. This function gets guesses for every round in game
    public int playGame(List<Integer> roundGuesses) {

        for (int i = 0; i <= this.numberOfRounds; i++) {

            if(this.numberOfPrizes==0)
                break;

            /*
            Number of prizes should decrease since it wouldn't make sense to find the same prize
            within a single game.
            When player finds all prizes, game will end.
            Game will end if number of available prizes hits zero.

            In order to make it happen, attribute numberOfPrizes should be a variable and not a constant
            therefore it loses its 'final' keyword.
            */

            int roundGuess = roundGuesses.get(i);
            this.roundsPassed++;

            boolean didWinRound = this.playRound(roundGuess);
            if(!didWinRound) {
                break;
            }

            /*
            If the player loses the game, prizes he gathered so far will remain.
            Player's score after losing was left open-ended, so I've assumed this.

             */

        }

        return this.numberOfPoints;
    }
}
