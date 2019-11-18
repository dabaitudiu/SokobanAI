# sokoban
cs271 sokoban solver

### Usage:
#### 0. Class: Point
- Encapsulated point information
#### 1. Class: MapParser
- Input a txt file, like:
```
#######
#*@  !#
#     #
#######
```
- converts to **Normal Input Form**
```
7 4
18 1 1 1 2 1 3 1 4 1 5 1 6 1 7 2 1 2 7 3 1 3 7 4 1 4 2 4 3 4 4 4 5 4 6 4 7 
1 2 3 
1 2 6 
2 2 
```
#### 2. Class: Sokoban
- Read a Normal Input Form txt file, load all information in to a class instance.

#### 3. Class: State
- State showing current positions of player and boxes.

#### 4. Class: SokobanSolver
- BFS solver

#### 5. inputs/map.txt, num.txt
- map is direct map
- num is normal input form
