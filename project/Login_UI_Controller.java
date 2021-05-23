package project;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class Login_UI_Controller implements Initializable {

    @FXML
    public TextField changeTextBox;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button outerShadowButtonLogin, closeButton, minButton, login;
    @FXML
    public Hyperlink signUp;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public ImageView imageView;
    @FXML
    public Pane containerPane;
    @FXML
    public FontAwesomeIconView userIcon, passIcon;

    public static Scene LoginScene;

    AlertBox_UI_Controller alertBox = new AlertBox_UI_Controller();

    String enteredName, enteredPassword;
    public static String loadName = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image bg = new Image(getClass().getResourceAsStream("loginUiBackground.JPG"));
        imageView.setImage(bg);
        Rectangle clip = new Rectangle();
        clip.setHeight(700);
        clip.setWidth(1200);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        imageView.setClip(clip);

        AnchorPane.setOpacity(0.7);
        changeTextBox.setCursor(Cursor.DEFAULT);
        makeFadeIn();
        username.focusedProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue || username.getText().length() > 20) {
                        userIconDisappear();
                    } else {
                        userIconAppear();
                    }
                }
                );
        password.focusedProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue || password.getText().length() > 20) {
                        passIconDisappear();
                    } else {
                        passIconAppear();
                    }
                }
                );

    }


    @FXML
    public void cursorMove() {
        changeTextBox.requestFocus();
        changeTextBox.setCursor(Cursor.DEFAULT);
    }

    public void selected() {
        outerShadowButtonLogin.setStyle(" -fx-effect: innershadow(gaussian, #606b9f, 20, 0, 4.12, 4.12);");
        if (!username.getText().equals("") && !password.getText().equals("")) {
            containerPane.setOpacity(0.5);
        }
    }

    public void released() {
        containerPane.setOpacity(1);
        outerShadowButtonLogin.setStyle(" -fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
    }

    public void makeFadeIn() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(0.7);
        transition.setToValue(1);
        transition.play();
    }

    private double x = 400.0, y = 400.0;

    @FXML
    public void makeDragable() {
        imageView.setOnMousePressed((event)
                -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        imageView.setOnMouseDragged((event)
                -> {
            Stage stage = (Stage) AnchorPane.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    @FXML
    public void closeScreen() //closes screen when close button is pushed
    {
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
    public void loadSignup() {
        makeFadeOut(1);
    }

    @FXML
    public void loadRecovery() {
        makeFadeOut(2);
    }

    public void makeFadeOut(int page) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(1);
        transition.setToValue(0.7);
        transition.setOnFinished((event) -> {
            load_scene(page);
        });
        transition.play();
    }

    @FXML
    public void load_scene(int page) {
        String pageName = "";
        switch (page) {
            case 1 ->
                pageName = "Signup_UI.fxml";
            case 2 ->
                pageName = "ForgotPassword_UI.fxml";
            case 3 ->
                pageName = "homepageUILight.fxml";
            default -> {
            }
        }

        try {
            Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource(pageName));
            Scene scene = new Scene(root);
            Stage CurrentStage = (Stage) signUp.getScene().getWindow();
            String css = project.Login_UI_Controller.class.getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            scene.setFill(Color.TRANSPARENT);
            CurrentStage.setScene(scene);

        } catch (IOException exception) {
        }

    }

    public void userIconDisappear() {
        userIcon.setOpacity(0.00);
    }

    public void passIconDisappear() {
        passIcon.setOpacity(0.00);
    }

    public void userIconAppear() {
        userIcon.setOpacity(0.80);
    }

    public void passIconAppear() {
        passIcon.setOpacity(0.80);
    }

    @FXML
    public void verify() throws Exception {
        enteredName = username.getText();
        enteredPassword = password.getText();
        loadName = enteredName;

        String empty = "";

        if (enteredName.equals(empty)) // handles empty name input
        {
            username.setText("");
            username.setPromptText("Username Can't be empty");
        } else if (enteredPassword.equals(empty)) // handles empty password input
        {
            password.setText("");
            password.setPromptText("Password Can't be empty");
        } else // checks input
        {

            LoginDatabaseManager manager = new LoginDatabaseManager();
            //manager object (manages login input)
            switch (manager.verifyLogin(enteredName, enteredPassword)) {
                case 0:
                    alertBox.display("Wrong Username", "Username doesn't exist", 594, 120, 100, 280, 20, 45);
                    break;
                case 1:
                    alertBox.display("Wrong Password", "The password you entered doesn't match", 594, 120, 100, 280, 20, 45);
                    break;
                default: {
                    ProfileManager profile = new ProfileManager(enteredName, enteredPassword);
                    profile.editProfile();
                    makeFadeOut(3);
                }

            }
        }
    }

    public boolean autoVerify(String enteredName, String enteredPassword) throws Exception {

        loadName = enteredName;
        LoginDatabaseManager manager = new LoginDatabaseManager();
        switch (manager.verifyLogin(enteredName, enteredPassword)) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }

}
