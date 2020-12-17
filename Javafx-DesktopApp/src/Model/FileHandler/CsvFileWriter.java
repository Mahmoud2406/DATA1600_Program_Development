package Model.FileHandler;

import javafx.collections.ObservableList;

import java.io.*;

public class CsvFileWriter implements Filewriter {
    @Override
    public void writeToFile(ObservableList<Object> list, String path) throws IOException {
        PrintWriter writer = null;
        File file = new File(path);
        if (file != null) {
            writer = new PrintWriter(path);
            // Viser til utf-16 encoding ved åpning via excel, dette resulterer til at første linje av
            // csv filen vil være tom. Vi valgte å løse dette på den måten for at Excel skal forstå encodingen til
            // Csv filen. vi bruker dermed denne char'en som kommer først i streamen
            char CSV_BOM = '\uFEFF';
            writer.println(CSV_BOM);
            for (Object obj : list) {
                writer.println(obj);
            }
        } else {
            throw new IOException("Filen eksisterer ikke");
        }
        if (writer != null) {
            writer.close();
        }
    }
}
