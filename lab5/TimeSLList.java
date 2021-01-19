import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        System.out.println("Timing Table for getLast");
        for (int N = 1000; N < 160000; N*=2) {
            SLList<Integer> L = new SLList<>(0);
            for (int i = 0; i < N; ++i) {
                L.addLast(0);
            }

            Stopwatch sw = new Stopwatch();
            for (int i =0; i< 10000;i += 1) {
                L.getLast();
            }
            double time = sw.elapsedTime();

            Ns.add(N);
            opCounts.add(10000);
            times.add(time);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
