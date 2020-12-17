package Model.FileHandler;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface
Filewriter {
    void writeToFile(ObservableList<Object> write, String path) throws IOException;

}
