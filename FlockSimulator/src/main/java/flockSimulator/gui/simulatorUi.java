/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.gui;

import flockSimulator.domain.Agent;
import flockSimulator.domain.Vector;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author peje
 */
public class simulatorUi extends Application {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    private double mouseX;
    private double mouseY;
    private Pane root;
    private ArrayList<Agent> agents = new ArrayList<>();

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private Label frameRate = new Label();
    private Label agentAmount = new Label();

    private Slider alignment = new Slider(0, 1, 0.5);
    private Slider cohesion = new Slider(0, 1, 0.5);
    private Slider separation = new Slider(0, 1, 0.5);
    private Slider maxSpeed = new Slider(0, 1, 0.5);
    private Slider maxForce = new Slider(0, 1, 0.5);
    private Label alignmentValue = new Label();
    private Label cohesionValue = new Label();
    private Label separationValue = new Label();
    private Label maxSpeedValue = new Label();
    private Label maxForceValue = new Label();

    // Setup simulator scene
    private Parent setup() {
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.getChildren().addAll(frameRate, agentAmount, alignment, alignmentValue,
                cohesion, cohesionValue, separation, separationValue, maxSpeed,
                maxSpeedValue, maxForce, maxForceValue);
        
        frameRate.setTranslateY(0);
        agentAmount.setTranslateY(20);
        
        alignment.setTranslateY(40);
        alignmentValue.setTranslateX(100);
        alignmentValue.setTranslateY(40);

        cohesion.setTranslateY(60);
        cohesionValue.setTranslateX(100);
        cohesionValue.setTranslateY(60);

        separation.setTranslateY(80);
        separationValue.setTranslateX(100);
        separationValue.setTranslateY(80);

        maxSpeed.setTranslateY(100);
        maxSpeedValue.setTranslateX(100);
        maxSpeedValue.setTranslateY(100);

        maxForce.setTranslateY(120);
        maxForceValue.setTranslateX(100);
        maxForceValue.setTranslateY(120);

        frameRate.setText("Current frame rate: ??.???");

        createNodesAtRandom(100);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();

                // Count framerate
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double framesPerSecond = 1_000_000_000.0 / elapsedNanosPerFrame;
                    frameRate.setText("Current frame rate: " + framesPerSecond);
                }
            }
        };
        timer.start();

        return root;
    }

    // Update scene, new positions of all agents
    private void update() {
        Vector mouse = new Vector(mouseX, mouseY);
        for (int i = 0; i < agents.size(); i++) {
            agentAction(agents.get(i), mouse);
        }
    }

    // Make agent behave in some way
    private void agentAction(Agent agent, Vector target) {
        agent.applyBehaviors(agents, target);
        agent.updatePosition();
        agent.checkEdges();
    }

    // Create new agent
    private void createNode() {
        double x = Math.ceil(mouseX);
        double y = Math.ceil(mouseY);

        if (300 < x || 200 < y) {
            Agent agent = new Agent(x, y, 12.0, 100.0, 4.0, 0.2, WIDTH, HEIGHT);
            agents.add(agent);
            root.getChildren().add(agent.display());
            agentAmount.setText("Amount of agents: " + agents.size());
        }

    }

    // Create specified amount of agents at random positions. For init of scene
    private void createNodesAtRandom(int amount) {
        for (int i = 0; i < amount; i++) {
            double x = Math.ceil(Math.random() * WIDTH);
            double y = Math.ceil(Math.random() * HEIGHT);

            Agent agent = new Agent(x, y, 12.0, 100.0, 4.0, 0.2, WIDTH, HEIGHT);
            agents.add(agent);
            root.getChildren().add(agent.display());
            agentAmount.setText("Amount of agents: " + agents.size());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello simulation!");

        primaryStage.setScene(new Scene(setup()));
        primaryStage.getScene().setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });
        primaryStage.getScene().setOnMouseDragged(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            createNode();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
