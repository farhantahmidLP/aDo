package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;

import javafx.animation.*;
import javafx.concurrent.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.web.*;
import javafx.stage.*;
import java.util.*;
import javafx.util.*;

// Audio Player class
public class AudioPlayer {

    public static boolean aduioPlayerExists = false;
    public static Stage stage;
    public static WebEngine currentEngine;
    // final String variables for style
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

    public static String audioPlayerDefaultButton;
    public static String FIX_WEBVIEW_STYLE;

    boolean canPerform = false;
    boolean pressLoop = true;

    public static String shortenSongName(String song, int range) {
        String newName = "";
        if (song.length() > range) {
            int i = range;
            while (song.charAt(i) != ' ') {
                i--;
            }
            for (int j = 0; j < i; j++) {
                newName += song.charAt(j);
            }
            newName += "...";
            return newName;
        }
        return song;
    }

    // method : It's used to create shadows/effects under the buttons
    public static Button MakeShadowButton(double h, double w, double x, double y, String color, double offsetX, double offsetY, String style) {
        Button shadowButton = new Button();
        DropShadow effect = new DropShadow(BlurType.GAUSSIAN, Color.web(color), 13, 0, offsetX, offsetY);
        shadowButton.setEffect(effect);
        shadowButton.setPrefSize(w, h);
        shadowButton.setLayoutX(x);
        shadowButton.setLayoutY(y);
        if (AppColor.runtimeDark) {
            shadowButton.setStyle("-fx-background-color:  #42476c; -fx-background-radius: 7;");
            FIX_WEBVIEW_STYLE = "document.getElementById(\"playpause\").style.color = \"#42476c\"; "
                    + "document.getElementById(\"playpause\").style.backgroundColor = '#42476c';"
                    + "document.getElementById(\"playpause\").style.borderColor = \"#42476c\";"
                    //
                    + "document.getElementById(\"muteBtn\").style.color = \"#42476c\";"
                    + "document.getElementById(\"muteBtn\").style.backgroundColor = '#42476c';"
                    + "document.getElementById(\"muteBtn\").style.borderColor = \"#42476c\";"
                    //
                    + "document.getElementById(\"loopbtn\").style.color = \"#42476c\";"
                    + "document.getElementById(\"loopbtn\").style.backgroundColor = '#42476c';"
                    + "document.getElementById(\"loopbtn\").style.borderColor = \"#42476c\";"
                    //
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.color = \"#42476c\";"
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.backgroundColor = '#42476c';"
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.borderColor = \"#42476c\";"
                    //
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.color = \"#42476c\";"
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.backgroundColor = '#42476c';"
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.borderColor = \"#42476c\";";
        } else {
            shadowButton.setStyle("-fx-background-color:  #dbdbdb; -fx-background-radius: 7;");

            FIX_WEBVIEW_STYLE = "document.getElementById(\"playpause\").style.color = \"#dbdbdb\"; "
                    + "document.getElementById(\"playpause\").style.backgroundColor = '#dbdbdb';"
                    + "document.getElementById(\"playpause\").style.borderColor = \"#dbdbdb\";"
                    //
                    + "document.getElementById(\"muteBtn\").style.color = \"#dbdbdb\";"
                    + "document.getElementById(\"muteBtn\").style.backgroundColor = '#dbdbdb';"
                    + "document.getElementById(\"muteBtn\").style.borderColor = \"#dbdbdb\";"
                    //
                    + "document.getElementById(\"loopbtn\").style.color = \"#dbdbdb\";"
                    + "document.getElementById(\"loopbtn\").style.backgroundColor = '#dbdbdb';"
                    + "document.getElementById(\"loopbtn\").style.borderColor = \"#dbdbdb\";"
                    //
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.color = \"#dbdbdb\";"
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.backgroundColor = '#dbdbdb';"
                    + "document.getElementsByClassName(\"externalctrl\")[0].style.borderColor = \"#dbdbdb\";"
                    //
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.color = \"#dbdbdb\";"
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.backgroundColor = '#dbdbdb';"
                    + "document.getElementsByClassName(\"externalctrl\")[1].style.borderColor = \"#dbdbdb\";";
        }
        return shadowButton;
    }

    public static Pane makeCoverPane(double h, double w, double x, double y) {
        Pane pane = new Pane();
        pane.setPrefHeight(h);
        pane.setPrefWidth(w);
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        if (AppColor.runtimeDark) {
            pane.setStyle("-fx-background-color: #42476c");
        } else {
            pane.setStyle("-fx-background-color: #dbdbdb");
        }
        return pane;
    }

    // static variables for api Keys.
    public static String[] apiKeys = AudioSearchManager.apiKeys;
    public static boolean[] invalid = new boolean[apiKeys.length];
    public static int apiIndex = 0;

    //to store saved songs name and url and hyperlinks
    // AudioPlayer constructor: 
    AudioPlayer(Stage stage, String URL, String SONGNAME, String status) throws Exception {

        aduioPlayerExists = true;
        this.stage = stage;
        if (AppColor.runtimeDark) {
            audioPlayerDefaultButton = "    align-items: center;\n"
                    + "    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74);\n"
                    + "    -fx-background-radius: 7;"
                    + "    -fx-text-fill: white;"
                    + "    -fx-font-size: 14;";
        } else {
            audioPlayerDefaultButton = "    align-items: center;\n"
                    + "    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea);\n"
                    + "    -fx-background-radius: 7;"
                    + "    -fx-text-fill: #6e6e6e;"
                    + "    -fx-font-size: 14;";
        }

        // play-Pause button
        Button playPause = new Button("⌛");
        playPause.setLayoutX(220.0);
        playPause.setLayoutY(465.0);
        playPause.setPrefHeight(35.0);
        playPause.setPrefWidth(50.0);
        playPause.setStyle(audioPlayerDefaultButton);

        // mute button
        Button mute = new Button("🔊");
        mute.setLayoutX(460.0);
        mute.setLayoutY(596.0);
        mute.setPrefHeight(35.0);
        mute.setPrefWidth(45.0);
        mute.setStyle(audioPlayerDefaultButton);

        // autoPlay buttom
        Button autoPlay = new Button("➤");
        autoPlay.setLayoutX(670.0);
        autoPlay.setLayoutY(596.0);
        autoPlay.setPrefHeight(35.0);
        autoPlay.setPrefWidth(45.0);
        autoPlay.setStyle(audioPlayerDefaultButton);

        // player webview
        WebView webview = new WebView();
        webview.getEngine().load(URL);
        if (AppColor.runtimeDark) {
            webview.getEngine().setUserStyleSheetLocation(getClass().getResource("audioPlayerStyle.css").toString());
        } else {
            webview.getEngine().setUserStyleSheetLocation(getClass().getResource("audioPlayerDark.css").toString());
        }
        //
        webview.getEngine().setJavaScriptEnabled(true);
        webview.setLayoutX(190);
        webview.setLayoutY(410);
        Rectangle clip = new Rectangle();
        clip.setHeight(190);
        clip.setWidth(758);
        clip.setLayoutX(21);
        clip.setLayoutY(48);
        clip.setArcHeight(42);
        clip.setArcWidth(42);
        if (AppColor.runtimeDark) {
            clip.setFill(Color.web("#42476c", 1.0));
        } else {
            clip.setFill(Color.web("#dbdbdb", 1.0));
        }

        webview.setClip(clip);
        webview.getEngine().setCreatePopupHandler((PopupFeatures config) -> null);
        this.currentEngine = webview.getEngine();

        // auto-play button action event
        autoPlay.setOnAction(e -> {
            if (canPerform) {
                if (autoPlay.getText().equals("➤")) {
                    autoPlay.setText("🔁");
                } else {
                    autoPlay.setText("➤");
                }
                try {
                    String script = "toggleLoop(document.getElementById(\"loopbtn\"))";
                    webview.getEngine().executeScript(script);
                    autoPlay.setStyle(audioPlayerDefaultButton);
                } catch (Exception exc) {
                    if (autoPlay.getText().equals("➤")) {
                        autoPlay.setText("🔁");
                    } else {
                        autoPlay.setText("➤");
                    }
                }
            }
        }
        );

        // play - pause button action event
        playPause.setOnAction(e -> {
            if (canPerform) {
                if (pressLoop) {
                    autoPlay.fire();
                    pressLoop = false;
                }
                if (playPause.getText().equals("▶")) {
                    playPause.setText("||");
                    playPause.setStyle("-fx-font-size: 14;");
                } else {
                    playPause.setStyle("-fx-font-size: 10;");
                    playPause.setText("▶");
                }

                try {
                    String script = "togglePlay(document.getElementById(\"playpause\"))";
                    webview.getEngine().executeScript(script);
                    webview.getEngine().executeScript(FIX_WEBVIEW_STYLE);
                    playPause.setStyle(audioPlayerDefaultButton);
                } catch (Exception exc) {
                    if (playPause.getText().equals("▶")) {
                        playPause.setText("||");
                        playPause.setStyle("-fx-font-size: 14;");
                    } else {
                        playPause.setStyle("-fx-font-size: 10;");
                        playPause.setText("▶");
                    }

                }

            }
        }
        );

        // mute button action event
        mute.setOnAction(e -> {

            if (canPerform) {
                if (mute.getText().equals("🔊")) {
                    mute.setText("🔇");
                } else {
                    mute.setText("🔊");
                }

                try {
                    String script = "toggleMute(document.getElementById(\"muteBtn\"))";
                    webview.getEngine().executeScript(script);
                    mute.setStyle(audioPlayerDefaultButton);
                } catch (Exception exc) {

                    if (mute.getText().equals("🔊")) {
                        mute.setText("🔇");
                    } else {
                        mute.setText("🔊");
                    }

                }
            }
        }
        );

        // Saved Song List 
        AudioSearchManager.getSavedSongs();
        ListView songList = new ListView(AudioSearchManager.savedSongHyperlinks);
        HashMap<String, String> linkIndexedInfo = new HashMap<>();
        // songlinks action:
        for (int i = 0;
                i < AudioSearchManager.savedSongHyperlinks.size();
                i++) {

            linkIndexedInfo.put(AudioSearchManager.savedSongHyperlinks.get(i).toString(), (AudioSearchManager.savedSongURL.get(i) + "`" + AudioSearchManager.savedSongName.get(i)));

            AudioSearchManager.savedSongHyperlinks.get(i).setOnAction(e -> {
                try {
                    String current = ((Hyperlink) e.getSource()).toString();
                    String[] info = linkIndexedInfo.get(current).split("`", 2);
                    webview.getEngine().load("");
                    AudioSearchManager.makeAudioPlayer(stage, info[0], info[1]);
                } catch (Exception ex) {
                }
            });

            songList.setLayoutX(55);
            songList.setLayoutY(90);
            songList.setPrefHeight(313);
            songList.setPrefWidth(300);
            songList.setVisible(false);

            int deleteSongIndex = -1;

            for (int ind = 0; ind < AudioSearchManager.savedSongURL.size(); ind++) {
                if (URL.equals(AudioSearchManager.savedSongURL.get(ind))) {
                    deleteSongIndex = ind;
                    break;
                }
            }

            // save-song button ⭳
            Button saveOrDeleteButton = new Button("🗑");
            if (deleteSongIndex == -1) {
                saveOrDeleteButton.setText("💾");
            }
            saveOrDeleteButton.setLayoutX(740.0);
            saveOrDeleteButton.setLayoutY(596.0);
            saveOrDeleteButton.setPrefHeight(35.0);
            saveOrDeleteButton.setPrefWidth(45.0);
            saveOrDeleteButton.setStyle(audioPlayerDefaultButton);

            // save-song button action event
            saveOrDeleteButton.setOnAction(e -> {

                if (saveOrDeleteButton.getText().equals("💾")) {

                    try {
                        AudioSearchManager.LocallySaveSong(SONGNAME, URL);
                        webview.getEngine().load("");
                        AudioSearchManager.makeAudioPlayer(stage, URL, SONGNAME);
                    } catch (Exception ex) {
                    }
                    saveOrDeleteButton.setText("▼");
                } else if (saveOrDeleteButton.getText().equals("🗑")) {
                    try {
                        AudioSearchManager.locallyDeleteSong(URL);
                        webview.getEngine().load("");
                        AudioSearchManager.makeAudioPlayer(stage, URL, SONGNAME);
                    } catch (Exception ex) {

                    }

                }
            }
            );
            //border pane
            Pane radiusPane = new Pane();
            radiusPane.setPrefSize(323, 333);
            radiusPane.setMaxSize(323, 333);
            radiusPane.setMinSize(323, 333);
            radiusPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            radiusPane.setLayoutX(44);
            radiusPane.setLayoutY(80);
            radiusPane.setDisable(true);

            // shadow panes
            Pane radiusShadowPane;

            if (AppColor.runtimeDark) {
                radiusPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #42476c;");
                DropShadow listShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#545d7d"), 20, 0, -7, -7);
                radiusPane.setEffect(listShadow);
                radiusShadowPane = new Pane();
                radiusShadowPane.setPrefSize(323, 333);
                radiusShadowPane.setMaxSize(323, 333);
                radiusShadowPane.setMinSize(323, 333);
                radiusShadowPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                radiusShadowPane.setLayoutX(44);
                radiusShadowPane.setLayoutY(80);
                radiusShadowPane.setDisable(true);
                radiusShadowPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #42476c;");
                DropShadow listDownShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#333850"), 20, 0, 7, 7);
                radiusShadowPane.setEffect(listDownShadow);
                radiusPane.setVisible(false);
                radiusShadowPane.setVisible(false);
            } else {
                radiusPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb;");
                DropShadow listShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#fcfcfc"), 20, 0, -7, -7);
                radiusPane.setEffect(listShadow);
                radiusShadowPane = new Pane();
                radiusShadowPane.setPrefSize(323, 333);
                radiusShadowPane.setMaxSize(323, 333);
                radiusShadowPane.setMinSize(323, 333);
                radiusShadowPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                radiusShadowPane.setLayoutX(44);
                radiusShadowPane.setLayoutY(80);
                radiusShadowPane.setDisable(true);
                radiusShadowPane.setStyle("-fx-background-radius: 15px; -fx-background-color: #dbdbdb;");
                DropShadow listDownShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#bababa"), 20, 0, 7, 7);
                radiusShadowPane.setEffect(listDownShadow);
                radiusPane.setVisible(false);
                radiusShadowPane.setVisible(false);
            }

            //api Webview
            WebView ApiWebView = new WebView();
            ApiWebView.prefHeight(20);
            ApiWebView.prefWidth(20);
            ApiWebView.setLayoutX(100);
            ApiWebView.setLayoutX(200);
            // song name
            Label songName = new Label();
            songName.setText("    " + shortenSongName(SONGNAME, 79));
            if (AppColor.runtimeDark) {
                songName.setStyle("-fx-text-fill: snow; -fx-font-size: 16; -fx-font-family:\"Trebuchet MS\" !important;");
            } else {
                songName.setStyle("-fx-text-fill: grey; -fx-font-size: 16; -fx-font-family:\"Trebuchet MS\" !important;");
            }

            songName.setLayoutX(230);
            songName.setLayoutY(537);

            // searchBar
            TextField searchBar = new TextField();
            searchBar.setPrefSize(290, 50);
            searchBar.setLayoutX(390);
            searchBar.setLayoutY(260);
            searchBar.setPromptText("Enter Song name");
            if (AppColor.runtimeDark) {
                searchBar.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #434a70, #515985);\n"
                        + "    -fx-background-radius: 18;\n"
                        + "    -fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);\n"
                        + "    -fx-text-fill:#ffffff; "
                        + "-fx-prompt-text-fill: snow;");
            } else {

                searchBar.setStyle("    -fx-background-color: linear-gradient(from 0% 130% to 6% 220%, #d9d9d9, #e0e0e0);\n"
                        + "    -fx-background-radius: 18;\n"
                        + "    -fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);\n"
                        + "    -fx-text-fill: #000000;"
                        + "-fx-prompt-text-fill: grey;");
            }

            searchBar.setAlignment(Pos.CENTER);

            // search button
            Button searchButton = new Button("🔍");
            searchButton.setLayoutX(705.0);
            searchButton.setLayoutY(260.0);
            searchButton.setPrefHeight(45.0);
            searchButton.setPrefWidth(45.0);
            if (AppColor.runtimeDark) {
                searchButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;"
                        + "-fx-font-size: 20; -fx-text-fill: white;");
            } else {
                searchButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;"
                        + "-fx-font-size: 20; -fx-text-fill: #6e6e6e;");
            }

            searchButton.setOnAction(e -> {
                try {
                    if (searchBar.getText().equals("")) {
                        searchBar.setPromptText("song name can't be empty");
                    } else {
                        if (!playPause.getText().equals("▶")) {
                            playPause.fire();
                        }
                        searchButton.setText("⌛");
                        playPause.setText("⌛");
                        searchVideo(searchBar.getText(), ApiWebView, webview, stage, songName, playPause, searchButton);
                        searchBar.setText("");
                        searchBar.setPromptText("Enter Song Name");

                    }

                } catch (Exception ex) {
                    System.out.print("error while searching (@youtube api)");
                }
            }
            );

            // Home button
            Button backButton = new Button("");
            backButton.setLayoutX(110.0);
            backButton.setLayoutY(17.0);
            backButton.setPrefHeight(42.0);
            backButton.setPrefWidth(42.0);

            if (AppColor.runtimeDark) {
                backButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
            } else {
                backButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea);  -fx-background-radius: 15;");
            }

            // Home Icon
            FontAwesomeIconView backIcon = new FontAwesomeIconView(FontAwesomeIcon.ARROW_CIRCLE_LEFT);
            backIcon.setLayoutX(118.5);
            backIcon.setLayoutY(49.5);

            if (AppColor.runtimeDark) {
                backIcon.setFill(Color.web("#dadada"));
            } else {
                backIcon.setFill(Color.web("#6e6e6e"));
            }
            backIcon.setSize("30");
            backIcon.setDisable(true);

            // home button effects
            Button homeButtonLowerShadow = new Button();
            Button homeButtonUpperShadow = new Button();

            homeButtonLowerShadow.setPrefSize(39, 39);
            homeButtonLowerShadow.setLayoutX(111);
            homeButtonLowerShadow.setLayoutY(18);

            homeButtonUpperShadow.setPrefSize(39, 39);
            homeButtonUpperShadow.setLayoutX(111);
            homeButtonUpperShadow.setLayoutY(18);

            if (AppColor.runtimeDark) {
                homeButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
                homeButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
            } else {
                homeButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
                homeButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
            }

            // Playlist button
            Button playlistButton = new Button("");
            playlistButton.setLayoutX(180.0);
            playlistButton.setLayoutY(17.0);
            playlistButton.setPrefHeight(42.0);
            playlistButton.setPrefWidth(42.0);
            if (AppColor.runtimeDark) {
                playlistButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
            } else {
                playlistButton.setStyle("    -fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
            }

            // Playlist Icon
            FontAwesomeIconView playlistIcon = new FontAwesomeIconView(FontAwesomeIcon.BARS);
            playlistIcon.setLayoutX(193);
            playlistIcon.setLayoutY(47);

            if (AppColor.runtimeDark) {
                playlistIcon.setFill(Color.web("#dadada"));
            } else {
                playlistIcon.setFill(Color.web("#6e6e6e"));
            }
            playlistIcon.setSize("20");
            playlistIcon.setDisable(true);

            //Playlist button effects
            Button playlistButtonLowerShadow = new Button();
            Button playlistButtonUpperShadow = new Button();

            playlistButtonLowerShadow.setPrefSize(39, 39);
            playlistButtonLowerShadow.setLayoutX(181);
            playlistButtonLowerShadow.setLayoutY(18);

            playlistButtonUpperShadow.setPrefSize(39, 39);
            playlistButtonUpperShadow.setLayoutX(181);
            playlistButtonUpperShadow.setLayoutY(18);

            if (AppColor.runtimeDark) {
                playlistButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #333850, 17, 0, 7, 7); -fx-background-radius: 15;");
                playlistButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #545d7d, 17, 0, -4, -4); -fx-background-radius: 15;");
            } else {
                playlistButtonLowerShadow.setStyle(" -fx-effect: dropshadow(gaussian, #bababa, 17, 0, 7, 7); -fx-background-radius: 15;");
                playlistButtonUpperShadow.setStyle(" -fx-effect: dropshadow(gaussian, #fcfcfc, 17, 0, -4, -4); -fx-background-radius: 15;");
            }

            // Playlist button action:
            playlistButton.setOnMousePressed(e -> {
                playlistButtonUpperShadow.setVisible(false);
                playlistButtonLowerShadow.setVisible(false);
            });
            playlistButton.setOnMouseReleased(e -> {
                playlistButtonUpperShadow.setVisible(true);
                playlistButtonLowerShadow.setVisible(true);

                if (songList.isVisible()) {
                    songList.setVisible(false);
                    radiusPane.setVisible(false);
                    radiusShadowPane.setVisible(false);
                } else {
                    songList.setVisible(true);
                    radiusPane.setVisible(true);
                    radiusShadowPane.setVisible(true);
                }
            });

            // used for shadow and effect
            Pane webPaneOne, webPaneTwo;

            if (AppColor.runtimeDark) {

                webPaneOne = new Pane();
                webPaneOne.setStyle("-fx-background-color: #42476c; -fx-background-radius: 20px;");
                webPaneOne.setPrefHeight(190);
                webPaneOne.setPrefWidth(760);
                webPaneOne.setLayoutX(212);
                webPaneOne.setLayoutY(460);
                DropShadow paneShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#545d7d"), 27, 0, -6, -6);
                webPaneOne.setEffect(paneShadow);
                // used for shadow and effect
                webPaneTwo = new Pane();
                webPaneTwo.setStyle("-fx-background-color: #42476c; -fx-background-radius: 20px;");
                webPaneTwo.setPrefHeight(189);
                webPaneTwo.setPrefWidth(760);
                webPaneTwo.setLayoutX(210);
                webPaneTwo.setLayoutY(460);
                paneShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#333850"), 27, 0, 7, 7);
                webPaneTwo.setEffect(paneShadow);

            } else {
                webPaneOne = new Pane();
                webPaneOne.setStyle("-fx-background-color: #dbdbdb; -fx-background-radius: 20px;");
                webPaneOne.setPrefHeight(190);
                webPaneOne.setPrefWidth(760);
                webPaneOne.setLayoutX(212);
                webPaneOne.setLayoutY(460);
                DropShadow paneShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#fcfcfc"), 27, 0, -6, -6);
                webPaneOne.setEffect(paneShadow);
                // used for shadow and effect
                webPaneTwo = new Pane();
                webPaneTwo.setStyle("-fx-background-color: #dbdbdb; -fx-background-radius: 20px;");
                webPaneTwo.setPrefHeight(189);
                webPaneTwo.setPrefWidth(760);
                webPaneTwo.setLayoutX(210);
                webPaneTwo.setLayoutY(460);
                paneShadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#bababa"), 27, 0, 7, 7);
                webPaneTwo.setEffect(paneShadow);

            }

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
                aduioPlayerExists = false;
                webview.getEngine().load("");
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

            //image
            Rectangle sceneClip = new Rectangle();
            sceneClip.setHeight(700);
            sceneClip.setWidth(1200);
            sceneClip.setArcHeight(39);
            sceneClip.setArcWidth(39);

            if (AppColor.runtimeDark) {
                sceneClip.setStyle("-fx-stroke: #404359; -fx-stroke-width: 2;");
                sceneClip.setFill(Color.web("#42476c", 1.0));
            } else {
                sceneClip.setStyle("-fx-stroke: #a6a6a6; -fx-stroke-width: 2;");
                sceneClip.setFill(Color.web("#dbdbdb", 1.0));
            }

            // button Cover #42476c
            Pane songNamePane = new Pane();

            if (AppColor.runtimeDark) {
                songNamePane.setStyle("-fx-background-color: linear-gradient(from 0% 330% to 6% 620%, #434a70, #515985);"
                        + "-fx-background-radius: 20px;"
                        + "-fx-effect: innershadow(gaussian, #2a2d41, 29, 0, 4.3, 4.3);");
            } else {
                songNamePane.setStyle("-fx-background-color: linear-gradient(from 0% 330% to 6% 620%, #d9d9d9, #e0e0e0);"
                        + "-fx-background-radius: 20px;"
                        + "-fx-effect: innershadow(gaussian, #c4c4c4, 29, 0, 4.3, 4.3);");

            }
            songNamePane.setPrefHeight(53);
            songNamePane.setPrefWidth(750);
            songNamePane.setLayoutX(216);
            songNamePane.setLayoutY(520);

            // page label
            Label pageLabel = new Label("aDo Music");
            pageLabel.setLayoutX(250);
            pageLabel.setLayoutY(14);

            if (AppColor.runtimeDark) {
                pageLabel.setStyle("-fx-text-fill: snow; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
            } else {
                pageLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 42px; -fx-font-family:\"Trebuchet MS\" ");
            }

            //root
            Pane root = new Pane();
            root.getChildren().add(ApiWebView);
            root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            root.getChildren().addAll(sceneClip, close_button, min_button);
            root.getChildren().addAll(webPaneOne, webPaneTwo, webview);
            root.getChildren().add(pageLabel);

            // fixing webview ( load case: downwards )
            root.getChildren().add(makeCoverPane(70, 102, 406, 540));
            root.getChildren().add(makeCoverPane(70, 100, 675, 540));
            root.getChildren().add(makeCoverPane(38, 297, 211, 600));
            root.getChildren().add(makeCoverPane(38, 296, 673, 600));
            root.getChildren().add(makeCoverPane(15, 735, 222, 460));
            root.getChildren().add(makeCoverPane(15, 735, 222, 515));
            root.getChildren().add(makeCoverPane(15, 735, 222, 562.5));
            root.getChildren().add(makeCoverPane(150, 15, 216, 464));

            // searchButton Shadow
            if (AppColor.runtimeDark) {
                root.getChildren().add(MakeShadowButton(41, 40, 708, 263, "#383c5c", 6, 6, "style2"));
                root.getChildren().add(MakeShadowButton(41, 40, 708, 263, "#4c527c", -3, -3, "style2"));

                root.getChildren().add(MakeShadowButton(35, 49, 220, 465, "#383c5c", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 49, 220, 465, "#4c527c", -3, -3, "style1"));
                //

                root.getChildren().add(MakeShadowButton(35, 43, 460, 596, "#383c5c", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 43, 460, 597, "#4c527c", -3, -3, "style1"));
                //
                root.getChildren().add(MakeShadowButton(35, 43, 670, 596, "#383c5c", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 43, 670, 597, "#4c527c", -3, -3, "style1"));
                //    
                root.getChildren().add(MakeShadowButton(35, 42, 740, 595, "#383c5c", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(34, 42, 741, 598, "#4c527c", -3, -3, "style1"));
            } else // 
            {
                root.getChildren().add(MakeShadowButton(41, 40, 708, 263, "#bababa", 6, 6, "style2"));
                root.getChildren().add(MakeShadowButton(41, 40, 708, 263, "#fcfcfc", -3, -3, "style2"));

                root.getChildren().add(MakeShadowButton(35, 49, 220, 465, "#bababa", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 49, 220, 465, "#fcfcfc", -3, -3, "style1"));
                //
                root.getChildren().add(MakeShadowButton(35, 43, 460, 596, "#bababa", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 43, 460, 597, "#fcfcfc", -3, -3, "style1"));
                //
                root.getChildren().add(MakeShadowButton(35, 43, 670, 596, "#bababa", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(35, 43, 670, 597, "#fcfcfc", -3, -3, "style1"));
                //    
                root.getChildren().add(MakeShadowButton(35, 42, 740, 595, "#bababa", 6, 6, "style1"));
                root.getChildren().add(MakeShadowButton(34, 42, 741, 598, "#fcfcfc", -3, -3, "style1"));
            }

            root.getChildren().addAll(homeButtonLowerShadow, homeButtonUpperShadow);
            root.getChildren().addAll(playlistButtonLowerShadow, playlistButtonUpperShadow);
            root.getChildren().addAll(songNamePane, songName, mute, playPause, autoPlay, searchButton, backButton, playlistButton, searchBar);
            root.getChildren().addAll(saveOrDeleteButton, radiusShadowPane, radiusPane, songList);
            root.getChildren().addAll(backIcon, playlistIcon);
            make_draggable(root);

            //css
            String css;
            if (AppColor.runtimeDark) {
                css = project.Main.class
                        .getResource("Style.css").toExternalForm();
            } else {
                css = project.Main.class
                        .getResource("styleLight.css").toExternalForm();
            }

            //scene
            Scene scene = new Scene(root, 1200, 700);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(css);
            //stage

            showStage(stage, scene);

            webview.getEngine().getLoadWorker().stateProperty().addListener((var observable, var oldState, var newState) -> {
                canPerform = (newState == Worker.State.SUCCEEDED);
                if (canPerform) {
                    playPause.setText("▶");
                }
            });
            backButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                homeButtonLowerShadow.setVisible(false);
                homeButtonUpperShadow.setVisible(false);
            });
            backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
                homeButtonLowerShadow.setVisible(true);
                homeButtonUpperShadow.setVisible(true);
                FadeTransition transition = new FadeTransition();
                transition.setDuration(Duration.millis(300));
                transition.setNode(root);
                transition.setFromValue(1);
                transition.setToValue(1);
                transition.setOnFinished((event) -> {
                    stage.setIconified(true);
                });
                transition.play();
            });

        }

    }

    public void showStage(Stage stage, Scene scene
    ) {

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished((ActionEvent event) -> {
            stage.setScene(scene);
            stage.show();

        });
        pause.play();
    }

    public void makeFadeOut(Pane currentRoot, Stage stage) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(250));
        transition.setNode(currentRoot);
        transition.setFromValue(1);
        transition.setToValue(0.7);
        transition.setOnFinished((event) -> {
            try {
                Parent root;
                if (AppColor.runtimeDark) {
                    root = (AnchorPane) FXMLLoader.load(getClass().getResource("Homepage_UI.fxml"));
                } else {
                    root = (AnchorPane) FXMLLoader.load(getClass().getResource("homepageUILight.fxml"));
                }

                Scene scene = new Scene(root);
                String css = project.Main.class
                        .getResource("Style.css").toExternalForm();
                scene.getStylesheets().add(css);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            } catch (IOException ex) {

            }
        });
        transition.play();
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

    public void searchVideo(String query, WebView ApiWebView, WebView webview, Stage stage, Label songName, Button playPause, Button searchButton) throws Exception {

        System.out.println("User searched for: " + query);

        WebEngine webEngine = ApiWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://maruf22-dev.github.io/adoAPI.github.io/API.html");

        String apiKey;
        apiIndex = 0;
        int invalidCnt = 0;
        while (true) {
            int ind = (new Random()).nextInt(invalid.length);
            if (invalid[ind] == true) {
                invalidCnt++;
            } else {
                apiIndex = ind;
                break;
            }
            if (invalidCnt == invalid.length) {
                apiIndex = 0;
                break;
            }
        }
        System.out.print(apiIndex + " : current API index\n");
        apiKey = apiKeys[apiIndex];

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {

            if (newState == Worker.State.SUCCEEDED) {

                // for randommized key (production)
                String script = "getVideo('" + query + "'" + ", '" + apiKey + "')";
                // for specified key (testing)
                //* String script = "getVideo('" + query + "'" + ", '" + apiKeys[1] + "')";
                // for invalid key (testing)
                //* String script = "getVideo('" + query + "', 'invalid')";
                webEngine.executeScript(script);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished((ActionEvent event) -> {
                    String code = webEngine.getDocument().getElementById("videoCode").getTextContent();
                    String title = ApiWebView.getEngine().getDocument().getElementById("title").getTextContent();
                    String status = ApiWebView.getEngine().getDocument().getElementById("status").getTextContent();
                    System.out.println("Code of top result: " + code);
                    System.out.println("Title of top result: " + title);
                    System.out.println("Api query status: " + status);
                    System.out.print(pause.getDuration());
                    //showWebview(webview, songName, playPause, mute, autoPlay, searchButton, code, title, status);
                    if (status.equals("unsuccessful")) {
                        if (invalid[apiIndex] == false) {
                            songName.setText("Couldn't Find: " + shortenSongName(query, 75) + " (Error 403: at API call)");
                            playPause.setText("▶");
                            searchButton.setText("Search");
                            System.out.print("API limit crossed for this api Key index: " + apiIndex + "\n");
                            invalid[apiIndex] = true;
                        }
                    } else {
                        try {
                            String url = "https://www.ravbug.com/yt-audio/?v=" + code;
                            webview.getEngine().load("");
                            System.gc();
                            AudioSearchManager.makeAudioPlayer(stage, url, title);
                        } catch (Exception ex) {
                        }
                    }
                });
                pause.play();
            }
        });
    }

}
