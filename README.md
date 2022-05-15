# Competitive Snake | Final Project For Comp Sci
 


A multiplayer Snake game, which allows users to compete and prove
who is a better snake player.

## In Progress

- [x] Figure out how to connect a Server and a Client
  - [x] Figure out how to make them communicate
- [x] Have Snake Game Logic Made
  - [x] Classic mode
  - [ ] Competitive mode
- [x] Have Snake Board being visualized and sent between a Server and a Client
  - [x] Have two Boards, their Snakes and apples being rendered
  - [ ] Add a choice to play SinglePlayer Snake
  - [x] Add Menus to the GUI to be able to Host or Join the Server
- [x] Make a Multi-Threaded Server to handle multiple Client inputs
  - [x] See the updated boards during the game

## How To Play It?

In order to start the game and play with your friend you need to
to be on the same network. Or if you are on different networks,
one of the users have to have their ports open in order to
connect to them.

There are multiple settings such as:

* The Game Speed/Snake Speed

* Amount of Apples Spawning

* Board Size

* Classic or Competitive Mode
  * Classic is just a classic Snake Game where a person eats apples and
    increases in size.
    
  * Competitive is a mode similar to competitive Tetris, the moment one
    of the users eats an apple, instead of them being increased in size
    the opponent will instead.
    
A server host has total control over the game settings. The moment the server is opened
the user who hosts the server needs to tell their IP and Port for the other user to join.

---
PS I'm super bad at coding. Please spare me. I just learned sockets
It's probably the hardest ~~(overexgaration, but it was hard)~~ project I did this year.
