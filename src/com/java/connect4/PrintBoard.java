package com.java.connect4;
import java.security.SecureRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintBoard {

	public final int game_board[][] = new int[6][7];
	private final Lock accessLock = new ReentrantLock();
	private final Condition player_cond = accessLock.newCondition();

	private int start_game;
	private int l_value;
	public int count = 0;
	private int game_status;

	public void printPrintBoard() {
		int i, j;
		for (i = 5; i >= 0; i--) {
			for (j = 0; j < 7; j++) {
				System.out.print(" " + game_board[i][j]);
			}
			System.out.printf(" \n");
		}
	}

	public void initilizeGame() {
		start_game = 1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				game_board[i][j] = 0;
			}
		}
		player_cond.signalAll();
	}

	public void var_lock() {
		accessLock.lock();
	}

	public void var_unlock() {
		accessLock.unlock();
	}

	public int getGameStatus() {
		return game_status;
	}

	public int last_value() {
		return l_value;
	}

	public boolean start() {
		if (start_game == 1)
			return true;
		return false;
	}
	public void palyer_wait() throws InterruptedException {
		player_cond.await();
	}
	public void dropCoin(int turn) {
		l_value = turn;
		SecureRandom rand = new SecureRandom();
		int c = rand.nextInt(1000) % 7;
		for (int r = 0; r < 6; r++) {
			if (game_board[r][c] == 0) {
				(game_board[r][c]) = turn;
				count++;
				break;
			}
		}
		c++;
		System.out.print("Player " + turn + " has dropped the coin in column " + c + "\n");
		game_status = 1;

	}

	public void checkingWinningConditions() {
		game_status = 0;
		if (count == 42) {
			System.out.println(" Game is Tie . Play again !!!");
			System.exit(0);
		}
		horizontalCheckFunction();
		ltorDiagonalCheckFunction();
		rtolDiagonalCheckFunction();
	}

	private void horizontalCheckFunction() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (j + 3 < 7 && game_board[i][j] != 0 && game_board[i][j] == game_board[i][j + 1]
						&& game_board[i][j + 1] == game_board[i][j + 2]
						&& game_board[i][j + 2] == game_board[i][j + 3]) {
					announceWinner(game_board[i][j]);
				}
			}
		}
	}

	private void ltorDiagonalCheckFunction() {

		for (int i = 5; i > 2; i--) {
			for (int j = 0; j < 4; j++) {
				if (game_board[i][j] != 0 && game_board[i][j] == game_board[i - 1][j + 1]
						&& game_board[i - 1][j + 2] == game_board[i - 2][j + 2]
						&& game_board[i - 2][j + 2] == game_board[i - 3][j + 3]) {
					announceWinner(game_board[i][j]);
				}
			}
		}
	}
	private void rtolDiagonalCheckFunction() {

		for (int i = 5; i > 2; i--) {
			for (int j = 6; j > 2; j--) {
				if ((i-3 > 2 && j +3 > 7 )&& game_board[i][j] != 0 && game_board[i][j] == game_board[i - 1][j + 1]
						&& game_board[i - 1][j + 1] == game_board[i - 2][j + 2]
						&& game_board[i - 2][j + 2] == game_board[i - 3][j + 3]) {
					announceWinner(game_board[i][j]);
				}
			}
		}
	}
	private void announceWinner(int winner) {
		System.out.println("Player " + winner + " won the game !!! ");
		System.exit(0);
	}

	public void checkIsBoardFull() throws InterruptedException {
		if (game_status == 1)
			return;
		player_cond.signalAll();
		player_cond.await();

	}

	public void signalToWaitingThreads() {
		player_cond.signalAll();
	}

}
