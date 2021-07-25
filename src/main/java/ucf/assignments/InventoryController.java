/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 christopher polanco
 */
package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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

    @FXML
    public Label nameError;
    @FXML
    public Label serialError;
    @FXML
    public Label valueError;
    @FXML
    public TextField textFieldValue;
    @FXML
    public TextField textFieldsNumber;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField filterBar;
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
    public Button addRow;
    public Button openBtn;
    public Button saveBtn;
    public Button saveHTMLBtn;
    public Button saveJSONBtn;

    String name;
    Float value;
    String serial;

    // initialize an ObservableList to view on TableView GUI
    public ObservableList<Table> dataList = FXCollections.observableArrayList();

    checker c = new checker();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        tableView.setPlaceholder(new Label("You shouldn't be seeing this... Add some data?"));

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        sNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Serial"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // add to ObservableList example values on GUI
        Table ex1 = new Table("$399.00", "AXB124AXY3", "Xbox One");
        Table ex2 = new Table("$599.99", "S40AZBDE47", "Samsung TV");
        Table ex3 = new Table("$14.99", "EZGGLOL420", "Lemon Pledge");
        Table ex4 = new Table("$4.20", "SD51S2DETX", "Colt 45");
        dataList.addAll(ex1, ex2, ex3, ex4);

        // initialize a FilteredList
        // create a Listener to check inputValue
        // check if inputs are null or empty
        // Set String equal to inputValue
        // if the inputValue (for search bar) contains data from the ValueColumn, return true
        // if the inputValue (for search bar) contains data from the SerialNumberColumn, return true
        // if the inputValue (for search bar) contains data from the NameColumn, return true
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

        // initialize a SortedList with the new List created by the FilteredList
        // compare the SortedList to the TableView
        // set the values from the SortedList to TableView
        SortedList<Table> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public int lengthOfDataList() {

        // get size of current data list
        return dataList.size();
    }

    public boolean checkValue() {
        try {

            // get value from TextField
            // initialize a String to the value from TextField
            // if the String does NOT contain a number, return false
            // else,
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            value = Float.valueOf(ex5.getValue());
            String otherValue = ex5.getValue();
            if (!c.isDigit(otherValue)) {
                return false;
            } else {
                valueError.setText("Value Status: OKAY!");
            }
        } catch (NumberFormatException e) {
            valueError.setText("ERROR: Input a number");
        } catch (Exception e) {
            valueError.setText("ERROR?");
        }

        return true;
    }

    public String getValue() {
        try {

            // get data from value TextField
            // cast the string from TextField into a float
            // format a new string that contains the float value
            // return new string
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            value = Float.valueOf(ex5.getValue());
            String fixedValue;
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            fixedValue = formatter.format(value);
            return fixedValue;

        } catch (NumberFormatException e) {
            return "";
        }
    }

    public boolean checkName() {
        try {

            // get data from name TextField
            // if data is within 2 - 256 char, then set the error status to "OKAY" and return true
            // else return false with error
            Table ex5 = new Table(textFieldValue.getText(), textFieldsNumber.getText(), textFieldName.getText());
            name = ex5.getName();
            if (name.length() >= 2 && name.length() <= 256) {
                nameError.setText("Name Status: OKAY!");
            } else {
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

            // initialize an ArrayList
            // get data from current TableView
            // add data to ArrayList
            // if ArrayList contains data from TableView, then return false
            // if ArrayList is empty, then return false
            // if ArrayList data length is NOT 10 char, then return false
            // if ArrayList data contains special char, then return false
            // else data is okay to be inputted and return true
            List<String> serialData = new ArrayList<>();
            for (Table item : tableView.getItems()) {
                serialData.add(sNumberColumn.getCellObservableValue(item).getValue());
            }

            if (serialData.contains(serial)) {
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
            } else {
                serialError.setText("Serial Status: OKAY!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @FXML
    public void addButton(ActionEvent actionEvent) {

        // call methods that validate user input for the three textFields
        // if all three boolean methods return true, then add data to tableView
        checkValue();
        checkSerial();
        checkName();
        if (checkName() && checkSerial() && checkValue()) {
            Table ex5 = new Table(getValue(), textFieldsNumber.getText(), textFieldName.getText());
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
        // initialize a FileChooser
        // set extension filters such as .tsv, .html, .json
        // open a dialog box
        // if the file is NOT empty or NULL, then open the selected file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Table Files", "*.txt", "*.html", "*.tsv", "*.json"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Table-Separated-Values", "*.txt"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JavaScript Object Notation", "*.json"));
        Stage stage = (Stage) root.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }

    @FXML
    public void saveTSVButtonAction(ActionEvent event) {

        // initialize FileChooser
        // set a filter to save file as .txt with the extension .tsv before the filter
        // open save dialog box
        // initialize a file writer
        // write to file column names in tabular format
        // initialize an ArrayList
        // for i = 0; get the size of the array list, increment var i
        // add to array list set values from table in tabular format
        // loop ends when end of list is reached
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save this file");
        fileChooser.setInitialFileName("New Table" + ".tsv");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        Stage stage = (Stage) root.getScene().getWindow();
        try {

            File file = fileChooser.showSaveDialog(stage);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
            writer.write("Value ($)\t");
            writer.write("Serial Number\t");
            writer.write("Name\n");
            fileChooser.setInitialDirectory(file.getParentFile());
            List<List<String>> arrList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                arrList.add(new ArrayList<>());
                arrList.get(i).add(table.value.get() + "\t");
                arrList.get(i).add("\t" + table.serial.get());
                arrList.get(i).add("\t" + table.name.get() + "\n");
            }

            // for j = 0; as long as the ArrayList is greater than j, increment j
            // for i = 0; as long as the string List size is greater than i, increment i
            // write to file the new list created
            for (List<String> strings : arrList) {
                for (String string : strings) {
                    writer.write(string);
                }
            }
            writer.close();
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void saveHTMLButtonAction(ActionEvent event) {

        // initialize FileChooser
        // set a filter to save file as .html
        // open save dialog box
        // initialize a file writer
        // write to file html table format
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save this file");
        fileChooser.setInitialFileName("New Table");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html"));
        Stage stage = (Stage) root.getScene().getWindow();
        try {
            File file = fileChooser.showSaveDialog(stage);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
            writer.write("""
                    <table>
                      <tr>
                        <th>Value ($)</th>
                        <th>Serial Number</th>
                        <th>Name</th>
                      </tr>
                      <tr>
                    """);
            fileChooser.setInitialDirectory(file.getParentFile());

            // initialize an ArrayList
            // for i = 0; i is less than the size of table items, increment i
            // add to ArrayList the data from valueColumn
            List<List<String>> valueList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                valueList.add(new ArrayList<>());
                valueList.get(i).add("<td>" + table.value.get() + "</td>\n");
            }

            // initialize an ArrayList
            // for i = 0; i is less than the size of table items, increment i
            // add to ArrayList the data from serialColumn
            List<List<String>> sNumberList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                sNumberList.add(new ArrayList<>());
                sNumberList.get(i).add("<td>" + table.serial.get() + "</td>\n");
            }

            // initialize an ArrayList
            // for i = 0; i is less than the size of table items, increment i
            // add to ArrayList the data from nameColumn
            List<List<String>> nameList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                nameList.add(new ArrayList<>());
                nameList.get(i).add("<td>" + table.name.get() + "</td>\n");
            }

            // for j = 0; j is less than the size of the value list, increment j
            // set all list at j value index
            // for i = 0; set the value of the index to and Cast to a String
            // write to file the String from each list
            // close html table format
            for (int j = 0; j < valueList.size(); j++) {
                List<String> value = valueList.get(j);
                List<String> serial = sNumberList.get(j);
                List<String> name = nameList.get(j);
                for (int i = 0; i < value.size(); i++) {
                    String valueString = value.get(i);
                    String serialString = serial.get(i);
                    String nameString = name.get(i);
                    writer.write("<tr>\n");
                    writer.write(valueString + " " + serialString + " " + nameString + "</tr>\n");
                }
            }
            writer.write("</table>");
            writer.close();
        } catch (Exception ignored) {
        }
    }

    @FXML
    public void saveJSONButtonAction(ActionEvent event) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save this file");
        fileChooser.setInitialFileName("New Table");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JavaScript Object Notation", "*.json"));
        Stage stage = (Stage) root.getScene().getWindow();
        try {

            File file = fileChooser.showSaveDialog(stage);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
            String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";

            writer.write(jsonString);
            fileChooser.setInitialDirectory(file.getParentFile());
            List<List<String>> valueList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                valueList.add(new ArrayList<>());
                valueList.get(i).add(table.value.get());
            }

            List<List<String>> sNumberList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                sNumberList.add(new ArrayList<>());
                sNumberList.get(i).add(table.serial.get());
            }

            List<List<String>> nameList = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Table table = tableView.getItems().get(i);
                nameList.add(new ArrayList<>());
                nameList.get(i).add(table.name.get());
            }

/*            for (int j = 0; j < valueList.size(); j++) {
                List<String> value = valueList.get(j);
                List<String> serial = sNumberList.get(j);
                List<String> name = nameList.get(j);
                //System.out.print(valueList);
                for (int i = 0; i < value.size(); i++) {
                    String valueString = value.get(i);
                    String serialString = serial.get(i);
                    String nameString = name.get(i);
                    System.out.print(valueString + " " + serialString + " " + nameString + "</tr>\n");
                    writer.write("<tr>\n");
                    writer.write(valueString + " " + serialString + " " + nameString + "</tr>\n");
                }
            }*/
            writer.close();
        } catch (Exception ignored) {
        }
    }
}
