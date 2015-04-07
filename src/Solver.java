import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Solver {
    private ArrayList<Predicate> startWorld;
    private ArrayList<Predicate> targetWorld;

    public Solver(ArrayList<Predicate> startWorld, ArrayList<Predicate> targetWorld) {
        this.startWorld = new ArrayList<Predicate>(startWorld);
        this.targetWorld = new ArrayList<Predicate>(targetWorld);
    }

    public void setStartWorld(ArrayList<Predicate> startWorld) {
        this.startWorld = startWorld;
    }

    /**
     * Runs A* and finds the list of actions that need to be executed to get to `targetWorld`.
     */
    public ArrayList<Action> findSolution() {
        PriorityQueue<ProblemState> open = new PriorityQueue<ProblemState>();

        open.add(new ProblemState(null, startWorld, null, 0, solutionDistance(startWorld, targetWorld)));
        boolean found = false;
        ProblemState solutionState = null;
        while (!open.isEmpty() && !found) {
            ProblemState best = open.poll();

            ArrayList<Action> options = getOptions(best.world);
            for (Action action: options) {
                ArrayList<Predicate> newWorld = performAction(best.world, action);
                ProblemState successor = new ProblemState(best,newWorld, action,best.g + 1,
                        solutionDistance(newWorld, targetWorld));

                if (isSolution(newWorld, targetWorld)) {
                    found = true;
                    solutionState = successor;
                    break;
                }

                open.add(successor);
            }
        }
        return makeSolution(solutionState);
    }

    /**
     * Applies given action to given world. Returns a new world.
     */
    public ArrayList<Predicate> performAction(ArrayList<Predicate> world, Action action) {
        ArrayList<Predicate> newWorld = new ArrayList<Predicate>();
        newWorld.addAll(world);

        Pair<ArrayList<Predicate>, ArrayList<Predicate>> changes = action.apply();
        ArrayList<Predicate> toRemove = changes.getFst();
        ArrayList<Predicate> toAdd = changes.getSnd();

        newWorld.removeAll(toRemove);
        newWorld.addAll(toAdd);

        return newWorld;
    }

    /**
     * Get a list of all the possible actions that `world` allows.
     */
    private ArrayList<Action> getOptions(ArrayList<Predicate> world) {
        ArrayList<Action> options = new ArrayList<Action>();
        boolean isArmEmpty = false;
        Block heldBock = null;

        for (Predicate p: world) {
            if (p.getName().equals(Predicate.ARMEMPTY)) {
                isArmEmpty = true;
            }
            if (p.getName().equals(Predicate.HOLD)) {
                heldBock = p.getArg1();
            }
        }

        // unstack
        if (isArmEmpty) {
            for (Predicate p1 : world) {
                if (p1.getName().equals(Predicate.ON)) {
                    Block top = p1.getArg1();
                    Block bottom = p1.getArg2();
                    // find if it is clear
                    boolean isClear = false;
                    for (Predicate p2 : world) {
                        if (p2.getName().equals(Predicate.CLEAR) && p2.getArg1().equals(top)) {
                            isClear = true;
                            break;
                        }
                    }
                    if (isClear) {
                        options.add(new Action(Action.UNSTACK, top, bottom));
                    }
                }
            }
        }

        // stack
        if (!isArmEmpty) {
            for (Predicate p1 : world) {
                if (p1.getName().equals(Predicate.CLEAR)) {
                    Block bottom = p1.getArg1();
                    options.add(new Action(Action.STACK, heldBock, bottom));
                }
            }
        }

        // pickup
        if (isArmEmpty) {
            for (Predicate p1 : world) {
                if (p1.getName().equals(Predicate.ONTABLE)) {
                    Block block = p1.getArg1();
                    // find if it is clear
                    boolean isClear = false;
                    for (Predicate p2 : world) {
                        if (p2.getName().equals(Predicate.CLEAR) && p2.getArg1().equals(block)) {
                            isClear = true;
                            break;
                        }
                    }
                    if (isClear) {
                        options.add(new Action(Action.PICKUP, block));
                    }
                }
            }
        }

        // putdown
        if (!isArmEmpty) {
            options.add(new Action(Action.PUTDOWN, heldBock));
        }

        return options;
    }

    /**
     * Construct the list of actions having found the solution node.
     */
    private ArrayList<Action> makeSolution(ProblemState end) {
        ArrayList<Action> solution = new ArrayList<Action>();
        ProblemState iter = end;
        while (iter.parent != null) {
            solution.add(iter.actionTaken);
            iter = iter.parent;
        }
        Collections.reverse(solution);
        return solution;
    }

    /**
     * Heuristic for A*. The amount of predicates in `world` that are not part of `targetWorld`.
     */
    private int solutionDistance(ArrayList<Predicate> world,
                                       ArrayList<Predicate> targetWorld) {
        int contained = 0;
        for (Predicate p: world) {
            if (targetWorld.contains(p)) {
                contained += 1;
            }
        }
        return world.size() - contained;
    }

    protected boolean isSolution(ArrayList<Predicate> world,
                                 ArrayList<Predicate> targetWorld) {
        return world.containsAll(targetWorld) && targetWorld.containsAll(world);
    }
}
