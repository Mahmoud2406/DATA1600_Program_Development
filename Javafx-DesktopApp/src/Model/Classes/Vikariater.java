package Model.Classes;

import java.io.Serializable;

public class Vikariater implements Serializable {
    private String stillingsNavn;
    private String arbeidsGiverNavn;
    private String utdanning;
    private String jobbKategori;
    private String stillingType;
    private String arbeidsOppgaver;
    private String kvalifikasjoner;
    private String arbeidsSted;
    private String arbeidStid;
    private String varighet;
    private String lønnsNivaa;
    private String stillingBeskrivelse;


    public Vikariater(String stillingsNavn, String arbeidsGiverNavn, String utdanning, String jobbKategori, String stillingType, String arbeidsOppgaver, String kvalifikasjoner, String arbeidsSted, String arbeidStid, String varighet, String lønnsNivaa, String stillingBeskrivelse) {
        this.stillingsNavn = stillingsNavn;
        this.arbeidsGiverNavn = arbeidsGiverNavn;
        this.utdanning = utdanning;
        this.jobbKategori = jobbKategori;
        this.stillingType = stillingType;
        this.arbeidsOppgaver = arbeidsOppgaver;
        this.kvalifikasjoner = kvalifikasjoner;
        this.arbeidsSted = arbeidsSted;
        this.arbeidStid = arbeidStid;
        this.varighet = varighet;
        this.lønnsNivaa = lønnsNivaa;
        this.stillingBeskrivelse = stillingBeskrivelse;
    }

    //get metode for utdanning
    public String getUtdanning() {
        return utdanning;
    }

    //get metode for Arbeidsgivernavn
    public String getArbeidsGiverNavn() {
        return arbeidsGiverNavn;
    }

    //get metode for jobbkategori
    public String getJobbKategori() {
        return jobbKategori;
    }

    //get metode for stillingstype
    public String getStillingType() {
        return stillingType;
    }

    //get metode for arbeidsoppgaver
    public String getArbeidsOppgaver() {
        return arbeidsOppgaver;
    }

    //get metode for kvalifikasjoner
    public String getKvalifikasjoner() {
        return kvalifikasjoner;
    }

    //get metode for arbeidssted
    public String getArbeidsSted() {
        return arbeidsSted;
    }

    //get metode for arbeidsstid
    public String getArbeidStid() {
        return arbeidStid;
    }

    //get metode for varighet
    public String getVarighet() {
        return varighet;
    }

    //get metode for getlønnsnivaa
    public String getLønnsNivaa() {
        return lønnsNivaa;
    }

    //get metode for getStillingBeskrivelse
    public String getStillingBeskrivelse() {
        return stillingBeskrivelse;
    }

    //get metode for stillingsnavn
    public String getStillingsNavn() {
        return stillingsNavn;
    }

    // Overrider tostring metoden for å skrive til csv fil.
    @Override
    public String toString() {
        return stillingsNavn + ";" +
                arbeidsGiverNavn + ";" +
                utdanning + ";" +
                jobbKategori + ";" +
                stillingType + ";" +
                arbeidsOppgaver + ";" +
                kvalifikasjoner + ";" +
                arbeidsSted + ";" +
                arbeidStid + ";" +
                varighet + ";" +
                lønnsNivaa + ";" +
                stillingBeskrivelse;
    }


}