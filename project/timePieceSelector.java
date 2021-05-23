package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class timePieceSelector {

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
    Hyperlink FlipTimePiece, TimePiece;
    ImageView holderOne, holderTwo;
    Pane root;
    String timeLinkStyle;

    timePieceSelector(Stage stage) {

        if (AppColor.runtimeDark) {
            timeLinkStyle = "-fx-font-size:20;"
                    + "-fx-text-fill : white;"
                    + "-fx-border-color: transparent;";
        } else {
            timeLinkStyle = "-fx-font-size:20;"
                    + "-fx-text-fill : grey;"
                    + "-fx-border-color: transparent;";
        }

        // flip timepiece hyperlink
        FlipTimePiece = new Hyperlink("Flip");
        FlipTimePiece.setLayoutX((1200 / 2) - (400 / 2) - 45);
        FlipTimePiece.setLayoutY(470);
        FlipTimePiece.setStyle(timeLinkStyle);

        FlipTimePiece.setOnAction(
                e -> {
                    makeFadeOut(0);
                });

        // timepiece hyperlink
        TimePiece = new Hyperlink("Modern");
        TimePiece.setLayoutX((1200 / 2) + (400 / 2) - 15);
        TimePiece.setLayoutY(470);
        TimePiece.setStyle(timeLinkStyle);

        TimePiece.setOnAction(
                e -> {
                    makeFadeOut(1);
                });
        FileInputStream inputOne, inputTwo;

        // first ImageView
        holderOne = new ImageView();

        Rectangle clip = new Rectangle();
        clip.setHeight(250);
        clip.setWidth(400);
        clip.setArcHeight(25);
        clip.setArcWidth(25);
        //  System.out.println(System.getProperty("user.dir"));
        try {
            //Image imageOne = new Image(getClass().getResourceAsStream("./TimePieceFlip.png"));
            Image imageOne = new Image("project/TimePieceFlip.png");

            holderOne.setImage(imageOne);
            holderOne.setFitHeight(250);
            holderOne.setFitWidth(400);
            holderOne.setLayoutX((1200 / 2) - (400) - 25);
            holderOne.setLayoutY(200);
            holderOne.setClip(clip);

        } catch (Exception ex) {
            FlipTimePiece.setText(System.getProperty("user.dir"));
        }

        holderOne.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            FlipTimePiece.fire();

        });
        holderOne.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            holderOne.setScaleX(1.03);
            holderOne.setScaleY(1.03);
        });

        holderOne.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            holderOne.setScaleX(1);
            holderOne.setScaleY(1);

        });

        // second ImageView
        holderTwo = new ImageView();

        Rectangle clipTwo = new Rectangle();
        clipTwo.setHeight(250);
        clipTwo.setWidth(400);
        clipTwo.setArcHeight(25);
        clipTwo.setArcWidth(25);

        try {
            Image imageTwo = new Image("project/TimePieceModern.png");
            holderTwo.setImage(imageTwo);
            holderTwo.setFitHeight(250);
            holderTwo.setFitWidth(400);
            holderTwo.setLayoutX((1200 / 2) + 25);
            holderTwo.setLayoutY(200);
            holderTwo.setClip(clipTwo);

        } catch (Exception ex) {
            TimePiece.setText("");
        }

        holderTwo.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            TimePiece.fire();
        });

        holderTwo.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            holderTwo.setScaleX(1.03);
            holderTwo.setScaleY(1.03);
        });

        holderTwo.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            holderTwo.setScaleX(1);
            holderTwo.setScaleY(1);

        });

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
        backIcon.setLayoutX(118.5);
        backIcon.setLayoutY(49.5);
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

        // page label
        Label pageLabel = new Label("TimePiece");
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

        root.getChildren().addAll(close_button, min_button, FlipTimePiece, TimePiece, holderOne, holderTwo);
        root.getChildren().addAll(homeButtonLowerShadow, homeButtonUpperShadow, backButton, backIcon, pageLabel);
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
                    changeScene(1);
                } catch (IOException ex) {

                }
            });
            transition.play();
        });
    }
    // method: param: page nummber

    public void makeFadeOut(int page) // loads page with animation
    {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(3400));
        transition.setNode(root);
        transition.setFromValue(1);
        transition.setToValue(0.4);
        if (page == 0) //
        {
            try {
                FlipTimePiece ft = new FlipTimePiece(new Stage());
            } catch (Exception ex) {
                System.out.print(ex);

            }
        }
        transition.setOnFinished((event) -> {
            root.setOpacity(1);
            if (page == 1) {
                TimePiece t = new TimePiece(new Stage());
            }
        });
        transition.play();
    }

    public void changeScene(int page) throws IOException {
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
