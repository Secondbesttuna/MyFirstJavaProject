import java.util.*;

public class CandyCrush {

	public static void main(String[] args) {
   		Scanner scan = new Scanner(System.in);
   		System.out.println("Welcome to the game!");
   		while (true) {
        		System.out.println("Enter ’s’ to start the game or ’q’ to quit:");
        		String input = scan.nextLine();
        		if (input.equalsIgnoreCase("q")) {
        			break; // Exit the game loop
        		}
		    	if(input.equalsIgnoreCase("s")) {
		     		System.out.print("Enter the size of matrix: ");
		   			int row = scan.nextInt();
		   			int column = scan.nextInt();
		     		CandyCrushGame candyCrush = new CandyCrushGame(row,column);
		    		candyCrush.play();
		    	}
		}
    
	}
}
class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
}
class CandyCrushGame {
    Scanner scan = new Scanner(System.in);
    public static final char[] candy_types = {'R', 'G', 'B'};
    public char[][] board;
    public Random random;
    public int Rpoints = 0;
    public int Gpoints = 0;
    public int Bpoints = 0;
    public int row;
    public int column;
    public int counter = 0;
    public CandyCrushGame(int row, int column) {
        board = new char[row][column];
        this.row=row;
        this.column=column;
        initializeBoard();
    }
    public void initializeBoard() {
        random = new Random();
        for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
            	int index = random.nextInt(candy_types.length);
                board[i][j] = candy_types[index];
            }
        }
        while (checkMatches() == true) {
        }
        Rpoints = 0;
       	Gpoints = 0;
        Bpoints = 0;
        counter = 0;
    }
    public void shuffleBoard() {
        int[] candyCount = new int[candy_types.length];
        for (int i = 0; i < candyCount.length; i++) { // Initialize candyCount array with zeros
            candyCount[i] = 0;
        }   
        for (int i = 0; i < row; i++) { // Count the number of each candy type on the board
            for (int j = 0; j < column; j++) {
                for (char candyType : candy_types) {
                    if (board[i][j] == candyType) {
                        candyCount[Arrays.binarySearch(candy_types, candyType)]++;
                    }
                }
            }
        }
        for (int i = 0; i < row; i++) { // Shuffle the board while maintaining the same candy counts
            for (int j = 0; j < column; j++) {
                int candyIndex = random.nextInt(candy_types.length);
                while (candyCount[candyIndex] <= 0) {
                    candyIndex = random.nextInt(candy_types.length);
                }
                board[i][j] = candy_types[candyIndex];
                candyCount[candyIndex]--;
            }
        }
    }
    public void swapCells(int x, int y, String direction) {
    	if(direction.equals("up")&& x - 1 >= 0){
    		char temp = board[x][y];
    		board[x][y] = board[x-1][y];
    		board[x-1][y] = temp;
    	}
    	else if(direction.equals("down")&& x + 1 < row){
    		char temp = board[x][y];
    		board[x][y] = board[x+1][y];
    		board[x+1][y] = temp;
    	}
    	else if(direction.equals("left")&& y - 1 >= 0){
    		char temp = board[x][y];
    		board[x][y] = board[x][y-1];
    		board[x][y-1] = temp;
    	}
    	else if(direction.equals("right")&& y + 1 < column){
    		char temp = board[x][y];
    		board[x][y] = board[x][y+1];
    		board[x][y+1] = temp;
    	}	 
	}
	public boolean checkMatches() {
		boolean cm = false;
        	for (int i = 0; i < row; i++) {
            		for (int j = 0; j < column; j++) {
                		if (i <= row - 3 && board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j]) { // Checking for vertical matches
                    		clearMatches(i, j, i + 1, j, i + 2, j);
                   			cm = true;
                		}
                		if (j <= column - 3 && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2]) { // Checking for horizontal matches
                    		clearMatches(i, j, i, j + 1, i, j + 2);
                    		cm = true;
                		}
            		}
        	}
        	return cm;
    }
    private void clearMatches(int x1, int y1, int x2, int y2, int x3, int y3) {
	switch (board[x1][y1]) {
   		case 'R':
        	Rpoints += 10;
       		break;
        case 'G':
            Gpoints += 10;
        	break;
        case 'B':
            Bpoints += 10;
        	break;
        }
        board[x1][y1] = candy_types[random.nextInt(candy_types.length)];
        board[x2][y2] = candy_types[random.nextInt(candy_types.length)];
        board[x3][y3] = candy_types[random.nextInt(candy_types.length)]; 
    	counter++;
   	    if (x1 == x2 && x2 == x3) {        // Vertical match
        	int x4 = (x1 + x2 + x3) / 3;
      	 	int y4 = (y1 + y2 + y3) / 3;
       		board[x4][y4] = candy_types[random.nextInt(candy_types.length)];
        	if (x1 == x4) {      // Vertical match of 5 candies, clear the 5th candy
            	int x5 = x1;
            	int y5 = (y1 + y2 + y3 + y4) / 4;
           		board[x5][y5] = candy_types[random.nextInt(candy_types.length)];
        	}
    	}
    	else if (y1 == y2 && y2 == y3) {        // Horizontal match
        	int x4 = (x1 + x2 + x3) / 3;
        	int y4 = (y1 + y2 + y3) / 3;
        	board[x4][y4] = candy_types[random.nextInt(candy_types.length)];
        	if (y1 == y4) {          // Horizontal match of 5 candies, clear the 5th candy
            	int x5 = (x1 + x2 + x3 + x4) / 4;
            	int y5 = y1;
            	board[x5][y5] = candy_types[random.nextInt(candy_types.length)];
        	}
    	}
  }
    public boolean isMovePossible() {
    boolean mp = false;  // Set the default value to false
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            if (i <= row - 3 && board[i][j] == board[i + 1][j] && board[i + 1][j] == board[i + 2][j]) {
                mp = true;  // A valid move is possible
            }
            if (j <= column - 3 && (board[i][j] == board[i][j + 1] && board[i][j + 1] == board[i][j + 2])) {
                mp = true;  // A valid move is possible
            }
        }
    }
    return mp;
}
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
            	char candy = board[i][j];
            	String colorCode;
                switch (candy) {
                	case 'R':
                    	colorCode = Colors.RED;
                    	break;
                	case 'G':
                    	colorCode = Colors.GREEN;
                    	break;
                	case 'B':
                    	colorCode = Colors.BLUE;
                    	break;
                	default:
                    	colorCode = Colors.RESET;
                    	break;
            	}

            	System.out.print("|"+colorCode + candy + Colors.RESET);
           	}
            System.out.println("|");
        }
    }
    public void play() {
    initializeBoard();
    int rm1 = 0;
    int rm2 = 0;
    int mode = random.nextInt(2); 
        if(mode==0){      
        	System.out.println("You have to collect 200 points in 15 moves!");
        	System.out.println("Good Luck!");
        	System.out.println("You have 15 moves to reach the goal");  
        	for(int n = 15 ;n > 0; n--){   
        		printBoard(); // Print the board before swapping
				System.out.println("Enter the cell:");
        		int x = scan.nextInt()-1;
        		int y = scan.nextInt()-1;
        		scan.nextLine(); // a buffer to consume the nextline character
        		System.out.println("Enter the direction:");
        		String direction = scan.nextLine();
        		if (x < 0 || x >= row || y < 0 || y >= column) {
        			System.out.println("Invalid move!");
        			n++;
        			continue;
        		}
        		swapCells(x, y, direction);
        		printBoard(); // Print the board after swapping
        		if(checkMatches() == false){
        			swapCells(x, y, direction);
        			System.out.println("Invalid move !");
        			n++;
        		}
        		else{
        			System.out.println("Clearing board:");         
        			printBoard(); 
        			System.out.println("--------------");    
        			while (checkMatches() == true) {
        				printBoard();    //seen another match  
        				System.out.println("--------------");
        				
        			}      
        			System.out.println("You have collected " + counter*10 + " points and you have " + (n - 1) + " moves left!");
        			rm1 = n; 			
        		}
        	}
        	if (!isMovePossible()) {
           		System.out.println("No moves possible, shuffling board.");
           		shuffleBoard();
       		}
        	if(counter*10>=200){
       			System.out.println("You Win!");
       		}
       		else if(rm1==0){
       			System.out.println("You Lost!");
       		}
       	}       
        if(mode==1){   
        	System.out.println("You have to collect 100 points for each colour in 20 moves!");
        	System.out.println("Good Luck!");
        	System.out.println("You have 20 moves to reach the goal");
        	for(int m = 20 ;m > 0; m--){      
        		printBoard(); // Print the board before swapping
				System.out.println("Enter the cell:");
        		int x = scan.nextInt()-1;
        		int y = scan.nextInt()-1;
        		scan.nextLine(); // a buffer to consume the nextline character
        		System.out.println("Enter the direction:");
        		String direction = scan.nextLine();
        		if (x < 0 || x >= row || y < 0 || y >= column) {
        			System.out.println("Invalid move!");
        			m++;
        			continue;
        		}
        		swapCells(x, y, direction);
        		printBoard(); // Print the board after swapping
        		if(checkMatches() == false){
        			swapCells(x, y, direction);
        			System.out.println("Invalid move !");
        			m++;
        		}
        		else{
        			System.out.println("Clearing board:");
        			printBoard(); 
        			System.out.println("--------------");    
        			while (checkMatches() == true) {
        				printBoard();    //seen another match
        				System.out.println("--------------"); 
        			}      
        			System.out.println("You have collected " + Rpoints + " points for red and you have collected " + Gpoints + " points for green and you have collected " + Bpoints + " points for blue and you have " + (m - 1) + " moves left!");
        			rm2 = m;
        		}
        	}
        	if (!isMovePossible()) {
           		System.out.println("No moves possible, shuffling board.");
           		shuffleBoard();
        	}
        	if(Rpoints>=100 && Bpoints>=100 && Gpoints>=100){
        		System.out.println("You Win!");
        	}
        	else if(rm2==0){
        		System.out.println("You Lost!");	
        	}
       	}        
    }
}