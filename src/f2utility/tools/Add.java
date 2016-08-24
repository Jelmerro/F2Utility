package f2utility.tools;

import f2utility.ToolsBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jelmerro
 */
public class Add extends GridPane implements Tool {

    private final Label label;
    private final TextField prefix;
    private final TextField suffix;
    private final TextField insert;
    private final TextField pos;

    public Add() {
        super();
        Deactivate();
        //Label
        label = new Label("Add");
        setMargin(label, new Insets(5, 5, 0, 5));
        add(label, 0, 0);
        //Prefix field
        prefix = new TextField();
        setMargin(prefix, new Insets(5, 5, 5, 5));
        prefix.setPromptText("Prefix");
        prefix.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        prefix.setMinWidth(100);
        prefix.setMaxWidth(100);
        add(prefix, 0, 1);
        //Suffix field
        suffix = new TextField();
        setMargin(suffix, new Insets(0, 5, 5, 5));
        suffix.setPromptText("Suffix");
        suffix.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        suffix.setMinWidth(100);
        suffix.setMaxWidth(100);
        add(suffix, 0, 2);
        //Insert field
        insert = new TextField();
        setMargin(insert, new Insets(5, 5, 5, 0));
        insert.setPromptText("Insert");
        insert.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        insert.setMinWidth(100);
        insert.setMaxWidth(100);
        add(insert, 1, 1);
        //Suffix field
        pos = new TextField();
        setMargin(pos, new Insets(0, 5, 5, 0));
        pos.setPromptText("Position");
        pos.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        pos.setMinWidth(100);
        pos.setMaxWidth(100);
        add(pos, 1, 2);
    }

    @Override
    public String processName(String name) {
        if (!prefix.getText().isEmpty()) {
            name = prefix.getText() + name;
        }
        if (!suffix.getText().isEmpty()) {
            name = name + suffix.getText();
        }
        if (!insert.getText().isEmpty() && !pos.getText().isEmpty()) {
            try {
                int position = Integer.parseInt(pos.getText());
                if (position > name.length()) {
                    return name + insert.getText();
                } else if (position > 0) {
                    return name.substring(0, position - 1) + insert.getText() + name.substring(position - 1);
                } else {
                    return name;
                }
            } catch (Exception ex) {
                return name;
            }
        } else {
            return name;
        }
    }

    @Override
    public void checkActive() {
        if (!prefix.getText().isEmpty()) {
            Activate();
        } else if (!suffix.getText().isEmpty()) {
            Activate();
        } else if (!insert.getText().isEmpty() && !pos.getText().isEmpty()) {
            try {
                int position = Integer.parseInt(pos.getText());
                if (position > 0) {
                    Activate();
                } else {
                    Deactivate();
                }
            } catch (Exception ex) {
                Deactivate();
            }
        } else {
            Deactivate();
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
        prefix.setText("");
        suffix.setText("");
        insert.setText("");
        pos.setText("");
        checkActive();
    }
}
