package flocksimulator.benchmark;

import flocksimulator.benchmark.mock.MockAgentGenerator;
import flocksimulator.benchmark.mock.MockSpatialAgentGenerator;
import flocksimulator.domain.AgentGenerator;
import flocksimulator.domain.Generator;
import flocksimulator.domain.SpatialAgentGenerator;
import flocksimulator.util.Vector;
import flocksimulator.util.MathWrapper;

/**
 * Benchmarking class for running the simulation in different configurations and
 * printing result to the console
 *
 * @author peje
 */
public class Benchmark {

    private String[] tests;
    private Generator generator;

    public Benchmark(String[] tests) {
        this.tests = tests;
    }

    /**
     * Run benchmarks based on input from user
     */
    public void run() {
        int[] numberOfAgents;
        for (String test : tests) {
            switch (test) {
                case "TEST_BRUTE":
                    System.out.println("Brute force + distanceNoSqrt");
                    this.generator = new AgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false // rotation
                    );
                    this.generator.setAlignment(1.0);
                    this.generator.setCohesion(1.0);
                    this.generator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_BIN":
                    System.out.println("Bin lattice spatial data structure + distanceNoSqrt");
                    this.generator = new SpatialAgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false, // rotation
                            40 // cell size 
                    );
                    this.generator.setAlignment(1.0);
                    this.generator.setCohesion(1.0);
                    this.generator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_BRUTESQRT":
                    System.out.println("Brute force + simple distance calculation");
                    this.generator = new MockAgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false // rotation
                    );
                    this.generator.setAlignment(1.0);
                    this.generator.setCohesion(1.0);
                    this.generator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_BINSQRT":
                    System.out.println("Spatial + simple distance calculation");
                    this.generator = new MockSpatialAgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false, // rotation
                            40
                    );
                    this.generator.setAlignment(1.0);
                    this.generator.setCohesion(1.0);
                    this.generator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Run simulation for 1000 cycles and measure total amount of time required
     * using production versions of Generator classes
     *
     * @param numberOfAgents how many agents to test for on each round of
     * benchmark
     */
    public void runBenchmark(int[] numberOfAgents) {
        System.out.println("----- TESTING -----");
        Vector target = new Vector(1280 / 2, 720 / 2);
        for (int agentAmount : numberOfAgents) {
            initGenerator(agentAmount);

            long start = System.currentTimeMillis();

            for (int i = 0; i < 1000; i++) {
                this.generator.updateAgents(target);
            }

            long end = System.currentTimeMillis();

            printResult(start, end, agentAmount);
            resetGenerator();
        }
        System.out.println("-------------------");
    }

    private void initGenerator(int amount) {
        for (int i = 0; i < amount; i++) {
            double x = MathWrapper.ceil(MathWrapper.random() * 1280);
            double y = MathWrapper.ceil(MathWrapper.random() * 720);
            this.generator.createAgent(x, y);
        }
    }

    private void resetGenerator() {
        this.generator.clearAgents();
    }

    private void printResult(long start, long end, int amount) {
        long elapsedMillis = (end - start);
        double avgUpdateTime = elapsedMillis / 1000.0;
        double estimatedFramesPerSecond = 1000.0 / avgUpdateTime;

        System.out.println("----- " + amount + " agents -----");
        System.out.println("Time for 1000" + String.format(" updates: %d ms", elapsedMillis));
        System.out.println(String.format("Average time per update %.5f ms", avgUpdateTime));
        System.out.println("Estimated FPS: " + estimatedFramesPerSecond);

    }

}
