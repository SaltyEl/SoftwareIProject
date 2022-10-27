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

public class ModifyPart implements Initializable {

    public Button modifyPartCancelButton;
    public RadioButton modifyPartInHouseRB;
    public RadioButton modifyPartOutsourcedRB;
    public Button modifyPartSaveBtn;
    public TextField modifyPartIdTxt;
    public TextField modifyPartNameTxt;
    public TextField modifyPartInventoryTxt;
    public TextField modifyPartCostTxt;
    public TextField modifyPartMaxTxt;
    public TextField modifyPartMinTxt;
    public ToggleGroup modifyPart;

    public static Part modifiedPart;
    public TextField modifyPartVariableTxt;
    public Label modPartVariableLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (modifiedPart instanceof InHouse) {
            modifyPartInHouseRB.setSelected(true);
            modifyPartVariableTxt.setText(Integer.toString(((InHouse) modifiedPart).getMachineId()));
            modPartVariableLabel.setText("Machine ID");
        }
        else {
            modifyPartOutsourcedRB.setSelected(true);
            Outsourced outsourcedPart = (Outsourced)modifiedPart;
            modifyPartVariableTxt.setText(outsourcedPart.getCompanyName());
            modPartVariableLabel.setText("Company Name");

        }
        modifyPartIdTxt.setText(Integer.toString(modifiedPart.getId()));
        modifyPartNameTxt.setText(modifiedPart.getName());
        modifyPartInventoryTxt.setText(Integer.toString(modifiedPart.getStock()));
        modifyPartCostTxt.setText(Double.toString(modifiedPart.getPrice()));
        modifyPartMaxTxt.setText(Integer.toString(modifiedPart.getMax()));
        modifyPartMinTxt.setText(Integer.toString(modifiedPart.getMin()));
    }

    public void windowLoader (ActionEvent actionEvent, String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ModifyPart.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/main-window.fxml", modifyPartCancelButton, 1000, 400);
    }


    public void onSaveButtonClick(ActionEvent actionEvent) throws IOException {
        Part partToMod;
        int indexOfPart;
        try {

            indexOfPart = Inventory.getAllParts().indexOf(modifiedPart);
            int id = modifiedPart.getId();
            String name = modifyPartNameTxt.getText();
            int stock = Integer.parseInt(modifyPartInventoryTxt.getText());
            double price = Double.parseDouble(modifyPartCostTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            if (modifyPartInHouseRB.isSelected()) {
                int machineID = Integer.parseInt(modifyPartVariableTxt.getText());
                partToMod = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(indexOfPart, partToMod);
            }
            else if (modifyPartOutsourcedRB.isSelected()) {
                String companyName = modifyPartVariableTxt.getText();
                partToMod = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(indexOfPart, partToMod);
            }
            windowLoader(actionEvent, "/view/main-window.fxml", modifyPartSaveBtn, 1000, 400);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();
        }

    }

    public void onModOutsourcedBtnClick(ActionEvent actionEvent) {
        modPartVariableLabel.setText("Company Name");
    }

    public void onModInHouseBtnClick(ActionEvent actionEvent) {
        modPartVariableLabel.setText("Machine ID");
    }
}
