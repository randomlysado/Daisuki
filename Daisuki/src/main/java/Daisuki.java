
import java.util.Scanner;
public class Daisuki {

    public static void main(String[] args) { // Introduction to the game / Main Menu
        Scanner sc = new Scanner(System.in);
        String select; // users selected menu option
        System.out.println("Welcome to Daisuki!");
        Rules();
        
        do { //loops until user chooses to play
            System.out.println("");
            System.out.println("Are you ready to play?");
            System.out.println("A) Yes!");
            System.out.println("B) No Let me hear the rules again.");
            select = sc.next().toUpperCase();
            switch(select){
                case "A":
                       MainGame();
                    break;
                case "B":
                    Rules();
                    break;
                default:
                    System.out.println("");
                    System.out.println("Enter a valid option!");
                    System.out.println("");
                    break;
            }
        }while (select != "A");
    }
    static void Rules(){ //displays Rules
        System.out.println("");
        System.out.println("How To Play:");
        System.out.println("* This game requires 3 players");
        System.out.println("You will play a total of 11 rounds, 2 dice rolls per round for each player");
        System.out.println("Each player (after rolling both die) will choose a column to place their score for");
        System.out.println("Once the column has a score from each player, the player with the highest score will be awarded the corresponding point");
        System.out.println("If there are any ties, no scores will be awarded for anybody");
        }
    static void MainGame(){
        String[] Player = new String[2];
        int[] Score = new int[2];
       
            
            
        }
    }
    
    
    
    
    
}
