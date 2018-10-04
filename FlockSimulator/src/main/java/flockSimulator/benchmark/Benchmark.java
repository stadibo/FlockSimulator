/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.benchmark;

import flockSimulator.domain.AgentGenerator;
import flockSimulator.domain.Vector;
import flockSimulator.util.MathWrapper;

/**
 *
 * @author peje
 */
public class Benchmark {

    private String test;
    private AgentGenerator generator;

    public Benchmark(String test) {
        this.test = test;
        this.generator = new AgentGenerator(
                12.0, // size
                100.0, // awareness
                4.0, // maxSpeed
                0.2, // maxForce
                1280, // resolution of simulation window
                720,
                false // rotation
        );
    }

    public void run() {
        int[] numberOfAgents;
        switch (test) {
            case "TEST_1":
                break;
            case "TEST_2":
                break;
            case "TEST_3":
                break;
            default:
                numberOfAgents = new int[]{25, 50, 100, 200, 400, 800, 1600};
                this.generator.clearAgents();
                runBenchmark(numberOfAgents);
                break;
        }

    }

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
            int rnd = (int) MathWrapper.random();
            this.generator.createAgent(rnd, rnd);
        }
    }

    private void resetGenerator() {
        this.generator.clearAgents();
    }

    private void printResult(long start, long end, int amount) {
        long elapsedMillis = (end - start);
        double avgUpdateTime = elapsedMillis / 1000.0;
        double estimatedFramesPerSecond = 1000.0 / avgUpdateTime;

        System.out.println("----- " + amount + " -----");
        System.out.println("Time for " + amount + String.format(" updates: %d ms", elapsedMillis));
        System.out.println(String.format("Average time per update %.5f ms", avgUpdateTime));
        System.out.println("Estimated FPS: " + estimatedFramesPerSecond);

    }

}
