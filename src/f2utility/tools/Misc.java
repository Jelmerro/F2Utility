package f2utility.tools;

import f2utility.ToolsBox;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Misc tools: modify the case or trim it
 *
 * @author Jelmerro
 */
public class Misc extends VBox implements Tool {

    private final Label label;
    private final ComboBox mode;
    private final CheckBox trim;

    /**
     * Constructor for the Misc Tool
     */
    public Misc() {
        super(5);
        Deactivate();
        //Label
        label = new Label("Misc");
        setMargin(label, new Insets(5, 5, 0, 5));
        getChildren().add(label);
        //Mode field
        ArrayList list = new ArrayList<>();
        list.add("Same case");
        list.add("Lowercase");
        list.add("Uppercase");
        list.add("Sentence");
        list.add("Title");
        mode = new ComboBox(FXCollections.observableArrayList(list));
        mode.getSelectionModel().select(0);
        setMargin(mode, new Insets(0, 5, 0, 5));
        mode.valueProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        mode.setMinWidth(100);
        mode.setMaxWidth(100);
        getChildren().add(mode);
        //Trim field
        trim = new CheckBox("Trim");
        setMargin(trim, new Insets(0, 5, 5, 5));
        trim.setOnAction(e -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        trim.setMinWidth(100);
        trim.setMaxWidth(100);
        getChildren().add(trim);
    }

    @Override
    public String processName(String name) {
        //Applies the selected case if any
        if (mode.getSelectionModel().getSelectedItem().equals("Lowercase")) {
            name = name.toLowerCase();
        } else if (mode.getSelectionModel().getSelectedItem().equals("Uppercase")) {
            name = name.toUpperCase();
        } else if (mode.getSelectionModel().getSelectedItem().equals("Sentence")) {
            //Capitalize the first alphabetic character found and break
            try {
                for (int i = 0; i < name.length(); i++) {
                    if (Character.isAlphabetic(name.charAt(i))) {
                        name = name.substring(0, i) + Character.toUpperCase(name.charAt(i)) + name.substring(i + 1).toLowerCase();
                        break;
                    }
                }
            } catch (Exception ex) {
            }
        } else if (mode.getSelectionModel().getSelectedItem().equals("Title")) {
            //Capitalize every alphabetic letter that follows a nonalphabetic letter
            //Single quote ' being the exception to that
            //Also capitalizes the very first character (if needed)
            try {
                String nameTemp = "";
                boolean capital = true;
                for (int i = 0; i < name.length(); i++) {
                    if (capital) {
                        nameTemp += Character.toUpperCase(name.charAt(i));
                    } else {
                        nameTemp += Character.toLowerCase(name.charAt(i));
                    }
                    capital = false;
                    if (!Character.isAlphabetic(name.charAt(i)) && name.charAt(i) != '\'') {
                        capital = true;
                    }
                }
                name = nameTemp;
            } catch (Exception ex) {
            }
        }
        //Trims if selected
        if (trim.isSelected()) {
            name = name.trim();
        }
        return name;
    }

    @Override
    public void checkActive() {
        //If a case mode is selected and/or the trim is checked, activate
        //Else deactivate
        if (mode.getSelectionModel().getSelectedItem().equals("Same case")) {
            if (trim.isSelected()) {
                Activate();
            } else {
                Deactivate();
            }
        } else {
            Activate();
        }
    }

    @Override
    public void Activate() {
        setStyle(activated);
    }

    @Override
    public void Deactivate() {
        setStyle(deactivated);
    }

    @Override
    public void Reset() {
        mode.getSelectionModel().select(0);
        trim.setSelected(false);
        checkActive();
    }
}
