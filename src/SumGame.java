import java.util.Scanner;

public class SumGame {

    private int[][] dynamicTable;
    private String[][] optimalPath;
    private int[] gameArray = {3, 1, 7, 5, 8, 4};

    public static void main(String[] args) {
        new SumGame();
    }

    private SumGame() {
        System.out.println("The dynamic table represents the discrepancy between players' scores");
        dynamicTable = new int[gameArray.length][gameArray.length];
        optimalPath = new String[gameArray.length][gameArray.length];
        buildTable(0, gameArray.length - 1);
        printTable();
        gamePlay();
    }

    private int buildTable(int first, int last) {

        if (first == last){
            dynamicTable[first][last] = gameArray[first];
            return gameArray[first];
        }
        int left = gameArray[first] - buildTable(first + 1, last);
        int down = gameArray[last] - buildTable(first, last - 1);
        if (left > down) {
            dynamicTable[first][last] = left;
            optimalPath[first][last] = Character.toString('\u2190');
            return left;
        } else {
            dynamicTable[first][last] = down;
            optimalPath[first][last] = Character.toString('\u2193');
            return down;
        }
    }

    private void printTable(){
        for(int i: gameArray){
            System.out.print(i + "\t\t");
        }
        System.out.println();
        for(int i: gameArray){
            System.out.print("-------");
        }
        System.out.println();
        for(int i=0; i<dynamicTable.length;i++){
            System.out.println();
            for(int j=0; j<dynamicTable.length; j++){
                if(dynamicTable[i][j] == 0){
                    //System.out.print("-\t\t");
                }
                if(optimalPath[i][j] != null)
                System.out.print(dynamicTable[i][j] +optimalPath[i][j] +  "\t\t");
                else if(dynamicTable[i][j] != 0) {
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
        int first = 0, last = gameArray.length-1;
        int optimalMove = 0, userSelection = 0;
        Scanner userInput = new Scanner(System.in);
        while (first < last) {
            if(optimalPath[first][last].equals('\u2190')){
                optimalMove = gameArray[last];
            }else{
                optimalMove = gameArray[first];
            }
            System.out.println("\nYour choices are " + gameArray[first] + " and " + gameArray[last] +
                "\n Your optimal choice for this move is " +optimalMove+":");
            userSelection = userInput.nextInt();
            try {
                if (userSelection == gameArray[first]) {
                    player1Sum += gameArray[first++];
                } else {
                    player1Sum += gameArray[last--];
                }
                if (optimalPath[first][last].equals('\u2190')) {
                    System.out.println("Computer has chosen " + gameArray[last]);
                    player2Sum += gameArray[last--];

                } else {
                    System.out.println("Computer has chosen " + gameArray[first]);
                    player2Sum += gameArray[first++];
                }
                System.out.println("Current scores\nPlayer 1: " + player1Sum + "\nPlayer 2: " + player2Sum);
                printTable();
            }catch (NullPointerException np){
                if(player1Sum > player2Sum){
                    System.out.println("Player 1 wins!");
                }else{
                    System.out.println("Computer wins!");
                }
                System.out.println("Player 1: "+player1Sum+"\nPlayer 2:"+player2Sum);
            }
        }
    }
}
