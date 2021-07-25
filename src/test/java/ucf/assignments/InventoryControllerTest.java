package ucf.assignments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    // given
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
        //given
        Label valueTestLabel = bot.lookup("#valueError").queryAs(Label.class);

        // when
        assertNotNull(valueTestLabel);
        bot.clickOn("#textFieldValue");
        bot.write("123.17");
        bot.clickOn("#addRow");

        // then
        assertEquals("Value Status: OKAY!", valueTestLabel.getText());
    }

    @Test
    void testSerialNumberInput(FxRobot bot) {
        // given
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);

        // when
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXXXX");
        bot.clickOn("#addRow");

        // then
        assertEquals("Serial Status: OKAY!", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberInput_isAlphanumeric(FxRobot bot) {
        // given
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);

        // when
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXX!@");
        bot.clickOn("#addRow");

        // then
        assertEquals("ERROR: Serial cannot contain special characters", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberInput_isEmpty(FxRobot bot) {
        // given
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);

        // when
        assertNotNull(sNumberTestLabel);
        bot.clickOn("#textFieldsNumber");
        bot.write("");
        bot.clickOn("#addRow");

        // then
        assertEquals("ERROR: Field is empty", sNumberTestLabel.getText());
    }

    @Test
    void testSerialNumberDuplicationInput(FxRobot bot) {
        // given
        Label sNumberTestLabel = bot.lookup("#serialError").queryAs(Label.class);

        // when
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

        // then
        assertEquals("ERROR: Serial Duplication!", sNumberTestLabel.getText());
    }

    @Test
    void testNameInput(FxRobot bot) {
        // given
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);

        // when
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("test");
        bot.clickOn("#addRow");

        // then
        assertEquals("Name Status: OKAY!", nameTestLabel.getText());
    }

    @Test
    void testLess_Than_Two_Characters(FxRobot bot) {
        // given
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);

        // when
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("N");
        bot.clickOn("#addRow");

        // then
        assertEquals("ERROR: Name must be between 2 and 256 inclusive characters", nameTestLabel.getText());
    }

    @Test
    void testMore_Than_256_Characters(FxRobot bot) {
        // given
        Label nameTestLabel = bot.lookup("#nameError").queryAs(Label.class);

        // when
        assertNotNull(nameTestLabel);
        bot.clickOn("#textFieldName");
        bot.write("c99aQvP9AjwD08WKtQCJxfd5Il8z2Fdjd4ybFkNlcZXbdq55pLTJS9JXSzmtsHVlglKrkhUdbun" +
                "67NRP7aeM0PHZJt7YYl5xVI69csP5rCGGhBu01gcr1hwBRASjIUwjMAsUTUurP7xaDHI3prFgxLzBw4q8CqYWK" +
                "fN3t6rPaTAkfgrNPuxhx5a3Ksp9qk5oKcQpVazYtGSy0dvRGAVG4A1N8NP2LPOUc4auSTNc0S0zlzRSm1Tm8GOdlT" +
                "MsxQKfF");
        bot.clickOn("#addRow");

        // then
        assertEquals("ERROR: Name must be between 2 and 256 inclusive characters", nameTestLabel.getText());
    }

    @Test
    void add100Items(FxRobot bot) {

        // when
        int x = inventory.lengthOfDataList();

        // given
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

        // then
        assertEquals(100, x);
    }

/*    @Test
    void deletionTest(FxRobot bot) {
        TableColumn table = bot.lookup("#nameColumn").queryAs(inventory.nameColumn.getCellValueFactory().toString());
        bot.clickOn("#textFieldValue");
        bot.write("123.45");
        bot.clickOn("#textFieldsNumber");
        bot.write("XXXXXXXXXX");
        bot.clickOn("#textFieldName");
        bot.write("Lemon Pledge");
        bot.clickOn("#addRow");
        bot.clickOn(String.valueOf(table.getFocusModel().getFocusedCell()));
    }*/
}