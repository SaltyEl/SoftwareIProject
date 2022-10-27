package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;


public class MainWindow implements Initializable {
    public Button partAddButton;
    public Button productAddButton;
    public Button modifyPartButton;
    public TableView<Part> partsTableView;
    public TableView<Product> productsTableView;
    public TableColumn<Part, Integer> partIdCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Double> partCostCol;
    public TableColumn<Product, Integer> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productInventoryCol;
    public TableColumn<Product, Double> productCostCol;
    public Button deletePartButton;
    public Button productModifyButton;
    public Button productDeleteButton;
    public TextField productsSearchBar;
    public  TextField partsSearchBar;
    public Label productHasPartsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Window initialized.");

        //Parts Table - Displays Part ID, Name, Inventory and Price to table for each part.
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Products Table
        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    public void onPartsDeleteButtonClicked(ActionEvent actionEvent) {
        Part partToBeDeleted = partsTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(partToBeDeleted);
    }

    private void windowLoader (ActionEvent actionEvent, String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void onPartAddClick(ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/AddPart.fxml", partAddButton, 475, 425);
    }

    public void onPartsModifyButtonClick(ActionEvent actionEvent) throws IOException {
        ModifyPart.modifiedPart = partsTableView.getSelectionModel().getSelectedItem();
        windowLoader(actionEvent, "/view/ModifyPart.fxml", modifyPartButton, 475, 425);
    }

    public void onProductAddClick(ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/AddProduct.fxml", productAddButton, 850, 500);
    }

    public void onProductModifyButtonClick(ActionEvent actionEvent) throws IOException {
        ModifyProduct.modifiedProduct = productsTableView.getSelectionModel().getSelectedItem();
        windowLoader(actionEvent, "/view/ModifyProduct.fxml", productModifyButton, 850, 500);
    }

    public void onProductDeleteClick(ActionEvent actionEvent) {
        Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();
        if (productToDelete.getAllAssociatedParts().size() == 0) {
            Inventory.deleteProduct(productToDelete);
        }
        else {
            productHasPartsLabel.setText("This Product Has Parts");
        }
    }

    public void onExitClick(ActionEvent actionEvent) {
        System.out.println("Exit");
        System.exit(0);
    }

    //Main Window Part Search Bar Functionality
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String queryString = partsSearchBar.getText();

        ObservableList<Part> partNameSearchResult = Inventory.lookUpPart(queryString);

            try {
                if (partNameSearchResult.size() != 0) {
                    partsTableView.setItems(partNameSearchResult);
                }
                else {
                    int idSearch = Integer.parseInt(queryString);
                    Part idSearchResult = Inventory.lookUpPart(idSearch);
                    if (idSearchResult != null) {
                        TableView.TableViewSelectionModel<Part> selectionModel = partsTableView.getSelectionModel();
                        selectionModel.select(idSearchResult);
                        //partNameSearchResult.add(idSearchResult);
                    }
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

    //Main Window Product Search Bar Functionality
    public void getProductsResultsHandler(ActionEvent actionEvent) {
        String queryString = productsSearchBar.getText();

        ObservableList<Product> productNameSearchResult = Inventory.lookUpProduct(queryString);
        try {
            if (productNameSearchResult.size() != 0) {
                productsTableView.setItems(productNameSearchResult);
            }
            else {
                int productID = Integer.parseInt(queryString);
                Product productIDSearchResult = Inventory.lookUpProduct(productID);
                if (productIDSearchResult != null) {
                    TableView.TableViewSelectionModel<Product> selectionModel = productsTableView.getSelectionModel();
                    selectionModel.select(productIDSearchResult);
                }
            }
        }
        catch (NumberFormatException e){
            //Do nothing.
        }
    }
}