package no.ntnu.idatg2003.mappe10.model.engine;

import no.ntnu.idatg2003.mappe10.model.board.BoardGameObserver;
import no.ntnu.idatg2003.mappe10.model.player.Player;
import no.ntnu.idatg2003.mappe10.model.tile.Property;

public class MonopolyGame extends BoardGame {

    public MonopolyGame() {
        super();
    }


    @Override
    public void onPassStartTile(Player player){
        player.addToBalance(200);
        System.out.println(player.getName() + " passed the start tile and received 200.");
    }

}

