# **STICKHERO**

1. Run using Intellij/Eclipse by running the StickHero.java file located at StickHero\src\main\java\com\project\stickhero.

2. **How to PLAY:**
Press SPACE BAR to generate the stick.
Press S key to collect hearts/flip the player upside down.
If you cannot revive/do not want to play again, click on ‘EXIT’ to close the game.


3. **The 2 Design Patterns that we have used are:**
* Singleton: There is only one player in the game. We limited the number of players by implementing this Design Pattern.
* Factory Method: We are continuously generating different Pillars and Hearts. Therefore, we have used 2 methods which will generate the same without asking the client for details.
        
4. **The scoring system is as follows:**
* Acquired Heart: + 1 point/heart
* Perfect Stick Length: +1 point/heart


5. **Junit Tests:**
        We have made Junit Test Cases to check the following things:
        Attribute Data.HeartCounter: To check if hearts are being generated at the right count.


6. **To save HighScore:**
The player has to click the button ‘Exit’ on the GameOver Page, only then will the score they scored will be saved and compared with the high score.
Moreover, the high score includes the number of hearts collected + the number of perfect length sticks generated. You can use this to revive if you gather the required amount.

### **CLASSES & FUNCTIONALITIES:**


* STICK: Has a method to rotate the stick after it has been generated.
* PILLAR: Used to generate pillars by implementing Factory Method.
* PLAYER: has methods whenDead() for the aftermath of when the player dies, and 2 more methods associated with the translation of the player through the game.
* DATA: Used to score the statistics of the game and has a method to generate hearts.
* HOMEPAGE: Connected to the homepage.fxml file of the game, opens homepage after revival of the players.
* GAME OVER: Connects the homepage, and the game-playing page together. Also used to revive the players.
* STICKHERO: Contains the main flow of the game, includes rungame(), checks for collision and reads statistics from file.
* ForJunit: Contains the JUnit Tests:
1. testHeartCounter(): passes if the heartCounter is either 2 or 3, i.e, 1 heart has been generated.
2. wontRevive(): passes if the player does not revive.
3. hasRevived(): passes if the player revives.
4. SingletonDP(): to test for Singleton implementation.
5. testHeartScore(): tests score, taken equal to 1 for this case.
