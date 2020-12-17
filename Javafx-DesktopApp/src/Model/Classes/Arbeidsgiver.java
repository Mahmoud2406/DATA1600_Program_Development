package Model.Classes;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Arbeidsgiver extends Person implements Serializable {
    private String jurdiskNavn;
    private String bransje;
    private String selskapsform;
    private String email;
    private String sektor;
    public ArrayList<Object> vikariaterList;

    // overskriver equals metoden for å brukes mot duplikate objekter.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Arbeidsgiver that = (Arbeidsgiver) o;
        return Objects.equals(jurdiskNavn, that.jurdiskNavn) &&
                Objects.equals(bransje, that.bransje) &&
                Objects.equals(selskapsform, that.selskapsform) &&
                Objects.equals(email, that.email) &&
                Objects.equals(sektor, that.sektor) &&
                Objects.equals(vikariaterList, that.vikariaterList);
    }

    //overskriver hashkoden
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jurdiskNavn, bransje, selskapsform, email, sektor, vikariaterList);
    }

    //Henter ut super sin konstruktør
    public Arbeidsgiver(String fornavn, String etternavn, String adresse, String postSted, String postNummer,
                        String telefonNummer, String jurdiskNavn, String bransje,
                        String selskapsform, String email, String sektor) {
        super(fornavn, etternavn, adresse, postSted, postNummer, telefonNummer);
        this.jurdiskNavn = jurdiskNavn;
        this.bransje = bransje;
        this.selskapsform = selskapsform;
        this.email = email;
        this.sektor = sektor;
    }

    //get metode for juridiske navnet
    public String getJurdiskNavn() {
        return jurdiskNavn;
    }

    //get metode for Bransje
    public String getBransje() {
        return bransje;
    }

    //get metode for selskapsform
    public String getSelskapsform() {
        return selskapsform;
    }

    //get metode for Email
    public String getEmail() {
        return email;
    }

    //get metode for sektor
    public String getSektor() {
        return sektor;
    }

    // Overrider tostring metoden for å skrive til csv fil.
    @Override
    public String toString() {
        return super.toString() +
                jurdiskNavn + ";" +
                bransje + ";" +
                selskapsform + ";" +
                email + ";" +
                sektor;
    }
}
