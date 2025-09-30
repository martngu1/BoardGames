# BoardGame Application

The **BoardGame** application is a Java-based simulation of generic board game mechanics. It allows you to create a board with tiles, manage players, define property rules, and simulate turn-based gameplay with flexible rules. This project emphasizes modularity, testability, and clean object-oriented design.

## Features

- Tile-based board system with customizable layout
- Turn-based player movement
- Property ownership and rent system
- Support for special tile types (e.g., rent-scaling properties, skip-turn tiles)
- Player balance tracking (income and expenses)
- Easily extendable and testable architecture

## How to Run

### Requirements

- Java 21+
- Maven 3.6+

### Build and Run

```bash
git clone https://git.gvk.idi.ntnu.no/course/prog2005/prog2005-2025-workspace/martngu/assignment1-cloudtech.git
Open the project in your IDE (e.g., IntelliJ IDEA)
Do a mvn clean install to build the project

```
This will launch the main menu, where you can select a game and start playing.

## Board Games
- **Ladder Game**: A simple game where players move up and down a ladder based on dice rolls.
    - *Special Tiles*:
        - **Ladder Tile**: Moves the player up to a higher or lower position on the board
        - **Prison Tile**: Sends the player to prison for a turn. If double is rolled, the player can escape. (Only 1 player in prison at a time)
        - **Home Tile**: Sends the player back to the start position.
- **Monopoly**: A classic property trading game where players buy, sell, and trade properties to bankrupt their opponents.
  - *Special Tiles*:
    - **Property Tile**: Players can buy properties and charge rent to other players.
    - **Chance Tile**: Players draw a chance card that can have various effects.
    - **Prison Tile**: Players can go to jail for various reasons.


## Project Structure

- `src/main/java/no.ntnu.idatg2003.mappe10/model/`: Game logic and data models.
- `src/main/java/no.ntnu.idatg2003.mappe10/ui/view/`: JavaFX UI for each game and common components.
- `src/main/java/no.ntnu.idatg2003.mappe10/ui/controller/`: Controllers for game logic and UI interaction.
- `src/main/resources/`: Game assets, saved games, player CSVs, pictures, mp3.sounds and stylesheets.


## Future Implementation
- The Lost Diamond (In progress)

## Contributors
- Martin Nguyen
- Stian Julius Vo
