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

    // Setup simulator scene
    private Parent setup() {
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        createNodesAtRandom(1000);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
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
        agent.flock(agents);
        //agent.efficientFlock(agents);
        //agent.applyBehaviors(agents, target);
        agent.updatePosition();
        agent.checkEdges();
    }

    // Create new agent
    private void createNode() {
        double x = Math.ceil(mouseX);
        double y = Math.ceil(mouseY);

        Agent agent = new Agent(x, y);
        agents.add(agent);
        root.getChildren().add(agent.display());
    }

    // Create specified amount of agents at random positions. For init of scene
    private void createNodesAtRandom(int amount) {
        for (int i = 0; i < amount; i++) {
            double x = Math.ceil(Math.random() * WIDTH);
            double y = Math.ceil(Math.random() * HEIGHT);

            Agent agent = new Agent(x, y);
            agents.add(agent);
            root.getChildren().add(agent.display());
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
