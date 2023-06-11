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
import lt.kitm.utils.PrisijungesVartotojas;
import lt.kitm.utils.Validation;

import java.io.IOException;
import java.sql.SQLException;

public class PrisijungimoController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField slaptazodis;
    @FXML
    private Label zinute;

    public void onActionRegistruotis(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/registracijos_langas.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Registracija");
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
            this.zinute.setText("Klaida atidarant registracijos langą");
        }
    }

    public void onActionPrisijungti(ActionEvent actionEvent) {
        // TODO: Čia reikia padaryti validaciją email ir slaptaždio
        try {
            Vartotojas vartotojas = VartotojasDAO.read(this.email.getText());
            if (vartotojas != null && Password.checkPassword(this.slaptazodis.getText(), vartotojas.getSlaptazodis())) {
                Parent root;
                if (vartotojas.getTipas().equals("ADMINAS")) {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("view/bibliotekos_langas_adminas.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("view/bibliotekos_langas_vartotojas.fxml"));
                }
                PrisijungesVartotojas.pridetiVartotoja(vartotojas);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Biblioteka");
                stage.setScene(new Scene(root, 1100, 800));
            } else {
                this.zinute.setText("Neteisingas el. paštas arba slaptažodis");
            }
        } catch (SQLException e) {
            this.zinute.setText("Įvyko klaida nuskaitant duomenis iš DB");
        } catch (IOException e) {
            e.printStackTrace();
            this.zinute.setText("Klaida atidarant Bibliotekos langą");
        }
    }
}
