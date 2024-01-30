package Model;

import Model.Product;

public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;
    public Electronics(String productID,String productName,int availableItems,double price,String brand,int warrantyPeriod){
        super(productID,productName,availableItems,price);
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    //getters+setters

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    @Override
    public String toString() {
        return
                        "ProductID: " + getProductID() + "\n" +
                        "Model.Product Name: " + getProductName() +  "\n" +
                        "Available items: " + getAvailableItems() +  "\n" +
                        "Price: " + getPrice() +  "\n"+
                        "Brand: " + getBrand() +  "\n" +
                        "Warranty: " + getWarrantyPeriod();
    }
    //methosds
}
