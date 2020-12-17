package Controller;
import Model.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenyBar_Controller implements Initializable {
    // referanse fra den primære stagen til Main.
    Stage stage;
    Parent root;
    //Siden denne controlleren alltid vil eksistere på toppen av guiene i form av menybar, kan vi
    // Lagre informasjonen til alle tabellene, Dette gjør at vi kan navigere i brukergrensesnittet uten
    // å miste noe data fra tabellene.
    public static ObservableList<Object> jobbsokerList = FXCollections.observableArrayList();
    public static ObservableList<Object> arbeidsgiverList = FXCollections.observableArrayList();
    public static ObservableList<Object> vikariaterList = FXCollections.observableArrayList();
    public static ObservableList<Object> opptatteVikariatList = FXCollections.observableArrayList();
    public static String jobbsokerPath;
    public static String vikariaterPath;

    @FXML
    private Button btnVikar, btnAgiver, btnVikariater, btnHjem;

    @FXML
    private void onAction(ActionEvent event) throws IOException {
        //Finner stagen til menycontrolleren.
        stage = (Stage) btnHjem.getScene().getWindow();
        //Dersom det trykkes på en av knappene, vil root endre FXML loaderen sin til angitt resource
        if (event.getSource() == btnHjem) {
            root = FXMLLoader.load(getClass().getResource("/resource/Main_view.fxml"));
        }
        if (event.getSource() == btnAgiver) {
            root = FXMLLoader.load(getClass().getResource("/resource/Arbeidsgiver_view.fxml"));
        }
        if (event.getSource() == btnVikar) {
            root = FXMLLoader.load(getClass().getResource("/resource/Jobbsoker_view.fxml"));
        }
        if (event.getSource() == btnVikariater) {
            root = FXMLLoader.load(getClass().getResource("/resource/Vikariater_view.fxml"));
        }
        //Henter ut scene fra Main
        Scene scene = Main.getScene();
        //Setter root til scene
        scene.setRoot(root);
        //legger til stylesheet filen i scenen.
        scene.getStylesheets().add(getClass().getResource("/Model/stylesheet.css").toExternalForm());
        //setter ny scene til stagen
        stage.setScene(scene);
        //Viser den nye stagen
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
