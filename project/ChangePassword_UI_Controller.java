package project;

import javafx.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.animation.*;
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ChangePassword_UI_Controller implements Initializable {

    @FXML
    public Hyperlink back, resend;
    @FXML
    public Button closeButton;
    @FXML
    public Button minButton;

    public AnchorPane AnchorPane;
    @FXML
    public ImageView imageView;
    @FXML
    public TextField changeTextBox;
    @FXML
    PasswordField password, secondPassword;
    @FXML
    public Button outerShadowButtonFp;
    @FXML
    public Pane containerPane;
    @FXML
    public Label instruction, status;
    
    public static String userName, userEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image bg=new Image(getClass().getResourceAsStream("loginUiBackground.JPG"));
        imageView.setImage(bg);
        Rectangle clip=new Rectangle();
        clip.setHeight(700);
        clip.setWidth(1200);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        imageView.setClip(clip);
        
        instruction.setText("Enter new Password");

    }

    @FXML
    public void cursorMove() {
        changeTextBox.requestFocus();
        changeTextBox.setCursor(Cursor.DEFAULT);
    }

    public void selected() {
        outerShadowButtonFp.setStyle(" -fx-effect: innershadow(gaussian, #606b9f, 20, 0, 4.12, 4.12);");
        if (!password.getText().equals("")) {
            containerPane.setOpacity(0.9);
        }
    }

    public void released() {
        containerPane.setOpacity(1);
        outerShadowButtonFp.setStyle(" -fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
    }

    public void makeFadeIn() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(0.7);
        transition.setToValue(1);
        transition.play();
    }

    @FXML
    public void changeToLogin() //closes screen when close button is pushed
    {
        makeFadeOut();
    }

    public void makeFadeOut() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(1);
        transition.setToValue(0.7);
        transition.setOnFinished((event) -> {
            load_login_scene();
        });
        transition.play();
    }

    public void load_login_scene() {

        try {
            Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource("Login_UI.fxml"));
            Scene scene = new Scene(root);
            Stage CurrentStage = (Stage) back.getScene().getWindow();
            String css = project.Main.class.getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            scene.setFill(Color.TRANSPARENT);
            CurrentStage.setScene(scene);

        } catch (IOException exception) {
            //System.out.print("error");   
        }

    }
    VerificationManager manager = new VerificationManager();
    AlertBox_UI_Controller alertBox = new AlertBox_UI_Controller();

    private static boolean validPassword(String password) {
        int count = 0, capital = 0, num = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                capital++;
            }
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                num++;
            }
            count++;
        }
        return count >= 8 && capital >= 1 && num >= 1;
    }

    public void enterEvent() throws Exception {

        instruction.setText("Enter new Password");
        if (instruction.getText().equals("Enter new Password")) {
            if (password.getText().equals("")) {
                status.setText("              Password can't be empty");
            } else if (!validPassword(password.getText())) {
                alertBox.display("Invalid Password", "  Password must contain:\n* 8 characters.\n* Minimum one number.\n* Minimum one Capital letter.", 920, 350, 149, 220, 25, 45);
            } else if (!password.getText().equals(secondPassword.getText())) {
                status.setText("          The passwords doesn't match");
            } else {
                manager.changePassword(password.getText());
                status.setText("");
                manager.changeServerCode(ForgotPassword_UI_Controller.userName, ForgotPassword_UI_Controller.userEmail, "noMail");
                EmailManager emailManager = new EmailManager();
                emailManager.sendMail(userName, userEmail, "passwordChangedMail");
                alertBox.display("Recover", "Your Password has successfully been changed", 610, 100, 100, 280, 20, 45);
                back.fire();
            }
        }

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

}
