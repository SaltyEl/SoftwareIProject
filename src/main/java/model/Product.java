package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class allows for Products to be created and manipulated throughout the program.
 *
 * @author Blake Ramsey
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return Returns ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets product ID
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Returns product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set product name.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Returns the product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return Returns the product stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the product stock.
     * @param stock The stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return Returns the minimum product stock.
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum product stock.
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return Returns the max product stock.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max product stock.
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated part to associatedParts list.
     * @param part part to add.
     */
    public void addAssociatedPart(Part part) {
            associatedParts.add(part);
    }

    /**
     * Deletes selected part.
     *
     * @param selectedAssociatedPart Part to delete.
     * @return Returns true when deleted.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     *
     * @return returns all associated product parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }
}
