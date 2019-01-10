package leonblejc.kambusvoznired;

//Class for storing information of a single bus

public class Bus {
    private String odhod, prihod, ime, cena;

    Bus(String ime, String cena, String odhod, String prihod) {
        this.ime = ime;
        this.cena = cena;
        this.odhod = odhod;
        this.prihod = prihod;
    }

    String getCena() {
        return cena;
    }

    String getOdhod() {
        return odhod;
    }

    String getPrihod() {
        return prihod;
    }

    public String getIme() {
        return ime;
    }
}
