package leonblejc.kambusvoznired;

public class Bus {
    private String odhod, prihod, ime, cena;

    Bus(String ime, String cena, String odhod, String prihod) {
        this.ime = ime;
        this.cena = cena;
        this.odhod = odhod;
        this.prihod = prihod;
    }

    public String getCena() {
        return cena;
    }

    public String getOdhod() {
        return odhod;
    }

    public String getPrihod() {
        return prihod;
    }

    public String getIme() {
        return ime;
    }
}
