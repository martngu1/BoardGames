package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.util.Random;

public class RobberAction implements TileAction {
  private String description;
  private Random robber;

  public RobberAction() {
    this.description = "been robbed!";
    this.robber = new Random();
  }

  public void performAction(Player player, BoardGame game) {
    int amount = robber.nextInt(10, 220);
    player.subtractFromBalance(amount);
    if (player.getBalance() < 0) {
      player.setBalance(0);
    }
    game.notifyBalanceUpdate(player);
  }

  public String getDescription() {
    return description;
  }
}
