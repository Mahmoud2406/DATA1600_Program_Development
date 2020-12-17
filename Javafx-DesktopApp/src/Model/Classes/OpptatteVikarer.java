package Model.Classes;

public class OpptatteVikarer {
    private Jobbsoker jobbsoker;
    private Vikariater vikariat;
    // Konstruktør som tar inn jobbsøker og vikariat objekt
    public OpptatteVikarer(Jobbsoker jobbsoker, Vikariater vikariat) {
        this.jobbsoker = jobbsoker;
        this.vikariat = vikariat;
    }
    // getmetode for jobbsøker
    public Jobbsoker getJobbsoker() {
        return jobbsoker;
    }
    // getmetode for vikariat
    public Vikariater getVikariat() {
        return vikariat;
    }
    // Overrider tostring metoden for å skrive til csv fil.
    @Override
    public String toString() {
        return
                jobbsoker + ";" +
                        vikariat;
    }
}
