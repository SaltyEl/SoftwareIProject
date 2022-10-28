package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ModifyProduct implements Initializable {

    public static Product modifiedProduct;

    private ObservableList<Part> partsList = FXCollections.observableArrayList();
    public TableColumn<Part, Double> partCostCol1;
    public TableColumn<Part, Integer> partInventoryCol1;
    public TableColumn<Part, String> partNameCol1;
    public TableColumn<Part, Integer> partIdCol1;
    public Button modProductCancelBtn;
    public Button modProductSaveBtn;
    public Button modProductRemoveBtn;
    public Button modProductAddBtn;
    public TextField modProductSearchTxt;
    public TextField modProductPriceTxt;
    public TextField modProductInvTxt;
    public TextField modProductMinTxt;
    public TextField modNameTxt;
    public TextField modProductIDTxt;
    public TextField modProductMaxTxt;
    public TableView<Part> associatedPartsTableView;
    public TableView<Part> partsTableView2;
    public TableColumn<Part, Integer> associatedPartsIDCol;
    public TableColumn<Part, String> associatedPartsNameCol;
    public TableColumn<Part, Integer> associatedPartsInventoryCol;
    public TableColumn<Part, Double> associatedPartsCostCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Fill in Product Details
        modProductIDTxt.setText(Integer.toString(modifiedProduct.getId()));
        modNameTxt.setText(modifiedProduct.getName());
        modProductInvTxt.setText(Integer.toString(modifiedProduct.getStock()));
        modProductPriceTxt.setText(Double.toString(modifiedProduct.getPrice()));
        modProductMaxTxt.setText(Integer.toString(modifiedProduct.getMax()));
        modProductMinTxt.setText(Integer.toString(modifiedProduct.getMin()));

        //Parts Table - Displays Part ID, Name, Inventory and Price to table for each part.
        partsTableView2.setItems(Inventory.getAllParts());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Associated Parts Table
        partsList = modifiedProduct.getAllAssociatedParts();
        associatedPartsTableView.setItems(partsList);
        associatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void windowLoader (ActionEvent actionEvent, String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ModifyProduct.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        Part partToAdd = partsTableView2.getSelectionModel().getSelectedItem();
        if (partToAdd != null) {
            partsList.add(partToAdd);
        }
    }

    public void onRemoveButtonClick(ActionEvent actionEvent) {
        try {
            Part partToRemove = associatedPartsTableView.getSelectionModel().getSelectedItem();
            if (partToRemove != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && ButtonType.OK == result.get()) {
                    partsList.remove(partToRemove);
                }
            }
        }
        catch(Exception e){
                //Do nothing.
            }
        }

    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        Product productToMod;
        int indexOfProduct;
        try {

            indexOfProduct = Inventory.getAllProducts().indexOf(modifiedProduct);
            String productName = modNameTxt.getText();
            if (productName.isEmpty()) {
                throw new Exception("Product must have a name.");
            }
            int productInv = Integer.parseInt(modProductInvTxt.getText());
            double price = Double.parseDouble(modProductPriceTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());

            if (min >= max) {
                throw new Exception("Min must be less than max");
            }
            if (productInv < min || productInv > max) {
                throw new Exception("Inventory must be between min and max");
            }

            productToMod = new Product(modifiedProduct.getId(), productName, price, productInv, min, max);
            if (partsList.size() > 0) {
                for (Part part : partsList) {
                    productToMod.addAssociatedPart(part);
                }
            }


            Inventory.updateProduct(indexOfProduct, productToMod);
            windowLoader(actionEvent, "/view/main-window.fxml", modProductSaveBtn, 950, 400);
        }
        catch(NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/main-window.fxml", modProductCancelBtn, 950, 400);
    }

    public void getPartsResultsHandler2(ActionEvent actionEvent) {
        String searchText = modProductSearchTxt.getText();

        ObservableList<Part> returnedParts = Inventory.lookUpPart(searchText);
        Part idPartResult = null;
        try {
            if (returnedParts.size() != 0) {
                partsTableView2.setItems(returnedParts);
            }
            else {
                Scanner scnr = new Scanner(searchText);
                if (scnr.hasNextInt()){
                    int textNumSearch = Integer.parseInt(searchText);
                    idPartResult = Inventory.lookUpPart(textNumSearch);
                    if (idPartResult != null) {
                        TableView.TableViewSelectionModel<Part> selectionModel = partsTableView2.getSelectionModel();
                        selectionModel.select(idPartResult);
                    }
                }
            }
            if (idPartResult == null && returnedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Parts");
                alert.setContentText("No matching part found");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException e){
            //Do Nothing
        }
    }
}
