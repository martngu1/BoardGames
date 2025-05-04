package no.ntnu.idatg2003.mappe10.model.actions;

import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.TileAction;

public class ExtraActions implements TileAction {
    public void performAction(Player player){
        System.out.println("ExtraActions performed");
    }
}
