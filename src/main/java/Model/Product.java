package Model;

import java.io.Serializable;

public  abstract class  Product implements Serializable {
    private String productID;
    private String productName;
    private int availableItems;
    private double price;


    public Product(String productID,String productName,int availableItems,double price){
        this.productID=productID;
        this.productName=productName;
        this.availableItems=availableItems;
        this.price=price;
        }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public double getPrice() {
        return price;
    }
    /// setters
    public void purchase(int quantity) {
        availableItems -=quantity;
}
    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
            return
                    "ProductID: " + getProductID() + "\n" +
                            "Model.Product Name: " + getProductName() +  "\n" +
                            "Available items: " + getAvailableItems() +  "\n" +
                            "Price: " + getPrice() +  "\n";

        }
}
