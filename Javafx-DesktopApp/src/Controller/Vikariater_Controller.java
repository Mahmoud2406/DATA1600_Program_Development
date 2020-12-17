package Controller;

import Model.Classes.*;
import Model.FileHandler.FileChooserHandler;
import Model.Threads.ChooseFileThread;
import Model.Validation.TxtValidation;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static Model.ControllerHandler.ControllerElementsHandler.*;
import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.alert;

public class Vikariater_Controller extends Super_Controller {
    //Vikariat kolonner
    @FXML
    public TableColumn<Object, String> stillingsnavnCol, arbeidsgiverCol, utdanningCol,
            jobbkategoriCol, stillingstypeCol,
            oppgaverCol, kvalifikasjonerCol,
            arbeidsstedCol, arbeidsstidCol,
            varighetCol, lonnsnivaCol,
            stillingsbeskrivelseCol;
    //Inputfelt
    @FXML
    private TextField stillingsNavn, arbeidsSted, lonnNivo, varighet, arbeidsTid, search;
    //nedtrekkslister
    @FXML
    private ComboBox<Object> kategori, utdanning;
    //nedtrekksliste
    @FXML
    private ComboBox<String> velgArbeidsGiver;
    //Stillingstype
    @FXML
    private RadioButton engasjement, fast;
    //Tekstinputfelt for beskrivelser
    @FXML
    private TextArea kvalifikasjoner, arbeidsOppgaver, stillingsBeskrivelse;
    //Regex feilmelding her.
    @FXML
    private TextArea txtError;
    //Knapper
    @FXML
    private Button btnRegister,btnUpdate,btnDelete;
    //Tråd progressbaren
    @FXML
    private ProgressBar idProgressBar;
    //Vikattabellen
    @FXML
    public TableView<Object> vikariatTable;
    //Oppdaterer den markerte raden i tabellen
    @FXML
    public void onActionUpdate(ActionEvent actionEvent) {
        try {
            if (validation().isEmpty()) {
                vikariaterList.set(indeks, getDataVikariat());
                setValueToNull();
            } else {
                txtError.setText(validation());
            }
        } catch (NullPointerException e) {
            alert("Du kan ikke oppdatere  | årsak: Nullverdier i feltene");
        }
    }
    //Registrerer et vikariat
    @FXML
    public void onActionRegister(ActionEvent actionEvent) {
        try {
            //vi har ikke brukt filtrering for duplikate med vikariater
            //fordi det kan hende at samme arbedisgiveren har flere av samme vikariater
            if (validation().isEmpty()) {
                vikariaterList.add(getDataVikariat());
                //Setter den oppdaterte lista i tabellen
                vikariatTable.setItems(vikariaterList);
            } else {
                //Skriver ut regex feilmeldingen
                txtError.setText(validation());
            }
        } catch (NullPointerException e) {
            //Regex sjekker for isEmpty og ikke nullpekere, Derfor fanger vi opp nullpekerne her.
            alert("Du kan ikke registrere | årsak: Nullverdier i feltene");
        }
    }

    //Sletter markerte raden
    @FXML
    public void onActionDelete(ActionEvent actionEvent) {
        try {
            vikariatTable.getItems().remove(indeks);
            setValueToNull();
            indeks = -1;
        } catch (IndexOutOfBoundsException e) {
            alert("Du kan ikke slette en rad du ikke har markert.");
        }
    }
    //Velger en fil og kjører innlesningen i en ny tråd.
    @FXML
    public void onActionChooseFile(ActionEvent actionEvent)  {
        String path = null;
            path = FileChooserHandler.saveToFile(false);
            if(path !=null) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                Task<Void> task = new ChooseFileThread(this::threadDone,this::threadFailed, path, 3);
                super.bindProgressBar(task,idProgressBar,btnDelete,btnRegister,btnUpdate);
                service.execute(task);
            }
    }
    //Kjøres hvis tråden lykkes
    private void threadDone() {
        super.threadDone(btnDelete,btnRegister,btnUpdate,idProgressBar,vikariatTable,vikariaterList);
    }
    //Kjøres hvis tråden feiler
    private void threadFailed(){
        super.threadFailed(idProgressBar,btnUpdate,btnRegister,btnDelete);
    }

    //Lagrer data til fil.
    @FXML
    public void onActionSaveToNewFile(ActionEvent actionEvent) {
        try {
            String path = FileChooserHandler.saveToFile(true);
            if (path != null) {
                writer = FileChooserHandler.decideFileWriter(path);
                //lagrer fil valgt av brukeren.
                writer.writeToFile(vikariaterList, path);
                //lagrer fil internt i applikasjonen.
                writer.writeToFile(vikariaterList, "./src\\resource\\CSV_files\\Vikariater.csv");
                vikariaterPath = path;
            }
        } catch (IOException e) {
            alert(e.getMessage());
        }
    }

    @FXML
    public void getSelected() {
        try {
            // setter indeksen til den markerte raden.
            indeks = vikariatTable.getSelectionModel().getSelectedIndex();
            // Sjekker tabellens markerte items og henter dem ut i form et vikariat objekt
            if (vikariatTable.getSelectionModel().getSelectedItem() != null) {
                Object selectedVikariater = vikariatTable.getSelectionModel().getSelectedItem();
                Vikariater vikariater = (Vikariater) selectedVikariater;
                stillingsNavn.setText(vikariater.getStillingsNavn());
                velgArbeidsGiver.setValue(vikariater.getArbeidsGiverNavn());
                utdanning.setValue(vikariater.getUtdanning());
                kategori.setValue(vikariater.getJobbKategori());
                arbeidsOppgaver.setText(vikariater.getArbeidsOppgaver());
                kvalifikasjoner.setText(vikariater.getKvalifikasjoner());
                arbeidsSted.setText(vikariater.getArbeidsSted());
                arbeidsTid.setText(vikariater.getArbeidStid());
                varighet.setText(vikariater.getVarighet());
                lonnNivo.setText(vikariater.getLønnsNivaa());
                stillingsBeskrivelse.setText(vikariater.getStillingBeskrivelse());
                setSelectedRadio(vikariater.getStillingType(), fast, engasjement);
            }
        } catch (NullPointerException e) {
            // Fanger et unntak hvis enkelte elementer i objektet har nulllpekere i seg
            alert("Dataene du velget er ikke omfattende");
        }
    }

    //Nullstiller alle komponentene
    public void setValueToNull() {
        setTextToNull(stillingsNavn, arbeidsSted, lonnNivo, varighet,
                arbeidsTid, kvalifikasjoner, arbeidsOppgaver, stillingsBeskrivelse);
        setComboToNull(kategori, utdanning, velgArbeidsGiver);
        txtError.setText(null);
    }

    //Validerer inputfeltene og returnerer feilmelding
    public String validation() {
        return TxtValidation.validateAllinVikariater(stillingsNavn.getText(), arbeidsSted.getText(),
                arbeidsTid.getText(), varighet.getText(), lonnNivo.getText());

    }

    //Lager et object av feltene som er registrert
    public Object getDataVikariat() {
        String stillingsType = getSelectedRadio(fast, engasjement);
        return new Vikariater(stillingsNavn.getText(), velgArbeidsGiver.getValue(),
                utdanning.getValue().toString(), kategori.getValue().toString(), stillingsType,
                arbeidsOppgaver.getText(), kvalifikasjoner.getText(), arbeidsSted.getText(),
                arbeidsTid.getText(), varighet.getText(), lonnNivo.getText(),
                stillingsBeskrivelse.getText());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Henter ut combobox verdier som er lagret i egne filer internt i applikasjonen.
        super.generateLists();
        //kaller på søk metoden i ControllerElementsHandler
        searchInTable(search,vikariatTable,vikariaterList);
        //Setter propertyvalue til alle kolonnene lik attributtene i Vikariat klassen
        setVikariatValue(stillingsnavnCol, arbeidsgiverCol, utdanningCol, jobbkategoriCol, stillingstypeCol,
                oppgaverCol, kvalifikasjonerCol, arbeidsstedCol, arbeidsstidCol,
                varighetCol, lonnsnivaCol, stillingsbeskrivelseCol);
        //Setter inn verdier/Lister i nedtrekkslistene og vikariat tabellen
        kategori.setItems(super.kategoriList);
        utdanning.setItems(super.utdanningList);
        velgArbeidsGiver.setItems(super.arbeidsgiverNameList);
        vikariatTable.setItems(vikariaterList);

    }

}
