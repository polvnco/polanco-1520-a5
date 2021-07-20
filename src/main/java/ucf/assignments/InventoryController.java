package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    public TextField textFieldValue;
    public TextField textFieldsNumber;
    public TextField textFieldName;
    @FXML
    private TableView<Table> tableView = new TableView();
    @FXML
    private TableColumn<Table, Integer> valueColumn = new TableColumn<>();
    @FXML
    private TableColumn<Table, String> sNumberColumn = new TableColumn<>();
    @FXML
    private TableColumn<Table, String> nameColumn = new TableColumn<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        tableView.setPlaceholder(new Label("You shouldn't be seeing this..."));

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        sNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Serial"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));



    }

    public void addButton(ActionEvent actionEvent) {
        System.out.println(textFieldsNumber.getText());
        tableView.getItems().add((new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText())));
    }
}
