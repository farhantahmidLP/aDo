package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaskFileHandler {
    static DataOutputStream dosFile;
    static DataInputStream dosFileInput;
    File folder, file;

    TaskFileHandler(String path, String name, String content) {
        File folder = new File(path + "/" + name + "/");
        File file = new File(path + "/" + name + ".txt");

        if (!folder.exists()) {
            createFolder(folder);
        }

        if (!file.exists()) {
            if (createFile(file))
                updateFileContent(file, content);
        } else {
            updateFileContent(file, content);
        }

    }

    public static boolean createFolder(File folder) {
        folder.mkdir();
        return folder.exists();
    }

    public static void deleteElement(String pathToFolder) {
        deleteFileOrFolder(new File(pathToFolder));
        deleteFileOrFolder(new File(pathToFolder + ".txt"));
    }

    public static void deleteFileOrFolder(File file) {
        try {
            for (File f : file.listFiles()) {
                f.delete();
                deleteFileOrFolder(f);
            }
        } catch (Exception e) {

        }
        file.delete();
    }

    public static boolean createFile(File file) {
        try {
            dosFile = new DataOutputStream(new FileOutputStream(file));
            dosFile.close();
        } catch (IOException e) {
        }
        return (file.exists());

    }

    public static boolean updateFileContent(File file, String content) {
        try {
            dosFile = new DataOutputStream(new FileOutputStream(file));
            dosFile.writeUTF(content);
            dosFile.close();
        } catch (IOException e) {
        }
        return (file.exists());
    }

    public static String getContent(String path, String name) {
        File file = new File(path + "/" + name);
        if (file.exists()) {
            try {
                dosFileInput = new DataInputStream(new FileInputStream(file));
                String str = dosFileInput.readUTF();
                dosFileInput.close();
                return str;
            } catch (IOException e) {
            } catch (Exception e) {
            }
        }
        return "nonExisting";
    }
    
    // absolute path upto folder
    public static String[] getCurrentDirNames(String path)
    {
        File f = new File(path);
        return f.list();
    }

}
