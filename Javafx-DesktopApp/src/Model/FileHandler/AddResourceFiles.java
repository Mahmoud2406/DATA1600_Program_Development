package Model.FileHandler;


import Model.Classes.Arbeidsgiver;
import Model.FileHandler.CsvFileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddResourceFiles {
    // Henter ut nedtrekkslistene internt i applikasjonen
    public static ObservableList<Object> addItems(String path) throws Exception {
        CsvFileReader read = new CsvFileReader();
        ObservableList<Object> oListKategori;
        oListKategori = read.readFile(path);
        return oListKategori;
    }

    // Henter ut Arbeidsgivernavnene internt i applikasjonen, deretter settes de i en liste av type String
    public static ObservableList<String> addItemsObjectName(String path) throws Exception {
        ObservableList<Object> oList = addItems(path);
        ObservableList<String> arbeidsgiverNameList = FXCollections.observableArrayList();
        for (Object objName : oList) {
            if (objName instanceof Arbeidsgiver) {
                arbeidsgiverNameList.add(((Arbeidsgiver) objName).getJurdiskNavn());
            }
        }
        return arbeidsgiverNameList;
    }


}
