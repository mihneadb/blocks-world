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
                new Predicate(Predicate.ON, B, A),
                new Predicate(Predicate.ONTABLE, A),
                new Predicate(Predicate.ONTABLE, C),
                new Predicate(Predicate.ONTABLE, D),
                new Predicate(Predicate.CLEAR, B),
                new Predicate(Predicate.CLEAR, C),
                new Predicate(Predicate.CLEAR, D),
                new Predicate(Predicate.ARMEMPTY)
        ));

        ArrayList<Predicate> targetWorld = new ArrayList<Predicate>(Arrays.asList(
                new Predicate(Predicate.ON, C, A),
                new Predicate(Predicate.ON, B, D),
                new Predicate(Predicate.ONTABLE, A),
                new Predicate(Predicate.ONTABLE, D),
                new Predicate(Predicate.CLEAR, C),
                new Predicate(Predicate.CLEAR, B),
                new Predicate(Predicate.ARMEMPTY)
        ));

        // Complex setup.
//        ArrayList<Predicate> world = new ArrayList<Predicate>(Arrays.asList(
//                new Predicate(Predicate.ONTABLE, C),
//                new Predicate(Predicate.ONTABLE, G),
//                new Predicate(Predicate.CLEAR, A),
//                new Predicate(Predicate.CLEAR, D),
//                new Predicate(Predicate.ON, A, B),
//                new Predicate(Predicate.ON, B, C),
//                new Predicate(Predicate.ON, D, E),
//                new Predicate(Predicate.ON, E, F),
//                new Predicate(Predicate.ON, F, G),
//                new Predicate(Predicate.ARMEMPTY)
//        ));
//
//        ArrayList<Predicate> targetWorld = new ArrayList<Predicate>(Arrays.asList(
//                new Predicate(Predicate.ONTABLE, A),
//                new Predicate(Predicate.ONTABLE, F),
//                new Predicate(Predicate.CLEAR, B),
//                new Predicate(Predicate.CLEAR, E),
//                new Predicate(Predicate.ON, B, D),
//                new Predicate(Predicate.ON, D, G),
//                new Predicate(Predicate.ON, G, A),
//                new Predicate(Predicate.ON, E, C),
//                new Predicate(Predicate.ON, C, F),
//                new Predicate(Predicate.ARMEMPTY)
//        ));

        System.out.println("From " + world);
        System.out.println("To " + targetWorld);
        System.out.println();

        Solver solver = new Solver(world, targetWorld);

        long now = System.currentTimeMillis();
        ArrayList<Action> solution = solver.findSolution();
        long now2 = System.currentTimeMillis();

        for (Action action: solution) {
            ArrayList<Predicate> newWorld = solver.performAction(world, action);

            if (DEBUG) {
                System.out.println("Applying " + action);
                System.out.println("To " + world);
                System.out.println("Got " + newWorld);
                System.out.println();
            }

            world = newWorld;
        }
        System.out.println("Took " + solution.size() +  " steps.");
        System.out.println("Took " + (now2 - now) +  " ms.");
    }
}
