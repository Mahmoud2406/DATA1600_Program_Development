package Model;

import Controller.Main_Controller;
import Model.Threads.LoadThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Model.ControllerHandler.AlertHandler.alertExit;

public class Main extends Application {
    // Tar vare på scene-variabelen for endre på sider uten å danne en ny scene.
    // vi bruker metoden setRoot fra Scene for å unngå nye scener. Dette valgte vi å gjøre på den
    // måten for å unngå forskjellige størrelser på Guiene etter at klienten manuelt endrer på dette
    // og klikker seg videre til andre sider.
    private static Scene scene;
    public static Scene getScene() {
        return scene;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/Main_view.fxml"));
        Parent root = loader.load();
        Main_Controller myController = loader.getController();
        primaryStage.setTitle("Vikarbyrå");
        primaryStage.getIcons().add(new Image("resource/Icons/baricon.png"));
        scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        // Gjir en ekstra bekreftelse før lukking av program
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (alertExit("Husket å lagre filen før du avslutter?")) {
                Platform.exit();
            } else {
                e.consume();
            }
        });
        // Setter første innlesing ved kjøring av programmet i en egen tråd
        ExecutorService service = Executors.newSingleThreadExecutor();
        Task<Void> task = new LoadThread(myController::threadDone);
        service.execute(task);
        // Når tråden kjøres, kobles progressbaren til task sin progress
        // Og deaktiverer alle mulige funksjonelle knapper i GUIen
        myController.getIdProgressBar().progressProperty().bind(task.progressProperty());
        myController.btnOpptatte.setDisable(true);
        myController.btnRegister.setDisable(true);

    }










    public static void main(String[] args) throws Exception {
        launch(args);


    }
}
