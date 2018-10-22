package flocksimulator.benchmark;

import flocksimulator.benchmark.mock.MockAgentGenerator;
import flocksimulator.benchmark.mock.MockGenerator;
import flocksimulator.benchmark.mock.MockSpatialAgentGenerator;
import flocksimulator.domain.AgentGenerator;
import flocksimulator.domain.Generator;
import flocksimulator.domain.SpatialAgentGenerator;
import flocksimulator.domain.Vector;
import flocksimulator.util.MathWrapper;

/**
 *
 * @author peje
 */
public class Benchmark {

    private String[] tests;
    private MockGenerator mockGenerator;
    private Generator generator;

    public Benchmark(String[] tests) {
        this.tests = tests;
    }

    public void run() {
        int[] numberOfAgents;
        for (String test : tests) {
            switch (test) {
                case "TEST_BRUTE":
                    System.out.println("Brute force");
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
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200, 6400};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_BIN":
                    System.out.println("Bin lattice spatial data structure");
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
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200, 6400};
                    this.generator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_JAVABRUTE":
                    System.out.println("Brute force with java ArrayList");
                    this.mockGenerator = new MockAgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false // rotation
                    );
                    this.mockGenerator.setAlignment(1.0);
                    this.mockGenerator.setCohesion(1.0);
                    this.mockGenerator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200, 6400};
                    this.mockGenerator.clearAgents();
                    runBenchmark(numberOfAgents);
                    break;
                case "TEST_JAVABIN":
                    System.out.println("Brute force with java ArrayList");
                    this.mockGenerator = new MockSpatialAgentGenerator(
                            12.0, // size
                            100.0, // awareness
                            4.0, // maxSpeed
                            0.2, // maxForce
                            1280, // resolution of simulation window
                            720,
                            false, // rotation
                            40
                    );
                    this.mockGenerator.setAlignment(1.0);
                    this.mockGenerator.setCohesion(1.0);
                    this.mockGenerator.setSeparation(1.5);
                    numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600, 3200, 6400};
                    this.mockGenerator.clearAgents();
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

    /**
     * Run simulation for 1000 cycles and measure total amount of time required
     * using mock versions of Generator classes, which use java data structures
     *
     * @param numberOfAgents how many agents to test for on each round of
     * benchmark
     */
    public void runMockBenchmark(int[] numberOfAgents) {
        System.out.println("----- TESTING -----");
        Vector target = new Vector(1280 / 2, 720 / 2);
        for (int agentAmount : numberOfAgents) {
            initGenerator(agentAmount);

            long start = System.currentTimeMillis();

            for (int i = 0; i < 1000; i++) {
                this.mockGenerator.updateAgents(target);
            }

            long end = System.currentTimeMillis();

            printResult(start, end, agentAmount);
            resetMockGenerator();
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

    private void resetMockGenerator() {
        this.mockGenerator.clearAgents();
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
