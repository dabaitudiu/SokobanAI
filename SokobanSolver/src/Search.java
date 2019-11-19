import java.util.LinkedList;
import java.util.Queue;

public class Search {

    public Search(State state) {
    }

    public void bfs(State state) {
        Queue<State> queue = new LinkedList<>();
        Queue<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            if (curr.reachedGoal()) {
                System.out.println("**************** Solution Found ! ******************");
                System.out.println(curr.getMove());
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

    public void bfsVerbose(State state) {
        Queue<State> queue = new LinkedList<>();
        Queue<State> visited = new LinkedList<>();
        queue.add(state);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
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
                    else System.out.println(e.toString() + " has been visited. Thus pass. ");
                }
            }
        }
    }

}
