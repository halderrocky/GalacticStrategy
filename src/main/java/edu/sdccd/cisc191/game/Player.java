package edu.sdccd.cisc191.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Galactic Strategy game.
 * Each player has a name and a fleet of GalacticShips.
 */
public class Player {
    private String name;
    private List<GalacticShip> fleet;

    /**
     * Constructs a Player with the specified name and initializes an empty fleet.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.fleet = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<GalacticShip> getFleet() {
        return fleet;
    }

    /**
     * Adds a GalacticShip to the player's fleet.
     *
     * @param ship The GalacticShip to add.
     */
    public void addShip(GalacticShip ship) {
        fleet.add(ship);
    }

    /**
     * Returns the total health of all ships in the fleet.
     *
     * @return The total health of the fleet.
     */
    public int getTotalFleetHealth() {
        int totalHealth = 0;
        for (GalacticShip ship : fleet) {
            totalHealth += ship.getHealth();
        }
        return totalHealth;
    }

    /**
     * Saves the player's fleet data to a file.
     *
     * @param filename The name of the file to save to.
     * @throws IOException If an I/O error occurs.
     */
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Player: " + name + "\n");
            for (GalacticShip ship : fleet) {
                writer.write("Ship: " + ship.getName() + " Health: " + ship.getHealth() + "\n");
            }
        }
    }

    /**
     * Loads the player's fleet data from a file.
     *
     * @param filename The name of the file to load from.
     * @throws IOException If an I/O error occurs.
     * @throws Exception If the file format is incorrect.
     */
    public void loadFromFile(String filename) throws IOException, Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null || !line.startsWith("Player: ")) {
                throw new Exception("Invalid file format");
            }
            name = line.substring(8);
            fleet.clear();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Ship: ")) {
                    String[] parts = line.split(" Health: ");
                    if (parts.length != 2) {
                        throw new Exception("Invalid ship data format");
                    }
                    String shipName = parts[0].substring(6);
                    int shipHealth = Integer.parseInt(parts[1]);
                    fleet.add(new GalacticShip(shipName, shipHealth));
                }
            }
        }
    }
}
