package Model.Threads;

import Model.Classes.Arbeidsgiver;
import Model.Classes.Jobbsoker;
import Model.Classes.Vikariater;
import Model.Exceptions.InvalidFileFormatException;
import Model.FileHandler.FileChooserHandler;
import Model.FileHandler.Filereader;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.IOException;

import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.alert;


public class ChooseFileThread extends Task<Void> {
    // Kjøres hvis tråden lykkes
    private Runnable runMeWhenDone;
    // Kjøres hvis tråden feiles
    private Runnable runMeWhenFailed;

    String path;
    public ObservableList<Object> list;
    int counter;

    // Konstruktør som tar inn filpathen og to Runnable objekter, i tillegg til en teller
    // Som definerer hvilken liste som skal lagres på
    public ChooseFileThread(Runnable doneFunc, Runnable failedFunc, String path, int counter) {
        this.runMeWhenDone = doneFunc;
        this.runMeWhenFailed = failedFunc;
        this.path = path;
        this.counter = counter;
    }

    @Override
    public Void call() throws Exception {
        Filereader reader = FileChooserHandler.decideFileReader(path);
        list = reader.readFile(path);
        // Går gjennom objektene i listen og finner hvilken type den er av
        for (Object obj : list) {
            // dersom telleren som er valgt er 1 og objektene er av type jobbsøker
            if (counter == 1 && obj instanceof Jobbsoker) {
                // Lagre listen til den statiske jobbsøkerlisten
                jobbsokerList = list;
                // går ut av forløkka
                break;
                // dersom telleren som er valgt er 2 og objektene er av type Arbeidsgiver
            } else if (counter == 2 && obj instanceof Arbeidsgiver) {
                // Lagre listen til den statiske arbeidsgiverListen
                arbeidsgiverList = list;
                break;
                // dersom telleren som er valgt er 3 og objektene er av type Vikariater
            } else if (counter == 3 && obj instanceof Vikariater) {
                // Lagre listen til den statiske vikariaterListen
                vikariaterList = list;
                break;
            } else {
                // Dersom listen ikke er av disse typene nevnt ovenfor skal det kastes et unntak.
                throw new InvalidFileFormatException("Denne filen støttes ikke på denne fanen, Men i en annen fane");
            }
        }
        // Får tråden til å sove i 5 sekunder for testing
        Thread.sleep(5000);
        return null;
    }

    @Override
    protected void succeeded() {
        runMeWhenDone.run();
    }

    @Override
    protected void failed() {
        runMeWhenFailed.run();
        // Fanger opp exception i dialogboks
        alert(this.getException().getMessage());
    }
}
