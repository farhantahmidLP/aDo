package project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class backendConnector {

    static String base;
    static String relocateStack;
    static String shorterRelocateStack;
    public static String currentPage, fileName, content;
    public static String name, urgency, significance, deadline, target;
    // parent object for sublist, task, steps
    // pane arraylist, list array, sublist array, tasks array , steps array, 
    // current directory: string
    // method: get all files content from currrent directory 

    // method void: to change relocateStack upon changing page ( check duplicate page error )
    // string to object of (tasks, steps,sublists, lists) and adds those object to the corrseponding pane array
    // 
    public static ObservableList<Pane> elementPanes = FXCollections.observableArrayList();
    public static ObservableList<Hyperlink> elementsLink = FXCollections.observableArrayList();
    public static ObservableList<Button> elementsInfo = FXCollections.observableArrayList();
    public static ObservableList<Button> elementsDelete = FXCollections.observableArrayList();

    public MainList sublistParent;
    public Sublist tasksParent;
    public Tasks stepsParent;
    public TaskBreakDown elementManager = new TaskBreakDown();

    backendConnector() {
        base = System.getProperty("user.dir").replace("\\", "/");
        relocateStack = base + "/" + "src/project/TaskScheme";
    }

    backendConnector(Object parent, String currentPage, String parentPage, String fileName, String content) {
        this.currentPage = currentPage;
        this.fileName = fileName;
        this.elementManager = new TaskBreakDown();
        this.content = content;
        getElementProperties(content);

        base = System.getProperty("user.dir").replace("\\", "/");
        relocateStack = base + "/" + "src/project/TaskScheme";
        if (currentPage.equals("sublist")) {
            this.sublistParent = (MainList) (parent);
        } else if (currentPage.equals("tasks")) {
            this.tasksParent = (Sublist) (parent);
        } else if (currentPage.equals("steps")) {
            this.stepsParent = (Tasks) (parent);
        }
    }

    public Object createElement(String name, String urgency, String significance, String deadline, String target) {
        if (currentPage.equals("mainlist")) {
            return new MainList(name, urgency, significance, deadline, target);
        } else if (currentPage.equals("sublist")) {
            return new Sublist(sublistParent, name, urgency, significance, deadline, target);
        } else if (currentPage.equals("tasks")) {
            return new Tasks(tasksParent, name, urgency, significance, deadline, target);
        } else if (currentPage.equals("steps")) {
            return new Steps(stepsParent, name, urgency, significance, deadline, target);
        } else {
            return new Object();
        }
    }

    public static void getElementProperties(String content) {
        name = decodeContent(content)[0];
        urgency = decodeContent(content)[1];
        significance = decodeContent(content)[2];
        deadline = decodeContent(content)[3];
        target = decodeContent(content)[4];
    }

    public void addElementToList(Object o) {
        elementManager.add(o);
    }

    public void getElementIndex(Object o) {
        elementManager.getIndexOf(o);
    }

    public void removeElement(Object o) {
        if ((elementManager.getIndexOf(o)) != -1) {
            elementManager.remove(elementManager.getIndexOf(o), o);
        }
    }

    public void relocateMethod(String currentPage, String parentPage) {
        if (!currentPage.equals(parentPage)) {
            relocateStack += "/" + fileName;
        }
    }

    public static void shortenRelocateStack(String directory) {
        String shortDirectory = "";
        for (int i = directory.length() - 1; i >= 0; i--) {
            if (directory.charAt(i) == '/') {
                for (int j = 0; j < i; j++) {
                    shortDirectory += directory.charAt(j);
                }
                break;
            }
        }
        relocateStack = shortDirectory;
    }

    public void getSavedElements() {
        String[] files = TaskFileHandler.getCurrentDirNames(relocateStack);

        for (String file : files) {
            String currentContent;
            if (!file.contains(".txt")) {
                currentContent = TaskFileHandler.getContent(relocateStack, (file + ".txt"));

                getElementProperties(currentContent);
                Hyperlink currentLink = new Hyperlink(name + " " + deadline);
                currentLink.setLayoutY(7);

                elementsLink.add(currentLink);

                FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
                infoIcon.setLayoutX(308);
                infoIcon.setLayoutY(36);

                infoIcon.setSize("30");
                infoIcon.setDisable(true);

                Button infoButton = new Button("");
                infoButton.setLayoutX(300);
                infoButton.setLayoutY(4);
                infoButton.setPrefSize(42, 42);

                if (AppColor.runtimeDark) {
                    infoIcon.setFill(Color.web("#dadada"));
                    currentLink.setStyle("-fx-underline:false; -fx-cursor:default; -fx-border-color:transparent; -fx-text-fill:snow; -fx-font-size:18;");
                    infoButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
                } else {
                    infoIcon.setFill(Color.GREY);
                    currentLink.setStyle("-fx-underline:false; -fx-cursor:default; -fx-border-color:transparent; -fx-text-fill:grey; -fx-font-size:18;");
                    infoButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
                }

                elementsInfo.add(infoButton);

                infoButton.setOnMouseReleased(e
                        -> {

                    TargetBox t = new TargetBox();
                    String formattedContent = "\t" + decodeContent(currentContent)[4] + "\n\n\tUrgence: " + decodeContent(currentContent)[1] + "\n\n\tSignificance: "
                            + decodeContent(currentContent)[2];
                    try {
                        // (String title, String message, double x, double y, double h, double w, double lx, double ly)
                        t.display("Info\nTitle: ", formattedContent, 500, 400, 240, 360, 0, 0);
                    } catch (Exception ex) {

                    }

                });

                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteIcon.setLayoutX(369.5);
                deleteIcon.setLayoutY(35);

                deleteIcon.setSize("30");
                deleteIcon.setDisable(true);

                Button deleteButton = new Button("");
                deleteButton.setLayoutX(360);
                deleteButton.setLayoutY(4);
                deleteButton.setPrefSize(42, 42);
                if (AppColor.runtimeDark) {
                    deleteButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #474c74); -fx-background-radius: 15;");
                    deleteIcon.setFill(Color.web("#dadada"));
                } else {
                    deleteButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea); -fx-background-radius: 15;");
                    deleteIcon.setFill(Color.GREY);

                }

                elementsDelete.add(deleteButton);

                deleteButton.setOnMouseReleased(
                        e -> {

                            String path = relocateStack + "/" + decodeContent(currentContent)[0] + "";
                            TaskFileHandler.deleteFileOrFolder(new File(path));
                            TaskFileHandler.deleteFileOrFolder(new File(path + ".txt"));
                            elementPanes = FXCollections.observableArrayList();
                            elementsLink = FXCollections.observableArrayList();
                            elementsInfo = FXCollections.observableArrayList();
                            elementsDelete = FXCollections.observableArrayList();
                            new MainListUi(MainListUi.stage);

                        });

                Pane currentPane = new Pane();
                currentPane.setPrefSize(400, 50);

                currentPane.getChildren().addAll(elementsLink.get(elementsLink.size() - 1));
                currentPane.getChildren().addAll(elementsDelete.get(elementsDelete.size() - 1));
                currentPane.getChildren().addAll(elementsInfo.get(elementsInfo.size() - 1));
                currentPane.getChildren().addAll(infoIcon, deleteIcon);
                if (AppColor.runtimeDark) {
                    currentPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #323752, #474c74);");
                } else {
                    currentPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #c5c5c5, #eaeaea);");

                }
                elementPanes.add(currentPane);
            }
        }
    }

    public static void saveElementsLocally(String content) {
        getElementProperties(content);
        TaskFileHandler t = new TaskFileHandler(relocateStack, name, content);
        elementPanes = FXCollections.observableArrayList();
        elementsLink = FXCollections.observableArrayList();
        elementsInfo = FXCollections.observableArrayList();
        elementsDelete = FXCollections.observableArrayList();
        new MainListUi(MainListUi.stage);
    }

    public static void closePage() {
        elementPanes = FXCollections.observableArrayList();
        elementsLink = FXCollections.observableArrayList();
        elementsInfo = FXCollections.observableArrayList();
        elementsDelete = FXCollections.observableArrayList();
    }

    public static String encodeContent(String name, String urgency, String significance, String deadline, String target) {
        return name + "`" + urgency + "`" + significance + "`" + deadline + "`" + target;
    }

    public static String[] decodeContent(String content) {
        String[] array;
        array = content.split("`");
        return array;
    }
}
