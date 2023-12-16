public class Clothing extends Product{
    private String size;
    private int colour;
    public Clothing(String productID,String productName,int availableItems,double price,String size,int colour){
        super(productID,productName,availableItems,price);
        this.size=size;
        this.colour=colour;
    }
    //
    //
    //////

}
