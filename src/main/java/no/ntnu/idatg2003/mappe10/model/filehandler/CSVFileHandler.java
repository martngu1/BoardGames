package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading player data to and from a CSV file.
 */
public class CSVFileHandler{

    /**
     * Saves a list of players to a CSV file.
     *
     * @param filePath the path to the file where the players will be saved
     * @param players  the list of players to save
     */
    public void savePlayers(String filePath, List<Player> players) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Player player : players) {
                writer.write(player.getName() + "," + player.getPlayingPiece());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a list of players from a CSV file.
     *
     * @param filename the name of the file to load players from
     * @param game     the game instance to associate with the players
     * @return a list of players loaded from the file
     */
    public List<Player> loadPlayers(String filename, BoardGame game) {
        List <Player> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Player player = new Player(parts[0],parts[0], game);
                players.add(player);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
