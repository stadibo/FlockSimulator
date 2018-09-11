/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.gui;

import flockSimulator.domain.Agent;
import flockSimulator.domain.Vector;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

/**
 *
 * @author peje
 */
public class simulatorUi extends Application {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public double mouseX;
    public double mouseY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Hello simulation!");
//        Pane box = new Pane();
//        box.setPrefSize(WIDTH, HEIGHT);

        Agent mover = new Agent(WIDTH / 2, HEIGHT / 2);
        Ellipse moverDisplay = new Ellipse(20, 20);
        moverDisplay.setFill(Color.BLACK);
        moverDisplay.setTranslateX(mover.getX());
        moverDisplay.setTranslateY(mover.getY());

        //box.getChildren(moverDisplay);
        
        Scene environment = new Scene(new Group(moverDisplay), WIDTH, HEIGHT);
        primaryStage.setScene(environment);
        primaryStage.show();

        environment.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Vector mouse = new Vector(mouseX, mouseY);
                mover.goTo(mouse);
                mover.updatePosition();
                mover.checkEdges();
                moverDisplay.setTranslateX(mover.getX());
                moverDisplay.setTranslateY(mover.getY());
            }

        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
