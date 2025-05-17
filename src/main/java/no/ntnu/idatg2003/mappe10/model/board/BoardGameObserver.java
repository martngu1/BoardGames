package no.ntnu.idatg2003.mappe10.model.board;

/**
 * Interface for observing changes in a board game.
 */
public interface BoardGameObserver {
    void updatePosition(String playerName, int newPosition);
}
