# **StickHero**
The first project on my Github that is timeless. Created as a course-project during my 2nd year at IIIT Delhi.
## Basic Information

**What this is:** A simple and fun game where you have to constantly reach the next pillar to survive. The idea was taken from <a href="https://youtu.be/MIxayJB89ic?si=l7oab-cQbKB1m9AM">Stickhero<a/>, however implementation varies greatly. <br>
**Expected Users:** Anyone bored.<br>
**Tech Stack:** JavaFX, JUnit, Maven.<br>

## Running the game

1. Clone. Preferably, open the project through `Intellij IDEA`
2. Run the `StickHero.java` file, located at `StickHero/src/main/java/com/project/stickhero`

## Instructions for the game

- **Generate stick:** Press `SPACEBAR`.
- **Collect Hearts/Flip Player:** Press the `S` key.
- **Exit Game:** Click on the `EXIT` button to close the game if you cannot revive or do not wish to play again.

## Warning

**Everything from this point on has been written as a part of project-submission details. Reading below, mostly, is pointless.**
<br>

## Design Patterns Used

- **Singleton Pattern:** Ensures only one instance of the player exists in the game.
- **Factory Method Pattern:** Used to continuously generate different Pillars and Hearts without requiring player details.

## Scoring System

- **Acquired Heart:** +1 point per heart collected.
- **Perfect Stick Length:** +1 point for generating a stick of perfect length.

## JUnit Tests

JUnit tests are implemented to verify various functionalities:

1. **`testHeartCounter()`**: Passes if `heartCounter` is either 2 or 3, indicating that 1 heart has been generated.
2. **`wontRevive()`**: Passes if the player does not revive.
3. **`hasRevived()`**: Passes if the player successfully revives.
4. **`SingletonDP()`**: Tests the Singleton implementation.
5. **`testHeartScore()`**: Verifies the scoring, with a score of 1 in this case.

## Saving High Score

- The player’s score is saved and compared with the high score when clicking the ‘Exit’ button on the Game Over page.
- The high score includes the number of hearts collected and the number of perfect length sticks generated. This score can be used to revive the player if the required amount is gathered.

## Classes & Functionalities

- **`STICK`**: Manages stick rotation after generation.
- **`PILLAR`**: Generates pillars using the Factory Method pattern.
- **`PLAYER`**: Contains methods such as `whenDead()` for post-death scenarios and additional methods for player movement.
- **`DATA`**: Tracks game statistics and generates hearts.
- **`HOMEPAGE`**: Connects to `homepage.fxml` and opens the homepage after player revival.
- **`GAME OVER`**: Manages transitions between the homepage and game-playing page, and handles player revival.
- **`STICKHERO`**: Contains the main game flow, including `rungame()`, collision detection, and file statistics reading.
- **`ForJunit`**: Contains JUnit test cases:
  - **`testHeartCounter()`**: Tests if heart count is correct.
  - **`wontRevive()`**: Tests if the player does not revive.
  - **`hasRevived()`**: Tests if the player successfully revives.
  - **`SingletonDP()`**: Tests Singleton design pattern.
  - **`testHeartScore()`**: Tests scoring functionality.

## Acknowledgments

- **JavaFX:** Less tutorials, however, a god-gifted game development library for beginners.
- **JUnit**: Useful? Maybe?
