package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {


    public TextField addProductIdTxt;
    public TextField addProductNameTxt;
    public TextField addProductMaxTxt;
    public TextField addProductMinTxt;
    public TextField addProductInvTxt;
    public TextField addProductPriceTxt;
    public TextField addProductSearchTxt;
    public Button addProductAddBtn;
    public Button addProductRemoveButton;
    public Button addProductSaveButton;
    public Button addProductCancelBtn;
    public TableColumn<Part, Integer> associatedPartIdCol;
    public TableColumn<Part, String> associatedPartNameCol;
    public TableColumn<Part, Integer> associatedPartInvCol;
    public TableColumn<Part, Double> associatedPartCostCol;
    public TableView<Part> partsTableView1;
    public TableColumn<Part, Integer> partIdCol1;
    public TableColumn<Part, String> partNameCol1;
    public TableColumn<Part, Integer> partInventoryCol1;
    public TableColumn<Part, Double> partCostCol1;
    public TableView<Part> associatedPartsTableView;

    private ObservableList<Part> partsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsList = FXCollections.observableArrayList();

        //Parts Table - Displays Part ID, Name, Inventory and Price to table for each part.
        partsTableView1.setItems(Inventory.getAllParts());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Associated Parts Table
        associatedPartsTableView.setItems(partsList);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        System.out.println("Add Product Initialized");
    }

    private void windowLoader (ActionEvent actionEvent, String fxmlDoc, Button buttonClicked, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddProduct.class.getResource(fxmlDoc.toString()));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        Stage stage = (Stage) buttonClicked.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*Add part to Observable partsList, which can be added to product. Update associated parts
    table view to show parts added.*/
    public void onPartAddClick(ActionEvent actionEvent) throws IOException {
        Part partToAdd = partsTableView1.getSelectionModel().getSelectedItem();
        partsList.add(partToAdd);
    }

    public void onCancelBtnClick(ActionEvent actionEvent) throws IOException {
        windowLoader(actionEvent, "/view/main-window.fxml", addProductCancelBtn, 950, 400);
    }

    //Functionality for adding new product
    public void onProductAddSaveClick(ActionEvent actionEvent) throws IOException {
        int id;
        if (!Inventory.getAllProducts().isEmpty()) {
            Product product = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1);
            int tempId = product.getId();
            id = tempId + 1;
        }
        else {
            id = 1;
        }
        String name = addProductNameTxt.getText();
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int stock = Integer.parseInt(addProductInvTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());

        Product newProduct = new Product(id, name, price, stock, min, max);
        if (!(partsList.isEmpty())){
            for (Part part : partsList) {
                newProduct.addAssociatedPart(part);
            }
        }
        Inventory.addProduct(newProduct);

        windowLoader(actionEvent, "/view/main-window.fxml", addProductSaveButton, 950, 400);
    }

    public void getPartsResultHandler1(ActionEvent actionEvent) {
        String searchText = addProductSearchTxt.getText();

        ObservableList<Part> returnedParts = Inventory.lookUpPart(searchText);
        try {
            if (returnedParts.size() != 0) {
                partsTableView1.setItems(returnedParts);
            }
            else {
                int textNumSearch = Integer.parseInt(searchText);
                Part idPartResult = Inventory.lookUpPart(textNumSearch);
                if (idPartResult != null) {
                    TableView.TableViewSelectionModel<Part> selectionModel = partsTableView1.getSelectionModel();
                    selectionModel.select(idPartResult);
                }
            }
        }
        catch (NumberFormatException e){
            //Do Nothing
        }
    }

    public void onRemoveAssociatedPartClick(ActionEvent actionEvent) {
        Part partToRemove = associatedPartsTableView.getSelectionModel().getSelectedItem();
        partsList.remove(partToRemove);
    }
}
