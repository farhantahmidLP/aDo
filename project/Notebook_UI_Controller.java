package project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Notebook_UI_Controller implements Initializable {

    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public Button closeButton;
    @FXML
    public Button button;
    @FXML
    public Button minButton;
    @FXML
    public Button homepageButton;
    @FXML
    public TextArea noteArea;
    @FXML
    public ColorPicker color_picker;
    @FXML
    public Slider fontSlider;
    @FXML
    public MenuButton bgButton;
    @FXML
    public Label fontSizeLabel, darkMark, lightMark;
    private String currentTextColor = "grey";
    private String currentBgColor = "#c4c4c4";
    private int currentFontSize = 24;
    private final NoteManager manager = new NoteManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lightMark.setStyle("-fx-opacity: 1");
        darkMark.setStyle("-fx-opacity: 0");
        fontSlider.setMin(14);
        fontSlider.setMax(64);
        fontSlider.setValue(16);
        fontSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            currentFontSize = (int) new_val.doubleValue();
            fontSizeLabel.setText("Font size: " + currentFontSize);
            noteArea.setStyle("-fx-text-fill:" + currentTextColor + "; -fx-font-size: " + currentFontSize + "px; -fx-font-family: verdana; -fx-control-inner-background: " + currentBgColor + ";");
        });
        noteArea.setStyle("-fx-font-size: " + currentFontSize + "px; -fx-font-family: verdana; -fx-text-fill:" + currentTextColor + "; -fx-control-inner-background: " + currentBgColor + ";");
    }
    private double x = 400.0, y = 400.0;

    @FXML
    public void makeDragable() {
        AnchorPane.setOnMousePressed((event)
                -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        AnchorPane.setOnMouseDragged((event)
                -> {
            Stage stage = (Stage) AnchorPane.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    @FXML
    public void closeScreen() //closes screen when close button is pushed 
    {
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
        Stage CurrentStage = (Stage) closeButton.getScene().getWindow();
        CurrentStage.close();
    }

    @FXML
    public void minScreen() //minimizes screen when close button is pushed 
    {
        Stage CurrentStage = (Stage) minButton.getScene().getWindow();
        CurrentStage.setIconified(true);
    }

    @FXML
    public void loadHomepage() {
        makeFadeOut(1);
    }

    @FXML
    public void loadScene(int page) {
        String pageName = "";
        if (page == 1) {
            if (AppColor.runtimeDark) {
                pageName = "Homepage_UI.fxml";
            } else {
                pageName = "homepageUILight.fxml";
            }
        }
        try {
            Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource(pageName));
            Scene scene = new Scene(root);
            Stage CurrentStage = (Stage) homepageButton.getScene().getWindow();
            if (AppColor.runtimeDark) {
                String css = project.Login_UI_Controller.class.getResource("Style.css").toExternalForm();
                scene.getStylesheets().add(css);
            } else {
                String css = project.Login_UI_Controller.class.getResource("styleLight.css").toExternalForm();
                scene.getStylesheets().add(css);
            }
            scene.setFill(Color.TRANSPARENT);
            CurrentStage.setScene(scene);
        } catch (IOException exception) {
        }
    }

    public void makeFadeOut(int page) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(1);
        transition.setToValue(0.7);
        transition.setOnFinished((event) -> {
            loadScene(page);
        });
        transition.play();
    }

    @FXML
    public void open_text() {
        noteArea.setText(manager.open());
    }

    @FXML
    public void save_text() {
        manager.save(noteArea.getText());
    }

    @FXML
    public void save_text_as() {
        manager.saveAs(noteArea.getText());
    }

    @FXML
    public void change_color() {
        String color = color_picker.getValue().toString();
        String valid_color = "#";
        for (int i = 2; i < color.length() - 2; i++) {
            valid_color += color.charAt(i);
        }
        currentTextColor = valid_color;
        noteArea.setStyle("-fx-text-fill:" + valid_color + "; -fx-font-size: " + currentFontSize + "px; -fx-font-family: verdana; -fx-control-inner-background: " + currentBgColor + ";");
    }

    @FXML
    public void changeBGtoDark() {
        currentBgColor = "#6e6e6e";
        currentTextColor = "white";
        lightMark.setStyle("-fx-opacity: 0");
        darkMark.setStyle("-fx-opacity: 1");
        noteArea.setStyle("-fx-text-fill:" + currentTextColor + "; -fx-font-size: " + currentFontSize + "px; -fx-font-family: verdana; -fx-control-inner-background: " + currentBgColor + ";");
    }

    @FXML
    public void changeBGtoWhite() {
        currentBgColor = "#c4c4c4";
        currentTextColor = "grey";
        lightMark.setStyle("-fx-opacity: 1");
        darkMark.setStyle("-fx-opacity: 0");
        noteArea.setStyle("-fx-text-fill:" + currentTextColor + "; -fx-font-size: " + currentFontSize + "px; -fx-font-family: verdana; -fx-control-inner-background: " + currentBgColor + ";");
    }
}
