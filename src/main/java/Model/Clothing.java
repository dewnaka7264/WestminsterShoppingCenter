package Model;

import Model.Product;

public class Clothing extends Product {
    private String size;
    private String colour;
    public Clothing(String productID,String productName,int availableItems,double price,String size,String colour){
        super(productID,productName,availableItems,price);
        this.size=size;
        this.colour=colour;
    }
    //

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    @Override
    public String toString() {
        return
                        "ProductID: " + getProductID() + "\n" +
                        "Model.Product Name: " + getProductName() +  "\n" +
                        "Available items: " + getAvailableItems() +  "\n" +
                        "Price: " + getPrice() +  "\n"+
                        "Colour: " + getColour() +  "\n" +
                        "Size: " + getSize();
    }


}
