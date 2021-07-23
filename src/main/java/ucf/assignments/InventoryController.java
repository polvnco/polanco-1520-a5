package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML public Label nameError;
    @FXML public Label serialError;
    @FXML public Label valueError;
    @FXML public TextField textFieldValue;
    @FXML public TextField textFieldsNumber;
    @FXML public TextField textFieldName;
    @FXML public TextField filterBar;
    @FXML
    public TableView<Table> tableView;
    @FXML
    public TableColumn<Object, String> valueColumn;
    @FXML
    public TableColumn<Object, String> sNumberColumn;
    @FXML
    public TableColumn<Object, String> nameColumn;
    @FXML
    public Pane root;

    String name;
    Float value;
    String serial;

    //public ObservableList<Table> data = FXCollections.observableArrayList();

    public ObservableList<Table> dataList = FXCollections.observableArrayList();
    checker c = new checker();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        tableView.setPlaceholder(new Label("You shouldn't be seeing this..."));

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        sNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Serial"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        Table ex1 = new Table("$399.00", "AXB124AXY3", "Xbox One");
        Table ex2 = new Table("$599.99", "S40AZBDE47", "Samsung TV");
        Table ex3 = new Table("$14.99", "EZGGLOL420", "Lemon Pledge");
        Table ex4 = new Table("$4.20", "SD51S2DETX", "Colt 45");


        dataList.addAll(ex1, ex2, ex3, ex4);

        FilteredList<Table> filteredData = new FilteredList<>(dataList, b -> true);
        filterBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(table -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            if (table.getValue().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (table.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return table.getSerial().toLowerCase().contains(lowerCaseFilter);

        }));

        SortedList<Table> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    public boolean checkValue() {
        try {
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            value = Float.valueOf(ex5.getValue());
            String otherValue = ex5.getValue();
            //String formattedValue = String.format("%.02f", value);
            //System.out.println(formattedValue);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String newValue = formatter.format(value);
            System.out.println(newValue);
            if (!c.isDigit(otherValue)) {
                valueError.setText("ERROR: Input a number");
                return false;
            }
        } catch (NumberFormatException e) {
            valueError.setText("ERROR: Input a number");
        } catch (Exception e) {
            valueError.setText("ERROR?");
        }

        return true;
    }

    public boolean checkName() {
        try {
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            name = ex5.getName();
            if (name.length() >= 2 && name.length() <= 256) {
                nameError.setText("Name Status: OKAY!");
            }
            else {
                nameError.setText("ERROR: Name must be between 2 and 256 inclusive characters");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean checkSerial() {
        try {
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            serial = ex5.getSerial();
            //System.out.println(c.isDigitAlpha(serial));


            List<String> serialData = new ArrayList<>();
            for (Table item : tableView.getItems()) {
                serialData.add(sNumberColumn.getCellObservableValue(item).getValue());
            }

            if (serialData.contains(serial)) {
                System.out.println("");
                serialError.setText("ERROR: Serial Duplication!");
                return false;
            } else if (serial.isEmpty()) {
                serialError.setText("ERROR: Field is empty");
                return false;
            } else if (serial.length() != 10) {
                serialError.setText("ERROR: Serial length of XXXXXXXXXX");
                return false;
            } else if (!c.isDigitAlpha(serial)) {
                serialError.setText("ERROR: Serial cannot contain special characters");
                return false;
            }
            else {
                serialError.setText("Serial Status: OKAY!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
    @FXML
    public void addButton(ActionEvent actionEvent) {
        checkValue();
        checkSerial();
        checkName();
        //System.out.println(checkSerial());
        //System.out.println(checkName());
        if (checkName() && checkSerial()) {
            Table ex5 = new Table("$" + textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            dataList.add(ex5);
        }
    }

    @FXML
    public void deleteButtonAction(ActionEvent event) {
        // grab selected item
        // grab a table for it
        // check if it exists in a data list. then delete from data list
        ObservableList<Table> deletedDataList = tableView.getSelectionModel().getSelectedItems();
        System.out.println(deletedDataList);
        dataList.removeAll(deletedDataList);
    }

    public void openButtonAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open this shit");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        Stage stage = (Stage) root.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }

    @FXML
    public void saveButtonAction(ActionEvent event) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save this shit");
        fileChooser.setInitialFileName("New Table");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tab-Separated-Values", "*.tsv"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        Stage stage = (Stage) root.getScene().getWindow();
        try {

            File file = fileChooser.showSaveDialog(stage);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
            writer.write("Value ($)\t");
            writer.write("Serial Number\t");
            writer.write("Name\n");
            fileChooser.setInitialDirectory(file.getParentFile());
            List <List<String>> arrList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                arrList.add(new ArrayList<>());
                arrList.get(i).add(table.value.get() + "\t");
                arrList.get(i).add("\t" + table.serial.get());
                arrList.get(i).add("\t" + table.name.get() + "\n");
            }

            for (int i = 0; i < arrList.size(); i++) {
                for (int j = 0; j < arrList.get(i).size(); j++) {
                    System.out.print(arrList.get(i).get(j));
                    writer.write(arrList.get(i).get(j));
                }
            }
                writer.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}
