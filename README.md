## UC Irvine CS271 Sokoban AI

For better understanding and detailed explanations, our report is [Here](https://github.com/dabaitudiu/sokoban/blob/master/SokobanAI.pdf)

### Problem statement & Input format:
```
a. input is 5 lines defining the board :

   1.    sixeH sizeV, e.g. "3 5"
   2.    nWallSquares a list of coordinates of wall squares, e.g. "12 1 1 1 2 1 3 2 1 2 3 3 1 3 3 4 1 4 3 5 1 5 2 5 3"
   3.    nBoxes a list of coordinates of boxes, e.g. "1 3 2"
   4.    nStorageLocations a list of coordinates of storage locations, e.g. "1 4 2"
   5.    player’s initial location x and y, e.g. "2 2"

b. output is a single line, beginning with nMoves followed by a sequence of letters (U,D,L,R) indicating direction of the move, e.g. "1 D".
```

### Usage
For your convenience, you may import it as a Intellij project. Head forward to the SokobanSolver directories and run the SokobanSolver class.
```
1. For a sample test: 
   cd SokobanSolver
   cd src
   java SokobanSolver.java

2. If you want to change input:
   a. Go to benchmarks and paste your formal input
   b. In the file SokobanSolver.java, change the following:

   static final String filename = "../formal_inputs/12_1_a.txt";

3. If you **FAIL** to compile / run:
   Solution a: javac *.java, then run
       

   Solution b: If above still not working, run these commands consequentially:
   	javac Point.java
	javac Sokoban.java
	javac DeadLockDetector.java
	javac MapParser.java
	javac State.java
	javac Search.java
	javac SokobanSolver.java
	java SokobanSolver

    If still not working, please inform zhenhal3@uci.edu.
```


### Explanations

#### 0. Directories Clarifications
- **SokobanSolver** is the final version that will be used in solving sokoban problems. This program concisely provides fastest computations and comparions using different algorithms.
- **SokobanBeta** is the test version which have newest test features. In this class, a ``MapGenerator`` Class, and ``CNN `` is used for exploration. However, the speed is affected and thus not used for actual.

#### 1. Helper Class: MapParser
If you want to transfer a typical sokoban 2D map to a formal input, you can create an instance of this class
```java
public static void main(String[] args) throws FileNotFoundException {
        parseMap("maps/your_map.txt");
}
```
Your map should be in the form like the map below, where # stands for wall, $ for box, .for target, @ for player.
```
###########
#   ###   #
#   $  ## #
#      #  #
#    ######
#      #. #
#         #
# @  #    #
#####  $  #
# .       # 
###########
```


#### 2. Helper Class: MapGenerator
To spawn sokoban maps in large numbers, I implemented this sokoban map generator. Its rough idea is to ask the player to define the number of walls, boxes, etc., and spawn these artifacts randomly on the map. Then it will run the fastest greedy algorithm to check whether this map is available. If a map is proved to be available, it will output the formal format to player assigned output file.
```java
    public static void main(String[] args) throws FileNotFoundException {
        int n = 5; // number of instances you want to spawn.
        int start = 1; // filename start index
        int index = start;
        for (int i = start; i < n+start; i++) {
            String outputFile = String.format("file_%d.txt",i);
            MapGenerator generator = new MapGenerator(9,9,10,3,SEED,outputFile);
            generator.generate(20); // parameter here indicates the maximum times your program tries to avoid out of memory.
        }
}
```

#### 3. Class : Search
The search class provides a platform to manipulate the basic 'state' of a graph search. We implemented BFS, DFS, IDS, UCS, Greedy, and A* search in this class.


#### 3. Class : State
The basic class which describes a current state of a problem.
```java
State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false, hst);

Search search = new Search(false); // parameter here indicates verbose or not.
```

#### 4. Class : Sokoban
Create an instance of this class to read and parse a formal input.
```java
Sokoban sokoban = new Sokoban(filename);
```

### Output:
- A sample output for comparing speed be like this (measured in ms):
```

BFS: 417  DFS: 8  IDS: 174  UCS: 248  Gr: 47  Gr: 93  A*: 149  A*: 65 
Process finished with exit code 0

```

You can change the search class so that they print paths:
```
**************** Solution Found ! ******************
ddrruuuudlll
bfs: 122 ms
**************** Solution Found ! ******************
rrlrdrdlrdllllllururrrrulllllurrrrlllldrrrrrdrdllrrdllluuurruuldll
ids: 97 ms
**************** Solution Found ! ******************
drdruuuuldll
ucs: 68 ms
**************** Solution Found ! ******************
rddruuuuldll
greedy(euclidean): 2 ms
**************** Solution Found ! ******************
drdruuuudlll
greedy(manhatten): 7 ms
**************** Solution Found ! ******************
drdruuuuldll
A* (euclidean): 8 ms
**************** Solution Found ! ******************
ddrruuuuldll
A* (manhatten): 8 ms

```


