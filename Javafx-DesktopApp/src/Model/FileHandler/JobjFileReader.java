package Model.FileHandler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class JobjFileReader implements Filereader {

    @Override
    public ObservableList<Object> readFile(String path) throws IOException, ClassNotFoundException {
        List<Object> list;
        ObservableList<Object> oList;
        File file = new File(path);
        // Dersom filen eksisterer gjøres følgende
        if (file.exists()) {
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                // caster listen til et Arraylist fordi serialisering ikke støøtter Observible List
                list = (ArrayList<Object>) ois.readObject();
                // Parser dermed arraylist til et observible list
                oList = FXCollections.observableList(list);
                ois.close();
                fis.close();
                // Returnerer observible listen
                return oList;
        }
        // Kaster exception dersom filen ikke eksisterer
        else {
            throw new IOException("Denne filen eksisterer ikke.");
        }
    }
}

