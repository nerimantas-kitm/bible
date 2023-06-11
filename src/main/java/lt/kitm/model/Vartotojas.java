package lt.kitm.model;

public class Vartotojas {
    private int id;
    private String email;
    private String vardas;
    private String pavarde;
    private String slaptazodis;
    private String tipas;

    public Vartotojas() {
    }

    public Vartotojas(String email, String vardas, String pavarde, String slaptazodis, String tipas) {
        this.email = email;
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.slaptazodis = slaptazodis;
        this.tipas = tipas;
    }

    public Vartotojas(int id, String email, String vardas, String pavarde, String slaptazodis, String tipas) {
        this.id = id;
        this.email = email;
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.slaptazodis = slaptazodis;
        this.tipas = tipas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

    public String getTipas() {
        return tipas;
    }

    public void setTipas(String tipas) {
        this.tipas = tipas;
    }

    @Override
    public String toString() {
        return "Vartotojas{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", vardas='" + vardas + '\'' +
                ", pavarde='" + pavarde + '\'' +
                ", slaptazodis='" + slaptazodis + '\'' +
                ", tipas='" + tipas + '\'' +
                '}';
    }
}
