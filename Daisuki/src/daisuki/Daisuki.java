package daisuki;

import java.util.Scanner;
import java.util.Random;
class Player{
    String name;
    int playerNum;
    int score;
    
}
class Scoreboard{
    String[][] table = new String[4][13];
    
    void create(){
        for (int x = 0; x<=3; x++){ //cycles through table Rows
            for(int y = 0; y<=12; y++){ // cycles through table Columns
                if(x==0){
                    if(y== 0){
                       this.table[x][y] = ""; //leaves first 0,0 empty

                    }
                    else if( y>0 && y<=11){
                        this.table[x][y] =  String.valueOf(y +1); // Inputs scores into correct table header columns 
                    }
                    else if(y==12){
                        this.table[x][y] = "Scores";
                    }
                }
                if(x==1){
                    if(y== 0){
                       this.table[x][y] = "Player 1";

                    }
                    else if( y>0 && y<=12){
                        this.table[x][y] =  "";
                    }
                    
                }
                if(x==2){
                    if(y== 0){
                       this.table[x][y] = "Player 2";
                    }
                    else if( y>0 && y<=12){
                        this.table[x][y] =  "";
                    }
                }
                if(x==3){
                    if(y == 0){
                       this.table[x][y] = "Player 3";
                    }
                    else if( y>0 && y<=12){
                        this.table[x][y] =  "";
                    }
                }
            }
        }
    }
    void display(){
        for(int x = 0; x<= 3; x++){
            for(int y = 0; y <= 12; y++){
                switch(y){
                    case 0 :
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
    void updateNameNScore(Player[] p){
                
        for(int x = 0; x< p.length; x++){
              this.table[x+1][0] = p[x].name; //Input player1s name into table
              this.table[x+1][12] = String.valueOf(p[x].score); // Input player1s score into table
        }
              
                
        
    }
    void enterRoll(Player p, int point, int roll){       
        int pointPos = getPointPos(point);
        int playerPos = getPlayerPos(p);
        table[playerPos][pointPos] = String.valueOf(roll);
         if(checkColFilled(pointPos)) completeCol(pointPos,p);
          
          
          
    }
    boolean checkColFilled(int pointPos){
        
        
        for(int x = 1;x<=3;x++){
            if(table[x][pointPos].equals("")) return false;
        }
        return true;
        
    
    }
    void completeCol(int pointPos, Player p){
               
        int player1Point = Integer.valueOf(table[1][pointPos]);
        int player2Point = Integer.valueOf(table[2][pointPos]);
        int player3Point = Integer.valueOf(table[3][pointPos]);
        int highestPoint;
        int highestPos;
        //Looks for any duplicate rolls
        
        if(player1Point == player2Point || player1Point == player3Point || player2Point == player3Point)
        {
            System.out.println("A Tie was Found");
            System.out.println("Nobody Gets Point " +table[0][pointPos] + "...");
            return;
        }
        //Looks for highest dice roll
        if(player1Point > player2Point){highestPoint = player1Point; highestPos = 1;}
        else{highestPoint=player2Point; highestPos = 2;}
        if(player3Point > highestPoint ){highestPoint = player3Point; highestPos = 3;}
        
        
        p.score += Integer.valueOf(table[0][pointPos]);
        table[highestPos][12] = String.valueOf(p.score);
        System.out.println(p.name+" just scored " +table[0][pointPos]+" points");
        System.out.println("Bringing them to a total of "+ p.score);
      
    }
    
    
  
    boolean checkPointFilled(Player p, int point){
            int pointpos = getPointPos(point);
            int playerpos = getPlayerPos(p);
        return !table[playerpos][pointpos].equals("");
    
    }
      int getPlayerPos(Player p){
        int pos;
        for(int x = 1; x<=3; x++){
            if(p.name.equals(table[x][0])){
                pos = x;
                return pos;
            }
        }
        return 0;
    }
       int getPointPos(int point){
        int pos;
        for(int x = 1; x<12; x++){
            if(String.valueOf(point).equals(table[0][x])){
                pos = x;
                return pos;
            }
        }
        return 0;
        

    }
    
    
}


public class Daisuki {
    /**
     * @param args the command line arguments
     */
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
            switch(select){
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
        }while (select != "A");
    }
       static void rules(){ //displays Rules
        System.out.println("");
        System.out.println("How To Play:");
        System.out.println("* This game requires 3 players");
        System.out.println("You will play a total of 11 rounds, 2 dice rolls per round for each player");
        System.out.println("Each player (after rolling both dice) will choose a column to place their score for");
        System.out.println("Once the column has a score from each player, the player with the highest score will be awarded the corresponding point");
        System.out.println("If there are any ties, no scores will be awarded for anybody");
        }
    static void mainGame(){
        Scanner sc = new Scanner(System.in);
        String select;
        Random rnd = new Random();
        int[] dice = new int[2];
      Scoreboard tbl = new Scoreboard();
      Player[] player = new Player[3];
      for(int x = 0; x<3; x++){
          player[x] = new Player();
      }
      
      tbl.create();   
        System.out.println("Lets Get Started!!"); // Enter player names
        autofillName(player);
        tbl.updateNameNScore(player);
        
//        for(int x = 0; x<3; x++){
//            System.out.println("Player "+ (x+1) +", Whats your name?");
//            while (true){
//                System.out.print("Name: ");
//                select = sc.next(); 
//                player[x].name = select;
//
//                System.out.println("");
//                System.out.println("Are you Sure?");
//                System.out.println("You can't change this later.");
//                System.out.println("");
//                System.out.println("A) Yes I'm sure");
//                System.out.println("B) Let me change my name");
//                System.out.print("Option: ");
//                select = sc.next().toUpperCase();
//                System.out.println(select);
//                if("A".equals(select)){
//                    break;
//                }
//            }
//        }

        for(int round = 1; round<=11; round++){// Starts Players into 11 round game
            for(int turn = 0; turn<3; turn++){ //Begins turn for each player
                System.out.println("");
                System.out.println(player[turn].name +" Its your turn to Roll the dice");
                sc.nextLine();
                dice[0] = rnd.nextInt(6) +1;
                dice[1] = rnd.nextInt(6) +1;
                System.out.println("");
                System.out.println("Dice 1: " + dice[0]);
                System.out.println("Dice 2: " + dice[1]);
                System.out.println("That is a total of " + (dice[0] +dice[1]));
                sc.nextLine();
                
                tbl.display();
                int selectPoint;
                while (true){
                    System.out.println("");
                    System.out.println("What point do you wanna play for?");
                    System.out.print("Point: ");
                    selectPoint = sc.nextInt();
                    try{
                        if(selectPoint <= 1 || selectPoint >= 13) throw new Exception("Out of bounds");
                        if(tbl.checkPointFilled(player[turn], selectPoint)) throw new Exception("Already Used");
                        break;
                    }catch(Exception e){
                        System.out.println("Invalid Option!");
                        System.out.println(e);
                        System.out.println("Pick a Number from the top row.");
                        System.out.println("");
                    }
                }
                tbl.enterRoll(player[turn], selectPoint, (dice[0]+dice[1]));
                tbl.display();
                
               
                
                
            }
        } 
    }
     static void autofillName(Player[] players){
         players[0].name ="Percy";
         players[1].name ="Jackson";
         players[2].name="Martha";
         
         
        }
}
