package project;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TimePiece {

    Label timeLabel;
    SimpleDateFormat timeFormat;
    String currentTime;

    TimePiece(Stage stage) {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        Pane root = new Pane();
        root.setPrefSize(screenSize.getWidth(), screenSize.getHeight());
        root.setStyle("-fx-background-color: black;");
        timeFormat = new SimpleDateFormat("HH  mm  ss");
        currentTime = timeFormat.format(Calendar.getInstance().getTime());

        HBox TimeFlow = new HBox();
        Text timeText = new Text(currentTime);
        timeText.setFont(new Font(220));
        timeText.setFill(Color.WHITE);
        TimeFlow.getChildren().addAll(timeText);
        TimeFlow.setPrefWidth(screenSize.getWidth());
        TimeFlow.setPrefHeight(screenSize.getHeight());
        TimeFlow.setLayoutY(0);
        TimeFlow.setAlignment(Pos.CENTER);

        Timeline clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(.5), (ActionEvent event) -> {
                    currentTime = timeFormat.format(Calendar.getInstance().getTime());
                    timeText.setText(currentTime);

                }));

        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
        //close button
        Button close_button = new Button("X");
        close_button.setLayoutX(27.0);
        close_button.setLayoutY(27.0);
        close_button.setPrefHeight(20.0);
        close_button.setPrefWidth(20.0);
        close_button.setMaxHeight(20.0);
        close_button.setMaxWidth(20.0);
        close_button.setStyle("-fx-font-size: 15;"
                + "    -fx-background-color: linear-gradient(to bottom right, black, #4f4a4a);\n"
                + "    -fx-stroke-width: 20;\n"
                + "    -fx-background-radius: 50%;\n"
                + "    -fx-color: white;\n"
                + "    -fx-text-fill : white;");

        close_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            close_button.setScaleX(1.3);
            close_button.setScaleY(1.3);
            close_button.setScaleZ(1.3);
        });
        close_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            close_button.setScaleX(1);
            close_button.setScaleY(1);
            close_button.setScaleZ(1);
        });
        close_button.setOnAction(e -> {

            stage.close();
        });

        root.getChildren().addAll(TimeFlow, close_button);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();

        PauseTransition idle = new PauseTransition(Duration.seconds(3));
        idle.setOnFinished(e -> {
            scene.setCursor(Cursor.NONE);
            close_button.setVisible(false);
        });
        scene.addEventHandler(MouseEvent.ANY, e -> {
            close_button.setVisible(true);
            idle.playFromStart();
            scene.setCursor(Cursor.DEFAULT);
        });
    }

}
