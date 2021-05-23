package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.stage.FileChooser;
import java.util.Scanner;

public class NoteManager {

    public String open() {
        String str = "";
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("c", "*.c"),
                new FileChooser.ExtensionFilter("c++", "*.cpp"),
                new FileChooser.ExtensionFilter("java", "*.java")
        );
        File save = chooser.showOpenDialog(null);
        try {
            if (save != null) {
                Scanner scan = new Scanner(save);
                while (scan.hasNextLine() == true) {
                    str += (scan.nextLine() + "\n");
                }

            }
        } catch (FileNotFoundException ex) {
            System.out.print(ex);
        }
        return str;
    }

    public void save(String content) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File save = chooser.showSaveDialog(null);
        try {
            if (save != null) {
                try (PrintWriter writer = new PrintWriter(save)) {
                    writer.print(content);
                    writer.close();
                }
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

    public void saveAs(String content) {
        FileChooser chooser = new FileChooser();

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("c", "*.c"),
                new FileChooser.ExtensionFilter("c++", "*.cpp"),
                new FileChooser.ExtensionFilter("java", "*.java")
        );
        File save = chooser.showSaveDialog(null);
        try {
            if (save != null) {
                try (PrintWriter writer = new PrintWriter(save)) {
                    writer.print(content);
                    writer.close();
                }
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }

}
