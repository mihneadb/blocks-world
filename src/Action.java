import java.util.ArrayList;

public class Action {
    /*
        Declare public variables for class, these variables are used in
        different functions so they are declared outside to use the values
        throughout.
    */

    // Variables that identify object and it's arguments used within
    private String name;
    private Block arg1;
    private Block arg2;

    // Variables that contain unchanging values of which operation is applied
    public static String UNSTACK = "unstack";
    public static String STACK = "stack";
    public static String PICKUP = "pickup";
    public static String PUTDOWN = "putdown";

    /*
        Constructor for Action class when given 2 block arguments.
    */
    public Action(String name, Block arg1, Block arg2) {
        this.name = name;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    /*
        Constructor for Action class when given 1 block arguments.
    */
    public Action(String name, Block arg1) {
        this(name, arg1, null);
    }

    /*
        Apply Function used to apply the chosen function labelled by name variable,
        this style of applying the function is used for better control of applying
        said function by need.
    */
    public Pair<ArrayList<Predicate>, ArrayList<Predicate>> apply() {
        ArrayList<Predicate> toRemove = new ArrayList<Predicate>();
        ArrayList<Predicate> toAdd = new ArrayList<Predicate>();

        /*  If statement to check which changes to apply, the possible types are those
            listed within the global variables. Then proceeds to add to either the
            toRemove array for removals or toAdd array for additions.
        */
        if (name.equals(UNSTACK)) {
            toRemove.add(new Predicate(Predicate.ON, arg1, arg2));
            toRemove.add(new Predicate(Predicate.ARMEMPTY));
            toRemove.add(new Predicate(Predicate.CLEAR, arg1));
            toAdd.add(new Predicate(Predicate.HOLD, arg1));
            toAdd.add(new Predicate(Predicate.CLEAR, arg2));
        } else if (name.equals(STACK)) {
            toRemove.add(new Predicate(Predicate.HOLD, arg1));
            toRemove.add(new Predicate(Predicate.CLEAR, arg2));
            toAdd.add(new Predicate(Predicate.ON, arg1, arg2));
            toAdd.add(new Predicate(Predicate.CLEAR, arg1));
            toAdd.add(new Predicate(Predicate.ARMEMPTY));
        } else if (name.equals(PICKUP)) {
            toRemove.add(new Predicate(Predicate.ARMEMPTY));
            toRemove.add(new Predicate(Predicate.ONTABLE, arg1));
            toRemove.add(new Predicate(Predicate.CLEAR, arg1));
            toAdd.add(new Predicate(Predicate.HOLD, arg1));
        } else if (name.equals(PUTDOWN)) {
            toRemove.add(new Predicate(Predicate.HOLD, arg1));
            toAdd.add(new Predicate(Predicate.ARMEMPTY));
            toAdd.add(new Predicate(Predicate.ONTABLE, arg1));
            toAdd.add(new Predicate(Predicate.CLEAR, arg1));
        }

        return new Pair<ArrayList<Predicate>, ArrayList<Predicate>>(toRemove, toAdd);
    }

    /*
        This function is used to convert arguments to string format as
        "name(arg1, arg2)". This method uses a StringBuilder to put together
        the string by adding the non-null components in the required format.
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); //temp string to hold string built with arguments
        sb.append(name);
        sb.append("(");
        if (arg1 != null) {
            sb.append(arg1);
            if (arg2 != null) {
                sb.append(", ");
                sb.append(arg2);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /*
        This function compares the object in the parameter with 'this'
        object. It compares the global variables one by one and stores
        the equality in the variable 'ok' which it returns. This method
        is used to ensure all variables are equal otherwise it is false.
    */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Action)) {
            return super.equals(obj);
        }
        Action other = (Action) obj;

        boolean ok = name.equals(other.name);
        if (arg1 != null) {
            ok = ok && arg1.equals(other.arg1);
        }
        if (arg2 != null) {
            ok = ok && arg2.equals(other.arg2);
        }

        return ok;
    }
}
