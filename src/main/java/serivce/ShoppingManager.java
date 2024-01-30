package serivce;

import Model.Product;

import java.util.List;

public interface ShoppingManager {
    void addProductToSystem();
    void deleteProductFromSystem();

    void printProductsList();
    void saveProductsToFile();
    List<Product> readFromFile();

}
