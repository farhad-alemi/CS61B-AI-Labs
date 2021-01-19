package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String randString = "";
        for (int i = 0; i < n; ++i) {
            randString += CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return randString;
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(width / 2.0, height / 2.0, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); ++i) {
            StdDraw.clear();
            StdDraw.text(width / 2.0, height / 2.0, Character.toString(letters.charAt(i)));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String userInput = "";
        while (userInput.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                userInput += StdDraw.nextKeyTyped();
            }
        }
        return userInput;
    }

    public void startGame() {
        round = 1;

        while(!gameOver) {
            drawFrame("Round: " + round);
            StdDraw.pause(1500);
            String s = generateRandomString(round);
            flashSequence(s);
            String userInput = solicitNCharsInput(round);
            if (!s.equals(userInput)) {
                drawFrame("Game Over!, you made it to round: " + round);
                gameOver = true;
            }
            round++;
        }
    }

}
