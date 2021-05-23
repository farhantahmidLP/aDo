package project;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.stage.*;

public class TargetBox {

    private final String notHovered = "-fx-font-size: 7;"
            + "-fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white;";
    private final String Hovered = "-fx-font-size: 7;"
            + "-fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white; -fx-effect: innershadow(gaussian, #656566, 25, 0, 4.12, 4.12);";

    public void display(String title, String message, double x, double y, double h, double w, double lx, double ly) throws Exception {

        Text label = new Text("\n\n\n" + message);

        label.setLayoutX(20);
        label.setLayoutY(ly);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Button close_button = new Button("X");
        close_button.setLayoutX(10.0);
        close_button.setLayoutY(10.0);
        close_button.setPrefHeight(7.0);
        close_button.setPrefWidth(7.0);
        close_button.setMaxSize(7, 7);
        close_button.setStyle(notHovered);

        close_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            close_button.setStyle(Hovered);
        });

        close_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            close_button.setStyle(notHovered);
        });
        close_button.setOnAction(e -> stage.close());
        TextFlow pane = new TextFlow();
        pane.getChildren().addAll(close_button, label);
        pane.setPrefHeight(h);
        pane.setPrefWidth(w);

        pane.setLayoutX(0);
        pane.setLayoutY(0);
        make_draggable(pane);

        if (AppColor.runtimeDark) {
            pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #4f5478); -fx-background-radius: 5px;");
            label.setFill(Color.SNOW);

        } else {
            pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 5px;");
            label.setFill(Color.BLACK);

        }
        Scene scene = new Scene(pane);

        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);

        stage.setHeight(h);
        stage.setWidth(w);
        stage.setX(x);
        stage.setY(y);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }
    private double x = 400.0, y = 400.0;

    public void make_draggable(TextFlow pane) {
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
