package Model.FileHandler;

import Model.Classes.Arbeidsgiver;
import Model.Classes.Jobbsoker;
import Model.Classes.OpptatteVikarer;
import Model.Classes.Vikariater;
import Model.Exceptions.*;
import Model.Validation.TxtValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static Model.ControllerHandler.AlertHandler.alert;

public class CsvFileReader implements Filereader {
    //Overskriver lesemetoden
    @Override
    public ObservableList<Object> readFile(String path) throws Exception {
        ObservableList<Object> list = FXCollections.observableArrayList();
        BufferedReader reader = null;
        File file = new File(path);
        //Setter en teller for å hoppe over første linjen. (Dette gjøres fordi ved lagring til
        // fil, skrives det en BOM med utf-16 encoding for Excel
        int count = 0;
        //Hvi filen eksisterer skal den gjøre følgende
        if (file.exists()) {
            reader = Files.newBufferedReader(Paths.get(path));
            String line;
            //Så lenge linjen som leses ikke er lik null, legges linjen til i listen.
            while ((line = reader.readLine()) != null) {
                count++;
                //Hopper over første linje
                if (count == 1) {
                    continue;
                }
                list.add(parseObject(line));
            }
        }
        //Lukker leseren på siste linje
        if (reader != null) {
            reader.close();
        }
        return list;
    }

    public Object parseObject(String line) throws Exception {
        String validate;
        Object obj;
        //Lager et array av linjen splittet ved semikolon
        String[] split = line.split(";");
        //Hvis arrayets lengde er 11, lages det et objekt av arbeidsgiver
        if (split.length == 11) {
            //Validerer innlesningen fra fil
            validate = TxtValidation.validateAllinArbeidsgiver(split[6], split[0], split[1], split[2], split[4],
                    split[3], split[5], split[9], split[7]);
            obj = new Arbeidsgiver(split[0], split[1], split[2], split[3],
                    split[4], split[5], split[6], split[7], split[8], split[9], split[10]);
            if (!validate.isEmpty()) {
                throw new InvalidArbeidsgiverException("Feil i arbeidsgiver filen, dataene " +
                        "tilfredstiller ikke valideringskravene");
            }
            //Hvis arrayets lengde er 14, lages det et objekt av jobbsøker
        } else if (split.length == 14) {
            //Validerer innlesningen fra fil
            validate = TxtValidation.validateAllInJobbsoker(split[0], split[1], split[2], split[4],
                    split[3], split[5], split[10], split[11], split[12], split[9], split[13], split[8], split[7]);
            obj = new Jobbsoker(split[0], split[1], split[2], split[3],
                    split[4], split[5], split[6], split[7], split[8],
                    split[9], split[10], split[11], split[12], split[13]);
            if (!validate.isEmpty()) {
                throw new InvalidJobbsokerException("Feil i jobbsøker filen, dataene " +
                        "tilfredstiller ikke valideringskravene");
            }
            //Hvis arrayets lengde er 12, lages det et objekt av vikariater
        } else if (split.length == 12) {
            //Validerer innlesningen fra fil
            validate = TxtValidation.validateAllinVikariater(split[0], split[7], split[8], split[9], split[10]);
            obj = new Vikariater(split[0], split[1], split[2], split[3], split[4],
                    split[5], split[6], split[7], split[8], split[9], split[10], split[11]);
            if (!validate.isEmpty()) {
                throw new InvalidVikariaterException("Feil i vikariater filen, dataene " +
                        "tilfredstiller ikke valideringskravene");
            }
            //Hvis arrayets lengde er 26, lages det et objekt av OpptatteVikarer
        } else if (split.length == 26) {
            //Validerer innlesningen fra fil.
            validate = TxtValidation.validateAllInJobbsoker(split[0], split[1], split[2], split[4],
                    split[3], split[5], split[10], split[11], split[12], split[9], split[13], split[8], split[7]);
            validate += TxtValidation.validateAllinVikariater(split[14], split[21], split[22], split[23], split[24]);
            obj = new OpptatteVikarer(new Jobbsoker(split[0], split[1], split[2], split[3],
                    split[4], split[5], split[6], split[7], split[8],
                    (split[9]), split[10], split[11], split[12], split[13]),
                    new Vikariater(split[14], split[15], split[16], split[17],
                            split[18], split[19], split[20], split[21], split[22],
                            split[23], split[24], split[25]));
            if (!validate.isEmpty()) {
                throw new InvalidOpptatteVikariaterException("Feil i Opptattevikariater filen, dataene " +
                        "tilfredstiller ikke valideringskravene");
            }
            // Hvis linjen avsluttes med semikolon betyr dette at filen kommer fra applikasjonen
        } else if (line.substring(line.length() - 1).equals(";")) {
            // Fjerner semikolon fra linjen
            return line.substring(0, line.length() - 1);
        } else {
            // Kaaster exception ved feil format som ikke er egnet til applikasjonen.
            throw new InvalidFileFormatException("Dette filformatet støttes ikke av applikasjonen");
        }
        assert (obj != null);
        return obj;

    }


}
