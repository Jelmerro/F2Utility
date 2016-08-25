package f2utility;

import f2utility.tools.Add;
import f2utility.tools.Misc;
import f2utility.tools.Numbering;
import f2utility.tools.Regex;
import f2utility.tools.RemoveRange;
import f2utility.tools.RemoveStartEnd;
import f2utility.tools.Tool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Horizontal Box containing all the tools for renaming
 *
 * @author Jelmerro
 */
public class ToolBox extends HBox {

    private static ToolBox toolBox;
    private static ArrayList<Node> tools;

    public static ToolBox getInstance() {
        if (toolBox == null) {
            //ToolBox
            toolBox = new ToolBox(5);
            toolBox.setMinHeight(100);
            toolBox.setMaxHeight(100);
            toolBox.setBackground(new Background(new BackgroundFill(Color.web("#EEE"), CornerRadii.EMPTY, Insets.EMPTY)));
            toolBox.setPadding(new Insets(5));
            //Tools
            tools = new ArrayList<>();
            tools.add(new Regex());
            tools.add(new RemoveRange());
            tools.add(new RemoveStartEnd());
            tools.add(new Add());
            tools.add(new Numbering());
            tools.add(new Misc());
            //ButtonBox
            VBox buttonBox = new VBox(6);
            //Rename button
            Button renameButton = new Button("Rename");
            renameButton.setMinSize(90, 42);
            renameButton.setOnAction(e -> {
                //Results of the rename process are checked here
                HashMap<File, Boolean> results = getInstance().rename();
                //And a list is made for all the items that failed
                String failedItems = "";
                for (Entry<File, Boolean> entry : results.entrySet()) {
                    if (!entry.getValue()) {
                        File file = entry.getKey();
                        String ext = file.getExt();
                        if (ext.equals("-")) {
                            failedItems += file.getParent() + File.separator + file.getNewName() + "\n";
                        } else {
                            failedItems += file.getParent() + File.separator + file.getNewName() + "." + ext + "\n";
                        }
                    }
                }
                //No failed items means the renaming was a success
                //Otherwise a the list of failed items is shown
                if (failedItems.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rename success");
                    alert.setHeaderText("Succesfully renamed all files");
                    alert.setContentText(results.size() + " files and folders were renamed with succes");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Rename error");
                    alert.setHeaderText("Some files were not renamed correctly");
                    TextArea list = new TextArea(failedItems);
                    alert.getDialogPane().setContent(list);
                    alert.showAndWait();
                }
            });
            buttonBox.getChildren().add(renameButton);
            //Reset button
            Button resetButton = new Button("Reset");
            resetButton.setMinSize(90, 42);
            resetButton.setOnAction(e -> {
                for (Node n : tools) {
                    try {
                        Tool tool = (Tool) n;
                        tool.Reset();
                    } catch (ClassCastException ex) {

                    }
                }
            });
            buttonBox.getChildren().add(resetButton);
            tools.add(buttonBox);
            //Add all the tools
            for (Node tool : tools) {
                toolBox.getChildren().add(tool);
            }
        }
        return toolBox;
    }

    /**
     * Renames all the items and returns the list with results
     *
     * @return results HashMap<File, Boolean>
     */
    public HashMap<File, Boolean> rename() {
        HashMap<File, Boolean> map = new HashMap<>();
        for (File file : FileList.getInstance().getItems()) {
            boolean success = file.rename();
            if (success) {
                String ext = file.getExt();
                if (ext.equals("-")) {
                    FileList.getInstance().getItems().set(FileList.getInstance().getItems().indexOf(file), new File(file.getParent() + File.separator + file.getNewName()));
                } else {
                    FileList.getInstance().getItems().set(FileList.getInstance().getItems().indexOf(file), new File(file.getParent() + File.separator + file.getNewName() + "." + ext));
                }
            }
            map.put(file, success);
        }
        return map;
    }

    /**
     * Loops over the tools and updates the list with the new name
     */
    public void updateNewNames() {
        for (File file : FileList.getInstance().getItems()) {
            String name = file.getName();
            for (Node n : tools) {
                try {
                    Tool tool = (Tool) n;
                    name = tool.processName(name);
                } catch (ClassCastException ex) {

                }
            }
            file.setNewName(name);
        }
        FileList.getInstance().getColumns().get(0).setVisible(false);
        FileList.getInstance().getColumns().get(0).setVisible(true);
    }

    private ToolBox(double d) {
        super(d);
    }
}
