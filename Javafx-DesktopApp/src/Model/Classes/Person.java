package Model.Classes;


import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    private String fornavn;
    private String etternavn;

    // overskriver equals metoden for å brukes mot duplikate objekter.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(fornavn, person.fornavn) &&
                Objects.equals(etternavn, person.etternavn) &&
                Objects.equals(adresse, person.adresse) &&
                Objects.equals(postSted, person.postSted) &&
                Objects.equals(postNummer, person.postNummer) &&
                Objects.equals(telefonNummer, person.telefonNummer);
    }

    //overskriver hashkoden
    @Override
    public int hashCode() {
        return Objects.hash(fornavn, etternavn, adresse, postSted, postNummer, telefonNummer);
    }

    private String adresse;
    private String postSted;
    private String postNummer;
    private String telefonNummer;

    //super sin konstruktør
    public Person(String fornavn, String etternavn, String adresse, String postSted, String postNummer, String telefonNummer) {
        this.fornavn = (fornavn);
        this.etternavn = (etternavn);
        this.adresse = (adresse);
        this.postSted = (postSted);
        this.postNummer = (postNummer);
        this.telefonNummer = (telefonNummer);
    }

    //get metode for fornavnet
    public String getFornavn() {
        return fornavn;
    }

    //get metode for etternavnet
    public String getEtternavn() {
        return etternavn;
    }

    //get metode for adresse
    public String getAdresse() {
        return adresse;
    }

    //get metode for possted
    public String getPostSted() {
        return postSted;
    }

    //get metode for posstnummer
    public String getPostNummer() {
        return postNummer;
    }

    //get metode for tlf
    public String getTelefonNummer() {
        return telefonNummer;
    }

    // Overrider tostring metoden for å skrive til csv fil.
    @Override
    public String toString() {
        return fornavn + ";" +
                etternavn + ";" +
                adresse + ";" +
                postSted + ";" +
                postNummer + ";" +
                telefonNummer + ";";
    }
}

