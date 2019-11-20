import java.util.*;

public class Search {

    public Search(State state) {
    }

    public void bfs(State state) {
        long startTime = System.currentTimeMillis();
        Queue<State> queue = new LinkedList<>();
        Queue<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            if (curr.reachedGoal()) {
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
                System.out.println("bfs: " + (System.currentTimeMillis() - startTime) + " ms");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }

    public void dfs() {

    }

    public void ucs(State state) {
        long startTime = System.currentTimeMillis();
        Queue<State> queue = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State t1, State t2) {
                return t1.getMove().length() - t2.getMove().length();
            }
        });
        List<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
//            System.out.println(curr.toString() + " is popped. with cost = " + curr.getMove().length());
            if (curr.reachedGoal()) {
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
                System.out.println("ucs: " + (System.currentTimeMillis() - startTime) + " ms");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }

    public void greedy(State state, String heuristic) {
        long startTime = System.currentTimeMillis();
        Queue<State> queue = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State t1, State t2) {
                if (heuristic.equals("euclidean")) return t1.euclidean() - t2.euclidean();
                else if (heuristic.equals("manhatten")) return t1.manhatten() - t2.manhatten();
                else return 0;
            }
        });
        List<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
//            System.out.println(curr.toString() + " is popped. with cost = " + curr.getMove().length());
            if (curr.reachedGoal()) {
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
                System.out.println("greedy(" + heuristic + "): "+ (System.currentTimeMillis() - startTime) + " ms");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }



    public void bfsVerbose(State state) {
        Queue<State> queue = new LinkedList<>();
        Queue<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            System.out.println(curr.toString() + " has been visited. ");
            System.out.println("--------------------Next Popped map: ---------------------");
            curr.loadMap();
            curr.printMap();
            System.out.println(curr.toString());
            if (curr.reachedGoal()) {
                System.out.println();
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                    else {
                        for (State a : visited) System.out.print(a.hashCode() + " ");
                        System.out.println(e.toString() + " has been visited. Thus pass. ");
                    }
                }
            }
        }
    }

}
