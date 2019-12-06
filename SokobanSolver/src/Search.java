import java.util.*;

public class Search {
    private boolean verbose;

    /**
     * Instance a Search Object for executing a search.
     * @param verbose Default: False. Set to true if you want to see debug information.
     */
    public Search(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * BFS starts with a node and search all its neighbors
     * that haven't been visited respectively in level-order.
     * @param state
     */
    public void bfs(State state) {
        // Track time
        long startTime = System.currentTimeMillis();

        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.add(state);

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            if (curr.reachedGoal()) {
//                System.out.println("**************** Solution Found ! ******************");
//                System.out.println(curr.getMove());
//                System.out.println("bfs: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.print((System.currentTimeMillis() - startTime) + " ");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }

    /**
     * DFS starts with a node and added all its neighbors that haven't been
     * visited to a stack. It continues popping out nodes from stack until all nodes
     * have been visited.
     * @param state
     */
    public void dfs(State state) {
        long startTime = System.currentTimeMillis();

        Stack<State> stack = new Stack<>();
        HashSet<State> visited = new HashSet<>();
        stack.push(state);

        while (!stack.isEmpty()) {
            State curr = stack.pop();
            visited.add(curr);
            if (curr.reachedGoal()) {
//                System.out.println("**************** Solution Found ! ******************");
//                System.out.println(curr.getMove());
//                System.out.println("dfs: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.print((System.currentTimeMillis() - startTime) + " ");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) stack.push(e);
                }
            }
        }
    }

    /**
     * Iterative Depth Search Algorithm solves the problem that dfs can get very long result, which uses too much space.
     * @param state
     */
    public void ids(State state) {
        long startTime = System.currentTimeMillis();
        int limit = 50;
        int max_limit = 500;

        while(limit <= max_limit) {
//            System.out.println("IDS Limit: " + limit);
            Stack<State> stack = new Stack<>();
            HashSet<State> visited = new HashSet<>();
            stack.push(state);

            while (!stack.isEmpty()) {
                State curr = stack.pop();
                visited.add(curr);
                if (curr.reachedGoal()) {
//                    System.out.println("**************** Solution Found ! ******************");
//                    System.out.println(curr.getMove());
//                    System.out.println("ids: " + (System.currentTimeMillis() - startTime) + " ms");
                    System.out.print((System.currentTimeMillis() - startTime) + " ");
                    return;
                } else {
                    for (State e : curr.getNeighbors()) {
                        if (!visited.contains(e) && e.getMove().length() <= limit) stack.push(e);
                    }
                }
            }
            limit += 10;
        }
    }

    /**
     * Uniform-Cost Search works similar like BFS/DFS, however, it uses a priority queue rather than
     * a queue/stack. Every time it pops out a node that has fewer cost. In this program, we uses the
     * number of moves as the cost for every node. The method 'getMove()' in Class 'State' offers this function.
     * @param state
     */
    public void ucs(State state) {
        long startTime = System.currentTimeMillis();
        Queue<State> queue = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State t1, State t2) {
                return t1.getMove().length() - t2.getMove().length();
            }
        });
        Set<State> visited = new HashSet<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
//            System.out.println(curr.toString() + " is popped. with cost = " + curr.getMove().length());
            if (curr.reachedGoal()) {
//                System.out.println("**************** Solution Found ! ******************");
//                System.out.println(curr.getMove());
//                System.out.println("ucs: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.print((System.currentTimeMillis() - startTime) + " ");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }

    /**
     * Greedy search algorithm. Works like UCS, it also uses a priority queue. However,
     * greedy uses heuristic function to determine which node to pop next. A heuristic
     * function can be a euclidean distance, which measures as (x-x0)^2 + (y-y0)^2; or
     * can either be a manhattan distance, which measures as |x-x0| + |y-y0|.
     * @param state
     * @param heuristic
     */
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
        HashSet<State> visited = new HashSet<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            if (curr.reachedGoal()) {
//                System.out.println("**************** Solution Found ! ******************");
//                System.out.println(curr.getMove());
//                System.out.println("greedy(" + heuristic + "): "+ (System.currentTimeMillis() - startTime) + " ms");
                System.out.print((System.currentTimeMillis() - startTime) + " ");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }

    /**
     * A* search works using Cost function F(n) = G(n) + H(n)
     * G(n) stands for cost
     * H(n) stands for heuristic function.
     * It combines past cost and future cost prediction.
     * @param state
     * @param heuristic
     */
    public void astar(State state, String heuristic) {
        long startTime = System.currentTimeMillis();
        Queue<State> queue = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State t1, State t2) {
                int v1 = t1.getMove().length();
                int v2 = t2.getMove().length();
                if (heuristic.equals("euclidean")) return (v1 + t1.euclidean()) - (v2 + t2.euclidean());
                else if (heuristic.equals("manhatten")) return (v1 + t1.manhatten()) - (v2 + t2.manhatten());
                else return 0;
            }
        });
        HashSet<State> visited = new HashSet<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            if (curr.reachedGoal()) {
//                System.out.println("**************** Solution Found ! ******************");
//                System.out.println(curr.getMove());
//                System.out.println("A* (" + heuristic + "): "+ (System.currentTimeMillis() - startTime) + " ms");
                System.out.print((System.currentTimeMillis() - startTime) + " ");
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }
    }





}
