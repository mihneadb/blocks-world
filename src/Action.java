import java.util.ArrayList;

public class Action {
    private String name;
    private Block arg1;
    private Block arg2;

    public static String UNSTACK = "unstack";
    public static String STACK = "stack";
    public static String PICKUP = "pickup";
    public static String PUTDOWN = "putdown";

    public Action(String name, Block arg1, Block arg2) {
        this.name = name;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    // fst = what to remove, snd = what to add
    public Pair<ArrayList<Predicate>, ArrayList<Predicate>> apply() {
        ArrayList<Predicate> toRemove = new ArrayList<Predicate>();
        ArrayList<Predicate> toAdd = new ArrayList<Predicate>();

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

    public Action getOpposite() {
        if (name.equals(STACK)) {
            return new Action(UNSTACK, arg1, arg2);
        }
        if (name.equals(UNSTACK)) {
            return new Action(STACK, arg1, arg2);
        }
        if (name.equals(PICKUP)) {
            return new Action(PUTDOWN, arg1);
        }
        return new Action(PICKUP, arg1);
    }

    public Action(String name, Block arg1) {
        this(name, arg1, null);
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
