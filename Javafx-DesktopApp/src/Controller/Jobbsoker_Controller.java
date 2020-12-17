package Controller;

import Model.Threads.ChooseFileThread;
import Model.FileHandler.FileChooserHandler;
import Model.Classes.Jobbsoker;
import Model.Validation.TxtValidation;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.alert;
import static Model.ControllerHandler.ControllerElementsHandler.*;


public class Jobbsoker_Controller extends Super_Controller {
    //Jobbsøker inputfelt.
    @FXML
    private TextField fornavn, etternavn,
            adresse, lonnKrav, postNummer,
            telefonNummer, postSted, refNavn, refTlf, search;
    //Jobbsøker kolonner
    @FXML
    private TableColumn<Object, String> fornavnCol,
            etternavnCol, adresseCol, poststedcol, postNrCol,
            tlfCol, kjonnCol, jobberfCol, fodtSelCol, utdanningCol,
            lonnkravCol, refNavnCol, refNrCol, jobbkategoriCol;
    @FXML
    private DatePicker fodselsDato;
    @FXML
    private ComboBox kategori, utdanning;
    @FXML
    private TextArea txtError;
    @FXML
    private RadioButton annet, kvinne, mann;
    @FXML
    private TextArea jobberfaring;
    @FXML
    private TableView<Object> jobbsokerTable;
    @FXML
    private BorderPane idPane;
    @FXML
    private ProgressBar idProgressBar;
    @FXML
    private Button btnDelete, btnUpdate, btnRegister;

    //Oppdaterer den markerte raden
    @FXML
    public void onActionUpdate(ActionEvent actionEvent) {
        try {
            //Sjekker valideringen for feltene
            if (validation().isEmpty()) {
                //Setter den markerte lista på den tidligere markerte indeksen
                jobbsokerList.set(indeks, getDataFraJobbsoker());
                setValueToNull();
            } else {
                //Feilmeldingen kommer her
                txtError.setText(validation());
            }
        } catch (NullPointerException e) {
            alert("Du kan ikke oppdatere  | årsak: Nullverdier i feltene");
        }
    }

    @FXML
    public void onActionRegister(ActionEvent actionEvent) {
        try {
            //Sjekker valideringen for feltene
            if (validation().isEmpty()) {
                //Sjekker om det ikke eksisterer samme objekt for å unngå duplikate elementer.
                if (!jobbsokerList.contains(getDataFraJobbsoker())) {
                    jobbsokerList.add(getDataFraJobbsoker());
                } else {
                    alert("Jobbsøkeren er registrert tidligere");
                }
                //setter den oppdaterte listen i tabellen
                jobbsokerTable.setItems(jobbsokerList);
                setValueToNull();
            } else {
                //her kommer regex feilmeldingen ut.
                txtError.setText(validation());
            }

        } catch (NullPointerException e) {
            //Regex sjekker for isEmpty og ikke nullpekere, Derfor fanger vi opp nullpekerne her.
            alert("Du kan ikke registrere  | årsak: Nullverdier i feltene");
        }
    }


    //Henter ut raden som er markert og sletter den fra tabellen.
    @FXML
    public void onActionDelete(ActionEvent actionEvent) {
        try {
            jobbsokerTable.getItems().remove(indeks);
            //Nullstiller alle felt.
            setValueToNull();
            indeks = -1;
        } catch (IndexOutOfBoundsException e) {
            // Dersom ingenting er markert. settes indeksen tilbake til standarverdien som er -1.
            alert("Du kan ikke slette en rad du ikke har markert.");
        }
    }

    //Leser fra en bestemt fil i en egen tråd.
    @FXML
    public void onActionChooseFile(ActionEvent actionEvent) {
        // når saveToFile er false, vil filechooseren fungere som en "Showinputdialog" og ikke "SaveinputDialog"
        String path = FileChooserHandler.saveToFile(false);
        if (path != null) {
            ExecutorService service = Executors.newSingleThreadExecutor();
            //counter 1 definerer at dette er en jobbsøker
            Task<Void> task = new ChooseFileThread(this::threadDone, this::threadFailed, path, 1);
            //Deaktiverer knappene for å ikke kunne registrere,oppdatere eller slette når tråden kjøres.
            super.bindProgressBar(task, idProgressBar, btnDelete, btnRegister, btnUpdate);
            service.execute(task);
        }
    }

    //Kjøres hvis tråden lykkes
    private void threadDone() {
        super.threadDone(btnDelete, btnRegister, btnUpdate, idProgressBar, jobbsokerTable, jobbsokerList);
    }

    //Kjøres hvis tråden feiles
    private void threadFailed() {
        super.threadFailed(idProgressBar, btnUpdate, btnRegister, btnDelete);
    }


    //Lagrer den statiske listen til fil
    @FXML
    public void onActionSaveToNewFile(ActionEvent actionEvent) {
        try {
            String path = FileChooserHandler.saveToFile(true);
            if (path != null) {
                writer = FileChooserHandler.decideFileWriter(path);
                //lagrer til bestemt fil
                writer.writeToFile(jobbsokerList, path);
                //Lagrer til intern fil
                writer.writeToFile(jobbsokerList, "./src\\resource\\CSV_files\\Jobbsøker.csv");
                jobbsokerPath = path;
            }
        } catch (IOException e) {
            alert(e.getMessage());
        }
    }

    public void getSelected() {
        try {
            // setter indeksen til den markerte raden.
            indeks = jobbsokerTable.getSelectionModel().getSelectedIndex();
            // Sjekker tabellens markerte items og henter dem ut i form av et jobbsøker objekt
            if (jobbsokerTable.getSelectionModel().getSelectedItem() != null) {
                Object selectedJobbsoker = jobbsokerTable.getSelectionModel().getSelectedItem();
                Jobbsoker jobbsoker = (Jobbsoker) selectedJobbsoker;
                setPersonSelected(jobbsoker, fornavn, etternavn, adresse, postSted, postNummer, telefonNummer);
                refTlf.setText(jobbsoker.getReferanseTlf());
                refNavn.setText(jobbsoker.getReferanseNavn());
                jobberfaring.setText(jobbsoker.getJobberfaring());
                utdanning.setValue(jobbsoker.getUtdanning());
                kategori.setValue(jobbsoker.getJobbKategori());
                lonnKrav.setText(jobbsoker.getLonnskrav());
                setSelectedRadio(jobbsoker.getKjonn(), mann, kvinne, annet);
                fodselsDato.setValue(LocalDate.parse(jobbsoker.getFodselsDato()));
            }
        } catch (NullPointerException e) {
            // Fanger et unntak hvis enkelte elementer i objektet har nulllpekere i seg
            alert("Dataene du velget er ikke omfattende");
        }
    }

    //Nullstiller alle komponentene
    public void setValueToNull() {
        setTextToNull(fornavn, etternavn, adresse, lonnKrav, postNummer,
                telefonNummer, postSted, refNavn, refTlf, jobberfaring);
        setComboToNull(kategori, utdanning);
        setDateToNull(fodselsDato);
        txtError.setText(null);
    }

    //Validerer inputfeltene og returnerer feilmelding
    public String validation() {
        return TxtValidation.validateAllInJobbsoker(fornavn.getText(), etternavn.getText(),
                adresse.getText(), postNummer.getText(), postSted.getText(), telefonNummer.getText(),
                lonnKrav.getText(), refNavn.getText(), refTlf.getText(), kategori.getValue(),
                utdanning.getValue(), fodselsDato.getValue(), jobberfaring.getText());
    }

    //Lager et object av feltene som er registrert
    public Object getDataFraJobbsoker() {
        String kjonn = getSelectedRadio(annet, mann, kvinne);
        return new Jobbsoker(fornavn.getText(), etternavn.getText(), adresse.getText(), postSted.getText(),
                postNummer.getText(), telefonNummer.getText(), kjonn, jobberfaring.getText(),
                fodselsDato.getValue().toString(), utdanning.getValue().toString(), lonnKrav.getText(),
                refNavn.getText(), refTlf.getText(), kategori.getValue().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Henter ut combobox verdier som er lagret i egne filer internt i applikasjonen.
        super.generateLists();
        searchInTable(search, jobbsokerTable, jobbsokerList);
        idProgressBar.setProgress(0.0);

        //Setter propertyvalue til alle kolonnene lik attributtene i Arbeidsgiver klassen
        setPersonValue(fornavnCol, etternavnCol, adresseCol, poststedcol, postNrCol, tlfCol);
        setJobbsokerValue(kjonnCol, jobberfCol, fodtSelCol, utdanningCol,
                lonnkravCol, refNavnCol, refNrCol, jobbkategoriCol);
        //Setter inn verdier i nedtrekkslistene
        kategori.setItems(super.kategoriList);
        utdanning.setItems(super.utdanningList);
        //Legger til Listen i tabellen.
        jobbsokerTable.setItems(jobbsokerList);


    }


}

