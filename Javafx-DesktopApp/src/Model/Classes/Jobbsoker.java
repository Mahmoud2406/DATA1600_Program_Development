package Model.Classes;

import java.io.Serializable;
import java.util.Objects;

public class Jobbsoker extends Person implements Serializable {
    private String kjonn;
    private String Jobberfaring;
    private String fodselsDato;
    private String utdanning;
    private String lonnskrav;
    private String referanseNavn;
    private String referanseTlf;
    private String jobbKategori;

    // overskriver equals metoden for å brukes mot duplikate objekter.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Jobbsoker jobbsoker = (Jobbsoker) o;
        return Objects.equals(kjonn, jobbsoker.kjonn) &&
                Objects.equals(Jobberfaring, jobbsoker.Jobberfaring) &&
                Objects.equals(fodselsDato, jobbsoker.fodselsDato) &&
                Objects.equals(utdanning, jobbsoker.utdanning) &&
                Objects.equals(lonnskrav, jobbsoker.lonnskrav) &&
                Objects.equals(referanseNavn, jobbsoker.referanseNavn) &&
                Objects.equals(referanseTlf, jobbsoker.referanseTlf) &&
                Objects.equals(jobbKategori, jobbsoker.jobbKategori);
    }

    //overskriver hashkoden
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kjonn, Jobberfaring, fodselsDato, utdanning, lonnskrav, referanseNavn, referanseTlf, jobbKategori);
    }

    //Henter ut super sin konstruktør
    public Jobbsoker(String fornavn, String etternavn, String adresse, String postSted, String postNummer, String telefonNummer,
                     String kjonn, String jobberfaring, String fodselsDato, String utdanning,
                     String lonnskrav, String referanseNavn, String referanseTlf, String jobbKategori) {
        super(fornavn, etternavn, adresse, postSted, postNummer, telefonNummer);
        this.kjonn = (kjonn);
        Jobberfaring = (jobberfaring);
        this.fodselsDato = (fodselsDato);
        this.utdanning = (utdanning);
        this.lonnskrav = (lonnskrav);
        this.referanseNavn = (referanseNavn);
        this.referanseTlf = (referanseTlf);
        this.jobbKategori = (jobbKategori);
    }

    //get metode for kjønn
    public String getKjonn() {
        return kjonn;
    }
    //get metode for jobberfaring
    public String getJobberfaring() {
        return Jobberfaring;
    }
    //get metode for fødselsdato
    public String getFodselsDato() {
        return fodselsDato;
    }
    //get metode for utdanning
    public String getUtdanning() {
        return utdanning;
    }
    //get metode for Lønnskravet
    public String getLonnskrav() {
        return lonnskrav;
    }
    //get metode for referansenavnet
    public String getReferanseNavn() {
        return referanseNavn;
    }
    //get metode for referansetelefonnummer
    public String getReferanseTlf() {
        return referanseTlf;
    }
    //get metode for jobbkategori
    public String getJobbKategori() {
        return jobbKategori;
    }

    // Overrider tostring metoden for å skrive til csv fil.
    @Override
    public String toString() {
        return super.toString() + kjonn + ";" +
                Jobberfaring + ";" +
                fodselsDato + ";" +
                utdanning + ";" +
                lonnskrav + ";" +
                referanseNavn + ";" +
                referanseTlf + ";" +
                jobbKategori;
    }

}
