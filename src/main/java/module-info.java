module com.c482performanceassessment.c482performanceassessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    opens com.c482performanceassessment.main to javafx.fxml;
    exports com.c482performanceassessment.main;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
}