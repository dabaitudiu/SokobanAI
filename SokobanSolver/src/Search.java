import java.util.*;

public class Search {
    private boolean verbose;

    public Search(boolean verbose) {
        this.verbose = verbose;
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

    public void dfs(State state) {
        Stack<State> stack = new Stack<>();
        HashSet<State> visited = new HashSet<>();
        stack.push(state);
        while (!stack.isEmpty()) {
            if (verbose) System.out.println("Current stack");
            if (verbose) for (State a : stack) System.out.print(a.getMove() + " ");
            State curr = stack.pop();
            visited.add(curr);
            if (verbose) {
                System.out.println(curr.toString() + " has been visited. ");
                System.out.println("--------------------Next Popped map: ---------------------");
                curr.loadMap();
                curr.printMap();
                System.out.println(curr.toString());
            }
            if (curr.reachedGoal()) {
                System.out.println();
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) stack.push(e);
                    else {
                        if (verbose) {
                            System.out.println("player: " + curr.getPlayer().toString());
                            System.out.println("boxes: ");
                            for (Point k : curr.getBoxes()) {
                                System.out.print(k.toString() + " ");
                            }
                            System.out.println();
                        }
                        if (verbose) System.out.println(e.toString() + " has been visited. Thus pass. ");
                    }
                }
            }
        }
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
                else if (heuristic.equals("singleMatch")) return t1.singleMatch() - t2.singleMatch();
                else return 0;
            }
        });
        List<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            System.out.println(curr.toString() + " is popped. with cost = " + curr.getMove().length());
            System.out.println("--------------------Popped map: ---------------------");
            curr.loadMap();
            curr.printMap();
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
            if (verbose)System.out.println(curr.toString() + " has been visited. ");
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
                        if (verbose) {
                            System.out.println("player: " + curr.getPlayer().toString());
                            System.out.println("boxes: ");
                            for (Point k : curr.getBoxes()) {
                                System.out.print(k.toString() + " ");
                            }
                            System.out.println();
                        }
                        if (verbose) System.out.println(e.toString() + " has been visited. Thus pass. ");
                    }
                }
            }
        }
    }

}
