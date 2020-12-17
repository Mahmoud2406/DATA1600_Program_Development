package Model.FileHandler;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface Filereader {
    ObservableList<Object> readFile(String path) throws IOException, Exception;
}
