import java.util.Random;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Game implements ActionListener {
	
	static Board b = new Board(10, 10);
	static int lastRow, lastColumn;
	
	static int ticks = 0;
	static Timer clock;
	
	
	static Coordinate clickLoc;
	static int clickRow, clickCol;
	static int row, col;
	static int[][] grid = new int[10][10];
	static int score = 0;
	
	static Random r = new Random();
	
	public Game() {
		clock = new Timer(500, this);
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame outputArea = new JFrame("Level 1");
    	JOptionPane.showMessageDialog(outputArea, "Level 1" 
    			+ "\nClick on targets to eliminate. Do not let the targets form a 3 in a row!"
    			+ "\n Hit OK to begin.");
		b.displayMessage("Score: " + score);
		Game t = new Game();
		clock.start(); // start the timer
		while(!isThree(grid)){	//while three in a row has not been made 
			//click location
			clickLoc = b.getClick();
			clickRow = clickLoc.getRow();
			clickCol = clickLoc.getCol();

			if (grid[clickRow][clickCol] == 1){
				score += 100;
				b.putPeg("target_filled", clickRow, clickCol);
				Thread.sleep(50);
				b.removePeg(clickRow, clickCol);
				grid[clickRow][clickCol] = 0;
			}
			
			b.displayMessage("" + score);
		}
		
		clock.stop();
		
		JOptionPane.showMessageDialog(outputArea, "You lost!");
	}
	
	public static boolean isThree(int[][] grid){
		//check all spaces except the edge rows and columns
		for(int i = 1; i < grid.length - 1; i++){
			for(int j = 1; j < grid[i].length - 1; j++){
				if(checkGrid(grid, i, j)){
					return true;
				}
			}
		}
		//check row 1
		for(int i = 1; i < 9; i++){
			if( grid[i][0] == 1 && grid[i+1][0] == 1 && grid[i-1][0] == 1 ){
				return true;
			}
		}
		//check row 10
		for(int i = 1; i < 9; i++){
			if( grid[i][9] == 1 && grid[i+1][9] == 1 && grid[i-1][9] == 1 ){
				return true;
			}
		}
		//check column 1
		for(int i = 1; i < 9; i++){
			if( grid[0][i] == 1 && grid[0][i+1] == 1 && grid[0][i-1] == 1 ){
				return true;
			}
		}
		//check column 10
		for(int i = 1; i < 9; i++){
			if( grid[9][i] == 1 && grid[9][i+1] == 1 && grid[9][i-1] == 1 ){
				return true;
			}
		}
		//all else fails, return false
		return false;
	}
	
	/**
	 * Check the general grid for any 3s
	 */
	public static boolean checkGrid(int[][] grid, int i, int j) {
		return grid[i][j] == 1 && (
				(grid[i+1][j] == 1 && grid[i-1][j] == 1) ||
				(grid[i][j+1] == 1 && grid[i][j-1] == 1)
				);
	}

	public void actionPerformed(ActionEvent e) {
		ticks += 0.5;
		//random location
		row = r.nextInt(9);
		col = r.nextInt(9);
		grid[row][col] = 1;
		b.putPeg("target", row, col);
		System.out.println(ticks);
	}
}
