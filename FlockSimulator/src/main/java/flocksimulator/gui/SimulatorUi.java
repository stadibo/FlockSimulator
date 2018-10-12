package flocksimulator.gui;

import flocksimulator.domain.AgentGenerator;
import flocksimulator.domain.Generator;
import flocksimulator.domain.SpatialAgentGenerator;
import flocksimulator.util.MathWrapper;
import flocksimulator.domain.Vector;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class for graphical user interface through which to interact with simulator
 *
 * @author peje
 */
public class SimulatorUi extends Application {

    private int WIDTH = 1280;
    private int HEIGHT = 720;
    private double mouseX;
    private double mouseY;
    private Vector mouse;
    private Generator agentGenerator;
    private Pane root;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private Label frameRate = new Label();
    private Label agentAmount = new Label();

    private Slider alignment = new Slider(0, 3, 1.0);
    private Slider cohesion = new Slider(0, 3, 1.0);
    private Slider separation = new Slider(0, 3, 1.5);
    private Slider maxSpeed = new Slider(0, 25, 4.0);
    private Slider maxForce = new Slider(0, 1, 0.2);
    private Label alignmentValue = new Label();
    private Label cohesionValue = new Label();
    private Label separationValue = new Label();
    private Label maxSpeedValue = new Label();
    private Label maxForceValue = new Label();

    /**
     * Setup simulator scene
     *
     * @return
     */
    private Parent setup() {
        root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);
        root.getChildren().addAll(frameRate, agentAmount, alignment, alignmentValue,
                cohesion, cohesionValue, separation, separationValue, maxSpeed,
                maxSpeedValue, maxForce, maxForceValue);

        frameRate.setTranslateY(0);
        agentAmount.setTranslateY(20);
        setupSliders();

        agentGenerator = new SpatialAgentGenerator(
                12.0,
                100.0,
                maxSpeed.getValue(),
                maxForce.getValue(),
                WIDTH,
                HEIGHT,
                false,
                60
        );
        
//        agentGenerator = new AgentGenerator(
//                12.0,
//                100.0,
//                maxSpeed.getValue(),
//                maxForce.getValue(),
//                WIDTH,
//                HEIGHT,
//                false
//        );

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
                    frameRate.setText(String.format("Current frame rate: %.3f", framesPerSecond));
                }
            }
        };
        timer.start();

        return root;
    }

    /**
     * Create sliders for modifiable parameters
     */
    private void setupSliders() {
        alignment.setTranslateY(40);
        alignment.setBlockIncrement(0.1d);
        alignment.setMajorTickUnit(0.1d);
        alignment.setMinorTickCount(0);
        alignment.setSnapToTicks(true);
        alignmentValue.setText("Alignment " + Double.toString(alignment.getValue()));
        alignmentValue.setTranslateX(140);
        alignmentValue.setTranslateY(40);

        cohesion.setTranslateY(60);
        cohesion.setBlockIncrement(0.1d);
        cohesion.setMajorTickUnit(0.1d);
        cohesion.setMinorTickCount(0);
        cohesion.setSnapToTicks(true);
        cohesionValue.setText("Cohesion " + Double.toString(cohesion.getValue()));
        cohesionValue.setTranslateX(140);
        cohesionValue.setTranslateY(60);

        separation.setTranslateY(80);
        separation.setBlockIncrement(0.1d);
        separation.setMajorTickUnit(0.1d);
        separation.setMinorTickCount(0);
        separation.setSnapToTicks(true);
        separationValue.setText("Separation " + Double.toString(separation.getValue()));
        separationValue.setTranslateX(140);
        separationValue.setTranslateY(80);

        maxSpeed.setTranslateY(100);
        maxSpeed.setBlockIncrement(1d);
        maxSpeed.setMajorTickUnit(1d);
        maxSpeed.setMinorTickCount(0);
        maxSpeed.setSnapToTicks(true);
        maxSpeedValue.setText("Max speed " + Double.toString(maxSpeed.getValue()));
        maxSpeedValue.setTranslateX(140);
        maxSpeedValue.setTranslateY(100);

        maxForce.setTranslateY(120);
        maxForce.setBlockIncrement(0.1d);
        maxForce.setMajorTickUnit(0.1d);
        maxForce.setMinorTickCount(0);
        maxForce.setSnapToTicks(true);
        maxForceValue.setText("Max force " + Double.toString(maxForce.getValue()));
        maxForceValue.setTranslateX(140);
        maxForceValue.setTranslateY(120);

        // Create listeners for sliders so that parameters of agents can be changed
        alignment.valueProperty().addListener((observable, oldvalue, newValue) -> {
            String value = String.format("%.1f", newValue.doubleValue());
            alignmentValue.setText("Alignment " + value);
            agentGenerator.setAlignment(newValue.doubleValue());
        });

        cohesion.valueProperty().addListener((observable, oldvalue, newValue) -> {
            String value = String.format("%.1f", newValue.doubleValue());
            cohesionValue.setText("Cohesion " + value);
            agentGenerator.setCohesion(newValue.doubleValue());
        });

        separation.valueProperty().addListener((observable, oldvalue, newValue) -> {
            String value = String.format("%.1f", newValue.doubleValue());
            separationValue.setText("Separation " + value);
            agentGenerator.setSeparation(newValue.doubleValue());
        });

        maxSpeed.valueProperty().addListener((observable, oldvalue, newValue) -> {
            String value = String.format("%.1f", newValue.doubleValue());
            maxSpeedValue.setText("Max speed " + value);
            agentGenerator.setMaxSpeed(newValue.doubleValue());
        });

        maxForce.valueProperty().addListener((observable, oldvalue, newValue) -> {
            String value = String.format("%.1f", newValue.doubleValue());
            maxForceValue.setText("Max force " + value);
            agentGenerator.setMaxForce(newValue.doubleValue());
        });
    }

    /**
     * Update scene, new positions of all agents
     */
    private void update() {
        mouse= new Vector(mouseX, mouseY);
        agentGenerator.updateAgents(mouse);
    }

    /**
     * Create new Node and add it to the root of the scene
     */
    private void createNode() {
        double x = MathWrapper.ceil(mouseX);
        double y = MathWrapper.ceil(mouseY);
        if (0 <= x && x <= WIDTH && 0 <= y && y <= HEIGHT) {
            if (300 < x || 200 < y) {
                root.getChildren().add(agentGenerator.createAgent(x, y));
                agentAmount.setText("Amount of agents: " + agentGenerator.getAgentsSize());
            }
        }
    }

    /**
     * Create specified amount of agents at random positions. For init of scene
     *
     * @param amount
     */
    private void createNodesAtRandom(int amount) {
        for (int i = 0; i < amount; i++) {
            double x = MathWrapper.ceil(MathWrapper.random() * WIDTH);
            double y = MathWrapper.ceil(MathWrapper.random() * HEIGHT);

            root.getChildren().add(agentGenerator.createAgent(x, y));
            agentAmount.setText("Amount of agents: " + agentGenerator.getAgentsSize());
        }
    }
    
//    private void updateWindowWidth(int newWidth) {
//        this.WIDTH = newWidth;
//        this.agentGenerator.setWidth(newWidth);
//    }
//    
//    private void updateWindowHeight(int newHeight) {
//        this.HEIGHT = newHeight;
//        this.agentGenerator.setHeight(newHeight);
//    }

    /**
     * Method for starting simulator
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting simulation...");

        Scene scene = new Scene(setup());
        primaryStage.setScene(scene);
        primaryStage.getScene().setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });
        primaryStage.getScene().setOnMouseDragged(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            createNode();
        });
//        scene.widthProperty().addListener((observable, oldWidth, newWidth) -> {
//            int value = (int) MathWrapper.round((double) newWidth);
//            updateWindowWidth(value);
//        });
//        scene.heightProperty().addListener((observable, oldHeight, newHeight) -> {
//            int value = (int) MathWrapper.round((double) newHeight);
//            updateWindowHeight(value);
//            
//        });

        primaryStage.show();
    }

}
