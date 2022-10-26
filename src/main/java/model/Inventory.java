package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;

import static controller.ModifyPart.modifiedPart;

public class Inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){ allParts.add(newPart);}

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    //Search for Part by ID
    public static Part lookUpPart(int partId) {
        ObservableList<Part> allPartsList = getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part partResult = allPartsList.get(i);
            if (partResult.getId() == partId){
                return partResult;
            }
        }
        return null;
    }

    //Search for product by ID

    public static Product lookUpProduct (int productId){
        ObservableList<Product> allProductsList = getAllProducts();

        for (int i = 0; i < allParts.size(); i++) {
            Product productResult = allProductsList.get(i);
            if (productResult.getId() == productId){
                return productResult;
            }
        }
        return null;
    }
    //Search for Part by Name
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> allPartsTemp = getAllParts();
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part part : allPartsTemp){
            if (part.getName().contains(partName)){
                namedParts.add(part);
            }
        }
        return namedParts;
    }

    //Search for Product by name
    public static ObservableList<Product> lookUpProduct (String productName) {
        ObservableList<Product> allProductsTemp = getAllProducts();
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product product : allProductsTemp) {
            if (product.getName().contains(productName)){
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




}
