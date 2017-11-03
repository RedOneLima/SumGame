import java.util.Scanner;

/**
 * @author Kyle Hewitt
 * @author Rebecca Levine
 * @version 0.0.1
 */

public class SumGame {

    private int[][] dynamicTable;
    private String[][] optimalPath;
    private int[] gameArray;
    private final char DOWN = '\u2193';
    private final char LEFT = '\u2190';
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private int first, last;

    public static void main(String[] args) {
        new SumGame();
    }

    private SumGame() {
        int n;
        Scanner fillArray = new Scanner(System.in);
        do {
            System.out.print("How many values would you like to use (even value): ");
            n = fillArray.nextInt();
        }while(n %2 != 0);
        gameArray = new int[n];
        for(int i = 0; i<n ; i++){
            System.out.println("Enter value: " + (i+1));
            gameArray[i] = fillArray.nextInt();
        }
        dynamicTable = new int[gameArray.length][gameArray.length];
        optimalPath = new String[gameArray.length][gameArray.length];
        first = 0; last = gameArray.length-1;
        buildTable();
        printTable();
        gamePlay();
    }

    private void buildTable() {

        int opt1, opt2, opt3;

        for (int k = 0; k < gameArray.length; k++){
            for (int i = 0, j = k; j < gameArray.length; i++, j++){
                if((i+2)<=j) opt1 = dynamicTable[i+2][j];
                else opt1 = 0;

                if((i+1) <= (j-1)) opt2 = dynamicTable[i+1][j-1];
                else opt2 = 0;

                if(i <= (j-2)) opt3 = dynamicTable[i][j-2];
                else opt3 = 0;

                if(gameArray[i]+min(opt1,opt2) > gameArray[j]+min(opt2,opt3)) {
                    dynamicTable[i][j] = gameArray[i] + min(opt1, opt2);
                    optimalPath[i][j] = Character.toString(DOWN);
                }else{
                    dynamicTable[i][j] = gameArray[j] + min(opt2, opt3);
                    optimalPath[i][j] = Character.toString(LEFT);
                }
            }
        }
    }

    private void printTable(){
        String headingSep = "";
        for(int i: gameArray){
            System.out.print("\t"+ i + "\t");
        }
        System.out.println();
        for(int i: gameArray){
            headingSep += Character.toString('\u2550')+Character.toString('\u2550')+Character.toString('\u2550')+
                    Character.toString('\u2550')+Character.toString('\u2550')+Character.toString('\u2550')+
                    Character.toString('\u2550');

        }
        System.out.print(headingSep);


        for (int f = 0; f<optimalPath.length; f++) {
            for(int r = 0; r<optimalPath.length; r++){
                if (f == r){
                    optimalPath[f][r] = null;
                }
            }
        }

        for(int i=0; i<dynamicTable.length;i++){
            System.out.println();
            for(int j=0; j<dynamicTable.length; j++){
                //Print the divider down the rows
                if (j==0) System.out.print(gameArray[i]+Character.toString('\u2551')+"\t");
                //Has a corrisponding arrow
                if(optimalPath[i][j] != null)
                    if(i == first && j == last)
                        System.out.print(GREEN_BACKGROUND+dynamicTable[i][j]+RESET+optimalPath[i][j] +  "\t\t");
                    else
                        System.out.print(dynamicTable[i][j] +optimalPath[i][j] +  "\t\t");
                else if(dynamicTable[i][j] != 0) {
                    if(i == first && j == last)
                        System.out.print(GREEN_BACKGROUND+dynamicTable[i][j]+RESET +"\t\t");
                    else
                        System.out.print(dynamicTable[i][j] + "\t\t");

                }
                else{
                    System.out.print("-\t\t");
                }
            }
        }

    }

    private void gamePlay() {
        int player1Sum = 0, player2Sum = 0;
        int optimalMove = 0, userSelection;
        Scanner userInput = new Scanner(System.in);
        while (first <= last) {
            if(optimalPath[first][last].equals(Character.toString(LEFT))){
                optimalMove = gameArray[last];
            }else if(optimalPath[first][last].equals(Character.toString(DOWN))){
                optimalMove = gameArray[first];
            }
            System.out.print("\nYour choices are " + gameArray[first] + " and " + gameArray[last] +
                "\nYour optimal choice for this move is " +optimalMove+": ");
            userSelection = userInput.nextInt();
                if(userSelection != gameArray[first] && userSelection != gameArray[last]){
                    System.out.println("You can only pick the first or last elements in the list");
                    continue;
                }

                if (userSelection == gameArray[first]) {
                    player1Sum += gameArray[first++];
                } else {
                    player1Sum += gameArray[last--];
                }
                if (optimalPath[first][last] ==null){
                    player2Sum += dynamicTable[first][last];
                    break;
                }
                if (optimalPath[first][last].equals(Character.toString('\u2190'))) {
                    System.out.println("Computer has chosen " + gameArray[last]);
                    player2Sum += gameArray[last--];

                } else if (optimalPath[first][last].equals(Character.toString('\u2193'))) {
                    System.out.println("Computer has chosen " + gameArray[first]);
                    player2Sum += gameArray[first++];
                }
                else{
                    System.out.println("Computer chosen" +gameArray[first]);
                    player2Sum += gameArray[first];
                }
                System.out.println("Current scores\nPlayer 1: " + player1Sum + "\nComputer: " + player2Sum);
                printTable();
            }
                if(player1Sum > player2Sum){
                    System.out.println("Player 1 wins!");
                }else if(player1Sum == player2Sum) {
                    System.out.println("Its a TIE!");
                }else{
                    System.out.println("Computer wins!");
                }
                System.out.println("Player 1: "+player1Sum+"\nComputer:"+player2Sum);
            }

    private int min(int one, int two){
        if (one<two) return one;
        return two;
    }

}
