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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * The controller for interaction between models and main-window.fxml
 *
 * @author Blake Ramsey
 */
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

    /**
     * This method initializes the AddProduct controller after root element has been processed.
     *
     * @param url Resolves relative paths for root object, or null if location is not known.
     * @param resourceBundle Resources used to localize root object, or null if root object was not localized.
     */
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


    /**
     * This method deletes selected part, and provides confirmation that part should be deleted.
     *
     * @param actionEvent actionEvent is initiated by clicking deletePartButton
     */
    public void onPartsDeleteButtonClicked(ActionEvent actionEvent) {
        try {
            Part partToBeDeleted = partsTableView.getSelectionModel().getSelectedItem();
            if (partToBeDeleted == null) {
                throw new Exception();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partToBeDeleted);
            }
        } catch (Exception e) {
            //Do nothing.
        }
    }

    /**
     * This method directs user to the next appropriate window.<br><br> LOGICAL ERROR - Deleted redundant ActionEvent field, as
     * 'buttonClicked' Button was already performing the same duty.
     *
     * @param fxmlDoc The location of the window that should be loaded.
     * @param buttonClicked The button that is clicked.
     * @param width The width of window to be loaded.
     * @param height The height of window to be loaded.
     * @throws IOException
     */
    private void windowLoader (String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method invokes the windowLoader method to take user to AddPart window.
     *
     * @param actionEvent This actionEvent is triggered by partAddButton.
     * @throws IOException
     */
    public void onPartAddClick(ActionEvent actionEvent) throws IOException {
        windowLoader("/view/AddPart.fxml", partAddButton, 475, 425);
    }

    /**
     * This method takes the selected part, and sends it to the static 'modifiedPart' field located in
     * ModifyPart controller. This method then uses the windowLoader method to send user to ModifyPart window.
     * <br>
     * <br>
     * RUNTIME EXCEPTION - Caught nullptr runtime exception. Used this is catch in other methods as well.
     * @param actionEvent actionEvent is triggered by clicking modifyPart button.
     * @throws IOException
     */
    public void onPartsModifyButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            ModifyPart.modifiedPart = partsTableView.getSelectionModel().getSelectedItem();
            windowLoader("/view/ModifyPart.fxml", modifyPartButton, 475, 425);
        }
        catch (Exception npe) {
            //Do nothing.
        }
    }

    /**
     * This method invokes windowLoader method to send user to AddProduct window.
     * <br>
     * <br>
     * RUNTIME ERROR - Caught runtime error for nullptr.
     *
     * @param actionEvent actionEvent is triggered by productAddButton.
     * @throws IOException
     */
    public void onProductAddClick(ActionEvent actionEvent) throws IOException {
        windowLoader("/view/AddProduct.fxml", productAddButton, 850, 500);
    }

    /**
     * This method takes the selected product, and sends it to the static 'modifiedProduct' field located in
     * ModifyProduct controller. This method then uses the windowLoader method to send user to ModifyProduct window.
     *
     * @param actionEvent actionEvent is triggered by productModifyButton.
     * @throws IOException
     */
    public void onProductModifyButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            ModifyProduct.modifiedProduct = productsTableView.getSelectionModel().getSelectedItem();
            windowLoader("/view/ModifyProduct.fxml", productModifyButton, 850, 500);
        }
        catch(Exception npe){
            //Do nothing.
        }
    }

    /**
     * This method deletes selected product. This method has confirmation before deleting.
     *<br>
     * <br>
     * LOGIC ERROR - Confirmation Alert was running even if no product was selected. Added new Exception throw for
     * if the productToDelete was null to circumvent this.
     *
     * @param actionEvent actionEvent is triggered by clicking product delete button.
     */
    public void onProductDeleteClick(ActionEvent actionEvent) {
        try {
            Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();
            if (productToDelete == null) {
                throw new Exception();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (productToDelete.getAllAssociatedParts().size() < 1) {
                    Inventory.deleteProduct(productToDelete);
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Product");
                    alert2.setContentText("This Product Has Parts");
                    alert2.showAndWait();
                }
            }
        } catch (Exception e) {
            //Do nothing
        }
    }

    /**
     * Exits application.
     *
     * @param actionEvent actionEvent is triggered by exitButton.
     */
    public void onExitClick(ActionEvent actionEvent) {
        System.out.println("Exit");
        System.exit(0);
    }

    /**
     * This method allows users to search for parts in Inventories allParts list, and searches for a parts
     * name (or partial name) and ID.
     *
     * @param actionEvent This actionEvent is generated by searching (i.e. hitting Return / Enter) in the search bar.
     */
    public void getPartsResultsHandler(ActionEvent actionEvent) {
        String queryString = partsSearchBar.getText();

        ObservableList<Part> partNameSearchResult = Inventory.lookUpPart(queryString);
        Part idSearchResult = null;
            try {
                if (partNameSearchResult.size() != 0) {
                    partsTableView.setItems(partNameSearchResult);
                }
                else {
                    Scanner scnr = new Scanner(queryString);
                    if (scnr.hasNextInt()) {
                        int idSearch = Integer.parseInt(queryString);
                        idSearchResult = Inventory.lookUpPart(idSearch);
                        if (idSearchResult != null) {
                            TableView.TableViewSelectionModel<Part> selectionModel = partsTableView.getSelectionModel();
                            selectionModel.select(idSearchResult);
                        }
                    }
                }
                if (idSearchResult == null && partNameSearchResult.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product");
                    alert.setContentText("No matching product found");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                //ignore
            }
        }

    /**
     * This method allows users to search for products in Inventories allProducts list, and searches for a products
     * name (or partial name) and ID.
     *
     * @param actionEvent This actionEvent is generated by searching (i.e. hitting Return / Enter) in the search bar.
     */
    //Main Window Product Search Bar Functionality
    public void getProductsResultsHandler(ActionEvent actionEvent) {
        String queryString = productsSearchBar.getText();

        ObservableList<Product> productNameSearchResult = Inventory.lookUpProduct(queryString);
        Product productIDSearchResult = null;
        try {
            if (productNameSearchResult.size() != 0) {
                productsTableView.setItems(productNameSearchResult);
            }
            else {
                Scanner scnr = new Scanner(queryString);
                if (scnr.hasNextInt()) {
                    int productID = Integer.parseInt(queryString);
                    productIDSearchResult = Inventory.lookUpProduct(productID);
                    if (productIDSearchResult != null) {
                        TableView.TableViewSelectionModel<Product> selectionModel = productsTableView.getSelectionModel();
                        selectionModel.select(productIDSearchResult);
                    }
                }
            }
            if (productIDSearchResult == null && productNameSearchResult.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product");
                alert.setContentText("No matching product found");
                alert.showAndWait();
            }
        }
        catch (Exception e){
            //Do nothing.
        }
    }
}