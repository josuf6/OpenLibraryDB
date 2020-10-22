package ehu.isad;

public class Book {
    String comboIsbn;
    String comboTitulua;

    String thumbnail_url;
    Details details;

    public Book(String pComboIsbn, String pComboTitulua) {
        comboIsbn = pComboIsbn;
        comboTitulua = pComboTitulua;
    }

    public String getComboTitulua() {
        return comboTitulua;
    }

    public String getComboIsbn() {
        return comboIsbn;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public Details getDetails() {
        return details;
    }
}
