package com.java.connect4;
public class Player implements Runnable {	
	
	private final PrintBoard board ;
	private int turn;
	
	public Player(PrintBoard game_board,int turn)
	{
		board = game_board;
		this.turn = turn;
	}
	public void run() {
		board.var_lock();
		if(!board.start())
		{
			try {
				board.palyer_wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				board.var_unlock();
			}
		}
		for(;;){
			while(board.getGameStatus()==1)
			{
				try {
					board.palyer_wait();
					if(board.last_value()==turn)
						board.palyer_wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					board.var_unlock();
				}
			}
			board.dropCoin(turn);
			board.printPrintBoard();
			board.signalToWaitingThreads();
		}
	}
}
