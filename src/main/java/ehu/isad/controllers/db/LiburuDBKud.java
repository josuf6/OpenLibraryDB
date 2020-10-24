package ehu.isad.controllers.db;

import ehu.isad.Book;
import ehu.isad.utils.Sarea;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class LiburuDBKud {

    private static final LiburuDBKud nLiburuDBKud = new LiburuDBKud();

    public static LiburuDBKud getLiburuDBKud() {
        return nLiburuDBKud;
    }

    private LiburuDBKud() {}

    public boolean liburuaDago(String pISBN) {
        String query = "select isbn, izenburua, azpititulua, orriKop, argazkiIzen from Liburua where isbn='" + pISBN +"';";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void gordeLiburua(Book pLiburua, String pIsbn) {
        String titulua = pLiburua.getDetails().getTitle();
        String azpititulua = pLiburua.getDetails().getSubtitle();
        int orriKop = pLiburua.getDetails().getNumber_of_pages();
        String argazkiIzen = pIsbn + ".jpg";
        String argazkiURL = pLiburua.getThumbnail_url();

        String query = "insert into Liburua (isbn, izenburua, azpititulua, orriKop, argazkiIzen, argazkiURL) values ('" + pIsbn + "', '" + titulua + "', '" + azpititulua + "', " + orriKop + ", '" + argazkiIzen + "', '" + argazkiURL + "');";
        DBKud.getDBKud().execSQL(query);
        String[] argitaletxeak = pLiburua.getDetails().getPublishers();
        this.gordeArgitaletxeak(pIsbn, argitaletxeak);
        this.gordeArgazkia(pIsbn);
    }

    private void gordeArgitaletxeak(String pIsbn, String[] pArgitaletxeak) {
        int i = 0;
        while (i < pArgitaletxeak.length) {
            if (!this.argitaletxeaDago(pArgitaletxeak[i])) {
                String query = "insert into Argitaletxea (izena) values (\"" + pArgitaletxeak[i] + "\");";
                DBKud.getDBKud().execSQL(query);
            }
            if (!this.liburuArgitDago(pIsbn, pArgitaletxeak[i])) {
                String query = "insert into LiburuArgit (liburuIsbn, argitIzen) values ('" + pIsbn + "', \"" + pArgitaletxeak[i] + "\");";
                DBKud.getDBKud().execSQL(query);
                i++;
            }
        }
    }

    private boolean argitaletxeaDago(String pIzen) {
        String query = "select izena from Argitaletxea where izena=\"" + pIzen +"\";";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean liburuArgitDago(String pIsbn, String pIzen) {
        String query = "select liburuIsbn, argitIzen from LiburuArgit where liburuIsbn='" + pIsbn + "' and argitIzen=\"" + pIzen +"\";";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void gordeArgazkia(String pIsbn) {
        String argazkiIzen = this.getArgazkiIzen(pIsbn);
        if (!argazkiIzen.isBlank()) {
            String urlTxikia = this.getArgazkiURL(pIsbn);
            this.gordeArgazkiaURL(urlTxikia, argazkiIzen);
        }
    }

    private String getArgazkiIzen(String pIsbn) {
        String argazkiIzen = "";
        String query = "select argazkiIzen from Liburua where isbn='" + pIsbn + "';";
        ResultSet rs = DBKud.getDBKud().execSQL(query);
        try {
            if (rs.next()) {
                argazkiIzen = rs.getString("argazkiIzen");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (argazkiIzen.equals("null")) return "";
        return argazkiIzen;
    }

    private String getArgazkiURL(String pIsbn) {
        String argazkiURL = "";
        if (this.liburuaDago(pIsbn)) {
            String query = "select argazkiURL from Liburua where isbn='" + pIsbn + "';";
            ResultSet rs = DBKud.getDBKud().execSQL(query);
            try {
                if (rs.next()) {
                    argazkiURL = rs.getString("argazkiURL");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (argazkiURL.equals("null")) return "";
        return argazkiURL;
    }

    private void gordeArgazkiaURL(String pUrlTxikia, String pArgazkiIzen) {
        if (!pUrlTxikia.isBlank()) {
            Image argazkia = null;
            String urlErtaina =  pUrlTxikia.substring(0, pUrlTxikia.length() - 5) + "M.jpg";
            try {
                argazkia = Sarea.getSarea().argazkiaLortu(urlErtaina);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Properties properties = Sarea.lortuEzarpenak();
            String helbidea = properties.getProperty("imagespath");
            File helbideFitx = new File(helbidea);
            if (helbideFitx.exists() && helbideFitx.isDirectory()) {
                this.gordeArgazkiaHelbideFitx(helbidea, pArgazkiIzen, argazkia);
            }
        }
    }

    private void gordeArgazkiaHelbideFitx(String pHelbidea, String pArgazkiIzen, Image pArgazkia) {
        File argazkiFitx = new File(pHelbidea + "/" + pArgazkiIzen);
        try {
            if (!argazkiFitx.exists() && argazkiFitx.createNewFile()) {
                BufferedImage bi = SwingFXUtils.fromFXImage(pArgazkia, null);
                try {
                    ImageIO.write(bi, "jpg", argazkiFitx);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIzenburua(String pIsbn) {
        String izenburua = "";
        if (this.liburuaDago(pIsbn)) {
            String query = "select izenburua from Liburua where isbn='" + pIsbn + "';";
            ResultSet rs = DBKud.getDBKud().execSQL(query);
            try {
                if (rs.next()) {
                    izenburua = rs.getString("izenburua");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (izenburua.equals("null")) return "";
        return izenburua;
    }

    public String getAzpititulua(String pIsbn) {
        String azpititulua = "";
        if (this.liburuaDago(pIsbn)) {
            String query = "select azpititulua from Liburua where isbn='" + pIsbn + "';";
            ResultSet rs =DBKud.getDBKud().execSQL(query);
            try {
                if (rs.next()) {
                    azpititulua = rs.getString("azpititulua");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (azpititulua.equals("null")) return "";
        return azpititulua;
    }

    public String getArgitaletxeak(String pIsbn) {
        ArrayList<String> argitaletxeak = null;
        if (this.liburuaDago(pIsbn)) {
            argitaletxeak = new ArrayList<>();
            String query = "select argitIzen from LiburuArgit where liburuIsbn='" + pIsbn + "';";
            ResultSet rs =DBKud.getDBKud().execSQL(query);
            try {
                while (rs.next()) {
                    String argitaletxea = rs.getString("argitIzen");
                    if (!argitaletxea.equals("null")) argitaletxeak.add(argitaletxea);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return this.argitaletxeakKudeatu(argitaletxeak);
    }

    private String argitaletxeakKudeatu(ArrayList<String> argitaletxeak) {
        StringBuilder emaitza;
        if (argitaletxeak != null && argitaletxeak.size() > 0) {
            emaitza = new StringBuilder(argitaletxeak.get(0));
            int i = 1;
            while (i < argitaletxeak.size()) {
                emaitza.append(", ").append(argitaletxeak.get(i));
                i++;
            }
        }
        else return "";
        return emaitza.toString();
    }

    public String getOrriKop(String pIsbn) {
        int orriKop = 0;
        if (this.liburuaDago(pIsbn)) {
            String query = "select orriKop from Liburua where isbn='" + pIsbn + "';";
            ResultSet rs =DBKud.getDBKud().execSQL(query);
            try {
                if (rs.next()) {
                    orriKop = rs.getInt("orriKop");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (orriKop == 0) return "";
        return Integer.toString(orriKop);
    }

    public Image getArgazkia(String pIsbn) {
        Image argazkia = null;
        if (this.liburuaDago(pIsbn)) {
            String argazkiIzen = this.getArgazkiIzen(pIsbn);
            if (!argazkiIzen.isBlank()) {
                Properties properties = Sarea.lortuEzarpenak();
                String helbidea = properties.getProperty("imagespath");
                File argazkiFitx = new File(helbidea + "/" + argazkiIzen);
                if (argazkiFitx.exists() && argazkiFitx.isFile()) {
                        try {
                            BufferedImage bi = ImageIO.read(argazkiFitx);
                            argazkia = SwingFXUtils.toFXImage(bi, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
        return argazkia;
    }
}
