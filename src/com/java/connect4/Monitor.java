package com.java.connect4;
public class Monitor implements Runnable {

	private final PrintBoard board;
	public Monitor(PrintBoard game_board) {
		board = game_board;
	}

	/*
	  The run method performs the following functions:
	   1. Acquire the lock 
	   2. Initialize the game variables 
	   3. Print the Game Board
	   4. Check if board is full 
	   5. Check the winner 
	   6. print the Game Board
	 */
	public void run() {
		System.out.println(" Welcome to  Connect 4 Game ");
		board.var_lock();
		board.initilizeGame();
		board.printPrintBoard();
		for (;;) {
			try {
				board.checkIsBoardFull();
			} catch (InterruptedException e) {
				e.printStackTrace();
				board.var_unlock();
			}
			board.checkingWinningConditions();
			System.out.println("Winner not found ");
		}

	}
}
