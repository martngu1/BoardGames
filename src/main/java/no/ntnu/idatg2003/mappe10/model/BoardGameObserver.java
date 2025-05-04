package no.ntnu.idatg2003.mappe10.model;

public interface BoardGameObserver {
    void updatePosition(String playerName, int newPosition);
}
