public class Predicate implements Comparable {
    /*
        The private variables that can only be accessed directly
        through the class.
    */
    private String name;
    private Block arg1;
    private Block arg2;

    /*
        The states of the predicate. Static as they are not changed.
    */
    public static String ON = "on";
    public static String ONTABLE = "ontable";
    public static String CLEAR = "clear";
    public static String HOLD = "hold";
    public static String ARMEMPTY = "armempty";

    /*
        The constructor to create a predicate based on 2 arguments given
        with the name. Three constructors are made for this class so that 
        the same class can be used under different criteria.
    */
    public Predicate(String name, Block arg1, Block arg2) {
        this.name = name;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    /*
        The constructor to create a predicate based on 1 arguments given
        with the name.
    */
    public Predicate(String name, Block arg1) {
        this(name, arg1, null);
    }

    /*
        The constructor to create a predicate based on 0 arguments given
        with the name.
    */
    public Predicate(String name) {
        this(name, null, null);
    }

    /*
        This function is used to convert arguments to string format as
        "name(arg1, arg2)". This method uses a StringBuilder to put together
        the string by adding the non-null components in the required format.
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
        The name is private so the getter function returns the name of the Predicate.
    */
    public String getName() {
        return name;
    }

    /*
        The arg1 is private so the getter function returns the arg1 of the Predicate.
    */
    public Block getArg1() {
        return arg1;
    }

    /*
        The arg2 is private so the getter function returns the arg2 of the Predicate.
    */
    public Block getArg2() {
        return arg2;
    }

    /*
        This function compares the object in the parameter with 'this'
        object. It compares the global variables one by one and stores
        the equality in the variable 'ok' which it returns. This method
        is used to ensure all variables are equal otherwise it is false.
    */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Predicate)) {
            return super.equals(obj);
        }
        Predicate other = (Predicate) obj;

        boolean ok = name.equals(other.name);
        if (arg1 != null) {
            ok = ok && arg1.equals(other.arg1);
        }
        if (arg2 != null) {
            ok = ok && arg2.equals(other.arg2);
        }

        return ok;
    }

    /*
        This function compares 'this' predicates value to a given object's.
        only compares arguments if they are non null. because otherwise they 
        do not exist.
    */
    @Override
    public int compareTo(Object o) {
        Predicate other = (Predicate) o;
        int c = name.compareTo(other.name);
        if (c == 0 && arg1 != null) {
            c = arg1.getName().compareTo(other.getName());
            if (c == 0 && arg2 != null) {
                c = arg2.getName().compareTo(other.getName());
            }
        }
        return c;
    }
}
