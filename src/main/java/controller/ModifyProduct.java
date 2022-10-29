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

/**
 * The controller for interaction between Product.java and ModifyProduct.fxml
 *
 * @author Blake Ramsey
 */
public class ModifyProduct implements Initializable {

    public static Product modifiedProduct;

    private static ObservableList<Part> partsList;
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


    /**
     * This method initializes the ModifyProduct controller after root element has been processed.
     *
     * @param url Resolves relative paths for root object, or null if location is not known.
     *
     * @param resourceBundle Resources used to localize root object, or null if root object was not localized.
     */
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
        partsList = FXCollections.observableArrayList();
        partsList.addAll(modifiedProduct.getAllAssociatedParts());
        associatedPartsTableView.setItems(partsList);
        associatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(ModifyProduct.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method adds selected item to partsList.
     *
     * @param actionEvent actionEvent is triggered by clicking Add button.
     */
    public void onAddButtonClick(ActionEvent actionEvent) {
        Part partToAdd = partsTableView2.getSelectionModel().getSelectedItem();
        if (partToAdd != null) {
            partsList.add(partToAdd);
        }
    }

    /**
     * This method removes part from partsList, after confirming that the user wants it to be removed.
     *
     * @param actionEvent actionEvent is triggered by clicking Remove button.
     */
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

    /**
     * This method take information from text fields (edited or not) and partsList and uses them to generate
     * a new product, with appropriate exceptions being thrown when fields are filled in incorrectly. This method invokes windowloader method in order to return
     * to previous window.
     *
     * @param actionEvent The actionEvent generated by the Save button being clicked.
     * @throws IOException
     */
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
            windowLoader("/view/main-window.fxml", modProductSaveBtn, 950, 400);
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

    /**
     * This method cancels and progress made in current window, and returns to previous window.
     *
     * @param actionEvent actionEvent is triggered by clicking cancel button.
     * @throws IOException
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        windowLoader("/view/main-window.fxml", modProductCancelBtn, 950, 400);
    }

    /**
     * This method allows users to search for parts in Inventories allParts list, and searches for a parts
     * name (or partial name) and ID.
     *
     * @param actionEvent This actionEvent is generated by searching (i.e. hitting Return / Enter) in the search bar.
     */
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
            //Do nothing.
        }
    }
}
