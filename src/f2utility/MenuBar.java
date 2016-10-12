package f2utility;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * MenuBar containing all the actions regarding the fileList
 *
 * @author Jelmerro
 */
public class MenuBar extends javafx.scene.control.MenuBar {

    private static MenuBar menuBar;

    /**
     * Returns the only allowed instance of the MenuBar
     *
     * @return menuBar MenuBar
     */
    public static MenuBar getInstance() {
        if (menuBar == null) {
            //Create the menuBar
            menuBar = new MenuBar();
            //File menu
            Menu fileMenu = new Menu("File");
            //Open files item
            MenuItem fileItem = new MenuItem("Open files");
            fileItem.setOnAction(e -> {
                FileList.getInstance().showFileChooser();
            });
            fileItem.setAccelerator(KeyCombination.valueOf("O"));
            //Open folder item
            MenuItem folderItem = new MenuItem("Open folder");
            folderItem.setOnAction(e -> {
                FileList.getInstance().showDirChooser();
            });
            folderItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
            //About item
            MenuItem aboutItem = new MenuItem("About");
            aboutItem.setOnAction(e -> {
                new AboutAlert().showAndWait();
            });
            aboutItem.setAccelerator(KeyCombination.valueOf("A"));
            //Exit item
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.setOnAction(e -> {
                System.exit(0);
            });
            exitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
            //List menu
            Menu listMenu = new Menu("List");
            //Move up item
            MenuItem moveUpItem = new MenuItem("Move up");
            moveUpItem.setOnAction(e -> {
                //Stores a copy of the selected items
                //Moves each of them up
                //Selects all the previously selected items
                ObservableList<File> selectedCells = FXCollections.observableArrayList(FileList.getInstance().getSelectionModel().getSelectedItems());
                for (File file : selectedCells) {
                    int i = FileList.getInstance().getItems().indexOf(file);
                    Collections.swap(FileList.getInstance().getItems(), i, i - 1);
                }
                for (File file : selectedCells) {
                    FileList.getInstance().getSelectionModel().select(file);
                }
                ToolBox.getInstance().updateNewNames();
            });
            moveUpItem.setAccelerator(KeyCombination.valueOf("["));
            //Move down item
            MenuItem moveDownItem = new MenuItem("Move down");
            moveDownItem.setOnAction(e -> {
                //Stores a copy of the selected items
                //Moves each of them down
                //Selects all the previously selected items
                ObservableList<File> selectedCells = FXCollections.observableArrayList(FileList.getInstance().getSelectionModel().getSelectedItems());
                Collections.reverse(selectedCells);
                for (File file : selectedCells) {
                    int i = FileList.getInstance().getItems().indexOf(file);
                    Collections.swap(FileList.getInstance().getItems(), i, i + 1);
                }
                for (File file : selectedCells) {
                    FileList.getInstance().getSelectionModel().select(file);
                }
                ToolBox.getInstance().updateNewNames();
            });
            moveDownItem.setAccelerator(KeyCombination.valueOf("]"));
            //Delete item
            MenuItem deleteItem = new MenuItem("Remove selected");
            deleteItem.setOnAction(e -> {
                FileList.getInstance().removeSelectedFiles();
            });
            deleteItem.setAccelerator(KeyCombination.valueOf("DELETE"));
            //Clear item
            MenuItem clearItem = new MenuItem("Clear list");
            clearItem.setOnAction(e -> {
                FileList.getInstance().getItems().clear();
            });
            clearItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE, KeyCombination.SHIFT_DOWN));
            //Add all items
            fileMenu.getItems().addAll(folderItem, fileItem, aboutItem, exitItem);
            listMenu.getItems().addAll(moveUpItem, moveDownItem, deleteItem, clearItem);
            menuBar.getMenus().addAll(fileMenu, listMenu);
        }
        return menuBar;
    }

    private MenuBar() {
        super();
    }
}
