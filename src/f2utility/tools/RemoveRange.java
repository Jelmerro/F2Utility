package f2utility.tools;

import f2utility.ToolBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Remove a range of characters
 *
 * @author Jelmerro
 */
public class RemoveRange extends VBox implements Tool {

    private final Label label;
    private final TextField start;
    private final TextField end;

    /**
     * Constructor for the RemoveRange Tool
     */
    public RemoveRange() {
        super(5);
        Deactivate();
        //Label
        label = new Label("Remove range");
        setMargin(label, new Insets(5, 5, 0, 5));
        getChildren().add(label);
        //Start field
        start = new TextField();
        setMargin(start, new Insets(0, 5, 0, 5));
        start.setPromptText("From");
        start.setOnKeyReleased(t -> {
            ToolBox.getInstance().updateNewNames();
            checkActive();
        });
        start.setMinWidth(100);
        start.setMaxWidth(100);
        getChildren().add(start);
        //End field
        end = new TextField();
        setMargin(end, new Insets(0, 5, 5, 5));
        end.setPromptText("To");
        end.setOnKeyReleased(t -> {
            ToolBox.getInstance().updateNewNames();
            checkActive();
        });
        end.setMinWidth(100);
        end.setMaxWidth(100);
        getChildren().add(end);
    }

    @Override
    public String processName(String name) {
        //Removes the provided range if valid
        //Also checks for too high end integer and works around it
        try {
            int startNum = Integer.parseInt(start.getText());
            int endNum = Integer.parseInt(end.getText());
            if (startNum <= endNum && startNum > 0 && endNum > 0) {
                if (endNum > name.length()) {
                    return name.substring(0, startNum - 1);
                } else {
                    return name.substring(0, startNum - 1) + name.substring(endNum);
                }
            } else {
                return name;
            }
        } catch (Exception ex) {
            return name;
        }
    }

    @Override
    public void checkActive() {
        //If a valid start and end integer are provided
        //And the end integer is higher than or equal to the start integer, activate
        //Else deactivate
        try {
            int startNum = Integer.parseInt(start.getText());
            int endNum = Integer.parseInt(end.getText());
            if (startNum <= endNum && startNum > 0 && endNum > 0) {
                Activate();
            } else {
                Deactivate();
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
        start.setText("");
        end.setText("");
        checkActive();
    }
}
