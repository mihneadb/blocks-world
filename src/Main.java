import java.u1til.*;

public class Main {

    static boolean DEBUG = true;

    /*
        Main function, acts as the model of the program. Variables that running program
        is based on are declared here, and initiates the solver.
    */
    public static void main(String[] args) throws Exception {
        // Blocks declared A through G
        Block A = new Block("A");
        Block B = new Block("B");
        Block C = new Block("C");
        Block D = new Block("D");
        Block E = new Block("E");
        Block F = new Block("F");
        Block G = new Block("G");

        /*
            creates initial world with blocks placed as a template of the initial
            world requirements
        */
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

        /*
            The target world that must be achieved from the initial world. With the changes
            that will be applied.
        */
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

        /*
            Initiate the solver and then find the solution to transform the 
            initial world. This solution finding is timed and recorded.
        */
        Solver solver = new Solver(world, targetWorld);

        long now = System.currentTimeMillis();
        ArrayList<Action> solution = solver.findSolution();
        long now2 = System.currentTimeMillis();

        /*  
            Once solution is found, the solutions actions are performed accordingly.
            A seperate solver is used to perform actions to ensure modularity.
        */
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
