package project;

import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;
import javafx.geometry.*;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import java.io.*;
import java.util.Calendar;
import javafx.stage.Screen;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class FlipTimePiece {

    double time = 1;
    Scene scene;
    Pane pane;
    Pane minEffectPaneOne, minEffectPaneTwo, hEffectPaneOne, hEffectPaneTwo;
    double screenHeight, screenWidth;
    Text hourText;
    Label amPmTextOne, amPmTextTwo;
    TextFlow hourTextFlowOne, hourTextFlowTwo, hourTextFlowThree;
    Text minuteText;
    TextFlow minuteTextFlowOne, minuteTextFlowTwo, minuteTextFlowThree;
    int previousHour, previousMinute;
    int currentHour, currentMinute;
    String amPmString;
    boolean start = true;

    static String refineMinute(int minute) {
        if (minute >= 10) {
            return Integer.toString(minute);
        } else {
            return ("0" + Integer.toString(minute));
        }
    }

    FlipTimePiece(Stage stage) throws Exception, IOException {

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
            close_button.setScaleX(1.2);
            close_button.setScaleY(1.2);
            close_button.setScaleZ(1.2);
        });
        close_button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            close_button.setScaleX(1);
            close_button.setScaleY(1);
            close_button.setScaleZ(1);
        });
        close_button.setOnAction(e -> {
            stage.close();
        });
        final Font amPmFont = Font.loadFont(new FileInputStream(new File("src/project/BebasNeueBold.ttf")), 50);
        //final Font amPmFont = Font.loadFont("/project/BebasNeueBold.ttf", 50);

        pane = new Pane();
        pane.setStyle("-fx-background-color:black;");
        pane.setPrefSize(1200, 700);
        scene = new Scene(pane);
        pane.getChildren().add(close_button);
        scene.setCamera(new PerspectiveCamera());
        stage.setScene(scene);
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        screenHeight = screenSize.getHeight();//828 
        screenWidth = screenSize.getWidth();//1440
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.initStyle(StageStyle.UNDECORATED);
        PauseTransition startTime = new PauseTransition(Duration.seconds(3.5));
        startTime.setOnFinished(eh -> {
            stage.show();
        });
        startTime.play();
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
        previousHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) % 12;
        if (previousHour == 0) {
            previousHour = 12;
        }
        previousMinute = Calendar.getInstance().get(Calendar.MINUTE);
        Timeline amPmTimeline = new Timeline(new KeyFrame(Duration.seconds(time), (ActionEvent event) -> {
            if (Calendar.getInstance().get(Calendar.AM_PM) == 0) {
                amPmString = "AM";
            } else {
                amPmString = "PM";
            }
            amPmTextOne = new Label(amPmString);
            amPmTextOne.setFont(amPmFont);
            amPmTextOne.setStyle("-fx-text-fill:white");
            amPmTextOne.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5) + 20);//(screenWidth / 2) - (350) - (550.0 / 5.5)
            amPmTextOne.setLayoutY(screenHeight / 1.64);
            amPmTextTwo = new Label(amPmString);
            amPmTextTwo.setFont(amPmFont);
            amPmTextTwo.setStyle("-fx-text-fill:white");
            amPmTextTwo.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5) + 20);
            amPmTextTwo.setLayoutY(screenHeight / 1.64);
        }));
        amPmTimeline.setCycleCount(Timeline.INDEFINITE);
        amPmTimeline.play();
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(time), (ActionEvent event) -> {
            currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) % 12;
            currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
            if (currentHour == 0) {
                currentHour = 12;
            }
            if (start) {
                start = false;
                try {
                    hourFlipper(0, 90, true);
                    minuteFlipper(0, 90, true);
                    previousHour = currentHour;
                    previousMinute = currentMinute;
                } catch (IOException ex) {
                }
            } else {
                if ((previousMinute != currentMinute) && (previousHour != currentHour)) {
                    try {
                        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) % 12;
                        currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                        if (currentHour == 0) {
                            currentHour = 12;
                        }
                        minuteFlipper(0, 90, true);
                        hourFlipper(0, 90, true);
                        previousHour = currentHour;
                        previousMinute = currentMinute;
                    } catch (IOException ex) {
                    }
                } else if ((previousMinute != currentMinute)) {
                    try {
                        minuteFlipper(0, 90, true);
                        previousHour = currentHour;
                        previousMinute = currentMinute;
                    } catch (IOException ex) {
                    }
                } else if ((previousHour != currentHour)) {
                    try {
                        hourFlipper(0, 90, true);
                        previousHour = currentHour;
                        previousMinute = currentMinute;
                    } catch (IOException ex) {
                    }
                }
            }
        }));
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    public void movePivot(Pane node, double x, double y) {
        node.getTransforms().add(new Translate(-x, -y));
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    public void hourFlipper(double fromAngle, double toAngle, boolean status) throws IOException {
        // final Font f = Font.loadFont("/project/BebasNeueBold.ttf", 420);
        final Font f = Font.loadFont(new FileInputStream(new File("src/project/BebasNeueBold.ttf")), 420);
        hourTextFlowThree = new TextFlow();
        hEffectPaneOne = new Pane();
        hEffectPaneOne.setPrefSize(350, 7);
        hEffectPaneOne.setStyle("-fx-background-color:black;");
        hEffectPaneOne.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5));
        hEffectPaneOne.setLayoutY((screenHeight / 2));
        hEffectPaneTwo = new Pane();
        hEffectPaneTwo.setPrefSize(350, 7);
        hEffectPaneTwo.setStyle("-fx-background-color:black;");
        hEffectPaneTwo.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5));
        hEffectPaneTwo.setLayoutY((screenHeight / 2));
        hourText = new Text(Integer.toString(currentHour - 1));
        hourText.setFont(f);
        hourText.setFill(Color.WHITE);
        hourText.setLayoutX(550);
        hourText.setLayoutY(100);
        hourTextFlowThree.getChildren().addAll(hourText);
        hourTextFlowThree.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        hourTextFlowThree.setPrefSize(350, 350);
        hourTextFlowThree.setMinSize(350, 350);
        hourTextFlowThree.setMaxSize(350, 350);
        hourTextFlowThree.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5));
        hourTextFlowThree.setLayoutY((screenHeight / 2) - (350 / 2));
        hourTextFlowThree.setTextAlignment(TextAlignment.CENTER);
        Rectangle clipNew = new Rectangle();
        clipNew.setHeight(550 / 2);
        clipNew.setWidth(550);
        clipNew.setFill(Color.RED);
        clipNew.setLayoutY(350 / 2);
        hourTextFlowThree.setClip(clipNew);
        hourTextFlowTwo = new TextFlow();
        hourText = new Text(Integer.toString(currentHour));
        hourText.setFont(f);
        hourText.setFill(Color.WHITE);
        hourText.setLayoutX(550);
        hourText.setLayoutY(100);
        hourTextFlowTwo.getChildren().addAll(hourText);
        hourTextFlowTwo.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        hourTextFlowTwo.setPrefSize(350, 350);
        hourTextFlowTwo.setMinSize(350, 350);
        hourTextFlowTwo.setMaxSize(350, 350);
        hourTextFlowTwo.setLayoutX((screenWidth / 2) - (350) - (550.0 / 5.5));
        hourTextFlowTwo.setLayoutY((screenHeight / 2) - (350 / 2));
        hourTextFlowTwo.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().addAll(hourTextFlowTwo, hourTextFlowThree, hEffectPaneOne, amPmTextOne);
        hourTextFlowOne = new TextFlow();
        if (status && (currentHour > 1)) {
            hourText = new Text(Integer.toString(currentHour - 1));
        } else {
            hourText = new Text(Integer.toString(currentHour));
        }
        hourText.setFont(f);
        hourText.setFill(Color.WHITE);
        hourText.setLayoutX(550);
        hourText.setLayoutY(100);
        hourTextFlowOne.getChildren().addAll(hourText);
        hourTextFlowOne.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        hourTextFlowOne.setPrefSize(350, 350);
        hourTextFlowOne.setMinSize(350, 350);
        hourTextFlowOne.setMaxSize(350, 350);
        hourTextFlowOne.setTextAlignment(TextAlignment.CENTER);
        Pane clipper = new Pane();
        clipper.setRotationAxis(new Point3D(1, 0, 0));
        clipper.setRotate(fromAngle);
        clipper.setStyle("-fx-background-color: #00000000;");
        clipper.setLayoutX(screenWidth / 2 - (550));//250  
        clipper.setLayoutY((screenHeight / 2) - (550 / 2));
        clipper.setPrefSize(550, 550);
        clipper.setMaxSize(550, 550);
        clipper.getChildren().add(hourTextFlowOne);
        hourTextFlowOne.setLayoutX((550 / 2) - (350 / 2));
        Rectangle clip = new Rectangle();
        clip.setHeight(550 / 2);
        clip.setWidth(550);
        clip.setFill(Color.RED);
        if (status) {
            hourTextFlowOne.setLayoutY((550 / 2) - (350 / 2));
            clip.setLayoutY(0);
        } else {
            hourTextFlowOne.setLayoutY(100);
            clip.setLayoutY(550 / 2);
        }
        clipper.setClip(clip);
        pane.getChildren().add(clipper);
        RotateTransition rotator = new RotateTransition(Duration.seconds(time), clipper);
        rotator.setAxis(Rotate.X_AXIS);
        rotator.setFromAngle(fromAngle);
        rotator.setToAngle(toAngle);
        rotator.setAutoReverse(false);
        rotator.setInterpolator(Interpolator.LINEAR);
        movePivot(clipper, 0, 0);
        rotator.setCycleCount(1);//Animation.INDEFINITE  
        rotator.setOnFinished(eh -> {
            if (!status) {
                hourText.setText(Integer.toString(currentHour));
            }
        });
        rotator.play();
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(time), (ActionEvent event) -> {
        }));
        clockTimeline.setCycleCount(1);//Timeline.INDEFINITE  
        clockTimeline.play();
        if (status) {
            clockTimeline.setOnFinished(eh -> {
                try {
                    amPmTextOne.setVisible(false);
                    amPmTextTwo.setVisible(false);
                    hourFlipper(-90, 0, false);
                    pane.getChildren().addAll(hEffectPaneTwo, amPmTextTwo);
                } catch (IOException ex) {
                }
            });
        } else {
            clockTimeline.setOnFinished(eh -> {
                amPmTextOne.setVisible(true);
                amPmTextTwo.setVisible(true);
                pane.getChildren().addAll(amPmTextTwo);
            });
        }
    }

    public void minuteFlipper(double fromAngle, double toAngle, boolean status) throws IOException {
        //final Font f = Font.loadFont("/project/BebasNeueBold.ttf", 420);
        final Font f = Font.loadFont(new FileInputStream(new File("src/project/BebasNeueBold.ttf")), 420);
        minuteTextFlowThree = new TextFlow();
        if (currentMinute > 1) {
            minuteText = new Text(refineMinute(currentMinute - 1));
        } else {
            if (currentMinute == 0) {
                minuteText = new Text("59");
            } else {
                minuteText = new Text("00");
            }
        }
        minEffectPaneOne = new Pane();
        minEffectPaneOne.setPrefSize(350, 7);
        minEffectPaneOne.setStyle("-fx-background-color:black;");
        minEffectPaneOne.setLayoutX((screenWidth / 2) + 100);
        minEffectPaneOne.setLayoutY((screenHeight / 2));
        minEffectPaneTwo = new Pane();
        minEffectPaneTwo.setPrefSize(350, 7);
        minEffectPaneTwo.setStyle("-fx-background-color:black;");
        minEffectPaneTwo.setLayoutX((screenWidth / 2) + 100);
        minEffectPaneTwo.setLayoutY((screenHeight / 2));
        minuteText.setFont(f);
        minuteText.setFill(Color.WHITE);
        minuteText.setLayoutX(550);
        minuteText.setLayoutY(100);
        minuteTextFlowThree.getChildren().add(minuteText);
        minuteTextFlowThree.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        minuteTextFlowThree.setPrefSize(350, 350);
        minuteTextFlowThree.setMinSize(350, 350);
        minuteTextFlowThree.setMaxSize(350, 350);
        minuteTextFlowThree.setLayoutX((screenWidth / 2) + 100);
        minuteTextFlowThree.setLayoutY((screenHeight / 2) - (350 / 2));
        minuteTextFlowThree.setTextAlignment(TextAlignment.CENTER);
        Rectangle clipNew = new Rectangle();
        clipNew.setHeight(550 / 2);
        clipNew.setWidth(550);
        clipNew.setFill(Color.RED);
        clipNew.setLayoutY(350 / 2);
        minuteTextFlowThree.setClip(clipNew);
        minuteTextFlowTwo = new TextFlow();
        minuteText = new Text(refineMinute(currentMinute));
        minuteText.setFont(f);
        minuteText.setFill(Color.WHITE);
        minuteText.setLayoutX(550);
        minuteText.setLayoutY(100);
        minuteTextFlowTwo.getChildren().add(minuteText);
        minuteTextFlowTwo.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        minuteTextFlowTwo.setPrefSize(350, 350);
        minuteTextFlowTwo.setMinSize(350, 350);
        minuteTextFlowTwo.setMaxSize(350, 350);
        minuteTextFlowTwo.setLayoutX((screenWidth / 2) + 100);
        minuteTextFlowTwo.setLayoutY((screenHeight / 2) - (350 / 2));
        minuteTextFlowTwo.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().addAll(minuteTextFlowTwo, minuteTextFlowThree, minEffectPaneOne);
        minuteTextFlowOne = new TextFlow();
        if (status) {
            if (currentMinute > 1) {
                minuteText = new Text(refineMinute(currentMinute - 1));
            } else {
                if (currentMinute == 0) {
                    minuteText = new Text("59");
                } else {
                    minuteText = new Text("00");
                }
            }
        } else {
            minuteText = new Text(refineMinute(currentMinute));
        }
        minuteText.setFont(f);
        minuteText.setFill(Color.WHITE);
        minuteText.setLayoutX(550);
        minuteText.setLayoutY(100);
        minuteTextFlowOne.getChildren().add(minuteText);
        minuteTextFlowOne.setStyle("-fx-background-radius: 25px; -fx-background-color:#232323; -fx-align-items:center;");
        minuteTextFlowOne.setPrefSize(350, 350);
        minuteTextFlowOne.setMinSize(350, 350);
        minuteTextFlowOne.setMaxSize(350, 350);
        minuteTextFlowOne.setTextAlignment(TextAlignment.CENTER);
        Pane minuteClipper = new Pane();
        minuteClipper.setRotationAxis(new Point3D(1, 0, 0));
        minuteClipper.setRotate(fromAngle);
        minuteClipper.setStyle("-fx-background-color: #00000000;");
        minuteClipper.setLayoutX((screenWidth / 2));
        minuteClipper.setLayoutY((screenHeight / 2) - (550 / 2));
        minuteClipper.setPrefSize(550, 550);
        minuteClipper.setMaxSize(550, 550);
        minuteClipper.getChildren().add(minuteTextFlowOne);
        minuteTextFlowOne.setLayoutX(100);
        Rectangle clip = new Rectangle();
        clip.setHeight(550 / 2);
        clip.setWidth(550);
        clip.setFill(Color.RED);
        if (status) {
            minuteTextFlowOne.setLayoutY((550 / 2) - (350 / 2));
            clip.setLayoutY(0);
        } else {
            minuteTextFlowOne.setLayoutY(100);
            clip.setLayoutY(550 / 2);
        }
        minuteClipper.setClip(clip);
        pane.getChildren().add(minuteClipper);
        RotateTransition rotator = new RotateTransition(Duration.seconds(time), minuteClipper);
        rotator.setAxis(Rotate.X_AXIS);
        rotator.setFromAngle(fromAngle);
        rotator.setToAngle(toAngle);
        rotator.setAutoReverse(false);
        rotator.setInterpolator(Interpolator.LINEAR);
        movePivot(minuteClipper, 0, 0);
        rotator.setCycleCount(1);
        rotator.setOnFinished(eh -> {
            if (!status) {
                minuteText.setText(refineMinute(currentMinute));
            }
        });
        rotator.play();
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(time), (ActionEvent event) -> {
        }));
        clockTimeline.setCycleCount(1);
        clockTimeline.play();
        if (status) {
            clockTimeline.setOnFinished(eh -> {
                try {
                    minuteFlipper(-90, 0, false);
                    pane.getChildren().add(minEffectPaneTwo);
                } catch (IOException ex) {
                }
            });
        }
    }
}
