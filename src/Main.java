import java.util.*;

public class Main {

    static boolean DEBUG = true;

    public static void main(String[] args) throws Exception {
        Block A = new Block("A");
        Block B = new Block("B");
        Block C = new Block("C");
        Block D = new Block("D");
        Block E = new Block("E");
        Block F = new Block("F");
        Block G = new Block("G");

        // Simple setup.
        ArrayList<Predicate> world = new ArrayList<Predicate>(Arrays.asList(
                new Predicate(Predicate.CLEAR, A),
                new Predicate(Predicate.ON, A, B),
                new Predicate(Predicate.ON, B, C),
                new Predicate(Predicate.ONTABLE, C),
                new Predicate(Predicate.CLEAR, D),
                new Predicate(Predicate.ON, D, E),
                new Predicate(Predicate.ON, E, F),
                new Predicate(Predicate.ON, F, G),
                new Predicate(Predicate.ONTABLE, G),
                new Predicate(Predicate.ARMEMPTY)
        ));

        ArrayList<Predicate> targetWorld = new ArrayList<Predicate>(Arrays.asList(
                new Predicate(Predicate.CLEAR, B),
                new Predicate(Predicate.ON, B, D),
                new Predicate(Predicate.ON, D, G),
                new Predicate(Predicate.ON, G, A),
                new Predicate(Predicate.ONTABLE, A),
                new Predicate(Predicate.CLEAR, E),
                new Predicate(Predicate.ON, E, C),
                new Predicate(Predicate.ON, C, F),
                new Predicate(Predicate.ONTABLE, F),
                new Predicate(Predicate.ARMEMPTY)
        ));

        System.out.println("From " + world);
        System.out.println("To " + targetWorld);
        System.out.println();

        Solver solver1 = new Solver(world, targetWorld);
        Solver solver2 = new Solver(world, targetWorld);

        int steps = 0;

        while (true) {
            if (solver1.isSolution(world, targetWorld)) {
                System.out.println("Agents are done.");
                break;
            }

            System.out.println("World: " + world);

            ArrayList<Predicate> newWorld = null;

            Action action1 = solver1.findSolution().get(0);
            ArrayList<Predicate> newWorld1 = solver1.performAction(world, action1);
            newWorld = newWorld1;
            System.out.println("Agent applied: " + action1);

            solver2.setStartWorld(newWorld1);
            if (solver2.isSolution(newWorld, targetWorld)) {
                System.out.println("New world: " + newWorld);
                System.out.println("Agents are done.");
                break;
            }

            Action action2 = solver2.findSolution().get(0);
            if (!action1.inConflictWith(action2)) {
                newWorld = solver2.performAction(newWorld, action2);
            } else {
                action2 = null;
            }


            if (DEBUG) {
                if (action2 != null) {
                    System.out.println("Agent applied: " + action2);
                } else {
                    System.out.println("Agent waited.");
                }
                if (newWorld != null) {
                    System.out.println("New world: " + newWorld);
                }
                System.out.println();
            }

            world = newWorld;
            solver1.setStartWorld(world);
            solver2.setStartWorld(world);
            steps += 1;
        }
    }
}
