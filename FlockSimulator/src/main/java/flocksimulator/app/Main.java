package flocksimulator.app;

import flocksimulator.benchmark.Benchmark;
import flocksimulator.gui.SimulatorUi;
import static javafx.application.Application.launch;

/**
 * Main class for determining whether to launch simulator GUI or to launch
 * benchmarks for algorithm
 *
 * @author peje
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].substring(0, 4).equals("TEST")) {
                Benchmark performanceTest = new Benchmark(args);
                performanceTest.run();
            }
        } else {
            launch(SimulatorUi.class, args);
        }
    }
}
