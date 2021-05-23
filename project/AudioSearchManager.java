package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AudioSearchManager {

    public static List<String> savedSongURL = new ArrayList<>();
    public static List<String> savedSongName = new ArrayList<>();
    public static ObservableList<Hyperlink> savedSongHyperlinks = FXCollections.observableArrayList();
    public static String[] apiKeys
            = {
                // current capacity : 400 searches

                "AIzaSyDrh-HQqjnrtPsPg4N2sOiZHmXFLe6LNmE",// 0 - maruf22

                "AIzaSyAu6Q1MnDAQfk1TiX7x8AdwdnBIkwyCRF4", // 1 - servicecustomer829

                "AIzaSyAAG-MxxB6VhueIMKNgbbOzRvL10CMOIzE",// 2 - abir

                "AIzaSyCDndZXpCAMSCeuthk73pq7jon5KMHz0FQ" // 3 - farhan NSU
            //
            //
            //
            //                "AIzaSyBcMEws8owl0gKgTMpHVrX35F5FwBR2BF8",// 4
            //                "AIzaSyDqi4kbhGNccjKDRSVWOpvbBhNE2P5Ljbw",// 5
            //                "AIzaSyC5m46zXTEX9_n09yyYXAss27eJf993el0",// 6
            //                "AIzaSyAgwYAUryLwYC1L3LnD2Wqhb2Jom3au1Y0",// 7
            //                "AIzaSyD7IQSJx91homvnE1WsZY9bwmErtrF54cE",// 8
            //                "AIzaSyBoK_J1vY6gTuqMjTaqiu-DcFrnmlVlG1E",// 9 
            //                "AIzaSyCfMID0VIa57a9m1TMEsFnYQDBb3VgafdA",// 10
            //                "AIzaSyCMRI_yGSNGxwgMdy7pdX8XmIyMlC86Y60",// 11
            //                "AIzaSyCMRI_yGSNGxwgMdy7pdX8XmIyMlC86Y60" // 12
            };

    static String getPath() {
        String path = "";
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        if (OS.contains("win")) {
            path = System.getProperty("user.dir") + "\\" + "Savedsongs.txt";
        } else if (OS.contains("mac")) {
            path = System.getProperty("user.dir") + "/" + "Savedsongs.txt";
        }
        return path;
    }

    public static String[] getSongInfo(String line) {
        String[] string = new String[2];
        string[0] = "";
        string[1] = "";
        int i = 0;
        while (line.charAt(i) != '`') {
            string[0] += line.charAt(i);
            i++;
        }
        i++;
        string[1] = "";
        while (i < line.length()) {
            string[1] += line.charAt(i);
            i++;
        }
        return string;
    }

    static String content = "";

    public static void LocallySaveSong(String songName, String songCode) throws IOException {
        File songFile = new File(getPath());
        FileWriter fileWriter;

        BufferedReader reader = new BufferedReader(new FileReader(songFile));
        String line;
        content = "";
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        fileWriter = new FileWriter(songFile);
        fileWriter.write(content + songName + "`" + songCode);
        reader.close();
        fileWriter.close();
    }

    public static void locallyDeleteSong(String url) throws FileNotFoundException, IOException {
        File songFile = new File(getPath());
        BufferedReader reader = new BufferedReader(new FileReader(songFile));
        String line = "", content = "";
        while ((line = reader.readLine()) != null) {
            if (!line.contains(url)) {
                content += line + "\n";
            }
        }
        FileWriter fileWriter = new FileWriter(songFile);
        fileWriter.write(content);
        reader.close();
        fileWriter.close();

    }

    static String songName = "", songURL = "";

    public static void getSavedSongs() throws IOException {

        File songFile = new File(getPath());

        songName = "";
        songURL = "";
        if (songFile.length() != 0) {
            BufferedReader reader = new BufferedReader(new FileReader(songFile));
            String line;
            while ((line = reader.readLine()) != null) {
                songName = getSongInfo(line)[0];
                songURL = getSongInfo(line)[1];
                Hyperlink current = new Hyperlink(AudioPlayer.shortenSongName(songName, 30));
                if(AppColor.runtimeDark)
                {
                    current.setTextFill(Color.SNOW);
                }
                else
                {
                    current.setTextFill(Color.GREY);
                }
                
                current.setStyle("-fx-padding: 5;");
                savedSongURL.add(songURL);
                savedSongName.add(songName);
                savedSongHyperlinks.add(current);
            }
            Collections.reverse(savedSongURL);
            Collections.reverse(savedSongName);
            Collections.reverse(savedSongHyperlinks);
        } else {
            
            Hyperlink current = new Hyperlink(AudioPlayer.shortenSongName("", 30));
            current.setTextFill(Color.SNOW);
            current.setStyle("-fx-padding: 5;");
            savedSongURL.add(songURL);
            savedSongName.add(songName);
            savedSongHyperlinks.add(current);
            Collections.reverse(savedSongURL);
            Collections.reverse(savedSongName);
            Collections.reverse(savedSongHyperlinks);

        }
    }

    public static void makeAudioPlayer(Stage stage, String savedsongURL, String PassedSavedSongName) throws Exception {
        savedSongURL = new ArrayList<>();
        savedSongName = new ArrayList<>();
        savedSongHyperlinks = FXCollections.observableArrayList();
        new AudioPlayer(stage, savedsongURL, PassedSavedSongName, "successful");
    }

}
