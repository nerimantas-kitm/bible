package lt.kitm.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import lt.kitm.dto.KnygaDTO;
import lt.kitm.model.KnygaDAO;
import lt.kitm.utils.PrisijungesVartotojas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BibliotekaVartotojasController {
    @FXML
    private RadioButton mygtukas_knygos;
    @FXML
    private RadioButton mygtukas_rezervacijos;
    @FXML
    private RadioButton mygtukas_pamegtos;
    @FXML
    private VBox turinys;
    @FXML
    private Label zinute;

    public void initialize() {
        this.mygtukas_knygos.getStyleClass().remove("radio-button");
        this.mygtukas_knygos.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BibliotekaVartotojasController.this.uzkrautiKnygas();
            }
        });
        this.mygtukas_rezervacijos.getStyleClass().remove("radio-button");
        this.mygtukas_rezervacijos.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BibliotekaVartotojasController.this.uzkrautiRezervacijas();
            }
        });
        this.mygtukas_pamegtos.getStyleClass().remove("radio-button");
        this.mygtukas_pamegtos.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                BibliotekaVartotojasController.this.uzkrautiPamegtasKnygas();
            }
        });
        this.uzkrautiKnygas();
    }

    public void onActionAtsijungti(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/prisijungimo_langas.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Bible");
            stage.setScene(new Scene(root, 600, 400));
            PrisijungesVartotojas.pasalintiVartotoja();
        } catch (IOException e) {
            this.zinute.setText("Klaida grįžtant į prisijungimo langą");
        }
    }

    private void rezervuotiKnyga(int vartotojoId, int knygosId) {
        try {
            if (KnygaDAO.knygaRezervuota(knygosId)) {
                this.zinute.setText("Knyga yra rezervuota kito vartotojo");
            } else {
                KnygaDAO.rezervuotiKnyga(vartotojoId, knygosId);
                this.zinute.setText("Knyga rezervuota");
            }
        } catch (SQLException e) {
            //TODO: reikia žinutės jei nepavyko
            e.printStackTrace();
        }
    }

    private void nebemegtiKnygos(int vartotojoId, int knygosId) {
        try {
            KnygaDAO.nebemegtiKnygos(vartotojoId, knygosId);
            this.zinute.setText("Knyga pašalita iš pamėgtė knygų sąrašo");
            this.uzkrautiPamegtasKnygas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void atsauktiRezervacija(int vartotojoId, int knygosId) {
        try {
            KnygaDAO.atsauktiRezervacija(vartotojoId, knygosId);
            this.zinute.setText("Knygos rezervacija atšaukta");
            this.uzkrautiRezervacijas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void pamegtiKnyga(int vartotojoId, int knygosId) {
        try {
            if (KnygaDAO.arVartotojasPamegesKnyga(vartotojoId, knygosId)) {
                this.zinute.setText("Knygą jau turite pamėgtų sąraše");
            } else {
                KnygaDAO.pamegtiKnyga(vartotojoId, knygosId);
                this.zinute.setText("Knyga pamėgta");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void uzkrautiKnygas() {
        try {
            this.turinys.getChildren().clear();
            TextField filtravimas = new TextField();
            filtravimas.setPromptText("Knyogs pavadinimas");
            this.turinys.getChildren().add(filtravimas);
            //TODO: reikia padaryti, kad gražiau išsiplėstu lentelės
            ArrayList<KnygaDTO> knygos = KnygaDAO.visosKnygos();
            TableView<KnygaDTO> tableView = new TableView<>();
            TableColumn<KnygaDTO, String> pavadinimas = new TableColumn<>("Pavadinimas");
            pavadinimas.setCellValueFactory(new PropertyValueFactory<>("pavadinimas"));
            TableColumn<KnygaDTO, String> santrauka = new TableColumn<>("Santrauka");
            santrauka.setCellValueFactory(new PropertyValueFactory<>("santrauka"));
            santrauka.setMaxWidth(300);
            TableColumn<KnygaDTO, Integer> puslapiuSkaicius = new TableColumn<>("Puslapiu sk.");
            puslapiuSkaicius.setCellValueFactory(new PropertyValueFactory<>("puslapiuSkaicius"));
            TableColumn<KnygaDTO, String> kategorija = new TableColumn<>("Kategorija");
            kategorija.setCellValueFactory(new PropertyValueFactory<>("kategorijosPavadinimas"));
            TableColumn<KnygaDTO, String> isbn = new TableColumn<>("ISBN");
            isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            TableColumn<KnygaDTO, Void> rezervuoti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryRezervuoti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Rezervuoti");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                rezervuotiKnyga(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            rezervuoti.setCellFactory(cellFactoryRezervuoti);
            TableColumn<KnygaDTO, Void> pamegti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryPamegti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Pamėgti");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                pamegtiKnyga(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            pamegti.setCellFactory(cellFactoryPamegti);
            tableView.getColumns().addAll(pavadinimas, santrauka, puslapiuSkaicius, kategorija, isbn, rezervuoti, pamegti);
            this.turinys.getChildren().add(tableView);
            tableView.getItems().setAll(knygos);
            filtravimas.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<KnygaDTO> prafiltruotiKlausimai = (ArrayList<KnygaDTO>) knygos.
                            stream().
                            filter(p -> p.getPavadinimas().toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());
                    tableView.getItems().setAll(prafiltruotiKlausimai);
                }
            });
            tableView.prefHeightProperty().bind(this.turinys.heightProperty());
        } catch (SQLException e) {
            //TODO: žinutė jei nepavyko?
        }
    }

    private void uzkrautiRezervacijas() {
        try {
            this.turinys.getChildren().clear();
            TextField filtravimas = new TextField();
            filtravimas.setPromptText("Knyogs pavadinimas");
            this.turinys.getChildren().add(filtravimas);
            //TODO: reikia padaryti, kad gražiau išsiplėstu lentelės
            ArrayList<KnygaDTO> knygos = KnygaDAO.gautiRezervuotasKnygas(PrisijungesVartotojas.getVartotojas().getId());
            TableView<KnygaDTO> tableView = new TableView<>();
            TableColumn<KnygaDTO, String> pavadinimas = new TableColumn<>("Pavadinimas");
            pavadinimas.setCellValueFactory(new PropertyValueFactory<>("pavadinimas"));
            TableColumn<KnygaDTO, String> santrauka = new TableColumn<>("Santrauka");
            santrauka.setCellValueFactory(new PropertyValueFactory<>("santrauka"));
            santrauka.setMaxWidth(300);
            TableColumn<KnygaDTO, Integer> puslapiuSkaicius = new TableColumn<>("Puslapiu sk.");
            puslapiuSkaicius.setCellValueFactory(new PropertyValueFactory<>("puslapiuSkaicius"));
            TableColumn<KnygaDTO, String> kategorija = new TableColumn<>("Kategorija");
            kategorija.setCellValueFactory(new PropertyValueFactory<>("kategorijosPavadinimas"));
            TableColumn<KnygaDTO, String> isbn = new TableColumn<>("ISBN");
            isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            TableColumn<KnygaDTO, Void> rezervuoti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryRezervuoti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Atšaukti rezervacija");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                atsauktiRezervacija(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            rezervuoti.setCellFactory(cellFactoryRezervuoti);
            TableColumn<KnygaDTO, Void> pamegti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryPamegti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Pamėgti");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                pamegtiKnyga(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            pamegti.setCellFactory(cellFactoryPamegti);
            tableView.getColumns().addAll(pavadinimas, santrauka, puslapiuSkaicius, kategorija, isbn, rezervuoti, pamegti);
            this.turinys.getChildren().add(tableView);
            tableView.getItems().setAll(knygos);
            filtravimas.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<KnygaDTO> prafiltruotiKlausimai = (ArrayList<KnygaDTO>) knygos.
                            stream().
                            filter(p -> p.getPavadinimas().toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());
                    tableView.getItems().setAll(prafiltruotiKlausimai);
                }
            });
            tableView.prefHeightProperty().bind(this.turinys.heightProperty());
        } catch (SQLException e) {
            //TODO: žinutė jei nepavyko?
        }
    }

    private void uzkrautiPamegtasKnygas() {
        this.turinys.getChildren().clear();
        try {
            this.turinys.getChildren().clear();
            TextField filtravimas = new TextField();
            filtravimas.setPromptText("Knyogs pavadinimas");
            this.turinys.getChildren().add(filtravimas);
            //TODO: reikia padaryti, kad gražiau išsiplėstu lentelės
            ArrayList<KnygaDTO> knygos = KnygaDAO.gautiPamegtasKnygas(PrisijungesVartotojas.getVartotojas().getId());
            TableView<KnygaDTO> tableView = new TableView<>();
            TableColumn<KnygaDTO, String> pavadinimas = new TableColumn<>("Pavadinimas");
            pavadinimas.setCellValueFactory(new PropertyValueFactory<>("pavadinimas"));
            TableColumn<KnygaDTO, String> santrauka = new TableColumn<>("Santrauka");
            santrauka.setCellValueFactory(new PropertyValueFactory<>("santrauka"));
            santrauka.setMaxWidth(300);
            TableColumn<KnygaDTO, Integer> puslapiuSkaicius = new TableColumn<>("Puslapiu sk.");
            puslapiuSkaicius.setCellValueFactory(new PropertyValueFactory<>("puslapiuSkaicius"));
            TableColumn<KnygaDTO, String> kategorija = new TableColumn<>("Kategorija");
            kategorija.setCellValueFactory(new PropertyValueFactory<>("kategorijosPavadinimas"));
            TableColumn<KnygaDTO, String> isbn = new TableColumn<>("ISBN");
            isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            TableColumn<KnygaDTO, Void> rezervuoti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryRezervuoti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Rezervuoti");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                rezervuotiKnyga(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            rezervuoti.setCellFactory(cellFactoryRezervuoti);
            TableColumn<KnygaDTO, Void> pamegti = new TableColumn<>();
            Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>> cellFactoryPamegti = new Callback<TableColumn<KnygaDTO, Void>, TableCell<KnygaDTO, Void>>() {
                @Override
                public TableCell<KnygaDTO, Void> call(TableColumn<KnygaDTO, Void> param) {
                    final TableCell<KnygaDTO, Void> mygtukasRezervuotiLangelis = new TableCell<KnygaDTO, Void>() {
                        final Button mygtukas = new Button("Nebemėgstu");
                        {
                            mygtukas.setOnAction((ActionEvent event) -> {
                                KnygaDTO knyga = getTableView().getItems().get(getIndex());
                                nebemegtiKnygos(PrisijungesVartotojas.getVartotojas().getId(), knyga.getId());
                            });
                        }
                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(mygtukas);
                            }
                        }
                    };
                    return mygtukasRezervuotiLangelis;
                }
            };
            pamegti.setCellFactory(cellFactoryPamegti);
            tableView.getColumns().addAll(pavadinimas, santrauka, puslapiuSkaicius, kategorija, isbn, rezervuoti, pamegti);
            this.turinys.getChildren().add(tableView);
            tableView.getItems().setAll(knygos);
            filtravimas.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<KnygaDTO> prafiltruotiKlausimai = (ArrayList<KnygaDTO>) knygos.
                            stream().
                            filter(p -> p.getPavadinimas().toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());
                    tableView.getItems().setAll(prafiltruotiKlausimai);
                }
            });
            tableView.prefHeightProperty().bind(this.turinys.heightProperty());
        } catch (SQLException e) {
            //TODO: žinutė jei nepavyko?
        }
    }
}
