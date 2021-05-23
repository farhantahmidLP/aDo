package project;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Signup_UI_Controller implements Initializable {

    @FXML
    public Button close_button;
    @FXML
    public Button min_button;
    @FXML
    public Button log_in;
    @FXML
    public Button sign_up;
    @FXML
    public Hyperlink back;
    @FXML
    public TextField user_name;
    @FXML
    public TextField email_id;
    @FXML
    public PasswordField pass_word;
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public ImageView image_view;
    @FXML
    public TextField changeTextBox;
    @FXML
    public Button outerShadowButtonSignup;
    @FXML
    public Pane containerPane;
    @FXML
    public FontAwesomeIconView userIcon;
    @FXML
    public FontAwesomeIconView emailIcon;
    @FXML
    public FontAwesomeIconView passIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image bg=new Image(getClass().getResourceAsStream("loginUiBackground.JPG"));
        image_view.setImage(bg);
        Rectangle clip=new Rectangle();
        clip.setHeight(700);
        clip.setWidth(1200);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        image_view.setClip(clip);
        
        AnchorPane.setOpacity(0.5);
        make_fadein();
        user_name.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue || user_name.getText().length() > 20) {
                userIconDisappear();
            } else {
                userIconAppear();
            }
        });
        pass_word.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue || pass_word.getText().length() > 20) {
                passIconDisappear();
            } else {
                passIconAppear();
            }
        });
        email_id.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue || email_id.getText().length() > 20) {
                emailIconDisappear();
            } else {
                emailIconAppear();
            }
        });

    }

    @FXML
    public void cursorMove() {
        System.out.println("Rafin and Faiza :D ");
        changeTextBox.requestFocus();
        changeTextBox.setCursor(Cursor.DEFAULT);
    }

    public void selected() {
        outerShadowButtonSignup.setStyle(" -fx-effect: innershadow(gaussian, #606b9f, 20, 0, 4.12, 4.12);");

    }

    public void released() {
        outerShadowButtonSignup.setStyle(" -fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
    }

    public void make_fadein() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(0.7);
        transition.setToValue(1);
        transition.play();
    }

    @FXML
    public void changeto_login() //closes screen when close button is pushed
    {
        make_fadeout();
    }

    public void make_fadeout() {
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

    public void sign_up() throws Exception {
        AlertBox_UI_Controller alertBox = new AlertBox_UI_Controller();
        String entered_name = user_name.getText();
        String entered_pass = pass_word.getText();
        String entered_email = email_id.getText();

        String empty = "";
        if (entered_name.equals(empty)) // handles empty name input
        {
            user_name.setText("");
            user_name.setPromptText(" Username Can't be empty");
        } else if (entered_email.equals(empty)) // handles empty password input
        {
            email_id.setText("");
            email_id.setPromptText(" Email Can't be empty");
        } else if (!validEmail(entered_email)) {
            alertBox.display("Invalid Email", "Make sure that you entered a valid email", 920, 320, 100, 265, 25, 45);
        } else if (entered_pass.equals(empty)) // handles empty password input
        {
            pass_word.setText("");
            pass_word.setPromptText("Password Can't be empty");

        } else if (!validPassword(entered_pass)) {
            alertBox.display("Invalid Password", "  Password must contain:\n* 8 characters.\n* Minimum one number.\n* Minimum one Capital letter.", 920, 350, 149, 220, 25, 45);
        } else {

            SignUpDatabaseManager manager = new SignUpDatabaseManager();
            switch (manager.signUpProcess(entered_name, entered_pass, entered_email)) {
                case 1 ->
                    alertBox.display("Invalid username", "Username is not available", 920, 260, 100, 190, 25, 45);
                case 2 ->
                    alertBox.display("Invalid email", "This email is assosiated with another account", 920, 330, 100, 280, 20, 45);
                default -> {
                    alertBox.display("Created", "Account has been created", 920, 260, 100, 190, 25, 45);
                    back.fire();
                }
            }
        }
    }

    private double x = 400.0, y = 400.0;

    @FXML
    public void make_draggable() {
        image_view.setOnMousePressed((event)
                -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        image_view.setOnMouseDragged((event)
                -> {
            Stage stage = (Stage) AnchorPane.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    @FXML
    public void close_screen() //closes screen when close button is pushed
    {
        Stage CurrentStage = (Stage) close_button.getScene().getWindow();
        CurrentStage.close();
    }

    @FXML
    public void min_screen() //minimizes screen when close button is pushed
    {
        Stage CurrentStage = (Stage) min_button.getScene().getWindow();
        CurrentStage.setIconified(true);
    }

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

    public void userIconDisappear() {
        userIcon.setOpacity(0.00);
    }

    public void emailIconDisappear() {
        emailIcon.setOpacity(0.00);
    }

    public void passIconDisappear() {
        passIcon.setOpacity(0.00);
    }

    public void userIconAppear() {
        userIcon.setOpacity(0.80);
    }

    public void emailIconAppear() {
        emailIcon.setOpacity(0.80);
    }

    public void passIconAppear() {
        passIcon.setOpacity(0.80);
    }

    private static boolean validEmail(String email) {
        if (email.length() < 3) {
            return false;
        }
        int at = 0, dot = 0, first = 0, last = 0, invalid = 0, afterDot = 0;
        if ((email.charAt(0) >= 48 && email.charAt(0) <= 57) || (email.charAt(0) >= 65 && email.charAt(0) <= 90) || (email.charAt(0) >= 97 && email.charAt(0) <= 122)) {
            first = 1;
        }
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                if ((email.charAt(i - 1) >= 48 && email.charAt(i - 1) <= 57) || (email.charAt(i - 1) >= 65 && email.charAt(i - 1) <= 90) || (email.charAt(i - 1) >= 97 && email.charAt(i - 1) <= 122)) {
                    last = 1;
                }
                at++;
            }
            if (email.charAt(i) < 37 && email.charAt(i) > 122) {
                invalid++;
            } else if (email.charAt(i) > 37 && email.charAt(i) < 43) {
                invalid++;
            } else if (email.charAt(i) == 44 || email.charAt(i) == 47) {
                invalid++;
            } else if (email.charAt(i) > 57 && email.charAt(i) < 64) {
                invalid++;
            } else if (email.charAt(i) > 90 && email.charAt(i) < 95) {
                invalid++;
            } else if (email.charAt(i) == 96) {
                invalid++;
            }
            if (email.charAt(i) == '.') {
                dot++;
            }
            if (email.charAt(i) == '.') {
                afterDot = email.length() - 1 - i;
            }
        }
        return !(at > 1 || first != 1 || last != 1 || (dot < 1 || dot > 2) || afterDot < 2 || invalid > 0);
    }

}
