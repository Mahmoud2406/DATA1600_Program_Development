package Model.ControllerHandler;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandler {
    // Generelle dialogbokser for håndtering av unntak.
    public static void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Vikarbyråå");
        alert.setHeaderText("Error");
        alert.setContentText(msg);

        alert.showAndWait();
    }
    // dialogsbokser for succeed.
    public static void alertSucceed(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vikarbyråå");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    // dialogbokser for alert
    public static boolean alertExit(String msg) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText("Lagring av fil");
    alert.setTitle("Vikarbyrå");
    alert.setContentText(msg);
    ButtonType okButton = new ButtonType("Ja", ButtonBar.ButtonData.YES);
    ButtonType noButton = new ButtonType("Nei", ButtonBar.ButtonData.NO);

    alert.getButtonTypes().setAll(okButton, noButton);
    Optional<ButtonType> choose = alert.showAndWait();
    if (choose.get() == okButton) {
        //Returnerer true hvis det trykkes ja
        return true;
    } else {
        //Returnerer false hvis det trykkes nei
        return false;
    }
}

}
