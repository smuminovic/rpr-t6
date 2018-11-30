package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sample.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MainTest {
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    @Test
    public void start (FxRobot robot) {
        TextField imeField= robot.lookup("#imeField").queryAs(TextField.class);
        assertEquals("", imeField.getText());
    }
    @Test
    public void nameFieldTest(FxRobot robot) {
        TextField nameField = robot.lookup("#imeField").queryAs(TextField.class);
        robot.clickOn("#imeField").write("Samira");
        assertEquals("Samira", nameField.getText());
    }
    @Test
    public void indeksFieldTest(FxRobot robot) {
        TextField indeksField = robot.lookup("#indexField").queryAs(TextField.class);
        robot.clickOn("#indexField").write("17582");
        assertEquals("17582", indeksField.getText());
    }
}

