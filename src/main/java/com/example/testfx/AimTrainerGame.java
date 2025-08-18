package com.example.testfx;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class AimTrainerGame {
    private boolean movingTargets;
    private double targetSize;
    private double targetSpeed;
    private int timeLimit;
    private Color targetColor;
    private Color backgroundColor;
    Random random = new Random();

    int getRandomX() {
        return random.nextInt(500);
    }
    int getRandomY() {
        return random.nextInt(500);
    }

    private int score = 0;
    private int misclicks = 0;

    public AimTrainerGame(boolean mt, double ts, double tsp, int tl, Color targetColor, Color backgroundColor) {
        this.movingTargets = mt;
        this.targetSize = ts;
        this.targetSpeed = tsp;
        this.timeLimit = tl;
        this.targetColor = targetColor;
        this.backgroundColor = backgroundColor;
    }

    public void start(Stage stage) {
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));

        Circle target = new Circle(targetSize, targetColor);
        target.setCenterX(300);
        target.setCenterY(360);

        Text scoret = new Text(50, 50, "Score: " + score);
        Text misclickt = new Text(50, 80, "Misclicks: " + misclicks);
        Text timelimittext = new Text(50, 65, "Time Limit: " + timeLimit);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLimit--;
                Platform.runLater(() -> {
                    timelimittext.setText("Time Limit: " + timeLimit);
                    if (timeLimit == 0) {
                        timer.cancel();
                        Platform.exit();
                        System.exit(0);
                    }
                });
            }
        }, 0, 1000);

        stage.setOnCloseRequest(e ->{
            timer.cancel();
            Platform.exit();
            System.exit(0);
        });

        root.setOnMouseClicked(e -> {
            if (target.contains(target.sceneToLocal(e.getX(), e.getY()))) {
                score++;
                scoret.setText("Score: " + score);
                target.setCenterX(getRandomX());
                target.setCenterY(getRandomY());
            } else {
                misclicks++;
                misclickt.setText("Misclicks: " + misclicks);
            }
        });

        root.getChildren().addAll(target, scoret, misclickt, timelimittext);

        Scene gameScene = new Scene(root, 700, 700);
        stage.setScene(gameScene);
        stage.setTitle("Aim Trainer");
        stage.setResizable(false);
        stage.show();

        if (movingTargets) {
            final double[] dx = {targetSpeed};
            final double[] dy = {targetSpeed};

            AnimationTimer mover = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    double x = target.getCenterX() + dx[0];
                    double y = target.getCenterY() + dy[0];

                    if (x - targetSize < 0 || x + targetSize > 700) dx[0] *= -1;
                    if (y - targetSize < 0 || y + targetSize > 700) dy[0] *= -1;

                    target.setCenterX(target.getCenterX() + dx[0]);
                    target.setCenterY(target.getCenterY() + dy[0]);
                }
            };
            mover.start();
        }
    }
}
