import java.util.ArrayList;

public class ProblemState implements Comparable {
    ProblemState parent;
    ArrayList<Predicate> world;
    Action actionTaken;
    int g, h;

    /*
        Constructor for the problem state, the parameters take in the parent problem state,
        the world, which action is taken, as well as g and h variables. These are then applied
        to the object's global variables.
    */
    public ProblemState(ProblemState parent, ArrayList<Predicate> world, Action actionTaken, int g, int h) {
        this.parent = parent;
        this.world = world;
        this.actionTaken = actionTaken;
        this.g = g;
        this.h = h;
    }

    /*
        The getter for F which is g + h. A getter is used as the global variables are not set as public.
    */
    public int getF() {
        return g + h;
    }

    /*
        Uses Integer.compare to compare the F values from 'this' object and the given
        object in the parameters. Integer.compare is used to compare the integer values
        of the F values even in cases where they are decimal values.
    */
    @Override
    public int compareTo(Object o) {
        ProblemState other = (ProblemState) o;
        return Integer.compare(this.getF(), other.getF());
    }
}
