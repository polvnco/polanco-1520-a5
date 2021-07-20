package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

public class Table {
    private final SimpleStringProperty value;
    private final SimpleStringProperty serial;
    private final SimpleStringProperty name;

    public Table(String value, String serial, String name) {
        this.value = new SimpleStringProperty(value);
        this.serial = new SimpleStringProperty(serial);
        this.name = new SimpleStringProperty(name);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public String getSerial() {
        return serial.get();
    }

    public SimpleStringProperty serialProperty() {
        return serial;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
}
