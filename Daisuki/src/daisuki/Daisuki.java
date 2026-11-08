package daisuki;

import java.util.Scanner;
import java.util.Random;

/**
 * A single game participant Name is set once at game start score accumulates as
 * points are awarded
 */
class Player {

    String name = "";
    int score = 0;
}

/**
 * Grid layout: row 0 Headers : col 0 = empty, cols 1-11 are points 2-12 col 12
 * = Scores rows 1-3 : Player 1/2/3 col 0 = names, cols 1-11 = players dice
 * rolls, col 12 = total score
 */
class Scoreboard {

    String[][] table = new String[4][13];

    /**
     * Initializes row 0 headers (point values 2..12 + "Scores") and player name
     * labels in col 0
     */
    void create() {// Initialises headers and placeholder player names into the table
        for (int x = 0; x <= 3; x++) { //cycles through table Rows
            for (int y = 0; y <= 12; y++) { // cycles through table Columns
                switch (x) {
                    case 0:
                        if (y == 0) {
                            this.table[x][y] = ""; //leaves first 0,0 empty

                        } else if (y > 0 && y <= 11) {
                            this.table[x][y] = String.valueOf(y + 1); // Inputs scores into correct table header columns 
                        } else if (y == 12) {
                            this.table[x][y] = "Scores";
                        }
                        break;
                    case 1:
                        if (y == 0) {
                            this.table[x][y] = "Player 1";

                        } else if (y > 0 && y <= 12) {
                            this.table[x][y] = "";
                        }
                        break;
                    case 2:
                        if (y == 0) {
                            this.table[x][y] = "Player 2";
                        } else if (y > 0 && y <= 12) {
                            this.table[x][y] = "";
                        }
                        break;
                    case 3:
                        if (y == 0) {
                            this.table[x][y] = "Player 3";
                        } else if (y > 0 && y <= 12) {
                            this.table[x][y] = "";
                        }
                        break;
                }
            }
        }
    }

    /**
     * Renders the scoreboard Column widths: col 0 (name) = 10 chars, col 12
     * (score) = 6 chars, all others = 2 chars
     */
    void display() {
        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 12; y++) {
                switch (y) {
                    case 0:
                        System.out.printf("|%-10s|", table[x][y]);
                        break;
                    case 12:
                        System.out.printf("|%-6s|", table[x][y]);
                        break;
                    default:
                        System.out.printf("|%-2s|", table[x][y]);
                        break;
                }
            }
            System.out.printf("%n");
        }
    }

    /**
     * Copies each player's current name and score into table rows 1-3
     */
    void updateNameNScore(Player[] p) {

        for (int x = 0; x < p.length; x++) {
            this.table[x + 1][0] = p[x].name; //Input player1s name into table
            this.table[x + 1][12] = String.valueOf(p[x].score); // Input player1s score into table
        }

    }

    /**
     * Records a player's roll at the column for the given point value
     */
    void enterRoll(Player p, int point, int roll) {
        int pointPos = getPointPos(point);
        int playerPos = getPlayerPos(p);
        table[playerPos][pointPos] = String.valueOf(roll);

    }

    /**
     * Returns true if all three players have filled the column for the given
     * point value
     */
    boolean checkColFilled(int pointPos) {

        for (int x = 1; x <= 3; x++) {
            if (table[x][pointPos - 1].equals("")) {
                return false;
            }
        }
        return true;

    }

    /**
     * Resolves a fully-filled column: finds the highest roll, awards the
     * column's point value to that player, and updates the table's score column
     * Ties award no points to anyone
     */
    void completeCol(int pointPos, Player[] p) {
        pointPos = getPointPos(pointPos);
        int player1Point = Integer.valueOf(table[1][pointPos]);
        int player2Point = Integer.valueOf(table[2][pointPos]);
        int player3Point = Integer.valueOf(table[3][pointPos]);
        int highestPos = 1;
        //Looks for any duplicate rolls

        int highestScore = Math.max(player1Point, Math.max(player2Point, player3Point));
        int winCount = 0;
        if (player1Point == highestScore) {
            highestPos = 1;
            winCount++;
        }
        if (player2Point == highestScore) {
            highestPos = 2;
            winCount++;
        }
        if (player3Point == highestScore) {
            highestPos = 3;
            winCount++;
        }

        // Determines the winner or declares a draw
        if (winCount > 1) {
            System.out.println("A Tie was Found");
            System.out.println("Nobody Gets Point " + table[0][pointPos] + "...");
            return;
        }

        System.out.println(p[highestPos].name + " Won");
        System.out.println(p[highestPos].name + " Score: " + p[highestPos].score);
        p[highestPos].score += Integer.valueOf(table[0][pointPos]);
        System.out.println(p[highestPos].name + " Score: " + p[highestPos].score);
        table[highestPos][12] = String.valueOf(p[highestPos].score);
        System.out.println(p[highestPos].name + " just scored " + table[0][pointPos] + " points");
        System.out.println("Bringing them to a total of " + p[highestPos].score);

    }

    /**
     * Returns true if the given player has already placed a roll at the given
     * point value (2..12)
     */
    boolean checkPointFilled(Player p, int point) {
        int pointpos = getPointPos(point);
        int playerpos = getPlayerPos(p);
        return !table[playerpos][pointpos].equals("");

    }

    /**
     * Returns the table row (1..3) for this player, or 0 if not found
     */
    int getPlayerPos(Player p) {
        int pos;
        for (int x = 1; x <= 3; x++) {
            if (p.name.equals(table[x][0])) {
                pos = x;
                return pos;
            }
        }
        return 0;
    }

    /**
     * Returns the column index (1..11) whose header matches the given point
     * value, or 0 if not found
     */
    int getPointPos(int point) {
        int pos;
        for (int x = 1; x < 12; x++) {
            if (String.valueOf(point).equals(table[0][x])) {
                pos = x;
                return pos;
            }
        }
        return 0;

    }

}

/**
 *
 *
 *
 * Game model: 3 players, 11 rounds Each player rolls 2 dice and picks an unused
 * point column (values 2..12) When all three have filled a column, the highest
 * roll takes that column's point value; ties award nothing
 */
public class Daisuki {

    static Player[] player = new Player[3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String select; // users selected menu option
        System.out.println("Welcome to Daisuki!");
        rules();

        do { //loops until user chooses to play
            System.out.println("");
            System.out.println("Are you ready to play?");
            System.out.println("A) Yes!");
            System.out.println("B) No Let me hear the rules again.");
            select = sc.next().toUpperCase();
            switch (select) {
                case "A":
                    mainGame();
                    break;
                case "B":
                    rules();
                    break;
                default:
                    System.out.println("");
                    System.out.println("Enter a valid option!");
                    System.out.println("");
                    break;
            }
        } while (!select.equals("A"));
    }

    /**
     * Prints the how-to-play text (11 rounds, 2 dice per roll, 12 point
     * columns, ties = no points)
     */
    static void rules() { //displays Rules
        System.out.println("");
        System.out.println("How To Play:");
        System.out.println("* This game requires 3 players");
        System.out.println("You will play a total of 11 rounds, 2 dice rolls per round for each player");
        System.out.println("Each player (after rolling both dice) will choose a column to place their score for");
        System.out.println("Once the column has a score from each player, the player with the highest score will be awarded the corresponding point");
        System.out.println("If there are any ties, no scores will be awarded for anybody");
    }

    /**
     * Runs one full game: name setup, then 11 rounds of 3 turns each
     */
    static void mainGame() {
        Scanner sc = new Scanner(System.in);
        String select;
        Random rnd = new Random();
        int[] dice = new int[2];
        Scoreboard tbl = new Scoreboard();
        for (int x = 0; x < 3; x++) {
            player[x] = new Player();
        }

        tbl.create();
        System.out.println("Lets Get Started!!"); // Enter player names

        for (int x = 0; x < 3; x++) {
            System.out.println("Player " + (x + 1) + ", Whats your name?");
            while (true) {
                System.out.print("Name: ");
                select = sc.next();
                player[x].name = select;

                System.out.println("");
                System.out.println("Are you Sure?");
                System.out.println("You can't change this later.");
                System.out.println("");
                System.out.println("A) Yes I'm sure");
                System.out.println("B) Let me change my name");
                System.out.print("Option: ");
                select = sc.next().toUpperCase();
                System.out.println(select);
                if ("A".equals(select)) {
                    break;
                }
            }

            tbl.updateNameNScore(player);
        }
        for (int round = 1; round <= 11; round++) {// Starts Players into 11 round game
                tbl.table[0][0] = "Round "+ round; // display round in top left of scoreboard

            for (int turn = 0; turn < 3; turn++) { //Begins turn for each player
                System.out.println("");
                System.out.println(player[turn].name + " Its your turn to Roll the dice");
                sc.nextLine(); // consume leftover newline so player can press Enter to roll
                dice[0] = rnd.nextInt(6) + 1;
                dice[1] = rnd.nextInt(6) + 1;
                System.out.println("");
                System.out.println("Dice 1: " + dice[0]);
                System.out.println("Dice 2: " + dice[1]);
                System.out.println("That is a total of " + (dice[0] + dice[1]));
                sc.nextLine();
                tbl.display();
                int selectPoint;
                while (true) {
                    System.out.println("");
                    System.out.println("What point do you wanna play for?");
                    System.out.print("Point: ");
                    try {
                        selectPoint = sc.nextInt();

                        if (selectPoint <= 1 || selectPoint >= 13) {
                            throw new Exception("Out of bounds");
                        }
                        if (tbl.checkPointFilled(player[turn], selectPoint)) {
                            throw new Exception("Already Used");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid Option!");
                        System.out.println("Pick a Number from the top row.");
                        System.out.println("");
                        sc.nextLine();
                    }
                }
                tbl.enterRoll(player[turn], selectPoint, (dice[0] + dice[1]));
                if (tbl.checkColFilled(selectPoint)) {
                    tbl.completeCol(selectPoint, player);
                }
                tbl.display();
            }
            displayHighestPlayerScore();
        }
    }

    /**
     * Prints the current leader
     */
    static void displayHighestPlayerScore() {
        int highestPos = 0;
        int highestScore = Math.max(player[0].score, Math.max(player[1].score, player[2].score));
        int winCount = 0;
        if (player[0].score == highestScore) {
            highestPos = 1;
            winCount++;
        }
        if (player[1].score == highestScore) {
            highestPos = 2;
            winCount++;
        }
        if (player[2].score == highestScore) {
            highestPos = 3;
            winCount++;
        }
        // Determines the winner or declares a draw
        if (winCount > 1) {
            System.out.println("This Round has ended in a draw");
            System.out.println("Don't be discourgaged, it's still anyones game!");
            return;
        }
        System.out.println(player[highestPos].name + " is in the lead with " + highestScore + " points");

    }
}