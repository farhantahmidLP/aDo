package project;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.stage.*;

public class AlertBox_UI_Controller {

    private final String notHovered = "-fx-font-size: 7;"
            + "-fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-stroke-width: 20;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white;";
    private final String Hovered = "-fx-font-size: 7;"
            + "-fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-stroke-width: 20;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white; -fx-effect: innershadow(gaussian, #656566, 25, 0, 4.12, 4.12);";

    public void display(String title, String message, double x, double y, double h, double w, double lx, double ly) throws Exception {

        Label label = new Label(message);

        label.setLayoutX(lx);
        label.setLayoutY(ly);
        if(AppColor.runtimeDark)
        {
             label.setStyle("-fx-text-fill: #bfbfbf");
        }
        else
        {
            label.setStyle("-fx-text-fill: black");
        }
        
       

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Button close_button = new Button("X");
        close_button.setLayoutX(14.0);
        close_button.setLayoutY(14.0);
        close_button.setPrefHeight(19.0);
        close_button.setPrefWidth(20.0);
        close_button.setStyle(notHovered);

        close_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            close_button.setStyle(Hovered);
        });

        close_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            close_button.setStyle(notHovered);
        });
        close_button.setOnAction(e -> stage.close());
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(label, close_button);
        if(AppColor.runtimeDark)
        pane.setStyle("-fx-background-color: #42476c; -fx-background-radius: 15px;");
        else
        {
            pane.setStyle("-fx-background-color: #dbdbdb; -fx-background-radius: 15px;");
        }
        pane.setPrefHeight(h);
        pane.setPrefWidth(w);

        pane.setLayoutX(0);
        pane.setLayoutY(0);
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
    

}


