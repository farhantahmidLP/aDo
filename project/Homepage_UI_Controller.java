package project;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class Homepage_UI_Controller implements Initializable {

    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public Pane containerPane;
    @FXML
    public Button closeButton, minButton, outerShadowButtonSignup;
    @FXML
    public TextField changeTextBox;
    @FXML
    public TextField user_name;
    @FXML
    public PasswordField pass_word;
    @FXML
    public Pane Logout, NoteBook, TimePiece, aDoMusic, PomodoroPage, TaskScheme;
    @FXML
    public Label userLabel, darkMark, lightMark;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Rectangle clip = new Rectangle();
        clip.setHeight(700);
        clip.setWidth(1200);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        AnchorPane.setClip(clip);
        AnchorPane.setOpacity(0.5);
        if (AppColor.runtimeDark) {
            lightMark.setOpacity(0);
            darkMark.setOpacity(1);
        } else {
            lightMark.setOpacity(1);
            darkMark.setOpacity(0);
        }

        makeFadeIn();

    }

    @FXML
    public void cursorMove() {
        changeTextBox.requestFocus();
        changeTextBox.setCursor(Cursor.DEFAULT);
    }

    public void selected() {
        outerShadowButtonSignup.setStyle(" -fx-effect: innershadow(gaussian, #606b9f, 20, 0, 4.12, 4.12);");

    }

    public void released() {
        outerShadowButtonSignup.setStyle(" -fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
    }

    public void makeFadeIn() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(0.7);
        transition.setToValue(1);
        transition.play();
        transition.setOnFinished(e
                -> {
            userLabel.setText(Login_UI_Controller.loadName);
        });
    }

    @FXML
    public void loadLogout() throws Exception //closes screen when close button is pushed
    {
        // instage of ProfileManager deletes profile file from device
        System.out.println("*Login verification is needed after loging out.*\nProfile deleted from device...");
        ProfileManager profile = new ProfileManager();
        profile.deleteProfile();
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
        makeFadeOut(1);

    }

    @FXML
    public void loadMusicplayer() // loads MusicPlayer
    {
        makeFadeOut(0);
    }

    @FXML
    public void loadNotepad() //closes screen when close button is pushed
    {
        makeFadeOut(2);
    }

    @FXML
    public void loadTimePiece() // loads timePiece
    {

        makeFadeOut(3);

    }

    @FXML
    public void loadPomodoro() // loads pomodoro
    {
        if (Pomodoro.exists) {
            if (Pomodoro.stage.isIconified()) {
                Pomodoro.stage.setIconified(true);
            }
            Pomodoro.stage.requestFocus();
        } else {
            makeFadeOut(4);
        }
    }

    @FXML
    public void loadTaskScheme() {

        makeFadeOut(5);

    }

    @FXML
    public void changeBGtoWhite() {

        AppColor.changeMode();

        makeFadeOut(6);

    }

    @FXML
    public void changeBGtoDark() {

        AppColor.changeMode();

        makeFadeOut(7);

    }

    // method: param: page nummber
    public void makeFadeOut(int page) // loads page with animation
    {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(AnchorPane);
        transition.setFromValue(1);
        transition.setToValue(0.7);
        transition.setOnFinished((event) -> {
            if (page == 1 || page == 2 || page == 6 || page == 7) // 
            {
                load_scene(page);
            } //
            else if (page == 3) //
            {
                timePieceSelector t = new timePieceSelector((Stage) (TimePiece.getScene().getWindow()));
            } else if (page == 4) {
                // System.out.printf("YES it came here");
                pomodoroSelector ps = new pomodoroSelector((Stage) (PomodoroPage.getScene().getWindow()));
            } else if (page == 5) {
                MainListUi MLUI = new MainListUi((Stage) (TaskScheme.getScene().getWindow()));
            } else if (page == 0) //
            {
                AnchorPane.setOpacity(1);
                try {
                    final String URL = "";
                    final String SONGNAME = "";
                    AudioSearchManager.savedSongHyperlinks = FXCollections.observableArrayList();

                    if (AudioPlayer.aduioPlayerExists) //
                    {
                        if (AudioPlayer.stage.isIconified()) {
                            AudioPlayer.stage.setIconified(false);
                            AudioPlayer.stage.setX(((Stage) (aDoMusic.getScene().getWindow())).getX());
                            AudioPlayer.stage.setY(((Stage) (aDoMusic.getScene().getWindow())).getY());
                        }
                        AudioPlayer.stage.requestFocus();

                    } //
                    else {
                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.getIcons().add(new Image("project/icon.PNG"));
                        stage.setTitle("aDo");
                        stage.requestFocus();
                        stage.setX(((Stage) (aDoMusic.getScene().getWindow())).getX());
                        stage.setY(((Stage) (aDoMusic.getScene().getWindow())).getY());
                        AudioPlayer player = new AudioPlayer(stage, URL, SONGNAME, "successful");
                    }
                } catch (Exception ex) {
                    System.out.print(ex);
                }
            }
        });
        transition.play();
    }

    public void load_scene(int page) {

        Pane myPane = null;

        String pageName = "";
        if (page == 1) {
            System.out.println("Logged out...");
            pageName = "Login_UI.fxml";
            myPane = Logout;
        } else if (page == 2) {
            if (AppColor.runtimeDark) {
                pageName = "Notebook_UI.fxml";
            } else {
                pageName = "NoteBookUILight.fxml";
            }
            myPane = NoteBook;

        } else if (page == 6) {
            pageName = "homepageUILight.fxml";
            myPane = AnchorPane;
        } else if (page == 7) {

            pageName = "Homepage_UI.fxml";
            myPane = AnchorPane;
        } else {
            myPane = AnchorPane;
        }

        try {
            Parent root = (AnchorPane) FXMLLoader.load(getClass().getResource(pageName));
            Scene scene = new Scene(root);
            Stage CurrentStage = (Stage) myPane.getScene().getWindow();
            String css;
            if (AppColor.runtimeDark) {

                css = project.Main.class.getResource("Style.css").toExternalForm();
            } else {
                css = project.Main.class.getResource("notebookStyleCss.css").toExternalForm();
            }

            scene.getStylesheets().add(css);
            scene.setFill(Color.TRANSPARENT);
            CurrentStage.setScene(scene);

        } catch (IOException exception) {
            System.out.print("error1 loading scene: @Homepage_UI_Controller");
        } catch (Exception exception) {
            System.out.print("\n\n" + exception + "\n\n");
        }

    }

    private double x = 400.0, y = 400.0;

    @FXML
    public void makeDragable() // makes scene draggable
    {
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
        Stage CurrentStage = (Stage) closeButton.getScene().getWindow();
        CurrentStage.close();
        System.exit(0);
    }

    @FXML
    public void minScreen() //minimizes screen when close button is pushed
    {
        Stage CurrentStage = (Stage) minButton.getScene().getWindow();
        CurrentStage.setIconified(true);
    }

    @FXML
    public void logoutHovered() {
        Logout.setScaleX(1.05);
        Logout.setScaleY(1.05);
    }

    @FXML
    public void logoutNotHovered() {
        Logout.setScaleX(1.00);
        Logout.setScaleY(1.00);
    }

    @FXML
    public void timepieceHovered() {
        TimePiece.setScaleX(1.05);
        TimePiece.setScaleY(1.05);
    }

    @FXML
    public void timepieceNotHovered() {
        TimePiece.setScaleX(1.00);
        TimePiece.setScaleY(1.00);
    }

    @FXML
    public void adoMusicHovered() {
        aDoMusic.setScaleX(1.05);
        aDoMusic.setScaleY(1.05);
    }

    @FXML
    public void adoMusicNotHovered() {
        aDoMusic.setScaleX(1.00);
        aDoMusic.setScaleY(1.00);
    }

    @FXML
    public void notebookHovered() {
        NoteBook.setScaleX(1.05);
        NoteBook.setScaleY(1.05);
    }

    @FXML
    public void notebookNotHovered() {
        NoteBook.setScaleX(1.00);
        NoteBook.setScaleY(1.00);
    }

    @FXML
    public void pomodoroHovered() {
        PomodoroPage.setScaleX(1.05);
        PomodoroPage.setScaleY(1.05);
    }

    @FXML
    public void pomodoroNotHovered() {
        PomodoroPage.setScaleX(1.00);
        PomodoroPage.setScaleY(1.00);
    }

    @FXML
    public void taskSchemeHovered() {
        TaskScheme.setScaleX(1.05);
        TaskScheme.setScaleY(1.05);
    }

    @FXML
    public void taskSchemeNotHovered() {
        TaskScheme.setScaleX(1.00);
        TaskScheme.setScaleY(1.00);
    }

}
