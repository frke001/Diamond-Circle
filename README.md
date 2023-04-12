# DiamondCircle Game

This application implements the game "DiamondCircle" which is played on a matrix with a minimum dimension of 7x7 and a maximum dimension of 10x10. The game can be played by a minimum of 2 and a maximum of 4 players. The dimensions of the matrix and the number of players are determined before starting the game, and user input validation is required.

## Game Rules

- Each player has a name that must be unique and four figures of the same color.
- There are three types of figures: regular, floating, and super-fast.
- Each figure can be red, green, blue, or yellow.
- Regular and super-fast figures can fall into a hole, while the floating figure remains hovering above the hole.
- In addition to the figures that players use, there is also a "ghost" figure, which starts moving when the first player moves.
- The ghost figure moves along the path and places bonus fields - diamonds - on the path.
- The number of diamonds placed ranges from 2 to the size of the matrix and is chosen randomly.
- Placing diamonds takes place every 5 seconds and lasts until the end of the game.
- When the ghost figure encounters a diamond, it "picks it up," and the number of fields it passes is increased by the number of diamonds it has collected.
- Figures move along the path shown in the image below, and the matrix has a minimum dimension of 7x7.
- The order of the players is determined randomly, and the players take turns to make a move.
- A move is defined as moving a figure from one position to another by a certain number of fields.
- When moving, if the field to be moved to is already occupied, the figure is placed on the next available field.
- Moving from one field to another takes one second.
- The way of movement is determined based on a randomly selected card from a deck of 52 cards.
- There are regular cards and special cards, which can create holes on the path.
- If a figure is on a hole, and it is not a floating figure, it falls.
- The game ends when all the players run out of figures, i.e., each player's figure has reached the end or all figures have fallen.
- Information about the fields passed and the time of movement is stored for each figure.
- At the end of the game, the results are saved in text files named `IGRA_current_time.txt`.

![image](https://user-images.githubusercontent.com/93668747/231577038-ad9b89f9-74ce-44e6-b5b5-a659518856ee.png)
![image](https://user-images.githubusercontent.com/93668747/231577160-cad8ccf4-c7f2-44fd-a99c-44609933d4a3.png)


