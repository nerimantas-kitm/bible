package lt.kitm.model;

public class Knyga {
    private int id;
    private String isbn;
    private String pavadinimas;
    private String santrauka;
    private String nuotrauka;
    private int puslapiuSkaicius;
    private int kategorijosId;

    public Knyga() {
    }

    public Knyga(String isbn, String pavadinimas, String santrauka, String nuotrauka, int puslapiuSkaicius, int kategorijosId) {
        this.isbn = isbn;
        this.pavadinimas = pavadinimas;
        this.santrauka = santrauka;
        this.nuotrauka = nuotrauka;
        this.puslapiuSkaicius = puslapiuSkaicius;
        this.kategorijosId = kategorijosId;
    }

    public Knyga(int id, String isbn, String pavadinimas, String santrauka, String nuotrauka, int puslapiuSkaicius) {
        this.id = id;
        this.isbn = isbn;
        this.pavadinimas = pavadinimas;
        this.santrauka = santrauka;
        this.nuotrauka = nuotrauka;
        this.puslapiuSkaicius = puslapiuSkaicius;
    }

    public Knyga(String isbn, String pavadinimas, String santrauka, String nuotrauka, int puslapiuSkaicius) {
        this.isbn = isbn;
        this.pavadinimas = pavadinimas;
        this.santrauka = santrauka;
        this.nuotrauka = nuotrauka;
        this.puslapiuSkaicius = puslapiuSkaicius;
    }

    public Knyga(int id, String isbn, String pavadinimas, String santrauka, String nuotrauka, int puslapiuSkaicius, int kategorijosId) {
        this.id = id;
        this.isbn = isbn;
        this.pavadinimas = pavadinimas;
        this.santrauka = santrauka;
        this.nuotrauka = nuotrauka;
        this.puslapiuSkaicius = puslapiuSkaicius;
        this.kategorijosId = kategorijosId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getSantrauka() {
        return santrauka;
    }

    public void setSantrauka(String santrauka) {
        this.santrauka = santrauka;
    }

    public String getNuotrauka() {
        return nuotrauka;
    }

    public void setNuotrauka(String nuotrauka) {
        this.nuotrauka = nuotrauka;
    }

    public int getPuslapiuSkaicius() {
        return puslapiuSkaicius;
    }

    public void setPuslapiuSkaicius(int puslapiuSkaicius) {
        this.puslapiuSkaicius = puslapiuSkaicius;
    }

    public int getKategorijosId() {
        return kategorijosId;
    }

    public void setKategorijosId(int kategorijosId) {
        this.kategorijosId = kategorijosId;
    }
}
