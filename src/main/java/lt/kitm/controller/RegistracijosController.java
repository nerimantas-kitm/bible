package lt.kitm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.kitm.model.Vartotojas;
import lt.kitm.model.VartotojasDAO;
import lt.kitm.utils.Password;
import lt.kitm.utils.Validation;

import java.io.IOException;
import java.sql.SQLException;

public class RegistracijosController {
    @FXML
    private Label zinute;
    @FXML
    private TextField email;
    @FXML
    private TextField vardas;
    @FXML
    private TextField pavarde;
    @FXML
    private PasswordField slaptazodis1;
    @FXML
    private PasswordField slaptazodis2;


    public void onActionAtsaukti(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/prisijungimo_langas.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Bible");
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            this.zinute.setText("Klaida grįžtant į prisijungimo langą");
        }
    }

    public void onActionRegistruotis(ActionEvent actionEvent) {
        if (!Validation.isValidEmail(email.getText())) {
            this.zinute.setText("Klaidingas email");
            //TODO: padaryti patikrinimą ar sutampa slaptažodžiai ir jų validaciją
            // Žiūrėti Helpdesk
        } else {
            try {
                // Vartotojo tipas visuomet SKAITYTOJAS. ADMINISTRATORIUS sukurti ranka pakeičiant įrašą DB
                VartotojasDAO.create(new Vartotojas(email.getText(), vardas.getText(), pavarde.getText(), Password.hashPassword(slaptazodis1.getText()), "SKAITYTOJAS"));
                this.onActionAtsaukti(actionEvent);
            } catch (SQLException throwables) {
                this.zinute.setText("El. pašto adresas jau egzistuoja");
            }
        }
    }
}
