package no.ntnu.idatg2003.mappe10.model;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private Player winner;
    private List<Player> playerList;
    private Dice dice;
    private final int numberOfTiles = 100;

    /**
     * Creates a new player list. Old player list is overwritten.
     */
    public void createPlayerList(){
        playerList = new ArrayList<>();
    }

    /**
     * Adds a player to the board game.
     *
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        playerList.add(player);
    }

    /**
     * Creates a new board with the given number of tiles.
     */
    public void createBoard(){
        board = new Board();
        Tile dummy = null;
        for (int i = numberOfTiles; i >= 1; i--){
            Tile tile = new Tile(i);
            tile.setNextTile(dummy);
            board.addTile(tile);
            dummy = tile;
        }

    }

    /**
     * Creates a new dice with the given number of sides.
     *
     * @param numberOfDice the number of dice to create
     */
    public void createDice(int numberOfDice){
        dice = new Dice(numberOfDice);
    }

    /**
     * Plays the game. Each player in the player list rolls the dice and moves the number of steps rolled.
     */
    public boolean play() {
        for (Player player : playerList) {
            currentPlayer = player;
            int diceRoll = dice.roll();
            currentPlayer.move(diceRoll);
            if (playerWon()) {
                winner = currentPlayer;
                return false;
            }
        }
        return true;
    }

    /**
     * Play one round of the game for the CL version. Prints out the current player and the tile the player is on.
     */
    public boolean playCL() {
        for (Player player : playerList) {
            currentPlayer = player;
            int diceRoll = dice.roll();
            currentPlayer.move(diceRoll);
            if (playerWon()) {
                winner = currentPlayer;
                System.out.println("The winner is: " + getWinner().getName());
                return false;
            }
            System.out.println("Player: " + currentPlayer.getName() +
                  " on tile " + currentPlayer.getCurrentTile().getTileId());
        }
        return true;
    }

    /**
     * Returns true if the current player has won the game, false if not.
     *
     * @return true if the current player has won the game
     */
    private boolean playerWon() {
        return currentPlayer.getCurrentTile().getTileId() == board.getLastTile().getTileId();
    }

    /**
     * Returns the player that has won the game.
     *
     * @return the player that has won the game
     */
    public Player getWinner(){
        return winner;
    }

    /**
     * Returns the board of the board game.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

}
