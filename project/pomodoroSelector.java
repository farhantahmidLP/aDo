/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class pomodoroSelector {

    private final static String CLOSE_NOT_HOVERED = "-fx-font-size: 7;"
            + "    -fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-stroke-width: 20;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white;";
    private final static String CLOSE_HOVERED = "-fx-font-size: 7;"
            + "    -fx-background-color: linear-gradient(to bottom right, #bf211d, #ff6561);\n"
            + "    -fx-stroke-width: 20;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white; -fx-effect: innershadow(gaussian, #656566, 25, 0, 4.12, 4.12);";

    private final static String MIN_NOT_HOVERED = "-fx-font-size: 7;"
            + " -fx-background-color: linear-gradient(to bottom right, #c28619, #ffc863);\n"
            + "    -fx-stroke-width: 50;\n"
            + "    -fx-text-fill : white;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;";
    private final static String MIN_HOVERED = "-fx-font-size: 7;"
            + "    -fx-background-color: linear-gradient(to bottom right, #c28619, #ffc863);\n"
            + "    -fx-stroke-width: 20;\n"
            + "    -fx-background-radius: 50%;\n"
            + "    -fx-color: white;\n"
            + "    -fx-text-fill : white; -fx-effect: innershadow(gaussian, #656566, 25, 0, 4.12, 4.12);";

    public Stage currentStage;
    Pane root;

    pomodoroSelector(Stage stage) {
        //close button
        Button close_button = new Button("X");
        close_button.setLayoutX(26.0);
        close_button.setLayoutY(27.0);
        close_button.setPrefHeight(19.0);
        close_button.setPrefWidth(20.0);
        close_button.setStyle(CLOSE_NOT_HOVERED);

        close_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            close_button.setStyle(CLOSE_HOVERED);
        });
        close_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            close_button.setStyle(CLOSE_NOT_HOVERED);
        });
        close_button.setOnAction(e -> {
            System.out.println("Shutting down all processes...");
            if (AudioPlayer.aduioPlayerExists) {
                {
                    System.out.println("Closing audioplayer...");
                    AudioPlayer.currentEngine.load("");
                    AudioPlayer.stage.close();
                }
            }
            if (Pomodoro.exists) {
                {
                    System.out.println("Closing Pomodoro...");
                    Pomodoro.stage.close();
                }
            }
            stage.close();
        });

        // minimize button
        Button min_button = new Button("—");
        min_button.setLayoutX(60.0);
        min_button.setLayoutY(27.0);
        min_button.setPrefHeight(19.0);
        min_button.setPrefWidth(20.0);
        min_button.setStyle(MIN_NOT_HOVERED);
        // action events
        min_button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            min_button.setStyle(MIN_HOVERED);
        });

        min_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            min_button.setStyle(MIN_NOT_HOVERED);
        });
        min_button.setOnAction(e -> stage.setIconified(true));

        // Home button
        Button backButton = new Button("");
        backButton.setLayoutX(110.0);
        backButton.setLayoutY(17.0);
        backButton.setPrefHeight(42.0);
        backButton.setPrefWidth(42.0);

        if (AppColor.runtimeDark) {
            backButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
        } else {
            backButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
        }

        // Home Icon
        FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.HOME);
        backIcon.setLayoutX(116.5);
        backIcon.setLayoutY(47.5);

        if (AppColor.runtimeDark) {
            backIcon.setFill(Color.web("#dadada"));
        } else {
            backIcon.setFill(Color.GREY);
        }
        backIcon.setSize("30");
        backIcon.setDisable(true);

        // home button effects
        Button homeButtonLowerShadow = new Button();
        Button homeButtonUpperShadow = new Button();

        homeButtonLowerShadow.setPrefSize(39, 39);
        homeButtonLowerShadow.setLayoutX(111);
        homeButtonLowerShadow.setLayoutY(18);

        homeButtonUpperShadow.setPrefSize(39, 39);
        homeButtonUpperShadow.setLayoutX(111);
        homeButtonUpperShadow.setLayoutY(18);

        if (AppColor.runtimeDark) {
            homeButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
            homeButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
        } else {
            homeButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
            homeButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
        }

        // start button
        Button startButton = new Button("");
        startButton.setLayoutX(1049.0);
        startButton.setLayoutY(549.0);
        startButton.setPrefHeight(42.0);
        startButton.setPrefWidth(42.0);

        if (AppColor.runtimeDark) {
            startButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
        } else {
            startButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
        }

        // start Icon
        FontAwesomeIconView startIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_RIGHT);
        startIcon.setLayoutX(1057.5);
        startIcon.setLayoutY(581.5);

        if (AppColor.runtimeDark) {
            startIcon.setFill(Color.web("#dadada"));
        } else {
            startIcon.setFill(Color.GREY);
        }
        startIcon.setSize("30");
        startIcon.setDisable(true);

        // start button effects
        Button startButtonLowerShadow = new Button();
        Button startButtonUpperShadow = new Button();

        startButtonLowerShadow.setPrefSize(34, 34);
        startButtonLowerShadow.setLayoutX(1053);
        startButtonLowerShadow.setLayoutY(553);

        startButtonUpperShadow.setPrefSize(34, 34);
        startButtonUpperShadow.setLayoutX(1053);
        startButtonUpperShadow.setLayoutY(553);

        if (AppColor.runtimeDark) {
            startButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
            startButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
        } else {
            startButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
            startButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
        }

        Pane dropShadowPane = new Pane();
        Pane holder = new Pane();
        dropShadowPane.setPrefSize(400, 300);
        dropShadowPane.setLayoutX(1200 / 2 - (400 / 2));
        dropShadowPane.setLayoutY((700 / 2) - (300 / 2));
        if (AppColor.runtimeDark) {
            dropShadowPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #42476c; "
                    + "-fx-effect: dropshadow(gaussian, #333850, 24, 0, 7, 7); -fx-background-radius: 15;");

            holder.setStyle("-fx-background-radius: 15px; -fx-background-color: linear-gradient(to bottom right, #3b4061, #474c74); "
                    + "-fx-effect: dropshadow(gaussian, #545d7d, 24, 0, -4, -4); -fx-background-radius: 15;");
        } else {
            dropShadowPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb; "
                    + "-fx-effect: dropshadow(gaussian, #bababa, 24, 0, 7, 7); -fx-background-radius: 15;");

            holder.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb; "
                    + "-fx-effect: dropshadow(gaussian, #fcfcfc, 24, 0, -4, -4); -fx-background-radius: 15;");
        }

        TextField workTime = new TextField();
        TextField breakTime = new TextField();
        Label workLabel = new Label("Work Time");
        Label breakLabel = new Label("Break Time");
        workTime.setPromptText("Enter Work Time (Minutes)");
        workTime.setLayoutX(100);
        workTime.setLayoutY(60);
        workTime.setPrefSize(200, 50);
        workTime.setAlignment(Pos.CENTER);
        breakTime.setPromptText("Enter Break Time (Minutes)");
        breakTime.setAlignment(Pos.CENTER);
        breakTime.setLayoutX(100);
        breakTime.setLayoutY(170);
        breakTime.setPrefSize(200, 50);
        if (AppColor.runtimeDark) {
            workTime.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #434a70, #515985);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill:#ffffff;");
            breakTime.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #434a70, #515985);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill:#ffffff;");

            workLabel.setStyle("-fx-font-size: 18; -fx-text-fill: snow");
            breakLabel.setStyle("-fx-font-size: 18; -fx-text-fill: snow;");
        } else {
            workTime.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #d9d9d9, #e0e0e0);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill: #6b6b6b;");
            breakTime.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #d9d9d9, #e0e0e0);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill: #6b6b6b;");

            workLabel.setStyle("-fx-font-size: 18; -fx-text-fill: grey");
            breakLabel.setStyle("-fx-font-size: 18; -fx-text-fill: grey;");
        }

        workLabel.setLayoutX(153);
        workLabel.setLayoutY(25);

        breakLabel.setLayoutX(153);
        breakLabel.setLayoutY(135);
        holder.getChildren().addAll(workTime, workLabel, breakTime, breakLabel);
        holder.setPrefSize(400, 300);
        holder.setLayoutX(1200 / 2 - (400 / 2));
        holder.setLayoutY((700 / 2) - (300 / 2));

        // page label
        Label pageLabel = new Label("Pomodoro");
        pageLabel.setLayoutX(180);
        pageLabel.setLayoutY(14);
        if (AppColor.runtimeDark) {
            pageLabel.setStyle("-fx-text-fill: snow; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
        } else {
            pageLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
        }

        root = new Pane();
        root.setPrefSize(1200, 700);
        if (AppColor.runtimeDark) {
            root.setStyle("-fx-background-radius: 15px; -fx-background-color: #42476c; -fx-border-color: #404359; -fx-border-width: 2px; -fx-border-radius: 15px;");
        } else {
            root.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb; -fx-border-color: #a6a6a6; -fx-border-width: 2px; -fx-border-radius: 15px;");
        }

        root.getChildren().addAll(close_button, min_button);
        root.getChildren().addAll(homeButtonLowerShadow, homeButtonUpperShadow, backButton, backIcon);
        root.getChildren().addAll(startButtonLowerShadow, startButtonUpperShadow, startButton, startIcon);
        root.getChildren().addAll(dropShadowPane, holder, pageLabel);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        this.currentStage = stage;
        make_draggable(root);

        backButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            homeButtonLowerShadow.setVisible(false);
            homeButtonUpperShadow.setVisible(false);
        });
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            homeButtonLowerShadow.setVisible(true);
            homeButtonUpperShadow.setVisible(true);
            FadeTransition transition = new FadeTransition();
            transition.setDuration(Duration.millis(300));
            transition.setNode(root);
            transition.setFromValue(1);
            transition.setToValue(0.5);
            transition.setOnFinished((event) -> {
                try {
                    changeScene(1, "", "");
                } catch (IOException ex) {
                }
            });
            transition.play();
        });

        startButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            startButtonLowerShadow.setVisible(false);
            startButtonUpperShadow.setVisible(false);
        });
        startButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            startButtonLowerShadow.setVisible(true);
            startButtonUpperShadow.setVisible(true);
            FadeTransition transition = new FadeTransition();
            transition.setDuration(Duration.millis(300));
            transition.setNode(root);
            transition.setFromValue(1);
            transition.setToValue(1);
            transition.setOnFinished((event) -> {
                try {
                    if (stringChecker(workTime.getText()) && stringChecker(breakTime.getText())) {
                        if (Pomodoro.exists) {
                            if (Pomodoro.stage.isIconified()) {
                                Pomodoro.stage.setIconified(true);
                            }
                            Pomodoro.stage.requestFocus();
                        } else {
                            changeScene(2, workTime.getText(), breakTime.getText());
                        }
                    }
                } catch (IOException ex) {
                }
            });
            transition.play();
        });
    }
    // method: param: page nummber

    public void changeScene(int page, String work, String breakTime) throws IOException {
        if (page == 1) {
            Parent root; 
            if (AppColor.runtimeDark) {
               root = FXMLLoader.load(getClass().getResource("Homepage_UI.fxml"));
            } else {
               root = FXMLLoader.load(getClass().getResource("homepageUILight.fxml"));
            }
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            String css;
            if (AppColor.runtimeDark) {
                css = project.Main.class.getResource("Style.css").toExternalForm();
            } else {
                css = project.Main.class.getResource("styleLight.css").toExternalForm();
            }
            scene.getStylesheets().add(css);
            this.currentStage.setScene(scene);
        } else if (page == 2) {

            Pomodoro p = new Pomodoro(new Stage(), work, breakTime);
        }
    }

    private double x = 400.0, y = 400.0;

    public static boolean stringChecker(String time) {
        if (time.length() == 0) {
            return false;
        }
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) < '0' || time.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

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
