# Inventory TableView App

Hi All! This README.md is for fulfilling the requirement of
>User guide is complete and well-written.

>The user guide must be free of spelling and grammatical errors, and should reflect a university education.

## Running The App

After clicking on *run* in the Gradle ToolBar, the application will then be built and displayed  as a window.

## Add an Inventory Item

![Inventory App](https://s6.gifyu.com/images/Screenshot-2021-07-25-151056.png)

With the app open, click and enter information on the three TextFields.
Once the fields are complete, click on the "Add to List" button to see the inventory item entered inputted on the TableView.

Example of Adding Inventory Item:

![Add Item](https://s6.gifyu.com/images/addItem.gif)

## Delete an Inventory Item
To remove an item, first select a row that you wish to delete. Then click on the "Delete Selected Row" button.

Example of Deleting Inventory Item:
![Delete Item](https://s6.gifyu.com/images/deleteItem.gif)


## Edit an Inventory Item
To edit an item, double-click on the cell you wish to edit. Change the value of the cell and hit enter once finished editing.

If you were to try to add another inventory item with the same Serial Number, the Serial Error Console will show text.

Example of Editing Inventory Item:
![Edit Item](https://s6.gifyu.com/images/editItem.gif)

## Sort and Search Through Inventory Items
The "Keyword Search" bar at the top of the application will filter, search, and predicate the keyword a user enters and set the TableView items to only show which items contain the keyword.

The TableView has built-in sorting functions. Clicking on the columns allow you to sort by Ascending or Descending order.

Example of Sort and Search Inventory Items:
![Sort and Search Item](https://s6.gifyu.com/images/sortAndSearch.gif)

## Save and Open Inventory Items
This application allows you to save the table in two different formats. HTML and TSV (ends with a .txt extension to view).

The open button will allow you to open said saved files with an application that can read and open those files. NotePad++ (TSV) and Google Chrome (HTML)

Example of Saving and Opening Inventory Items:
![Save and Open Item](https://s6.gifyu.com/images/saveAndOpen2.gif)

## Error Handling
The application is coded with error handling of input values in the TextFields. 

### Value Error
The program will ensure the user enters a numeric value and prevent it if the input is NaN.

![Value Error](https://s6.gifyu.com/images/valueError.gif)

### Serial Errors
The application requests that the serial is unique (checks if the serial is already in the inventory), checks if the length is 10 characters, and if it contains special characters.

The add button will only work if the "Serial Status" is "OKAY!".

![Serial Error](https://s6.gifyu.com/images/serialError.gif)

### Name Error
The user must input characters between 2 and 256 inclusive.

The add button will prevent the user from adding inventory item if there is an error.

![Name Error](https://s6.gifyu.com/images/nameError.md.gif)

## Tests (TestFX)
Test cases were done with TestFX. The following dependencies are required to run these test cases.

```java
dependecies {
        testImplementation 'org.junit.jupiter:junit-jupiter-api:5.5.1'
        testImplementation "org.testfx:testfx-junit5:4.0.16-alpha"
        testImplementation group: 'org.assertj', name: 'assertj-core', version: '3.13.2'
        }
```

Great feature of TestFX is the use of the FxRobot. It allowed me to tell a robot to add values, click buttons, and much more.

Here's an example:

![TestFX](https://s6.gifyu.com/images/testFX.gif)

---
Made with ♥ by Christopher Polanco