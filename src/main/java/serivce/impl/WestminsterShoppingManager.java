package serivce.impl;

import Model.Clothing;
import Model.Electronics;
import Model.Product;
import serivce.impl.ConsoleMenu;

import java.io.*;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static serivce.impl.ConsoleMenu.*;

public class WestminsterShoppingManager implements serivce.ShoppingManager{


    @Override
    public static void addProductToSystem() {
        System.out.println("Adding a new product to the system");
        if (MaxProductCount == 50) {
            System.out.println("System has reached the maximum number of 50 products");
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter productID:");
            String productID;
            while (true) {
                productID = scanner.next();
                if (!isProductIDDuplicate(productID)) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Model.Product with ID " + productID + " already exists. Please enter a different product ID.");
                }
            }
            scanner.nextLine();
            System.out.println("Enter product name:");
            String productName = scanner.nextLine();
            //scanner.nextLine();
            System.out.println("Enter price:");
            double price = scanner.nextDouble();
            System.out.println("Number of available items;");
//            int availableItems = scanner.nextInt();
            int availableItems;
            while (true) {
                try {
                    availableItems = scanner.nextInt();
                    if (availableItems <= 50-MaxProductCount && availableItems > 0) {
                        break; // Valid input, exit the loop
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and " + (50-MaxProductCount) + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }}
            System.out.println("Enter product Type (Model.Electronics or Model.Clothing) - E or C:");
            String product_Type;
            while (true) {
                product_Type = scanner.next().toUpperCase(); // Convert input to uppercase for case-insensitive comparison
                if ("E".equalsIgnoreCase(product_Type) || "C".equalsIgnoreCase(product_Type)) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Invalid input. Please enter 'E' for Model.Electronics or 'C' for Model.Clothing.");
                }
            }
            if ("E".equals(product_Type)) {
                System.out.println("Enter product brand: ");
                String brand = scanner.next();
                System.out.println("Enter warranty period(in weeks): ");
                int warrantyPeriod = scanner.nextInt();
                Product newproduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                productArrayList.add(newproduct);
                MaxProductCount= MaxProductCount+availableItems;

                System.out.println("New Model.Electronics products added to the system.");


            } else if ("C".equals(product_Type)) {
                System.out.println("Enter cloth size: ");
                String size = scanner.next();
                System.out.println("Enter cloth colour: ");
                String colour = scanner.next();

                Product newproduct = new Clothing(productID, productName, availableItems, price, size, colour);
                productArrayList.add(newproduct);
                MaxProductCount= MaxProductCount+availableItems;

                System.out.println("New clothing products added to the system.");

            }
        }
    }


    @Override
    public static void deleteProductFromSystem() {
        System.out.println("Deleting a product by ID:");
        if (productArrayList.size() > 0) {
            System.out.println("Enter product ID: ");
            Scanner newscanner = new Scanner(System.in);
            String productIDtoDelete = newscanner.next();
            newscanner.nextLine();

            // Check if the product with the specified ID exists
            Product productToDelete = getProductById(productArrayList, productIDtoDelete);

            if (productToDelete != null) {
                int deletedProductCount = productToDelete.getAvailableItems();
                MaxProductCount = MaxProductCount - deletedProductCount;
                productArrayList.remove(productToDelete);
                System.out.println("This product has been deleted from the system.");
            } else {
                System.out.println("Model.Product not found with ID: " + productIDtoDelete);
            }
        } else {
            System.out.println("No existing products in the system.");
        }
    }

    @Override
    public static void printProductsList() {
        System.out.println("Printing the product List");
        System.out.println("---------------------------------------");
        if (productArrayList.size() > 0) {
            Collections.sort(productArrayList, (p1, p2) -> p1.getProductID().compareTo(p2.getProductID()));
            for (Product product : productArrayList) {
                System.out.println(product.toString());
                System.out.println("---------------------------------------");
            }
        }else{
            System.out.println("Model.Product List is empty.");
        }
    }

    @Override
    public static void saveProductsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.ser"))) {
            outputStream.writeObject(productArrayList);
            System.out.println("Model.Product list saved to " + "Products.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public static List<Product> readFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.ser"))) {
            List<Product> loadedProductList = (List<Product>) inputStream.readObject();
            System.out.println("Loaded product list: " + loadedProductList);
            return loadedProductList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
