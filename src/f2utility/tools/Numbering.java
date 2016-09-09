package f2utility.tools;

import f2utility.ToolBox;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import static javafx.scene.layout.VBox.setMargin;

/**
 * Tool for numbering the items with optional padding
 *
 * @author Jelmerro
 */
public class Numbering extends VBox implements Tool {

    private final Label label;
    private final ComboBox mode;
    private final TextField pad;
    private int count;

    /**
     * Constructor of the Numbering Tool
     */
    public Numbering() {
        super(5);
        Deactivate();
        count = 1;
        //Label
        label = new Label("Numbering");
        setMargin(label, new Insets(5, 5, 0, 5));
        getChildren().add(label);
        //Mode field
        ArrayList list = new ArrayList<>();
        list.add("None");
        list.add("Prefix");
        list.add("Suffix");
        mode = new ComboBox(FXCollections.observableArrayList(list));
        mode.getSelectionModel().select(0);
        setMargin(mode, new Insets(0, 5, 0, 5));
        mode.valueProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            ToolBox.getInstance().updateNewNames();
            checkActive();
        });
        mode.setMinWidth(100);
        mode.setMaxWidth(100);
        getChildren().add(mode);
        //Padding field
        pad = new TextField();
        setMargin(pad, new Insets(0, 5, 5, 5));
        pad.setPromptText("Padding");
        pad.setOnKeyReleased(t -> {
            ToolBox.getInstance().updateNewNames();
            checkActive();
        });
        pad.setMinWidth(100);
        pad.setMaxWidth(100);
        getChildren().add(pad);
    }

    /**
     * Resets the counter for numbering the items
     */
    public void resetCount() {
        count = 1;
    }

    @Override
    public String processName(String name) {
        //If any mode is selected it should be numbered
        //But only if the padding is either valid or empty
        try {
            int padding = Integer.parseInt(pad.getText());
            if (padding > 0) {
                if (mode.getSelectionModel().getSelectedItem().equals("Prefix")) {
                    name = String.format("%0" + padding + "d", count) + name;
                } else if (mode.getSelectionModel().getSelectedItem().equals("Suffix")) {
                    name = name + String.format("%0" + padding + "d", count);
                }
            }
        } catch (Exception ex) {
            if (pad.getText().isEmpty()) {
                if (mode.getSelectionModel().getSelectedItem().equals("Prefix")) {
                    name = count + name;
                } else if (mode.getSelectionModel().getSelectedItem().equals("Suffix")) {
                    name = name + count;
                }
            }
        }
        count++;
        return name;
    }

    @Override
    public void checkActive() {
        //If any numbering mode is selected
        //And the padding is either empty or a valid integer, activate
        //Else deactivate
        if (mode.getSelectionModel().getSelectedItem().equals("None")) {
            Deactivate();
        } else {
            try {
                if (Integer.parseInt(pad.getText()) > 0) {
                    Activate();
                } else {
                    Deactivate();
                }
            } catch (Exception ex) {
                if (pad.getText().isEmpty()) {
                    Activate();
                } else {
                    Deactivate();
                }
            }
        }
    }

    @Override
    public void Activate() {
        setStyle(ACTIVATED);
    }

    @Override
    public void Deactivate() {
        setStyle(DEACTIVATED);
    }

    @Override
    public void Reset() {
        mode.getSelectionModel().select(0);
        pad.setText("");
        checkActive();
    }
}
