package ttt.grpc.client;

import java.util.Scanner;

import ttt.grpc.TTT;
import ttt.grpc.TTTServiceGrpc;

public class Game {
	Scanner keyboardSc;
	TTTServiceGrpc.TTTServiceBlockingStub stub;
	int winner = 0;
	int player = 1;

	public Game(TTTServiceGrpc.TTTServiceBlockingStub stube) {
		stub = stube;
		keyboardSc = new Scanner(System.in);
	}

	public int readPlay() {
		int play;
		do {
			System.out.printf(
					"\nPlayer %d, please enter the number of the square "
							+ "where you want to place your %c (or 0 to refresh the board): \n",
					player, (player == 1) ? 'X' : 'O');
			play = keyboardSc.nextInt();
		} while (play > 10 || play < 0);
		return play;
	}

	public void playGame() {
		int play;
		boolean playAccepted;

		do {
			player = ++player % 2;
			do {
				System.out.println(currentBoardAsk());
				play = readPlay();
				if (play == 10) {
					int plays = infoAsk(player);
					System.out.println("Your opponent already played " + plays + " times.");
					playAccepted = false;

				} else if (play != 0) {
					playAccepted = playAsk(--play / 3, play % 3, player);
					if (!playAccepted)
						System.out.println("Invalid play! Try again.");
				} else
					playAccepted = false;
			} while (!playAccepted);
			winner = checkWinnerAsk();
		} while (winner == -1);
	}

	private int checkWinnerAsk() {
		TTT.CheckWinnerRequest request = TTT.CheckWinnerRequest.newBuilder().build();

		// Finally, make the call using the stub
		TTT.CheckWinnerResponse response = stub.checkWinner(request);
		return response.getIntei();
	}

	private boolean playAsk(int l, int c, int player) {
		TTT.PlayRequest request = TTT.PlayRequest.newBuilder().setLinha(l).setColuna(c).setJogador(player).build();

		// Finally, make the call using the stub
		TTT.PlayResponse response = stub.play(request);
		return response.getBo();
	}

	private String currentBoardAsk() {
		TTT.CurrentBoardRequest request = TTT.CurrentBoardRequest.newBuilder().build();

		// Finally, make the call using the stub
		TTT.CurrentBoardResponse response = stub.currentBoard(request);
		return response.getBoard();
	}

	private int infoAsk(int player) {
		TTT.InfoRequest request = TTT.InfoRequest.newBuilder().setJogador(player).build();

		// Finally, make the call using the stub
		TTT.InfoResponse response = stub.info(request);
		return response.getNjogs();
	}

	public void congratulate() {
		if (winner == 2)
			System.out.printf("\nHow boring, it is a draw\n");
		else
			System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);
	}

}
