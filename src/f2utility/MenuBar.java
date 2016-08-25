package f2utility;

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

    public static MenuBar getInstance() {
        if (menuBar == null) {
            //Create the menuBar
            menuBar = new MenuBar();
            //File menu
            Menu menu = new Menu("Menu");
            menuBar.getMenus().addAll(menu);
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
            //Delete item
            MenuItem deleteItem = new MenuItem("Remove selected");
            deleteItem.setOnAction(e -> {
                FileList.getInstance().removeSelectedFiles();
            });
            deleteItem.setAccelerator(KeyCombination.valueOf("DELETE"));
            //clear item
            MenuItem clearItem = new MenuItem("Clear list");
            clearItem.setOnAction(e -> {
                FileList.getInstance().getItems().clear();
            });
            clearItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE, KeyCombination.SHIFT_DOWN));
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
            //Add all items
            menu.getItems().addAll(folderItem, fileItem, deleteItem, clearItem, aboutItem, exitItem);
        }
        return menuBar;
    }

    private MenuBar() {
        super();
    }
}
