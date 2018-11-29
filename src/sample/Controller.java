package sample;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);;
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
    public String uporediSaJmbg = "";
    public String datumZaIspis = "";
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
        if(s.length()<8)return false;
        int d = 0, m = 0, g = 0;
        String dan = "", mjesec = "", godina = "";
        if (s.length() == 8) {
            dan = s.substring(0, 2);
            mjesec = s.substring(2, 4);
            godina = s.substring(4, 8);
        }
        else if (s.length() == 10) {
            if (s.charAt(2) == s.charAt(5) && (s.charAt(2) == '.' || s.charAt(2) == '/' | s.charAt(2) == '-')) {
                dan = s.substring(0, 2);
                mjesec = s.substring(3, 5);
                godina = s.substring(6, 10);
            }
        }
        else {
            return false;
        }
        try {
            d = Integer.parseInt(dan);
            m = Integer.parseInt(mjesec);
            g = Integer.parseInt(godina);
        }catch (Exception ex){}
        datumZaIspis=""+ d+"/"+m+"/"+g;
        int novaG=Integer.parseInt(godina.substring(1,godina.length()));
        //uporediSaJmbg=""+dan+mjesec+novaG;
        //System.out.println("Uporedi:"+ uporediSaJmbg);
        if(d==0 || m==0 || g==0 )return false;
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
    public static boolean isValidEmail(String emailStr){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

}
