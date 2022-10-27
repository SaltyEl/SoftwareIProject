package com.c482performanceassessment.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }


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
