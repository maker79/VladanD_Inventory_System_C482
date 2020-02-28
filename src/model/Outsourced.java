/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Vladan
 */
public class Outsourced extends Part {
    
    private String companyName;

    public Outsourced(int partId, String partName, double partPrice, int partStock, int partMin, int partMax, String companyName) {
        super(partId, partName, partPrice, partStock, partMin, partMax);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
