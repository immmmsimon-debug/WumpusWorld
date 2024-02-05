# Todo

#   Modify Score
- Reward getting gold: +50;
- Reward killing wumpus (stretch goal): +100

## Make goal gettable
- add some logic that if the player is on the gold: +50
  - Swtich graphic to empty chest

## Implement Stack
- Create a stack and "push" every move
  - This allow you to back track if you are in danger
  - you can also use this to back track all the way to the beginning if you find the gold (terrible solution)


### Kill Wumpus
- Add the ability to kill the Wumpus
  - Player gets one "shot" total
  - Shot: Goes through the entire map to kill him

#### AI
- Start simple: just gettig to work and return is goal 1
  - Create new ai methods, do not destroy a working solution enven if bad)
- Creat new ai's and attempt better scoring soltuions
  - One idea
    - create a list of all possible moves
    - Score all posible moves (heuristic)
    - Choose the best move
    - Create a method that can get you to any move
  - Strech algorithms (make your own first)
    - A* pathfinding
    - Dykstra's Algorith