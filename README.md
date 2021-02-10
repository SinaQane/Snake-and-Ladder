# Snake-and-Ladder

My Basic Programming project for university course

It's similar to the original Snake and Ladder game with some differences:

1. Pieces can move in each direction. Winner is the player whose score is more than the his/her rival afer 40 moves (20 moves each).

2. Dices' sides don't have equal chances, getting a prize or getting the same number on a dice twice in a row changes the sides' chances. Getting a number "2" from a dice resets the chances to "1-1-1-1-1-1". You can right click on dice to get its info.

3. Pieces can't pass through the walls. They also only can go to the cells with the same color and the white cells.

4. Pieces have abilities and they can also die. We have four kind of pieces:

A) Healer (Blue piece): Heals a random piece in its adjcent cells in each move. Making a dead piece alive again. Getting a number "1" on the dice activates it.
B) Sniper (Red piece): Kills a random piece in its adjcent cells in each move. Making a alive piece dead and diactivating it. Getting a number "5" on the dice activates it.
C) Bomber (Green piece): Kills every piece, destroys every prize in a 3*3 area and makes its cell black. Getting a number "3" on the dice activates it.
D) Thief (Yellow piece): It's always activated, it can go to every cell, and it can grab and move a prize from a point to the other one.

5. There are four different sankes:

A) Normal: It moves piece to its tail and takes 3 points from you.
B) Magical: It moves piece to its tail and gives 3 points to you. It also makes the piece activated.
C) Killer: It moves piece to its tail and takes 3 points from you. It also kills the piece.
D) Earthworm: It moves piece to a random location and takes 3 points from you.

6. If you exit the game, after coming back to the game and entering your names in order, you can resume your last game.
