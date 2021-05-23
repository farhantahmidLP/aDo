package project;

import java.io.*;
import java.util.*;

// ProfileManager class: it's instance is used to check if profile exists or not
public final class ProfileManager {

    // necessary datafields
    String path = "";
    protected String userName, passWord;
    public File profile;

    // get's file path
    static String getPath() {
        String path = "";
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        // if user os is windows
        if (OS.contains("win")) {
            path = System.getProperty("user.dir") + "\\" + "profile.txt";
        }//
        /// if user is is mac
        ///
        else if (OS.contains("mac")) {
            path = System.getProperty("user.dir") + "/" + "profile.txt";
        }
        return path;
    }

    // no args constructor
    ProfileManager() throws Exception {
        this.profile = new File(getPath());
    }

    // constructor : string: name, string pass
    // initializes datafields with passed value
    ProfileManager(String name, String pass) throws Exception {
        this.userName = name;
        this.passWord = pass;
        this.profile = new File(getPath());
        editProfile();
    }

    // method: editProfile -> writes name and password to file (stimulates the creation of profile building)
    public void editProfile() throws Exception {

        try (FileWriter profileEditor = new FileWriter(profile)) {
            profileEditor.write(userName + "\n" + passWord);
            profileEditor.close();
        }
    }

    // method: verifies user by comparing from databse
    // returns status in boolean
    public boolean verifyLoginStatus() throws Exception {

        if (!profile.exists()) {
            return false;
        }

        Scanner reader = new Scanner(profile);

        String Content = "";
        while (reader.hasNext()) {
            Content += reader.next() + '`';
        }
        String name = "", pass = "";

        boolean gotOne = false;
        // processing file content
        for (int i = 0; i < Content.length(); i++) {
            if (Content.charAt(i) == '`') {
                gotOne = true;
            }
            if (!gotOne) {
                if (Content.charAt(i) != '`') {
                    name += Content.charAt(i);
                }
            } else {
                if (Content.charAt(i) != '`') {
                    pass += Content.charAt(i);
                }
            }
        }
        // status holds the boolean value from static method of Login_UI_Controller class
        System.out.println("Verifiying username and password ... \nMatch profile file (if exists) with user profile from MongoDB database...");
        boolean status = new Login_UI_Controller().autoVerify(name, pass);
        reader.close();
        return status;

    }

    public void deleteProfile() throws Exception {
        editProfile();
        profile.deleteOnExit();
    }

}
