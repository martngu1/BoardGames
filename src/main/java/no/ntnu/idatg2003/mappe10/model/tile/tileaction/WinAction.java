package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class WinAction implements TileAction {

  private String description;

  public WinAction(String description) {
    this.description = description;
  }

  @Override
  public void performAction(Player player, BoardGame game) {
    game.setWinner(player);
  }

  @Override
  public String getDescription() {
    return description;
  }
}
