package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private AStarGraph<Vertex> input;
    private Vertex start;
    private Vertex end;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private int numStatesExplored;
    private double explorationTime;
    private DoubleMapPQ<Vertex> pq;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> parent;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.input = input;
        this.start = start;
        this.end = end;
        outcome = SolverOutcome.SOLVED;
        solution = new ArrayList<>();
        numStatesExplored = 0;
        pq = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        parent = new HashMap<>();

        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        parent.put(start, start);

        Stopwatch sw = new Stopwatch();
        while (pq.size() > 0 && !pq.getSmallest().equals(end) && sw.elapsedTime() < timeout) {
            Vertex p = pq.removeSmallest();
            numStatesExplored++;
            for (WeightedEdge<Vertex> e : input.neighbors(p)) {
                Vertex q = e.to();
                double w = e.weight();
                if (distTo(p) + w < distTo(q)) {
                    distTo.put(q, distTo(p) + w);
                    parent.put(q, p);
                    double priority = distTo(q) + input.estimatedDistanceToGoal(q, end);
                    if (pq.contains(q)) {
                        pq.changePriority(q, priority);
                    } else {
                        pq.add(q, priority);
                    }
                }
            }
        }
        explorationTime = sw.elapsedTime();

        if (pq.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (!pq.getSmallest().equals((end))) {
            outcome = SolverOutcome.TIMEOUT;
        }

        if (outcome == SolverOutcome.SOLVED) {
            Vertex curr = end;
            solution.add(end);
            while (!curr.equals(start)) {
                curr = parent.get(curr);
                solution.add(curr);
            }
            Collections.reverse(solution);
        }

    }

    private double distTo(Vertex v) {
        if (distTo.containsKey(v)) {
            return  distTo.get(v);
        }
        return Double.POSITIVE_INFINITY;
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return distTo(end);
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }
}
