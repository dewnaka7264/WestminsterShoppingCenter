public interface ShoppingManager {
    void addProductToSystem(Product product);
    void deleteProductFromSystem(String productID);

    void printProduceList();
    void saveProduceToFile();

    ///
}
