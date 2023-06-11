package lt.kitm.dto;

public class KnygaDTO {
    private int id;
    private String isbn;
    private String pavadinimas;
    private String santrauka;
    private String nuotrauka;
    private int puslapiuSkaicius;
    private int kategorijosId;
    private String kategorijosPavadinimas;

    public KnygaDTO() {
    }

    public KnygaDTO(int id, String isbn, String pavadinimas, String santrauka, String nuotrauka, int puslapiuSkaicius, int kategorijosId, String kategorijosPavadinimas) {
        this.id = id;
        this.isbn = isbn;
        this.pavadinimas = pavadinimas;
        this.santrauka = santrauka;
        this.nuotrauka = nuotrauka;
        this.puslapiuSkaicius = puslapiuSkaicius;
        this.kategorijosId = kategorijosId;
        this.kategorijosPavadinimas = kategorijosPavadinimas;
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

    public String getKategorijosPavadinimas() {
        return kategorijosPavadinimas;
    }

    public void setKategorijosPavadinimas(String kategorijosPavadinimas) {
        this.kategorijosPavadinimas = kategorijosPavadinimas;
    }

    @Override
    public String toString() {
        return "KnygaDTO{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", pavadinimas='" + pavadinimas + '\'' +
                ", santrauka='" + santrauka + '\'' +
                ", nuotrauka='" + nuotrauka + '\'' +
                ", puslapiuSkaicius=" + puslapiuSkaicius +
                ", kategorijosId=" + kategorijosId +
                ", kategorijosPavadinimas='" + kategorijosPavadinimas + '\'' +
                '}';
    }
}
