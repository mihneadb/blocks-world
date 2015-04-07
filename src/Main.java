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
                new Predicate(Predicate.ON, A, B),
                new Predicate(Predicate.ON, B, C),
                new Predicate(Predicate.ON, C, D),
                new Predicate(Predicate.ONTABLE, D),
                new Predicate(Predicate.CLEAR, A),
                new Predicate(Predicate.ARMEMPTY)
        ));

        ArrayList<Predicate> targetWorld1 = new ArrayList<Predicate>(Arrays.asList(
                new Predicate(Predicate.ON, A, B),
                new Predicate(Predicate.ONTABLE, B),
                new Predicate(Predicate.CLEAR, A),
                new Predicate(Predicate.ARMEMPTY)
        ));

        ArrayList<Predicate> targetWorld2 = new ArrayList<Predicate>(Arrays.asList(
                new Predicate(Predicate.ON, C, D),
                new Predicate(Predicate.ONTABLE, D),
                new Predicate(Predicate.CLEAR, C),
                new Predicate(Predicate.ARMEMPTY)
        ));

        double difficulty1 = 4;
        double difficulty2 = 4;

        System.out.println("From " + world);
        System.out.println("To A " + targetWorld1);
        System.out.println("To B " + targetWorld2);
        System.out.println();

        NonStrictSolver solver1 = new NonStrictSolver(world, targetWorld1);
        NonStrictSolver solver2 = new NonStrictSolver(world, targetWorld2);
        boolean done1 = false, done2 = false;
        // 1 or 2.
        int token = 1 + new Random().nextInt(2);

        int steps = 0;
        while (!done1 || !done2) {
            if (!done1 && solver1.isSolution(world, targetWorld1)) {
                done1 = true;
                System.out.println("Agent1 is done in " + steps + " steps.");
                double effort = (double) steps / 2;
                System.out.println("Effort: " + effort);
                System.out.println("Score: " + (difficulty1 - effort));
            }
            if (!done2 && solver2.isSolution(world, targetWorld2)) {
                done2 = true;
                System.out.println("Agent2 is done in " + steps + " steps.");
                double effort = (double) steps / 2;
                System.out.println("Effort: " + effort);
                System.out.println("Score: " + (difficulty2 - effort));
            }

            Action action1 = null, action2 = null;
            if (!done1) {
                action1 = solver1.findSolution().get(0);
            }
            if (!done2) {
                action2 = solver2.findSolution().get(0);
            }

            ArrayList<Predicate> newWorld = null;
            // Conflict.
            if (action1 != null && action2 != null && action1.inConflictWith(action2)) {
                Action action = null;
                if (token == 1) {
                    action = action1;
                    token = 2;
                    System.out.println("Agent1 won the conflict.");
                } else {
                    action = action2;
                    token = 1;
                    System.out.println("Agent2 won the conflict.");
                }
                newWorld = solver1.performAction(world, action);

                if (DEBUG) {
                    System.out.println("World: " + world);
                    System.out.println("Applied: " + action);
                    System.out.println("New world: " + newWorld);
                    System.out.println();
                }
            } else {
                if (action1 != null) {
                    newWorld = solver1.performAction(world, action1);
                }
                if (action2 != null) {
                    newWorld = solver2.performAction(newWorld, action2);
                }

                if (DEBUG) {
                    System.out.println("World: " + world);
                    if (action1 != null) {
                        System.out.println("Applied: " + action1);
                    }
                    if (action2 != null) {
                        System.out.println("Applied: " + action2);
                    }
                    if (newWorld != null) {
                        System.out.println("New world: " + newWorld);
                    }
                    System.out.println();
                }
            }

            world = newWorld;
            solver1.setStartWorld(world);
            solver2.setStartWorld(world);
            steps += 1;
        }
    }
}
