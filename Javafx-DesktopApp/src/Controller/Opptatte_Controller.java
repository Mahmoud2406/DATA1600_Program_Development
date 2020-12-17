package Controller;

import Model.Classes.OpptatteVikarer;
import Model.FileHandler.CsvFileReader;
import Model.FileHandler.Filereader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import static Model.ControllerHandler.AlertHandler.alert;
import static Model.ControllerHandler.ControllerElementsHandler.*;
import java.net.URL;
import java.util.ResourceBundle;
import static Controller.MenyBar_Controller.opptatteVikariatList;

public class Opptatte_Controller extends Super_Controller {
Filereader filereader = new CsvFileReader();
    @FXML
    TableView<Object> opptatteTable;
    //Opptattevikariater kolonner
    @FXML
    private TableColumn<OpptatteVikarer, String>
            fornavnCol, // hentes fra jobbsøker
            etternavnCol, // hentes fra jobbsøker
            arbeidsgiverNavnCol, // hentes fra vikariat
            jobbKategoriCol, // hentes fra vikariat
            stillingsNavnCol; // hentes fra vikariat

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Kaller på den statiske metoden som setter property values til de ulike kolonnene.
        setOpptatteVikariaterValue(fornavnCol, etternavnCol, arbeidsgiverNavnCol, jobbKategoriCol, stillingsNavnCol);
        try {
            //leser inn filen til en liste.
            opptatteVikariatList = filereader.readFile("./src\\resource\\CSV_files\\OpptatteVikariater.csv");
        } catch (Exception e) {
            alert(e.getMessage());
        }
        //Setter inn listen i tabellen
        opptatteTable.setItems(opptatteVikariatList);
    }

}
