public class Predicate implements Comparable {
    private String name;
    private Block arg1;
    private Block arg2;

    public static String ON = "on";
    public static String ONTABLE = "ontable";
    public static String CLEAR = "clear";
    public static String HOLD = "hold";
    public static String ARMEMPTY = "armempty";

    public Predicate(String name, Block arg1, Block arg2) {
        this.name = name;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public Predicate(String name, Block arg1) {
        this(name, arg1, null);
    }

    public Predicate(String name) {
        this(name, null, null);
    }

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

    public String getName() {
        return name;
    }

    public Block getArg1() {
        return arg1;
    }

    public Block getArg2() {
        return arg2;
    }

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
