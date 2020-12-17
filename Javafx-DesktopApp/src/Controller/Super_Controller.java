package Controller;

import Model.FileHandler.AddResourceFiles;
import Model.FileHandler.Filewriter;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import static Model.ControllerHandler.AlertHandler.alertSucceed;

public class Super_Controller implements Initializable {
    // Denne indeksen endres for hver gang noen markerer noe i en tabell, og
    // endres tilbake til -1 dersom man ikke velger noe.
    // Hvis den er -1, da vil det fanges opp en indexoutofboundsException
    // med en veiledende feilmelding til brukeren
    int indeks = -1;
    //Filewriter som alle controllere bruker
    Filewriter writer;
    //Lister over forskjellige nedtrekksliste verdier som hentes fra intern fil
    ObservableList<Object> kategoriList;
    ObservableList<Object> utdanningList;
    ObservableList<Object> bransjeList;
    ObservableList<String> arbeidsgiverNameList;

    // Metode for å binde sammen progressbaren med task sin progress.
    public void bindProgressBar(Task<Void> task, ProgressBar bar, Button... btn) {
        //Gjør progressbaren synlig igjen
        bar.setVisible(true);
        bar.progressProperty().bind(task.progressProperty());
        //Slår av alle knapper for å unngå oppdatering,
        // registrering og sletting når tråden kjører
        for (Button button : btn) {
            button.setDisable(true);
        }
    }
    // kjøres dersom tråden lykkes
    public void threadDone(Button b, Button b2, Button b3,
                           ProgressBar progressBar, TableView table, ObservableList<Object> list) {
        b.setDisable(false);
        b2.setDisable(false);
        b3.setDisable(false);
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0.0);
        table.setItems(list);
        alertSucceed("Opplastningen er vellykket!");

    }
    // Kjører dersom tråden feiles
    public void threadFailed(ProgressBar progressBar,Button ... btn) {
        for (Button button: btn) {
            button.setDisable(false);
        }
        progressBar.progressProperty().unbind();
        progressBar.setVisible(false);
    }

    //Setter de interne filene inn i listene til super.
    public void generateLists() {
        try {
            kategoriList = AddResourceFiles.addItems("./src\\resource\\CSV_files\\Jobbkategori.csv");
            bransjeList = AddResourceFiles.addItems("./src\\resource\\CSV_files\\Bransje.csv");
            utdanningList = AddResourceFiles.addItems("./src\\resource\\CSV_files\\Utdanning.csv");
            arbeidsgiverNameList = AddResourceFiles.addItemsObjectName("./src\\resource\\CSV_files\\Arbeidsgiver.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
