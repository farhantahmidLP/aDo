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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainListUi {

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

    Pane root;
    public static Stage stage;

    MainListUi(Stage stage) {
        this.stage = stage;
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
        FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_LEFT);
        backIcon.setLayoutX(118.5);
        backIcon.setLayoutY(49.5);
        if (AppColor.runtimeDark) {
            backIcon.setFill(Color.web("#dadada"));
        } else {
            backIcon.setFill(Color.GREY);
        }

        backIcon.setSize("30");
        backIcon.setDisable(true);

        // back button effects
        Button backButtonLowerShadow = new Button();
        Button backButtonUpperShadow = new Button();

        backButtonLowerShadow.setPrefSize(39, 39);
        backButtonLowerShadow.setLayoutX(111);
        backButtonLowerShadow.setLayoutY(18);

        backButtonUpperShadow.setPrefSize(39, 39);
        backButtonUpperShadow.setLayoutX(111);
        backButtonUpperShadow.setLayoutY(18);

        if (AppColor.runtimeDark) {
            backButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
            backButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
        } else {
            backButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
            backButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
        }

        backButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            backButtonLowerShadow.setVisible(false);
            backButtonUpperShadow.setVisible(false);
        });
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            backButtonLowerShadow.setVisible(true);
            backButtonUpperShadow.setVisible(true);
            FadeTransition transition = new FadeTransition();
            transition.setDuration(Duration.millis(300));
            transition.setNode(root);
            transition.setFromValue(1);
            transition.setToValue(0.5);
            transition.setOnFinished((event) -> {
                try {
                    backendConnector.closePage();
                    changeScene(1, stage);
                } catch (IOException ex) {

                }
            });
            transition.play();
        });

        // Holder Pane
        Pane HolderPane = new Pane();
        Pane dropShadowPane = new Pane();
        HolderPane.setVisible(false);
        if (AppColor.runtimeDark) {
            HolderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, #545d7d, 24, 0, -4, -4);");
            dropShadowPane.setStyle("-fx-background-radius: 20px; -fx-background-color: #42476c; "
                    + "-fx-effect: dropshadow(gaussian, #333850, 24, 0, 7, 7);");
        } else {
            HolderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, #fcfcfc, 24, 0, -4, -4);");
            dropShadowPane.setStyle("-fx-background-radius: 20px; -fx-background-color: #dbdbdb; "
                    + "-fx-effect: dropshadow(gaussian, #bababa, 24, 0, 7, 7);");
        }

        HolderPane.setPrefSize(500, 550);
        HolderPane.setLayoutX(600);
        HolderPane.setLayoutY(100);

        dropShadowPane.setPrefSize(500, 550);
        dropShadowPane.setVisible(false);
        dropShadowPane.setLayoutX(600);
        dropShadowPane.setLayoutY(100);

        TextField nameField = new TextField();

        Label title = new Label("Create a new task");
        title.setLayoutX(90);
        title.setLayoutY(10);

        //
        Label name = new Label("Name of Task");

        name.setLayoutX(175);
        name.setLayoutY(70);

        nameField.setPrefSize(400, 50);
        nameField.setLayoutX(50);
        nameField.setLayoutY(110);
        nameField.setPromptText("Enter Task Name");
        nameField.setAlignment(Pos.CENTER);

        Label target = new Label("Target");
        target.setLayoutX(215);
        target.setLayoutY(210);

        TextField targetField = new TextField();
        targetField.setLayoutX(50);
        targetField.setLayoutY(250);
        targetField.setPrefSize(400, 50);
        targetField.setPromptText("Enter Target of Task");
        targetField.setAlignment(Pos.CENTER);

        Label urgence = new Label("Urgence");
        urgence.setLayoutX(85);
        urgence.setLayoutY(330);

        ChoiceBox choiceBoxUrgence = new ChoiceBox();
        choiceBoxUrgence.setLayoutX(70);
        choiceBoxUrgence.setLayoutY(370);
        choiceBoxUrgence.getItems().add("Urgent");
        choiceBoxUrgence.getItems().add("Moderate");
        choiceBoxUrgence.getItems().add("Less Urgent");
        choiceBoxUrgence.getSelectionModel().select(0);
        String css;
        if (AppColor.runtimeDark) {
            css = project.MainListUi.class.getResource("Style.css").toExternalForm();
        } else {
            css = project.MainListUi.class.getResource("styleLight.css").toExternalForm();
        }
        choiceBoxUrgence.getStylesheets().add(css);
        choiceBoxUrgence.setPrefHeight(42);
        choiceBoxUrgence.setPrefWidth(120);

        Button urgenceBoxLowerShadow = new Button();
        Button urgenceBoxUpperShadow = new Button();

        urgenceBoxLowerShadow.setPrefSize(117, 39);
        urgenceBoxLowerShadow.setLayoutX(71);
        urgenceBoxLowerShadow.setLayoutY(371);

        urgenceBoxUpperShadow.setPrefSize(117, 39);
        urgenceBoxUpperShadow.setLayoutX(71);
        urgenceBoxUpperShadow.setLayoutY(371);

        Label significance = new Label("Significance");
        significance.setLayoutX(305);
        significance.setLayoutY(330);

        ChoiceBox choiceBoxSignificance = new ChoiceBox();
        choiceBoxSignificance.setLayoutX(310);
        choiceBoxSignificance.setLayoutY(370);
        choiceBoxSignificance.getStylesheets().add(css);
        choiceBoxSignificance.getItems().add("Important");
        choiceBoxSignificance.getItems().add("Medium");
        choiceBoxSignificance.getItems().add("Less Important");
        choiceBoxSignificance.getSelectionModel().select(0);
        choiceBoxSignificance.setPrefHeight(42);
        choiceBoxSignificance.setPrefWidth(130);

        Button significanceBoxLowerShadow = new Button();
        Button significanceBoxUpperShadow = new Button();

        significanceBoxLowerShadow.setPrefSize(117, 39);
        significanceBoxLowerShadow.setLayoutX(313);
        significanceBoxLowerShadow.setLayoutY(371);

        significanceBoxUpperShadow.setPrefSize(117, 39);
        significanceBoxUpperShadow.setLayoutX(313);
        significanceBoxUpperShadow.setLayoutY(371);

        Button createButton = new Button("Create");
        createButton.setPrefSize(84, 42);
        createButton.setLayoutX(208);
        createButton.setLayoutY(450);

        Button createButtonLowerShadow = new Button();
        Button createButtonUpperShadow = new Button();

        createButtonLowerShadow.setPrefSize(81, 39);
        createButtonLowerShadow.setLayoutX(209);
        createButtonLowerShadow.setLayoutY(451);

        createButtonUpperShadow.setPrefSize(81, 39);
        createButtonUpperShadow.setLayoutX(209);
        createButtonUpperShadow.setLayoutY(451);

        createButton.setOnAction(e
                -> {
            if (!nameField.getText().equals("")) {
                String content = backendConnector.encodeContent(nameField.getText(), getUrgency(choiceBoxUrgence), getSignificance(choiceBoxSignificance), " ", "Target : " + targetField.getText());
                backendConnector.saveElementsLocally(content);
            }
        }
        );

        HolderPane.getChildren().addAll(name, title, target, urgence, significance, nameField, targetField);
        HolderPane.getChildren().addAll(urgenceBoxUpperShadow, urgenceBoxLowerShadow, choiceBoxUrgence, significanceBoxLowerShadow, significanceBoxUpperShadow, choiceBoxSignificance, createButtonUpperShadow, createButtonLowerShadow, createButton);

        //plus button
        Button plusButton = new Button("");
        plusButton.setLayoutX(1058.0);
        plusButton.setLayoutY(17.0);
        plusButton.setPrefHeight(42.0);
        plusButton.setPrefWidth(42.0);

        // Home Icon
        FontAwesomeIconView plusIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        plusIcon.setLayoutX(1066.5);
        plusIcon.setLayoutY(49.5);
        plusIcon.setSize("30");
        plusIcon.setDisable(true);

        // back button effects
        Button plusButtonLowerShadow = new Button();
        Button plusButtonUpperShadow = new Button();

        plusButtonLowerShadow.setPrefSize(39, 39);
        plusButtonLowerShadow.setLayoutX(1059);
        plusButtonLowerShadow.setLayoutY(18);

        plusButtonUpperShadow.setPrefSize(39, 39);
        plusButtonUpperShadow.setLayoutX(1059);
        plusButtonUpperShadow.setLayoutY(18);

        plusButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            plusButtonLowerShadow.setVisible(false);
            plusButtonUpperShadow.setVisible(false);
        });
        plusButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
            plusButtonLowerShadow.setVisible(true);
            plusButtonUpperShadow.setVisible(true);
            HolderPane.setVisible(!HolderPane.isVisible());
            dropShadowPane.setVisible(!dropShadowPane.isVisible());

        });

        backendConnector backend = new backendConnector();
        backend.getSavedElements();

        ListView scrollablePaneList = new ListView(backendConnector.elementPanes);
        scrollablePaneList.setLayoutX(100);
        scrollablePaneList.setLayoutY(100);
        scrollablePaneList.setId("taskPane");
        scrollablePaneList.setPrefSize(450, 550);
        scrollablePaneList.setMaxSize(450, 550);
        String schemeCss;
        if (AppColor.runtimeDark) {
            schemeCss = project.MainListUi.class.getResource("taskScheme.css").toExternalForm();
        } else {
            schemeCss = project.MainListUi.class.getResource("taskSchemeLight.css").toExternalForm();
        }
        scrollablePaneList.getStylesheets().add(schemeCss);
        scrollablePaneList.setStyle(" -fx-background-radius:20px;");

        // page label
        Label pageLabel = new Label("Task Scheme");
        pageLabel.setLayoutX(180);
        pageLabel.setLayoutY(14);

        root = new Pane();
        root.setPrefSize(1200, 700);

        if (AppColor.runtimeDark) {
            title.setStyle("-fx-text-fill: white; -fx-font-size: 40px; -fx-font-family:\"Trebuchet MS\" ");
            name.setStyle("-fx-text-fill: snow; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            nameField.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #434a70, #515985);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill:#ffffff;");
            targetField.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #434a70, #515985);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill:#ffffff;");
            target.setStyle("-fx-text-fill: snow; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            urgence.setStyle("-fx-text-fill: snow; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            //
            choiceBoxUrgence.setStyle("-fx-text-fill:snow; -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
            urgenceBoxLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 24, 0, 7, 7); -fx-background-radius: 15;");
            urgenceBoxUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 24, 0, -4, -4); -fx-background-radius: 15;");
            //
            significance.setStyle("-fx-text-fill: snow; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            choiceBoxSignificance.setStyle("-fx-color:snow; -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");

            significanceBoxLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 24, 0, 7, 7); -fx-background-radius: 15;");
            significanceBoxUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 24, 0, -4, -4); -fx-background-radius: 15;");
            //
            createButton.setStyle("-fx-text-fill:snow; -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
            createButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 24, 0, 7, 7); -fx-background-radius: 15;");
            createButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 24, 0, -4, -4); -fx-background-radius: 15;");
            //
            plusButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
            plusIcon.setFill(Color.web("#dadada"));
            plusButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
            plusButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
            //
            pageLabel.setStyle("-fx-text-fill: snow; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
            //
            root.setStyle("-fx-background-radius: 15px; -fx-background-color: #42476c; -fx-border-color: #404359; -fx-border-width: 2px; -fx-border-radius: 15px;");
        } else //
        //
        //
        //
        {

            title.setStyle("-fx-text-fill: grey; -fx-font-size: 40px; -fx-font-family:\"Trebuchet MS\" ");
            name.setStyle("-fx-text-fill: grey; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            nameField.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #d9d9d9, #e0e0e0);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill: #000000;");
            targetField.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #d9d9d9, #e0e0e0);\n"
                    + "    -fx-background-radius: 18;\n"
                    + "    -fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);\n"
                    + "    -fx-text-fill:#000000;");
            target.setStyle("-fx-text-fill: grey; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            urgence.setStyle("-fx-text-fill: grey; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            //
            choiceBoxUrgence.setStyle("-fx-text-fill:grey; -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
            urgenceBoxLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 24, 0, 7, 7); -fx-background-radius: 15;");
            urgenceBoxUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 24, 0, -4, -4); -fx-background-radius: 15;");
            // 
            significance.setStyle("-fx-text-fill: grey; -fx-font-size: 24px; -fx-font-family:\"Trebuchet MS\" ");
            choiceBoxSignificance.setStyle("-fx-color:grey; -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
            significanceBoxLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 24, 0, 7, 7); -fx-background-radius: 15;");
            significanceBoxUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 24, 0, -4, -4); -fx-background-radius: 15;");
            //
            createButton.setStyle("-fx-text-fill:grey; -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
            createButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 24, 0, 7, 7); -fx-background-radius: 15;");
            createButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 24, 0, -4, -4); -fx-background-radius: 15;");
            //
            plusButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
            plusIcon.setFill(Color.GREY);
            plusButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
            plusButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
            //
            pageLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
            //
            root.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb; -fx-border-color: #a6a6a6; -fx-border-width: 2px; -fx-border-radius: 15px;");

        }
        root.getChildren().addAll(close_button, min_button);
        root.getChildren().addAll(backButtonLowerShadow, backButtonUpperShadow, backButton, backIcon);
        root.getChildren().addAll(plusButtonLowerShadow, plusButtonUpperShadow, plusButton, plusIcon, dropShadowPane, HolderPane, pageLabel);

        root.getChildren().addAll(scrollablePaneList);
        make_draggable(root);

        Scene scene = new Scene(root);

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(int page, Stage currentStage) throws IOException {
        Parent root;
        if (AppColor.runtimeDark) {
            root = FXMLLoader.load(getClass().getResource("Homepage_UI.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("homepageUILight.fxml"));
        }
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        String css = project.Main.class.getResource("Style.css").toExternalForm();
        scene.getStylesheets().add(css);
        currentStage.setScene(scene);
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

    public static String encodeContent(String name, String urgency, String significance, String deadline, String target) {
        return name + "`" + urgency + "`" + significance + "`" + deadline + "`" + target;
    }

    public static String[] decodeContent(String content) {
        String[] array;
        array = content.split("`");
        return array;
    }

    public static String getUrgency(ChoiceBox c) {
        switch (c.getSelectionModel().getSelectedIndex()) {
            case 0 -> {
                return "Urgent";
            }
            case 1 -> {
                return "Moderate";
            }
            case 2 -> {
                return "Less Urgent";
            }
            default -> {
            }
        }
        return "";
    }

    public static String getSignificance(ChoiceBox c) {
        switch (c.getSelectionModel().getSelectedIndex()) {
            case 0 -> {
                return "Important";
            }
            case 1 -> {
                return "Medium";
            }
            case 2 -> {
                return "Less Important";
            }
            default -> {
            }
        }
        return "";
    }

}
