package Controller;

import Model.Classes.Arbeidsgiver;
import Model.Threads.ChooseFileThread;
import Model.FileHandler.FileChooserHandler;
import Model.Validation.TxtValidation;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.alert;
import static Model.ControllerHandler.ControllerElementsHandler.*;

public class Arbeidsgiver_Controller extends Super_Controller {

    //Arbeidsgiver kolonner.
    @FXML
    private TableColumn < Object, String >
            aFornavnCol, aEtternavnCol, aAdresseCol, aPoststedCol, aPostNrCol, aTlfCol,
            aJuridiskNavnCol, aBransjeCol, aSelskapsFormCol,
            aEmailCol, aSektorCol;
    //Arbeidsgiver inputfelt.
    @FXML
    private TextField arbeidsgiverJuridiskNavn, arbeidsgiverTelefonnummer, arbeidsgiverEmail,
            arbeidsgiverFornavn, arbeidsgiverEtternavn,
            arbeidsgiverAdresse, arbeidsgiverPostnr, arbeidsgiverPoststed, search;
    //Valideringen kommer her
    @FXML
    private TextArea txtError;
    //bransje
    @FXML
    private ComboBox < Object > arbeidsgiverNACE;
    //radioknapper for sektor og selskapsform.
    @FXML
    private RadioButton arbeidsgiverOffentlig, arbeidsgiverPrivat, arbeidsgiverAS, arbeidsgiverEnkeltForetak;
    //Arbeisdgivertabellen.
    @FXML
    private TableView < Object > arbeidsgiverTable;
    //Progressbar for tråden ved innlesning.
    @FXML
    private ProgressBar idProgressBar;
    //Knapper for endring i tabell.
    @FXML
    private Button btnRegister, btnDelete, btnUpdate;

    //Oppdaterer den markerte raden
    @FXML
    public void onActionUpdate(ActionEvent actionEvent) {
        try {
            if (validation().isEmpty()) {
                arbeidsgiverList.set(indeks, getDataFromArbeidsgiver());
                setValueToNull();
            } else {
                txtError.setText(validation());
            }
        } catch (NullPointerException e) {
            alert("Du kan ikke oppdatere  | årsak: Nullverdier i feltene");
        }
    }

    //Registrerer en Arbeidsgiver
    @FXML
    public void onActionRegister(ActionEvent actionEvent) {
        try {
            //Sjekker valideringen for feltene
            if (validation().isEmpty()) {
                //Sjekker om det ikke eksisterer samme objekt for å unngå duplikate elementer.
                if (!arbeidsgiverList.contains(getDataFromArbeidsgiver())) {
                    arbeidsgiverList.add(getDataFromArbeidsgiver());
                } else {
                    alert("Arbedisgiveren er registrert tidligere");
                }
                //Setter den oppdaterte lista i tabellen
                arbeidsgiverTable.setItems(arbeidsgiverList);
                setValueToNull();
            } else {
                //Skriver ut regex feilmeldingen
                txtError.setText(validation());
            }
        } catch (NullPointerException e) {
            //Regex sjekker for isEmpty og ikke nullpekere, Derfor fanger vi opp nullpekerne her.
            alert("Du kan ikke registrere  | årsak: Nullverdier i feltene");
        }

    }

    //Sletter markerte raden
    @FXML
    public void onActionDelete(ActionEvent actionEvent) {
        try {
            arbeidsgiverTable.getItems().remove(indeks);
            setValueToNull();
            indeks = -1;
        } catch (IndexOutOfBoundsException e) {
            alert("Du kan ikke slette en rad du ikke har markert.");
        }
    }

    //Velger en fil og kjører innlesningen i en ny tråd.
    @FXML
    public void onActionChooseFile(ActionEvent actionEvent) {
        String path = FileChooserHandler.saveToFile(false);
        if (path != null) {
            ExecutorService service = Executors.newSingleThreadExecutor();
            Task < Void > task = new ChooseFileThread(this::threadDone, this::threadFailed, path, 2);
            //Deaktiverer knappene for å ikke kunne registrere,oppdatere eller slette når tråden kjøres.
            super.bindProgressBar(task, idProgressBar, btnDelete, btnRegister, btnUpdate);
            service.execute(task);
        }
    }

    //Kjøres hvis tråden lykkes
    private void threadDone() {
        super.threadDone(btnDelete, btnRegister, btnUpdate, idProgressBar, arbeidsgiverTable, arbeidsgiverList);
    }
    //Kjøres hvis tråden feiler
    private void threadFailed() {
        super.threadFailed(idProgressBar, btnUpdate, btnRegister, btnDelete);
    }


    //Lagrer data til fil.
    @FXML
    public void onActionSaveToNewFile(ActionEvent actionEvent) {
        try {
            String path = FileChooserHandler.saveToFile(true);
            if (path != null) {
                writer = FileChooserHandler.decideFileWriter(path);
                //lagrer fil valgt av brukeren.
                writer.writeToFile(arbeidsgiverList, path);
                //lagrer fil internt i applikasjonen.
                writer.writeToFile(arbeidsgiverList, "./src\\resource\\CSV_files\\Arbeidsgiver.csv");
            }
        } catch (IOException e) {
            alert(e.getMessage());
        }

    }


    public void getSelected() {
        try {
            // setter indeksen til den markerte raden.
            indeks = arbeidsgiverTable.getSelectionModel().getSelectedIndex();
            // Sjekker tabellens markerte celler og henter dem ut i form av et Arbeidsgiver objekt
            if (arbeidsgiverTable.getSelectionModel().getSelectedItem() != null) {
                Object selectedJobbsoker = arbeidsgiverTable.getSelectionModel().getSelectedItem();
                Arbeidsgiver arbeidsgiver = (Arbeidsgiver) selectedJobbsoker;
                setPersonSelected(arbeidsgiver, arbeidsgiverFornavn, arbeidsgiverEtternavn,
                        arbeidsgiverAdresse, arbeidsgiverPoststed, arbeidsgiverPostnr,
                        arbeidsgiverTelefonnummer);
                arbeidsgiverJuridiskNavn.setText(arbeidsgiver.getJurdiskNavn());
                arbeidsgiverEmail.setText(arbeidsgiver.getEmail());
                arbeidsgiverNACE.setValue(arbeidsgiver.getBransje());
                setSelectedRadio(arbeidsgiver.getSelskapsform(), arbeidsgiverEnkeltForetak, arbeidsgiverAS);
                setSelectedRadio(arbeidsgiver.getSektor(), arbeidsgiverPrivat, arbeidsgiverOffentlig);
            }
        } catch (NullPointerException e) {
            // Fanger et unntak hvis enkelte elementer i objektet har nulllpekere i seg
            alert("Dataene du valgte er ikke omfattende");
        }
    }

    //Nullstiller alle komponentene
    public void setValueToNull() {
        setTextToNull(arbeidsgiverEtternavn,
                arbeidsgiverAdresse, arbeidsgiverTelefonnummer,
                arbeidsgiverJuridiskNavn, arbeidsgiverPostnr, arbeidsgiverEmail,
                arbeidsgiverPoststed, arbeidsgiverFornavn, txtError);
        setComboToNull(arbeidsgiverNACE);

    }

    //Validerer inputfeltene og returnerer feilmelding
    public String validation() {
        return TxtValidation.validateAllinArbeidsgiver(arbeidsgiverJuridiskNavn.getText(),
                arbeidsgiverFornavn.getText(), arbeidsgiverEtternavn.getText(), arbeidsgiverAdresse.getText(),
                arbeidsgiverPostnr.getText(), arbeidsgiverPoststed.getText(), arbeidsgiverTelefonnummer.getText(),
                arbeidsgiverEmail.getText(), arbeidsgiverNACE.getValue());
    }

    //Lager et object av feltene som er registrert
    public Object getDataFromArbeidsgiver() {
        String selskapsForm = getSelectedRadio(arbeidsgiverAS, arbeidsgiverEnkeltForetak);
        String sektor = getSelectedRadio(arbeidsgiverPrivat, arbeidsgiverOffentlig);
        return new Arbeidsgiver(arbeidsgiverFornavn.getText(), arbeidsgiverEtternavn.getText(),
                arbeidsgiverAdresse.getText(), arbeidsgiverPoststed.getText(), arbeidsgiverPostnr.getText(),
                arbeidsgiverTelefonnummer.getText(), arbeidsgiverJuridiskNavn.getText(),
                arbeidsgiverNACE.getValue().toString(), selskapsForm,
                arbeidsgiverEmail.getText(), sektor);
    }


    public void initialize(URL location, ResourceBundle resources) {
        //Henter ut combobox verdier som er lagret i egne filer internt i applikasjonen.
        super.generateLists();
        //kaller på søk metoden i ControllerElementsHandler
        searchInTable(search, arbeidsgiverTable, arbeidsgiverList);
        //Setter propertyvalue til alle kolonnene lik attributtene i Arbeidsgiver klassen
        setPersonValue(aFornavnCol, aEtternavnCol, aAdresseCol, aPoststedCol, aPostNrCol, aTlfCol);
        setArbeidsgiverValue(aJuridiskNavnCol, aBransjeCol, aSelskapsFormCol, aEmailCol, aSektorCol);
        //Setter inn verdier i nedtrekkslisten
        arbeidsgiverNACE.setItems(bransjeList);
        //Setter inn arbeidsgivere i tabellen.
        arbeidsgiverTable.setItems(arbeidsgiverList);
    }

}