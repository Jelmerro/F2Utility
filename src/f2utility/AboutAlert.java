package f2utility;

import java.awt.Desktop;
import java.net.URI;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

/**
 * Alert for the about section of the program, includes link to github page
 *
 * @author Jelmerro
 */
class AboutAlert extends Alert {

    /**
     * Constructor of the AboutAlert
     */
    public AboutAlert() {
        super(AlertType.INFORMATION);
        initModality(Modality.APPLICATION_MODAL);
        //Icon
        ImageView icon = new ImageView(F2Utility.stage.getIcons().get(0));
        icon.maxHeight(80);
        icon.setFitHeight(80);
        icon.setPreserveRatio(true);
        icon.setCache(true);
        getDialogPane().setGraphic(icon);
        //Content
        setTitle("About");
        setHeaderText("F2Utility 1.0.1");
        setContentText("An easy and effective batch file rename tool\nCreated by Jelmerro\nMIT License");
        //Github button
        Button githubButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        githubButton.setText("Github");
        githubButton.setOnAction(e -> {
            //Try opening the default desktop browser
            //Else show the url in an alert
            new Thread(() -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Jelmerro/F2Utility"));
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.NONE, "URL: https://github.com/Jelmerro/F2Utility", ButtonType.CLOSE).showAndWait();
                }
            }).start();
            e.consume();
        });
        setOnCloseRequest(e -> {
            e.consume();
        });
    }
}
