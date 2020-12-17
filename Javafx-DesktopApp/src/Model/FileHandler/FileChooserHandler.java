package Model.FileHandler;


import Model.FileHandler.*;
import Model.Main;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;

public class FileChooserHandler {

    // Velger hvilken writer basert på pathen
    public static Filewriter decideFileWriter(String path) {
        if (path != null) {
            // Sjekker om getFileExtension metoden returnerer csv
            if (getFileExtension(path).equals("csv")) {
                return new CsvFileWriter();
                // Hvis ikke returner new Jobjwriter
            } else {
                return new JobjFileWriter();
            }
            //Filechooseren gjør det ikke mulig å velge noe annet enn csv eller jobj
        }
        return null;
    }
    // Velger hvilken reader basert på pathen
    public static Filereader decideFileReader(String path) {
        if (path != null) {
            // Sjekker om getFileExtension metoden returnerer csv
            if (getFileExtension(path).equals("csv")) {
                return new CsvFileReader();
            } else {
                // Hvis ikke returner new Jobjreader
                return new JobjFileReader();
            }
            //Filechooseren gjør det ikke mulig å velge noe annet enn csv eller jobj
        }
        return null;
    }

    public static String saveToFile(boolean newfile)  {
        FileChooser fileChooser = new FileChooser();
        //Begrenser filformatet til kun csv og jobj
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Csv Files", "*.csv"),
                new FileChooser.ExtensionFilter("Jobj Files", "*.jobj"));
        File file;
        // Dersom newfile er true, vises save dialog vinduet
        if (newfile) {
            file = fileChooser.showSaveDialog(Main.getScene().getWindow());
            // Dersom newfile er false, vises open dialog vinduet
        } else {
            file = fileChooser.showOpenDialog(Main.getScene().getWindow());

        }
        // Dersom filen ikke finnes returner null
        if (file == null) {
            return null;
        }
        //Returnerer pathen til filen
        return file.getPath();
    }

    // Finner hvilke type format filen har
    public static String getFileExtension(String path) {
        if (path != null) {
            String fileName = new File(path).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }
        return null;
    }
}
