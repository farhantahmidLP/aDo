package project;

import javafx.application.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.stage.*;

// sub class of the javafx Application class
public class Main extends Application {

    // main method: calls tge start method
    public static void main(String[] args) {
        System.out.println("Launcing application . . .");
        launch(args);
    }

    @Override
    // start method: starts the application
    public void start(Stage stage) throws Exception {
        System.out.println("Application Started.");

        // css 
        String css = project.Main.class.getResource("Style.css").toExternalForm();

        // root
        Parent root = FXMLLoader.load(getClass().getResource("Loading_UI.fxml"));

        // scene : loading scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(css);
        Login_UI_Controller.LoginScene = scene;

        // stage
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("project/icon.PNG"));
        stage.setTitle("aDo");
        stage.setX((screenSize.getWidth() / 2) - (480 / 2));
        stage.setY((screenSize.getHeight() / 2) - (270 / 2));
        // stage created with scene from root
        // showing stage
        stage.show();
        // close all background processes upon closed
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(e -> Platform.exit());
    }
}


