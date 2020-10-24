package ehu.isad.controllers.ui;

import ehu.isad.Liburuak;
import ehu.isad.controllers.db.LiburuDBKud;
import ehu.isad.utils.Sarea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class XehetasunakKud implements Initializable {

    private Liburuak mainApp;
    private Sarea nSarea;

    @FXML
    private HBox hBoxIzenb;

    @FXML
    private Label lblIzenburuDatuak;

    @FXML
    private HBox hBoxAzpitit;

    @FXML
    private Label lblAzpititDatuak;

    @FXML
    private HBox hBoxArgit;

    @FXML
    private Label lblArgitDatuak;

    @FXML
    private HBox hBoxOrriKop;

    @FXML
    private Label lblOrriKopDatuak;

    @FXML
    private HBox hBoxIrudi;

    @FXML
    private ImageView imgIrudia;

    @FXML
    private Button btnAtzera;

    public void setMainApp(Liburuak main) {
        mainApp = main;
    }

    @FXML
    void onClick() throws Exception {
        btnAtzera.setDisable(true);
        mainApp.liburuakErakutsi();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nSarea = Sarea.getSarea();
    }

    public void infoKudeatu(String pIsbn) {
        nSarea.infoLortu(pIsbn);
        lblIzenburuDatuak.setText(LiburuDBKud.getLiburuDBKud().getIzenburua(pIsbn));
        lblAzpititDatuak.setText(LiburuDBKud.getLiburuDBKud().getAzpititulua(pIsbn));
        lblArgitDatuak.setText(LiburuDBKud.getLiburuDBKud().getArgitaletxeak(pIsbn));
        lblOrriKopDatuak.setText(LiburuDBKud.getLiburuDBKud().getOrriKop(pIsbn));
        imgIrudia.setImage(LiburuDBKud.getLiburuDBKud().getArgazkia(pIsbn));
        this.UITamainaEgokitu();
    }

    private void UITamainaEgokitu() {
        hBoxIzenb.setPrefHeight(lblIzenburuDatuak.getPrefHeight());
        hBoxAzpitit.setPrefHeight(lblAzpititDatuak.getPrefHeight());
        hBoxArgit.setPrefHeight(lblArgitDatuak.getPrefHeight());
        hBoxOrriKop.setPrefHeight(lblOrriKopDatuak.getPrefHeight());
        hBoxIrudi.setPrefHeight(imgIrudia.getFitHeight());
    }
}