package f2utility.tools;

import f2utility.ToolsBox;
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
 *
 * @author Jelmerro
 */
public class Numbering extends VBox implements Tool {

    private final Label label;
    private final ComboBox mode;
    private final TextField pad;

    public Numbering() {
        super(5);
        Deactivate();
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
            ToolsBox.getInstance().updateNewNames();
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
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        pad.setMinWidth(100);
        pad.setMaxWidth(100);
        getChildren().add(pad);
    }

    @Override
    public String processName(String name) {
        return name;
    }

    @Override
    public void checkActive() {
        if (mode.getSelectionModel().getSelectedItem().equals("None")) {
            Deactivate();
        } else {
            try {
                if (Integer.parseInt(pad.getText()) >= 0) {
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
        setStyle(activated);
    }

    @Override
    public void Deactivate() {
        setStyle(deactivated);
    }

    @Override
    public void Reset() {
        mode.getSelectionModel().select(0);
        pad.setText("");
        checkActive();
    }
}
