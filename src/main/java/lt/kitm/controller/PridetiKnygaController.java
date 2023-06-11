package lt.kitm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lt.kitm.model.Kategorija;
import lt.kitm.model.Knyga;
import lt.kitm.model.KnygaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PridetiKnygaController {
    @FXML
    private TextField isbn;
    @FXML
    private TextField pavadinimas;
    @FXML
    private TextArea santrauka;
    @FXML
    private Spinner<Integer> puslapiu_skaicius;
    @FXML
    private ComboBox<String> kategorija;
    @FXML
    private Label zinute;
    private ArrayList<Kategorija> kategorijos;
    private boolean buvoPrideta = false;

    public boolean buvoPrideta() {
        return this.buvoPrideta;
    }

    public void uzkrautiKategorijas(ArrayList<Kategorija> kategorijos) {
        this.kategorijos = kategorijos;
        kategorija.getItems().addAll(
                kategorijos.stream()
                .map(Kategorija::getPavadinimas)
                .collect(Collectors.toList()
        ));
    }

    public void onActionAtsaukti(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onActionPrideti(ActionEvent actionEvent) {
        //TODO: reikia patikrinti ar laukeliai tušti
        try {
            Knyga knyga = new Knyga(this.isbn.getText(), this.pavadinimas.getText(), this.santrauka.getText(), "", this.puslapiu_skaicius.getValue());
            if (!this.kategorija.getSelectionModel().isEmpty()) {
                int pasirinktaKategorija = kategorijos.stream()
                        .filter(k -> k.getPavadinimas().equals(this.kategorija.getValue()))
                        .findFirst().get().getId();
                knyga.setKategorijosId(pasirinktaKategorija);
            }
            KnygaDAO.create(knyga);
            this.buvoPrideta = true;
            this.onActionAtsaukti(actionEvent);
        } catch (SQLException e) {
            //TODO: žinutė jei klaida
            e.printStackTrace();
        }
    }
}
