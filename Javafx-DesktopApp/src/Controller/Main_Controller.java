package Controller;

import Model.Classes.*;
import Model.FileHandler.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static Model.ControllerHandler.ControllerElementsHandler.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.*;

public class Main_Controller extends Super_Controller {
    //Resulatat av ny ansatt til nytt vikariat.
    @FXML
    public TextArea lblResult;
    //Markerte vikaren
    TableView.TableViewSelectionModel vikariatSelection;
    //Markerte Jobbsøkeren
    TableView.TableViewSelectionModel jobbsokerSelection;
    //Opptatte vikariat listen
    ObservableList<Object> opptattevikariatList;
    //Den filtrerte listen til jobbsøker
    ObservableList<Jobbsoker> filteredJobbsokerList;
    //Den filtrerte listen til vikariater
    ObservableList<Vikariater> filteredVikariaterList;


    //vikariat kolonner
    @FXML
    public TableColumn stillingsnavnCol;
    @FXML
    public TableColumn arbeidsgiverCol;
    @FXML
    public TableColumn vutdanningCol;
    @FXML
    public TableColumn vjobbkategoriCol;
    @FXML
    public TableColumn stillingstypeCol;
    @FXML
    public TableColumn oppgaverCol;
    @FXML
    public TableColumn kvalifikasjonerCol;
    @FXML
    public TableColumn arbeidsstedCol;
    @FXML
    public TableColumn arbeidsstidCol;
    @FXML
    public TableColumn varighetCol;
    @FXML
    public TableColumn lonnsnivaCol;
    @FXML
    public TableColumn stillingsbeskrivelseCol;
    //jobbsoker kolonner
    @FXML
    public TableColumn fornavnCol;
    @FXML
    public TableColumn etternavnCol;
    @FXML
    public TableColumn adresseCol;
    @FXML
    public TableColumn poststedcol;
    @FXML
    public TableColumn postNrCol;
    @FXML
    public TableColumn tlfCol;
    @FXML
    public TableColumn kjonnCol;
    @FXML
    public TableColumn jobberfCol;
    @FXML
    public TableColumn fodtSelCol;
    @FXML
    public TableColumn utdanningCol;
    @FXML
    public TableColumn lonnkravCol;
    @FXML
    public TableColumn refNavnCol;
    @FXML
    public TableColumn refNrCol;
    @FXML
    public TableColumn jobbkategoriCol;
    @FXML
    public TableView jobbsokerTable;
    @FXML
    public TableView vikariatTable;

    //denne getmetoden brukes i main ved oppstart.
    @FXML
    public ProgressBar getIdProgressBar() {
        return idProgressBar;
    }

    //Progressbar for tråden
    @FXML
    private ProgressBar idProgressBar;
    //Knapp for å vise opptatte stillinger
    //Knapp for å registrere jobbsøker mot et vikariat etter å ha marker begge to
    @FXML
    public Button btnOpptatte, btnRegister;
    //Nedtrekslister for filtrering
    @FXML
    private ComboBox<Object> vikarKategori, arbeidsgiverKategori;

    //OnAction som åpner et nytt vindu med opptatte vikariater
    @FXML
    void opptatteStillinger(ActionEvent event) throws IOException {
        Parent opptatteStilling = FXMLLoader.load(getClass().getResource("/resource/Opptatte_view.fxml"));
        Scene viewSceneOpptatte = new Scene(opptatteStilling, 700, 700);
        viewSceneOpptatte.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
        Stage stage = new Stage();
        stage.getIcons().add(new Image("resource/Icons/baricon.png"));
        stage.setScene(viewSceneOpptatte);
        stage.initOwner(lblResult.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

    }

    //Henter den markerte vikariaten i form av et objekt
    @FXML
    public Vikariater getSelectedVikariat() {
        Vikariater vikariater = null;
        try {
            // setter indeksen til den markerte raden.
            vikariatSelection = vikariatTable.getSelectionModel();
            // Sjekker tabellens markerte items og henter dem ut i form av et objekt
            Object selectedVikariater = vikariatTable.getSelectionModel().getSelectedItem();
            if (selectedVikariater != null) {
                vikariater = (Vikariater) selectedVikariater;
                //caster objektet fra type Object til Vikariater
            }
        } catch (NullPointerException e) {
            alert("Dataene du velget er ikke omfattende");
        }
        return vikariater;
    }

    @FXML
    public Jobbsoker getSelectedJobbsoker() {
        Jobbsoker jobbsokere = null;
        // setter indeksen til den markerte raden.
        jobbsokerSelection = jobbsokerTable.getSelectionModel();
        // Sjekker tabellens markerte items og henter dem ut i form et objekt
        Object selectedJobbsokere = jobbsokerSelection.getSelectedItem();
        if (selectedJobbsokere != null) {
            jobbsokere = (Jobbsoker) selectedJobbsokere;
            //kaster objektet til et Jobbsøker objekt
        }
        return jobbsokere;
    }


    public void onActionRegister(ActionEvent actionEvent) {
        //Filereader som skal lese tidligere opptattevikariater
        Filereader reader = new CsvFileReader();
        //Filewriter for lagring til intern fil
        Filewriter writeToCsv = new CsvFileWriter();
        //Filewriter som skriver til tidligere lagrede jobbsøker fil.
        Filewriter writeToJobbsoker = FileChooserHandler.decideFileWriter(jobbsokerPath);
        //Filewriter som skriver til tidligere lagrede vikariater fil.
        Filewriter writeToVikariater = FileChooserHandler.decideFileWriter(vikariaterPath);
        //Henter vikariat og jobbsøker objektene
        Vikariater vikariater = getSelectedVikariat();
        Jobbsoker jobbsokere = getSelectedJobbsoker();
        //Lager et objekt av type Opptattevikariater og setter inn begge objektene
        OpptatteVikarer opptatteVikarer = new OpptatteVikarer(jobbsokere, vikariater);
        try {
            //Leser av alle tidligere lagrede opptatte vikariater til en liste
            opptattevikariatList = reader.readFile("./src\\resource\\CSV_files\\OpptatteVikariater.csv");
            //Skriver ut en melding til bruker
            lblResult.setText(jobbsokere.getFornavn() + " har nå fått jobb som : " + vikariater.getStillingsNavn() +
                    " hos " + vikariater.getArbeidsGiverNavn());
            //Legger til opptattevikariater objektet i listen
            opptattevikariatList.add(opptatteVikarer);
            //Fjerner jobbsøker som nå er ansatt fra tabellen
            jobbsokerList.remove(jobbsokere);
            //Fjerner vikariatet som nå har blitt tatt av jobbsøkeren
            vikariaterList.remove(vikariater);
        } catch (NullPointerException e) {
            //Dersom ikke både jobbsøker og vikariat er markert, vil brukeren bli varslet om dette
            alert("Du må markere en jobbsøker og et vikariat");
        } catch (IOException e) {
            //Fanger opp unntak ved eventuelle IOexception feil.
            alert(e.getMessage());
        } catch (Exception e) {
            //Fanger opp unntak ved innlesing
            alert(e.getMessage());
        }
        //Dersom filtreringslistene har blitt brukt, vil radene slettes fra dem.
        //Dersom de ikke er tatt i bruk vil de være null.
        if (filteredJobbsokerList != null) {
            filteredJobbsokerList.remove(jobbsokere);
        }
        if (filteredVikariaterList != null) {
            filteredVikariaterList.remove(vikariater);
        }

        try {
            //Lagrer til filen som er valgt av brukeren
            if(jobbsokerPath != null){
                writeToJobbsoker.writeToFile(jobbsokerList, jobbsokerPath);
            }
            if(vikariaterPath != null){
                writeToVikariater.writeToFile(vikariaterList, vikariaterPath);
            }
            //Lagrer til filene som ligger internt i applikasjonen
            writeToCsv.writeToFile(jobbsokerList, "./src\\resource\\CSV_files\\Jobbsøker.csv");
            writeToCsv.writeToFile(vikariaterList, "./src\\resource\\CSV_files\\Vikariater.csv");
            //Opptattevikariater lagres kun internt i applikasjonen, dersom brukeren vil akksessere dataene må
            //brukeren inn på applikasjonen og trykke på Opptattestillinger under "Hjem"
            writeToCsv.writeToFile(opptattevikariatList, "./src\\resource\\CSV_files\\OpptatteVikariater.csv");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            alert("Feil");
        }
        
        vikariatSelection.clearSelection();
        jobbsokerSelection.clearSelection();

    }

    public void kategoriVikariater(ActionEvent actionEvent) {
        filteredVikariaterList = FXCollections.observableArrayList();
        for (Object o : vikariaterList) {
            //både csv reader og jobj returnerer Object, dermed må vi caste litt.
            Vikariater vikariater = (Vikariater) o;
            //Sjekker om den valgte kategorien i nedtrekslisten er lik kategorien i objektet.
            if (arbeidsgiverKategori.getValue().toString().equals(vikariater.getJobbKategori())) {
                filteredVikariaterList.add(vikariater);
            }
        }
        //Dersom Vis alle er valgt, returneres hoved listen.
        if (arbeidsgiverKategori.getValue().toString().equals("Vis alle")) {
            vikariatTable.setItems(vikariaterList);
        } else {
            vikariatTable.setItems(filteredVikariaterList);
        }
    }

    public void KategoriJobbsoker(ActionEvent actionEvent) {
        filteredJobbsokerList = FXCollections.observableArrayList();
        //både csv reader og jobj returnerer Object, dermed må vi caste litt.
        for (Object o : jobbsokerList) {
            Jobbsoker jobbsoker = (Jobbsoker) o;
            //Sjekker om den valgte kategorien i nedtrekslisten er lik kategorien i objektet.
            if (vikarKategori.getValue().toString().equals(jobbsoker.getJobbKategori())) {
                filteredJobbsokerList.add(jobbsoker);
            }
        }
        //Dersom Vis alle er valgt, returneres hoved listen.
        if (vikarKategori.getValue().toString().equals("Vis alle")) {
            jobbsokerTable.setItems(jobbsokerList);
        } else {
            jobbsokerTable.setItems(filteredJobbsokerList);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Kaller på super sin generateLists for å lese inn lister
        super.generateLists();
        //Setter inn en ny verdi på starten av listen.
        //Dette gjør vi fordi vi bruker samme kategori liste både i registreringsskjemaet og
        // for filtreringen. Det er med andre ord mindre unødvendig kode på den måten.
        super.kategoriList.add(0, "Vis alle");

        //Setter propertyvalue til alle kolonnene.
        setJobbsokerValue(kjonnCol, jobberfCol, fodtSelCol, utdanningCol,
                lonnkravCol, refNavnCol, refNrCol, jobbkategoriCol);
        setPersonValue(fornavnCol,
                etternavnCol, adresseCol, poststedcol, postNrCol,
                tlfCol);
        setVikariatValue(stillingsnavnCol,
                arbeidsgiverCol, vutdanningCol, vjobbkategoriCol, stillingstypeCol,
                oppgaverCol, kvalifikasjonerCol, arbeidsstedCol, arbeidsstidCol,
                varighetCol, lonnsnivaCol, stillingsbeskrivelseCol);
        //Setter de statiske listene inn i tabellene.
        vikariatTable.setItems(vikariaterList);
        jobbsokerTable.setItems(jobbsokerList);
        //Setter inn verdier i nedrtekkslistene for filtrering.
        vikarKategori.setItems(super.kategoriList);
        arbeidsgiverKategori.setItems(super.kategoriList);


    }

    //Dersom tråden som kommer fra main(Ved aller første kjøring) lykkes,
    // skal den gjøre følgende;
    public void threadDone() {
        vikariatTable.setItems(vikariaterList);
        jobbsokerTable.setItems(jobbsokerList);
        idProgressBar.progressProperty().unbind();
        idProgressBar.setProgress(0.0);
        idProgressBar.setVisible(false);
        btnRegister.setDisable(false);
        btnOpptatte.setDisable(false);
    }
}


