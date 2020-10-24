package ehu.isad.utils;

import com.google.gson.Gson;
import ehu.isad.Book;
import ehu.isad.controllers.db.LiburuDBKud;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Sarea {

    private static final Sarea nSarea = new Sarea();

    public static Sarea getSarea() {
        return nSarea;
    }

    private Sarea() {}

    public static Properties lortuEzarpenak()  {
        Properties properties = null;

        try (InputStream in = Sarea.class.getResourceAsStream("/setup.properties")) {
            properties = new Properties();
            properties.load(in);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public void infoLortu(String pISBN) {
        if (!LiburuDBKud.getLiburuDBKud().liburuaDago(pISBN)) {
            String emaitza = this.irakurriURL(pISBN);
            Gson gson = new Gson();
            Book liburua = gson.fromJson(emaitza, Book.class);
            LiburuDBKud.getLiburuDBKud().gordeLiburua(liburua, pISBN);
        }
    }

    private String irakurriURL(String pISBN) {
        String inputLine = "";
        try {
            URL url = new URL("https://openlibrary.org/api/books?bibkeys=ISBN:" + pISBN + "&jscmd=details&format=json");
            URLConnection urlKonexioa = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlKonexioa.getInputStream()));
            inputLine = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.infoZatitu(pISBN, inputLine);
    }

    private String infoZatitu(String pISBN, String pInputLine) {
        String[] zatiak = pInputLine.split("\\{\"ISBN:" + pISBN + "\":");
        String inputLine = zatiak[1];
        inputLine = inputLine.substring(0, inputLine.length()-1);
        return inputLine;
    }

    public Image argazkiaLortu(String url) throws IOException {
        URLConnection konexioa = new URL(url).openConnection();
        konexioa.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        try (InputStream stream = konexioa.getInputStream()) {
            return new Image(stream);
        }
    }
}