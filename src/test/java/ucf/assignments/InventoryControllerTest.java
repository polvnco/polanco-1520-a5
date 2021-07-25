package ucf.assignments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class InventoryControllerTest {

    InventoryController inventory = new InventoryController();

    @Start
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Inventory.fxml")));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @Test
    void testValueInput(FxRobot bot) {
        Label valueTestLabel = bot.lookup("#valueError").queryAs(Label.class);
        assertNotNull(valueTestLabel);
        bot.clickOn("#textFieldValue");
        bot.write("123.17");
        bot.clickOn("#addRow");
        assertEquals("Value Status: OKAY!", valueTestLabel.getText());
    }

    @Test
    void testSerialNumberInput(FxRobot bot) {
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXXXX");
        bot.clickOn("#addRow");
        assertEquals("Serial Status: OKAY!", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberInput_isAlphanumeric(FxRobot bot) {
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXX!@");
        bot.clickOn("#addRow");
        assertEquals("ERROR: Serial cannot contain special characters", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberInput_isEmpty(FxRobot bot) {
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("");
        bot.clickOn("#addRow");
        assertEquals("ERROR: Field is empty", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberDuplicationInput(FxRobot bot) {
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldValue");
        bot.write("123.45");
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXXXX");
        bot.clickOn("#textFieldName");
        bot.write("TestCase");
        bot.clickOn("#addRow");
        bot.clickOn("#textFieldValue");
        bot.eraseText(6);
        bot.write("123.45");
        bot.clickOn("#textFieldsNumber");
        bot.eraseText(10);
        bot.write("XXXXXXXXXX");
        bot.clickOn("#textFieldName");
        bot.eraseText(8);
        bot.write("TestCaseAgain");
        bot.clickOn("#addRow");
        assertEquals("ERROR: Serial Duplication!", sNumberTestLabel.getText());
    }

    @Test
    void testNameInput(FxRobot bot) {
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("test");
        bot.clickOn("#addRow");
        assertEquals("Name Status: OKAY!", nameTestLabel.getText());
    }

    @Test
    void testLess_Than_Two_Characters(FxRobot bot) {
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("N");
        bot.clickOn("#addRow");
        assertEquals("ERROR: Name must be between 2 and 256 inclusive characters", nameTestLabel.getText());
    }

    @Test
    void testMore_Than_256_Characters(FxRobot bot) {
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("c99aQvP9AjwD08WKtQCJxfd5Il8z2Fdjd4ybFkNlcZXbdq55pLTJS9JXSzmtsHVlglKrkhUdbun" +
                "67NRP7aeM0PHZJt7YYl5xVI69csP5rCGGhBu01gcr1hwBRASjIUwjMAsUTUurP7xaDHI3prFgxLzBw4q8CqYWK" +
                "fN3t6rPaTAkfgrNPuxhx5a3Ksp9qk5oKcQpVazYtGSy0dvRGAVG4A1N8NP2LPOUc4auSTNc0S0zlzRSm1Tm8GOdlT" +
                "MsxQKfF");
        bot.clickOn("#addRow");
        assertEquals("ERROR: Name must be between 2 and 256 inclusive characters", nameTestLabel.getText());
    }

    @Test
    void add100Items(FxRobot bot) {
        int x = inventory.lengthOfDataList();
        for (int i = 1000000000; x < 100; i++){
            String count = String.valueOf(i);
            bot.clickOn("#textFieldValue");
            bot.write(count);
            bot.clickOn("#textFieldsNumber");
            bot.write(count);
            bot.clickOn("#textFieldName");
            bot.write(count);
            bot.clickOn("#addRow");
            System.out.println(x);
            bot.clickOn("#textFieldName");
            bot.eraseText(10);
            bot.clickOn("#textFieldsNumber");
            bot.eraseText(10);
            bot.clickOn("#textFieldValue");
            bot.eraseText(10);
            x++;
        }
        assertEquals(100, x);
    }
    @Test
    void checkValue() {
    }

    @Test
    void getValue() {
    }

    @Test
    void checkName() {
    }

    @Test
    void checkSerial() {
    }

    @Test
    void addButton() {
    }

    @Test
    void deleteButtonAction() {
    }

    @Test
    void openButtonAction() {
    }

    @Test
    void saveTSVButtonAction() {
    }

    @Test
    void saveHTMLButtonAction() {
    }

    @Test
    void saveJSONButtonAction() {
    }
}