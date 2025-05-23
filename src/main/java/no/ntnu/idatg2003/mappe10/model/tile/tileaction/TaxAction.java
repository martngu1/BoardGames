package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

public class TaxAction implements TileAction{

    private final int taxAmount;
    private String description;

    public TaxAction(int taxAmount, String description) {
        this.taxAmount = taxAmount;
        this.description = description;
    }

    @Override
    public void performAction(Player player, BoardGame game) {
        player.subtractFromBalance(taxAmount);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getTaxAmount() {
        return taxAmount;
    }


}
