public class Electronics extends Product{

    private String brand;
    private int warrantyPeriod;
    public Electronics(String productID,String productName,int availableItems,double price,String brand,int warrantyPeriod){
        super(productID,productName,availableItems,price);
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;
    }
    //getters+setters
    //methosds
}
