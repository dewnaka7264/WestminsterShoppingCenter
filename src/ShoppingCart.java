import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> products;
    public ShoppingCart(){
        this.products=new ArrayList<Product>();
    }
    public void addProduct(Product product){

    }

    public void removeProduct(Product product){

    }
    public void totalCost(){
        double totalBill=0;
        for (Product product:products
             ) {
            //totalBill=totalBill+product.getPrice
            
        }
        System.out.println("total is: "+totalBill);
    }

}
