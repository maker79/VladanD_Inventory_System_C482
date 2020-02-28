/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vladandinventoryfxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *
 * @author Vladan
 */
public class VladanDInventoryFXMLApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // some sample data for parts and products
        InHouse inHousePart1 = new InHouse(1, "Power supply", 2.35, 35, 5, 20, 001234567);
        InHouse inHousePart2 = new InHouse(2, "Voltage regulator", 1.75, 100, 10, 50, 000003245);
        InHouse inHousePart3 = new InHouse(3, "Transistor", 0.59, 500, 5, 100, 111234567);
        InHouse inHousePart4 = new InHouse(4, "Resistor", 0.29, 500, 5, 100, 003456262);
        InHouse inHousePart5 = new InHouse(5, "Fuse", 1.00, 200, 5, 20, 000011112);
        InHouse inHousePart6 = new InHouse(6, "Capacitor", 0.25, 300, 5, 20, 111110909);
        InHouse inHousePart7 = new InHouse(7, "Battery", 2.35, 35, 5, 20, 000001234);
        
        Outsourced outsourcedPart1 = new Outsourced(8, "Switch", 3.00, 40, 1, 10, "Lsu Inc");
        Outsourced outsourcedPart2 = new Outsourced(9, "Terminal", 2.00, 50, 1, 10, "UTBS CO");
        Outsourced outsourcedPart3 = new Outsourced(10, "Lamp", 5.00, 35, 1, 10, "XYZ Company");
        
        Product product1 = new Product(1, "Single-Sided PCB", 125.99, 50, 5, 20);
        Product product2 = new Product(2, "Double-Sided PCB", 175.99, 50, 5, 20);
        Product product3 = new Product(3, "Multilayer PCB", 199.99, 50, 5, 20);
        
        // add In House Parts
        Inventory.addPart(inHousePart1);
        Inventory.addPart(inHousePart2);
        Inventory.addPart(inHousePart3);
        Inventory.addPart(inHousePart4);
        Inventory.addPart(inHousePart5);
        Inventory.addPart(inHousePart6);
        Inventory.addPart(inHousePart7);
        // add Outsourced parts
        Inventory.addPart(outsourcedPart1);
        Inventory.addPart(outsourcedPart2);
        Inventory.addPart(outsourcedPart3);
        // add products
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        //add associated parts to products
        product1.addAssociatedPart(inHousePart1);
        product1.addAssociatedPart(inHousePart2);
        product1.addAssociatedPart(inHousePart3);
        product1.addAssociatedPart(inHousePart4);
        product1.addAssociatedPart(inHousePart5);
        product1.addAssociatedPart(outsourcedPart1);
        
        product2.addAssociatedPart(inHousePart1);
        product2.addAssociatedPart(inHousePart5);
        product2.addAssociatedPart(inHousePart6);
        product2.addAssociatedPart(inHousePart7);
        product2.addAssociatedPart(outsourcedPart2);
        
        product3.addAssociatedPart(inHousePart5);
        product3.addAssociatedPart(inHousePart6);
        product3.addAssociatedPart(inHousePart7);
        product3.addAssociatedPart(outsourcedPart3);
        product3.addAssociatedPart(outsourcedPart3);
        
        
        
        
        launch(args);
    }
    
}
