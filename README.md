# Croquet Tournament

**Welcome!**

This is a JavaFX program for managing a tournament (not necessarily Croquet 😉) with different games and players. You
can add a game to the tournament, see an overview of the current standings, also with per-game details, and edit some
settings.

The program has language support for German and default (English).

## Table of Contents

[Installation instructions](#installation-instructions)

- [Provided](#provided)
- [Source code](#source-code)

[Usage](#usage)

- [Running the program](#running-the-program)
- [Using the program](#using-the-program)

## Installation instructions

### Provided

If you just want to run the program on your computer, there is no need to install Java or any frameworks. Just download
and run the *.exe* installer from the [releases page](https://github.com/flopsif/croquet-tournament/releases) (Windows
only). If you want a solution without installing the program, download the *croquet.zip* file and unpack it to a desired
location (Windows only).

### Source code

If you want to use the source code and run the program via maven or create your own image/installer, you need to have at
least Java 16 and [Maven](https://maven.apache.org/) installed on your system and added to your PATH. To get the source
code,run `git clone https://github.com/flopsif/croquet-tournament.git` on the command line in a desired location or
download the code from [here](https://github.com/flopsif/croquet-tournament).

To create your own image and *zip*, run `mvn clean javafx:jlink`. The output will be located in the *target* directory.

To create your own installer, run `mvn clean javafx:jlink jpackage:jpackage`. The installer will be located in the
*target/dist* directory.

To create a cross-platform fat jar, you need to uncomment the indicated additional dependencies in the *pom.xml*
and run `mvn clean compile jar:jar package`. The *jar* will be located in the *target* directory.
***Important: With these dependencies un-commented, you cannot run any of the other commands mentioned above.***

## Usage

### Running the program

To run the program, just search for *CroquetTournament* in your program search (installed version)
or run the *launcher.bat* file inside the *bin* directory from your unpacked *croquet.zip*.

To run the source code, navigate to the directory where the *src* folder and the *pom.xml* are located and
run `mvn clean javafx:run` from a command line.

### Using the program

*Please note: The program is navigable by keyboard. Press <kbd>TAB</kbd> to change selection, press and hold <kbd>
ALT</kbd> to see underlined letters for shortcuts.*

Once inside the program, the main window opens with four buttons for the main actions:
adding a game, tournament overview, editing an old game (not yet available), editing the settings. Click one of these
buttons to open the respective window.

#### Tournament loading error

When you run the program for the first time, a warning will pop up that the tournament could not be loaded
(in this case, because you have not saved a tournament so far). Click the left button to add a new tournament and enter
an identifier for the tournament (like *2021.2* or *fall21*) and click *OK*.

If the warning pops up on another occasion, something went wrong with loading the tournament. Make sure that the file
with the desired tournament is present at the right location and the identifier for the current tournament is correct.
To enter another identifier, click the middle button and enter the desired tournament identifier.

#### Adding a game

Inside the window for adding a new game, enter a date (if not entered (correctly), it defaults to the current date)
and a difficulty measurement (like *easy* or *extremely difficult*; defaults to *not set*). Below, choose the players
according to their performance.

*If a player is not selected, they are assumed absent and get the points of the place two places worse than the last
player selected. Example: Seven out of eleven players have been selected and therefore places one to seven are assigned
to those players. The remaining four players (assumed absent)
get points as if they all scored to ninth (7+2) place.*

To save the game, click OK.

#### Tournament overview

The first window shows the current standings with the total points of each player, sorted from highest/best to
lowest/worst. To see the individual game details, click the button below the scoreboard. A new window opens with each
game showed individually.

#### Editing an old game

*This function is not yet available, but will be added in a future update. Stay tuned!*

#### Editing settings

To edit and save settings, enter your new values according to the tooltips (hover with the pointer over the field).

In the first field, enter a **unique** identifier for the current tournament (like *2021.2* or *fall21*). If you enter
an identifier for an existing tournament, the program will try to load it when the settings are saved.

In the second field, enter all player names separated with commas (order does not matter). When saved, new players are
automatically added to the current tournament.

In the third field, enter the points according to places separated by commas. *Example: In your tournament, first place
should get 15 points, second place 12, third place 10, fourth place 9, etc. You then enter *15,12,10,9,8,7,6,5,4,3,2,1*
into the field.*

In the last field, enter the path to the directory where the tournament files should be saved. Use the button at the
right to select a directory without having to input the path by yourself.
***Important**: The existing files at the old location are **not** moved to the new directory. If you want to have them
at the new location, you need to move them by yourself.*

To save the settings, click *Save*, to save and close, click *OK*, to discard your changes, click *Cancel*.