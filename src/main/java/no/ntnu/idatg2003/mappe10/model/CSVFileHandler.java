package no.ntnu.idatg2003.mappe10.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFileHandler{

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

    public List<Player> loadPlayers(String filename, BoardGame game) {
        List <Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Player player = new Player(parts[0], game);
                player.setPlayingPiece(parts[1]);
                players.add(player);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
