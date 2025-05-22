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
                    setDescription("Chance card: " + selectedCard + " - You gain 200");
                    break;
                case "Go to jail":
                    player.setTurnsToSkip(1);
                    player.placeOnTile(game.getTileById(11));
                    setDescription("Chance card: " + selectedCard + " - You go to jail");
                    break;
                case "Jail free card":
                    player.skipPrisonTurn(-1);
                    setDescription("Chance card: " + selectedCard + " - You get a 'Get out of jail free' card");
                    break;
                case "Go to start":
                    player.placeOnTile(game.getTileById(1));
                    player.addToBalance(200);
                    setDescription("Chance card: " + selectedCard + " - You go to start and collect 200");
                    break;
                case "Pay 100":
                    player.subtractFromBalance(100);
                    setDescription("Chance card: " + selectedCard + " - You pay 100");
                    break;
                case "Pay 200":
                    player.subtractFromBalance(200);
                    setDescription("Chance card: " + selectedCard + " - You pay 200");
                    break;
            }
        }

        @Override
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
}
