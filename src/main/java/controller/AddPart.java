package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddPart implements Initializable{

    public Button addPartCancelButton;
    public TextField addPartIdTxt;
    public TextField addPartNameTxt;
    public TextField addPartInventoryTxt;
    public TextField addPartCostTxt;
    public TextField addPartMaxTxt;
    public TextField addPartVariableTxt;
    public TextField addPartMinTxt;
    public RadioButton addPartInHouseRB;
    public RadioButton addPartOutSourcedRB;
    public Button addPartSaveBtn;
    public Label inHouseVSOutsourcedLabel;

    private String name;
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add part window initialized.");
    }

    private void windowLoader (ActionEvent actionEvent, String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddPart.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void onCancelClick (ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/main-window.fxml", addPartCancelButton, 1000, 400);
    }
    public void onSaveButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            if (!Inventory.getAllParts().isEmpty()) {
                Part part = Inventory.getAllParts().get((Inventory.getAllParts().size() - 1));
                int tempID = part.getId();
                id = tempID + 1;
            } else {
                id = 1;
            }

            String name = addPartNameTxt.getText();
            int inventory = Integer.parseInt(addPartInventoryTxt.getText());
            double cost = Double.parseDouble(addPartCostTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());

            if (min > max) {
                throw new Exception("Min should be less than Max.");
            }
            if ((inventory > max) || (inventory < min)) {
                throw new Exception("Inventory must be between Min and Max");
            }

            if (addPartInHouseRB.isSelected()) {
                int machineID = Integer.parseInt(addPartVariableTxt.getText());
                InHouse newPart = new InHouse(id, name, cost, inventory, min, max, machineID);
                Inventory.addPart(newPart);
            } else {
                String companyName = addPartVariableTxt.getText();
                Outsourced newPart = new Outsourced(id, name, cost, inventory, min, max, companyName);
                Inventory.addPart(newPart);
            }

            windowLoader(actionEvent, "/view/main-window.fxml", addPartCancelButton, 1000, 400);
        }
        catch(NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }


    public void onOutsourced(ActionEvent actionEvent) {
        inHouseVSOutsourcedLabel.setText("Company Name");
    }

    public void onInHouse(ActionEvent actionEvent) {
        inHouseVSOutsourcedLabel.setText("Machine ID");
    }
}
