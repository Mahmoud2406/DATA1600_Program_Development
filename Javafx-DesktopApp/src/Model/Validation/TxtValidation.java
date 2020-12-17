package Model.Validation;

public class TxtValidation {

    // Validerer alt i arbeidsgiver feltene
    public static String validateAllinArbeidsgiver(String juridiskNavn, String fornavn, String etternavn,
                                                   String adresse, String postnr, String poststed, String tlf, String email, Object obj) {
        return validateJuridiskNavn(juridiskNavn) +
                validateFornavn(fornavn) +
                validateEtternavn(etternavn) +
                validateAdresse(adresse) +
                validatePostnr(postnr) +
                validatePoststed(poststed) +
                validateTlfNr(tlf) +
                validateEmail(email) +
                validateObjectOnNull(obj);
    }

    // Validerer alt ivikariater feltene
    public static String validateAllinVikariater(String stillingsnavn,
                                                 String arbeidssted, String arbeidsstid,
                                                 String varighet,
                                                 String lonnsnivå) {
        return validateStillingsnavn(stillingsnavn) +
                validateArbeidstid(arbeidsstid) +
                validateArbeidsted(arbeidssted) +
                validateVarighet(varighet) +
                validateLonn(lonnsnivå);
    }

    // Validerer alt i jobbsøker feltene
    public static String validateAllInJobbsoker(String fornavn, String etternavn,
                                                String adresse, String postnr, String poststed, String tlf,
                                                String lonnniva, String refNavn, String refTlf, Object utddanning,
                                                Object kategori, Object fodselsdato, String jobberfaring) {

        return validateFornavn(fornavn) +
                validateEtternavn(etternavn) +
                validateAdresse(adresse) +
                validatePostnr(postnr) +
                validatePoststed(poststed) +
                validateTlfNr(tlf) +
                validateLonn(lonnniva) +
                validateFornavn(refNavn) +
                validateTlfNr(refTlf) +
                validateObjectOnNull(kategori) +
                validateObjectOnNull(utddanning) +
                validateFodselsdato(fodselsdato) +
                validateJobberfaring(jobberfaring);
    }

    // validerer ved hjelp av regex og returnerer enten tom string eller feilmelding

    public static String validateJuridiskNavn(String input) {
        String lbl = "";

        String regex = "[a-zæøåA-ZÆØÅ ]*";
        if (!input.matches(regex)) {
            lbl = ("Juridiske navnet har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn juridiske navnet \n");
        }

        return lbl;

    }

    public static String validateFornavn(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ]*";
        if (!input.matches(regex)) {
            lbl = ("Fornavnet har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn fornavnet \n");
        }

        return lbl;

    }

    public static String validateEtternavn(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ]*";
        if (!input.matches(regex)) {
            lbl = ("Etternavnet har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn etternavnet \n");
        }

        return lbl;

    }


    public static String validateAdresse(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ0-9 ]*";
        if (!input.matches(regex)) {

            lbl = ("Adressen har feil format \n");
        } else if (input.isEmpty()) {

            lbl = ("Du må fylle inn adress feltet \n");
        }

        return lbl;

    }

    public static String validatePostnr(String input) {

        String lbl = "";
        String regex = "[0-9]{4}";
        if (!input.matches(regex)) {

            lbl = ("Postnummeret har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn postnr feltet \n");
        }

        return lbl;

    }

    public static String validatePoststed(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ]{2,25}";
        if (!input.matches(regex)) {
            lbl = ("Poststedet har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn poststed feltet \n");
        }

        return lbl;
    }

    public static String validateArbeidsted(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ]{2,25}";
        if (!input.matches(regex)) {
            lbl = ("Arbeidssted har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn poststed feltet \n");
        }

        return lbl;
    }

    public static String validateTlfNr(String input) {
        String lbl = "";
        String regex = "[0-9]{8}";
        if (!input.matches(regex)) {
            lbl = ("Telefonnumeret har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn telefonnummer feltet \n");
        }

        return lbl;

    }


    public static String validateEmail(String input) {

        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ.-_]*[@][a-zæøåA-ZÆØÅ]*[.]{1}[a-zA-Z]{2,3}";
        if (!input.matches(regex)) {

            lbl = ("Emailen har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn email feltet \n");
        }

        return lbl;
    }

    public static String validateStillingsnavn(String input) {
        String lbl = "";
        String regex = "[a-zæøåA-ZÆØÅ ]*";
        if (!input.matches(regex)) {
            lbl = ("Stillingsnavn har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn stillingsnavnet \n");
        }
        return lbl;

    }


    public static String validateArbeidstid(String input) {

        String lbl = "";
        String regex = "[0-9]{2,3}[%]";
        if (!input.matches(regex)) {
            lbl = ("Arbeidstiden har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn arbeidstiden feltet \n");
        }

        return lbl;
    }

    public static String validateVarighet(String input) {
        String lbl = "";
        String regex = "[0-9 -.]*";
        if (!input.matches(regex)) {
            lbl = ("Varighet har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn varighet feltet \n");
        }

        return lbl;
    }

    public static String validateLonn(String input) {

        String lbl = "";
        String regex = "[0-9 ]{3,10}";
        if (!input.matches(regex)) {
            lbl = ("Lønnsnivået har feil format \n");
        } else if (input.isEmpty()) {
            lbl = ("Du må fylle inn lønnsnivå feltet \n");
        }

        return lbl;
    }

    public static String validateJobberfaring(String input) {
        String lbl = "";
        if (input.isEmpty()) {
            lbl = ("Du må fylle inn jobberfaring \n");
        }
        return lbl;
    }

    public static String validateFodselsdato(Object obj) {
        String lbl = "";
        // String regex = "[0-9]{2}[.][0-9]{2}[.][0-9]{4}";
        //if (!obj.toString().matches(regex)) {
        //lbl = "Du "
        //}
        //}
        if (obj == null) {
            lbl = ("Velg fødselsdatoen \n");
        }
        return lbl;
    }

    public static String validateObjectOnNull(Object obj) {
        String lbl = "";
        if (obj == null) {

            lbl = ("Velg ønsket utvalg i nedtrekslistene \n");
        }

        return lbl;
    }

}
