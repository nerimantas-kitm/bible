package lt.kitm.model;

public class Kategorija {
    private int id;
    private String pavadinimas;

    public Kategorija() {
    }

    public Kategorija(int id, String pavadinimas) {
        this.id = id;
        this.pavadinimas = pavadinimas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }
}
