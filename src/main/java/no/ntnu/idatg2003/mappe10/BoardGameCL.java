package no.ntnu.idatg2003.mappe10;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.util.Iterator;
import java.util.List;

public class BoardGameCL {
  public static void main(String[] args) {
    BoardGame boardGame = new BoardGame();
    boardGame.initializeGame(2, 100, 10, 10);
    new Player("Player 1", boardGame);
    new Player("Player 2", boardGame);
    new Player("Player 3", boardGame);
    new Player("Player 4", boardGame);
    boardGame.placeAllPlayersOnTile(boardGame.getBoard().getFirstTile());

    System.out.println("Starting game...\n\nThe following players are playing the game:");
    Iterator<Player> iterator = boardGame.getPlayerListIterator();
    while (iterator.hasNext()) {
      Player player = iterator.next();
      System.out.println("Name: " + player.getName());
    }

    boolean gameInProgress = true;
    int round = 1;
    while (gameInProgress) {
      System.out.println("\nRound " + round + ":");
      gameInProgress = boardGame.playCL();
      round++;
    }

  }
}
