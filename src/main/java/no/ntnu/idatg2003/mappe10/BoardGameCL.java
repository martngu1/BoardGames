package no.ntnu.idatg2003.mappe10;

import no.ntnu.idatg2003.mappe10.model.BoardGame;
import no.ntnu.idatg2003.mappe10.model.Player;

public class BoardGameCL {
  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();
    boardGame.createBoard();
    boardGame.createDice(2);
    boardGame.createPlayerList();

    Player player1 = new Player("Player 1", boardGame);
    Player player2 = new Player("Player 2", boardGame);
    Player player3 = new Player("Player 3", boardGame);
    Player player4 = new Player("Player 4", boardGame);

    System.out.println("Starting game...\n\nThe following players are playing the game:");
    System.out.println("Name: " + player1.getName());
    System.out.println("Name: " + player2.getName());
    System.out.println("Name: " + player3.getName());
    System.out.println("Name: " + player4.getName());

    boolean gameInProgress = true;
    int round = 1;
    while (gameInProgress) {
      System.out.println("\nRound " + round + ":");
      gameInProgress = boardGame.playCL();
      round++;
    }

  }
}
