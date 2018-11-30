package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public TextField imeField;
    public TextField prezimeField;
    public TextField indexField;
    public TextField jmbgField;
    public TextField datumField;
    public TextField emailField;
    public TextField adresaField;
    public TextField telefonField;
    public ComboBox mjestoField;
    public ChoiceBox statusField;
    public ChoiceBox godinaField;
    public ChoiceBox ciklusField;
    public ChoiceBox smjerField;
    public CheckBox pripadnostField;
    private boolean imeValidno = false, prezimeValidno = false, indeksValidan = false, jmbgValidan = false, datumValidan = false, emailValidan = false, telefonValidan = false;
    public String uporediSaJmbg = "";
    public String datumZaIspis = "";

    public boolean formularValidan() {
        return (imeValidno && prezimeValidno && indeksValidan && jmbgValidan && datumValidan && emailValidan && telefonValidan);
    }
    //provjera za ime i prezime
    private boolean validnoImePrezime(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z'))
                return false;
        }
        return !n.trim().isEmpty();
    }

    //provjera za indeks
    private boolean validanIndeks(String n) {
        if (n.length() > 5) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) < '9')) {
                return false;
            }
        }
        return !n.trim().isEmpty();
    }

    //provjera za datum
    private boolean validanUnosDatum(String n) {

        return isDateValid(n);
    }

    public boolean isDateValid(String s) {
        if (s.length() < 8) return false;
        int d = 0, m = 0, g = 0;
        String dan = "", mjesec = "", godina = "";
        if (s.length() == 8) {
            dan = s.substring(0, 2);
            mjesec = s.substring(2, 4);
            godina = s.substring(4, 8);
        } else if (s.length() == 10) {
            if (s.charAt(2) == s.charAt(5) && (s.charAt(2) == '.' || s.charAt(2) == '/' | s.charAt(2) == '-')) {
                dan = s.substring(0, 2);
                mjesec = s.substring(3, 5);
                godina = s.substring(6, 10);
            }
        } else {
            return false;
        }
        try {
            d = Integer.parseInt(dan);
            m = Integer.parseInt(mjesec);
            g = Integer.parseInt(godina);
        } catch (Exception ex) {
        }
        datumZaIspis = "" + d + "/" + m + "/" + g;
        int novaG = Integer.parseInt(godina.substring(1, godina.length()));
        uporediSaJmbg=""+dan+mjesec+novaG;
        //System.out.println("Uporedi:"+ uporediSaJmbg);
        if (d == 0 || m == 0 || g == 0) return false;
        boolean dateIsValid = true;
        try {
            LocalDate.of(g, m, d);
        } catch (DateTimeException e) {
            dateIsValid = false;
        }
        return dateIsValid;
    }

    //provjera email-a
    private boolean validanUnosEmail(String n) {
        return isValidEmail(n);
    }

    public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //provjera telefona
    private boolean validanUnosTelefon(String n) {
        return isValidTelephone(n);
    }

    public static boolean isValidTelephone(String nmbr) {
        //System.out.println(nmbr);
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Pattern pattern1 = Pattern.compile("\\d{3}-\\d{6}");
        Matcher matcher = pattern.matcher(nmbr);
        Matcher matcher1 = pattern1.matcher(nmbr);
        return (matcher.matches() || matcher1.matches());
    }

    //provjera za jmbg
    private boolean validanUnos(String n) {
        if (n.length() != 13) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) <= '9')) {
                return false;
            }
        }
        return !n.trim().isEmpty();
    }

    public static boolean isJmbgValid(String jmbg) {
        int politickaRegija = (jmbg.charAt(7) - '0') * 10 + (jmbg.charAt(8) - '0');
        if (politickaRegija < 0 || politickaRegija > 96) return false;
        int jedinstveniBroj = (jmbg.charAt(9) - '0') * 100 + (jmbg.charAt(10) - '0') * 10 + (jmbg.charAt(11) - '0');
        if (!((jedinstveniBroj > 0 && jedinstveniBroj <= 499) || (jedinstveniBroj >= 500 && jedinstveniBroj <= 999)))
            return false;
        int kontrolnaCifra = 11 - ((7 * ((jmbg.charAt(0) - '0') + (jmbg.charAt(6) - '0')) + 6 * ((jmbg.charAt(1) - '0') + (jmbg.charAt(7) - '0')) + 5 * ((jmbg.charAt(2) - '0') + (jmbg.charAt(8) - '0')) + 4 * ((jmbg.charAt(3) - '0') + (jmbg.charAt(9) - '0')) + 3 * ((jmbg.charAt(4) - '0') + (jmbg.charAt(10) - '0')) + 2 * ((jmbg.charAt(5) - '0') + (jmbg.charAt(11) - '0'))) % 11);
        if (kontrolnaCifra > 9) kontrolnaCifra = 0;
        if (kontrolnaCifra != (jmbg.charAt(12) - '0')) return false;
        return true;
    }

    @FXML
    public void initialize() {
        imeValidno = false;
        prezimeValidno = false;
        indeksValidan = false;
        jmbgValidan = false;
        datumValidan = false;
        emailValidan = false;
        telefonValidan = false;
        imeField.getStyleClass().add("poljeNijeIspravno");
        prezimeField.getStyleClass().add("poljeNijeIspravno");
        indexField.getStyleClass().add("poljeNijeIspravno");
        jmbgField.getStyleClass().add("poljeNijeIspravno");
        datumField.getStyleClass().add("poljeNijeIspravno");
        emailField.getStyleClass().add("poljeNijeIspravno");
        telefonField.getStyleClass().add("poljeNijeIspravno");
        imeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validnoImePrezime(n)) {
                    imeField.getStyleClass().removeAll("poljeNijeIspravno");
                    imeField.getStyleClass().add("poljeIspravno");
                    imeValidno = true;
                } else {
                    imeField.getStyleClass().removeAll("poljeIspravno");
                    imeField.getStyleClass().add("poljeNijeIspravno");
                    imeValidno = false;
                }
            }
        });
        prezimeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validnoImePrezime(n)) {
                    prezimeField.getStyleClass().removeAll("poljeNijeIspravno");
                    prezimeField.getStyleClass().add("poljeIspravno");
                    prezimeValidno = true;
                } else {
                    prezimeField.getStyleClass().removeAll("poljeIspravno");
                    prezimeField.getStyleClass().add("poljeNijeIspravno");
                    prezimeValidno = false;
                }
            }
        });
        indexField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanIndeks(n)) {
                    indexField.getStyleClass().removeAll("poljeNijeIspravno");
                    indexField.getStyleClass().add("poljeIspravno");
                    indeksValidan = true;
                } else {
                    indexField.getStyleClass().removeAll("poljeIspravno");
                    indexField.getStyleClass().add("poljeNijeIspravno");
                    indeksValidan = false;
                }
            }
        });
        jmbgField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanUnos(n)) {
                    jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                    jmbgField.getStyleClass().add("poljeIspravno");
                    jmbgValidan = true;
                    String jmbg = jmbgField.getText();
                    String izdvojiDatum = jmbg.substring(0, 7);
                    if (datumField.getText().equals("")) {
                        jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgField.getStyleClass().add("poljeIspravno");
                        if (izdvojiDatum.equals(uporediSaJmbg) ) {
                            datumField.getStyleClass().removeAll("poljeNijeIspravno");
                            datumField.getStyleClass().add("poljeIspravno");
                        }
                    }
                    else {
                        jmbgField.getStyleClass().removeAll("poljeIspravno");
                        jmbgField.getStyleClass().add("poljeNijeIspravno");
                    }
                } else {
                    jmbgField.getStyleClass().removeAll("poljeIspravno");
                    jmbgField.getStyleClass().add("poljeNijeIspravno");
                    jmbgValidan = false;
                }
            }
        });
        datumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanUnosDatum(n)) {
                    datumField.getStyleClass().removeAll("poljeNijeIspravno");
                    datumField.getStyleClass().add("poljeIspravno");
                    datumValidan = true;
                    String jmbg = jmbgField.getText();
                    String izdvojiDatum = jmbg.substring(0, 7);
                    if (izdvojiDatum.equals(uporediSaJmbg) || jmbgField.getText().equals("")) {
                        datumField.getStyleClass().removeAll("poljeNijeIspravno");
                        datumField.getStyleClass().add("poljeIspravno");
                        jmbgField.getStyleClass().removeAll("poljeNijeIspravno");
                        jmbgField.getStyleClass().add("poljeIspravno");

                    }
                    else {
                        datumField.getStyleClass().removeAll("poljeIspravno");
                        datumField.getStyleClass().add("poljeNijeIspravno");
                    }
                } else {
                    datumField.getStyleClass().removeAll("poljeIspravno");
                    datumField.getStyleClass().add("poljeNijeIspravno");
                    datumValidan = false;
                }
            }
        });
        emailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanUnosEmail(n)) {
                    emailField.getStyleClass().removeAll("poljeNijeIspravno");
                    emailField.getStyleClass().add("poljeIspravno");
                    emailValidan = true;
                } else {
                    emailField.getStyleClass().removeAll("poljeIspravno");
                    emailField.getStyleClass().add("poljeNijeIspravno");
                    emailValidan = false;
                }
            }
        });
        telefonField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (validanUnosTelefon(n)) {
                    telefonField.getStyleClass().removeAll("poljeNijeIspravno");
                    telefonField.getStyleClass().add("poljeIspravno");
                    telefonValidan = true;
                } else {
                    telefonField.getStyleClass().removeAll("poljeIspravno");
                    telefonField.getStyleClass().add("poljeNijeIspravno");
                    telefonValidan = false;
                }
            }
        });
    }
    @FXML
    public void dugmeKliknuto(ActionEvent actionEvent) {
        String mjesto = mjestoField.getEditor().getText();
        String ime = imeField.getText();
        String prezime = prezimeField.getText();
        String datum = datumField.getText();
        String jmbg = jmbgField.getText();
        String izdvojiDatum = "";
        if (jmbg.length() == 13) izdvojiDatum = jmbg.substring(0, 7);
        if (isDateValid(datum)) {
            datumValidan = true;
        } else {
            datumValidan = false;
            datumField.getStyleClass().add("poljeNijeIspravno");
        }
        if (isJmbgValid(jmbg) && izdvojiDatum != "" && izdvojiDatum.equals(uporediSaJmbg)) { ///OVDJE
            jmbgValidan = true;
        } else {
            jmbgValidan = false;
            jmbgField.getStyleClass().add("poljeNijeIspravno");
        }
        String email = emailField.getText();
        if (isValidEmail(email)) {
            emailValidan = true;

        } else {
            emailValidan = false;
            emailField.getStyleClass().add("poljeNijeIspravno");
        }
        telefonValidan = isValidTelephone(telefonField.getText());
        if (telefonValidan) telefonField.getStyleClass().add("poljeIspravno");
        if (formularValidan()) {
            System.out.println("Student: " + ime + " " + prezime + " ( " + indexField.getText() + " )");
            System.out.println("JMBG: " + jmbg + ", datum rođenja: " + datumZaIspis+", mjesto rodđenja: "+ mjesto);
            System.out.println("Ulica stanovanja: " + adresaField.getText() + ",broj telefona: " + telefonField.getText());
            System.out.println("Email adresa: " + email);
            System.out.println(statusField.getValue().toString() + " student, smjer: " + smjerField.getValue().toString() + " godina: " + godinaField.getValue());
            if (pripadnostField.isSelected()) System.out.println("Postoji neka od boračke pripadnosti");
            else System.out.println("Ne postoji nikakva boračka pripadnost");
        }
        if (!formularValidan()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nije validno");
            alert.setHeaderText("Popunjeni formular nije validan");
            alert.setContentText("Podaci označeni crvenom bojom su pogrešni, ili nedostaju");
            alert.show();
        }
    }
}
