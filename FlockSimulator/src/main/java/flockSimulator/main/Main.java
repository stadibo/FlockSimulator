/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.main;

import flockSimulator.benchmark.Benchmark;
import flockSimulator.gui.simulatorUi;
import static javafx.application.Application.launch;

/**
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
            launch(simulatorUi.class, args);
        }
    }
}
