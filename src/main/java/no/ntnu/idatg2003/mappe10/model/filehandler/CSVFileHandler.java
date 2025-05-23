package no.ntnu.idatg2003.mappe10.model.filehandler;

import no.ntnu.idatg2003.mappe10.exceptions.PlayerFileException;
import no.ntnu.idatg2003.mappe10.model.engine.BoardGame;
import no.ntnu.idatg2003.mappe10.model.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFileHandler{

    public void savePlayers(String filePath, List<Player> players) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (Player player : players) {
                writer.write(player.getName() + "," + player.getPlayingPiece());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PlayerFileException("Error writing players to CSV file: " + filePath, e);
        }
    }

    public Map<String, String> loadPlayers(String filename) {
        Map<String, String> players = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    throw new PlayerFileException("Invalid player data in CSV file: " + line);
                }
                players.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            throw new PlayerFileException("Error reading CSV file: " + filename, e);
        }
        return players;
    }
}
