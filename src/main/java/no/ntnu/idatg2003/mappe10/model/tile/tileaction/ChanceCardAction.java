package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ChanceCardAction implements TileAction{
        private String description;
        private final List<String> chanceCards = List.of(
                "Gain 200",
                "Go to jail",
                "Jail free card",
                "Go to start",
                "Pay 100",
                "Pay 200"
                );

        public ChanceCardAction(String description) {
            this.description = description;
        }

        @Override
        public void performAction(Player player, BoardGame game) {
            // Randomly select a chance card from the list
            int randomIndex = (int) (Math.random() * chanceCards.size());
            String selectedCard = chanceCards.get(randomIndex);

            // Perform the action based on the selected card
            switch (selectedCard) {
                case "Gain 200":
                    player.addToBalance(200);
                    break;
                case "Go to jail":
                    player.skipNextTurns(1);
                    player.placeOnTile(game.getTileById(20));
                    break;
                case "Jail free card":
                    player.skipNextTurns(-1);
                    break;
                case "Go to start":
                    player.placeOnTile(game.getTileById(1));
                    break;
                case "Pay 100":
                    player.subtractFromBalance(100);
                    break;
                case "Pay 200":
                    player.subtractFromBalance(200);
                    break;
            }
        }

        @Override
        public String getDescription() {
            return description;
        }
}
