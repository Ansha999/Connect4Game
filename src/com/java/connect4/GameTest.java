package com.java.connect4;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameTest {
public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		PrintBoard game_board = new PrintBoard();
		exec.execute(new Monitor(game_board));
		exec.execute(new Player(game_board, 1));
		exec.execute(new Player(game_board, 2));
		exec.shutdown();
		exec.awaitTermination(10, TimeUnit.MINUTES);
	}
}