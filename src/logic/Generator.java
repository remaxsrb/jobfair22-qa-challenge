package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    public List<Integer> generatePositions(int numberOfPrizes, int gameSequenceSize)
    {
        List<Integer> prizeSpots = new ArrayList<>(numberOfPrizes);

        for (int i = 0; i < numberOfPrizes; i++) {
            int number = new Random().nextInt(gameSequenceSize);
            prizeSpots.add(i,number);
        }

        return  prizeSpots;
    }
}
