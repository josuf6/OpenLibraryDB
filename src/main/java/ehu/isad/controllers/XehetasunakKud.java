package ehu.isad.controllers;

import ehu.isad.Book;
import ehu.isad.Liburuak;
import ehu.isad.utils.Sarea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class XehetasunakKud implements Initializable {

    private Liburuak mainApp;
    private Sarea nSarea;
    private Book nLiburu;

    @FXML
    private VBox vBoxEdukia;

    @FXML
    private Label lblXehetasunak;

    @FXML
    private HBox hBoxIzenb;

    @FXML
    private Label lblIzenburua;

    @FXML
    private Label lblIzenburuDatuak;

    @FXML
    private HBox hBoxAzpitit;

    @FXML
    private Label lblAzpititulua;

    @FXML
    private Label lblAzpititDatuak;

    @FXML
    private HBox hBoxArgit;

    @FXML
    private Label lblArgitaletxeak;

    @FXML
    private Label lblArgitDatuak;

    @FXML
    private HBox hBoxOrriKop;

    @FXML
    private Label lblOrriKop;

    @FXML
    private Label lblOrriKopDatuak;

    @FXML
    private HBox hBoxIrudi;

    @FXML
    private Label lblIrudia;

    @FXML
    private ImageView imgIrudia;

    @FXML
    private Button btnAtzera;

    public void setMainApp(Liburuak main) {
        mainApp = main;
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        btnAtzera.setDisable(true);
        mainApp.liburuakErakutsi();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nSarea = new Sarea();
    }

    public void infoKudeatu(String pIsbn) {
        nLiburu = nSarea.infoLortu(pIsbn);
        lblIzenburuDatuak.setText(this.izenburuaKudeatu(nLiburu.getDetails().getTitle()));
        lblAzpititDatuak.setText(this.azpitituluaKudeatu(nLiburu.getDetails().getSubtitle()));
        lblArgitDatuak.setText(this.argitaletxeakKudeatu(nLiburu.getDetails().getPublishers()));
        lblOrriKopDatuak.setText(this.orriKopKudeatu(nLiburu.getDetails().getNumber_of_pages()));
        this.irudiaJarri();
        this.UITamainaEgokitu();
    }

    private void irudiaJarri() {
        String urlTxikia = nLiburu.getThumbnail_url();
        String urlErtaina =  urlTxikia.substring(0, urlTxikia.length() - 5) + "M.jpg";
        try {
            imgIrudia.setImage(nSarea.irudiaLortu(urlErtaina));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void UITamainaEgokitu() {
        hBoxIzenb.setPrefHeight(lblIzenburuDatuak.getPrefHeight());
        hBoxAzpitit.setPrefHeight(lblAzpititDatuak.getPrefHeight());
        hBoxArgit.setPrefHeight(lblArgitDatuak.getPrefHeight());
        hBoxOrriKop.setPrefHeight(lblOrriKopDatuak.getPrefHeight());
        hBoxIrudi.setPrefHeight(imgIrudia.getFitHeight());
    }

    private String izenburuaKudeatu(String tit) {
        if (tit == null) return "";
        else return tit;
    }

    private String azpitituluaKudeatu(String azpiTit) {
        if (azpiTit == null) return "";
        else return azpiTit;
    }

    private String argitaletxeakKudeatu(String[] argitaletxeak) {
        StringBuilder emaitza = new StringBuilder();
        if (argitaletxeak.length > 0) {
            emaitza = new StringBuilder(argitaletxeak[0]);
            int i = 1;
            while (i < argitaletxeak.length) {
                emaitza.append(", ").append(argitaletxeak[i]);
                i++;
            }
        }
        return emaitza.toString();
    }

    private String orriKopKudeatu(int number_of_pages) {
        if (number_of_pages == 0) return "";
        else return Integer.toString(number_of_pages);
    }
}