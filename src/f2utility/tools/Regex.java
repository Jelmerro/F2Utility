package f2utility.tools;

import f2utility.ToolsBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jelmerro
 */
public class Regex extends VBox implements Tool {

    private final Label label;
    private final TextField match;
    private final TextField replace;

    public Regex() {
        super(5);
        Deactivate();
        //Label
        label = new Label("Regex or Replace");
        setMargin(label, new Insets(5, 5, 0, 5));
        getChildren().add(label);
        //Match field
        match = new TextField();
        setMargin(match, new Insets(0, 5, 0, 5));
        match.setPromptText("Match");
        match.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        match.setMinWidth(100);
        match.setMaxWidth(100);
        getChildren().add(match);
        //Replacement field
        replace = new TextField();
        setMargin(replace, new Insets(0, 5, 5, 5));
        replace.setPromptText("Replacement");
        replace.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        replace.setMinWidth(100);
        replace.setMaxWidth(100);
        getChildren().add(replace);
    }

    @Override
    public String processName(String name) {
        try {
            return name.replaceAll(match.getText(), replace.getText());
        } catch (Exception ex) {
            return name;
        }
    }

    @Override
    public void checkActive() {
        try {
            "".replaceAll(match.getText(), replace.getText());
            if (match.getText().equals(replace.getText())) {
                Deactivate();
            } else {
                Activate();
            }
        } catch (Exception ex) {
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
        match.setText("");
        replace.setText("");
        checkActive();
    }
}
