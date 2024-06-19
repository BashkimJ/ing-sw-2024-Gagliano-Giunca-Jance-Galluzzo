# Codex Naturalis

<a href="https://www.craniocreations.it/prodotto/codex-naturalis"><img src="https://m.media-amazon.com/images/I/814qEh0JKdS._AC_UF1000,1000_QL80_.jpg" align="middle" width = 400px height = 300px></a>

## Introduction
The purpose of this project is to implement the game Codex Naturalis using java language. The game has been developed from Cranio Creations.
To consult the regulation of the game you can visit the Cranio Creations page by clicking on the above image. This project is part of the evaluation of the Final Exam of the Software Engineering at the Politecnico
di Milano, Italy.

## Group components
- <a href="https://github.com/BashkimJ">Bashkim Jance</a>
- <a href="https://github.com/SimoneGalluzzo">Simone Galluzzo</a>
- <a href="https://github.com/EmanueleGagliano">Emanuele Gagliano</a>
- <a href="https://github.com/samuelgiunca">Samuele Giunca</a>

## Implemented Features
| Feature | Implemented |
|---------|-------------|
|Simplified Rules       |    ✔️            |
| Completed Rules       |     ✔️        |
|TUI                    | ✔️             |
|GUI                    |✔️|
|Socket                 | ✔️|
|RMI                    | ✔️|
|Resilience to Clients Disconnections|✔️ |
|Chat|✔️|
|Persistence| ✔️|
|Multiple Games|❌ |

## Java Doc And Testing
You can consult the documentation of this project by seeing the comments at the top of each method or class. In our project we have been mostly concentrated in testing the Model which contains all the game's logic. He have been
able to cover overall:
- Between 90%-95% of all the methods in the Model.
- 80% of the lines included in the Model.
- 100% of all the classes included in the model.
To consult the tests you can find them at src/test/java/it/polimi/ingsw.

## How to run the game.
Firstly you must obtain the two executable jar files that can be found in Deriverables/Jars. They are named Server.jar and Client.jar.
### How to run Server.jar
1. Open a terminal which can be the Intellij terminal, CMD or PowerShell.
2. Navigate to the directory where the jar files are saved.
3. Run this command: java -jar Server.jar.
4. At this point the server must start and listen for requests in both modalities: Socket and RMI.

### How to run Client.jar
1. Open a terminal.
2. Navigate to the directory where the jar files are saved.
3. Run the command java -jar Client.jar [args].
4. Args --> If you want to run the game using GUI no args are required, otherwise if you want to run it using TUI the argument must be "-t".
5. Example: Using GUI -> java -jar Client.jar.
6. Example: Using TUI-> java -jar Client.jar "-t".
7. During Run Time: To choose socket connection you must type -s when asked, -r for RMI.
8. To start a new game type y when asked, n to resume a possible saved game.
### Considerations
Our projects uses Ansi Escape Codes which infact are not supported from CMD or the PowerShell of windows. So if you want to run the game using the TUI mode you must use a terminal that supports the ANSI Escape
Codes. We would recommend using the Intellij Idea Terminal or the <a href="https://learn.microsoft.com/it-it/windows/terminal/install">Windows Terminal</a>.Also will work fine with Ubuntu terminal. If you want to move the jar files from one folder
to another be sure to also move the file save_game.ser created at runtime to save the state of a started game. If you don't do so you want be able to resume a previous game and at runtime a new saved_game.ser will 
be created to save the state of the new created game.
