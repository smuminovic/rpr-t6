package sample;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {
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
//provjera za ime i prezime
    private boolean validnoImePrezime(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z'))
                return false;
        }
        return !n.trim().isEmpty();
    }
    private boolean validanIndeks(String n) {
        if (n.length() > 5) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= '0' && n.charAt(i) < '9')) {
                return false;
            }
        }
        return !n.trim().isEmpty();
    }

}
