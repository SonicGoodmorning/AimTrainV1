package com.example.testfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AimTrainer {

    // ts pmo icl
    private boolean mt; // moving targets
    private double ts;  // target size
    private double tsp; // target speed
    private int tl;     // time limit

    // UI Stuff
    // basically defines that it exists, this is like where the front end meets the backend ig
    @FXML
    private Button start;
    @FXML
    private CheckBox tmove;
    @FXML
    private Slider tspeed;
    @FXML
    private Slider tsize;
    @FXML
    private Spinner<Integer> tlimit;
    @FXML
    private ColorPicker tcolor;
    @FXML
    private ColorPicker bcolor;

    // the thingy I need for some reason that wont work if its named anything else, Ion know i didnt read the documentation
    @FXML
    public void initialize() {
        tlimit.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 30)
        );
    }


    // Start button jawn
    @FXML
    private void startg() {

        mt = tmove.isSelected();
        ts = tsize.getValue();
        tsp = tspeed.getValue();
        tl = tlimit.getValue();
        Color targetColor = tcolor.getValue();
        Color backgroundColor = bcolor.getValue();

        AimTrainerGame game = new AimTrainerGame(mt, ts, tsp, tl, targetColor, backgroundColor);
        try {
            game.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Moving Targets: " + mt);
        System.out.println("Target Size: " + ts);
        System.out.println("Target Speed: " + tsp);
        System.out.println("Time Limit: " + tl);
        System.out.println("Target Color: " + targetColor);
        System.out.println("Background Color: " + backgroundColor);


    }
}
