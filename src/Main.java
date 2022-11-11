import logic.FindThePrize;
import logic.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        FindThePrize game = FindThePrize.init(4 , 3, 5, new Generator());
        List<Integer> guesses = new ArrayList<>(
                Arrays.asList(1, 4, 2, 1,3));
        int points = game.playGame(guesses);
        System.out.printf("Number of points: %d\n", points);
    }
}