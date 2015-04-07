import java.util.ArrayList;

public class NonStrictSolver extends Solver {

    public NonStrictSolver(ArrayList<Predicate> startWorld, ArrayList<Predicate> targetWorld) {
        super(startWorld, targetWorld);
    }

    /**
     * This one looks for targetWorld to be *included* in world, since there are multiple goals.
     */
    @Override
    protected boolean isSolution(ArrayList<Predicate> world, ArrayList<Predicate> targetWorld) {
        return world.containsAll(targetWorld);
    }
}
