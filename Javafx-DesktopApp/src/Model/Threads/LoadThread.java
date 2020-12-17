package Model.Threads;

import Model.FileHandler.CsvFileReader;
import Model.FileHandler.Filereader;
import javafx.concurrent.Task;

import static Controller.MenyBar_Controller.*;
import static Model.ControllerHandler.AlertHandler.alert;

public class LoadThread extends Task<Void> {
    // Kjøres når tråden lykkes
    private Runnable runMeWhenDone;
    public LoadThread(Runnable run) {
    this.runMeWhenDone = run;
    }


    @Override
    protected Void call() throws Exception {
        Filereader reader = new CsvFileReader();
        // Leser fra intern fil og lagrer på statiske lister som ligger i MenyBar_Contorller
        jobbsokerList = reader.readFile("./src\\resource\\CSV_files\\Jobbsøker.csv");
        arbeidsgiverList = reader.readFile("./src\\resource\\CSV_files\\Arbeidsgiver.csv");
        vikariaterList = reader.readFile("./src\\resource\\CSV_files\\Vikariater.csv");
        opptatteVikariatList = reader.readFile("./src\\resource\\CSV_files\\OpptatteVikariater.csv");
        // Fanger opp exception i dialogboks
        Thread.sleep(5000);
        return null;
    }

    @Override
    protected void succeeded() {
        this.runMeWhenDone.run();
    }
    @Override
    protected void failed() {
        alert(this.getException().getMessage());
    }

}