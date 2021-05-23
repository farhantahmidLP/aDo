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

public class ForgotPassword_UI_Controller implements Initializable {

    @FXML
    public Hyperlink back, resend;
    @FXML
    public Button closeButton, min_button, log_in, outerShadowButtonFp;
    @FXML
    public TextField textBox, changeTextBox;
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public ImageView imageView;
    @FXML
    public Pane containerPane;
    @FXML
    public Label instruction, status, timerLabel;

    AlertBox_UI_Controller alertBox = new AlertBox_UI_Controller();

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

        AnchorPane.setOpacity(0.5);
        makeFadeIn();
    }

    public void cursorMove() {
        changeTextBox.requestFocus();
        changeTextBox.setCursor(Cursor.DEFAULT);
    }

    public void selected() {
        outerShadowButtonFp.setStyle(" -fx-effect: innershadow(gaussian, #606b9f, 20, 0, 4.12, 4.12);");
        if (!textBox.getText().equals("")) {
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
            loadLoginScene();
        });
        transition.play();
    }

    public void loadLoginScene() {

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
    public static String userName, userEmail;
    VerificationManager manager = new VerificationManager();

    public void resendEvent() {
        manager.changeServerCode(userName, userEmail, "sendMail");
        timerLabel.setText("3:00");
        manager.time = 180;
        manager.reset_timer();
        manager.begin_timer(timerLabel);

    }

    public void loadPasswordScene() {

        try {
            Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource("ChangePassword_UI.fxml"));
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

    public void enterEvent() {

        if (textBox != null) {
            System.out.print("Not null");
            if (textBox.getText().equals("") && instruction.getText().equals("Enter Username to continue")) {
                status.setText("          Username can't be empty");
            } else if (textBox.getText().equals("") && instruction.getText().equals("   Enter Email to continue.")) {
                status.setText("            Email can't be empty");
            } else if (instruction.getText().equals("Enter Username to continue")) {
                boolean action = manager.onUsernameEntered(textBox.getText());
                if (action) {
                    userName = textBox.getText();
                    instruction.setText("   Enter Email to continue.");
                    String space = "";
                    for (int i = 0; i < 28 - manager.getCensoredEmail().length(); i++) {
                        space = space + " ";
                    }
                    status.setText(space + "Hint: " + manager.getCensoredEmail());
                    textBox.setText("");
                    textBox.setPromptText("email");
                } else {
                    status.setText("       The user name doesn't exist");
                }
            } else if (instruction.getText().equals("   Enter Email to continue.")) {
                boolean action = manager.onEmalEntered(textBox.getText());
                if (action) {
                    userEmail = textBox.getText();
                    instruction.setText("    Enter Verification code");
                    textBox.setText("");
                    textBox.setPromptText("Verification code");
                    status.setText("");
                    manager.changeServerCode(userName, userEmail, "sendMail");
                    resend.setDisable(false);
                    resend.setText("Didn't get a code? Resend");
                    manager.begin_timer(timerLabel);
                } else {
                    status.setText("\t The email isn't assosiated\n\t\t to any account");
                }
            } else if (instruction.getText().equals("    Enter Verification code")) {

                boolean action = manager.on_Code_entered(textBox.getText());
                if (action) {
                    ChangePassword_UI_Controller.userName = this.userName;
                    ChangePassword_UI_Controller.userEmail = this.userEmail;
                    loadPasswordScene();
                } else {

                    try {
                        alertBox.display("Recover", "The code you enetered doesn't match\ntry Again", 594, 120, 100, 280, 20, 45);
                    } catch (Exception ex) {

                    }
                }
            }
        }

    }

    private double x = 400.0, y = 400.0;

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

    public void closeScreen() //closes screen when close button is pushed
    {
        Stage CurrentStage = (Stage) closeButton.getScene().getWindow();
        CurrentStage.close();
    }

    public void minScreen() //minimizes screen when close button is pushed
    {
        Stage CurrentStage = (Stage) min_button.getScene().getWindow();
        CurrentStage.setIconified(true);
    }

}
