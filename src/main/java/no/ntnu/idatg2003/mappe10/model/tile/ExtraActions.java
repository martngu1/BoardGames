package no.ntnu.idatg2003.mappe10.model.tile;

import no.ntnu.idatg2003.mappe10.model.player.Player;

public class ExtraActions implements TileAction {
    public void performAction(Player player){
        System.out.println("ExtraActions performed");
    }
}
