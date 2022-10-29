package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;

import static controller.ModifyPart.modifiedPart;

/**
 * This class allows product and part inventory to be manipulated throughout the program.
 *
 * @author Blake Ramsey
 */
public class Inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){ allParts.add(newPart);}

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Search for part by ID
     *
     * @param partId the partId to search for
     * @return Returns the part if found, null if not.
     */
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

    /**
     * Search for product by ID.
     *
     * @param productId the product ID to search for
     * @return Returns the product if found, null if not.
     */

    public static Product lookUpProduct (int productId){
        ObservableList<Product> allProductsList = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product productResult = allProductsList.get(i);
            if (productResult.getId() == productId){
                return productResult;
            }
        }
        return null;
    }

    /**
     * Search for part by Name
     *
     * @param partName the Part name to search for.
     * @return Returns the part if found, or empty list if not.
     */
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

    /**
     * Search for product by name.
     *
     * @param productName The product name to search for.
     * @return Returns the product if found, or empty list if not.
     */
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

    /**
     * Updates part and assigns it to proper index.
     *
     * @param index The index to which the part will be assigned.
     * @param selectedPart The part to update.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product and assigns to proper index.
     *
     * @param index The index to which the product will be assigned.
     * @param newProduct The product to update.
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes part.
     * @param selectedPart The part to delete.
     */
    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    /**
     * Deletes product.
     * @param selectedProduct The product to delete.
     */
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }

    /**
     * @return allParts list.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * @return allProducts list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




}
