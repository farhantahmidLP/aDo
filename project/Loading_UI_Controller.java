package project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class Loading_UI_Controller implements Initializable {

    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public ImageView imageView, upperView;
    // storing elements form fxml

    public Stage stage;

    @Override
    // initializing stage
    public void initialize(URL url, ResourceBundle rb) {
        Image bg = new Image(getClass().getResourceAsStream("ADO.png"));
        imageView.setImage(bg);
        upperView.setImage(bg);
        DropShadow effect, upperEffect;

        // shadow effect on icon Image
        effect = new DropShadow(BlurType.GAUSSIAN, Color.web("#5f6066"), 24, 0.2, 5, 5);
        effect.setHeight(40);
        effect.setWidth(40);
        imageView.setEffect(effect);

        upperEffect = new DropShadow(BlurType.GAUSSIAN, Color.web("#ffffff"), 24, 0.2, -4, -4);
        upperEffect.setHeight(40);
        upperEffect.setWidth(40);
        upperView.setEffect(upperEffect);

        try {
            // 
            profileChecker();
            System.out.println("Checking if profile exists...");
        } catch (Exception ex) {
            System.out.println("Error happened in profilechecker method - calling: @ Loading_UI_Controller");
        }
    }

    // profile checker method: void ->  checks if a profile exists: logs in if does, otherwise loads login page
    void profileChecker() throws Exception {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished((ActionEvent event) -> {
            try {
                // profile is an object of ProfileManager class, used to check if profile exists
                ProfileManager profile = new ProfileManager();
                String css = project.Main.class.getResource("Style.css").toExternalForm();
                Scene HomeScene = new Scene(FXMLLoader.load(getClass().getResource("homepageUILight.fxml")));
                HomeScene.setFill(Color.TRANSPARENT);
                HomeScene.getStylesheets().add(css);
                Scene LoginScene = new Scene(FXMLLoader.load(getClass().getResource("Login_UI.fxml")));
                LoginScene.setFill(Color.TRANSPARENT);
                LoginScene.getStylesheets().add(css);
                stage = (Stage) AnchorPane.getScene().getWindow();
                if (profile.verifyLoginStatus()) {
                    System.out.println("Profile exists, loading homepage...");
                    makeFadeOut(stage, HomeScene);
                } else {
                    System.out.println("Profile doesn't exist, loading login page...");
                    makeFadeOut(stage, LoginScene);
                }
            } catch (Exception ex) {
                System.out.println("Error happened in profilechecker method : @ Loading_UI_Controller");
            }
        });
        pause.play();
    }

    // method: makeFadeout -> changes scene after animation
    public void makeFadeOut(Stage stage, Scene scene) {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        // animation
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(3000));
        transition.setNode(AnchorPane);
        transition.setFromValue(1);
        transition.setToValue(0.0);
        transition.setOnFinished((event) -> {
            // setting up stage for loginPage
            stage.setX((screenSize.getWidth() / 2) - (1200 / 2));
            stage.setY((screenSize.getHeight() / 2) - (700 / 2));
            // changing stage scene to login scene
            stage.setScene(scene);
            System.out.println("loading finshed");
        });
        transition.play();
    }

}
