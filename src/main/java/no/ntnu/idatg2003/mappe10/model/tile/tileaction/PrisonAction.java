package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class PrisonAction implements TileAction {

    private String description;
    private Player currentPrisoner;

    public PrisonAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public void performAction(Player player, BoardGame game) {
        // Only imprison if no one is currently in prison
        if (currentPrisoner == null || !currentPrisoner.shouldSkipTurn()) {
            currentPrisoner = player;
            player.setTurnsToSkip(1);
            System.out.println(player.getName() + " has been sent to prison and will skip their next turn.");
        } else {
            System.out.println(player.getName() + " avoided prison because it's already occupied by " + currentPrisoner.getName());
        }
    }
}