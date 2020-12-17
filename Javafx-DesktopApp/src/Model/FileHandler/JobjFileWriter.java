package Model.FileHandler;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class JobjFileWriter implements Filewriter {
    @Override
    public void writeToFile(ObservableList<Object> obLisr, String path) throws IOException {

            FileOutputStream fop = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fop);
        File file = new File(path);
        if(file != null) {
            // Siden observible lisr ikke støttes av valideringen, setter vi den i kopikonstruktøren
            // til et arraylist.
            oos.writeObject(new ArrayList<>(obLisr));
            oos.close();
            fop.flush();
            fop.close();
        }
        else {
            throw new IOException("Denne filen eksisterer ikke");
        }

    }
}
