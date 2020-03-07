/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Vladan
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> lookedupParts = FXCollections.observableArrayList();
    private static ObservableList<Product> lookedupProducts = FXCollections.observableArrayList();
    // variables for creating auto generated id nummber for parts and products
    // numbers include manually created test data
    private static int partIdCount = 10;
    private static int productIdCount = 3;

    // This method will add new Part
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    // This method will add new Product
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    // This method will Look up parts using part ID 
    public static Part lookupPart(int partId){
        for(Part p : allParts){
            if(p.getPartId() == partId)
                return p;
        }
        return null;
    }
    
    // This method will Look up products using product ID
    public static Product lookupProduct(int productId){
        for(Product r : allProducts) {
            if(r.getProductId() == productId)
                return r;
        }
        return null;
    }

    
    // This method will loop through observable list and look up Parts by Name
    public static ObservableList<Part> lookupPart(String partName){
        
        for(int i = 0; i < allParts.size(); i++){
            Part p = allParts.get(i);
            
            if(p.getPartName().contains(partName)){
                lookedupParts.add(p);
            }
        }
        return lookedupParts;   
        }
    
    // This method will loop through observable list and look up Products by Name
    public static ObservableList<Product> lookupProduct(String productName){
        
        for(int i = 0; i < allProducts.size(); i++){
            Product pr = allProducts.get(i);
            
            if(pr.getProductName().contains(productName)){
                lookedupProducts.add(pr);
            }
        }
        return lookedupProducts;
    }
    
    // This method will update Part table
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    // This method will update Product table
     public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    // This method will delete selected Part
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }
    
    // This method will delete selected Product
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }
    
    // This method will get all the parts from the inventory
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    // This method will get all the products from the inventory
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    public static int handlePartIdCount(){
        partIdCount++;
        return partIdCount;
    }
    
    public static int handleProductIdCount(){
        productIdCount++;
        return productIdCount;
    }
    
    
 }
