package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.engine.LostDiamondGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class WinAction implements TileAction {

  private String description;

  public WinAction() {
    this.description = "Victory!";
  }

  @Override
  public void performAction(Player player, BoardGame game) {
    if (game instanceof LostDiamondGame) {

    }
    game.setWinner(player);
  }

  @Override
  public String getDescription() {
    return description;
  }
}
