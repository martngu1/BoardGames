package no.ntnu.idatg2003.mappe10.model.tile.tileaction;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.util.List;

/**
 * Represents an action that can be performed when a player lands on a chance card tile.
 * The action can be a random event that affects the player's balance or position on the board.
 */
public class ChanceCardAction implements TileAction{
    private String description;
    // List of chance cards with their respective actions
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

    /**
     * Performs the action of drawing a chance card and executing its effect.
     *
     * @param player the player who is performing the action
     * @param game   the game instance
     */
    @Override
    public void performAction(Player player, BoardGame game) {
        // Randomly select a chance card from the list
        int randomIndex = (int) (Math.random() * chanceCards.size());
        String selectedCard = chanceCards.get(randomIndex);

        // Perform the action based on the selected card
        switch (selectedCard) {
            case "Gain 100":
                player.addToBalance(100);
                setDescription("Chance card: " + selectedCard + " - You gain 100");
                break;
            case "Gain 200":
                player.addToBalance(200);
                setDescription("Chance card: " + selectedCard + " - You gain 200");
                break;
            case "Go to jail":
                player.setTurnsToSkip(1);
                player.placeOnTile(game.getTileById(11)); // Jail Tile
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
                // Check if the player can afford to pay
                handlePlayerPayment(player, game, 100);
                setDescription("Chance card: " + selectedCard + " - You pay 100");
                break;
            case "Pay 200":
                handlePlayerPayment(player, game, 200);
                setDescription("Chance card: " + selectedCard + " - You pay 100");
                break;
        }
    }

    /**
     * Gets the description of the action.
     *
     * @return the description of the action
     */
    @Override
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description of the action.
     *
     * @param description the new description of the action
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Handles the payment process for the player.
     *
     * @param player the player who is making the payment
     * @param game   the game instance
     * @param amount the amount to be paid
     */
    private void handlePlayerPayment(Player player, BoardGame game, int amount) {
        int totalSellValue = game.getTotalSellValue(player);
        if (player.canAfford(amount)) {
            player.subtractFromBalance(amount);
            game.notifyBalanceUpdate(player);
        } else if (player.getBalance() + totalSellValue < amount) {
            System.out.println(player.getName() + " cannot afford the payment and is bankrupt.");
            int restBalance = player.getBalance();
            player.subtractFromBalance(restBalance - 1); // Leave them with ~0
            game.notifyBalanceUpdate(player);
            game.removePlayer(player);
        } else {
            System.out.println(player.getName() + " must sell property to pay " + amount);
            game.notifyOfferToSellProperty(player, amount);
        }
    }
}
