package project;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Pomodoro {

    public static Stage stage;
    public static boolean exists = false;
    Label currentTime, currentLabel;
    String workTime, breakTime;
    int MinutesLeft, SecondsLeft = 0;
    boolean isBreak = false;

    Pomodoro(Stage stage, String workTime, String breakTime) {
        exists = true;
        currentLabel = new Label("Work Time");

        this.stage = stage;
        MinutesLeft = Integer.parseInt(workTime);
        SecondsLeft = 0;
        currentTime = new Label(MinutesLeft + " " + SecondsLeft);
        if (AppColor.runtimeDark) {
            currentTime.setStyle("-fx-font-size: 25; -fx-text-fill: snow");
            currentLabel.setStyle("-fx-font-size: 30; -fx-text-fill: snow");
        } else {
            currentTime.setStyle("-fx-font-size: 25; -fx-text-fill: grey");
            currentLabel.setStyle("-fx-font-size: 30; -fx-text-fill: grey");
        }

        currentTime.setLayoutX(123);
        currentTime.setLayoutY(80);

        currentLabel.setLayoutX(75);
        currentLabel.setLayoutY(20);

        Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            SecondsLeft--;

            if (SecondsLeft < 0) //
            {
                SecondsLeft = 59;
                MinutesLeft--;
                if (MinutesLeft < 0) //
                {
                    // java.awt.Toolkit.getDefaultToolkit().beep();
                    isBreak = !isBreak;
                    if (isBreak)//
                    {
                        currentLabel.setText("Break Time");
                        MinutesLeft = Integer.parseInt(breakTime);
                        SecondsLeft = 0;
                    } else //
                    {
                        currentLabel.setText("Work Time");
                        MinutesLeft = Integer.parseInt(workTime);
                        SecondsLeft = 0;
                    }
                }
            } //
            else {
            }
            currentTime.setText(MinutesLeft + ":" + SecondsLeft);

        }));
        countDown.setCycleCount(Timeline.INDEFINITE);
        countDown.play();

        // Home button
        Button stopButton = new Button("Stop");
        stopButton.setLayoutX(129.0);
        stopButton.setLayoutY(134.0);
        stopButton.setPrefHeight(42.0);
        stopButton.setPrefWidth(42.0);
        if (AppColor.runtimeDark) {
            stopButton.setStyle(" -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-text-fill: snow; -fx-background-radius: 15;");

        } else {
            stopButton.setStyle(" -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-text-fill: grey; -fx-background-radius: 15;");

        }

        // home button effects
        Button stopButtonLowerShadow = new Button();
        Button stopButtonUpperShadow = new Button();

        stopButtonLowerShadow.setPrefSize(39, 39);
        stopButtonLowerShadow.setLayoutX(130);
        stopButtonLowerShadow.setLayoutY(135);

        stopButtonUpperShadow.setPrefSize(39, 39);
        stopButtonUpperShadow.setLayoutX(130);
        stopButtonUpperShadow.setLayoutY(135);

        if (AppColor.runtimeDark) {
            stopButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
            stopButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");

        } else {
            stopButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
            stopButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");

        }

        Pane root = new Pane();
        root.getChildren().addAll(currentLabel, currentTime);
        root.getChildren().addAll(stopButtonLowerShadow, stopButtonUpperShadow, stopButton);
        root.setPrefSize(300, 200);

        if (AppColor.runtimeDark) {
            root.setStyle("-fx-background-radius: 12px; "
                    + " -fx-background-color: linear-gradient(to bottom right, #323752, #474c74);");

        } else {
            root.setStyle("-fx-background-radius: 12px; "
                    + " -fx-background-color: linear-gradient(to bottom right, #bababa, #fcfcfc);");
        }

        make_draggable(root);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("project/icon.PNG"));
        stage.setTitle("aDo");
        stage.setX(800);
        stage.setY(400);
        stage.show();

        stopButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            stopButtonLowerShadow.setVisible(false);
            stopButtonUpperShadow.setVisible(false);
        });
        stopButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            stopButtonLowerShadow.setVisible(true);
            stopButtonUpperShadow.setVisible(true);
            FadeTransition transition = new FadeTransition();
            transition.setDuration(Duration.millis(300));
            transition.setNode(root);
            transition.setFromValue(1);
            transition.setToValue(1);
            transition.setOnFinished((event) -> {
                exists = false;
                stage.close();

            });
            transition.play();
        });
    }

    private double x = 400.0, y = 400.0;

    public void make_draggable(Pane pane) {
        pane.setOnMousePressed((event)
                -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        pane.setOnMouseDragged((event)
                -> {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

}
