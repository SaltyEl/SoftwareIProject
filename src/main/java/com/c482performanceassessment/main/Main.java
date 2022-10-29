package com.c482performanceassessment.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

import java.io.IOException;

/**
 * This class creates an app for Parts and Products, which can be manipulated in various ways.
 * <br>
 * <br>
 * FUTURE ENHANCEMENTS - This application could be enhanced further in many ways.
 * <br>
 * 1. Store data that is added in long-term storage instead of just locally.<br>
 * 2. Create windowLoader method that could be used statically throughout the application.<br>
 * 3. Create a login window in order to protect sensitive inventory information.
 *
 * @author Blake Ramsey
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method launches the application, and adds pre-made parts and products.
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {

        //Initial TableView Part and Product Objects.
        InHouse defaultOutsourcedPart = new InHouse(1, "Bike Seat", 15.99, 5, 1, 10, 5555);
        InHouse defaultOutsourcedPart2 = new InHouse(2, "Bike Tire", 20.99, 7, 1, 10, 5556);
        InHouse defaultOutsourcedPart3 = new InHouse(3, "Bike Frame", 59.99, 3, 1, 10, 5557);

        Inventory.addPart(defaultOutsourcedPart);
        Inventory.addPart(defaultOutsourcedPart2);
        Inventory.addPart(defaultOutsourcedPart3);

        Product defaultProduct1 = new Product(1, "Street Bike", 399.99, 3, 0, 10);
        Product defaultProduct2 = new Product(2, "Mountain Bike", 499.99, 5, 0, 10);
        defaultProduct2.addAssociatedPart(defaultOutsourcedPart);

        Inventory.addProduct(defaultProduct1);
        Inventory.addProduct(defaultProduct2);

        launch();
    }
}
