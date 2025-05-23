package no.ntnu.idatg2003.mappe10.ui.view;

import javafx.scene.control.Alert;

public class Alerts {
    public void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alert-style.css").toExternalForm());


        alert.showAndWait();
    }
}
