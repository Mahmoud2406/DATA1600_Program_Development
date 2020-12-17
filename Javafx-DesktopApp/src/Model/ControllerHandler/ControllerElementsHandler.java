package Model.ControllerHandler;

import Model.Classes.OpptatteVikarer;
import Model.Classes.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerElementsHandler {
    // Setter radioknappen til den tilsvarende markerte raden
    public static void setSelectedRadio(String obj, RadioButton... radio) {
        for (RadioButton btnRadio : radio) {
            if (obj.equals(btnRadio.getText())) {
                btnRadio.setSelected(true);
            }
        }
    }
    // Henter ut den markerte radoknappen
    public static String getSelectedRadio(RadioButton... radio) {
        for (RadioButton btnRadio : radio) {
            if (btnRadio.isSelected()) {
                return btnRadio.getText();
            }
        }
        return null;
    }

    //Setter Person verdier inn i inputfeltene ved markering av rad.
    public static void setPersonSelected(Person person, TextInputControl... txt) {
        txt[0].setText(person.getFornavn());
        txt[1].setText(person.getEtternavn());
        txt[2].setText(person.getAdresse());
        txt[3].setText(person.getPostSted());
        txt[4].setText(person.getPostNummer());
        txt[5].setText(person.getTelefonNummer());
    }

    //Søke metoden som oppdateres etter hver tatseklikk
    public static void searchInTable(TextField search, TableView table, ObservableList<Object> list) {
        search.textProperty().addListener(observable -> {
            //Hvis søkefeltet er tomt, settes den ordinære listen inn i tabellen.
            if (search.textProperty().get().isEmpty()) {
                table.setItems(list);
                return;
            }
            ObservableList<Object> items = FXCollections.observableArrayList();
            //Henter ut colonnene i raden i form av Wildcards
            ObservableList<TableColumn<Object, ?>> column = table.getColumns();
            // iterator som går gjennom enhver kolonne og rad.
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < column.size(); j++) {
                    TableColumn colVar = column.get(j);
                    String cellValue = colVar.getCellData(list.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    // Dersom inputverdien inneholder noe eller hele
                    // stringen til en cell, vil hele raden settes inn i listen
                    if (cellValue.contains(search.getText().toLowerCase()) &&
                            cellValue.startsWith(search.getText().toLowerCase())) {
                        items.add(list.get(i));
                        break;
                    }
                }
            }
            // Setter listen inn i tabellen
            table.setItems(items);
        });
    }

    //Henter utvalgte attributter fra jobbsøker og vikariat datafeltene i Opptattevikariater.
    //Kobler disse til definerte kolonner
    public static void setOpptatteVikariaterValue(TableColumn<OpptatteVikarer, String>... obj) {
        obj[0].setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getJobbsoker().getFornavn()));
        obj[1].setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getJobbsoker().getEtternavn()));
        obj[2].setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVikariat().getArbeidsGiverNavn()));
        obj[3].setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVikariat().getJobbKategori()));
        obj[4].setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVikariat().getStillingsNavn()));
    }

    //Setter arbeidsgiver propertyvalue til kolonnene.
    public static void setArbeidsgiverValue(TableColumn<Object, String>... obj) {
        obj[0].setCellValueFactory(new PropertyValueFactory<>("jurdiskNavn"));
        obj[1].setCellValueFactory(new PropertyValueFactory<>("bransje"));
        obj[2].setCellValueFactory(new PropertyValueFactory<>("selskapsform"));
        obj[3].setCellValueFactory(new PropertyValueFactory<>("email"));
        obj[4].setCellValueFactory(new PropertyValueFactory<>("sektor"));
    }
    //Setter vikariat propertyvalue til kolonnene.
    public static void setVikariatValue(TableColumn<Object, String>... obj) {
        obj[0].setCellValueFactory(new PropertyValueFactory<>("stillingsNavn"));
        obj[1].setCellValueFactory(new PropertyValueFactory<>("arbeidsGiverNavn"));
        obj[2].setCellValueFactory(new PropertyValueFactory<>("utdanning"));
        obj[3].setCellValueFactory(new PropertyValueFactory<>("jobbKategori"));
        obj[4].setCellValueFactory(new PropertyValueFactory<>("stillingType"));
        obj[5].setCellValueFactory(new PropertyValueFactory<>("arbeidsOppgaver"));
        obj[6].setCellValueFactory(new PropertyValueFactory<>("kvalifikasjoner"));
        obj[7].setCellValueFactory(new PropertyValueFactory<>("arbeidsSted"));
        obj[8].setCellValueFactory(new PropertyValueFactory<>("arbeidStid"));
        obj[9].setCellValueFactory(new PropertyValueFactory<>("varighet"));
        obj[10].setCellValueFactory(new PropertyValueFactory<>("lønnsNivaa"));
        obj[11].setCellValueFactory(new PropertyValueFactory<>("stillingBeskrivelse"));


    }

    //Nullstiller alle inputfelt.
    public static void setTextToNull(TextInputControl... obj) {
        for (TextInputControl objects : obj) {
            objects.setText(null);
        }
    }
    //Nullstiller alle nedtrekslister
    public static void setComboToNull(ComboBox... obj) {
        for (ComboBox<Object> objects : obj) {
            objects.setValue(null);
        }
    }
    //Nullstiller Datepicker objektet
    public static void setDateToNull(DatePicker date) {
            date.setValue(null);
    }
    //Setter person propertyvalue til kolonnene.
    public static void setPersonValue(TableColumn<Object, String>... obj) {
        obj[0].setCellValueFactory(new PropertyValueFactory<>("fornavn"));
        obj[1].setCellValueFactory(new PropertyValueFactory<>("etternavn"));
        obj[2].setCellValueFactory(new PropertyValueFactory<>("adresse"));
        obj[3].setCellValueFactory(new PropertyValueFactory<>("postSted"));
        obj[4].setCellValueFactory(new PropertyValueFactory<>("postNummer"));
        obj[5].setCellValueFactory(new PropertyValueFactory<>("telefonNummer"));

    }

    //Setter jobbsøker propertyvalue til kolonnene.
    public static void setJobbsokerValue(TableColumn<Object, String>... obj) {
        obj[0].setCellValueFactory(new PropertyValueFactory<>("kjonn"));
        obj[1].setCellValueFactory(new PropertyValueFactory<>("Jobberfaring"));
        obj[2].setCellValueFactory(new PropertyValueFactory<>("fodselsDato"));
        obj[3].setCellValueFactory(new PropertyValueFactory<>("utdanning"));
        obj[4].setCellValueFactory(new PropertyValueFactory<>("lonnskrav"));
        obj[5].setCellValueFactory(new PropertyValueFactory<>("referanseNavn"));
        obj[6].setCellValueFactory(new PropertyValueFactory<>("referanseTlf"));
        obj[7].setCellValueFactory(new PropertyValueFactory<>("jobbKategori"));

    }
}
