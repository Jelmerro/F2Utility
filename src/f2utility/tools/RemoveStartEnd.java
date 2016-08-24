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
public class RemoveStartEnd extends VBox implements Tool {

    private final Label label;
    private final TextField start;
    private final TextField end;

    public RemoveStartEnd() {
        super(5);
        Deactivate();
        //Label
        label = new Label("Remove start/end");
        setMargin(label, new Insets(5, 5, 0, 5));
        getChildren().add(label);
        //Start field
        start = new TextField();
        setMargin(start, new Insets(0, 5, 0, 5));
        start.setPromptText("Start");
        start.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        start.setMinWidth(100);
        start.setMaxWidth(100);
        getChildren().add(start);
        //End field
        end = new TextField();
        setMargin(end, new Insets(0, 5, 5, 5));
        end.setPromptText("End");
        end.setOnKeyReleased(t -> {
            ToolsBox.getInstance().updateNewNames();
            checkActive();
        });
        end.setMinWidth(100);
        end.setMaxWidth(100);
        getChildren().add(end);
    }

    @Override
    public String processName(String name) {
        try {
            int startNum = Integer.parseInt(start.getText());
            if (startNum > 0) {
                name = name.substring(Integer.parseInt(start.getText()));
            }
        } catch (Exception ex) {
        }
        try {
            int startNum = Integer.parseInt(end.getText());
            if (startNum > 0) {
                name = name.substring(0, name.length() - Integer.parseInt(end.getText()));
            }
        } catch (Exception ex2) {
        }
        return name;
    }

    @Override
    public void checkActive() {
        try {
            int startNum = Integer.parseInt(start.getText());
            if (startNum > 0) {
                Activate();
            } else {
                Deactivate();
            }
        } catch (Exception ex) {
            try {
                int endNum = Integer.parseInt(end.getText());
                if (endNum > 0) {
                    Activate();
                } else {
                    Deactivate();
                }
            } catch (Exception ex2) {
                Deactivate();
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
        start.setText("");
        end.setText("");
        checkActive();
    }
}
