package no.ntnu.idatg2003.mappe10.model.board;

import no.ntnu.idatg2003.mappe10.model.player.Player;

public interface BoardGameObserver {
    void updatePosition();
    void onTileAction(String player, String actionDescription);
    void onGameOver(String name);
}
