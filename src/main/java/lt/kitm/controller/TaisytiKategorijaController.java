package lt.kitm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.kitm.model.Kategorija;
import lt.kitm.model.KategorijaDAO;

import java.sql.SQLException;

public class TaisytiKategorijaController {
    @FXML
    private Label kategorijos_id;
    @FXML
    private TextField kategorijos_pavadinimas;
    @FXML
    private Label zinute;
    private boolean pakeista = false;
    private Kategorija kategorija;

    public void uzkrautiKategorijosInformacija(Kategorija kategorija) {
        this.kategorija = kategorija;
        this.kategorijos_id.setText("Kategorijos ID: " + kategorija.getId());
        this.kategorijos_pavadinimas.setText(kategorija.getPavadinimas());
    }

    public boolean isPakeista() {
        return this.pakeista;
    }

    public void onActionAtsaukti(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onActionAtnaujinti(ActionEvent actionEvent) {
        try {
            this.kategorija.setPavadinimas(this.kategorijos_pavadinimas.getText());
            KategorijaDAO.update(this.kategorija);
            this.pakeista = true;
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace();
            this.zinute.setText("Kategorija tokiu pavadinimu jau egzistuoja");
        }
    }
}
